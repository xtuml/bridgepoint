package com.mentor.nucleus.bp.sequencecapture;

import lib.TIM;

import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;

import com.mentor.nucleus.bp.core.AsynchronousMessage_c;
import com.mentor.nucleus.bp.core.ComponentParticipant_c;
import com.mentor.nucleus.bp.core.Component_c;
import com.mentor.nucleus.bp.core.End_c;
import com.mentor.nucleus.bp.core.EventSupplementalData_c;
import com.mentor.nucleus.bp.core.ExecutableProperty_c;
import com.mentor.nucleus.bp.core.Gd_c;
import com.mentor.nucleus.bp.core.InformalArgument_c;
import com.mentor.nucleus.bp.core.InformalAsynchronousMessage_c;
import com.mentor.nucleus.bp.core.InformalSynchronousMessage_c;
import com.mentor.nucleus.bp.core.InteractionParticipant_c;
import com.mentor.nucleus.bp.core.InterfaceOperation_c;
import com.mentor.nucleus.bp.core.InterfaceSignal_c;
import com.mentor.nucleus.bp.core.Lifespan_c;
import com.mentor.nucleus.bp.core.MessageArgument_c;
import com.mentor.nucleus.bp.core.Message_c;
import com.mentor.nucleus.bp.core.Modeleventnotification_c;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.PropertyParameter_c;
import com.mentor.nucleus.bp.core.Sequence_c;
import com.mentor.nucleus.bp.core.StateMachineEventDataItem_c;
import com.mentor.nucleus.bp.core.StateMachineEvent_c;
import com.mentor.nucleus.bp.core.SynchronousMessage_c;
import com.mentor.nucleus.bp.core.SystemModel_c;
import com.mentor.nucleus.bp.core.TimingMark_c;
import com.mentor.nucleus.bp.core.common.BaseModelDelta;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.core.common.InstanceList;
import com.mentor.nucleus.bp.core.common.ModelRoot;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.core.util.EditorUtil;
import com.mentor.nucleus.bp.sequencecapture.views.SequenceCapture;
import com.mentor.nucleus.bp.ui.canvas.AnchorOnSegment_c;
import com.mentor.nucleus.bp.ui.canvas.AutoReconciliationSpecification_c;
import com.mentor.nucleus.bp.ui.canvas.CanvasPlugin;
import com.mentor.nucleus.bp.ui.canvas.Connector_c;
import com.mentor.nucleus.bp.ui.canvas.Diagramelement_c;
import com.mentor.nucleus.bp.ui.canvas.ElementInModelSpecification_c;
import com.mentor.nucleus.bp.ui.canvas.ElementSpecification_c;
import com.mentor.nucleus.bp.ui.canvas.FloatingText_c;
import com.mentor.nucleus.bp.ui.canvas.Graphconnector_c;
import com.mentor.nucleus.bp.ui.canvas.Graphedge_c;
import com.mentor.nucleus.bp.ui.canvas.Graphelement_c;
import com.mentor.nucleus.bp.ui.canvas.GraphicalElement_c;
import com.mentor.nucleus.bp.ui.canvas.Graphnode_c;
import com.mentor.nucleus.bp.ui.canvas.LineSegment_c;
import com.mentor.nucleus.bp.ui.canvas.ModelSpecification_c;
import com.mentor.nucleus.bp.ui.canvas.Model_c;
import com.mentor.nucleus.bp.ui.canvas.Modeltype_c;
import com.mentor.nucleus.bp.ui.canvas.Ooaofgraphics;
import com.mentor.nucleus.bp.ui.canvas.Ooatype_c;
import com.mentor.nucleus.bp.ui.canvas.Waypoint_c;
import com.mentor.nucleus.bp.ui.graphics.editor.GraphicalEditor;
import com.mentor.nucleus.bp.ui.graphics.editor.GraphicalEditorInput;
import com.mentor.nucleus.bp.ui.graphics.editor.ModelEditor;

public class SequenceBuilder {
  private static Sequence_c rootSequence = null;
  private static Sequence_c currentSequence = null;
  private static ComponentParticipant_c currentComponentParticipant = null;
  private static int sequenceNumber = 1;
  private static IEditorPart f_edPart;
  private static long startTime = 0;

  public static ComponentParticipant_c getComponentParticipantFor(
      Component_c comp) {
    ComponentParticipant_c compPart = null;
    if (SequenceCapture.isCapturing() && comp != null) {
      if (getCurrentSequence() != null) {
        ComponentParticipant_c[] compParts = ComponentParticipant_c
            .getManySQ_COPsOnR930(InteractionParticipant_c
                .getManySQ_PsOnR929(getCurrentSequence()));
        for (int i = 0; i < compParts.length; i++) {
          Component_c formalComp = Component_c.getOneC_COnR955(compParts[i]);
          if (formalComp == comp) {
            compPart = compParts[i];
            break;
          }
        }
        if (compPart == null) { // Doesn't exist yet
          getCurrentSequence().Newcomponentparticipant(); // create it
          compPart = getCurrentComponentParticipant(); // collect it
          // compPart.Formalize(comp.getId()); // formalize it. This doesn't
          // work due to local root instance population issue locating the
          // component when the diagram being created is not in the same PMC
          // as the component itself.
          compPart.relateAcrossR955To(comp); // formalize this way instead
          getCurrentSequence().Newlifespan(compPart.getPart_id(), false,
              Gd_c.Null_unique_id(), false);
          Lifespan_c ls = Lifespan_c.getOneSQ_LSOnR940(InteractionParticipant_c
              .getOneSQ_POnR930(compPart));
          newLifespanGraphic(ls, compPart);
          if (f_edPart instanceof ModelEditor) {
            ((ModelEditor) f_edPart).getGraphicalEditor().zoomAll();
          }
        }
      }
    } else {
      // We did not successfully create a comp participant, don't allow
      // the old one to be used, or the output would be incorrect.
      setCurrentComponentParticipant(null);
    }
    return compPart;
  }

  public static void initializeCurrentSequenceFor(NonRootModelElement nrme) {
    if (SequenceCapture.isCapturing() && currentSequence == null) {
      Ooaofooa[] roots = Ooaofooa.getInstancesUnderSystem(nrme.getModelRoot());
      for (int rootCount = 0; rootCount < roots.length; rootCount++) {
        Sequence_c[] seqs = Sequence_c.SequenceInstances(roots[rootCount]);
        for (int i = 0; i < seqs.length; i++) {
          if (seqs[i].getName().equals("Captured Sequences")) {
            seqs[i].Newsequence();
            // TODO give it a name.
            break;
          } // / end we found the root sequence diagram
        } // end for all sequences
        if (currentSequence != null) {
          ModelSpecification_c modelSpec = ModelSpecification_c
              .ModelSpecificationInstance(Ooaofgraphics.getDefaultInstance(),
                  new ClassQueryInterface_c() {
                    @Override
                    public boolean evaluate(Object candidate) {
                      return ((ModelSpecification_c) candidate).getModel_type() == Modeltype_c.SequenceDiagram
                          && ((ModelSpecification_c) candidate).getOoa_type() == Ooatype_c.Sequence;
                    }
                  });
          if (modelSpec != null) {
            modelSpec.Elementcreated(currentSequence);
          }
          break;
        }
      } // end for all roots
      // If the link with view button is checked, open and focus the new diagram
      if (SequenceCapture.getInstance().getLinkWithView().isChecked()) {
        Object current = EditorUtil.getElementToEdit(currentSequence);

        try {
          FileEditorInput input = GraphicalEditorInput.createInstance(current);
          if (input != null) {
            f_edPart = PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                .getActivePage().openEditor(input,
                    "com.mentor.nucleus.bp.ui.graphics.editor.ModelEditor");
          }
        } catch (PartInitException e) {
          CanvasPlugin.logError("Failed to initialize Diagram Editor, reason:",
              e);
        }
      }
      startTime = TIM.getCurrentTime();
    } // end new sequence diagram required
    // Current sequence diagram should now be non null
    // (change captured by model element created above)
  }

  public static void setCurrentSequence(Sequence_c currentSequence) {
    SequenceBuilder.currentSequence = currentSequence;
  }

  public static Sequence_c getCurrentSequence() {
    return currentSequence;
  }

  public static void setCurrentComponentParticipant(
      ComponentParticipant_c currentComponentParticipant) {
    SequenceBuilder.currentComponentParticipant = currentComponentParticipant;
  }

  private static ComponentParticipant_c getCurrentComponentParticipant() {
    return currentComponentParticipant;
  }

  private static void newLifespanGraphic(final NonRootModelElement elem,
      final NonRootModelElement source) {
    if (currentSequence != null) {
      Ooaofgraphics modelRoot = Ooaofgraphics.getInstance(currentSequence
          .getModelRoot().getId());
      reconcileGraphics((Ooaofooa) currentSequence.getModelRoot(), modelRoot);
      InstanceList instanceList = modelRoot.getInstanceList(Model_c.class);
      // TODO, this is not very safe . . .
      Model_c v_canvas = (Model_c) instanceList.get(instanceList.size() - 1);
      ElementSpecification_c elemSpec = ElementSpecification_c
          .getOneGD_ESOnR11(ElementInModelSpecification_c
              .getManyGD_EMSsOnR11(ModelSpecification_c
                  .getOneGD_MSOnR9(v_canvas)), new ClassQueryInterface_c() {
            @Override
            public boolean evaluate(Object candidate) {
              return ((ElementSpecification_c) candidate).getOoa_type() == Ooatype_c.Lifeline;
            }
          });
      // find source graphical element
      GraphicalElement_c srcGe = getGraphicalElementFor(v_canvas, source);
      Graphelement_c srcGraphElement = Graphelement_c.getOneDIM_GEOnR23(srcGe);

      Graphnode_c srcGn = Graphnode_c.getOneDIM_NDOnR301(srcGraphElement);

      float startConnectorX = srcGraphElement.getPositionx()
          + (srcGn.getWidth() / 2);
      float startConnectorY = srcGraphElement.getPositiony()
          + srcGn.getHeight();
      float endConnectorX = startConnectorX;
      float endConnectorY = startConnectorY + (4 * srcGn.getHeight());
      createAndAnchorConnector(v_canvas, srcGraphElement, null, elemSpec, elem,
          startConnectorX, startConnectorY, endConnectorX, endConnectorY);
    }// end current sequence is not null
  } // End newConnector

  private static Graphelement_c createAndAnchorConnector(Model_c onCanvas,
      Graphelement_c srcGraphElement, Graphelement_c tgtGraphElement,
      ElementSpecification_c elemSpec, NonRootModelElement elem, float startX,
      float startY, float endX, float endY) {
    ModelRoot modelRoot = onCanvas.getModelRoot();
    GraphicalElement_c v_newConElem = new GraphicalElement_c(modelRoot);
    v_newConElem.relateAcrossR1To(onCanvas);
    Connector_c v_connector = new Connector_c(modelRoot);
    v_newConElem.relateAcrossR2To(v_connector);
    Graphedge_c v_graphEdge = new Graphedge_c(modelRoot);
    v_graphEdge.relateAcrossR20To(v_connector);
    // Initialize supporting connector instances
    Graphelement_c v_graphEle = new Graphelement_c(modelRoot);
    v_graphEle.relateAcrossR301To(v_graphEdge);
    v_graphEle.relateAcrossR23To(v_newConElem);
    Diagramelement_c v_diaEle = new Diagramelement_c(modelRoot);
    v_diaEle.relateAcrossR302To(v_graphEle);
    // create line segment
    LineSegment_c v_segment = new LineSegment_c(modelRoot);
    if (srcGraphElement != null) {
      // Connect to source element
      Graphconnector_c srcGc = new Graphconnector_c(modelRoot);
      srcGc.setPositionx(startX);
      srcGc.setPositiony(startY);
      srcGc.relateAcrossR311To(srcGraphElement);
      srcGc.relateAcrossR320To(v_graphEdge);
      LineSegment_c srcLs = LineSegment_c.getOneGD_LSOnR6(Connector_c
          .getOneGD_CONOnR2(GraphicalElement_c
              .getOneGD_GEOnR23(srcGraphElement)));
      if (srcLs != null) {
        AnchorOnSegment_c aos = new AnchorOnSegment_c(modelRoot);
        srcGc.relateAcrossR26To(aos);
        srcLs.relateAcrossR26To(aos);
      }
    }
    if (tgtGraphElement != null) {
      // Connect to target element
      Graphconnector_c srcGc = new Graphconnector_c(modelRoot);
      srcGc.setPositionx(endX);
      srcGc.setPositiony(endY);
      srcGc.relateAcrossR311To(tgtGraphElement);
      srcGc.relateAcrossR321To(v_graphEdge);
      LineSegment_c tgtLs = LineSegment_c.getOneGD_LSOnR6(Connector_c
          .getOneGD_CONOnR2(GraphicalElement_c
              .getOneGD_GEOnR23(tgtGraphElement)));
      if (tgtLs != null) {
        AnchorOnSegment_c aos = new AnchorOnSegment_c(modelRoot);
        srcGc.relateAcrossR26To(aos);
        tgtLs.relateAcrossR26To(aos);
      }
    }
    // start point
    Waypoint_c v_startPoint = new Waypoint_c(modelRoot);
    v_startPoint.setPositionx(startX);
    v_startPoint.setPositiony(startY);
    v_startPoint.relateAcrossR21To(v_segment);
    v_startPoint.relateAcrossR319To(v_graphEdge);
    // end point
    Waypoint_c v_endPoint = new Waypoint_c(modelRoot);
    v_endPoint.setPositionx(endX);
    v_endPoint.setPositiony(endY);
    v_endPoint.relateAcrossR22To(v_segment);
    v_endPoint.relateAcrossR319To(v_graphEdge);
    v_endPoint.relateAcrossR324ToFollows(v_startPoint);
    // hook up the segment to the connector
    v_connector.relateAcrossR6To(v_segment);
    // create associated text positions
    FloatingText_c v_text = new FloatingText_c(modelRoot);
    v_text.setEnd(End_c.Start);
    v_connector.relateAcrossR8To(v_text);
    v_text.Createsupertypeinstances();
    v_text = new FloatingText_c(modelRoot);
    v_text.setEnd(End_c.Middle);
    v_connector.relateAcrossR8To(v_text);
    v_text.Createsupertypeinstances();
    v_text = new FloatingText_c(modelRoot);
    v_text.setEnd(End_c.End);
    v_connector.relateAcrossR8To(v_text);
    v_text.Createsupertypeinstances();
    // Hook up the correct element specification type
    ElementSpecification_c v_newSpec = elemSpec;
    v_newConElem.relateAcrossR10To(v_newSpec);
    v_newConElem.setRepresents(elem);
    v_newConElem.setOoa_id(elem.Get_ooa_id());

    return v_graphEle;
  }

  private static GraphicalElement_c getGraphicalElementFor(final Model_c on,
      final Object modelElement) {
    return GraphicalElement_c.getOneGD_GEOnR1(on, new ClassQueryInterface_c() {
      @Override
      public boolean evaluate(Object candidate) {
        Object rep = ((GraphicalElement_c) candidate).getRepresents();
        return rep != null && rep.equals(modelElement);
      }
    });
  }

  private static void reconcileGraphics(Ooaofooa modelRoot,
      Ooaofgraphics graphicsRoot) {
    // use the reconciler to create all graphical nodes . . .
    ModelSpecification_c modelSpec = ModelSpecification_c
        .ModelSpecificationInstance(Ooaofgraphics.getDefaultInstance(),
            new ClassQueryInterface_c() {
              @Override
              public boolean evaluate(Object candidate) {
                return ((ModelSpecification_c) candidate).getModel_type() == Modeltype_c.SequenceDiagram
                    && ((ModelSpecification_c) candidate).getOoa_type() == Ooatype_c.Sequence;
              }
            });
    AutoReconciliationSpecification_c reconciler = AutoReconciliationSpecification_c
        .getOneGD_ARSOnR33(modelSpec, new ClassQueryInterface_c() {
          @Override
          public boolean evaluate(Object candidate) {
            return ((AutoReconciliationSpecification_c) candidate)
                .getCountmethod().equals("Getcomponentcount");
          }
        });
    SystemModel_c system = ((Ooaofooa) modelRoot).getRoot();
    reconciler.Updateallelements(false, system.getSys_id());
    reconciler = AutoReconciliationSpecification_c.getOneGD_ARSOnR33(modelSpec,
        new ClassQueryInterface_c() {
          @Override
          public boolean evaluate(Object candidate) {
            return ((AutoReconciliationSpecification_c) candidate)
                .getCountmethod().equals("Getsequencecount");
          }
        });
    reconciler.Updateallelements(false, system.getSys_id());
  }

  public static void newMessage(StateMachineEvent_c smEvt,
      ComponentParticipant_c srcCompPart, ComponentParticipant_c tgtCompPart) {
    if (SequenceCapture.isCapturing() && currentSequence != null) {
      // initialize message instance
      Message_c newMsg = new Message_c(srcCompPart.getModelRoot());
      AsynchronousMessage_c asMsg = new AsynchronousMessage_c(srcCompPart
          .getModelRoot());
      asMsg.relateAcrossR1018To(newMsg);
      // Not currently possible to formalize to an intercomponent message
      InformalAsynchronousMessage_c iasMsg = new InformalAsynchronousMessage_c(
          srcCompPart.getModelRoot());
      iasMsg.relateAcrossR1019To(asMsg);
      InteractionParticipant_c srcIap = InteractionParticipant_c
          .getOneSQ_POnR930(srcCompPart);
      newMsg.relateAcrossR1008To(srcIap);
      InteractionParticipant_c tgtIap = InteractionParticipant_c
          .getOneSQ_POnR930(tgtCompPart);
      newMsg.relateAcrossR1007To(tgtIap);
      asMsg.setInformalname(smEvt.Get_name());
      asMsg.setDescrip(smEvt.getDescrip());
      // TODO initialize sequence number to something decent
      asMsg.setSequencenumb(Integer.toString(sequenceNumber++));
      // add arguments
      StateMachineEventDataItem_c[] smEvtDis = StateMachineEventDataItem_c
          .getManySM_EVTDIsOnR522(EventSupplementalData_c
              .getOneSM_SUPDTOnR520(smEvt));
      for (int i = 0; i < smEvtDis.length; i++) {
        MessageArgument_c ma = new MessageArgument_c(asMsg.getModelRoot());
        ma.relateAcrossR1000To(newMsg);
        InformalArgument_c ia = new InformalArgument_c(asMsg.getModelRoot());
        ia.relateAcrossR1013To(ma);
        if (smEvtDis[i] != null) {
          ma.setInformalname(smEvtDis[i].getName());
        }
        // TODO setup runtime values for addition to diagram
      }
      Graphelement_c newCon = newMessageGraphic(newMsg, srcCompPart,
          tgtCompPart);
      newTimingMark(newCon);
    }
  }

  private static void newTimingMark(Graphelement_c newCon) {
    if (SequenceCapture.getInstance().getCreateTiming().isChecked()) {
      long time = TIM.getCurrentTime();
      Graphedge_c gEdge = Graphedge_c.getOneDIM_EDOnR301(newCon);
      Graphconnector_c srcGCon = Graphconnector_c.getOneDIM_CONOnR320(gEdge);
      Graphconnector_c tgtGCon = Graphconnector_c.getOneDIM_CONOnR321(gEdge);
      boolean rightGoing = srcGCon.getPositionx() < tgtGCon.getPositionx();
      // Obtain the life span to hook up to
      Graphelement_c gGElem = Graphelement_c.getOneDIM_GEOnR311(tgtGCon);
      GraphicalElement_c gElem = GraphicalElement_c.getOneGD_GEOnR23(gGElem);
      if (gElem.getRepresents() instanceof Lifespan_c) {
        Lifespan_c ls = (Lifespan_c) gElem.getRepresents();
        Sequence_c seq = Sequence_c.getOneSQ_SOnR929(InteractionParticipant_c
            .getOneSQ_POnR930(ls));
        TimingMark_c mark = new TimingMark_c(seq.getModelRoot());
        mark.relateAcrossR931To(ls);
        ElementSpecification_c elemSpec = ElementSpecification_c
            .ElementSpecificationInstance(Ooaofgraphics.getDefaultInstance(),
                new ClassQueryInterface_c() {
                  @Override
                  public boolean evaluate(Object candidate) {
                    return ((ElementSpecification_c) candidate).getOoa_type() == Ooatype_c.TimingMark;
                  }
                });
        float startX = tgtGCon.getPositionx();
        float startY = tgtGCon.getPositiony();
        float endY = tgtGCon.getPositiony();
        float endX = tgtGCon.getPositionx() + XIncrement;
        if (!rightGoing) {
          endX = tgtGCon.getPositionx() - XIncrement;
        }
        Model_c onCanvas = Model_c.getOneGD_MDOnR1(gElem);
        mark.setName("T + " + Long.toString((time - startTime) / 1000) + " μS");
        createAndAnchorConnector(onCanvas, gGElem, null, elemSpec, mark,
            startX, startY, endX, endY);
      }
    }
  }

  final static float YIncrement = 40.0f;
  final static float XIncrement = 40.0f;

  private static Graphelement_c newMessageGraphic(
      final NonRootModelElement elem, final NonRootModelElement source,
      final NonRootModelElement target) {
    Ooaofgraphics modelRoot = Ooaofgraphics.getInstance(currentSequence
        .getModelRoot().getId());
    // find all lifelines on the current diagram
    InstanceList instanceList = modelRoot.getInstanceList(Model_c.class);
    // TODO, this is not very safe . . .
    Model_c v_canvas = (Model_c) instanceList.get(instanceList.size() - 1);
    GraphicalElement_c[] llGes = GraphicalElement_c.getManyGD_GEsOnR1(v_canvas,
        new ClassQueryInterface_c() {

          @Override
          public boolean evaluate(Object candidate) {
            return ((GraphicalElement_c) candidate).getOoa_type() == Ooatype_c.Lifeline;
          }
        });
    // find all anchors on all lines
    Graphconnector_c[] gcons = Graphconnector_c
        .getManyDIM_CONsOnR311(Graphelement_c.getManyDIM_GEsOnR23(llGes));
    // find the anchor with the greatest Y Position
    float greatestY = 0.0f;
    // First get a reasonable starting value in case there were no connectors
    Waypoint_c[] llStartWps = Waypoint_c.getManyDIM_WAYsOnR319(Graphedge_c
        .getManyDIM_EDsOnR301(Graphelement_c.getManyDIM_GEsOnR23(llGes)));
    for (int i = 0; i < llStartWps.length; i++) {
      // if it is the start waypoint . . .
      if (Waypoint_c.getOneDIM_WAYOnR324Precedes(llStartWps[i]) != null) {
        if (llStartWps[i].getPositiony() > greatestY) {
          greatestY = llStartWps[i].getPositiony();
        }
      }
    }
    // Now lets iterate over the connectors to see if messages already exist
    for (int i = 0; i < gcons.length; i++) {
      if (gcons[i].getPositiony() > greatestY) {
        greatestY = gcons[i].getPositiony();
      }
    }
    // newSrcY = greatest Y position + half the symbol height
    float newSrcY = greatestY + YIncrement;
    // newTgtY = newSrcY + half the symbol height
    float newTgtY = newSrcY + YIncrement;
    boolean lifelinesExtended = false;
    // if lifeline end point y <= newDestY extend lines
    Graphelement_c[] llGraphElems = Graphelement_c.getManyDIM_GEsOnR23(llGes);
    for (int i = 0; i < llGraphElems.length; i++) {
      Waypoint_c[] wps = Waypoint_c.getManyDIM_WAYsOnR319(Graphedge_c
          .getOneDIM_EDOnR301(llGraphElems[i]));
      for (int j = 0; j < wps.length; j++) {
        // if we don't find a following waypoint it is the end
        if (Waypoint_c.getOneDIM_WAYOnR324Precedes(wps[j]) == null) {
          if (wps[j].getPositiony() < newTgtY) {
            lifelinesExtended = true;
            break;
          }
        }
      }
      if (lifelinesExtended) {
        break;
      }
    }
    if (lifelinesExtended) {
      for (int i = 0; i < llGraphElems.length; i++) {
        Waypoint_c[] wps = Waypoint_c.getManyDIM_WAYsOnR319(Graphedge_c
            .getOneDIM_EDOnR301(llGraphElems[i]));
        for (int j = 0; j < wps.length; j++) {
          // if we don't find a following waypoint it is the end
          if (Waypoint_c.getOneDIM_WAYOnR324Precedes(wps[j]) == null) {
            if (wps[j].getPositiony() < newTgtY) {
              wps[j].setPositiony(newTgtY + YIncrement);
            }
          }
        }
      }
    }
    // find lifespan for srcPart
    Lifespan_c srcLs = Lifespan_c.getOneSQ_LSOnR940(InteractionParticipant_c
        .getOneSQ_POnR930((ComponentParticipant_c) source));
    GraphicalElement_c srcLLge = null;
    for (int i = 0; i < llGes.length; i++) {
      if (llGes[i].getRepresents().equals(srcLs)) {
        srcLLge = llGes[i];
        break;
      }
    }
    // find lifespan for destPart
    Lifespan_c tgtLs = Lifespan_c.getOneSQ_LSOnR940(InteractionParticipant_c
        .getOneSQ_POnR930((ComponentParticipant_c) target));
    GraphicalElement_c tgtLLge = null;
    for (int i = 0; i < llGes.length; i++) {
      if (llGes[i].getRepresents().equals(tgtLs)) {
        tgtLLge = llGes[i];
        break;
      }
    }
    Graphelement_c newGraphElem = null;
    if (srcLLge != null && tgtLLge != null) {
      Waypoint_c srcWp = Waypoint_c.getOneDIM_WAYOnR319(Graphedge_c
          .getOneDIM_EDOnR301(Graphelement_c.getOneDIM_GEOnR23(srcLLge)));
      // newsrcX = this lifeline endPointX
      float newSrcX = srcWp.getPositionx();
      Waypoint_c tgtWp = Waypoint_c.getOneDIM_WAYOnR319(Graphedge_c
          .getOneDIM_EDOnR301(Graphelement_c.getOneDIM_GEOnR23(tgtLLge)));
      // newTgtX = this lifeline endPointX
      float newTgtX = tgtWp.getPositionx();
      // create and anchor connector
      Graphelement_c srcGraphElem = Graphelement_c.getOneDIM_GEOnR23(srcLLge);
      Graphelement_c tgtGraphElem = Graphelement_c.getOneDIM_GEOnR23(tgtLLge);
      AsynchronousMessage_c asm = AsynchronousMessage_c
          .getOneMSG_AMOnR1018((Message_c) elem);
      if (asm != null) {
        ElementSpecification_c msgSpec = ElementSpecification_c
            .getOneGD_ESOnR11(ElementInModelSpecification_c
                .getManyGD_EMSsOnR11(ModelSpecification_c
                    .getOneGD_MSOnR9(v_canvas)), new ClassQueryInterface_c() {
              @Override
              public boolean evaluate(Object candidate) {
                return ((ElementSpecification_c) candidate).getOoa_type() == Ooatype_c.AsynchronousMessage;
              }
            });
        newGraphElem = createAndAnchorConnector(v_canvas, srcGraphElem,
            tgtGraphElem, msgSpec, asm, newSrcX, newSrcY, newTgtX, newTgtY);
      } else {
        ElementSpecification_c msgSpec = ElementSpecification_c
            .getOneGD_ESOnR11(ElementInModelSpecification_c
                .getManyGD_EMSsOnR11(ModelSpecification_c
                    .getOneGD_MSOnR9(v_canvas)), new ClassQueryInterface_c() {
              @Override
              public boolean evaluate(Object candidate) {
                return ((ElementSpecification_c) candidate).getOoa_type() == Ooatype_c.SynchronousMessage;
              }
            });
        SynchronousMessage_c sm = SynchronousMessage_c
            .getOneMSG_SMOnR1018((Message_c) elem);
        newGraphElem = createAndAnchorConnector(v_canvas, srcGraphElem,
            tgtGraphElem, msgSpec, sm, newSrcX, newSrcY, newTgtX, newTgtY);
      }
    }
    // if lifelines were extended, scroll the diagram
    if (lifelinesExtended) {
      if (f_edPart instanceof ModelEditor) {
        GraphicalEditor ge = ((ModelEditor) f_edPart).getGraphicalEditor();
        Point viewportLocation = ((FigureCanvas) ge.getCanvas()).getViewport()
            .getViewLocation();
        viewportLocation.y = viewportLocation.y + (int) YIncrement;
        ((FigureCanvas) ge.getCanvas()).getViewport().setViewLocation(
            viewportLocation);
        ge.storeViewportLocation();
      }
    }
    return newGraphElem;
  }

  public static void newSynchronousMessage(ExecutableProperty_c ep,
      ComponentParticipant_c srcCompPart, ComponentParticipant_c tgtCompPart) {
    if (SequenceCapture.isCapturing() && currentSequence != null && ep != null) {
      InterfaceSignal_c is = InterfaceSignal_c.getOneC_ASOnR4004(ep);
      InterfaceOperation_c io = InterfaceOperation_c.getOneC_IOOnR4004(ep);
      // initialize message instance
      Message_c newMsg = new Message_c(ep.getModelRoot());
      if (is != null) {
        AsynchronousMessage_c asMsg = new AsynchronousMessage_c(ep
            .getModelRoot());
        asMsg.relateAcrossR1018To(newMsg);
        // Not currently possible to formalize to an intercomponent message
        InformalAsynchronousMessage_c iasMsg = new InformalAsynchronousMessage_c(
            ep.getModelRoot());
        iasMsg.relateAcrossR1019To(asMsg);
        InteractionParticipant_c srcIap = InteractionParticipant_c
            .getOneSQ_POnR930(srcCompPart);
        newMsg.relateAcrossR1008To(srcIap);
        InteractionParticipant_c tgtIap = InteractionParticipant_c
            .getOneSQ_POnR930(tgtCompPart);
        newMsg.relateAcrossR1007To(tgtIap);
        asMsg.setInformalname(is.getName());
        asMsg.setDescrip(is.getDescrip());
        // TODO initialize sequence number to something decent
        asMsg.setSequencenumb(Integer.toString(sequenceNumber++));
        // add arguments
        PropertyParameter_c[] pps = PropertyParameter_c.getManyC_PPsOnR4006(ep);
        for (int i = 0; i < pps.length; i++) {
          MessageArgument_c ma = new MessageArgument_c(asMsg.getModelRoot());
          ma.relateAcrossR1000To(newMsg);
          InformalArgument_c ia = new InformalArgument_c(asMsg.getModelRoot());
          ia.relateAcrossR1013To(ma);
          // Set up values
          ma.setInformalname(pps[i].getName());
          // TODO extract value
          /*
           * RuntimeValue_c rv = RuntimeValue_c.getOneRV_RVLOnR3303(pps[i]); if
           * (rv != null) { ma.setValue(rv.getLabel()); }
           */
        }
      } else { // interface operation is not empty
        SynchronousMessage_c sMsg = new SynchronousMessage_c(ep.getModelRoot());
        sMsg.relateAcrossR1018To(newMsg);
        // Not currently possible to formalize to an intercomponent message
        InformalSynchronousMessage_c isMsg = new InformalSynchronousMessage_c(
            ep.getModelRoot());
        isMsg.relateAcrossR1020To(sMsg);
        InteractionParticipant_c srcIap = InteractionParticipant_c
            .getOneSQ_POnR930(srcCompPart);
        newMsg.relateAcrossR1008To(srcIap);
        InteractionParticipant_c tgtIap = InteractionParticipant_c
            .getOneSQ_POnR930(tgtCompPart);
        newMsg.relateAcrossR1007To(tgtIap);
        sMsg.setInformalname(io.getName());
        sMsg.setDescrip(io.getDescrip());
        // TODO initialize sequence number to something decent
        sMsg.setSequencenumb(Integer.toString(sequenceNumber++));
        // add arguments
        PropertyParameter_c[] pps = PropertyParameter_c.getManyC_PPsOnR4006(ep);
        for (int i = 0; i < pps.length; i++) {
          MessageArgument_c ma = new MessageArgument_c(sMsg.getModelRoot());
          ma.relateAcrossR1000To(newMsg);
          InformalArgument_c ia = new InformalArgument_c(sMsg.getModelRoot());
          ia.relateAcrossR1013To(ma);
          // Set up values
          ma.setInformalname(pps[i].getName());
          // TODO extract value
          /*
           * RuntimeValue_c rv = RuntimeValue_c.getOneRV_RVLOnR3303(pps[i]); if
           * (rv != null) { ma.setValue(rv.getLabel()); }
           */
        }
      }
      Graphelement_c newCon = newMessageGraphic(newMsg, srcCompPart,
          tgtCompPart);
      newTimingMark(newCon);
    }
  }

  public static void setRootSequence(Sequence_c rootSequence) {
    SequenceBuilder.rootSequence = rootSequence;
  }

  public static Sequence_c getRootSequence() {
    return rootSequence;
  }
}
