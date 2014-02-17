//====================================================================
//
//File:      $RCSfile: ActivityPropertyDescriptor.java,v $
//Version:   $Revision: 1.8 $
//Modified:  $Date: 2013/01/10 23:20:17 $
//
//(c) Copyright 2004-2014 by Mentor Graphics Corp. All rights reserved.
//
//====================================================================
package com.mentor.nucleus.bp.ui.properties;

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

import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.ui.text.activity.ShowActivityAction;

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
