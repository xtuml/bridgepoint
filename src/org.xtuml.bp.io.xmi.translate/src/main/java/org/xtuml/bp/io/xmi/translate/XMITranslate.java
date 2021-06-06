package org.xtuml.bp.io.xmi.translate;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;
import org.xtuml.bp.io.xmi.translate.XtumlMapper.MapperType;
import org.xtuml.bp.io.xmi.translate.processors.sql.graphical.GraphicalElementProcessorSQL;

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
	public static Map<ModelElement, String> graphicElements = new HashMap<>();

	public void loadXMI(String xmi, String output)
			throws ParserConfigurationException, SAXException, XMLParseException {
		URL mm = getClass().getResource("metamodel2.xml");
		String[] transformers = new String[2];
		transformers[0] = getClass().getResource("xmiTrans2_0.xml").getFile();
		transformers[1] = getClass().getResource("xtumlXmiExtension.xml").getFile();
		loadXMI(mm.getFile(), transformers, xmi, output);
	}

	void loadXMI(String metamodel, String[] transformers, String xmi, String output)
			throws ParserConfigurationException, SAXException, XMLParseException {
		XMITranslator.log("Loading XMI document...");
		xtumlOutput = new StringBuilder();
		/* apply any header specified */
		xtumlOutput.append(XtumlMapper.process(MapperType.SQL, null, "HEADER"));
		MetaModel mm = null;
		XMLParser parser = null;
		XMITransformations trans = null;
		Model model = null;
		XMIReader reader = null;

		if (!metamodel.equals("")) {
			mm = new MetaModel();
			parser = new XMLParser();
			parser.parse(metamodel, mm.getSAXParserHandler());
		}
		if (transformers.length > 0) {
			trans = new XMITransformations(mm);
			for (int i = 0; i < transformers.length; i++) {
				parser.parse(transformers[i], trans.getSAXParserHandler());
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
		model.forEach(e -> outputXtuml(e));
		// process graphics after collecting ooaofooa data
		processGraphics();
		// if output is specified
		if (!output.equals("")) {
			try {
				File file = new File(output).getAbsoluteFile();
				File parent = file.getParentFile();
				parent.mkdirs();
				FileWriter xtumlWriter = new FileWriter(file);
				xtumlWriter.write(xtumlOutput.toString());
				xtumlWriter.close();
			} catch (IOException e) {
				System.err.println("Unable to export translated model.");
				System.err.println(e.getMessage());
			}
		}
		XMITranslator.printReport(output);
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
		XMITranslator.logTodo("\nTranslated " + graphicElements.size() + " graphical elements.");
	}

	private void outputXtuml(ModelElement e) {
		if (e == null)
			return;
		String mapping = e.getType().getMapping();
		if (mapping == null) {
			// see if a relational child
			mapping = getMappingAsRelationalChild(e);
			if (mapping == null) {
				XMITranslator.logNoMapping("WARN: No mapping for " + e.getType().getName());
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
		xtumlOutput.append(XtumlMapper.process(MapperType.SQL, e, mapping));
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
