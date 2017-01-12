package org.xtuml.bp.ui.marking;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
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
import org.xtuml.bp.core.Ooaofooa;
import org.xtuml.bp.core.common.ModelRoot;
import org.xtuml.bp.core.common.NonRootModelElement;

public class MarkingEditorDialog extends Dialog {

	private String title;
	private IProject project;
	private CCombo elementTypeCombo;
    private CCombo modelElementCombo;
    private Table table;
    
    private static final String DELIM = ",";
    private static final String PREFIX = "org.xtuml.bp.core.";

    private static final int MAX_FEATURES = 15;
    private static final String FEATURE_FILE = "/gen/features.mark";
    private static final String MARKINGS_FILE = "/gen/application.mark";
    
    // Map of Element Types and a vector of associated features
    private HashMap<String, Vector<String>> featureMap;
    
    // Ordered map of fully-pathed application model elements and an ordered map of associated feature/value pairs
    private LinkedHashMap<String, LinkedHashMap<String,String>> markingsMap;
    
	public MarkingEditorDialog(Shell parentShell, String title, IProject project) {
		super(parentShell);
		this.title = title;
		this.project = project;
	
		// Read in the feature map
		populateFeatures();
		
		// Read in the markings
		populateMarkings();
	}

	private void populateFeatures() {
		featureMap = new HashMap<String, Vector<String>>();
		Scanner inFile = new Scanner("");
		
		try {
			inFile = new Scanner(new FileReader(project.getLocation().toString() + FEATURE_FILE));
			inFile.useDelimiter(",|\\r|\\n");
		} catch (FileNotFoundException fnfe) {
			System.out.println( fnfe );
		}
		
		while ( inFile.hasNext() ) {
			String elementType = inFile.next().trim();
			if ( elementType.startsWith("#") || elementType.isEmpty() ) {
				inFile.nextLine().trim();  // Throw away rest of line in case there are delimiters in the comment
				continue; 
			}
			
			String featureName = inFile.next().trim();
			Vector<String> list;
			
			if ( featureMap.containsKey(elementType) ) {
				// The element type has already been seen, add the feature to the list
				list = featureMap.get(elementType);
			} else {
				// Element Type has not been seen yet
				list = new Vector<String>();
				featureMap.put(elementType, list);
			}
			list.add(featureName);
		}
		
		inFile.close();
	}

	private String validateFeatures() {
		Set<String> featureSet = featureMap.keySet();
		Iterator<String> featureSetIter = featureSet.iterator();
		String invalidElements = "";
		
		while (featureSetIter.hasNext()) {
			String ooaClassName = featureSetIter.next();
			ooaClassName = ooaClassName.replaceAll(" ", "");
			try {
				Class.forName(PREFIX + ooaClassName + "_c");
			} catch (ClassNotFoundException e) {
				invalidElements = invalidElements + ooaClassName + "\n";
			}
		}
		
		if ( !invalidElements.isEmpty() ) {
			invalidElements = "The features marking data contains the following invalid element types. You must\n" + 
					"correct these errors in order to edit application marks: \n\n" + invalidElements;
		}
		
		return invalidElements;
	}
	
	private void populateMarkings() {
		markingsMap = new LinkedHashMap<String, LinkedHashMap<String,String>>();
		Scanner inFile = new Scanner("");
		
		try {
			inFile = new Scanner(new FileReader(project.getLocation().toString() + MARKINGS_FILE));
			inFile.useDelimiter(",|\\r|\\n");
		} catch (FileNotFoundException fnfe) {
			System.out.println( fnfe );
		}
		
		while ( inFile.hasNext() ) {
			String modelElement = inFile.next().trim();
			String featureName = inFile.next().trim();
			String featureValue = inFile.nextLine().trim();
			featureValue = featureValue.replaceFirst(",", "");
			updateFeature(modelElement, featureName, featureValue);
		}
		
		inFile.close();
	}

	private void updateFeature(String modelElement, String featureName, String newValue) {
		LinkedHashMap<String,String> markList;

		if ( markingsMap.containsKey(modelElement) ) {
			// The model element has already been seen, add the feature to the list
			markList = markingsMap.get(modelElement);
		} else {
			// Element Type has not been seen yet
			markList = new LinkedHashMap<String,String>();
			markingsMap.put(modelElement, markList);
		}
		markList.put(featureName, newValue);
	}
	
	private void reloadTableFeatures(String elementType, String modelElement) {
		table.clearAll();
		table.deselectAll();
		
		Vector<String> featureList = featureMap.get(elementType);
		if ( featureList == null ) { return; }
		Iterator<String> listIter = featureList.iterator();
		LinkedHashMap<String,String> markList = markingsMap.get(modelElement);
		int i = 0;
		while (listIter.hasNext()) {
			TableItem item = table.getItem(i);
			String feature = listIter.next();
			String value = "";
			if ( markList != null ) { value = markList.get(feature); }
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
			Class<?> clazz = Class.forName(PREFIX + ooaClassName + "_c");

			ModelRoot[] roots = Ooaofooa.getInstancesUnderSystem(project.getName());
			// This Java reflection call helps invoke a method like this:
			//     ModelClass_c[] mcs = ModelClass_c.ModelClassInstances(modelRoot);
			// So the loop here finds all instances of a given OOAofOOA class in
			// a project.
			Method instancesMethod = clazz.getMethod(ooaClassName + "Instances", ModelRoot.class);
			for (ModelRoot modelroot : roots) {
				Object[] instances = (Object[]) instancesMethod.invoke(null, modelroot);
				for (Object inst : instances) {
					String entryText = ((NonRootModelElement) inst).getPath();
					modelElementCombo.add(entryText);
				}
			}

		} catch (ClassNotFoundException e) {
			System.out.println(e.toString());
	    } catch ( NoSuchMethodException e ) {
            System.out.println(e.toString());
        } catch ( NullPointerException e ) {
            System.out.println(e.toString());
        } catch ( SecurityException e ) {
        	System.out.println(e.toString());
        } catch ( IllegalAccessException e ) {
        	System.out.println(e.toString());
        } catch ( IllegalArgumentException e ) {
        	System.out.println(e.toString());
        } catch ( InvocationTargetException e ) {
        	System.out.println(e.toString());
        } catch ( ExceptionInInitializerError e ) {
        	System.out.println(e.toString());
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
		try {
			FileOutputStream fout = new FileOutputStream(project.getLocation().toString() + MARKINGS_FILE);
			PrintStream stream = new PrintStream(fout);
			
			// Persist the markings
			for (Map.Entry<String, LinkedHashMap<String,String>> elementEntry : markingsMap.entrySet()) {
				for ( Map.Entry<String, String> featureEntry : elementEntry.getValue().entrySet()) {
					if ( ! featureEntry.getValue().isEmpty() ) {
						stream.println(elementEntry.getKey() + DELIM + featureEntry.getKey() + DELIM + featureEntry.getValue());
					}
				}
			}
			fout.close();
		} catch (IOException e) {
			System.out.println( e );
		}
        
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

		String validationError = validateFeatures();
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
			String[] featuresArray = featureMap.keySet().stream().toArray(String[]::new);
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
									updateFeature(modelElementCombo.getText(), item.getText(0), item.getText(column));
									text.dispose();
									break;
								case SWT.Traverse:
									switch (e.detail) {
									case SWT.TRAVERSE_RETURN:
										item.setText(column, text.getText());
										// FALL THROUGH
									case SWT.TRAVERSE_ESCAPE:
										updateFeature(modelElementCombo.getText(), item.getText(0),
												item.getText(column));
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

}
