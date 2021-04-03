package org.xtuml.bp.io.xmi.translate;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;
import org.xtuml.bp.io.xmi.translate.XtumlMapper.MapperType;

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

	private StringBuilder xtumlOutput = new StringBuilder();;

	public void loadXMI(String xmi, String output)
			throws ParserConfigurationException, SAXException, XMLParseException {
		URL mm = getClass().getResource("metamodel2.xml");
		URL transformer = getClass().getResource("xmiTrans2_0.xml");
		loadXMI(mm.getFile(), transformer.getFile(), xmi, output);
	}

	void loadXMI(String metamodel, String transformer, String xmi, String output)
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
		if (!transformer.equals("")) {
			trans = new XMITransformations(mm);
			parser.parse(transformer, trans.getSAXParserHandler());
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
		outputXtuml(root);
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

	private void outputXtuml(ModelElement e) {
		if (e == null)
			return;
		String mapping = e.getType().getMapping();
		if (mapping == null) {
			// see if a relational child
			mapping = getMappingAsRelationalChild(e);
			if (mapping == null) {
				XMITranslator.logNoMapping("WARN: No mapping for " + e.getType().getName());
				return;
			}
		}
		mapToXtuml(e, mapping);
		if (e.getOwnedElements() != null) {
			e.getOwnedElements().forEach(child -> {
				outputXtuml(child);
			});
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
