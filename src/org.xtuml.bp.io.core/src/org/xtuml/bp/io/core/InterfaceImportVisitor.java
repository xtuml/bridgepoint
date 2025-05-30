package org.xtuml.bp.io.core;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.xtuml.bp.core.DataType_c;
import org.xtuml.bp.core.Elementtypeconstants_c;
import org.xtuml.bp.core.ExecutableProperty_c;
import org.xtuml.bp.core.Ifdirectiontype_c;
import org.xtuml.bp.core.InterfaceOperation_c;
import org.xtuml.bp.core.InterfaceSignal_c;
import org.xtuml.bp.core.Interface_c;
import org.xtuml.bp.core.Ooaofooa;
import org.xtuml.bp.core.Package_c;
import org.xtuml.bp.core.PackageableElement_c;
import org.xtuml.bp.core.PropertyParameter_c;
import org.xtuml.bp.core.Visibility_c;
import org.xtuml.bp.core.util.DimensionsUtil;
import org.xtuml.bp.io.core.XtumlParser.Interface_definitionContext;
import org.xtuml.bp.io.core.XtumlParser.Message_definitionContext;
import org.xtuml.bp.io.core.XtumlParser.ParameterContext;
import org.xtuml.bp.io.core.XtumlParser.Parameter_listContext;

public class InterfaceImportVisitor extends XtumlImportVisitor {

	public InterfaceImportVisitor(Ooaofooa modelRoot) {
		super(modelRoot);
	}

	@Override
	public Interface_c visitInterface_definition(Interface_definitionContext ctx) {
		final Package_c parent_pkg = (Package_c) currentRoot;

		// find or create interface
		final String iface_name = visitName(ctx.iface_name);
		final Interface_c iface = findOrCreate(Interface_c.class, parent_pkg.getPath() + "::" + iface_name, true);
		iface.setName(iface_name);
		final PackageableElement_c pe = new PackageableElement_c(modelRoot);
		pe.relateAcrossR8001To(iface);
		pe.setVisibility(Visibility_c.Public);
		pe.setType(Elementtypeconstants_c.INTERFACE);
		currentRoot = iface;

		// set interface description
		if (ctx.description() != null) {
			iface.setDescrip(ctx.description().getText().lines().map(line -> line.replaceFirst("//! ?", ""))
					.collect(Collectors.joining(System.lineSeparator())));
		}

		// link to the parent package
		pe.relateAcrossR8000To(parent_pkg);

		// Process the executable properties
		final List<ExecutableProperty_c> c_eps = ctx.message_definition().stream().map(this::visitMessage_definition)
				.collect(Collectors.toList());

		// link individual operations and signals together
		final InterfaceOperation_c[] c_ios = InterfaceOperation_c
				.getManyC_IOsOnR4004(c_eps.toArray(new ExecutableProperty_c[0]));
		for (int i = 0; i + 1 < c_ios.length; i++) {
			c_ios[i].relateAcrossR4019ToPrecedes(c_ios[i + 1]);
		}
		final InterfaceSignal_c[] c_ass = InterfaceSignal_c
				.getManyC_ASsOnR4004(c_eps.toArray(new ExecutableProperty_c[0]));
		for (int i = 0; i + 1 < c_ass.length; i++) {
			c_ass[i].relateAcrossR4020ToPrecedes(c_ass[i + 1]);
		}

		iface.setDeclarationOnly(false);
		return iface;
	}

	@Override
	public ExecutableProperty_c visitMessage_definition(Message_definitionContext ctx) {
		final Interface_c iface = (Interface_c) currentRoot;

		// parse message info
		final String name = visitScoped_name(ctx.msg_name);
		final int direction = "to".equals(ctx.direction.getText()) ? Ifdirectiontype_c.ClientServer
				: Ifdirectiontype_c.ServerClient;

		// find or create executable property
		final ExecutableProperty_c c_ep = findOrCreate(ExecutableProperty_c.class, iface.getPath() + "::" + name);
		c_ep.setName(name);
		c_ep.setDirection(direction);
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
				.map(line -> line.replaceFirst("//! ?", "")).collect(Collectors.joining(System.lineSeparator())) : "";

		// process marks
		// TODO eventually, this should tie in with the marking editor, but for
		// now it is just used to get the message number
		final Map<String, Mark> marks = ctx.marks() != null ? visitMarks(ctx.marks()) : Collections.emptyMap();
		if (marks.containsKey(MESSAGE_NUM)) {
			c_ep.setNumb(marks.get(MESSAGE_NUM).getInteger());
		} else {
			c_ep.setNumb(0);
		}

		// set subtype specific info
		final InterfaceOperation_c c_io = InterfaceOperation_c.getOneC_IOOnR4004(c_ep);
		final InterfaceSignal_c c_as = InterfaceSignal_c.getOneC_ASOnR4004(c_ep);
		if (c_io != null) {
			c_io.setName(name);
			c_io.setDescrip(messageDescription);
			c_io.setDirection(direction);

			// set return type
			if (ctx.type_reference() != null) {
				c_io.relateAcrossR4008To((DataType_c) visit(ctx.type_reference()));

				// set return dimensions
				String dim_string = getDimString(ctx.type_reference().array_type_reference());
				c_io.setReturn_dimensions(dim_string);
				List<Integer> dims = DimensionsUtil.getDimensionsData(dim_string, c_io);
				for (int i = 0; i < dims.size(); i++) {
					c_io.Resizereturn_dimensions(i, dims.get(i), dims.size());
				}
			} else {
				c_io.relateAcrossR4008To(voidType);
			}
		} else {
			c_as.setName(name);
			c_as.setDescrip(messageDescription);
			c_as.setDirection(direction);
		}

		// process parameters
		if (ctx.parameter_list() != null) {
			currentRoot = c_ep;
			visit(ctx.parameter_list());
			currentRoot = iface;
		}

		// relate to the interface
		c_ep.relateAcrossR4003To(iface);

		return c_ep;
	}

	@Override
	public PropertyParameter_c visitParameter_list(Parameter_listContext ctx) {
		// link parameters to each other in order
		PropertyParameter_c prevPp = null;
		for (ParameterContext paramCtx : ctx.parameter()) {
			final PropertyParameter_c c_pp = visitParameter(paramCtx);
			if (prevPp != null) {
				prevPp.relateAcrossR4021ToPrecedes(c_pp);
			}
			prevPp = c_pp;
		}
		return prevPp;
	}

	@Override
	public PropertyParameter_c visitParameter(ParameterContext ctx) {
		final ExecutableProperty_c c_ep = (ExecutableProperty_c) currentRoot;

		// create a new parameter
		final PropertyParameter_c c_pp = new PropertyParameter_c(modelRoot);
		c_pp.setName(visitName(ctx.param_name));

		// set by value/ref
		c_pp.setBy_ref("in".equals(ctx.by_ref.getText()) ? 0 : 1);

		// link the data type
		c_pp.relateAcrossR4007To((DataType_c) visit(ctx.type_reference()));

		// set the array dimensions
		final String dimString = getDimString(ctx.type_reference().array_type_reference());
		c_pp.setDimensions(dimString);
		List<Integer> dims = DimensionsUtil.getDimensionsData(dimString, c_pp);
		for (int i = 0; i < dims.size(); i++) {
			c_pp.Resizedimensions(i, dims.get(i), dims.size());
		}

		// link to message
		c_pp.relateAcrossR4006To(c_ep);

		return c_pp;
	}

}
