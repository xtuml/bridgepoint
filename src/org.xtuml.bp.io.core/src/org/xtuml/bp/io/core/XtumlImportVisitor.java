package org.xtuml.bp.io.core;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.core.resources.IResource;
import org.xtuml.bp.core.Actiondialect_c;
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
import org.xtuml.bp.core.InterfaceReference_c;
import org.xtuml.bp.core.InterfaceSignal_c;
import org.xtuml.bp.core.Interface_c;
import org.xtuml.bp.core.ModelClass_c;
import org.xtuml.bp.core.Ooaofooa;
import org.xtuml.bp.core.Package_c;
import org.xtuml.bp.core.PackageableElement_c;
import org.xtuml.bp.core.Parsestatus_c;
import org.xtuml.bp.core.Port_c;
import org.xtuml.bp.core.PropertyParameter_c;
import org.xtuml.bp.core.ProvidedExecutableProperty_c;
import org.xtuml.bp.core.ProvidedOperation_c;
import org.xtuml.bp.core.ProvidedSignal_c;
import org.xtuml.bp.core.Provision_c;
import org.xtuml.bp.core.RequiredExecutableProperty_c;
import org.xtuml.bp.core.RequiredOperation_c;
import org.xtuml.bp.core.RequiredSignal_c;
import org.xtuml.bp.core.Requirement_c;
import org.xtuml.bp.core.SearchResultSet_c;
import org.xtuml.bp.core.SystemModel_c;
import org.xtuml.bp.core.Visibility_c;
import org.xtuml.bp.core.common.IdAssigner;
import org.xtuml.bp.core.common.NonRootModelElement;
import org.xtuml.bp.core.common.PersistenceManager;
import org.xtuml.bp.core.util.DimensionsUtil;
import org.xtuml.bp.io.core.XtumlParser.Action_bodyContext;
import org.xtuml.bp.io.core.XtumlParser.Array_type_referenceContext;
import org.xtuml.bp.io.core.XtumlParser.Component_definitionContext;
import org.xtuml.bp.io.core.XtumlParser.Const_expressionContext;
import org.xtuml.bp.io.core.XtumlParser.Discontiguous_definitionContext;
import org.xtuml.bp.io.core.XtumlParser.Inst_set_type_referenceContext;
import org.xtuml.bp.io.core.XtumlParser.Inst_type_referenceContext;
import org.xtuml.bp.io.core.XtumlParser.Interface_definitionContext;
import org.xtuml.bp.io.core.XtumlParser.MarkContext;
import org.xtuml.bp.io.core.XtumlParser.Mark_argumentContext;
import org.xtuml.bp.io.core.XtumlParser.MarksContext;
import org.xtuml.bp.io.core.XtumlParser.Message_declarationContext;
import org.xtuml.bp.io.core.XtumlParser.Message_definitionContext;
import org.xtuml.bp.io.core.XtumlParser.NameContext;
import org.xtuml.bp.io.core.XtumlParser.Named_type_referenceContext;
import org.xtuml.bp.io.core.XtumlParser.Package_definitionContext;
import org.xtuml.bp.io.core.XtumlParser.ParameterContext;
import org.xtuml.bp.io.core.XtumlParser.Parameter_listContext;
import org.xtuml.bp.io.core.XtumlParser.Port_definitionContext;
import org.xtuml.bp.io.core.XtumlParser.Scoped_nameContext;

public class XtumlImportVisitor extends XtumlBaseVisitor<Object> {

	private final Ooaofooa modelRoot;
	private final IResource resource;
	private NonRootModelElement currentRoot = null;

	private final DataType_c defaultType;
	private final DataType_c voidType;

	private static final String MESSAGE_NUM = "message_num";
	private static final String SYSTEM_MODEL = "system_model";
	private static final String USE_GLOBALS = "use_globals";
	private static final String MULTIPLICITY = "mult";
	private static final String KEY_LETTERS = "key_letters";
	private static final String REALIZED = "realized";
	private static final String HIDE_GRAPHIC = "hide_graphic";
	private static final String INFORMAL_NAME = "informal_name";
	private static final String NOPARSE = "noparse";

	public XtumlImportVisitor(final Ooaofooa modelRoot, final IResource resource) {
		this.modelRoot = modelRoot;
		this.resource = resource;
		this.defaultType = (DataType_c) modelRoot.getInstanceList(DataType_c.class)
				.get("ba5eda7a-def5-0000-0000-000000000002");
		this.voidType = (DataType_c) modelRoot.getInstanceList(DataType_c.class)
				.get("ba5eda7a-def5-0000-0000-000000000000");
	}

	@Override
	public NonRootModelElement visitDiscontiguous_definition(Discontiguous_definitionContext ctx) {
		// TODO handle parent system or parent component
		try {
			currentRoot = PersistenceManager.getDefaultInstance()
					.selectAndWait(resource,
							() -> Stream
									.of(Stream.of(Package_c.PackageInstances(modelRoot)),
											Stream.of(SystemModel_c.SystemModelInstances(modelRoot)),
											Stream.of(Component_c.ComponentInstances(modelRoot)))
									.flatMap(s -> s)
									.filter(selected -> selected.getPath().equals(visit(ctx.parent_name))).findAny())
					.get();
			return (NonRootModelElement) visit(ctx.definition());
		} catch (InterruptedException | ExecutionException | CancellationException e) {
			throw new CoreImport.XtumlLoadException("Failed to find package '" + visit(ctx.parent_name) + "'.", e);
		}
	}

	@Override
	public NonRootModelElement visitPackage_definition(Package_definitionContext ctx) {
		// If we encounter the "system_model" mark, this is a system model
		// instance. Otherwise it is a regular package.
		@SuppressWarnings("unchecked")
		final Map<String, Mark> marks = ctx.marks() != null ? (Map<String, Mark>) visit(ctx.marks())
				: Collections.emptyMap();
		if (marks.containsKey(SYSTEM_MODEL)) {
			// create a system model
			final SystemModel_c s_sys = SystemModel_c.resolveInstance(modelRoot, UUID.randomUUID(),
					(String) visit(ctx.pkg_name), false, (String) visit(ctx.pkg_name));
			// look for "use_globals=true" in the "@system_model" mark
			s_sys.setUseglobals(marks.get(SYSTEM_MODEL).getBoolean(USE_GLOBALS));
			currentRoot = s_sys;
			ctx.package_item().forEach(this::visit);
			return s_sys;
		} else {
			// TODO create a package
			return null;
		}
	}

	@Override
	public NonRootModelElement visitComponent_definition(Component_definitionContext ctx) {
		// TODO consider components defined directly within other components
		final Package_c parent_pkg = (Package_c) currentRoot;

		// find or create component
		final String comp_name = (String) visit(ctx.comp_name);
		final Component_c comp = Component_c.resolveInstance(modelRoot, UUID.randomUUID(), IdAssigner.NULL_UUID,
				IdAssigner.NULL_UUID, comp_name, "", 0, IdAssigner.NULL_UUID, false, "", "",
				parent_pkg.getPath() + "::" + comp_name);
		final PackageableElement_c pe = new PackageableElement_c(modelRoot);
		pe.relateAcrossR8000To(parent_pkg);
		pe.relateAcrossR8001To(comp);
		pe.setVisibility(Visibility_c.Public);
		pe.setType(Elementtypeconstants_c.COMPONENT);
		currentRoot = comp;

		// set component description
		if (ctx.description() != null) {
			comp.setDescrip(ctx.description().getText().lines().map(line -> line.replace("//!", "").strip())
					.collect(Collectors.joining(System.lineSeparator())));
		}

		// process marks
		@SuppressWarnings("unchecked")
		final Map<String, Mark> marks = ctx.marks() != null ? (Map<String, Mark>) visit(ctx.marks())
				: Collections.emptyMap();
		if (marks.containsKey(MULTIPLICITY) && marks.get(MULTIPLICITY).getString().equals("many")) {
			comp.setMult(1);
		}
		if (marks.containsKey(KEY_LETTERS)) {
			comp.setKey_lett(marks.get(KEY_LETTERS).getString());
		}
		if (marks.containsKey(REALIZED)) {
			comp.setIsrealized(true);
			comp.setRealized_class_path(marks.get(REALIZED).getString());

		}

		// load all component items
		// TODO ensure packages load first?
		ctx.component_item().forEach(this::visit);

		return comp;
	}

	@Override
	public NonRootModelElement visitPort_definition(Port_definitionContext ctx) {
		final Component_c comp = (Component_c) currentRoot;

		// find or create port
		final String port_name = (String) visit(ctx.port_name);
		final Port_c port = Port_c.resolveInstance(modelRoot, UUID.randomUUID(), comp.getId(), port_name, 0, false, "",
				comp.getPath() + "::" + port_name);
		port.relateAcrossR4010To(comp);
		final NonRootModelElement oldRoot = currentRoot;
		currentRoot = port;

		// find the formalized interface
		Interface_c iface = null;
		if (ctx.iface_name != null) {
			try {
				iface = (Interface_c) PersistenceManager.getDefaultInstance().selectAndWait(resource, () -> {
					List<Interface_c> ifaces = findVisibleElements(comp, Elementtypeconstants_c.INTERFACE).stream()
							.map(Interface_c::getOneC_IOnR8001)
							.filter(ifc -> ifc.getPath().endsWith((String) visit(ctx.iface_name)))
							.collect(Collectors.toList());
					if (ifaces.isEmpty()) {
						return Optional.empty();
					} else {
						if (ifaces.size() > 1) {
							throw new IllegalArgumentException(
									"The given path corresponds to more than one unique element");
						}
						return Optional.of(ifaces.get(0));
					}
				}).get();
			} catch (InterruptedException | ExecutionException | CancellationException e) {
				throw new CoreImport.XtumlLoadException(
						"Failed to find interface '" + visit(ctx.iface_name) + "' for port definition.", e);
			}
		}

		// process marks
		@SuppressWarnings("unchecked")
		final Map<String, Mark> marks = ctx.marks() != null ? (Map<String, Mark>) visit(ctx.marks())
				: Collections.emptyMap();
		if (marks.containsKey(MULTIPLICITY) && marks.get(MULTIPLICITY).getString().equals("many")) {
			port.setMult(1);
		}
		if (marks.containsKey(KEY_LETTERS)) {
			port.setKey_lett(marks.get(KEY_LETTERS).getString());
		}
		if (marks.containsKey(HIDE_GRAPHIC)) {
			port.setDonotshowportoncanvas(true);
		}

		// create the interface reference
		final String informalName = marks.containsKey(INFORMAL_NAME) ? marks.get(INFORMAL_NAME).getString()
				: "Unnamed Interface";
		final InterfaceReference_c ir = InterfaceReference_c.resolveInstance(modelRoot, UUID.randomUUID(),
				IdAssigner.NULL_UUID, IdAssigner.NULL_UUID, port.getId(),
				port.getPath() + "::" + (iface != null ? iface.getName() : informalName));
		ir.relateAcrossR4016To(port);
		if (iface != null) {
			ir.relateAcrossR4012To(iface);
		}
		Provision_c c_p = null;
		Requirement_c c_r = null;
		if (ctx.direction.getText().equals("provided")) {
			c_p = Provision_c.resolveInstance(modelRoot, UUID.randomUUID(), "", informalName, "", "", ir.getPath());
			c_p.relateAcrossR4009To(ir);
		} else {
			c_r = Requirement_c.resolveInstance(modelRoot, UUID.randomUUID(), "", "", informalName, "", ir.getPath());
			c_r.relateAcrossR4009To(ir);
		}

		// set port description
		if (ctx.description() != null) {
			if (c_p != null) {
				c_p.setDescrip(ctx.description().getText().lines().map(line -> line.replace("//!", "").strip())
						.collect(Collectors.joining(System.lineSeparator())));
			} else {
				c_r.setDescrip(ctx.description().getText().lines().map(line -> line.replace("//!", "").strip())
						.collect(Collectors.joining(System.lineSeparator())));
			}
		}

		// process all message definitions
		ctx.message_definition().forEach(this::visit);

		currentRoot = oldRoot;
		return port;
	}

	@Override
	public NonRootModelElement visitMessage_definition(Message_definitionContext ctx) {

		// TODO Consider validating that the signature matches. At the moment, this
		// method simply matches by name. Note that this also assumes no overloading.

		final Port_c port = (Port_c) currentRoot;

		final Provision_c c_p = Provision_c.getOneC_POnR4009(InterfaceReference_c.getOneC_IROnR4016(port));
		final Requirement_c c_r = Requirement_c.getOneC_ROnR4009(InterfaceReference_c.getOneC_IROnR4016(port));

		final ExecutableProperty_c c_ep = ExecutableProperty_c.getOneC_EPOnR4003(
				Interface_c.getOneC_IOnR4012(InterfaceReference_c.getOneC_IROnR4016(port)),
				selected -> ((NonRootModelElement) selected).getName().equals(visit(ctx.msg_name)));

		@SuppressWarnings("unchecked")
		final Map<String, Mark> marks = ctx.marks() != null ? (Map<String, Mark>) visit(ctx.marks())
				: Collections.emptyMap();

		if (c_p != null) {
			final ProvidedExecutableProperty_c spr_pep = new ProvidedExecutableProperty_c(modelRoot);
			spr_pep.relateAcrossR4501To(c_ep);
			spr_pep.relateAcrossR4501To(c_p);
			if (InterfaceOperation_c.getOneC_IOOnR4004(c_ep) != null) {
				final ProvidedOperation_c spr_po = new ProvidedOperation_c(modelRoot);
				spr_po.relateAcrossR4503To(spr_pep);
				spr_po.setDialect(Actiondialect_c.oal); // TODO always set to OAL
				if (marks.containsKey(MESSAGE_NUM)) {
					spr_po.setNumb(marks.get(MESSAGE_NUM).getInteger());
				}
				if (marks.containsKey(NOPARSE)) {
					spr_po.setSuc_pars(Parsestatus_c.doNotParse);
				} else {
					spr_po.setSuc_pars(Parsestatus_c.parseInitial);
				}
				spr_po.setAction_semantics_internal((String) visit(ctx.action_body()));
				return spr_po;
			} else {
				final ProvidedSignal_c spr_ps = ProvidedSignal_c.resolveInstance(modelRoot, UUID.randomUUID(), "", "",
						"", Parsestatus_c.OOA_UNINITIALIZED_ENUM, Actiondialect_c.OOA_UNINITIALIZED_ENUM, 0,
						c_p.getPath() + "::" + c_ep.getName());
				spr_ps.relateAcrossR4503To(spr_pep);
				spr_ps.setDialect(Actiondialect_c.oal); // TODO always set to OAL
				if (marks.containsKey(MESSAGE_NUM)) {
					spr_ps.setNumb(marks.get(MESSAGE_NUM).getInteger());
				}
				if (marks.containsKey(NOPARSE)) {
					spr_ps.setSuc_pars(Parsestatus_c.doNotParse);
				} else {
					spr_ps.setSuc_pars(Parsestatus_c.parseInitial);
				}
				spr_ps.setAction_semantics_internal((String) visit(ctx.action_body()));
				return spr_ps;
			}
		} else {
			final RequiredExecutableProperty_c spr_rep = new RequiredExecutableProperty_c(modelRoot);
			spr_rep.relateAcrossR4500To(c_ep);
			spr_rep.relateAcrossR4500To(c_r);
			if (InterfaceOperation_c.getOneC_IOOnR4004(c_ep) != null) {
				final RequiredOperation_c spr_ro = new RequiredOperation_c(modelRoot);
				spr_ro.relateAcrossR4502To(spr_rep);
				spr_ro.setDialect(Actiondialect_c.oal); // TODO always set to OAL
				if (marks.containsKey(MESSAGE_NUM)) {
					spr_ro.setNumb(marks.get(MESSAGE_NUM).getInteger());
				}
				if (marks.containsKey(NOPARSE)) {
					spr_ro.setSuc_pars(Parsestatus_c.doNotParse);
				} else {
					spr_ro.setSuc_pars(Parsestatus_c.parseInitial);
				}
				spr_ro.setAction_semantics_internal((String) visit(ctx.action_body()));
				return spr_ro;
			} else {
				final RequiredSignal_c spr_rs = new RequiredSignal_c(modelRoot);
				spr_rs.relateAcrossR4502To(spr_rep);
				spr_rs.setDialect(Actiondialect_c.oal); // TODO always set to OAL
				if (marks.containsKey(MESSAGE_NUM)) {
					spr_rs.setNumb(marks.get(MESSAGE_NUM).getInteger());
				}
				if (marks.containsKey(NOPARSE)) {
					spr_rs.setSuc_pars(Parsestatus_c.doNotParse);
				} else {
					spr_rs.setSuc_pars(Parsestatus_c.parseInitial);
				}
				spr_rs.setAction_semantics_internal((String) visit(ctx.action_body()));
				return spr_rs;
			}
		}
	}

	@Override
	public NonRootModelElement visitInterface_definition(Interface_definitionContext ctx) {
		final Package_c parent_pkg = (Package_c) currentRoot;

		// find or create interface
		final String iface_name = (String) visit(ctx.iface_name);
		final Interface_c iface = Interface_c.resolveInstance(modelRoot, UUID.randomUUID(), parent_pkg.getPackage_id(),
				iface_name, "", parent_pkg.getPath() + "::" + iface_name);
		final PackageableElement_c pe = new PackageableElement_c(modelRoot);
		pe.relateAcrossR8000To(parent_pkg);
		pe.relateAcrossR8001To(iface);
		pe.setVisibility(Visibility_c.Public);
		pe.setType(Elementtypeconstants_c.INTERFACE);
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
		final String name = (String) visit(ctx.msg_name);
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
		@SuppressWarnings("unchecked")
		final Map<String, Mark> marks = ctx.marks() != null ? (Map<String, Mark>) visit(ctx.marks())
				: Collections.emptyMap();
		if (marks.containsKey(MESSAGE_NUM)) {
			c_ep.setNumb(marks.get(MESSAGE_NUM).getInteger());
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
		c_pp.setName((String) visit(ctx.param_name));
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
						.filter(dt -> dt.getPath().endsWith((String) visit(ctx.scoped_name())))
						.collect(Collectors.toList());
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
					"Failed to find type '" + visit(ctx.scoped_name()) + "' for named type reference.", e);
		}
	}

	@Override
	public NonRootModelElement visitArray_type_reference(Array_type_referenceContext ctx) {
		return (NonRootModelElement) visit(ctx.type_reference());
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
								.getPath() + "::" + obj.getKey_lett()).endsWith((String) visit(ctx.scoped_name())))
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
					"Failed to find class '" + visit(ctx.scoped_name()) + "' for instance type reference.", e);
		}
	}

	@Override
	public NonRootModelElement visitInst_set_type_reference(Inst_set_type_referenceContext ctx) {
		return DataType_c.getOneS_DTOnR17(InstanceReferenceDataType_c.getOneS_IRDTOnR123(
				ModelClass_c.getOneO_OBJOnR123(
						InstanceReferenceDataType_c.getOneS_IRDTOnR17((DataType_c) visit(ctx.inst_type_reference()))),
				selected -> ((InstanceReferenceDataType_c) selected).getIsset()));
	}

	@Override
	public Map<String, Mark> visitMarks(MarksContext ctx) {
		final Map<String, Mark> marks = new HashMap<>();
		ctx.mark().forEach(m -> marks.put(m.MarkName().getText().substring(1), (Mark) visit(m)));
		return Collections.unmodifiableMap(marks);
	}

	@Override
	public Mark visitMark(MarkContext ctx) {
		final Mark mark = new Mark();
		ListIterator<Mark_argumentContext> args = ctx.mark_arguments().mark_argument().listIterator();
		while (args.hasNext()) {
			final int i = args.nextIndex();
			final Mark_argumentContext arg = args.next();
			final String name = arg.arg_name != null ? arg.arg_name.getText() : Integer.toString(i);
			mark.put(name, visit(arg));
		}
		return mark;
	}

	@Override
	public Object visitConst_expression(Const_expressionContext ctx) {
		if (ctx.StringLiteral() != null) {
			return ctx.getText().substring(1, ctx.getText().length() - 1);
		} else if (ctx.IntegerLiteral() != null) {
			return Integer.parseInt(ctx.getText());
		} else if (ctx.BooleanLiteral() != null) {
			return Boolean.parseBoolean(ctx.getText());
		} else {
			return super.visitConst_expression(ctx);
		}
	}

	@Override
	public String visitAction_body(Action_bodyContext ctx) {
		final String text = ctx.getText();
		if (ctx.UnparsedActions() != null) {
			final String tab = text.lines().filter(l -> l.contains("@endnoparse"))
					.map(l -> l.substring(0, l.length() - "@endnoparse".length())).findAny().orElseThrow();
			return text.substring("@noparse".length(), text.length() - "@endnoparse".length()).strip().lines()
					.map(l -> l.replaceFirst(tab, "")).collect(Collectors.joining("\n"));
		} else {
			return ctx.getText();
		}

	}

	@Override
	public String visitName(NameContext ctx) {
		if (ctx.getText().startsWith("'")) {
			return ctx.getText().substring(1, ctx.getText().length() - 1);
		} else {
			return ctx.getText();
		}
	}

	@Override
	public String visitScoped_name(Scoped_nameContext ctx) {
		return ctx.name().stream().map(this::visitName).collect(Collectors.joining("::"));
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

	private List<PackageableElement_c> findVisibleElements(final Package_c pkg, final int elementType) {
		pkg.Clearscope();
		pkg.Collectvisibleelementsforname(true, Gd_c.Null_unique_id(), false, "", pkg.getPackage_id(), elementType);
		final SearchResultSet_c results = SearchResultSet_c.getOnePE_SRSOnR8005(pkg,
				selected -> "".equals(((SearchResultSet_c) selected).getName())
						&& ((SearchResultSet_c) selected).getType() == elementType);
		return List.of(PackageableElement_c.getManyPE_PEsOnR8002(ElementVisibility_c.getManyPE_VISsOnR8006(results)));
	}

	private List<PackageableElement_c> findVisibleElements(final Component_c comp, final int elementType) {
		comp.Clearscope();
		comp.Collectvisibleelementsforname(true, Gd_c.Null_unique_id(), "", comp.getId(), elementType);
		final ComponentResultSet_c results = ComponentResultSet_c.getOnePE_CRSOnR8007(comp,
				selected -> "".equals(((ComponentResultSet_c) selected).getName())
						&& ((ComponentResultSet_c) selected).getType() == elementType);
		return List.of(PackageableElement_c.getManyPE_PEsOnR8004(ComponentVisibility_c.getManyPE_CVSsOnR8008(results)));
	}

	private List<PackageableElement_c> findVisibleElements(final PackageableElement_c referencePe,
			final int elementType) {
		final Package_c pkg = Package_c.getOneEP_PKGOnR8000(referencePe);
		final Component_c comp = Component_c.getOneC_COnR8003(referencePe);
		if (pkg != null) {
			return findVisibleElements(pkg, elementType);
		} else if (comp != null) {
			return findVisibleElements(comp, elementType);
		} else {
			return Collections.emptyList();
		}
	}

	private final class Mark extends LinkedHashMap<String, Object> implements Map<String, Object> {

		private static final long serialVersionUID = 1L;

		@SuppressWarnings("unused")
		public String getString(String key) {
			return (String) get(key);
		}

		public boolean getBoolean(String key) {
			return (boolean) get(key);
		}

		@SuppressWarnings("unused")
		public int getInteger(String key) {
			return (int) get(key);
		}

		public String getString() {
			return (String) values().iterator().next();
		}

		@SuppressWarnings("unused")
		public boolean getBoolean() {
			return (boolean) values().iterator().next();
		}

		public int getInteger() {
			return (int) values().iterator().next();
		}

	}

}
