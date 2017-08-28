CREATE TABLE ACT_ACT (
    Action_ID UNIQUE_ID,
    Type STRING,
    LoopLevel INTEGER,
    Block_ID UNIQUE_ID,
    CurrentScope_ID UNIQUE_ID,
    return_value INTEGER,
    Label STRING,
    Parsed_Block_ID UNIQUE_ID );
CREATE TABLE ACT_AI (
    Statement_ID UNIQUE_ID,
    r_Value_ID UNIQUE_ID,
    l_Value_ID UNIQUE_ID,
    attributeLineNumber INTEGER,
    attributeColumn INTEGER
);
CREATE TABLE ACT_BIC (
    Component_Id UNIQUE_ID,
    Action_ID UNIQUE_ID
);
CREATE TABLE ACT_BIE (
    Element_ID UNIQUE_ID,
    Action_ID UNIQUE_ID
);
CREATE TABLE ACT_BLK (
    Block_ID UNIQUE_ID,
    WhereSpecOK BOOLEAN,
    InWhereSpec BOOLEAN,
    SelectedFound BOOLEAN,
    TempBuffer STRING,
    SupData1 STRING,
    SupData2 STRING,
    CurrentLine INTEGER,
    CurrentCol INTEGER,
    currentKeyLettersLineNumber INTEGER,
    currentKeyLettersColumn INTEGER,
    currentParameterAssignmentNameLineNumber INTEGER,
    currentParameterAssignmentNameColumn INTEGER,
    currentAssociationNumberLineNumber INTEGER,
    currentAssociationNumberColumn INTEGER,
    currentAssociationPhraseLineNumber INTEGER,
    currentAssociationPhraseColumn INTEGER,
    currentDataTypeNameLineNumber INTEGER,
    currentDataTypeNameColumn INTEGER,
    blockInStackFrameCreated BOOLEAN,
    Action_ID UNIQUE_ID,
    Parsed_Action_ID UNIQUE_ID
);
CREATE TABLE ACT_BRB (
    Action_ID UNIQUE_ID,
    Brg_ID UNIQUE_ID
);
CREATE TABLE ACT_BRG (
    Statement_ID UNIQUE_ID,
    Brg_ID UNIQUE_ID,
    bridgeNameLineNumber INTEGER,
    bridgeNameColumn INTEGER,
    externalEntityKeyLettersLineNumber INTEGER,
    externalEntityKeyLettersColumn INTEGER
);
CREATE TABLE ACT_BRK (
    Statement_ID UNIQUE_ID
);
CREATE TABLE ACT_CNV (
    Statement_ID UNIQUE_ID,
    Obj_ID UNIQUE_ID,
    modelClassKeyLettersLineNumber INTEGER,
    modelClassKeyLettersColumn INTEGER
);
CREATE TABLE ACT_CON (
    Statement_ID UNIQUE_ID
);
CREATE TABLE ACT_CR (
    Statement_ID UNIQUE_ID,
    Var_ID UNIQUE_ID,
    is_implicit BOOLEAN,
    Obj_ID UNIQUE_ID,
    modelClassKeyLettersLineNumber INTEGER,
    modelClassKeyLettersColumn INTEGER
);
CREATE TABLE ACT_CTL (
    Statement_ID UNIQUE_ID
);
CREATE TABLE ACT_DAB (
    Action_ID UNIQUE_ID,
    Obj_ID UNIQUE_ID,
    Attr_ID UNIQUE_ID,
    AttributeWritten BOOLEAN
);
CREATE TABLE ACT_DEL (
    Statement_ID UNIQUE_ID,
    Var_ID UNIQUE_ID
);
CREATE TABLE ACT_E (
    Statement_ID UNIQUE_ID,
    Block_ID UNIQUE_ID,
    If_Statement_ID UNIQUE_ID
);
CREATE TABLE ACT_EL (
    Statement_ID UNIQUE_ID,
    Block_ID UNIQUE_ID,
    Value_ID UNIQUE_ID,
    If_Statement_ID UNIQUE_ID
);
CREATE TABLE ACT_FIO (
    Statement_ID UNIQUE_ID,
    Var_ID UNIQUE_ID,
    is_implicit BOOLEAN,
    cardinality STRING,
    Obj_ID UNIQUE_ID,
    extentLineNumber INTEGER,
    extentColumn INTEGER
);
CREATE TABLE ACT_FIW (
    Statement_ID UNIQUE_ID,
    Var_ID UNIQUE_ID,
    is_implicit BOOLEAN,
    cardinality STRING,
    Where_Clause_Value_ID UNIQUE_ID,
    Obj_ID UNIQUE_ID,
    extentLineNumber INTEGER,
    extentColumn INTEGER
);
CREATE TABLE ACT_FNB (
    Action_ID UNIQUE_ID,
    Sync_ID UNIQUE_ID
);
CREATE TABLE ACT_FNC (
    Statement_ID UNIQUE_ID,
    Sync_ID UNIQUE_ID,
    functionNameLineNumber INTEGER,
    functionNameColumn INTEGER
);
CREATE TABLE ACT_FOR (
    Statement_ID UNIQUE_ID,
    Block_ID UNIQUE_ID,
    is_implicit BOOLEAN,
    Loop_Var_ID UNIQUE_ID,
    Set_Var_ID UNIQUE_ID,
    Obj_ID UNIQUE_ID
);
CREATE TABLE ACT_IF (
    Statement_ID UNIQUE_ID,
    Block_ID UNIQUE_ID,
    Value_ID UNIQUE_ID,
    Elif_Statement_ID UNIQUE_ID,
    Else_Statement_ID UNIQUE_ID
);
CREATE TABLE ACT_IOP (
    Statement_ID UNIQUE_ID,
    opNameLineNumber INTEGER,
    opNameColumn INTEGER,
    ownerNameLineNumber INTEGER,
    ownerNameColumn INTEGER,
    ProvidedOp_Id UNIQUE_ID,
    RequiredOp_Id UNIQUE_ID,
    Value_ID UNIQUE_ID
);
CREATE TABLE ACT_LNK (
    Link_ID UNIQUE_ID,
    Rel_Phrase STRING,
    Statement_ID UNIQUE_ID,
    Rel_ID UNIQUE_ID,
    Next_Link_ID UNIQUE_ID,
    Mult INTEGER,
    Obj_ID UNIQUE_ID,
    modelClassKeyLettersLineNumber INTEGER,
    modelClassKeyLettersColumn INTEGER,
    associationNumberLineNumber INTEGER,
    associationNumberColumn INTEGER,
    phraseLineNumber INTEGER,
    phraseColumn INTEGER
);
CREATE TABLE ACT_OPB (
    Action_ID UNIQUE_ID,
    Tfr_ID UNIQUE_ID
);
CREATE TABLE ACT_POB (
    Action_ID UNIQUE_ID,
    Id UNIQUE_ID
);
CREATE TABLE ACT_PSB (
    Action_ID UNIQUE_ID,
    Id UNIQUE_ID
);
CREATE TABLE ACT_REL (
    Statement_ID UNIQUE_ID,
    One_Side_Var_ID UNIQUE_ID,
    Other_Side_Var_ID UNIQUE_ID,
    relationship_phrase STRING,
    Rel_ID UNIQUE_ID,
    associationNumberLineNumber INTEGER,
    associationNumberColumn INTEGER,
    associationPhraseLineNumber INTEGER,
    associationPhraseColumn INTEGER
);
CREATE TABLE ACT_RET (
    Statement_ID UNIQUE_ID,
    Value_ID UNIQUE_ID
);
CREATE TABLE ACT_ROB (
    Action_ID UNIQUE_ID,
    Id UNIQUE_ID
);
CREATE TABLE ACT_RSB (
    Action_ID UNIQUE_ID,
    Id UNIQUE_ID
);
CREATE TABLE ACT_RU (
    Statement_ID UNIQUE_ID,
    One_Side_Var_ID UNIQUE_ID,
    Other_Side_Var_ID UNIQUE_ID,
    Associative_Var_ID UNIQUE_ID,
    relationship_phrase STRING,
    Rel_ID UNIQUE_ID,
    associationNumberLineNumber INTEGER,
    associationNumberColumn INTEGER,
    associationPhraseLineNumber INTEGER,
    associationPhraseColumn INTEGER
);
CREATE TABLE ACT_SAB (
    Action_ID UNIQUE_ID,
    SM_ID UNIQUE_ID,
    Act_ID UNIQUE_ID
);
CREATE TABLE ACT_SEL (
    Statement_ID UNIQUE_ID,
    Var_ID UNIQUE_ID,
    is_implicit BOOLEAN,
    cardinality STRING,
    Value_ID UNIQUE_ID
);
CREATE TABLE ACT_SGN (
    Statement_ID UNIQUE_ID,
    sigNameLineNumber INTEGER,
    sigNameColumn INTEGER,
    ownerNameLineNumber INTEGER,
    ownerNameColumn INTEGER,
    ProvidedSig_Id UNIQUE_ID,
    RequiredSig_Id UNIQUE_ID,
    Value_ID UNIQUE_ID
);
CREATE TABLE ACT_SMT (
    Statement_ID       UNIQUE_ID,
    Block_ID   UNIQUE_ID,
    Previous_Statement_ID      UNIQUE_ID,
    LineNumber INTEGER,
    StartPosition      INTEGER,
    Label      STRING,
    buffer STRING, 
    buffer2 STRING,
    Controlled_Block  UNIQUE_ID,
    control STRING );   
CREATE TABLE ACT_SR (
    Statement_ID UNIQUE_ID
);
CREATE TABLE ACT_SRW (
    Statement_ID UNIQUE_ID,
    Where_Clause_Value_ID UNIQUE_ID
);
CREATE TABLE ACT_TAB (
    Action_ID UNIQUE_ID,
    SM_ID UNIQUE_ID,
    Act_ID UNIQUE_ID
);
CREATE TABLE ACT_TFM (
    Statement_ID UNIQUE_ID,
    Tfr_ID UNIQUE_ID,
    Var_ID UNIQUE_ID,
    operationNameLineNumber INTEGER,
    operationNameColumn INTEGER,
    modelClassKeyLettersLineNumber INTEGER,
    modelClassKeyLettersColumn INTEGER
);
CREATE TABLE ACT_UNR (
    Statement_ID UNIQUE_ID,
    One_Side_Var_ID UNIQUE_ID,
    Other_Side_Var_ID UNIQUE_ID,
    relationship_phrase STRING,
    Rel_ID UNIQUE_ID,
    associationNumberLineNumber INTEGER,
    associationNumberColumn INTEGER,
    associationPhraseLineNumber INTEGER,
    associationPhraseColumn INTEGER
);
CREATE TABLE ACT_URU (
    Statement_ID UNIQUE_ID,
    One_Side_Var_ID UNIQUE_ID,
    Other_Side_Var_ID UNIQUE_ID,
    Associative_Var_ID UNIQUE_ID,
    relationship_phrase STRING,
    Rel_ID UNIQUE_ID,
    associationNumberLineNumber INTEGER,
    associationNumberColumn INTEGER,
    associationPhraseLineNumber INTEGER,
    associationPhraseColumn INTEGER
);
CREATE TABLE ACT_WHL (
    Statement_ID UNIQUE_ID,
    Value_ID UNIQUE_ID,
    Block_ID UNIQUE_ID
);
CREATE TABLE A_ACT (
    Id UNIQUE_ID
);
CREATE TABLE A_AE (
    Id UNIQUE_ID
);
CREATE TABLE A_AEA (
    Id UNIQUE_ID,
    Name STRING,
    Descrip STRING
);
CREATE TABLE A_AF (
    Id UNIQUE_ID,
    Descrip STRING
);
CREATE TABLE A_AP (
    Id UNIQUE_ID,
    Package_IDdeprecated UNIQUE_ID,
    Name STRING,
    Descrip STRING
);
CREATE TABLE A_ATE (
    Id UNIQUE_ID,
    Name STRING,
    Descrip STRING
);
CREATE TABLE A_CTL (
    Id UNIQUE_ID
);
CREATE TABLE A_DM (
    Id UNIQUE_ID,
    Name STRING,
    Descrip STRING
);
CREATE TABLE A_E (
    Id UNIQUE_ID,
    Package_IDdeprecated UNIQUE_ID,
    Guard STRING,
    Descrip STRING,
    TargetId UNIQUE_ID,
    SourceId UNIQUE_ID
);
CREATE TABLE A_FF (
    Id UNIQUE_ID,
    Descrip STRING
);
CREATE TABLE A_FJ (
    Id UNIQUE_ID,
    Descrip STRING,
    GuardCondition STRING
);
CREATE TABLE A_GA (
    Id UNIQUE_ID,
    Name STRING,
    Descrip STRING
);
CREATE TABLE A_INI (
    Id UNIQUE_ID,
    Descrip STRING
);
CREATE TABLE A_N (
    Id UNIQUE_ID,
    Package_IDdeprecated UNIQUE_ID
);
CREATE TABLE A_OBJ (
    Id UNIQUE_ID,
    Name STRING,
    Descrip STRING
);
CREATE TABLE A_SS (
    Id UNIQUE_ID,
    Name STRING,
    Descrip STRING
);
CREATE TABLE CL_IC (
    Id UNIQUE_ID,
    AssignedComp_Id UNIQUE_ID,
    ParentComp_Id UNIQUE_ID,
    Component_Package_IDdeprecated UNIQUE_ID,
    Mult INTEGER,
    ClassifierName STRING,
    Name STRING,
    Descrip STRING
);
CREATE TABLE CL_IIR (
    Id UNIQUE_ID,
    Ref_Id UNIQUE_ID,
    CL_POR_Id UNIQUE_ID,
    Delegation_Id UNIQUE_ID,
    Name STRING,
    Descrip STRING
);
CREATE TABLE CL_IP (
    Id UNIQUE_ID,
    Name STRING,
    Descrip STRING
);
CREATE TABLE CL_IPINS (
    Satisfaction_Id UNIQUE_ID,
    ImportedProvision_Id UNIQUE_ID
);
CREATE TABLE CL_IR (
    Id UNIQUE_ID,
    Satisfaction_Element_Id UNIQUE_ID,
    Name STRING,
    Descrip STRING
);
CREATE TABLE CL_POR (
    CL_IC_Id UNIQUE_ID,
    C_PO_Id UNIQUE_ID,
    Name STRING,
    Id UNIQUE_ID
);
CREATE TABLE CNST_CSP (
    Constant_Spec_ID UNIQUE_ID,
    InformalGroupName STRING,
    Descrip STRING
);
CREATE TABLE CNST_LFSC (
    Const_ID UNIQUE_ID,
    DT_ID_Deprecated UNIQUE_ID
);
CREATE TABLE CNST_LSC (
    Const_ID UNIQUE_ID,
    DT_ID_Deprecated UNIQUE_ID,
    Value STRING
);
CREATE TABLE CNST_SYC (
    Const_ID UNIQUE_ID,
    Name STRING,
    Descrip STRING,
    DT_ID UNIQUE_ID,
    Constant_Spec_ID UNIQUE_ID,
    Previous_Const_ID UNIQUE_ID,
    Previous_DT_DT_ID_Deprecated UNIQUE_ID
);
CREATE TABLE COMM_LNK (
    Link_ID UNIQUE_ID,
    Rel_ID UNIQUE_ID,
    Numb STRING,
    Descrip STRING,
    StartText STRING,
    EndText STRING,
    isFormal BOOLEAN,
    StartVisibility INTEGER,
    EndVisibility INTEGER,
    Start_Part_ID UNIQUE_ID,
    Destination_Part_ID UNIQUE_ID
);
CREATE TABLE C_AS (
    Id UNIQUE_ID,
    Name STRING,
    Descrip STRING,
    Direction INTEGER,
    Previous_Id UNIQUE_ID
);
CREATE TABLE C_C (
    Id UNIQUE_ID,
    Package_IDdeprecated UNIQUE_ID,
    NestedComponent_IDdeprecated UNIQUE_ID,
    Name STRING,
    Descrip STRING,
    Mult INTEGER,
    Root_Package_IDdeprecated UNIQUE_ID,
    isRealized BOOLEAN,
    Realized_Class_Path STRING
);
CREATE TABLE C_DG (
    Id UNIQUE_ID,
    Name STRING
);
CREATE TABLE C_EP (
    Id UNIQUE_ID,
    Interface_Id UNIQUE_ID,
    Direction INTEGER,
    Name STRING,
    Descrip STRING
);
CREATE TABLE C_I (
    Id UNIQUE_ID,
    Package_IDdeprecated UNIQUE_ID,
    Name STRING,
    Descrip STRING
);
CREATE TABLE C_IO (
    Id UNIQUE_ID,
    DT_ID UNIQUE_ID,
    Name STRING,
    Descrip STRING,
    Direction INTEGER,
    Return_Dimensions STRING,
    Previous_Id UNIQUE_ID
);
CREATE TABLE C_IR (
    Id UNIQUE_ID,
    Formal_Interface_Id UNIQUE_ID,
    Delegation_Id UNIQUE_ID,
    Port_Id UNIQUE_ID
);
CREATE TABLE C_P (
    Provision_Id UNIQUE_ID,
    Name STRING,
    InformalName STRING,
    Descrip STRING,
    pathFromComponent STRING
);
CREATE TABLE C_PO (
    Id UNIQUE_ID,
    Component_Id UNIQUE_ID,
    Name STRING,
    Mult INTEGER,
    DoNotShowPortOnCanvas BOOLEAN
);
CREATE TABLE C_PP (
    PP_Id UNIQUE_ID,
    Signal_Id UNIQUE_ID,
    DT_ID UNIQUE_ID,
    Name STRING,
    Descrip STRING,
    By_Ref INTEGER,
    Dimensions STRING,
    Previous_PP_Id UNIQUE_ID
);
CREATE TABLE C_R (
    Requirement_Id UNIQUE_ID,
    Name STRING,
    Descrip STRING,
    InformalName STRING,
    reversePathFromComponent STRING
);
CREATE TABLE C_RID (
    Reference_Id UNIQUE_ID,
    Delegation_Id UNIQUE_ID
);
CREATE TABLE C_SF (
    Id UNIQUE_ID,
    Requirement_Id UNIQUE_ID,
    Provision_Id UNIQUE_ID,
    Descrip STRING,
    Label STRING
);
CREATE TABLE EP_PKG (
    Package_ID UNIQUE_ID,
    Sys_ID UNIQUE_ID,
    Direct_Sys_ID UNIQUE_ID,
    Name STRING,
    Descrip STRING,
    Num_Rng INTEGER
);
CREATE TABLE E_CEA (
    Statement_ID UNIQUE_ID
);
CREATE TABLE E_CEC (
    Statement_ID UNIQUE_ID
);
CREATE TABLE E_CEI (
    Statement_ID UNIQUE_ID,
    Var_ID UNIQUE_ID
);
CREATE TABLE E_CES (
    Statement_ID UNIQUE_ID,
    is_implicit BOOLEAN,
    Var_ID UNIQUE_ID
);
CREATE TABLE E_CSME (
    Statement_ID UNIQUE_ID,
    SMevt_ID UNIQUE_ID
);
CREATE TABLE E_ESS (
    Statement_ID UNIQUE_ID,
    ParmListOK BOOLEAN,
    PEIndicated BOOLEAN,
    eventDerivedLabelLineNumber INTEGER,
    eventDerivedLabelColumn INTEGER,
    eventMeaningLineNumber INTEGER,
    eventMeaningColumn INTEGER,
    eventTargetKeyLettersLineNumber INTEGER,
    eventTargetKeyLettersColumn INTEGER,
    firstEventDataItemNameLineNumber INTEGER,
    firstEventDataItemNameColumn INTEGER,
    currentLaterEventDataItemNameLineNumber INTEGER,
    currentLaterEventDataItemNameColumn INTEGER
);
CREATE TABLE E_GAR (
    Statement_ID UNIQUE_ID
);
CREATE TABLE E_GEC (
    Statement_ID UNIQUE_ID
);
CREATE TABLE E_GEN (
    Statement_ID UNIQUE_ID,
    Var_ID UNIQUE_ID
);
CREATE TABLE E_GES (
    Statement_ID UNIQUE_ID
);
CREATE TABLE E_GPR (
    Statement_ID UNIQUE_ID,
    Value_ID UNIQUE_ID
);
CREATE TABLE E_GSME (
    Statement_ID UNIQUE_ID,
    SMevt_ID UNIQUE_ID
);
CREATE TABLE G_EIS (
    Element_ID UNIQUE_ID,
    Sys_ID UNIQUE_ID
);
CREATE TABLE IA_UCP (
    Part_ID UNIQUE_ID,
    Name STRING,
    Descrip STRING
);
CREATE TABLE I_AVL (
    Inst_ID UNIQUE_ID,
    Obj_ID UNIQUE_ID,
    Attr_ID UNIQUE_ID,
    Value STRING,
    Label STRING,
    RuntimeValue_ID UNIQUE_ID,
    Derived_RuntimeValue_ID UNIQUE_ID
);
CREATE TABLE I_BSF (
    Block_ID UNIQUE_ID,
    Stack_Frame_ID UNIQUE_ID,
    Statement_ID UNIQUE_ID,
    isExecuting BOOLEAN
);
CREATE TABLE I_CIN (
    Container_ID UNIQUE_ID
);
CREATE TABLE I_DIV (
    DIV_ID UNIQUE_ID,
    Event_ID UNIQUE_ID,
    SM_ID UNIQUE_ID,
    SMedi_ID UNIQUE_ID,
    RuntimeValue_ID UNIQUE_ID,
    PP_Id UNIQUE_ID
);
CREATE TABLE I_EQE (
    Event_Queue_Entry_ID UNIQUE_ID,
    Execution_Engine_ID UNIQUE_ID,
    Event_ID UNIQUE_ID,
    Next_Event_Queue_Entry_ID UNIQUE_ID
);
CREATE TABLE I_EVI (
    Event_ID UNIQUE_ID,
    isExecuting BOOLEAN,
    isCreation BOOLEAN,
    SMevt_ID UNIQUE_ID,
    Target_Inst_ID UNIQUE_ID,
    nextEvent_ID UNIQUE_ID,
    Sent_By_Inst_ID UNIQUE_ID,
    next_self_Event_ID UNIQUE_ID,
    Sent_By_CIE_ID UNIQUE_ID,
    CIE_ID UNIQUE_ID,
    Execution_Engine_ID UNIQUE_ID,
    Originating_Execution_Engine_ID UNIQUE_ID,
    Label STRING
);
CREATE TABLE I_EXE (
    Running BOOLEAN,
    Execution_Engine_ID UNIQUE_ID,
    Dom_IDdeprecated UNIQUE_ID,
    Component_Id UNIQUE_ID,
    Package_ID UNIQUE_ID,
    Next_Unique_ID INTEGER,
    Next_Instance_ID INTEGER,
    ImportedComponent_Id UNIQUE_ID,
    Label STRING,
    Container_ID UNIQUE_ID
);
CREATE TABLE I_ICQE (
    Stack_ID UNIQUE_ID,
    Stack_Frame_ID UNIQUE_ID,
    Execution_Engine_ID UNIQUE_ID
);
CREATE TABLE I_INS (
    Inst_ID UNIQUE_ID,
    Name STRING,
    SM_ID UNIQUE_ID,
    SMstt_ID UNIQUE_ID,
    Execution_Engine_ID UNIQUE_ID,
    Trans_ID UNIQUE_ID,
    CIE_ID UNIQUE_ID,
    Label STRING,
    Default_Name STRING
);
CREATE TABLE I_LIP (
    Participation_ID UNIQUE_ID,
    Rel_ID UNIQUE_ID,
    Inst_ID UNIQUE_ID,
    Label STRING
);
CREATE TABLE I_LNK (
    Link_ID UNIQUE_ID,
    Rel_ID UNIQUE_ID,
    fromInst_ID UNIQUE_ID,
    toInst_ID UNIQUE_ID,
    assocInst_ID UNIQUE_ID
);
CREATE TABLE I_MON (
    Execution_Engine_ID UNIQUE_ID,
    Inst_ID UNIQUE_ID,
    enabled BOOLEAN
);
CREATE TABLE I_RCH (
    Channel_Id UNIQUE_ID,
    Execution_Engine_ID UNIQUE_ID,
    other_Execution_Engine_ID UNIQUE_ID,
    Satisfaction_Id UNIQUE_ID,
    Delegation_Id UNIQUE_ID,
    Next_provider_Channel_Id UNIQUE_ID
);
CREATE TABLE I_SQE (
    Self_Queue_Entry_ID UNIQUE_ID,
    Execution_Engine_ID UNIQUE_ID,
    Event_ID UNIQUE_ID,
    Next_Self_Queue_Entry_ID UNIQUE_ID
);
CREATE TABLE I_STACK (
    Stack_ID UNIQUE_ID,
    Execution_Engine_ID UNIQUE_ID,
    runState INTEGER,
    suspendReason STRING
);
CREATE TABLE I_STF (
    Stack_Frame_ID UNIQUE_ID,
    Created_For_Wired_Bridge BOOLEAN,
    readyForInterrupt BOOLEAN,
    Bridge_Caller_Stack_Frame_ID UNIQUE_ID,
    Child_Stack_Frame_ID UNIQUE_ID,
    Top_Stack_Frame_Stack_ID UNIQUE_ID,
    Stack_ID UNIQUE_ID,
    Inst_ID UNIQUE_ID,
    Value_Q_Stack_ID UNIQUE_ID,
    Blocking_Stack_Frame_ID UNIQUE_ID
);
CREATE TABLE I_TIM (
    Timer_ID UNIQUE_ID,
    delay INTEGER,
    running BOOLEAN,
    recurring BOOLEAN,
    Event_ID UNIQUE_ID,
    Label STRING,
    expiration INTEGER
);
CREATE TABLE I_VSF (
    ValueInStackFrame_ID UNIQUE_ID,
    RuntimeValue_ID UNIQUE_ID,
    Value_ID UNIQUE_ID,
    Stack_Frame_ID UNIQUE_ID
);
CREATE TABLE MSG_A (
    Arg_ID UNIQUE_ID,
    Informal_Msg_ID UNIQUE_ID,
    Formal_Msg_ID UNIQUE_ID,
    Label STRING,
    Value STRING,
    InformalName STRING,
    Descrip STRING,
    isFormal BOOLEAN
);
CREATE TABLE MSG_AM (
    Msg_ID UNIQUE_ID,
    InformalName STRING,
    Descrip STRING,
    GuardCondition STRING,
    DurationObservation STRING,
    DurationConstraint STRING,
    isFormal BOOLEAN,
    Label STRING,
    SequenceNumb STRING
);
CREATE TABLE MSG_B (
    Msg_ID UNIQUE_ID,
    Brg_ID UNIQUE_ID
);
CREATE TABLE MSG_BA (
    Arg_ID UNIQUE_ID,
    BParm_ID UNIQUE_ID
);
CREATE TABLE MSG_E (
    Msg_ID UNIQUE_ID,
    SMevt_ID UNIQUE_ID
);
CREATE TABLE MSG_EA (
    Arg_ID UNIQUE_ID,
    SM_ID UNIQUE_ID,
    SMedi_ID UNIQUE_ID
);
CREATE TABLE MSG_EPA (
    Arg_ID UNIQUE_ID,
    PP_Id UNIQUE_ID
);
CREATE TABLE MSG_F (
    Msg_ID UNIQUE_ID,
    Sync_ID UNIQUE_ID
);
CREATE TABLE MSG_FA (
    Arg_ID UNIQUE_ID,
    SParm_ID UNIQUE_ID
);
CREATE TABLE MSG_IA (
    Arg_ID UNIQUE_ID
);
CREATE TABLE MSG_IAM (
    Msg_ID UNIQUE_ID
);
CREATE TABLE MSG_IOP (
    Msg_ID UNIQUE_ID,
    Id UNIQUE_ID
);
CREATE TABLE MSG_ISM (
    Msg_ID UNIQUE_ID
);
CREATE TABLE MSG_M (
    Msg_ID UNIQUE_ID,
    Receiver_Part_ID UNIQUE_ID,
    Sender_Part_ID UNIQUE_ID,
    participatesInCommunication BOOLEAN
);
CREATE TABLE MSG_O (
    Msg_ID UNIQUE_ID,
    Tfr_ID UNIQUE_ID
);
CREATE TABLE MSG_OA (
    Arg_ID UNIQUE_ID,
    TParm_ID UNIQUE_ID
);
CREATE TABLE MSG_R (
    Msg_ID UNIQUE_ID,
    Name STRING,
    Descrip STRING,
    GuardCondition STRING,
    ResultTarget STRING,
    ReturnValue STRING,
    SequenceNumb STRING
);
CREATE TABLE MSG_SIG (
    Msg_ID UNIQUE_ID,
    Id UNIQUE_ID
);
CREATE TABLE MSG_SM (
    Msg_ID UNIQUE_ID,
    InformalName STRING,
    Descrip STRING,
    GuardCondition STRING,
    ResultTarget STRING,
    ReturnValue STRING,
    isFormal BOOLEAN,
    Label STRING,
    SequenceNumb STRING
);
CREATE TABLE O_ATTR (
    Attr_ID UNIQUE_ID,
    Obj_ID UNIQUE_ID,
    PAttr_ID UNIQUE_ID,
    Name STRING,
    Descrip STRING,
    Prefix STRING,
    Root_Nam STRING,
    Pfx_Mode INTEGER,
    DT_ID UNIQUE_ID,
    Dimensions STRING,
    DefaultValue STRING
);
CREATE TABLE O_BATTR (
    Attr_ID UNIQUE_ID,
    Obj_ID UNIQUE_ID
);
CREATE TABLE O_DBATTR (
    Attr_ID UNIQUE_ID,
    Obj_ID UNIQUE_ID,
    Action_Semantics STRING,
    Suc_Pars INTEGER,
    Dialect INTEGER
);
CREATE TABLE O_ID (
    Oid_ID INTEGER,
    Obj_ID UNIQUE_ID
);
CREATE TABLE O_IOBJ (
    IObj_ID UNIQUE_ID,
    Obj_ID UNIQUE_ID,
    Modl_Typ INTEGER,
    SS_IDdeprecated UNIQUE_ID,
    Obj_Name STRING,
    Obj_KL STRING
);
CREATE TABLE O_NBATTR (
    Attr_ID UNIQUE_ID,
    Obj_ID UNIQUE_ID
);
CREATE TABLE O_OBJ (
    Obj_ID UNIQUE_ID,
    Name STRING,
    Numb INTEGER,
    Key_Lett STRING,
    Descrip STRING,
    SS_IDdeprecated UNIQUE_ID,
    AdapterName STRING,
    Order INTEGER
);
CREATE TABLE O_OIDA (
    Attr_ID UNIQUE_ID,
    Obj_ID UNIQUE_ID,
    Oid_ID INTEGER,
    localAttributeName STRING
);
CREATE TABLE O_RATTR (
    Attr_ID UNIQUE_ID,
    Obj_ID UNIQUE_ID,
    BAttr_ID UNIQUE_ID,
    BObj_ID UNIQUE_ID,
    Ref_Mode INTEGER,
    BaseAttrName STRING
);
CREATE TABLE O_REF (
    Obj_ID UNIQUE_ID,
    RObj_ID UNIQUE_ID,
    ROid_ID INTEGER,
    RAttr_ID UNIQUE_ID,
    Rel_ID UNIQUE_ID,
    OIR_ID UNIQUE_ID,
    ROIR_ID UNIQUE_ID,
    Attr_ID UNIQUE_ID,
    ARef_ID UNIQUE_ID,
    PARef_ID UNIQUE_ID,
    Is_Cstrd BOOLEAN,
    Descrip STRING,
    RObj_Name STRING,
    RAttr_Name STRING,
    Rel_Name STRING
);
CREATE TABLE O_RTIDA (
    Attr_ID UNIQUE_ID,
    Obj_ID UNIQUE_ID,
    Oid_ID INTEGER,
    Rel_ID UNIQUE_ID,
    OIR_ID UNIQUE_ID
);
CREATE TABLE O_TFR (
    Tfr_ID UNIQUE_ID,
    Obj_ID UNIQUE_ID,
    Name STRING,
    Descrip STRING,
    DT_ID UNIQUE_ID,
    Instance_Based INTEGER,
    Action_Semantics STRING,
    Suc_Pars INTEGER,
    Return_Dimensions STRING,
    Previous_Tfr_ID UNIQUE_ID,
    Dialect INTEGER
);
CREATE TABLE O_TPARM (
    TParm_ID UNIQUE_ID,
    Tfr_ID UNIQUE_ID,
    Name STRING,
    DT_ID UNIQUE_ID,
    By_Ref INTEGER,
    Dimensions STRING,
    Previous_TParm_ID UNIQUE_ID,
    Descrip STRING,
    Order   INTEGER
);
CREATE TABLE PA_DIC (
    Component_Id UNIQUE_ID,
    Delegation_Id UNIQUE_ID
);
CREATE TABLE PA_SIC (
    Component_Id UNIQUE_ID,
    Satisfaction_Id UNIQUE_ID
);
CREATE TABLE PE_CRS (
    Id UNIQUE_ID,
    Name STRING,
    Type INTEGER
);
CREATE TABLE PE_CVS (
    Visibility_ID UNIQUE_ID,
    Element_ID UNIQUE_ID,
    Id UNIQUE_ID,
    Name STRING,
    Type INTEGER
);
CREATE TABLE PE_PE (
    Element_ID UNIQUE_ID,
    Visibility INTEGER,
    Package_ID UNIQUE_ID,
    Component_ID UNIQUE_ID,
    type INTEGER
);
CREATE TABLE PE_SRS (
    Package_ID UNIQUE_ID,
    Name STRING,
    Type INTEGER
);
CREATE TABLE PE_VIS (
    Visibility_ID UNIQUE_ID,
    Element_ID UNIQUE_ID,
    Package_ID UNIQUE_ID,
    Name STRING,
    Type INTEGER
);
CREATE TABLE R_AONE (
    Obj_ID UNIQUE_ID,
    Rel_ID UNIQUE_ID,
    OIR_ID UNIQUE_ID,
    Mult INTEGER,
    Cond INTEGER,
    Txt_Phrs STRING
);
CREATE TABLE R_AOTH (
    Obj_ID UNIQUE_ID,
    Rel_ID UNIQUE_ID,
    OIR_ID UNIQUE_ID,
    Mult INTEGER,
    Cond INTEGER,
    Txt_Phrs STRING
);
CREATE TABLE R_ASSOC (
    Rel_ID UNIQUE_ID
);
CREATE TABLE R_ASSR (
    Obj_ID UNIQUE_ID,
    Rel_ID UNIQUE_ID,
    OIR_ID UNIQUE_ID,
    Mult INTEGER
);
CREATE TABLE R_COMP (
    Rel_ID UNIQUE_ID,
    Rel_Chn STRING
);
CREATE TABLE R_CONE (
    Obj_ID UNIQUE_ID,
    Rel_ID UNIQUE_ID,
    OIR_ID UNIQUE_ID,
    Mult INTEGER,
    Cond INTEGER,
    Txt_Phrs STRING
);
CREATE TABLE R_COTH (
    Obj_ID UNIQUE_ID,
    Rel_ID UNIQUE_ID,
    OIR_ID UNIQUE_ID,
    Mult INTEGER,
    Cond INTEGER,
    Txt_Phrs STRING
);
CREATE TABLE R_FORM (
    Obj_ID UNIQUE_ID,
    Rel_ID UNIQUE_ID,
    OIR_ID UNIQUE_ID,
    Mult INTEGER,
    Cond INTEGER,
    Txt_Phrs STRING
);
CREATE TABLE R_OIR (
    Obj_ID UNIQUE_ID,
    Rel_ID UNIQUE_ID,
    OIR_ID UNIQUE_ID,
    IObj_ID UNIQUE_ID
);
CREATE TABLE R_PART (
    Obj_ID UNIQUE_ID,
    Rel_ID UNIQUE_ID,
    OIR_ID UNIQUE_ID,
    Mult INTEGER,
    Cond INTEGER,
    Txt_Phrs STRING
);
CREATE TABLE R_REL (
    Rel_ID UNIQUE_ID,
    Numb INTEGER,
    Descrip STRING,
    SS_IDdeprecated UNIQUE_ID,
    ContainmentClassKL STRING
);
CREATE TABLE R_RGO (
    Obj_ID UNIQUE_ID,
    Rel_ID UNIQUE_ID,
    OIR_ID UNIQUE_ID
);
CREATE TABLE R_RTO (
    Obj_ID UNIQUE_ID,
    Rel_ID UNIQUE_ID,
    OIR_ID UNIQUE_ID,
    Oid_ID INTEGER
);
CREATE TABLE R_SIMP (
    Rel_ID UNIQUE_ID
);
CREATE TABLE R_SUB (
    Obj_ID UNIQUE_ID,
    Rel_ID UNIQUE_ID,
    OIR_ID UNIQUE_ID
);
CREATE TABLE R_SUBSUP (
    Rel_ID UNIQUE_ID
);
CREATE TABLE R_SUPER (
    Obj_ID UNIQUE_ID,
    Rel_ID UNIQUE_ID,
    OIR_ID UNIQUE_ID
);
CREATE TABLE SM_ACT (
    Act_ID UNIQUE_ID,
    SM_ID UNIQUE_ID,
    Suc_Pars INTEGER,
    Action_Semantics STRING,
    Descrip STRING,
    Dialect INTEGER
);
CREATE TABLE SM_AH (
    Act_ID UNIQUE_ID,
    SM_ID UNIQUE_ID
);
CREATE TABLE SM_ASM (
    SM_ID UNIQUE_ID,
    Obj_ID UNIQUE_ID
);
CREATE TABLE SM_CH (
    SMstt_ID UNIQUE_ID,
    SMevt_ID UNIQUE_ID,
    SM_ID UNIQUE_ID,
    SMspd_IDdeprecated UNIQUE_ID,
    Descrip STRING
);
CREATE TABLE SM_CRTXN (
    Trans_ID UNIQUE_ID,
    SM_ID UNIQUE_ID,
    SMevt_ID UNIQUE_ID,
    SMspd_IDdeprecated UNIQUE_ID
);
CREATE TABLE SM_EIGN (
    SMstt_ID UNIQUE_ID,
    SMevt_ID UNIQUE_ID,
    SM_ID UNIQUE_ID,
    SMspd_IDdeprecated UNIQUE_ID,
    Descrip STRING
);
CREATE TABLE SM_EVT (
    SMevt_ID UNIQUE_ID,
    SM_ID UNIQUE_ID,
    SMspd_IDdeprecated UNIQUE_ID,
    Numb INTEGER,
    Mning STRING,
    Is_Lbl_U INTEGER,
    Unq_Lbl STRING,
    Drv_Lbl STRING,
    Descrip STRING
);
CREATE TABLE SM_EVTDI (
    SMedi_ID UNIQUE_ID,
    SM_ID UNIQUE_ID,
    Name STRING,
    Descrip STRING,
    DT_ID UNIQUE_ID,
    Dimensions STRING,
    SMevt_ID UNIQUE_ID,
    Previous_SMedi_ID UNIQUE_ID,
    Order   INTEGER
);
CREATE TABLE SM_ISM (
    SM_ID UNIQUE_ID,
    Obj_ID UNIQUE_ID
);
CREATE TABLE SM_LEVT (
    SMevt_ID UNIQUE_ID,
    SM_ID UNIQUE_ID,
    SMspd_IDdeprecated UNIQUE_ID
);
CREATE TABLE SM_MEAH (
    Act_ID UNIQUE_ID,
    SM_ID UNIQUE_ID,
    Trans_ID UNIQUE_ID
);
CREATE TABLE SM_MEALY (
    SM_ID UNIQUE_ID
);
CREATE TABLE SM_MOAH (
    Act_ID UNIQUE_ID,
    SM_ID UNIQUE_ID,
    SMstt_ID UNIQUE_ID
);
CREATE TABLE SM_MOORE (
    SM_ID UNIQUE_ID
);
CREATE TABLE SM_NETXN (
    Trans_ID UNIQUE_ID,
    SM_ID UNIQUE_ID,
    SMstt_ID UNIQUE_ID,
    SMspd_IDdeprecated UNIQUE_ID
);
CREATE TABLE SM_NLEVT (
    SMevt_ID UNIQUE_ID,
    SM_ID UNIQUE_ID,
    SMspd_IDdeprecated UNIQUE_ID,
    polySMevt_ID UNIQUE_ID,
    polySM_ID UNIQUE_ID,
    polySMspd_IDdeprecated UNIQUE_ID,
    Local_Meaning STRING,
    Name STRING
);
CREATE TABLE SM_NSTXN (
    Trans_ID UNIQUE_ID,
    SM_ID UNIQUE_ID,
    SMstt_ID UNIQUE_ID,
    SMevt_ID UNIQUE_ID,
    SMspd_IDdeprecated UNIQUE_ID
);
CREATE TABLE SM_PEVT (
    SMevt_ID UNIQUE_ID,
    SM_ID UNIQUE_ID,
    SMspd_IDdeprecated UNIQUE_ID,
    localClassName STRING,
    localClassKL STRING,
    localEventMning STRING
);
CREATE TABLE SM_SEME (
    SMstt_ID UNIQUE_ID,
    SMevt_ID UNIQUE_ID,
    SM_ID UNIQUE_ID,
    SMspd_IDdeprecated UNIQUE_ID
);
CREATE TABLE SM_SEVT (
    SMevt_ID UNIQUE_ID,
    SM_ID UNIQUE_ID,
    SMspd_IDdeprecated UNIQUE_ID
);
CREATE TABLE SM_SGEVT (
    SMevt_ID UNIQUE_ID,
    SM_ID UNIQUE_ID,
    SMspd_IDdeprecated UNIQUE_ID,
    Provided_Signal_Id UNIQUE_ID,
    Required_Signal_Id UNIQUE_ID,
    signal_name STRING
);
CREATE TABLE SM_SM (
    SM_ID UNIQUE_ID,
    Descrip STRING,
    Config_ID INTEGER
);
CREATE TABLE SM_STATE (
    SMstt_ID UNIQUE_ID,
    SM_ID UNIQUE_ID,
    SMspd_IDdeprecated UNIQUE_ID,
    Name STRING,
    Numb INTEGER,
    Final INTEGER
);
CREATE TABLE SM_TAH (
    Act_ID UNIQUE_ID,
    SM_ID UNIQUE_ID,
    Trans_ID UNIQUE_ID
);
CREATE TABLE SM_TXN (
    Trans_ID UNIQUE_ID,
    SM_ID UNIQUE_ID,
    SMstt_ID UNIQUE_ID,
    SMspd_IDdeprecated UNIQUE_ID
);
CREATE TABLE SPR_PEP (
    Id UNIQUE_ID,
    ExecutableProperty_Id UNIQUE_ID,
    Provision_Id UNIQUE_ID
);
CREATE TABLE SPR_PO (
    Id UNIQUE_ID,
    Name STRING,
    Descrip STRING,
    Action_Semantics STRING,
    Suc_Pars INTEGER,
    Dialect INTEGER
);
CREATE TABLE SPR_PS (
    Id UNIQUE_ID,
    Name STRING,
    Descrip STRING,
    Action_Semantics STRING,
    Suc_Pars INTEGER,
    Dialect INTEGER
);
CREATE TABLE SPR_REP (
    Id UNIQUE_ID,
    ExecutableProperty_Id UNIQUE_ID,
    Requirement_Id UNIQUE_ID
);
CREATE TABLE SPR_RO (
    Id UNIQUE_ID,
    Name STRING,
    Descrip STRING,
    Action_Semantics STRING,
    Suc_Pars INTEGER,
    Dialect INTEGER
);
CREATE TABLE SPR_RS (
    Id UNIQUE_ID,
    Name STRING,
    Descrip STRING,
    Action_Semantics STRING,
    Suc_Pars INTEGER,
    Dialect INTEGER
);
CREATE TABLE SQ_AP (
    Part_ID UNIQUE_ID,
    Name STRING,
    Descrip STRING,
    LS_Part_ID UNIQUE_ID
);
CREATE TABLE SQ_AV (
    Av_ID UNIQUE_ID,
    Obj_ID UNIQUE_ID,
    Attr_ID UNIQUE_ID,
    Label STRING,
    Value STRING,
    InformalName STRING,
    Informal_Part_ID UNIQUE_ID,
    Formal_Part_ID UNIQUE_ID,
    Descrip STRING,
    isFormal BOOLEAN
);
CREATE TABLE SQ_CIP (
    Part_ID UNIQUE_ID,
    Obj_ID UNIQUE_ID,
    Name STRING,
    InformalClassName STRING,
    Label STRING,
    Descrip STRING,
    isFormal BOOLEAN
);
CREATE TABLE SQ_COP (
    Part_ID UNIQUE_ID,
    Component_Id UNIQUE_ID,
    Label STRING,
    InformalComponentName STRING,
    Descrip STRING,
    isFormal BOOLEAN
);
CREATE TABLE SQ_CP (
    Part_ID UNIQUE_ID,
    Obj_ID UNIQUE_ID,
    Label STRING,
    InformalName STRING,
    Descrip STRING,
    isFormal BOOLEAN
);
CREATE TABLE SQ_CPA (
    Ia_ID UNIQUE_ID,
    Name STRING,
    Type STRING,
    Part_ID UNIQUE_ID,
    Descrip STRING
);
CREATE TABLE SQ_EEP (
    Part_ID UNIQUE_ID,
    EE_ID UNIQUE_ID,
    Label STRING,
    InformalName STRING,
    Descrip STRING,
    isFormal BOOLEAN
);
CREATE TABLE SQ_FA (
    Ia_ID UNIQUE_ID
);
CREATE TABLE SQ_FAV (
    Av_ID UNIQUE_ID
);
CREATE TABLE SQ_IA (
    Ia_ID UNIQUE_ID
);
CREATE TABLE SQ_IAV (
    Av_ID UNIQUE_ID
);
CREATE TABLE SQ_LS (
    Part_ID UNIQUE_ID,
    Source_Part_ID UNIQUE_ID,
    Descrip STRING,
    Destroyed BOOLEAN
);
CREATE TABLE SQ_P (
    Part_ID UNIQUE_ID,
    Sequence_Package_IDdeprecated UNIQUE_ID
);
CREATE TABLE SQ_PP (
    Part_ID UNIQUE_ID,
    Package_ID UNIQUE_ID,
    Label STRING,
    InformalName STRING,
    Descrip STRING,
    isFormal BOOLEAN
);
CREATE TABLE SQ_TM (
    Mark_ID UNIQUE_ID,
    Name STRING,
    Part_ID UNIQUE_ID,
    Descrip STRING
);
CREATE TABLE SQ_TS (
    Span_ID UNIQUE_ID,
    Mark_ID UNIQUE_ID,
    Prev_Mark_ID UNIQUE_ID,
    Name STRING,
    Descrip STRING
);
CREATE TABLE S_BPARM (
    BParm_ID UNIQUE_ID,
    Brg_ID UNIQUE_ID,
    Name STRING,
    DT_ID UNIQUE_ID,
    By_Ref INTEGER,
    Dimensions STRING,
    Previous_BParm_ID UNIQUE_ID,
    Descrip STRING,
    Order   INTEGER
);
CREATE TABLE S_BRG (
    Brg_ID UNIQUE_ID,
    EE_ID UNIQUE_ID,
    Name STRING,
    Descrip STRING,
    Brg_Typ INTEGER,
    DT_ID UNIQUE_ID,
    Action_Semantics STRING,
    Suc_Pars INTEGER,
    Return_Dimensions STRING,
    Dialect INTEGER
);
CREATE TABLE S_CDT (
    DT_ID UNIQUE_ID,
    Core_Typ INTEGER
);
CREATE TABLE S_DIM (
    elementCount INTEGER,
    dimensionCount INTEGER,
    Sync_ID UNIQUE_ID,
    SParm_ID UNIQUE_ID,
    BParm_ID UNIQUE_ID,
    Brg_ID UNIQUE_ID,
    Id UNIQUE_ID,
    Obj_ID UNIQUE_ID,
    Attr_ID UNIQUE_ID,
    TParm_ID UNIQUE_ID,
    Tfr_ID UNIQUE_ID,
    Member_ID UNIQUE_ID,
    DT_ID UNIQUE_ID,
    PP_Id UNIQUE_ID,
    SM_ID UNIQUE_ID,
    SMedi_ID UNIQUE_ID,
    DIM_ID UNIQUE_ID,
    Var_ID UNIQUE_ID
);
CREATE TABLE S_DT (
    DT_ID UNIQUE_ID,
    Dom_IDdeprecated UNIQUE_ID,
    Name STRING,
    Descrip STRING,
    DefaultValue STRING
);
CREATE TABLE S_EDT (
    DT_ID UNIQUE_ID
);
CREATE TABLE S_EE (
    EE_ID UNIQUE_ID,
    Name STRING,
    Descrip STRING,
    Key_Lett STRING,
    Dom_IDdeprecated UNIQUE_ID,
    Realized_Class_Path STRING,
    Label STRING,
    isRealized BOOLEAN
);
CREATE TABLE S_EEM (
    EEmod_ID UNIQUE_ID,
    EE_ID UNIQUE_ID,
    Modl_Typ INTEGER,
    SS_IDdeprecated UNIQUE_ID
);
CREATE TABLE S_ENUM (
    Enum_ID UNIQUE_ID,
    Name STRING,
    Descrip STRING,
    EDT_DT_ID UNIQUE_ID,
    Previous_Enum_ID UNIQUE_ID
);
CREATE TABLE S_EXP (
    Exception_ID UNIQUE_ID,
    Name STRING,
    Descrip STRING
);
CREATE TABLE S_IRDT (
    DT_ID UNIQUE_ID,
    isSet BOOLEAN,
    Obj_ID UNIQUE_ID
);
CREATE TABLE S_MBR (
    Member_ID UNIQUE_ID,
    Name STRING,
    Descrip STRING,
    Parent_DT_DT_ID UNIQUE_ID,
    DT_ID UNIQUE_ID,
    Previous_Member_ID UNIQUE_ID,
    Dimensions STRING
);
CREATE TABLE S_SDT (
    DT_ID UNIQUE_ID
);
CREATE TABLE S_SPARM (
    SParm_ID UNIQUE_ID,
    Sync_ID UNIQUE_ID,
    Name STRING,
    DT_ID UNIQUE_ID,
    By_Ref INTEGER,
    Dimensions STRING,
    Previous_SParm_ID UNIQUE_ID,
    Descrip STRING,
    Order   INTEGER
);
CREATE TABLE S_SYNC (
    Sync_ID UNIQUE_ID,
    Dom_IDdeprecated UNIQUE_ID,
    Name STRING,
    Descrip STRING,
    Action_Semantics STRING,
    DT_ID UNIQUE_ID,
    Suc_Pars INTEGER,
    Return_Dimensions STRING,
    Dialect INTEGER
);
CREATE TABLE S_SYS (
    Sys_ID UNIQUE_ID,
    Name STRING,
    useGlobals BOOLEAN
);
CREATE TABLE S_UDT (
    DT_ID UNIQUE_ID,
    CDT_DT_ID UNIQUE_ID,
    Gen_Type INTEGER,
    Definition STRING
);
CREATE TABLE TE_ABA (
    AbaID UNIQUE_ID,
    subtypeKL STRING,
    te_cID UNIQUE_ID,
    SelfEventCount INTEGER,
    NonSelfEventCount INTEGER,
    ParameterDeclaration STRING,
    ParameterDefinition STRING,
    ParameterStructure STRING,
    ParameterTrace STRING,
    ParameterFormat STRING,
    ParameterInvocation STRING,
    ParameterSMSGinvoke STRING,
    ParameterAssignment STRING,
    ParameterAssignmentBase STRING,
    scope STRING,
    GeneratedName STRING,
    ReturnDataType STRING,
    dimensions INTEGER,
    te_dimID UNIQUE_ID,
    array_spec STRING,
    code STRING
);
CREATE TABLE TE_ACT (
    AbaID UNIQUE_ID,
    GeneratedName STRING,
    number INTEGER,
    SM_ID UNIQUE_ID,
    Act_ID UNIQUE_ID
);
CREATE TABLE TE_ASSIGN (
    isImplicit BOOLEAN,
    dimensions INTEGER,
    array_spec STRING,
    left_declaration STRING,
    Core_Typ INTEGER,
    lval STRING,
    rval STRING,
    Statement_ID UNIQUE_ID,
    rval_dimensions INTEGER
);
CREATE TABLE TE_ATTR (
    ID UNIQUE_ID,
    Used BOOLEAN,
    read BOOLEAN,
    written BOOLEAN,
    Included BOOLEAN,
    Order INTEGER,
    ParamBuffer STRING,
    Name STRING,
    GeneratedName STRING,
    DefaultValue STRING,
    translate BOOLEAN,
    dimensions INTEGER,
    te_dimID UNIQUE_ID,
    array_spec STRING,
    te_classGeneratedName STRING,
    GeneratedType STRING,
    prevID UNIQUE_ID,
    Attr_ID UNIQUE_ID,
    Obj_ID UNIQUE_ID
);
CREATE TABLE TE_BLK (
    Block_ID UNIQUE_ID,
    first_Statement_ID UNIQUE_ID,
    declaration STRING,
    deallocation STRING,
    depth INTEGER,
    indentation STRING,
    AbaID UNIQUE_ID
);
CREATE TABLE TE_BREAK (
    Statement_ID UNIQUE_ID
);
CREATE TABLE TE_BRG (
    EEname STRING,
    EEkeyletters STRING,
    Name STRING,
    GeneratedName STRING,
    AbaID UNIQUE_ID,
    Brg_ID UNIQUE_ID,
    EE_ID UNIQUE_ID
);
CREATE TABLE TE_BRIDGE (
    bridge_name STRING,
    parameters STRING,
    Statement_ID UNIQUE_ID
);
CREATE TABLE TE_C (
    ID UNIQUE_ID,
    Name STRING,
    Descrip STRING,
    number INTEGER,
    StateTrace BOOLEAN,
    StmtTrace BOOLEAN,
    DetectEmpty BOOLEAN,
    OptDisabled BOOLEAN,
    RawComments BOOLEAN,
    CodeComments BOOLEAN,
    CollectionsFlavor INTEGER,
    classes_file STRING,
    functions_file STRING,
    MaxObjExtent INTEGER,
    MaxRelExtent INTEGER,
    MaxSelectExtent INTEGER,
    MaxSelfEvents INTEGER,
    MaxNonSelfEvents INTEGER,
    MaxPriorityEvents INTEGER,
    MaxTimers INTEGER,
    InterleavedBridges INTEGER,
    PEIClassCount INTEGER,
    PersistentClassCount INTEGER,
    module_file STRING,
    port_file STRING,
    include_file STRING,
    included_in_build BOOLEAN,
    internal_behavior BOOLEAN,
    isRealized BOOLEAN,
    SystemID INTEGER,
    next_ID UNIQUE_ID,
    cId UNIQUE_ID,
    first_eeID UNIQUE_ID,
    first_syncID UNIQUE_ID,
    smsg_send STRING,
    smsg_recv STRING
);
CREATE TABLE TE_CALLOUT (
    file STRING,
    initialization STRING,
    pre_xtUML_initialization STRING,
    post_xtUML_initialization STRING,
    background_processing STRING,
    event_cant_happen STRING,
    pre_shutdown STRING,
    post_shutdown STRING,
    event_no_instance STRING,
    event_free_list_empty STRING,
    empty_handle_detected STRING,
    object_pool_empty STRING,
    node_list_empty STRING,
    interleaved_bridge_overflow STRING,
    self_event_queue_empty STRING,
    non_self_event_queue_empty STRING,
    persistence_error STRING
);
CREATE TABLE TE_CI (
    ID UNIQUE_ID,
    Name STRING,
    ClassifierName STRING,
    template_parameter_values STRING,
    te_cID UNIQUE_ID,
    cl_icId UNIQUE_ID
);
CREATE TABLE TE_CIA (
    class_info_name STRING,
    class_info_type STRING,
    active_count STRING,
    class_count STRING,
    count_type STRING
);
CREATE TABLE TE_CLASS (
    ID UNIQUE_ID,
    Name STRING,
    Numb INTEGER,
    Key_Lett STRING,
    scope STRING,
    GeneratedName STRING,
    CBGeneratedName STRING,
    Included BOOLEAN,
    PEIsDefinedInData BOOLEAN,
    IsFixedPopulation BOOLEAN,
    IsReadOnly BOOLEAN,
    ExcludeFromGen BOOLEAN,
    MaxExtentSize INTEGER,
    SelfCreated BOOLEAN,
    NonSelfCreated BOOLEAN,
    Persistent BOOLEAN,
    Order INTEGER,
    IsTrace BOOLEAN,
    ContainerIndex INTEGER,
    Task INTEGER,
    class_file STRING,
    system_class_number STRING,
    CBsystem_class_number STRING,
    persist_link STRING,
    dispatcher STRING,
    CBdispatcher STRING,
    attribute_format STRING,
    attribute_dump STRING,
    te_cID UNIQUE_ID,
    Obj_ID UNIQUE_ID,
    nextID UNIQUE_ID
);
CREATE TABLE TE_CONTAINER (
    flavor STRING
);
CREATE TABLE TE_CONTINUE (
    Statement_ID UNIQUE_ID
);
CREATE TABLE TE_CONTROL (
    Statement_ID UNIQUE_ID
);
CREATE TABLE TE_COPYRIGHT (
    body STRING
);
CREATE TABLE TE_CREATE_EVENT (
    sm_evt UNIQUE_ID,
    is_implicit BOOLEAN,
    class_name STRING,
    event_meaning STRING,
    oal_var_name STRING,
    var_name STRING,
    recipient_var_name STRING,
    parameters STRING,
    Statement_ID UNIQUE_ID
);
CREATE TABLE TE_CREATE_INSTANCE (
    o_obj UNIQUE_ID,
    is_implicit BOOLEAN,
    class_name STRING,
    var_name STRING,
    Statement_ID UNIQUE_ID
);
CREATE TABLE TE_DBATTR (
    Included BOOLEAN,
    AbaID UNIQUE_ID,
    GeneratedName STRING,
    Attr_ID UNIQUE_ID,
    Obj_ID UNIQUE_ID
);
CREATE TABLE TE_DCI (
    te_cID UNIQUE_ID,
    class_numbers STRING,
    persist_union STRING,
    task_list STRING,
    task_numbers STRING,
    max STRING,
    max_models STRING,
    init STRING,
    array_name STRING
);
CREATE TABLE TE_DELETE_INSTANCE (
    o_obj UNIQUE_ID,
    var_name STRING,
    del_count INTEGER,
    Statement_ID UNIQUE_ID
);
CREATE TABLE TE_DIM (
    te_dimID UNIQUE_ID,
    elementCount INTEGER,
    dimensionCount INTEGER,
    next_te_dimID UNIQUE_ID
);
CREATE TABLE TE_DISP (
    Dispatcher_ID INTEGER,
    SystemID INTEGER,
    Name STRING,
    Descrip STRING,
    message_id_type STRING,
    base_message_type STRING
);
CREATE TABLE TE_DLIST (
    flavor STRING,
    remove_node STRING
);
CREATE TABLE TE_DMA (
    file STRING,
    allocate STRING,
    release STRING,
    debugging_heap BOOLEAN
);
CREATE TABLE TE_DT (
    ID UNIQUE_ID,
    Name STRING,
    Core_Typ INTEGER,
    Included BOOLEAN,
    ExtName STRING,
    Include_File STRING,
    Initial_Value STRING,
    Value STRING,
    Is_Enum BOOLEAN,
    Owning_Dom_Name STRING,
    string_format STRING,
    generated BOOLEAN,
    te_cID UNIQUE_ID,
    DT_ID UNIQUE_ID
);
CREATE TABLE TE_EE (
    Name STRING,
    RegisteredName STRING,
    Key_Lett STRING,
    Descrip STRING,
    Included BOOLEAN,
    file STRING,
    Include_File STRING,
    Used BOOLEAN,
    te_cID UNIQUE_ID,
    EE_ID UNIQUE_ID,
    ID UNIQUE_ID,
    nextID UNIQUE_ID
);
CREATE TABLE TE_ELIF (
    condition STRING,
    Statement_ID UNIQUE_ID
);
CREATE TABLE TE_ELSE (
    Statement_ID UNIQUE_ID
);
CREATE TABLE TE_ENUM (
    Name STRING,
    Value STRING,
    Order INTEGER,
    GeneratedName STRING,
    Enum_ID UNIQUE_ID
);
CREATE TABLE TE_EQ (
    ignored STRING,
    cant_happen STRING,
    system_events_union STRING,
    system_event_pool STRING,
    constant_type STRING,
    base_event_type STRING,
    base_variable STRING,
    max_events STRING,
    max_self_events STRING,
    max_nonself_events STRING,
    new STRING,
    allocate STRING,
    delete STRING,
    modify STRING,
    self STRING,
    non_self STRING,
    search_and_destroy STRING,
    run_flag STRING,
    event_message_variable STRING,
    scope STRING
);
CREATE TABLE TE_EVENT_PARAMETERS (
    evt_msg_var STRING,
    parameter STRING,
    value STRING,
    value_type STRING,
    Statement_ID UNIQUE_ID
);
CREATE TABLE TE_EVT (
    ID UNIQUE_ID,
    Name STRING,
    Numb INTEGER,
    GeneratedName STRING,
    Enumerator STRING,
    Order INTEGER,
    Used BOOLEAN,
    UsedCount INTEGER,
    Priority INTEGER,
    SM_ID UNIQUE_ID,
    SMevt_ID UNIQUE_ID,
    te_smID UNIQUE_ID
);
CREATE TABLE TE_EXTENT (
    sets_type STRING,
    type STRING,
    active STRING,
    inactive STRING,
    initializer STRING,
    size_type STRING,
    size_name STRING,
    istate_type STRING,
    istate_name STRING,
    container_type STRING,
    container_name STRING,
    pool_type STRING,
    pool_name STRING,
    population_type STRING,
    population_name STRING,
    rstorsize_type STRING,
    rstorsize_name STRING,
    linkfunc_name STRING
);
CREATE TABLE TE_FILE (
    hdr_file_ext STRING,
    src_file_ext STRING,
    obj_file_ext STRING,
    sys_main STRING,
    factory STRING,
    events STRING,
    nvs STRING,
    nvs_bridge STRING,
    sets STRING,
    types STRING,
    thread STRING,
    trace STRING,
    tim STRING,
    callout STRING,
    ilb STRING,
    persist STRING,
    xtumlload STRING,
    interfaces STRING,
    registers STRING,
    domain_color_path STRING,
    domain_source_path STRING,
    domain_include_path STRING,
    system_source_path STRING,
    system_include_path STRING,
    system_color_path STRING,
    bridge_mark STRING,
    system_mark STRING,
    datatype_mark STRING,
    event_mark STRING,
    class_mark STRING,
    domain_mark STRING,
    system_functions_mark STRING,
    arc_path STRING,
    root_path STRING
);
CREATE TABLE TE_FOR (
    isImplicit BOOLEAN,
    class_name STRING,
    loop_variable STRING,
    set_variable STRING,
    Statement_ID UNIQUE_ID
);
CREATE TABLE TE_FUNCTION (
    method STRING,
    parameters STRING,
    Statement_ID UNIQUE_ID
);
CREATE TABLE TE_GENERATE (
    sm_evt UNIQUE_ID,
    self_directed BOOLEAN,
    var_name STRING,
    event_label STRING,
    event_meaning STRING,
    parameters STRING,
    Statement_ID UNIQUE_ID
);
CREATE TABLE TE_GENERATE_CREATOR_EVENT (
    sm_evt UNIQUE_ID,
    self_directed BOOLEAN,
    var_name STRING,
    event_label STRING,
    event_meaning STRING,
    parameters STRING,
    Statement_ID UNIQUE_ID
);
CREATE TABLE TE_GENERATE_PRECREATED_EVENT (
    self_directed BOOLEAN,
    var_name STRING,
    Statement_ID UNIQUE_ID
);
CREATE TABLE TE_GENERATE_TO_CLASS (
    sm_evt UNIQUE_ID,
    self_directed BOOLEAN,
    var_name STRING,
    event_label STRING,
    even_meaning STRING,
    parameters STRING,
    Statement_ID UNIQUE_ID
);
CREATE TABLE TE_IF (
    condition STRING,
    Statement_ID UNIQUE_ID
);
CREATE TABLE TE_IIR (
    ID UNIQUE_ID,
    component_name STRING,
    port_name STRING,
    interface_name STRING,
    polymorphic BOOLEAN,
    template_parameter_values STRING,
    C2C STRING,
    cl_iirId UNIQUE_ID,
    c_irId UNIQUE_ID,
    te_poID UNIQUE_ID,
    provider_te_iirID UNIQUE_ID
);
CREATE TABLE TE_ILB (
    file STRING,
    define_name STRING,
    data_define_name STRING,
    interleave_bridge STRING,
    interleave_bridge_done STRING,
    get_data STRING,
    dispatch STRING
);
CREATE TABLE TE_INSTANCE (
    create STRING,
    create_persistent STRING,
    delete STRING,
    delete_persistent STRING,
    self STRING,
    global_self STRING,
    base STRING,
    handle STRING,
    factory_init STRING,
    base_class STRING,
    max_extent STRING,
    max_association_extent STRING,
    max_transient_extent STRING,
    current_state STRING,
    scope STRING,
    get_dci STRING,
    get_event_dispatcher STRING,
    get_thread_assignment STRING,
    module STRING
);
CREATE TABLE TE_IOP (
    name STRING,
    parameters STRING,
    Statement_ID UNIQUE_ID
);
CREATE TABLE TE_LNK (
    ID UNIQUE_ID,
    Mult INTEGER,
    rel_phrase STRING,
    rel_number INTEGER,
    OAL STRING,
    buffer STRING,
    next_ID UNIQUE_ID,
    Link_ID UNIQUE_ID,
    linkage STRING,
    iterator STRING,
    left STRING,
    te_classGeneratedName STRING,
    first BOOLEAN,
    last BOOLEAN,
    assoc_type STRING
);
CREATE TABLE TE_MACT (
    AbaID UNIQUE_ID,
    ID UNIQUE_ID,
    Name STRING,
    Descrip STRING,
    GeneratedName STRING,
    ComponentName STRING,
    OALParamBuffer STRING,
    PortName STRING,
    InterfaceName STRING,
    MessageName STRING,
    Direction INTEGER,
    Provision BOOLEAN,
    subtypeKL STRING,
    polymorphic BOOLEAN,
    trace BOOLEAN,
    Order INTEGER,
    SPR_POId UNIQUE_ID,
    SPR_PSId UNIQUE_ID,
    SPR_ROId UNIQUE_ID,
    SPR_RSId UNIQUE_ID,
    te_cID UNIQUE_ID,
    te_poID UNIQUE_ID,
    te_evtID UNIQUE_ID,
    nextID UNIQUE_ID
);
CREATE TABLE TE_MBR (
    ID UNIQUE_ID,
    Name STRING,
    GeneratedName STRING,
    dimensions INTEGER,
    te_dimID UNIQUE_ID,
    array_spec STRING,
    previousID UNIQUE_ID,
    te_dtID UNIQUE_ID,
    Member_ID UNIQUE_ID,
    DT_ID UNIQUE_ID
);
CREATE TABLE TE_OIR (
    data_member STRING,
    assoc_type STRING,
    object_id STRING,
    rel_phrase STRING,
    Mult INTEGER,
    NavigatedTo BOOLEAN,
    OIR_ID UNIQUE_ID,
    Obj_ID UNIQUE_ID,
    Rel_ID UNIQUE_ID
);
CREATE TABLE TE_OPERATION (
    instance_based BOOLEAN,
    operation_name STRING,
    parameters STRING,
    var_name STRING,
    Statement_ID UNIQUE_ID
);
CREATE TABLE TE_OUTFILE (
    ID UNIQUE_ID,
    Name STRING,
    body STRING,
    Order INTEGER
);
CREATE TABLE TE_PAR (
    Name STRING,
    By_Ref INTEGER,
    buffer STRING,
    Order INTEGER,
    Value_ID UNIQUE_ID,
    te_parmID UNIQUE_ID
);
CREATE TABLE TE_PARM (
    Name STRING,
    Descrip STRING,
    Order INTEGER,
    ParamBuffer STRING,
    OALParamBuffer STRING,
    ID UNIQUE_ID,
    nextID UNIQUE_ID,
    GeneratedName STRING,
    By_Ref INTEGER,
    te_dtID UNIQUE_ID,
    dimensions INTEGER,
    te_dimID UNIQUE_ID,
    array_spec STRING,
    AbaID UNIQUE_ID,
    SM_ID UNIQUE_ID,
    SMedi_ID UNIQUE_ID,
    SParm_ID UNIQUE_ID,
    TParm_ID UNIQUE_ID,
    PP_Id UNIQUE_ID,
    BParm_ID UNIQUE_ID
);
CREATE TABLE TE_PERSIST (
    persist_file STRING,
    class_union STRING,
    instance_cache_depth STRING,
    link_cache_depth STRING,
    check_mark STRING,
    post_link STRING,
    link_type_name STRING,
    link_type_type STRING,
    factory_init STRING,
    commit STRING,
    restore STRING,
    remove STRING,
    domainnum_name STRING,
    domainnum_type STRING,
    classnum_name STRING,
    classnum_type STRING,
    index_name STRING,
    index_type STRING,
    instid_type STRING,
    instid_name STRING,
    dirty_type STRING,
    dirty_name STRING,
    dirty_dirty INTEGER,
    dirty_clean INTEGER
);
CREATE TABLE TE_PO (
    ID UNIQUE_ID,
    Name STRING,
    GeneratedName STRING,
    InterfaceName STRING,
    PackageName STRING,
    smsg_send STRING,
    smsg_recv STRING,
    Provision BOOLEAN,
    polymorphic BOOLEAN,
    sibling INTEGER,
    Order INTEGER,
    te_cID UNIQUE_ID,
    c_iId UNIQUE_ID,
    c_poId UNIQUE_ID,
    first_te_mactID UNIQUE_ID
);
CREATE TABLE TE_PREFIX (
    symbol STRING,
    symbolsw STRING,
    type STRING,
    typesw STRING,
    define_u STRING,
    define_c STRING,
    define_csw STRING,
    define_usw STRING,
    file STRING,
    result STRING,
    provided_port STRING,
    required_port STRING,
    channel STRING
);
CREATE TABLE TE_QUEUE (
    Queue_ID INTEGER,
    Dispatcher_ID INTEGER,
    Type INTEGER,
    Descrip STRING,
    RenderCode BOOLEAN,
    MaxDepth INTEGER
);
CREATE TABLE TE_REL (
    Numb INTEGER,
    LinkNeeded BOOLEAN,
    UnlinkNeeded BOOLEAN,
    Navigated BOOLEAN,
    Order INTEGER,
    storage_needed BOOLEAN,
    Rel_ID UNIQUE_ID
);
CREATE TABLE TE_RELATE (
    one_o_obj UNIQUE_ID,
    oth_o_obj UNIQUE_ID,
    r_rel UNIQUE_ID,
    is_reflexive BOOLEAN,
    relationship_number INTEGER,
    relationship_phrase STRING,
    one_var_name STRING,
    oth_var_name STRING,
    Statement_ID UNIQUE_ID
);
CREATE TABLE TE_RELATE_USING (
    one_o_obj UNIQUE_ID,
    oth_o_obj UNIQUE_ID,
    ass_o_obj UNIQUE_ID,
    r_rel UNIQUE_ID,
    is_inflexive BOOLEAN,
    relationship_number INTEGER,
    relationship_phrase STRING,
    one_var_name STRING,
    oth_var_name STRING,
    ass_var_name STRING,
    one_rel_phrase STRING,
    oth_rel_phrase STRING,
    Statement_ID UNIQUE_ID
);
CREATE TABLE TE_RELINFO (
    obj UNIQUE_ID,
    related_obj UNIQUE_ID,
    rel UNIQUE_ID,
    rto UNIQUE_ID,
    rgo UNIQUE_ID,
    oir UNIQUE_ID,
    multiplicity INTEGER,
    gen_link_methods BOOLEAN,
    rel_phrase STRING,
    is_formalizer BOOLEAN,
    is_supertype BOOLEAN,
    generate_subtype BOOLEAN,
    gen_declaration BOOLEAN
);
CREATE TABLE TE_RELSTORE (
    data_declare STRING,
    data_init STRING,
    link_calls STRING,
    link_index INTEGER,
    data_fini STRING,
    self_name STRING
);
CREATE TABLE TE_RETURN (
    value STRING,
    cast1 STRING,
    cast2 STRING,
    Statement_ID UNIQUE_ID
);
CREATE TABLE TE_SELECT (
    o_obj UNIQUE_ID,
    is_implicit BOOLEAN,
    class_name STRING,
    target_class_name STRING,
    class_description STRING,
    multiplicity STRING,
    var_name STRING,
    Statement_ID UNIQUE_ID
);
CREATE TABLE TE_SELECT_RELATED (
    Statement_ID UNIQUE_ID,
    link_ID UNIQUE_ID,
    starting_Value_ID UNIQUE_ID,
    where_clause_Value_ID UNIQUE_ID,
    starting_Var_ID UNIQUE_ID,
    result_Var_ID UNIQUE_ID,
    by_where BOOLEAN,
    where_clause STRING,
    where_clause_OAL STRING,
    multiplicity STRING,
    is_implicit BOOLEAN,
    result_var STRING,
    result_var_OAL STRING,
    start_many BOOLEAN,
    start_var STRING,
    start_var_OAL STRING,
    te_classGeneratedName STRING
);
CREATE TABLE TE_SELECT_WHERE (
    o_obj UNIQUE_ID,
    is_implicit BOOLEAN,
    class_name STRING,
    oal_var_name STRING,
    class_description STRING,
    multiplicity STRING,
    var_name STRING,
    selected_var_name STRING,
    where_clause STRING,
    special BOOLEAN,
    oid_id INTEGER,
    Statement_ID UNIQUE_ID
);
CREATE TABLE TE_SET (
    number_of_containoids STRING,
    iterator_class_name STRING,
    factory STRING,
    init STRING,
    copy STRING,
    clear STRING,
    setadd STRING,
    insert_element STRING,
    insert_instance STRING,
    insert_block STRING,
    remove_element STRING,
    remove_instance STRING,
    element_count STRING,
    contains STRING,
    equality STRING,
    emptiness STRING,
    get_any STRING,
    iterator_reset STRING,
    iterator_next STRING,
    base_class STRING,
    element_type STRING,
    scope STRING,
    module STRING
);
CREATE TABLE TE_SGN (
    sm_evt UNIQUE_ID,
    self_directed BOOLEAN,
    var_name STRING,
    event_label STRING,
    event_meaning STRING,
    parameters STRING,
    Statement_ID UNIQUE_ID
);
CREATE TABLE TE_SLIST (
    flavor STRING,
    remove_node STRING
);
CREATE TABLE TE_SM (
    ID UNIQUE_ID,
    complete BOOLEAN,
    initial_state STRING,
    SEMname STRING,
    action_array STRING,
    action_type STRING,
    events_union STRING,
    txn_action_array STRING,
    txn_action_count INTEGER,
    state_names_array STRING,
    num_states INTEGER,
    num_events INTEGER,
    SM_ID UNIQUE_ID,
    te_classGeneratedName STRING
);
CREATE TABLE TE_SMT (
    Statement_ID UNIQUE_ID,
    OAL STRING,
    buffer STRING,
    buffer2 STRING,
    trace STRING,
    next_Statement_ID UNIQUE_ID,
    sub_Block_ID UNIQUE_ID,
    subtypeKL STRING,
    parent_Block_ID UNIQUE_ID
);
CREATE TABLE TE_STATE (
    Name STRING,
    enumerator STRING,
    Numb INTEGER,
    number INTEGER,
    Order INTEGER,
    SM_ID UNIQUE_ID,
    SMstt_ID UNIQUE_ID
);
CREATE TABLE TE_STRING (
    memset STRING,
    memmove STRING,
    strcpy STRING,
    stradd STRING,
    strlen STRING,
    strcmp STRING,
    strget STRING,
    itoa STRING,
    atoi STRING,
    max_string_length STRING,
    debug_buffer_depth STRING
);
CREATE TABLE TE_SWC (
    Obj_Kl STRING,
    Where_Spec STRING,
    Key STRING,
    Ret_Val BOOLEAN,
    Built_In BOOLEAN,
    Oid_ID INTEGER,
    GeneratedName STRING
);
CREATE TABLE TE_SYNC (
    Name STRING,
    Included BOOLEAN,
    IsInitFunction BOOLEAN,
    IsSafeForInterrupts BOOLEAN,
    XlateSemantics BOOLEAN,
    Order INTEGER,
    AbaID UNIQUE_ID,
    GeneratedName STRING,
    intraface_method STRING,
    deferred_method STRING,
    te_cID UNIQUE_ID,
    Sync_ID UNIQUE_ID,
    ID UNIQUE_ID,
    nextID UNIQUE_ID
);
CREATE TABLE TE_SYS (
    SystemID INTEGER,
    ModelCompilerName STRING,
    ExecutableName STRING,
    MaxStringLen INTEGER,
    MaxObjExtent INTEGER,
    MaxRelExtent INTEGER,
    MaxSelectExtent INTEGER,
    TotalContainers INTEGER,
    MaxSelfEvents INTEGER,
    MaxNonSelfEvents INTEGER,
    MaxTimers INTEGER,
    MaxInterleavedBridges INTEGER,
    MaxInterleavedBridgeDataSize INTEGER,
    CollectionsFlavor INTEGER,
    ForcePriorityEvents BOOLEAN,
    PEIClassCount INTEGER,
    PersistentClassCount INTEGER,
    PersistInstanceCacheDepth INTEGER,
    PersistLinkCacheDepth INTEGER,
    UnitsToDynamicallyAllocate INTEGER,
    InstanceLoading BOOLEAN,
    self_name STRING,
    Name STRING,
    AUTOSAR BOOLEAN,
    VFB BOOLEAN,
    SystemCPortsType STRING,
    AllPortsPoly BOOLEAN,
    StructuredMessaging BOOLEAN,
    NetworkSockets BOOLEAN,
    Sys_ID UNIQUE_ID
);
CREATE TABLE TE_TARGET (
    language STRING,
    c2cplusplus_linkage_begin STRING,
    c2cplusplus_linkage_end STRING,
    main STRING
);
CREATE TABLE TE_TFR (
    Included BOOLEAN,
    XlateSemantics BOOLEAN,
    Instance_Based INTEGER,
    Key_Lett STRING,
    Name STRING,
    GeneratedName STRING,
    AbaID UNIQUE_ID,
    Tfr_ID UNIQUE_ID
);
CREATE TABLE TE_THREAD (
    file STRING,
    create STRING,
    mutex_lock STRING,
    mutex_unlock STRING,
    nonbusy_wait STRING,
    nonbusy_wake STRING,
    shutdown STRING,
    enabled BOOLEAN,
    flavor STRING,
    serialize BOOLEAN,
    number_of_threads INTEGER,
    extra_initialization STRING,
    AUTOSAR_enabled STRING
);
CREATE TABLE TE_TIM (
    max_timers STRING,
    keyed_timer_support BOOLEAN,
    recurring_timer_support BOOLEAN,
    timer_event_search_and_destroy BOOLEAN,
    internal_type STRING,
    scope STRING,
    event_name STRING
);
CREATE TABLE TE_TRACE (
    component_msg_start STRING,
    component_msg_end STRING,
    state_txn_start STRING,
    state_txn_end STRING,
    state_txn_event_ignored STRING,
    state_txn_cant_happen STRING,
    oal_trace STRING
);
CREATE TABLE TE_TYPEMAP (
    instance_index_name STRING,
    instance_index_type STRING,
    object_size_name STRING,
    object_size_type STRING,
    object_number_name STRING,
    object_number_type STRING,
    state_number_name STRING,
    state_number_type STRING,
    domain_number_name STRING,
    domain_number_type STRING,
    event_number_name STRING,
    event_number_type STRING,
    event_priority_name STRING,
    event_priority_type STRING,
    event_flags_name STRING,
    event_flags_type STRING,
    poly_return_name STRING,
    poly_return_type STRING,
    SEM_cell_name STRING,
    SEM_cell_type STRING,
    structured_data_types STRING,
    enumeration_info STRING,
    user_supplied_data_types STRING
);
CREATE TABLE TE_UNRELATE (
    one_o_obj UNIQUE_ID,
    oth_o_obj UNIQUE_ID,
    r_rel UNIQUE_ID,
    is_inflexive BOOLEAN,
    relationship_number INTEGER,
    relationship_phrase STRING,
    one_var_name STRING,
    oth_var_name STRING,
    Statement_ID UNIQUE_ID
);
CREATE TABLE TE_UNRELATE_USING (
    one_o_obj UNIQUE_ID,
    oth_o_obj UNIQUE_ID,
    ass_o_obj UNIQUE_ID,
    r_rel UNIQUE_ID,
    is_reflexive BOOLEAN,
    relationship_number INTEGER,
    relationship_phrase STRING,
    one_var_name STRING,
    oth_var_name STRING,
    ass_var_name STRING,
    one_rel_phrase STRING,
    oth_rel_phrase STRING,
    Statement_ID UNIQUE_ID
);
CREATE TABLE TE_VAL (
    OAL STRING,
    buffer STRING,
    dimensions INTEGER,
    array_spec STRING,
    te_dimID UNIQUE_ID,
    Value_ID UNIQUE_ID
);
CREATE TABLE TE_VAR (
    OAL STRING,
    buffer STRING,
    dimensions INTEGER,
    te_dimID UNIQUE_ID,
    array_spec STRING,
    Var_ID UNIQUE_ID
);
CREATE TABLE TE_WHERE (
    WhereKey BOOLEAN,
    select_any_where STRING,
    Oid_ID INTEGER,
    Obj_ID UNIQUE_ID
);
CREATE TABLE TE_WHILE (
    condition STRING,
    Statement_ID UNIQUE_ID
);
CREATE TABLE TM_BUILD (
    package_to_build STRING,
    package_obj_name STRING,
    package_inst_name STRING
);
CREATE TABLE TM_C (
    Package STRING,
    Name STRING,
    xName STRING,
    include_file STRING,
    isChannel BOOLEAN,
    isRealized BOOLEAN,
    te_cID UNIQUE_ID
);
CREATE TABLE TM_ENUMINIT (
    Domain STRING,
    enumeration STRING,
    value STRING
);
CREATE TABLE TM_ENUMVAL (
    Domain STRING,
    enumeration STRING,
    enumerator STRING,
    value STRING
);
CREATE TABLE TM_IF (
    Package STRING,
    Name STRING,
    c_iId UNIQUE_ID
);
CREATE TABLE TM_POINTER (
    Domain STRING,
    DT_name STRING,
    pointer_type STRING,
    include_file STRING
);
CREATE TABLE TM_PRECISION (
    Domain STRING,
    DT_name STRING,
    xName STRING,
    initial_value STRING
);
CREATE TABLE TM_SYNC (
    RegisteredDomain STRING,
    Name STRING,
    IsSafeForInterrupts BOOLEAN,
    IsInitFunction BOOLEAN
);
CREATE TABLE TM_SYSTAG (
    MaxStringLen INTEGER,
    MaxRelExtent INTEGER,
    MaxSelectExtent INTEGER,
    MaxSelfEvents INTEGER,
    MaxNonSelfEvents INTEGER,
    MaxTimers INTEGER,
    MaxInterleavedBridges INTEGER,
    MaxInterleavedBridgeDataSize INTEGER,
    CollectionsFlavor INTEGER,
    PersistInstanceCacheDepth INTEGER,
    PersistLinkCacheDepth INTEGER,
    UnitsToDynamicallyAllocate INTEGER,
    VFB BOOLEAN,
    InstanceLoading BOOLEAN,
    SystemCPortsType STRING,
    AllPortsPoly BOOLEAN,
    StructuredMessaging BOOLEAN,
    NetworkSockets BOOLEAN
);
CREATE TABLE TM_TEMPLATE (
    ID UNIQUE_ID,
    tm_cPackage STRING,
    tm_cName STRING,
    tm_ifPackage STRING,
    tm_ifName STRING,
    current BOOLEAN,
    template_parameters STRING,
    template_parameters_default STRING,
    template_actual_parameters STRING,
    template_default_instantiation STRING
);
CREATE TABLE TM_THREAD (
    enabled BOOLEAN,
    flavor STRING,
    serialize BOOLEAN,
    number_of_threads INTEGER,
    extra_initialization STRING
);
CREATE TABLE TM_TP (
    ID UNIQUE_ID,
    typename STRING,
    Name STRING,
    DefaultValue STRING,
    tm_templateID UNIQUE_ID
);
CREATE TABLE TM_TPV (
    ID UNIQUE_ID,
    instance STRING,
    value STRING,
    tm_tpID UNIQUE_ID,
    te_iirID UNIQUE_ID,
    te_ciID UNIQUE_ID
);
CREATE TABLE UC_BA (
    Assoc_ID UNIQUE_ID,
    Descrip STRING
);
CREATE TABLE UC_E (
    Assoc_ID UNIQUE_ID,
    Descrip STRING
);
CREATE TABLE UC_G (
    Assoc_ID UNIQUE_ID,
    Descrip STRING
);
CREATE TABLE UC_I (
    Assoc_ID UNIQUE_ID,
    Descrip STRING
);
CREATE TABLE UC_UCA (
    Assoc_ID UNIQUE_ID,
    Source_Part_ID UNIQUE_ID,
    Destination_Part_ID UNIQUE_ID
);
CREATE TABLE V_AER (
    Value_ID UNIQUE_ID,
    Root_Value_ID UNIQUE_ID,
    Index_Value_ID UNIQUE_ID
);
CREATE TABLE V_ALV (
    Value_ID UNIQUE_ID,
    Array_Value_ID UNIQUE_ID
);
CREATE TABLE V_AVL (
    Value_ID UNIQUE_ID,
    Root_Value_ID UNIQUE_ID,
    Obj_ID UNIQUE_ID,
    Attr_ID UNIQUE_ID
);
CREATE TABLE V_BIN (
    Value_ID UNIQUE_ID,
    Right_Value_ID UNIQUE_ID,
    Left_Value_ID UNIQUE_ID,
    Operator STRING
);
CREATE TABLE V_BRV (
    Value_ID UNIQUE_ID,
    Brg_ID UNIQUE_ID,
    ParmListOK BOOLEAN,
    externalEntityKeyLettersLineNumber INTEGER,
    externalEntityKeyLettersColumn INTEGER
);
CREATE TABLE V_EDV (
    Value_ID UNIQUE_ID
);
CREATE TABLE V_EPR (
    Value_ID UNIQUE_ID,
    SM_ID UNIQUE_ID,
    SMedi_ID UNIQUE_ID,
    PP_Id UNIQUE_ID
);
CREATE TABLE V_FNV (
    Value_ID UNIQUE_ID,
    Sync_ID UNIQUE_ID,
    ParmListOK BOOLEAN
);
CREATE TABLE V_INS (
    Var_ID UNIQUE_ID,
    Obj_ID UNIQUE_ID
);
CREATE TABLE V_INT (
    Var_ID UNIQUE_ID,
    IsImplicitInFor BOOLEAN,
    Obj_ID UNIQUE_ID
);
CREATE TABLE V_IRF (
    Value_ID UNIQUE_ID,
    Var_ID UNIQUE_ID
);
CREATE TABLE V_ISR (
    Value_ID UNIQUE_ID,
    Var_ID UNIQUE_ID
);
CREATE TABLE V_LBO (
    Value_ID UNIQUE_ID,
    Value STRING
);
CREATE TABLE V_LEN (
    Value_ID UNIQUE_ID,
    Enum_ID UNIQUE_ID,
    dataTypeNameLineNumber INTEGER,
    dataTypeNameColumn INTEGER
);
CREATE TABLE V_LIN (
    Value_ID UNIQUE_ID,
    Value STRING
);
CREATE TABLE V_LOC (
    Id UNIQUE_ID,
    LineNumber INTEGER,
    StartPosition INTEGER,
    EndPosition INTEGER,
    Var_ID UNIQUE_ID
);
CREATE TABLE V_LRL (
    Value_ID UNIQUE_ID,
    Value STRING
);
CREATE TABLE V_LST (
    Value_ID UNIQUE_ID,
    Value STRING
);
CREATE TABLE V_MSV (
    Value_ID UNIQUE_ID,
    PEP_Id UNIQUE_ID,
    REP_Id UNIQUE_ID,
    ParmListOK BOOLEAN,
    ownerNameLineNumber INTEGER,
    ownerNameColumn INTEGER,
    Target_Value_ID UNIQUE_ID
);
CREATE TABLE V_MVL (
    Value_ID UNIQUE_ID,
    Root_Value_ID UNIQUE_ID,
    Member_ID UNIQUE_ID,
    DT_DT_ID UNIQUE_ID
);
CREATE TABLE V_PAR (
    Value_ID UNIQUE_ID,
    Statement_ID UNIQUE_ID,
    Invocation_Value_ID UNIQUE_ID,
    Name STRING,
    Next_Value_ID UNIQUE_ID,
    labelLineNumber INTEGER,
    labelColumn INTEGER,
    Order   INTEGER
);
CREATE TABLE V_PVL (
    Value_ID UNIQUE_ID,
    BParm_ID UNIQUE_ID,
    SParm_ID UNIQUE_ID,
    TParm_ID UNIQUE_ID,
    PP_Id UNIQUE_ID
);
CREATE TABLE V_SCV (
    Value_ID UNIQUE_ID,
    Const_ID UNIQUE_ID,
    DT_ID_Deprecated UNIQUE_ID
);
CREATE TABLE V_SLR (
    Value_ID UNIQUE_ID,
    Obj_ID UNIQUE_ID,
    Attr_ID UNIQUE_ID,
    Op_Value_ID UNIQUE_ID
);
CREATE TABLE V_TRN (
    Var_ID UNIQUE_ID,
    DT_ID UNIQUE_ID,
    Dimensions STRING
);
CREATE TABLE V_TRV (
    Value_ID UNIQUE_ID,
    Tfr_ID UNIQUE_ID,
    Var_ID UNIQUE_ID,
    ParmListOK BOOLEAN,
    modelClassKeyLettersLineNumber INTEGER,
    modelClassKeyLettersColumn INTEGER
);
CREATE TABLE V_TVL (
    Value_ID UNIQUE_ID,
    Var_ID UNIQUE_ID
);
CREATE TABLE V_UNY (
    Value_ID UNIQUE_ID,
    Operand_Value_ID UNIQUE_ID,
    Operator STRING
);
CREATE TABLE V_VAL (
    Value_ID UNIQUE_ID,
    isLValue BOOLEAN,
    isImplicit BOOLEAN,
    LineNumber INTEGER,
    StartPosition INTEGER,
    EndPosition INTEGER,
    firstParameterLabelLineNumber INTEGER,
    firstParameterLabelColumn INTEGER,
    currentLaterParameterLabelLineNumber INTEGER,
    currentLaterParameterLabelColumn INTEGER,
    DT_ID UNIQUE_ID,
    Block_ID UNIQUE_ID,
    buffer STRING
);
CREATE TABLE V_VAR (
    Var_ID UNIQUE_ID,
    Block_ID UNIQUE_ID,
    Name STRING,
    Declared BOOLEAN,
    DT_ID UNIQUE_ID
);
CREATE ROP REF_ID R1000 FROM MC MSG_A (Informal_Msg_ID) TO 1C MSG_M (Msg_ID);
CREATE ROP REF_ID R1001 FROM MC MSG_A (Formal_Msg_ID) TO 1C MSG_M (Msg_ID);
CREATE ROP REF_ID R1007 FROM MC MSG_M (Sender_Part_ID) TO 1C SQ_P (Part_ID);
CREATE ROP REF_ID R1008 FROM MC MSG_M (Receiver_Part_ID) TO 1C SQ_P (Part_ID);
CREATE ROP REF_ID R1009 FROM MC MSG_E (SMevt_ID) TO 1C SM_EVT (SMevt_ID);
CREATE ROP REF_ID R101 FROM MC O_IOBJ (Obj_ID) TO 1C O_OBJ (Obj_ID);
CREATE ROP REF_ID R1010 FROM MC MSG_F (Sync_ID) TO 1C S_SYNC (Sync_ID);
CREATE ROP REF_ID R1011 FROM MC MSG_O (Tfr_ID) TO 1C O_TFR (Tfr_ID);
CREATE ROP REF_ID R1012 FROM MC MSG_B (Brg_ID) TO 1C S_BRG (Brg_ID);
CREATE ROP REF_ID R1013 FROM 1C MSG_BA (Arg_ID) TO 1 MSG_A (Arg_ID);
CREATE ROP REF_ID R1013 FROM 1C MSG_OA (Arg_ID) TO 1 MSG_A (Arg_ID);
CREATE ROP REF_ID R1013 FROM 1C MSG_FA (Arg_ID) TO 1 MSG_A (Arg_ID);
CREATE ROP REF_ID R1013 FROM 1C MSG_EA (Arg_ID) TO 1 MSG_A (Arg_ID);
CREATE ROP REF_ID R1013 FROM 1C MSG_IA (Arg_ID) TO 1 MSG_A (Arg_ID);
CREATE ROP REF_ID R1013 FROM 1C MSG_EPA (Arg_ID) TO 1 MSG_A (Arg_ID);
CREATE ROP REF_ID R1014 FROM MC MSG_BA (BParm_ID) TO 1C S_BPARM (BParm_ID);
CREATE ROP REF_ID R1015 FROM MC MSG_OA (TParm_ID) TO 1C O_TPARM (TParm_ID);
CREATE ROP REF_ID R1016 FROM MC MSG_FA (SParm_ID) TO 1C S_SPARM (SParm_ID);
CREATE ROP REF_ID R1017 FROM MC MSG_EA (SM_ID, SMedi_ID) TO 1C SM_EVTDI (SM_ID, SMedi_ID);
CREATE ROP REF_ID R1018 FROM 1C MSG_AM (Msg_ID) TO 1 MSG_M (Msg_ID);
CREATE ROP REF_ID R1018 FROM 1C MSG_SM (Msg_ID) TO 1 MSG_M (Msg_ID);
CREATE ROP REF_ID R1018 FROM 1C MSG_R (Msg_ID) TO 1 MSG_M (Msg_ID);
CREATE ROP REF_ID R1019 FROM 1C MSG_E (Msg_ID) TO 1 MSG_AM (Msg_ID);
CREATE ROP REF_ID R1019 FROM 1C MSG_IAM (Msg_ID) TO 1 MSG_AM (Msg_ID);
CREATE ROP REF_ID R1019 FROM 1C MSG_SIG (Msg_ID) TO 1 MSG_AM (Msg_ID);
CREATE ROP REF_ID R102 FROM MC O_ATTR (Obj_ID) TO 1 O_OBJ (Obj_ID);
CREATE ROP REF_ID R1020 FROM 1C MSG_F (Msg_ID) TO 1 MSG_SM (Msg_ID);
CREATE ROP REF_ID R1020 FROM 1C MSG_O (Msg_ID) TO 1 MSG_SM (Msg_ID);
CREATE ROP REF_ID R1020 FROM 1C MSG_B (Msg_ID) TO 1 MSG_SM (Msg_ID);
CREATE ROP REF_ID R1020 FROM 1C MSG_ISM (Msg_ID) TO 1 MSG_SM (Msg_ID);
CREATE ROP REF_ID R1020 FROM 1C MSG_IOP (Msg_ID) TO 1 MSG_SM (Msg_ID);
CREATE ROP REF_ID R1021 FROM MC MSG_SIG (Id) TO 1C C_AS (Id);
CREATE ROP REF_ID R1022 FROM MC MSG_IOP (Id) TO 1C C_IO (Id);
CREATE ROP REF_ID R1023 FROM MC MSG_EPA (PP_Id) TO 1C C_PP (PP_Id);
CREATE ROP REF_ID R103 FROM 1C O_ATTR (PAttr_ID, Obj_ID) PHRASE 'succeeds' TO 1C O_ATTR (Attr_ID, Obj_ID) PHRASE 'precedes';
CREATE ROP REF_ID R104 FROM MC O_ID (Obj_ID) TO 1 O_OBJ (Obj_ID);
CREATE ROP REF_ID R105 FROM MC O_OIDA (Oid_ID, Obj_ID) TO 1 O_ID (Oid_ID, Obj_ID);
CREATE ROP REF_ID R105 FROM MC O_OIDA (Attr_ID, Obj_ID) TO 1 O_ATTR (Attr_ID, Obj_ID);
CREATE ROP REF_ID R106 FROM 1C O_BATTR (Attr_ID, Obj_ID) TO 1 O_ATTR (Attr_ID, Obj_ID);
CREATE ROP REF_ID R106 FROM 1C O_RATTR (Attr_ID, Obj_ID) TO 1 O_ATTR (Attr_ID, Obj_ID);
CREATE ROP REF_ID R107 FROM 1C O_DBATTR (Attr_ID, Obj_ID) TO 1 O_BATTR (Attr_ID, Obj_ID);
CREATE ROP REF_ID R107 FROM 1C O_NBATTR (Attr_ID, Obj_ID) TO 1 O_BATTR (Attr_ID, Obj_ID);
CREATE ROP REF_ID R108 FROM M O_REF (Attr_ID, Obj_ID) TO 1 O_RATTR (Attr_ID, Obj_ID);
CREATE ROP REF_ID R109 FROM MC R_RTO (Oid_ID, Obj_ID) TO 1C O_ID (Oid_ID, Obj_ID);
CREATE ROP REF_ID R110 FROM MC O_RTIDA (Oid_ID, OIR_ID, Obj_ID, Rel_ID) TO 1 R_RTO (Oid_ID, OIR_ID, Obj_ID, Rel_ID);
CREATE ROP REF_ID R110 FROM MC O_RTIDA (Attr_ID, Oid_ID, Obj_ID) TO 1 O_OIDA (Attr_ID, Oid_ID, Obj_ID);
CREATE ROP REF_ID R1103 FROM MC A_E (TargetId) TO 1 A_N (Id);
CREATE ROP REF_ID R1104 FROM MC A_E (SourceId) TO 1 A_N (Id);
CREATE ROP REF_ID R1105 FROM 1C A_ACT (Id) TO 1 A_N (Id);
CREATE ROP REF_ID R1105 FROM 1C A_OBJ (Id) TO 1 A_N (Id);
CREATE ROP REF_ID R1105 FROM 1C A_CTL (Id) TO 1 A_N (Id);
CREATE ROP REF_ID R1106 FROM 1C A_FF (Id) TO 1 A_CTL (Id);
CREATE ROP REF_ID R1106 FROM 1C A_AF (Id) TO 1 A_CTL (Id);
CREATE ROP REF_ID R1106 FROM 1C A_INI (Id) TO 1 A_CTL (Id);
CREATE ROP REF_ID R1106 FROM 1C A_DM (Id) TO 1 A_CTL (Id);
CREATE ROP REF_ID R1106 FROM 1C A_FJ (Id) TO 1 A_CTL (Id);
CREATE ROP REF_ID R1107 FROM 1C A_AE (Id) TO 1 A_ACT (Id);
CREATE ROP REF_ID R1107 FROM 1C A_GA (Id) TO 1 A_ACT (Id);
CREATE ROP REF_ID R1107 FROM 1C A_SS (Id) TO 1 A_ACT (Id);
CREATE ROP REF_ID R111 FROM MC O_REF (OIR_ID, Obj_ID, Rel_ID) TO 1 R_RGO (OIR_ID, Obj_ID, Rel_ID);
CREATE ROP REF_ID R111 FROM MC O_REF (RAttr_ID, ROIR_ID, ROid_ID, RObj_ID, Rel_ID) TO 1 O_RTIDA (Attr_ID, OIR_ID, Oid_ID, Obj_ID, Rel_ID);
CREATE ROP REF_ID R1112 FROM 1C A_ATE (Id) TO 1 A_AE (Id);
CREATE ROP REF_ID R1112 FROM 1C A_AEA (Id) TO 1 A_AE (Id);
CREATE ROP REF_ID R112 FROM 1C O_REF (PARef_ID) PHRASE 'succeeds' TO 1C O_REF (ARef_ID) PHRASE 'precedes';
CREATE ROP REF_ID R1128 FROM MC COMM_LNK (Rel_ID) TO 1C R_REL (Rel_ID);
CREATE ROP REF_ID R113 FROM MC O_RATTR (BAttr_ID, BObj_ID) TO 1C O_BATTR (Attr_ID, Obj_ID);
CREATE ROP REF_ID R1133 FROM MC COMM_LNK (Start_Part_ID) TO 1 SQ_P (Part_ID);
CREATE ROP REF_ID R1134 FROM MC COMM_LNK (Destination_Part_ID) TO 1 SQ_P (Part_ID);
CREATE ROP REF_ID R114 FROM MC O_ATTR (DT_ID) TO 1 S_DT (DT_ID);
CREATE ROP REF_ID R115 FROM MC O_TFR (Obj_ID) TO 1 O_OBJ (Obj_ID);
CREATE ROP REF_ID R116 FROM MC O_TFR (DT_ID) TO 1 S_DT (DT_ID);
CREATE ROP REF_ID R117 FROM MC O_TPARM (Tfr_ID) TO 1 O_TFR (Tfr_ID);
CREATE ROP REF_ID R118 FROM MC O_TPARM (DT_ID) TO 1 S_DT (DT_ID);
CREATE ROP REF_ID R120 FROM MC S_DIM (Attr_ID, Obj_ID) TO 1C O_ATTR (Attr_ID, Obj_ID);
CREATE ROP REF_ID R1206 FROM MC UC_UCA (Source_Part_ID) TO 1 SQ_P (Part_ID);
CREATE ROP REF_ID R1207 FROM MC UC_UCA (Destination_Part_ID) TO 1 SQ_P (Part_ID);
CREATE ROP REF_ID R121 FROM MC S_DIM (TParm_ID) TO 1C O_TPARM (TParm_ID);
CREATE ROP REF_ID R1210 FROM 1C UC_E (Assoc_ID) TO 1 UC_UCA (Assoc_ID);
CREATE ROP REF_ID R1210 FROM 1C UC_G (Assoc_ID) TO 1 UC_UCA (Assoc_ID);
CREATE ROP REF_ID R1210 FROM 1C UC_I (Assoc_ID) TO 1 UC_UCA (Assoc_ID);
CREATE ROP REF_ID R1210 FROM 1C UC_BA (Assoc_ID) TO 1 UC_UCA (Assoc_ID);
CREATE ROP REF_ID R122 FROM MC S_DIM (Tfr_ID) TO 1C O_TFR (Tfr_ID);
CREATE ROP REF_ID R123 FROM MC S_IRDT (Obj_ID) TO 1 O_OBJ (Obj_ID);
CREATE ROP REF_ID R124 FROM 1C O_TPARM (Previous_TParm_ID) PHRASE 'succeeds' TO 1C O_TPARM (TParm_ID) PHRASE 'precedes';
CREATE ROP REF_ID R125 FROM 1C O_TFR (Previous_Tfr_ID) PHRASE 'succeeds' TO 1C O_TFR (Tfr_ID) PHRASE 'precedes';
CREATE ROP REF_ID R1401 FROM MC EP_PKG (Sys_ID) TO 1C S_SYS (Sys_ID);
CREATE ROP REF_ID R1405 FROM MC EP_PKG (Direct_Sys_ID) TO 1 S_SYS (Sys_ID);
CREATE ROP REF_ID R1500 FROM MC CNST_SYC (DT_ID) TO 1 S_DT (DT_ID);
CREATE ROP REF_ID R1502 FROM 1C CNST_LFSC (Const_ID) TO 1 CNST_SYC (Const_ID);
CREATE ROP REF_ID R1503 FROM 1C CNST_LSC (Const_ID) TO 1 CNST_LFSC (Const_ID);
CREATE ROP REF_ID R1504 FROM MC CNST_SYC (Constant_Spec_ID) TO 1 CNST_CSP (Constant_Spec_ID);
CREATE ROP REF_ID R1505 FROM 1C CNST_SYC (Previous_Const_ID, Previous_DT_DT_ID_Deprecated) PHRASE 'succeeds' TO 1C CNST_SYC (Const_ID, DT_ID) PHRASE 'precedes';
CREATE ROP REF_ID R17 FROM 1C S_CDT (DT_ID) TO 1 S_DT (DT_ID);
CREATE ROP REF_ID R17 FROM 1C S_UDT (DT_ID) TO 1 S_DT (DT_ID);
CREATE ROP REF_ID R17 FROM 1C S_EDT (DT_ID) TO 1 S_DT (DT_ID);
CREATE ROP REF_ID R17 FROM 1C S_SDT (DT_ID) TO 1 S_DT (DT_ID);
CREATE ROP REF_ID R17 FROM 1C S_IRDT (DT_ID) TO 1 S_DT (DT_ID);
CREATE ROP REF_ID R18 FROM MC S_UDT (CDT_DT_ID) TO 1 S_DT (DT_ID);
CREATE ROP REF_ID R19 FROM MC S_BRG (EE_ID) TO 1 S_EE (EE_ID);
CREATE ROP REF_ID R20 FROM MC S_BRG (DT_ID) TO 1 S_DT (DT_ID);
CREATE ROP REF_ID R2001 FROM MC TE_SWC (GeneratedName) TO 1C TE_CLASS (GeneratedName);
CREATE ROP REF_ID R2002 FROM MC TE_MACT (te_cID) TO 1 TE_C (ID);
CREATE ROP REF_ID R2003 FROM MC TE_DISP (SystemID) TO 1C TE_SYS (SystemID);
CREATE ROP REF_ID R2004 FROM MC TE_QUEUE (Dispatcher_ID) TO 1C TE_DISP (Dispatcher_ID);
CREATE ROP REF_ID R2005 FROM MC TE_PO (te_cID) TO 1 TE_C (ID);
CREATE ROP REF_ID R2006 FROM MC TE_MACT (te_poID) TO 1 TE_PO (ID);
CREATE ROP REF_ID R2007 FROM MC TE_PO (c_iId) TO 1 C_I (Id);
CREATE ROP REF_ID R2008 FROM MC TE_CI (te_cID) TO 1 TE_C (ID);
CREATE ROP REF_ID R2009 FROM 1C TE_CI (cl_icId) TO 1 CL_IC (Id);
CREATE ROP REF_ID R201 FROM M R_OIR (Rel_ID) TO 1 R_REL (Rel_ID);
CREATE ROP REF_ID R201 FROM MC R_OIR (Obj_ID) TO 1 O_OBJ (Obj_ID);
CREATE ROP REF_ID R2010 FROM 1C TE_ACT (AbaID) TO 1 TE_ABA (AbaID);
CREATE ROP REF_ID R2010 FROM 1C TE_SYNC (AbaID) TO 1 TE_ABA (AbaID);
CREATE ROP REF_ID R2010 FROM 1C TE_TFR (AbaID) TO 1 TE_ABA (AbaID);
CREATE ROP REF_ID R2010 FROM 1C TE_BRG (AbaID) TO 1 TE_ABA (AbaID);
CREATE ROP REF_ID R2010 FROM 1C TE_DBATTR (AbaID) TO 1 TE_ABA (AbaID);
CREATE ROP REF_ID R2010 FROM 1C TE_MACT (AbaID) TO 1 TE_ABA (AbaID);
CREATE ROP REF_ID R2011 FROM 1C TE_BLK (AbaID) TO 1C TE_ABA (AbaID);
CREATE ROP REF_ID R2012 FROM 1C TE_SMT (next_Statement_ID) PHRASE 'precedes' TO 1C TE_SMT (Statement_ID) PHRASE 'succeeds';
CREATE ROP REF_ID R2013 FROM 1C TE_IIR (cl_iirId) TO 1C CL_IIR (Id);
CREATE ROP REF_ID R2014 FROM 1C TE_BLK (first_Statement_ID) TO 1C TE_SMT (Statement_ID);
CREATE ROP REF_ID R2015 FROM 1C TE_SMT (sub_Block_ID) TO 1C TE_BLK (Block_ID);
CREATE ROP REF_ID R2016 FROM 1C TE_BLK (Block_ID) TO 1 ACT_BLK (Block_ID);
CREATE ROP REF_ID R2017 FROM 1C TE_C (next_ID) PHRASE 'precedes' TO 1C TE_C (ID) PHRASE 'succeeds';
CREATE ROP REF_ID R2018 FROM 1C TE_SYS (Sys_ID) TO 1C S_SYS (Sys_ID);
CREATE ROP REF_ID R2019 FROM 1C TE_CLASS (Obj_ID) TO 1 O_OBJ (Obj_ID);
CREATE ROP REF_ID R202 FROM MC R_OIR (IObj_ID) TO 1C O_IOBJ (IObj_ID);
CREATE ROP REF_ID R2020 FROM 1C TE_EE (EE_ID) TO 1 S_EE (EE_ID);
CREATE ROP REF_ID R2021 FROM 1C TE_DT (DT_ID) TO 1 S_DT (DT_ID);
CREATE ROP REF_ID R2022 FROM 1C TE_ACT (SM_ID, Act_ID) TO 1 SM_ACT (SM_ID, Act_ID);
CREATE ROP REF_ID R2023 FROM 1C TE_SYNC (Sync_ID) TO 1 S_SYNC (Sync_ID);
CREATE ROP REF_ID R2024 FROM 1C TE_TFR (Tfr_ID) TO 1 O_TFR (Tfr_ID);
CREATE ROP REF_ID R2025 FROM 1C TE_BRG (Brg_ID) TO 1 S_BRG (Brg_ID);
CREATE ROP REF_ID R2026 FROM 1C TE_DBATTR (Attr_ID, Obj_ID) TO 1 O_DBATTR (Attr_ID, Obj_ID);
CREATE ROP REF_ID R2027 FROM 1C TE_ENUM (Enum_ID) TO 1 S_ENUM (Enum_ID);
CREATE ROP REF_ID R2028 FROM 1C TE_PARM (BParm_ID) TO 1C S_BPARM (BParm_ID);
CREATE ROP REF_ID R2029 FROM 1C TE_PARM (TParm_ID) TO 1C O_TPARM (TParm_ID);
CREATE ROP REF_ID R203 FROM 1C R_RTO (OIR_ID, Obj_ID, Rel_ID) TO 1 R_OIR (OIR_ID, Obj_ID, Rel_ID);
CREATE ROP REF_ID R203 FROM 1C R_RGO (OIR_ID, Obj_ID, Rel_ID) TO 1 R_OIR (OIR_ID, Obj_ID, Rel_ID);
CREATE ROP REF_ID R203 FROM 1C R_CONE (OIR_ID, Obj_ID, Rel_ID) TO 1 R_OIR (OIR_ID, Obj_ID, Rel_ID);
CREATE ROP REF_ID R203 FROM 1C R_COTH (OIR_ID, Obj_ID, Rel_ID) TO 1 R_OIR (OIR_ID, Obj_ID, Rel_ID);
CREATE ROP REF_ID R2030 FROM 1C TE_PARM (SParm_ID) TO 1C S_SPARM (SParm_ID);
CREATE ROP REF_ID R2031 FROM 1C TE_PARM (SM_ID, SMedi_ID) TO 1C SM_EVTDI (SM_ID, SMedi_ID);
CREATE ROP REF_ID R2032 FROM 1C TE_WHERE (Oid_ID, Obj_ID) TO 1 O_ID (Oid_ID, Obj_ID);
CREATE ROP REF_ID R2033 FROM 1C TE_ATTR (Attr_ID, Obj_ID) TO 1 O_ATTR (Attr_ID, Obj_ID);
CREATE ROP REF_ID R2034 FROM 1C TE_REL (Rel_ID) TO 1 R_REL (Rel_ID);
CREATE ROP REF_ID R2035 FROM 1C TE_OIR (OIR_ID, Obj_ID, Rel_ID) TO 1 R_OIR (OIR_ID, Obj_ID, Rel_ID);
CREATE ROP REF_ID R2036 FROM 1C TE_EVT (SMevt_ID) TO 1 SM_EVT (SMevt_ID);
CREATE ROP REF_ID R2037 FROM 1C TE_STATE (SM_ID, SMstt_ID) TO 1 SM_STATE (SM_ID, SMstt_ID);
CREATE ROP REF_ID R2038 FROM 1C TE_SMT (Statement_ID) TO 1 ACT_SMT (Statement_ID);
CREATE ROP REF_ID R2039 FROM 1C TE_VAR (Var_ID) TO 1 V_VAR (Var_ID);
CREATE ROP REF_ID R204 FROM 1C R_SUPER (OIR_ID, Obj_ID, Rel_ID) TO 1 R_RTO (OIR_ID, Obj_ID, Rel_ID);
CREATE ROP REF_ID R204 FROM 1C R_PART (OIR_ID, Obj_ID, Rel_ID) TO 1 R_RTO (OIR_ID, Obj_ID, Rel_ID);
CREATE ROP REF_ID R204 FROM 1C R_AONE (OIR_ID, Obj_ID, Rel_ID) TO 1 R_RTO (OIR_ID, Obj_ID, Rel_ID);
CREATE ROP REF_ID R204 FROM 1C R_AOTH (OIR_ID, Obj_ID, Rel_ID) TO 1 R_RTO (OIR_ID, Obj_ID, Rel_ID);
CREATE ROP REF_ID R2040 FROM 1C TE_VAL (Value_ID) TO 1 V_VAL (Value_ID);
CREATE ROP REF_ID R2041 FROM 1C TE_PARM (nextID) PHRASE 'precedes' TO 1C TE_PARM (ID) PHRASE 'succeeds';
CREATE ROP REF_ID R2042 FROM 1C TE_LNK (Link_ID) TO 1 ACT_LNK (Link_ID);
CREATE ROP REF_ID R2043 FROM 1C TE_SM (SM_ID) TO 1 SM_SM (SM_ID);
CREATE ROP REF_ID R2044 FROM 1C TE_PO (c_poId) TO 1 C_PO (Id);
CREATE ROP REF_ID R2045 FROM 1C TE_SLIST (flavor) TO 1 TE_CONTAINER (flavor);
CREATE ROP REF_ID R2045 FROM 1C TE_DLIST (flavor) TO 1 TE_CONTAINER (flavor);
CREATE ROP REF_ID R2046 FROM 1C TE_IIR (c_irId) TO 1C C_IR (Id);
CREATE ROP REF_ID R2047 FROM 1C TE_MBR (Member_ID, DT_ID) TO 1 S_MBR (Member_ID, Parent_DT_DT_ID);
CREATE ROP REF_ID R2048 FROM 1C TE_PARM (PP_Id) TO 1C C_PP (PP_Id);
CREATE ROP REF_ID R2049 FROM MC TE_PARM (te_dtID) TO 1 TE_DT (ID);
CREATE ROP REF_ID R205 FROM 1C R_SUB (OIR_ID, Obj_ID, Rel_ID) TO 1 R_RGO (OIR_ID, Obj_ID, Rel_ID);
CREATE ROP REF_ID R205 FROM 1C R_FORM (OIR_ID, Obj_ID, Rel_ID) TO 1 R_RGO (OIR_ID, Obj_ID, Rel_ID);
CREATE ROP REF_ID R205 FROM 1C R_ASSR (OIR_ID, Obj_ID, Rel_ID) TO 1 R_RGO (OIR_ID, Obj_ID, Rel_ID);
CREATE ROP REF_ID R2050 FROM 1C TE_MACT (SPR_POId) TO 1C SPR_PO (Id);
CREATE ROP REF_ID R2051 FROM 1C TE_MACT (SPR_PSId) TO 1C SPR_PS (Id);
CREATE ROP REF_ID R2052 FROM 1C TE_MACT (SPR_ROId) TO 1C SPR_RO (Id);
CREATE ROP REF_ID R2053 FROM 1C TE_MACT (SPR_RSId) TO 1C SPR_RS (Id);
CREATE ROP REF_ID R2054 FROM 1C TE_C (cId) TO 1 C_C (Id);
CREATE ROP REF_ID R2055 FROM 1C TE_ATTR (te_dimID) TO 1C TE_DIM (te_dimID);
CREATE ROP REF_ID R2056 FROM MC TE_PARM (te_dimID) TO 1C TE_DIM (te_dimID);
CREATE ROP REF_ID R2057 FROM 1C TE_VAR (te_dimID) TO 1C TE_DIM (te_dimID);
CREATE ROP REF_ID R2058 FROM 1C TE_ABA (te_dimID) TO 1C TE_DIM (te_dimID);
CREATE ROP REF_ID R2059 FROM 1C TE_MBR (te_dimID) TO 1C TE_DIM (te_dimID);
CREATE ROP REF_ID R206 FROM 1C R_SIMP (Rel_ID) TO 1 R_REL (Rel_ID);
CREATE ROP REF_ID R206 FROM 1C R_COMP (Rel_ID) TO 1 R_REL (Rel_ID);
CREATE ROP REF_ID R206 FROM 1C R_ASSOC (Rel_ID) TO 1 R_REL (Rel_ID);
CREATE ROP REF_ID R206 FROM 1C R_SUBSUP (Rel_ID) TO 1 R_REL (Rel_ID);
CREATE ROP REF_ID R2060 FROM 1C TE_DIM (next_te_dimID) PHRASE 'precedes' TO 1C TE_DIM (te_dimID) PHRASE 'succeeds';
CREATE ROP REF_ID R2061 FROM MC TE_ATTR (te_classGeneratedName) TO 1 TE_CLASS (GeneratedName);
CREATE ROP REF_ID R2062 FROM MC TE_PARM (AbaID) TO 1C TE_ABA (AbaID);
CREATE ROP REF_ID R2063 FROM 1C TE_PAR (Value_ID) TO 1 V_PAR (Value_ID);
CREATE ROP REF_ID R2064 FROM MC TE_CLASS (te_cID) TO 1 TE_C (ID);
CREATE ROP REF_ID R2065 FROM MC TE_C (SystemID) TO 1 TE_SYS (SystemID);
CREATE ROP REF_ID R2066 FROM MC TE_EXTENT () TO 1 TE_SET ();
CREATE ROP REF_ID R2066 FROM 1 TE_EXTENT () TO 1 TE_INSTANCE ();
CREATE ROP REF_ID R2067 FROM 1C TE_MBR (previousID) PHRASE 'succeeds' TO 1C TE_MBR (ID) PHRASE 'precedes';
CREATE ROP REF_ID R2068 FROM 1 TE_MBR (te_dtID) TO 1 TE_DT (ID);
CREATE ROP REF_ID R2069 FROM 1C TE_ASSIGN (Statement_ID) TO 1 TE_SMT (Statement_ID);
CREATE ROP REF_ID R2069 FROM 1C TE_FOR (Statement_ID) TO 1 TE_SMT (Statement_ID);
CREATE ROP REF_ID R2069 FROM 1C TE_ELSE (Statement_ID) TO 1 TE_SMT (Statement_ID);
CREATE ROP REF_ID R2069 FROM 1C TE_DELETE_INSTANCE (Statement_ID) TO 1 TE_SMT (Statement_ID);
CREATE ROP REF_ID R2069 FROM 1C TE_IF (Statement_ID) TO 1 TE_SMT (Statement_ID);
CREATE ROP REF_ID R2069 FROM 1C TE_WHILE (Statement_ID) TO 1 TE_SMT (Statement_ID);
CREATE ROP REF_ID R2069 FROM 1C TE_CREATE_INSTANCE (Statement_ID) TO 1 TE_SMT (Statement_ID);
CREATE ROP REF_ID R2069 FROM 1C TE_ELIF (Statement_ID) TO 1 TE_SMT (Statement_ID);
CREATE ROP REF_ID R2069 FROM 1C TE_CREATE_EVENT (Statement_ID) TO 1 TE_SMT (Statement_ID);
CREATE ROP REF_ID R2069 FROM 1C TE_UNRELATE_USING (Statement_ID) TO 1 TE_SMT (Statement_ID);
CREATE ROP REF_ID R2069 FROM 1C TE_UNRELATE (Statement_ID) TO 1 TE_SMT (Statement_ID);
CREATE ROP REF_ID R2069 FROM 1C TE_RELATE (Statement_ID) TO 1 TE_SMT (Statement_ID);
CREATE ROP REF_ID R2069 FROM 1C TE_SELECT (Statement_ID) TO 1 TE_SMT (Statement_ID);
CREATE ROP REF_ID R2069 FROM 1C TE_RELATE_USING (Statement_ID) TO 1 TE_SMT (Statement_ID);
CREATE ROP REF_ID R2069 FROM 1C TE_SELECT_WHERE (Statement_ID) TO 1 TE_SMT (Statement_ID);
CREATE ROP REF_ID R2069 FROM 1C TE_SELECT_RELATED (Statement_ID) TO 1 TE_SMT (Statement_ID);
CREATE ROP REF_ID R2069 FROM 1C TE_GENERATE_PRECREATED_EVENT (Statement_ID) TO 1 TE_SMT (Statement_ID);
CREATE ROP REF_ID R2069 FROM 1C TE_EVENT_PARAMETERS (Statement_ID) TO 1 TE_SMT (Statement_ID);
CREATE ROP REF_ID R2069 FROM 1C TE_GENERATE (Statement_ID) TO 1 TE_SMT (Statement_ID);
CREATE ROP REF_ID R2069 FROM 1C TE_GENERATE_CREATOR_EVENT (Statement_ID) TO 1 TE_SMT (Statement_ID);
CREATE ROP REF_ID R2069 FROM 1C TE_GENERATE_TO_CLASS (Statement_ID) TO 1 TE_SMT (Statement_ID);
CREATE ROP REF_ID R2069 FROM 1C TE_SGN (Statement_ID) TO 1 TE_SMT (Statement_ID);
CREATE ROP REF_ID R2069 FROM 1C TE_IOP (Statement_ID) TO 1 TE_SMT (Statement_ID);
CREATE ROP REF_ID R2069 FROM 1C TE_OPERATION (Statement_ID) TO 1 TE_SMT (Statement_ID);
CREATE ROP REF_ID R2069 FROM 1C TE_BRIDGE (Statement_ID) TO 1 TE_SMT (Statement_ID);
CREATE ROP REF_ID R2069 FROM 1C TE_FUNCTION (Statement_ID) TO 1 TE_SMT (Statement_ID);
CREATE ROP REF_ID R2069 FROM 1C TE_RETURN (Statement_ID) TO 1 TE_SMT (Statement_ID);
CREATE ROP REF_ID R2069 FROM 1C TE_CONTROL (Statement_ID) TO 1 TE_SMT (Statement_ID);
CREATE ROP REF_ID R2069 FROM 1C TE_BREAK (Statement_ID) TO 1 TE_SMT (Statement_ID);
CREATE ROP REF_ID R2069 FROM 1C TE_CONTINUE (Statement_ID) TO 1 TE_SMT (Statement_ID);
CREATE ROP REF_ID R207 FROM M R_PART (Rel_ID) TO 1 R_SIMP (Rel_ID);
CREATE ROP REF_ID R2070 FROM 1C TE_SELECT_RELATED (starting_Value_ID) TO 1 TE_VAL (Value_ID);
CREATE ROP REF_ID R2071 FROM MC TE_EVT (te_smID) TO 1 TE_SM (ID);
CREATE ROP REF_ID R2072 FROM MC TE_SM (te_classGeneratedName) TO 1 TE_CLASS (GeneratedName);
CREATE ROP REF_ID R2073 FROM 1 TE_SELECT_RELATED (link_ID) TO 1 TE_LNK (ID);
CREATE ROP REF_ID R2074 FROM 1C TE_SELECT_RELATED (where_clause_Value_ID) TO 1C TE_VAL (Value_ID);
CREATE ROP REF_ID R2075 FROM 1C TE_LNK (next_ID) PHRASE 'precedes' TO 1C TE_LNK (ID) PHRASE 'succeeds';
CREATE ROP REF_ID R2076 FROM MC TE_LNK (te_classGeneratedName) TO 1 TE_CLASS (GeneratedName);
CREATE ROP REF_ID R2077 FROM 1C TE_SELECT_RELATED (te_classGeneratedName) TO 1 TE_CLASS (GeneratedName);
CREATE ROP REF_ID R2078 FROM MC TE_SMT (parent_Block_ID) TO 1 TE_BLK (Block_ID);
CREATE ROP REF_ID R2079 FROM MC TE_VAL (te_dimID) TO 1C TE_DIM (te_dimID);
CREATE ROP REF_ID R208 FROM 1C R_FORM (Rel_ID) TO 1 R_SIMP (Rel_ID);
CREATE ROP REF_ID R2080 FROM M TE_IIR (te_poID) TO 1C TE_PO (ID);
CREATE ROP REF_ID R2081 FROM MC TE_IIR (provider_te_iirID) PHRASE 'requires or delegates' TO 1C TE_IIR (ID) PHRASE 'provides or is delegated';
CREATE ROP REF_ID R2082 FROM MC TE_MACT (te_evtID) TO 1C TE_EVT (ID);
CREATE ROP REF_ID R2083 FROM 1C TE_MACT (nextID) PHRASE 'precedes' TO 1C TE_MACT (ID) PHRASE 'succeeds';
CREATE ROP REF_ID R2084 FROM MC TE_SYNC (te_cID) TO 1 TE_C (ID);
CREATE ROP REF_ID R2085 FROM MC TE_EE (te_cID) TO 1C TE_C (ID);
CREATE ROP REF_ID R2086 FROM MC TE_DT (te_cID) TO 1C TE_C (ID);
CREATE ROP REF_ID R2087 FROM 1C TE_ATTR (prevID) PHRASE 'succeeds' TO 1C TE_ATTR (ID) PHRASE 'precedes';
CREATE ROP REF_ID R2088 FROM MC TE_ABA (te_cID) TO 1C TE_C (ID);
CREATE ROP REF_ID R2089 FROM MC TE_BRG (EE_ID) TO 1 TE_EE (EE_ID);
CREATE ROP REF_ID R209 FROM 1 R_AONE (Rel_ID) TO 1 R_ASSOC (Rel_ID);
CREATE ROP REF_ID R2090 FROM 1 TE_DCI (te_cID) TO 1 TE_C (ID);
CREATE ROP REF_ID R2091 FROM MC TE_PAR (te_parmID) TO 1 TE_PARM (ID);
CREATE ROP REF_ID R2092 FROM 1C TE_CLASS (nextID) PHRASE 'precedes' TO 1C TE_CLASS (ID) PHRASE 'succeeds';
CREATE ROP REF_ID R2093 FROM 1C TE_SELECT_RELATED (result_Var_ID) TO 1 TE_VAR (Var_ID);
CREATE ROP REF_ID R2094 FROM 1C TE_SELECT_RELATED (starting_Var_ID) TO 1 TE_VAR (Var_ID);
CREATE ROP REF_ID R2095 FROM 1C TE_SYNC (nextID) PHRASE 'precedes' TO 1C TE_SYNC (ID) PHRASE 'succeeds';
CREATE ROP REF_ID R2096 FROM 1C TE_EE (nextID) PHRASE 'precedes' TO 1C TE_EE (ID) PHRASE 'succeeds';
CREATE ROP REF_ID R2097 FROM 1 TE_C (first_syncID) TO 1C TE_SYNC (ID);
CREATE ROP REF_ID R2098 FROM 1C TE_C (first_eeID) TO 1C TE_EE (ID);
CREATE ROP REF_ID R2099 FROM 1C TE_PO (first_te_mactID) TO 1C TE_MACT (ID);
CREATE ROP REF_ID R21 FROM MC S_BPARM (Brg_ID) TO 1 S_BRG (Brg_ID);
CREATE ROP REF_ID R210 FROM 1 R_AOTH (Rel_ID) TO 1 R_ASSOC (Rel_ID);
CREATE ROP REF_ID R211 FROM 1 R_ASSR (Rel_ID) TO 1 R_ASSOC (Rel_ID);
CREATE ROP REF_ID R212 FROM 1 R_SUPER (Rel_ID) TO 1 R_SUBSUP (Rel_ID);
CREATE ROP REF_ID R213 FROM MC R_SUB (Rel_ID) TO 1 R_SUBSUP (Rel_ID);
CREATE ROP REF_ID R214 FROM 1 R_CONE (Rel_ID) TO 1 R_COMP (Rel_ID);
CREATE ROP REF_ID R215 FROM 1 R_COTH (Rel_ID) TO 1 R_COMP (Rel_ID);
CREATE ROP REF_ID R22 FROM MC S_BPARM (DT_ID) TO 1 S_DT (DT_ID);
CREATE ROP REF_ID R24 FROM MC S_SPARM (Sync_ID) TO 1 S_SYNC (Sync_ID);
CREATE ROP REF_ID R25 FROM MC S_SYNC (DT_ID) TO 1 S_DT (DT_ID);
CREATE ROP REF_ID R26 FROM MC S_SPARM (DT_ID) TO 1 S_DT (DT_ID);
CREATE ROP REF_ID R27 FROM MC S_ENUM (EDT_DT_ID) TO 1 S_EDT (DT_ID);
CREATE ROP REF_ID R2800 FROM 1C TM_TEMPLATE (tm_cPackage, tm_cName) TO 1C TM_C (Package, Name);
CREATE ROP REF_ID R2801 FROM MC TM_TP (tm_templateID) TO 1 TM_TEMPLATE (ID);
CREATE ROP REF_ID R2802 FROM 1C TM_TEMPLATE (tm_ifPackage, tm_ifName) TO 1C TM_IF (Package, Name);
CREATE ROP REF_ID R2804 FROM 1C TM_C (te_cID) TO 1C TE_C (ID);
CREATE ROP REF_ID R2805 FROM MC TM_TPV (te_ciID) TO 1C TE_CI (ID);
CREATE ROP REF_ID R2806 FROM MC TM_TPV (te_iirID) TO 1C TE_IIR (ID);
CREATE ROP REF_ID R2807 FROM 1C TM_IF (c_iId) TO 1 C_I (Id);
CREATE ROP REF_ID R2808 FROM MC TM_TPV (tm_tpID) TO 1 TM_TP (ID);
CREATE ROP REF_ID R2901 FROM MC I_LNK (fromInst_ID, Rel_ID) TO 1 I_LIP (Inst_ID, Rel_ID);
CREATE ROP REF_ID R2902 FROM MC I_LNK (toInst_ID, Rel_ID) TO 1 I_LIP (Inst_ID, Rel_ID);
CREATE ROP REF_ID R2903 FROM MC I_LNK (assocInst_ID, Rel_ID) TO 1C I_LIP (Inst_ID, Rel_ID);
CREATE ROP REF_ID R2904 FROM MC I_LNK (Rel_ID) TO 1 R_REL (Rel_ID);
CREATE ROP REF_ID R2906 FROM MC I_EVI (SMevt_ID) TO 1 SM_EVT (SMevt_ID);
CREATE ROP REF_ID R2907 FROM MC I_EVI (Target_Inst_ID) TO 1C I_INS (Inst_ID);
CREATE ROP REF_ID R2908 FROM 1C I_EVI (nextEvent_ID) PHRASE 'will be processed after' TO 1C I_EVI (Event_ID) PHRASE 'will be processed before';
CREATE ROP REF_ID R2909 FROM MC I_AVL (Inst_ID) TO 1 I_INS (Inst_ID);
CREATE ROP REF_ID R2910 FROM MC I_AVL (Attr_ID, Obj_ID) TO 1 O_ATTR (Attr_ID, Obj_ID);
CREATE ROP REF_ID R2915 FROM MC I_INS (SM_ID, SMstt_ID) TO 1C SM_STATE (SM_ID, SMstt_ID);
CREATE ROP REF_ID R2923 FROM MC I_BSF (Block_ID) TO 1 ACT_BLK (Block_ID);
CREATE ROP REF_ID R2923 FROM MC I_BSF (Stack_Frame_ID) TO 1 I_STF (Stack_Frame_ID);
CREATE ROP REF_ID R2928 FROM 1C I_STF (Child_Stack_Frame_ID) PHRASE 'next context' TO 1C I_STF (Stack_Frame_ID) PHRASE 'previous context';
CREATE ROP REF_ID R2929 FROM 1C I_STF (Top_Stack_Frame_Stack_ID) TO 1C I_STACK (Stack_ID);
CREATE ROP REF_ID R2930 FROM 1 I_STACK (Execution_Engine_ID) TO 1C I_EXE (Execution_Engine_ID);
CREATE ROP REF_ID R2933 FROM MC I_DIV (Event_ID) TO 1 I_EVI (Event_ID);
CREATE ROP REF_ID R2934 FROM MC I_DIV (SM_ID, SMedi_ID) TO 1C SM_EVTDI (SM_ID, SMedi_ID);
CREATE ROP REF_ID R2935 FROM MC I_EVI (Target_Inst_ID) TO 1C I_INS (Inst_ID);
CREATE ROP REF_ID R2937 FROM MC I_EVI (Sent_By_Inst_ID) TO 1C I_INS (Inst_ID);
CREATE ROP REF_ID R2939 FROM 1C I_EVI (next_self_Event_ID) PHRASE 'will be processed before' TO 1C I_EVI (Event_ID) PHRASE 'will be processed after';
CREATE ROP REF_ID R2940 FROM 1C I_TIM (Event_ID) TO 1C I_EVI (Event_ID);
CREATE ROP REF_ID R2941 FROM MC I_BSF (Statement_ID) TO 1 ACT_SMT (Statement_ID);
CREATE ROP REF_ID R2943 FROM MC I_STF (Stack_ID) TO 1C I_STACK (Stack_ID);
CREATE ROP REF_ID R2944 FROM MC I_EQE (Execution_Engine_ID) TO 1 I_EXE (Execution_Engine_ID);
CREATE ROP REF_ID R2944 FROM 1C I_EQE (Event_ID) TO 1 I_EVI (Event_ID);
CREATE ROP REF_ID R2945 FROM 1C I_EQE (Next_Event_Queue_Entry_ID) PHRASE 'follows' TO 1C I_EQE (Event_Queue_Entry_ID) PHRASE 'precedes';
CREATE ROP REF_ID R2946 FROM MC I_SQE (Execution_Engine_ID) TO 1 I_EXE (Execution_Engine_ID);
CREATE ROP REF_ID R2946 FROM 1C I_SQE (Event_ID) TO 1 I_EVI (Event_ID);
CREATE ROP REF_ID R2947 FROM 1C I_SQE (Next_Self_Queue_Entry_ID) PHRASE 'follows' TO 1C I_SQE (Self_Queue_Entry_ID) PHRASE 'precedes';
CREATE ROP REF_ID R2949 FROM MC I_MON (Execution_Engine_ID) TO 1 I_EXE (Execution_Engine_ID);
CREATE ROP REF_ID R2949 FROM 1C I_MON (Inst_ID) TO 1 I_INS (Inst_ID);
CREATE ROP REF_ID R2951 FROM MC I_VSF (Stack_Frame_ID) TO 1 I_STF (Stack_Frame_ID);
CREATE ROP REF_ID R2953 FROM MC I_INS (Trans_ID, SM_ID) TO 1 SM_TXN (Trans_ID, SM_ID);
CREATE ROP REF_ID R2954 FROM MC I_STF (Inst_ID) TO 1C I_INS (Inst_ID);
CREATE ROP REF_ID R2955 FROM MC I_EXE (Component_Id) TO 1C C_C (Id);
CREATE ROP REF_ID R2956 FROM MC I_DIV (PP_Id) TO 1C C_PP (PP_Id);
CREATE ROP REF_ID R2957 FROM MC I_INS (Execution_Engine_ID) TO 1 I_EXE (Execution_Engine_ID);
CREATE ROP REF_ID R2958 FROM MC I_LIP (Inst_ID) TO 1 I_INS (Inst_ID);
CREATE ROP REF_ID R2959 FROM MC I_LIP (Rel_ID) TO 1 R_REL (Rel_ID);
CREATE ROP REF_ID R2963 FROM MC I_EXE (ImportedComponent_Id) TO 1C CL_IC (Id);
CREATE ROP REF_ID R2964 FROM MC I_EVI (Execution_Engine_ID) TO 1 I_EXE (Execution_Engine_ID);
CREATE ROP REF_ID R2965 FROM 1C I_STF (Blocking_Stack_Frame_ID) PHRASE 'blocked by' TO 1C I_STF (Stack_Frame_ID) PHRASE 'blocks';
CREATE ROP REF_ID R2966 FROM MC I_ICQE (Stack_ID) TO 1 I_STACK (Stack_ID);
CREATE ROP REF_ID R2966 FROM 1C I_ICQE (Stack_Frame_ID) TO 1 I_STF (Stack_Frame_ID);
CREATE ROP REF_ID R2967 FROM MC I_STF (Value_Q_Stack_ID) TO 1C I_STACK (Stack_ID);
CREATE ROP REF_ID R2968 FROM MC I_RCH (Execution_Engine_ID) PHRASE 'is interface provider to' TO 1 I_EXE (Execution_Engine_ID) PHRASE 'is interface requirer of';
CREATE ROP REF_ID R2968 FROM MC I_RCH (other_Execution_Engine_ID) PHRASE 'is interface requirer of' TO 1 I_EXE (Execution_Engine_ID) PHRASE 'is interface provider to';
CREATE ROP REF_ID R2969 FROM MC I_RCH (Satisfaction_Id) TO 1C C_SF (Id);
CREATE ROP REF_ID R2970 FROM 1C I_EXE (Package_ID) TO 1C EP_PKG (Package_ID);
CREATE ROP REF_ID R2972 FROM MC I_RCH (Delegation_Id) TO 1C C_DG (Id);
CREATE ROP REF_ID R2973 FROM 1C I_RCH (Next_provider_Channel_Id) PHRASE 'requirer' TO 1C I_RCH (Channel_Id) PHRASE 'provider';
CREATE ROP REF_ID R2974 FROM 1C I_CIN (Container_ID) TO 1 I_EXE (Execution_Engine_ID);
CREATE ROP REF_ID R2975 FROM MC I_EXE (Container_ID) TO 1C I_CIN (Container_ID);
CREATE ROP REF_ID R2976 FROM MC I_EVI (Originating_Execution_Engine_ID) TO 1C I_EXE (Execution_Engine_ID);
CREATE ROP REF_ID R2977 FROM MC I_ICQE (Execution_Engine_ID) TO 1 I_EXE (Execution_Engine_ID);
CREATE ROP REF_ID R2978 FROM MC I_VSF (Value_ID) TO 1C V_VAL (Value_ID);
CREATE ROP REF_ID R4002 FROM MC C_SF (Requirement_Id) TO 1 C_R (Requirement_Id);
CREATE ROP REF_ID R4002 FROM MC C_SF (Provision_Id) TO 1 C_P (Provision_Id);
CREATE ROP REF_ID R4003 FROM MC C_EP (Interface_Id) TO 1 C_I (Id);
CREATE ROP REF_ID R4004 FROM 1C C_IO (Id) TO 1 C_EP (Id);
CREATE ROP REF_ID R4004 FROM 1C C_AS (Id) TO 1 C_EP (Id);
CREATE ROP REF_ID R4006 FROM MC C_PP (Signal_Id) TO 1 C_EP (Id);
CREATE ROP REF_ID R4007 FROM MC C_PP (DT_ID) TO 1 S_DT (DT_ID);
CREATE ROP REF_ID R4008 FROM MC C_IO (DT_ID) TO 1 S_DT (DT_ID);
CREATE ROP REF_ID R4009 FROM 1C C_R (Requirement_Id) TO 1 C_IR (Id);
CREATE ROP REF_ID R4009 FROM 1C C_P (Provision_Id) TO 1 C_IR (Id);
CREATE ROP REF_ID R4010 FROM MC C_PO (Component_Id) TO 1 C_C (Id);
CREATE ROP REF_ID R4012 FROM MC C_IR (Formal_Interface_Id) TO 1C C_I (Id);
CREATE ROP REF_ID R4013 FROM MC C_RID (Reference_Id) TO 1 C_IR (Id);
CREATE ROP REF_ID R4013 FROM MC C_RID (Delegation_Id) TO 1 C_DG (Id);
CREATE ROP REF_ID R4014 FROM MC C_IR (Delegation_Id) TO 1C C_DG (Id);
CREATE ROP REF_ID R4016 FROM MC C_IR (Port_Id) TO 1 C_PO (Id);
CREATE ROP REF_ID R4017 FROM MC S_DIM (PP_Id) TO 1C C_PP (PP_Id);
CREATE ROP REF_ID R4018 FROM MC S_DIM (Id) TO 1C C_IO (Id);
CREATE ROP REF_ID R4019 FROM 1C C_IO (Previous_Id) PHRASE 'succeeds' TO 1C C_IO (Id) PHRASE 'precedes';
CREATE ROP REF_ID R4020 FROM 1C C_AS (Previous_Id) PHRASE 'succeeds' TO 1C C_AS (Id) PHRASE 'precedes';
CREATE ROP REF_ID R4021 FROM 1C C_PP (Previous_PP_Id) PHRASE 'succeeds' TO 1C C_PP (PP_Id) PHRASE 'precedes';
CREATE ROP REF_ID R4201 FROM MC CL_IC (AssignedComp_Id) TO 1C C_C (Id);
CREATE ROP REF_ID R4205 FROM MC CL_IC (ParentComp_Id) TO 1C C_C (Id);
CREATE ROP REF_ID R44 FROM MC S_MBR (Parent_DT_DT_ID) TO 1 S_SDT (DT_ID);
CREATE ROP REF_ID R45 FROM MC S_MBR (DT_ID) TO 1 S_DT (DT_ID);
CREATE ROP REF_ID R4500 FROM MC SPR_REP (ExecutableProperty_Id) TO 1 C_EP (Id);
CREATE ROP REF_ID R4500 FROM MC SPR_REP (Requirement_Id) TO 1 C_R (Requirement_Id);
CREATE ROP REF_ID R4501 FROM MC SPR_PEP (ExecutableProperty_Id) TO 1 C_EP (Id);
CREATE ROP REF_ID R4501 FROM MC SPR_PEP (Provision_Id) TO 1 C_P (Provision_Id);
CREATE ROP REF_ID R4502 FROM 1C SPR_RS (Id) TO 1 SPR_REP (Id);
CREATE ROP REF_ID R4502 FROM 1C SPR_RO (Id) TO 1 SPR_REP (Id);
CREATE ROP REF_ID R4503 FROM 1C SPR_PO (Id) TO 1 SPR_PEP (Id);
CREATE ROP REF_ID R4503 FROM 1C SPR_PS (Id) TO 1 SPR_PEP (Id);
CREATE ROP REF_ID R46 FROM 1C S_MBR (Previous_Member_ID, Parent_DT_DT_ID) PHRASE 'succeeds' TO 1C S_MBR (Member_ID, Parent_DT_DT_ID) PHRASE 'precedes';
CREATE ROP REF_ID R4701 FROM MC CL_IIR (Ref_Id) TO 1C C_IR (Id);
CREATE ROP REF_ID R4703 FROM 1C CL_IP (Id) TO 1 CL_IIR (Id);
CREATE ROP REF_ID R4703 FROM 1C CL_IR (Id) TO 1 CL_IIR (Id);
CREATE ROP REF_ID R4704 FROM 1C CL_IIR (Delegation_Id) TO 1C C_DG (Id);
CREATE ROP REF_ID R4705 FROM 1C CL_IPINS (Satisfaction_Id) TO 1 C_SF (Id);
CREATE ROP REF_ID R4705 FROM MC CL_IPINS (ImportedProvision_Id) TO 1 CL_IP (Id);
CREATE ROP REF_ID R4706 FROM 1C CL_IR (Satisfaction_Element_Id) TO 1C C_SF (Id);
CREATE ROP REF_ID R4707 FROM MC CL_POR (CL_IC_Id) TO 1 CL_IC (Id);
CREATE ROP REF_ID R4708 FROM MC CL_IIR (CL_POR_Id) TO 1 CL_POR (Id);
CREATE ROP REF_ID R4709 FROM MC CL_POR (C_PO_Id) TO 1C C_PO (Id);
CREATE ROP REF_ID R49 FROM MC S_DIM (BParm_ID) TO 1C S_BPARM (BParm_ID);
CREATE ROP REF_ID R50 FROM MC S_DIM (Brg_ID) TO 1C S_BRG (Brg_ID);
CREATE ROP REF_ID R501 FROM MC SM_STATE (SM_ID) TO 1 SM_SM (SM_ID);
CREATE ROP REF_ID R502 FROM MC SM_EVT (SM_ID) TO 1 SM_SM (SM_ID);
CREATE ROP REF_ID R503 FROM MC SM_SEME (SM_ID, SMstt_ID) TO 1 SM_STATE (SM_ID, SMstt_ID);
CREATE ROP REF_ID R503 FROM MC SM_SEME (SMevt_ID, SM_ID) TO 1 SM_SEVT (SMevt_ID, SM_ID);
CREATE ROP REF_ID R504 FROM 1C SM_EIGN (SMevt_ID, SM_ID, SMstt_ID) TO 1 SM_SEME (SMevt_ID, SM_ID, SMstt_ID);
CREATE ROP REF_ID R504 FROM 1C SM_CH (SMevt_ID, SM_ID, SMstt_ID) TO 1 SM_SEME (SMevt_ID, SM_ID, SMstt_ID);
CREATE ROP REF_ID R504 FROM 1C SM_NSTXN (SMevt_ID, SM_ID, SMstt_ID) TO 1 SM_SEME (SMevt_ID, SM_ID, SMstt_ID);
CREATE ROP REF_ID R505 FROM MC SM_TXN (SM_ID) TO 1 SM_SM (SM_ID);
CREATE ROP REF_ID R506 FROM MC SM_TXN (SMstt_ID, SM_ID) TO 1 SM_STATE (SMstt_ID, SM_ID);
CREATE ROP REF_ID R507 FROM 1C SM_NETXN (Trans_ID, SM_ID) TO 1 SM_TXN (Trans_ID, SM_ID);
CREATE ROP REF_ID R507 FROM 1C SM_CRTXN (Trans_ID, SM_ID) TO 1 SM_TXN (Trans_ID, SM_ID);
CREATE ROP REF_ID R507 FROM 1C SM_NSTXN (Trans_ID, SM_ID) TO 1 SM_TXN (Trans_ID, SM_ID);
CREATE ROP REF_ID R508 FROM MC SM_NETXN (SM_ID, SMstt_ID) TO 1 SM_STATE (SM_ID, SMstt_ID);
CREATE ROP REF_ID R509 FROM 1C SM_CRTXN (SM_ID, SMevt_ID) TO 1C SM_LEVT (SM_ID, SMevt_ID);
CREATE ROP REF_ID R51 FROM MC S_DIM (Sync_ID) TO 1C S_SYNC (Sync_ID);
CREATE ROP REF_ID R510 FROM 1C SM_MEALY (SM_ID) TO 1 SM_SM (SM_ID);
CREATE ROP REF_ID R510 FROM 1C SM_MOORE (SM_ID) TO 1 SM_SM (SM_ID);
CREATE ROP REF_ID R511 FROM MC SM_MOAH (SM_ID) TO 1 SM_MOORE (SM_ID);
CREATE ROP REF_ID R511 FROM 1C SM_MOAH (SM_ID, SMstt_ID) TO 1 SM_STATE (SM_ID, SMstt_ID);
CREATE ROP REF_ID R512 FROM M SM_MEAH (SM_ID) TO 1 SM_MEALY (SM_ID);
CREATE ROP REF_ID R512 FROM 1C SM_MEAH (Trans_ID, SM_ID) TO 1 SM_TXN (Trans_ID, SM_ID);
CREATE ROP REF_ID R513 FROM 1C SM_MOAH (Act_ID, SM_ID) TO 1 SM_AH (Act_ID, SM_ID);
CREATE ROP REF_ID R513 FROM 1C SM_MEAH (Act_ID, SM_ID) TO 1 SM_AH (Act_ID, SM_ID);
CREATE ROP REF_ID R513 FROM 1C SM_TAH (Act_ID, SM_ID) TO 1 SM_AH (Act_ID, SM_ID);
CREATE ROP REF_ID R514 FROM 1 SM_AH (SM_ID, Act_ID) TO 1 SM_ACT (SM_ID, Act_ID);
CREATE ROP REF_ID R515 FROM MC SM_ACT (SM_ID) TO 1 SM_SM (SM_ID);
CREATE ROP REF_ID R516 FROM MC SM_EVTDI (SM_ID) TO 1 SM_SM (SM_ID);
CREATE ROP REF_ID R517 FROM 1C SM_ISM (SM_ID) TO 1 SM_SM (SM_ID);
CREATE ROP REF_ID R517 FROM 1C SM_ASM (SM_ID) TO 1 SM_SM (SM_ID);
CREATE ROP REF_ID R518 FROM 1C SM_ISM (Obj_ID) TO 1 O_OBJ (Obj_ID);
CREATE ROP REF_ID R519 FROM 1C SM_ASM (Obj_ID) TO 1 O_OBJ (Obj_ID);
CREATE ROP REF_ID R52 FROM MC S_DIM (SParm_ID) TO 1C S_SPARM (SParm_ID);
CREATE ROP REF_ID R524 FROM MC SM_EVTDI (DT_ID) TO 1 S_DT (DT_ID);
CREATE ROP REF_ID R525 FROM 1C SM_SEVT (SM_ID, SMevt_ID) TO 1 SM_EVT (SM_ID, SMevt_ID);
CREATE ROP REF_ID R525 FROM 1C SM_PEVT (SM_ID, SMevt_ID) TO 1 SM_EVT (SM_ID, SMevt_ID);
CREATE ROP REF_ID R526 FROM 1C SM_NLEVT (SMevt_ID, SM_ID) TO 1 SM_SEVT (SMevt_ID, SM_ID);
CREATE ROP REF_ID R526 FROM 1C SM_LEVT (SMevt_ID, SM_ID) TO 1 SM_SEVT (SMevt_ID, SM_ID);
CREATE ROP REF_ID R526 FROM 1C SM_SGEVT (SMevt_ID, SM_ID) TO 1 SM_SEVT (SMevt_ID, SM_ID);
CREATE ROP REF_ID R527 FROM MC SM_NLEVT (polySM_ID, polySMevt_ID) TO 1 SM_PEVT (SM_ID, SMevt_ID);
CREATE ROP REF_ID R528 FROM 1C SM_SGEVT (Provided_Signal_Id) TO 1C SPR_PS (Id);
CREATE ROP REF_ID R529 FROM 1C SM_SGEVT (Required_Signal_Id) TO 1C SPR_RS (Id);
CREATE ROP REF_ID R53 FROM MC S_DIM (Member_ID, DT_ID) TO 1C S_MBR (Member_ID, Parent_DT_DT_ID);
CREATE ROP REF_ID R530 FROM 1C SM_TAH (Trans_ID, SM_ID) TO 1 SM_TXN (Trans_ID, SM_ID);
CREATE ROP REF_ID R531 FROM MC S_DIM (SM_ID, SMedi_ID) TO 1C SM_EVTDI (SM_ID, SMedi_ID);
CREATE ROP REF_ID R532 FROM MC SM_EVTDI (SMevt_ID) TO 1 SM_EVT (SMevt_ID);
CREATE ROP REF_ID R533 FROM 1C SM_EVTDI (SM_ID, Previous_SMedi_ID) PHRASE 'succeeds' TO 1C SM_EVTDI (SM_ID, SMedi_ID) PHRASE 'precedes';
CREATE ROP REF_ID R54 FROM 1C S_SPARM (Previous_SParm_ID) PHRASE 'succeeds' TO 1C S_SPARM (SParm_ID) PHRASE 'precedes';
CREATE ROP REF_ID R55 FROM 1C S_BPARM (Previous_BParm_ID) PHRASE 'succeeds' TO 1C S_BPARM (BParm_ID) PHRASE 'precedes';
CREATE ROP REF_ID R56 FROM 1C S_ENUM (Previous_Enum_ID) PHRASE 'succeeds' TO 1C S_ENUM (Enum_ID) PHRASE 'precedes';
CREATE ROP REF_ID R601 FROM MC ACT_BLK (Action_ID) TO 1 ACT_ACT (Action_ID);
CREATE ROP REF_ID R602 FROM MC ACT_SMT (Block_ID) TO 1 ACT_BLK (Block_ID);
CREATE ROP REF_ID R603 FROM 1C ACT_FOR (Statement_ID) TO 1 ACT_SMT (Statement_ID);
CREATE ROP REF_ID R603 FROM 1C ACT_WHL (Statement_ID) TO 1 ACT_SMT (Statement_ID);
CREATE ROP REF_ID R603 FROM 1C ACT_IF (Statement_ID) TO 1 ACT_SMT (Statement_ID);
CREATE ROP REF_ID R603 FROM 1C ACT_EL (Statement_ID) TO 1 ACT_SMT (Statement_ID);
CREATE ROP REF_ID R603 FROM 1C ACT_E (Statement_ID) TO 1 ACT_SMT (Statement_ID);
CREATE ROP REF_ID R603 FROM 1C ACT_BRG (Statement_ID) TO 1 ACT_SMT (Statement_ID);
CREATE ROP REF_ID R603 FROM 1C ACT_FNC (Statement_ID) TO 1 ACT_SMT (Statement_ID);
CREATE ROP REF_ID R603 FROM 1C ACT_RET (Statement_ID) TO 1 ACT_SMT (Statement_ID);
CREATE ROP REF_ID R603 FROM 1C ACT_TFM (Statement_ID) TO 1 ACT_SMT (Statement_ID);
CREATE ROP REF_ID R603 FROM 1C ACT_AI (Statement_ID) TO 1 ACT_SMT (Statement_ID);
CREATE ROP REF_ID R603 FROM 1C ACT_DEL (Statement_ID) TO 1 ACT_SMT (Statement_ID);
CREATE ROP REF_ID R603 FROM 1C ACT_CNV (Statement_ID) TO 1 ACT_SMT (Statement_ID);
CREATE ROP REF_ID R603 FROM 1C ACT_CR (Statement_ID) TO 1 ACT_SMT (Statement_ID);
CREATE ROP REF_ID R603 FROM 1C ACT_SEL (Statement_ID) TO 1 ACT_SMT (Statement_ID);
CREATE ROP REF_ID R603 FROM 1C ACT_FIO (Statement_ID) TO 1 ACT_SMT (Statement_ID);
CREATE ROP REF_ID R603 FROM 1C ACT_FIW (Statement_ID) TO 1 ACT_SMT (Statement_ID);
CREATE ROP REF_ID R603 FROM 1C ACT_URU (Statement_ID) TO 1 ACT_SMT (Statement_ID);
CREATE ROP REF_ID R603 FROM 1C ACT_UNR (Statement_ID) TO 1 ACT_SMT (Statement_ID);
CREATE ROP REF_ID R603 FROM 1C ACT_RU (Statement_ID) TO 1 ACT_SMT (Statement_ID);
CREATE ROP REF_ID R603 FROM 1C ACT_REL (Statement_ID) TO 1 ACT_SMT (Statement_ID);
CREATE ROP REF_ID R603 FROM 1C ACT_CTL (Statement_ID) TO 1 ACT_SMT (Statement_ID);
CREATE ROP REF_ID R603 FROM 1C ACT_BRK (Statement_ID) TO 1 ACT_SMT (Statement_ID);
CREATE ROP REF_ID R603 FROM 1C ACT_CON (Statement_ID) TO 1 ACT_SMT (Statement_ID);
CREATE ROP REF_ID R603 FROM 1C E_ESS (Statement_ID) TO 1 ACT_SMT (Statement_ID);
CREATE ROP REF_ID R603 FROM 1C E_GPR (Statement_ID) TO 1 ACT_SMT (Statement_ID);
CREATE ROP REF_ID R603 FROM 1C ACT_IOP (Statement_ID) TO 1 ACT_SMT (Statement_ID);
CREATE ROP REF_ID R603 FROM 1C ACT_SGN (Statement_ID) TO 1 ACT_SMT (Statement_ID);
CREATE ROP REF_ID R604 FROM 1C ACT_LNK (Next_Link_ID) PHRASE 'precedes' TO 1C ACT_LNK (Link_ID) PHRASE 'succeeds';
CREATE ROP REF_ID R605 FROM 1C ACT_FOR (Block_ID) TO 1 ACT_BLK (Block_ID);
CREATE ROP REF_ID R606 FROM 1C ACT_E (Block_ID) TO 1 ACT_BLK (Block_ID);
CREATE ROP REF_ID R607 FROM 1C ACT_IF (Block_ID) TO 1 ACT_BLK (Block_ID);
CREATE ROP REF_ID R608 FROM 1C ACT_WHL (Block_ID) TO 1 ACT_BLK (Block_ID);
CREATE ROP REF_ID R609 FROM 1C ACT_AI (r_Value_ID) TO 1 V_VAL (Value_ID);
CREATE ROP REF_ID R610 FROM 1C ACT_FIW (Where_Clause_Value_ID) TO 1 V_VAL (Value_ID);
CREATE ROP REF_ID R611 FROM 1C ACT_SRW (Where_Clause_Value_ID) TO 1 V_VAL (Value_ID);
CREATE ROP REF_ID R612 FROM MC ACT_BLK (Parsed_Action_ID) TO 1C ACT_ACT (Action_ID);
CREATE ROP REF_ID R613 FROM 1C ACT_SEL (Value_ID) TO 1 V_VAL (Value_ID);
CREATE ROP REF_ID R614 FROM MC ACT_FOR (Loop_Var_ID) TO 1 V_VAR (Var_ID);
CREATE ROP REF_ID R615 FROM MC ACT_REL (One_Side_Var_ID) TO 1 V_VAR (Var_ID);
CREATE ROP REF_ID R616 FROM MC ACT_REL (Other_Side_Var_ID) TO 1 V_VAR (Var_ID);
CREATE ROP REF_ID R617 FROM MC ACT_RU (One_Side_Var_ID) TO 1 V_VAR (Var_ID);
CREATE ROP REF_ID R618 FROM MC ACT_RU (Other_Side_Var_ID) TO 1 V_VAR (Var_ID);
CREATE ROP REF_ID R619 FROM MC ACT_RU (Associative_Var_ID) TO 1 V_VAR (Var_ID);
CREATE ROP REF_ID R620 FROM MC ACT_UNR (One_Side_Var_ID) TO 1 V_VAR (Var_ID);
CREATE ROP REF_ID R621 FROM MC ACT_UNR (Other_Side_Var_ID) TO 1 V_VAR (Var_ID);
CREATE ROP REF_ID R622 FROM MC ACT_URU (One_Side_Var_ID) TO 1 V_VAR (Var_ID);
CREATE ROP REF_ID R623 FROM MC ACT_URU (Other_Side_Var_ID) TO 1 V_VAR (Var_ID);
CREATE ROP REF_ID R624 FROM MC ACT_URU (Associative_Var_ID) TO 1 V_VAR (Var_ID);
CREATE ROP REF_ID R625 FROM 1C ACT_IF (Value_ID) TO 1 V_VAL (Value_ID);
CREATE ROP REF_ID R626 FROM 1C ACT_WHL (Value_ID) TO 1 V_VAL (Value_ID);
CREATE ROP REF_ID R627 FROM MC V_PAR (Statement_ID) TO 1C ACT_TFM (Statement_ID);
CREATE ROP REF_ID R628 FROM MC V_PAR (Statement_ID) TO 1C ACT_BRG (Statement_ID);
CREATE ROP REF_ID R629 FROM MC ACT_IOP (Value_ID) TO 1C V_VAL (Value_ID);
CREATE ROP REF_ID R630 FROM MC ACT_SGN (Value_ID) TO 1C V_VAL (Value_ID);
CREATE ROP REF_ID R633 FROM MC ACT_CR (Var_ID) TO 1 V_VAR (Var_ID);
CREATE ROP REF_ID R634 FROM MC ACT_DEL (Var_ID) TO 1 V_VAR (Var_ID);
CREATE ROP REF_ID R637 FROM 1 ACT_LNK (Statement_ID) TO 1C ACT_SEL (Statement_ID);
CREATE ROP REF_ID R638 FROM MC ACT_SEL (Var_ID) TO 1 V_VAR (Var_ID);
CREATE ROP REF_ID R639 FROM MC ACT_FIO (Var_ID) TO 1 V_VAR (Var_ID);
CREATE ROP REF_ID R640 FROM 1C ACT_BIE (Action_ID) TO 1 ACT_ACT (Action_ID);
CREATE ROP REF_ID R640 FROM MC ACT_BIE (Element_ID) TO 1 PE_PE (Element_ID);
CREATE ROP REF_ID R650 FROM 1C ACT_ACT (Parsed_Block_ID) TO 1C ACT_BLK (Block_ID);
CREATE ROP REF_ID R652 FROM MC ACT_FOR (Set_Var_ID) TO 1 V_VAR (Var_ID);
CREATE ROP REF_ID R653 FROM MC ACT_REL (Rel_ID) TO 1 R_REL (Rel_ID);
CREATE ROP REF_ID R654 FROM MC ACT_RU (Rel_ID) TO 1 R_REL (Rel_ID);
CREATE ROP REF_ID R655 FROM MC ACT_UNR (Rel_ID) TO 1 R_REL (Rel_ID);
CREATE ROP REF_ID R656 FROM MC ACT_URU (Rel_ID) TO 1 R_REL (Rel_ID);
CREATE ROP REF_ID R657 FROM MC ACT_IOP (RequiredOp_Id) TO 1C SPR_RO (Id);
CREATE ROP REF_ID R658 FROM 1C ACT_EL (Block_ID) TO 1 ACT_BLK (Block_ID);
CREATE ROP REF_ID R659 FROM 1C ACT_EL (Value_ID) TO 1 V_VAL (Value_ID);
CREATE ROP REF_ID R660 FROM MC ACT_SGN (RequiredSig_Id) TO 1C SPR_RS (Id);
CREATE ROP REF_ID R661 FROM 1C ACT_SMT (Previous_Statement_ID, Block_ID) PHRASE 'succeeds' TO 1C ACT_SMT (Statement_ID, Block_ID) PHRASE 'precedes';
CREATE ROP REF_ID R662 FROM MC V_PAR (Statement_ID) TO 1C ACT_SGN (Statement_ID);
CREATE ROP REF_ID R663 FROM MC ACT_SGN (ProvidedSig_Id) TO 1C SPR_PS (Id);
CREATE ROP REF_ID R664 FROM 1C ACT_SRW (Statement_ID) TO 1 ACT_SEL (Statement_ID);
CREATE ROP REF_ID R664 FROM 1C ACT_SR (Statement_ID) TO 1 ACT_SEL (Statement_ID);
CREATE ROP REF_ID R665 FROM MC ACT_FIW (Var_ID) TO 1 V_VAR (Var_ID);
CREATE ROP REF_ID R666 FROM 1C ACT_ACT (Block_ID) TO 1C ACT_BLK (Block_ID);
CREATE ROP REF_ID R667 FROM MC ACT_TFM (Var_ID) TO 1C V_VAR (Var_ID);
CREATE ROP REF_ID R668 FROM 1C ACT_RET (Value_ID) TO 1C V_VAL (Value_ID);
CREATE ROP REF_ID R669 FROM MC V_PAR (Statement_ID) TO 1C ACT_FNC (Statement_ID);
CREATE ROP REF_ID R670 FROM MC ACT_FOR (Obj_ID) TO 1 O_OBJ (Obj_ID);
CREATE ROP REF_ID R671 FROM MC ACT_CR (Obj_ID) TO 1 O_OBJ (Obj_ID);
CREATE ROP REF_ID R672 FROM MC ACT_CNV (Obj_ID) TO 1 O_OBJ (Obj_ID);
CREATE ROP REF_ID R673 FROM MC ACT_TFM (Tfr_ID) TO 1C O_TFR (Tfr_ID);
CREATE ROP REF_ID R674 FROM MC ACT_BRG (Brg_ID) TO 1C S_BRG (Brg_ID);
CREATE ROP REF_ID R675 FROM MC ACT_FNC (Sync_ID) TO 1C S_SYNC (Sync_ID);
CREATE ROP REF_ID R676 FROM MC ACT_FIW (Obj_ID) TO 1C O_OBJ (Obj_ID);
CREATE ROP REF_ID R677 FROM MC ACT_FIO (Obj_ID) TO 1C O_OBJ (Obj_ID);
CREATE ROP REF_ID R678 FROM MC ACT_LNK (Obj_ID) TO 1 O_OBJ (Obj_ID);
CREATE ROP REF_ID R679 FROM MC V_PAR (Statement_ID) TO 1C ACT_IOP (Statement_ID);
CREATE ROP REF_ID R680 FROM MC ACT_IOP (ProvidedOp_Id) TO 1C SPR_PO (Id);
CREATE ROP REF_ID R681 FROM MC ACT_LNK (Rel_ID) TO 1 R_REL (Rel_ID);
CREATE ROP REF_ID R682 FROM MC ACT_EL (If_Statement_ID) TO 1 ACT_IF (Statement_ID);
CREATE ROP REF_ID R683 FROM 1C ACT_E (If_Statement_ID) TO 1 ACT_IF (Statement_ID);
CREATE ROP REF_ID R684 FROM 1C ACT_RSB (Id) TO 1 SPR_RS (Id);
CREATE ROP REF_ID R685 FROM 1C ACT_ROB (Id) TO 1 SPR_RO (Id);
CREATE ROP REF_ID R686 FROM 1C ACT_PSB (Id) TO 1 SPR_PS (Id);
CREATE ROP REF_ID R687 FROM 1C ACT_POB (Id) TO 1 SPR_PO (Id);
CREATE ROP REF_ID R688 FROM 1C ACT_TAB (SM_ID, Act_ID) TO 1 SM_ACT (SM_ID, Act_ID);
CREATE ROP REF_ID R689 FROM 1C ACT_AI (l_Value_ID) TO 1 V_VAL (Value_ID);
CREATE ROP REF_ID R690 FROM 1C ACT_IF (Elif_Statement_ID) TO 1C ACT_EL (Statement_ID);
CREATE ROP REF_ID R691 FROM 1C ACT_SAB (SM_ID, Act_ID) TO 1 SM_ACT (SM_ID, Act_ID);
CREATE ROP REF_ID R692 FROM 1C ACT_IF (Else_Statement_ID) TO 1C ACT_E (Statement_ID);
CREATE ROP REF_ID R693 FROM 1C ACT_DAB (Attr_ID, Obj_ID) TO 1 O_DBATTR (Attr_ID, Obj_ID);
CREATE ROP REF_ID R694 FROM MC ACT_BIC (Component_Id) TO 1 C_C (Id);
CREATE ROP REF_ID R694 FROM 1C ACT_BIC (Action_ID) TO 1 ACT_ACT (Action_ID);
CREATE ROP REF_ID R695 FROM 1C ACT_FNB (Sync_ID) TO 1 S_SYNC (Sync_ID);
CREATE ROP REF_ID R696 FROM 1C ACT_OPB (Tfr_ID) TO 1 O_TFR (Tfr_ID);
CREATE ROP REF_ID R697 FROM 1C ACT_BRB (Brg_ID) TO 1 S_BRG (Brg_ID);
CREATE ROP REF_ID R698 FROM 1C ACT_SAB (Action_ID) TO 1 ACT_ACT (Action_ID);
CREATE ROP REF_ID R698 FROM 1C ACT_DAB (Action_ID) TO 1 ACT_ACT (Action_ID);
CREATE ROP REF_ID R698 FROM 1C ACT_FNB (Action_ID) TO 1 ACT_ACT (Action_ID);
CREATE ROP REF_ID R698 FROM 1C ACT_OPB (Action_ID) TO 1 ACT_ACT (Action_ID);
CREATE ROP REF_ID R698 FROM 1C ACT_BRB (Action_ID) TO 1 ACT_ACT (Action_ID);
CREATE ROP REF_ID R698 FROM 1C ACT_POB (Action_ID) TO 1 ACT_ACT (Action_ID);
CREATE ROP REF_ID R698 FROM 1C ACT_PSB (Action_ID) TO 1 ACT_ACT (Action_ID);
CREATE ROP REF_ID R698 FROM 1C ACT_ROB (Action_ID) TO 1 ACT_ACT (Action_ID);
CREATE ROP REF_ID R698 FROM 1C ACT_RSB (Action_ID) TO 1 ACT_ACT (Action_ID);
CREATE ROP REF_ID R698 FROM 1C ACT_TAB (Action_ID) TO 1 ACT_ACT (Action_ID);
CREATE ROP REF_ID R699 FROM 1C ACT_ACT (CurrentScope_ID) TO 1C ACT_BLK (Block_ID);
CREATE ROP REF_ID R700 FROM MC V_PAR (Statement_ID) TO 1C E_ESS (Statement_ID);
CREATE ROP REF_ID R701 FROM 1C E_CES (Statement_ID) TO 1 E_ESS (Statement_ID);
CREATE ROP REF_ID R701 FROM 1C E_GES (Statement_ID) TO 1 E_ESS (Statement_ID);
CREATE ROP REF_ID R702 FROM 1C E_CSME (Statement_ID) TO 1 E_CES (Statement_ID);
CREATE ROP REF_ID R703 FROM 1C E_GSME (Statement_ID) TO 1 E_GES (Statement_ID);
CREATE ROP REF_ID R704 FROM 1C E_CEI (Statement_ID) TO 1 E_CSME (Statement_ID);
CREATE ROP REF_ID R704 FROM 1C E_CEA (Statement_ID) TO 1 E_CSME (Statement_ID);
CREATE ROP REF_ID R704 FROM 1C E_CEC (Statement_ID) TO 1 E_CSME (Statement_ID);
CREATE ROP REF_ID R705 FROM 1C E_GEN (Statement_ID) TO 1 E_GSME (Statement_ID);
CREATE ROP REF_ID R705 FROM 1C E_GAR (Statement_ID) TO 1 E_GSME (Statement_ID);
CREATE ROP REF_ID R705 FROM 1C E_GEC (Statement_ID) TO 1 E_GSME (Statement_ID);
CREATE ROP REF_ID R706 FROM MC E_CSME (SMevt_ID) TO 1 SM_EVT (SMevt_ID);
CREATE ROP REF_ID R707 FROM MC E_GSME (SMevt_ID) TO 1 SM_EVT (SMevt_ID);
CREATE ROP REF_ID R710 FROM MC E_CES (Var_ID) TO 1 V_VAR (Var_ID);
CREATE ROP REF_ID R711 FROM MC E_CEI (Var_ID) TO 1 V_VAR (Var_ID);
CREATE ROP REF_ID R712 FROM MC E_GEN (Var_ID) TO 1 V_VAR (Var_ID);
CREATE ROP REF_ID R714 FROM 1C E_GPR (Value_ID) TO 1C V_VAL (Value_ID);
CREATE ROP REF_ID R800 FROM 1C V_PAR (Value_ID) TO 1 V_VAL (Value_ID);
CREATE ROP REF_ID R8000 FROM MC PE_PE (Package_ID) TO 1C EP_PKG (Package_ID);
CREATE ROP REF_ID R8001 FROM 1C S_DT (DT_ID) TO 1 PE_PE (Element_ID);
CREATE ROP REF_ID R8001 FROM 1C SQ_P (Part_ID) TO 1 PE_PE (Element_ID);
CREATE ROP REF_ID R8001 FROM 1C UC_UCA (Assoc_ID) TO 1 PE_PE (Element_ID);
CREATE ROP REF_ID R8001 FROM 1C A_N (Id) TO 1 PE_PE (Element_ID);
CREATE ROP REF_ID R8001 FROM 1C O_OBJ (Obj_ID) TO 1 PE_PE (Element_ID);
CREATE ROP REF_ID R8001 FROM 1C C_C (Id) TO 1 PE_PE (Element_ID);
CREATE ROP REF_ID R8001 FROM 1C CL_IC (Id) TO 1 PE_PE (Element_ID);
CREATE ROP REF_ID R8001 FROM 1C C_I (Id) TO 1 PE_PE (Element_ID);
CREATE ROP REF_ID R8001 FROM 1C EP_PKG (Package_ID) TO 1 PE_PE (Element_ID);
CREATE ROP REF_ID R8001 FROM 1C CNST_CSP (Constant_Spec_ID) TO 1 PE_PE (Element_ID);
CREATE ROP REF_ID R8001 FROM 1C A_AP (Id) TO 1 PE_PE (Element_ID);
CREATE ROP REF_ID R8001 FROM 1C A_E (Id) TO 1 PE_PE (Element_ID);
CREATE ROP REF_ID R8001 FROM 1C MSG_M (Msg_ID) TO 1 PE_PE (Element_ID);
CREATE ROP REF_ID R8001 FROM 1C O_IOBJ (IObj_ID) TO 1 PE_PE (Element_ID);
CREATE ROP REF_ID R8001 FROM 1C R_REL (Rel_ID) TO 1 PE_PE (Element_ID);
CREATE ROP REF_ID R8001 FROM 1C S_EE (EE_ID) TO 1 PE_PE (Element_ID);
CREATE ROP REF_ID R8001 FROM 1C S_SYNC (Sync_ID) TO 1 PE_PE (Element_ID);
CREATE ROP REF_ID R8001 FROM 1C C_SF (Id) TO 1 PE_PE (Element_ID);
CREATE ROP REF_ID R8001 FROM 1C C_DG (Id) TO 1 PE_PE (Element_ID);
CREATE ROP REF_ID R8002 FROM MC PE_VIS (Element_ID) TO 1 PE_PE (Element_ID);
CREATE ROP REF_ID R8002 FROM MC PE_VIS (Package_ID) TO 1 EP_PKG (Package_ID);
CREATE ROP REF_ID R8003 FROM MC PE_PE (Component_ID) TO 1C C_C (Id);
CREATE ROP REF_ID R8004 FROM MC PE_CVS (Id) TO 1 C_C (Id);
CREATE ROP REF_ID R8004 FROM MC PE_CVS (Element_ID) TO 1 PE_PE (Element_ID);
CREATE ROP REF_ID R8005 FROM MC PE_SRS (Package_ID) TO 1 EP_PKG (Package_ID);
CREATE ROP REF_ID R8006 FROM MC PE_VIS (Name, Type, Package_ID) TO 1 PE_SRS (Name, Type, Package_ID);
CREATE ROP REF_ID R8007 FROM MC PE_CRS (Id) TO 1 C_C (Id);
CREATE ROP REF_ID R8008 FROM MC PE_CVS (Name, Type, Id) TO 1 PE_CRS (Name, Type, Id);
CREATE ROP REF_ID R801 FROM 1C V_FNV (Value_ID) TO 1 V_VAL (Value_ID);
CREATE ROP REF_ID R801 FROM 1C V_PVL (Value_ID) TO 1 V_VAL (Value_ID);
CREATE ROP REF_ID R801 FROM 1C V_SLR (Value_ID) TO 1 V_VAL (Value_ID);
CREATE ROP REF_ID R801 FROM 1C V_BRV (Value_ID) TO 1 V_VAL (Value_ID);
CREATE ROP REF_ID R801 FROM 1C V_IRF (Value_ID) TO 1 V_VAL (Value_ID);
CREATE ROP REF_ID R801 FROM 1C V_AVL (Value_ID) TO 1 V_VAL (Value_ID);
CREATE ROP REF_ID R801 FROM 1C V_LIN (Value_ID) TO 1 V_VAL (Value_ID);
CREATE ROP REF_ID R801 FROM 1C V_LST (Value_ID) TO 1 V_VAL (Value_ID);
CREATE ROP REF_ID R801 FROM 1C V_UNY (Value_ID) TO 1 V_VAL (Value_ID);
CREATE ROP REF_ID R801 FROM 1C V_TRV (Value_ID) TO 1 V_VAL (Value_ID);
CREATE ROP REF_ID R801 FROM 1C V_ISR (Value_ID) TO 1 V_VAL (Value_ID);
CREATE ROP REF_ID R801 FROM 1C V_EDV (Value_ID) TO 1 V_VAL (Value_ID);
CREATE ROP REF_ID R801 FROM 1C V_TVL (Value_ID) TO 1 V_VAL (Value_ID);
CREATE ROP REF_ID R801 FROM 1C V_LRL (Value_ID) TO 1 V_VAL (Value_ID);
CREATE ROP REF_ID R801 FROM 1C V_LBO (Value_ID) TO 1 V_VAL (Value_ID);
CREATE ROP REF_ID R801 FROM 1C V_BIN (Value_ID) TO 1 V_VAL (Value_ID);
CREATE ROP REF_ID R801 FROM 1C V_LEN (Value_ID) TO 1 V_VAL (Value_ID);
CREATE ROP REF_ID R801 FROM 1C V_MVL (Value_ID) TO 1 V_VAL (Value_ID);
CREATE ROP REF_ID R801 FROM 1C V_AER (Value_ID) TO 1 V_VAL (Value_ID);
CREATE ROP REF_ID R801 FROM 1C V_ALV (Value_ID) TO 1 V_VAL (Value_ID);
CREATE ROP REF_ID R801 FROM 1C V_MSV (Value_ID) TO 1 V_VAL (Value_ID);
CREATE ROP REF_ID R801 FROM 1C V_SCV (Value_ID) TO 1 V_VAL (Value_ID);
CREATE ROP REF_ID R802 FROM 1C V_BIN (Left_Value_ID) TO 1 V_VAL (Value_ID);
CREATE ROP REF_ID R803 FROM 1C V_BIN (Right_Value_ID) TO 1 V_VAL (Value_ID);
CREATE ROP REF_ID R804 FROM 1C V_UNY (Operand_Value_ID) TO 1 V_VAL (Value_ID);
CREATE ROP REF_ID R805 FROM MC V_TVL (Var_ID) TO 1 V_VAR (Var_ID);
CREATE ROP REF_ID R806 FROM MC V_AVL (Attr_ID, Obj_ID) TO 1 O_ATTR (Attr_ID, Obj_ID);
CREATE ROP REF_ID R807 FROM 1C V_AVL (Root_Value_ID) TO 1 V_VAL (Value_ID);
CREATE ROP REF_ID R808 FROM MC V_IRF (Var_ID) TO 1 V_VAR (Var_ID);
CREATE ROP REF_ID R809 FROM MC V_ISR (Var_ID) TO 1 V_VAR (Var_ID);
CREATE ROP REF_ID R810 FROM MC V_PAR (Invocation_Value_ID) TO 1C V_BRV (Value_ID);
CREATE ROP REF_ID R811 FROM MC V_PAR (Invocation_Value_ID) TO 1C V_TRV (Value_ID);
CREATE ROP REF_ID R812 FROM MC V_SLR (Attr_ID, Obj_ID) TO 1C O_ATTR (Attr_ID, Obj_ID);
CREATE ROP REF_ID R814 FROM 1C V_INT (Var_ID) TO 1 V_VAR (Var_ID);
CREATE ROP REF_ID R814 FROM 1C V_INS (Var_ID) TO 1 V_VAR (Var_ID);
CREATE ROP REF_ID R814 FROM 1C V_TRN (Var_ID) TO 1 V_VAR (Var_ID);
CREATE ROP REF_ID R816 FROM 1C V_PAR (Next_Value_ID) PHRASE 'precedes' TO 1C V_PAR (Value_ID) PHRASE 'succeeds';
CREATE ROP REF_ID R817 FROM MC V_PAR (Invocation_Value_ID) TO 1C V_FNV (Value_ID);
CREATE ROP REF_ID R818 FROM MC V_INT (Obj_ID) TO 1 O_OBJ (Obj_ID);
CREATE ROP REF_ID R819 FROM MC V_INS (Obj_ID) TO 1 O_OBJ (Obj_ID);
CREATE ROP REF_ID R820 FROM MC V_VAL (DT_ID) TO 1 S_DT (DT_ID);
CREATE ROP REF_ID R821 FROM MC V_TRN (DT_ID) TO 1C S_DT (DT_ID);
CREATE ROP REF_ID R823 FROM MC V_VAR (Block_ID) TO 1 ACT_BLK (Block_ID);
CREATE ROP REF_ID R824 FROM MC V_LEN (Enum_ID) TO 1 S_ENUM (Enum_ID);
CREATE ROP REF_ID R825 FROM 1C V_SLR (Op_Value_ID) TO 1C V_TRV (Value_ID);
CREATE ROP REF_ID R826 FROM MC V_VAL (Block_ID) TO 1 ACT_BLK (Block_ID);
CREATE ROP REF_ID R827 FROM MC V_FNV (Sync_ID) TO 1 S_SYNC (Sync_ID);
CREATE ROP REF_ID R828 FROM MC V_BRV (Brg_ID) TO 1 S_BRG (Brg_ID);
CREATE ROP REF_ID R829 FROM MC V_TRV (Tfr_ID) TO 1 O_TFR (Tfr_ID);
CREATE ROP REF_ID R830 FROM MC V_TRV (Var_ID) TO 1C V_VAR (Var_ID);
CREATE ROP REF_ID R831 FROM MC V_PVL (BParm_ID) TO 1C S_BPARM (BParm_ID);
CREATE ROP REF_ID R832 FROM MC V_PVL (SParm_ID) TO 1C S_SPARM (SParm_ID);
CREATE ROP REF_ID R833 FROM MC V_PVL (TParm_ID) TO 1C O_TPARM (TParm_ID);
CREATE ROP REF_ID R834 FROM MC V_EPR (Value_ID) TO 1 V_EDV (Value_ID);
CREATE ROP REF_ID R835 FROM MC V_LOC (Var_ID) TO 1 V_VAR (Var_ID);
CREATE ROP REF_ID R836 FROM MC V_MVL (Member_ID, DT_DT_ID) TO 1 S_MBR (Member_ID, Parent_DT_DT_ID);
CREATE ROP REF_ID R837 FROM 1C V_MVL (Root_Value_ID) TO 1 V_VAL (Value_ID);
CREATE ROP REF_ID R838 FROM 1C V_AER (Root_Value_ID) TO 1 V_VAL (Value_ID);
CREATE ROP REF_ID R839 FROM 1C V_AER (Index_Value_ID) TO 1 V_VAL (Value_ID);
CREATE ROP REF_ID R840 FROM 1C V_ALV (Array_Value_ID) TO 1 V_VAL (Value_ID);
CREATE ROP REF_ID R841 FROM MC V_MSV (PEP_Id) TO 1C SPR_PEP (Id);
CREATE ROP REF_ID R842 FROM MC V_PAR (Invocation_Value_ID) TO 1C V_MSV (Value_ID);
CREATE ROP REF_ID R843 FROM MC V_PVL (PP_Id) TO 1C C_PP (PP_Id);
CREATE ROP REF_ID R844 FROM MC S_DIM (Var_ID) TO 1C V_TRN (Var_ID);
CREATE ROP REF_ID R845 FROM MC V_MSV (REP_Id) TO 1C SPR_REP (Id);
CREATE ROP REF_ID R846 FROM MC V_EPR (SM_ID, SMedi_ID) TO 1C SM_EVTDI (SM_ID, SMedi_ID);
CREATE ROP REF_ID R847 FROM MC V_EPR (PP_Id) TO 1C C_PP (PP_Id);
CREATE ROP REF_ID R848 FROM MC V_VAR (DT_ID) TO 1C S_DT (DT_ID);
CREATE ROP REF_ID R849 FROM MC S_DIM (Var_ID) TO 1C V_VAR (Var_ID);
CREATE ROP REF_ID R850 FROM MC V_SCV (Const_ID) TO 1 CNST_SYC (Const_ID);
CREATE ROP REF_ID R851 FROM MC V_MSV (Target_Value_ID) TO 1C V_VAL (Value_ID);
CREATE ROP REF_ID R9 FROM MC S_EEM (EE_ID) TO 1 S_EE (EE_ID);
CREATE ROP REF_ID R9000 FROM MC PA_SIC (Component_Id) TO 1 C_C (Id);
CREATE ROP REF_ID R9000 FROM 1C PA_SIC (Satisfaction_Id) TO 1 C_SF (Id);
CREATE ROP REF_ID R9002 FROM MC PA_DIC (Component_Id) TO 1 C_C (Id);
CREATE ROP REF_ID R9002 FROM 1 PA_DIC (Delegation_Id) TO 1 C_DG (Id);
CREATE ROP REF_ID R9100 FROM MC G_EIS (Element_ID) TO 1 PE_PE (Element_ID);
CREATE ROP REF_ID R9100 FROM MC G_EIS (Sys_ID) TO 1 S_SYS (Sys_ID);
CREATE ROP REF_ID R930 FROM 1C SQ_CIP (Part_ID) TO 1 SQ_P (Part_ID);
CREATE ROP REF_ID R930 FROM 1C SQ_EEP (Part_ID) TO 1 SQ_P (Part_ID);
CREATE ROP REF_ID R930 FROM 1C SQ_CP (Part_ID) TO 1 SQ_P (Part_ID);
CREATE ROP REF_ID R930 FROM 1C SQ_AP (Part_ID) TO 1 SQ_P (Part_ID);
CREATE ROP REF_ID R930 FROM 1C SQ_LS (Part_ID) TO 1 SQ_P (Part_ID);
CREATE ROP REF_ID R930 FROM 1C IA_UCP (Part_ID) TO 1 SQ_P (Part_ID);
CREATE ROP REF_ID R930 FROM 1C SQ_COP (Part_ID) TO 1 SQ_P (Part_ID);
CREATE ROP REF_ID R930 FROM 1C SQ_PP (Part_ID) TO 1 SQ_P (Part_ID);
CREATE ROP REF_ID R931 FROM MC SQ_TM (Part_ID) TO 1 SQ_LS (Part_ID);
CREATE ROP REF_ID R933 FROM MC SQ_EEP (EE_ID) TO 1C S_EE (EE_ID);
CREATE ROP REF_ID R934 FROM MC SQ_CIP (Obj_ID) TO 1C O_OBJ (Obj_ID);
CREATE ROP REF_ID R935 FROM MC SQ_CPA (Part_ID) TO 1 SQ_CP (Part_ID);
CREATE ROP REF_ID R936 FROM MC SQ_AV (Informal_Part_ID) TO 1C SQ_CIP (Part_ID);
CREATE ROP REF_ID R937 FROM MC SQ_AV (Formal_Part_ID) TO 1C SQ_CIP (Part_ID);
CREATE ROP REF_ID R938 FROM MC SQ_AV (Attr_ID, Obj_ID) TO 1C O_ATTR (Attr_ID, Obj_ID);
CREATE ROP REF_ID R939 FROM MC SQ_CP (Obj_ID) TO 1C O_OBJ (Obj_ID);
CREATE ROP REF_ID R940 FROM 1C SQ_LS (Source_Part_ID) TO 1 SQ_P (Part_ID);
CREATE ROP REF_ID R941 FROM MC SQ_TS (Prev_Mark_ID) TO 1 SQ_TM (Mark_ID);
CREATE ROP REF_ID R942 FROM MC SQ_TS (Mark_ID) TO 1 SQ_TM (Mark_ID);
CREATE ROP REF_ID R947 FROM 1C SQ_IA (Ia_ID) TO 1 SQ_CPA (Ia_ID);
CREATE ROP REF_ID R947 FROM 1C SQ_FA (Ia_ID) TO 1 SQ_CPA (Ia_ID);
CREATE ROP REF_ID R948 FROM 1C SQ_IAV (Av_ID) TO 1 SQ_AV (Av_ID);
CREATE ROP REF_ID R948 FROM 1C SQ_FAV (Av_ID) TO 1 SQ_AV (Av_ID);
CREATE ROP REF_ID R949 FROM 1C SQ_AP (LS_Part_ID) TO 1C SQ_LS (Part_ID);
CREATE ROP REF_ID R955 FROM MC SQ_COP (Component_Id) TO 1C C_C (Id);
CREATE ROP REF_ID R956 FROM MC SQ_PP (Package_ID) TO 1C EP_PKG (Package_ID);
