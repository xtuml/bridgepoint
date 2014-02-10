package com.mentor.nucleus.bp.utilities.ui;

import java.util.ArrayList;

import org.eclipse.swt.widgets.TreeItem;

public class TreeUtilities {

	public static String getTextResultForOrphanedElementList(TreeItem[] orphaned) {
		String result = "";
		for(int i = 0; i < orphaned.length; i++) {
			String parentPath = orphaned[i].getParentItem().getText();
			TreeItem parent = orphaned[i].getParentItem().getParentItem();
			while(parent != null) {
				parentPath = parent.getText() + "::" + parentPath;
				parent = parent.getParentItem();
			}
			result += parentPath + "::"
					+ orphaned[i].getText() + "\n";
		}
		return result;
	}
	
	public static TreeItem[] getOrphanedElementsFromTree(TreeItem topItem) {
		ArrayList<TreeItem> list = new ArrayList<TreeItem>();
		if(topItem.getText().contains("rphaned"))
			list.add(topItem);
		for(int i = 0; i < topItem.getItems().length; i++) {
			TreeItem[] findOrphanedItems = getOrphanedElementsFromTree(topItem.getItem(i));
			for(int j = 0; j < findOrphanedItems.length; j++)
				list.add(findOrphanedItems[j]);
		}
		return list.toArray(new TreeItem[list.size()]);
	}
}
