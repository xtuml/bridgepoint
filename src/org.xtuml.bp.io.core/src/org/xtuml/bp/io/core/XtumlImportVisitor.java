package org.xtuml.bp.io.core;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.xtuml.bp.core.Actiondialect_c;
import org.xtuml.bp.core.ComponentResultSet_c;
import org.xtuml.bp.core.ComponentVisibility_c;
import org.xtuml.bp.core.Component_c;
import org.xtuml.bp.core.DataType_c;
import org.xtuml.bp.core.ElementVisibility_c;
import org.xtuml.bp.core.Elementtypeconstants_c;
import org.xtuml.bp.core.Gd_c;
import org.xtuml.bp.core.InstanceReferenceDataType_c;
import org.xtuml.bp.core.ModelClass_c;
import org.xtuml.bp.core.Ooaofooa;
import org.xtuml.bp.core.Package_c;
import org.xtuml.bp.core.PackageableElement_c;
import org.xtuml.bp.core.SearchResultSet_c;
import org.xtuml.bp.core.SystemModel_c;
import org.xtuml.bp.core.common.ClassQueryInterface_c;
import org.xtuml.bp.core.common.ModelRoot;
import org.xtuml.bp.core.common.NonRootModelElement;
import org.xtuml.bp.core.common.PersistenceManager;
import org.xtuml.bp.core.common.SequentialExecutor;
import org.xtuml.bp.io.core.CoreImport.XtumlLoadException;
import org.xtuml.bp.io.core.XtumlParser.Action_bodyContext;
import org.xtuml.bp.io.core.XtumlParser.Array_type_referenceContext;
import org.xtuml.bp.io.core.XtumlParser.Const_expressionContext;
import org.xtuml.bp.io.core.XtumlParser.Discontiguous_definitionContext;
import org.xtuml.bp.io.core.XtumlParser.Inst_set_type_referenceContext;
import org.xtuml.bp.io.core.XtumlParser.Inst_type_referenceContext;
import org.xtuml.bp.io.core.XtumlParser.MarkContext;
import org.xtuml.bp.io.core.XtumlParser.Mark_argumentContext;
import org.xtuml.bp.io.core.XtumlParser.MarksContext;
import org.xtuml.bp.io.core.XtumlParser.NameContext;
import org.xtuml.bp.io.core.XtumlParser.Named_type_referenceContext;
import org.xtuml.bp.io.core.XtumlParser.Scoped_nameContext;
import org.xtuml.bp.io.core.XtumlParser.TargetContext;

public class XtumlImportVisitor extends XtumlBaseVisitor<Object> {

	protected final SequentialExecutor executor;
	protected final Ooaofooa modelRoot;
	protected final DataType_c integerType;
	protected final DataType_c voidType;
	protected final DataType_c uniqueIdType;

	protected NonRootModelElement currentRoot = null;
	protected NonRootModelElement searchRoot = null;

	public static final String MESSAGE_NUM = "message_num";
	public static final String OPERATION_NUM = "operation_num";
	public static final String FUNCTION_NUM = "function_num";
	public static final String EVENT_NUM = "event_num";
	public static final String STATE_NUM = "state_num";
	public static final String SYSTEM_MODEL = "system_model";
	public static final String USE_GLOBALS = "use_globals";
	public static final String MULTIPLICITY = "mult";
	public static final String KEY_LETTERS = "key_letters";
	public static final String REALIZED = "realized";
	public static final String HIDE_GRAPHIC = "hide_graphic";
	public static final String INFORMAL_NAME = "informal_name";
	public static final String NOPARSE = "noparse";
	public static final String CLASS_NUM = "class_num";
	public static final String REF_MODE = "ref_mode";
	public static final String USE_PREFIX = "use_prefix";
	public static final String USE_REF_PREFIX = "use_referred_to_prefix";
	public static final String NUMBER_RANGE = "start_numbering";
	public static final String RAW_TYPE_DEF = "raw_definition";
	public static final String CLASSIFIER_NAME = "classifier_name";
	public static final String FINAL = "final";
	public static final String DIALECT = "dialect";

	public XtumlImportVisitor(final Ooaofooa modelRoot) {
		this.executor = PersistenceManager.getDefaultInstance().getSequentialExecutor();
		this.modelRoot = modelRoot;
		this.integerType = (DataType_c) modelRoot.getInstanceList(DataType_c.class)
				.getGlobal("ba5eda7a-def5-0000-0000-000000000002");
		this.voidType = (DataType_c) modelRoot.getInstanceList(DataType_c.class)
				.getGlobal("ba5eda7a-def5-0000-0000-000000000000");
		this.uniqueIdType = (DataType_c) modelRoot.getInstanceList(DataType_c.class)
				.getGlobal("ba5eda7a-def5-0000-0000-000000000005");
	}

	@Override
	public NonRootModelElement visitTarget(TargetContext ctx) {
		final NonRootModelElement rootElement = (NonRootModelElement) super.visitTarget(ctx);
		if (rootElement == null) {
			throw new XtumlLoadException("Root element is null.");
		} else {
			return rootElement;
		}
	}

	@Override
	public NonRootModelElement visitDiscontiguous_definition(Discontiguous_definitionContext ctx) {
		final String parentPath = visitScoped_name(ctx.parent_name);
		try {
			// TODO load systems and then navigate to packages and components
			currentRoot = executor.callAndWait(() -> {
				final SystemModel_c[] systems = SystemModel_c
						.SystemModelInstances(Ooaofooa.getInstance(ModelRoot.DEFAULT_WORKING_MODELSPACE));
				final Package_c[] pkgs = Package_c.getManyEP_PKGsOnR1405(systems);
				final Component_c[] comps = Component_c
						.getManyC_CsOnR8001(PackageableElement_c.getManyPE_PEsOnR8000(pkgs));
				final ModelClass_c[] classes = ModelClass_c
						.getManyO_OBJsOnR8001(PackageableElement_c.getManyPE_PEsOnR8000(pkgs));
				return Stream.of(systems, pkgs, comps, classes).flatMap(s -> Stream.of(s))
						.filter(selected -> selected.getPath().equals(parentPath)).findAny();
			});
			searchRoot = currentRoot;
		} catch (Exception e) {
			throw new CoreImport.XtumlLoadException("Failed to find parent element '" + parentPath + "'.", e);
		}
		return (NonRootModelElement) visit(ctx.definition());
	}

	@Override
	public DataType_c visitNamed_type_reference(Named_type_referenceContext ctx) {
		String typeName = visitScoped_name(ctx.scoped_name());
		if ("timer".equals(typeName)) {
			typeName = "inst_ref<Timer>";
		}
		final String scopedTypeName = typeName;
		try {
			return executor.callAndWait(
					() -> searchByPath(Elementtypeconstants_c.DATATYPE, scopedTypeName, DataType_c::getOneS_DTOnR8001));
		} catch (Exception e) {
			throw new CoreImport.XtumlLoadException(
					"Failed to find type '" + visit(ctx.scoped_name()) + "' for named type reference.", e);
		}
	}

	@Override
	public DataType_c visitArray_type_reference(Array_type_referenceContext ctx) {
		return (DataType_c) visit(ctx.type_reference());
	}

	@Override
	public DataType_c visitInst_type_reference(Inst_type_referenceContext ctx) {
		try {
			final String refClassPath = visitScoped_name(ctx.scoped_name());
			final ModelClass_c refClass = executor.callAndWait(
					() -> searchByPath(Elementtypeconstants_c.CLASS, refClassPath, ModelClass_c::getOneO_OBJOnR8001));
			refClass.Newinstancereferencedatatype();
			return DataType_c.getOneS_DTOnR17(InstanceReferenceDataType_c.getOneS_IRDTOnR123(refClass,
					selected -> !((InstanceReferenceDataType_c) selected).getIsset()));
		} catch (Exception e) {
			throw new CoreImport.XtumlLoadException(
					"Failed to find class '" + visit(ctx.scoped_name()) + "' for instance type reference.", e);
		}
	}

	@Override
	public DataType_c visitInst_set_type_reference(Inst_set_type_referenceContext ctx) {
		return DataType_c.getOneS_DTOnR17(InstanceReferenceDataType_c.getOneS_IRDTOnR123(
				ModelClass_c.getOneO_OBJOnR123(InstanceReferenceDataType_c
						.getOneS_IRDTOnR17(visitInst_type_reference(ctx.inst_type_reference()))),
				selected -> ((InstanceReferenceDataType_c) selected).getIsset()));
	}

	@Override
	public Map<String, Mark> visitMarks(MarksContext ctx) {
		final Map<String, Mark> marks = new HashMap<>();
		ctx.mark().forEach(m -> marks.put(m.MarkName().getText().substring(1), visitMark(m)));
		return Collections.unmodifiableMap(marks);
	}

	@Override
	public Mark visitMark(MarkContext ctx) {
		final Mark mark = new Mark();
		if (ctx.mark_arguments() != null) {
			final ListIterator<Mark_argumentContext> args = ctx.mark_arguments().mark_argument().listIterator();
			while (args.hasNext()) {
				final int i = args.nextIndex();
				final Mark_argumentContext arg = args.next();
				final String name = arg.arg_name != null ? arg.arg_name.getText() : Integer.toString(i);
				mark.put(name, visit(arg));
			}
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
		} else if (ctx.RealLiteral() != null) {
			return Double.parseDouble(ctx.getText());
		} else {
			return ctx.getText();
		}
	}

	@Override
	public String visitAction_body(Action_bodyContext ctx) {
		final String text = ctx.getText();
		if (ctx.UnparsedActions() != null) {
			final String tab = text.lines().filter(l -> l.contains("@endnoparse"))
					.map(l -> l.substring(0, l.length() - "@endnoparse".length())).findAny().orElse("");
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

	protected String getDimString(final Array_type_referenceContext ctx) {
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

	protected <T extends NonRootModelElement> Optional<T> searchByPath(final int elementType, final String path,
			final Function<PackageableElement_c, T> resultMapper) {
		return searchByPath(elementType, path, resultMapper, true);
	}

	protected <T extends NonRootModelElement> Optional<T> searchByPath(final int elementType, final String path,
			final Function<PackageableElement_c, T> resultMapper, boolean includeDeclarations) {
		final List<T> results = findVisibleElements(searchRoot, elementType).stream().map(resultMapper)
				.filter(Objects::nonNull).filter(o -> o.getPath().endsWith(path))
				.filter(o -> includeDeclarations || !o.isDeclarationOnly()).collect(Collectors.toList());
		if (results.isEmpty()) {
			return Optional.empty();
		} else if (results.size() == 1) {
			return Optional.of(results.get(0));
		} else {
			throw new IllegalArgumentException("The given path corresponds to more than one unique element");
		}
	}

	private List<PackageableElement_c> findVisibleElements(final NonRootModelElement element, final int elementType) {
		if (element instanceof Package_c) {
			return findVisibleElements((Package_c) element, elementType);
		} else if (element instanceof Component_c) {
			return findVisibleElements((Component_c) element, elementType);
		} else if (element instanceof ModelClass_c) {
			return findVisibleElements(PackageableElement_c.getOnePE_PEOnR8001((ModelClass_c) element), elementType);
		} else if (element instanceof PackageableElement_c) {
			final Package_c pkg = Package_c.getOneEP_PKGOnR8000((PackageableElement_c) element);
			final Component_c comp = Component_c.getOneC_COnR8003((PackageableElement_c) element);
			if (pkg != null) {
				return findVisibleElements(pkg, elementType);
			} else if (comp != null) {
				return findVisibleElements(comp, elementType);
			} else {
				return Collections.emptyList();
			}

		} else {
			throw new IllegalArgumentException();
		}
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

	protected <T extends NonRootModelElement> T findOrCreate(Class<T> type, String path) {
		return findOrCreate(type, path, false);
	}

	@SuppressWarnings("unchecked")
	protected <T extends NonRootModelElement> T findOrCreate(Class<T> type, String path, boolean createAsDeclaration) {
		T instance = null;
		try {
			final Method selectAnyWhere = type.getMethod(type.getSimpleName().replace("_c", "") + "Instance",
					ModelRoot.class, ClassQueryInterface_c.class);
			final ClassQueryInterface_c whereClause = selected -> ((NonRootModelElement) selected).getPath()
					.equals(path);
			instance = (T) selectAnyWhere.invoke(null, modelRoot, whereClause);
			if (instance == null) {
				final Constructor<T> constructor = type.getConstructor(ModelRoot.class);
				instance = constructor.newInstance(modelRoot);
				instance.setDeclarationOnly(createAsDeclaration);
			}
			return instance;
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | InstantiationException e) {
			throw new CoreImport.XtumlLoadException(e);
		}
	}

	int getDialectCode(String dialect) {
		return Stream.of(Actiondialect_c.class.getFields()).filter(field -> field.getName().equals(dialect))
				.map(field -> {
					try {
						return (int) field.get(null);
					} catch (IllegalArgumentException | IllegalAccessException e) {
						return Actiondialect_c.OOA_UNINITIALIZED_ENUM;
					}
				}).findAny().orElse(Actiondialect_c.OOA_UNINITIALIZED_ENUM);
	}

	protected static final class Mark extends LinkedHashMap<String, Object> implements Map<String, Object> {

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