
CREATE TABLE W_CTI (
	     Name	STRING,
	     Workflow_Name	STRING,
	     Statement_ID	STRING,
	     Key_Lett	STRING,
	     Type	STRING );
CREATE TABLE CME (
	     Specialism	STRING,
	     Label	STRING,
	     Key_Lett	STRING,
	     Resultant_Class	STRING, 
	     Global	BOOLEAN,
	     WizardDescription	STRING,
	     requiredSelectionCount	STRING,
	     requiresHeterogeneousSelection	BOOLEAN,
	     extraEntryText	STRING,
	     UsesSelectionDialog	BOOLEAN,
	     EnableVisibilityFiltering	BOOLEAN,
	     TypeToChoose	STRING,
	     Resultant_Class_RelChain	STRING );
CREATE TABLE W_CFL (
	     Name	STRING,
	     Step_ID	STRING,
	     Label	STRING );
CREATE TABLE W_FLD (
	     Name	STRING,
	     Instance_Handle	STRING,
	     Statement_ID	STRING,
	     Key_Lett	STRING,
	     Step_ID	STRING,
	     Cardinality	STRING,
	     Type	STRING,
	     buffer	STRING );
CREATE TABLE MEF (
	     Specialism	STRING,
	     Label	STRING,
	     Key_Lett	STRING,
	     name	STRING,
	     value	STRING );
CREATE TABLE W_STEP (
	     Step_ID	STRING,
	     Workflow_Name	STRING,
	     Block_ID	STRING,
	     Parent_Step_ID	STRING,
	     Previous_Step_ID	STRING,
	     buffer	STRING );
CREATE TABLE W_WOF (
	     Name	STRING,
	     Sync_ID	UNIQUE_ID, 
	     Key_Lett	STRING,
	     Specialism	STRING,
	     Label	STRING );

CREATE ROP REF_ID R2003 FROM MC 	W_CTI 	( Workflow_Name )
		         TO 1  	W_WOF 	( Name );
CREATE ROP REF_ID R2001 FROM 1C 	W_WOF 	( Sync_ID )
		         TO 1  	S_SYNC 	( Sync_ID );
CREATE ROP REF_ID R2002 FROM MC 	W_CTI 	( Key_Lett )
		         TO 1  	O_OBJ 	( Key_Lett );
CREATE ROP REF_ID R2000 FROM MC 	CME 	( Key_Lett )
		         TO 1  	O_OBJ 	( Key_Lett );
-- CREATE ROP REF_ID R2004 FROM 1C 	W_CTI 	( Statement_ID )
-- 		         TO 1  	ACT_SMT 	( Statement_ID );
CREATE ROP REF_ID R2005 FROM MC 	W_STEP 	( Workflow_Name )
		         TO 1  	W_WOF 	( Name );
-- CREATE ROP REF_ID R2006 FROM 1  	W_STEP	( Block_ID )
-- 		         TO 1  	ACT_BLK 	( Block_ID );
CREATE ROP REF_ID R2007 FROM MC 	W_STEP 	( Parent_Step_ID )  PHRASE 'contained_by'
		         TO 1C  W_STEP 	( Step_ID )  PHRASE 'contains';
CREATE ROP REF_ID R2008 FROM 1C 	W_STEP 	( Parent_Step_ID , Previous_Step_ID )  PHRASE 'precedes'
		         TO 1C  W_STEP 	( Parent_Step_ID , Step_ID )  PHRASE 'succeeds';
CREATE ROP REF_ID R2009 FROM MC 	W_FLD 	( Step_ID )
		         TO 1  	W_STEP 	( Step_ID );
-- CREATE ROP REF_ID R2010 FROM 1C 	W_FLD 	( Statement_ID )
-- 		         TO 1  	ACT_SMT 	( Statement_ID );
CREATE ROP REF_ID R2011 FROM MC 	W_FLD 	( Key_Lett )
		         TO 1C  O_OBJ 	( Key_Lett );
-- CREATE ROP REF_ID R2012 FROM 1  	W_WOF	( Specialism , Key_Lett , Label )
-- 		         TO 1  	CME 	( Specialism , Key_Lett , Label );
-- CREATE ROP REF_ID R2013 FROM MC 	MEF 	( Specialism , Key_Lett , Label )
-- 		         TO 1  	CME 	( Specialism , Key_Lett , Label );
CREATE ROP REF_ID R2014 FROM 1C 	W_CFL 	( Name , Step_ID )
		         TO 1  	W_FLD 	( Name , Step_ID );

