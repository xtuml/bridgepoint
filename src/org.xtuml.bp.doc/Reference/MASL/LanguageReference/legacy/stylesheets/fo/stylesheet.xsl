<?xml version="1.0"?>
<!-- 
  UK Crown Copyright (c) 2016. All Rights Reserved
-->
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:d="http://docbook.org/ns/docbook" xmlns:fo="http://www.w3.org/1999/XSL/Format" exclude-result-prefixes="d" version="1.0">
  <xsl:import href="http://docbook.sourceforge.net/release/xsl-ns/current/fo/docbook.xsl"/>
  <xsl:import href="../common/common.xsl"/>
  <xsl:import href="credits.xsl"/>
  <xsl:import href="../../generated/stylesheets/fo/titlepage.xsl"/>
  <xsl:import href="headfoot.xsl"/>
  <xsl:import href="revisionhist.xsl"/>
  <xsl:import href="ebnf.xsl"/>

  <xsl:param name="preferred.mediaobject.role">fo</xsl:param>
  <xsl:param name="fop1.extensions">1</xsl:param>

  <xsl:param name="local.l10n.xml" select="document('')"/>
  <l:i18n xmlns:l="http://docbook.sourceforge.net/xmlns/l10n/1.0">
    <l:l10n language="en">
      <l:gentext key="TableOfContents" text="Contents"/>
      <l:gentext key="tableofcontents" text="Contents"/>
      <l:gentext key="ListOfExamples" text="Examples"/>
      <l:gentext key="listofexamples" text="Examples"/>
      <l:gentext key="Abstract" text="Synopsis"/>
      <l:gentext key="Author" text="Creator"/>
      <l:gentext key="Approver" text="Approver"/>
      <l:context name="title-numbered">
        <l:template name="chapter" text="%n. %t"/>
        <l:template name="appendix" text="%n. %t"/>
      </l:context>
    </l:l10n>
  </l:i18n>

  <xsl:template match="d:cover" mode="titlepage.mode">
    <xsl:apply-templates mode="titlepage.mode"/>
  </xsl:template>

  <xsl:template match="d:cover/d:bridgehead" mode="titlepage.mode">
    <fo:block xsl:use-attribute-sets="cover.title.properties"><xsl:value-of select="."/></fo:block>
  </xsl:template>

  <xsl:param name="footer.rule">0</xsl:param>
  <xsl:param name="header.rule">0</xsl:param>
  <xsl:param name="hyphenate">false</xsl:param>
  <xsl:template name="initial.page.number">auto-odd</xsl:template>
  <xsl:template name="page.number.format">1</xsl:template>

  <xsl:param name="bibliography.style">iso690</xsl:param>
 
  <!-- Page Layout -->
  <xsl:param name="paper.type">A4</xsl:param>

  <xsl:param name="page.margin.bottom">1.5cm</xsl:param>
  <xsl:param name="page.margin.top">1.5cm</xsl:param>
  <xsl:param name="page.margin.inner">2.5cm</xsl:param>
  <xsl:param name="page.margin.outer">2.5cm</xsl:param>
  <xsl:param name="body.margin.bottom">3cm</xsl:param>
  <xsl:param name="body.margin.top">2.5cm</xsl:param>
  <xsl:param name="region.before.extent">2.5cm</xsl:param>
  <xsl:param name="region.after.extent">2.5cm</xsl:param>

  <!-- Paragraph style -->

  <xsl:attribute-set name="normal.para.spacing">
     <xsl:attribute name="space-before">0pt</xsl:attribute>
     <xsl:attribute name="space-after">6pt</xsl:attribute>
     <xsl:attribute name="space-before.optimum">0pt</xsl:attribute>
     <xsl:attribute name="space-after.optimum">6pt</xsl:attribute>
     <xsl:attribute name="space-before.maximum">6pt</xsl:attribute>
     <xsl:attribute name="space-after.maximum">6pt</xsl:attribute>
     <xsl:attribute name="space-before.minimum">0pt</xsl:attribute>
     <xsl:attribute name="space-after.minimum">0pt</xsl:attribute>
  </xsl:attribute-set>

  <xsl:attribute-set name="component.title.properties">
     <xsl:attribute name="font-size">14pt</xsl:attribute>
     <xsl:attribute name="font-weight">bold</xsl:attribute>
     <xsl:attribute name="space-before">0pt</xsl:attribute>
     <xsl:attribute name="space-after">6pt</xsl:attribute>
     <xsl:attribute name="space-before.optimum">0pt</xsl:attribute>
     <xsl:attribute name="space-after.optimum">6pt</xsl:attribute>
     <xsl:attribute name="space-before.maximum">6pt</xsl:attribute>
     <xsl:attribute name="space-after.maximum">6pt</xsl:attribute>
     <xsl:attribute name="space-before.minimum">0pt</xsl:attribute>
     <xsl:attribute name="space-after.minimum">0pt</xsl:attribute>
  </xsl:attribute-set>

  <xsl:attribute-set name="section.title.properties">
     <xsl:attribute name="font-size"><xsl:value-of select="$body.font.size"/></xsl:attribute>
     <xsl:attribute name="space-before">0pt</xsl:attribute>
     <xsl:attribute name="space-after">0pt</xsl:attribute>
     <xsl:attribute name="space-before.optimum">0pt</xsl:attribute>
     <xsl:attribute name="space-after.optimum">0pt</xsl:attribute>
     <xsl:attribute name="space-before.maximum">0pt</xsl:attribute>
     <xsl:attribute name="space-after.maximum">0pt</xsl:attribute>
     <xsl:attribute name="space-before.minimum">0pt</xsl:attribute>
     <xsl:attribute name="space-after.minimum">0pt</xsl:attribute>
  </xsl:attribute-set>

  <xsl:attribute-set name="section.title.level1.properties">
     <xsl:attribute name="font-size"><xsl:value-of select="$body.font.size"/></xsl:attribute>
     <xsl:attribute name="font-weight">bold</xsl:attribute>
     <xsl:attribute name="space-before">18pt</xsl:attribute>
     <xsl:attribute name="space-after">6pt</xsl:attribute>
  </xsl:attribute-set>

  <xsl:attribute-set name="section.title.level2.properties">
     <xsl:attribute name="font-size"><xsl:value-of select="$body.font.size"/></xsl:attribute>
     <xsl:attribute name="font-weight">bold</xsl:attribute>
     <xsl:attribute name="space-before">18pt</xsl:attribute>
     <xsl:attribute name="space-after">6pt</xsl:attribute>
  </xsl:attribute-set>

  <xsl:attribute-set name="section.title.level3.properties">
     <xsl:attribute name="font-size"><xsl:value-of select="$body.font.size"/></xsl:attribute>
     <xsl:attribute name="font-weight">normal</xsl:attribute>
     <xsl:attribute name="font-style">italic</xsl:attribute>
     <xsl:attribute name="space-before">18pt</xsl:attribute>
     <xsl:attribute name="space-after">6pt</xsl:attribute>
  </xsl:attribute-set>

  <xsl:attribute-set name="section.title.level4.properties">
     <xsl:attribute name="font-size"><xsl:value-of select="$body.font.size"/></xsl:attribute>
     <xsl:attribute name="font-weight">normal</xsl:attribute>
     <xsl:attribute name="font-style">italic</xsl:attribute>
     <xsl:attribute name="space-before">18pt</xsl:attribute>
     <xsl:attribute name="space-after">6pt</xsl:attribute>
  </xsl:attribute-set>

  <xsl:attribute-set name="section.title.level5.properties">
     <xsl:attribute name="font-size"><xsl:value-of select="$body.font.size"/></xsl:attribute>
     <xsl:attribute name="font-weight">normal</xsl:attribute>
     <xsl:attribute name="font-style">italic</xsl:attribute>
     <xsl:attribute name="space-before">18pt</xsl:attribute>
     <xsl:attribute name="space-after">6pt</xsl:attribute>
  </xsl:attribute-set>

  <xsl:attribute-set name="section.title.level6.properties">
     <xsl:attribute name="font-size"><xsl:value-of select="$body.font.size"/></xsl:attribute>
     <xsl:attribute name="font-weight">normal</xsl:attribute>
     <xsl:attribute name="font-style">italic</xsl:attribute>
     <xsl:attribute name="space-before">18pt</xsl:attribute>
     <xsl:attribute name="space-after">6pt</xsl:attribute>
  </xsl:attribute-set>

  <xsl:attribute-set name="formal.title.properties">
     <xsl:attribute name="font-family"><xsl:value-of select="$body.font.family"/></xsl:attribute>
     <xsl:attribute name="font-size"><xsl:value-of select="$body.font.size"/></xsl:attribute>
     <xsl:attribute name="font-weight">bold</xsl:attribute>
     <xsl:attribute name="space-before">0pt</xsl:attribute>
     <xsl:attribute name="space-after">6pt</xsl:attribute>
  </xsl:attribute-set>

  <xsl:attribute-set name="admonition.title.properties">
     <xsl:attribute name="font-family"><xsl:value-of select="$body.font.family"/></xsl:attribute>
     <xsl:attribute name="font-size"><xsl:value-of select="$body.font.size"/></xsl:attribute>
     <xsl:attribute name="font-weight">bold</xsl:attribute>
     <xsl:attribute name="space-before">0pt</xsl:attribute>
     <xsl:attribute name="space-after">6pt</xsl:attribute>
  </xsl:attribute-set>

  <xsl:attribute-set name="abstract.title.properties">
     <xsl:attribute name="font-family"><xsl:value-of select="$body.font.family"/></xsl:attribute>
     <xsl:attribute name="font-size">12pt</xsl:attribute>
     <xsl:attribute name="font-weight">bold</xsl:attribute>
     <xsl:attribute name="space-before">0pt</xsl:attribute>
     <xsl:attribute name="space-after">6pt</xsl:attribute>
  </xsl:attribute-set>

  <xsl:attribute-set name="cover.title.properties" use-attribute-sets="component.title.properties">
     <xsl:attribute name="space-before">12pt</xsl:attribute>
     <xsl:attribute name="space-before.optimum">12pt</xsl:attribute>
     <xsl:attribute name="space-before.maximum">100pt</xsl:attribute>
     <xsl:attribute name="space-before.minimum">0pt</xsl:attribute>
     <xsl:attribute name="space-after">6pt</xsl:attribute>
     <xsl:attribute name="space-before.optimum">6pt</xsl:attribute>
     <xsl:attribute name="space-before.maximum">12pt</xsl:attribute>
     <xsl:attribute name="space-before.minimum">0pt</xsl:attribute>
  </xsl:attribute-set>
  

  <xsl:param name="body.start.indent">0pt</xsl:param>
  <xsl:param name="alignment">left</xsl:param>
  <xsl:param name="body.font.family">sans-serif</xsl:param>
  <xsl:param name="body.font.master">12</xsl:param>

  <xsl:param name="title.font.family">sans-serif</xsl:param>
  <xsl:param name="preface.autolabel">0</xsl:param>
  <xsl:param name="chapter.autolabel">1</xsl:param>
  <xsl:param name="reference.autolabel">a</xsl:param>
  <xsl:param name="appendix.autolabel">A</xsl:param>
  <xsl:param name="section.autolabel">1</xsl:param>
  <xsl:param name="section.autolabel.max.depth">5</xsl:param>
  <xsl:param name="section.label.includes.component.label">1</xsl:param>

  <xsl:attribute-set name="book.titlepage.recto.style">
     <xsl:attribute name="font-size"><xsl:value-of select="$body.font.size"/></xsl:attribute>
     <xsl:attribute name="font-weight">normal</xsl:attribute>
  </xsl:attribute-set>

  <xsl:attribute-set name="book.titlepage.verso.style">
     <xsl:attribute name="font-size"><xsl:value-of select="$body.font.size"/></xsl:attribute>
     <xsl:attribute name="font-weight">normal</xsl:attribute>
  </xsl:attribute-set>

  <xsl:param name="shade.verbatim">1</xsl:param>
  <xsl:param name="title.margin.left">0pt</xsl:param>

  <!--
  Copyright
  Set the copyright statement to Crown requirements.
  -->
  <xsl:template match="d:copyright" mode="titlepage.mode">
      <xsl:call-template name="dingbat">
          <xsl:with-param name="dingbat">copyright</xsl:with-param>
      </xsl:call-template>
      <xsl:call-template name="gentext.space"/>
      <xsl:apply-templates select="d:holder" mode="titlepage.mode"/>
      <xsl:call-template name="gentext.space"/>
      <xsl:call-template name="copyright.years">
          <xsl:with-param name="years" select="d:year"/>
          <xsl:with-param name="print.ranges" select="$make.year.ranges"/>
          <xsl:with-param name="single.year.ranges"
                          select="$make.single.year.ranges"/>
      </xsl:call-template>. All Rights Reserved
  </xsl:template>





<!--
Release information and Revision History
Display the release information on titlepage and display the
revision history in a table.
-->
<xsl:template match="d:releaseinfo" mode="titlepage.mode">
    <xsl:apply-templates mode="titlepage.mode"/>
</xsl:template>




<!--
Blank page content
-->
<xsl:template name="blank.page.content">
    <fo:static-content flow-name="blank-body">
        <fo:block  text-align="center">
This page is intentionally left blank.
        </fo:block>
    </fo:static-content>
</xsl:template>

</xsl:stylesheet>

