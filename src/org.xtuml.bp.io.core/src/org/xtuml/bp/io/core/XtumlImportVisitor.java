package org.xtuml.bp.io.core;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.xtuml.bp.core.ComponentResultSet_c;
import org.xtuml.bp.core.ComponentVisibility_c;
import org.xtuml.bp.core.Component_c;
import org.xtuml.bp.core.DataType_c;
import org.xtuml.bp.core.ElementVisibility_c;
import org.xtuml.bp.core.Elementtypeconstants_c;
import org.xtuml.bp.core.ExecutableProperty_c;
import org.xtuml.bp.core.Gd_c;
import org.xtuml.bp.core.Ifdirectiontype_c;
import org.xtuml.bp.core.InterfaceOperation_c;
import org.xtuml.bp.core.InterfaceSignal_c;
import org.xtuml.bp.core.Interface_c;
import org.xtuml.bp.core.Ooaofooa;
import org.xtuml.bp.core.Package_c;
import org.xtuml.bp.core.PackageableElement_c;
import org.xtuml.bp.core.PropertyParameter_c;
import org.xtuml.bp.core.SearchResultSet_c;
import org.xtuml.bp.core.SystemModel_c;
import org.xtuml.bp.core.Visibility_c;
import org.xtuml.bp.core.common.NonRootModelElement;
import org.xtuml.bp.core.common.PersistenceManager;
import org.xtuml.bp.io.core.XtumlParser.Message_declarationContext;
import org.xtuml.bp.io.core.XtumlParser.ParameterContext;
import org.xtuml.bp.io.core.XtumlParser.Parameter_listContext;
import org.xtuml.bp.io.core.XtumlParser.TargetContext;

public class XtumlImportVisitor extends XtumlBaseVisitor<NonRootModelElement> {

	private final Ooaofooa modelRoot;
	private NonRootModelElement currentRoot = null;

	private final DataType_c defaultType;

	public XtumlImportVisitor(final Ooaofooa modelRoot) {
		this.modelRoot = modelRoot;
		this.defaultType = (DataType_c) modelRoot.getInstanceList(DataType_c.class)
				.get("ba5eda7a-def5-0000-0000-000000000002");
	}

	@Override
	public NonRootModelElement visitTarget(TargetContext ctx) {
		// find or create package TODO create if does not exist
		final Package_c parent_pkg = (Package_c) findOrCreateParent(ctx.pkg.getText());

		// find or create interface
		final String iface_name = ctx.iface_name.getText();
		final UUID iface_id = UUID.nameUUIDFromBytes((parent_pkg.getPath() + "::" + iface_name).getBytes());
		Interface_c iface = (Interface_c) modelRoot.getInstanceList(Interface_c.class).getGlobal(iface_id);
		if (iface == null) {
			iface = new Interface_c(modelRoot, iface_id, parent_pkg.getPackage_id(), iface_name, "");
		} else {
			iface.convertFromProxy();
		}
		final PackageableElement_c pe = new PackageableElement_c(modelRoot, iface_id, Visibility_c.Public,
				parent_pkg.getPackage_id(), null, Elementtypeconstants_c.INTERFACE);
		pe.relateAcrossR8000To(parent_pkg);
		iface.relateAcrossR8001To(pe);
		iface.setName(ctx.iface_name.getText());
		currentRoot = iface;

		// link executable properties to interface
		final List<ExecutableProperty_c> c_eps = ctx.message_declaration().stream()
				.map(m -> (ExecutableProperty_c) visit(m)).collect(Collectors.toList());
		for (ExecutableProperty_c c_ep : c_eps) {
			c_ep.relateAcrossR4003To(iface);
		}

		// link individual operations and signals together
		final List<InterfaceOperation_c> c_ios = c_eps.stream()
				.map(c_ep -> InterfaceOperation_c.getOneC_IOOnR4004(c_ep)).filter(c_io -> c_io != null)
				.collect(Collectors.toList());
		for (int i = 0; i + 1 < c_ios.size(); i++) {
			c_ios.get(i).relateAcrossR4019ToPrecedes(c_ios.get(i + 1));
		}
		final List<InterfaceSignal_c> c_ass = c_eps.stream().map(c_ep -> InterfaceSignal_c.getOneC_ASOnR4004(c_ep))
				.filter(c_as -> c_as != null).collect(Collectors.toList());
		for (int i = 0; i + 1 < c_ass.size(); i++) {
			c_ass.get(i).relateAcrossR4020ToPrecedes(c_ass.get(i + 1));
		}

		return iface;
	}

	@Override
	public NonRootModelElement visitMessage_declaration(Message_declarationContext ctx) {
		// parse message info
		final String name = ctx.msg_name.getText();
		final UUID msg_id = UUID.nameUUIDFromBytes((currentRoot.getPath() + "::" + name).getBytes());
		final String typeName = ctx.type_name != null ? ctx.type_name.getText() : null;
		final int direction = ctx.to != null ? Ifdirectiontype_c.ClientServer : Ifdirectiontype_c.ServerClient;

		// find or create executable property
		ExecutableProperty_c c_ep = (ExecutableProperty_c) modelRoot.getInstanceList(ExecutableProperty_c.class)
				.getGlobal(msg_id);
		if (c_ep == null) {
			c_ep = new ExecutableProperty_c(modelRoot, msg_id, ((Interface_c) currentRoot).getId(), direction, name, "",
					0);
		} else {
			c_ep.convertFromProxy();
		}
		c_ep.setDirection(direction);
		if (typeName != null) {
			final InterfaceOperation_c c_io = new InterfaceOperation_c(modelRoot);
			c_io.relateAcrossR4004To(c_ep);
		} else {
			final InterfaceSignal_c c_as = new InterfaceSignal_c(modelRoot);
			c_as.relateAcrossR4004To(c_ep);
		}

		// TODO for now, unconditionally delete any existing parameters
		Stream.of(PropertyParameter_c.getManyC_PPsOnR4006(c_ep)).forEach(PropertyParameter_c::Dispose);

		// set subtype specific info
		final InterfaceOperation_c c_io = InterfaceOperation_c.getOneC_IOOnR4004(c_ep);
		final InterfaceSignal_c c_as = InterfaceSignal_c.getOneC_ASOnR4004(c_ep);
		if (c_io != null) {
			final DataType_c dt = DataType_c.DataTypeInstance(Ooaofooa.getDefaultInstance(),
					selected -> ((DataType_c) selected).getName().equals(ctx.type_name.getText()));
			c_io.relateAcrossR4008To(dt);
			c_io.setName(name);
			c_io.setDirection(direction);
		} else {
			c_as.setName(name);
			c_as.setDirection(direction);
		}

		// link parameters to the message
		if (ctx.parameter_list() != null) {
			PropertyParameter_c c_pp = (PropertyParameter_c) visit(ctx.parameter_list());
			while (c_pp != null) {
				c_ep.relateAcrossR4006To(c_pp);
				c_pp = PropertyParameter_c.getOneC_PPOnR4021Precedes(c_pp);
			}
		}

		return c_ep;
	}

	@Override
	public NonRootModelElement visitParameter_list(Parameter_listContext ctx) {
		// link parameters to each other in order
		List<PropertyParameter_c> c_pps = ctx.parameter().stream().map(p -> (PropertyParameter_c) visit(p))
				.collect(Collectors.toList());
		for (int i = 0; i + 1 < c_pps.size(); i++) {
			c_pps.get(i).relateAcrossR4021ToPrecedes(c_pps.get(i + 1));
		}
		return !c_pps.isEmpty() ? c_pps.get(0) : null;
	}

	@Override
	public NonRootModelElement visitParameter(ParameterContext ctx) {
		// create a new parameter
		PropertyParameter_c c_pp = new PropertyParameter_c(modelRoot);
		c_pp.setName(ctx.param_name.getText());
		c_pp.relateAcrossR4007To(this.defaultType);

		// link the data type
		try {
			final DataType_c dt = PersistenceManager.getDefaultInstance().selectAndWait(() -> {
				System.out.println("Selecting in: " + Thread.currentThread().getName());
				List<DataType_c> dts = findVisibleElements(
						PackageableElement_c.getOnePE_PEOnR8001((Interface_c) currentRoot),
						Elementtypeconstants_c.DATATYPE).stream().map(DataType_c::getOneS_DTOnR8001)
						.filter(d -> d.getName().endsWith(ctx.type_name.getText())).collect(Collectors.toList());
				if (dts.isEmpty()) {
					return Optional.empty();
				} else {
					if (dts.size() > 1) {
						// TODO warn!
					}
					return Optional.of(dts.get(0));
				}
			}).get();
			c_pp.unrelateAcrossR4007From(DataType_c.getOneS_DTOnR4007(c_pp));
			c_pp.relateAcrossR4007To(dt);
		} catch (InterruptedException | ExecutionException e) {
			// TODO warn!
		}
		return c_pp;
	}

	private NonRootModelElement findOrCreateParent(final String path) {
		// search for a system model
		SystemModel_c sys = SystemModel_c.SystemModelInstance(modelRoot,
				selected -> ((SystemModel_c) selected).getPath().equals(path));
		if (sys != null) {
			return sys;
		}

		// search for a package
		Package_c pkg = Package_c.PackageInstance(modelRoot, selected -> ((Package_c) selected).getPath().equals(path));
		if (pkg != null) {
			return pkg;
		}

		// TODO search for a component?

		// create new package
		pkg = new Package_c(modelRoot);
		pkg.setName(path.substring(path.lastIndexOf("::") + 2)); // Note: assumes at least one '::' in the path
		PackageableElement_c pe = new PackageableElement_c(modelRoot, UUID.randomUUID(), Visibility_c.Public, null,
				null, Elementtypeconstants_c.PACKAGE);
		pkg.relateAcrossR8001To(pe);

		// find or create the parent
		final NonRootModelElement parent = findOrCreateParent(path.substring(0, path.lastIndexOf("::")));
		if (parent instanceof SystemModel_c) {
			pkg.relateAcrossR1401To((SystemModel_c) parent);
			pkg.relateAcrossR1405To((SystemModel_c) parent);
		} else if (parent instanceof Package_c) {
			pe.relateAcrossR8000To((Package_c) parent);
			sys = SystemModel_c.getOneS_SYSOnR1405((Package_c) parent);
			pkg.relateAcrossR1405To(sys);
			// } else if (parent instanceof Component_c) { // TODO?
		}

		return pkg;
	}

	private List<PackageableElement_c> findVisibleElements(final PackageableElement_c referencePe,
			final int elementType) {
		final Package_c pkg = Package_c.getOneEP_PKGOnR8000(referencePe);
		final Component_c comp = Component_c.getOneC_COnR8003(referencePe);
		if (pkg != null) {
			pkg.Clearscope();
			pkg.Collectvisibleelementsforname(true, Gd_c.Null_unique_id(), false, "", pkg.getPackage_id(), elementType);
			final SearchResultSet_c results = SearchResultSet_c.getOnePE_SRSOnR8005(pkg, candidate -> {
				final SearchResultSet_c selected = (SearchResultSet_c) candidate;
				return (selected.getName().equals("") && selected.getType() == elementType);
			});
			return List
					.of(PackageableElement_c.getManyPE_PEsOnR8002(ElementVisibility_c.getManyPE_VISsOnR8006(results)));
		} else if (comp != null) {
			comp.Clearscope();
			comp.Collectvisibleelementsforname(true, Gd_c.Null_unique_id(), "", comp.getId(), elementType);
			final ComponentResultSet_c results = ComponentResultSet_c.getOnePE_CRSOnR8007(comp, candidate -> {
				final ComponentResultSet_c selected = (ComponentResultSet_c) candidate;
				return (selected.getName().equals("") && selected.getType() == elementType);
			});
			return List.of(
					PackageableElement_c.getManyPE_PEsOnR8004(ComponentVisibility_c.getManyPE_CVSsOnR8008(results)));

		} else {
			return Collections.emptyList();
		}
	}

}
