<?xml version="1.0"?>
<!-- 
  UK Crown Copyright (c) 2016. All Rights Reserved
-->
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:d="http://docbook.org/ns/docbook" xmlns:fo="http://www.w3.org/1999/XSL/Format" exclude-result-prefixes="d" version="1.0">
  <xsl:import href="../common/utils.xsl"/>

  <xsl:attribute-set name="version.history.cell">
     <xsl:attribute name="padding-before">6pt</xsl:attribute>
     <xsl:attribute name="padding-after">6pt</xsl:attribute>
     <xsl:attribute name="padding-start">3pt</xsl:attribute>
     <xsl:attribute name="padding-end">3pt</xsl:attribute>
     <xsl:attribute name="border-color">black</xsl:attribute>
     <xsl:attribute name="border-style">solid</xsl:attribute>
  </xsl:attribute-set>

  <xsl:template match="d:revhistory" mode="titlepage.mode">

    <fo:block xsl:use-attribute-sets="cover.title.properties">Document Amendment History</fo:block>

    <fo:block space-before="6pt" font-size="{$body.font.size}">
      <fo:table table-layout="fixed" text-align="left" width="100%" border-color="black" border-style="double" border-width="4pt">
        <fo:table-column column-width="15%"/>
        <fo:table-column column-width="25%"/>
        <fo:table-column column-width="15%"/>
        <fo:table-column column-width="45%"/>
        <fo:table-header background-color="#bfbfbf" font-weight="bold" text-align="center" border-color="black" border-style="double" border-width="2pt">
          <fo:table-cell xsl:use-attribute-sets="version.history.cell">
            <fo:block>Version</fo:block>
          </fo:table-cell>
          <fo:table-cell xsl:use-attribute-sets="version.history.cell">
            <fo:block>Date</fo:block>
          </fo:table-cell>
          <fo:table-cell xsl:use-attribute-sets="version.history.cell">
            <fo:block>R&amp;A</fo:block>
          </fo:table-cell>
          <fo:table-cell xsl:use-attribute-sets="version.history.cell">
            <fo:block>Amendments</fo:block>
          </fo:table-cell>
        </fo:table-header>
        <fo:table-body>
          <xsl:apply-templates select="d:revision" mode="titlepage.mode"/>
        </fo:table-body>
      </fo:table>
    </fo:block>
  </xsl:template>

  <xsl:template match="d:revhistory/d:revision" mode="titlepage.mode">
    <xsl:variable name="revnumber" select="d:revnumber"/>
    <xsl:variable name="revdate" select="d:date"/>
    <xsl:variable name="revauthor" select="d:authorinitials"/>
    <xsl:variable name="revremark" select="d:revremark|d:revdescription"/>
    <fo:table-row>
      <fo:table-cell xsl:use-attribute-sets="version.history.cell">
        <fo:block text-align="center">
          <xsl:if test="$revnumber">
            <xsl:apply-templates select="$revnumber[1]" mode="titlepage.mode"/>
          </xsl:if>
        </fo:block>
      </fo:table-cell>
      <fo:table-cell xsl:use-attribute-sets="version.history.cell">
        <fo:block text-align="center">
          <xsl:apply-templates select="$revdate[1]" mode="titlepage.mode"/>
        </fo:block>
      </fo:table-cell>
           <fo:table-cell xsl:use-attribute-sets="version.history.cell">
            <fo:block text-align="center"></fo:block>
          </fo:table-cell>
     <xsl:if test="$revremark">
        <fo:table-cell xsl:use-attribute-sets="version.history.cell">
          <fo:block text-align="start">
            <xsl:apply-templates select="$revremark[1]" mode="titlepage.mode"/>
          </fo:block>
        </fo:table-cell>
      </xsl:if>
    </fo:table-row>
  </xsl:template>

  <xsl:template match="d:revision/d:revnumber" mode="titlepage.mode">
    <xsl:apply-templates mode="titlepage.mode"/>
  </xsl:template>

  <xsl:template match="d:revision/d:date" mode="titlepage.mode">
      <xsl:apply-templates mode="format-date" select="."/>
  </xsl:template>

  <xsl:template match="d:revision/d:revremark" mode="titlepage.mode">
    <xsl:apply-templates mode="titlepage.mode"/>
  </xsl:template>

</xsl:stylesheet>
