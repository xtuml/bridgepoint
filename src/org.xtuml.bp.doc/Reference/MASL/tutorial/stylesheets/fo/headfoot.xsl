<?xml version="1.0"?>
<!-- 
  UK Crown Copyright (c) 2016. All Rights Reserved
-->
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:d="http://docbook.org/ns/docbook" xmlns:dt="http://exslt.org/dates-and-times" xmlns:fo="http://www.w3.org/1999/XSL/Format" exclude-result-prefixes="d" version="1.0">

  <xsl:import href="../common/utils.xsl"/>

  <xsl:template name="header.table">
    <xsl:param name="pageclass" select="''"/>
    <xsl:param name="sequence" select="''"/>
    <xsl:param name="gentext-key" select="''"/>
    <fo:block font-size="16pt" font-weight="bold" text-align="center" space-after="6pt">
      <xsl:value-of select="/*/@security"/>
    </fo:block>
    <fo:block font-size="12pt" text-align-last="justify" space-after="0pt">
      <xsl:value-of select="/*/d:info/d:title"/> 
      <fo:leader leader-pattern="space"/>
     <fo:inline font-size="12pt" text-align="right" space-after="6pt">
      <xsl:apply-templates mode="format-date" select="/*/d:info/d:pubdate"/>
    </fo:inline>
    </fo:block>
    <fo:block font-size="12pt" text-align-last="left" space-after="0pt">
      <xsl:value-of select="/*/d:info/d:biblioid[@class='pubsnumber']"/>
    </fo:block>
  </xsl:template>

  <xsl:template name="footer.table">
    <xsl:param name="pageclass" select="''"/>
    <xsl:param name="sequence" select="''"/>
    <xsl:param name="gentext-key" select="''"/>
    <fo:block font-size="12pt" font-weight="normal" text-align="center" space-after="3pt">
      <fo:page-number/><xsl:text>&#160;of&#160;</xsl:text><fo:page-number-citation ref-id="last-page"/>
    </fo:block>
    <fo:block font-size="8pt" text-align="center" space-after="3pt">
      <fo:inline color="#7f7f7f"><xsl:value-of select="/*/d:info/d:legalnotice[@role='foia']"/></fo:inline>
    </fo:block>
    <fo:block font-size="16pt" font-weight="bold" text-align="center">
      <xsl:value-of select="/*/@security"/>
    </fo:block>
  </xsl:template>

</xsl:stylesheet>
