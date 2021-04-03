package org.xtuml.bp.io.xmi.translate.processors;

import java.util.List;

import com.sdmetrics.model.ModelElement;

public interface XtumlTypeProcessor {
	List<String> getValues(ModelElement modelElement);

	/* metamodel keyletters */
	String getKeyLetters();

	void setKeyLetters(String keyLetters);

	ModelElement getModelElement();

	void setModelElement(ModelElement element);

	String process(ModelElement modelElement, String keyletters);

	default String createSupportingElements() {
		return "";
	}

}
