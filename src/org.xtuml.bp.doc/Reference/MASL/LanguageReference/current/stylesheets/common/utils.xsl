<?xml version="1.0"?>
<!-- 
  UK Crown Copyright (c) 2016. All Rights Reserved
-->
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:d="http://docbook.org/ns/docbook" xmlns:dt="http://exslt.org/dates-and-times" xmlns:fo="http://www.w3.org/1999/XSL/Format" exclude-result-prefixes="d" version="1.0">

  <xsl:template match="*|text()" mode="format-date">
    <xsl:variable name="full-date" select="dt:date(.)"/>
    <xsl:variable name="month-date" select="dt:date(concat(.,'-01'))"/>
    <xsl:choose>
      <xsl:when test="$full-date != ''">
        <xsl:value-of select="dt:day-in-month($full-date)"/>
        <xsl:text> </xsl:text>
        <xsl:value-of select="dt:month-name($full-date)"/>
        <xsl:text> </xsl:text>
        <xsl:value-of select="dt:year($full-date)"/>
      </xsl:when>
      <xsl:when test="$month-date != ''">
        <xsl:value-of select="dt:month-name($month-date)"/>
        <xsl:text> </xsl:text>
        <xsl:value-of select="dt:year($month-date)"/>
      </xsl:when>
      <xsl:otherwise>
        <xsl:value-of select="."/>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:template>

</xsl:stylesheet>

