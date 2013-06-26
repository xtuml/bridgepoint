package com.mentor.nucleus.bp.sequencecapture;

import org.eclipse.swt.widgets.Display;

import com.mentor.nucleus.bp.core.BlockInStackFrame_c;
import com.mentor.nucleus.bp.core.Block_c;
import com.mentor.nucleus.bp.core.Body_c;
import com.mentor.nucleus.bp.core.ClassInEngine_c;
import com.mentor.nucleus.bp.core.ComponentInstance_c;
import com.mentor.nucleus.bp.core.ComponentParticipant_c;
import com.mentor.nucleus.bp.core.ComponentReference_c;
import com.mentor.nucleus.bp.core.Component_c;
import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.ExecutableProperty_c;
import com.mentor.nucleus.bp.core.Instance_c;
import com.mentor.nucleus.bp.core.IntercomponentQueueEntry_c;
import com.mentor.nucleus.bp.core.Modeleventnotification_c;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.PendingEvent_c;
import com.mentor.nucleus.bp.core.ProvidedExecutableProperty_c;
import com.mentor.nucleus.bp.core.ProvidedOperationBody_c;
import com.mentor.nucleus.bp.core.ProvidedOperation_c;
import com.mentor.nucleus.bp.core.ProvidedSignalBody_c;
import com.mentor.nucleus.bp.core.ProvidedSignal_c;
import com.mentor.nucleus.bp.core.RequiredExecutableProperty_c;
import com.mentor.nucleus.bp.core.RequiredOperationBody_c;
import com.mentor.nucleus.bp.core.RequiredOperation_c;
import com.mentor.nucleus.bp.core.RequiredSignalBody_c;
import com.mentor.nucleus.bp.core.RequiredSignal_c;
import com.mentor.nucleus.bp.core.Sequence_c;
import com.mentor.nucleus.bp.core.StackFrame_c;
import com.mentor.nucleus.bp.core.Stack_c;
import com.mentor.nucleus.bp.core.StateMachineEvent_c;
import com.mentor.nucleus.bp.core.SystemModel_c;
import com.mentor.nucleus.bp.core.common.IModelChangeListener;
import com.mentor.nucleus.bp.core.common.IModelDelta;
import com.mentor.nucleus.bp.core.common.ModelChangedEvent;
import com.mentor.nucleus.bp.core.common.ModelElement;
import com.mentor.nucleus.bp.core.common.ModelRoot;
import com.mentor.nucleus.bp.core.common.RelationshipChangeModelDelta;
import com.mentor.nucleus.bp.core.common.Transaction;
import com.mentor.nucleus.bp.core.common.TransactionException;
import com.mentor.nucleus.bp.core.common.TransactionManager;
import com.mentor.nucleus.bp.sequencecapture.views.SequenceCapture;
import com.mentor.nucleus.bp.ui.canvas.Ooaofgraphics;

public class SequenceBuilderListener implements IModelChangeListener {
  private static SequenceBuilderListener instance = null;

  public static SequenceBuilderListener getCurrent() {
    if (instance == null) {
      instance = new SequenceBuilderListener();
    }
    return instance;
  }

  @Override
  public boolean isBatchedNotificationEnabled() {
    return false;
  }

  @Override
  public void modelChanged(ModelChangedEvent event) {
    // TODO Auto-generated method stub
    int foo = 1;
  }

  @Override
  public void modelElementAboutToBeDeleted(ModelChangedEvent event) {
    // TODO Auto-generated method stub

  }

  @Override
  public void modelElementAboutToBeReloaded(ModelChangedEvent event) {
    // TODO Auto-generated method stub

  }

  @Override
  public void modelElementAttributeChanged(ModelChangedEvent event,
      IModelDelta delta) {
    // TODO Auto-generated method stub

  }

  @Override
  public void modelElementCreated(ModelChangedEvent event, IModelDelta delta) {
    if (SequenceCapture.isCapturing()
        && delta.getModelElement() instanceof Sequence_c) {
      if (SequenceBuilder.getCurrentSequence() == null) {
        SequenceBuilder
            .setCurrentSequence((Sequence_c) delta.getModelElement());
      }
    }
    if (SequenceCapture.isCapturing()
        && delta.getModelElement() instanceof ComponentParticipant_c) {
      SequenceBuilder
          .setCurrentComponentParticipant((ComponentParticipant_c) delta
              .getModelElement());
    }
    if (delta.getModelElement() instanceof Sequence_c) {
      Sequence_c seq = (Sequence_c) delta.getModelElement();
      if (SequenceBuilder.getRootSequence() == null
          && seq.getName().equals("Captured Sequences")) {
        SequenceBuilder.setRootSequence(seq);
      }
    }
  }

  @Override
  public void modelElementDeleted(ModelChangedEvent event, IModelDelta delta) {
    if (delta.getKind() == Modeleventnotification_c.DELTA_DELETE) {
      if (delta.getModelElement().equals(SequenceBuilder.getCurrentSequence())) {
        SequenceBuilder.setCurrentSequence(null);
        SequenceCapture.getInstance().setActionToEnableCapture();
      }
    }

  }

  @Override
  public void modelElementLoaded(ModelChangedEvent event) {
    // TODO Auto-generated method stub

  }

  @Override
  public void modelElementRecreated(ModelChangedEvent event, IModelDelta delta) {
    // TODO Auto-generated method stub

  }

  @Override
  public void modelElementRelationChanged(ModelChangedEvent event,
      IModelDelta delta) {
    if (SequenceCapture.isCapturing()) {
      if (delta instanceof RelationshipChangeModelDelta) {
        String relationName = ((RelationshipChangeModelDelta) delta)
            .getRelationName();
        if (relationName.equals("2977")) {
          if (delta.getKind() == Modeleventnotification_c.DELTA_ELEMENT_UNRELATED) {
            ModelElement me = delta.getModelElement();
            if (me instanceof IntercomponentQueueEntry_c) {
              // Interface Operation or signal with Port OAL enqueued
              ComponentInstance_c srcCI = (ComponentInstance_c) ((RelationshipChangeModelDelta) delta)
                  .getDestinationModelElement();
              StackFrame_c sf = StackFrame_c
                  .getOneI_STFOnR2966((IntercomponentQueueEntry_c) me);
              Stack_c tgtStack = Stack_c
                  .getOneI_STACKOnR2966((IntercomponentQueueEntry_c) me);
              ComponentInstance_c tgtCI = ComponentInstance_c
                  .getOneI_EXEOnR2930(tgtStack);
              ExecutableProperty_c ep = getExecutablePropertyFor(sf);
              PortOALExecutionHandler poeh = new PortOALExecutionHandler(srcCI,
                  tgtCI, ep);
              Display.getDefault().asyncExec(poeh); // Update the diagram
            }
          }
        }
        if (relationName.equals("2931")) {
          ModelElement element = delta.getModelElement();
          if (delta.getKind() == Modeleventnotification_c.DELTA_ELEMENT_RELATED) {
            if (element instanceof PendingEvent_c) {
              // Signal assigned to state machine event
              PendingEvent_c pe = (PendingEvent_c) element;
              ComponentInstance_c srcCI = ComponentInstance_c
                  .getOneI_EXEOnR2976(pe);
              if (srcCI == null) { // sent from an instance ?
                Instance_c srcInstance = Instance_c.getOneI_INSOnR2935(pe);
                if (srcInstance != null) {
                  srcCI = ComponentInstance_c.getOneI_EXEOnR2957(srcInstance);
                }
                if (srcCI == null) { // sent from a class state machine ?
                  ClassInEngine_c classInCI = ClassInEngine_c
                      .getOneCSME_CIEOnR2931(pe);
                  if (classInCI != null) {
                    srcCI = ComponentInstance_c.getOneI_EXEOnR2960(classInCI);
                  }
                }
              }
              ComponentInstance_c tgtCI = ComponentInstance_c
                  .getOneI_EXEOnR2964(pe);
              Component_c srcComp = Component_c.getOneC_COnR2955(srcCI);
              Component_c tgtComp = Component_c.getOneC_COnR2955(tgtCI);
              if (srcComp == null) {
                srcComp = Component_c.getOneC_COnR4201(ComponentReference_c
                    .getOneCL_ICOnR2963(srcCI));
              }
              if (tgtComp == null) {
                tgtComp = Component_c.getOneC_COnR4201(ComponentReference_c
                    .getOneCL_ICOnR2963(tgtCI));
              }
              StateMachineEvent_c smEvt = StateMachineEvent_c.
                                                        getOneSM_EVTOnR2906(pe);
              SignalEventHandler seh = new SignalEventHandler(srcCI,
                                                                  tgtCI, smEvt);
              Display.getDefault().asyncExec(seh); // Update the diagram
            }
          } // Is being related
        }
      }
    } // end if isCapturing
  }

  @Override
  public void modelElementReloaded(ModelChangedEvent event) {
    // TODO Auto-generated method stub

  }

  @Override
  public void modelElementUnloaded(ModelChangedEvent event) {
    // TODO Auto-generated method stub

  }

  @Override
  public void modelEventReceived(ModelChangedEvent event) {
    // TODO Auto-generated method stub

  }

  @Override
  public void modelRootChanged(ModelChangedEvent event) {
    // TODO Auto-generated method stub

  }
  
  @Override
  public void systemAboutToBeDisabled(SystemModel_c system) {
  }

  @Override
  public boolean isSynchronous() {
    return true;
  }

  public boolean isMaskable() {
    return false;
  }

  private class SignalEventHandler implements Runnable {
    ComponentInstance_c srcCI;
    ComponentInstance_c tgtCI;
    StateMachineEvent_c smEvt;

    public SignalEventHandler(ComponentInstance_c srcCI,
        ComponentInstance_c tgtCI, StateMachineEvent_c smEvt) {
      this.srcCI = srcCI;
      this.tgtCI = tgtCI;
      this.smEvt = smEvt;
    }

    public void run() {
      TransactionManager tm = srcCI.getTransactionManager();
      Transaction tr = null;
      try {
        try {
          tr = tm.startTransaction("Sequence Builder",
              new ModelRoot[] { Ooaofooa.getDefaultInstance(),
                  Ooaofgraphics.getDefaultInstance() });
        } catch (TransactionException te) {
          CorePlugin.logError("Cannot start Sequence Builder Transaction", te);
        }
        SequenceBuilder.initializeCurrentSequenceFor(srcCI);
        ComponentParticipant_c srcCompPart = null;
        Component_c srcComp = getComponentFor(srcCI);
        if (srcComp != null) {
          srcCompPart = SequenceBuilder.getComponentParticipantFor(srcComp);
        }
        ComponentParticipant_c tgtCompPart = null;
        Component_c tgtComp = getComponentFor(tgtCI);
        if (tgtComp != null) {
          tgtCompPart = SequenceBuilder.getComponentParticipantFor(tgtComp);
        }
        if (srcComp != tgtComp) {
          SequenceBuilder.newMessage(smEvt, srcCompPart, tgtCompPart);
        }
      } finally {
        if (tr != null) {
          tm.endTransaction(tr);
        }
      }

    }
  }

  private class PortOALExecutionHandler implements Runnable {
    ComponentInstance_c srcCI;
    ComponentInstance_c tgtCI;
    ExecutableProperty_c exProp;

    public PortOALExecutionHandler(ComponentInstance_c srcCI,
        ComponentInstance_c tgtCI, ExecutableProperty_c ep) {
      this.srcCI = srcCI;
      this.tgtCI = tgtCI;
      this.exProp = ep;
    }

    public void run() {
      TransactionManager tm = srcCI.getTransactionManager();
      Transaction tr = null;
      try {
        try {
          tr = tm.startTransaction("Sequence Builder",
              new ModelRoot[] { Ooaofooa.getDefaultInstance(),
                  Ooaofgraphics.getDefaultInstance() });
        } catch (TransactionException te) {
          CorePlugin.logError("Cannot start Sequence Builder Transaction", te);
        }
        SequenceBuilder.initializeCurrentSequenceFor(srcCI);
        Component_c srcComp = getComponentFor(srcCI);
        ComponentParticipant_c srcCompPart = SequenceBuilder
            .getComponentParticipantFor(srcComp);
        Component_c tgtComp = getComponentFor(tgtCI);
        ComponentParticipant_c tgtCompPart = SequenceBuilder
            .getComponentParticipantFor(tgtComp);
        if (srcComp != tgtComp) {
          SequenceBuilder.newSynchronousMessage(exProp, srcCompPart,
              tgtCompPart);
        }
      } finally {
        if (tr != null) {
          tm.endTransaction(tr);
        }
      }
    }

  }

  private static ExecutableProperty_c getExecutablePropertyFor(StackFrame_c sf) {
    BlockInStackFrame_c bisf = BlockInStackFrame_c.getOneI_BSFOnR2923(sf);
    Block_c blk = Block_c.getOneACT_BLKOnR2923(bisf);
    Body_c bdy = Body_c.getOneACT_ACTOnR666(blk);
    ExecutableProperty_c ep = null;
    ProvidedOperationBody_c pob = ProvidedOperationBody_c
        .getOneACT_POBOnR698(bdy);
    if (pob != null) {
      ProvidedOperation_c po = ProvidedOperation_c.getOneSPR_POOnR687(pob);
      ProvidedExecutableProperty_c pep = ProvidedExecutableProperty_c
          .getOneSPR_PEPOnR4503(po);
      ep = ExecutableProperty_c.getOneC_EPOnR4501(pep);
    }
    RequiredOperationBody_c rob = RequiredOperationBody_c
        .getOneACT_ROBOnR698(bdy);
    if (rob != null) {
      RequiredOperation_c ro = RequiredOperation_c.getOneSPR_ROOnR685(rob);
      RequiredExecutableProperty_c rep = RequiredExecutableProperty_c
          .getOneSPR_REPOnR4502(ro);
      ep = ExecutableProperty_c.getOneC_EPOnR4500(rep);
    }
    ProvidedSignalBody_c psb = ProvidedSignalBody_c.getOneACT_PSBOnR698(bdy);
    if (psb != null) {
      ProvidedSignal_c ps = ProvidedSignal_c.getOneSPR_PSOnR686(psb);
      ProvidedExecutableProperty_c pep = ProvidedExecutableProperty_c
          .getOneSPR_PEPOnR4503(ps);
      ep = ExecutableProperty_c.getOneC_EPOnR4501(pep);
    }
    RequiredSignalBody_c rsb = RequiredSignalBody_c.getOneACT_RSBOnR698(bdy);
    if (rsb != null) {
      RequiredSignal_c rs = RequiredSignal_c.getOneSPR_RSOnR684(rsb);
      RequiredExecutableProperty_c rep = RequiredExecutableProperty_c
          .getOneSPR_REPOnR4502(rs);
      ep = ExecutableProperty_c.getOneC_EPOnR4500(rep);
    }
    return ep;
  }

  private Component_c getComponentFor(ComponentInstance_c ci) {
    Component_c comp = Component_c.getOneC_COnR2955(ci);
    if (comp == null) {
      comp = Component_c.getOneC_COnR4201(ComponentReference_c
          .getOneCL_ICOnR2963(ci));
    }
    return comp;
  }
}
