<?xml version="1.0" encoding="UTF-8"?>

<!-- ===================================================================

File:      script_utils.xsl, 1.12
Modified:  01/23/03, 16:03:14

Copyright (c) 2002-2013 Mentor Graphics CorporationMentor Graphics CorporationMentor Graphics CorporationMentor Graphics CorporationMentor Graphics CorporationMentor Graphics Corporation  All rights reserved.

========================================================================
This document contains information proprietary and confidential to
Project Technology, Inc. and is not for external distribution.
========================================================================

               Debugger Trace Stylesheet
   
Output:        Text

Dependencies:  None

Description:   This script provides utility functions for the scripts
               that process protocol data into Object Action Language.
      
==================================================================== -->

<!DOCTYPE xsl:stylesheet [
   <!ENTITY nbsp "&#160;">
   <!ENTITY xtuml "<sup>X</sup><sub>T</sub>UML">
   <!ENTITY reg "(R)">
]>

<xsl:stylesheet version="1.0" 
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

  <xsl:template name="get-dom-name">
    <xsl:param name="dom-id"/>
    <xsl:value-of select="/system/content/domain[@dom-id=$dom-id]/@name"/>
  </xsl:template>

  <xsl:template name="get-obj-key-lett">
    <xsl:param name="dom-id"/>
    <xsl:param name="obj-id"/>
    <xsl:value-of select="/system/content/domain[@dom-id=$dom-id]/subsystem/class[@obj-id=$obj-id]/@key-lett"/>
  </xsl:template>

  <xsl:template name="get-obj-key-lett-from-sm">
    <xsl:param name="dom-id"/>
    <xsl:param name="sm-id"/>
    <xsl:value-of select="/system/content/domain[@dom-id=$dom-id]/subsystem/class/instance-statechart[@sm-id=$sm-id]/../@key-lett"/>
    <xsl:value-of select="/system/content/domain[@dom-id=$dom-id]/subsystem/class/class-statechart[@sm-id=$sm-id]/../@key-lett"/>
  </xsl:template>

  <xsl:template name="get-obj-name">
    <xsl:param name="dom-id"/>
    <xsl:param name="obj-id"/>
    <xsl:value-of select="/system/content/domain[@dom-id=$dom-id]/subsystem/class[@obj-id=$obj-id]/@name"/>
  </xsl:template>

  <xsl:template name="get-obj-tree-item-id">
    <xsl:param name="dom-id"/>
    <xsl:param name="obj-inst-id"/>
    <xsl:value-of select="/system/content/domain[@dom-id=$dom-id]/subsystem/class/instances/object[@obj-inst-id=$obj-inst-id]/@tree-item-id"/>
    <xsl:value-of select="/system/content/domain[@dom-id=$dom-id]/subsystem/class/class-statechart/class-state-machine[@obj-id=$obj-inst-id]/@tree-item-id"/>
  </xsl:template>

  <xsl:template name="get-attr-name">
    <xsl:param name="dom-id"/>
    <xsl:param name="obj-id"/>
    <xsl:param name="attr-id"/>
    <xsl:value-of  select="/system/content/domain[@dom-id=$dom-id]/subsystem/class[@obj-id=$obj-id]/attribute[@attr-id=$attr-id]/@name"/>
  </xsl:template>

  <xsl:template name="get-attr-tree-item-id">
    <xsl:param name="dom-id"/>
    <xsl:param name="obj-inst-id"/>
    <xsl:param name="attr-id"/>
    <xsl:value-of  select="/system/content/domain[@dom-id=$dom-id]/subsystem/class/instances/object[@obj-inst-id=$obj-inst-id]/attribute-value[@attr-id=$attr-id]/@tree-item-id"/>
  </xsl:template>

  <xsl:template name="get-state-name">
    <xsl:param name="dom-id"/>
    <xsl:param name="smstt-id"/>
    <xsl:param name="sm-id"/>
    <xsl:value-of select="/system/content/domain[@dom-id=$dom-id]/subsystem/class/instance-statechart[@sm-id=$sm-id]/state-model-state[@smstt-id=$smstt-id]/@name"/>
    <xsl:value-of select="/system/content/domain[@dom-id=$dom-id]/subsystem/class/class-statechart[@sm-id=$sm-id]/state-model-state[@smstt-id=$smstt-id]/@name"/>
  </xsl:template>

  <xsl:template name="get-rel-num">
    <xsl:param name="dom-id"/>
    <xsl:param name="rel-id"/>
    <xsl:value-of select="/system/content/domain[@dom-id=$dom-id]/subsystem/association[@rel-id=$rel-id]/@numb"/>
  </xsl:template>

  <xsl:template name="get-evt-drv-lbl">
    <xsl:param name="dom-id"/>
    <xsl:param name="smevt-id"/>
    <xsl:value-of select="/system/content/domain[@dom-id=$dom-id]/subsystem/class/instance-statechart/state-model-event[@smevt-id=$smevt-id]/@drv-lbl"/>
    <xsl:value-of select="/system/content/domain[@dom-id=$dom-id]/subsystem/class/class-statechart/state-model-event[@smevt-id=$smevt-id]/@drv-lbl"/>
  </xsl:template>

  <xsl:template name="get-evt-mning">
    <xsl:param name="dom-id"/>
    <xsl:param name="smevt-id"/>
    <xsl:value-of select="/system/content/domain[@dom-id=$dom-id]/subsystem/class/instance-statechart/state-model-event[@smevt-id=$smevt-id]/@mning"/>
    <xsl:value-of select="/system/content/domain[@dom-id=$dom-id]/subsystem/class/class-statechart/state-model-event[@smevt-id=$smevt-id]/@mning"/>
  </xsl:template>

  <xsl:template name="get-evt-di-name">
    <xsl:param name="dom-id"/>
    <xsl:param name="smedi-id"/>
    <xsl:value-of select="/system/content/domain[@dom-id=$dom-id]/subsystem/class/instance-statechart/state-model-event/state-model-event-data-item[@smedi-id=$smedi-id]/@name"/>
    <xsl:value-of select="/system/content/domain[@dom-id=$dom-id]/subsystem/class/class-statechart/state-model-event/state-model-event-data-item[@smedi-id=$smedi-id]/@name"/>
  </xsl:template>

  <xsl:template name="get-ext-evt-drv-lbl">
    <xsl:param name="dom-id"/>
    <xsl:param name="eeevt-id"/>
    <xsl:value-of select="/system/content/domain[@dom-id=$dom-id]/external-entity/external-entity-event[@eeevt-id=$eeevt-id]/@drv-lbl"/>
  </xsl:template>

  <xsl:template name="get-ext-evt-mning">
    <xsl:param name="dom-id"/>
    <xsl:param name="eeevt-id"/>
    <xsl:value-of select="/system/content/domain[@dom-id=$dom-id]/external-entity/external-entity-event[@eeevt-id=$eeevt-id]/@mning"/>
  </xsl:template>

  <xsl:template name="get-ext-evt-di-name">
    <xsl:param name="dom-id"/>
    <xsl:param name="eeedi-id"/>
    <xsl:value-of select="/system/content/domain[@dom-id=$dom-id]/external-entity/external-entity-event/external-entity-event-data[@eeedi-id=$eeedi-id]/@name"/>
  </xsl:template>

  <xsl:template name="get-fn-name">
    <xsl:param name="dom-id"/>
    <xsl:param name="fn-id"/>
    <xsl:value-of select="/system/content/domain[@dom-id=$dom-id]/function[@sync-id=$fn-id]/@name"/>
  </xsl:template>

  <xsl:template name="get-fn-parm-name">
    <xsl:param name="dom-id"/>
    <xsl:param name="sync-id"/>
    <xsl:param name="sparm-id"/>
    <xsl:value-of select="/system/content/domain[@dom-id=$dom-id]/function/function-parameter[@sparm-id=$sparm-id]/@name"/>
  </xsl:template>

  <xsl:template name="get-brg-parm-name">
    <xsl:param name="dom-id"/>
    <xsl:param name="brg-id"/>
    <xsl:param name="bparm-id"/>
    <xsl:value-of select="/system/content/domain[@dom-id=$dom-id]/external-entity/bridge/bridge-parameter[@bparm-id=$bparm-id]/@name"/>
  </xsl:template>

  <xsl:template name="get-op-parm-name">
    <xsl:param name="dom-id"/>
    <xsl:param name="op-id"/>
    <xsl:param name="parm-id"/>
    <xsl:value-of select="/system/content/domain[@dom-id=$dom-id]/subsystem/class/operation/operation-parameter[@tparm-id=$parm-id]/@name"/>
  </xsl:template>

  <xsl:template name="get-brg-name">
    <xsl:param name="dom-id"/>
    <xsl:param name="brg-id"/>
    <xsl:value-of select="/system/content/domain[@dom-id=$dom-id]/external-entity/bridge[@brg-id=$brg-id]/@name"/>
  </xsl:template>

  <xsl:template name="get-ee-name">
    <xsl:param name="dom-id"/>
    <xsl:param name="ee-id"/>
    <xsl:value-of select="/system/content/domain[@dom-id=$dom-id]/external-entity[@ee-id=$ee-id]/@name"/>
  </xsl:template>

  <xsl:template name="get-ee-key-lett">
    <xsl:param name="dom-id"/>
    <xsl:param name="ee-id"/>
    <xsl:value-of select="/system/content/domain[@dom-id=$dom-id]/external-entity[@ee-id=$ee-id]/@key-lett"/>
  </xsl:template>

  <xsl:template name="get-op-name">
    <xsl:param name="dom-id"/>
    <xsl:param name="op-id"/>
    <xsl:value-of select="/system/content/domain[@dom-id=$dom-id]/subsystem/class/operation[@tfr-id=$op-id]/@name"/>
  </xsl:template>

  <xsl:template name="get-op-inst-based">
    <xsl:param name="dom-id"/>
    <xsl:param name="op-id"/>
    <xsl:value-of select="/system/content/domain[@dom-id=$dom-id]/subsystem/class/operation[@tfr-id=$op-id]/@instance-based"/>
  </xsl:template>

  <xsl:template name="get-identifier">
    <xsl:param name="dom-id"/>
    <xsl:param name="inst-id"/>
    <xsl:variable name="cur-inst" select="/system/content/domain[@dom-id=$dom-id]/subsystem/class/instances/object[@obj-inst-id=$inst-id]"/>
    <xsl:choose>
      <xsl:when test='$inst-id="0"'>
Unknown
      </xsl:when>
      <xsl:when test="$cur-inst">
        <xsl:variable name="cur-class" select="$cur-inst/../../@name"/>
        <xsl:value-of select="$cur-class" />[
        <xsl:for-each select="$cur-inst/../../class-identifier[@oid-id='0']/class-identifier-attribute">
          <xsl:variable name="cur-attr-id" select="@attr-id"/>
          <xsl:value-of select="$cur-inst/attribute-value[@attr-id=$cur-attr-id]/@value"/>
          <xsl:if test="position() != last()">.</xsl:if>
        </xsl:for-each>
 ]
      </xsl:when>
      <xsl:otherwise>
        <xsl:variable name="cur-csm" select="/system/content/domain[@dom-id=$dom-id]/subsystem/class/class-statechart/class-state-machine[@obj-id=$inst-id]"/>
        <xsl:value-of select="$cur-csm/../../@name" /> Class State Machine
      </xsl:otherwise>
    </xsl:choose>
  </xsl:template>

  <xsl:template match="oal-value" mode="event">
    <xsl:variable name="name">
      <xsl:call-template name="get-evt-di-name">
        <xsl:with-param name="dom-id" select="../@dom-id"/>
        <xsl:with-param name="smedi-id" select="@smedi-id"/>
      </xsl:call-template>
    </xsl:variable>
    <xsl:value-of select="$name"/>:<xsl:value-of select="."/>
    <xsl:if test="position() != last()">, </xsl:if>
  </xsl:template>

  <xsl:template match="oal-value" mode="function">
    <xsl:variable name="name">
      <xsl:call-template name="get-fn-parm-name">
        <xsl:with-param name="dom-id" select="../@dom-id"/>
        <xsl:with-param name="sparm-id" select="@sparm-id"/>
      </xsl:call-template>
    </xsl:variable>
    <xsl:value-of select="$name"/>:<xsl:value-of select="."/>
    <xsl:if test="position() != last()">, </xsl:if>
  </xsl:template>

  <xsl:template match="oal-value" mode="operation">
    <xsl:variable name="name">
      <xsl:call-template name="get-op-parm-name">
        <xsl:with-param name="dom-id" select="../@dom-id"/>
        <xsl:with-param name="parm-id" select="@tparm-id"/>
      </xsl:call-template>
    </xsl:variable>
    <xsl:value-of select="$name"/>:<xsl:value-of select="."/>
    <xsl:if test="position() != last()">, </xsl:if>
  </xsl:template>

  <xsl:template match="oal-value" mode="bridge">
    <xsl:variable name="name">
      <xsl:call-template name="get-brg-parm-name">
        <xsl:with-param name="dom-id" select="../@dom-id"/>
        <xsl:with-param name="bparm-id" select="@bparm-id"/>
      </xsl:call-template>
    </xsl:variable>
    <xsl:value-of select="$name"/>:<xsl:value-of select="."/>
    <xsl:if test="position() != last()">, </xsl:if>
  </xsl:template>

  <xsl:template match="oal-value" mode="ee-event">
    <xsl:variable name="name">
      <xsl:call-template name="get-ext-evt-di-name">
        <xsl:with-param name="dom-id" select="../@dom-id"/>
        <xsl:with-param name="eeedi-id" select="@eeedi-id"/>
      </xsl:call-template>
    </xsl:variable>
    <xsl:value-of select="$name"/>:<xsl:value-of select="."/>
    <xsl:if test="position() != last()">, </xsl:if>
  </xsl:template>

  <xsl:template match="value" mode="event">
    <xsl:variable name="name">
      <xsl:call-template name="get-evt-di-name">
        <xsl:with-param name="dom-id" select="../@dom-id"/>
        <xsl:with-param name="smedi-id" select="@smedi-id"/>
      </xsl:call-template>
    </xsl:variable>
    <xsl:value-of select="$name"/>:<xsl:value-of select="."/>
    <xsl:if test="position() != last()">, </xsl:if>
  </xsl:template>

  <xsl:template match="value" mode="function">
    <xsl:variable name="name">
      <xsl:call-template name="get-fn-parm-name">
        <xsl:with-param name="dom-id" select="../@dom-id"/>
        <xsl:with-param name="sparm-id" select="@sparm-id"/>
      </xsl:call-template>
    </xsl:variable>
    <xsl:value-of select="$name"/>:<xsl:value-of select="."/>
    <xsl:if test="position() != last()">, </xsl:if>
  </xsl:template>

  <xsl:template match="value" mode="operation">
    <xsl:variable name="name">
      <xsl:call-template name="get-op-parm-name">
        <xsl:with-param name="dom-id" select="../@dom-id"/>
        <xsl:with-param name="parm-id" select="@tparm-id"/>
      </xsl:call-template>
    </xsl:variable>
    <xsl:value-of select="$name"/>:<xsl:value-of select="."/>
    <xsl:if test="position() != last()">, </xsl:if>
  </xsl:template>

  <xsl:template match="value" mode="bridge">
    <xsl:variable name="name">
      <xsl:call-template name="get-brg-parm-name">
        <xsl:with-param name="dom-id" select="../@dom-id"/>
        <xsl:with-param name="bparm-id" select="@bparm-id"/>
      </xsl:call-template>
    </xsl:variable>
    <xsl:value-of select="$name"/>:<xsl:value-of select="."/>
    <xsl:if test="position() != last()">, </xsl:if>
  </xsl:template>

</xsl:stylesheet>
