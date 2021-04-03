package org.xtuml.bp.io.xmi.translate;

import java.util.Map;

import org.xtuml.bp.io.xmi.translate.processors.XtumlTypeProcessor;
import org.xtuml.bp.io.xmi.translate.processors.sql.ComponentProcessorSQL;
import org.xtuml.bp.io.xmi.translate.processors.sql.InterfaceProcessorSQL;
import org.xtuml.bp.io.xmi.translate.processors.sql.AttributeProcessorSQL;
import org.xtuml.bp.io.xmi.translate.processors.sql.DataTypeProcessorSQL;
import org.xtuml.bp.io.xmi.translate.processors.sql.HeaderProcessorSQL;
import org.xtuml.bp.io.xmi.translate.processors.sql.InterfaceOperationProcessorSQL;
import org.xtuml.bp.io.xmi.translate.processors.sql.ModelClassProcessorSQL;
import org.xtuml.bp.io.xmi.translate.processors.sql.PackageProcessorSQL;

import com.sdmetrics.model.ModelElement;

public class XtumlMapper {

	static enum MapperType {
		SQL,
	}

	static Map<MapperType, Map<String, XtumlTypeProcessor>> mappers = Map.of(MapperType.SQL,
			Map.of("HEADER", new HeaderProcessorSQL(), "S_DT", new DataTypeProcessorSQL(), "O_OBJ",
					new ModelClassProcessorSQL(), "EP_PKG", new PackageProcessorSQL(), "O_ATTR",
					new AttributeProcessorSQL(), "C_C", new ComponentProcessorSQL(), "C_I", new InterfaceProcessorSQL(),
					"C_IO", new InterfaceOperationProcessorSQL()));

	public static String process(MapperType type, ModelElement e, String mapping) {
		Map<String, XtumlTypeProcessor> processors = mappers.get(type);
		XtumlTypeProcessor xtumlTypeProcessor = processors.get(mapping);
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
