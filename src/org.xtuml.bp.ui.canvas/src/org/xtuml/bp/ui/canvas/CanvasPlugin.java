package org.xtuml.bp.ui.canvas;
import java.io.IOException;
//=====================================================================
//
// File:      $RCSfile: CanvasPlugin.java,v $
// Version:   $Revision: 1.40 $
// Modified:  $Date: 2013/01/10 23:19:00 $
//
// (c) Copyright 2004-2014 Mentor Graphics Corporation All rights reserved.
//
//=====================================================================
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
//=====================================================================
//
// This class is responsible for loading the resources for the Eclipse
// BridgePoint Canvas plugin.
//
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;

import org.xtuml.bp.core.End_c;
import org.xtuml.bp.core.Ooaofooa;
import org.xtuml.bp.core.Pref_c;
import org.xtuml.bp.core.SystemModel_c;
import org.xtuml.bp.core.common.BridgePointPreferencesStore;
import org.xtuml.bp.core.common.ClassQueryInterface_c;
import org.xtuml.bp.core.common.ILogger;
import org.xtuml.bp.core.common.ModelRoot;
import org.xtuml.bp.core.common.NonRootModelElement;
import org.xtuml.bp.core.common.TraceLogger;
import org.xtuml.bp.core.common.TransactionManager;
import org.xtuml.bp.core.util.CoreUtil;
import org.xtuml.bp.ui.canvas.util.GraphicsUtil;
public class CanvasPlugin extends AbstractUIPlugin {
  private static CanvasPlugin plugin;
  private ResourceBundle resourceBundle;
  private CanvasModelListener modelChangeListener;
  private CanvasTransactionListener transactionListener;
  private static boolean isActivated;
  
  public CanvasPlugin() {
    super();
    plugin = this;
    try {
      resourceBundle = ResourceBundle.getBundle("org.xtuml.bp.ui.canvas.CanvasPluginResources");  //$NON-NLS-1$
    } catch (MissingResourceException x) {
      resourceBundle = null;
    }
  }
  public static CanvasPlugin getDefault() {
    return plugin;
  }
  public static IWorkspace getWorkspace() {
    return ResourcesPlugin.getWorkspace();
  }
  public static String getResourceString(String key) {
    ResourceBundle bundle = CanvasPlugin.getDefault().getResourceBundle();
    try {
      return bundle.getString(key);
    } catch (MissingResourceException e) {
      return key;
    }
  }
  public static ImageDescriptor getImageDescriptor(String name) {
    String iconPath = "icons/";
    try {
      URL installURL = getDefault().getBundle().getEntry("/"); //$NON-NLS-1$
      URL url = new URL(installURL, iconPath + name);
      return ImageDescriptor.createFromURL(url);
    } catch (MalformedURLException e) {
      // should not happen
      return ImageDescriptor.getMissingImageDescriptor();
    }
  }
  public ResourceBundle getResourceBundle() {
    return resourceBundle;
  }
  
  public static class DebugGraphicsCreation {
	  public DebugGraphicsCreation() {}
  }
  
  private void initializeCanvases() {
	Ooaofgraphics graphicsModelRoot = Ooaofgraphics.getDefaultInstance();
    IExtensionRegistry reg = Platform.getExtensionRegistry();
    IExtensionPoint extPt = reg.getExtensionPoint("org.xtuml.bp.core.editors"); //$NON-NLS-1$
    IExtension[] exts = extPt.getExtensions();
    IPreferenceStore prefStore = CanvasPlugin.getDefault().getPreferenceStore();
    for (int i = 0; i < exts.length; i++) {
      IConfigurationElement[] elems = exts[i].getConfigurationElements();
      for (int j = 0; j < elems.length; j++) {
        if (elems[j].getName().equals("editor")) { //$NON-NLS-1$
          IConfigurationElement[] modelEditorExtensions = elems[j].getChildren();
          for (int k = 0; k < modelEditorExtensions.length; k++) {
        	IConfigurationElement modelEditorExtension = modelEditorExtensions[k];
			int value = 0;
            if (modelEditorExtension.getName().equals("symbol")) {
              String className = modelEditorExtension.getAttribute("class");
              if (!className.equals("")) {
                String symTypDef = modelEditorExtension.getAttribute("elemType");
                if (symTypDef != null) {
                  int elemType = getValueFor(symTypDef); 
                  String elemName = modelEditorExtension.getAttribute("name");
                  ElementSpecification_c es = new ElementSpecification_c(graphicsModelRoot);
                  es.setName(elemName);
                  es.setOoa_type(elemType);
                  IConfigurationElement[] col = modelEditorExtension.getChildren("color");
                  if ( col != null && col.length > 0 ) 
                  {
                  int r = Integer.parseInt(col[0].getAttribute("r"));
                  int g = Integer.parseInt(col[0].getAttribute("g"));
                  int b = Integer.parseInt(col[0].getAttribute("b"));
                  String clrStr = Integer.toString(r) + "," + Integer.toString(g) + "," + Integer.toString(b);
                  String prefStr = "CanvasPreferencePage." + elemName + ".intColor";
                  prefStore.setDefault(prefStr, clrStr);
                  clrStr = prefStore.getString(prefStr);
                  if (clrStr == null || clrStr.equals("")) {
                    // The preference is not set, set it to the default
                    prefStore.setToDefault(prefStr);
                    clrStr = prefStore.getString(prefStr);
                  }
                  es.setInternal(parseColor(clrStr));
                  }
                  else
                  {
                	  es.setHasnointeriorcolor(true);
	                  es.setInternal(parseColor("255,255,255"));
                  }                  
                  try {
                    es.setRepresents(Class.forName(className));
                  } catch (ClassNotFoundException e) {
                    logError("Specified metamodel class not found: ", e);
                  }
                  es.setCreator(modelEditorExtension.getAttribute("createMethod"));
                  es.setIconname(modelEditorExtension.getAttribute("icon"));
                  String defHStr = modelEditorExtension.getAttribute("defaultHeight");
                  String defWStr = modelEditorExtension.getAttribute("defaultWidth");
                  if(defHStr != null) {
                    int defH = Integer.parseInt(defHStr);
                    es.setDefaultheight(defH);
                  }
                  if(defWStr != null) {
                    int defW = Integer.parseInt(defWStr);
                    es.setDefaultwidth(defW);
                  }
                  String isFixedAspectStr = modelEditorExtension.getAttribute("fixedAspect");
                  if(isFixedAspectStr != null && isFixedAspectStr.equals("true")) {
                		  es.setIsfixedaspectratio(true);
                    }
                  String isFixedSizeStr = modelEditorExtension.getAttribute("fixedSize");
                  if(isFixedSizeStr != null && isFixedSizeStr.equals("true")) {
                      es.setIsfixedsize(true);
                    }
                  String isAnchorHost = modelEditorExtension.getAttribute("isAnchorHost");
                  if(isAnchorHost != null && isAnchorHost.equals("true")) {
                	  es.setIsanchorhost(true);
                  }
                  String hasFloatingStr = modelEditorExtension.getAttribute("floatingText");
                  if(hasFloatingStr != null && hasFloatingStr.equals("true")) {
                      es.setHasfloatingtext(true);
                    }
                  String layer = modelEditorExtension.getAttribute("layer");
                  if(layer != null ) {
                	  try {
                        es.setLayer(Integer.parseInt(layer));
                	  }
                      catch (NumberFormatException e) {
                          logError("Could not parse symbol '"+ elemName +"' layer: ", e);
                      }
                  }
                  String antiAliased = modelEditorExtension.getAttribute("antiAliased");
                  if(antiAliased != null) {
                	  if(antiAliased.equalsIgnoreCase("true")) {
                		  es.setAntialiased(true);
                	  } else {
                		  es.setAntialiased(false);
                	  }
                  } else {
                	  // default to false until drawing performance is enhanced
                	  es.setAntialiased(false);
                  }
                  String isTransparent =  modelEditorExtension.getAttribute("isTransparent");
                  if (isTransparent != null && isTransparent.equalsIgnoreCase("true")) {
                	  es.setIstransparent(true);
                  } else {
                	  es.setIstransparent(false);
                  }
                  String creationRule =  modelEditorExtension.getAttribute("creationRule");
                  if (creationRule != null) {
                	  es.setCreationrule(creationRule);
                  } else {
                	  es.setCreationrule("manual");
                  }
                  String hasNameCompartment = modelEditorExtension.getAttribute("hasNameCompartment");
                  if(hasNameCompartment != null) { 
                	  if(hasNameCompartment.equalsIgnoreCase("true")) {
                		  es.setHasnamecompartment(true);
                	  } else
                		  es.setHasnamecompartment(false);
                  }
                  
                  // setup element spec subtypes
                  String symbolType = modelEditorExtension.getAttribute("symbolType");
                  es.setSymboltype(symbolType);
        		  if (symbolType.equalsIgnoreCase("connector")) {
        			  ConnectorSpecification_c cs = new ConnectorSpecification_c(
        					  graphicsModelRoot);
        			  cs.relateAcrossR200To(es);
        		  }
                  // setup dependencies
                  addClientClassDependency(es, className);
                  IConfigurationElement[] depends = modelEditorExtension.getChildren("dependsOn");
                  for ( int d = 0; d < depends.length; ++d )
                  {
                    addClientClassDependency(es, depends[d].getAttribute("class"));
                  }
                  // setup auto resize
                  String causeAutoResize = modelEditorExtension.getAttribute("causeAutoResize");
                  if(causeAutoResize != null) {
                	  if(causeAutoResize.equalsIgnoreCase("false")) {
                		  es.setCauseautoresize(false);
                	  }
                  } else {
                	  es.setCauseautoresize(true);
                  }
                }
              }
            } // end if element name is Symbol
          }
          // Note that this section is loading only "defaultfor" definitions.
          // "symbol" definitions follow this
          for (int k = 0; k < modelEditorExtensions.length; k++) {
          	int value = 0;
          	IConfigurationElement modelEditorExtension = modelEditorExtensions[k];
            if (modelEditorExtension.getName().equals("defaultFor")) {
              String className = modelEditorExtension.getAttribute("class");
              if (!className.equals("")) {
                String mdlTypDef = modelEditorExtension.getAttribute("modelType");
                String ooaTypDef = modelEditorExtension.getAttribute("ooaType");
                if (mdlTypDef != null && !mdlTypDef.equals("")) {
				  int modelType = getValueFor(mdlTypDef);         	
                  int ooaType = getValueFor(ooaTypDef);                	
                  IConfigurationElement[] col = modelEditorExtension.getChildren("color");
                  int r = Integer.parseInt(col[0].getAttribute("r"));
                  int g = Integer.parseInt(col[0].getAttribute("g"));
                  int b = Integer.parseInt(col[0].getAttribute("b"));
                  String diagName = modelEditorExtension.getAttribute("name");
                  ModelSpecification_c ms = new ModelSpecification_c(graphicsModelRoot);
                  ms.setName(diagName);
                  ms.setModel_type(modelType);
                  ms.setOoa_type(ooaType);
                  String clrStr = Integer.toString(r) + "," + Integer.toString(g) + "," + Integer.toString(b);
                  String prefStr = "CanvasPreferencePage." + diagName + ".bkgColor";
                  prefStore.setDefault(prefStr, clrStr);
                  clrStr = prefStore.getString(prefStr);
                  if (clrStr == null || clrStr.equals("")) {
                    // The preference is not set, set it to the default
                    prefStore.setToDefault(prefStr);
                    clrStr = prefStore.getString(prefStr);
                  }
                  ms.setBackground(parseColor(clrStr));
                  try {
                    ms.setRepresents(Class.forName(className));
                  } catch (ClassNotFoundException e) {
                    logError("Specified metamodel class not found: ", e);
                  }
                  IConfigurationElement[] symbols = modelEditorExtension.getChildren("validSymbol");
                  for (int m = 0; m < symbols.length; m++) {
                    final String name = symbols[m].getAttribute("name");
                    final String symbolClassName = symbols[m].getAttribute("class");
                    String symbolOoaTypeDef = symbols[m].getAttribute("elemType");
                    int symbolElemType = -1;
                    if(symbolOoaTypeDef != null) {
                    	symbolElemType = getValueFor(symbolOoaTypeDef);
                    }
                    final int finalElemType = symbolElemType;
                    ElementSpecification_c elem = ElementSpecification_c.ElementSpecificationInstance(graphicsModelRoot, new ClassQueryInterface_c() {
                      public boolean evaluate(Object candidate) {
                    	  boolean result = false;
                    	  ElementSpecification_c elemSpec = (ElementSpecification_c) candidate;
                    	  if(elemSpec.getName().equals(name)) {
                    		  result = true;
                    	  }
                    	  if(finalElemType != -1) {
                    		  if(elemSpec.getOoa_type() == finalElemType)
                    			  result = true;
                    		  else
                    			  result = false;
                    	  }
                    	  if(symbolClassName != null) {
                    		  try {
								if(elemSpec.getRepresents() == Class.forName(symbolClassName) && elemSpec.getName().equals(name)) {
									  result = true;
								  } else {
									  result = false;
								  }
							} catch (ClassNotFoundException e) {
								logError("Specified metamodel class not found: ", e);
							}
                    	  }
                    	  return result;
                      }
                    });
                    if (elem != null) {
                      // get the tool category from specification
                      String toolCategory = symbols[m].getAttribute("tool_category");
                      if(toolCategory != null) {
                    	  elem.setToolcategory(toolCategory);
                      }
                      ElementInModelSpecification_c eims = new ElementInModelSpecification_c(graphicsModelRoot);
                      ms.relateAcrossR11To(eims);
                      elem.relateAcrossR11To(eims);
                    }
                  }
                }
              }
            }
          }
          //Note that this section is only loading "symbol" definitions.
          // "defaultfor" definitions preceeded this
          for (int k = 0; k < modelEditorExtensions.length; k++) {
        	  IConfigurationElement modelEditorExtension = modelEditorExtensions[k];
        	  if (modelEditorExtension.getName().equals("symbol")) {
        		  final String finalName = modelEditorExtension.getAttribute("name");
        		  String symTypDef = modelEditorExtension.getAttribute("elemType");
                  if (symTypDef != null) {
                    final int elemType = getValueFor(symTypDef);
	        		  ElementSpecification_c es = ElementSpecification_c.
	        		  ElementSpecificationInstance(graphicsModelRoot,new ClassQueryInterface_c() {
	        			  
	        			  public boolean evaluate(Object candidate) {
	        				  ElementSpecification_c es = (ElementSpecification_c) candidate;
	        				  return es.getName().equals(finalName) && es.getOoa_type() == elemType;
	        			  }
	        			  
	        		  });
	        		  // setup element spec subtypes
	        		  String symbolType = modelEditorExtension.getAttribute("symbolType");
	        		  if (symbolType.equalsIgnoreCase("connector")) {
	        			  ConnectorSpecification_c cs = ConnectorSpecification_c.getOneTS_CSPOnR200(es);
	        			  IConfigurationElement[] terms = modelEditorExtension
	        			                                       .getChildren("terminator");
	        			  for (int l = 0; l < terms.length; l++) {
	        				  TerminalSpecification_c tms = new TerminalSpecification_c(
	        						  graphicsModelRoot);
	        				  tms.setName(terms[l].getAttribute("name"));
	        				  boolean isStart = terms[l].getAttribute("end")
	        				  .equals("start");
	        				  if (isStart) {
	        					  cs.relateAcrossR202To(tms);
	        				  } else {
	        					  cs.relateAcrossR203To(tms);
	        				  }
	        				  final String symbol = terms[l]
	        				                              .getAttribute("symbol");
	        				  final String symbolClassName = terms[l]
	        				                                       .getAttribute("symbolClass");
	        				  ElementSpecification_c els = ElementSpecification_c
	        				  	.ElementSpecificationInstance(
	        						  graphicsModelRoot,
	        						  new ClassQueryInterface_c() {
	        							  public boolean evaluate(
	        									  Object candidate) {
	        								  boolean result = false;
	        								  ElementSpecification_c elemSpec = (ElementSpecification_c) candidate;
	        								  if (elemSpec.getName()
	        										  .equals(symbol)) {
	        									  result = true;
	        								  }
	        								  
	        								  if (symbolClassName != null) {
	        									  try {
	        										  if (elemSpec
	        												  .getRepresents() == Class
	        												  .forName(symbolClassName) && (elemSpec.getName().equals(symbol))) {
	        											  result = true;
	        										  } else {
	        											  result = false;
	        										  }
	        									  } catch (ClassNotFoundException e) {
	        										  logError(
	        												  "Specified metamodel class not found: ",
	        												  e);
	        									  }
	        								  }
	        								  return result;
	        							  }
	        						  });
	        				  String type = terms[l].getAttribute("type");
	        				  if (els != null) {
	        					  if (type.equals("shape")) {
	        						  ShapeTerminal_c sst = new ShapeTerminal_c(
	        								  graphicsModelRoot);
	        						  sst.relateAcrossR201To(tms);
	        						  ShapeSpecification_c ss = ShapeSpecification_c
	        						  .getOneTS_SSPOnR200(els);
	        						  if (ss != null) {
	        							  sst.relateAcrossR204To(ss);
	        						  }
	        					  } else if (type.equals("connector")) {
	        						  ConnectorTerminal_c cnt = new ConnectorTerminal_c(
	        								  graphicsModelRoot);
	        						  cnt.relateAcrossR201To(tms);
	        						  ConnectorSpecification_c cns = ConnectorSpecification_c
	        						  .getOneTS_CSPOnR200(els);
	        						  if (cns != null) {
	        							  cnt.relateAcrossR205To(cns);
	        							  String terminatesAt = terms[l]
	        							                              .getAttribute("terminatesAt");
	        							  if (terminatesAt.equals("start")) {
	        								  cnt
	        								  .setTerminatesat(End_c.Start);
	        							  } else if (terminatesAt
	        									  .equals("middle")) {
	        								  cnt
	        								  .setTerminatesat(End_c.Middle);
	        							  } else if (terminatesAt
	        									  .equals("end")) {
	        								  cnt.setTerminatesat(End_c.End);
	        							  }
	        						  }
	        					  }
	        				  } else if (type.equals("whitespace")) {
	        					  WhitespaceTerminal_c wst = new WhitespaceTerminal_c(
	        							  graphicsModelRoot);
	        					  wst.relateAcrossR201To(tms);
	        				  }
							  IConfigurationElement[] automaticCreations = terms[l]
											.getChildren("automaticCreation");
							  for(IConfigurationElement automaticCreation : automaticCreations) {
								  final String creationSymbol = automaticCreation.getAttribute("symbol");
		        				  final String creationSymbolClassName = automaticCreation
			                                       .getAttribute("symbolClass");
		        				  els = ElementSpecification_c
		        						  	.ElementSpecificationInstance(
		        						  			graphicsModelRoot,
		        						  			new ClassQueryInterface_c() {
		        						  				public boolean evaluate(
		        						  						Object candidate) {
		        						  					boolean result = false;
		        						  					ElementSpecification_c elemSpec = (ElementSpecification_c) candidate;
		        						  					if (elemSpec.getName()
		        						  							.equals(creationSymbol)) {
		        						  						result = true;
		        						  					}
							  
		        						  					if (creationSymbolClassName != null) {
		        						  						try {
		        						  							if (elemSpec
		        						  									.getRepresents() == Class
		        						  									.forName(creationSymbolClassName) && (elemSpec.getName().equals(creationSymbol))) {
		        						  								result = true;
		        						  							} else {
		        						  								result = false;
		        						  							}
		        						  						} catch (ClassNotFoundException e) {
		        						  							logError(
		        						  									"Specified metamodel class not found: ",
		        						  									e);
		        						  						}
		        						  					}
		        						  					return result;
		        						  				}
		        						  			});
		        				  if(els != null) {
		        					  tms.relateAcrossR209To(els);
		        				  }
							  }
	        			  }
	        		  } else if (symbolType.equalsIgnoreCase("shape")) {
	        			  ShapeSpecification_c ss = new ShapeSpecification_c(
	        					  graphicsModelRoot);
	        			  ss.relateAcrossR200To(es);
	        			  
	        			  NoncontainingShapeSpecification_c ncss = new NoncontainingShapeSpecification_c(
	        					  graphicsModelRoot);
	        			  ncss.relateAcrossR208To(ss);
	        		  } else if (symbolType.equalsIgnoreCase("container")) {
	        			  ShapeSpecification_c ss = new ShapeSpecification_c(
	        					  graphicsModelRoot);
	        			  ss.relateAcrossR200To(es);

	        			  ContainingShapeSpecification_c css = new ContainingShapeSpecification_c(
	        					  graphicsModelRoot);
	        			  css.relateAcrossR208To(ss);
	        		  }
	        	  }
        	  }
          }
        }
      }
    } // end for all extensions
    // Auto reconcile instance left until last to ensure that all
    // symbol specifications that might be referenced are created
    
    
    // Here to set the association between ARS and the ES //
    
    for (int i = 0; i < exts.length; i++) {
      IConfigurationElement[] elems = exts[i].getConfigurationElements();
      for (int j = 0; j < elems.length; j++) {
        if (elems[j].getName().equals("editor")) { //$NON-NLS-1$
          IConfigurationElement[] modelEditorExtensions = elems[j].getChildren();
          for (int k = 0; k < modelEditorExtensions.length; k++) {
        	IConfigurationElement  modelEditorExtension = modelEditorExtensions[k];
            if (modelEditorExtension.getName().equals("symbol")) {
              // setup autoReconcile
              IConfigurationElement[] autoReconcile = modelEditorExtension.
                                                   getChildren("autoReconcile");
              for ( int e = 0; e < autoReconcile.length; ++e )
              {
     			AutoReconciliationSpecification_c ars =
     				   new AutoReconciliationSpecification_c(graphicsModelRoot);
                ars.setBridge_numelements(autoReconcile[e].
                		                           getAttribute("Bridge_NumElements"));
                ars.setBridge_getelementid(autoReconcile[e].
                		                         getAttribute("Bridge_GetElementID"));
                ars.setBridge_connectorexists(autoReconcile[e].
                		                   getAttribute("Bridge_ConnectorExists"));
				if (ars.getBridge_connectorexists() == null) {
					ars.setBridge_connectorexists("");
				}
				ars.setBridge_numconnectors(autoReconcile[e].getAttribute("Bridge_NumConnectors"));
				if (ars.getBridge_numconnectors() == null) {
					ars.setBridge_numconnectors("");
				}
                ars.setBridge_getconnectoridconditional(autoReconcile[e].
  		                 getAttribute("Bridge_GetConnectorIDConditional"));
                 if (ars.getBridge_getconnectoridconditional() == null) {
               	  ars.setBridge_getconnectoridconditional("");
                 }                
                ElementSpecification_c scanTarget = locateEsByNameAndClassType(
                		       graphicsModelRoot, modelEditorExtension.getAttribute("name"), modelEditorExtension.getAttribute("class")); 
                ars.relateAcrossR29To(scanTarget);
                ElementSpecification_c targetSymbol = locateEsByNameAndClassType(
                		                                graphicsModelRoot,
                		         autoReconcile[e].getAttribute("targetSymbol"), null);
                ars.relateAcrossR31To(targetSymbol);
                String ssSpec = autoReconcile[e].getAttribute("sourceSymbol");
                if (ssSpec != null) {
    		      ars.relateAcrossR30To(locateEsByNameAndClassType(graphicsModelRoot,
    		    		                                               ssSpec, null));
                }
                else {
                  ars.relateAcrossR30To(targetSymbol);
                }
              }
              
              
              
            }
            
            else if (modelEditorExtension.getName().equals("defaultFor")) {
                // setup autoReconcile
                IConfigurationElement[] autoReconcile = modelEditorExtension.
                                                     getChildren("autoReconcile");
                for ( int e = 0; e < autoReconcile.length; ++e )
                {
       			AutoReconciliationSpecification_c ars =
       				   new AutoReconciliationSpecification_c(graphicsModelRoot);
                  ars.setBridge_numelements(autoReconcile[e].
                  		                           getAttribute("Bridge_NumElements"));
                  ars.setBridge_getelementid(autoReconcile[e].
                  		                         getAttribute("Bridge_GetElementID"));
                  ars.setBridge_connectorexists(autoReconcile[e].
                  		                   getAttribute("Bridge_ConnectorExists"));
  				  if (ars.getBridge_connectorexists() == null) {
					ars.setBridge_connectorexists("");
  				  }
                  ars.setBridge_numconnectors(autoReconcile[e].
                  		                 getAttribute("Bridge_NumConnectors"));
                  if (ars.getBridge_numconnectors() == null) {
                    ars.setBridge_numconnectors("");
                  }
                  ars.setBridge_getconnectoridconditional(autoReconcile[e].
   		                 getAttribute("Bridge_GetConnectorIDConditional"));
                  if (ars.getBridge_getconnectoridconditional() == null) {
                	  ars.setBridge_getconnectoridconditional("");
                  }
                // ElementSpecification_c scanTarget = locateEsByNameAndClassType(
           		       //graphicsModelRoot, modelEditorExtension.getAttribute("name"), modelEditorExtension.getAttribute("class")); 
                  ModelSpecification_c modelSpec = locateMesByNameAndClassType(graphicsModelRoot, modelEditorExtension.getAttribute("name"), modelEditorExtension.getAttribute("class")); 
                  ars.relateAcrossR33To(modelSpec);
                  ElementSpecification_c targetSymbol = locateEsByNameAndClassType(
                  		                                graphicsModelRoot,
                  		         autoReconcile[e].getAttribute("targetSymbol"),  autoReconcile[e].getAttribute("class"));
                  ars.relateAcrossR31To(targetSymbol);
                  String ssSpec = autoReconcile[e].getAttribute("sourceSymbol");
                  if (ssSpec != null) {
      		      ars.relateAcrossR30To(locateEsByNameAndClassType(graphicsModelRoot,
      		    		                                               ssSpec, null));
                  }
                  else {
                    ars.relateAcrossR30To(targetSymbol);
                  }
                }}
          }
        }
      }
    } 
  }
  private ModelSpecification_c locateMesByNameAndClassType(Ooaofgraphics modelRoot,
		String name, String className) {
	  ModelSpecification_c [] modelSpecs =
		  ModelSpecification_c.ModelSpecificationInstances(modelRoot);
      for (int i=0; i < modelSpecs.length; i++) {
	       if(className != null) {
		  // verify that the class name is the same
		     if(!modelSpecs[i].getRepresents().getName().equals(className)) {
			  continue;
		  }
	  }
	      if (modelSpecs[i].getName().equals(name)) {
		     return modelSpecs[i];
	  }
}
Throwable e = new Throwable(name);
logError("Specified Symbol specification not found: ", e);
return null;
	
}
private ElementSpecification_c locateEsByNameAndClassType(ModelRoot modelRoot,
		                                                          String name, String className) {
	  ElementSpecification_c [] ess =
                ElementSpecification_c.ElementSpecificationInstances(modelRoot);
	  for (int i=0; i < ess.length; i++) {
		  if(className != null) {
			  // verify that the class name is the same
			  if(!ess[i].getRepresents().getName().equals(className)) {
				  continue;
			  }
		  }
		  if (ess[i].getName().equals(name))
		  {
			  return ess[i];
		  }
	  }
	  Throwable e = new Throwable(name);
      logError("Specified Symbol specification not found: ", e);
	  return null;
  }
  private void hookListeners() {
      modelChangeListener = new CanvasModelListener();
      Ooaofooa.getDefaultInstance().addModelChangeListener(modelChangeListener);
      transactionListener = new CanvasTransactionListener();
      TransactionManager.getSingleton().addTransactionListener(transactionListener);
  }
  private static void addClientClassDependency(
      ElementSpecification_c es,
      final String className)
  {
        ClientClassDependency_c ccd = new ClientClassDependency_c(Ooaofgraphics.getDefaultInstance());
        ccd.relateAcrossR17To(es);
        try
        {
            ccd.setRepresents(Class.forName(className));
        }
        catch (ClassNotFoundException ex)
        {
            ccd.setRepresents(null);
        }
  }
  public static Color parseColor(String value) {
    String[] vals = value.split(",");
    int r = Integer.parseInt(vals[0]);
    int g = Integer.parseInt(vals[1]);
    int b = Integer.parseInt(vals[2]);
    return new Color(Display.getDefault(), new RGB(r, g, b));
  }
  public static String serializeColor(Color value) {
    return Integer.toString(value.getRed()) + "," + Integer.toString(value.getGreen()) + "," + Integer.toString(value.getBlue());
  }
  public static void logError( String msg, Throwable e )
  {
    CanvasPlugin cp = getDefault();
    // plugin is null when running unit tests
    if (cp != null) 
    {
        Status status =
            new Status(
                IStatus.ERROR,
                (String)cp.getBundle().getHeaders().get(Constants.BUNDLE_NAME),
                IStatus.ERROR,
                msg,
                e);
        getDefault().getLog().log(status);
    }
    else 
    {
        System.err.println(msg);
        if ( e != null )
        {
            e.printStackTrace();
        }
    }
  }
  public static void logTraceMsg(int filterType, String filterValue, String message )
  {
	  if (Ooaofgraphics.log != null) {
		  Ooaofgraphics.log.println(filterType,
				filterValue,
				message);
	  }
  }  
  public static boolean stringBufferLoggingIsEnabled() {
	  boolean isEnabled = false;
	  if (Ooaofgraphics.log != null && Ooaofgraphics.log instanceof TraceLogger) {
		  TraceLogger traceLog = (TraceLogger)Ooaofgraphics.log;
		  isEnabled = traceLog.isUsingTheStringBuffer();
	  }
	  return isEnabled;
  }
  /**
   * 
   * @param filename A name for the file to write to. This is just a name NOT 
   *                 a fully qualified. The file is put in the workspace.
   */
  public static void writeTraceLog(String filename) {
	  if(Ooaofgraphics.log != null && Ooaofgraphics.log instanceof TraceLogger) {
		  TraceLogger traceLog = (TraceLogger)Ooaofgraphics.log;
		  try {
			String workspaceFolder = ResourcesPlugin.getWorkspace().getRoot().getLocation().toString();
		    traceLog.stringLogger.write(workspaceFolder + "/" + filename);
		  } catch (IOException ioexp) {
			  CanvasPlugin.logError("", ioexp);
		  }
	  }
  }
  /* (non-Javadoc)
   * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
   */
  public void start(BundleContext context) throws Exception
  {
	if (!CoreUtil.IsRunningHeadless) {
	    super.start(context);
	        
	    initializeCanvases();
	    hookListeners();
		CanvasPlugin.isActivated = true;
	} else {
		CanvasPlugin.isActivated = false;
	}
  }

    /* (non-Javadoc)
     * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
     */
    public void stop(BundleContext context) throws Exception
    {
    	if (CanvasPlugin.isActivated) {
	        super.stop(context);
	        
	        Ooaofooa.getDefaultInstance().removeModelChangeListener(modelChangeListener);
	        
	        TransactionManager.getSingleton().removeTransactionListener(transactionListener);
    	}
    }
    
  private static int getValueFor(String enumeration) {
	try
	{
	   // Get last part and first part 	
	   String[] stringItems = enumeration.split("\\.");
	   String lastPart = stringItems[stringItems.length - 1];
	   String firstPart = "";
	   for (int a = 0; a < stringItems.length - 2; a++)
	   		firstPart = firstPart.concat(stringItems[a] + ".");
	   firstPart = firstPart.concat(stringItems[stringItems.length - 2]);					   
	   
	   Class c = Class.forName(firstPart); // get the class
		try {
			Field enumerator = c.getField(lastPart); // get the field (lastPart) from the class
			try {
				return enumerator.getInt(c); // get the value of the field
			} catch (IllegalArgumentException e2) {
				
				e2.printStackTrace();
			} catch (IllegalAccessException e2) {
				
				e2.printStackTrace();
			}
		} catch (SecurityException e1) {
			
			e1.printStackTrace();
		} catch (NoSuchFieldException e1) {
			
			e1.printStackTrace();
		}		
	  }
	
	catch (ClassNotFoundException e)
	{
		e.printStackTrace();
	}
	return 0;
  }

  	
  	/**
  	 * This is used to associate a Graphical instance type with an ooaofooa 
  	 * instance. This routine was originally created in CanvasModelListener.java, 
  	 * but it has been reused in other places and therefore it was moved to 
  	 * this location to make it easier to find and reuse because it is NOT 
  	 * specific to the CanvasModelListener.
  	 * 
  	 * @param model
  	 */
	public static void setGraphicalRepresents(final Model_c model) {
		Ooaofooa modelRoot = Ooaofooa.getInstance(model.getModelRoot().getId());
		if (model.getRepresents() == null) {
			ModelSpecification_c ms = ModelSpecification_c.getOneGD_MSOnR9(model);
			if (ms == null) {
				ms = ModelSpecification_c.ModelSpecificationInstance(Ooaofgraphics.getDefaultInstance(),
						new ClassQueryInterface_c() {

							@Override
							public boolean evaluate(Object candidate) {
								return ((ModelSpecification_c) candidate).getModel_type() == model
										.getModel_typeCachedValue()
										&& ((ModelSpecification_c) candidate).getOoa_type() == model
												.getOoa_typeCachedValue();
							}
						});

				if (ms != null) {
					ms.relateAcrossR9To(model);
				}
			}
			model.setRepresents(Cl_c.Getinstancefromooa_id(modelRoot, model.getOoa_id(), model.getOoa_type()));
		}
		ArrayList<GraphicalElement_c> ges = new ArrayList<GraphicalElement_c>(
				Arrays.asList(GraphicalElement_c.getManyGD_GEsOnR1(model)));
		ges.addAll(Arrays
				.asList(GraphicalElement_c.getManyGD_GEsOnR32(ElementInSuppression_c.getManyGD_EISsOnR32(model))));
		for (Iterator<GraphicalElement_c> it = ges.iterator(); it.hasNext();) {
			final GraphicalElement_c ge = it.next();
			ElementSpecification_c es = ElementSpecification_c.getOneGD_ESOnR10(ge);
			if (es == null) {
				ElementSpecification_c spec = ElementSpecification_c
						.ElementSpecificationInstance(Ooaofgraphics.getDefaultInstance(), new ClassQueryInterface_c() {

							@Override
							public boolean evaluate(Object candidate) {
								return ((ElementSpecification_c) candidate).getOoa_type() == ge
										.getOoa_typeCachedValue();
							}
						});
				if (spec != null) {
					spec.relateAcrossR10To(ge);
				}
			}
			Object instanceFromId = null;
			if (model.getRepresents() instanceof SystemModel_c) {
				instanceFromId = Cl_c.Getinstancefromooa_id((SystemModel_c) model.getRepresents(), ge.getOoa_id(),
						ge.getOoa_type());
			} else {
				instanceFromId = Cl_c.Getinstancefromooa_id(modelRoot, ge.getOoa_id(), ge.getOoa_type());
			}
			if (instanceFromId != null && (ge.getRepresents() == null || ge.getRepresents() != instanceFromId)) {
				ge.setRepresents(instanceFromId);
			}
		}
	}

	/**
	 * This routine was originally created in CanvasModelListener.java, but it has been 
	 * reused in other places and therefore it was moved to this location to make it 
	 * easier to find and reuse because it is NOT specific to the CanvasModelListener.
  	 * 
	 * @param canvas
	 * @param o
	 * @return True if the given Object is a part of the specified canvas (GD_MD) and false if it is not.
	 */
	public static boolean classInView(Model_c canvas, Object o) {
		if (canvas != null) {
			if (canvas.getRepresents() == null) {
				if (Cl_c.Getooa_idfrominstance(o).equals(canvas.getOoa_id())
						&& GraphicsUtil.getOoaType(o) == canvas.getOoa_type())
					return true;
			} else {
				if (canvas.getRepresents() == o)
					return true;
			}
			ModelSpecification_c ms = ModelSpecification_c.getOneGD_MSOnR9(canvas);
			ElementInModelSpecification_c[] ems_set = ElementInModelSpecification_c.getManyGD_EMSsOnR11(ms);
			ElementSpecification_c[] es_set = ElementSpecification_c.getManyGD_ESsOnR11(ems_set);
			ClientClassDependency_c[] ccd_set = ClientClassDependency_c.getManyGD_CCDsOnR17(es_set);
			final Class<?> depClass = o.getClass();
			for (int i = 0; i < ccd_set.length; ++i) {
				if (ccd_set[i].getRepresents() == depClass) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Find and return the GD_GE instance that graphically represents the specified Object instance
	 * within the given modelRoot
	 * 
	 * @param modelRoot The model root to search 
	 * @param o The ooaofooa instance we are looking for
	 * @return 
	 */
	static public GraphicalElement_c getGraphicalElement(Ooaofgraphics modelRoot, Object o) {
		GraphicalElement_c result = null;
		GraphicalElement_c[] ges = GraphicalElement_c.GraphicalElementInstances(modelRoot);
		int type = GraphicsUtil.getElementOoaType(o, modelRoot);
		for (int j = 0; j < ges.length; j++) {
			if ((ges[j].getRepresents() != null && ges[j].getRepresents() == o)
					|| (ges[j].getOoa_id().equals(((NonRootModelElement) o).Get_ooa_id())
							&& ges[j].getOoa_type() == type)) {
				result = ges[j];
				break;
			}
		}
		return result;
	}

}