package org.xtuml.bp.io.xmi.translate.processors.sql;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.sdmetrics.model.ModelElement;

import org.xtuml.bp.io.xmi.translate.processors.IdProcessor;
import org.xtuml.bp.io.xmi.translate.processors.generated.AbstractClassAsAssociatedOneSideProcessor;

public class ClassAsAssociatedOneSideProcessorSQL extends AbstractClassAsAssociatedOneSideProcessor {

    private String classId = IdProcessor.NULL_ID;
    private ModelElement oneSide;
    private String assocId;
    private ClassInAssociationProcessorSQL ciaProcessor;

    public ClassAsAssociatedOneSideProcessorSQL(ModelElement element, String keyLetters, String assocId) {
        setModelElement(element);
        setKeyLetters(keyLetters);
        this.assocId = assocId;
        initialize();
    }

    public String getClassId() {
        return classId;
    }

    public void initialize() {
        String lowerBound = getModelElement().getPlainAttribute("lowerBounds");
        String upperBound = getModelElement().getPlainAttribute("upperBounds");
        if (!lowerBound.equals("") || !upperBound.equals("")) {
            System.out.println();
        }
        Collection<?> ends = getModelElement().getSetAttribute("ownedends");
        oneSide = (ModelElement) ends.toArray()[0];
        ModelElement owningClass = oneSide.getRefAttribute("propertytype");
        classId = owningClass.getPlainAttribute("id");
        ciaProcessor = new ClassInAssociationProcessorSQL(classId, assocId);
        ciaProcessor.setKeyLetters("R_OIR");
    }

    @Override
    public String getObj_ID() {
        return SQLUtils.idValue(classId);
    }

    @Override
    public String getRel_ID() {
        return SQLUtils.idValue(assocId);
    }

    @Override
    public String getOIR_ID() {
        return SQLUtils.idValue(ciaProcessor.getId());
    }

    @Override
    public String getMult() {
        return SQLUtils.numberValue(0);
    }

    @Override
    public String getCond() {
        return SQLUtils.numberValue(0);
    }

    @Override
    public String getTxt_Phrs() {
        return SQLUtils.stringValue(oneSide.getName());
    }

    @Override
    public boolean handlesPackageableElement() {
        return true;
    }

    @Override
    public String createSupportingElements() {
        StringBuilder supporting = new StringBuilder();
        supporting.append(ciaProcessor.getProcessorOutput());
        ReferredToClassInAssocProcessorSQL referredToClassInAssocProcessorSQL = new ReferredToClassInAssocProcessorSQL(
                classId, assocId, ciaProcessor.getId());
        referredToClassInAssocProcessorSQL.setKeyLetters("R_RTO");
        supporting.append(referredToClassInAssocProcessorSQL.getProcessorOutput());
        return supporting.toString();
    }

    @Override
    public String getProcessorOutput() {
        return SQLUtils.getProcessorOutput(this);
    }

    @Override
    public List<String> getValues(ModelElement element) {
        return Stream.of(getObj_ID(), getRel_ID(), getOIR_ID(), getMult(), getCond(), getTxt_Phrs())
                .collect(Collectors.toList());
    }
}
