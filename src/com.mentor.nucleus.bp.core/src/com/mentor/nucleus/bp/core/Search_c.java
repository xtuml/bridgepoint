package com.mentor.nucleus.bp.core;
//========================================================================
//
//File:      $RCSfile: Search_c.java,v $
//Version:   $Revision: 1.6 $
//Modified:  $Date: 2013/01/10 22:54:14 $
//
//Copyright (c) 2005-2014 Mentor Graphics Corporation.  All rights reserved.
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
//
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.text.Document;

import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.core.common.IModelMatchListener;
import com.mentor.nucleus.bp.core.common.IPersistenceHierarchyMetaData;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.core.common.PersistenceManager;
import com.mentor.nucleus.bp.core.search.DocumentCharSequence;
import com.mentor.nucleus.bp.core.ui.Selection;
import com.mentor.nucleus.bp.core.util.ActionLanguageDescriptionUtil;
import com.mentor.nucleus.bp.core.util.CoreUtil;

// Search
public class Search_c {
	
	private static IModelMatchListener matchListener;
	private static Matcher matcher;
	private static Set<NonRootModelElement> descriptionElementsInScope = new HashSet<NonRootModelElement>();
	private static Set<NonRootModelElement> actionLanguageElementsInScope = new HashSet<NonRootModelElement>();

	public static UUID Locatecontentresults(final String p_Contents,
			boolean isCaseSensitive, final String p_Pattern) {
		int regexOptions = Pattern.MULTILINE;
		if (!isCaseSensitive) {
			regexOptions |= Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE;
		}
		if (matcher == null) {
			Document document = new Document(p_Contents);
			DocumentCharSequence sequence = new DocumentCharSequence(document);
			Pattern pattern = Pattern.compile(p_Pattern, regexOptions);
			matcher = pattern.matcher(sequence);
		}
		if (matcher.find()) {
			int start = matcher.start();
			int end = matcher.end();
			if (end != start) {
				ContentMatchResult_c cmr = new ContentMatchResult_c(Ooaofooa
						.getDefaultInstance());
				cmr.setStartposition(start);
				cmr.setLength(end - start);
				return cmr.getId();
			}
		}
		return Gd_c.Null_unique_id();
	} // End locateContentResults

	public static void Gatherparticipants(Object monitor, final UUID id) {
		Query_c query = Query_c.QueryInstance(Ooaofooa.getDefaultInstance(),
				new ClassQueryInterface_c() {

					@Override
					public boolean evaluate(Object candidate) {
						return ((Query_c) candidate).getId().equals(id);
					}
				});
		ActionLanguageQuery_c alq = ActionLanguageQuery_c
				.getOneSQU_AOnR9600(query);
		if (alq != null) {
			gatherActionLanguageParticipants(query, monitor);
		}
		DescriptionQuery_c dq = DescriptionQuery_c.getOneSQU_DEOnR9600(query);
		if (dq != null) {
			gatherDescriptionParticipants(query, monitor);
		}
	}

	/**
	 * Locate all elements that have a Descrip attribute, which are within the
	 * configured scope. Create the necessary Participant element for each.
	 * 
	 * @param query
	 * @param monitor 
	 */
	private static void gatherDescriptionParticipants(Query_c query, Object monitor) {
		for(NonRootModelElement element : descriptionElementsInScope) {
			Object uiElement = getUIInstance(element);
			// now create the necessary participant
			query.Createparticipant(uiElement.getClass().getName(),
					((NonRootModelElement) uiElement).getInstanceKey(),
					((NonRootModelElement) uiElement).getModelRoot().getId(),
					ActionLanguageDescriptionUtil
							.getDescriptionAttributeValue(element));
			IProgressMonitor progressMonitor = (IProgressMonitor) monitor;
			if(progressMonitor.isCanceled()) {
				return;
			}
		}
	}
	
	/**
	 * Locate all elements that have an Action_Semantics attribute, which are
	 * within the configured scope. Create the necessary Participant element for
	 * each.
	 * 
	 * @param query
	 * @param monitor 
	 */
	private static void gatherActionLanguageParticipants(Query_c query, Object monitor) {
		for (NonRootModelElement element : actionLanguageElementsInScope) {
			Object uiElement = getUIInstance(element);
			// now create the necessary participant
			query.Createparticipant(uiElement.getClass().getName(),
					((NonRootModelElement) uiElement).getInstanceKey(),
					((NonRootModelElement) uiElement).getModelRoot().getId(),
					ActionLanguageDescriptionUtil
							.getActionLanguageAttributeValue(element));
			IProgressMonitor progressMonitor = (IProgressMonitor) monitor;
			if(progressMonitor.isCanceled()) {
				return;
			}
		}
	}

	public static NonRootModelElement[] gatherChildrenForSelected(
			NonRootModelElement[] selected, IProgressMonitor monitor) {
		IPersistenceHierarchyMetaData metaData = PersistenceManager
				.getHierarchyMetaData();
		List<NonRootModelElement> children = new ArrayList<NonRootModelElement>();
		for (int i = 0; i < selected.length; i++) {
			children.add(selected[i]);
			List<?> metaChildren = metaData.getChildren(selected[i], false);
			for (Object child : metaChildren) {
				NonRootModelElement[] gatheredChildren = gatherChildrenForSelected(new NonRootModelElement[] { (NonRootModelElement) child }, monitor);
				for (int j = 0; j < gatheredChildren.length; j++) {
					if (!children.contains(gatheredChildren[j])
							&& gatheredChildren[j].getModelRoot() instanceof Ooaofooa) {
						children.add(gatheredChildren[j]);
					}
				}
				if(monitor.isCanceled()) {
					// user cancelled, return
					return children.toArray(new NonRootModelElement[children.size()]);
				}
			}
		}
		return children.toArray(new NonRootModelElement[children.size()]);
	}

	private static Object getUIInstance(Object instance) {
		if (instance instanceof Action_c) {
			// return the state or transaction
			StateMachineState_c state = StateMachineState_c
					.getOneSM_STATEOnR511(MooreActionHome_c
							.getOneSM_MOAHOnR513(ActionHome_c
									.getOneSM_AHOnR514((Action_c) instance)));
			if (state != null) {
				return state;
			}
			Transition_c transition = Transition_c
					.getOneSM_TXNOnR530(TransitionActionHome_c
							.getOneSM_TAHOnR513(ActionHome_c
									.getOneSM_AHOnR514((Action_c) instance)));
			if (transition != null) {
				return transition;
			}
		}
		if (instance instanceof DerivedBaseAttribute_c) {
			return Attribute_c.getOneO_ATTROnR106(BaseAttribute_c
					.getOneO_BATTROnR107((DerivedBaseAttribute_c) instance));
		}
		return instance;
	}

	private static Object getAttributeHolder(Object instance,
			boolean actionLanguage) {
		if (instance instanceof StateMachineState_c) {
			// return the state action
			Action_c action = Action_c
					.getOneSM_ACTOnR514(ActionHome_c
							.getOneSM_AHOnR513(MooreActionHome_c
									.getOneSM_MOAHOnR511((StateMachineState_c) instance)));
			if (action != null) {
				return action;
			}
		}
		if (instance instanceof Transition_c) {
			Action_c action = Action_c.getOneSM_ACTOnR514(ActionHome_c
					.getOneSM_AHOnR513(TransitionActionHome_c
							.getOneSM_TAHOnR530((Transition_c) instance)));
			if (action != null) {
				return action;
			}
		}
		if (instance instanceof Attribute_c && actionLanguage) {
			DerivedBaseAttribute_c dba = DerivedBaseAttribute_c
					.getOneO_DBATTROnR107(BaseAttribute_c
							.getOneO_BATTROnR106((Attribute_c) instance));
			if (dba != null) {
				return dba;
			}
		}
		return instance;
	}

	public static boolean Monitorcanceled(Object monitor) {
		return ((IProgressMonitor) monitor).isCanceled();
	}

	public static void Clearquerydata() {
		matcher = null;
	}
	
	public static void setMatchListener(IModelMatchListener listener) {
		Search_c.matchListener = listener;
	}
	
	public static void Matchcreated(Object match) {
		if(matchListener != null && match instanceof Match_c) {
			matchListener.matchCreated((Match_c) match);
		}
	}

	public static void Configurescope(Object monitor, int scope) {
		IProgressMonitor progressMonitor = (IProgressMonitor) monitor;
		// clear previous element set
		actionLanguageElementsInScope.clear();
		descriptionElementsInScope.clear();
		Ooaofooa[] instances = Ooaofooa.getInstances();
		if (scope == Searchscope_c.Universe) {
			try {
				CoreUtil.loadWorkspace(progressMonitor);
				for (int i = 0; i < instances.length; i++) {
					List<Class<?>> classes = ActionLanguageDescriptionUtil
							.getClassesSupportingActionLanguage();
					for (Class<?> clazz : classes) {
						Object[] elements = instances[i].getInstanceList(clazz)
								.toArray();
						for (int j = 0; j < elements.length; j++) {
							actionLanguageElementsInScope
									.add((NonRootModelElement) elements[j]);
							if (progressMonitor.isCanceled()) {
								// user cancelled, return
								return;
							}
						}
					}
					classes = ActionLanguageDescriptionUtil
							.getClassesSupportingDescriptions();
					for (Class<?> clazz : classes) {
						Object[] elements = instances[i].getInstanceList(clazz)
								.toArray();
						for (int j = 0; j < elements.length; j++) {
							descriptionElementsInScope
									.add((NonRootModelElement) elements[j]);
							if (progressMonitor.isCanceled()) {
								// user cancelled, return
								return;
							}
						}
					}
				}
			} catch (CoreException e1) {
				CorePlugin.logError("Unable to load workspace for search.", e1);
			}
		} else if (scope == Searchscope_c.Selection
				|| scope == Searchscope_c.EnclosingSystem) {
			// make instances empty, and process here
			instances = new Ooaofooa[0];
			NonRootModelElement[] selected = Selection.getInstance()
					.getSelectedNonRootModelElements();
			selected = gatherChildrenForSelected(selected, progressMonitor);
			for (int i = 0; i < selected.length; i++) {
				Object originalInstance = selected[i];
				originalInstance = getAttributeHolder(originalInstance, false);
				if (ActionLanguageDescriptionUtil
						.hasActionLanguageAttribute(originalInstance.getClass())) {
					actionLanguageElementsInScope
							.add((NonRootModelElement) originalInstance);
				}
				if (ActionLanguageDescriptionUtil
						.hasDescriptionAttribute(originalInstance.getClass())) {
					descriptionElementsInScope
							.add((NonRootModelElement) originalInstance);
				}
				progressMonitor.worked(1);
				if (progressMonitor.isCanceled()) {
					// user cancelled, return
					return;
				}
			}
		}
	}

} // End Search_c

