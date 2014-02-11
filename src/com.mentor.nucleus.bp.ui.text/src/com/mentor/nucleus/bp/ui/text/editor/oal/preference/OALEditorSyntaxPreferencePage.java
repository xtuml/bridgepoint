//========================================================================
//
// File:      $RCSfile: OALEditorSyntaxPreferencePage.java,v $
// Version:   $Revision: 1.10 $
// Modified:  $Date: 2013/01/10 23:21:04 $
//
// (c) Copyright 2004-2014 by Mentor Graphics Corp. All rights reserved.
//
//========================================================================
// Licensed under the Apache License, Version 2.0 (the "License"); you may not
// use this file except in compliance with the License.  You may obtain a copy
// of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
// WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.   See the
// License for the specific language governing permissions and limitations under
// the License.
//========================================================================

package com.mentor.nucleus.bp.ui.text.editor.oal.preference;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.jface.preference.ColorSelector;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;

import com.mentor.nucleus.bp.ui.text.OALEditorPlugin;
import com.mentor.nucleus.bp.ui.text.editor.SyntaxHighlightingPreferences;
import com.mentor.nucleus.bp.ui.text.editor.SyntaxHighlightingPreferences.TokenTypeInfo;
import com.mentor.nucleus.bp.ui.text.editor.oal.OALEditorConfiguration;
import com.mentor.nucleus.bp.ui.text.editor.oal.OALDocumentProvider;
import com.mentor.nucleus.bp.ui.preference.BaseModelEditor;
import com.mentor.nucleus.bp.ui.preference.BasePlugin;
import com.mentor.nucleus.bp.ui.preference.IPreferenceModel;
import com.mentor.nucleus.bp.ui.preference.IPreferenceModel.IChangeListener;

/**
 * @author babar
 */
public class OALEditorSyntaxPreferencePage extends BaseModelEditor {

    //	background widgets
    private Button systemDefaultRadio;
    private Button customColorRadio;
    private Group bkGroundColorGroup;
    private ColorSelector backgroundColorSelector;

    // foreground widgets
    private List syntaxForegroundList;
    private ColorSelector syntaxColorSelector;
    private Button boldCheckButton;

    private Map stringToIdMap = new HashMap();

    public OALEditorSyntaxPreferencePage() {
        super();
        setTitle("Syntax");
    }

    // Implementation of BaseModelEditor abstract methods ****************START
    protected BasePlugin getPlugin() {
        return OALEditorPlugin.getDefaultOALPlugin();
    }

    protected void synchronizeUITo(IPreferenceModel aModel) {
        if (!(model instanceof SyntaxHighlightingPreferences)) {
            throw new IllegalArgumentException("Cannot edit model of type " + model.getClass().getName());
        }

        SyntaxHighlightingPreferences clonedPreferences = (SyntaxHighlightingPreferences) aModel;

        if (clonedPreferences.isSystemColor()) {
            systemDefaultRadio.setSelection(true);
            customColorRadio.setSelection(false);
        } else {
            customColorRadio.setSelection(true);
            systemDefaultRadio.setSelection(false);
        }

        backgroundColorSelector.setColorValue(clonedPreferences.getBackgroundRGB());

        syntaxForegroundList.removeAll();

        int[] tokenTypes = clonedPreferences.getTokenTypes();
        for (int i = 0; i < tokenTypes.length; i++) {
            TokenTypeInfo info = clonedPreferences.getTokenTypeInfo(tokenTypes[i]);
            stringToIdMap.put(info.getTypeDescription(), new Integer(info.getTypeId()));

            syntaxForegroundList.add(info.getTypeDescription());
        }

        syntaxForegroundList.setSelection(0);
        setBackgroundButtonState();
        setForegroundUIState();
    }

    // Implementation of BaseModelEditor abstract methods ****************END

    protected Control createContents(Composite parent) {

        Composite uiContainer = new Canvas(parent, SWT.NULL);
        uiContainer.setLayout(new GridLayout());

        Composite colorParent = new Canvas(uiContainer, SWT.NULL);

        GridLayout paneLayout = new GridLayout();
        paneLayout.numColumns = 2;
        colorParent.setLayout(paneLayout);

        //Creating the group box and setting its layout
        bkGroundColorGroup = new Group(colorParent, SWT.SHADOW_ETCHED_IN);
        GridLayout bkLayout = new GridLayout(2, true);
        bkLayout.numColumns = 3;
        bkGroundColorGroup.setLayout(bkLayout);

        GridData data = new GridData(GridData.FILL_HORIZONTAL);
        data.grabExcessHorizontalSpace = true;
        data.horizontalIndent = -1;
        bkGroundColorGroup.setLayoutData(data);

        //Setting group box name
        bkGroundColorGroup.setText("&Background Color");

        //Now adding the two radio buttons, for System defualt colors and custom color
        systemDefaultRadio = new Button(bkGroundColorGroup, SWT.RADIO | SWT.LEFT);
        systemDefaultRadio.setText("&System Default");
        systemDefaultRadio.setLayoutData(new GridData());
        //bkGroundColorGroup.add

        customColorRadio = new Button(bkGroundColorGroup, SWT.RADIO | SWT.LEFT);
        customColorRadio.setText("&Custom");
        customColorRadio.setLayoutData(new GridData());

        //Adding the background color selector button besides the custom radio button
        backgroundColorSelector = new ColorSelector(bkGroundColorGroup);
        backgroundColorSelector.getButton().setLayoutData(new GridData(SWT.RIGHT));

        GridData gd;

        /*
         *Adding the list, the color button and bold option 
        */

        // 1) Adding simple label
        Label label = new Label(colorParent, SWT.NONE);
        label.setText("Foreground:");
        gd = new GridData(GridData.FILL_HORIZONTAL);
        gd.horizontalSpan = 2;
        label.setLayoutData(gd);

        // 2) The color list and its slection event listener

        syntaxForegroundList = new List(colorParent, SWT.BORDER);
        //syntaxForegroundList.setItems(syntaxMembers);

        syntaxForegroundList.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        // 3) color selector button
        Composite buttonArea = new Composite(colorParent, SWT.NONE);
        gd = new GridData();
        buttonArea.setLayoutData(gd);

        // Setup buttons ... following few lines are a strange code of hadnling the layout stuff
        RowLayout buttonLayout = new RowLayout();
        buttonLayout.type = SWT.VERTICAL;
        buttonArea.setLayout(buttonLayout);

        Composite colorArea = new Composite(buttonArea, SWT.NULL);
        GridData gridData = new GridData();
        gridData.horizontalAlignment = GridData.FILL;
        gridData.verticalAlignment = GridData.FILL;
        buttonArea.setLayoutData(gridData);

        GridLayout gl = new GridLayout();
        gl.numColumns = 2;
        colorArea.setLayout(gl);

        Label colorLabel = new Label(colorArea, SWT.NULL);
        colorLabel.setText("Color:");
        gd = new GridData();
        gd.horizontalIndent = -3;
        colorLabel.setLayoutData(gd);

        //.. the button is here
        syntaxColorSelector = new ColorSelector(colorArea);
        syntaxColorSelector.getButton().setLayoutData(new GridData());

        // 4) Check button for bold option
        boldCheckButton = new Button(buttonArea, SWT.CHECK);
        boldCheckButton.setText("Bold");

        RowData rowData = new RowData(150 - 5, SWT.DEFAULT);
        boldCheckButton.setLayoutData(rowData);

        label = new Label(uiContainer, SWT.LEFT);
        label.setText("Preview:");
        label.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        setupEventListeners();
        setupModel();

        Control previewer = createPreviewer(uiContainer);
        gd = new GridData(GridData.FILL_BOTH);
        gd.widthHint = convertWidthInCharsToPixels(20);
        gd.heightHint = convertHeightInCharsToPixels(11);
        previewer.setLayoutData(gd);

        setBackgroundButtonState();
        setForegroundUIState();

        return uiContainer;
    }

    protected Control createPreviewer(Composite parent) {
        final SyntaxHighlightingPreferences clonedPreferences = (SyntaxHighlightingPreferences) clonedModel;

        final SourceViewer viewer = new SourceViewer(parent, null, null, false, SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER);
        OALEditorConfiguration viewerConfiguration = new OALEditorConfiguration(clonedPreferences);
        viewer.configure(viewerConfiguration);
        Font font = JFaceResources.getFont("org.eclipse.jdt.ui.editors.textfont");
        viewer.getTextWidget().setFont(font);
        viewer.getTextWidget().setBackground(clonedPreferences.getBackgroundColor());
        viewer.setEditable(false);

        String content = loadPreviewContentFromFile("OALPreviewCode.txt");
        IDocument document = new Document(content);
        OALDocumentProvider.setDocumentPartitioner(document);
        viewer.setDocument(document);

        clonedPreferences.addModelChangeListener(new IChangeListener() {
            public void modelChanged(IPreferenceModel model, int changeHints) {
                switch (changeHints) {
                    case SyntaxHighlightingPreferences.MODEL_BACKGROUND_CHANGED :
                        viewer.getTextWidget().setBackground(clonedPreferences.getBackgroundColor());
                        break;
                    case SyntaxHighlightingPreferences.MODEL_TOKEN_ATTRIBUTE_CHANGED :
                        viewer.invalidateTextPresentation();
                        break;
                }
            }
        });

        return viewer.getControl();
    }

    protected void setupModel() {
        OALEditorPlugin plugin = OALEditorPlugin.getDefaultOALPlugin();
        IPreferenceModel model = plugin.getSyntaxHighlightingPreferences();
        setModel(model);
    }

    protected void setupEventListeners() {
        systemDefaultRadio.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                getClonedPreferences().setBackgroundToSystemDefault();
                setBackgroundButtonState();
            }
        });

        customColorRadio.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                getClonedPreferences().setBackgroundToCustomColor();
                setBackgroundButtonState();
            }
        });

        backgroundColorSelector.getButton().addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent selection) {
                //set the color as selected by the user 
                getClonedPreferences().setBackgroundRGB(backgroundColorSelector.getColorValue());
            }
        });

        syntaxForegroundList.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                if (syntaxForegroundList.getSelectionIndex() != -1) {
                    setForegroundUIState();
                }
            }
        });

        syntaxColorSelector.getButton().addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent selection) {
                TokenTypeInfo info = getSelectedTokenTypeInfo();
                //set the color as selected by the user 
                info.setTextAttributes(syntaxColorSelector.getColorValue(), null, info.getStyle());
            }
        });

        boldCheckButton.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent selection) {
                TokenTypeInfo info = getSelectedTokenTypeInfo();

                //set the color as selected by the user 
                boolean isBold = boldCheckButton.getSelection();
                int style = info.getStyle();
                if (isBold) {
                    style = style | SWT.BOLD;
                } else {
                    style = style & (~SWT.BOLD);
                }

                info.setTextAttributes(info.getForegroundRGB(), null, style);
            }
        });

    }

    protected TokenTypeInfo getSelectedTokenTypeInfo() {
        int typeId = ((Integer) stringToIdMap.get(syntaxForegroundList.getSelection()[0])).intValue();

        TokenTypeInfo info = getClonedPreferences().getTokenTypeInfo(typeId);
        return info;
    }

    // common methods for maintain UI State *****************************START
    protected void setBackgroundButtonState() {
        if (getClonedPreferences().isSystemColor()) {
            backgroundColorSelector.setEnabled(false);
        } else {
            backgroundColorSelector.setEnabled(true);
            backgroundColorSelector.setColorValue(getClonedPreferences().getBackgroundRGB());
        }
    }

    public void setForegroundUIState() {
        TokenTypeInfo info = getSelectedTokenTypeInfo();

        syntaxColorSelector.setColorValue(info.getForegroundRGB());
        boldCheckButton.setSelection(info.isBold());
    }
    
//  common methods for maintain UI State **********************************END

    protected SyntaxHighlightingPreferences getClonedPreferences() {
        return (SyntaxHighlightingPreferences) clonedModel;
    }

    private String loadPreviewContentFromFile(String filename) {
        String line;
        String separator = System.getProperty("line.separator"); //$NON-NLS-1$
        StringBuffer buffer = new StringBuffer(512);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(filename)));
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
                buffer.append(separator);
            }
        } catch (IOException io) {

        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                }
            }
        }
        return buffer.toString();
    }

}
