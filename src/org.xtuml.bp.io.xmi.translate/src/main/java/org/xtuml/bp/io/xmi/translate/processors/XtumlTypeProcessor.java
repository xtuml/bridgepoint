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

	void preprocess(ModelElement modelElement, String keyletters);

	void postprocess(ModelElement modelElement, String keyletters);

	String process(ModelElement modelElement, String keyletters);

	default String createSupportingElements() {
		return "";
	}

	boolean isGraphical();

	IgnoreType ignoreTranslation();

	boolean forcePackageableElement();

}
