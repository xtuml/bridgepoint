//=====================================================================
//
//File:      $RCSfile: ImportBPSqlHelper.java,v $
//Version:   $Revision: 1.15 $
//Modified:  $Date: 2013/05/10 16:17:57 $
//
//(c) Copyright 2005-2014 by Mentor Graphics Corp. All rights reserved.
//
//=====================================================================
// Licensed under the Apache License, Version 2.0 (the "License"); you may not 
// use this file except in compliance with the License.  You may obtain a copy 
// of the License at
//
//       http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software 
// distributed under the License is distributed on an "AS IS" BASIS, WITHOUT 
// WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.   See the 
// License for the specific language governing permissions and limitations under
// the License.
//=====================================================================

package com.mentor.nucleus.bp.io.sql;

import java.util.UUID;

import com.mentor.nucleus.bp.core.Association_c;
import com.mentor.nucleus.bp.core.ClassAsLink_c;
import com.mentor.nucleus.bp.core.ClassAsSubtype_c;
import com.mentor.nucleus.bp.core.ClassInAssociation_c;
import com.mentor.nucleus.bp.core.CoreDataType_c;
import com.mentor.nucleus.bp.core.CreationTransition_c;
import com.mentor.nucleus.bp.core.DataTypePackage_c;
import com.mentor.nucleus.bp.core.Domain_c;
import com.mentor.nucleus.bp.core.EnumerationDataType_c;
import com.mentor.nucleus.bp.core.ExternalEntityPackage_c;
import com.mentor.nucleus.bp.core.FunctionPackage_c;
import com.mentor.nucleus.bp.core.ImportedClass_c;
import com.mentor.nucleus.bp.core.LinkedAssociation_c;
import com.mentor.nucleus.bp.core.ModelClass_c;
import com.mentor.nucleus.bp.core.ReferringClassInAssoc_c;
import com.mentor.nucleus.bp.core.SimpleAssociation_c;
import com.mentor.nucleus.bp.core.SubtypeSupertypeAssociation_c;
import com.mentor.nucleus.bp.core.UserDataType_c;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.io.core.CoreImport;
import com.mentor.nucleus.bp.io.core.ImportHelper;
import com.mentor.nucleus.bp.ui.canvas.Connector_c;
import com.mentor.nucleus.bp.ui.canvas.Diagram_c;
import com.mentor.nucleus.bp.ui.canvas.Diagramelement_c;
import com.mentor.nucleus.bp.ui.canvas.ElementSpecification_c;
import com.mentor.nucleus.bp.ui.canvas.Graphconnector_c;
import com.mentor.nucleus.bp.ui.canvas.Graphedge_c;
import com.mentor.nucleus.bp.ui.canvas.Graphelement_c;
import com.mentor.nucleus.bp.ui.canvas.GraphicalElement_c;
import com.mentor.nucleus.bp.ui.canvas.Graphnode_c;
import com.mentor.nucleus.bp.ui.canvas.ModelSpecification_c;
import com.mentor.nucleus.bp.ui.canvas.Model_c;
import com.mentor.nucleus.bp.ui.canvas.Modeltype_c;
import com.mentor.nucleus.bp.ui.canvas.Ooaofgraphics;
import com.mentor.nucleus.bp.ui.canvas.Ooatype_c;
import com.mentor.nucleus.bp.ui.canvas.Shape_c;

/**
 * See superclass.  Holds Java-only methods (that were previously 
 * specified in gen_import_java.inc) that apply only to SQL imports. 
 */
public class ImportBPSqlHelper extends ImportHelper
{
    /**
     * Constructor.
     */
    public ImportBPSqlHelper(CoreImport importer) 
    {
        super(importer);
    }

    void fixUpGraphicalData()
    {
        ElementSpecification_c subtype_es = ElementSpecification_c.ElementSpecificationInstance(Ooaofgraphics
            .getDefaultInstance(), new ClassQueryInterface_c() {
            public boolean evaluate(Object candidate)
            {
                return (((ElementSpecification_c) candidate).getOoa_type() == Ooatype_c.Subtype);
            }
        });
        ElementSpecification_c link_es = ElementSpecification_c.ElementSpecificationInstance(Ooaofgraphics
            .getDefaultInstance(), new ClassQueryInterface_c() {
            public boolean evaluate(Object candidate)
            {
                return (((ElementSpecification_c) candidate).getOoa_type() == Ooatype_c.AssociativeLink);
            }
        });
        ElementSpecification_c supertype_es = ElementSpecification_c.ElementSpecificationInstance(Ooaofgraphics
            .getDefaultInstance(), new ClassQueryInterface_c() {
            public boolean evaluate(Object candidate)
            {
                return (((ElementSpecification_c) candidate).getOoa_type() == Ooatype_c.Supertype);
            }
        });
        ElementSpecification_c assoc_es = ElementSpecification_c.ElementSpecificationInstance(Ooaofgraphics
            .getDefaultInstance(), new ClassQueryInterface_c() {
            public boolean evaluate(Object candidate)
            {
                return (((ElementSpecification_c) candidate).getOoa_type() == Ooatype_c.Association);
            }
        });
        ElementSpecification_c creation_txn_es = ElementSpecification_c.ElementSpecificationInstance(Ooaofgraphics
            .getDefaultInstance(), new ClassQueryInterface_c() {
            public boolean evaluate(Object candidate)
            {
                return (((ElementSpecification_c) candidate).getOoa_type() == Ooatype_c.CreationTransition);
            }
        });
        GraphicalElement_c[] ges = GraphicalElement_c.GraphicalElementInstances(getGraphicsModelRoot());
        for (int i = 0; i < ges.length; i++) {
            if (ges[i].getOoa_typeCachedValue() == Ooatype_c.OOA_UNINITIALIZED_ENUM) {
                Connector_c con = Connector_c.getOneGD_CONOnR2(ges[i]);
                final GraphicalElement_c ge = GraphicalElement_c.getOneGD_GEOnR23(Graphelement_c
                    .getOneDIM_GEOnR311(Graphconnector_c.getOneDIM_CONOnR321(Graphedge_c.getOneDIM_EDOnR20(con))));
                if (ge != null) {
                    Association_c assoc = Association_c.AssociationInstance(getModelRoot(),
                        new ClassQueryInterface_c() {
                            public boolean evaluate(Object candidate)
                            {
                                return (((Association_c) candidate).getRel_id().equals(ge.getOoa_id()));
                            }
                        });
                    if (assoc != null) {
                        SubtypeSupertypeAssociation_c subsup = SubtypeSupertypeAssociation_c
                            .getOneR_SUBSUPOnR206(assoc);
                        LinkedAssociation_c la = LinkedAssociation_c.getOneR_ASSOCOnR206(assoc);
                        if (subsup != null && subtype_es != null) {
                            ges[i].relateAcrossR10To(subtype_es);
                            final GraphicalElement_c startElem = GraphicalElement_c.getOneGD_GEOnR23(Graphelement_c
                                .getOneDIM_GEOnR311(Graphconnector_c.getOneDIM_CONOnR320(Graphedge_c
                                    .getOneDIM_EDOnR20(con))));
                            if (startElem != null) {
                                ModelClass_c mc = null;
                                if (startElem.getOoa_typeCachedValue() == Ooatype_c.Class) {
                                    mc = ModelClass_c.ModelClassInstance(getModelRoot(), new ClassQueryInterface_c() {
                                        public boolean evaluate(Object candidate)
                                        {
                                            return (((ModelClass_c) candidate).getObj_id().equals(startElem.getOoa_id()));
                                        }
                                    });
                                }
                                else if (startElem.getOoa_typeCachedValue() == Ooatype_c.ImportedClass) {
                                    ImportedClass_c imp_class = ImportedClass_c.ImportedClassInstance(getModelRoot(),
                                        new ClassQueryInterface_c() {
                                            public boolean evaluate(Object candidate)
                                            {
                                                return (((ImportedClass_c) candidate).getIobj_id().equals(startElem
                                                    .getOoa_id()));
                                            }
                                        });
                                    mc = ModelClass_c.getOneO_OBJOnR101(imp_class);
                                }
                                if (mc != null) {
                                    ClassAsSubtype_c[] casFromRel = ClassAsSubtype_c.getManyR_SUBsOnR213(subsup);
                                    ClassAsSubtype_c[] casFromClass = ClassAsSubtype_c
                                        .getManyR_SUBsOnR205(ReferringClassInAssoc_c
                                            .getManyR_RGOsOnR203(ClassInAssociation_c.getManyR_OIRsOnR201(mc)));
                                    for (int j = 0; j < casFromClass.length; j++) {
                                        boolean matchFound = false;
                                        for (int k = 0; k < casFromRel.length; k++) {
                                            if (casFromRel[k] == casFromClass[j]) {
                                                ges[i].setOoa_id(casFromRel[k].Get_ooa_id());
                                                ges[i].setRepresents(casFromRel[k]);
                                                matchFound = true;
                                                break;
                                            }
                                        }
                                        if (matchFound) {
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                        else if (la != null && link_es != null) {
                            ges[i].relateAcrossR10To(link_es);
                            ClassAsLink_c cal = ClassAsLink_c.getOneR_ASSROnR211(la);
                            ges[i].setOoa_id(cal.Get_ooa_id());
                            ges[i].setRepresents(cal);
                        }
                    }
                }
            }
            else if (ges[i].getOoa_typeCachedValue() == Ooatype_c.Association) {
                final GraphicalElement_c assocGe = ges[i];
                Association_c assoc = Association_c.AssociationInstance(getModelRoot(), new ClassQueryInterface_c() {
                    public boolean evaluate(Object candidate)
                    {
                        return (((Association_c) candidate).getRel_id().equals(assocGe.getOoa_id()));
                    }
                });
                if (assoc != null) {
                    SubtypeSupertypeAssociation_c subsup = SubtypeSupertypeAssociation_c.getOneR_SUBSUPOnR206(assoc);
                    LinkedAssociation_c la = LinkedAssociation_c.getOneR_ASSOCOnR206(assoc);
                    SimpleAssociation_c sa = SimpleAssociation_c.getOneR_SIMPOnR206(assoc);
                    if (subsup != null && supertype_es != null) {
                        reRelateElementSpec(supertype_es, assocGe, assoc);
                    }
                    else if (la != null && assoc_es != null) {
                        reRelateElementSpec(assoc_es, assocGe, assoc);
                    }
                    else if (sa != null && assoc_es != null) {
                        reRelateElementSpec(assoc_es, assocGe, assoc);
                    }
                }
            }
            else if (ges[i].getOoa_typeCachedValue() == Ooatype_c.Transition) {
                final GraphicalElement_c txnGe = ges[i];
                CreationTransition_c crtxn = CreationTransition_c.CreationTransitionInstance(getModelRoot(),
                    new ClassQueryInterface_c() {
                        public boolean evaluate(Object candidate)
                        {
                            return (((CreationTransition_c) candidate).getTrans_id().equals(txnGe.getOoa_id()));
                        }
                    });
                if (crtxn != null) {
                    ges[i].relateAcrossR10To(creation_txn_es);
                }
            }
        }
        //
        // Create graphics for data types
        //
        final Domain_c dom = Domain_c.DomainInstance(getModelRoot());
        Model_c dpdCanvas = Model_c.ModelInstance(getGraphicsModelRoot(), new ClassQueryInterface_c() {
            public boolean evaluate(Object candidate)
            {
                Model_c selected = (Model_c) candidate;
                return selected.getModel_type() == Modeltype_c.DomainPackageDiagram
                    && selected.getOoa_id().equals(dom.getDom_id());
            }
        });
        if (dpdCanvas != null) {
            createDataTypePkgModel(dpdCanvas.getDiagramid());
            createFunctionPkgModel(dpdCanvas.getDiagramid());
            createEEPkgModel(dpdCanvas.getDiagramid());
            Ooaofgraphics.Fixbpgraphics(getGraphicsModelRoot());
        }
    } // end fixUpGraphicalData - BP 6.1

    private void reRelateElementSpec(ElementSpecification_c elemSpec, final GraphicalElement_c assocGe,
        Association_c assoc)
    {
        ElementSpecification_c old_es = ElementSpecification_c.getOneGD_ESOnR10(assocGe);
        if (old_es != null) {
            old_es.unrelateAcrossR10From(assocGe);
        }
        assocGe.relateAcrossR10To(elemSpec);
        assocGe.setRepresents(assoc);
    }

    private void createDataTypePkgModel(UUID dpdCanvasId)
    {
        DataTypePackage_c dPk = DataTypePackage_c.DataTypePackageInstance(getModelRoot());
        if (dPk != null) {
            Ooaofgraphics root = getGraphicsModelRoot();
            GraphicalElement_c pkgElem = new GraphicalElement_c(root);
            UUID newGEid = pkgElem.getElementid();
            pkgElem.delete();
            pkgElem = new GraphicalElement_c(root, newGEid, dpdCanvasId, dPk.Get_ooa_id(), Ooatype_c.DataTypePackage,
                dPk, "");
            pkgElem.batchRelate(root, false, true);
            Model_c pkgMdl = new Model_c(root);
            Diagram_c diagram = new Diagram_c(root);
            pkgMdl.relateAcrossR18To(diagram);
            pkgMdl.Initialize(dPk);
            ModelSpecification_c dt_ms = ModelSpecification_c.ModelSpecificationInstance(Ooaofgraphics
                .getDefaultInstance(), new ClassQueryInterface_c() {
                public boolean evaluate(Object candidate)
                {
                    return (((ModelSpecification_c) candidate).getModel_type() == Modeltype_c.DataTypePackageDiagram);
                }
            });
            pkgMdl.relateAcrossR9To(dt_ms);
            pkgMdl.batchRelate(root, false, true);
            CoreDataType_c[] cdts = CoreDataType_c.CoreDataTypeInstances(getModelRoot());
            for (int i = 0; i < cdts.length; i++) {
                GraphicalElement_c dtElem = new GraphicalElement_c(root);
                newGEid = dtElem.getElementid();
                dtElem.delete();
                dtElem = new GraphicalElement_c(root, newGEid, pkgMdl.getDiagramid(), cdts[i].Get_ooa_id(),
                    Ooatype_c.CoreDataType, cdts[i], "");
                dtElem.batchRelate(root, false, true);
            }
            UserDataType_c[] udts = UserDataType_c.UserDataTypeInstances(getModelRoot());
            for (int i = 0; i < udts.length; i++) {
                GraphicalElement_c dtElem = new GraphicalElement_c(root);
                newGEid = dtElem.getElementid();
                dtElem.delete();
                dtElem = new GraphicalElement_c(root, newGEid, pkgMdl.getDiagramid(), udts[i].Get_ooa_id(),
                    Ooatype_c.UserDataType, udts[i], "");
                dtElem.batchRelate(root, false, true);
            }
            EnumerationDataType_c[] edts = EnumerationDataType_c.EnumerationDataTypeInstances(getModelRoot());
            for (int i = 0; i < edts.length; i++) {
                GraphicalElement_c dtElem = new GraphicalElement_c(root);
                newGEid = dtElem.getElementid();
                dtElem.delete();
                dtElem = new GraphicalElement_c(root, newGEid, pkgMdl.getDiagramid(), edts[i].Get_ooa_id(),
                    Ooatype_c.EnumerationDataType, edts[i], "");
                dtElem.batchRelate(root, false, true);
            }
        }
    }

    private void createFunctionPkgModel(UUID dpdCanvasId)
    {
        FunctionPackage_c fPk = FunctionPackage_c.FunctionPackageInstance(getModelRoot());
        if (fPk != null) {
            Ooaofgraphics root = getGraphicsModelRoot();
            GraphicalElement_c pkgElem = new GraphicalElement_c(root);
            UUID newGEid = pkgElem.getElementid();
            pkgElem.delete();
            pkgElem = new GraphicalElement_c(root, newGEid, dpdCanvasId, fPk.Get_ooa_id(), Ooatype_c.FunctionPackage,
                fPk, "");
            pkgElem.batchRelate(root, false, true);
            Model_c pkgMdl = new Model_c(root);
            Diagram_c diagram = new Diagram_c(root);
            pkgMdl.relateAcrossR18To(diagram);
            pkgMdl.Initialize(fPk);
            ModelSpecification_c func_ms = ModelSpecification_c.ModelSpecificationInstance(Ooaofgraphics
                .getDefaultInstance(), new ClassQueryInterface_c() {
                public boolean evaluate(Object candidate)
                {
                    return (((ModelSpecification_c) candidate).getModel_type() == Modeltype_c.FunctionPackageDiagram);
                }
            });
            pkgMdl.relateAcrossR9To(func_ms);
            pkgMdl.batchRelate(root, false, true);
        }
    }

    private void createEEPkgModel(UUID dpdCanvasId)
    {
        ExternalEntityPackage_c ePk = ExternalEntityPackage_c.ExternalEntityPackageInstance(getModelRoot());
        if (ePk != null) {
            Ooaofgraphics root = getGraphicsModelRoot();
            Diagramelement_c pkgDiagramElem = new Diagramelement_c(root);
            Graphelement_c pkgGraphElem = new Graphelement_c(root);
            pkgGraphElem.relateAcrossR302To(pkgDiagramElem);
            Graphnode_c pkgGraphNode = new Graphnode_c(root);
            pkgGraphNode.relateAcrossR301To(pkgGraphElem);
            GraphicalElement_c pkgElem = new GraphicalElement_c(
                root, pkgDiagramElem.getElementid(), dpdCanvasId, ePk.Get_ooa_id(),
                Ooatype_c.ExternalEntityPackage, ePk, "");
            pkgGraphElem.relateAcrossR23To(pkgElem);
            Shape_c pkgShape = new Shape_c(root);
            pkgShape.relateAcrossR2To(pkgElem);
            pkgShape.relateAcrossR19To(pkgGraphNode);
            pkgElem.batchRelate(root, false, true);
            Model_c pkgMdl = new Model_c(root);
            Diagram_c diagram = new Diagram_c(root);
            pkgMdl.relateAcrossR18To(diagram);
            pkgMdl.Initialize(ePk);
            ModelSpecification_c ee_ms = ModelSpecification_c.ModelSpecificationInstance(Ooaofgraphics
                .getDefaultInstance(), new ClassQueryInterface_c() {
                public boolean evaluate(Object candidate)
                {
                    return (((ModelSpecification_c) candidate).getModel_type() == Modeltype_c.ExternalEntityPackageDiagram);
                }
            });
            pkgMdl.relateAcrossR9To(ee_ms);
            pkgMdl.batchRelate(root, false, true);
        }
    }
}
