package org.xtuml.bp.io.xmi.translate;

import java.util.Map;

import org.xtuml.bp.io.xmi.translate.processors.XtumlTypeProcessor;
import org.xtuml.bp.io.xmi.translate.processors.sql.RequirementProcessorSQL;
import org.xtuml.bp.io.xmi.translate.processors.sql.ComponentProcessorSQL;
import org.xtuml.bp.io.xmi.translate.processors.sql.InterfaceProcessorSQL;
import org.xtuml.bp.io.xmi.translate.processors.sql.AttributeProcessorSQL;
import org.xtuml.bp.io.xmi.translate.processors.sql.DataTypeProcessorSQL;
import org.xtuml.bp.io.xmi.translate.processors.sql.HeaderProcessorSQL;
import org.xtuml.bp.io.xmi.translate.processors.sql.InterfaceOperationProcessorSQL;
import org.xtuml.bp.io.xmi.translate.processors.sql.ModelClassProcessorSQL;
import org.xtuml.bp.io.xmi.translate.processors.sql.OperationProcessorSQL;
import org.xtuml.bp.io.xmi.translate.processors.sql.PackageProcessorSQL;
import org.xtuml.bp.io.xmi.translate.processors.sql.ProvisionProcessorSQL;
import org.xtuml.bp.io.xmi.translate.processors.sql.graphical.GraphicalElementProcessorSQL;
import org.xtuml.bp.io.xmi.translate.processors.sql.graphical.ModelProcessorSQL;

import com.sdmetrics.model.ModelElement;

import static java.util.Map.entry;

public class XtumlMapper {

	static enum MapperType {
		SQL,
	}

	static Map<MapperType, Map<String, XtumlTypeProcessor>> mappers = Map.of(MapperType.SQL,
			Map.ofEntries(entry("HEADER", new HeaderProcessorSQL()), entry("S_DT", new DataTypeProcessorSQL()),
					entry("O_OBJ", new ModelClassProcessorSQL()), entry("EP_PKG", new PackageProcessorSQL()),
					entry("O_ATTR", new AttributeProcessorSQL()), entry("C_C", new ComponentProcessorSQL()),
					entry("C_I", new InterfaceProcessorSQL()), entry("C_IO", new InterfaceOperationProcessorSQL()),
					entry("O_TFR", new OperationProcessorSQL()), entry("C_P", new ProvisionProcessorSQL()),
					entry("C_R", new RequirementProcessorSQL())));
	static Map<MapperType, Map<String, XtumlTypeProcessor>> graphicalMappers = Map.of(MapperType.SQL,
			Map.of("GD_MD", new ModelProcessorSQL(), "GD_GE", new GraphicalElementProcessorSQL()));

	public static String process(MapperType type, ModelElement e, String mapping) {
		Map<String, XtumlTypeProcessor> processors = mappers.get(type);
		XtumlTypeProcessor xtumlTypeProcessor = processors.get(mapping);
		if (xtumlTypeProcessor == null) {
			// check for graphical processor
			processors = graphicalMappers.get(type);
			xtumlTypeProcessor = processors.get(mapping);
		}
		if (xtumlTypeProcessor == null) {
			XMITranslator.logNoProcessor("TODO: Implement processor for " + mapping);
			return "";
		} else {
			String result = xtumlTypeProcessor.process(e, mapping);
			XMITranslator.log(result);
			return result;
		}
	}

}
