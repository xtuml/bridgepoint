package com.mentor.nucleus.bp.ui.canvas;
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

import com.mentor.nucleus.bp.core.End_c;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.core.common.ModelRoot;
import com.mentor.nucleus.bp.core.common.TransactionManager;
import com.mentor.nucleus.bp.core.util.CoreUtil;
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
      resourceBundle = ResourceBundle.getBundle("com.mentor.nucleus.bp.ui.canvas.CanvasPluginResources");  //$NON-NLS-1$
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
  private void initializeCanvases() {
	Ooaofgraphics graphicsModelRoot = Ooaofgraphics.getDefaultInstance();
    IExtensionRegistry reg = Platform.getExtensionRegistry();
    IExtensionPoint extPt = reg.getExtensionPoint("com.mentor.nucleus.bp.core.editors"); //$NON-NLS-1$
    IExtension[] exts = extPt.getExtensions();
    IPreferenceStore prefStore = CanvasPlugin.getDefault().getPreferenceStore();
    for (int i = 0; i < exts.length; i++) {
      IConfigurationElement[] elems = exts[i].getConfigurationElements();
      for (int j = 0; j < elems.length; j++) {
        if (elems[j].getName().equals("editor")) { //$NON-NLS-1$
          IConfigurationElement[] defs = elems[j].getChildren();
          for (int k = 0; k < defs.length; k++) {
			int value = 0;
            if (defs[k].getName().equals("symbol")) {
              String className = defs[k].getAttribute("class");
              if (!className.equals("")) {
                String symTypDef = defs[k].getAttribute("elemType");
                if (symTypDef != null) {
                  int elemType = getValueFor(symTypDef); 
                  String elemName = defs[k].getAttribute("name");
                  ElementSpecification_c es = new ElementSpecification_c(graphicsModelRoot);
                  es.setName(elemName);
                  es.setOoa_type(elemType);
                  IConfigurationElement[] col = defs[k].getChildren("color");
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
                  es.setCreator(defs[k].getAttribute("createMethod"));
                  es.setIconname(defs[k].getAttribute("icon"));
                  String defHStr = defs[k].getAttribute("defaultHeight");
                  String defWStr = defs[k].getAttribute("defaultWidth");
                  if(defHStr != null) {
                    int defH = Integer.parseInt(defHStr);
                    es.setDefaultheight(defH);
                  }
                  if(defWStr != null) {
                    int defW = Integer.parseInt(defWStr);
                    es.setDefaultwidth(defW);
                  }
                  String isFixedAspectStr = defs[k].getAttribute("fixedAspect");
                  if(isFixedAspectStr != null && isFixedAspectStr.equals("true")) {
                		  es.setIsfixedaspectratio(true);
                    }
                  String isFixedSizeStr = defs[k].getAttribute("fixedSize");
                  if(isFixedSizeStr != null && isFixedSizeStr.equals("true")) {
                      es.setIsfixedsize(true);
                    }
                  String isAnchorHost = defs[k].getAttribute("isAnchorHost");
                  if(isAnchorHost != null && isAnchorHost.equals("true")) {
                	  es.setIsanchorhost(true);
                  }
                  String hasFloatingStr = defs[k].getAttribute("floatingText");
                  if(hasFloatingStr != null && hasFloatingStr.equals("true")) {
                      es.setHasfloatingtext(true);
                    }
                  String layer = defs[k].getAttribute("layer");
                  if(layer != null ) {
                	  try {
                        es.setLayer(Integer.parseInt(layer));
                	  }
                      catch (NumberFormatException e) {
                          logError("Could not parse symbol '"+ elemName +"' layer: ", e);
                      }
                  }
                  String antiAliased = defs[k].getAttribute("antiAliased");
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
                  String isTransparent =  defs[k].getAttribute("isTransparent");
                  if (isTransparent != null && isTransparent.equalsIgnoreCase("true")) {
                	  es.setIstransparent(true);
                  } else {
                	  es.setIstransparent(false);
                  }
                  String creationRule =  defs[k].getAttribute("creationRule");
                  if (creationRule != null) {
                	  es.setCreationrule(creationRule);
                  } else {
                	  es.setCreationrule("manual");
                  }
                  String hasNameCompartment = defs[k].getAttribute("hasNameCompartment");
                  if(hasNameCompartment != null) { 
                	  if(hasNameCompartment.equalsIgnoreCase("true")) {
                		  es.setHasnamecompartment(true);
                	  } else
                		  es.setHasnamecompartment(false);
                  }
                  
                  // setup element spec subtypes
                  String symbolType = defs[k].getAttribute("symbolType");
                  es.setSymboltype(symbolType);
        		  if (symbolType.equalsIgnoreCase("connector")) {
        			  ConnectorSpecification_c cs = new ConnectorSpecification_c(
        					  graphicsModelRoot);
        			  cs.relateAcrossR200To(es);
        		  }
                  // setup dependencies
                  addClientClassDependency(es, className);
                  IConfigurationElement[] depends = defs[k].getChildren("dependsOn");
                  for ( int d = 0; d < depends.length; ++d )
                  {
                    addClientClassDependency(es, depends[d].getAttribute("class"));
                  }
                  // setup auto resize
                  String causeAutoResize = defs[k].getAttribute("causeAutoResize");
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
          for (int k = 0; k < defs.length; k++) {
          	int value = 0;
            if (defs[k].getName().equals("defaultFor")) {
              String className = defs[k].getAttribute("class");
              if (!className.equals("")) {
                String mdlTypDef = defs[k].getAttribute("modelType");
                String ooaTypDef = defs[k].getAttribute("ooaType");
                if (mdlTypDef != null && !mdlTypDef.equals("")) {
				  int modelType = getValueFor(mdlTypDef);         	
                  int ooaType = getValueFor(ooaTypDef);                	
                  IConfigurationElement[] col = defs[k].getChildren("color");
                  int r = Integer.parseInt(col[0].getAttribute("r"));
                  int g = Integer.parseInt(col[0].getAttribute("g"));
                  int b = Integer.parseInt(col[0].getAttribute("b"));
                  String diagName = defs[k].getAttribute("name");
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
                  IConfigurationElement[] symbols = defs[k].getChildren("validSymbol");
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
          for (int k = 0; k < defs.length; k++) {
        	  if (defs[k].getName().equals("symbol")) {
        		  final String finalName = defs[k].getAttribute("name");
        		  String symTypDef = defs[k].getAttribute("elemType");
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
	        		  String symbolType = defs[k].getAttribute("symbolType");
	        		  if (symbolType.equalsIgnoreCase("connector")) {
	        			  ConnectorSpecification_c cs = ConnectorSpecification_c.getOneTS_CSPOnR200(es);
	        			  IConfigurationElement[] terms = defs[k]
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
          IConfigurationElement[] defs = elems[j].getChildren();
          for (int k = 0; k < defs.length; k++) {
            if (defs[k].getName().equals("symbol")) {
              // setup autoReconcile
              IConfigurationElement[] autoReconcile = defs[k].
                                                   getChildren("autoReconcile");
              for ( int e = 0; e < autoReconcile.length; ++e )
              {
     			AutoReconciliationSpecification_c ars =
     				   new AutoReconciliationSpecification_c(graphicsModelRoot);
                ars.setName(autoReconcile[e].getAttribute("name"));
                ars.setCountmethod(autoReconcile[e].
                		                           getAttribute("countMethod"));
                ars.setElementmethod(autoReconcile[e].
                		                         getAttribute("elementMethod"));
                ars.setElementexistsmethod(autoReconcile[e].
                		                   getAttribute("elementExistsMethod"));
                ars.setTemplateelementmethod(autoReconcile[e].
                		                 getAttribute("templateElementMethod"));
                if (ars.getTemplateelementmethod() == null) {
                  ars.setTemplateelementmethod("");
                }
                ElementSpecification_c scanTarget = locateEsByNameAndClassType(
                		       graphicsModelRoot, defs[k].getAttribute("name"), defs[k].getAttribute("class")); 
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
            
            else if (defs[k].getName().equals("defaultFor")) {
                // setup autoReconcile
                IConfigurationElement[] autoReconcile = defs[k].
                                                     getChildren("autoReconcile");
                for ( int e = 0; e < autoReconcile.length; ++e )
                {
       			AutoReconciliationSpecification_c ars =
       				   new AutoReconciliationSpecification_c(graphicsModelRoot);
                  ars.setName(autoReconcile[e].getAttribute("name"));
                  ars.setCountmethod(autoReconcile[e].
                  		                           getAttribute("countMethod"));
                  ars.setElementmethod(autoReconcile[e].
                  		                         getAttribute("elementMethod"));
                  ars.setElementexistsmethod(autoReconcile[e].
                  		                   getAttribute("elementExistsMethod"));
                  ars.setTemplateelementmethod(autoReconcile[e].
                  		                 getAttribute("templateElementMethod"));
                  if (ars.getTemplateelementmethod() == null) {
                    ars.setTemplateelementmethod("");
                  }
                 // ElementSpecification_c scanTarget = locateEsByNameAndClassType(
           		       //graphicsModelRoot, defs[k].getAttribute("name"), defs[k].getAttribute("class")); 
                  ModelSpecification_c modelSpec = locateMesByNameAndClassType(graphicsModelRoot, defs[k].getAttribute("name"), defs[k].getAttribute("class")); 
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
}