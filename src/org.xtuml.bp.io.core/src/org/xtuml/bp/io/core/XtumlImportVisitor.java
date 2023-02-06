package org.xtuml.bp.io.core;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.core.resources.IResource;
import org.xtuml.bp.core.ComponentResultSet_c;
import org.xtuml.bp.core.ComponentVisibility_c;
import org.xtuml.bp.core.Component_c;
import org.xtuml.bp.core.DataType_c;
import org.xtuml.bp.core.ElementVisibility_c;
import org.xtuml.bp.core.Elementtypeconstants_c;
import org.xtuml.bp.core.ExecutableProperty_c;
import org.xtuml.bp.core.Gd_c;
import org.xtuml.bp.core.Ifdirectiontype_c;
import org.xtuml.bp.core.InstanceReferenceDataType_c;
import org.xtuml.bp.core.InterfaceOperation_c;
import org.xtuml.bp.core.InterfaceSignal_c;
import org.xtuml.bp.core.Interface_c;
import org.xtuml.bp.core.ModelClass_c;
import org.xtuml.bp.core.Ooaofooa;
import org.xtuml.bp.core.Package_c;
import org.xtuml.bp.core.PackageableElement_c;
import org.xtuml.bp.core.PropertyParameter_c;
import org.xtuml.bp.core.SearchResultSet_c;
import org.xtuml.bp.core.Visibility_c;
import org.xtuml.bp.core.common.NonRootModelElement;
import org.xtuml.bp.core.common.PersistenceManager;
import org.xtuml.bp.core.util.DimensionsUtil;
import org.xtuml.bp.io.core.XtumlParser.Array_type_referenceContext;
import org.xtuml.bp.io.core.XtumlParser.Inst_set_type_referenceContext;
import org.xtuml.bp.io.core.XtumlParser.Inst_type_referenceContext;
import org.xtuml.bp.io.core.XtumlParser.Interface_definitionContext;
import org.xtuml.bp.io.core.XtumlParser.MarkContext;
import org.xtuml.bp.io.core.XtumlParser.Message_declarationContext;
import org.xtuml.bp.io.core.XtumlParser.Named_type_referenceContext;
import org.xtuml.bp.io.core.XtumlParser.ParameterContext;
import org.xtuml.bp.io.core.XtumlParser.Parameter_listContext;
import org.xtuml.bp.io.core.XtumlParser.TargetContext;

public class XtumlImportVisitor extends XtumlBaseVisitor<NonRootModelElement> {

	private final Ooaofooa modelRoot;
	private final IResource resource;
	private NonRootModelElement currentRoot = null;

	private final DataType_c defaultType;
	private final DataType_c voidType;

	private static final String MESSAGE_NUM = "message_num";

	public XtumlImportVisitor(final Ooaofooa modelRoot, final IResource resource) {
		this.modelRoot = modelRoot;
		this.resource = resource;
		this.defaultType = (DataType_c) modelRoot.getInstanceList(DataType_c.class)
				.get("ba5eda7a-def5-0000-0000-000000000002");
		this.voidType = (DataType_c) modelRoot.getInstanceList(DataType_c.class)
				.get("ba5eda7a-def5-0000-0000-000000000000");
	}

	@Override
	public NonRootModelElement visitTarget(TargetContext ctx) {
		try {
			final Package_c parent_pkg = PersistenceManager.getDefaultInstance().selectAndWait(resource, () -> {
				return Optional.ofNullable(Package_c.PackageInstance(modelRoot,
						selected -> ((Package_c) selected).getPath().equals(ctx.pkg.getText())));
			}).get();
			currentRoot = parent_pkg;
			return visit(ctx.interface_definition());
		} catch (InterruptedException | ExecutionException | CancellationException e) {
			throw new CoreImport.XtumlLoadException("Failed to find package '" + ctx.pkg.getText() + "'.", e);
		}
	}

	@Override
	public NonRootModelElement visitInterface_definition(Interface_definitionContext ctx) {
		final Package_c parent_pkg = (Package_c) currentRoot;

		// find or create interface
		final String iface_name = ctx.iface_name.getText();
		final Interface_c iface = Interface_c.resolveInstance(modelRoot, UUID.randomUUID(), parent_pkg.getPackage_id(),
				iface_name, "", parent_pkg.getPath() + "::" + iface_name);
		final PackageableElement_c pe = new PackageableElement_c(modelRoot, iface.getId(), Visibility_c.Public,
				parent_pkg.getPackage_id(), null, Elementtypeconstants_c.INTERFACE);
		pe.relateAcrossR8000To(parent_pkg);
		iface.relateAcrossR8001To(pe);
		iface.setName(ctx.iface_name.getText());
		currentRoot = iface;

		// set interface description
		if (ctx.description() != null) {
			iface.setDescrip(ctx.description().getText().lines().map(line -> line.replace("//!", "").strip())
					.collect(Collectors.joining(System.lineSeparator())));
		}

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
		final int direction = "to".equals(ctx.direction.getText()) ? Ifdirectiontype_c.ClientServer
				: Ifdirectiontype_c.ServerClient;

		// find or create executable property
		final ExecutableProperty_c c_ep = ExecutableProperty_c.resolveInstance(modelRoot, UUID.randomUUID(),
				((Interface_c) currentRoot).getId(), direction, name, "", 0, currentRoot.getPath() + "::" + name);
		if (ctx.type_reference() != null) {
			c_ep.unrelateAcrossR4004From(InterfaceOperation_c.getOneC_IOOnR4004(c_ep));
			final InterfaceOperation_c c_io = new InterfaceOperation_c(modelRoot);
			c_io.relateAcrossR4008To(voidType);
			c_ep.relateAcrossR4004To(c_io);
		} else {
			c_ep.unrelateAcrossR4004From(InterfaceSignal_c.getOneC_ASOnR4004(c_ep));
			c_ep.relateAcrossR4004To(new InterfaceSignal_c(modelRoot));
		}

		// get the message description
		final String messageDescription = ctx.description() != null ? ctx.description().getText().lines()
				.map(line -> line.replace("//!", "").strip()).collect(Collectors.joining(System.lineSeparator())) : "";

		// process marks
		// TODO eventually, this should tie in with the marking editor, but for
		// now it is just used to get the message number
		for (MarkContext mark : ctx.mark()) {
			if (MESSAGE_NUM.equals(mark.MarkName().getText().substring(1))) {
				c_ep.setNumb(Integer.parseInt(mark.mark_arguments().mark_argument(0).getText()));
			}
		}

		// set subtype specific info
		final InterfaceOperation_c c_io = InterfaceOperation_c.getOneC_IOOnR4004(c_ep);
		final InterfaceSignal_c c_as = InterfaceSignal_c.getOneC_ASOnR4004(c_ep);
		if (c_io != null) {
			c_io.setName(name);
			c_io.setDescrip(messageDescription);
			c_io.setDirection(direction);

			// set return type
			c_io.unrelateAcrossR4008From(DataType_c.getOneS_DTOnR4008(c_io));
			c_io.relateAcrossR4008To((DataType_c) Optional.ofNullable(visit(ctx.type_reference())).orElse(voidType));

			// set return dimensions
			String dim_string = getDimString(ctx.type_reference().array_type_reference());
			c_io.setReturn_dimensions(dim_string);
			List<Integer> dims = DimensionsUtil.getDimensionsData(dim_string, c_io);
			for (int i = 0; i < dims.size(); i++) {
				c_io.Resizereturn_dimensions(i, dims.get(i), dims.size());
			}
		} else {
			c_as.setName(name);
			c_as.setDescrip(messageDescription);
			c_as.setDirection(direction);
		}

		// delete any existing parameters
		Stream.of(PropertyParameter_c.getManyC_PPsOnR4006(c_ep)).forEach(PropertyParameter_c::Dispose);

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
		c_pp.relateAcrossR4007To(defaultType);

		// set by value/ref
		c_pp.setBy_ref("in".equals(ctx.by_ref.getText()) ? 0 : 1);

		// link the data type
		c_pp.unrelateAcrossR4007From(DataType_c.getOneS_DTOnR4007(c_pp));
		c_pp.relateAcrossR4007To((DataType_c) Optional.ofNullable(visit(ctx.type_reference())).orElse(defaultType));

		// set the array dimensions
		String dim_string = getDimString(ctx.type_reference().array_type_reference());
		c_pp.setDimensions(dim_string);
		List<Integer> dims = DimensionsUtil.getDimensionsData(dim_string, c_pp);
		for (int i = 0; i < dims.size(); i++) {
			c_pp.Resizedimensions(i, dims.get(i), dims.size());
		}

		return c_pp;
	}

	@Override
	public NonRootModelElement visitNamed_type_reference(Named_type_referenceContext ctx) {
		try {
			return PersistenceManager.getDefaultInstance().selectAndWait(resource, () -> {
				List<DataType_c> dts = findVisibleElements(
						PackageableElement_c.getOnePE_PEOnR8001((Interface_c) currentRoot),
						Elementtypeconstants_c.DATATYPE).stream().map(DataType_c::getOneS_DTOnR8001)
						.filter(dt -> dt.getPath().endsWith(ctx.scoped_name().getText())).collect(Collectors.toList());
				if (dts.isEmpty()) {
					return Optional.empty();
				} else {
					if (dts.size() > 1) {
						throw new IllegalArgumentException(
								"The given path corresponds to more than one unique element");
					}
					return Optional.of(dts.get(0));
				}
			}).get();
		} catch (InterruptedException | ExecutionException | CancellationException e) {
			throw new CoreImport.XtumlLoadException(
					"Failed to find type '" + ctx.scoped_name().getText() + "' for named type reference.", e);
		}
	}

	@Override
	public NonRootModelElement visitArray_type_reference(Array_type_referenceContext ctx) {
		return visit(ctx.type_reference());
	}

	@Override
	public NonRootModelElement visitInst_type_reference(Inst_type_referenceContext ctx) {
		try {
			return PersistenceManager.getDefaultInstance().selectAndWait(resource, () -> {
				List<ModelClass_c> objs = findVisibleElements(
						PackageableElement_c.getOnePE_PEOnR8001((Interface_c) currentRoot),
						Elementtypeconstants_c.CLASS)
						.stream().map(ModelClass_c::getOneO_OBJOnR8001)
						.filter(obj -> (Package_c.getOneEP_PKGOnR8000(PackageableElement_c.getOnePE_PEOnR8001(obj))
								.getPath() + "::" + obj.getKey_lett()).endsWith(ctx.scoped_name().getText()))
						.collect(Collectors.toList());
				if (objs.isEmpty()) {
					return Optional.empty();
				} else {
					if (objs.size() > 1) {
						throw new IllegalArgumentException(
								"The given path corresponds to more than one unique element");
					}
					objs.get(0).Newinstancereferencedatatype();
					return Optional.ofNullable(
							DataType_c.getOneS_DTOnR17(InstanceReferenceDataType_c.getOneS_IRDTOnR123(objs.get(0),
									selected -> !((InstanceReferenceDataType_c) selected).getIsset())));
				}
			}).get();
		} catch (InterruptedException | ExecutionException | CancellationException e) {
			throw new CoreImport.XtumlLoadException(
					"Failed to find class '" + ctx.scoped_name().getText() + "' for instance type reference.", e);
		}
	}

	@Override
	public NonRootModelElement visitInst_set_type_reference(Inst_set_type_referenceContext ctx) {
		return DataType_c.getOneS_DTOnR17(InstanceReferenceDataType_c.getOneS_IRDTOnR123(
				ModelClass_c.getOneO_OBJOnR123(
						InstanceReferenceDataType_c.getOneS_IRDTOnR17((DataType_c) visit(ctx.inst_type_reference()))),
				selected -> ((InstanceReferenceDataType_c) selected).getIsset()));
	}

	private String getDimString(final Array_type_referenceContext ctx) {
		String dim_string = "";
		Array_type_referenceContext array_type_ref = ctx;
		while (array_type_ref != null) {
			if (array_type_ref.bound != null) {
				dim_string += "[" + array_type_ref.bound.getText() + "]";
			} else {
				dim_string += "[]";
			}
			array_type_ref = array_type_ref.type_reference().array_type_reference();
		}
		return dim_string;
	}

	private List<PackageableElement_c> findVisibleElements(final PackageableElement_c referencePe,
			final int elementType) {
		final Package_c pkg = Package_c.getOneEP_PKGOnR8000(referencePe);
		final Component_c comp = Component_c.getOneC_COnR8003(referencePe);
		if (pkg != null) {
			pkg.Clearscope();
			pkg.Collectvisibleelementsforname(true, Gd_c.Null_unique_id(), false, "", pkg.getPackage_id(), elementType);
			final SearchResultSet_c results = SearchResultSet_c.getOnePE_SRSOnR8005(pkg,
					selected -> "".equals(((SearchResultSet_c) selected).getName())
							&& ((SearchResultSet_c) selected).getType() == elementType);
			return List
					.of(PackageableElement_c.getManyPE_PEsOnR8002(ElementVisibility_c.getManyPE_VISsOnR8006(results)));
		} else if (comp != null) {
			comp.Clearscope();
			comp.Collectvisibleelementsforname(true, Gd_c.Null_unique_id(), "", comp.getId(), elementType);
			final ComponentResultSet_c results = ComponentResultSet_c.getOnePE_CRSOnR8007(comp,
					selected -> "".equals(((ComponentResultSet_c) selected).getName())
							&& ((ComponentResultSet_c) selected).getType() == elementType);
			return List.of(
					PackageableElement_c.getManyPE_PEsOnR8004(ComponentVisibility_c.getManyPE_CVSsOnR8008(results)));

		} else {
			return Collections.emptyList();
		}
	}

}
