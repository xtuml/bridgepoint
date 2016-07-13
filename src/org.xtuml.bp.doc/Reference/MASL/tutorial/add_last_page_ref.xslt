<?xml version="1.0" encoding="UTF-8"?>
<!-- 
  UK Crown Copyright (c) 2016. All Rights Reserved
-->
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format">
  <xsl:import href="identity.xslt"/>

  <xsl:template match="/fo:root/fo:page-sequence[last()]/fo:flow[last()]">
    <xsl:copy>
      <xsl:apply-templates select="@* | node()"/>
      <fo:block id="last-page"/>
    </xsl:copy>
  </xsl:template>

</xsl:stylesheet>
