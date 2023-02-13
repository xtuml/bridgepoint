package org.xtuml.bp.io.mdl;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Comparator;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.ui.editors.text.EditorsUI;
import org.eclipse.ui.texteditor.AbstractDecoratedTextEditorPreferenceConstants;
import org.xtuml.bp.core.Component_c;
import org.xtuml.bp.core.DataType_c;
import org.xtuml.bp.core.ExecutableProperty_c;
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
import org.xtuml.bp.core.SystemModel_c;
import org.xtuml.bp.core.common.IPersistenceHierarchyMetaData;
import org.xtuml.bp.core.common.NonRootModelElement;
import org.xtuml.bp.core.sorter.PropertyParameter_cSorter;
import org.xtuml.bp.io.core.ProxyUtil;

// TODO determine how to sanitize names

public class ExportModelText extends ExportModelComponent {

	private int tabDepth = 0;
	private final Stack<StringBuilder> buffers = new Stack<>();
	private final IPersistenceHierarchyMetaData metadata = new PersistenceHierarchyMetaData();

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
		if (element instanceof Interface_c || element instanceof SystemModel_c || element instanceof Component_c) {
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
			append("%spackage %s is\n", getTab(), sanitizeName(inst.getName()));
			tabDepth++;

			final Package_c[] ep_pkgs = Package_c.getManyEP_PKGsOnR1401(inst);
			if (ep_pkgs.length > 0) {
				append("%s\n", getTab());
			}
			Stream.of(ep_pkgs).sorted(Comparator.comparing(NonRootModelElement::getName)).forEach(ep_pkg -> {
				append("%spackage %s;\n", getTab(), sanitizeName(ep_pkg.getName()));
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
	protected void export_Component_c(Component_c inst, IProgressMonitor pm, boolean writeAsProxies,
			boolean isPersistable) throws IOException {
		if (inst == null) {
			return;
		}
		if (!writeAsProxies && !forceWriteAsProxy) {

			append("%swithin %s is\n\n", getTab(), metadata.getParent(inst).getPath());
			tabDepth++;

			// component metadata
			inst.getDescrip().strip().lines().forEach(line -> {
				append("%s//! %s\n", getTab(), line);
			});
			if (inst.getMult() == 1) {
				append("%s@mult(\"many\");\n", getTab());
			}
			if (!inst.getKey_lett().isBlank()) {
				append("%s@key_letters(\"%s\");\n", getTab(), inst.getKey_lett().strip());
			}
			if (inst.getIsrealized()) {
				append("%s@realized(classpath=\"%s\");\n", getTab(), inst.getRealized_class_path().strip());
			}

			append("%scomponent %s is\n", getTab(), sanitizeName(inst.getName()));
			tabDepth++;

			// TODO inner components, delegations, satisfactions, etc.

			// declare inner packages
			final Package_c[] ep_pkgs = Package_c
					.getManyEP_PKGsOnR8001(PackageableElement_c.getManyPE_PEsOnR8003(inst));
			if (ep_pkgs.length > 0) {
				append("%s\n", getTab());
			}
			Stream.of(ep_pkgs).sorted(Comparator.comparing(NonRootModelElement::getName)).forEach(ep_pkg -> {
				append("%spackage %s;\n", getTab(), sanitizeName(ep_pkg.getName()));
			});
			if (ep_pkgs.length > 0) {
				append("%s\n", getTab());
			}

			// ports
			final Port_c[] c_pos = Port_c.getManyC_POsOnR4010(inst);
			for (Port_c c_po : Stream.of(c_pos).sorted(Comparator.comparing(NonRootModelElement::getName)).collect(Collectors.toList())) {
				export_Port_c(c_po, pm, writeAsProxies, isPersistable);
			}

			tabDepth--;
			append("%send component;\n\n", getTab());
			tabDepth--;
			append("%send;\n", getTab());
		} else {
			super.export_Component_c(inst, pm, writeAsProxies, isPersistable);
		}

	}

	@Override
	protected void export_Port_c(Port_c inst, IProgressMonitor pm, boolean writeAsProxies, boolean isPersistable)
			throws IOException {
		if (inst == null) {
			return;
		}
		if (!writeAsProxies && !forceWriteAsProxy) {

			final InterfaceReference_c c_ir = InterfaceReference_c.getOneC_IROnR4016(inst);
			final Provision_c c_p = Provision_c.getOneC_POnR4009(c_ir);
			final Requirement_c c_r = Requirement_c.getOneC_ROnR4009(c_ir);
			final String description = c_p != null ? c_p.getDescrip() : c_r.getDescrip();
			final String informal_name = c_p != null ? c_p.getInformalname() : c_r.getInformalname();

			// port metadata
			description.strip().lines().forEach(line -> {
				append("%s//! %s\n", getTab(), line);
			});
			if (inst.getMult() == 1) {
				append("%s@mult(\"many\");\n", getTab());
			}
			if (inst.getDonotshowportoncanvas()) {
				append("%s@hide_graphic;\n", getTab());
			}
			if (!inst.getKey_lett().isBlank()) {
				append("%s@key_letters(\"%s\");\n", getTab(), inst.getKey_lett().strip());
			}
			if (!"Unnamed Interface".equals(informal_name)) {
				append("%s@informal_name(\"%s\");\n", getTab(), informal_name);
			}

			append("%s%s port %s", getTab(), c_p != null ? "provided" : "required", sanitizeName(inst.getName()));
			if (c_ir.Isformal()) {
				append(" implements %s is\n", c_ir.Interfacename()); // TODO get shortest path to the interface
				tabDepth++;

				final ProvidedOperation_c[] spr_pos = ProvidedOperation_c
						.getManySPR_POsOnR4503(ProvidedExecutableProperty_c.getManySPR_PEPsOnR4501(c_p));
				final ProvidedSignal_c[] spr_pss = ProvidedSignal_c
						.getManySPR_PSsOnR4503(ProvidedExecutableProperty_c.getManySPR_PEPsOnR4501(c_p));
				final RequiredOperation_c[] spr_ros = RequiredOperation_c
						.getManySPR_ROsOnR4502(RequiredExecutableProperty_c.getManySPR_REPsOnR4500(c_r));
				final RequiredSignal_c[] spr_rss = RequiredSignal_c
						.getManySPR_RSsOnR4502(RequiredExecutableProperty_c.getManySPR_REPsOnR4500(c_r));

				if (spr_pos.length + spr_pss.length + spr_ros.length + spr_rss.length > 0) {
					append("\n");
				}

				for (ProvidedOperation_c spr_po : Stream.of(spr_pos)
						.sorted(Comparator.comparing(NonRootModelElement::getName)).collect(Collectors.toList())) {
					export_ProvidedOperation_c(spr_po, pm, writeAsProxies, isPersistable);
				}
				for (ProvidedSignal_c spr_ps : Stream.of(spr_pss)
						.sorted(Comparator.comparing(NonRootModelElement::getName)).collect(Collectors.toList())) {
					export_ProvidedSignal_c(spr_ps, pm, writeAsProxies, isPersistable);
				}
				for (RequiredOperation_c spr_ro : Stream.of(spr_ros)
						.sorted(Comparator.comparing(NonRootModelElement::getName)).collect(Collectors.toList())) {
					export_RequiredOperation_c(spr_ro, pm, writeAsProxies, isPersistable);
				}
				for (RequiredSignal_c spr_rs : Stream.of(spr_rss)
						.sorted(Comparator.comparing(NonRootModelElement::getName)).collect(Collectors.toList())) {
					export_RequiredSignal_c(spr_rs, pm, writeAsProxies, isPersistable);
				}

				tabDepth--;
				append("%send port;\n\n", getTab());
			} else {
				append(";\n");
			}

		} else {
			write_Port_c_proxy_sql(inst);
		}
	}

	@Override
	protected void export_ProvidedSignal_c(ProvidedSignal_c inst, IProgressMonitor pm, boolean writeAsProxies,
			boolean isPersistable) throws IOException {
		if (inst == null) {
			return;
		}
		if (!writeAsProxies && !forceWriteAsProxy) {

			final ExecutableProperty_c c_ep = ExecutableProperty_c
					.getOneC_EPOnR4501(ProvidedExecutableProperty_c.getOneSPR_PEPOnR4503(inst));

			export_PortMessage(ProxyUtil.newProxy(PortMessage.class, inst), c_ep, pm, writeAsProxies, isPersistable);

		} else {
			write_ProvidedSignal_c_proxy_sql(inst);
		}
	}

	@Override
	protected void export_ProvidedOperation_c(ProvidedOperation_c inst, IProgressMonitor pm, boolean writeAsProxies,
			boolean isPersistable) throws IOException {
		if (inst == null) {
			return;
		}
		if (!writeAsProxies && !forceWriteAsProxy) {

			final ExecutableProperty_c c_ep = ExecutableProperty_c
					.getOneC_EPOnR4501(ProvidedExecutableProperty_c.getOneSPR_PEPOnR4503(inst));

			export_PortMessage(ProxyUtil.newProxy(PortMessage.class, inst), c_ep, pm, writeAsProxies, isPersistable);

		} else {
			write_ProvidedOperation_c_proxy_sql(inst);
		}
	}

	@Override
	protected void export_RequiredSignal_c(RequiredSignal_c inst, IProgressMonitor pm, boolean writeAsProxies,
			boolean isPersistable) throws IOException {
		if (inst == null) {
			return;
		}
		if (!writeAsProxies && !forceWriteAsProxy) {

			final ExecutableProperty_c c_ep = ExecutableProperty_c
					.getOneC_EPOnR4500(RequiredExecutableProperty_c.getOneSPR_REPOnR4502(inst));

			export_PortMessage(ProxyUtil.newProxy(PortMessage.class, inst), c_ep, pm, writeAsProxies, isPersistable);

		} else {
			write_RequiredSignal_c_proxy_sql(inst);
		}
	}

	@Override
	protected void export_RequiredOperation_c(RequiredOperation_c inst, IProgressMonitor pm, boolean writeAsProxies,
			boolean isPersistable) throws IOException {
		if (inst == null) {
			return;
		}
		if (!writeAsProxies && !forceWriteAsProxy) {

			final ExecutableProperty_c c_ep = ExecutableProperty_c
					.getOneC_EPOnR4500(RequiredExecutableProperty_c.getOneSPR_REPOnR4502(inst));

			export_PortMessage(ProxyUtil.newProxy(PortMessage.class, inst), c_ep, pm, writeAsProxies, isPersistable);

		} else {
			write_RequiredOperation_c_proxy_sql(inst);
		}
	}

	@Override
	protected void export_Interface_c(Interface_c inst, IProgressMonitor pm, boolean writeAsProxies,
			boolean isPersistable) throws IOException {
		if (inst == null) {
			return;
		}
		if (!writeAsProxies && !forceWriteAsProxy) {
			append("%swithin %s is\n\n", getTab(), metadata.getParent(inst).getPath());
			tabDepth++;
			inst.getDescrip().strip().lines().forEach(line -> {
				append("%s//! %s\n", getTab(), line);
			});
			append("%sinterface %s is\n", getTab(), sanitizeName(inst.getName()));
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

			// append the description
			inst.getDescrip().strip().lines().forEach(line -> {
				append("%s//! %s\n", getTab(), line);
			});

			// TODO append parameter descriptions

			// append the message
			if (inst.getNumb() > 0) {
				append("%s@message_num(%d);\n", getTab(), inst.getNumb());
			}
			append("%s%s;\n", getTab(), getMessageSignature(inst, pm, writeAsProxies, isPersistable));

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

	private void export_PortMessage(PortMessage inst, ExecutableProperty_c c_ep, IProgressMonitor pm,
			boolean writeAsProxies, boolean isPersistable) throws IOException {
		// message metadata
		if (inst.getSuc_pars() == Parsestatus_c.doNotParse) {
			append("%s@noparse;\n", getTab());
		}
		if (inst.getNumb() > 0) {
			append("%s@message_num(%d);\n", getTab(), inst.getNumb());
		}

		append("%s%s is\n", getTab(), getMessageSignature(c_ep, pm, writeAsProxies, isPersistable));
		tabDepth++;

		// append the inner actions
		if (!inst.getAction_semantics().isBlank()) {
			append("%s@noparse\n", getTab()); // TODO
		}
		inst.getAction_semantics().strip().lines().forEach(line -> {
			append("%s%s\n", getTab(), line);
		});
		if (!inst.getAction_semantics().isBlank()) {
			append("%s@endnoparse\n", getTab());
		}

		tabDepth--;
		append("%send message;\n\n", getTab());
	}

	private String getMessageSignature(final ExecutableProperty_c c_ep, final IProgressMonitor pm,
			final boolean writeAsProxies, final boolean isPersistable) {

		// build the parameter list
		final PropertyParameter_c[] c_pps = PropertyParameter_c.getManyC_PPsOnR4006(c_ep);
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
		final DataType_c returnType = DataType_c.getOneS_DTOnR4008(InterfaceOperation_c.getOneC_IOOnR4004(c_ep));
		final String returnTypeRef = returnType != null
				? " return "
						+ getTypeReference(returnType,
								InterfaceOperation_c.getOneC_IOOnR4004(c_ep).getReturn_dimensions(),
								Package_c.getOneEP_PKGOnR8000(
										PackageableElement_c.getOnePE_PEOnR8001(Interface_c.getOneC_IOnR4003(c_ep))))
				: "";

		// get the message direction
		final String direction = c_ep.getDirection() == Ifdirectiontype_c.ClientServer ? "to provider"
				: "from provider";

		return String.format("message %s(%s)%s %s", c_ep.getName(), parameterList, returnTypeRef, direction);
	}

	private String sanitizeName(final String name) {
		if (name.matches(".*\\s.*")) {
			return "'" + name.replaceAll("\\s+", " ") + "'";
		} else {
			return name;
		}
	}

	private String getTypeReference(final DataType_c type, final String dims, final Package_c referencePoint) {
		String type_ref = "";
		Matcher m = Pattern.compile("\\[([^\\]]*)\\]").matcher(dims);
		while (m.find()) {
			type_ref += String.format("sequence%s of ", !m.group(1).isBlank() ? " (" + m.group(1) + ")" : "");
		}
		final InstanceReferenceDataType_c irdt = InstanceReferenceDataType_c.getOneS_IRDTOnR17(type);
		if (irdt != null) {
			final ModelClass_c obj = ModelClass_c.getOneO_OBJOnR123(irdt);
			if (irdt.getIsset()) {
				type_ref += "set of ";
			}
			type_ref += "instance of " + obj.getKey_lett(); // TODO get shortest scoped name
		} else {
			type_ref += type.getName(); // TODO get shortest scoped name
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
		final IPreferenceStore store = EditorsUI.getPreferenceStore();
		final boolean spacesForTabs = store.getBoolean(AbstractDecoratedTextEditorPreferenceConstants.EDITOR_SPACES_FOR_TABS);
		final String tabChar = spacesForTabs ? " " : "\t";
		final int tabWidth = spacesForTabs ? store.getInt(AbstractDecoratedTextEditorPreferenceConstants.EDITOR_TAB_WIDTH) : 1;
		String tab = "";
		for (int i = 0; i < tabDepth * tabWidth; i++) {
			tab += tabChar;
		}
		return tab;
	}

	private interface PortMessage {
		int getSuc_pars();

		int getNumb();

		String getAction_semantics();
	}

}
