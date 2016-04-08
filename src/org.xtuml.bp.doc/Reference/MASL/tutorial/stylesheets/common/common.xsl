<?xml version="1.0"?>
<!-- 
  UK Crown Copyright (c) 2016. All Rights Reserved
-->
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">

  <xsl:param name="section.autolabel" select="1"/>
  <xsl:param name="section.label.includes.component.label" select="1"/>

  <!--
  Change the docbook EBNF defaults to comply with
  the ISO/IEC 14977:1996(E) standard for EBNF
  -->
  <xsl:param name="ebnf.assignment" select="'='"/>
  <xsl:param name="ebnf.statement.terminator" select="';'"/>


  <xsl:param name="make.year.ranges" select="true"/>
  <xsl:param name="make.single.year.ranges" select="true"/>






</xsl:stylesheet>
