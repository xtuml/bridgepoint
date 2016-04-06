<?xml version="1.0"?>
<!-- 
  UK Crown Copyright (c) 2016. All Rights Reserved
-->
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:d="http://docbook.org/ns/docbook" xmlns:fo="http://www.w3.org/1999/XSL/Format" exclude-result-prefixes="d" version="1.0">

  <xsl:template match="d:authorgroup" mode="titlepage.mode">
    <fo:block space-before="60pt">
      <fo:table table-layout="fixed" text-align="left" width="100%">
        <fo:table-column column-width="15%"/>
        <fo:table-column column-width="40%"/>
        <fo:table-column column-width="25%"/>
        <fo:table-column column-width="5%"/>
        <fo:table-column column-width="14%"/>
        <fo:table-header>
          <fo:table-cell>
            <fo:block/>
          </fo:table-cell>
          <fo:table-cell>
            <fo:block/>
          </fo:table-cell>
          <fo:table-cell>
            <fo:block font-weight="bold" text-align="center" space-after="6pt" space-after.conditionality="retain">Signature</fo:block>
          </fo:table-cell>
          <fo:table-cell>
            <fo:block/>
          </fo:table-cell>
          <fo:table-cell>
            <fo:block font-weight="bold" text-align="center" space-after="6pt" space-after.conditionality="retain">Date</fo:block>
          </fo:table-cell>
        </fo:table-header>
        <fo:table-body>
          <xsl:apply-templates select="d:author" mode="titlepage.credits.mode">
            <xsl:with-param name="label">Author</xsl:with-param>
          </xsl:apply-templates>
          <xsl:apply-templates select="d:othercredit[@class='other' and @otherclass='approver']" mode="titlepage.credits.mode">
            <xsl:with-param name="label">Approver</xsl:with-param>
          </xsl:apply-templates>
        </fo:table-body>
      </fo:table>
    </fo:block>
  </xsl:template>

  <xsl:template match="d:author|d:othercredit" mode="titlepage.credits.mode">
    <xsl:param name="label"/>
    <fo:table-row>
      <fo:table-cell>
        <fo:block space-after="12pt" space-after.conditionality="retain">
            <xsl:call-template name="gentext">
              <xsl:with-param name="key" select="$label"/>
            </xsl:call-template>
            <xsl:text>:</xsl:text>
        </fo:block>
      </fo:table-cell>
      <fo:table-cell>
        <fo:block padding-before="0pt" space-after="12pt" space-after.conditionality="retain">
          <fo:block>
            <xsl:call-template name="person.name"/>
          </fo:block>
          <xsl:if test="d:affiliation/d:jobtitle">
            <fo:block>
              <xsl:text>(</xsl:text>
              <xsl:value-of select="d:affiliation/d:jobtitle"/>
              <xsl:text>)</xsl:text>
            </fo:block>
          </xsl:if>
        </fo:block>
      </fo:table-cell>
      <fo:table-cell display-align="after">
        <fo:block padding-before="0pt" space-after="12pt" space-after.conditionality="retain" border-after-style="dashed"></fo:block>
      </fo:table-cell>
      <fo:table-cell>
        <fo:block/>
      </fo:table-cell>
      <fo:table-cell display-align="after">
        <fo:block padding-before="0pt" space-after="12pt" space-after.conditionality="retain" border-after-style="dashed"></fo:block>
      </fo:table-cell>
    </fo:table-row>
  </xsl:template>


</xsl:stylesheet>
