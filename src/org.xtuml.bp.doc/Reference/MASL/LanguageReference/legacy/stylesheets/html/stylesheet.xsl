<?xml version="1.0"?>
<!-- 
  UK Crown Copyright (c) 2016. All Rights Reserved
-->
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:d="http://docbook.org/ns/docbook" exclude-result-prefixes="d" version="1.0">
  <xsl:import href="http://docbook.sourceforge.net/release/xsl-ns/current/html/chunkfast.xsl"/>
  <xsl:import href="../common/common.xsl"/>
  <xsl:import href="../../generated/stylesheets/html/titlepage.xsl"/>
  <xsl:param name="base.dir" select="'./html/'"/>
  <xsl:param name="chunker.output.encoding" select="'UTF-8'"/>
  <xsl:param name="chunker.output.indent" select="'yes'"/>
  <xsl:param name="chunk.separate.lots" select="0"/>
  <xsl:param name="chunk.tocs.and.lots" select="1"/>
  <xsl:param name="chunk.section.depth" select="1"/>
  <xsl:param name="chunk.first.sections" select="1"/>
  <xsl:param name="chunk.quietly" select="1"/>
  <xsl:param name="use.id.as.filename" select="1"/>
  <xsl:param name="toc.section.depth" select="3"/>
  <xsl:param name="toc.max.depth" select="3"/>
  <xsl:param name="generate.section.toc.level" select="1"/>
  <xsl:param name="draft.watermark.image" select="''"/>
  <xsl:param name="id.warnings" select="1"/>
  <xsl:param name="css.decoration" select="0"/>
  <xsl:param name="make.clean.html" select="1"/>
  <xsl:param name="html.extra.head.links" select="1"/>
  <xsl:param name="navig.showtitles" select="1"/>

  <!--
  Change the docbook EBNF defaults to comply with
  the ISO/IEC 14977:1996(E) standard for EBNF
  -->

  <xsl:template match="d:rhs/d:lineannotation" mode="rhslo">
      <xsl:text>(*&#160;</xsl:text>
      <xsl:apply-templates/>
      <xsl:text>&#160;*)</xsl:text>
      <br/>
  </xsl:template>


  <xsl:template name="user.header.navigation">
      <xsl:call-template name="security.surround">
          <xsl:with-param name="surround" select="//d:book"/>
      </xsl:call-template>
  </xsl:template>

  <xsl:template name="user.footer.navigation">
      <xsl:call-template name="security.surround">
          <xsl:with-param name="surround" select="//d:book"/>
      </xsl:call-template>
  </xsl:template>

  <xsl:template name="security.surround">
      <xsl:param name="surround"/>
      <xsl:element name="div">
          <xsl:attribute name="align">center</xsl:attribute>
          <xsl:value-of select="$surround/@security"/>
      </xsl:element>
  </xsl:template>


  <xsl:template match="d:copyright" mode="titlepage.mode">
      <xsl:if test="generate-id() = generate-id(//d:refentryinfo/d:copyright[1])
          and ($stylesheet.result.type = 'html' or $stylesheet.result.type = 'xhtml')">
        <h2>Copyright</h2>
      </xsl:if>
      <p>
          <xsl:apply-templates select="d:holder" mode="titlepage.mode"/>
          <xsl:call-template name="gentext.space"/>
          <xsl:call-template name="dingbat">
              <xsl:with-param name="dingbat">copyright</xsl:with-param>
          </xsl:call-template>
          <xsl:call-template name="gentext.space"/>
          <xsl:call-template name="copyright.years">
              <xsl:with-param name="years" select="d:year"/>
              <xsl:with-param name="print.ranges" select="$make.year.ranges"/>
              <xsl:with-param name="single.year.ranges"
                              select="$make.single.year.ranges"/>
          </xsl:call-template>. All Rights Reserved.
      </p>
  </xsl:template>


  <xsl:template match="d:biblioid" mode="titlepage.mode">
      <xsl:call-template name="paragraph">
          <xsl:with-param name="class" select="name(.)"/>
          <xsl:with-param name="content">
              <xsl:apply-templates mode="titlepage.mode"/>
          </xsl:with-param>
      </xsl:call-template>
  </xsl:template>


  <xsl:template match="d:revhistory" mode="titlepage.mode">

      <xsl:variable name="id">
          <xsl:call-template name="object.id"/>
      </xsl:variable>

      <xsl:variable name="title">
          <xsl:call-template name="gentext">
              <xsl:with-param name="key">RevHistory</xsl:with-param>
          </xsl:call-template>
      </xsl:variable>

      <xsl:variable name="contents">
          <div>
              <table border="1" width="100%" summary="Revision history">
                  <tr>
                      <th align="{$direction.align.start}" valign="top" colspan="3">
                          <b>
                              <xsl:call-template name="gentext">
                                  <xsl:with-param name="key" select="'RevHistory'"/>
                              </xsl:call-template>
                          </b>
                      </th>
                  </tr>
                  <xsl:apply-templates mode="titlepage.mode"/>
              </table>
          </div>
      </xsl:variable>

      <xsl:choose>
          <xsl:when test="$generate.revhistory.link != 0">
             <!-- Compute name of revhistory file -->
              <xsl:variable name="file">
	        <xsl:call-template name="ln.or.rh.filename">
	          <xsl:with-param name="is.ln" select="false()"/>
	        </xsl:call-template>
              </xsl:variable>

              <xsl:variable name="filename">
                <xsl:call-template name="make-relative-filename">
                  <xsl:with-param name="base.dir" select="$base.dir"/>
                  <xsl:with-param name="base.name" select="$file"/>
                </xsl:call-template>
              </xsl:variable>

              <a href="{$file}"> 
                 <xsl:copy-of select="$title"/>
              </a>

              <xsl:call-template name="write.chunk">
                  <xsl:with-param name="filename" select="$filename"/>
                  <xsl:with-param name="quiet" select="$chunk.quietly"/>
                  <xsl:with-param name="content">
                      <xsl:call-template name="user.preroot"/>
                      <html>
                          <head>
                              <xsl:call-template name="system.head.content"/>
                              <xsl:call-template name="head.content">
                                  <xsl:with-param name="title">
                                      <xsl:value-of select="$title"/>
                                      <xsl:if test="../../title">
                                          <xsl:value-of select="concat(' (', ../../title, ')')"/>
                                      </xsl:if>
                                  </xsl:with-param>
                              </xsl:call-template>
                              <xsl:call-template name="user.head.content"/>
                          </head>
                          <body>
                              <xsl:call-template name="body.attributes"/>
                              <xsl:copy-of select="$contents"/>
                          </body>
                      </html>
                      <xsl:text>&#x0a;</xsl:text>
                  </xsl:with-param>
              </xsl:call-template>
          </xsl:when>
          <xsl:otherwise>
              <xsl:copy-of select="$contents"/>
          </xsl:otherwise>
      </xsl:choose>
  </xsl:template>

  <xsl:template match="d:revhistory/d:revision" mode="titlepage.mode">
      <xsl:variable name="revnumber" select="d:revnumber"/>
      <xsl:variable name="revdate"   select="d:date"/>
      <xsl:variable name="revremark" select="d:revremark|d:revdescription"/>
      <tr>
          <td align="{$direction.align.start}">
              <xsl:if test="$revnumber">
                  <xsl:call-template name="gentext">
                      <xsl:with-param name="key" select="'Revision'"/>
                  </xsl:call-template>
                  <xsl:call-template name="gentext.space"/>
                  <xsl:apply-templates select="$revnumber[1]" mode="titlepage.mode"/>
              </xsl:if>
          </td>
          <td align="{$direction.align.start}">
              <xsl:apply-templates select="$revdate[1]" mode="titlepage.mode"/>
          </td>
          <td align="{$direction.align.start}">
              <xsl:apply-templates select="$revremark[1]" mode="titlepage.mode"/>
          </td>
      </tr>
  </xsl:template>

  <xsl:template match="d:revhistory">
    <div>
      <table border="0" width="100%" summary="Revision history">
        <tr>
          <th align="{$direction.align.start}" valign="top" colspan="3">
            <b>
              <xsl:call-template name="gentext">
                <xsl:with-param name="key" select="'RevHistory'"/>
              </xsl:call-template>
            </b>
          </th>
        </tr>
        <xsl:apply-templates/>
      </table>
    </div>
  </xsl:template>

  <xsl:template match="d:revhistory/d:revision">
      <xsl:variable name="revnumber" select="d:revnumber"/>
      <xsl:variable name="revdate"   select="d:date"/>
      <xsl:variable name="revremark" select="d:revremark|d:revdescription"/>
      <tr>
          <td align="{$direction.align.start}">
              <xsl:if test="$revnumber">
                  <xsl:call-template name="gentext">
                      <xsl:with-param name="key" select="'Revision'"/>
                  </xsl:call-template>
                  <xsl:call-template name="gentext.space"/>
                  <xsl:apply-templates select="$revnumber"/>
              </xsl:if>
          </td>
          <td align="{$direction.align.start}">
            <xsl:apply-templates select="$revdate"/>
          </td>
          <td align="{$direction.align.start}">
            <xsl:apply-templates select="$revremark"/>
          </td>
      </tr>
  </xsl:template>



</xsl:stylesheet>
