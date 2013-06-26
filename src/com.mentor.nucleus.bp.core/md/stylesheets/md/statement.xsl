<?xml version="1.0" encoding="UTF-8"?>

<!-- ===================================================================

File:      statement.xsl, 1.42
Modified:  10/01/03, 14:32:31

(c) Copyright 2002-2013 Mentor Graphics Corporation  All rights reserved.

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
<pre>
      File: <xsl:value-of select="xml-url"/>
</pre>
  </xsl:template>
   
  <xsl:template match="debug[@cmd='Session Ready']">
    <font color="orange">Session Ready</font>:
<pre>
      Model Compiler: <xsl:value-of select="product"/>
      <xsl:text> </xsl:text><xsl:value-of select="version"/>
      Trace Formatting: Statement - Version: 1.42
</pre>
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

  <!-- Object Creation -->
  <xsl:template match="debug[@cmd='Notify Create Object Instance'] | debug[@cmd='Notify Create Object Instance No Variable']">
    <xsl:variable name="obj_key_lett">
      <xsl:call-template name="get-obj-key-lett">
        <xsl:with-param name="dom-id" select="@dom-id"/>
        <xsl:with-param name="obj-id" select="@obj-id"/>
      </xsl:call-template>
    </xsl:variable>
    <xsl:variable name="tree_item_id">
      <xsl:call-template name="get-obj-tree-item-id">
        <xsl:with-param name="dom-id" select="@dom-id"/>
        <xsl:with-param name="obj-id" select="@obj-id"/>
        <xsl:with-param name="obj-inst-id" select="@obj-inst-id"/>
      </xsl:call-template>
    </xsl:variable>
    <font color="black">STMT:	</font>
    <font color="blue">create object instance </font>
    <xsl:if test="local-variable">
      <a href="{$tree_item_id}">
        <font color = "purple"><xsl:value-of select="local-variable"/></font>
      </a>
    </xsl:if>
    <font color="blue"> of</font>
    <font color="red"><xsl:value-of select="$obj_key_lett"/></font>
    <font color="blue">;</font>
  </xsl:template>

  <!-- Log Creation Transition -->
  <xsl:template match="debug[@cmd='Log Creation Transition']">
    <xsl:variable name="obj_key_lett">
      <xsl:call-template name="get-obj-key-lett">
        <xsl:with-param name="dom-id" select="@dom-id"/>
        <xsl:with-param name="obj-id" select="@obj-id"/>
      </xsl:call-template>
    </xsl:variable>
    <xsl:variable name="tree_item_id">
      <xsl:call-template name="get-obj-tree-item-id">
        <xsl:with-param name="dom-id" select="@dom-id"/>
        <xsl:with-param name="obj-id" select="@obj-id"/>
        <xsl:with-param name="obj-inst-id" select="@obj-inst-id"/>
      </xsl:call-template>
    </xsl:variable>
    <xsl:variable name="ident">
      <xsl:call-template name="get-identifier">
        <xsl:with-param name="dom-id" select="@dom-id"/>
        <xsl:with-param name="inst-id" select="@obj-inst-id"/>
      </xsl:call-template>
    </xsl:variable>
    <font color="black">INFO:	</font>
    <font color="blue">Creation Transition for class </font>
    <font color="red"><xsl:value-of select="$obj_key_lett"/></font>
    <font color="blue">(instance id = <xsl:value-of select="$ident"/>).</font>
  </xsl:template>

  <!-- Object Deletion -->
  <xsl:template match="debug[@cmd='Notify Delete Object Instance']">
    <xsl:variable name="obj_key_lett">
      <xsl:call-template name="get-obj-key-lett">
        <xsl:with-param name="dom-id" select="@dom-id"/>
        <xsl:with-param name="obj-id" select="@obj-id"/>
      </xsl:call-template>
    </xsl:variable>
    <font color="black">STMT:	</font>
    <font color="blue">delete object instance </font>
    <font color = "purple"><xsl:value-of select="local-variable"/></font>
    <font color="blue"> of</font>
    <font color="red"><xsl:value-of select="$obj_key_lett"/></font>
    <font color="blue">;</font>
  </xsl:template>
   
  <!-- Attribute Modification -->
  <xsl:template match="debug[@cmd='Notify Modify Attribute']">
    <xsl:variable name="dom_id" select="@dom-id"/>
    <xsl:variable name="obj_id" select="@obj-id"/>
    <xsl:variable name="obj_inst_id" select="@obj-inst-id"/>
    <xsl:variable name="attr_id" select="@attr-id"/>
    <xsl:variable name="attr_name">
      <xsl:call-template name="get-attr-name">
        <xsl:with-param name="dom-id" select="@dom-id"/>
        <xsl:with-param name="obj-id" select="@obj-id"/>
        <xsl:with-param name="attr-id" select="@attr-id"/>
      </xsl:call-template>
    </xsl:variable>
    <xsl:variable name="tree_item_id">
      <xsl:call-template name="get-attr-tree-item-id">
        <xsl:with-param name="dom-id" select="@dom-id"/>
        <xsl:with-param name="obj-id" select="@obj-id"/>
        <xsl:with-param name="obj-inst-id" select="@obj-inst-id"/>
        <xsl:with-param name="attr-id" select="@attr-id"/>
      </xsl:call-template>
    </xsl:variable>
    <font color="black">STMT:	</font>
    <font color="blue">assign </font>
    <a href="{$tree_item_id}">
      <font color="purple"><xsl:value-of select="local-variable"/>.<xsl:value-of select="$attr_name"/></font>
    </a>
    <font color="blue"> = </font>
    <font color="red"><xsl:value-of select="oal-value"/></font>
    <font color="blue">;</font>
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

  <!-- Notify Modify Local Variable -->
  <xsl:template match="debug[@cmd='Notify Modify Local Variable']">
    <font color="black">STMT:	</font>
    <font color="blue">assign </font>
    <font color="purple"><xsl:value-of select="local-variable"/></font>
    <font color="blue"> = </font>
    <font color="red"><xsl:value-of select="oal-value"/></font>
    <font color="blue">;</font>
  </xsl:template>

  <!-- Relationship Creation -->
  <xsl:template match="debug[@cmd='Notify Relationship Creation']">
    <xsl:variable name="rel-num">
      <xsl:call-template name="get-rel-num">
        <xsl:with-param name="dom-id" select="@dom-id"/>
        <xsl:with-param name="rel-id" select="@rel-id"/>      
      </xsl:call-template>
    </xsl:variable>
    <xsl:variable name="from-tree-item-id">
      <xsl:call-template name="get-obj-tree-item-id">
        <xsl:with-param name="dom-id" select="@dom-id"/>
        <xsl:with-param name="obj-inst-id" select="@from-obj-inst-id"/>
      </xsl:call-template>
    </xsl:variable>
    <xsl:variable name="to-tree-item-id">
      <xsl:call-template name="get-obj-tree-item-id">
        <xsl:with-param name="dom-id" select="@dom-id"/>
        <xsl:with-param name="obj-inst-id" select="@to-obj-inst-id"/>
      </xsl:call-template>
    </xsl:variable>
    <font color="black">STMT:	</font>
    <font color="blue">relate </font>
    <a href="{$from-tree-item-id}">
    <font color="purple"><xsl:value-of select="from-obj-inst-variable"/></font>
    </a>
    <font color="blue"> to </font>
    <a href="{$to-tree-item-id}">
    <font color="purple"><xsl:value-of select="to-obj-inst-variable"/></font>
    </a>
    <font color="blue"> across </font>
    <font color="red">R<xsl:value-of select="$rel-num"/>
      <xsl:if test="txt-phrs!=''">.'<xsl:value-of select="txt-phrs"/>'</xsl:if>
    </font>
    <font color="blue">;</font>
  </xsl:template>

  <xsl:template match="debug[@cmd='Notify Associative Relationship Creation']">
    <xsl:variable name="rel-num">
      <xsl:call-template name="get-rel-num">
        <xsl:with-param name="dom-id" select="@dom-id"/>
        <xsl:with-param name="rel-id" select="@rel-id"/>      
      </xsl:call-template>
    </xsl:variable>
    <xsl:variable name="aone-tree-item-id">
      <xsl:call-template name="get-obj-tree-item-id">
        <xsl:with-param name="dom-id" select="@dom-id"/>
        <xsl:with-param name="obj-inst-id" select="@from-obj-inst-id"/>
      </xsl:call-template>
    </xsl:variable>
    <xsl:variable name="aoth-tree-item-id">
      <xsl:call-template name="get-obj-tree-item-id">
        <xsl:with-param name="dom-id" select="@dom-id"/>
        <xsl:with-param name="obj-inst-id" select="@to-obj-inst-id"/>
      </xsl:call-template>
    </xsl:variable>
    <xsl:variable name="assoc-tree-item-id">
      <xsl:call-template name="get-obj-tree-item-id">
        <xsl:with-param name="dom-id" select="@dom-id"/>
        <xsl:with-param name="obj-inst-id" select="@assoc-obj-inst-id"/>
      </xsl:call-template>
    </xsl:variable>
    <font color="black">STMT:	</font>
    <font color="blue">relate </font>
    <a href="{$aone-tree-item-id}">
    <font color="purple"><xsl:value-of select="from-obj-inst-variable"/></font>
    </a>
    <font color="blue"> to </font>
    <a href="{$aoth-tree-item-id}">
    <font color="purple"><xsl:value-of select="to-obj-inst-variable"/></font>
    </a>
    <font color="blue"> across </font>
    <font color="red">R<xsl:value-of select="$rel-num"/>
      <xsl:if test="txt-phrs!=''">.'<xsl:value-of select="txt-phrs"/>'</xsl:if>
    </font>
    <font color="blue"> using </font>
    <a href="{$assoc-tree-item-id}">
    <font color="purple"><xsl:value-of select="assoc-obj-inst-variable"/></font>
    </a>
    <font color="blue">;</font>
  </xsl:template>

  <!-- Relationship Deletion -->
  <xsl:template match="debug[@cmd='Notify Relationship Deletion']">
    <xsl:variable name="rel-num">
      <xsl:call-template name="get-rel-num">
        <xsl:with-param name="dom-id" select="@dom-id"/>
        <xsl:with-param name="rel-id" select="@rel-id"/>      
      </xsl:call-template>
    </xsl:variable>
    <xsl:variable name="from-tree-item-id">
      <xsl:call-template name="get-obj-tree-item-id">
        <xsl:with-param name="dom-id" select="@dom-id"/>
        <xsl:with-param name="obj-inst-id" select="@from-obj-inst-id"/>
      </xsl:call-template>
    </xsl:variable>
    <xsl:variable name="to-tree-item-id">
      <xsl:call-template name="get-obj-tree-item-id">
        <xsl:with-param name="dom-id" select="@dom-id"/>
        <xsl:with-param name="obj-inst-id" select="@to-obj-inst-id"/>
      </xsl:call-template>
    </xsl:variable>
    <font color="black">STMT:	</font>
    <font color="blue">unrelate </font>
    <a href="{$from-tree-item-id}">
    <font color="purple"><xsl:value-of select="from-obj-inst-variable"/></font>
    </a>
    <font color="blue"> from </font>
    <a href="{$to-tree-item-id}">
    <font color="purple"><xsl:value-of select="to-obj-inst-variable"/></font>
    </a>
    <font color="blue"> across </font>
    <font color="red">R<xsl:value-of select="$rel-num"/>
      <xsl:if test="txt-phrs!=''">.'<xsl:value-of select="txt-phrs"/>'</xsl:if>
    </font>
    <font color="blue">;</font>
  </xsl:template>

  <xsl:template match="debug[@cmd='Notify Associative Relationship Deletion']">
    <xsl:variable name="rel-num">
      <xsl:call-template name="get-rel-num">
        <xsl:with-param name="dom-id" select="@dom-id"/>
        <xsl:with-param name="rel-id" select="@rel-id"/>      
      </xsl:call-template>
    </xsl:variable>
    <xsl:variable name="aone-tree-item-id">
      <xsl:call-template name="get-obj-tree-item-id">
        <xsl:with-param name="dom-id" select="@dom-id"/>
        <xsl:with-param name="obj-inst-id" select="@from-obj-inst-id"/>
      </xsl:call-template>
    </xsl:variable>
    <xsl:variable name="aoth-tree-item-id">
      <xsl:call-template name="get-obj-tree-item-id">
        <xsl:with-param name="dom-id" select="@dom-id"/>
        <xsl:with-param name="obj-inst-id" select="@to-obj-inst-id"/>
      </xsl:call-template>
    </xsl:variable>
    <xsl:variable name="assoc-tree-item-id">
      <xsl:call-template name="get-obj-tree-item-id">
        <xsl:with-param name="dom-id" select="@dom-id"/>
        <xsl:with-param name="obj-inst-id" select="@assoc-obj-inst-id"/>
      </xsl:call-template>
    </xsl:variable>
    <font color="black">STMT:	</font>
    <font color="blue">unrelate </font>
    <a href="{$aone-tree-item-id}">
    <font color="purple"><xsl:value-of select="from-obj-inst-variable"/></font>
    </a>
    <font color="blue"> from </font>
    <a href="{$aoth-tree-item-id}">
    <font color="purple"><xsl:value-of select="to-obj-inst-variable"/></font>
    </a>
    <font color="blue"> across </font>
    <font color="red">R<xsl:value-of select="$rel-num"/>
      <xsl:if test="txt-phrs!=''">.'<xsl:value-of select="txt-phrs"/>'</xsl:if>
    </font>
    <font color="blue"> using </font>
    <a href="{$assoc-tree-item-id}">
    <font color="purple"><xsl:value-of select="assoc-obj-inst-variable"/></font>
    </a>
    <font color="blue">;</font>
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

  <!-- Function Invocation -->
  <xsl:template match="debug[@cmd='Notify Function Invocation']">
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
    <font color="black">STMT:	</font>
    <font color="purple"><xsl:value-of select="$dom-name"/>::<xsl:value-of select="$fn-name"/>(
    <xsl:apply-templates select="oal-value" mode="function"/>)</font>
    <font color="blue">;</font>
  </xsl:template>

  <!-- Function Entry -->
  <xsl:template match="debug[@cmd='Notify Function Entry']">
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
    <xsl:apply-templates select="value" mode="function"/>)
    <xsl:if test="@funcinv-id!='0'"> (id=<xsl:value-of select="@funcinv-id"/>)</xsl:if> Invocation Begin.</font>
  </xsl:template>

  <!-- Notify Function Exit -->
  <xsl:template match="debug[@cmd='Notify Function Exit']">
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
    <!-- 
        If oal-invoked is equal to '0', then that implies that is a 
        function (user-invoked or init) that we want to place a 
        horizontal line after
    -->
    <xsl:if test="@oal-invoked='0'"><hr/></xsl:if>
  </xsl:template>

  <!-- Generate Event -->
  <xsl:template match="debug[@cmd='Notify Generate Object Event Instance']">
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
    <font color="black">STMT:	</font>
    <font color="blue">generate </font>
    <font color="purple"><xsl:value-of select="$drv-lbl"/>:'<xsl:value-of select="$mning"/>'(
    <xsl:apply-templates select="oal-value" mode="event"/>)</font>
    <font color="blue"> to </font>
    <font color="red"><xsl:value-of select="local-variable"/></font>
    <font color="blue">;</font>
  </xsl:template>

  <xsl:template match="debug[@cmd='Notify Generate Class Event Instance'] | debug[@cmd='Notify Generate Creation Event Instance']">
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
    <xsl:variable name="key-lett">
      <xsl:if test="@cmd='Notify Generate Class Event Instance'">
        <xsl:call-template name="get-obj-key-lett-from-sm">
          <xsl:with-param name="dom-id" select="@dom-id"/>
          <xsl:with-param name="sm-id" select="@sm-id"/>
        </xsl:call-template>
      </xsl:if>
      <xsl:if test="@cmd='Notify Generate Creation Event Instance'">
        <xsl:call-template name="get-obj-key-lett">
          <xsl:with-param name="dom-id" select="@dom-id"/>
          <xsl:with-param name="obj-id" select="@obj-id"/>
        </xsl:call-template>
      </xsl:if>
    </xsl:variable>
    <font color="black">STMT:	</font>
    <font color="blue">generate </font>
    <font color="purple"><xsl:value-of select="$drv-lbl"/>:'<xsl:value-of select="$mning"/>'(  
    <xsl:apply-templates select="oal-value" mode="event"/>)</font>
      <font color="blue"> to </font>
      <font color="red"><xsl:value-of select="$key-lett"/></font>
    <xsl:if test="@cmd='Notify Generate Class Event Instance'">
      <font color="blue"> class</font>
    </xsl:if>
    <xsl:if test="@cmd='Notify Generate Creation Event Instance'">
      <font color="blue"> creator</font>
    </xsl:if>
    <font color="blue">;</font>
  </xsl:template>

  <xsl:template match="debug[@cmd='Notify Generate Event Variable']">
    <font color="black">STMT:	</font>
    <font color="blue">generate </font>
    <font color="purple"><xsl:value-of select="local-variable"/></font>
    <font color="blue">;</font>
  </xsl:template>

  <xsl:template match="debug[@cmd='Notify Generate Event Attribute']">
    <xsl:variable name="attr_name">
      <xsl:call-template name="get-attr-name">
        <xsl:with-param name="dom-id" select="@dom-id"/>
        <xsl:with-param name="obj-id" select="@obj-id"/>
        <xsl:with-param name="attr-id" select="@attr-id"/>
      </xsl:call-template>
    </xsl:variable>
    <xsl:variable name="tree_item_id">
      <xsl:call-template name="get-attr-tree-item-id">
        <xsl:with-param name="dom-id" select="@dom-id"/>
        <xsl:with-param name="obj-id" select="@obj-id"/>
        <xsl:with-param name="obj-inst-id" select="@obj-inst-id"/>
        <xsl:with-param name="attr-id" select="@attr-id"/>
      </xsl:call-template>
    </xsl:variable>
    <font color="black">STMT:	</font>
    <font color="blue">generate </font>
    <a href="{$tree_item_id}">
      <font color="purple"><xsl:value-of select="local-variable"/>.<xsl:value-of select="$attr_name"/></font>
    </a>
    <font color="blue">;</font>
  </xsl:template>

  <!-- Event Creation -->
  <xsl:template match="debug[@cmd='Notify Create Object Event Instance']">
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
    <xsl:variable name="tree_item_id">
      <xsl:call-template name="get-obj-tree-item-id">
        <xsl:with-param name="dom-id" select="@dom-id"/>
        <xsl:with-param name="obj-id" select="@obj-id"/>
        <xsl:with-param name="obj-inst-id" select="@obj-inst-id"/>
      </xsl:call-template>
    </xsl:variable>
    <font color="black">STMT:	</font>
    <font color="blue">create event instance </font>
    <font color="purple"><xsl:value-of select="local-variable"/> </font>
    <font color="blue">of </font>
    <font color="purple"><xsl:value-of select="$drv-lbl"/>:'<xsl:value-of select="$mning"/>'(
    <xsl:apply-templates select="oal-value" mode="event"/>)</font>
    <font color="blue"> to </font>
    <a href="{$tree_item_id}">
      <font color="red"><xsl:value-of select="to-obj-inst-variable"/></font>
    </a>
    <font color="blue">;</font>
  </xsl:template>

  <xsl:template match="debug[@cmd='Notify Create Class Event Instance'] | debug[@cmd='Notify Create Creation Event Instance'] ">
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
    <xsl:variable name="key-lett">
      <xsl:if test="@cmd='Notify Create Class Event Instance'">
        <xsl:call-template name="get-obj-key-lett-from-sm">
          <xsl:with-param name="dom-id" select="@dom-id"/>
          <xsl:with-param name="sm-id" select="@sm-id"/>
        </xsl:call-template>
      </xsl:if>
      <xsl:if test="@cmd='Notify Create Creation Event Instance'">
        <xsl:call-template name="get-obj-key-lett">
          <xsl:with-param name="dom-id" select="@dom-id"/>
          <xsl:with-param name="obj-id" select="@obj-id"/>
        </xsl:call-template>
      </xsl:if>
    </xsl:variable>
    <font color="black">STMT:	</font>
    <font color="blue">create event instance </font>
    <font color="purple"><xsl:value-of select="local-variable"/> </font>
    <font color="blue">of </font>
    <font color="purple"><xsl:value-of select="$drv-lbl"/>:'<xsl:value-of select="$mning"/>'(
    <xsl:apply-templates select="oal-value" mode="event"/>)</font>
    <font color="blue"> to </font>
    <font color="red"><xsl:value-of select="$key-lett"/></font>
    <xsl:if test="@cmd='Notify Create Class Event Instance'">
      <font color="blue"> class</font>
    </xsl:if>
    <xsl:if test="@cmd='Notify Create Creation Event Instance'">
      <font color="blue"> creator</font>
    </xsl:if>
    <font color="blue">;</font>
  </xsl:template>

  <!-- Event Delay -->
  <xsl:template match="debug[@cmd='Notify Create Delayed Event']">
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
    <xsl:variable name="tree_item_id">
      <xsl:call-template name="get-obj-tree-item-id">
        <xsl:with-param name="dom-id" select="@dom-id"/>
        <xsl:with-param name="obj-id" select="@obj-id"/>
        <xsl:with-param name="obj-inst-id" select="@obj-inst-id"/>
      </xsl:call-template>
    </xsl:variable>
    <font color="black">STMT:	</font>
    <font color="blue">delay event instance </font>
    <font color="purple"><xsl:value-of select="local-variable"/> </font>
    <font color="blue">of </font>
    <font color="purple"><xsl:value-of select="$drv-lbl"/>:'<xsl:value-of select="$mning"/>'(
    <xsl:apply-templates select="oal-value" mode="event"/>)</font>
    <font color="blue">by </font>
    <font color="purple"><xsl:value-of select="@delay"/> </font>
    <xsl:if test="@recurring='1'">
      <font color="blue">recurring </font>
    </xsl:if>
    <font color="blue">;</font>
  </xsl:template>

  <xsl:template match="debug[@cmd='Notify Change Expiry of Delayed Event']">
   <!-- only process if this is not a message from the target after 
        it's un-blocked (context==0 and stmt_id == 0 ) -->
   <xsl:if test="@context!='0' or @stmt_id!='0'">
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
    <xsl:variable name="tree_item_id">
      <xsl:call-template name="get-obj-tree-item-id">
        <xsl:with-param name="dom-id" select="@dom-id"/>
        <xsl:with-param name="obj-id" select="@obj-id"/>
        <xsl:with-param name="obj-inst-id" select="@obj-inst-id"/>
      </xsl:call-template>
    </xsl:variable>
    <font color="black">STMT:	</font>
    <font color="blue">change delay on event instance </font>
    <xsl:if test="local-variable!=''">
      <font color="purple"><xsl:value-of select="local-variable"/> </font>
      <font color="blue">of </font>
    </xsl:if>
    <font color="purple"><xsl:value-of select="$drv-lbl"/>:'<xsl:value-of select="$mning"/>'(
    <xsl:apply-templates select="oal-value" mode="event"/>)</font>
    <xsl:if test="@add='1'">
      <font color="blue"> adding </font>
    </xsl:if>
    <xsl:if test="@add='0'">
      <font color="blue"> to </font>
    </xsl:if>
    <font color="purple"><xsl:value-of select="@delay"/></font>
    <font color="blue">;</font>
    <br/>
   </xsl:if>
  </xsl:template>

  <!-- Modify Event Data Item -->
  <xsl:template match="debug[@cmd='Notify Modify Event Data Item']">
    <!-- Cant Happen at Release 1.0 -->
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
    <xsl:variable name="name">
      <xsl:call-template name="get-evt-di-name">
        <xsl:with-param name="dom-id" select="@dom-id"/>
        <xsl:with-param name="smedi-id" select="@smedi-id"/>
      </xsl:call-template>
    </xsl:variable>
    <font color="black">USER: </font>
    <font color="blue">Modified Event Data Item </font>
    <font color = "purple"><xsl:value-of select="$drv-lbl"/>:<xsl:value-of select="$mning"/></font>
    <font color = "purple">.<xsl:value-of select="$name"/> = <xsl:value-of select="oal-value"/></font>
    <font color="blue">;</font>
  </xsl:template>

  <!-- Event Deletion -->
  <xsl:template match="debug[@cmd='Notify Delete Event Instance']">
    <font color="black">STMT:	</font>
    <font color="blue">delete event instance </font>
    <font color = "purple"><xsl:value-of select="local-variable"/></font>
    <font color="blue">;</font>
  </xsl:template>

  <xsl:template match="debug[@cmd='Notify Delete Delayed Event']">
    <font color="black">STMT:	</font>
    <font color="blue">delete delayed event instance </font>
    <font color = "purple"><xsl:value-of select="local-variable"/></font>
    <font color="blue">;</font>
  </xsl:template>

  <xsl:template match="debug[@cmd='Notify Delayed Event Ready']">
    <!-- No output required -->
  </xsl:template>

  <!-- Bridge Operation -->
  <xsl:template match="debug[@cmd='Notify Bridge Operation Invocation']">
    <xsl:variable name="brg-name">
      <xsl:call-template name="get-brg-name">
        <xsl:with-param name="dom-id" select="@dom-id"/>
        <xsl:with-param name="brg-id" select="@brg-id"/>
      </xsl:call-template>
    </xsl:variable>
    <xsl:variable name="ee-key-lett">
      <xsl:call-template name="get-ee-key-lett">
        <xsl:with-param name="dom-id" select="@dom-id"/>
        <xsl:with-param name="ee-id" select="@ee-id"/>
      </xsl:call-template>
    </xsl:variable>
    <font color="black">STMT:	</font>
    <font color="purple"><xsl:value-of select="$ee-key-lett"/>::<xsl:value-of select="$brg-name"/>(
    <xsl:apply-templates select="oal-value" mode="bridge"/>)</font>
    <font color="blue">;</font>
  </xsl:template>

  <!-- Bridge Entry -->
  <xsl:template match="debug[@cmd='Notify Bridge Operation Entry']">
    <xsl:variable name="brg-name">
      <xsl:call-template name="get-brg-name">
        <xsl:with-param name="dom-id" select="@dom-id"/>
        <xsl:with-param name="brg-id" select="@brg-id"/>
      </xsl:call-template>
    </xsl:variable>
    <xsl:variable name="ee-key-lett">
      <xsl:call-template name="get-ee-key-lett">
        <xsl:with-param name="dom-id" select="@dom-id"/>
        <xsl:with-param name="ee-id" select="@ee-id"/>
      </xsl:call-template>
    </xsl:variable>
    <font color="black">INFO:	</font>
    <font color="purple"><xsl:value-of select="$ee-key-lett"/>::<xsl:value-of select="$brg-name"/>(
    <xsl:apply-templates select="value" mode="bridge"/>) Invocation Begin.</font>
  </xsl:template>

  <!-- Bridge Exit -->
  <xsl:template match="debug[@cmd='Notify Bridge Operation Exit']">
    <xsl:variable name="brg-name">
      <xsl:call-template name="get-brg-name">
        <xsl:with-param name="dom-id" select="@dom-id"/>
        <xsl:with-param name="brg-id" select="@brg-id"/>
      </xsl:call-template>
    </xsl:variable>
    <xsl:variable name="ee-key-lett">
      <xsl:call-template name="get-ee-key-lett">
        <xsl:with-param name="dom-id" select="@dom-id"/>
        <xsl:with-param name="ee-id" select="@ee-id"/>
      </xsl:call-template>
    </xsl:variable>
    <font color="black">INFO:	</font>
    <font color="purple"><xsl:value-of select="$ee-key-lett"/>::<xsl:value-of select="$brg-name"/> Invocation End.</font>
  </xsl:template>

  <!-- Parameter Modification -->
  <xsl:template match="debug[@cmd='Notify Modify Function Parameter']">
    <xsl:variable name="dom_id" select="@dom-id"/>
    <xsl:variable name="obj_id" select="@obj-id"/>
    <xsl:variable name="obj_inst_id" select="@obj-inst-id"/>
    <xsl:variable name="attr_id" select="@attr-id"/>
    <xsl:variable name="parm-name">
      <xsl:call-template name="get-fn-parm-name">
        <xsl:with-param name="dom-id" select="@dom-id"/>
        <xsl:with-param name="sparm-id" select="@sparm-id"/>
      </xsl:call-template>
    </xsl:variable>
    <font color="black">STMT:	</font>
    <font color="blue">assign </font>
    <font color="purple">param.<xsl:value-of select="$parm-name"/></font>
    <font color="blue"> = </font>
    <font color="red"><xsl:value-of select="oal-value"/></font>
    <font color="blue">;</font>
  </xsl:template>
  
  <xsl:template match="debug[@cmd='Notify Modify Bridge Operation Parameter']">
    <xsl:variable name="dom_id" select="@dom-id"/>
    <xsl:variable name="obj_id" select="@obj-id"/>
    <xsl:variable name="obj_inst_id" select="@obj-inst-id"/>
    <xsl:variable name="attr_id" select="@attr-id"/>
    <xsl:variable name="parm-name">
      <xsl:call-template name="get-brg-parm-name">
        <xsl:with-param name="dom-id" select="@dom-id"/>
        <xsl:with-param name="brg-id" select="@brg-id"/>
        <xsl:with-param name="bparm-id" select="@bparm-id"/>
      </xsl:call-template>
    </xsl:variable>
    <font color="black">STMT:	</font>
    <font color="blue">assign </font>
    <font color="purple">param.<xsl:value-of select="$parm-name"/></font>
    <font color="blue"> = </font>
    <font color="red"><xsl:value-of select="oal-value"/></font>
    <font color="blue">;</font>
  </xsl:template>

  <!-- Operation -->
  <xsl:template match="debug[@cmd='Notify Instance Operation Invocation']">
    <xsl:variable name="op-name">
      <xsl:call-template name="get-op-name">
        <xsl:with-param name="dom-id" select="@dom-id"/>
        <xsl:with-param name="op-id" select="@tfr-id"/>
      </xsl:call-template>
    </xsl:variable>
    <font color="black">STMT:	</font>
    <font color="purple"><xsl:value-of select="local-variable"/>.<xsl:value-of select="$op-name"/>(
    <xsl:apply-templates select="oal-value" mode="operation"/>)</font>
    <font color="blue">;</font>
  </xsl:template>

  <!-- Operation Entry -->
  <xsl:template match="debug[@cmd='Notify Instance Operation Entry']">
    <xsl:variable name="op-name">
      <xsl:call-template name="get-op-name">
        <xsl:with-param name="dom-id" select="@dom-id"/>
        <xsl:with-param name="op-id" select="@tfr-id"/>
      </xsl:call-template>
    </xsl:variable>
    <font color="black">INFO:	</font>
    <font color="purple">self.<xsl:value-of select="$op-name"/>(
    <xsl:apply-templates select="value" mode="operation"/>) Invocation Begin.</font>
  </xsl:template>

  <xsl:template match="debug[@cmd='Notify Class Operation Invocation']">
    <xsl:variable name="op-name">
      <xsl:call-template name="get-op-name">
        <xsl:with-param name="dom-id" select="@dom-id"/>
        <xsl:with-param name="op-id" select="@tfr-id"/>
      </xsl:call-template>
    </xsl:variable>
    <xsl:variable name="class-key-lett">
      <xsl:call-template name="get-obj-key-lett">
        <xsl:with-param name="dom-id" select="@dom-id"/>
        <xsl:with-param name="obj-id" select="@obj-id"/>
      </xsl:call-template>
    </xsl:variable>
    <font color="black">STMT:	</font>
    <font color="purple"><xsl:value-of select="$class-key-lett"/>::<xsl:value-of select="$op-name"/>(
    <xsl:apply-templates select="oal-value" mode="operation"/>)</font>
    <font color="blue">;</font>
  </xsl:template>

  <xsl:template match="debug[@cmd='Notify Class Operation Entry']">
    <xsl:variable name="op-name">
      <xsl:call-template name="get-op-name">
        <xsl:with-param name="dom-id" select="@dom-id"/>
        <xsl:with-param name="op-id" select="@tfr-id"/>
      </xsl:call-template>
    </xsl:variable>
    <xsl:variable name="class-key-lett">
      <xsl:call-template name="get-obj-key-lett">
        <xsl:with-param name="dom-id" select="@dom-id"/>
        <xsl:with-param name="obj-id" select="@obj-id"/>
      </xsl:call-template>
    </xsl:variable>
    <font color="black">INFO:	</font>
    <font color="purple"><xsl:value-of select="$class-key-lett"/>::<xsl:value-of select="$op-name"/>(
    <xsl:apply-templates select="oal-value" mode="operation"/>) Invocation Begin.</font>
  </xsl:template>

  <xsl:template match="debug[@cmd='Notify Modify Operation Parameter']">
    <xsl:variable name="dom_id" select="@dom-id"/>
    <xsl:variable name="obj_id" select="@obj-id"/>
    <xsl:variable name="obj_inst_id" select="@obj-inst-id"/>
    <xsl:variable name="attr_id" select="@attr-id"/>
    <xsl:variable name="parm-name">
      <xsl:call-template name="get-op-parm-name">
        <xsl:with-param name="dom-id" select="@dom-id"/>
        <xsl:with-param name="op-id" select="@tfr-id"/>
        <xsl:with-param name="parm-id" select="@tparm-id"/>
      </xsl:call-template>
    </xsl:variable>
    <font color="black">STMT:	</font>
    <font color="blue">assign </font>
    <font color="purple">param.<xsl:value-of select="$parm-name"/></font>
    <font color="blue"> = </font>
    <font color="red"><xsl:value-of select="oal-value"/></font>
    <font color="blue">;</font>
  </xsl:template>
  
  <xsl:template match="debug[@cmd='Notify Operation Exit']">
    <xsl:variable name="op-name">
      <xsl:call-template name="get-op-name">
        <xsl:with-param name="dom-id" select="@dom-id"/>
        <xsl:with-param name="op-id" select="@tfr-id"/>
      </xsl:call-template>
    </xsl:variable>
    <xsl:variable name="inst-based">
      <xsl:call-template name="get-op-inst-based">
        <xsl:with-param name="dom-id" select="@dom-id"/>
        <xsl:with-param name="op-id" select="@tfr-id"/>
      </xsl:call-template>
    </xsl:variable>
    <font color="black">INFO:	</font>
    <xsl:if test="$inst-based='1'">
    <font color="purple">self.<xsl:value-of select="$op-name"/> Invocation End.</font>
    </xsl:if>
    <xsl:if test="$inst-based='0'">
      <xsl:variable name="class-key-lett">
        <xsl:call-template name="get-obj-key-lett">
          <xsl:with-param name="dom-id" select="@dom-id"/>
          <xsl:with-param name="obj-id" select="@obj-id"/>
        </xsl:call-template>
      </xsl:variable>
      <font color="purple"><xsl:value-of select="$class-key-lett"/>::<xsl:value-of select="$op-name"/> Invocation End.</font>
    </xsl:if>
  </xsl:template>

  <!-- Generate External Entity Event -->
  <xsl:template match="debug[@cmd='Notify Generate External Entity Event']">
    <xsl:variable name="drv-lbl">
      <xsl:call-template name="get-ext-evt-drv-lbl">
        <xsl:with-param name="dom-id" select="@dom-id"/>
        <xsl:with-param name="eeevt-id" select="@eeevt-id"/>
      </xsl:call-template>
    </xsl:variable>
    <xsl:variable name="mning">
      <xsl:call-template name="get-ext-evt-mning">
        <xsl:with-param name="dom-id" select="@dom-id"/>
        <xsl:with-param name="eeevt-id" select="@eeevt-id"/>
      </xsl:call-template>
    </xsl:variable>
    <font color="black">STMT:	</font>
    <font color="blue">generate </font>
    <font color="purple"><xsl:value-of select="$drv-lbl"/>:'<xsl:value-of select="$mning"/>'(
    <xsl:apply-templates select="oal-value" mode="ee-event"/>)</font>
    <font color="blue"> to external entity</font>
    <font color="blue">;</font>
  </xsl:template>

  <!-- Create External Entity Event -->
  <xsl:template match="debug[@cmd='Notify Create External Entity Event']">
    <xsl:variable name="drv-lbl">
      <xsl:call-template name="get-ext-evt-drv-lbl">
        <xsl:with-param name="dom-id" select="@dom-id"/>
        <xsl:with-param name="eeevt-id" select="@eeevt-id"/>
      </xsl:call-template>
    </xsl:variable>
    <xsl:variable name="mning">
      <xsl:call-template name="get-ext-evt-mning">
        <xsl:with-param name="dom-id" select="@dom-id"/>
        <xsl:with-param name="eeevt-id" select="@eeevt-id"/>
      </xsl:call-template>
    </xsl:variable>
    <font color="black">STMT:	</font>
    <font color="blue">create external entity event instance </font>
    <font color="purple"><xsl:value-of select="local-variable"/> </font>
    <font color="blue">of </font>
    <font color="purple"><xsl:value-of select="$drv-lbl"/>:'<xsl:value-of select="$mning"/>'(
    <xsl:apply-templates select="oal-value" mode="ee-event"/>)</font>
    <font color="blue">;</font>
  </xsl:template>

  <!-- Select from instances of -->
  <xsl:template match="debug[@cmd='Log Select from execution'] | debug[@cmd='Log Select from where execution']">
    <font color="black">STMT:	</font>
    <font color="blue">select <xsl:value-of select="cardinality"/> </font>
    <font color="purple"><xsl:value-of select="local-variable"/> </font>
    <font color="blue">from instances of </font>
    <font color="purple"><xsl:value-of select="key-lett"/></font>
    <xsl:if test="where-clause">
      <font color="blue">where </font>
      <font color="purple"><xsl:value-of select="where-clause"/></font>
    </xsl:if>
    <font color="blue">;</font> 
  </xsl:template>

  <!-- Select related by -->
  <xsl:template match="debug[@cmd='Log Select related by execution'] | debug[@cmd='Log Select related by where execution']">
    <font color="black">STMT:	</font>
    <font color="blue">select <xsl:value-of select="cardinality"/> </font>
    <font color="purple"><xsl:value-of select="local-variable"/> </font>
    <font color="blue">related by </font>
    <font color="purple"><xsl:value-of select="chain"/></font>
    <xsl:if test="where-clause">
      <font color="blue">where </font>
      <font color="purple"><xsl:value-of select="where-clause"/></font>
    </xsl:if>
    <font color="blue">;</font> 
  </xsl:template>

  <!-- Log If execution -->
  <xsl:template match="debug[@cmd='Log If execution']">
    <font color="black">STMT:	</font>
    <font color="blue">if </font>
    <font color="purple"><xsl:value-of select="condition"/> </font>
  </xsl:template>

  <!-- Log Elif execution -->
  <xsl:template match="debug[@cmd='Log Elif execution']">
    <font color="black">STMT:	</font>
    <font color="blue">else if </font>
    <font color="purple"><xsl:value-of select="condition"/> </font>
  </xsl:template>

  <!-- Log Else execution -->
  <xsl:template match="debug[@cmd='Log Else execution']">
    <font color="black">STMT:	</font>
    <font color="blue">else </font>
  </xsl:template>

  <!-- Log Endif execution -->
  <xsl:template match="debug[@cmd='Log Endif execution']">
    <font color="black">STMT:	</font>
    <font color="blue">end if </font>
    <font color="blue">;</font>
  </xsl:template>

  <!-- Log While execution -->
  <xsl:template match="debug[@cmd='Log While execution']">
    <font color="black">STMT:	</font>
    <font color="blue">while </font>
    <font color="purple"><xsl:value-of select="condition"/> </font>
  </xsl:template>

  <!-- Log Endwhile execution -->
  <xsl:template match="debug[@cmd='Log Endwhile execution']">
    <font color="black">STMT:	</font>
    <font color="blue">end while </font>
    <font color="blue">;</font>
  </xsl:template>

  <!-- Log For execution -->
  <xsl:template match="debug[@cmd='Log For execution']">
    <font color="black">STMT:	</font>
    <font color="blue">for each </font>
    <font color="purple"><xsl:value-of select="loop-variable-name"/> </font>
    <font color="blue">in </font>
    <font color="purple"><xsl:value-of select="set-variable-name"/></font>
  </xsl:template>

  <!-- Log Endfor execution -->
  <xsl:template match="debug[@cmd='Log Endfor execution']">
    <font color="black">STMT:	</font>
    <font color="blue">end for </font>
    <font color="blue">;</font>
  </xsl:template>

  <!-- Log Break execution -->
  <xsl:template match="debug[@cmd='Log Break execution']">
    <font color="black">STMT:	</font>
    <font color="blue">break </font>
    <font color="blue"><xsl:value-of select="type"/></font>
    <font color="blue">;</font>
  </xsl:template>

  <!-- Log Continue execution -->
  <xsl:template match="debug[@cmd='Log Continue execution']">
    <font color="black">STMT:	</font>
    <font color="blue">continue </font>
    <font color="blue">;</font>
  </xsl:template>

  <!-- Log Return execution -->
  <xsl:template match="debug[@cmd='Log Return execution']">
    <font color="black">STMT:	</font>
    <font color="blue">return <xsl:value-of select="return-value"/></font>
    <font color="blue">;</font>
  </xsl:template>

  <!-- Log Control execution -->
  <xsl:template match="debug[@cmd='Log Control execution']">
    <font color="black">STMT:	</font>
    <font color="blue">control </font>
    <font color="blue"><xsl:value-of select="command"/></font>
    <font color="blue">;</font>
  </xsl:template>

  <!-- Log Event Ignored -->
  <xsl:template match="debug[@cmd='Log Event Ignored']">
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
    <font color="red"><xsl:value-of select="$drv-lbl"/> : '<xsl:value-of select="$mning"/>' Resulted in EVENT IGNORED</font>
    <br/>
    <font color="black">DEST OBJ INST: <xsl:value-of select="$dest-ident"/></font><br/>
    <font color="black">CURRENT STATE: </font>
    '<font color="red"><xsl:value-of select="$state-name"/></font>'
    <hr/>
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

  <!-- Derived Attribute Calculation Begin -->
  <xsl:template match="debug[@cmd='Notify MDA Invocation Begin']">
    <xsl:variable name="obj_key_lett">
      <xsl:call-template name="get-obj-key-lett">
        <xsl:with-param name="dom-id" select="@dom-id"/>
        <xsl:with-param name="obj-id" select="@obj-id"/>
      </xsl:call-template>
    </xsl:variable>
    <xsl:variable name="attr_name">
      <xsl:call-template name="get-attr-name">
        <xsl:with-param name="dom-id" select="@dom-id"/>
        <xsl:with-param name="obj-id" select="@obj-id"/>
        <xsl:with-param name="attr-id" select="@attr-id"/>
      </xsl:call-template>
    </xsl:variable>
    <font color="black">INFO:	</font>
    <font color="purple">Derived Attribute '<xsl:value-of select="$obj_key_lett"/>.<xsl:value-of select="$attr_name"/>' Invocation Begin</font>
  </xsl:template>
  
  <!-- Derived Attribute Calculation End -->
  <xsl:template match="debug[@cmd='Notify MDA Invocation End']">
    <xsl:variable name="obj_key_lett">
      <xsl:call-template name="get-obj-key-lett">
        <xsl:with-param name="dom-id" select="@dom-id"/>
        <xsl:with-param name="obj-id" select="@obj-id"/>
      </xsl:call-template>
    </xsl:variable>
    <xsl:variable name="attr_name">
      <xsl:call-template name="get-attr-name">
        <xsl:with-param name="dom-id" select="@dom-id"/>
        <xsl:with-param name="obj-id" select="@obj-id"/>
        <xsl:with-param name="attr-id" select="@attr-id"/>
      </xsl:call-template>
    </xsl:variable>
    <font color="black">INFO:	</font>
    <font color="purple">Derived Attribute '<xsl:value-of select="$obj_key_lett"/>.<xsl:value-of select="$attr_name"/>' Invocation End</font>
  </xsl:template>
  
  <!-- FBO Entry -->
  <xsl:template match="debug[@cmd='Notify FBO Entry']">
    <xsl:variable name="class-key-lett">
      <xsl:call-template name="get-obj-key-lett">
        <xsl:with-param name="dom-id" select="@dom-id"/>
        <xsl:with-param name="obj-id" select="@obj-id"/>
      </xsl:call-template>
    </xsl:variable>
    <xsl:variable name="state-name">
      <xsl:call-template name="get-state-name">
        <xsl:with-param name="dom-id" select="@dom-id"/>
        <xsl:with-param name="smstt-id" select="@smstt-id"/>
        <xsl:with-param name="sm-id" select="@sm-id"/>        
      </xsl:call-template>
    </xsl:variable>
    <font color="black">INFO:	</font>
    <font color="purple">FBO <xsl:value-of select="$class-key-lett"/>::<xsl:value-of select="$state-name"/>(
    <xsl:apply-templates select="value" mode="event"/>) Invocation Begin.</font>
  </xsl:template>

  <!-- FBO Exit -->
  <xsl:template match="debug[@cmd='Notify FBO Exit']">
    <xsl:variable name="class-key-lett">
      <xsl:call-template name="get-obj-key-lett">
        <xsl:with-param name="dom-id" select="@dom-id"/>
        <xsl:with-param name="obj-id" select="@obj-id"/>
      </xsl:call-template>
    </xsl:variable>
    <xsl:variable name="state-name">
      <xsl:call-template name="get-state-name">
        <xsl:with-param name="dom-id" select="@dom-id"/>
        <xsl:with-param name="smstt-id" select="@smstt-id"/>
        <xsl:with-param name="sm-id" select="@sm-id"/>        
      </xsl:call-template>
    </xsl:variable>
    <font color="black">INFO:	</font>
    <font color="purple">FBO <xsl:value-of select="$class-key-lett"/>::<xsl:value-of select="$state-name"/> Invocation End.</font>
  </xsl:template>

</xsl:stylesheet>
