-- ============================================================================
-- File:        ooa_schema.sql
--
-- Description:
--
-- This file provides an SQL schema file suitable for use with the
-- DesignPoint ModelCompilers.
--
-- Model Repository:  ooa_ooa_1_9.ooa
--
-- Notice:
-- (c) Copyright 2000-2014 Mentor Graphics Corporation  All rights reserved.
--     All rights reserved.
--
--   !!! THIS IS AN AUTO-GENERATED FILE. PLEASE DO NOT EDIT. !!!
-- ============================================================================

-- ============================================================================
-- Objects In Subsystem: Subsystem  
-- ============================================================================

-- Object: 1.  Domain
CREATE TABLE S_DOM (
	Dom_ID	UNIQUE_ID,
	Name	STRING,
	Descrip	STRING,
	Full_Der	INTEGER,
	Config_ID	UNIQUE_ID,
	Sys_ID	UNIQUE_ID );

-- Object: 2.  Subsystem
CREATE TABLE S_SS (
	SS_ID	UNIQUE_ID,
	Name	STRING,
	Descrip	STRING,
	Prefix	STRING,
	Num_Rng	INTEGER,
	Dom_ID	UNIQUE_ID,
	Config_ID	UNIQUE_ID );

-- Object: 3.  External Entity
CREATE TABLE S_EE (
	EE_ID	UNIQUE_ID,
	Name	STRING,
	Descrip	STRING,
	Key_Lett	STRING,
	Dom_ID	UNIQUE_ID,
	Realized_Class_Path STRING,
    Label STRING,
    isRealized BOOLEAN );

-- Object: 4.  External Entity in Model
CREATE TABLE S_EEM (
	EEmod_ID	UNIQUE_ID,
	EE_ID	UNIQUE_ID,
	Modl_Typ	INTEGER,
	SS_ID	UNIQUE_ID );

-- Object: 5.  External Entity Data Item
CREATE TABLE S_EEDI (
	EEdi_ID	UNIQUE_ID,
	EE_ID	UNIQUE_ID,
	Name	STRING,
	Descrip	STRING,
	DT_ID	UNIQUE_ID );

-- Object: 6.  External Entity Event
CREATE TABLE S_EEEVT (
	EEevt_ID	UNIQUE_ID,
	EE_ID	UNIQUE_ID,
	Numb	INTEGER,
	Mning	STRING,
	Is_Lbl_U	INTEGER,
	Unq_Lbl	STRING,
	Drv_Lbl	STRING,
	Descrip	STRING );

-- Object: 7.  External Entity Event Data Item
CREATE TABLE S_EEEDI (
	EEedi_ID	UNIQUE_ID,
	EE_ID	UNIQUE_ID,
	Name	STRING,
	Descrip	STRING,
	DT_ID	UNIQUE_ID,
	Order   INTEGER );

-- Object: 8.  External Entity Event Data
CREATE TABLE S_EEEDT (
	EE_ID	UNIQUE_ID,
	EEevt_ID	UNIQUE_ID,
	EEedi_ID	UNIQUE_ID );

-- Object: 9.  Data Type
CREATE TABLE S_DT (
	DT_ID	UNIQUE_ID,
	Dom_ID	UNIQUE_ID,
	Name	STRING,
	Descrip	STRING,
	DefaultValue STRING );

-- Object: 10.  Core Data Type
CREATE TABLE S_CDT (
	DT_ID	UNIQUE_ID,
	Core_Typ	INTEGER );

-- Object: 11.  User Data Type
CREATE TABLE S_UDT (
	DT_ID	UNIQUE_ID,
	CDT_ID	UNIQUE_ID,
	Gen_Type	INTEGER );

-- Object: 12.  Bridge
CREATE TABLE S_BRG (
	Brg_ID	UNIQUE_ID,
	EE_ID	UNIQUE_ID,
	Name	STRING,
	Descrip	STRING,
	Brg_Typ	INTEGER,
	DT_ID	UNIQUE_ID,
	Action_Semantics	STRING,
	Suc_Pars	INTEGER,
	Return_Dimensions STRING );

-- Object: 13.  Bridge Parameter
CREATE TABLE S_BPARM (
	BParm_ID	UNIQUE_ID,
	Brg_ID	UNIQUE_ID,
	Name	STRING,
	DT_ID	UNIQUE_ID,
	By_Ref	INTEGER,	
	Dimensions STRING,
  Previous_BParm_ID	UNIQUE_ID,
	Descrip STRING,
	Order   INTEGER );

-- Object: 14.  Enumeration Data Type
CREATE TABLE S_EDT (
	DT_ID	UNIQUE_ID );

-- Object: 15.  Enumerator
CREATE TABLE S_ENUM (
	Enum_ID	UNIQUE_ID,
	Name	STRING,
	Descrip	STRING,
	EDT_ID	UNIQUE_ID,
	Previous_Enum_ID UNIQUE_ID );

-- Object: 16.  Synchronous Service
CREATE TABLE S_SYNC (
	Sync_ID	UNIQUE_ID,
	Dom_ID	UNIQUE_ID,
	Name	STRING,
	Descrip	STRING,
	Action_Semantics	STRING,
	DT_ID	UNIQUE_ID,
	Suc_Pars	INTEGER,
	Return_Dimensions STRING );

-- Object: 17.  Synchronous Service Parameter
CREATE TABLE S_SPARM (
	SParm_ID	UNIQUE_ID,
	Sync_ID	UNIQUE_ID,
	Name	STRING,
	DT_ID	UNIQUE_ID,
	By_Ref	INTEGER,
	Dimensions STRING,
  Previous_SParm_ID	UNIQUE_ID,
	Descrip  STRING,
	Order   INTEGER );
CREATE TABLE S_FPK (
	     FunPack_ID	UNIQUE_ID,
	     Name	STRING,
	     Dom_ID	UNIQUE_ID,
	     Parent_FunPack_ID	UNIQUE_ID );
CREATE TABLE S_FIP (
	     FunPack_ID	UNIQUE_ID,
	     Sync_ID	UNIQUE_ID );
CREATE TABLE S_FPIP (
	     FunPack_ID	UNIQUE_ID );
CREATE TABLE S_EEPK (
	     EEPack_ID	UNIQUE_ID,
	     Name	STRING,
	     Dom_ID	UNIQUE_ID,
	     Parent_EEPack_ID	UNIQUE_ID );
CREATE TABLE S_EEIP (
	     EEPack_ID	UNIQUE_ID,
	     EE_ID	UNIQUE_ID );
CREATE TABLE S_EEPIP (
	     EEPack_ID	UNIQUE_ID );
CREATE TABLE S_DPK (
	     DatPack_ID	UNIQUE_ID,
	     Name	STRING,
	     Dom_ID	UNIQUE_ID,
	     Parent_DatPack_ID	UNIQUE_ID );
CREATE TABLE S_DIP (
	     DatPack_ID	UNIQUE_ID,
	     DT_ID	UNIQUE_ID );
CREATE TABLE S_DPIP (
	     DatPack_ID	UNIQUE_ID );
CREATE TABLE S_SIS (
	     Parent_SS_ID	UNIQUE_ID,
	     Child_SS_ID	UNIQUE_ID );
CREATE TABLE S_SID (
	     Dom_ID	UNIQUE_ID,
	     SS_ID	UNIQUE_ID );
CREATE TABLE PL_EEPID (
	     Dom_ID	UNIQUE_ID,
	     EEPack_ID	UNIQUE_ID );
CREATE TABLE PL_FPID (
	     FunPack_ID	UNIQUE_ID,
	     Dom_ID	UNIQUE_ID );
CREATE TABLE SQ_LS (
	     Part_ID	UNIQUE_ID,
	     ClassInst_Part_ID	UNIQUE_ID,
	     Descrip	STRING,
	     Destroyed	BOOLEAN );
CREATE TABLE SQ_CIP (
	     Part_ID	UNIQUE_ID,
	     Obj_ID	UNIQUE_ID,
	     Name	STRING,
	     InformalClassName	STRING,
	     Label	STRING,
	     Descrip	STRING,
	     isFormal	BOOLEAN );
CREATE TABLE SQ_TM (
	     Mark_ID	UNIQUE_ID,
	     Name	STRING,
	     Part_ID	UNIQUE_ID,
	     Descrip	STRING );
CREATE TABLE SQ_TS (
	     Span_ID	UNIQUE_ID,
	     Mark_ID	UNIQUE_ID,
	     Prev_Mark_ID	UNIQUE_ID,
	     Name	STRING,
	     Descrip	STRING );
CREATE TABLE SQ_CP (
	     Part_ID	UNIQUE_ID,
	     Obj_ID	UNIQUE_ID,
	     Label	STRING,
	     InformalName	STRING,
	     Descrip	STRING,
	     isFormal	BOOLEAN );
CREATE TABLE SQ_P (
	     Part_ID	UNIQUE_ID,
	     Seq_ID	UNIQUE_ID );
CREATE TABLE SQ_EEP (
	     Part_ID	UNIQUE_ID,
	     EE_ID	UNIQUE_ID,
	     Label	STRING,
	     InformalName	STRING,
	     Descrip	STRING,
	     isFormal	BOOLEAN );
CREATE TABLE SQ_FPP (
	     Part_ID	UNIQUE_ID,
	     FunPack_ID	UNIQUE_ID,
	     Label	STRING,
	     InformalName	STRING,
	     Descrip	STRING,
	     isFormal	BOOLEAN );
CREATE TABLE SQ_CPA (
	     Ia_ID	UNIQUE_ID,
	     Name	STRING,
	     Type	STRING,
	     Part_ID	UNIQUE_ID,
	     Descrip	STRING );
CREATE TABLE SQ_AV (
	     Av_ID	UNIQUE_ID,
	     Obj_ID	UNIQUE_ID,
	     Attr_ID	UNIQUE_ID,
	     Label	STRING,
	     Value	STRING,
	     InformalName	STRING,
	     Informal_Part_ID	UNIQUE_ID,
	     Formal_Part_ID	UNIQUE_ID,
	     Descrip	STRING,
	     isFormal	BOOLEAN );
CREATE TABLE SQ_AP (
	     Part_ID	UNIQUE_ID,
	     Name	STRING,
	     Descrip	STRING,
	     LS_Part_ID	UNIQUE_ID );
CREATE TABLE IA_UCP (
	     Part_ID	UNIQUE_ID,
	     Name	STRING,
	     Descrip	STRING );
CREATE TABLE SQ_IA (
	     Ia_ID	UNIQUE_ID );
CREATE TABLE SQ_FA (
	     Ia_ID	UNIQUE_ID );
CREATE TABLE SQ_FAV (
	     Av_ID	UNIQUE_ID );
CREATE TABLE SQ_IAV (
	     Av_ID	UNIQUE_ID );
CREATE TABLE MSG_M (
	     Msg_ID	UNIQUE_ID,
	     Receiver_Part_ID	UNIQUE_ID,
	     Sender_Part_ID	UNIQUE_ID );
CREATE TABLE MSG_A (
	     Arg_ID	UNIQUE_ID,
	     Informal_Msg_ID	UNIQUE_ID,
	     Formal_Msg_ID	UNIQUE_ID,
	     Label	STRING,
	     Value	STRING,
	     InformalName	STRING,
	     Descrip	STRING,
	     isFormal	BOOLEAN );
CREATE TABLE MSG_O (
	     Msg_ID	UNIQUE_ID,
	     Tfr_ID	UNIQUE_ID );
CREATE TABLE MSG_B (
	     Msg_ID	UNIQUE_ID,
	     Brg_ID	UNIQUE_ID );
CREATE TABLE MSG_E (
	     Msg_ID	UNIQUE_ID,
	     SMevt_ID	UNIQUE_ID );
CREATE TABLE MSG_F (
	     Msg_ID	UNIQUE_ID,
	     Sync_ID	UNIQUE_ID );
CREATE TABLE MSG_BA (
	     Arg_ID	UNIQUE_ID,
	     BParm_ID	UNIQUE_ID );
CREATE TABLE MSG_OA (
	     Arg_ID	UNIQUE_ID,
	     TParm_ID	UNIQUE_ID );
CREATE TABLE MSG_FA (
	     Arg_ID	UNIQUE_ID,
	     SParm_ID	UNIQUE_ID );
CREATE TABLE MSG_EA (
	     Arg_ID	UNIQUE_ID,
	     SM_ID	UNIQUE_ID,
	     SMedi_ID	UNIQUE_ID );
CREATE TABLE MSG_R (
	     Msg_ID	UNIQUE_ID,
	     Name	STRING,
	     Descrip	STRING,
	     GuardCondition	STRING,
	     ResultTarget	STRING,
	     ReturnValue	STRING,
	     SequenceNumb	STRING );
CREATE TABLE MSG_SM (
	     Msg_ID	UNIQUE_ID,
	     InformalName	STRING,
	     Descrip	STRING,
	     GuardCondition	STRING,
	     ResultTarget	STRING,
	     ReturnValue	STRING,
	     isFormal	BOOLEAN,
	     Label	STRING,
	     SequenceNumb	STRING );
CREATE TABLE MSG_AM (
	     Msg_ID	UNIQUE_ID,
	     InformalName	STRING,
	     Descrip	STRING,
	     GuardCondition	STRING,
	     DurationObservation	STRING,
	     DurationConstraint	STRING,
	     isFormal	BOOLEAN,
	     Label	STRING,
	     SequenceNumb	STRING );
CREATE TABLE MSG_ISM (
	     Msg_ID	UNIQUE_ID );
CREATE TABLE MSG_IAM (
	     Msg_ID	UNIQUE_ID );
CREATE TABLE MSG_IA (
	     Arg_ID	UNIQUE_ID );
CREATE TABLE COMM_LNK (
	     Link_ID	UNIQUE_ID,
	     Rel_ID	UNIQUE_ID,
	     Numb	STRING,
	     Descrip	STRING,
	     StartText	STRING,
	     EndText	STRING,
	     isFormal	BOOLEAN,
	     StartVisibility	INTEGER,
	     EndVisibility	INTEGER,
	     Start_Part_ID	UNIQUE_ID,
	     Destination_Part_ID	UNIQUE_ID );
CREATE TABLE COMM_COMM (
	     Comm_ID	UNIQUE_ID,
	     Dom_ID	UNIQUE_ID,
	     SS_ID	UNIQUE_ID,
	     CIC_Comm_ID	UNIQUE_ID,
	     Name	STRING,
	     Descrip	STRING );
CREATE TABLE COMM_CIC (
	     Comm_ID	UNIQUE_ID );
CREATE TABLE COMM_PIC (
	     Comm_ID	UNIQUE_ID,
	     Part_ID	UNIQUE_ID );
CREATE TABLE COMM_MIC (
	     Comm_ID	UNIQUE_ID,
	     Msg_ID	UNIQUE_ID );
CREATE TABLE SQ_S (
	     Seq_ID	UNIQUE_ID,
	     Dom_ID	UNIQUE_ID,
	     Name	STRING,
	     SS_ID	UNIQUE_ID,
	     Prev_Seq_ID	UNIQUE_ID,
	     Descrip	STRING );
CREATE TABLE SQ_SIS (
	     Seq_ID	UNIQUE_ID );
CREATE TABLE UC_UCC (
	     UCC_ID	UNIQUE_ID,
	     Dom_ID	UNIQUE_ID,
	     SS_ID	UNIQUE_ID,
	     UCCinUCC_ID	UNIQUE_ID,
	     Name	STRING,
	     Descrip	STRING );
CREATE TABLE UC_UCA (
	     Assoc_ID	UNIQUE_ID,
	     Source_Part_ID	UNIQUE_ID,
	     Destination_Part_ID	UNIQUE_ID );
CREATE TABLE UC_PIUC (
	     UCC_ID	UNIQUE_ID,
	     Part_ID	UNIQUE_ID );
CREATE TABLE UC_UIU (
	     UCC_ID	UNIQUE_ID );
CREATE TABLE UC_BA (
	     Assoc_ID	UNIQUE_ID,
	     Descrip	STRING );
CREATE TABLE UC_G (
	     Assoc_ID	UNIQUE_ID,
	     Descrip	STRING );
CREATE TABLE UC_I (
	     Assoc_ID	UNIQUE_ID,
	     Descrip	STRING );
CREATE TABLE UC_E (
	     Assoc_ID	UNIQUE_ID,
	     Descrip	STRING );
CREATE TABLE A_A (
	     Id	UNIQUE_ID,
	     Name	STRING,
	     Descrip	STRING,
	     Dom_ID	UNIQUE_ID,
	     SS_ID	UNIQUE_ID,
	     Parent_Id	UNIQUE_ID );
CREATE TABLE A_AIA (
	     Id	UNIQUE_ID );
CREATE TABLE A_AP (
	     Id	UNIQUE_ID,
	     ActivityId	UNIQUE_ID,
	     Name	STRING,
	     Descrip	STRING );
CREATE TABLE A_N (
	     Id	UNIQUE_ID,
	     ActivityId	UNIQUE_ID );
CREATE TABLE A_E (
	     Id	UNIQUE_ID,
	     ActivityId	UNIQUE_ID,
	     Guard	STRING,
	     Descrip	STRING,
	     TargetId	UNIQUE_ID,
	     SourceId	UNIQUE_ID );
CREATE TABLE A_GA (
	     Id	UNIQUE_ID,
	     Name	STRING,
	     Descrip	STRING );
CREATE TABLE A_AE (
	     Id	UNIQUE_ID );
CREATE TABLE A_AEA (
	     Id	UNIQUE_ID,
	     Name STRING,
	     Descrip STRING );
CREATE TABLE A_ATE (
	     Id	UNIQUE_ID,
	     Name STRING,
	     Descrip STRING );
CREATE TABLE A_SS (
	     Id	UNIQUE_ID,
	     Name	STRING,
	     Descrip	STRING );
CREATE TABLE A_ACT (
	     Id	UNIQUE_ID );
CREATE TABLE A_OBJ (
	     Id	UNIQUE_ID,
	     Name	STRING,
	     Descrip	STRING );
CREATE TABLE A_CTL (
	     Id	UNIQUE_ID );
CREATE TABLE A_INI (
	     Id	UNIQUE_ID,
	     Descrip	STRING );
CREATE TABLE A_FJ (
	     Id	UNIQUE_ID,
	     Descrip	STRING,
	     GuardCondition	STRING );
CREATE TABLE A_DM (
	     Id	UNIQUE_ID,
	     DecisionInput	STRING,
	     Descrip	STRING );
CREATE TABLE A_FF (
	     Id	UNIQUE_ID,
	     Descrip	STRING );
CREATE TABLE A_AF (
	     Id	UNIQUE_ID,
	     Descrip	STRING );

-- ============================================================================
-- Objects In Subsystem: Object  
-- ============================================================================

-- Object: 101.  Object
CREATE TABLE O_OBJ (
	Obj_ID	UNIQUE_ID,
	Name	STRING,
	Numb	INTEGER,
	Key_Lett	STRING,
	Descrip	STRING,
	SS_ID	UNIQUE_ID,
	AdapterName STRING,
	Order INTEGER );

-- Object: 102.  Imported Object
CREATE TABLE O_IOBJ (
	IObj_ID	UNIQUE_ID,
	Obj_ID	UNIQUE_ID,
	Modl_Typ	INTEGER,
	SS_ID	UNIQUE_ID,
	Obj_Name	STRING,
	Obj_KL	STRING );

-- Object: 103.  Attribute
CREATE TABLE O_ATTR (
	Attr_ID	UNIQUE_ID,
	Obj_ID	UNIQUE_ID,
	PAttr_ID	UNIQUE_ID,
	Name	STRING,
	Descrip	STRING,
	Prefix	STRING,
	Root_Nam	STRING,
	Pfx_Mode	INTEGER,
	DT_ID	UNIQUE_ID,
	Dimensions STRING,
	DefaultValue STRING );

-- Object: 104.  Object Identifier
CREATE TABLE O_ID (
	Oid_ID	INTEGER,
	Obj_ID	UNIQUE_ID );

-- Object: 105.  Object Identifier Attribute
CREATE TABLE O_OIDA (
	Attr_ID	UNIQUE_ID,
	Obj_ID	UNIQUE_ID,
	Oid_ID	INTEGER,
	localAttributeName STRING );

-- Object: 106.  Base Attribute
CREATE TABLE O_BATTR (
	Attr_ID	UNIQUE_ID,
	Obj_ID	UNIQUE_ID );

-- Object: 107.  Derived Based Attribute
CREATE TABLE O_DBATTR (
	Attr_ID	UNIQUE_ID,
	Obj_ID	UNIQUE_ID,
	Action_Semantics	STRING,
	Suc_Pars	INTEGER );

-- Object: 108.  New Base Attribute
CREATE TABLE O_NBATTR (
	Attr_ID	UNIQUE_ID,
	Obj_ID	UNIQUE_ID );

-- Object: 109.  Referential Attribute
CREATE TABLE O_RATTR (
	Attr_ID	UNIQUE_ID,
	Obj_ID	UNIQUE_ID,
	BAttr_ID	UNIQUE_ID,
	BObj_ID	UNIQUE_ID,
	Ref_Mode	INTEGER,
	BaseAttrName STRING );

-- Object: 110.  Attribute Reference in Object
CREATE TABLE O_REF (
	Obj_ID	UNIQUE_ID,
	RObj_ID	UNIQUE_ID,
	ROid_ID	INTEGER,
	RAttr_ID	UNIQUE_ID,
	Rel_ID	UNIQUE_ID,
	OIR_ID	UNIQUE_ID,
	ROIR_ID	UNIQUE_ID,
	Attr_ID	UNIQUE_ID,
	ARef_ID	UNIQUE_ID,
	PARef_ID	UNIQUE_ID,
	Is_Cstrd	BOOLEAN,
	Descrip	STRING,
	RObj_Name STRING,
	RAttr_Name STRING,
	Rel_Name String );

-- Object: 111.  Referred To Identifier Attribute
CREATE TABLE O_RTIDA (
	Attr_ID	UNIQUE_ID,
	Obj_ID	UNIQUE_ID,
	Oid_ID	INTEGER,
	Rel_ID	UNIQUE_ID,
	OIR_ID	UNIQUE_ID );

-- Object: 112.  Transformer
CREATE TABLE O_TFR (
	Tfr_ID	UNIQUE_ID,
	Obj_ID	UNIQUE_ID,
	Name	STRING,
	Descrip	STRING,
	DT_ID	UNIQUE_ID,
	Instance_Based	INTEGER,
	Action_Semantics	STRING,
	Suc_Pars	INTEGER,
	Return_Dimensions STRING,
    Previous_Tfr_ID	UNIQUE_ID  );

-- Object: 113.  Transformer Parameter
CREATE TABLE O_TPARM (
	TParm_ID	UNIQUE_ID,
	Tfr_ID	UNIQUE_ID,
	Name	STRING,
	DT_ID	UNIQUE_ID,
	By_Ref	INTEGER,	
	Dimensions STRING,
  Previous_TParm_ID	UNIQUE_ID,
	Descrip STRING,
	Order   INTEGER );


-- ============================================================================
-- Objects In Subsystem: Relationship  
-- ============================================================================

-- Object: 201.  Relationship
CREATE TABLE R_REL (
	Rel_ID	UNIQUE_ID,
	Numb	INTEGER,
	Descrip	STRING,
	SS_ID	UNIQUE_ID,
	ContainmentClassKL STRING );

-- Object: 202.  Object In Relationship
CREATE TABLE R_OIR (
	Obj_ID	UNIQUE_ID,
	Rel_ID	UNIQUE_ID,
	OIR_ID	UNIQUE_ID,
	IObj_ID	UNIQUE_ID );

-- Object: 203.  Referred To Object in Rel
CREATE TABLE R_RTO (
	Obj_ID	UNIQUE_ID,
	Rel_ID	UNIQUE_ID,
	OIR_ID	UNIQUE_ID,
	Oid_ID	INTEGER );

-- Object: 204.  Referring Object In Rel
CREATE TABLE R_RGO (
	Obj_ID	UNIQUE_ID,
	Rel_ID	UNIQUE_ID,
	OIR_ID	UNIQUE_ID );

-- Object: 205.  Simple Relationship
CREATE TABLE R_SIMP (
	Rel_ID	UNIQUE_ID );

-- Object: 206.  Object As Simple Participant
CREATE TABLE R_PART (
	Obj_ID	UNIQUE_ID,
	Rel_ID	UNIQUE_ID,
	OIR_ID	UNIQUE_ID,
	Mult	INTEGER,
	Cond	INTEGER,
	Txt_Phrs	STRING );

-- Object: 207.  Object As Simple Formalizer
CREATE TABLE R_FORM (
	Obj_ID	UNIQUE_ID,
	Rel_ID	UNIQUE_ID,
	OIR_ID	UNIQUE_ID,
	Mult	INTEGER,
	Cond	INTEGER,
	Txt_Phrs	STRING );

-- Object: 208.  Associative Relationship
CREATE TABLE R_ASSOC (
	Rel_ID	UNIQUE_ID );

-- Object: 209.  Object As Associated One Side
CREATE TABLE R_AONE (
	Obj_ID	UNIQUE_ID,
	Rel_ID	UNIQUE_ID,
	OIR_ID	UNIQUE_ID,
	Mult	INTEGER,
	Cond	INTEGER,
	Txt_Phrs	STRING );

-- Object: 210.  Object As Associated Other Side
CREATE TABLE R_AOTH (
	Obj_ID	UNIQUE_ID,
	Rel_ID	UNIQUE_ID,
	OIR_ID	UNIQUE_ID,
	Mult	INTEGER,
	Cond	INTEGER,
	Txt_Phrs	STRING );

-- Object: 211.  Object As Associator
CREATE TABLE R_ASSR (
	Obj_ID	UNIQUE_ID,
	Rel_ID	UNIQUE_ID,
	OIR_ID	UNIQUE_ID,
	Mult	INTEGER );

-- Object: 212.  Subtype/Supertype Relationship
CREATE TABLE R_SUBSUP (
	Rel_ID	UNIQUE_ID );

-- Object: 213.  Object As Supertype
CREATE TABLE R_SUPER (
	Obj_ID	UNIQUE_ID,
	Rel_ID	UNIQUE_ID,
	OIR_ID	UNIQUE_ID );

-- Object: 214.  Object As Subtype
CREATE TABLE R_SUB (
	Obj_ID	UNIQUE_ID,
	Rel_ID	UNIQUE_ID,
	OIR_ID	UNIQUE_ID );

-- Object: 215.  Composition Relationship
CREATE TABLE R_COMP (
	Rel_ID	UNIQUE_ID,
	Rel_Chn	STRING );

-- Object: 216.  Object As Composition One Side
CREATE TABLE R_CONE (
	Obj_ID	UNIQUE_ID,
	Rel_ID	UNIQUE_ID,
	OIR_ID	UNIQUE_ID,
	Mult	INTEGER,
	Cond	INTEGER,
	Txt_Phrs	STRING );

-- Object: 217.  Object As Composition Other Side
CREATE TABLE R_COTH (
	Obj_ID	UNIQUE_ID,
	Rel_ID	UNIQUE_ID,
	OIR_ID	UNIQUE_ID,
	Mult	INTEGER,
	Cond	INTEGER,
	Txt_Phrs	STRING );


-- ============================================================================
-- Objects In Subsystem: Communication And Access  
-- ============================================================================

-- Object: 401.  Communication Path
CREATE TABLE CA_COMM (
	CPath_ID	UNIQUE_ID,
	SS_ID	UNIQUE_ID );

-- Object: 402.  EE to SM Comm Path
CREATE TABLE CA_EESMC (
	CPath_ID	UNIQUE_ID,
	EEmod_ID	UNIQUE_ID,
	EE_ID	UNIQUE_ID,
	SM_ID	UNIQUE_ID );

-- Object: 403.  SM to SM Comm Path
CREATE TABLE CA_SMSMC (
	CPath_ID	UNIQUE_ID,
	OSM_ID	UNIQUE_ID,
	DSM_ID	UNIQUE_ID,
	OIObj_ID	UNIQUE_ID,
	DIObj_ID	UNIQUE_ID );

-- Object: 404.  SM to EE Comm Path
CREATE TABLE CA_SMEEC (
	CPath_ID	UNIQUE_ID,
	SM_ID	UNIQUE_ID,
	EE_ID	UNIQUE_ID,
	EEmod_ID	UNIQUE_ID );

-- Object: 405.  EE to SM Event Comm
CREATE TABLE CA_EESME (
	CPath_ID	UNIQUE_ID,
	SM_ID	UNIQUE_ID,
	SMevt_ID	UNIQUE_ID );

-- Object: 406.  SM to SM Event Comm
CREATE TABLE CA_SMSME (
	CPath_ID	UNIQUE_ID,
	SM_ID	UNIQUE_ID,
	SMevt_ID	UNIQUE_ID );

-- Object: 407.  SM to EE Event Comm
CREATE TABLE CA_SMEEE (
	CPath_ID	UNIQUE_ID,
	EE_ID	UNIQUE_ID,
	EEevt_ID	UNIQUE_ID );

-- Object: 408.  Access Path
CREATE TABLE CA_ACC (
	APath_ID	UNIQUE_ID,
	SS_ID	UNIQUE_ID,
	SM_ID	UNIQUE_ID,
	IObj_ID	UNIQUE_ID );

-- Object: 409.  SM to OBJ Access Path
CREATE TABLE CA_SMOA (
	APath_ID	UNIQUE_ID,
	Obj_ID	UNIQUE_ID,
	IObj_ID	UNIQUE_ID );

-- Object: 410.  SM to EE Access Path
CREATE TABLE CA_SMEEA (
	APath_ID	UNIQUE_ID,
	EE_ID	UNIQUE_ID,
	EEmod_ID	UNIQUE_ID );

-- Object: 411.  SM to OBJ Attribute Access
CREATE TABLE CA_SMOAA (
	APath_ID	UNIQUE_ID,
	Attr_ID	UNIQUE_ID,
	Obj_ID	UNIQUE_ID );

-- Object: 412.  SM to EE Data Item Access
CREATE TABLE CA_SMEED (
	APath_ID	UNIQUE_ID,
	EEdi_ID	UNIQUE_ID,
	EE_ID	UNIQUE_ID );


-- ============================================================================
-- Objects In Subsystem: State Model  
-- ============================================================================

-- Object: 501.  State Model
CREATE TABLE SM_SM (
	SM_ID	UNIQUE_ID,
	Descrip	STRING,
	Config_ID	INTEGER );

-- Object: 502.  State Model State
CREATE TABLE SM_STATE (
	SMstt_ID	UNIQUE_ID,
	SM_ID	UNIQUE_ID,
	SMspd_ID	UNIQUE_ID,
	Name	STRING,
	Numb	INTEGER,
	Final	INTEGER );

-- Object: 503.  State Model Event
CREATE TABLE SM_EVT (
	SMevt_ID	UNIQUE_ID,
	SM_ID	UNIQUE_ID,
	SMspd_ID	UNIQUE_ID,
	Numb	INTEGER,
	Mning	STRING,
	Is_Lbl_U	INTEGER,
	Unq_Lbl	STRING,
	Drv_Lbl	STRING,
	Descrip	STRING );

-- Object: 504.  State Event Matrix Entry
CREATE TABLE SM_SEME (
	SMstt_ID	UNIQUE_ID,
	SMevt_ID	UNIQUE_ID,
	SM_ID	UNIQUE_ID,
	SMspd_ID	UNIQUE_ID );

-- Object: 505.  New State Transition
CREATE TABLE SM_NSTXN (
	Trans_ID	UNIQUE_ID,
	SM_ID	UNIQUE_ID,
	SMstt_ID	UNIQUE_ID,
	SMevt_ID	UNIQUE_ID,
	SMspd_ID	UNIQUE_ID );

-- Object: 506.  Event Ignored
CREATE TABLE SM_EIGN (
	SMstt_ID	UNIQUE_ID,
	SMevt_ID	UNIQUE_ID,
	SM_ID	UNIQUE_ID,
	SMspd_ID	UNIQUE_ID,
	Descrip	STRING );

-- Object: 507.  Cant Happen
CREATE TABLE SM_CH (
	SMstt_ID	UNIQUE_ID,
	SMevt_ID	UNIQUE_ID,
	SM_ID	UNIQUE_ID,
	SMspd_ID	UNIQUE_ID,
	Descrip	STRING );

-- Object: 508.  Transition
CREATE TABLE SM_TXN (
	Trans_ID	UNIQUE_ID,
	SM_ID	UNIQUE_ID,
	SMstt_ID	UNIQUE_ID,
	SMspd_ID	UNIQUE_ID );

-- Object: 509.  No Event Transition
CREATE TABLE SM_NETXN (
	Trans_ID	UNIQUE_ID,
	SM_ID	UNIQUE_ID,
	SMstt_ID	UNIQUE_ID,
	SMspd_ID	UNIQUE_ID );

-- Object: 510.  Creation Transition
CREATE TABLE SM_CRTXN (
	Trans_ID	UNIQUE_ID,
	SM_ID	UNIQUE_ID,
	SMevt_ID	UNIQUE_ID,
	SMspd_ID	UNIQUE_ID );

-- Object: 511.  Moore State Model
CREATE TABLE SM_MOORE (
	SM_ID	UNIQUE_ID );

-- Object: 512.  Mealy State Model
CREATE TABLE SM_MEALY (
	SM_ID	UNIQUE_ID );

-- Object: 513.  Moore Action Home
CREATE TABLE SM_MOAH (
	Act_ID	UNIQUE_ID,
	SM_ID	UNIQUE_ID,
	SMstt_ID	UNIQUE_ID );

-- Object: 514.  Mealy Action Home
CREATE TABLE SM_MEAH (
	Act_ID	UNIQUE_ID,
	SM_ID	UNIQUE_ID,
	Trans_ID	UNIQUE_ID );

-- Object: 515.  Action Home
CREATE TABLE SM_AH (
	Act_ID	UNIQUE_ID,
	SM_ID	UNIQUE_ID );

-- Object: 516.  Action
CREATE TABLE SM_ACT (
	Act_ID	UNIQUE_ID,
	SM_ID	UNIQUE_ID,
	Suc_Pars	INTEGER,
	Action_Semantics	STRING,
	Descrip	STRING );

-- Object: 517.  State Model Event Data Item
CREATE TABLE SM_EVTDI (
	SMedi_ID	UNIQUE_ID,
	SM_ID	UNIQUE_ID,
	Name	STRING,
	Descrip	STRING,
	DT_ID	UNIQUE_ID,
	Dimensions STRING,
	SMevt_ID	UNIQUE_ID,
	Previous_SMedi_ID	UNIQUE_ID, 
	Order   INTEGER );

-- Object: 518.  Event Supplemental Data
CREATE TABLE SM_SUPDT (
	SMspd_ID	UNIQUE_ID,
	SM_ID	UNIQUE_ID,
	Non_Local	BOOLEAN );

-- Object: 519.  Supplemental Data Items
CREATE TABLE SM_SDI (
	SMedi_ID	UNIQUE_ID,
	SMspd_ID	UNIQUE_ID,
	SM_ID	UNIQUE_ID );

-- Object: 520.  Instance State Model
CREATE TABLE SM_ISM (
	SM_ID	UNIQUE_ID,
	Obj_ID	UNIQUE_ID );

-- Object: 521.  Assigner State Model
CREATE TABLE SM_ASM (
	SM_ID	UNIQUE_ID,
	Obj_ID	UNIQUE_ID );

-- Object: 522.  Polymorphic Event
CREATE TABLE SM_PEVT (
	SMevt_ID	UNIQUE_ID,
	SM_ID	UNIQUE_ID,
	SMspd_ID	UNIQUE_ID,
	localClassName STRING,
	localClassKL STRING,
	localEventMning STRING );

-- Object: 523.  SEM Event
CREATE TABLE SM_SEVT (
	SMevt_ID	UNIQUE_ID,
	SM_ID	UNIQUE_ID,
	SMspd_ID	UNIQUE_ID );

-- Object: 524.  Local Event
CREATE TABLE SM_LEVT (
	SMevt_ID	UNIQUE_ID,
	SM_ID	UNIQUE_ID,
	SMspd_ID	UNIQUE_ID );

-- Object: 525.  Non Local Event
CREATE TABLE SM_NLEVT (
	SMevt_ID	UNIQUE_ID,
	SM_ID	UNIQUE_ID,
	SMspd_ID	UNIQUE_ID,
	polySMevt_ID	UNIQUE_ID,
	polySM_ID	UNIQUE_ID,
	polySMspd_ID	UNIQUE_ID,
	Local_Meaning	STRING );

-- Object: 529.  Transition Action Home
CREATE TABLE SM_TAH (
	     Act_ID	UNIQUE_ID,
	     SM_ID	UNIQUE_ID,
	     Trans_ID	UNIQUE_ID );

-- ============================================================================
-- Objects In Subsystem: Version Management  
-- ============================================================================

-- Object: 905.  Component Version in Configuration Version
CREATE TABLE V_CIOV (
	Domain_Name	STRING,
	Component_id	UNIQUE_ID,
	Parent_Component_id	UNIQUE_ID,
	Component_type	INTEGER,
	Component_Branch_Name	STRING,
	Component_Version_Num	INTEGER,
	Pinned	BOOLEAN,
	Abstract	STRING );

-- ============================================================================
-- Objects In Subsystem: Element Packaging 
-- ============================================================================

CREATE TABLE EP_PKG (
	     Package_ID	UNIQUE_ID,
	     Sys_ID	UNIQUE_ID,
	     Direct_Sys_ID	UNIQUE_ID,
	     Name	STRING,
	     Descrip	STRING,
	     Num_Rng    INTEGER );
CREATE TABLE EP_PIP (
	     Parent_Package_ID	UNIQUE_ID,
	     Child_Package_ID	UNIQUE_ID );
CREATE TABLE EP_SPKG (
	     Package_ID	UNIQUE_ID,
	     Container_Package_ID	UNIQUE_ID );

-- ============================================================================
-- Classes In Subsystem:  Packageable Element  
-- ============================================================================

-- Class:  8000.  Packageable Element
CREATE TABLE PE_PE (
	Element_ID	UNIQUE_ID,
	Visibility	INTEGER,
	Package_ID	UNIQUE_ID,
	Component_ID	UNIQUE_ID,
	type	INTEGER );

-- ============================================================================
-- Relationships In Packageable Element  
-- ============================================================================
CREATE ROP REF_ID R8000	FROM MC PE_PE	(Package_ID)
			  TO 1  EP_PKG	(Package_ID);

CREATE ROP REF_ID R8001	FROM 1C O_IOBJ	(IObj_ID)
			  TO 1  PE_PE	(Element_ID);
CREATE ROP REF_ID R8001	FROM 1C O_OBJ	(Obj_ID)
			  TO 1  PE_PE	(Element_ID);
-- ============================================================================
-- Relationships In Subsystem: Subsystem  
-- ============================================================================
  
CREATE ROP REF_ID R1	FROM MC S_SS	(Dom_ID)
			  TO 1  S_DOM	(Dom_ID);

CREATE ROP REF_ID R2	FROM MC O_OBJ	(SS_ID)
			  TO 1C  S_SS	(SS_ID);

CREATE ROP REF_ID R3	FROM MC O_IOBJ	(SS_ID)
			  TO 1  S_SS	(SS_ID);

CREATE ROP REF_ID R4	FROM MC R_REL	(SS_ID)
			  TO 1  S_SS	(SS_ID);

CREATE ROP REF_ID R5	FROM MC CA_COMM	(SS_ID)
			  TO 1  S_SS	(SS_ID);

CREATE ROP REF_ID R6	FROM MC CA_ACC	(SS_ID)
			  TO 1  S_SS	(SS_ID);

CREATE ROP REF_ID R7	FROM MC S_EEM	(SS_ID)
			  TO 1  S_SS	(SS_ID);

CREATE ROP REF_ID R8	FROM MC S_EE	(Dom_ID)
			  TO 1  S_DOM	(Dom_ID);

CREATE ROP REF_ID R9	FROM MC S_EEM	(EE_ID)
			  TO 1  S_EE	(EE_ID);

CREATE ROP REF_ID R10	FROM MC S_EEEVT	(EE_ID)
			  TO 1  S_EE	(EE_ID);

CREATE ROP REF_ID R11	FROM MC S_EEDI	(EE_ID)
			  TO 1  S_EE	(EE_ID);

CREATE ROP REF_ID R12	FROM MC S_EEEDI	(EE_ID)
			  TO 1  S_EE	(EE_ID);

CREATE ROP REF_ID R13	FROM MC S_EEEDT	(EEedi_ID, EE_ID)
			  TO 1  S_EEEDI	(EEedi_ID, EE_ID);

CREATE ROP REF_ID R13	FROM MC S_EEEDT	(EE_ID, EEevt_ID)
			  TO 1  S_EEEVT	(EE_ID, EEevt_ID);

CREATE ROP REF_ID R14 FROM MC 	S_DT 	( Dom_ID )
		         TO 1C  	S_DOM 	( Dom_ID );

CREATE ROP REF_ID R15	FROM MC S_EEDI	(DT_ID)
			  TO 1  S_DT	(DT_ID);

CREATE ROP REF_ID R16	FROM MC S_EEEDI	(DT_ID)
			  TO 1  S_DT	(DT_ID);

CREATE ROP REF_ID R17	FROM 1C S_CDT	(DT_ID)
			  TO 1  S_DT	(DT_ID);

CREATE ROP REF_ID R17	FROM 1C S_UDT	(DT_ID)
			  TO 1  S_DT	(DT_ID);

CREATE ROP REF_ID R17	FROM 1C S_EDT	(DT_ID)
			  TO 1  S_DT	(DT_ID);

CREATE ROP REF_ID R18	FROM MC S_UDT	(CDT_ID)
			  TO 1  S_CDT	(DT_ID);

CREATE ROP REF_ID R19	FROM MC S_BRG	(EE_ID)
			  TO 1  S_EE	(EE_ID);

CREATE ROP REF_ID R20	FROM MC S_BRG	(DT_ID)
			  TO 1  S_DT	(DT_ID);

CREATE ROP REF_ID R21	FROM MC S_BPARM	(Brg_ID)
			  TO 1  S_BRG	(Brg_ID);

CREATE ROP REF_ID R22	FROM MC S_BPARM	(DT_ID)
			  TO 1  S_DT	(DT_ID);

CREATE ROP REF_ID R23	FROM MC S_SYNC	(Dom_ID)
			  TO 1  S_DOM	(Dom_ID);

CREATE ROP REF_ID R24	FROM MC S_SPARM	(Sync_ID)
			  TO 1  S_SYNC	(Sync_ID);

CREATE ROP REF_ID R25	FROM MC S_SYNC	(DT_ID)
			  TO 1  S_DT	(DT_ID);

CREATE ROP REF_ID R26	FROM MC S_SPARM	(DT_ID)
			  TO 1  S_DT	(DT_ID);

CREATE ROP REF_ID R27	FROM MC S_ENUM	(EDT_ID)
			  TO 1  S_EDT	(DT_ID);


-- ============================================================================
-- Relationships In Subsystem: Object  
-- ============================================================================
  
CREATE ROP REF_ID R101	FROM MC O_IOBJ	(Obj_ID)
			  TO 1C O_OBJ	(Obj_ID);

CREATE ROP REF_ID R102	FROM MC O_ATTR	(Obj_ID)
			  TO 1  O_OBJ	(Obj_ID);

CREATE ROP REF_ID R103	FROM 1C O_ATTR	(Obj_ID, PAttr_ID) PHRASE 'succeeds'
			  TO 1C O_ATTR	(Obj_ID, Attr_ID) PHRASE 'precedes';

CREATE ROP REF_ID R104	FROM MC O_ID	(Obj_ID)
			  TO 1  O_OBJ	(Obj_ID);

CREATE ROP REF_ID R105	FROM MC O_OIDA	(Obj_ID, Oid_ID)
			  TO 1  O_ID	(Obj_ID, Oid_ID);

CREATE ROP REF_ID R105	FROM MC O_OIDA	(Obj_ID, Attr_ID)
			  TO 1  O_ATTR	(Obj_ID, Attr_ID);

CREATE ROP REF_ID R106	FROM 1C O_BATTR	(Obj_ID, Attr_ID)
			  TO 1  O_ATTR	(Obj_ID, Attr_ID);

CREATE ROP REF_ID R106	FROM 1C O_RATTR	(Obj_ID, Attr_ID)
			  TO 1  O_ATTR	(Obj_ID, Attr_ID);

CREATE ROP REF_ID R107	FROM 1C O_DBATTR	(Attr_ID, Obj_ID)
			  TO 1  O_BATTR	(Attr_ID, Obj_ID);

CREATE ROP REF_ID R107	FROM 1C O_NBATTR	(Attr_ID, Obj_ID)
			  TO 1  O_BATTR	(Attr_ID, Obj_ID);

CREATE ROP REF_ID R108	FROM M  O_REF	(Obj_ID, Attr_ID)
			  TO 1  O_RATTR	(Obj_ID, Attr_ID);

CREATE ROP REF_ID R109	FROM MC R_RTO	(Obj_ID, Oid_ID)
			  TO 1C O_ID	(Obj_ID, Oid_ID);

CREATE ROP REF_ID R110	FROM M  O_RTIDA	(Oid_ID, Rel_ID, Obj_ID, OIR_ID)
			  TO 1  R_RTO	(Oid_ID, Rel_ID, Obj_ID, OIR_ID);

CREATE ROP REF_ID R110	FROM MC O_RTIDA	(Oid_ID, Attr_ID, Obj_ID)
			  TO 1  O_OIDA	(Oid_ID, Attr_ID, Obj_ID);

CREATE ROP REF_ID R111	FROM M  O_REF	(Obj_ID, Rel_ID, OIR_ID)
			  TO 1  R_RGO	(Obj_ID, Rel_ID, OIR_ID);

CREATE ROP REF_ID R111	FROM M  O_REF	(RObj_ID, ROIR_ID, Rel_ID, RAttr_ID, ROid_ID)
			  TO 1  O_RTIDA	(Obj_ID, OIR_ID, Rel_ID, Attr_ID, Oid_ID);

CREATE ROP REF_ID R112	FROM 1C O_REF	(PARef_ID) PHRASE 'succeeds'
			  TO 1C O_REF	(ARef_ID) PHRASE 'precedes';

CREATE ROP REF_ID R113	FROM MC O_RATTR	(BAttr_ID, BObj_ID)
			  TO 1  O_BATTR	(Attr_ID, Obj_ID);

CREATE ROP REF_ID R114	FROM MC O_ATTR	(DT_ID)
			  TO 1  S_DT	(DT_ID);

CREATE ROP REF_ID R115	FROM MC O_TFR	(Obj_ID)
			  TO 1  O_OBJ	(Obj_ID);

CREATE ROP REF_ID R116	FROM MC O_TFR	(DT_ID)
			  TO 1  S_DT	(DT_ID);

CREATE ROP REF_ID R117	FROM MC O_TPARM	(Tfr_ID)
			  TO 1  O_TFR	(Tfr_ID);

CREATE ROP REF_ID R118	FROM MC O_TPARM	(DT_ID)
			  TO 1  S_DT	(DT_ID);


-- ============================================================================
-- Relationships In Subsystem: Relationship  
-- ============================================================================
  
CREATE ROP REF_ID R201	FROM M  R_OIR	(Rel_ID)
			  TO 1  R_REL	(Rel_ID);

CREATE ROP REF_ID R201	FROM MC R_OIR	(Obj_ID)
			  TO 1  O_OBJ	(Obj_ID);

CREATE ROP REF_ID R202	FROM MC R_OIR	(IObj_ID)
			  TO 1C O_IOBJ	(IObj_ID);

CREATE ROP REF_ID R203	FROM 1C R_RTO	(Rel_ID, Obj_ID, OIR_ID)
			  TO 1  R_OIR	(Rel_ID, Obj_ID, OIR_ID);

CREATE ROP REF_ID R203	FROM 1C R_RGO	(Rel_ID, Obj_ID, OIR_ID)
			  TO 1  R_OIR	(Rel_ID, Obj_ID, OIR_ID);

CREATE ROP REF_ID R203	FROM 1C R_CONE	(Rel_ID, Obj_ID, OIR_ID)
			  TO 1  R_OIR	(Rel_ID, Obj_ID, OIR_ID);

CREATE ROP REF_ID R203	FROM 1C R_COTH	(Rel_ID, Obj_ID, OIR_ID)
			  TO 1  R_OIR	(Rel_ID, Obj_ID, OIR_ID);

CREATE ROP REF_ID R204	FROM 1C R_SUPER	(Rel_ID, OIR_ID, Obj_ID)
			  TO 1  R_RTO	(Rel_ID, OIR_ID, Obj_ID);

CREATE ROP REF_ID R204	FROM 1C R_PART	(Rel_ID, OIR_ID, Obj_ID)
			  TO 1  R_RTO	(Rel_ID, OIR_ID, Obj_ID);

CREATE ROP REF_ID R204	FROM 1C R_AONE	(Rel_ID, OIR_ID, Obj_ID)
			  TO 1  R_RTO	(Rel_ID, OIR_ID, Obj_ID);

CREATE ROP REF_ID R204	FROM 1C R_AOTH	(Rel_ID, OIR_ID, Obj_ID)
			  TO 1  R_RTO	(Rel_ID, OIR_ID, Obj_ID);

CREATE ROP REF_ID R205	FROM 1C R_SUB	(Obj_ID, Rel_ID, OIR_ID)
			  TO 1  R_RGO	(Obj_ID, Rel_ID, OIR_ID);

CREATE ROP REF_ID R205	FROM 1C R_FORM	(Obj_ID, Rel_ID, OIR_ID)
			  TO 1  R_RGO	(Obj_ID, Rel_ID, OIR_ID);

CREATE ROP REF_ID R205	FROM 1C R_ASSR	(Obj_ID, Rel_ID, OIR_ID)
			  TO 1  R_RGO	(Obj_ID, Rel_ID, OIR_ID);

CREATE ROP REF_ID R206	FROM 1C R_SIMP	(Rel_ID)
			  TO 1  R_REL	(Rel_ID);

CREATE ROP REF_ID R206	FROM 1C R_COMP	(Rel_ID)
			  TO 1  R_REL	(Rel_ID);

CREATE ROP REF_ID R206	FROM 1C R_ASSOC	(Rel_ID)
			  TO 1  R_REL	(Rel_ID);

CREATE ROP REF_ID R206	FROM 1C R_SUBSUP	(Rel_ID)
			  TO 1  R_REL	(Rel_ID);

CREATE ROP REF_ID R207	FROM 1  R_PART	(Rel_ID)
			  TO 1  R_SIMP	(Rel_ID);

CREATE ROP REF_ID R208	FROM 1C R_FORM	(Rel_ID)
			  TO 1  R_SIMP	(Rel_ID);

CREATE ROP REF_ID R209	FROM 1  R_AONE	(Rel_ID)
			  TO 1  R_ASSOC	(Rel_ID);

CREATE ROP REF_ID R210	FROM 1  R_AOTH	(Rel_ID)
			  TO 1  R_ASSOC	(Rel_ID);

CREATE ROP REF_ID R211	FROM 1  R_ASSR	(Rel_ID)
			  TO 1  R_ASSOC	(Rel_ID);

CREATE ROP REF_ID R212	FROM 1  R_SUPER	(Rel_ID)
			  TO 1  R_SUBSUP	(Rel_ID);

CREATE ROP REF_ID R213	FROM MC R_SUB	(Rel_ID)
			  TO 1  R_SUBSUP	(Rel_ID);

CREATE ROP REF_ID R214	FROM 1  R_CONE	(Rel_ID)
			  TO 1  R_COMP	(Rel_ID);

CREATE ROP REF_ID R215	FROM 1  R_COTH	(Rel_ID)
			  TO 1  R_COMP	(Rel_ID);


-- ============================================================================
-- Relationships In Subsystem: Communication And Access  
-- ============================================================================
  
CREATE ROP REF_ID R401	FROM 1C CA_EESMC	(CPath_ID)
			  TO 1  CA_COMM	(CPath_ID);

CREATE ROP REF_ID R401	FROM 1C CA_SMEEC	(CPath_ID)
			  TO 1  CA_COMM	(CPath_ID);

CREATE ROP REF_ID R401	FROM 1C CA_SMSMC	(CPath_ID)
			  TO 1  CA_COMM	(CPath_ID);

CREATE ROP REF_ID R402	FROM MC CA_EESMC	(EEmod_ID, EE_ID)
			  TO 1  S_EEM	(EEmod_ID, EE_ID);

CREATE ROP REF_ID R403	FROM MC CA_EESMC	(SM_ID)
			  TO 1  SM_SM	(SM_ID);

CREATE ROP REF_ID R404	FROM MC CA_EESME	(CPath_ID)
			  TO 1  CA_EESMC	(CPath_ID);

CREATE ROP REF_ID R405	FROM MC CA_EESME	(SMevt_ID)
			  TO 1  SM_EVT	(SMevt_ID);

CREATE ROP REF_ID R406	FROM MC CA_SMSMC	(OSM_ID)
			  TO 1  SM_SM	(SM_ID);

CREATE ROP REF_ID R407	FROM MC CA_SMSMC	(DSM_ID)
			  TO 1  SM_SM	(SM_ID);

CREATE ROP REF_ID R408	FROM MC CA_SMSME	(CPath_ID)
			  TO 1  CA_SMSMC	(CPath_ID);

CREATE ROP REF_ID R409	FROM MC CA_SMSME	(SMevt_ID)
			  TO 1  SM_EVT	(SMevt_ID);

CREATE ROP REF_ID R410	FROM MC CA_SMEEC	(SM_ID)
			  TO 1  SM_SM	(SM_ID);

CREATE ROP REF_ID R411	FROM MC CA_SMEEC	(EEmod_ID, EE_ID)
			  TO 1  S_EEM	(EEmod_ID, EE_ID);

CREATE ROP REF_ID R412	FROM MC CA_SMEEE	(CPath_ID)
			  TO 1  CA_SMEEC	(CPath_ID);

CREATE ROP REF_ID R413	FROM MC CA_SMEEE	(EE_ID, EEevt_ID)
			  TO 1  S_EEEVT	(EE_ID, EEevt_ID);

CREATE ROP REF_ID R414	FROM MC CA_SMSMC	(DIObj_ID)
			  TO 1C O_IOBJ	(IObj_ID);

CREATE ROP REF_ID R415	FROM 1C CA_SMOA	(APath_ID)
			  TO 1  CA_ACC	(APath_ID);

CREATE ROP REF_ID R415	FROM 1C CA_SMEEA	(APath_ID)
			  TO 1  CA_ACC	(APath_ID);

CREATE ROP REF_ID R416	FROM MC CA_ACC	(SM_ID)
			  TO 1  SM_SM	(SM_ID);

CREATE ROP REF_ID R417	FROM MC CA_SMOA	(Obj_ID)
			  TO 1  O_OBJ	(Obj_ID);

CREATE ROP REF_ID R418	FROM MC CA_SMOAA	(Obj_ID, APath_ID)
			  TO 1  CA_SMOA	(Obj_ID, APath_ID);

CREATE ROP REF_ID R419	FROM MC CA_SMOAA	(Obj_ID, Attr_ID)
			  TO 1  O_ATTR	(Obj_ID, Attr_ID);

CREATE ROP REF_ID R420	FROM MC CA_SMOA	(IObj_ID)
			  TO 1C O_IOBJ	(IObj_ID);

CREATE ROP REF_ID R421	FROM MC CA_SMEEA	(EEmod_ID, EE_ID)
			  TO 1  S_EEM	(EEmod_ID, EE_ID);

CREATE ROP REF_ID R422	FROM MC CA_SMEED	(EE_ID, APath_ID)
			  TO 1  CA_SMEEA	(EE_ID, APath_ID);

CREATE ROP REF_ID R423	FROM MC CA_SMEED	(EE_ID, EEdi_ID)
			  TO 1  S_EEDI	(EE_ID, EEdi_ID);

CREATE ROP REF_ID R424	FROM MC CA_SMSMC	(OIObj_ID)
			  TO 1C O_IOBJ	(IObj_ID);

CREATE ROP REF_ID R425	FROM MC CA_ACC	(IObj_ID)
			  TO 1C O_IOBJ	(IObj_ID);


-- ============================================================================
-- Relationships In Subsystem: State Model  
-- ============================================================================
  
CREATE ROP REF_ID R501	FROM MC SM_STATE	(SM_ID)
			  TO 1  SM_SM	(SM_ID);

CREATE ROP REF_ID R502	FROM MC SM_EVT	(SM_ID)
			  TO 1  SM_SM	(SM_ID);

CREATE ROP REF_ID R503	FROM MC SM_SEME	(SMstt_ID, SM_ID)
			  TO 1  SM_STATE	(SMstt_ID, SM_ID);

CREATE ROP REF_ID R503	FROM MC SM_SEME	(SM_ID, SMevt_ID, SMspd_ID)
			  TO 1  SM_SEVT	(SM_ID, SMevt_ID, SMspd_ID);

CREATE ROP REF_ID R504	FROM 1C SM_EIGN	(SMstt_ID, SMevt_ID, SMspd_ID, SM_ID)
			  TO 1  SM_SEME	(SMstt_ID, SMevt_ID, SMspd_ID, SM_ID);

CREATE ROP REF_ID R504	FROM 1C SM_CH	(SMstt_ID, SMevt_ID, SMspd_ID, SM_ID)
			  TO 1  SM_SEME	(SMstt_ID, SMevt_ID, SMspd_ID, SM_ID);

CREATE ROP REF_ID R504	FROM 1C SM_NSTXN	(SMstt_ID, SMevt_ID, SMspd_ID, SM_ID)
			  TO 1  SM_SEME	(SMstt_ID, SMevt_ID, SMspd_ID, SM_ID);

CREATE ROP REF_ID R505	FROM MC SM_TXN	(SM_ID)
			  TO 1  SM_SM	(SM_ID);

CREATE ROP REF_ID R506	FROM MC SM_TXN	(SMspd_ID, SMstt_ID, SM_ID)
			  TO 1  SM_STATE	(SMspd_ID, SMstt_ID, SM_ID);

CREATE ROP REF_ID R507	FROM 1C SM_NETXN	(SM_ID, Trans_ID)
			  TO 1  SM_TXN	(SM_ID, Trans_ID);

CREATE ROP REF_ID R507	FROM 1C SM_CRTXN	(SM_ID, Trans_ID)
			  TO 1  SM_TXN	(SM_ID, Trans_ID);

CREATE ROP REF_ID R507	FROM 1C SM_NSTXN	(SM_ID, Trans_ID)
			  TO 1  SM_TXN	(SM_ID, Trans_ID);

CREATE ROP REF_ID R508	FROM MC SM_NETXN	(SMstt_ID, SM_ID)
			  TO 1  SM_STATE	(SMstt_ID, SM_ID);

CREATE ROP REF_ID R509	FROM 1C SM_CRTXN	(SMevt_ID, SM_ID)
			  TO 1C SM_LEVT	(SMevt_ID, SM_ID);

CREATE ROP REF_ID R510	FROM 1C SM_MEALY	(SM_ID)
			  TO 1  SM_SM	(SM_ID);

CREATE ROP REF_ID R510	FROM 1C SM_MOORE	(SM_ID)
			  TO 1  SM_SM	(SM_ID);

CREATE ROP REF_ID R511	FROM M  SM_MOAH	(SM_ID)
			  TO 1  SM_MOORE	(SM_ID);

CREATE ROP REF_ID R511	FROM 1C SM_MOAH	(SMstt_ID, SM_ID)
			  TO 1  SM_STATE	(SMstt_ID, SM_ID);

CREATE ROP REF_ID R512	FROM M  SM_MEAH	(SM_ID)
			  TO 1  SM_MEALY	(SM_ID);

CREATE ROP REF_ID R512	FROM 1C SM_MEAH	(SM_ID, Trans_ID)
			  TO 1  SM_TXN	(SM_ID, Trans_ID);

CREATE ROP REF_ID R513	FROM 1C SM_MOAH	(SM_ID, Act_ID)
			  TO 1  SM_AH	(SM_ID, Act_ID);

CREATE ROP REF_ID R513	FROM 1C SM_MEAH	(SM_ID, Act_ID)
			  TO 1  SM_AH	(SM_ID, Act_ID);

CREATE ROP REF_ID R514	FROM 1  SM_AH	(SM_ID, Act_ID)
			  TO 1  SM_ACT	(SM_ID, Act_ID);

CREATE ROP REF_ID R515	FROM MC SM_ACT	(SM_ID)
			  TO 1  SM_SM	(SM_ID);

CREATE ROP REF_ID R516	FROM MC SM_EVTDI	(SM_ID)
			  TO 1  SM_SM	(SM_ID);

CREATE ROP REF_ID R517	FROM 1C SM_ISM	(SM_ID)
			  TO 1  SM_SM	(SM_ID);

CREATE ROP REF_ID R517	FROM 1C SM_ASM	(SM_ID)
			  TO 1  SM_SM	(SM_ID);

CREATE ROP REF_ID R518	FROM 1C SM_ISM	(Obj_ID)
			  TO 1  O_OBJ	(Obj_ID);

CREATE ROP REF_ID R519	FROM 1C SM_ASM	(Obj_ID)
			  TO 1  O_OBJ	(Obj_ID);

CREATE ROP REF_ID R520	FROM M  SM_EVT	(SMspd_ID, SM_ID)
			  TO 1  SM_SUPDT	(SMspd_ID, SM_ID);

CREATE ROP REF_ID R521	FROM MC SM_STATE	(SMspd_ID, SM_ID)
			  TO 1C SM_SUPDT	(SMspd_ID, SM_ID);

CREATE ROP REF_ID R522	FROM MC SM_SDI	(SMspd_ID, SM_ID)
			  TO 1  SM_SUPDT	(SMspd_ID, SM_ID);

CREATE ROP REF_ID R522	FROM MC SM_SDI	(SM_ID, SMedi_ID)
			  TO 1  SM_EVTDI	(SM_ID, SMedi_ID);

CREATE ROP REF_ID R523	FROM MC SM_SUPDT	(SM_ID)
			  TO 1  SM_SM	(SM_ID);

CREATE ROP REF_ID R524	FROM MC SM_EVTDI	(DT_ID)
			  TO 1  S_DT	(DT_ID);

CREATE ROP REF_ID R525	FROM 1C SM_SEVT	(SMspd_ID, SM_ID, SMevt_ID)
			  TO 1  SM_EVT	(SMspd_ID, SM_ID, SMevt_ID);

CREATE ROP REF_ID R525	FROM 1C SM_PEVT	(SMspd_ID, SM_ID, SMevt_ID)
			  TO 1  SM_EVT	(SMspd_ID, SM_ID, SMevt_ID);

CREATE ROP REF_ID R526	FROM 1C SM_NLEVT	(SM_ID, SMevt_ID, SMspd_ID)
			  TO 1  SM_SEVT	(SM_ID, SMevt_ID, SMspd_ID);

CREATE ROP REF_ID R526	FROM 1C SM_LEVT	(SM_ID, SMevt_ID, SMspd_ID)
			  TO 1  SM_SEVT	(SM_ID, SMevt_ID, SMspd_ID);

CREATE ROP REF_ID R527	FROM MC SM_NLEVT	(polySMevt_ID, polySM_ID)
			  TO 1  SM_PEVT	(SMevt_ID, SM_ID);
			  
CREATE ROP REF_ID R532 FROM MC 	SM_EVTDI 	( SMevt_ID )
		         TO 1  	SM_EVT 	( SMevt_ID );

-- ============================================================================
-- Relationships In Subsystem: Version Management  
-- ============================================================================
  

-- ============================================================================
-- Processing subsystems 
-- ============================================================================


CREATE TABLE ACT_ACT (
             Action_Id	STRING,
	     Type	STRING,
             Block_Id	STRING );
CREATE TABLE ACT_BLK (
             Block_Id	STRING,
             Action_Id	STRING,
             Type STRING );
CREATE TABLE ACT_SMT (
             Statement_Id	STRING,
             Block_Id	STRING,
             Controlled_Block	STRING,
             Previous_Statement_Id	STRING,
	     buffer	STRING,
	     buffer2	STRING,
             control	STRING );
CREATE TABLE ACT_SEL (
             Statement_Id	STRING,
             Variable_Name	STRING,
             Block_Id	STRING,
             is_implicit	STRING,
	     cardinality	STRING,
             Start_Variable_Name	STRING,
             Start_Block_Id	STRING );
CREATE TABLE ACT_FOR (
             Statement_Id	STRING,
             Block_Id	STRING,
             Loop_Variable_Name	STRING,
             Loop_Block_Id	STRING,
             Set_Variable_Name	STRING,
             Set_Block_Id	STRING,
             is_implicit	STRING,
	     Target_Obj_KL	STRING );
CREATE TABLE ACT_IF (
             Statement_Id	STRING,
             Block_Id	STRING,
             Value_Id	STRING );
CREATE TABLE ACT_FIO (
             Statement_Id	STRING,
             Variable_Name	STRING,
             Block_Id	STRING,
             is_implicit	STRING,
	     cardinality	STRING,
	     Target_Obj_KL	STRING );
CREATE TABLE ACT_CNV (
             Statement_Id	STRING,
	     New_Obj_KL	STRING );
CREATE TABLE ACT_CTL (
             Statement_Id	STRING,
	     Command	STRING );
CREATE TABLE ACT_BRK (
             Statement_Id	STRING );
CREATE TABLE ACT_CON (
             Statement_Id	STRING );
CREATE TABLE ACT_UNR (
             Statement_Id	STRING,
             One_Side_Variable_Name	STRING,
             One_Side_Block_Id	STRING,
             Other_Side_Variable_Name	STRING,
             Other_Side_Block_Id	STRING,
	     relationship_phrase	STRING,
             Numb	INTEGER );
CREATE TABLE ACT_AT (
             Statement_Id	STRING,
             Value_Id	STRING,
             Target_Variable_Name	STRING,
             Block_Id	STRING,
             is_implicit	STRING );
CREATE TABLE ACT_URU (
             Statement_Id	STRING,
             One_Side_Variable_Name	STRING,
             One_Side_Block_Id	STRING,
             Other_Side_Variable_Name	STRING,
             Other_Side_Block_Id	STRING,
             Associative_Variable_Name	STRING,
             Associative_Block_Id	STRING,
	     relationship_phrase	STRING,
             Numb	INTEGER );
CREATE TABLE ACT_REL (
             Statement_Id	STRING,
             One_Side_Variable_Name	STRING,
             One_Side_Block_Id	STRING,
             Other_Side_Variable_Name	STRING,
             Other_Side_Block_Id	STRING,
	     relationship_phrase	STRING,
             Numb	INTEGER );
CREATE TABLE ACT_CR (
             Statement_Id	STRING,
             Variable_Name	STRING,
             Block_Id	STRING,
             is_implicit	STRING,
	     New_Obj_KL	STRING );
CREATE TABLE ACT_E (
             Statement_Id	STRING,
             Block_Id	STRING,
             If_Statement_Id	STRING );
CREATE TABLE ACT_DEL (
             Statement_Id	STRING,
             Variable_Name	STRING,
             Block_Id	STRING );
CREATE TABLE ACT_RU (
             Statement_Id	STRING,
             One_Side_Variable_Name	STRING,
             One_Side_Block_Id	STRING,
             Other_Side_Variable_Name	STRING,
             Other_Side_Block_Id	STRING,
             Associative_Variable_Name	STRING,
             Associative_Block_Id	STRING,
	     relationship_phrase	STRING,
             Numb	INTEGER );
CREATE TABLE ACT_AI (
             Statement_Id	STRING,
             Value_Id	STRING,
             Instance_Variable_Name	STRING,
             Instance_Block_Id	STRING,
             Member_Variable_Name	STRING,
             Member_Block_Id	STRING );
CREATE TABLE ACT_LNK (
             Link_Id	STRING,
	     Target_Obj_KL	STRING,
	     Rel_Phrase	STRING,
             Statement_Id	STRING,
             Numb	INTEGER,
             Next_Link_Id	STRING );
CREATE TABLE ACT_BRG (
             Statement_Id	STRING,
	     External_Entity_KL	STRING,
	     Method_Name	STRING );
CREATE TABLE ACT_TFM (
             Statement_Id	STRING,
	     Object_KL	STRING,
	     Method_Name	STRING,
             Block_Id	STRING,
             Variable_Name	STRING );
CREATE TABLE ACT_SRW (
             Statement_Id	STRING,
             Where_clause_Value_Id	STRING );
CREATE TABLE ACT_FIW (
             Statement_Id	STRING,
             Variable_Name	STRING,
             Block_Id	STRING,
             is_implicit	STRING,
	     cardinality	STRING,
	     Target_Obj_KL	STRING,
             Where_clause_Value_Id	STRING );
CREATE TABLE ACT_WHL (
             Statement_Id	STRING,
             Value_Id	STRING,
             Block_Id	STRING );
CREATE TABLE ACT_EL (
             Statement_Id	STRING,
             Block_Id	STRING,
             Value_Id	STRING,
             If_Statement_Id	STRING );
CREATE TABLE ACT_SR (
             Statement_Id	STRING );
CREATE TABLE ACT_RET (
             Statement_Id	STRING,
             Value_Id	STRING );
CREATE TABLE ACT_FNC (
             Statement_Id	STRING,
	     Function_Name	STRING );
CREATE TABLE V_VAR (
             Variable_Name	STRING,
             Block_Id	STRING,
             DT_ID STRING );
CREATE TABLE V_SLF (
             Variable_Name	STRING,
             Block_Id	STRING );
CREATE TABLE V_INT (
             Variable_Name	STRING,
             Block_Id	STRING,
	     Key_Lett	STRING );
CREATE TABLE V_INS (
             Variable_Name	STRING,
             Block_Id	STRING,
	     Key_Lett	STRING );
CREATE TABLE V_TRN (
             Variable_Name	STRING,
             Block_Id	STRING );
CREATE TABLE V_VAL (
             Value_Id	STRING,
	     Buffer	STRING,
	     DT_ID STRING,
	     Block_Id STRING );
CREATE TABLE V_PAR (
             Statement_Id	STRING,
             Value_Id	STRING,
	     Name	STRING,
             Previous_Statement_Id	STRING,
             Previous_Value_Id	STRING,
             Order INTEGER );
CREATE TABLE V_BIN (
             Value_Id	STRING,
             Right_Value_Id	STRING,
             Left_Value_Id	STRING,
	     Operator	STRING );
CREATE TABLE V_LBO (
             Value_Id	STRING,
	     Value	STRING );
CREATE TABLE V_LRL (
             Value_Id	STRING,
	     Value	STRING );
CREATE TABLE V_LEN (
             Value_Id	STRING,
             Enum_Name	STRING,
		     Value		STRING );
CREATE TABLE V_TVL (
             Value_Id	STRING,
             Variable_Name	STRING,
             Block_Id	STRING );
CREATE TABLE V_EDV (
             Value_Id	STRING );
CREATE TABLE V_EPR (
             Value_Id	STRING,
             SM_ID	STRING,
             SMedi_ID	STRING );
CREATE TABLE V_ISR (
             Value_Id	STRING,
             Variable_Name	STRING,
             Block_Id	STRING );
CREATE TABLE V_TRV (
             Value_Id	STRING,
             Statement_Id	STRING );
CREATE TABLE V_UNY (
             Value_Id	STRING,
             Operand_Value_Id	STRING,
	     Operator	STRING );
CREATE TABLE V_PEX (
             Value_Id	STRING,
             Par_Value_Id	STRING );
CREATE TABLE V_LST (
             Value_Id	STRING,
	     Value	STRING );
CREATE TABLE V_LIN (
             Value_Id	STRING,
	     Value	STRING );
CREATE TABLE V_AVL (
             Value_Id	STRING,
             Member_Variable_Name	STRING,
             Member_Block_Id	STRING,
             Instance_Variable_Name	STRING,
             Instance_Block_Id	STRING );
CREATE TABLE V_IRF (
             Value_Id	STRING,
             Variable_Name	STRING,
             Block_Id	STRING );
CREATE TABLE V_BRV (
             Value_Id	STRING,
             Statement_Id	STRING );
CREATE TABLE V_SLR (
             Value_Id	STRING,
             Variable_Name	STRING,
             Block_Id	STRING );
CREATE TABLE V_PVL (
             Value_Id	STRING,
	     Parameter_Name	STRING );
CREATE TABLE V_FNC (
             Value_Id	STRING,
             Statement_Id	STRING );
CREATE TABLE E_GEN (
             Statement_Id	STRING,
             Variable_Name	STRING,
             Block_Id	STRING,
	     Event_Label	STRING );
CREATE TABLE E_GAR (
             Statement_Id	STRING,
	     Event_Label	STRING,
	     Key_Lett	STRING );
CREATE TABLE E_GEE (
             Statement_Id	STRING,
	     Event_Label	STRING,
	     Key_Lett	STRING );
CREATE TABLE E_GPR (
             Statement_Id	STRING,
             Variable_Name	STRING,
             Block_Id	STRING );
CREATE TABLE E_CEI (
             Statement_Id	STRING,
             Variable_Name	STRING,
             Variable_Block_Id	STRING,
             is_implicit	STRING,
	     Event_Label	STRING,
             Recipient_Variable_Name	STRING,
             Recipient_Block_Id	STRING );
CREATE TABLE E_CEA (
             Statement_Id	STRING,
             Variable_Name	STRING,
             is_implicit	STRING,
	     Event_Label	STRING,
	     Key_Lett	STRING,
             Block_Id	STRING );
CREATE TABLE E_CEE (
             Statement_Id	STRING,
             Variable_Name	STRING,
             is_implicit	STRING,
	     Event_Label	STRING,
	     Key_Lett	STRING,
             Block_Id	STRING );
CREATE TABLE E_CEC (
             Statement_Id	STRING,
             is_implicit	STRING,
	     Event_Label	STRING,
	     Key_Lett	STRING,
             Variable_Name	STRING,
             Block_Id	STRING );
CREATE TABLE E_GEC (
             Statement_Id	STRING,
	     Event_Label	STRING,
	     Key_Lett	STRING );

CREATE ROP REF_ID R601 FROM MC 	ACT_BLK 	( Action_Id, Type )
		         TO 1  	ACT_ACT 	( Action_Id, Type );
CREATE ROP REF_ID R602 FROM MC 	ACT_SMT 	( Block_Id )
		         TO 1  	ACT_BLK 	( Block_Id );
CREATE ROP REF_ID R603 FROM 1C 	ACT_SEL 	( Statement_Id )
		     TO 1  	ACT_SMT 	( Statement_Id );
CREATE ROP REF_ID R603 FROM 1C 	ACT_FOR 	( Statement_Id )
		     TO 1  	ACT_SMT 	( Statement_Id );
CREATE ROP REF_ID R603 FROM 1C 	ACT_IF 	( Statement_Id )
		     TO 1  	ACT_SMT 	( Statement_Id );
CREATE ROP REF_ID R603 FROM 1C 	ACT_CR 	( Statement_Id )
		     TO 1  	ACT_SMT 	( Statement_Id );
CREATE ROP REF_ID R603 FROM 1C 	ACT_REL 	( Statement_Id )
		     TO 1  	ACT_SMT 	( Statement_Id );
CREATE ROP REF_ID R603 FROM 1C 	ACT_UNR 	( Statement_Id )
		     TO 1  	ACT_SMT 	( Statement_Id );
CREATE ROP REF_ID R603 FROM 1C 	ACT_FIO 	( Statement_Id )
		     TO 1  	ACT_SMT 	( Statement_Id );
CREATE ROP REF_ID R603 FROM 1C 	ACT_CNV 	( Statement_Id )
		     TO 1  	ACT_SMT 	( Statement_Id );
CREATE ROP REF_ID R603 FROM 1C 	ACT_CTL 	( Statement_Id )
		     TO 1  	ACT_SMT 	( Statement_Id );
CREATE ROP REF_ID R603 FROM 1C 	ACT_BRK 	( Statement_Id )
		     TO 1  	ACT_SMT 	( Statement_Id );
CREATE ROP REF_ID R603 FROM 1C 	ACT_CON 	( Statement_Id )
		     TO 1  	ACT_SMT 	( Statement_Id );
CREATE ROP REF_ID R603 FROM 1C 	ACT_DEL 	( Statement_Id )
		     TO 1  	ACT_SMT 	( Statement_Id );
CREATE ROP REF_ID R603 FROM 1C 	ACT_RU 	( Statement_Id )
		     TO 1  	ACT_SMT 	( Statement_Id );
CREATE ROP REF_ID R603 FROM 1C 	ACT_URU 	( Statement_Id )
		     TO 1  	ACT_SMT 	( Statement_Id );
CREATE ROP REF_ID R603 FROM 1C 	ACT_AI 	( Statement_Id )
		     TO 1  	ACT_SMT 	( Statement_Id );
CREATE ROP REF_ID R603 FROM 1C 	ACT_AT 	( Statement_Id )
		     TO 1  	ACT_SMT 	( Statement_Id );
CREATE ROP REF_ID R603 FROM 1C 	ACT_E 	( Statement_Id )
		     TO 1  	ACT_SMT 	( Statement_Id );
CREATE ROP REF_ID R603 FROM 1C 	ACT_BRG 	( Statement_Id )
		     TO 1C  	ACT_SMT 	( Statement_Id );
CREATE ROP REF_ID R603 FROM 1C 	ACT_TFM 	( Statement_Id )
		     TO 1C  	ACT_SMT 	( Statement_Id );
CREATE ROP REF_ID R603 FROM 1C 	ACT_FIW 	( Statement_Id )
		     TO 1  	ACT_SMT 	( Statement_Id );
CREATE ROP REF_ID R603 FROM 1C 	ACT_WHL 	( Statement_Id )
		     TO 1  	ACT_SMT 	( Statement_Id );
CREATE ROP REF_ID R603 FROM 1C 	ACT_EL 	( Statement_Id )
		     TO 1  	ACT_SMT 	( Statement_Id );
CREATE ROP REF_ID R603 FROM 1C 	ACT_RET 	( Statement_Id )
		     TO 1  	ACT_SMT 	( Statement_Id );
CREATE ROP REF_ID R603 FROM 1C 	ACT_FNC 	( Statement_Id )
		     TO 1C  	ACT_SMT 	( Statement_Id );
CREATE ROP REF_ID R607 FROM 1C 	ACT_IF 	( Block_Id )
		         TO 1  	ACT_BLK 	( Block_Id );
CREATE ROP REF_ID R605 FROM 1C 	ACT_FOR 	( Block_Id )
		         TO 1  	ACT_BLK 	( Block_Id );
CREATE ROP REF_ID R606 FROM 1C 	ACT_E 	( Block_Id )
		         TO 1  	ACT_BLK 	( Block_Id );
CREATE ROP REF_ID R639 FROM 1C 	ACT_FIO 	( Variable_Name , Block_Id )
		         TO 1  	V_VAR 	( Variable_Name , Block_Id );
CREATE ROP REF_ID R638 FROM 1C 	ACT_SEL 	( Variable_Name , Block_Id )
		         TO 1  	V_VAR 	( Variable_Name , Block_Id );
CREATE ROP REF_ID R614 FROM 1C 	ACT_FOR 	( Loop_Variable_Name , Loop_Block_Id )
		         TO 1  	V_VAR 	( Variable_Name , Block_Id );
CREATE ROP REF_ID R652 FROM 1C 	ACT_FOR 	( Set_Variable_Name , Set_Block_Id )
		         TO 1  	V_VAR 	( Variable_Name , Block_Id );
CREATE ROP REF_ID R633 FROM 1C 	ACT_CR 	( Variable_Name , Block_Id )
		         TO 1  	V_VAR 	( Variable_Name , Block_Id );
CREATE ROP REF_ID R634 FROM MC 	ACT_DEL 	( Variable_Name , Block_Id )
		         TO 1  	V_VAR 	( Variable_Name , Block_Id );
CREATE ROP REF_ID R615 FROM MC 	ACT_REL 	( One_Side_Variable_Name , One_Side_Block_Id )
		         TO 1  	V_VAR 	( Variable_Name , Block_Id );
CREATE ROP REF_ID R617 FROM MC 	ACT_RU 	( One_Side_Variable_Name , One_Side_Block_Id )
		         TO 1  	V_VAR 	( Variable_Name , Block_Id );
CREATE ROP REF_ID R618 FROM MC 	ACT_RU 	( Other_Side_Variable_Name , Other_Side_Block_Id )
		         TO 1  	V_VAR 	( Variable_Name , Block_Id );
CREATE ROP REF_ID R619 FROM MC 	ACT_RU 	( Associative_Variable_Name , Associative_Block_Id )
		         TO 1  	V_VAR 	( Variable_Name , Block_Id );
CREATE ROP REF_ID R616 FROM MC 	ACT_REL 	( Other_Side_Variable_Name , Other_Side_Block_Id )
		         TO 1  	V_VAR 	( Variable_Name , Block_Id );
CREATE ROP REF_ID R620 FROM MC 	ACT_UNR 	( One_Side_Variable_Name , One_Side_Block_Id )
		         TO 1  	V_VAR 	( Variable_Name , Block_Id );
CREATE ROP REF_ID R621 FROM MC 	ACT_UNR 	( Other_Side_Variable_Name , Other_Side_Block_Id )
		         TO 1  	V_VAR 	( Variable_Name , Block_Id );
CREATE ROP REF_ID R622 FROM MC 	ACT_URU 	( One_Side_Variable_Name , One_Side_Block_Id )
		         TO 1  	V_VAR 	( Variable_Name , Block_Id );
CREATE ROP REF_ID R623 FROM MC 	ACT_URU 	( Other_Side_Variable_Name , Other_Side_Block_Id )
		         TO 1  	V_VAR 	( Variable_Name , Block_Id );
CREATE ROP REF_ID R624 FROM MC 	ACT_URU 	( Associative_Variable_Name , Associative_Block_Id )
		         TO 1  	V_VAR 	( Variable_Name , Block_Id );
CREATE ROP REF_ID R629 FROM MC 	ACT_AI 	( Instance_Variable_Name , Instance_Block_Id )
		         TO 1  	V_VAR 	( Variable_Name , Block_Id );
CREATE ROP REF_ID R636 FROM 1C 	ACT_AT 	( Target_Variable_Name , Block_Id )
		         TO 1  	V_VAR 	( Variable_Name , Block_Id );
CREATE ROP REF_ID R604 FROM 1C 	ACT_LNK 	( Next_Link_Id )  PHRASE 'precedes'
		         TO 1C  ACT_LNK 	( Link_Id )  PHRASE 'succeeds';
CREATE ROP REF_ID R613 FROM MC 	ACT_SEL 	( Start_Variable_Name , Start_Block_Id )
		         TO 1  	V_VAR 	( Variable_Name , Block_Id );
CREATE ROP REF_ID R630 FROM 1C 	ACT_AI 	( Member_Variable_Name , Member_Block_Id )
		         TO 1  	V_VAR 	( Variable_Name , Block_Id );
CREATE ROP REF_ID R608 FROM 1C 	ACT_WHL 	( Block_Id )
		         TO 1  	ACT_BLK 	( Block_Id );
CREATE ROP REF_ID R653 FROM MC 	ACT_REL 	( Numb )
		         TO 1  	R_REL 	( Numb );
CREATE ROP REF_ID R655 FROM MC 	ACT_UNR 	( Numb )
		         TO 1  	R_REL 	( Numb );
CREATE ROP REF_ID R656 FROM MC 	ACT_URU 	( Numb )
		         TO 1  	R_REL 	( Numb );
CREATE ROP REF_ID R654 FROM MC 	ACT_RU 	( Numb )
		         TO 1  	R_REL 	( Numb );
CREATE ROP REF_ID R681 FROM MC 	ACT_LNK 	( Numb )
		         TO 1  	R_REL 	( Numb );
CREATE ROP REF_ID R637 FROM M 	ACT_LNK 	( Statement_Id )
		         TO 1  	ACT_SEL 	( Statement_Id );
CREATE ROP REF_ID R628 FROM MC 	V_PAR 	( Statement_Id )
		         TO 1C  ACT_BRG 	( Statement_Id );
CREATE ROP REF_ID R627 FROM MC 	V_PAR 	( Statement_Id )
		         TO 1C  ACT_TFM 	( Statement_Id );
CREATE ROP REF_ID R826 FROM M 	V_VAL 	( Block_Id )
		         TO 1  	ACT_BLK 	( Block_Id );
CREATE ROP REF_ID R834 FROM MC 	V_EPR 	( Value_ID )
		     TO 1  	V_EDV 	( Value_ID );
CREATE ROP REF_ID R846	FROM MC V_EPR	(SM_ID, SMedi_ID)
			  TO 1  SM_EVTDI	(SM_ID, SMedi_ID);
CREATE ROP REF_ID R626 FROM 1C 	ACT_WHL 	( Value_Id )
		         TO 1  	V_VAL 	( Value_Id );
CREATE ROP REF_ID R625 FROM 1C 	ACT_IF 	( Value_Id )
		         TO 1  	V_VAL 	( Value_Id );
CREATE ROP REF_ID R609 FROM 1C 	ACT_AI 	( Value_Id )
		         TO 1  	V_VAL 	( Value_Id );
CREATE ROP REF_ID R635 FROM 1C 	ACT_AT 	( Value_Id )
		         TO 1  	V_VAL 	( Value_Id );
CREATE ROP REF_ID R611 FROM 1C 	ACT_SRW 	( Where_clause_Value_Id )
		         TO 1  	V_VAL 	( Value_Id );
CREATE ROP REF_ID R610 FROM 1C 	ACT_FIW 	( Where_clause_Value_Id )
		         TO 1  	V_VAL 	( Value_Id );
CREATE ROP REF_ID R657 FROM 1C 	ACT_SMT 	( Controlled_Block )
		         TO 1C  ACT_BLK 	( Block_Id );
CREATE ROP REF_ID R658 FROM 1C 	ACT_EL 	( Block_Id )
		         TO 1  	ACT_BLK 	( Block_Id );
CREATE ROP REF_ID R659 FROM 1C 	ACT_EL 	( Value_Id )
		         TO 1  	V_VAL 	( Value_Id );
CREATE ROP REF_ID R660 FROM MC 	V_VAR 	( Block_Id )
		         TO 1  	ACT_BLK 	( Block_Id );
CREATE ROP REF_ID R661 FROM 1C 	ACT_SMT 	( Previous_Statement_Id , Block_Id )  PHRASE 'precedes'
		         TO 1C  ACT_SMT 	( Statement_Id , Block_Id )  PHRASE 'succeeds';
CREATE ROP REF_ID R664 FROM 1C 	ACT_SRW 	( Statement_Id )
		     TO 1  	ACT_SEL 	( Statement_Id );
CREATE ROP REF_ID R664 FROM 1C 	ACT_SR 	( Statement_Id )
		     TO 1  	ACT_SEL 	( Statement_Id );
CREATE ROP REF_ID R665 FROM 1C 	ACT_FIW 	( Variable_Name , Block_Id )
		         TO 1  	V_VAR 	( Variable_Name , Block_Id );
CREATE ROP REF_ID R666 FROM 1C 	ACT_ACT 	( Block_Id )
		         TO 1C  ACT_BLK 	( Block_Id );
CREATE ROP REF_ID R667 FROM MC 	ACT_TFM 	( Variable_Name , Block_Id )
		         TO 1C  V_VAR 	( Variable_Name , Block_Id );
CREATE ROP REF_ID R668 FROM 1C 	ACT_RET 	( Value_Id )
		         TO 1C  V_VAL 	( Value_Id );
CREATE ROP REF_ID R669 FROM MC 	V_PAR 	( Statement_Id )
		         TO 1C  ACT_FNC 	( Statement_Id );
CREATE ROP REF_ID R682 FROM MC 	ACT_EL 	( If_Statement_Id )
		         TO 1  ACT_IF 	( Statement_Id );
CREATE ROP REF_ID R683 FROM 1C 	ACT_E 	( If_Statement_Id )
		         TO 1  ACT_IF 	( Statement_Id );		         
CREATE ROP REF_ID R814 FROM 1C 	V_SLF 	( Variable_Name , Block_Id )
		     TO 1  	V_VAR 	( Variable_Name , Block_Id );
CREATE ROP REF_ID R814 FROM 1C 	V_INT 	( Variable_Name , Block_Id )
		     TO 1  	V_VAR 	( Variable_Name , Block_Id );
CREATE ROP REF_ID R814 FROM 1C 	V_INS 	( Variable_Name , Block_Id )
		     TO 1  	V_VAR 	( Variable_Name , Block_Id );
CREATE ROP REF_ID R814 FROM 1C 	V_TRN 	( Variable_Name , Block_Id )
		     TO 1  	V_VAR 	( Variable_Name , Block_Id );
CREATE ROP REF_ID R815 FROM MC 	V_INT 	( Key_Lett )
		         TO 1  	O_OBJ 	( Key_Lett );
CREATE ROP REF_ID R813 FROM MC 	V_INS 	( Key_Lett )
		         TO 1  	O_OBJ 	( Key_Lett );
CREATE ROP REF_ID R801 FROM 1C 	V_BIN 	( Value_Id )
		     TO 1  	V_VAL 	( Value_Id );
CREATE ROP REF_ID R801 FROM 1C 	V_LBO 	( Value_Id )
		     TO 1  	V_VAL 	( Value_Id );
CREATE ROP REF_ID R801 FROM 1C 	V_LRL 	( Value_Id )
		     TO 1  	V_VAL 	( Value_Id );
CREATE ROP REF_ID R801 FROM 1C 	V_LEN 	( Value_Id )
		     TO 1  	V_VAL 	( Value_Id );
CREATE ROP REF_ID R801 FROM 1C 	V_TVL 	( Value_Id )
		     TO 1  	V_VAL 	( Value_Id );
CREATE ROP REF_ID R801 FROM 1C 	V_EDV 	( Value_Id )
		     TO 1  	V_VAL 	( Value_Id );
CREATE ROP REF_ID R801 FROM 1C 	V_ISR 	( Value_Id )
		     TO 1  	V_VAL 	( Value_Id );
CREATE ROP REF_ID R801 FROM 1C 	V_TRV 	( Value_Id )
		     TO 1  	V_VAL 	( Value_Id );
CREATE ROP REF_ID R801 FROM 1C 	V_UNY 	( Value_Id )
		     TO 1  	V_VAL 	( Value_Id );
CREATE ROP REF_ID R801 FROM 1C 	V_PEX 	( Value_Id )
		     TO 1  	V_VAL 	( Value_Id );
CREATE ROP REF_ID R801 FROM 1C 	V_LST 	( Value_Id )
		     TO 1  	V_VAL 	( Value_Id );
CREATE ROP REF_ID R801 FROM 1C 	V_LIN 	( Value_Id )
		     TO 1  	V_VAL 	( Value_Id );
CREATE ROP REF_ID R801 FROM 1C 	V_AVL 	( Value_Id )
		     TO 1  	V_VAL 	( Value_Id );
CREATE ROP REF_ID R801 FROM 1C 	V_IRF 	( Value_Id )
		     TO 1  	V_VAL 	( Value_Id );
CREATE ROP REF_ID R801 FROM 1C 	V_BRV 	( Value_Id )
		     TO 1  	V_VAL 	( Value_Id );
CREATE ROP REF_ID R801 FROM 1C 	V_SLR 	( Value_Id )
		     TO 1  	V_VAL 	( Value_Id );
CREATE ROP REF_ID R801 FROM 1C 	V_PVL 	( Value_Id )
		     TO 1  	V_VAL 	( Value_Id );
CREATE ROP REF_ID R801 FROM 1C 	V_FNC 	( Value_Id )
		     TO 1  	V_VAL 	( Value_Id );
CREATE ROP REF_ID R802 FROM 1C 	V_BIN 	( Left_Value_Id )
		         TO 1  	V_VAL 	( Value_Id );
CREATE ROP REF_ID R803 FROM 1C 	V_BIN 	( Right_Value_Id )
		         TO 1  	V_VAL 	( Value_Id );
CREATE ROP REF_ID R804 FROM 1C 	V_UNY 	( Operand_Value_Id )
		         TO 1  	V_VAL 	( Value_Id );
CREATE ROP REF_ID R805 FROM MC 	V_TVL 	( Variable_Name , Block_Id )
		         TO 1  	V_VAR 	( Variable_Name , Block_Id );
CREATE ROP REF_ID R807 FROM MC 	V_AVL 	( Instance_Variable_Name , Instance_Block_Id )
		         TO 1  	V_VAR 	( Variable_Name , Block_Id );
CREATE ROP REF_ID R808 FROM MC 	V_IRF 	( Variable_Name , Block_Id )
		         TO 1  	V_VAR 	( Variable_Name , Block_Id );
CREATE ROP REF_ID R809 FROM 1C 	V_ISR 	( Variable_Name , Block_Id )
		         TO 1  	V_VAR 	( Variable_Name , Block_Id );
CREATE ROP REF_ID R806 FROM 1C 	V_AVL 	( Member_Variable_Name , Member_Block_Id )
		         TO 1  	V_VAR 	( Variable_Name , Block_Id );
CREATE ROP REF_ID R800 FROM 1C 	V_PAR 	( Value_Id )
		         TO 1  	V_VAL 	( Value_Id );
CREATE ROP REF_ID R810 FROM 1C 	V_BRV 	( Statement_Id )
		         TO 1  	ACT_BRG 	( Statement_Id );
CREATE ROP REF_ID R811 FROM 1C 	V_TRV 	( Statement_Id )
		         TO 1  	ACT_TFM 	( Statement_Id );
CREATE ROP REF_ID R812 FROM 1C 	V_SLR 	( Variable_Name , Block_Id )
		         TO 1  	V_VAR 	( Variable_Name , Block_Id );
CREATE ROP REF_ID R816 FROM 1C 	V_PAR 	( Previous_Statement_Id , Previous_Value_Id )  PHRASE 'precedes'
		         TO 1C  V_PAR 	( Statement_Id , Value_Id )  PHRASE 'succeeds';
CREATE ROP REF_ID R817 FROM 1C 	V_FNC 	( Statement_Id )
		         TO 1  	ACT_FNC 	( Statement_Id );
CREATE ROP REF_ID R818 FROM MC 	V_INT 	( Key_Lett )
		         TO 1  	O_OBJ 	( Key_Lett );
CREATE ROP REF_ID R819 FROM MC 	V_INS 	( Key_Lett )
		         TO 1  	O_OBJ 	( Key_Lett );
CREATE ROP REF_ID R824 FROM 1C 	V_PEX 	( Par_Value_Id )
		     TO 1  	V_VAL 	( Value_Id );
CREATE ROP REF_ID R700 FROM 1C 	E_CEI 	( Statement_Id )
		     TO 1  	ACT_SMT 	( Statement_Id );
CREATE ROP REF_ID R700 FROM 1C 	E_GEN 	( Statement_Id )
		     TO 1  	ACT_SMT 	( Statement_Id );
CREATE ROP REF_ID R700 FROM 1C 	E_GPR 	( Statement_Id )
		     TO 1  	ACT_SMT 	( Statement_Id );
CREATE ROP REF_ID R700 FROM 1C 	E_CEA 	( Statement_Id )
		     TO 1  	ACT_SMT 	( Statement_Id );
CREATE ROP REF_ID R700 FROM 1C 	E_GAR 	( Statement_Id )
		     TO 1  	ACT_SMT 	( Statement_Id );
CREATE ROP REF_ID R700 FROM 1C 	E_CEE 	( Statement_Id )
		     TO 1  	ACT_SMT 	( Statement_Id );
CREATE ROP REF_ID R700 FROM 1C 	E_GEE 	( Statement_Id )
		     TO 1  	ACT_SMT 	( Statement_Id );
CREATE ROP REF_ID R700 FROM 1C 	E_CEC 	( Statement_Id )
		     TO 1  	ACT_SMT 	( Statement_Id );
CREATE ROP REF_ID R700 FROM 1C 	E_GEC 	( Statement_Id )
		     TO 1  	ACT_SMT 	( Statement_Id );
CREATE ROP REF_ID R706 FROM MC 	E_CEI 	( Variable_Name , Variable_Block_Id )
		         TO 1  	V_VAR 	( Variable_Name , Block_Id );
CREATE ROP REF_ID R707 FROM 1C 	E_CEA 	( Variable_Name , Block_Id )
		         TO 1  	V_VAR 	( Variable_Name , Block_Id );
CREATE ROP REF_ID R708 FROM 1C 	E_CEE 	( Variable_Name , Block_Id )
		         TO 1  	V_VAR 	( Variable_Name , Block_Id );
CREATE ROP REF_ID R712 FROM MC 	E_GAR 	( Key_Lett )
		         TO 1  	O_OBJ 	( Key_Lett );
CREATE ROP REF_ID R711 FROM MC 	E_GEN 	( Variable_Name , Block_Id )
		         TO 1  	V_VAR 	( Variable_Name , Block_Id );
CREATE ROP REF_ID R709 FROM 1C 	E_GPR 	( Variable_Name , Block_Id )
		         TO 1  	V_VAR 	( Variable_Name , Block_Id );
CREATE ROP REF_ID R715 FROM MC 	E_GEE 	( Key_Lett )
		         TO 1  	S_EE 	( Key_Lett );
CREATE ROP REF_ID R718 FROM MC 	V_PAR 	( Statement_Id )
		         TO 1C  E_CEI 	( Statement_Id );
CREATE ROP REF_ID R701 FROM MC 	V_PAR 	( Statement_Id )
		         TO 1C  E_GEN 	( Statement_Id );
CREATE ROP REF_ID R703 FROM MC 	V_PAR 	( Statement_Id )
		         TO 1C  E_GAR 	( Statement_Id );
CREATE ROP REF_ID R702 FROM MC 	V_PAR 	( Statement_Id )
		         TO 1  	E_CEA 	( Statement_Id );
CREATE ROP REF_ID R704 FROM MC 	V_PAR 	( Statement_Id )
		         TO 1  	E_CEE 	( Statement_Id );
CREATE ROP REF_ID R705 FROM MC 	V_PAR 	( Statement_Id )
		         TO 1C  E_GEE 	( Statement_Id );
CREATE ROP REF_ID R722 FROM MC 	V_PAR 	( Statement_Id )
		         TO 1C  E_CEC 	( Statement_Id );
CREATE ROP REF_ID R723 FROM MC 	V_PAR 	( Statement_Id )
		         TO 1C  E_GEC 	( Statement_Id );
CREATE ROP REF_ID R724 FROM MC 	E_GEC 	( Key_Lett )
		         TO 1  	O_OBJ 	( Key_Lett );
CREATE ROP REF_ID R725 FROM MC 	E_CEI 	( Recipient_Variable_Name , Recipient_Block_Id )
		         TO 1  	V_VAR 	( Variable_Name , Block_Id );
CREATE ROP REF_ID R726 FROM MC 	E_CEA 	( Key_Lett )
		         TO 1  	O_OBJ 	( Key_Lett );
CREATE ROP REF_ID R727 FROM MC 	E_CEE 	( Key_Lett )
		         TO 1  	S_EE 	( Key_Lett );
CREATE ROP REF_ID R728 FROM MC 	E_CEC 	( Key_Lett )
		         TO 1  	O_OBJ 	( Key_Lett );
CREATE ROP REF_ID R729 FROM 1  	E_CEC	( Variable_Name , Block_Id )
		         TO 1  	V_VAR 	( Variable_Name , Block_Id );
CREATE ROP REF_ID R300 FROM MC 	PL_EEPID 	( Dom_ID )
		         TO 1  	S_DOM 	( Dom_ID );
CREATE ROP REF_ID R300 FROM 1  	PL_EEPID 	( EEPack_ID )
		         TO 1  	S_EEPK 	( EEPack_ID );
CREATE ROP REF_ID R301 FROM 1  	PL_FPID 	( FunPack_ID )
		         TO 1  	S_FPK 	( FunPack_ID );
CREATE ROP REF_ID R301 FROM MC 	PL_FPID 	( Dom_ID )
		         TO 1  	S_DOM 	( Dom_ID );
CREATE ROP REF_ID R941 FROM MC 	SQ_TS 	( Prev_Mark_ID )
		         TO 1  	SQ_TM 	( Mark_ID );
CREATE ROP REF_ID R931 FROM MC 	SQ_TM 	( Part_ID )
		         TO 1  	SQ_LS 	( Part_ID );
CREATE ROP REF_ID R942 FROM MC 	SQ_TS 	( Mark_ID )
		         TO 1  	SQ_TM 	( Mark_ID );
CREATE ROP REF_ID R930 FROM 1C 	SQ_CIP 	( Part_ID )
		     TO 1  	SQ_P 	( Part_ID );
CREATE ROP REF_ID R930 FROM 1C 	SQ_EEP 	( Part_ID )
		     TO 1  	SQ_P 	( Part_ID );
CREATE ROP REF_ID R930 FROM 1C 	SQ_FPP 	( Part_ID )
		     TO 1  	SQ_P 	( Part_ID );
CREATE ROP REF_ID R930 FROM 1C 	SQ_CP 	( Part_ID )
		     TO 1  	SQ_P 	( Part_ID );
CREATE ROP REF_ID R930 FROM 1C 	SQ_AP 	( Part_ID )
		     TO 1  	SQ_P 	( Part_ID );
CREATE ROP REF_ID R930 FROM 1C 	IA_UCP 	( Part_ID )
		     TO 1  	SQ_P 	( Part_ID );
CREATE ROP REF_ID R930 FROM 1C 	SQ_LS 	( Part_ID )
		     TO 1  	SQ_P 	( Part_ID );
CREATE ROP REF_ID R932 FROM MC 	SQ_FPP 	( FunPack_ID )
		         TO 1C  S_FPK 	( FunPack_ID );
CREATE ROP REF_ID R933 FROM MC 	SQ_EEP 	( EE_ID )
		         TO 1C  S_EE 	( EE_ID );
CREATE ROP REF_ID R934 FROM MC 	SQ_CIP 	( Obj_ID )
		         TO 1C  O_OBJ 	( Obj_ID );
CREATE ROP REF_ID R935 FROM MC 	SQ_CPA 	( Part_ID )
		         TO 1  	SQ_CP 	( Part_ID );
CREATE ROP REF_ID R936 FROM MC 	SQ_AV 	( Informal_Part_ID )
		         TO 1C  SQ_CIP 	( Part_ID );
CREATE ROP REF_ID R937 FROM MC 	SQ_AV 	( Formal_Part_ID )
		         TO 1C  SQ_CIP 	( Part_ID );
CREATE ROP REF_ID R938 FROM MC 	SQ_AV 	( Obj_ID , Attr_ID )
		         TO 1C  O_ATTR 	( Obj_ID , Attr_ID );
CREATE ROP REF_ID R939 FROM MC 	SQ_CP 	( Obj_ID )
		         TO 1C  O_OBJ 	( Obj_ID );
CREATE ROP REF_ID R940 FROM 1C 	SQ_LS 	( ClassInst_Part_ID )
		         TO 1C  SQ_CIP 	( Part_ID );
CREATE ROP REF_ID R947 FROM 1C 	SQ_IA 	( Ia_ID )
		     TO 1  	SQ_CPA 	( Ia_ID );
CREATE ROP REF_ID R947 FROM 1C 	SQ_FA 	( Ia_ID )
		     TO 1  	SQ_CPA 	( Ia_ID );
CREATE ROP REF_ID R948 FROM 1C 	SQ_IAV 	( Av_ID )
		     TO 1  	SQ_AV 	( Av_ID );
CREATE ROP REF_ID R948 FROM 1C 	SQ_FAV 	( Av_ID )
		     TO 1  	SQ_AV 	( Av_ID );
CREATE ROP REF_ID R949 FROM 1C 	SQ_AP 	( LS_Part_ID )
		         TO 1C  SQ_LS 	( Part_ID );
CREATE ROP REF_ID R1000 FROM MC 	MSG_A 	( Informal_Msg_ID )
		         TO 1  	MSG_M 	( Msg_ID );
CREATE ROP REF_ID R1001 FROM MC 	MSG_A 	( Formal_Msg_ID )
		         TO 1  	MSG_M 	( Msg_ID );
CREATE ROP REF_ID R1007 FROM MC 	MSG_M 	( Sender_Part_ID )
		         TO 1C  SQ_P 	( Part_ID );
CREATE ROP REF_ID R1008 FROM MC 	MSG_M 	( Receiver_Part_ID )
		         TO 1C  SQ_P 	( Part_ID );
CREATE ROP REF_ID R1009 FROM MC 	MSG_E 	( SMevt_ID )
		         TO 1C  SM_EVT 	( SMevt_ID );
CREATE ROP REF_ID R1010 FROM MC 	MSG_F 	( Sync_ID )
		         TO 1C  S_SYNC 	( Sync_ID );
CREATE ROP REF_ID R1011 FROM MC 	MSG_O 	( Tfr_ID )
		         TO 1C  O_TFR 	( Tfr_ID );
CREATE ROP REF_ID R1012 FROM MC 	MSG_B 	( Brg_ID )
		         TO 1C  S_BRG 	( Brg_ID );
CREATE ROP REF_ID R1013 FROM 1C 	MSG_BA 	( Arg_ID )
		     TO 1  	MSG_A 	( Arg_ID );
CREATE ROP REF_ID R1013 FROM 1C 	MSG_OA 	( Arg_ID )
		     TO 1  	MSG_A 	( Arg_ID );
CREATE ROP REF_ID R1013 FROM 1C 	MSG_FA 	( Arg_ID )
		     TO 1  	MSG_A 	( Arg_ID );
CREATE ROP REF_ID R1013 FROM 1C 	MSG_EA 	( Arg_ID )
		     TO 1  	MSG_A 	( Arg_ID );
CREATE ROP REF_ID R1013 FROM 1C 	MSG_IA 	( Arg_ID )
		     TO 1  	MSG_A 	( Arg_ID );
CREATE ROP REF_ID R1014 FROM MC 	MSG_BA 	( BParm_ID )
		         TO 1C  S_BPARM 	( BParm_ID );
CREATE ROP REF_ID R1015 FROM MC 	MSG_OA 	( TParm_ID )
		         TO 1C  O_TPARM 	( TParm_ID );
CREATE ROP REF_ID R1016 FROM MC 	MSG_FA 	( SParm_ID )
		         TO 1C  S_SPARM 	( SParm_ID );
CREATE ROP REF_ID R1017 FROM MC 	MSG_EA 	( SM_ID , SMedi_ID )
		         TO 1C  SM_EVTDI 	( SM_ID , SMedi_ID );
CREATE ROP REF_ID R1018 FROM 1C 	MSG_AM 	( Msg_ID )
		     TO 1  	MSG_M 	( Msg_ID );
CREATE ROP REF_ID R1018 FROM 1C 	MSG_SM 	( Msg_ID )
		     TO 1  	MSG_M 	( Msg_ID );
CREATE ROP REF_ID R1018 FROM 1C 	MSG_R 	( Msg_ID )
		     TO 1  	MSG_M 	( Msg_ID );
CREATE ROP REF_ID R1019 FROM 1C 	MSG_E 	( Msg_ID )
		     TO 1  	MSG_AM 	( Msg_ID );
CREATE ROP REF_ID R1019 FROM 1C 	MSG_IAM 	( Msg_ID )
		     TO 1  	MSG_AM 	( Msg_ID );
CREATE ROP REF_ID R1020 FROM 1C 	MSG_F 	( Msg_ID )
		     TO 1  	MSG_SM 	( Msg_ID );
CREATE ROP REF_ID R1020 FROM 1C 	MSG_O 	( Msg_ID )
		     TO 1  	MSG_SM 	( Msg_ID );
CREATE ROP REF_ID R1020 FROM 1C 	MSG_B 	( Msg_ID )
		     TO 1  	MSG_SM 	( Msg_ID );
CREATE ROP REF_ID R1020 FROM 1C 	MSG_ISM 	( Msg_ID )
		     TO 1  	MSG_SM 	( Msg_ID );
CREATE ROP REF_ID R1129 FROM 1  	COMM_COMM	( CIC_Comm_ID )
		         TO 1C  COMM_CIC 	( Comm_ID );
CREATE ROP REF_ID R1130 FROM MC 	COMM_CIC 	( Comm_ID )
		         TO 1  	COMM_COMM 	( Comm_ID );
CREATE ROP REF_ID R1132 FROM MC 	COMM_COMM 	( Dom_ID )
		         TO 1C  S_DOM 	( Dom_ID );
CREATE ROP REF_ID R1126 FROM MC 	COMM_PIC 	( Comm_ID )
		         TO 1  	COMM_COMM 	( Comm_ID );
CREATE ROP REF_ID R1126 FROM 1C 	COMM_PIC 	( Part_ID )
		         TO 1  	SQ_P 	( Part_ID );
CREATE ROP REF_ID R1131 FROM MC 	COMM_COMM 	( SS_ID )
		         TO 1C  S_SS 	( SS_ID );
CREATE ROP REF_ID R1128 FROM MC 	COMM_LNK 	( Rel_ID )
		         TO 1C  R_REL 	( Rel_ID );
CREATE ROP REF_ID R1133 FROM MC 	COMM_LNK 	( Start_Part_ID )
		         TO 1  	SQ_P 	( Part_ID );
CREATE ROP REF_ID R1134 FROM MC 	COMM_LNK 	( Destination_Part_ID )
		         TO 1  	SQ_P 	( Part_ID );
CREATE ROP REF_ID R1135 FROM MC 	COMM_MIC 	( Comm_ID )
		         TO 1  	COMM_COMM 	( Comm_ID );
CREATE ROP REF_ID R1135 FROM 1C 	COMM_MIC 	( Msg_ID )
		         TO 1  	MSG_M 	( Msg_ID );
CREATE ROP REF_ID R913 FROM MC 	SQ_S 	( Dom_ID )
		         TO 1C  S_DOM 	( Dom_ID );
CREATE ROP REF_ID R914 FROM MC 	SQ_S 	( SS_ID )
		         TO 1C  S_SS 	( SS_ID );
CREATE ROP REF_ID R928 FROM 1  	SQ_S	( Prev_Seq_ID )
		         TO 1C  SQ_SIS 	( Seq_ID );
CREATE ROP REF_ID R911 FROM MC 	SQ_SIS 	( Seq_ID )
		         TO 1  	SQ_S 	( Seq_ID );
CREATE ROP REF_ID R929 FROM MC 	SQ_P 	( Seq_ID )
		         TO 1C  SQ_S 	( Seq_ID );
CREATE ROP REF_ID R1201 FROM MC 	UC_UCC 	( Dom_ID )
		         TO 1C  S_DOM 	( Dom_ID );
CREATE ROP REF_ID R1202 FROM MC 	UC_UCC 	( SS_ID )
		         TO 1C  S_SS 	( SS_ID );
CREATE ROP REF_ID R1208 FROM MC 	UC_UIU 	( UCC_ID )
		         TO 1  	UC_UCC 	( UCC_ID );
CREATE ROP REF_ID R1209 FROM 1  	UC_UCC	( UCCinUCC_ID )
		         TO 1C  UC_UIU 	( UCC_ID );
CREATE ROP REF_ID R1210 FROM 1C 	UC_E 	( Assoc_ID )
		     TO 1  	UC_UCA 	( Assoc_ID );
CREATE ROP REF_ID R1210 FROM 1C 	UC_G 	( Assoc_ID )
		     TO 1  	UC_UCA 	( Assoc_ID );
CREATE ROP REF_ID R1210 FROM 1C 	UC_I 	( Assoc_ID )
		     TO 1  	UC_UCA 	( Assoc_ID );
CREATE ROP REF_ID R1210 FROM 1C 	UC_BA 	( Assoc_ID )
		     TO 1  	UC_UCA 	( Assoc_ID );
CREATE ROP REF_ID R1203 FROM MC 	UC_PIUC 	( UCC_ID )
		         TO 1  	UC_UCC 	( UCC_ID );
CREATE ROP REF_ID R1203 FROM 1C 	UC_PIUC 	( Part_ID )
		         TO 1  	SQ_P 	( Part_ID );
CREATE ROP REF_ID R1206 FROM MC 	UC_UCA 	( Source_Part_ID )
		         TO 1  	SQ_P 	( Part_ID );
CREATE ROP REF_ID R1207 FROM MC 	UC_UCA 	( Destination_Part_ID )
		         TO 1  	SQ_P 	( Part_ID );
CREATE ROP REF_ID R1100 FROM MC 	A_A 	( Dom_ID )
		         TO 1C  S_DOM 	( Dom_ID );
CREATE ROP REF_ID R1108 FROM MC 	A_A 	( SS_ID )
		         TO 1C  S_SS 	( SS_ID );
CREATE ROP REF_ID R1109 FROM 1C 	A_AIA 	( Id )
		         TO 1  	A_A 	( Id );
CREATE ROP REF_ID R1110 FROM MC 	A_A 	( Parent_Id )
		         TO 1C  A_AIA 	( Id );
CREATE ROP REF_ID R1111 FROM MC 	A_AP 	( ActivityId )
		         TO 1  	A_A 	( Id );
CREATE ROP REF_ID R1102 FROM MC 	A_E 	( ActivityId )
		         TO 1  	A_A 	( Id );
CREATE ROP REF_ID R1101 FROM MC 	A_N 	( ActivityId )
		         TO 1  	A_A 	( Id );
CREATE ROP REF_ID R1103 FROM MC 	A_E 	( TargetId )
		         TO 1  	A_N 	( Id );
CREATE ROP REF_ID R1104 FROM MC 	A_E 	( SourceId )
		         TO 1  	A_N 	( Id );
CREATE ROP REF_ID R1105 FROM 1C 	A_ACT 	( Id )
		     TO 1  	A_N 	( Id );
CREATE ROP REF_ID R1105 FROM 1C 	A_OBJ 	( Id )
		     TO 1  	A_N 	( Id );
CREATE ROP REF_ID R1105 FROM 1C 	A_CTL 	( Id )
		     TO 1  	A_N 	( Id );
CREATE ROP REF_ID R1107 FROM 1C 	A_AE 	( Id )
		     TO 1  	A_ACT 	( Id );
CREATE ROP REF_ID R1107 FROM 1C 	A_GA 	( Id )
		     TO 1  	A_ACT 	( Id );
CREATE ROP REF_ID R1107 FROM 1C 	A_SS 	( Id )
		     TO 1  	A_ACT 	( Id );
CREATE ROP REF_ID R1106 FROM 1C 	A_FF 	( Id )
		     TO 1  	A_CTL 	( Id );
CREATE ROP REF_ID R1106 FROM 1C 	A_AF 	( Id )
		     TO 1  	A_CTL 	( Id );
CREATE ROP REF_ID R1106 FROM 1C 	A_INI 	( Id )
		     TO 1  	A_CTL 	( Id );
CREATE ROP REF_ID R1106 FROM 1C 	A_DM 	( Id )
		     TO 1  	A_CTL 	( Id );
CREATE ROP REF_ID R1106 FROM 1C 	A_FJ 	( Id )
		     TO 1  	A_CTL 	( Id );
CREATE ROP REF_ID R1112 FROM 1C 	A_ATE 	( Id )
		     TO 1  	A_AE 	( Id );
CREATE ROP REF_ID R1112 FROM 1C 	A_AEA 	( Id )
		     TO 1  	A_AE 	( Id );


-- ============================================================================
-- Pre-instantiated table inserts
-- ============================================================================

CREATE TABLE O_BATTR_PROXY (
	Attr_ID	UNIQUE_ID,
	Obj_ID	UNIQUE_ID,
	ContentPath STRING );

CREATE TABLE O_ID_PROXY (
	Oid_ID	INTEGER,
	Obj_ID	UNIQUE_ID,
	ContentPath STRING );

CREATE TABLE O_OIDA_PROXY (
	Attr_ID	UNIQUE_ID,
	Obj_ID	UNIQUE_ID,
	Oid_ID	INTEGER,
	ContentPath STRING );

CREATE TABLE O_OBJ_PROXY (
	Obj_ID	UNIQUE_ID,
	Name	STRING,
	Numb	INTEGER,
	Key_Lett	STRING,
	Descrip	STRING,
	SS_ID	UNIQUE_ID,
	AdapterName STRING,
	Order INTEGER,
	ContentPath STRING );

CREATE TABLE O_RTIDA_PROXY (
	Attr_ID	UNIQUE_ID,
	Obj_ID	UNIQUE_ID,
	Oid_ID	INTEGER,
	Rel_ID	UNIQUE_ID,
	OIR_ID	UNIQUE_ID,
	ContentPath STRING );

CREATE TABLE R_RTO_PROXY (
	Obj_ID	UNIQUE_ID,
	Rel_ID	UNIQUE_ID,
	OIR_ID	UNIQUE_ID,
	Oid_ID	INTEGER,
	ContentPath STRING );

CREATE TABLE R_RGO_PROXY (
	Obj_ID	UNIQUE_ID,
	Rel_ID	UNIQUE_ID,
	OIR_ID	UNIQUE_ID,
	ContentPath STRING );

CREATE TABLE S_DPK_PROXY (
	     DatPack_ID	UNIQUE_ID,
	     Name	STRING,
	     Dom_ID	UNIQUE_ID,
	     Parent_DatPack_ID	UNIQUE_ID,
	ContentPath STRING );

CREATE TABLE S_DT_PROXY (
	DT_ID	UNIQUE_ID,
	Dom_ID	UNIQUE_ID,
	Name	STRING,
	Descrip	STRING,
	ContentPath STRING );

CREATE TABLE S_EEPK_PROXY (
	     EEPack_ID	UNIQUE_ID,
	     Name	STRING,
	     Dom_ID	UNIQUE_ID,
	     Parent_EEPack_ID	UNIQUE_ID,
	ContentPath STRING );

CREATE TABLE S_FPK_PROXY (
	     FunPack_ID	UNIQUE_ID,
	     Name	STRING,
	     Dom_ID	UNIQUE_ID,
	     Parent_FunPack_ID	UNIQUE_ID,
	ContentPath STRING );

CREATE TABLE S_SS_PROXY (
	SS_ID	UNIQUE_ID,
	Name	STRING,
	Descrip	STRING,
	Prefix	STRING,
	Num_Rng	INTEGER,
	Dom_ID	UNIQUE_ID,
	Config_ID	UNIQUE_ID,
	ContentPath STRING );

CREATE TABLE SM_ISM_PROXY (
	SM_ID	UNIQUE_ID,
	Obj_ID	UNIQUE_ID,
	ContentPath STRING );
	     
