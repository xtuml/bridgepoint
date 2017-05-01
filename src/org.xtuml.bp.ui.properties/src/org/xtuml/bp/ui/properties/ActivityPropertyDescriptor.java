//====================================================================
//
//File:      $RCSfile: ActivityPropertyDescriptor.java,v $
//Version:   $Revision: 1.8 $
//Modified:  $Date: 2013/01/10 23:20:17 $
//
//(c) Copyright 2004-2014 by Mentor Graphics Corp. All rights reserved.
//
//====================================================================
package org.xtuml.bp.ui.properties;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.DialogCellEditor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.xtuml.bp.core.ActionHome_c;
import org.xtuml.bp.core.Action_c;
import org.xtuml.bp.core.Actiondialect_c;
import org.xtuml.bp.core.Attribute_c;
import org.xtuml.bp.core.BaseAttribute_c;
import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.DerivedBaseAttribute_c;
import org.xtuml.bp.core.MooreActionHome_c;
import org.xtuml.bp.core.StateMachineState_c;
import org.xtuml.bp.core.TransitionActionHome_c;
import org.xtuml.bp.core.Transition_c;
import org.xtuml.bp.ui.text.activity.ShowActivityAction;

public class ActivityPropertyDescriptor extends PropertyDescriptor
{
	/**
	 * Creates a property descriptor with the given id and display name.
	 * 
	 * @param id the id of the property
	 * @param displayName the name to display for the property
	 */
    Object m_cl ;
    
	public ActivityPropertyDescriptor(Object id, String displayName, Object cl)
	{
		super(id, displayName);
        m_cl = cl;
	}

	private class ActivityCellEditor extends DialogCellEditor
	{
		/**
				 * @param parent
				 */
		public ActivityCellEditor(Composite parent)
		{
			super(parent);
		}

		/* (non-Javadoc)
		* @see org.eclipse.jface.viewers.DialogCellEditor#openDialogBox(org.eclipse.swt.widgets.Control)
		*/
		protected Object openDialogBox(Control cellEditorWindow)
		{
            openActivityEditor( m_cl );
			return null;
		}

	}

	/**
	 * @see org.eclipse.ui.views.properties.IPropertyDescriptor#createPropertyEditor(Composite)
	 */
	public CellEditor createPropertyEditor(Composite parent)
	{
		CellEditor editor = new ActivityCellEditor(parent);
		if (getValidator() != null)
			editor.setValidator(getValidator());
		return editor;
	}
    
    static private void openActivityEditor( final Object inst )
    {
        int dialect = -1;
		// see if the current element should open
		// something other than itself
		Object dialectObj = inst;
		if (dialectObj instanceof StateMachineState_c) {
		    StateMachineState_c state = (StateMachineState_c) dialectObj;
			Action_c action = Action_c.getOneSM_ACTOnR514(ActionHome_c.getOneSM_AHOnR513((MooreActionHome_c.getOneSM_MOAHOnR511(state))));
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
            Method getDialectMethod = dialectObj.getClass().getMethod("getDialect"); //$$NON-NLS-1$$
            dialect = (int) getDialectMethod.invoke(dialectObj);
        } catch ( NoSuchMethodException e ) {
            System.out.println( e );
        } catch ( NullPointerException e ) {
            System.out.println( e );
        } catch ( SecurityException e ) {
            System.out.println( e );
        } catch ( IllegalAccessException e ) {
            System.out.println( e );
        } catch ( IllegalArgumentException e ) {
            System.out.println( e );
        } catch ( InvocationTargetException e ) {
            System.out.println( e );
        } catch ( ExceptionInInitializerError e ) {
            System.out.println( e );
        }
        if ( dialect != Actiondialect_c.none ) {
    
        try
        {
          IWorkspaceRunnable r = new IWorkspaceRunnable()
          {
            public void run(IProgressMonitor monitor) throws CoreException
            {
                IStructuredSelection ss = new StructuredSelection(inst);
                ShowActivityAction saa = new ShowActivityAction();
                Action a = new Action(){};
                saa.selectionChanged(a, ss);
                saa.run( a );
            }
          };
          CorePlugin.getWorkspace().run(r, null);
        }
        catch (CoreException x)
        {
          CorePlugin.logError("open activity editor problem", x);
        }

        }
    
    }

}
