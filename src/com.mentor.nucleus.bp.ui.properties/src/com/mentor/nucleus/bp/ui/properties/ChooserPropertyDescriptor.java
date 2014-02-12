//====================================================================
//
//File:      $RCSfile: ChooserPropertyDescriptor.java,v $
//Version:   $Revision: 1.5 $
//Modified:  $Date: 2013/01/10 23:20:17 $
//
//(c) Copyright 2004-2014 by Mentor Graphics Corp. All rights reserved.
//
//====================================================================
package com.mentor.nucleus.bp.ui.properties;

import java.lang.reflect.Method;

import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.DialogCellEditor;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.views.properties.PropertyDescriptor;

import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.core.ui.Selection;

public class ChooserPropertyDescriptor extends PropertyDescriptor
{
	/**
	 * Creates a property descriptor with the given id and display name.
	 * 
	 * @param id the id of the property
	 * @param displayName the name to display for the property
	 */
    Object m_cl ;
    Class<IObjectActionDelegate> m_actionClass;
    boolean m_readOnly;
    private static IObjectActionDelegate m_sda = null;
    
	@SuppressWarnings("unchecked")
	public ChooserPropertyDescriptor(Object id, String displayName, Object cl,
                                       String actionClassName, boolean readOnly)
	{
		super(id, displayName);
        m_cl = cl;
        m_readOnly = readOnly;
        if (actionClassName != "unknowntypeAction") {
          try {
            m_actionClass = (Class<IObjectActionDelegate>)Class.
                                                       forName(actionClassName);
          }
          catch (ClassNotFoundException cnf) {
        	
        	CorePlugin.logError("Class not found creating property descriptor",
                                                                           cnf);
          }
          catch (ClassCastException cce) {

        	CorePlugin.logError("Illegal class type for property descriptor",
                                                                           cce);
          }
        }
	}

	private class ChooserCellEditor extends DialogCellEditor
	{
		/**
				 * @param parent
				 */
		public ChooserCellEditor(Composite parent)
		{
			super(parent);
		}

		protected Control createControl(Composite parent) {
			 Control editor = super.createControl(parent);
			 Control[] children = ((Composite)editor).getChildren();
			 Label label = null;
			 for (int i=0; i < children.length; i++) {
				 if(children[i] instanceof Label) {
					 label = (Label)children[i];
					 break;
				 }
			 }
			 if (label != null) {
			   label.addMouseListener(new MouseListener() {
				
				@Override
				public void mouseUp(MouseEvent e) {
					// Do nothing
					
				}
				
				@Override
				public void mouseDown(MouseEvent e) {
					// Do nothing
					
				}
				
				@Override
				public void mouseDoubleClick(MouseEvent e) {
					openDialogBox(null);
				}
			  });
			 }
			 return editor;
		 }
		/* (non-Javadoc)
		* @see org.eclipse.jface.viewers.DialogCellEditor#openDialogBox(org.eclipse.swt.widgets.Control)
		*/
		protected Object openDialogBox(Control cellEditorWindow)
		{
			if (m_actionClass != null) {
              openChooser( m_cl, m_actionClass );
			}
			return null;
		}

	}

	/**
	 * @see org.eclipse.ui.views.properties.IPropertyDescriptor#createPropertyEditor(Composite)
	 */
	public CellEditor createPropertyEditor(Composite parent)
	{
        if ( m_readOnly )
        {
            return null;
        }
        else
        {
		  CellEditor editor = new ChooserCellEditor(parent);
		  if (getValidator() != null)
			editor.setValidator(getValidator());
		  return editor;
        }
	}
    
    static private void openChooser( final Object inst, final Class<IObjectActionDelegate> actionClass )
    {
    
        try
        {
          IWorkspaceRunnable r = new IWorkspaceRunnable()
          {
            public void run(IProgressMonitor monitor) throws CoreException
            {
            	m_sda = null;
                try {
                  m_sda = actionClass.newInstance();
                }
                catch (IllegalAccessException iae) {
                	CorePlugin.logError(
                		"Problem creating property descriptor chooser", iae);
                }
                catch (InstantiationException ie) {
                	CorePlugin.logError(
                    		"Problem creating property descriptor chooser", ie);
                }
                Display.getCurrent().asyncExec(new Runnable() {
                	public void run() {
                        IStructuredSelection ss = new StructuredSelection(inst);
                        Action a = new Action(){};
                        ISelection selection = Selection.getInstance().getSelection();
                        Selection.getInstance().setSelection(ss, false);
                        m_sda.run( a );
                        Selection.getInstance().setSelection(selection, false);
                	}
                });
            }
          };
          CorePlugin.getWorkspace().run(r, null);
        }
        catch (CoreException x)
        {
          CorePlugin.logError("open chooser problem", x);
        }
    
    }

	public int numValues() {
	  if (m_actionClass != null) {
	    try {
		  Method mthd = m_actionClass.getMethod("getElements", m_cl.getClass());
		  Object result = mthd.invoke(null, m_cl);
		  Object [] resA = (Object[])result;
		  return resA.length;
	    }
	    catch (Exception e) {
		  CorePlugin.logError("Exception getting chooser elements length", e);
	    }
	  }
	  return 0;
	}

	public String enumValue(int j) {
	  if (m_actionClass != null) {
	    try {
			Method mthd = m_actionClass.getMethod("getElements", m_cl.getClass());
			Object result = mthd.invoke(null, m_cl);
			Object [] resA = (Object[])result;
			if (resA[j] instanceof NonRootModelElement) {
			  return ((NonRootModelElement)resA[j]).getName();
			}
		  }
		  catch (Exception e) {
			  CorePlugin.logError("Exception getting chooser elements", e);
		  }
	  }
	  return null;
	}

}
