//========================================================================
//
//File:      $RCSfile: SyntaxHighlightingPreferencesStore.java,v $
//Version:   $Revision: 1.12 $
//Modified:  $Date: 2013/01/10 23:20:46 $
//
//(c) Copyright 2004-2014 by Mentor Graphics Corp. All rights reserved.
//
//========================================================================
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
//========================================================================

package com.mentor.nucleus.bp.ui.text.editor;

import java.util.StringTokenizer;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.RGB;

import com.mentor.nucleus.bp.ui.preference.BasePlugin;
import com.mentor.nucleus.bp.ui.preference.IPreferenceModel;
import com.mentor.nucleus.bp.ui.preference.IPreferenceModelStore;
import com.mentor.nucleus.bp.ui.text.OALEditorConstants;
import com.mentor.nucleus.bp.ui.text.editor.SyntaxHighlightingPreferences.TokenTypeInfo;
import com.mentor.nucleus.bp.ui.text.editor.oal.OALTokenTypes;

/**
 * @author babar
 */

public class SyntaxHighlightingPreferencesStore implements IPreferenceModelStore {
    private static String PREFIX = "oal_syntax_prefs_";

    private static String BACKGROUND_IS_SYSTEM_COLOR = PREFIX + "bg_is_system_color";
    private static String BACKGROUND_RGB = PREFIX + "bg_rgb";

    private static String TOKEN_TYPE_COUNT = PREFIX + "type_count";

    private static String TOKEN_TYPE_ID = PREFIX + "token_type_id";
    private static String TOKEN_DESCRIPTION = PREFIX + "token_discription";
    private static String TOKEN_FOREGROUND = PREFIX + "token_foreground";
    private static String TOKEN_IS_BOLD = PREFIX + "token_is_bold";

    public Class getModelClass() {
        return SyntaxHighlightingPreferences.class;
    }

    public void saveModel(IPreferenceStore store, IPreferenceModel model) {
        if (!(model instanceof SyntaxHighlightingPreferences)) {
            throw new IllegalArgumentException("Cannot save instance of " + model.getClass().getName());
        }

        SyntaxHighlightingPreferences prefs = (SyntaxHighlightingPreferences) model;

        try {
            store.setValue(BACKGROUND_IS_SYSTEM_COLOR, prefs.isSystemColor);
            PreferenceConverter.setValue(store, BACKGROUND_RGB, prefs.backgroundRGB);

            int[] tokenTypes = prefs.getTokenTypes();

            store.setValue(TOKEN_TYPE_COUNT, tokenTypes.length);

            for (int i = 0; i < tokenTypes.length; i++) {
                TokenTypeInfo tokenTypeInfo = prefs.getTokenTypeInfo(tokenTypes[i]);

                TextAttribute tAttribute = tokenTypeInfo.getTextAttribute();

                store.setValue(TOKEN_TYPE_ID + i, tokenTypeInfo.getTypeId());

                store.setValue(TOKEN_DESCRIPTION + tokenTypeInfo.getTypeId(), tokenTypeInfo.getTypeDescription());
                store.setValue(TOKEN_FOREGROUND + tokenTypeInfo.getTypeId(), getRGBString(tAttribute.getForeground().getRGB()));
                store.setValue(TOKEN_IS_BOLD + tokenTypeInfo.getTypeId(), tokenTypeInfo.isBold());
            }
        } catch (Exception e) {
        }
    }

    public IPreferenceModel loadModel(IPreferenceStore store, BasePlugin plugin, IPreferenceModel model) {
        SyntaxHighlightingPreferences prefs = null;

        if (model == null)
            prefs = new SyntaxHighlightingPreferences(plugin.getSharedColors());
        else {
            if (!(model instanceof SyntaxHighlightingPreferences)) {
                throw new IllegalArgumentException("Cannot load instance of " + model.getClass().getName());
            }
            prefs = (SyntaxHighlightingPreferences) model;
        }

        prefs.isSystemColor = store.getBoolean(BACKGROUND_IS_SYSTEM_COLOR);
        prefs.backgroundRGB = PreferenceConverter.getColor(store, BACKGROUND_RGB);

        int tokenTypeCount = store.getInt(TOKEN_TYPE_COUNT);

        if (tokenTypeCount == 0) {
            restoreModelDefaults(prefs);
            return prefs;
        }

        for (int i = 0; i < tokenTypeCount; i++) {
            int typeId = store.getInt(TOKEN_TYPE_ID + i);

            String typeDesc = store.getString(TOKEN_DESCRIPTION + typeId);
            RGB foregroundRGB = parseRGB(store.getString(TOKEN_FOREGROUND + typeId));
            boolean isBold = store.getBoolean(TOKEN_IS_BOLD + typeId);

            int style = SWT.NULL;
            if (isBold)
                style = SWT.BOLD;

            prefs.setTextAttribute(typeId, typeDesc, foregroundRGB, null, style);
        }

        return prefs;
    }

    public void restoreModelDefaults(IPreferenceModel model) {

        if (model == null)
            throw new IllegalArgumentException("Cannot restore a null model");
        else if (!(model instanceof SyntaxHighlightingPreferences)) {
            throw new IllegalArgumentException("Cannot load instance of " + model.getClass().getName());
        }

        SyntaxHighlightingPreferences prefs = (SyntaxHighlightingPreferences) model;

        prefs.setBackgroundRGB(OALEditorConstants.DEFAULT_BACKGROUND, OALEditorConstants.DEFAULT_BACKGROUND_ISSYSTEMDEFAULT);

        prefs.setTextAttribute(
            OALTokenTypes.TOKEN_TYPE_single_line_comment,
            OALEditorConstants.DEFAULT_LABEL_SINGLE_LINE_COMMENT,
            OALEditorConstants.DEFAULT_FOREGROUND_SINGLE_LINE_COMMENT,
            null,
            OALEditorConstants.DEFAULT_STYLE_SINGLE_LINE_COMMENT);
        prefs.setTextAttribute(
            OALTokenTypes.TOKEN_TYPE_multi_line_comment,
            OALEditorConstants.DEFAULT_LABEL_MULTI_LINE_COMMENT,
            OALEditorConstants.DEFAULT_FOREGROUND_MULTI_LINE_COMMENT,
            null,
            OALEditorConstants.DEFAULT_STYLE_MULTI_LINE_COMMENT);
        prefs.setTextAttribute(
            OALTokenTypes.TOKEN_TYPE_string,
            OALEditorConstants.DEFAULT_LABEL_STRING,
            OALEditorConstants.DEFAULT_FOREGROUND_STRING,
            null,
            OALEditorConstants.DEFAULT_STYLE_STRING);
        prefs.setTextAttribute(
            OALTokenTypes.TOKEN_TYPE_keyword,
            OALEditorConstants.DEFAULT_LABEL_KEYWORD,
            OALEditorConstants.DEFAULT_FOREGROUND_KEYWORD,
            null,
            OALEditorConstants.DEFAULT_STYLE_KEYWORD);
        prefs.setTextAttribute(
            OALTokenTypes.TOKEN_TYPE_other,
            OALEditorConstants.DEFAULT_LABEL_OTHER,
            OALEditorConstants.DEFAULT_FOREGROUND_OTHER,
            null,
            OALEditorConstants.DEFAULT_STYLE_OTHER);
    }

    private RGB parseRGB(String rgbStr) {
        StringTokenizer tokenizer = new StringTokenizer(rgbStr, ", ");
        int blue = 0;
        int red = 0;
        int green = 0;
        String temp;

        temp = tokenizer.nextToken();
        red = Integer.parseInt(temp);

        temp = tokenizer.nextToken();
        green = Integer.parseInt(temp);

        temp = tokenizer.nextToken();
        blue = Integer.parseInt(temp);

        return new RGB(red, green, blue);
    }

    private String getRGBString(RGB rgb) {
        String rgbValue = rgb.toString();
        rgbValue = rgbValue.substring(rgbValue.indexOf("{") + 1);
        rgbValue = rgbValue.substring(0, rgbValue.indexOf("}"));
        return rgbValue;
    }

}
