package org.xtuml.bp.ui.text.masl;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

//====================================================================
//
// File:      ShowMASLAction.java
//
//====================================================================
//
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IPartListener2;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.xtuml.bp.core.ActionHome_c;
import org.xtuml.bp.core.Action_c;
import org.xtuml.bp.core.Actiondialect_c;
import org.xtuml.bp.core.Attribute_c;
import org.xtuml.bp.core.BaseAttribute_c;
import org.xtuml.bp.core.DerivedBaseAttribute_c;
import org.xtuml.bp.core.MooreActionHome_c;
import org.xtuml.bp.core.StateMachineState_c;
import org.xtuml.bp.core.TransitionActionHome_c;
import org.xtuml.bp.core.Transition_c;
import org.xtuml.bp.core.common.NonRootModelElement;
import org.xtuml.bp.ui.text.TextPlugin;

public class ShowMASLAction implements IActionDelegate
{
  IStructuredSelection currentSelection;
  /* (non-Javadoc)
   * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
   */
  public void run(IAction action)
  {
    Object current = currentSelection.iterator().next();
    if (current != null)
    {
      try
      {
        MASLEditorInputFactory factory = (MASLEditorInputFactory)PlatformUI.getWorkbench().getElementFactory(MASLEditorInput.FACTORY_ID);
      	IEditorInput input = factory.createInstance(current);
        IWorkbenchPage page = (IWorkbenchPage) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
        page.addPartListener( (IPartListener2)new MASLPartListener());
        IEditorPart editor = page.openEditor(input, MASLEditorInput.EDITOR_ID);
      }
      catch (CoreException e)
      {
		TextPlugin.logError("Could not activate MASL Action Editor", e); //$NON-NLS-1$
      }
    }
  }
  /* (non-Javadoc)
   * @see org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action.IAction, org.eclipse.jface.viewers.ISelection)
   */
  public void selectionChanged(IAction action, ISelection selection)
  {
    currentSelection = (IStructuredSelection) selection;
    if ( currentSelection.size() == 1 ) {
        Object element = currentSelection.getFirstElement();
        action.setEnabled( editorEnabled((NonRootModelElement)element) );
    }
  }

   private static boolean editorEnabled( NonRootModelElement element ) {
    	MASLEditorInputFactory factory = MASLEditorInputFactory.getDefaultInstance();
        if ( factory.isSupported( element ) ) {
            int dialect = -1;
            NonRootModelElement dialectObj = element;
            if (dialectObj instanceof StateMachineState_c) {
                StateMachineState_c state = (StateMachineState_c) dialectObj;
                Action_c action = Action_c.getOneSM_ACTOnR514(ActionHome_c
                        .getOneSM_AHOnR513((MooreActionHome_c.getOneSM_MOAHOnR511(state))));
                if (action != null) {
                    dialectObj = action;
                }
            }
			else if (dialectObj instanceof Transition_c) {
				Action_c action = Action_c.getOneSM_ACTOnR514(ActionHome_c.getOneSM_AHOnR513(
						TransitionActionHome_c.getOneSM_TAHOnR530((Transition_c)dialectObj)));
				if (action != null) {
					dialectObj = action;
				}
			}
			else if ( dialectObj instanceof Attribute_c ) {
				DerivedBaseAttribute_c dbattr = DerivedBaseAttribute_c.getOneO_DBATTROnR107(
						BaseAttribute_c.getOneO_BATTROnR106((Attribute_c)dialectObj));
				if ( dbattr != null ) {
					dialectObj = dbattr;
				}
			}
            // Get the value of the dialect attribute
            try {
                Method getDialectMethod = dialectObj.getClass().getMethod("getDialect"); //$NON-NLS-1$
                dialect = (int) getDialectMethod.invoke(dialectObj);
            } catch (NoSuchMethodException e) {
                System.out.println(e);
            } catch (NullPointerException e) {
                System.out.println(e);
            } catch (SecurityException e) {
                System.out.println(e);
            } catch (IllegalAccessException e) {
                System.out.println(e);
            } catch (IllegalArgumentException e) {
                System.out.println(e);
            } catch (InvocationTargetException e) {
                System.out.println(e);
            } catch (ExceptionInInitializerError e) {
                System.out.println(e);
            }
            if ( dialect != Actiondialect_c.none ) {
                return true;
            }
        }
        return false;
    }
}
