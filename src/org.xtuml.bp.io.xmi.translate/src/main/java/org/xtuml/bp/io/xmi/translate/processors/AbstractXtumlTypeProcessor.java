package org.xtuml.bp.io.xmi.translate.processors;

import java.util.List;

import com.sdmetrics.model.ModelElement;

import org.xtuml.bp.io.xmi.translate.XMITranslator;

public abstract class AbstractXtumlTypeProcessor implements XtumlTypeProcessor {

    private String keyLetters;
    private ModelElement modelElement;

    @Override
    public String getKeyLetters() {
        return keyLetters;
    }

    @Override
    public void setKeyLetters(String keyLetters) {
        this.keyLetters = keyLetters;
    }

    @Override
    public ModelElement getModelElement() {
        return modelElement;
    }

    @Override
    public void setModelElement(ModelElement element) {
        this.modelElement = element;
    }

    public boolean handlesPackageableElement() {
        return false;
    };

    @Override
    public void preprocess(ModelElement element, String keyletters) {
        return;
    }

    @Override
    public void postprocess(ModelElement element, String keyletters) {
        return;
    }

    @Override
    public String process(ModelElement element, String keyLetters) {
        if (keyLetters.equals("PE_C")) {
            // ignore anything that does not have an owner
            if (element.getOwner() == null) {
                return "";
            }
        }
        setModelElement(element);
        setKeyLetters(keyLetters);
        if (ignoreTranslation() != IgnoreType.NOT_IGNORED) {
            if (ignoreTranslation() == IgnoreType.NOT_HANDLED) {
                XMITranslator.logSkipped("SKIPPING translation for: " + getKeyLetters());
            }
            return "";
        } else {
            preprocess(element, keyLetters);
            String result = getProcessorOutput();
            postprocess(element, keyLetters);
            return result;
        }
    }

    public abstract String getProcessorOutput();

    public abstract List<String> getValues(ModelElement modelElement);

    @Override
    public boolean forcePackageableElement() {
        return false;
    }

    @Override
    public boolean isGraphical() {
        return false;
    }

    @Override
    public IgnoreType ignoreTranslation() {
        return IgnoreType.NOT_IGNORED;
    }
}
