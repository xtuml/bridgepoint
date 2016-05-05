<!-- 
  UK Crown Copyright (c) 2016. All Rights Reserved
-->
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:d="http://docbook.org/ns/docbook" xmlns:fo="http://www.w3.org/1999/XSL/Format" exclude-result-prefixes="d" version="1.0">
<xsl:template match="d:productionset">
  <xsl:variable name="id"><xsl:call-template name="object.id"/></xsl:variable>

  <xsl:choose>
    <xsl:when test="d:title">
      <fo:block id="{$id}" xsl:use-attribute-sets="formal.object.properties">
        <xsl:call-template name="formal.object.heading">
          <xsl:with-param name="placement" select="'before'"/>
        </xsl:call-template>
        <fo:table table-layout="fixed" width="100%">
          <fo:table-column column-number="1" column-width="10%"/>
          <fo:table-column column-number="2" column-width="20%"/>
          <fo:table-column column-number="3" column-width="5%"/>
          <fo:table-column column-number="4" column-width="65%"/>
          <fo:table-body start-indent="0pt" end-indent="0pt">
            <xsl:apply-templates select="d:production|d:productionrecap"/>
          </fo:table-body>
        </fo:table>
      </fo:block>
    </xsl:when>
    <xsl:otherwise>
      <fo:table id="{$id}" table-layout="fixed" width="100%">
        <fo:table-column column-number="1" column-width="10%"/>
        <fo:table-column column-number="2" column-width="20%"/>
        <fo:table-column column-number="3" column-width="5%"/>
        <fo:table-column column-number="4" column-width="65%"/>
        <fo:table-body start-indent="0pt" end-indent="0pt">
          <xsl:apply-templates select="d:production|d:productionrecap"/>
        </fo:table-body>
      </fo:table>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template match="d:production">
  <xsl:param name="recap" select="false()"/>
  <xsl:variable name="id"><xsl:call-template name="object.id"/></xsl:variable>
  <fo:table-row>
    <fo:table-cell>
      <fo:block text-align="start" space-after="6pt" space-after.conditionality="retain">
        <xsl:text>[</xsl:text>
        <xsl:number count="d:production" level="any"/>
        <xsl:text>]</xsl:text>
      </fo:block>
    </fo:table-cell>
    <fo:table-cell>
      <fo:block text-align="end" space-after="6pt" space-after.conditionality="retain">
        <xsl:choose>
          <xsl:when test="$recap">
            <fo:basic-link internal-destination="{$id}"
                           xsl:use-attribute-sets="xref.properties">
              <xsl:apply-templates select="d:lhs"/>
            </fo:basic-link>
          </xsl:when>
          <xsl:otherwise>
            <fo:wrapper id="{$id}">
              <xsl:apply-templates select="d:lhs"/>
            </fo:wrapper>
          </xsl:otherwise>
        </xsl:choose>
      </fo:block>
    </fo:table-cell>
    <fo:table-cell>
      <fo:block text-align="center" space-after="6pt" space-after.conditionality="retain">
        <xsl:copy-of select="$ebnf.assignment"/>
      </fo:block>
    </fo:table-cell>
    <fo:table-cell>
      <fo:block  space-after="6pt" space-after.conditionality="retain">
      <fo:block>
        <xsl:apply-templates select="d:rhs"/>
        <xsl:copy-of select="$ebnf.statement.terminator"/>
      </fo:block>
      <xsl:if test="d:rhs/d:lineannotation|d:constraint">
        <fo:block>
          <xsl:apply-templates select="d:rhs/d:lineannotation" mode="rhslo"/>
          <xsl:apply-templates select="d:constraint"/>
        </fo:block>
      </xsl:if>
      </fo:block>
    </fo:table-cell>
  </fo:table-row>
</xsl:template>

  <xsl:template match="d:rhs/d:lineannotation" mode="rhslo">
      <xsl:text>(*&#160;</xsl:text>
      <xsl:apply-templates/>
      <xsl:text>&#160;*)</xsl:text>
  </xsl:template>

</xsl:stylesheet>
