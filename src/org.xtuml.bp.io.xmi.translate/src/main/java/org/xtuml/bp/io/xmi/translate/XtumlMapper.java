package org.xtuml.bp.io.xmi.translate;

import static java.util.Map.entry;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import com.sdmetrics.model.ModelElement;

import org.xtuml.bp.io.xmi.translate.processors.XtumlTypeProcessor;
import org.xtuml.bp.io.xmi.translate.processors.sql.ActorParticipantProcessorSQL;
import org.xtuml.bp.io.xmi.translate.processors.sql.AttributeProcessorSQL;
import org.xtuml.bp.io.xmi.translate.processors.sql.CommentProcessorSQL;
import org.xtuml.bp.io.xmi.translate.processors.sql.ComponentProcessorSQL;
import org.xtuml.bp.io.xmi.translate.processors.sql.DataTypeProcessorSQL;
import org.xtuml.bp.io.xmi.translate.processors.sql.EnumerationDataTypeProcessorSQL;
import org.xtuml.bp.io.xmi.translate.processors.sql.EnumeratorProcessorSQL;
import org.xtuml.bp.io.xmi.translate.processors.sql.HeaderProcessorSQL;
import org.xtuml.bp.io.xmi.translate.processors.sql.InterfaceOperationProcessorSQL;
import org.xtuml.bp.io.xmi.translate.processors.sql.InterfaceProcessorSQL;
import org.xtuml.bp.io.xmi.translate.processors.sql.ModelClassProcessorSQL;
import org.xtuml.bp.io.xmi.translate.processors.sql.OperationParameterProcessorSQL;
import org.xtuml.bp.io.xmi.translate.processors.sql.OperationProcessorSQL;
import org.xtuml.bp.io.xmi.translate.processors.sql.PackageProcessorSQL;
import org.xtuml.bp.io.xmi.translate.processors.sql.PropertyParameterProcessorSQL;
import org.xtuml.bp.io.xmi.translate.processors.sql.ProvisionProcessorSQL;
import org.xtuml.bp.io.xmi.translate.processors.sql.RequirementProcessorSQL;
import org.xtuml.bp.io.xmi.translate.processors.sql.UseCaseParticipantProcessorSQL;
import org.xtuml.bp.io.xmi.translate.processors.sql.graphical.GraphicalElementProcessorSQL;
import org.xtuml.bp.io.xmi.translate.processors.sql.graphical.ModelProcessorSQL;

public class XtumlMapper {

	static enum MapperType {
		SQL,
	}

	static Map<MapperType, Map<String, XtumlTypeProcessor>> mappers = Map.of(MapperType.SQL, Map.ofEntries(
			entry("HEADER", new HeaderProcessorSQL()), entry("S_DT", new DataTypeProcessorSQL()),
			entry("O_OBJ", new ModelClassProcessorSQL()), entry("EP_PKG", new PackageProcessorSQL()),
			entry("O_ATTR", new AttributeProcessorSQL()), entry("C_C", new ComponentProcessorSQL()),
			entry("C_I", new InterfaceProcessorSQL()), entry("C_IO", new InterfaceOperationProcessorSQL()),
			entry("O_TFR", new OperationProcessorSQL()), entry("C_P", new ProvisionProcessorSQL()),
			entry("C_R", new RequirementProcessorSQL()), entry("O_TPARM", new OperationParameterProcessorSQL()),
			entry("C_PP", new PropertyParameterProcessorSQL()), entry("PE_C", new CommentProcessorSQL()),
			entry("S_EDT", new EnumerationDataTypeProcessorSQL()), entry("S_ENUM", new EnumeratorProcessorSQL()),
			entry("IA_UCP", new UseCaseParticipantProcessorSQL()), entry("SQ_AP", new ActorParticipantProcessorSQL())));
	static Map<MapperType, Map<String, XtumlTypeProcessor>> graphicalMappers = Map.of(MapperType.SQL,
			Map.of("GD_MD", new ModelProcessorSQL(), "GD_GE", new GraphicalElementProcessorSQL()));

	public static String process(MapperType type, ModelElement e, String mapping) {
		// mapping can be either a single key_lett value or an array of values
		String actualMapping = getMappingToUse(e, mapping);
		Map<String, XtumlTypeProcessor> processors = mappers.get(type);
		XtumlTypeProcessor xtumlTypeProcessor = processors.get(actualMapping);
		if (xtumlTypeProcessor == null) {
			// check for graphical processor
			processors = graphicalMappers.get(type);
			xtumlTypeProcessor = processors.get(actualMapping);
		}
		if (xtumlTypeProcessor == null) {
			XMITranslator.logNoProcessor("TODO: Implement processor for " + actualMapping);
			return "";
		} else {
			String result = xtumlTypeProcessor.process(e, actualMapping);
			XMITranslator.log(result);
			return result;
		}
	}

	private static String getMappingToUse(ModelElement e, String mapping) {
		String[] mappings = mapping.split(",");
		if (mappings.length > 1) {
			// mapping is further broken into parent type / child key lett
			Optional<String> expectedMapping = Stream.of(mappings).filter(m -> {
				String[] ancestorChildEntries = m.split(":");
				if (ancestorChildEntries.length > 1) {
					// if any ancestor type matches elements owner type
					boolean foundAncestor = false;
					ModelElement owner = e.getOwner();
					while (owner != null) {
						if (owner.getType().getName().equals(ancestorChildEntries[0].trim())) {
							foundAncestor = true;
							break;
						}
						owner = owner.getOwner();
					}
					return foundAncestor;
				}
				return false;
			}).findFirst();
			if (expectedMapping.isPresent()) {
				return expectedMapping.get().split(":")[1];
			}
		}
		return mapping;
	}

}
