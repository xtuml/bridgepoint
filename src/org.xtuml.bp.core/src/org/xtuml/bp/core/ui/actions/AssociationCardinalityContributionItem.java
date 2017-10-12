package org.xtuml.bp.core.ui.actions;

import org.eclipse.jface.action.ContributionItem;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.xtuml.bp.core.Association_c;
import org.xtuml.bp.core.ClassAsAssociatedOneSide_c;
import org.xtuml.bp.core.ClassAsAssociatedOtherSide_c;
import org.xtuml.bp.core.ClassAsLink_c;
import org.xtuml.bp.core.ClassAsSimpleFormalizer_c;
import org.xtuml.bp.core.ClassAsSimpleParticipant_c;
import org.xtuml.bp.core.ClassInAssociation_c;
import org.xtuml.bp.core.LinkedAssociation_c;
import org.xtuml.bp.core.ModelClass_c;
import org.xtuml.bp.core.ReferredToClassInAssoc_c;
import org.xtuml.bp.core.ReferringClassInAssoc_c;
import org.xtuml.bp.core.SimpleAssociation_c;
import org.xtuml.bp.core.SubtypeSupertypeAssociation_c;
import org.xtuml.bp.core.common.NonRootModelElement;
import org.xtuml.bp.core.ui.Selection;

public class AssociationCardinalityContributionItem extends ContributionItem {

	private static String PROPERTY_PREFIX = "bridgepoint.";
	private static String LINK_PROPERTY = "CardinalityOnAssociativeLink";
	
	@Override
	public void fill(Menu menu, int index) {
		// selection must only contain Association, Model Class, Package or
		// Class As Link
		boolean validSelection = true;
		NonRootModelElement[] selectedNonRootModelElements = Selection.getInstance().getSelectedNonRootModelElements();
		if (selectedNonRootModelElements.length != 1) {
			// only support a single association
			return;
		}
		for (NonRootModelElement selected : selectedNonRootModelElements) {
			if (!(selected instanceof Association_c) && !(selected instanceof ClassAsLink_c)) {
				validSelection = false;
				break;
			}
			if (selected instanceof Association_c) {
				SubtypeSupertypeAssociation_c ssa = SubtypeSupertypeAssociation_c
						.getOneR_SUBSUPOnR206((Association_c) selected);
				if (ssa != null) {
					validSelection = false;
					break;
				}
			}
			if (selected instanceof ClassAsLink_c) {
				String propertyKey = PROPERTY_PREFIX + LINK_PROPERTY;
		        String actualPropertyValue = System.getProperty(propertyKey, "enabled");
		        if ( actualPropertyValue.equals("disabled") ) {
		        	validSelection = false;
		        	break;
		        }
			}
		}
		if (validSelection) {
			NonRootModelElement element = selectedNonRootModelElements[0];
			boolean useTxtPhrs = false;
			// create root menu
			Menu cardinalityMenu = createMenu("Cardinality", menu, index);
			ReferredToClassInAssoc_c rto = null;
			ReferringClassInAssoc_c rgo = null;
			ReferredToClassInAssoc_c otherRto = null;
			String rtoTxtPhrs = "";
			String rgoTxtPhrs = "";
			String rtoOtherTxtPhrs = "";
			int rtoCond = 0;
			int rtoMult = 0;
			int rgoCond = 0;
			int rgoMult = 0;
			int otherCond = 0;
			int otherMult = 0;
			if (element instanceof Association_c) {
				Association_c association = (Association_c) element;
				useTxtPhrs = ((Association_c) association).Is_reflexive();
				// create RTO menu
				ClassAsSimpleParticipant_c[] parts = ClassAsSimpleParticipant_c
						.getManyR_PARTsOnR207(SimpleAssociation_c.getManyR_SIMPsOnR206(association));
				// create RGO menu
				ClassAsSimpleFormalizer_c form = ClassAsSimpleFormalizer_c
						.getOneR_FORMOnR208(SimpleAssociation_c.getManyR_SIMPsOnR206(association));
				if (parts.length == 0) {
					// looking for a linked association (or a subsup could be
					// selected)
					ClassAsAssociatedOneSide_c one = ClassAsAssociatedOneSide_c
							.getOneR_AONEOnR209(LinkedAssociation_c.getManyR_ASSOCsOnR206(association));
					if (one != null) {
						rtoTxtPhrs = one.getTxt_phrs();
						rtoCond = one.getCond();
						rtoMult = one.getMult();
						ClassAsAssociatedOtherSide_c oth = ClassAsAssociatedOtherSide_c
								.getOneR_AOTHOnR210(LinkedAssociation_c.getManyR_ASSOCsOnR206(association));
						rgoTxtPhrs = oth.getTxt_phrs();
						otherCond = oth.getCond();
						otherMult = oth.getMult();
						rto = ReferredToClassInAssoc_c.getOneR_RTOOnR204(one);
						otherRto = ReferredToClassInAssoc_c.getOneR_RTOOnR204(oth);
					} else {
						// subsup ignore
						return;
					}
				} else {
					ClassAsSimpleParticipant_c part = parts[0];
					rtoTxtPhrs = part.getTxt_phrs();
					rtoCond = part.getCond();
					rtoMult = part.getMult();
					rto = ReferredToClassInAssoc_c.getOneR_RTOOnR204(part);
					if (parts.length > 1) {
						rtoOtherTxtPhrs = parts[1].getTxt_phrs();
						otherCond = parts[1].getCond();
						otherMult = parts[1].getMult();
						otherRto = ReferredToClassInAssoc_c.getOneR_RTOOnR204(parts[1]);
					} else {
						rgoTxtPhrs = form.getTxt_phrs();
						rgoCond = form.getCond();
						rgoMult = form.getMult();
						rgo = ReferringClassInAssoc_c.getOneR_RGOOnR205(form);
					}
				}
			} else {
				if (element instanceof ClassAsLink_c) {
					rgo = ReferringClassInAssoc_c.getOneR_RGOOnR205((ClassAsLink_c) element);
				}
			}
			final ModelClass_c rtoClass = ModelClass_c.getOneO_OBJOnR201(ClassInAssociation_c.getManyR_OIRsOnR203(rto));
			final ModelClass_c otherRtoClass = ModelClass_c
					.getOneO_OBJOnR201(ClassInAssociation_c.getManyR_OIRsOnR203(otherRto));
			final ModelClass_c rgoClass = ModelClass_c.getOneO_OBJOnR201(ClassInAssociation_c.getManyR_OIRsOnR203(rgo));
			final ReferredToClassInAssoc_c finalRto = rto;
			final ReferredToClassInAssoc_c finalOtherRto = otherRto;
			final ReferringClassInAssoc_c finalRgo = rgo;
			Menu rtoMenu = null;
			Menu rgoMenu = null;
			Menu otherRtoMenu = null;
			if (rto != null) {
				String name = useTxtPhrs ? rtoClass.getName() + ".'" + rtoTxtPhrs + "'" : rtoClass.getName();
				rtoMenu = createMenu(name, cardinalityMenu, 0);
			}
			if (rgo != null) {
				String name = useTxtPhrs ? rgoClass.getName() + ".'" + rgoTxtPhrs + "'" : rgoClass.getName();
				rgoMenu = createMenu(name, cardinalityMenu, rto == null ? 0 : 1);
			}
			if (otherRto != null) {
				String name = useTxtPhrs ? otherRtoClass.getName() + ".'" + rtoOtherTxtPhrs + "'" : otherRtoClass.getName();
				otherRtoMenu = createMenu(name, cardinalityMenu, 1);
			}
			if (rto != null) {
				String[] rules = new String[] { "1", "0..1", "1..*", "*" };
				for (int i = 0; i < rules.length; i++) {
					int ruleCond = 0;
					int ruleMult = 0;
					switch (i) {
					case 0:
						// defaults
						break;
					case 1:
						ruleCond = 1;
						ruleMult = 0;
						break;
					case 2:
						ruleCond = 0;
						ruleMult = 1;
						break;
					case 3:
						ruleCond = 1;
						ruleMult = 1;
					default:
						break;
					}
					final int rule = i;
					if (rto != null) {
						MenuItem actionItem;
						if (rtoCond == ruleCond && rtoMult == ruleMult) {
							actionItem = new MenuItem(rtoMenu, SWT.RADIO, rtoMenu.getItemCount());
							actionItem.setSelection(true);
							actionItem.setEnabled(false);
						} else {
							actionItem = new MenuItem(rtoMenu, SWT.RADIO, rtoMenu.getItemCount());
							actionItem.setSelection(false);
							actionItem.setEnabled(true);
						}
						actionItem.setText(rules[i]);
						actionItem.addSelectionListener(new SelectionAdapter() {

							@Override
							public void widgetDefaultSelected(SelectionEvent e) {
								MenuItem item = (MenuItem) e.widget;
								if (item.getSelection()) {
									AssociationCardinalityAction action = new AssociationCardinalityAction(finalRto, rule);
									action.run(null);
								}
							}

							@Override
							public void widgetSelected(SelectionEvent e) {
								widgetDefaultSelected(e);
							}

						});
					}
					if (rgo != null) {
						MenuItem actionItem;
						if (rgoCond == ruleCond && rgoMult == ruleMult) {
							actionItem = new MenuItem(rgoMenu, SWT.RADIO, rgoMenu.getItemCount());
							actionItem.setSelection(true);
							actionItem.setEnabled(false);
						} else {
							actionItem = new MenuItem(rgoMenu, SWT.RADIO, rgoMenu.getItemCount());
							actionItem.setSelection(false);
							actionItem.setEnabled(true);
						}
						actionItem.setText(rules[i]);
						actionItem.addSelectionListener(new SelectionAdapter() {

							@Override
							public void widgetDefaultSelected(SelectionEvent e) {
								MenuItem item = (MenuItem) e.widget;
								if (item.getSelection()) {
									AssociationCardinalityAction action = new AssociationCardinalityAction(finalRgo, rule);
									action.run(null);
								}
							}

							@Override
							public void widgetSelected(SelectionEvent e) {
								widgetDefaultSelected(e);
							}

						});
					}
					if (otherRto != null) {
						MenuItem actionItem;
						if (otherCond == ruleCond && otherMult == ruleMult) {
							actionItem = new MenuItem(otherRtoMenu, SWT.RADIO, otherRtoMenu.getItemCount());
							actionItem.setSelection(true);
							actionItem.setEnabled(false);
						} else {
							actionItem = new MenuItem(otherRtoMenu, SWT.RADIO, otherRtoMenu.getItemCount());
							actionItem.setSelection(false);
							actionItem.setEnabled(true);
						}
						actionItem.setText(rules[i]);
						actionItem.addSelectionListener(new SelectionAdapter() {

							@Override
							public void widgetDefaultSelected(SelectionEvent e) {
								MenuItem item = (MenuItem) e.widget;
								if (item.getSelection()) {
									AssociationCardinalityAction action = new AssociationCardinalityAction(finalOtherRto,
										rule);
									action.run(null);
								}
							}

							@Override
							public void widgetSelected(SelectionEvent e) {
								widgetDefaultSelected(e);
							}

						});
					}
				}

			} else {
				ClassAsLink_c link = (ClassAsLink_c) element;
				// linked association
				String[] rules = new String[] { " ", "{*}" };
				for (int i = 0; i < rules.length; i++) {
					MenuItem actionItem;
					if (link.getMult() == i) {
						actionItem = new MenuItem(rgoMenu, SWT.RADIO, rgoMenu.getItemCount());
						actionItem.setSelection(true);
						actionItem.setEnabled(false);
					} else {
						actionItem = new MenuItem(rgoMenu, SWT.RADIO, rgoMenu.getItemCount());
						actionItem.setSelection(false);
						actionItem.setEnabled(true);
					}
					actionItem.setText(rules[i]);
					actionItem.addSelectionListener(new SelectionAdapter() {

						@Override
						public void widgetDefaultSelected(SelectionEvent e) {
							MenuItem item = (MenuItem) e.widget;
							if (item.getSelection()) {
								AssociationCardinalityAction action = new AssociationCardinalityAction(finalRgo, -1);
								action.run(null);
							}
						}

						@Override
						public void widgetSelected(SelectionEvent e) {
							widgetDefaultSelected(e);
						}

					});
				}
			}
		}
	}

	private Menu createMenu(String menuName, Menu parent, int index) {
		Menu newMenu = new Menu(parent);
		MenuItem menuItem = null;
		if (index != -1) {
			menuItem = new MenuItem(parent, SWT.CASCADE, index);
		} else {
			menuItem = new MenuItem(parent, SWT.CASCADE);
		}
		menuItem.setText(menuName);
		menuItem.setMenu(newMenu);
		return newMenu;
	}

}
