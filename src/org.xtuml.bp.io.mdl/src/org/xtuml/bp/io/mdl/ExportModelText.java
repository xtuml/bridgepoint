package org.xtuml.bp.io.mdl;

import java.io.ByteArrayOutputStream; import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Comparator;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.core.runtime.IProgressMonitor;
import org.xtuml.bp.core.DataType_c;
import org.xtuml.bp.core.ExecutableProperty_c;
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
import org.xtuml.bp.core.SystemModel_c;
import org.xtuml.bp.core.common.NonRootModelElement;
import org.xtuml.bp.core.sorter.PropertyParameter_cSorter;

public class ExportModelText extends ExportModelComponent {

	private static final String TAB_CHARS = "  ";

	private int tabDepth = 0;
	private Stack<StringBuilder> buffers = new Stack<>();

	public ExportModelText(Ooaofooa modelRoot, ByteArrayOutputStream baos, NonRootModelElement element) {
		super(modelRoot, baos, element);
	}

	public ExportModelText(Ooaofooa modelRoot, String outfileName, boolean export_graphics,
			NonRootModelElement instance) throws FileNotFoundException {
		super(modelRoot, outfileName, false, instance);
	}

	public ExportModelText(Ooaofooa modelRoot, String outfileName, boolean export_graphics)
			throws FileNotFoundException {
		super(modelRoot, outfileName, false);
	}

	public ExportModelText(String outfileName, NonRootModelElement element) throws FileNotFoundException {
		super(outfileName, element);
	}

	@Override
	public String get_file_header(NonRootModelElement element) {
		// TODO remove this conditional once everything is implemented
		if (element instanceof Interface_c || element instanceof SystemModel_c) {
			return get_file_header("//",
					element.getClass().getSimpleName().substring(0, element.getClass().getSimpleName().length() - 2),
					"7.1.6", org.xtuml.bp.core.CorePlugin.getPersistenceVersion());
		} else {
			return super.get_file_header(element);
		}
	}
	
	@Override
	protected void export_SystemModel_c(SystemModel_c inst, IProgressMonitor pm, boolean writeAsProxies,
			boolean isPersistable) throws IOException {
		if (inst == null) {
			return;
		}
		if (!writeAsProxies && !forceWriteAsProxy) {
			append("%s@system_model(use_globals=%s);\n", getTab(), inst.getUseglobals());
			append("%spackage %s is\n", getTab(), inst.getName()); // TODO sanitize name
			tabDepth++;
			
			final Package_c[] ep_pkgs = Package_c.getManyEP_PKGsOnR1401(inst);
			if (ep_pkgs.length > 0) {
				append("%s\n", getTab());
			}
			Stream.of(ep_pkgs).sorted(Comparator.comparing(Package_c::getName)).forEach(ep_pkg -> {
			  append("%spackage %s;\n", getTab(), ep_pkg.getName());
			});
			if (ep_pkgs.length > 0) {
				append("%s\n", getTab());
			}
			
			tabDepth--;
			append("%send package;\n\n", getTab());

		} else {
			super.export_SystemModel_c(inst, pm, writeAsProxies, isPersistable);
		}

	}

	@Override
	protected void export_Interface_c(Interface_c inst, IProgressMonitor pm, boolean writeAsProxies,
			boolean isPersistable) throws IOException {
		if (inst == null) {
			return;
		}
		if (!writeAsProxies && !forceWriteAsProxy) {
			// get parent package
			final Package_c parent_pkg = Package_c.getOneEP_PKGOnR8000(PackageableElement_c.getOnePE_PEOnR8001(inst));
			append("%swithin %s is\n\n", getTab(), parent_pkg.getPath());
			tabDepth++;
			inst.getDescrip().strip().lines().forEach(line -> {
				append("%s//! %s\n", getTab(), line);
			});
			append("%sinterface %s is\n", getTab(), inst.getName()); // TODO sanitize name
			tabDepth++;

			// interface operations
			final ExecutableProperty_c[] c_eps_1 = ExecutableProperty_c.getManyC_EPsOnR4004(
					InterfaceOperation_c.getManyC_IOsOnR4004(ExecutableProperty_c.getManyC_EPsOnR4003(inst)));
			if (c_eps_1.length > 0) {
				append("%s\n", getTab());
			}
			for (ExecutableProperty_c c_ep : c_eps_1) {
				export_ExecutableProperty_c(c_ep, pm, writeAsProxies, isPersistable);
			}

			// interface signals
			final ExecutableProperty_c[] c_eps_2 = ExecutableProperty_c.getManyC_EPsOnR4004(
					InterfaceSignal_c.getManyC_ASsOnR4004(ExecutableProperty_c.getManyC_EPsOnR4003(inst)));
			if (c_eps_2.length > 0) {
				append("%s\n", getTab());
			}
			for (ExecutableProperty_c c_ep : c_eps_2) {
				export_ExecutableProperty_c(c_ep, pm, writeAsProxies, isPersistable);
			}

			if (c_eps_1.length + c_eps_2.length > 0) {
				append("%s\n", getTab());
			}

			tabDepth--;
			append("%send interface;\n\n", getTab());
			tabDepth--;
			append("%send;\n", getTab());
		} else {
			super.export_Interface_c(inst, pm, writeAsProxies, isPersistable);
		}
	}

	@Override
	protected void export_ExecutableProperty_c(ExecutableProperty_c inst, IProgressMonitor pm, boolean writeAsProxies,
			boolean isPersistable) throws IOException {
		if (inst == null) {
			return;
		}
		if (!writeAsProxies && !forceWriteAsProxy) {

			// build the parameter list
			final PropertyParameter_c[] c_pps = PropertyParameter_c.getManyC_PPsOnR4006(inst);
			new PropertyParameter_cSorter().sort(c_pps);
			final String parameterList = Stream.of(c_pps).map(c_pp -> {
				buffers.push(new StringBuilder());
				try {
					export_PropertyParameter_c(c_pp, pm, writeAsProxies, isPersistable);
				} catch (IOException e) {
					throw new UncheckedIOException(e);
				}
				return buffers.pop().toString();
			}).collect(Collectors.joining(", "));

			// get the return type if applicable
			final DataType_c returnType = DataType_c.getOneS_DTOnR4008(InterfaceOperation_c.getOneC_IOOnR4004(inst));
			final String returnTypeRef = returnType != null ? " return " + getTypeReference(returnType,
					InterfaceOperation_c.getOneC_IOOnR4004(inst).getReturn_dimensions(), Package_c.getOneEP_PKGOnR8000(
							PackageableElement_c.getOnePE_PEOnR8001(Interface_c.getOneC_IOnR4003(inst))))
					: "";

			// get the message direction
			final String direction = inst.getDirection() == Ifdirectiontype_c.ClientServer ? "to provider"
					: "from provider";

			// append the description
			inst.getDescrip().strip().lines().forEach(line -> {
				append("%s//! %s\n", getTab(), line);
			});

			// TODO append parameter descriptions

			// append the message
			if (inst.getNumb() > 0) {
				append("%s@message_num(%d);\n", getTab(), inst.getNumb());
			}
			append("%smessage %s(%s)%s %s;\n", getTab(), inst.getName(), parameterList, returnTypeRef, direction);

		} else {
			super.export_ExecutableProperty_c(inst, pm, writeAsProxies, isPersistable);
		}

	}

	@Override
	protected void export_PropertyParameter_c(PropertyParameter_c inst, IProgressMonitor pm, boolean writeAsProxies,
			boolean isPersistable) throws IOException {
		if (inst == null) {
			return;
		}
		if (!writeAsProxies && !forceWriteAsProxy) {
			append("%s: %s %s", inst.getName(), inst.getBy_ref() == 0 ? "in" : "out",
					getTypeReference(DataType_c.getOneS_DTOnR4007(inst), inst.getDimensions(),
							Package_c.getOneEP_PKGOnR8000(PackageableElement_c.getOnePE_PEOnR8001(
									Interface_c.getOneC_IOnR4003(ExecutableProperty_c.getOneC_EPOnR4006(inst))))));
		} else {
			write_PropertyParameter_c_proxy_sql(inst);
		}
	}

	private String getTypeReference(final DataType_c type, final String dims, final Package_c referencePoint) {
		String type_ref = "";
		Matcher m = Pattern.compile("\\[([^\\]]*)\\]").matcher(dims);
		while (m.find()) {
			type_ref += String.format("sequence%s of ", !"".equals(m.group(1)) ? " (" + m.group(1) + ")" : "");
		}
		final InstanceReferenceDataType_c irdt = InstanceReferenceDataType_c.getOneS_IRDTOnR17(type);
		if (irdt != null) {
			final ModelClass_c obj = ModelClass_c.getOneO_OBJOnR123(irdt);
			if (irdt.getIsset()) {
				type_ref += "set of ";
			}
			type_ref += "instance of " + obj.getKey_lett(); // TODO get scoped name
		} else {
			type_ref += type.getName(); // TODO get scoped name
		}
		return type_ref;
	}

	private void append(String format, Object... args) {
		if (!buffers.empty()) {
			buffers.peek().append(String.format(format, args));
		} else {
			m_fh.printf(format, args);
			m_fh.flush();
		}
	}

	private String getTab() {
		String tab = "";
		for (int i = 0; i < tabDepth; i++) {
			tab += TAB_CHARS;
		}
		return tab;
	}

}
