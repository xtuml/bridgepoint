<?xml version="1.0"?>
<!-- 
  UK Crown Copyright (c) 2016. All Rights Reserved
-->
<xsl:stylesheet xmlns="http://docbook.org/ns/docbook" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:db="http://docbook.org/ns/docbook" exclude-result-prefixes="db" version="1.0">

  <xsl:output method="xml" indent="yes"/>

  <xsl:template match="/">
    <appendix xml:id="AlphabeticalSyntaxReference">
      <title>Alphabetical Syntax Reference</title>
      <productionset>
        <xsl:for-each select="//db:production">
          <xsl:sort select="db:lhs"/>
          <productionrecap linkend="{@xml:id}"/>
        </xsl:for-each>
      </productionset>
    </appendix>
  </xsl:template>

</xsl:stylesheet>
