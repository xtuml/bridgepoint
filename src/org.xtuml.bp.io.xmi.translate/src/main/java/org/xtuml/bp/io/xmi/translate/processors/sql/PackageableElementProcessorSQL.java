package org.xtuml.bp.io.xmi.translate.processors.sql;

import java.util.List;

import com.sdmetrics.model.ModelElement;

import org.xtuml.bp.io.xmi.translate.processors.generated.AbstractPackageableElementProcessor;
import org.xtuml.bp.io.xmi.translate.processors.IdProcessor;

import ooaofooa.datatypes.Visibility;

public class PackageableElementProcessorSQL extends AbstractPackageableElementProcessor {

    private String id;

    public PackageableElementProcessorSQL() {

    }

    public PackageableElementProcessorSQL(String id) {
        this.id = id;
    }

    @Override
    public String getElement_ID() {
        if (this.id != null) {
            return SQLUtils.preprocessedIdValue(this.id);
        }
        return SQLUtils.idValue(getModelElement().getPlainAttribute("id"));
    }

    @Override
    public String getVisibility() {
        // TODO: figure out scope
        return SQLUtils.numberValue(Visibility.PUBLIC.getValue());
    }

    @Override
    public String getPackage_ID() {
        return getModelElement().getOwner() == null
                || !getModelElement().getOwner().getType().getName().equals("package")
                        ? SQLUtils.idValue(IdProcessor.NULL_ID)
                        : SQLUtils.idValue(getModelElement().getOwner().getPlainAttribute("id"));
    }

    @Override
    public String getComponent_ID() {
        return getModelElement().getOwner() == null
                || !getModelElement().getOwner().getType().getName().equals("component")
                        ? SQLUtils.idValue(IdProcessor.NULL_ID)
                        : SQLUtils.idValue(getModelElement().getOwner().getPlainAttribute("id"));
    }

    @Override
    public String gettype() {
        return SQLUtils.numberValue(getType(getModelElement().getType().getMapping()));
    }

    @Override
    public List<String> getValues(ModelElement modelElement) {
        return List.of(getElement_ID(), getVisibility(), getPackage_ID(), getComponent_ID(), gettype());
    }

    /**
     * Convert from BP enum values
     * 
     * public final static int FUNCTION = 1; public final static int COMPONENT = 2;
     * public final static int DATATYPE = 3; public final static int CLASS = 4;
     * public final static int EE = 5; public final static int INTERFACE = 6; public
     * final static int PACKAGE = 7; public final static int PORT = 8; public final
     * static int ASSOCIATION = 9; public final static int CONSTANT = 10; public
     * final static int ACTIVITY_PARTITION = 11; public final static int
     * ACTIVITY_EDGE = 12; public final static int TIMING_MARK = 13; public final
     * static int INTERACTION_PARTICIPANT = 14; public final static int TIME_SPAN =
     * 15; public final static int USE_CASE_ASSOCIATION = 16; public final static
     * int MESSAGE = 17; public final static int ACTIVITY_NODE = 18; public final
     * static int IMPORTED_CLASS = 19; public final static int INTERFACE_REFERENCE =
     * 20; public final static int COMPONENT_REFERENCE = 21; public final static int
     * SATISFACTION = 22; public final static int DELEGATION = 23; public final
     * static int EXCEPTION = 24; public final static int DEPL = 25;
     */
    private int getType(String mapping) {
        switch (mapping) {
        case "S_SYNC":
            return 1;
        case "C_C":
            return 2;
        case "S_DT":
            return 3;
        case "EP_PKG":
            return 7;
        case "O_OBJ":
            return 4;
        default:
            return 0;
        }
    }

    @Override
    public String getProcessorOutput() {
        return SQLUtils.getProcessorOutput(this);
    }
}
