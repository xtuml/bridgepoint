<?xml version="1.0" encoding="UTF-8"?>

<!-- ===================================================================

File:      state.xsl, 1.10
Modified:  10/01/03, 14:32:25

Copyright (c) 2002-2013 Mentor Graphics CorporationMentor Graphics CorporationMentor Graphics CorporationMentor Graphics CorporationMentor Graphics CorporationMentor Graphics Corporation  All rights reserved.

========================================================================
This document contains information proprietary and confidential to
Project Technology, Inc. and is not for external distribution.
========================================================================

Debugger Trace Stylesheet
   
Output:  Text

Dependencies: None

Description:  This stylesheet takes protocol output by an executing
              target and turns it into Object Action Language. While
              doing so, it adds hyperlinks so that clicking on items
              in the log can open a browser at the relevant place.

==================================================================== -->

<!DOCTYPE xsl:stylesheet [
   <!ENTITY nbsp "&#160;">
   <!ENTITY xtuml "<sup>X</sup><sub>T</sub>UML">
   <!ENTITY reg "(R)">
]>

<xsl:stylesheet version="1.0" 
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  
  <xsl:include href="user_invoke.xsl"/>
  <xsl:include href="script_utils.xsl"/>
   
  <xsl:output method="html" 
    encoding="UTF-8"
    indent="yes"/>
               
  <!-- We don't want to display the domain information -->
  <xsl:template match="/system/content/domain">
  </xsl:template>

  <xsl:template match="/system/log">
    <xsl:value-of select="@severity"/>: <xsl:value-of select="@message"/>
  </xsl:template>

  <xsl:template match="brkpt">
    <font color="red"> *** Breakpoint <xsl:value-of select="@name"/> on <xsl:value-of select="@element"/> Encountered *** </font>
  </xsl:template>

  <xsl:template match="debug"><xsl:for-each select="*"/></xsl:template>

  <xsl:template match="debug[@cmd='Add Domain']">
    <xsl:variable name="dom_name">
      <xsl:call-template name="get-dom-name">
        <xsl:with-param name="dom-id" select="@dom-id"/>
      </xsl:call-template>
    </xsl:variable>
    <font color="orange">Domain </font>
    <font color="red"><xsl:value-of select="$dom_name"/></font>
    <font color="orange"> added</font>:
    <blockquote>
      File: <xsl:value-of select="xml-url"/>
    </blockquote>
  </xsl:template>
   
  <xsl:template match="debug[@cmd='Session Ready']">
    <font color="orange">Session Ready</font>:
    <blockquote>
      Model Compiler: <xsl:value-of select="product"/>
    <xsl:text> </xsl:text>
    <xsl:value-of select="version"/>
    <br/>
      Trace Formatting: State - Version: 1.10
    </blockquote>
    <hr/>
  </xsl:template>
  
  <!-- Notify Session Done -->
  <xsl:template match="debug[@cmd='Notify Session Done']">
    <font color="red">Target closed debugging session</font>
  </xsl:template>

  <!-- Notify Target Shutdown -->
  <xsl:template match="debug[@cmd='Notify Target Shutdown']">
    <font color="red">Target shutting down </font>
  </xsl:template>

  <!-- New State -->
  <xsl:template match="debug[@cmd='Notify State Entry']">
    <!--xsl:value-of select="@ts"/-->
    <!--xsl:text> - </xsl:text-->
    <xsl:variable name="drv-lbl">
      <xsl:call-template name="get-evt-drv-lbl">
        <xsl:with-param name="dom-id" select="@dom-id"/>
        <xsl:with-param name="smevt-id" select="@smevt-id"/>
      </xsl:call-template>
    </xsl:variable>
    <xsl:variable name="mning">
      <xsl:call-template name="get-evt-mning">
        <xsl:with-param name="dom-id" select="@dom-id"/>
        <xsl:with-param name="smevt-id" select="@smevt-id"/>
      </xsl:call-template>
    </xsl:variable>
    <font color="black">EVENT : </font>
    <font color="red"><xsl:value-of select="$drv-lbl"/> : '<xsl:value-of select="$mning"/>'</font>
    <br/>
    <xsl:variable name="orig-ident">
      <xsl:call-template name="get-identifier">
        <xsl:with-param name="dom-id" select="@dom-id"/>
        <xsl:with-param name="inst-id" select="@orig-obj-inst-id"/>
      </xsl:call-template>
    </xsl:variable>
    <xsl:variable name="orig-tree-item-id">
      <xsl:call-template name="get-obj-tree-item-id">
        <xsl:with-param name="dom-id" select="@dom-id"/>
        <xsl:with-param name="obj-inst-id" select="@orig-obj-inst-id"/>
      </xsl:call-template>
    </xsl:variable>
    <a href="{$orig-tree-item-id}">
      <font color="black">ORIG OBJ INST: <xsl:value-of select="$orig-ident"/></font><br/>
    </a>
    <xsl:variable name="dest-ident">
      <xsl:call-template name="get-identifier">
        <xsl:with-param name="dom-id" select="@dom-id"/>
        <xsl:with-param name="inst-id" select="@obj-inst-id"/>
      </xsl:call-template>
    </xsl:variable>
    <xsl:variable name="dest-tree-item-id">
      <xsl:call-template name="get-obj-tree-item-id">
        <xsl:with-param name="dom-id" select="@dom-id"/>
        <xsl:with-param name="obj-inst-id" select="@obj-inst-id"/>
      </xsl:call-template>
    </xsl:variable>
    <a href="{$dest-tree-item-id}">
    <font color="black">DEST OBJ INST: <xsl:value-of select="$dest-ident"/></font><br/>
    </a>
    <xsl:variable name="prev-state-name">
      <xsl:call-template name="get-state-name">
        <xsl:with-param name="dom-id" select="@dom-id"/>
        <xsl:with-param name="smstt-id" select="@prev-smstt-id"/>
        <xsl:with-param name="sm-id" select="@sm-id"/>        
      </xsl:call-template>
    </xsl:variable>
    <font color="black">PREV STATE: </font>
    '<font color="red"><xsl:value-of select="$prev-state-name"/></font>'
    <br/>
    <xsl:variable name="curr-state-name">
      <xsl:call-template name="get-state-name">
        <xsl:with-param name="dom-id" select="@dom-id"/>
        <xsl:with-param name="smstt-id" select="@smstt-id"/>
        <xsl:with-param name="sm-id" select="@sm-id"/>        
      </xsl:call-template>
    </xsl:variable>
    <font color="black">NEW STATE: </font>
    '<font color="red"><xsl:value-of select="$curr-state-name"/></font>'
  </xsl:template>

  <!-- Notify State Exit -->
  <xsl:template match="debug[@cmd='Notify State Exit']">
    <hr/>
  </xsl:template>

  <!-- Async Function Pending -->
  <xsl:template match="debug[@cmd='Notify Async Function Pending']">
    <xsl:variable name="fn-name">
      <xsl:call-template name="get-fn-name">
        <xsl:with-param name="dom-id" select="@dom-id"/>
        <xsl:with-param name="fn-id" select="@sync-id"/>
      </xsl:call-template>
    </xsl:variable>
    <xsl:variable name="dom-name">
      <xsl:call-template name="get-dom-name">
        <xsl:with-param name="dom-id" select="@dom-id"/>
      </xsl:call-template>
    </xsl:variable>
    <font color="black">INFO:	</font>
    <font color="purple"><xsl:value-of select="$dom-name"/>::<xsl:value-of select="$fn-name"/>(
    <xsl:apply-templates select="value" mode="function"/>) (id=<xsl:value-of select="@funcinv-id"/>) Invocation Pending.</font>
  </xsl:template>

  <!-- Async Function Executing -->
  <xsl:template match="debug[@cmd='Notify Async Function Executing']">
    <xsl:variable name="fn-name">
      <xsl:call-template name="get-fn-name">
        <xsl:with-param name="dom-id" select="@dom-id"/>
        <xsl:with-param name="fn-id" select="@sync-id"/>
      </xsl:call-template>
    </xsl:variable>
    <xsl:variable name="dom-name">
      <xsl:call-template name="get-dom-name">
        <xsl:with-param name="dom-id" select="@dom-id"/>
      </xsl:call-template>
    </xsl:variable>
    <font color="black">INFO:	</font>
    <font color="purple"><xsl:value-of select="$dom-name"/>::<xsl:value-of select="$fn-name"/>(
    <xsl:apply-templates select="value" mode="function"/>) (id=<xsl:value-of select="@funcinv-id"/>) Invocation Execution Ready.</font>
  </xsl:template>

  <!-- Function Entry -->
  <xsl:template match="debug[@cmd='Notify Function Entry']">
    <xsl:if test="@funcinv-id!='0'">
    <xsl:variable name="fn-name">
      <xsl:call-template name="get-fn-name">
        <xsl:with-param name="dom-id" select="@dom-id"/>
        <xsl:with-param name="fn-id" select="@sync-id"/>
      </xsl:call-template>
    </xsl:variable>
    <xsl:variable name="dom-name">
      <xsl:call-template name="get-dom-name">
        <xsl:with-param name="dom-id" select="@dom-id"/>
      </xsl:call-template>
    </xsl:variable>
    <font color="black">INFO:	</font>
    <font color="purple"><xsl:value-of select="$dom-name"/>::<xsl:value-of select="$fn-name"/>(
    <xsl:apply-templates select="value" mode="function"/>) (id=<xsl:value-of select="@funcinv-id"/>) Invocation Begin.</font>
    </xsl:if>
  </xsl:template>

  <!-- Notify Function Exit -->
  <xsl:template match="debug[@cmd='Notify Function Exit']">
    <!-- 
        If oal-invoked is equal to '0', then that implies that is a 
        function (user-invoked or init) that we want to log and place
        a horizontal line after
    -->
    <xsl:if test="@oal-invoked='0'">
      <xsl:variable name="fn-name">
        <xsl:call-template name="get-fn-name">
          <xsl:with-param name="dom-id" select="@dom-id"/>
          <xsl:with-param name="fn-id" select="@sync-id"/>
        </xsl:call-template>
      </xsl:variable>
      <xsl:variable name="dom-name">
        <xsl:call-template name="get-dom-name">
          <xsl:with-param name="dom-id" select="@dom-id"/>
        </xsl:call-template>
      </xsl:variable>
      <font color="black">INFO:	</font>
      <font color="purple"><xsl:value-of select="$dom-name"/>::<xsl:value-of select="$fn-name"/> Invocation End.</font>
      <hr/>
    </xsl:if>
  </xsl:template>

  <!-- Log Control execution -->
  <xsl:template match="debug[@cmd='Log Control execution']">
    <font color="black">STMT: </font>
    <font color="blue">control </font>
    <font color="blue"><xsl:value-of select="command"/></font>
    <font color="blue">;</font>
  </xsl:template>

  <!-- Log Event Can't Happen -->
  <xsl:template match="debug[@cmd='Log Event Cant Happen']">
    <xsl:variable name="drv-lbl">
      <xsl:call-template name="get-evt-drv-lbl">
        <xsl:with-param name="dom-id" select="@dom-id"/>
        <xsl:with-param name="smevt-id" select="@smevt-id"/>
      </xsl:call-template>
    </xsl:variable>
    <xsl:variable name="mning">
      <xsl:call-template name="get-evt-mning">
        <xsl:with-param name="dom-id" select="@dom-id"/>
        <xsl:with-param name="smevt-id" select="@smevt-id"/>
      </xsl:call-template>
    </xsl:variable>
    <xsl:variable name="state-name">
      <xsl:call-template name="get-state-name">
        <xsl:with-param name="dom-id" select="@dom-id"/>
        <xsl:with-param name="smstt-id" select="@smstt-id"/>
        <xsl:with-param name="sm-id" select="@sm-id"/>        
      </xsl:call-template>
    </xsl:variable>
    <xsl:variable name="dest-ident">
      <xsl:call-template name="get-identifier">
        <xsl:with-param name="dom-id" select="@dom-id"/>
        <xsl:with-param name="inst-id" select="@obj-inst-id"/>
      </xsl:call-template>
    </xsl:variable>
    <font color="black">EVENT : </font>
    <font color="red"><xsl:value-of select="$drv-lbl"/> : '<xsl:value-of select="$mning"/>' Resulted in CAN'T HAPPEN</font>
    <br/>
    <font color="black">DEST OBJ INST: <xsl:value-of select="$dest-ident"/></font><br/>
    <font color="black">CURRENT STATE: </font>
    '<font color="red"><xsl:value-of select="$state-name"/></font>'
    <hr/>
  </xsl:template>

  <!-- Log Occurrence -->
  <xsl:template match="debug[@cmd='Log Occurrence']">
    <font color="red"><xsl:value-of select="severity"/>: <xsl:value-of select="message"/></font>
  </xsl:template>

</xsl:stylesheet>
