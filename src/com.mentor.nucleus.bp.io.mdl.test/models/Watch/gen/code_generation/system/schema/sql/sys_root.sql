-- ==================================================================
-- File: sys_root.sql
--
-- Description:
-- System wide parameters and configuration control meta-objects:
--
--   !!! This is an AUTO-GENERATED file. Please do NOT edit. !!!
-- ==================================================================
-- Class:  System
INSERT INTO TE_SYS VALUES (
	1,
	'MC3020',
	'V4.3.0',
	'',
	'',
	'',
	'',
	'ExecutableName',
	80,
	0,
	0,
	0,
	0,
	0,
	0,
	0,
	0,
	0,
	0,
	FALSE,
	FALSE,
	'Escher_',
	'Escher_',
	'Escher_',
	'Escher_',
	'',
	0,
	0,
	0,
	0,
	'',
	1,
	FALSE
	);

-- Class:  Dispatcher
INSERT INTO TE_DISP VALUES (
	1,
	1,
	'main',
	'model compiler system level dispatcher'
	);

-- self directed xtUML event queue.
INSERT INTO TE_QUEUE VALUES (
	1,
	1,
	1,
	'self directed xtUML event queue',
	FALSE,
	0,
	FALSE,
	0,
	FALSE,
	);

-- non-self directed xtUML event queue.
INSERT INTO TE_QUEUE VALUES (
	2,
	1,
	2,
	'Non-self directed OOA event queue',
	FALSE,
	0,
	TRUE,
	0,
	FALSE,
	);

