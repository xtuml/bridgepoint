package org.xtuml.bp.ui.marking;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Vector;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;
import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.ExecutableProperty_c;
import org.xtuml.bp.core.Function_c;
import org.xtuml.bp.core.Ooaofooa;
import org.xtuml.bp.core.Operation_c;
import org.xtuml.bp.core.common.ModelRoot;
import org.xtuml.bp.core.common.NonRootModelElement;
import org.xtuml.bp.ui.marking.MarkingData.Mark;

public class MarkingEditorDialog extends Dialog {

	private String title;
	private IProject project;
	private CCombo elementTypeCombo;
    private CCombo modelElementCombo;
    private Table table;
    
    private static final int MAX_FEATURES = 15;
    
    private MarkingData markingData;
        
	public MarkingEditorDialog(Shell parentShell, String title, IProject project) {
		super(parentShell);
		this.title = title;
		this.project = project;
	
		markingData = new MarkingData(project);		
	}

	private void reloadTableFeatures(String elementType, String modelElement) {
		table.clearAll();
		table.deselectAll();
		
		Vector<String> featureList = markingData.getFeatures(elementType);
		if ( featureList == null ) { return; }
		Iterator<String> listIter = featureList.iterator();
		LinkedHashMap<String,Mark> markList = markingData.getMarks(modelElement, elementType);
		int i = 0;
		while (listIter.hasNext()) {
			TableItem item = table.getItem(i);
			String feature = listIter.next();
			String value = "";
			if ( markList != null ) { 
				Mark mark = markList.get(MarkingData.getCombinedRef(feature, elementType));
				if ( mark != null ) {
					value = mark.value;
				}
			}
			item.setText(new String [] {feature, value});
			i++;
		}
	}
	
	private void reloadModelElements(String elementType) {
		table.clearAll();
		table.deselectAll();
		modelElementCombo.deselectAll();
		modelElementCombo.clearSelection();
		modelElementCombo.removeAll();
		
		try {
			// returns the Class object for the class with the specified name
			String ooaClassName = elementType.trim();
			ooaClassName = ooaClassName.replaceAll(" ", "");
			Class<?> clazz = Class.forName(CorePlugin.getDefault().getBundle().getSymbolicName() + "." + ooaClassName + "_c");

			ModelRoot[] roots = Ooaofooa.getInstancesUnderSystem(project.getName());
			// This Java reflection call helps invoke a method like this:
			//     ModelClass_c[] mcs = ModelClass_c.ModelClassInstances(modelRoot);
			// So the loop here finds all instances of a given OOAofOOA class in
			// a project.
			Method instancesMethod = clazz.getMethod(ooaClassName + "Instances", ModelRoot.class);
			for (ModelRoot modelroot : roots) {
				Object[] instances = (Object[]) instancesMethod.invoke(null, modelroot);
				for (Object inst : instances) {
					String entryText = getPathkey((NonRootModelElement) inst);
					modelElementCombo.add(entryText);
				}
			}
		} catch (ClassNotFoundException e) {
			CorePlugin.logError(e.toString(), e);
	    } catch ( NoSuchMethodException e ) {
			CorePlugin.logError(e.toString(), e);
        } catch ( NullPointerException e ) {
			CorePlugin.logError(e.toString(), e);
        } catch ( SecurityException e ) {
			CorePlugin.logError(e.toString(), e);
        } catch ( IllegalAccessException e ) {
			CorePlugin.logError(e.toString(), e);
        } catch ( IllegalArgumentException e ) {
			CorePlugin.logError(e.toString(), e);
        } catch ( InvocationTargetException e ) {
			CorePlugin.logError(e.toString(), e);
        } catch ( ExceptionInInitializerError e ) {
			CorePlugin.logError(e.toString(), e);
        }
	}
	
	@Override
	protected void createButtonsForButtonBar (Composite parent)
	{
		// If there were errors while creating the dialog, we only want to provide
		// a cancel button
		if ( elementTypeCombo == null ) {
			createButton (parent, IDialogConstants.CANCEL_ID, "Cancel", true) ;
		} else {
			super.createButtonsForButtonBar(parent);
		}
	} 
	
	@Override
	protected void okPressed() {
		table.forceFocus();  // Make sure we move the focus out of an cell edits in progress so they are persisted
		markingData.persist();
		super.okPressed();
	}
	
	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText(title);
	}

	@Override
	protected int getShellStyle() {
		return SWT.CLOSE | SWT.RESIZE;
	}
	
	@Override
	public boolean close() {
		return super.close();
	}

	@Override
	protected Control createDialogArea(Composite container) {
		Point size = new Point(800, 600);
		if (PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell() != null) {
			size = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell()
					.getSize();
		}
		
        Composite parent = (Composite) super.createDialogArea(container);
        parent.setSize(size);

		String validationError = markingData.validateFeatures();
		// If there was a feature validation error, show an error message.  Otherwise
		// build the marking editor dialog
		if (!validationError.isEmpty()) {
			Label label = new Label(parent, SWT.NONE);
			label.setText(validationError);
			label.pack();
		} else {			
			GridLayout oneColGridLayout = new GridLayout();
			oneColGridLayout.numColumns = 1;
			GridLayout twoColGridLayout = new GridLayout();
			twoColGridLayout.numColumns = 2;
			GridData gridData = new GridData(GridData.FILL, GridData.CENTER, true, false);
			gridData.horizontalSpan = 2;
			
			Composite selectionComposite = new Composite(parent, SWT.NONE);
			selectionComposite.setLayout(twoColGridLayout);
			selectionComposite.setLayoutData(gridData);
			
			Group elementTypeGroup = new Group(selectionComposite, SWT.SHADOW_ETCHED_IN);
			elementTypeGroup.setLocation(10, 10);
			elementTypeGroup.setSize(100, 75);
			elementTypeGroup.setText("Element Type");
			elementTypeGroup.setLayout(oneColGridLayout);
			elementTypeGroup.setLayoutData(gridData);

			elementTypeCombo = new CCombo(elementTypeGroup, SWT.BORDER | SWT.READ_ONLY);
			String[] featuresArray = markingData.getMarkables();
			elementTypeCombo.setItems(featuresArray);
			elementTypeCombo.setBounds(5, 5, 100, 20);
			elementTypeCombo.addSelectionListener(new SelectionListener() {
				public void widgetSelected(SelectionEvent e) {
					reloadModelElements(elementTypeCombo.getText());
				}

				public void widgetDefaultSelected(SelectionEvent e) {
					reloadModelElements(elementTypeCombo.getText());
				}
			});
			elementTypeCombo.setLayout(oneColGridLayout);
			elementTypeCombo.setLayoutData(new GridData(GridData.FILL, GridData.CENTER, true, false));

			Group modelElementGroup = new Group(selectionComposite, SWT.SHADOW_ETCHED_IN);
			modelElementGroup.setLocation(10, 10);
			modelElementGroup.setSize(500, 75);
			modelElementGroup.setText("Model Element");
			modelElementGroup.setLayout(oneColGridLayout);
			modelElementGroup.setLayoutData(gridData);

			modelElementCombo = new CCombo(modelElementGroup, SWT.BORDER | SWT.READ_ONLY);
			modelElementCombo.setItems(new String[] { " " });
			modelElementCombo.setBounds(5, 5, 600, 20);
			modelElementCombo.addSelectionListener(new SelectionListener() {
				public void widgetSelected(SelectionEvent e) {
					reloadTableFeatures(elementTypeCombo.getText(), modelElementCombo.getText());
				}

				public void widgetDefaultSelected(SelectionEvent e) {
					reloadTableFeatures(elementTypeCombo.getText(), modelElementCombo.getText());
				}
			});
			modelElementCombo.setLayout(oneColGridLayout);
			modelElementCombo.setLayoutData(new GridData(GridData.FILL, GridData.CENTER, true, false));

			Composite tableComposite = new Composite(parent, SWT.NONE);
			tableComposite.setLayout(oneColGridLayout);

			table = new Table(tableComposite, SWT.BORDER | SWT.MULTI);
			table.setHeaderVisible(true);
			table.setLinesVisible(true);
			for (int i = 0; i < 2; i++) {
				TableColumn column = new TableColumn(table, SWT.LEFT);
				if (i == 0) {
					column.setText("Feature");
					column.setWidth(175);
				} else {
					column.setText("Value");
					column.setWidth(500);
				}
				column.setResizable(true);
			}
			for (int i = 0; i < MAX_FEATURES; i++) {
				TableItem item = new TableItem(table, SWT.LEFT);
				item.setText(new String[] { "", "" });
			}
			final TableEditor editor = new TableEditor(table);
			editor.horizontalAlignment = SWT.LEFT;
			editor.grabHorizontal = true;
			table.addListener(SWT.MouseDown, event -> {
				Rectangle clientArea = table.getClientArea();
				Point pt = new Point(event.x, event.y);
				int index = table.getTopIndex();
				while (index < table.getItemCount()) {
					boolean visible = false;
					final TableItem item = table.getItem(index);
					for (int i = 1; i < table.getColumnCount(); i++) {
						if (item.getText(0).isEmpty()) {
							// We don't want to make the table editable for
							// blank feature lines
							continue;
						}
						Rectangle rect = item.getBounds(i);
						if (rect.contains(pt)) {
							final int column = i;
							final Text text = new Text(table, SWT.NONE);
							Listener textListener = e -> {
								switch (e.type) {
								case SWT.FocusOut:
									item.setText(column, text.getText());
									markingData.updateFeature(modelElementCombo.getText(), item.getText(0), 
											item.getText(column), elementTypeCombo.getText());
									text.dispose();
									break;
								case SWT.Traverse:
									switch (e.detail) {
									case SWT.TRAVERSE_RETURN:
										item.setText(column, text.getText());
										// FALL THROUGH
									case SWT.TRAVERSE_ESCAPE:
										markingData.updateFeature(modelElementCombo.getText(), item.getText(0),
												item.getText(column), elementTypeCombo.getText());
										text.dispose();
										e.doit = false;
									}
									break;
								}
							};
							text.addListener(SWT.FocusOut, textListener);
							text.addListener(SWT.Traverse, textListener);
							editor.setEditor(text, item, i);
							text.setText(item.getText(i));
							text.selectAll();
							text.setFocus();
							return;
						}
						if (!visible && rect.intersects(clientArea)) {
							visible = true;
						}
					}
					if (!visible)
						return;
					index++;
				}
			});

			elementTypeCombo.setFocus();
		}
        return parent;
	}

	private String getPathkey(NonRootModelElement inst) {
		String signature = new String("");
		String pathkey = ((NonRootModelElement) inst).getPath();
		
		// If the instance requires a full signature, replace the last segment which
		// is the name with the full signature
		if (inst instanceof Function_c) {
			signature = ((Function_c) inst).Getsignature(1);
		} else if (inst instanceof Operation_c) {
			signature = ((Operation_c) inst).Getsignature(1);
		} else if (inst instanceof ExecutableProperty_c) {
			signature = ((ExecutableProperty_c) inst).Getsignature(1);
		}
		
		if (!signature.isEmpty()) {
			signature = signature.replaceAll(", ", " ");
			String[] pathPieces = pathkey.split("::");
			String updatedPath = new String("");
			for (int i=0; i<pathPieces.length-1; ++i) {
				updatedPath = updatedPath.concat(pathPieces[i] + "::");
			}
			updatedPath = updatedPath.concat(signature);
			pathkey = updatedPath;
		}

		pathkey = pathkey.replaceFirst(project.getName() + "::", "");
		return pathkey;
	}
}
