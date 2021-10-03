package org.xtuml.bp.io.xmi.translate.processors.sql.interaction.supporting;

import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

import org.xtuml.bp.io.xmi.translate.XMITranslate;
import org.xtuml.bp.io.xmi.translate.processors.sql.interaction.LifespanProcessorSQL;
import org.xtuml.bp.io.xmi.translate.processors.sql.interaction.MessageProcessorSQL;

import com.sdmetrics.model.ModelElement;

public class SequenceUtil {
	// lifelines are reused through references
	private Map<String, ModelElement> createdLifelines = new HashMap<>();
	// capture all occurence specs, a bug causes a loss of the required
	// covered attribute in some cases
	private Map<String, ModelElement> occurrenceSpecsWithCovered = new HashMap<>();

	public String createSequence(ModelElement modelElement) {
		// traverse through this package to access all sequence
		// elements
		StringJoiner sequence = new StringJoiner("");
		sequence.add(createLifelines(modelElement.getPlainAttribute("id"), modelElement));
		sequence.add(createMessages(modelElement.getPlainAttribute("id"), modelElement));
		return sequence.toString();
	}

	int count = 1;
	private ModelElement lastCovered;

	private String createMessages(String packageId, ModelElement modelElement) {
		MessageProcessorSQL.lastYDiff = 0F;
		StringJoiner messageOutput = new StringJoiner("");
		if (modelElement.getType().hasAttribute("messages")) {
			modelElement.getSetAttribute("messages").forEach(m -> {
				ModelElement message = (ModelElement) m;
				// find source
				String sourceId = message.getRefAttribute("sendevent").getRefAttribute("covered")
						.getPlainAttribute("id");
				ModelElement source = createdLifelines.get(sourceId);
				// find target
				ModelElement target = null;
				ModelElement receiver = message.getRefAttribute("receiveevent");
				ModelElement receiverCovered = receiver.getRefAttribute("covered");
				if (receiverCovered != null) {
					// TODO: Need to investigate why in some cases this is null
					// The example model being converted has this line so covered
					// should not be null
					//
					// <fragment xmi:type="uml:OccurrenceSpecification"
					// xmi:id="EAID_FR000001_DB9D_43bd_B384_4A62BB75BFA7"
					// covered="EAID_4DE20905_DB9D_43bd_B384_4A62BB75BFA7" />
					target = createdLifelines.get(receiverCovered.getPlainAttribute("id"));
				} else {
					// try the occurence specs captured
					ModelElement lifeline = occurrenceSpecsWithCovered.get(receiver.getPlainAttribute("id"));
					if(lifeline != null) {
						target = createdLifelines.get(lifeline.getPlainAttribute("id"));
					}
				}
				if (source != null && target != null) {
					MessageProcessorSQL messageProcessorSql = new MessageProcessorSQL(source.getPlainAttribute("id"),
							target.getPlainAttribute("id"), count, packageId);
					messageProcessorSql.setModelElement(message);
					messageProcessorSql.setKeyLetters("MSG_M");
					messageOutput.add(messageProcessorSql.getProcessorOutput());
					count += 1;
				} else {
					XMITranslate.logger
							.debug("WARNING: Found message without a target.  ID: " + message.getPlainAttribute("id"));
				}
			});
		}
		count = 1;
		return messageOutput.toString();
	}

	private String createLifelines(String packageId, ModelElement modelElement) {
		StringJoiner lifelines = new StringJoiner("");
		if (modelElement.getType().hasAttribute("lifelines")) {
			modelElement.getSetAttribute("lifelines").stream().forEach(l -> {
				ModelElement lifeLine = (ModelElement) l;
				if (!createdLifelines.containsKey(lifeLine.getPlainAttribute("id"))) {
					lifelines.add(createLifeSpan(packageId, null, lifeLine));
				}
			});
		}
		if (modelElement.getType().hasAttribute("fragments")) {
			// TODO: Investigate, this is likely not right
			// the data in the model points to
			// a covered value, and we do not get one here
			modelElement.getSetAttribute("fragments").stream().forEach(f -> {
				ModelElement fragment = (ModelElement) f;
				if (fragment.getType().getName().equals("occurrencespec")) {
					ModelElement covered = fragment.getRefAttribute("covered");
					if(covered != null) {
						lastCovered = covered;
					}
					handleCovered(lifelines, packageId, covered, fragment);
					if(lastCovered != null) {
						occurrenceSpecsWithCovered.put(fragment.getPlainAttribute("id"), lastCovered);
					}
				}
				if (fragment.getType().isSetAttribute("covered")) {
					fragment.getSetAttribute("covered").forEach(c -> {
						ModelElement covered = (ModelElement) c;
						if(covered != null) {
							lastCovered = covered;
						}
						handleCovered(lifelines, packageId, covered, covered);
						if(lastCovered != null) {
							occurrenceSpecsWithCovered.put(fragment.getPlainAttribute("id"), lastCovered);
						}
					});
					lifelines.add(createLifelines(packageId, fragment));
				}
			});
		}
		if (modelElement.getType().hasAttribute("operands")) {
			modelElement.getSetAttribute("operands").forEach(op -> {
				lifelines.add(createLifelines(packageId, (ModelElement) op));
			});
		}
		return lifelines.toString();
	}

	private void handleCovered(StringJoiner lifelines, String packageId, ModelElement covered, ModelElement fragment) {
		// if not covered, skip the fragment representing any already created lifespan
		if (covered != null && !createdLifelines.containsKey(covered.getPlainAttribute("id"))) {
			lifelines.add(createLifeSpan(packageId, covered, fragment));
		}
	}

	private String createLifeSpan(String packageId, ModelElement covered, ModelElement lifeLine) {
		LifespanProcessorSQL lifespanProcessorSQL = new LifespanProcessorSQL(packageId);
		lifespanProcessorSQL.setModelElement(lifeLine);
		lifespanProcessorSQL.setKeyLetters("SQ_LS");
		createdLifelines.put(covered == null ? lifeLine.getPlainAttribute("id") : covered.getPlainAttribute("id"),
				lifeLine);
		return lifespanProcessorSQL.getProcessorOutput();
	}
}
