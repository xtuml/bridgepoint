package org.xtuml.bp.io.core;

import java.util.List;
import java.util.stream.Collectors;

import org.xtuml.bp.core.DataType_c;
import org.xtuml.bp.core.ExecutableProperty_c;
import org.xtuml.bp.core.Ifdirectiontype_c;
import org.xtuml.bp.core.InterfaceOperation_c;
import org.xtuml.bp.core.InterfaceSignal_c;
import org.xtuml.bp.core.Interface_c;
import org.xtuml.bp.core.Ooaofooa;
import org.xtuml.bp.core.Package_c;
import org.xtuml.bp.core.PackageableElement_c;
import org.xtuml.bp.core.PropertyParameter_c;
import org.xtuml.bp.io.core.XtumlParser.Message_declarationContext;
import org.xtuml.bp.io.core.XtumlParser.NameContext;
import org.xtuml.bp.io.core.XtumlParser.ParameterContext;
import org.xtuml.bp.io.core.XtumlParser.Parameter_listContext;
import org.xtuml.bp.io.core.XtumlParser.Scoped_nameContext;
import org.xtuml.bp.io.core.XtumlParser.TargetContext;

public class XtumlImportVisitor extends XtumlBaseVisitor<Object> {

	private final Ooaofooa modelRoot;

	public XtumlImportVisitor(final Ooaofooa modelRoot) {
		this.modelRoot = modelRoot;
	}

	@Override
	public Object visitTarget(TargetContext ctx) {
		// find or create package TODO create if does not exist
		Package_c parent_pkg = Package_c.PackageInstance(modelRoot,
				selected -> ((Package_c) selected).getPath().equals(visit(ctx.pkg)));
		if (parent_pkg == null) {
			System.err.println("Big issues");
			return null;
		}

		// create interface
		Interface_c iface = new Interface_c(modelRoot);
		iface.setName((String) visit(ctx.iface_name));
		PackageableElement_c pe = new PackageableElement_c(modelRoot);
		iface.relateAcrossR8001To(pe);
		pe.relateAcrossR8000To(parent_pkg);

		// link executable properties to interface
		List<ExecutableProperty_c> c_eps = ctx.message_declaration().stream().map(m -> (ExecutableProperty_c) visit(m))
				.collect(Collectors.toList());
		c_eps.forEach(c_ep -> c_ep.relateAcrossR4003To(iface));

		// link individual operations and signals together
		List<InterfaceOperation_c> c_ios = c_eps.stream().map(c_ep -> InterfaceOperation_c.getOneC_IOOnR4004(c_ep))
				.filter(c_io -> c_io != null)
				.collect(Collectors.toList());
		for (int i = 0; i + 1 < c_ios.size(); i++) {
			c_ios.get(i).relateAcrossR4019ToPrecedes(c_ios.get(i + 1));
		}
		List<InterfaceSignal_c> c_ass = c_eps.stream().map(c_ep -> InterfaceSignal_c.getOneC_ASOnR4004(c_ep))
				.filter(c_as -> c_as != null)
				.collect(Collectors.toList());
		for (int i = 0; i + 1 < c_ass.size(); i++) {
			c_ass.get(i).relateAcrossR4020ToPrecedes(c_ass.get(i + 1));
		}

		return iface;
	}

	@Override
	public Object visitMessage_declaration(Message_declarationContext ctx) {
		// create executable property
		final ExecutableProperty_c c_ep = new ExecutableProperty_c(modelRoot);
		final String name = (String) visit(ctx.msg_name);

		// link parameters to the message
		if (ctx.parameter_list() != null) {
			@SuppressWarnings("unchecked")
			final List<PropertyParameter_c> c_pps = (List<PropertyParameter_c>) visit(ctx.parameter_list());
			c_pps.forEach(c_pp -> c_ep.relateAcrossR4006To(c_pp));
		}

		// get the message direction
		final int direction = ctx.to != null ? Ifdirectiontype_c.ClientServer : Ifdirectiontype_c.ServerClient;
		c_ep.setDirection(direction);

		// link the return type
		if (ctx.type_name != null) {
			final InterfaceOperation_c c_io = new InterfaceOperation_c(modelRoot);
			c_io.relateAcrossR4004To(c_ep);
			c_io.setName(name);
			c_io.setDirection(direction);
			final DataType_c dt = DataType_c.DataTypeInstance(Ooaofooa.getDefaultInstance(),
					selected -> ((DataType_c) selected).getName().equals(visit(ctx.type_name)));
			if (dt != null) {
				c_io.relateAcrossR4008To(dt);
			} else {
				// TODO void?
			}
		} else {
			final InterfaceSignal_c c_as = new InterfaceSignal_c(modelRoot);
			c_as.relateAcrossR4004To(c_ep);
			c_as.setName(name);
			c_as.setDirection(direction);
		}

		return c_ep;
	}

	@Override
	public Object visitParameter_list(Parameter_listContext ctx) {
		// link parameters to each other in order
		List<PropertyParameter_c> c_pps = ctx.parameter().stream().map(p -> (PropertyParameter_c) visit(p))
				.collect(Collectors.toList());
		for (int i = 0; i + 1 < c_pps.size(); i++) {
			c_pps.get(i).relateAcrossR4021ToPrecedes(c_pps.get(i + 1));
		}
		return c_pps;
	}

	@Override
	public Object visitParameter(ParameterContext ctx) {
		// create a new parameter
		PropertyParameter_c c_pp = new PropertyParameter_c(modelRoot);
		c_pp.setName((String) visit(ctx.param_name));
		// link the data type
		DataType_c dt = DataType_c.DataTypeInstance(Ooaofooa.getDefaultInstance(),
				selected -> ((DataType_c) selected).getName().equals(visit(ctx.type_name)));
		c_pp.relateAcrossR4007To(dt);
		return c_pp;
	}

	@Override
	public Object visitScoped_name(Scoped_nameContext ctx) {
		return ctx.getText();
	}

	@Override
	public Object visitName(NameContext ctx) {
		return ctx.getText();
	}

}
