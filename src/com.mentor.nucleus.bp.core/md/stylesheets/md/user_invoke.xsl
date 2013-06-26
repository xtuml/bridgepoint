<?xml version="1.0" encoding="UTF-8"?>

<!-- ===================================================================

File:      user_invoke.xsl, 1.4
Modified:  01/23/03, 16:03:46

Copyright (c) 2002-2013 Mentor Graphics CorporationMentor Graphics CorporationMentor Graphics CorporationMentor Graphics CorporationMentor Graphics CorporationMentor Graphics Corporation  All rights reserved.

========================================================================
This document contains information proprietary and confidential to
Project Technology, Inc. and is not for external distribution.
========================================================================

               Debugger Trace Stylesheet
   
Output:        Text

Dependencies:  None

Description:   This stylesheet processes user invoked downward (to the
               target) protocol messages so that they are displayed
               nicely in the log window.
                    
==================================================================== -->

<!DOCTYPE xsl:stylesheet [
   <!ENTITY nbsp "&#160;">
   <!ENTITY xtuml "<sup>X</sup><sub>T</sub>UML">
   <!ENTITY reg "(R)">
]>

<xsl:stylesheet version="1.0" 
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

  <xsl:template match="debug[@cmd='Request Function Invocation']">
    <xsl:variable name="fn-name">
      <xsl:call-template name="get-fn-name">
        <xsl:with-param name="dom-id" select="@dom-id"/>
        <xsl:with-param name="fn-id" select="@sync-id"/>
      </xsl:call-template>
    </xsl:variable>
    <xsl:variable name="dom-name">
      <xsl:call-template name="get-dom-name">
        <xsl:with-param name="dom-id" select="@dom-id"/>
      </xsl:call-template>
    </xsl:variable>
    <font color="black">USER: </font>
    <font color="green">Function invocation: </font>
    <font color="purple"><xsl:value-of select="$dom-name"/>::<xsl:value-of select="$fn-name"/>(
    <xsl:apply-templates select="value" mode="function"/> )</font>
  </xsl:template>

</xsl:stylesheet>
