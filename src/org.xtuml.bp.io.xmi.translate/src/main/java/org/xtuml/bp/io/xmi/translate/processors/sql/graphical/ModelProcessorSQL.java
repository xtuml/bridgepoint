package org.xtuml.bp.io.xmi.translate.processors.sql.graphical;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.sdmetrics.model.ModelElement;

import org.xtuml.bp.io.xmi.translate.processors.generated.AbstractModelProcessor;
import org.xtuml.bp.io.xmi.translate.processors.sql.SQLUtils;

public class ModelProcessorSQL extends AbstractModelProcessor {

    @Override
    public String createSupportingElements() {
        DiagramProcessorSQL diagramProcessor = new DiagramProcessorSQL(false);
        diagramProcessor.setModelElement(getModelElement());
        diagramProcessor.setKeyLetters("DIM_DIA");
        return SQLUtils.getInsertStatement(diagramProcessor, getModelElement());
    }

    @Override
    public String getdiagramId() {
        return SQLUtils.idValue(getModelElement().getPlainAttribute("id"));
    }

    @Override
    public String getModel_Type() {
        int typeMapping = 0;
        String type = getModelElement().getPlainAttribute("type");
        switch (type) {
            case "Logical":
            case "CompositeStructure":
            case "Use Case":
                typeMapping = 112;
                break;
            case "Statechart":
                typeMapping = 8;
                break;
            default:
                typeMapping = 0;
                break;
        }
        return SQLUtils.numberValue(typeMapping);
    }

    @Override
    public String getOOA_ID() {
        String representsAsParent = getModelElement().getPlainAttribute("parentAsRepresents");
        if (!representsAsParent.equals("")) {
            // Special case for component diagrams, they are not supported as such
            // in xtuml. It was decided that rather place the contents in a child package
            // of the component, we will create a package along side the component itself.
            // This choice was made as component children are encapsulated by the component
            // where it seems this not the desired behaivor of these diagrams
            if (getModelElement().getPlainAttribute("type").equals("CompositeStructure")) {
                // use the represents
                return SQLUtils.idValue(getModelElement().getPlainAttribute("represents"));
            }
            return SQLUtils.idValue(representsAsParent);
        }
        return SQLUtils.idValue(getModelElement().getPlainAttribute("represents"));
    }

    @Override
    public String getOOA_Type() {
        int typeMapping = 0;
        String type = getModelElement().getPlainAttribute("type");
        switch (type) {
            case "Logical":
            case "CompositeStructure":
            case "Use Case":
                typeMapping = 108;
                break;
            case "Statechart":
                typeMapping = 40;
                break;
            default:
                typeMapping = 0;
                break;
        }
        return SQLUtils.numberValue(typeMapping);
    }

    @Override
    public String getUseGlobalPrint() {
        return SQLUtils.booleanValue(false);
    }

    @Override
    public String getPrintMode() {
        return SQLUtils.booleanValue(false);
    }

    @Override
    public String getPrintRows() {
        return SQLUtils.numberValue(1);
    }

    @Override
    public String getPrintCols() {
        return SQLUtils.numberValue(1);
    }

    @Override
    public String getIsLandscape() {
        return SQLUtils.booleanValue(false);
    }

    @Override
    public String getZoomFontSize() {
        return SQLUtils.numberValue(12);
    }

    @Override
    public String getGridOn() {
        return SQLUtils.booleanValue(false);
    }

    @Override
    public String getSelRectX() {
        return SQLUtils.numberValue(0);
    }

    @Override
    public String getSelRectY() {
        return SQLUtils.numberValue(0);
    }

    @Override
    public String getSelRectW() {
        return SQLUtils.numberValue(0);
    }

    @Override
    public String getSelRectH() {
        return SQLUtils.numberValue(0);
    }

    @Override
    public String getrepresents() {
        return SQLUtils.numberValue(0);
    }

    @Override
    public String getversion() {
        return SQLUtils.stringValue("7.6");
    }

    @Override
    public String getrepresents_path() {
        return SQLUtils.stringValue("");
    }

    @Override
    public List<String> getValues(ModelElement element) {
        return Stream.of(getdiagramId(), getModel_Type(), getOOA_ID(), getOOA_Type(), getUseGlobalPrint(),
                getPrintMode(), getPrintRows(), getPrintCols(), getIsLandscape(), getZoomFontSize(), getGridOn(),
                getSelRectX(), getSelRectY(), getSelRectW(), getSelRectH(), getrepresents(), getversion(),
                getrepresents_path()).collect(Collectors.toList());
    }

    @Override
    public String getProcessorOutput() {
        return SQLUtils.getProcessorOutput(this);
    }

}
