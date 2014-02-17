//========================================================================
//
//File:      $RCSfile: SyntaxHighlightingPreferences.java,v $
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

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.source.ISharedTextColors;
import org.eclipse.jface.util.Assert;
import org.eclipse.jface.util.ListenerList;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;

import com.mentor.nucleus.bp.ui.preference.IPreferenceModel;
import com.mentor.nucleus.bp.ui.preference.IPreferenceModelStore;

/**
 * This class serves as single place for maintaining token types and their text attributes. 
 * Both PreferencesUI and Hilighting code will use this class 
 * @author babar_ali
 */

public class SyntaxHighlightingPreferences implements IPreferenceModel {
	public static final int MODEL_BACKGROUND_CHANGED = 0;
	public static final int MODEL_TOKEN_ATTRIBUTE_CHANGED = 2;
	public static final int MODEL_UNCHANGED = -1;

	protected static Token DEFAULT_TOKEN = new Token(new TextAttribute(new Color(Display.getCurrent(), 0, 0, 0)));

	static SyntaxHighlightingPreferencesStore store = null;
	static {
		store = new SyntaxHighlightingPreferencesStore();
	}

	protected ISharedTextColors sharedColors;
	protected Map type2TokenMap = new HashMap(10);

	protected boolean isSystemColor = true;
	protected RGB backgroundRGB;
	
	ListenerList listeners = new ListenerList();


	public SyntaxHighlightingPreferences(ISharedTextColors aSharedColors) {
	
		Assert.isNotNull(aSharedColors, "Argument of type ISharedTextColors cannot be null");

		sharedColors = aSharedColors;
	}
	
	public void setBackgroundToSystemDefault(){
		setBackgroundRGB(new RGB(255, 255, 255), true);	
	}

	public void setBackgroundToCustomColor(){
		setBackgroundRGB(getBackgroundRGB(), false);	
	}
	
	public void setBackgroundRGB(int red, int green, int blue) {
		setBackgroundRGB(new RGB(red, green, blue), false);
	}

	public void setBackgroundRGB(RGB aBackgroundRGB) {
		setBackgroundRGB(aBackgroundRGB, false);
	}

	protected void setBackgroundRGB(RGB aBackgroundRGB, boolean aIsSystemColor) {
		if(aBackgroundRGB.equals(backgroundRGB) && isSystemColor == aIsSystemColor){
			return;
		}
		
		this.backgroundRGB = aBackgroundRGB;
		this.isSystemColor = aIsSystemColor;
		
		fireModelChangeEvent(MODEL_BACKGROUND_CHANGED);
	}

	public boolean isSystemColor() {
		return isSystemColor;
	}

	public RGB getBackgroundRGB() {
		return backgroundRGB;
	}

	public Color getBackgroundColor() {
		return sharedColors.getColor(backgroundRGB);
	}

	public void setTextAttribute(int tokenType, RGB foregroundRGB) {
		setTextAttribute(tokenType, null, foregroundRGB, null, 0);
	}

	public void setTextAttribute(int tokenType, RGB foregroundRGB, int style) {
		setTextAttribute(tokenType, null, foregroundRGB, null, style);
	}

	public void setTextAttribute(int tokenType, RGB foregroundRGB, RGB backgroundRGB, int style) {
		setTextAttribute(tokenType, null, foregroundRGB, null, 0);
	}

	public void setTextAttribute(int tokenType, String tokenTypeDesc, RGB foregroundRGB, RGB backgroundRGB, int style) {
		Integer tokenTypeObject = new Integer(tokenType);

		TokenTypeInfo tokenTypeInfo = (TokenTypeInfo) type2TokenMap.get(tokenTypeObject);

		if (tokenTypeInfo == null) {
			if(tokenTypeDesc == null)
				throw new IllegalArgumentException("Cannot have null description when creating new token type for TokenType=" + tokenType);
				
			tokenTypeInfo = new TokenTypeInfo(tokenType, tokenTypeDesc, foregroundRGB, backgroundRGB, style);
			type2TokenMap.put(tokenTypeObject, tokenTypeInfo);
		} else {
			tokenTypeInfo.setTextAttributes(foregroundRGB, backgroundRGB, style);
		}
	}

	public TextAttribute getTextAttribute(int tokenType) {
		TokenTypeInfo tokenTypeInfo = getTokenTypeInfo(tokenType);
		return tokenTypeInfo.getTextAttribute();
	}

	protected void setDefaultToken(int tokenType, IToken token) {
		TokenTypeInfo tokenTypeInfo = getTokenTypeInfo(tokenType);
		tokenTypeInfo.setDefaultToken(token);
	}

	public IToken getDefaultToken(int tokenType) {
		TokenTypeInfo tokenTypeInfo = getTokenTypeInfo(tokenType);
		return tokenTypeInfo.getDefaultToken();
	}

	public TokenTypeInfo getTokenTypeInfo(int tokenType) {
		Integer tokenTypeObject = new Integer(tokenType);
		TokenTypeInfo tokenTypeInfo = (TokenTypeInfo) type2TokenMap.get(tokenTypeObject);
		if (tokenTypeInfo == null)
			throw new IllegalArgumentException("Type not registered with id " + tokenType);

		return tokenTypeInfo;
	}

	/**
	 * 
	 * @note PreferencesUI will be particularly able to get list of types and their descriptions 
	 *       to render list items
	 */
	public int[] getTokenTypes() {
		int[] types = new int[type2TokenMap.size()];

		Iterator iterator = type2TokenMap.keySet().iterator();
		int index = 0;
		while (iterator.hasNext()) {
			types[index++] = ((Integer) iterator.next()).intValue();
		}

		return types;
	}

	public Class getStoreClass() {
		return SyntaxHighlightingPreferencesStore.class;
	}

	public IPreferenceModelStore getStore() {
		return store;
	}

	public void synchronizeTo(IPreferenceModel model) {
		if (!(model instanceof SyntaxHighlightingPreferences)) {
			throw new IllegalArgumentException("Cannot synchronize to instance of " + model.getClass().getName());
		}

		SyntaxHighlightingPreferences syncTo = (SyntaxHighlightingPreferences) model;
		int[] tokenTypeIds = syncTo.getTokenTypes();

		for (int i = 0; i < tokenTypeIds.length; i++) {
			TokenTypeInfo info = syncTo.getTokenTypeInfo(tokenTypeIds[i]);
			setTextAttribute(tokenTypeIds[i], info.getTypeDescription(),  info.getForegroundRGB(), null,  info.getStyle());
		}
		
		setBackgroundRGB(syncTo.getBackgroundRGB(), syncTo.isSystemColor());
	}

	public void addModelChangeListener(IChangeListener listener) {
		listeners.add(listener);
	}

	public void removeModelChangeListener(IChangeListener listener) {
		listeners.remove(listener);
	}

	protected void fireModelChangeEvent(int change) {
		Object[] listenerArray = listeners.getListeners();
		for (int i = 0; i < listenerArray.length; ++i) {
		   ((IChangeListener) listenerArray[i]).modelChanged(this, change);
		}
	}
	
	public Object deepClone(){
		SyntaxHighlightingPreferences prefs = new SyntaxHighlightingPreferences(sharedColors);
		
		prefs.setBackgroundRGB(getBackgroundRGB(), isSystemColor());
		
		int[] tokenTypeIds = getTokenTypes();
		for(int i=0; i<tokenTypeIds.length; i++){
			TokenTypeInfo info = getTokenTypeInfo(tokenTypeIds[i]);
			prefs.setTextAttribute(tokenTypeIds[i], info.getTypeDescription(),  info.getForegroundRGB(), null,  info.getStyle());
		}
		
		return prefs;
	}

	public class TokenTypeInfo {

		int typeId = -1;
		String typeDescription;
		Token defaultToken;

		public TokenTypeInfo(int typeId, String typeDescription, RGB foreground) {
			this(typeId, typeDescription, foreground, null, 0);
		}

		public TokenTypeInfo(int aTypeId, String aTypeDescription, RGB aForeground, RGB aBackground, int aStyle) {

			typeId = aTypeId;
			typeDescription = aTypeDescription;

			setTextAttributes(aForeground, aBackground, aStyle);
		}

		public int getTypeId() {
			return typeId;
		}

		public String getTypeDescription() {
			return typeDescription;
		}

		public IToken getDefaultToken() {
			return defaultToken;
		}

		public TextAttribute getTextAttribute() {
			return (TextAttribute) defaultToken.getData();
		}

		public RGB getForegroundRGB() {
			return getTextAttribute().getForeground().getRGB();
		}
		
		public int getStyle(){
			return getTextAttribute().getStyle();
		}

		public boolean isBold() {
			return ((getStyle() & SWT.BOLD) == SWT.BOLD);
		}

		public void setTextAttributes(RGB aForeground, RGB aBackground, int aStyle) {
			Assert.isNotNull(aForeground);

			Color background = null;
			if (aBackground != null)
				sharedColors.getColor(aBackground);

			TextAttribute textAttribute = new TextAttribute(sharedColors.getColor(aForeground), background, aStyle);

			if (defaultToken == null) {
				defaultToken = new Token(textAttribute);
			} else {
				defaultToken.setData(textAttribute);
			}
			fireModelChangeEvent(MODEL_TOKEN_ATTRIBUTE_CHANGED);
		}

		protected void setDefaultToken(IToken token) {
			defaultToken = (Token) token;
			fireModelChangeEvent(MODEL_TOKEN_ATTRIBUTE_CHANGED);
		}
	}
}