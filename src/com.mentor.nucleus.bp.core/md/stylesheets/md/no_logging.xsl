<?xml version="1.0" encoding="UTF-8"?>

<!-- ===================================================================

File:      no_logging.xsl. 1.4
Modified:  01/23/03, 16:02:49

Copyright (c) 2002-2013 Mentor Graphics CorporationMentor Graphics CorporationMentor Graphics CorporationMentor Graphics CorporationMentor Graphics CorporationMentor Graphics Corporation  All rights reserved.

========================================================================
This document contains information proprietary and confidential to
Project Technology, Inc. and is not for external distribution.
========================================================================

Debugger Trace Stylesheet
   
Output:  Text

Dependencies: None

Description:  This stylesheet only translates the log and brkpt messages.

==================================================================== -->

<!DOCTYPE xsl:stylesheet [
   <!ENTITY nbsp "&#160;">
   <!ENTITY xtuml "<sup>X</sup><sub>T</sub>UML">
   <!ENTITY reg "(R)">
]>

<xsl:stylesheet version="1.0" 
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  
  <xsl:output method="html" 
    encoding="UTF-8"
    indent="yes"/>
               
  <!-- We don't want to display the domain information -->
  <xsl:template match="/system/content/domain">
  </xsl:template>

  <xsl:template match="/system/log">
    <xsl:value-of select="@severity"/>: <xsl:value-of select="@message"/>
  </xsl:template>

  <xsl:template match="brkpt">
    <font color="red"> *** Breakpoint <xsl:value-of select="@name"/> on <xsl:value-of select="@element"/> Encountered *** </font>
  </xsl:template>

</xsl:stylesheet>
