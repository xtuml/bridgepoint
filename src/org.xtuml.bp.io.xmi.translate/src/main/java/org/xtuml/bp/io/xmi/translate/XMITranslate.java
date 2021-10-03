package org.xtuml.bp.io.xmi.translate;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xtuml.bp.io.xmi.translate.XtumlSQLMapper.MapperType;
import org.xtuml.bp.io.xmi.translate.processors.sql.graphical.GraphicalElementProcessorSQL;
import org.xtuml.bp.io.xmi.translate.processors.sql.packages.PackageProcessorSQL;
import org.xtuml.bp.io.xmi.translate.processors.sql.types.DataTypeProcessorSQL;

import com.sdmetrics.model.MetaModel;
import com.sdmetrics.model.MetaModelElement.MetaModelElementAttribute;
import com.sdmetrics.model.Model;
import com.sdmetrics.model.ModelElement;
import com.sdmetrics.model.XMIReader;
import com.sdmetrics.model.XMITransformations;
import com.sdmetrics.util.XMLParser;
import com.sdmetrics.util.XMLParser.XMLParseException;

/**
 * Translate UML2 compatible XMI to xtUML
 */
public class XMITranslate {

	private StringBuilder xtumlOutput = new StringBuilder();
	static List<ModelElement> processing = new ArrayList<>();
	static Map<ModelElement, String> afterGraphicsElements = new HashMap<>();
	public static Map<ModelElement, String> graphicElements = new HashMap<>();
	public static Map<String, ModelElement> eaDiagramConnectors = new HashMap<>();
	public static Map<String, String> eaPropertyParameterTypes = new HashMap<>();
	public static Map<String, ModelElement> eaStubTypes = new HashMap<>();

	public static List<String> supportedExtensions = new ArrayList<>() {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		{
			add("*.xml");
			add("*.xmi");
		}
	};

	static List<String> afterGraphics = new ArrayList<>() {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		{
			add("R_ASSR");
			add("R_SUB");
		}
	};
	public static ILogger logger;

	public XMITranslate(ILogger pLogger) {
		logger = pLogger;
	}

	public String loadXMI(String xmi)
			throws ParserConfigurationException, SAXException, XMLParseException, IOException {
		InputStream mm = getClass().getClassLoader().getResourceAsStream("metamodel2.xml");
		InputStream[] transformers = new InputStream[2];
		transformers[0] = getClass().getClassLoader().getResourceAsStream("xmiTrans2_0.xml");
		transformers[1] = getClass().getClassLoader().getResourceAsStream("xtumlExtensionsTrans.xml");
		return loadXMI(mm, transformers, xmi, "");
	}

	String loadXMI(Object metamodel, Object[] transformers, String xmi, String output)
			throws ParserConfigurationException, SAXException, XMLParseException, IOException {
		logger.log("Loading XMI document...");
		xtumlOutput = new StringBuilder();
		/* apply any header specified */
		xtumlOutput.append(XtumlSQLMapper.process(MapperType.SQL, null, "HEADER"));
		MetaModel mm = null;
		XMLParser parser = null;
		SAXParser saxParser = null;
		XMITransformations trans = null;
		Model model = null;
		XMIReader reader = null;

		if (!metamodel.equals("")) {
			mm = new MetaModel();
			parser = new XMLParser();
			if (metamodel instanceof InputStream) {
				SAXParserFactory spf = SAXParserFactory.newInstance();
				spf.setValidating(false);
				spf.setNamespaceAware(false);
				saxParser = spf.newSAXParser();
				InputSource source = new InputSource((InputStream) metamodel);
				saxParser.parse(source, mm.getSAXParserHandler());
			} else {
				parser.parse((String) metamodel, mm.getSAXParserHandler());
			}
		}
		if (transformers.length > 0) {
			trans = new XMITransformations(mm);
			for (int i = 0; i < transformers.length; i++) {
				if (transformers[i] instanceof String) {
					parser.parse((String) transformers[i], trans.getSAXParserHandler());
				} else if (transformers[i] instanceof InputStream) {
					InputSource source = new InputSource((InputStream) transformers[i]);
					saxParser.parse(source, trans.getSAXParserHandler());
				}
			}
		}
		if (!xmi.equals("")) {
			model = new Model(mm);
			reader = new XMIReader(trans, model);
			parser.parse(xmi, reader);
		}
		ModelElement root = model.iterator().next();
		while (root.getOwner() != null) {
			root = root.getOwner();
		}
		model.forEach(e -> cacheSupportingElements(e));
		model.forEach(e -> createSupportingElements(e));
		model.forEach(e -> outputXtuml(e));
		// process graphics after collecting ooaofooa data
		processGraphics();
		// process elements that require all graphics processed first
		processAfterGraphics();
		// if output is specified
		if (!output.equals("")) {
			try {
				File file = new File(output).getAbsoluteFile();
				File parent = file.getParentFile();
				parent.mkdirs();
				FileWriter xtumlWriter = new FileWriter(file, Charset.forName("UTF-8"));
				xtumlWriter.write(xtumlOutput.toString());
				xtumlWriter.close();
			} catch (IOException e) {
				System.err.println("Unable to export translated model.");
				System.err.println(e.getMessage());
			}
		}
		logger.printReport(output);
		return xtumlOutput.toString();
	}

	private void processGraphics() {
		// process shapes first to allow for DIM_CON setup
		graphicElements.forEach((e, m) -> {
			if (m.equals("GD_GE")) {
				GraphicalElementProcessorSQL processor = new GraphicalElementProcessorSQL();
				processor.setKeyLetters("GD_GE");
				processor.setModelElement(e);
				if (processor.isShape()) {
					mapToXtuml(e, m);
				}
			} else {
				mapToXtuml(e, m);
			}
		});
		graphicElements.forEach((e, m) -> {
			if (m.equals("GD_GE")) {
				GraphicalElementProcessorSQL processor = new GraphicalElementProcessorSQL();
				processor.setKeyLetters("GD_GE");
				processor.setModelElement(e);
				if (!processor.isShape()) {
					mapToXtuml(e, m);
				}
			}
		});
		logger.logTodo("\nTranslated " + graphicElements.size() + " graphical elements.");
	}

	private void processAfterGraphics() {
		afterGraphicsElements.forEach((e, m) -> {
			// remove from list
			afterGraphics.remove(m);
			processing.add(e);
			mapToXtuml(e, m);
		});
	}

	private void cacheSupportingElements(ModelElement e) {
		// cache diagram connectors for graphic creation
		if (e.getType().getName().equals("diagramConnector")) {
			String associationClass = e.getPlainAttribute("associationClass");
			if (!associationClass.equals("")) {
				eaDiagramConnectors.put(associationClass, e);
			}
			eaDiagramConnectors.put(e.getPlainAttribute("subject"), e);
		}
		if (e.getType().getName().equals("eaPropertyParameter")) {
			String eaType = e.getPlainAttribute("eaType");
			if (!eaType.equals("")) {
				eaPropertyParameterTypes.put(e.getPlainAttribute("eaId"), eaType);
			}
		}
	}

	static String pkgId = null;

	private void createSupportingElements(ModelElement e) {
		if (e.getType().getName().equals("eaStub")) {
			if (pkgId == null) {
				String[] packageInfo = PackageProcessorSQL.createPackage("EAStub_Types",
						"Enterprise Architecture has certain types marked as EAStub entries.  These may be intended as core types, but creating a UDT here.");
				xtumlOutput.append(packageInfo[0]);
				pkgId = packageInfo[1];
			}
			String umlType = e.getPlainAttribute("eaType");
			if (umlType.equals("DataType")) {
				String dataType = DataTypeProcessorSQL.createDataType(e.getPlainAttribute("id"),
						e.getPlainAttribute("name"),
						"EAStub { Type: " + umlType + ", Name: " + e.getPlainAttribute("name") + "}", pkgId);
				xtumlOutput.append(dataType);
			}
		}
	}

	private void outputXtuml(ModelElement e) {
		if (e == null) {
			return;
		}
		String mapping = e.getType().getMapping();
		if (mapping == null && !e.getType().getName().equals("eaStub")) {
			// see if a relational child
			mapping = getMappingAsRelationalChild(e);
			if (mapping == null) {
				logger.logNoMapping("WARN: No mapping for " + e.getType().getName());
			}
		}
		if (mapping != null) {
			if (mapping.equals("GD_GE") || mapping.equals("GD_MD")) {
				graphicElements.put(e, mapping);
			} else {
				mapToXtuml(e, mapping);
			}
		}
	}

	private void mapToXtuml(ModelElement e, String mapping) {
		xtumlOutput.append(XtumlSQLMapper.process(MapperType.SQL, e, mapping));
	}

	private String getMappingAsRelationalChild(ModelElement e) {
		ModelElement parent = e.getRefAttribute("context");
		List<String> keys = e.getRelationKeys();
		for (int i = 0; i < keys.size(); i++) {
			if (!keys.get(i).equals("context") && parent.getType().hasAttribute(keys.get(i))) {
				MetaModelElementAttribute attribute = parent.getType().getAttribute(keys.get(i));
				if (attribute != null) {
					return attribute.getMapping();
				}
			}
		}
		return null;
	}

}
