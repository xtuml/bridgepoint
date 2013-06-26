//========================================================================
//
//File:      $RCSfile: ShapeElement.java,v $
//Version:   $Revision: 1.3 $
//Modified:  $Date: 2013/01/10 22:43:53 $
//
//Copyright 2005-2013 Mentor Graphics Corporation. All rights reserved.
//
//========================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp., and is not for external distribution.
//======================================================================== 
//
package com.mentor.nucleus.bp.ui.canvas.test.model;

import java.util.UUID;

import com.mentor.nucleus.bp.core.Justification_c;
import com.mentor.nucleus.bp.core.Style_c;

public class ShapeElement {

	private UUID id = UUID.randomUUID();

	public String Get_compartment_text(int at, int comp_num, int ent_num) {
		if(at == Justification_c.Center_in_X && comp_num == 1) {
			return "Test Shape Element";
		}
		return "";
	}
	
	public int Get_compartments() {
		return 1;
	}
	
	public int Get_entries(int comp_num) {
		return 1;
	}
	
	public UUID Get_ooa_id() {
		return id ;
	}
	
	public int Get_style() {
		return Style_c.Box;
	}
	
	public int Get_text_style(int at, int comp_num, int ent_num) {
		return Style_c.None;
	}
}
