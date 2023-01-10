package org.xtuml.bp.io.mdl;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Stack;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.core.runtime.IProgressMonitor;
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
import org.xtuml.bp.core.common.NonRootModelElement;

public class ExportModelText extends ExportModelComponent {

	private static final String TAB_CHARS = "  ";

	private int tabDepth = 0;
	private Stack<StringBuilder> buffers = new Stack<>();

	public ExportModelText(Ooaofooa modelRoot, ByteArrayOutputStream baos, NonRootModelElement element) {
		super(modelRoot, baos, element);
	}

	public ExportModelText(Ooaofooa modelRoot, String outfileName, boolean export_graphics,
			NonRootModelElement instance) throws FileNotFoundException {
		super(modelRoot, outfileName, export_graphics, instance);
	}

	public ExportModelText(Ooaofooa modelRoot, String outfileName, boolean export_graphics)
			throws FileNotFoundException {
		super(modelRoot, outfileName, export_graphics);
	}

	public ExportModelText(String outfileName, NonRootModelElement element) throws FileNotFoundException {
		super(outfileName, element);
	}

	@Override
	public String get_file_header(NonRootModelElement element) {
		if (element instanceof Interface_c) {
			return get_file_header("//",
					element.getClass().getSimpleName().substring(0, element.getClass().getSimpleName().length() - 2),
					"7.1.6", org.xtuml.bp.core.CorePlugin.getPersistenceVersion());
		} else {
			return super.get_file_header(element);
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
			Package_c parent_pkg = Package_c.getOneEP_PKGOnR8000(PackageableElement_c.getOnePE_PEOnR8001(inst));
			append("%swithin %s is\n\n", getTab(), parent_pkg.getPath());
			tabDepth++;
			append("%sinterface %s is\n\n", getTab(), inst.getName()); // TODO sanitize name
			tabDepth++;

			// interface operations
			ExecutableProperty_c[] ExecutableProperty_c4003_1 = ExecutableProperty_c.getManyC_EPsOnR4004(
					InterfaceOperation_c.getManyC_IOsOnR4004(ExecutableProperty_c.getManyC_EPsOnR4003(inst)));
			for (int ExecutableProperty_c_index = 0; ExecutableProperty_c_index < ExecutableProperty_c4003_1.length; ++ExecutableProperty_c_index) {
				export_ExecutableProperty_c(ExecutableProperty_c4003_1[ExecutableProperty_c_index], pm, writeAsProxies,
						isPersistable);
			}
			append("%s\n", getTab());

			// interface signals
			ExecutableProperty_c[] ExecutableProperty_c4003_2 = ExecutableProperty_c.getManyC_EPsOnR4004(
					InterfaceSignal_c.getManyC_ASsOnR4004(ExecutableProperty_c.getManyC_EPsOnR4003(inst)));
			for (int ExecutableProperty_c_index = 0; ExecutableProperty_c_index < ExecutableProperty_c4003_2.length; ++ExecutableProperty_c_index) {
				export_ExecutableProperty_c(ExecutableProperty_c4003_2[ExecutableProperty_c_index], pm, writeAsProxies,
						isPersistable);
			}
			append("%s\n", getTab());

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
			PropertyParameter_c[] PropertyParameter_c4006 = PropertyParameter_c.getManyC_PPsOnR4006(inst);
			final String parameterList = Stream.of(PropertyParameter_c4006).map(pp -> {
				buffers.push(new StringBuilder());
				try {
					export_PropertyParameter_c(pp, pm, writeAsProxies, isPersistable);
				} catch (IOException e) {
					throw new UncheckedIOException(e);
				}
				return buffers.pop().toString();
			}).collect(Collectors.joining(", "));

			InterfaceSignal_c InterfaceSignal_c4004 = InterfaceSignal_c.getOneC_ASOnR4004(inst);
			InterfaceOperation_c InterfaceOperation_c4004 = InterfaceOperation_c.getOneC_IOOnR4004(inst);

			// get the return type if applicable
			// TODO handle type references, dimensions
			DataType_c returnType = DataType_c.getOneS_DTOnR4008(InterfaceOperation_c4004);
			final String returnTypeRef = returnType != null ? " return " + returnType.getName() : "";

			// get the message direction
			final String direction = (InterfaceSignal_c4004 != null
					&& InterfaceSignal_c4004.getDirection() == Ifdirectiontype_c.ClientServer)
					|| (InterfaceOperation_c4004 != null
							&& InterfaceOperation_c4004.getDirection() == Ifdirectiontype_c.ClientServer)
									? "to provider"
									: "from provider";

			// append the message
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
			// get the parameter type
			// TODO handle type references, dimensions
			DataType_c paramType = DataType_c.getOneS_DTOnR4007(inst, true);
			append("%s: %s", inst.getName(), paramType.getName());
		} else {
			write_PropertyParameter_c_proxy_sql(inst);
		}
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
