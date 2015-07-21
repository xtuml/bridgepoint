-- BP 6.1D content: domain syschar: 3

INSERT INTO S_DOM
	VALUES (2531670,
	'ooaofooa',
	'========================================================================

File:          $RCSfile: bp_ooa_ooa.sql,v $
Version:   $Revision: 1.14 $
Modified:  $Date: 2013/05/10 16:17:46 $

(c) Copyright 2003-2014 Mentor Graphics Corporation All rights reserved.

========================================================================
Licensed under the Apache License, Version 2.0 (the "License"); you may not
use this file except in compliance with the License.  You may obtain a copy
of the License at

     http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.   See the
License for the specific language governing permissions and limitations under
the License.
========================================================================

This file was created by exporting the BridgePoint OOA of OOA
(bp:/sql/bp5.0/sql_dir/ooa_ooa.bak, v1.11) as an sql file without
graphics.  The following changes were then made to the file:

- The '/' was changed to a ' ' in the Subtype/Supertype Relationship class name.
- The order of the last two CA_SMSMC attributes was reversed.
- The Version Management subsystem was removed

This domain describes an analysis of the models entered in the BridgePoint Model Builder tool.',
	1,
	1);
INSERT INTO S_CDT
	VALUES (524289,
	0);
INSERT INTO S_DT
	VALUES (524289,
	2531670,
	'void',
	'');
INSERT INTO S_CDT
	VALUES (524290,
	1);
INSERT INTO S_DT
	VALUES (524290,
	2531670,
	'boolean',
	'');
INSERT INTO S_CDT
	VALUES (524291,
	2);
INSERT INTO S_DT
	VALUES (524291,
	2531670,
	'integer',
	'');
INSERT INTO S_CDT
	VALUES (524292,
	3);
INSERT INTO S_DT
	VALUES (524292,
	2531670,
	'real',
	'');
INSERT INTO S_CDT
	VALUES (524293,
	4);
INSERT INTO S_DT
	VALUES (524293,
	2531670,
	'string',
	'');
INSERT INTO S_CDT
	VALUES (524294,
	5);
INSERT INTO S_DT
	VALUES (524294,
	2531670,
	'unique_id',
	'');
INSERT INTO S_CDT
	VALUES (524295,
	6);
INSERT INTO S_DT
	VALUES (524295,
	2531670,
	'state<State_Model>',
	'');
INSERT INTO S_CDT
	VALUES (524296,
	7);
INSERT INTO S_DT
	VALUES (524296,
	2531670,
	'same_as<Base_Attribute>',
	'');
INSERT INTO S_CDT
	VALUES (524297,
	8);
INSERT INTO S_DT
	VALUES (524297,
	2531670,
	'inst_ref<Object>',
	'');
INSERT INTO S_CDT
	VALUES (524298,
	9);
INSERT INTO S_DT
	VALUES (524298,
	2531670,
	'inst_ref_set<Object>',
	'');
INSERT INTO S_CDT
	VALUES (524299,
	10);
INSERT INTO S_DT
	VALUES (524299,
	2531670,
	'inst<Event>',
	'');
INSERT INTO S_CDT
	VALUES (524300,
	11);
INSERT INTO S_DT
	VALUES (524300,
	2531670,
	'inst<Mapping>',
	'');
INSERT INTO S_CDT
	VALUES (524301,
	12);
INSERT INTO S_DT
	VALUES (524301,
	2531670,
	'inst_ref<Mapping>',
	'');
INSERT INTO S_UDT
	VALUES (524302,
	524300,
	1);
INSERT INTO S_DT
	VALUES (524302,
	2531670,
	'date',
	'');
INSERT INTO S_UDT
	VALUES (524303,
	524300,
	2);
INSERT INTO S_DT
	VALUES (524303,
	2531670,
	'timestamp',
	'');
INSERT INTO S_UDT
	VALUES (524304,
	524301,
	3);
INSERT INTO S_DT
	VALUES (524304,
	2531670,
	'inst_ref<Timer>',
	'');
INSERT INTO S_SS
	VALUES (1048578,
	'Subsystem',
	'A domain (S_DOM) consists of one or more subsystems (S_SS) that each contain an information model that abstracts a portion of the data for the domain: objects (O_OBJ), relationships (R_REL), etc.  Breaking a domain into subsystems allows the complexity of the domain to be more easily managed: 1) subsystems are components and can be checked out individually allowing parallelism in development of the models and 2) breaking up a domain into smaller units is easier to understand.  A subsystem is composed of objects that tend to cluster, i.e., they have few relationships that span subsystem boundaries.  Inter-subsystem relationships, communications, and accesses are captured in the Subsystem Relationship Model (SRM), Subsystem Communication Model (SCM), and Subsystem Access Model (SAM) respectively.',
	'S',
	1,
	2531670,
	1048578);
INSERT INTO O_OBJ
	VALUES (1048577,
	'Domain',
	1,
	'S_DOM',
	'A typical software system generally consists of distinct and independent subject matters. A Shlaer-Mellor analysis partition is based within each of these subject matters and  each subject matter is called a domain (S_DOM). A domain is inhabited by its  own conceptual entities (called objects). A domain may be partitioned into subsystems depending upon its complexity. Each domain is given a mission statement which provides a charter for the construction of the OOA models.',
	1048578);
INSERT INTO O_NBATTR
	VALUES (1048577,
	1048577);
INSERT INTO O_BATTR
	VALUES (1048577,
	1048577);
INSERT INTO O_ATTR
	VALUES (1048577,
	1048577,
	0,
	'Dom_ID',
	'Full Name: Domain Identifier',
	'',
	'Dom_ID',
	0,
	524294);
INSERT INTO O_NBATTR
	VALUES (1048578,
	1048577);
INSERT INTO O_BATTR
	VALUES (1048578,
	1048577);
INSERT INTO O_ATTR
	VALUES (1048578,
	1048577,
	1048577,
	'Name',
	'Full Name: Domain Name
Description: A name  that represents the subject matter of the domain.',
	'',
	'Name',
	0,
	524293);
INSERT INTO O_NBATTR
	VALUES (1048579,
	1048577);
INSERT INTO O_BATTR
	VALUES (1048579,
	1048577);
INSERT INTO O_ATTR
	VALUES (1048579,
	1048577,
	1048578,
	'Descrip',
	'Full Name: Domain Description
Description: A brief description of the domain',
	'',
	'Descrip',
	0,
	524293);
INSERT INTO O_NBATTR
	VALUES (1048580,
	1048577);
INSERT INTO O_BATTR
	VALUES (1048580,
	1048577);
INSERT INTO O_ATTR
	VALUES (1048580,
	1048577,
	1048579,
	'Full_Der',
	'Full Name: Fully Derived Indicator
Description: A flag indicating whether the Object Communication Model and Object Access Model are fully derived from the information contained in the Object Information Model and Action Specifications.
Data Domain: 0 = OCM and OAM are not fully derived, 1 = OCM and OAM are fully derived
',
	'',
	'Full_Der',
	0,
	524291);
INSERT INTO O_NBATTR
	VALUES (1048581,
	1048577);
INSERT INTO O_BATTR
	VALUES (1048581,
	1048577);
INSERT INTO O_ATTR
	VALUES (1048581,
	1048577,
	1048580,
	'Config_ID',
	'Full Name: Configuration Identifier
Description:  The Configuration Identifier of the version management configuration which the domain is part of (refer to Version Management Subsystem). This ID can be used to access the V_CONFIG record corresponding to the Domain/Subsystem Configuration.',
	'',
	'Config_ID',
	0,
	524294);
INSERT INTO O_ID
	VALUES (0,
	1048577);
INSERT INTO O_OIDA
	VALUES (1048577,
	1048577,
	0);
INSERT INTO O_RTIDA
	VALUES (1048577,
	1048577,
	0,
	1048600,
	1048627);
INSERT INTO O_RTIDA
	VALUES (1048577,
	1048577,
	0,
	1048584,
	1048591);
INSERT INTO O_RTIDA
	VALUES (1048577,
	1048577,
	0,
	1048582,
	1048587);
INSERT INTO O_RTIDA
	VALUES (1048577,
	1048577,
	0,
	1048577,
	1048577);
INSERT INTO O_OBJ
	VALUES (1048578,
	'Subsystem',
	2,
	'S_SS',
	'A subsystem (S_SS) is based on the partitioning of an entire domain (S_DOM). The number of subsystems in a domain is dependent upon the domain subject matter and complexity. A subsystem is composed of objects which tend to cluster, i.e., objects which have many  relationships with one another but few relationships with objects in different clusters. Inter-subsystem relationships, asynchronous communications, and synchronous accesses are captured in the Subsystem Relationship Model, Subsystem Communication Model and Subsystem Access Model, respectively.',
	1048578);
INSERT INTO O_NBATTR
	VALUES (1048582,
	1048578);
INSERT INTO O_BATTR
	VALUES (1048582,
	1048578);
INSERT INTO O_ATTR
	VALUES (1048582,
	1048578,
	0,
	'SS_ID',
	'Full Name: Subsystem Identifier
Description: Unique identifier for the subsystem within the domain',
	'',
	'SS_ID',
	0,
	524294);
INSERT INTO O_NBATTR
	VALUES (1048583,
	1048578);
INSERT INTO O_BATTR
	VALUES (1048583,
	1048578);
INSERT INTO O_ATTR
	VALUES (1048583,
	1048578,
	1048582,
	'Name',
	'Full Name: Subsystem Name',
	'',
	'Name',
	0,
	524293);
INSERT INTO O_NBATTR
	VALUES (1048584,
	1048578);
INSERT INTO O_BATTR
	VALUES (1048584,
	1048578);
INSERT INTO O_ATTR
	VALUES (1048584,
	1048578,
	1048583,
	'Descrip',
	'Full Name: Subsystem Description',
	'',
	'Descrip',
	0,
	524293);
INSERT INTO O_NBATTR
	VALUES (1048585,
	1048578);
INSERT INTO O_BATTR
	VALUES (1048585,
	1048578);
INSERT INTO O_ATTR
	VALUES (1048585,
	1048578,
	1048584,
	'Prefix',
	'Full Name: Subsystem Prefix
Description: The subsystem keyletter prefix is used when objects are created in the subsystem.  The subsystem keyletter prefix is used as the default prefix in the object keyletters.
',
	'',
	'Prefix',
	0,
	524293);
INSERT INTO O_NBATTR
	VALUES (1048586,
	1048578);
INSERT INTO O_BATTR
	VALUES (1048586,
	1048578);
INSERT INTO O_ATTR
	VALUES (1048586,
	1048578,
	1048585,
	'Num_Rng',
	'Full Name: Number Range
Description:  The subsystem number range start is used when objects and relationships are created in the subsystem.  The subsystem number range start is used as the default auto-numbering start value in for the newly created object number and newly created relationship number. 
',
	'',
	'Num_Rng',
	0,
	524291);
INSERT INTO O_REF
	VALUES (1048578,
	1048577,
	0,
	1048577,
	1048577,
	1048578,
	1048577,
	1048587,
	1048585,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (1048587,
	1048578,
	1048577,
	1048577,
	1);
INSERT INTO O_ATTR
	VALUES (1048587,
	1048578,
	1048586,
	'Dom_ID',
	'',
	'',
	'Dom_ID',
	0,
	524296);
INSERT INTO O_NBATTR
	VALUES (1048588,
	1048578);
INSERT INTO O_BATTR
	VALUES (1048588,
	1048578);
INSERT INTO O_ATTR
	VALUES (1048588,
	1048578,
	1048587,
	'Config_ID',
	'Full Name: Configuration Identifier',
	'',
	'Config_ID',
	0,
	524294);
INSERT INTO O_ID
	VALUES (0,
	1048578);
INSERT INTO O_OIDA
	VALUES (1048582,
	1048578,
	0);
INSERT INTO O_RTIDA
	VALUES (1048582,
	1048578,
	0,
	1048578,
	1048579);
INSERT INTO O_RTIDA
	VALUES (1048582,
	1048578,
	0,
	1048598,
	1048622);
INSERT INTO O_RTIDA
	VALUES (1048582,
	1048578,
	0,
	1048597,
	1048620);
INSERT INTO O_RTIDA
	VALUES (1048582,
	1048578,
	0,
	1048595,
	1048616);
INSERT INTO O_RTIDA
	VALUES (1048582,
	1048578,
	0,
	1048594,
	1048614);
INSERT INTO O_RTIDA
	VALUES (1048582,
	1048578,
	0,
	1048596,
	1048618);
INSERT INTO O_OBJ
	VALUES (1048579,
	'External Entity',
	3,
	'S_EE',
	'An external entity (S_EE) represents something outside of the domain (S_DOM) being modeled that interacts with objects (O_OBJ) within the domain. The interactions are shown by event communication paths on the Object Communication Models and data access paths on the Object Access Models. Each external entity is given a unique name and keyletters within a domain.',
	1048578);
INSERT INTO O_NBATTR
	VALUES (1048589,
	1048579);
INSERT INTO O_BATTR
	VALUES (1048589,
	1048579);
INSERT INTO O_ATTR
	VALUES (1048589,
	1048579,
	0,
	'EE_ID',
	'Full Name: External Entity Identifier
Description: Uniquely identifies the external entity in the domain.',
	'',
	'EE_ID',
	0,
	524294);
INSERT INTO O_NBATTR
	VALUES (1048590,
	1048579);
INSERT INTO O_BATTR
	VALUES (1048590,
	1048579);
INSERT INTO O_ATTR
	VALUES (1048590,
	1048579,
	1048589,
	'Name',
	'Full Name: External Entity Name
Description: Provides a name for the external entity.',
	'',
	'Name',
	0,
	524293);
INSERT INTO O_NBATTR
	VALUES (1048591,
	1048579);
INSERT INTO O_BATTR
	VALUES (1048591,
	1048579);
INSERT INTO O_ATTR
	VALUES (1048591,
	1048579,
	1048590,
	'Descrip',
	'Full Name: External Entity Description
Description: A textual description of the external entity which appears in a domains Subsystem Partition Model (SPM).',
	'',
	'Descrip',
	0,
	524293);
INSERT INTO O_NBATTR
	VALUES (1048592,
	1048579);
INSERT INTO O_BATTR
	VALUES (1048592,
	1048579);
INSERT INTO O_ATTR
	VALUES (1048592,
	1048579,
	1048591,
	'Key_Lett',
	'Full Name: External Entity Key Letters
',
	'',
	'Key_Lett',
	0,
	524293);
INSERT INTO O_REF
	VALUES (1048579,
	1048577,
	0,
	1048577,
	1048584,
	1048592,
	1048591,
	1048593,
	1048586,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (1048593,
	1048579,
	1048577,
	1048577,
	1);
INSERT INTO O_ATTR
	VALUES (1048593,
	1048579,
	1048592,
	'Dom_ID',
	'',
	'',
	'Dom_ID',
	0,
	524296);
INSERT INTO O_ID
	VALUES (0,
	1048579);
INSERT INTO O_OIDA
	VALUES (1048589,
	1048579,
	0);
INSERT INTO O_RTIDA
	VALUES (1048589,
	1048579,
	0,
	1048589,
	1048604);
INSERT INTO O_RTIDA
	VALUES (1048589,
	1048579,
	0,
	1048592,
	1048610);
INSERT INTO O_RTIDA
	VALUES (1048589,
	1048579,
	0,
	1048580,
	1048583);
INSERT INTO O_RTIDA
	VALUES (1048589,
	1048579,
	0,
	1048581,
	1048585);
INSERT INTO O_RTIDA
	VALUES (1048589,
	1048579,
	0,
	1048579,
	1048581);
INSERT INTO O_NBATTR
	VALUES (1048711,
	1048579);
INSERT INTO O_BATTR
	VALUES (1048711,
	1048579);
INSERT INTO O_ATTR
	VALUES (1048711,
	1048579,
	1048593,
	'Realized_Class_Path',
	'',
	'',
	'Realized_Class_Path',
	0,
	524293);
INSERT INTO O_NBATTR
	VALUES (1048712,
	1048579);
INSERT INTO O_BATTR
	VALUES (1048712,
	1048579);
INSERT INTO O_ATTR
	VALUES (1048712,
	1048579,
	1048711,
	'Label',
	'',
	'',
	'Label',
	0,
	524293);
INSERT INTO O_NBATTR
	VALUES (1048713,
	1048579);
INSERT INTO O_BATTR
	VALUES (1048713,
	1048579);
INSERT INTO O_ATTR
	VALUES (1048713,
	1048579,
	1048712,
	'isRealized',
	'',
	'',
	'isRealized',
	0,
	524290);
INSERT INTO O_OBJ
	VALUES (1048580,
	'External Entity in Model',
	4,
	'S_EEM',
	'The external entity in model (S_EEM) represents the presence of an external entity (S_EE)  in a model such as the Object Communication Model or Object Access Model. The same external entity can be represented by more than one external entity in model in the same model to enhance model layout.',
	1048578);
INSERT INTO O_NBATTR
	VALUES (1048594,
	1048580);
INSERT INTO O_BATTR
	VALUES (1048594,
	1048580);
INSERT INTO O_ATTR
	VALUES (1048594,
	1048580,
	0,
	'EEmod_ID',
	'Full Name: External Entity In Model Identifier',
	'',
	'EEmod_ID',
	0,
	524294);
INSERT INTO O_REF
	VALUES (1048580,
	1048579,
	0,
	1048589,
	1048579,
	1048582,
	1048581,
	1048595,
	1048587,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (1048595,
	1048580,
	1048589,
	1048579,
	1);
INSERT INTO O_ATTR
	VALUES (1048595,
	1048580,
	1048594,
	'EE_ID',
	'',
	'',
	'EE_ID',
	0,
	524296);
INSERT INTO O_NBATTR
	VALUES (1048596,
	1048580);
INSERT INTO O_BATTR
	VALUES (1048596,
	1048580);
INSERT INTO O_ATTR
	VALUES (1048596,
	1048580,
	1048595,
	'Modl_Typ',
	'Full Name: Model Type Indicator
Description: Indicates in which  type of model the External Entity resides.
Data Domain: 6 = Object Communication Model, 7 = Object Access Model',
	'',
	'Modl_Typ',
	0,
	524291);
INSERT INTO O_REF
	VALUES (1048580,
	1048578,
	0,
	1048582,
	1048578,
	1048580,
	1048579,
	1048597,
	1048588,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (1048597,
	1048580,
	1048582,
	1048578,
	1);
INSERT INTO O_ATTR
	VALUES (1048597,
	1048580,
	1048596,
	'SS_ID',
	'',
	'',
	'SS_ID',
	0,
	524296);
INSERT INTO O_ID
	VALUES (0,
	1048580);
INSERT INTO O_OIDA
	VALUES (1048594,
	1048580,
	0);
INSERT INTO O_RTIDA
	VALUES (1048594,
	1048580,
	0,
	2097164,
	2097178);
INSERT INTO O_RTIDA
	VALUES (1048594,
	1048580,
	0,
	2097174,
	2097198);
INSERT INTO O_RTIDA
	VALUES (1048594,
	1048580,
	0,
	2097172,
	2097194);
INSERT INTO O_OIDA
	VALUES (1048595,
	1048580,
	0);
INSERT INTO O_RTIDA
	VALUES (1048595,
	1048580,
	0,
	2097164,
	2097178);
INSERT INTO O_RTIDA
	VALUES (1048595,
	1048580,
	0,
	2097172,
	2097194);
INSERT INTO O_RTIDA
	VALUES (1048595,
	1048580,
	0,
	2097174,
	2097198);
INSERT INTO O_OBJ
	VALUES (1048581,
	'External Entity Data Item',
	5,
	'S_EEDI',
	'An external entity data item is essentially an attribute of an external entity that can be accessed by an object (O_OBJ).  These synchronous Interactions between objects and external entities are shown on the Object Access Models.  Support for external entity data items is currently absent from the tool.',
	1048578);
INSERT INTO O_NBATTR
	VALUES (1048598,
	1048581);
INSERT INTO O_BATTR
	VALUES (1048598,
	1048581);
INSERT INTO O_ATTR
	VALUES (1048598,
	1048581,
	0,
	'EEdi_ID',
	'Full Name: External Entity Data Item Identifier',
	'',
	'EEdi_ID',
	0,
	524294);
INSERT INTO O_REF
	VALUES (1048581,
	1048579,
	0,
	1048589,
	1048592,
	1048609,
	1048610,
	1048599,
	1048577,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (1048599,
	1048581,
	1048589,
	1048579,
	1);
INSERT INTO O_ATTR
	VALUES (1048599,
	1048581,
	1048598,
	'EE_ID',
	'',
	'',
	'EE_ID',
	0,
	524296);
INSERT INTO O_NBATTR
	VALUES (1048600,
	1048581);
INSERT INTO O_BATTR
	VALUES (1048600,
	1048581);
INSERT INTO O_ATTR
	VALUES (1048600,
	1048581,
	1048599,
	'Name',
	'Full Name: External Entity Data Item Name',
	'',
	'Name',
	0,
	524293);
INSERT INTO O_NBATTR
	VALUES (1048601,
	1048581);
INSERT INTO O_BATTR
	VALUES (1048601,
	1048581);
INSERT INTO O_ATTR
	VALUES (1048601,
	1048581,
	1048600,
	'Descrip',
	'Full Name: External Entity Data Item Description',
	'',
	'Descrip',
	0,
	524293);
INSERT INTO O_REF
	VALUES (1048581,
	1048584,
	0,
	1048616,
	1048590,
	1048606,
	1048605,
	1048602,
	1048589,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (1048602,
	1048581,
	1048616,
	1048584,
	1);
INSERT INTO O_ATTR
	VALUES (1048602,
	1048581,
	1048601,
	'DT_ID',
	'',
	'',
	'DT_ID',
	0,
	524296);
INSERT INTO O_ID
	VALUES (0,
	1048581);
INSERT INTO O_OIDA
	VALUES (1048598,
	1048581,
	0);
INSERT INTO O_RTIDA
	VALUES (1048598,
	1048581,
	0,
	2097166,
	2097182);
INSERT INTO O_OIDA
	VALUES (1048599,
	1048581,
	0);
INSERT INTO O_RTIDA
	VALUES (1048599,
	1048581,
	0,
	2097166,
	2097182);
INSERT INTO O_OBJ
	VALUES (1048582,
	'External Entity Event',
	6,
	'S_EEEVT',
	'An external entity event (S_EEEVT) represents asynchronous communication between an object (O_OBJ) and an external entity (S_EE).  The communication is shown on the Object Communication Model.  Each external entity event is given a unique label and can have zero or more event data (S_EEEDT).',
	1048578);
INSERT INTO O_NBATTR
	VALUES (1048603,
	1048582);
INSERT INTO O_BATTR
	VALUES (1048603,
	1048582);
INSERT INTO O_ATTR
	VALUES (1048603,
	1048582,
	0,
	'EEevt_ID',
	'Full Name: External Entity Event Identifier',
	'',
	'EEevt_ID',
	0,
	524294);
INSERT INTO O_REF
	VALUES (1048582,
	1048579,
	0,
	1048589,
	1048580,
	1048584,
	1048583,
	1048604,
	1048590,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (1048604,
	1048582,
	1048589,
	1048579,
	1);
INSERT INTO O_ATTR
	VALUES (1048604,
	1048582,
	1048603,
	'EE_ID',
	'',
	'',
	'EE_ID',
	0,
	524296);
INSERT INTO O_NBATTR
	VALUES (1048605,
	1048582);
INSERT INTO O_BATTR
	VALUES (1048605,
	1048582);
INSERT INTO O_ATTR
	VALUES (1048605,
	1048582,
	1048604,
	'Numb',
	'Full Name: External Entity Event Number',
	'',
	'Numb',
	0,
	524291);
INSERT INTO O_NBATTR
	VALUES (1048606,
	1048582);
INSERT INTO O_BATTR
	VALUES (1048606,
	1048582);
INSERT INTO O_ATTR
	VALUES (1048606,
	1048582,
	1048605,
	'Mning',
	'Full Name: External Entity Event Meaning',
	'',
	'Mning',
	0,
	524293);
INSERT INTO O_NBATTR
	VALUES (1048607,
	1048582);
INSERT INTO O_BATTR
	VALUES (1048607,
	1048582);
INSERT INTO O_ATTR
	VALUES (1048607,
	1048582,
	1048606,
	'Is_Lbl_U',
	'Full Name: External Entity Event Label Unique Indicator
Description: This is a flag that indicates whether custom label keyletters are used for the External Entity Event.
Data Domain: 0 = custom label keyletters are used, 1 = External Entity keyletters are used',
	'',
	'Is_Lbl_U',
	0,
	524291);
INSERT INTO O_NBATTR
	VALUES (1048608,
	1048582);
INSERT INTO O_BATTR
	VALUES (1048608,
	1048582);
INSERT INTO O_ATTR
	VALUES (1048608,
	1048582,
	1048607,
	'Unq_Lbl',
	'Full Name: External Entity Event Unique Label
Description: Custom Key Letters for the External Entity Event',
	'',
	'Unq_Lbl',
	0,
	524293);
INSERT INTO O_DBATTR
	VALUES (1048609,
	1048582,
	'',
	0);
INSERT INTO O_BATTR
	VALUES (1048609,
	1048582);
INSERT INTO O_ATTR
	VALUES (1048609,
	1048582,
	1048608,
	'Drv_Lbl',
	'Full Name: External Entity Event Derived Label
Description: contains  the event label derived by concatenating the keyletters and the event number.
Data Domain: 0 = derived label created by concatenating EE keyletters + event number, 1 = derived label created by concatenating Unq_Lbl with event number
',
	'',
	'Drv_Lbl',
	0,
	524293);
INSERT INTO O_NBATTR
	VALUES (1048610,
	1048582);
INSERT INTO O_BATTR
	VALUES (1048610,
	1048582);
INSERT INTO O_ATTR
	VALUES (1048610,
	1048582,
	1048609,
	'Descrip',
	'Full Name: External Entity Event Description',
	'',
	'Descrip',
	0,
	524293);
INSERT INTO O_ID
	VALUES (0,
	1048582);
INSERT INTO O_OIDA
	VALUES (1048603,
	1048582,
	0);
INSERT INTO O_RTIDA
	VALUES (1048603,
	1048582,
	0,
	1048593,
	1048612);
INSERT INTO O_RTIDA
	VALUES (1048603,
	1048582,
	0,
	2097157,
	2097163);
INSERT INTO O_OIDA
	VALUES (1048604,
	1048582,
	0);
INSERT INTO O_RTIDA
	VALUES (1048604,
	1048582,
	0,
	2097157,
	2097163);
INSERT INTO O_RTIDA
	VALUES (1048604,
	1048582,
	0,
	1048593,
	1048612);
INSERT INTO O_OBJ
	VALUES (1048583,
	'External Entity Event Data Item',
	7,
	'S_EEEDI',
	'Each external entity (S_EE) may have zero or more event data items that can be used as supplemental data for external entity events.  The event data items are kept in a pool so that they can be used by one or more external entity events.  When an instance of event data item is used in an event, an instance of S_EEEDT is created to represent the event as data in an event.',
	1048578);
INSERT INTO O_NBATTR
	VALUES (1048611,
	1048583);
INSERT INTO O_BATTR
	VALUES (1048611,
	1048583);
INSERT INTO O_ATTR
	VALUES (1048611,
	1048583,
	0,
	'EEedi_ID',
	'Full Name: External Entity Event Data Item Identifier',
	'',
	'EEedi_ID',
	0,
	524294);
INSERT INTO O_REF
	VALUES (1048583,
	1048579,
	0,
	1048589,
	1048581,
	1048586,
	1048585,
	1048612,
	1048591,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (1048612,
	1048583,
	1048589,
	1048579,
	1);
INSERT INTO O_ATTR
	VALUES (1048612,
	1048583,
	1048611,
	'EE_ID',
	'',
	'',
	'EE_ID',
	0,
	524296);
INSERT INTO O_NBATTR
	VALUES (1048613,
	1048583);
INSERT INTO O_BATTR
	VALUES (1048613,
	1048583);
INSERT INTO O_ATTR
	VALUES (1048613,
	1048583,
	1048612,
	'Name',
	'Full Name: External Entity Event Data Item Name',
	'',
	'Name',
	0,
	524293);
INSERT INTO O_NBATTR
	VALUES (1048614,
	1048583);
INSERT INTO O_BATTR
	VALUES (1048614,
	1048583);
INSERT INTO O_ATTR
	VALUES (1048614,
	1048583,
	1048613,
	'Descrip',
	'Full Name: External Entity Event Data Item Description',
	'',
	'Descrip',
	0,
	524293);
INSERT INTO O_REF
	VALUES (1048583,
	1048584,
	0,
	1048616,
	1048583,
	1048590,
	1048589,
	1048615,
	1048592,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (1048615,
	1048583,
	1048616,
	1048584,
	1);
INSERT INTO O_ATTR
	VALUES (1048615,
	1048583,
	1048614,
	'DT_ID',
	'',
	'',
	'DT_ID',
	0,
	524296);
INSERT INTO O_ID
	VALUES (0,
	1048583);
INSERT INTO O_OIDA
	VALUES (1048611,
	1048583,
	0);
INSERT INTO O_RTIDA
	VALUES (1048611,
	1048583,
	0,
	1048593,
	1048611);
INSERT INTO O_OIDA
	VALUES (1048612,
	1048583,
	0);
INSERT INTO O_RTIDA
	VALUES (1048612,
	1048583,
	0,
	1048593,
	1048611);
INSERT INTO O_OBJ
	VALUES (1048584,
	'Data Type',
	9,
	'S_DT',
	'An analyst can assign a data type to the various data items in the models, i.e., object attribute, state model event data item, transformer and bridge parameters and return values.  This data type does not capture the representation of the data items, but rather, the characteristics of the data items including:  1) Value Definition, i.e., whole numbers, 2) Value Range, i.e., values between 0 and 10, 3) Operations, i.e., +, -, *, /',
	1048578);
INSERT INTO O_NBATTR
	VALUES (1048616,
	1048584);
INSERT INTO O_BATTR
	VALUES (1048616,
	1048584);
INSERT INTO O_ATTR
	VALUES (1048616,
	1048584,
	0,
	'DT_ID',
	'Full Name: Data Type Identifier',
	'',
	'DT_ID',
	0,
	524294);
INSERT INTO O_REF
	VALUES (1048584,
	1048577,
	0,
	1048577,
	1048582,
	1048588,
	1048587,
	1048617,
	1048593,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (1048617,
	1048584,
	1048577,
	1048577,
	1);
INSERT INTO O_ATTR
	VALUES (1048617,
	1048584,
	1048616,
	'Dom_ID',
	'',
	'',
	'Dom_ID',
	0,
	524296);
INSERT INTO O_NBATTR
	VALUES (1048618,
	1048584);
INSERT INTO O_BATTR
	VALUES (1048618,
	1048584);
INSERT INTO O_ATTR
	VALUES (1048618,
	1048584,
	1048617,
	'Name',
	'Full Name: Data Type Name',
	'',
	'Name',
	0,
	524293);
INSERT INTO O_NBATTR
	VALUES (1048619,
	1048584);
INSERT INTO O_BATTR
	VALUES (1048619,
	1048584);
INSERT INTO O_ATTR
	VALUES (1048619,
	1048584,
	1048618,
	'Descrip',
	'Full Name: Data Type Description',
	'',
	'Descrip',
	0,
	524293);
INSERT INTO O_ID
	VALUES (0,
	1048584);
INSERT INTO O_OIDA
	VALUES (1048616,
	1048584,
	0);
INSERT INTO O_RTIDA
	VALUES (1048616,
	1048584,
	0,
	1048583,
	1048589);
INSERT INTO O_RTIDA
	VALUES (1048616,
	1048584,
	0,
	3145735,
	3145741);
INSERT INTO O_RTIDA
	VALUES (1048616,
	1048584,
	0,
	3145730,
	3145731);
INSERT INTO O_RTIDA
	VALUES (1048616,
	1048584,
	0,
	2621462,
	2621493);
INSERT INTO O_RTIDA
	VALUES (1048616,
	1048584,
	0,
	1048585,
	1048593);
INSERT INTO O_RTIDA
	VALUES (1048616,
	1048584,
	0,
	1048601,
	1048629);
INSERT INTO O_RTIDA
	VALUES (1048616,
	1048584,
	0,
	1048591,
	1048607);
INSERT INTO O_RTIDA
	VALUES (1048616,
	1048584,
	0,
	3145731,
	3145733);
INSERT INTO O_RTIDA
	VALUES (1048616,
	1048584,
	0,
	1048590,
	1048605);
INSERT INTO O_RTIDA
	VALUES (1048616,
	1048584,
	0,
	1048603,
	1048633);
INSERT INTO O_RTIDA
	VALUES (1048616,
	1048584,
	0,
	1048587,
	1048599);
INSERT INTO O_ID
	VALUES (1,
	1048584);
INSERT INTO O_OIDA
	VALUES (1048617,
	1048584,
	1);
INSERT INTO O_OIDA
	VALUES (1048616,
	1048584,
	1);
INSERT INTO O_OBJ
	VALUES (1048585,
	'Core Data Type',
	10,
	'S_CDT',
	'Core Data Types are those data types which are fundamental, or core, to all data types.  The core data types are built-in to the tool and cannot be changed.',
	1048578);
INSERT INTO O_REF
	VALUES (1048585,
	1048584,
	0,
	1048616,
	1048585,
	1048594,
	1048593,
	1048620,
	1048578,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (1048620,
	1048585,
	1048616,
	1048584,
	1);
INSERT INTO O_ATTR
	VALUES (1048620,
	1048585,
	0,
	'DT_ID',
	'',
	'',
	'DT_ID',
	0,
	524296);
INSERT INTO O_NBATTR
	VALUES (1048621,
	1048585);
INSERT INTO O_BATTR
	VALUES (1048621,
	1048585);
INSERT INTO O_ATTR
	VALUES (1048621,
	1048585,
	1048620,
	'Core_Typ',
	'Full Name: Core Type
Description: The core BridgePoint type for the data type
Data Domain: 0 = void, 1 = boolean, 2 = integer, 3 = real, 4 = string, 5 = unique_id, 6 = current_state, 7 = same_as_base, 8 = inst_ref\<Object\>, 9 = inst_ref_set\<Object\>, 10 = inst\<Event\>, 11 = inst\<Mapping\>, 12 = inst_ref\<Mapping\>
',
	'',
	'Core_Typ',
	0,
	524291);
INSERT INTO O_ID
	VALUES (0,
	1048585);
INSERT INTO O_OIDA
	VALUES (1048620,
	1048585,
	0);
INSERT INTO O_RTIDA
	VALUES (1048620,
	1048585,
	0,
	1048586,
	1048597);
INSERT INTO O_OBJ
	VALUES (1048586,
	'User Data Type',
	11,
	'S_UDT',
	'User Data Types are those data types which have been created by the analyst and derived from the core data types.  ',
	1048578);
INSERT INTO O_REF
	VALUES (1048586,
	1048584,
	0,
	1048616,
	1048585,
	1048595,
	1048593,
	1048622,
	1048579,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (1048622,
	1048586,
	1048616,
	1048584,
	1);
INSERT INTO O_ATTR
	VALUES (1048622,
	1048586,
	0,
	'DT_ID',
	'',
	'',
	'DT_ID',
	0,
	524296);
INSERT INTO O_REF
	VALUES (1048586,
	1048585,
	0,
	1048620,
	1048586,
	1048598,
	1048597,
	1048623,
	1048594,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (1048623,
	1048586,
	1048616,
	1048584,
	1);
INSERT INTO O_ATTR
	VALUES (1048623,
	1048586,
	1048622,
	'CDT_ID',
	'',
	'C',
	'DT_ID',
	1,
	524296);
INSERT INTO O_NBATTR
	VALUES (1048624,
	1048586);
INSERT INTO O_BATTR
	VALUES (1048624,
	1048586);
INSERT INTO O_ATTR
	VALUES (1048624,
	1048586,
	1048623,
	'Gen_Type',
	'Full Name: User Defined Type Type
Data Domain: 0 = user defined, 1 = date, 2 = timestamp, 3 = inst_ref\<Timer\>

',
	'',
	'Gen_Type',
	0,
	524291);
INSERT INTO O_ID
	VALUES (0,
	1048586);
INSERT INTO O_OIDA
	VALUES (1048622,
	1048586,
	0);
INSERT INTO O_OBJ
	VALUES (1048587,
	'Bridge',
	12,
	'S_BRG',
	'A bridge (S_BRG) is a method associated with an external entity (S_EE).  Bridges can be synchronously called from action specifications.  Each bridge as a return type and zero or more parameters (S_BPARM).',
	1048578);
INSERT INTO O_NBATTR
	VALUES (1048625,
	1048587);
INSERT INTO O_BATTR
	VALUES (1048625,
	1048587);
INSERT INTO O_ATTR
	VALUES (1048625,
	1048587,
	0,
	'Brg_ID',
	'Full Name: Bridge Identifier',
	'',
	'Brg_ID',
	0,
	524294);
INSERT INTO O_REF
	VALUES (1048587,
	1048579,
	0,
	1048589,
	1048589,
	1048603,
	1048604,
	1048626,
	1048580,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (1048626,
	1048587,
	1048589,
	1048579,
	1);
INSERT INTO O_ATTR
	VALUES (1048626,
	1048587,
	1048625,
	'EE_ID',
	'',
	'',
	'EE_ID',
	0,
	524296);
INSERT INTO O_NBATTR
	VALUES (1048627,
	1048587);
INSERT INTO O_BATTR
	VALUES (1048627,
	1048587);
INSERT INTO O_ATTR
	VALUES (1048627,
	1048587,
	1048626,
	'Name',
	'Full Name: Bridge Name',
	'',
	'Name',
	0,
	524293);
INSERT INTO O_NBATTR
	VALUES (1048628,
	1048587);
INSERT INTO O_BATTR
	VALUES (1048628,
	1048587);
INSERT INTO O_ATTR
	VALUES (1048628,
	1048587,
	1048627,
	'Descrip',
	'Full Name: Bridge Description',
	'',
	'Descrip',
	0,
	524293);
INSERT INTO O_NBATTR
	VALUES (1048629,
	1048587);
INSERT INTO O_BATTR
	VALUES (1048629,
	1048587);
INSERT INTO O_ATTR
	VALUES (1048629,
	1048587,
	1048628,
	'Brg_Typ',
	'Full Name: Bridge Type
Data Domain: 0 = user defined, 1 = built-in',
	'',
	'Brg_Typ',
	0,
	524291);
INSERT INTO O_REF
	VALUES (1048587,
	1048584,
	0,
	1048616,
	1048587,
	1048600,
	1048599,
	1048630,
	1048595,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (1048630,
	1048587,
	1048616,
	1048584,
	1);
INSERT INTO O_ATTR
	VALUES (1048630,
	1048587,
	1048629,
	'DT_ID',
	'',
	'',
	'DT_ID',
	0,
	524296);
INSERT INTO O_NBATTR
	VALUES (1048631,
	1048587);
INSERT INTO O_BATTR
	VALUES (1048631,
	1048587);
INSERT INTO O_ATTR
	VALUES (1048631,
	1048587,
	1048630,
	'Action_Semantics',
	'Full Name: Action Semantics Field
Description: Action Semantics for the bridge action',
	'',
	'Action_Semantics',
	0,
	524293);
INSERT INTO O_NBATTR
	VALUES (1048632,
	1048587);
INSERT INTO O_BATTR
	VALUES (1048632,
	1048587);
INSERT INTO O_ATTR
	VALUES (1048632,
	1048587,
	1048631,
	'Suc_Pars',
	'Full Name: Successful Parse Indicator
Description: Indicates the status of the parse for the bridge action specification in the attribute Action_Semantics
Data Domain: 0=not parsed, 1 = parse successful, 2 = parse unsuccessful, 3 = parse on apply set but never been parsed',
	'',
	'Suc_Pars',
	0,
	524291);
INSERT INTO O_ID
	VALUES (0,
	1048587);
INSERT INTO O_OIDA
	VALUES (1048625,
	1048587,
	0);
INSERT INTO O_RTIDA
	VALUES (1048625,
	1048587,
	0,
	1048588,
	1048601);
INSERT INTO O_OBJ
	VALUES (1048588,
	'Bridge Parameter',
	13,
	'S_BPARM',
	'A parameter to a bridge (S_BRG) is called a bridge parameter.  Bridge parameters are either passed in by value, or by reference.  Bridge parameters can be accessed by using the param keyword from within a bridge action specification.',
	1048578);
INSERT INTO O_NBATTR
	VALUES (1048633,
	1048588);
INSERT INTO O_BATTR
	VALUES (1048633,
	1048588);
INSERT INTO O_ATTR
	VALUES (1048633,
	1048588,
	0,
	'BParm_ID',
	'Full Name: Bridge Parameter Identifier',
	'',
	'BParm_ID',
	0,
	524294);
INSERT INTO O_REF
	VALUES (1048588,
	1048587,
	0,
	1048625,
	1048588,
	1048602,
	1048601,
	1048634,
	1048596,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (1048634,
	1048588,
	1048625,
	1048587,
	1);
INSERT INTO O_ATTR
	VALUES (1048634,
	1048588,
	1048633,
	'Brg_ID',
	'',
	'',
	'Brg_ID',
	0,
	524296);
INSERT INTO O_NBATTR
	VALUES (1048635,
	1048588);
INSERT INTO O_BATTR
	VALUES (1048635,
	1048588);
INSERT INTO O_ATTR
	VALUES (1048635,
	1048588,
	1048634,
	'Name',
	'Full Name: Bridge Parameter Name
Description: The name of a parameter used to identify a supplemental data item being provided in a Bridge statement.',
	'',
	'Name',
	0,
	524293);
INSERT INTO O_REF
	VALUES (1048588,
	1048584,
	0,
	1048616,
	1048591,
	1048608,
	1048607,
	1048636,
	1048597,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (1048636,
	1048588,
	1048616,
	1048584,
	1);
INSERT INTO O_ATTR
	VALUES (1048636,
	1048588,
	1048635,
	'DT_ID',
	'',
	'',
	'DT_ID',
	0,
	524296);
INSERT INTO O_NBATTR
	VALUES (1048637,
	1048588);
INSERT INTO O_BATTR
	VALUES (1048637,
	1048588);
INSERT INTO O_ATTR
	VALUES (1048637,
	1048588,
	1048636,
	'By_Ref',
	'Full Name: By Reference Indicator
Description: Indicates whether or not this parameter is passed by reference
Data Domain: 0 = passed by value, 1 = passed by reference',
	'',
	'By_Ref',
	0,
	524291);
INSERT INTO O_ID
	VALUES (0,
	1048588);
INSERT INTO O_OIDA
	VALUES (1048633,
	1048588,
	0);
INSERT INTO O_OBJ
	VALUES (1048589,
	'External Entity Event Data',
	8,
	'S_EEEDT',
	'When an instance of event data item (S_EEEDI) is used in an event (S_EEEVT), an instance of S_EEEDT is created to represent the event as data in an event.  The supplemental data for an external entity event are instances of this object.',
	1048578);
INSERT INTO O_REF
	VALUES (1048589,
	1048583,
	0,
	1048612,
	1048593,
	1048613,
	1048611,
	1048638,
	1048598,
	0,
	0,
	'');
INSERT INTO O_REF
	VALUES (1048589,
	1048582,
	0,
	1048604,
	1048593,
	1048613,
	1048612,
	1048638,
	1048599,
	1048598,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (1048638,
	1048589,
	1048589,
	1048579,
	1);
INSERT INTO O_ATTR
	VALUES (1048638,
	1048589,
	0,
	'EE_ID',
	'',
	'',
	'EE_ID',
	0,
	524296);
INSERT INTO O_REF
	VALUES (1048589,
	1048582,
	0,
	1048603,
	1048593,
	1048613,
	1048612,
	1048639,
	1048600,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (1048639,
	1048589,
	1048603,
	1048582,
	1);
INSERT INTO O_ATTR
	VALUES (1048639,
	1048589,
	1048638,
	'EEevt_ID',
	'',
	'',
	'EEevt_ID',
	0,
	524296);
INSERT INTO O_REF
	VALUES (1048589,
	1048583,
	0,
	1048611,
	1048593,
	1048613,
	1048611,
	1048640,
	1048601,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (1048640,
	1048589,
	1048611,
	1048583,
	1);
INSERT INTO O_ATTR
	VALUES (1048640,
	1048589,
	1048639,
	'EEedi_ID',
	'',
	'',
	'EEedi_ID',
	0,
	524296);
INSERT INTO O_ID
	VALUES (0,
	1048589);
INSERT INTO O_OIDA
	VALUES (1048638,
	1048589,
	0);
INSERT INTO O_OIDA
	VALUES (1048640,
	1048589,
	0);
INSERT INTO O_OIDA
	VALUES (1048639,
	1048589,
	0);
INSERT INTO O_OBJ
	VALUES (1048590,
	'Enumeration Data Type',
	14,
	'S_EDT',
	'An enumeration is a data type represented by a set of constants called enumerators.  For example, an enumeration called Colors might have three enumerators Red, Blue, and Green.',
	1048578);
INSERT INTO O_REF
	VALUES (1048590,
	1048584,
	0,
	1048616,
	1048585,
	1048596,
	1048593,
	1048641,
	1048581,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (1048641,
	1048590,
	1048616,
	1048584,
	1);
INSERT INTO O_ATTR
	VALUES (1048641,
	1048590,
	0,
	'DT_ID',
	'',
	'',
	'DT_ID',
	0,
	524296);
INSERT INTO O_ID
	VALUES (0,
	1048590);
INSERT INTO O_OIDA
	VALUES (1048641,
	1048590,
	0);
INSERT INTO O_RTIDA
	VALUES (1048641,
	1048590,
	0,
	1048599,
	1048624);
INSERT INTO O_OBJ
	VALUES (1048591,
	'Enumerator',
	15,
	'S_ENUM',
	'Enumerators are constants that have meaning to the analyst.  An enumeration data type (S_EDT) defines zero or more enumerators.  For example, Red, Blue, and Green are enumerators of the enumeration Color.',
	1048578);
INSERT INTO O_NBATTR
	VALUES (1048642,
	1048591);
INSERT INTO O_BATTR
	VALUES (1048642,
	1048591);
INSERT INTO O_ATTR
	VALUES (1048642,
	1048591,
	0,
	'Enum_ID',
	'Full Name: Enumerator Identifier',
	'',
	'Enum_ID',
	0,
	524294);
INSERT INTO O_NBATTR
	VALUES (1048643,
	1048591);
INSERT INTO O_BATTR
	VALUES (1048643,
	1048591);
INSERT INTO O_ATTR
	VALUES (1048643,
	1048591,
	1048642,
	'Name',
	'Full Name: Enumerator Name',
	'',
	'Name',
	0,
	524293);
INSERT INTO O_NBATTR
	VALUES (1048644,
	1048591);
INSERT INTO O_BATTR
	VALUES (1048644,
	1048591);
INSERT INTO O_ATTR
	VALUES (1048644,
	1048591,
	1048643,
	'Descrip',
	'Full Name: Enumerator Description',
	'',
	'Descrip',
	0,
	524293);
INSERT INTO O_REF
	VALUES (1048591,
	1048590,
	0,
	1048641,
	1048599,
	1048625,
	1048624,
	1048645,
	1048602,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (1048645,
	1048591,
	1048616,
	1048584,
	1);
INSERT INTO O_ATTR
	VALUES (1048645,
	1048591,
	1048644,
	'EDT_ID',
	'',
	'E',
	'DT_ID',
	1,
	524296);
INSERT INTO O_ID
	VALUES (0,
	1048591);
INSERT INTO O_OIDA
	VALUES (1048642,
	1048591,
	0);
INSERT INTO O_OBJ
	VALUES (1048592,
	'Synchronous Service',
	16,
	'S_SYNC',
	'A synchronous service (S_SYNC) is a method associated with the domain (S_DOM).  It can be thought of as a global function within the domain.  Synchronous services can be synchronously called from action specifications or used to provide a definition for bridge method  in another domain.',
	1048578);
INSERT INTO O_NBATTR
	VALUES (1048646,
	1048592);
INSERT INTO O_BATTR
	VALUES (1048646,
	1048592);
INSERT INTO O_ATTR
	VALUES (1048646,
	1048592,
	0,
	'Sync_ID',
	'Full Name: Synchronous Service Identifier',
	'',
	'Sync_ID',
	0,
	524294);
INSERT INTO O_REF
	VALUES (1048592,
	1048577,
	0,
	1048577,
	1048600,
	1048626,
	1048627,
	1048647,
	1048582,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (1048647,
	1048592,
	1048577,
	1048577,
	1);
INSERT INTO O_ATTR
	VALUES (1048647,
	1048592,
	1048646,
	'Dom_ID',
	'',
	'',
	'Dom_ID',
	0,
	524296);
INSERT INTO O_NBATTR
	VALUES (1048648,
	1048592);
INSERT INTO O_BATTR
	VALUES (1048648,
	1048592);
INSERT INTO O_ATTR
	VALUES (1048648,
	1048592,
	1048647,
	'Name',
	'Full Name: Name',
	'',
	'Name',
	0,
	524293);
INSERT INTO O_NBATTR
	VALUES (1048649,
	1048592);
INSERT INTO O_BATTR
	VALUES (1048649,
	1048592);
INSERT INTO O_ATTR
	VALUES (1048649,
	1048592,
	1048648,
	'Descrip',
	'Full Name: Description',
	'',
	'Descrip',
	0,
	524293);
INSERT INTO O_NBATTR
	VALUES (1048650,
	1048592);
INSERT INTO O_BATTR
	VALUES (1048650,
	1048592);
INSERT INTO O_ATTR
	VALUES (1048650,
	1048592,
	1048649,
	'Action_Semantics',
	'Full Name: Action Semantics Field
Description: Action Semantics for the synchronous service action',
	'',
	'Action_Semantics',
	0,
	524293);
INSERT INTO O_REF
	VALUES (1048592,
	1048584,
	0,
	1048616,
	1048601,
	1048628,
	1048629,
	1048651,
	1048583,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (1048651,
	1048592,
	1048616,
	1048584,
	1);
INSERT INTO O_ATTR
	VALUES (1048651,
	1048592,
	1048650,
	'DT_ID',
	'',
	'',
	'DT_ID',
	0,
	524296);
INSERT INTO O_NBATTR
	VALUES (1048652,
	1048592);
INSERT INTO O_BATTR
	VALUES (1048652,
	1048592);
INSERT INTO O_ATTR
	VALUES (1048652,
	1048592,
	1048651,
	'Suc_Pars',
	'Full Name: Successful Parse Indicator
Description: Indicates the status of the parse for the bridge action specification in the attribute Action_Semantics
Data Domain: 0=not parsed, 1 = parse successful, 2 = parse unsuccessful, 3 = parse on apply set but never been parsed',
	'',
	'Suc_Pars',
	0,
	524291);
INSERT INTO O_ID
	VALUES (0,
	1048592);
INSERT INTO O_OIDA
	VALUES (1048646,
	1048592,
	0);
INSERT INTO O_RTIDA
	VALUES (1048646,
	1048592,
	0,
	1048602,
	1048630);
INSERT INTO O_OBJ
	VALUES (1048593,
	'Synchronous Service Parameter',
	17,
	'S_SPARM',
	'A parameter to a synchronous service (S_SYNC) is called a synchronous service parameter (S_SPARM).  Synchronous service parameters are either passed in by value, or by reference.  Synchronous service parameters can be accessed by using the param keyword from within a synchronous service action specification.',
	1048578);
INSERT INTO O_NBATTR
	VALUES (1048653,
	1048593);
INSERT INTO O_BATTR
	VALUES (1048653,
	1048593);
INSERT INTO O_ATTR
	VALUES (1048653,
	1048593,
	0,
	'SParm_ID',
	'Full Name: Parameter Identifier',
	'',
	'SParm_ID',
	0,
	524294);
INSERT INTO O_REF
	VALUES (1048593,
	1048592,
	0,
	1048646,
	1048602,
	1048631,
	1048630,
	1048654,
	1048603,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (1048654,
	1048593,
	1048646,
	1048592,
	1);
INSERT INTO O_ATTR
	VALUES (1048654,
	1048593,
	1048653,
	'Sync_ID',
	'',
	'',
	'Sync_ID',
	0,
	524296);
INSERT INTO O_NBATTR
	VALUES (1048655,
	1048593);
INSERT INTO O_BATTR
	VALUES (1048655,
	1048593);
INSERT INTO O_ATTR
	VALUES (1048655,
	1048593,
	1048654,
	'Name',
	'Full Name: Parameter Name',
	'',
	'Name',
	0,
	524293);
INSERT INTO O_REF
	VALUES (1048593,
	1048584,
	0,
	1048616,
	1048603,
	1048632,
	1048633,
	1048656,
	1048584,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (1048656,
	1048593,
	1048616,
	1048584,
	1);
INSERT INTO O_ATTR
	VALUES (1048656,
	1048593,
	1048655,
	'DT_ID',
	'',
	'',
	'DT_ID',
	0,
	524296);
INSERT INTO O_NBATTR
	VALUES (1048657,
	1048593);
INSERT INTO O_BATTR
	VALUES (1048657,
	1048593);
INSERT INTO O_ATTR
	VALUES (1048657,
	1048593,
	1048656,
	'By_Ref',
	'Full Name: By Reference Indicator
Description: Indicates whether or not this parameter is passed by reference
Data Domain: 0 = passed by value, 1 = passed by reference',
	'',
	'By_Ref',
	0,
	524291);
INSERT INTO O_ID
	VALUES (0,
	1048593);
INSERT INTO O_OIDA
	VALUES (1048653,
	1048593,
	0);
INSERT INTO O_IOBJ
	VALUES (1048577,
	1572865,
	5,
	1048578,
	'Relationship',
	'R_REL');
INSERT INTO O_IOBJ
	VALUES (1048578,
	2097153,
	5,
	1048578,
	'Communication Path',
	'CA_COMM');
INSERT INTO O_IOBJ
	VALUES (1048579,
	2097160,
	5,
	1048578,
	'Access Path',
	'CA_ACC');
INSERT INTO O_IOBJ
	VALUES (1048580,
	3145732,
	5,
	1048578,
	'Object',
	'O_OBJ');
INSERT INTO O_IOBJ
	VALUES (1048581,
	3145740,
	5,
	1048578,
	'Imported Object',
	'O_IOBJ');
INSERT INTO R_SIMP
	VALUES (1048577);
INSERT INTO R_REL
	VALUES (1048577,
	1,
	'',
	1048578);
INSERT INTO R_PART
	VALUES (1048577,
	1048577,
	1048577,
	0,
	0,
	'is first level of partitioning for');
INSERT INTO R_RTO
	VALUES (1048577,
	1048577,
	1048577,
	0);
INSERT INTO R_OIR
	VALUES (1048577,
	1048577,
	1048577,
	0);
INSERT INTO R_FORM
	VALUES (1048578,
	1048577,
	1048578,
	1,
	1,
	'is partitioned into');
INSERT INTO R_RGO
	VALUES (1048578,
	1048577,
	1048578);
INSERT INTO R_OIR
	VALUES (1048578,
	1048577,
	1048578,
	0);
INSERT INTO R_SIMP
	VALUES (1048578);
INSERT INTO R_REL
	VALUES (1048578,
	7,
	'',
	1048578);
INSERT INTO R_PART
	VALUES (1048578,
	1048578,
	1048579,
	0,
	0,
	'is a presence of an external entity in');
INSERT INTO R_RTO
	VALUES (1048578,
	1048578,
	1048579,
	0);
INSERT INTO R_OIR
	VALUES (1048578,
	1048578,
	1048579,
	0);
INSERT INTO R_FORM
	VALUES (1048580,
	1048578,
	1048580,
	1,
	1,
	'contains');
INSERT INTO R_RGO
	VALUES (1048580,
	1048578,
	1048580);
INSERT INTO R_OIR
	VALUES (1048580,
	1048578,
	1048580,
	0);
INSERT INTO R_SIMP
	VALUES (1048579);
INSERT INTO R_REL
	VALUES (1048579,
	9,
	'',
	1048578);
INSERT INTO R_PART
	VALUES (1048579,
	1048579,
	1048581,
	0,
	0,
	'is a presence in subsystem model of');
INSERT INTO R_RTO
	VALUES (1048579,
	1048579,
	1048581,
	0);
INSERT INTO R_OIR
	VALUES (1048579,
	1048579,
	1048581,
	0);
INSERT INTO R_FORM
	VALUES (1048580,
	1048579,
	1048582,
	1,
	1,
	'is represented by');
INSERT INTO R_RGO
	VALUES (1048580,
	1048579,
	1048582);
INSERT INTO R_OIR
	VALUES (1048580,
	1048579,
	1048582,
	0);
INSERT INTO R_SIMP
	VALUES (1048580);
INSERT INTO R_REL
	VALUES (1048580,
	10,
	'',
	1048578);
INSERT INTO R_PART
	VALUES (1048579,
	1048580,
	1048583,
	0,
	0,
	'is vehicle of communication for');
INSERT INTO R_RTO
	VALUES (1048579,
	1048580,
	1048583,
	0);
INSERT INTO R_OIR
	VALUES (1048579,
	1048580,
	1048583,
	0);
INSERT INTO R_FORM
	VALUES (1048582,
	1048580,
	1048584,
	1,
	1,
	'can receive asynchronous communication via');
INSERT INTO R_RGO
	VALUES (1048582,
	1048580,
	1048584);
INSERT INTO R_OIR
	VALUES (1048582,
	1048580,
	1048584,
	0);
INSERT INTO R_SIMP
	VALUES (1048581);
INSERT INTO R_REL
	VALUES (1048581,
	12,
	'',
	1048578);
INSERT INTO R_PART
	VALUES (1048579,
	1048581,
	1048585,
	0,
	0,
	'is data for events of');
INSERT INTO R_RTO
	VALUES (1048579,
	1048581,
	1048585,
	0);
INSERT INTO R_OIR
	VALUES (1048579,
	1048581,
	1048585,
	0);
INSERT INTO R_FORM
	VALUES (1048583,
	1048581,
	1048586,
	1,
	1,
	'can asynchronously communicate via');
INSERT INTO R_RGO
	VALUES (1048583,
	1048581,
	1048586);
INSERT INTO R_OIR
	VALUES (1048583,
	1048581,
	1048586,
	0);
INSERT INTO R_SIMP
	VALUES (1048582);
INSERT INTO R_REL
	VALUES (1048582,
	14,
	'',
	1048578);
INSERT INTO R_PART
	VALUES (1048577,
	1048582,
	1048587,
	0,
	0,
	'defines types within');
INSERT INTO R_RTO
	VALUES (1048577,
	1048582,
	1048587,
	0);
INSERT INTO R_OIR
	VALUES (1048577,
	1048582,
	1048587,
	0);
INSERT INTO R_FORM
	VALUES (1048584,
	1048582,
	1048588,
	1,
	1,
	'contains defined');
INSERT INTO R_RGO
	VALUES (1048584,
	1048582,
	1048588);
INSERT INTO R_OIR
	VALUES (1048584,
	1048582,
	1048588,
	0);
INSERT INTO R_SIMP
	VALUES (1048583);
INSERT INTO R_REL
	VALUES (1048583,
	16,
	'',
	1048578);
INSERT INTO R_PART
	VALUES (1048584,
	1048583,
	1048589,
	0,
	0,
	'is defined by');
INSERT INTO R_RTO
	VALUES (1048584,
	1048583,
	1048589,
	0);
INSERT INTO R_OIR
	VALUES (1048584,
	1048583,
	1048589,
	0);
INSERT INTO R_FORM
	VALUES (1048583,
	1048583,
	1048590,
	1,
	1,
	'defines the type of');
INSERT INTO R_RGO
	VALUES (1048583,
	1048583,
	1048590);
INSERT INTO R_OIR
	VALUES (1048583,
	1048583,
	1048590,
	0);
INSERT INTO R_SIMP
	VALUES (1048584);
INSERT INTO R_REL
	VALUES (1048584,
	8,
	'',
	1048578);
INSERT INTO R_PART
	VALUES (1048577,
	1048584,
	1048591,
	0,
	0,
	'interacts with');
INSERT INTO R_RTO
	VALUES (1048577,
	1048584,
	1048591,
	0);
INSERT INTO R_OIR
	VALUES (1048577,
	1048584,
	1048591,
	0);
INSERT INTO R_FORM
	VALUES (1048579,
	1048584,
	1048592,
	1,
	1,
	'interacts with');
INSERT INTO R_RGO
	VALUES (1048579,
	1048584,
	1048592);
INSERT INTO R_OIR
	VALUES (1048579,
	1048584,
	1048592,
	0);
INSERT INTO R_SUBSUP
	VALUES (1048585);
INSERT INTO R_REL
	VALUES (1048585,
	17,
	'',
	1048578);
INSERT INTO R_SUPER
	VALUES (1048584,
	1048585,
	1048593);
INSERT INTO R_RTO
	VALUES (1048584,
	1048585,
	1048593,
	0);
INSERT INTO R_OIR
	VALUES (1048584,
	1048585,
	1048593,
	0);
INSERT INTO R_SUB
	VALUES (1048585,
	1048585,
	1048594);
INSERT INTO R_RGO
	VALUES (1048585,
	1048585,
	1048594);
INSERT INTO R_OIR
	VALUES (1048585,
	1048585,
	1048594,
	0);
INSERT INTO R_SUB
	VALUES (1048586,
	1048585,
	1048595);
INSERT INTO R_RGO
	VALUES (1048586,
	1048585,
	1048595);
INSERT INTO R_OIR
	VALUES (1048586,
	1048585,
	1048595,
	0);
INSERT INTO R_SUB
	VALUES (1048590,
	1048585,
	1048596);
INSERT INTO R_RGO
	VALUES (1048590,
	1048585,
	1048596);
INSERT INTO R_OIR
	VALUES (1048590,
	1048585,
	1048596,
	0);
INSERT INTO R_SIMP
	VALUES (1048586);
INSERT INTO R_REL
	VALUES (1048586,
	18,
	'',
	1048578);
INSERT INTO R_PART
	VALUES (1048585,
	1048586,
	1048597,
	0,
	0,
	'are defined within');
INSERT INTO R_RTO
	VALUES (1048585,
	1048586,
	1048597,
	0);
INSERT INTO R_OIR
	VALUES (1048585,
	1048586,
	1048597,
	0);
INSERT INTO R_FORM
	VALUES (1048586,
	1048586,
	1048598,
	1,
	1,
	'defines domain of');
INSERT INTO R_RGO
	VALUES (1048586,
	1048586,
	1048598);
INSERT INTO R_OIR
	VALUES (1048586,
	1048586,
	1048598,
	0);
INSERT INTO R_SIMP
	VALUES (1048587);
INSERT INTO R_REL
	VALUES (1048587,
	20,
	'',
	1048578);
INSERT INTO R_PART
	VALUES (1048584,
	1048587,
	1048599,
	0,
	0,
	'return value defined by');
INSERT INTO R_RTO
	VALUES (1048584,
	1048587,
	1048599,
	0);
INSERT INTO R_OIR
	VALUES (1048584,
	1048587,
	1048599,
	0);
INSERT INTO R_FORM
	VALUES (1048587,
	1048587,
	1048600,
	1,
	1,
	'defines the return value');
INSERT INTO R_RGO
	VALUES (1048587,
	1048587,
	1048600);
INSERT INTO R_OIR
	VALUES (1048587,
	1048587,
	1048600,
	0);
INSERT INTO R_SIMP
	VALUES (1048588);
INSERT INTO R_REL
	VALUES (1048588,
	21,
	'',
	1048578);
INSERT INTO R_PART
	VALUES (1048587,
	1048588,
	1048601,
	0,
	0,
	'contains');
INSERT INTO R_RTO
	VALUES (1048587,
	1048588,
	1048601,
	0);
INSERT INTO R_OIR
	VALUES (1048587,
	1048588,
	1048601,
	0);
INSERT INTO R_FORM
	VALUES (1048588,
	1048588,
	1048602,
	1,
	1,
	'is part of');
INSERT INTO R_RGO
	VALUES (1048588,
	1048588,
	1048602);
INSERT INTO R_OIR
	VALUES (1048588,
	1048588,
	1048602,
	0);
INSERT INTO R_SIMP
	VALUES (1048589);
INSERT INTO R_REL
	VALUES (1048589,
	19,
	'',
	1048578);
INSERT INTO R_FORM
	VALUES (1048587,
	1048589,
	1048603,
	1,
	1,
	'uses');
INSERT INTO R_RGO
	VALUES (1048587,
	1048589,
	1048603);
INSERT INTO R_OIR
	VALUES (1048587,
	1048589,
	1048603,
	0);
INSERT INTO R_PART
	VALUES (1048579,
	1048589,
	1048604,
	0,
	0,
	'provides access to');
INSERT INTO R_RTO
	VALUES (1048579,
	1048589,
	1048604,
	0);
INSERT INTO R_OIR
	VALUES (1048579,
	1048589,
	1048604,
	0);
INSERT INTO R_SIMP
	VALUES (1048590);
INSERT INTO R_REL
	VALUES (1048590,
	15,
	'',
	1048578);
INSERT INTO R_PART
	VALUES (1048584,
	1048590,
	1048605,
	0,
	0,
	'is defined by');
INSERT INTO R_RTO
	VALUES (1048584,
	1048590,
	1048605,
	0);
INSERT INTO R_OIR
	VALUES (1048584,
	1048590,
	1048605,
	0);
INSERT INTO R_FORM
	VALUES (1048581,
	1048590,
	1048606,
	1,
	1,
	'defines the type of');
INSERT INTO R_RGO
	VALUES (1048581,
	1048590,
	1048606);
INSERT INTO R_OIR
	VALUES (1048581,
	1048590,
	1048606,
	0);
INSERT INTO R_SIMP
	VALUES (1048591);
INSERT INTO R_REL
	VALUES (1048591,
	22,
	'',
	1048578);
INSERT INTO R_PART
	VALUES (1048584,
	1048591,
	1048607,
	0,
	0,
	'is defined by');
INSERT INTO R_RTO
	VALUES (1048584,
	1048591,
	1048607,
	0);
INSERT INTO R_OIR
	VALUES (1048584,
	1048591,
	1048607,
	0);
INSERT INTO R_FORM
	VALUES (1048588,
	1048591,
	1048608,
	1,
	1,
	'defines the type of');
INSERT INTO R_RGO
	VALUES (1048588,
	1048591,
	1048608);
INSERT INTO R_OIR
	VALUES (1048588,
	1048591,
	1048608,
	0);
INSERT INTO R_SIMP
	VALUES (1048592);
INSERT INTO R_REL
	VALUES (1048592,
	11,
	'',
	1048578);
INSERT INTO R_FORM
	VALUES (1048581,
	1048592,
	1048609,
	1,
	1,
	'can be accessed synchronously via');
INSERT INTO R_RGO
	VALUES (1048581,
	1048592,
	1048609);
INSERT INTO R_OIR
	VALUES (1048581,
	1048592,
	1048609,
	0);
INSERT INTO R_PART
	VALUES (1048579,
	1048592,
	1048610,
	0,
	0,
	'is data for');
INSERT INTO R_RTO
	VALUES (1048579,
	1048592,
	1048610,
	0);
INSERT INTO R_OIR
	VALUES (1048579,
	1048592,
	1048610,
	0);
INSERT INTO R_ASSOC
	VALUES (1048593);
INSERT INTO R_REL
	VALUES (1048593,
	13,
	'',
	1048578);
INSERT INTO R_AONE
	VALUES (1048583,
	1048593,
	1048611,
	1,
	1,
	'may carry');
INSERT INTO R_RTO
	VALUES (1048583,
	1048593,
	1048611,
	0);
INSERT INTO R_OIR
	VALUES (1048583,
	1048593,
	1048611,
	0);
INSERT INTO R_AOTH
	VALUES (1048582,
	1048593,
	1048612,
	1,
	1,
	'is carried via');
INSERT INTO R_RTO
	VALUES (1048582,
	1048593,
	1048612,
	0);
INSERT INTO R_OIR
	VALUES (1048582,
	1048593,
	1048612,
	0);
INSERT INTO R_ASSR
	VALUES (1048589,
	1048593,
	1048613,
	0);
INSERT INTO R_RGO
	VALUES (1048589,
	1048593,
	1048613);
INSERT INTO R_OIR
	VALUES (1048589,
	1048593,
	1048613,
	0);
INSERT INTO R_SIMP
	VALUES (1048594);
INSERT INTO R_REL
	VALUES (1048594,
	2,
	'',
	1048578);
INSERT INTO R_PART
	VALUES (1048578,
	1048594,
	1048614,
	0,
	0,
	'is contained in');
INSERT INTO R_RTO
	VALUES (1048578,
	1048594,
	1048614,
	0);
INSERT INTO R_OIR
	VALUES (1048578,
	1048594,
	1048614,
	0);
INSERT INTO R_FORM
	VALUES (3145732,
	1048594,
	1048615,
	1,
	1,
	'is decomposed into');
INSERT INTO R_RGO
	VALUES (3145732,
	1048594,
	1048615);
INSERT INTO R_OIR
	VALUES (3145732,
	1048594,
	1048615,
	1048580);
INSERT INTO R_SIMP
	VALUES (1048595);
INSERT INTO R_REL
	VALUES (1048595,
	4,
	'',
	1048578);
INSERT INTO R_PART
	VALUES (1048578,
	1048595,
	1048616,
	0,
	0,
	'abstracts associations between objects in');
INSERT INTO R_RTO
	VALUES (1048578,
	1048595,
	1048616,
	0);
INSERT INTO R_OIR
	VALUES (1048578,
	1048595,
	1048616,
	0);
INSERT INTO R_FORM
	VALUES (1572865,
	1048595,
	1048617,
	1,
	1,
	'contains');
INSERT INTO R_RGO
	VALUES (1572865,
	1048595,
	1048617);
INSERT INTO R_OIR
	VALUES (1572865,
	1048595,
	1048617,
	1048577);
INSERT INTO R_SIMP
	VALUES (1048596);
INSERT INTO R_REL
	VALUES (1048596,
	5,
	'

Notes:

',
	1048578);
INSERT INTO R_PART
	VALUES (1048578,
	1048596,
	1048618,
	0,
	0,
	'abstracts asynchronous communication between objects in');
INSERT INTO R_RTO
	VALUES (1048578,
	1048596,
	1048618,
	0);
INSERT INTO R_OIR
	VALUES (1048578,
	1048596,
	1048618,
	0);
INSERT INTO R_FORM
	VALUES (2097153,
	1048596,
	1048619,
	1,
	1,
	'contains');
INSERT INTO R_RGO
	VALUES (2097153,
	1048596,
	1048619);
INSERT INTO R_OIR
	VALUES (2097153,
	1048596,
	1048619,
	1048578);
INSERT INTO R_SIMP
	VALUES (1048597);
INSERT INTO R_REL
	VALUES (1048597,
	6,
	'

Notes:

',
	1048578);
INSERT INTO R_PART
	VALUES (1048578,
	1048597,
	1048620,
	0,
	0,
	'abstracts synchronous data access between objects in');
INSERT INTO R_RTO
	VALUES (1048578,
	1048597,
	1048620,
	0);
INSERT INTO R_OIR
	VALUES (1048578,
	1048597,
	1048620,
	0);
INSERT INTO R_FORM
	VALUES (2097160,
	1048597,
	1048621,
	1,
	1,
	'contains');
INSERT INTO R_RGO
	VALUES (2097160,
	1048597,
	1048621);
INSERT INTO R_OIR
	VALUES (2097160,
	1048597,
	1048621,
	1048579);
INSERT INTO R_SIMP
	VALUES (1048598);
INSERT INTO R_REL
	VALUES (1048598,
	3,
	'',
	1048578);
INSERT INTO R_PART
	VALUES (1048578,
	1048598,
	1048622,
	0,
	0,
	'represents an object from another subsystem in');
INSERT INTO R_RTO
	VALUES (1048578,
	1048598,
	1048622,
	0);
INSERT INTO R_OIR
	VALUES (1048578,
	1048598,
	1048622,
	0);
INSERT INTO R_FORM
	VALUES (3145740,
	1048598,
	1048623,
	1,
	1,
	'can contain objects from other subsystems via');
INSERT INTO R_RGO
	VALUES (3145740,
	1048598,
	1048623);
INSERT INTO R_OIR
	VALUES (3145740,
	1048598,
	1048623,
	1048581);
INSERT INTO R_SIMP
	VALUES (1048599);
INSERT INTO R_REL
	VALUES (1048599,
	27,
	'',
	1048578);
INSERT INTO R_PART
	VALUES (1048590,
	1048599,
	1048624,
	0,
	0,
	'is defined by');
INSERT INTO R_RTO
	VALUES (1048590,
	1048599,
	1048624,
	0);
INSERT INTO R_OIR
	VALUES (1048590,
	1048599,
	1048624,
	0);
INSERT INTO R_FORM
	VALUES (1048591,
	1048599,
	1048625,
	1,
	1,
	'defines');
INSERT INTO R_RGO
	VALUES (1048591,
	1048599,
	1048625);
INSERT INTO R_OIR
	VALUES (1048591,
	1048599,
	1048625,
	0);
INSERT INTO R_SIMP
	VALUES (1048600);
INSERT INTO R_REL
	VALUES (1048600,
	23,
	'',
	1048578);
INSERT INTO R_FORM
	VALUES (1048592,
	1048600,
	1048626,
	1,
	1,
	'defines');
INSERT INTO R_RGO
	VALUES (1048592,
	1048600,
	1048626);
INSERT INTO R_OIR
	VALUES (1048592,
	1048600,
	1048626,
	0);
INSERT INTO R_PART
	VALUES (1048577,
	1048600,
	1048627,
	0,
	0,
	'is defined in ');
INSERT INTO R_RTO
	VALUES (1048577,
	1048600,
	1048627,
	0);
INSERT INTO R_OIR
	VALUES (1048577,
	1048600,
	1048627,
	0);
INSERT INTO R_SIMP
	VALUES (1048601);
INSERT INTO R_REL
	VALUES (1048601,
	25,
	'',
	1048578);
INSERT INTO R_FORM
	VALUES (1048592,
	1048601,
	1048628,
	1,
	1,
	'defines return type');
INSERT INTO R_RGO
	VALUES (1048592,
	1048601,
	1048628);
INSERT INTO R_OIR
	VALUES (1048592,
	1048601,
	1048628,
	0);
INSERT INTO R_PART
	VALUES (1048584,
	1048601,
	1048629,
	0,
	0,
	'has return type of');
INSERT INTO R_RTO
	VALUES (1048584,
	1048601,
	1048629,
	0);
INSERT INTO R_OIR
	VALUES (1048584,
	1048601,
	1048629,
	0);
INSERT INTO R_SIMP
	VALUES (1048602);
INSERT INTO R_REL
	VALUES (1048602,
	24,
	'',
	1048578);
INSERT INTO R_PART
	VALUES (1048592,
	1048602,
	1048630,
	0,
	0,
	'is defined for');
INSERT INTO R_RTO
	VALUES (1048592,
	1048602,
	1048630,
	0);
INSERT INTO R_OIR
	VALUES (1048592,
	1048602,
	1048630,
	0);
INSERT INTO R_FORM
	VALUES (1048593,
	1048602,
	1048631,
	1,
	1,
	'defines');
INSERT INTO R_RGO
	VALUES (1048593,
	1048602,
	1048631);
INSERT INTO R_OIR
	VALUES (1048593,
	1048602,
	1048631,
	0);
INSERT INTO R_SIMP
	VALUES (1048603);
INSERT INTO R_REL
	VALUES (1048603,
	26,
	'',
	1048578);
INSERT INTO R_FORM
	VALUES (1048593,
	1048603,
	1048632,
	1,
	1,
	'describes type of');
INSERT INTO R_RGO
	VALUES (1048593,
	1048603,
	1048632);
INSERT INTO R_OIR
	VALUES (1048593,
	1048603,
	1048632,
	0);
INSERT INTO R_PART
	VALUES (1048584,
	1048603,
	1048633,
	0,
	0,
	'is typed by ');
INSERT INTO R_RTO
	VALUES (1048584,
	1048603,
	1048633,
	0);
INSERT INTO R_OIR
	VALUES (1048584,
	1048603,
	1048633,
	0);
INSERT INTO S_SS
	VALUES (1572867,
	'Relationship',
	'A relationship (R_REL) captures an association between things in the real world. A relationship is stated in terms of the formal objects (O_OBJ) that model the real world entities participating in the association. There can be any number of relationships between the same two objects and any object can participate in any number of relationships with other objects.',
	'R',
	201,
	2531670,
	1572867);
INSERT INTO O_OBJ
	VALUES (1572865,
	'Relationship',
	201,
	'R_REL',
	'A Relationship captures an association that exists between things in the real world. A Relationship is stated in terms of the formal Objects that participate in the association.',
	1572867);
INSERT INTO O_NBATTR
	VALUES (1572865,
	1572865);
INSERT INTO O_BATTR
	VALUES (1572865,
	1572865);
INSERT INTO O_ATTR
	VALUES (1572865,
	1572865,
	0,
	'Rel_ID',
	'Full Name: Relationship Identifier',
	'',
	'Rel_ID',
	0,
	524294);
INSERT INTO O_NBATTR
	VALUES (1572866,
	1572865);
INSERT INTO O_BATTR
	VALUES (1572866,
	1572865);
INSERT INTO O_ATTR
	VALUES (1572866,
	1572865,
	1572865,
	'Numb',
	'Full Name: Relationship Number',
	'',
	'Numb',
	0,
	524291);
INSERT INTO O_NBATTR
	VALUES (1572867,
	1572865);
INSERT INTO O_BATTR
	VALUES (1572867,
	1572865);
INSERT INTO O_ATTR
	VALUES (1572867,
	1572865,
	1572866,
	'Descrip',
	'Full Name: Relationship Description',
	'',
	'Descrip',
	0,
	524293);
INSERT INTO O_REF
	VALUES (1572865,
	1048578,
	0,
	1048582,
	1048595,
	1048617,
	1048616,
	1572868,
	1572865,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (1572868,
	1572865,
	1048582,
	1048578,
	1);
INSERT INTO O_ATTR
	VALUES (1572868,
	1572865,
	1572867,
	'SS_ID',
	'',
	'',
	'SS_ID',
	0,
	524296);
INSERT INTO O_ID
	VALUES (0,
	1572865);
INSERT INTO O_OIDA
	VALUES (1572865,
	1572865,
	0);
INSERT INTO O_RTIDA
	VALUES (1572865,
	1572865,
	0,
	1572866,
	1572868);
INSERT INTO O_RTIDA
	VALUES (1572865,
	1572865,
	0,
	1572865,
	1572865);
INSERT INTO O_OBJ
	VALUES (1572866,
	'Simple Relationship',
	205,
	'R_SIMP',
	'A simple relationship (R_SIMP) is a relationship between two objects which is formalized with referential attributes in one of the objects.  The object that holds the referential attributes for  the relationship is know as the referring object in the relationship (R_RGO) and is the formalizer of the relationship (R_FORM).  The object that participates in the relationship and carries the identifying attributes used to formalize the relationship is the referred to object  (R_RTO) and is a participant in the relationship (R_PART).',
	1572867);
INSERT INTO O_REF
	VALUES (1572866,
	1572865,
	0,
	1572865,
	1572866,
	1572869,
	1572868,
	1572869,
	1572866,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (1572869,
	1572866,
	1572865,
	1572865,
	1);
INSERT INTO O_ATTR
	VALUES (1572869,
	1572866,
	0,
	'Rel_ID',
	'',
	'',
	'Rel_ID',
	0,
	524296);
INSERT INTO O_ID
	VALUES (0,
	1572866);
INSERT INTO O_OIDA
	VALUES (1572869,
	1572866,
	0);
INSERT INTO O_RTIDA
	VALUES (1572869,
	1572866,
	0,
	1572867,
	1572874);
INSERT INTO O_RTIDA
	VALUES (1572869,
	1572866,
	0,
	1572868,
	1572876);
INSERT INTO O_OBJ
	VALUES (1572867,
	'Associative Relationship',
	208,
	'R_ASSOC',
	'An associative relationship (R_ASSOC) is a relationship between two objects (R_AONE and R_AOTH)  which is formalized in an associative object (R_ASSR).  The object that holds the referential attributes for  the relationship is know as the referring object in the relationship (R_RGO) and the object that participates in the relationship and carries the identifying attributes used to formalize the relationship is the referred to object  (R_RTO).  Associative relationships require formalization of the relationship in an object called the associator (R_ASSR).',
	1572867);
INSERT INTO O_REF
	VALUES (1572867,
	1572865,
	0,
	1572865,
	1572866,
	1572871,
	1572868,
	1572870,
	1572867,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (1572870,
	1572867,
	1572865,
	1572865,
	1);
INSERT INTO O_ATTR
	VALUES (1572870,
	1572867,
	0,
	'Rel_ID',
	'',
	'',
	'Rel_ID',
	0,
	524296);
INSERT INTO O_ID
	VALUES (0,
	1572867);
INSERT INTO O_OIDA
	VALUES (1572870,
	1572867,
	0);
INSERT INTO O_RTIDA
	VALUES (1572870,
	1572867,
	0,
	1572870,
	1572880);
INSERT INTO O_RTIDA
	VALUES (1572870,
	1572867,
	0,
	1572871,
	1572882);
INSERT INTO O_RTIDA
	VALUES (1572870,
	1572867,
	0,
	1572869,
	1572878);
INSERT INTO O_OBJ
	VALUES (1572868,
	'Subtype Supertype Relationship',
	212,
	'R_SUBSUP',
	'A supertype/subtype relationship (R_SUBSUP) is a relationship between two objects, a supertype (R_SUPER) and a subtype (R_SUB).   The object that holds the referential attributes for  the relationship is know as the referring object in the relationship (R_RGO) and the object that participates in the relationship and carries the identifying attributes used to formalize the relationship is the referred to object  (R_RTO).  Supertype/Subtype relationships are always formalized in the subtype (R_SUB).',
	1572867);
INSERT INTO O_REF
	VALUES (1572868,
	1572865,
	0,
	1572865,
	1572866,
	1572872,
	1572868,
	1572871,
	1572868,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (1572871,
	1572868,
	1572865,
	1572865,
	1);
INSERT INTO O_ATTR
	VALUES (1572871,
	1572868,
	0,
	'Rel_ID',
	'',
	'',
	'Rel_ID',
	0,
	524296);
INSERT INTO O_ID
	VALUES (0,
	1572868);
INSERT INTO O_OIDA
	VALUES (1572871,
	1572868,
	0);
INSERT INTO O_RTIDA
	VALUES (1572871,
	1572868,
	0,
	1572872,
	1572884);
INSERT INTO O_RTIDA
	VALUES (1572871,
	1572868,
	0,
	1572873,
	1572886);
INSERT INTO O_OBJ
	VALUES (1572869,
	'Composition Relationship',
	215,
	'R_COMP',
	'A compositional relationship (R_COMP) is a relationship between two objects R_CONE, on one side of the relationship, and R_COTH, on the other.  A compositional relationship is one  that is formalized by a query along other relationships in a loop (R_COMP.Rel_Chn).',
	1572867);
INSERT INTO O_REF
	VALUES (1572869,
	1572865,
	0,
	1572865,
	1572866,
	1572870,
	1572868,
	1572872,
	1572869,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (1572872,
	1572869,
	1572865,
	1572865,
	1);
INSERT INTO O_ATTR
	VALUES (1572872,
	1572869,
	0,
	'Rel_ID',
	'',
	'',
	'Rel_ID',
	0,
	524296);
INSERT INTO O_NBATTR
	VALUES (1572873,
	1572869);
INSERT INTO O_BATTR
	VALUES (1572873,
	1572869);
INSERT INTO O_ATTR
	VALUES (1572873,
	1572869,
	1572872,
	'Rel_Chn',
	'Full Name: Relationship Chain
Description: Composed relationship chain as it appears in the model.
',
	'',
	'Rel_Chn',
	0,
	524293);
INSERT INTO O_ID
	VALUES (0,
	1572869);
INSERT INTO O_OIDA
	VALUES (1572872,
	1572869,
	0);
INSERT INTO O_RTIDA
	VALUES (1572872,
	1572869,
	0,
	1572874,
	1572888);
INSERT INTO O_RTIDA
	VALUES (1572872,
	1572869,
	0,
	1572875,
	1572890);
INSERT INTO O_OBJ
	VALUES (1572870,
	'Object As Simple Participant',
	206,
	'R_PART',
	'An Object As Simple Participant is the referred to object in a simple relationship.',
	1572867);
INSERT INTO O_REF
	VALUES (1572870,
	1572880,
	0,
	1572924,
	1572878,
	1572902,
	1572900,
	1572874,
	1572887,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (1572874,
	1572870,
	3145751,
	3145732,
	1);
INSERT INTO O_ATTR
	VALUES (1572874,
	1572870,
	0,
	'Obj_ID',
	'',
	'',
	'Obj_ID',
	0,
	524296);
INSERT INTO O_REF
	VALUES (1572870,
	1572866,
	0,
	1572869,
	1572867,
	1572873,
	1572874,
	1572875,
	1572870,
	0,
	0,
	'');
INSERT INTO O_REF
	VALUES (1572870,
	1572880,
	0,
	1572925,
	1572878,
	1572902,
	1572900,
	1572875,
	1572888,
	1572870,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (1572875,
	1572870,
	1572865,
	1572865,
	1);
INSERT INTO O_ATTR
	VALUES (1572875,
	1572870,
	1572874,
	'Rel_ID',
	'',
	'',
	'Rel_ID',
	0,
	524296);
INSERT INTO O_REF
	VALUES (1572870,
	1572880,
	0,
	1572926,
	1572878,
	1572902,
	1572900,
	1572876,
	1572889,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (1572876,
	1572870,
	1572922,
	1572879,
	1);
INSERT INTO O_ATTR
	VALUES (1572876,
	1572870,
	1572875,
	'OIR_ID',
	'',
	'',
	'OIR_ID',
	0,
	524296);
INSERT INTO O_NBATTR
	VALUES (1572877,
	1572870);
INSERT INTO O_BATTR
	VALUES (1572877,
	1572870);
INSERT INTO O_ATTR
	VALUES (1572877,
	1572870,
	1572876,
	'Mult',
	'Full Name: Multiplicity
Description: Multiplicity at the participant end of the relationship
Data Domain: 0 = one, 1 = many',
	'',
	'Mult',
	0,
	524291);
INSERT INTO O_NBATTR
	VALUES (1572878,
	1572870);
INSERT INTO O_BATTR
	VALUES (1572878,
	1572870);
INSERT INTO O_ATTR
	VALUES (1572878,
	1572870,
	1572877,
	'Cond',
	'Full Name: Conditionality
Description: Conditionality at the participant end of the relationship
Data Domain: 0 = unconditional, 1 = conditional',
	'',
	'Cond',
	0,
	524291);
INSERT INTO O_NBATTR
	VALUES (1572879,
	1572870);
INSERT INTO O_BATTR
	VALUES (1572879,
	1572870);
INSERT INTO O_ATTR
	VALUES (1572879,
	1572870,
	1572878,
	'Txt_Phrs',
	'Full Name: Text Phrase
Description: Text phrase at the participant end of the relationship
',
	'',
	'Txt_Phrs',
	0,
	524293);
INSERT INTO O_ID
	VALUES (0,
	1572870);
INSERT INTO O_OIDA
	VALUES (1572875,
	1572870,
	0);
INSERT INTO O_OIDA
	VALUES (1572876,
	1572870,
	0);
INSERT INTO O_OIDA
	VALUES (1572874,
	1572870,
	0);
INSERT INTO O_OBJ
	VALUES (1572871,
	'Object As Simple Formalizer',
	207,
	'R_FORM',
	'An Object As Simple Formalizer is the referring object in a simple relationship.',
	1572867);
INSERT INTO O_REF
	VALUES (1572871,
	1572881,
	0,
	1572928,
	1572877,
	1572898,
	1572896,
	1572880,
	1572890,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (1572880,
	1572871,
	3145751,
	3145732,
	1);
INSERT INTO O_ATTR
	VALUES (1572880,
	1572871,
	0,
	'Obj_ID',
	'',
	'',
	'Obj_ID',
	0,
	524296);
INSERT INTO O_REF
	VALUES (1572871,
	1572866,
	0,
	1572869,
	1572868,
	1572875,
	1572876,
	1572881,
	1572871,
	0,
	0,
	'');
INSERT INTO O_REF
	VALUES (1572871,
	1572881,
	0,
	1572929,
	1572877,
	1572898,
	1572896,
	1572881,
	1572891,
	1572871,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (1572881,
	1572871,
	1572865,
	1572865,
	1);
INSERT INTO O_ATTR
	VALUES (1572881,
	1572871,
	1572880,
	'Rel_ID',
	'',
	'',
	'Rel_ID',
	0,
	524296);
INSERT INTO O_REF
	VALUES (1572871,
	1572881,
	0,
	1572930,
	1572877,
	1572898,
	1572896,
	1572882,
	1572892,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (1572882,
	1572871,
	1572922,
	1572879,
	1);
INSERT INTO O_ATTR
	VALUES (1572882,
	1572871,
	1572881,
	'OIR_ID',
	'',
	'',
	'OIR_ID',
	0,
	524296);
INSERT INTO O_NBATTR
	VALUES (1572883,
	1572871);
INSERT INTO O_BATTR
	VALUES (1572883,
	1572871);
INSERT INTO O_ATTR
	VALUES (1572883,
	1572871,
	1572882,
	'Mult',
	'Full Name: Multiplicity
Description: Multiplicity at the formalizing end of the relationship
Data Domain: 0 = one, 1 = many',
	'',
	'Mult',
	0,
	524291);
INSERT INTO O_NBATTR
	VALUES (1572884,
	1572871);
INSERT INTO O_BATTR
	VALUES (1572884,
	1572871);
INSERT INTO O_ATTR
	VALUES (1572884,
	1572871,
	1572883,
	'Cond',
	'Full Name: Conditionality
Description: Conditionality at the formalizing end of the relationship
Data Domain: 0 = unconditional, 1 = conditional',
	'',
	'Cond',
	0,
	524291);
INSERT INTO O_NBATTR
	VALUES (1572885,
	1572871);
INSERT INTO O_BATTR
	VALUES (1572885,
	1572871);
INSERT INTO O_ATTR
	VALUES (1572885,
	1572871,
	1572884,
	'Txt_Phrs',
	'Full Name: Text Phrase
Description: Text phrase at the formalizing end of the relationship
',
	'',
	'Txt_Phrs',
	0,
	524293);
INSERT INTO O_ID
	VALUES (0,
	1572871);
INSERT INTO O_OIDA
	VALUES (1572881,
	1572871,
	0);
INSERT INTO O_OIDA
	VALUES (1572880,
	1572871,
	0);
INSERT INTO O_OIDA
	VALUES (1572882,
	1572871,
	0);
INSERT INTO O_OBJ
	VALUES (1572872,
	'Object As Associated One Side',
	209,
	'R_AONE',
	'In an associative relationship, this is one of the two objects in the relationship.   This object is the one at the other end of the relationship from R_AOTH.',
	1572867);
INSERT INTO O_REF
	VALUES (1572872,
	1572880,
	0,
	1572924,
	1572878,
	1572903,
	1572900,
	1572886,
	1572893,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (1572886,
	1572872,
	3145751,
	3145732,
	1);
INSERT INTO O_ATTR
	VALUES (1572886,
	1572872,
	0,
	'Obj_ID',
	'',
	'',
	'Obj_ID',
	0,
	524296);
INSERT INTO O_REF
	VALUES (1572872,
	1572867,
	0,
	1572870,
	1572869,
	1572877,
	1572878,
	1572887,
	1572872,
	0,
	0,
	'');
INSERT INTO O_REF
	VALUES (1572872,
	1572880,
	0,
	1572925,
	1572878,
	1572903,
	1572900,
	1572887,
	1572894,
	1572872,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (1572887,
	1572872,
	1572865,
	1572865,
	1);
INSERT INTO O_ATTR
	VALUES (1572887,
	1572872,
	1572886,
	'Rel_ID',
	'',
	'',
	'Rel_ID',
	0,
	524296);
INSERT INTO O_REF
	VALUES (1572872,
	1572880,
	0,
	1572926,
	1572878,
	1572903,
	1572900,
	1572888,
	1572895,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (1572888,
	1572872,
	1572922,
	1572879,
	1);
INSERT INTO O_ATTR
	VALUES (1572888,
	1572872,
	1572887,
	'OIR_ID',
	'',
	'',
	'OIR_ID',
	0,
	524296);
INSERT INTO O_NBATTR
	VALUES (1572889,
	1572872);
INSERT INTO O_BATTR
	VALUES (1572889,
	1572872);
INSERT INTO O_ATTR
	VALUES (1572889,
	1572872,
	1572888,
	'Mult',
	'Full Name: Multiplicity
Description: Multiplicity at one end of the associative relationship
Data Domain: 0 = one, 1 = many',
	'',
	'Mult',
	0,
	524291);
INSERT INTO O_NBATTR
	VALUES (1572890,
	1572872);
INSERT INTO O_BATTR
	VALUES (1572890,
	1572872);
INSERT INTO O_ATTR
	VALUES (1572890,
	1572872,
	1572889,
	'Cond',
	'Full Name: Conditionality
Description: Conditionality at one end of the associative relationship
Data Domain: 0 = unconditional, 1 = conditional',
	'',
	'Cond',
	0,
	524291);
INSERT INTO O_NBATTR
	VALUES (1572891,
	1572872);
INSERT INTO O_BATTR
	VALUES (1572891,
	1572872);
INSERT INTO O_ATTR
	VALUES (1572891,
	1572872,
	1572890,
	'Txt_Phrs',
	'Full Name: Text Phrase
Description: Text phrase at one end of the associative relationship
',
	'',
	'Txt_Phrs',
	0,
	524293);
INSERT INTO O_ID
	VALUES (0,
	1572872);
INSERT INTO O_OIDA
	VALUES (1572887,
	1572872,
	0);
INSERT INTO O_OIDA
	VALUES (1572888,
	1572872,
	0);
INSERT INTO O_OIDA
	VALUES (1572886,
	1572872,
	0);
INSERT INTO O_OBJ
	VALUES (1572873,
	'Object As Associated Other Side',
	210,
	'R_AOTH',
	'In an associative relationship, this is one of the two objects in the relationship.  This object is the one at the other end of the relationship from R_AONE.',
	1572867);
INSERT INTO O_REF
	VALUES (1572873,
	1572880,
	0,
	1572924,
	1572878,
	1572904,
	1572900,
	1572892,
	1572896,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (1572892,
	1572873,
	3145751,
	3145732,
	1);
INSERT INTO O_ATTR
	VALUES (1572892,
	1572873,
	0,
	'Obj_ID',
	'',
	'',
	'Obj_ID',
	0,
	524296);
INSERT INTO O_REF
	VALUES (1572873,
	1572867,
	0,
	1572870,
	1572870,
	1572879,
	1572880,
	1572893,
	1572873,
	0,
	0,
	'');
INSERT INTO O_REF
	VALUES (1572873,
	1572880,
	0,
	1572925,
	1572878,
	1572904,
	1572900,
	1572893,
	1572897,
	1572873,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (1572893,
	1572873,
	1572865,
	1572865,
	1);
INSERT INTO O_ATTR
	VALUES (1572893,
	1572873,
	1572892,
	'Rel_ID',
	'',
	'',
	'Rel_ID',
	0,
	524296);
INSERT INTO O_REF
	VALUES (1572873,
	1572880,
	0,
	1572926,
	1572878,
	1572904,
	1572900,
	1572894,
	1572898,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (1572894,
	1572873,
	1572922,
	1572879,
	1);
INSERT INTO O_ATTR
	VALUES (1572894,
	1572873,
	1572893,
	'OIR_ID',
	'',
	'',
	'OIR_ID',
	0,
	524296);
INSERT INTO O_NBATTR
	VALUES (1572895,
	1572873);
INSERT INTO O_BATTR
	VALUES (1572895,
	1572873);
INSERT INTO O_ATTR
	VALUES (1572895,
	1572873,
	1572894,
	'Mult',
	'Full Name: Multiplicity
Description: Multiplicity at one end of the associative relationship. 
Data Domain: 0 = one, 1 = many',
	'',
	'Mult',
	0,
	524291);
INSERT INTO O_NBATTR
	VALUES (1572896,
	1572873);
INSERT INTO O_BATTR
	VALUES (1572896,
	1572873);
INSERT INTO O_ATTR
	VALUES (1572896,
	1572873,
	1572895,
	'Cond',
	'Full Name: Conditionality
Description: Conditionality at one end of the associative relationship
Data Domain: 0 = unconditional, 1 = conditional',
	'',
	'Cond',
	0,
	524291);
INSERT INTO O_NBATTR
	VALUES (1572897,
	1572873);
INSERT INTO O_BATTR
	VALUES (1572897,
	1572873);
INSERT INTO O_ATTR
	VALUES (1572897,
	1572873,
	1572896,
	'Txt_Phrs',
	'Full Name: Text Phrase
Description: Text phrase at one end of the associative relationship
',
	'',
	'Txt_Phrs',
	0,
	524293);
INSERT INTO O_ID
	VALUES (0,
	1572873);
INSERT INTO O_OIDA
	VALUES (1572892,
	1572873,
	0);
INSERT INTO O_OIDA
	VALUES (1572893,
	1572873,
	0);
INSERT INTO O_OIDA
	VALUES (1572894,
	1572873,
	0);
INSERT INTO O_OBJ
	VALUES (1572874,
	'Object As Associator',
	211,
	'R_ASSR',
	'The Associator is the object that formalizes the associative relationship between two objects.',
	1572867);
INSERT INTO O_REF
	VALUES (1572874,
	1572881,
	0,
	1572928,
	1572877,
	1572899,
	1572896,
	1572898,
	1572899,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (1572898,
	1572874,
	3145751,
	3145732,
	1);
INSERT INTO O_ATTR
	VALUES (1572898,
	1572874,
	0,
	'Obj_ID',
	'',
	'',
	'Obj_ID',
	0,
	524296);
INSERT INTO O_REF
	VALUES (1572874,
	1572867,
	0,
	1572870,
	1572871,
	1572881,
	1572882,
	1572899,
	1572874,
	0,
	0,
	'');
INSERT INTO O_REF
	VALUES (1572874,
	1572881,
	0,
	1572929,
	1572877,
	1572899,
	1572896,
	1572899,
	1572900,
	1572874,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (1572899,
	1572874,
	1572865,
	1572865,
	1);
INSERT INTO O_ATTR
	VALUES (1572899,
	1572874,
	1572898,
	'Rel_ID',
	'',
	'',
	'Rel_ID',
	0,
	524296);
INSERT INTO O_REF
	VALUES (1572874,
	1572881,
	0,
	1572930,
	1572877,
	1572899,
	1572896,
	1572900,
	1572901,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (1572900,
	1572874,
	1572922,
	1572879,
	1);
INSERT INTO O_ATTR
	VALUES (1572900,
	1572874,
	1572899,
	'OIR_ID',
	'',
	'',
	'OIR_ID',
	0,
	524296);
INSERT INTO O_NBATTR
	VALUES (1572901,
	1572874);
INSERT INTO O_BATTR
	VALUES (1572901,
	1572874);
INSERT INTO O_ATTR
	VALUES (1572901,
	1572874,
	1572900,
	'Mult',
	'Full Name: Multiplicity
Description: Multiplicity of the associator.  Indicates how many instances of the associator may exist for each instance of the relationship between the two related objects.
Data Domain: 0 = one, 1 = many',
	'',
	'Mult',
	0,
	524291);
INSERT INTO O_ID
	VALUES (0,
	1572874);
INSERT INTO O_OIDA
	VALUES (1572900,
	1572874,
	0);
INSERT INTO O_OIDA
	VALUES (1572898,
	1572874,
	0);
INSERT INTO O_OIDA
	VALUES (1572899,
	1572874,
	0);
INSERT INTO O_OBJ
	VALUES (1572875,
	'Object As Supertype',
	213,
	'R_SUPER',
	'',
	1572867);
INSERT INTO O_REF
	VALUES (1572875,
	1572880,
	0,
	1572924,
	1572878,
	1572901,
	1572900,
	1572902,
	1572902,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (1572902,
	1572875,
	3145751,
	3145732,
	1);
INSERT INTO O_ATTR
	VALUES (1572902,
	1572875,
	0,
	'Obj_ID',
	'',
	'',
	'Obj_ID',
	0,
	524296);
INSERT INTO O_REF
	VALUES (1572875,
	1572868,
	0,
	1572871,
	1572872,
	1572883,
	1572884,
	1572903,
	1572875,
	0,
	0,
	'');
INSERT INTO O_REF
	VALUES (1572875,
	1572880,
	0,
	1572925,
	1572878,
	1572901,
	1572900,
	1572903,
	1572903,
	1572875,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (1572903,
	1572875,
	1572865,
	1572865,
	1);
INSERT INTO O_ATTR
	VALUES (1572903,
	1572875,
	1572902,
	'Rel_ID',
	'',
	'',
	'Rel_ID',
	0,
	524296);
INSERT INTO O_REF
	VALUES (1572875,
	1572880,
	0,
	1572926,
	1572878,
	1572901,
	1572900,
	1572904,
	1572904,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (1572904,
	1572875,
	1572922,
	1572879,
	1);
INSERT INTO O_ATTR
	VALUES (1572904,
	1572875,
	1572903,
	'OIR_ID',
	'',
	'',
	'OIR_ID',
	0,
	524296);
INSERT INTO O_ID
	VALUES (0,
	1572875);
INSERT INTO O_OIDA
	VALUES (1572904,
	1572875,
	0);
INSERT INTO O_OIDA
	VALUES (1572902,
	1572875,
	0);
INSERT INTO O_OIDA
	VALUES (1572903,
	1572875,
	0);
INSERT INTO O_OBJ
	VALUES (1572876,
	'Object As Subtype',
	214,
	'R_SUB',
	'',
	1572867);
INSERT INTO O_REF
	VALUES (1572876,
	1572881,
	0,
	1572928,
	1572877,
	1572897,
	1572896,
	1572905,
	1572905,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (1572905,
	1572876,
	3145751,
	3145732,
	1);
INSERT INTO O_ATTR
	VALUES (1572905,
	1572876,
	0,
	'Obj_ID',
	'',
	'',
	'Obj_ID',
	0,
	524296);
INSERT INTO O_REF
	VALUES (1572876,
	1572868,
	0,
	1572871,
	1572873,
	1572885,
	1572886,
	1572906,
	1572876,
	0,
	0,
	'');
INSERT INTO O_REF
	VALUES (1572876,
	1572881,
	0,
	1572929,
	1572877,
	1572897,
	1572896,
	1572906,
	1572906,
	1572876,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (1572906,
	1572876,
	1572865,
	1572865,
	1);
INSERT INTO O_ATTR
	VALUES (1572906,
	1572876,
	1572905,
	'Rel_ID',
	'',
	'',
	'Rel_ID',
	0,
	524296);
INSERT INTO O_REF
	VALUES (1572876,
	1572881,
	0,
	1572930,
	1572877,
	1572897,
	1572896,
	1572907,
	1572907,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (1572907,
	1572876,
	1572922,
	1572879,
	1);
INSERT INTO O_ATTR
	VALUES (1572907,
	1572876,
	1572906,
	'OIR_ID',
	'',
	'',
	'OIR_ID',
	0,
	524296);
INSERT INTO O_ID
	VALUES (0,
	1572876);
INSERT INTO O_OIDA
	VALUES (1572907,
	1572876,
	0);
INSERT INTO O_OIDA
	VALUES (1572906,
	1572876,
	0);
INSERT INTO O_OIDA
	VALUES (1572905,
	1572876,
	0);
INSERT INTO O_OBJ
	VALUES (1572877,
	'Object As Composition One Side',
	216,
	'R_CONE',
	'In a compositional relationship, this is one of the two objects in the relationship.   This object is the one at the other end of the relationship from R_COTH.',
	1572867);
INSERT INTO O_REF
	VALUES (1572877,
	1572879,
	0,
	1572920,
	1572876,
	1572894,
	1572891,
	1572908,
	1572908,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (1572908,
	1572877,
	3145751,
	3145732,
	1);
INSERT INTO O_ATTR
	VALUES (1572908,
	1572877,
	0,
	'Obj_ID',
	'',
	'',
	'Obj_ID',
	0,
	524296);
INSERT INTO O_REF
	VALUES (1572877,
	1572869,
	0,
	1572872,
	1572874,
	1572887,
	1572888,
	1572909,
	1572877,
	0,
	0,
	'');
INSERT INTO O_REF
	VALUES (1572877,
	1572879,
	0,
	1572921,
	1572876,
	1572894,
	1572891,
	1572909,
	1572909,
	1572877,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (1572909,
	1572877,
	1572865,
	1572865,
	1);
INSERT INTO O_ATTR
	VALUES (1572909,
	1572877,
	1572908,
	'Rel_ID',
	'',
	'',
	'Rel_ID',
	0,
	524296);
INSERT INTO O_REF
	VALUES (1572877,
	1572879,
	0,
	1572922,
	1572876,
	1572894,
	1572891,
	1572910,
	1572910,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (1572910,
	1572877,
	1572922,
	1572879,
	1);
INSERT INTO O_ATTR
	VALUES (1572910,
	1572877,
	1572909,
	'OIR_ID',
	'',
	'',
	'OIR_ID',
	0,
	524296);
INSERT INTO O_DBATTR
	VALUES (1572911,
	1572877,
	'',
	0);
INSERT INTO O_BATTR
	VALUES (1572911,
	1572877);
INSERT INTO O_ATTR
	VALUES (1572911,
	1572877,
	1572910,
	'Mult',
	'Full Name: Multiplicity
Description: Multiplicity at one side of the compositional relationship.  This attribute is derived from the multiplicities of the objects on the far end of each relationship in the chain when navigating the loop as specified in R_COMP.Rel_Chn from this object (R_CONE)  to the other participating in the relationship (R_COTH).  It is one when all multiplicities at the far ends in the loop are one, else it is many.
Data Domain: 0 = one, 1 = many',
	'',
	'Mult',
	0,
	524291);
INSERT INTO O_DBATTR
	VALUES (1572912,
	1572877,
	'',
	0);
INSERT INTO O_BATTR
	VALUES (1572912,
	1572877);
INSERT INTO O_ATTR
	VALUES (1572912,
	1572877,
	1572911,
	'Cond',
	'Full Name: Conditionality
Description: Conditionality at one side of the compositional relationship.  This attribute is derived from the conditionalities of the objects on the far end of each relationship in the chain when navigating the loop as specified in R_COMP.Rel_Chn from this object (R_CONE)  to the other participating in the relationship (R_COTH).  It is one when all conditionalities at the far ends in the loop are one, else it is many.
Data Domain: 0 = one, 1 = many',
	'',
	'Cond',
	0,
	524291);
INSERT INTO O_NBATTR
	VALUES (1572913,
	1572877);
INSERT INTO O_BATTR
	VALUES (1572913,
	1572877);
INSERT INTO O_ATTR
	VALUES (1572913,
	1572877,
	1572912,
	'Txt_Phrs',
	'Full Name: Text Phrase
',
	'',
	'Txt_Phrs',
	0,
	524293);
INSERT INTO O_ID
	VALUES (0,
	1572877);
INSERT INTO O_OIDA
	VALUES (1572909,
	1572877,
	0);
INSERT INTO O_OIDA
	VALUES (1572908,
	1572877,
	0);
INSERT INTO O_OIDA
	VALUES (1572910,
	1572877,
	0);
INSERT INTO O_OBJ
	VALUES (1572878,
	'Object As Composition Other Side',
	217,
	'R_COTH',
	'In a compositional relationship, this is one of the two objects in the relationship.   This object is the one at the other end of the relationship from R_CONE.',
	1572867);
INSERT INTO O_REF
	VALUES (1572878,
	1572879,
	0,
	1572920,
	1572876,
	1572895,
	1572891,
	1572914,
	1572911,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (1572914,
	1572878,
	3145751,
	3145732,
	1);
INSERT INTO O_ATTR
	VALUES (1572914,
	1572878,
	0,
	'Obj_ID',
	'',
	'',
	'Obj_ID',
	0,
	524296);
INSERT INTO O_REF
	VALUES (1572878,
	1572869,
	0,
	1572872,
	1572875,
	1572889,
	1572890,
	1572915,
	1572878,
	0,
	0,
	'');
INSERT INTO O_REF
	VALUES (1572878,
	1572879,
	0,
	1572921,
	1572876,
	1572895,
	1572891,
	1572915,
	1572912,
	1572878,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (1572915,
	1572878,
	1572865,
	1572865,
	1);
INSERT INTO O_ATTR
	VALUES (1572915,
	1572878,
	1572914,
	'Rel_ID',
	'',
	'',
	'Rel_ID',
	0,
	524296);
INSERT INTO O_REF
	VALUES (1572878,
	1572879,
	0,
	1572922,
	1572876,
	1572895,
	1572891,
	1572916,
	1572913,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (1572916,
	1572878,
	1572922,
	1572879,
	1);
INSERT INTO O_ATTR
	VALUES (1572916,
	1572878,
	1572915,
	'OIR_ID',
	'',
	'',
	'OIR_ID',
	0,
	524296);
INSERT INTO O_DBATTR
	VALUES (1572917,
	1572878,
	'',
	0);
INSERT INTO O_BATTR
	VALUES (1572917,
	1572878);
INSERT INTO O_ATTR
	VALUES (1572917,
	1572878,
	1572916,
	'Mult',
	'Full Name: Multiplicity
Description: Multiplicity at the other side of the compositional relationship.  This attribute is derived from the multiplicities of the objects on the far end of each relationship in the chain when navigating the loop as specified in R_COMP.Rel_Chn from this object (R_COTH)  to the other participating in the relationship (R_CONE).  It is one when all multiplicities at the far ends in the loop are one, else it is many.
Data Domain: 0 = one, 1 = many',
	'',
	'Mult',
	0,
	524291);
INSERT INTO O_DBATTR
	VALUES (1572918,
	1572878,
	'',
	0);
INSERT INTO O_BATTR
	VALUES (1572918,
	1572878);
INSERT INTO O_ATTR
	VALUES (1572918,
	1572878,
	1572917,
	'Cond',
	'Full Name: Conditionality
Description: Conditionality at the other side of the compositional relationship.  This attribute is derived from the conditionalities of the objects on the far end of each relationship in the chain when navigating the loop as specified in R_COMP.Rel_Chn from this object (R_COTH)  to the other participating in the relationship (R_CONE).  It is one when all conditionalities at the far ends in the loop are one, else it is many.
Data Domain: 0 = one, 1 = many',
	'',
	'Cond',
	0,
	524291);
INSERT INTO O_NBATTR
	VALUES (1572919,
	1572878);
INSERT INTO O_BATTR
	VALUES (1572919,
	1572878);
INSERT INTO O_ATTR
	VALUES (1572919,
	1572878,
	1572918,
	'Txt_Phrs',
	'Full Name: Text Phrase
',
	'',
	'Txt_Phrs',
	0,
	524293);
INSERT INTO O_ID
	VALUES (0,
	1572878);
INSERT INTO O_OIDA
	VALUES (1572915,
	1572878,
	0);
INSERT INTO O_OIDA
	VALUES (1572916,
	1572878,
	0);
INSERT INTO O_OIDA
	VALUES (1572914,
	1572878,
	0);
INSERT INTO O_OBJ
	VALUES (1572879,
	'Object In Relationship',
	202,
	'R_OIR',
	'An Object In Relationship captures the role which an object plays in participating in a relationship.',
	1572867);
INSERT INTO O_REF
	VALUES (1572879,
	3145732,
	0,
	3145751,
	1572865,
	1572866,
	1572867,
	1572920,
	1572879,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (1572920,
	1572879,
	3145751,
	3145732,
	1);
INSERT INTO O_ATTR
	VALUES (1572920,
	1572879,
	0,
	'Obj_ID',
	'',
	'',
	'Obj_ID',
	0,
	524296);
INSERT INTO O_REF
	VALUES (1572879,
	1572865,
	0,
	1572865,
	1572865,
	1572866,
	1572865,
	1572921,
	1572914,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (1572921,
	1572879,
	1572865,
	1572865,
	1);
INSERT INTO O_ATTR
	VALUES (1572921,
	1572879,
	1572920,
	'Rel_ID',
	'',
	'',
	'Rel_ID',
	0,
	524296);
INSERT INTO O_NBATTR
	VALUES (1572922,
	1572879);
INSERT INTO O_BATTR
	VALUES (1572922,
	1572879);
INSERT INTO O_ATTR
	VALUES (1572922,
	1572879,
	1572921,
	'OIR_ID',
	'Full Name: Object In Relationship Identifier',
	'',
	'OIR_ID',
	0,
	524294);
INSERT INTO O_REF
	VALUES (1572879,
	3145740,
	0,
	3145787,
	1572879,
	1572905,
	1572906,
	1572923,
	1572880,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (1572923,
	1572879,
	3145787,
	3145740,
	1);
INSERT INTO O_ATTR
	VALUES (1572923,
	1572879,
	1572922,
	'IObj_ID',
	'',
	'',
	'IObj_ID',
	0,
	524296);
INSERT INTO O_ID
	VALUES (0,
	1572879);
INSERT INTO O_OIDA
	VALUES (1572920,
	1572879,
	0);
INSERT INTO O_RTIDA
	VALUES (1572920,
	1572879,
	0,
	1572876,
	1572891);
INSERT INTO O_OIDA
	VALUES (1572921,
	1572879,
	0);
INSERT INTO O_RTIDA
	VALUES (1572921,
	1572879,
	0,
	1572876,
	1572891);
INSERT INTO O_OIDA
	VALUES (1572922,
	1572879,
	0);
INSERT INTO O_RTIDA
	VALUES (1572922,
	1572879,
	0,
	1572876,
	1572891);
INSERT INTO O_OBJ
	VALUES (1572880,
	'Referred To Object in Rel',
	203,
	'R_RTO',
	'A Referred To Object In Relationship is an object which contains identifying attributes which are referred to across the relationship.',
	1572867);
INSERT INTO O_REF
	VALUES (1572880,
	1572879,
	0,
	1572920,
	1572876,
	1572892,
	1572891,
	1572924,
	1572881,
	0,
	0,
	'');
INSERT INTO O_REF
	VALUES (1572880,
	3145738,
	0,
	3145783,
	3145745,
	3145765,
	3145766,
	1572924,
	1572915,
	1572881,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (1572924,
	1572880,
	3145751,
	3145732,
	1);
INSERT INTO O_ATTR
	VALUES (1572924,
	1572880,
	0,
	'Obj_ID',
	'',
	'',
	'Obj_ID',
	0,
	524296);
INSERT INTO O_REF
	VALUES (1572880,
	1572879,
	0,
	1572921,
	1572876,
	1572892,
	1572891,
	1572925,
	1572882,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (1572925,
	1572880,
	1572865,
	1572865,
	1);
INSERT INTO O_ATTR
	VALUES (1572925,
	1572880,
	1572924,
	'Rel_ID',
	'',
	'',
	'Rel_ID',
	0,
	524296);
INSERT INTO O_REF
	VALUES (1572880,
	1572879,
	0,
	1572922,
	1572876,
	1572892,
	1572891,
	1572926,
	1572883,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (1572926,
	1572880,
	1572922,
	1572879,
	1);
INSERT INTO O_ATTR
	VALUES (1572926,
	1572880,
	1572925,
	'OIR_ID',
	'',
	'',
	'OIR_ID',
	0,
	524296);
INSERT INTO O_REF
	VALUES (1572880,
	3145738,
	0,
	3145782,
	3145745,
	3145765,
	3145766,
	1572927,
	1572916,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (1572927,
	1572880,
	3145782,
	3145738,
	1);
INSERT INTO O_ATTR
	VALUES (1572927,
	1572880,
	1572926,
	'Oid_ID',
	'',
	'',
	'Oid_ID',
	0,
	524296);
INSERT INTO O_ID
	VALUES (0,
	1572880);
INSERT INTO O_OIDA
	VALUES (1572924,
	1572880,
	0);
INSERT INTO O_RTIDA
	VALUES (1572924,
	1572880,
	0,
	1572878,
	1572900);
INSERT INTO O_OIDA
	VALUES (1572925,
	1572880,
	0);
INSERT INTO O_RTIDA
	VALUES (1572925,
	1572880,
	0,
	1572878,
	1572900);
INSERT INTO O_OIDA
	VALUES (1572926,
	1572880,
	0);
INSERT INTO O_RTIDA
	VALUES (1572926,
	1572880,
	0,
	1572878,
	1572900);
INSERT INTO O_ID
	VALUES (1,
	1572880);
INSERT INTO O_OIDA
	VALUES (1572927,
	1572880,
	1);
INSERT INTO O_RTIDA
	VALUES (1572927,
	1572880,
	1,
	3145744,
	3145762);
INSERT INTO O_OIDA
	VALUES (1572924,
	1572880,
	1);
INSERT INTO O_RTIDA
	VALUES (1572924,
	1572880,
	1,
	3145744,
	3145762);
INSERT INTO O_OIDA
	VALUES (1572926,
	1572880,
	1);
INSERT INTO O_RTIDA
	VALUES (1572926,
	1572880,
	1,
	3145744,
	3145762);
INSERT INTO O_OIDA
	VALUES (1572925,
	1572880,
	1);
INSERT INTO O_RTIDA
	VALUES (1572925,
	1572880,
	1,
	3145744,
	3145762);
INSERT INTO O_OBJ
	VALUES (1572881,
	'Referring Object In Rel',
	204,
	'R_RGO',
	'A referring Object In Relationship is an object which contains referential attributes which refer to identifying attributes across the relationship.',
	1572867);
INSERT INTO O_REF
	VALUES (1572881,
	1572879,
	0,
	1572920,
	1572876,
	1572893,
	1572891,
	1572928,
	1572884,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (1572928,
	1572881,
	3145751,
	3145732,
	1);
INSERT INTO O_ATTR
	VALUES (1572928,
	1572881,
	0,
	'Obj_ID',
	'',
	'',
	'Obj_ID',
	0,
	524296);
INSERT INTO O_REF
	VALUES (1572881,
	1572879,
	0,
	1572921,
	1572876,
	1572893,
	1572891,
	1572929,
	1572885,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (1572929,
	1572881,
	1572865,
	1572865,
	1);
INSERT INTO O_ATTR
	VALUES (1572929,
	1572881,
	1572928,
	'Rel_ID',
	'',
	'',
	'Rel_ID',
	0,
	524296);
INSERT INTO O_REF
	VALUES (1572881,
	1572879,
	0,
	1572922,
	1572876,
	1572893,
	1572891,
	1572930,
	1572886,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (1572930,
	1572881,
	1572922,
	1572879,
	1);
INSERT INTO O_ATTR
	VALUES (1572930,
	1572881,
	1572929,
	'OIR_ID',
	'',
	'',
	'OIR_ID',
	0,
	524296);
INSERT INTO O_ID
	VALUES (0,
	1572881);
INSERT INTO O_OIDA
	VALUES (1572928,
	1572881,
	0);
INSERT INTO O_RTIDA
	VALUES (1572928,
	1572881,
	0,
	1572877,
	1572896);
INSERT INTO O_RTIDA
	VALUES (1572928,
	1572881,
	0,
	3145746,
	3145767);
INSERT INTO O_OIDA
	VALUES (1572930,
	1572881,
	0);
INSERT INTO O_RTIDA
	VALUES (1572930,
	1572881,
	0,
	3145746,
	3145767);
INSERT INTO O_RTIDA
	VALUES (1572930,
	1572881,
	0,
	1572877,
	1572896);
INSERT INTO O_OIDA
	VALUES (1572929,
	1572881,
	0);
INSERT INTO O_RTIDA
	VALUES (1572929,
	1572881,
	0,
	3145746,
	3145767);
INSERT INTO O_RTIDA
	VALUES (1572929,
	1572881,
	0,
	1572877,
	1572896);
INSERT INTO O_IOBJ
	VALUES (1572865,
	3145732,
	5,
	1572867,
	'Object',
	'O_OBJ');
INSERT INTO O_IOBJ
	VALUES (1572866,
	3145740,
	5,
	1572867,
	'Imported Object',
	'O_IOBJ');
INSERT INTO R_ASSOC
	VALUES (1572865);
INSERT INTO R_REL
	VALUES (1572865,
	201,
	'',
	1572867);
INSERT INTO R_AONE
	VALUES (1572865,
	1572865,
	1572865,
	1,
	1,
	'has instance associations abstracted');
INSERT INTO R_RTO
	VALUES (1572865,
	1572865,
	1572865,
	0);
INSERT INTO R_OIR
	VALUES (1572865,
	1572865,
	1572865,
	0);
INSERT INTO R_ASSR
	VALUES (1572879,
	1572865,
	1572866,
	1);
INSERT INTO R_RGO
	VALUES (1572879,
	1572865,
	1572866);
INSERT INTO R_OIR
	VALUES (1572879,
	1572865,
	1572866,
	0);
INSERT INTO R_AOTH
	VALUES (3145732,
	1572865,
	1572867,
	1,
	0,
	'abstracts association between instances of');
INSERT INTO R_RTO
	VALUES (3145732,
	1572865,
	1572867,
	0);
INSERT INTO R_OIR
	VALUES (3145732,
	1572865,
	1572867,
	1572865);
INSERT INTO R_SUBSUP
	VALUES (1572866);
INSERT INTO R_REL
	VALUES (1572866,
	206,
	'',
	1572867);
INSERT INTO R_SUPER
	VALUES (1572865,
	1572866,
	1572868);
INSERT INTO R_RTO
	VALUES (1572865,
	1572866,
	1572868,
	0);
INSERT INTO R_OIR
	VALUES (1572865,
	1572866,
	1572868,
	0);
INSERT INTO R_SUB
	VALUES (1572866,
	1572866,
	1572869);
INSERT INTO R_RGO
	VALUES (1572866,
	1572866,
	1572869);
INSERT INTO R_OIR
	VALUES (1572866,
	1572866,
	1572869,
	0);
INSERT INTO R_SUB
	VALUES (1572869,
	1572866,
	1572870);
INSERT INTO R_RGO
	VALUES (1572869,
	1572866,
	1572870);
INSERT INTO R_OIR
	VALUES (1572869,
	1572866,
	1572870,
	0);
INSERT INTO R_SUB
	VALUES (1572867,
	1572866,
	1572871);
INSERT INTO R_RGO
	VALUES (1572867,
	1572866,
	1572871);
INSERT INTO R_OIR
	VALUES (1572867,
	1572866,
	1572871,
	0);
INSERT INTO R_SUB
	VALUES (1572868,
	1572866,
	1572872);
INSERT INTO R_RGO
	VALUES (1572868,
	1572866,
	1572872);
INSERT INTO R_OIR
	VALUES (1572868,
	1572866,
	1572872,
	0);
INSERT INTO R_SIMP
	VALUES (1572867);
INSERT INTO R_REL
	VALUES (1572867,
	207,
	'',
	1572867);
INSERT INTO R_FORM
	VALUES (1572870,
	1572867,
	1572873,
	0,
	0,
	'relates');
INSERT INTO R_RGO
	VALUES (1572870,
	1572867,
	1572873);
INSERT INTO R_OIR
	VALUES (1572870,
	1572867,
	1572873,
	0);
INSERT INTO R_PART
	VALUES (1572866,
	1572867,
	1572874,
	0,
	0,
	'is related to formalizer via');
INSERT INTO R_RTO
	VALUES (1572866,
	1572867,
	1572874,
	0);
INSERT INTO R_OIR
	VALUES (1572866,
	1572867,
	1572874,
	0);
INSERT INTO R_SIMP
	VALUES (1572868);
INSERT INTO R_REL
	VALUES (1572868,
	208,
	'The conditionality towards R_FORM accounts for unformalized simple relationships in the analysis models. In BridgePoint V3.x, and unformalized simple relationship results in two instances of the Object As Simple Participant (R_PART), and zero instances of Object As Simple Formalizer. Archetypes should check the cardinality of R_FORM (verifying it is one) before navigating to R_PART.',
	1572867);
INSERT INTO R_FORM
	VALUES (1572871,
	1572868,
	1572875,
	0,
	1,
	'relates');
INSERT INTO R_RGO
	VALUES (1572871,
	1572868,
	1572875);
INSERT INTO R_OIR
	VALUES (1572871,
	1572868,
	1572875,
	0);
INSERT INTO R_PART
	VALUES (1572866,
	1572868,
	1572876,
	0,
	0,
	'is related to participant via');
INSERT INTO R_RTO
	VALUES (1572866,
	1572868,
	1572876,
	0);
INSERT INTO R_OIR
	VALUES (1572866,
	1572868,
	1572876,
	0);
INSERT INTO R_SIMP
	VALUES (1572869);
INSERT INTO R_REL
	VALUES (1572869,
	209,
	'',
	1572867);
INSERT INTO R_FORM
	VALUES (1572872,
	1572869,
	1572877,
	0,
	0,
	'relates');
INSERT INTO R_RGO
	VALUES (1572872,
	1572869,
	1572877);
INSERT INTO R_OIR
	VALUES (1572872,
	1572869,
	1572877,
	0);
INSERT INTO R_PART
	VALUES (1572867,
	1572869,
	1572878,
	0,
	0,
	'is related to other side via');
INSERT INTO R_RTO
	VALUES (1572867,
	1572869,
	1572878,
	0);
INSERT INTO R_OIR
	VALUES (1572867,
	1572869,
	1572878,
	0);
INSERT INTO R_SIMP
	VALUES (1572870);
INSERT INTO R_REL
	VALUES (1572870,
	210,
	'',
	1572867);
INSERT INTO R_FORM
	VALUES (1572873,
	1572870,
	1572879,
	0,
	0,
	'relates');
INSERT INTO R_RGO
	VALUES (1572873,
	1572870,
	1572879);
INSERT INTO R_OIR
	VALUES (1572873,
	1572870,
	1572879,
	0);
INSERT INTO R_PART
	VALUES (1572867,
	1572870,
	1572880,
	0,
	0,
	'is related to one side via');
INSERT INTO R_RTO
	VALUES (1572867,
	1572870,
	1572880,
	0);
INSERT INTO R_OIR
	VALUES (1572867,
	1572870,
	1572880,
	0);
INSERT INTO R_SIMP
	VALUES (1572871);
INSERT INTO R_REL
	VALUES (1572871,
	211,
	'',
	1572867);
INSERT INTO R_FORM
	VALUES (1572874,
	1572871,
	1572881,
	0,
	0,
	'uses a formalizer');
INSERT INTO R_RGO
	VALUES (1572874,
	1572871,
	1572881);
INSERT INTO R_OIR
	VALUES (1572874,
	1572871,
	1572881,
	0);
INSERT INTO R_PART
	VALUES (1572867,
	1572871,
	1572882,
	0,
	0,
	'formalizes relationship between associated objects');
INSERT INTO R_RTO
	VALUES (1572867,
	1572871,
	1572882,
	0);
INSERT INTO R_OIR
	VALUES (1572867,
	1572871,
	1572882,
	0);
INSERT INTO R_SIMP
	VALUES (1572872);
INSERT INTO R_REL
	VALUES (1572872,
	212,
	'',
	1572867);
INSERT INTO R_FORM
	VALUES (1572875,
	1572872,
	1572883,
	0,
	0,
	'relates');
INSERT INTO R_RGO
	VALUES (1572875,
	1572872,
	1572883);
INSERT INTO R_OIR
	VALUES (1572875,
	1572872,
	1572883,
	0);
INSERT INTO R_PART
	VALUES (1572868,
	1572872,
	1572884,
	0,
	0,
	'is related to subtypes via');
INSERT INTO R_RTO
	VALUES (1572868,
	1572872,
	1572884,
	0);
INSERT INTO R_OIR
	VALUES (1572868,
	1572872,
	1572884,
	0);
INSERT INTO R_SIMP
	VALUES (1572873);
INSERT INTO R_REL
	VALUES (1572873,
	213,
	'The conditionality towards Object As Subtype (R_SUB) represents that an analysis model may contain a supertype object that does not yet have any subtype objects entered on the OIM.',
	1572867);
INSERT INTO R_FORM
	VALUES (1572876,
	1572873,
	1572885,
	1,
	1,
	'relates');
INSERT INTO R_RGO
	VALUES (1572876,
	1572873,
	1572885);
INSERT INTO R_OIR
	VALUES (1572876,
	1572873,
	1572885,
	0);
INSERT INTO R_PART
	VALUES (1572868,
	1572873,
	1572886,
	0,
	0,
	'is related to supertype via');
INSERT INTO R_RTO
	VALUES (1572868,
	1572873,
	1572886,
	0);
INSERT INTO R_OIR
	VALUES (1572868,
	1572873,
	1572886,
	0);
INSERT INTO R_SIMP
	VALUES (1572874);
INSERT INTO R_REL
	VALUES (1572874,
	214,
	'',
	1572867);
INSERT INTO R_FORM
	VALUES (1572877,
	1572874,
	1572887,
	0,
	0,
	'relates');
INSERT INTO R_RGO
	VALUES (1572877,
	1572874,
	1572887);
INSERT INTO R_OIR
	VALUES (1572877,
	1572874,
	1572887,
	0);
INSERT INTO R_PART
	VALUES (1572869,
	1572874,
	1572888,
	0,
	0,
	'is related to other type via');
INSERT INTO R_RTO
	VALUES (1572869,
	1572874,
	1572888,
	0);
INSERT INTO R_OIR
	VALUES (1572869,
	1572874,
	1572888,
	0);
INSERT INTO R_SIMP
	VALUES (1572875);
INSERT INTO R_REL
	VALUES (1572875,
	215,
	'',
	1572867);
INSERT INTO R_FORM
	VALUES (1572878,
	1572875,
	1572889,
	0,
	0,
	'relates');
INSERT INTO R_RGO
	VALUES (1572878,
	1572875,
	1572889);
INSERT INTO R_OIR
	VALUES (1572878,
	1572875,
	1572889,
	0);
INSERT INTO R_PART
	VALUES (1572869,
	1572875,
	1572890,
	0,
	0,
	'is related to one side via');
INSERT INTO R_RTO
	VALUES (1572869,
	1572875,
	1572890,
	0);
INSERT INTO R_OIR
	VALUES (1572869,
	1572875,
	1572890,
	0);
INSERT INTO R_SUBSUP
	VALUES (1572876);
INSERT INTO R_REL
	VALUES (1572876,
	203,
	'',
	1572867);
INSERT INTO R_SUPER
	VALUES (1572879,
	1572876,
	1572891);
INSERT INTO R_RTO
	VALUES (1572879,
	1572876,
	1572891,
	0);
INSERT INTO R_OIR
	VALUES (1572879,
	1572876,
	1572891,
	0);
INSERT INTO R_SUB
	VALUES (1572880,
	1572876,
	1572892);
INSERT INTO R_RGO
	VALUES (1572880,
	1572876,
	1572892);
INSERT INTO R_OIR
	VALUES (1572880,
	1572876,
	1572892,
	0);
INSERT INTO R_SUB
	VALUES (1572881,
	1572876,
	1572893);
INSERT INTO R_RGO
	VALUES (1572881,
	1572876,
	1572893);
INSERT INTO R_OIR
	VALUES (1572881,
	1572876,
	1572893,
	0);
INSERT INTO R_SUB
	VALUES (1572877,
	1572876,
	1572894);
INSERT INTO R_RGO
	VALUES (1572877,
	1572876,
	1572894);
INSERT INTO R_OIR
	VALUES (1572877,
	1572876,
	1572894,
	0);
INSERT INTO R_SUB
	VALUES (1572878,
	1572876,
	1572895);
INSERT INTO R_RGO
	VALUES (1572878,
	1572876,
	1572895);
INSERT INTO R_OIR
	VALUES (1572878,
	1572876,
	1572895,
	0);
INSERT INTO R_SUBSUP
	VALUES (1572877);
INSERT INTO R_REL
	VALUES (1572877,
	205,
	'',
	1572867);
INSERT INTO R_SUPER
	VALUES (1572881,
	1572877,
	1572896);
INSERT INTO R_RTO
	VALUES (1572881,
	1572877,
	1572896,
	0);
INSERT INTO R_OIR
	VALUES (1572881,
	1572877,
	1572896,
	0);
INSERT INTO R_SUB
	VALUES (1572876,
	1572877,
	1572897);
INSERT INTO R_RGO
	VALUES (1572876,
	1572877,
	1572897);
INSERT INTO R_OIR
	VALUES (1572876,
	1572877,
	1572897,
	0);
INSERT INTO R_SUB
	VALUES (1572871,
	1572877,
	1572898);
INSERT INTO R_RGO
	VALUES (1572871,
	1572877,
	1572898);
INSERT INTO R_OIR
	VALUES (1572871,
	1572877,
	1572898,
	0);
INSERT INTO R_SUB
	VALUES (1572874,
	1572877,
	1572899);
INSERT INTO R_RGO
	VALUES (1572874,
	1572877,
	1572899);
INSERT INTO R_OIR
	VALUES (1572874,
	1572877,
	1572899,
	0);
INSERT INTO R_SUBSUP
	VALUES (1572878);
INSERT INTO R_REL
	VALUES (1572878,
	204,
	'',
	1572867);
INSERT INTO R_SUPER
	VALUES (1572880,
	1572878,
	1572900);
INSERT INTO R_RTO
	VALUES (1572880,
	1572878,
	1572900,
	0);
INSERT INTO R_OIR
	VALUES (1572880,
	1572878,
	1572900,
	0);
INSERT INTO R_SUB
	VALUES (1572875,
	1572878,
	1572901);
INSERT INTO R_RGO
	VALUES (1572875,
	1572878,
	1572901);
INSERT INTO R_OIR
	VALUES (1572875,
	1572878,
	1572901,
	0);
INSERT INTO R_SUB
	VALUES (1572870,
	1572878,
	1572902);
INSERT INTO R_RGO
	VALUES (1572870,
	1572878,
	1572902);
INSERT INTO R_OIR
	VALUES (1572870,
	1572878,
	1572902,
	0);
INSERT INTO R_SUB
	VALUES (1572872,
	1572878,
	1572903);
INSERT INTO R_RGO
	VALUES (1572872,
	1572878,
	1572903);
INSERT INTO R_OIR
	VALUES (1572872,
	1572878,
	1572903,
	0);
INSERT INTO R_SUB
	VALUES (1572873,
	1572878,
	1572904);
INSERT INTO R_RGO
	VALUES (1572873,
	1572878,
	1572904);
INSERT INTO R_OIR
	VALUES (1572873,
	1572878,
	1572904,
	0);
INSERT INTO R_SIMP
	VALUES (1572879);
INSERT INTO R_REL
	VALUES (1572879,
	202,
	'',
	1572867);
INSERT INTO R_FORM
	VALUES (1572879,
	1572879,
	1572905,
	1,
	1,
	'is used for spanning relationships as');
INSERT INTO R_RGO
	VALUES (1572879,
	1572879,
	1572905);
INSERT INTO R_OIR
	VALUES (1572879,
	1572879,
	1572905,
	0);
INSERT INTO R_PART
	VALUES (3145740,
	1572879,
	1572906,
	0,
	1,
	'may be represented by');
INSERT INTO R_RTO
	VALUES (3145740,
	1572879,
	1572906,
	0);
INSERT INTO R_OIR
	VALUES (3145740,
	1572879,
	1572906,
	1572866);
INSERT INTO S_SS
	VALUES (2097156,
	'Communication And Access',
	'Interactions between objects (O_OBJ) are modeled by communication (CA_COMM) and access (CA_ACC) paths. Communication paths show the asynchronous (event) and synchronous (data access) communication in the system between two objects and object/external entity (S_EE) pairs.  ',
	'CA',
	401,
	2531670,
	2097156);
INSERT INTO O_OBJ
	VALUES (2097153,
	'Communication Path',
	401,
	'CA_COMM',
	'A communication path (CA_COMM) represents modeled event communication path (on the Object Communication Model) between two of the following elements: State Models (SM_SM) and External Entities (S_EEM).  The communication path is created by the tool in fully-derived mode, or by the user in partially-derived mode.',
	2097156);
INSERT INTO O_NBATTR
	VALUES (2097153,
	2097153);
INSERT INTO O_BATTR
	VALUES (2097153,
	2097153);
INSERT INTO O_ATTR
	VALUES (2097153,
	2097153,
	0,
	'CPath_ID',
	'Full Name: Communication Path Identifier',
	'',
	'CPath_ID',
	0,
	524294);
INSERT INTO O_REF
	VALUES (2097153,
	1048578,
	0,
	1048582,
	1048596,
	1048619,
	1048618,
	2097154,
	2097153,
	0,
	0,
	'

Notes:

');
INSERT INTO O_RATTR
	VALUES (2097154,
	2097153,
	1048582,
	1048578,
	1);
INSERT INTO O_ATTR
	VALUES (2097154,
	2097153,
	2097153,
	'SS_ID',
	'',
	'',
	'SS_ID',
	0,
	524296);
INSERT INTO O_ID
	VALUES (0,
	2097153);
INSERT INTO O_OIDA
	VALUES (2097153,
	2097153,
	0);
INSERT INTO O_RTIDA
	VALUES (2097153,
	2097153,
	0,
	2097153,
	2097153);
INSERT INTO O_OBJ
	VALUES (2097154,
	'EE to SM Comm Path',
	402,
	'CA_EESMC',
	'An EE to SM communication path represents a path on the Object Communication Model that originates at an External Entity and terminates at a State Model.  ',
	2097156);
INSERT INTO O_REF
	VALUES (2097154,
	2097153,
	0,
	2097153,
	2097153,
	2097154,
	2097153,
	2097155,
	2097154,
	0,
	0,
	'

Notes:

');
INSERT INTO O_RATTR
	VALUES (2097155,
	2097154,
	2097153,
	2097153,
	1);
INSERT INTO O_ATTR
	VALUES (2097155,
	2097154,
	0,
	'CPath_ID',
	'',
	'',
	'CPath_ID',
	0,
	524296);
INSERT INTO O_REF
	VALUES (2097154,
	1048580,
	0,
	1048594,
	2097172,
	2097195,
	2097194,
	2097156,
	2097176,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (2097156,
	2097154,
	1048594,
	1048580,
	1);
INSERT INTO O_ATTR
	VALUES (2097156,
	2097154,
	2097155,
	'EEmod_ID',
	'',
	'',
	'EEmod_ID',
	0,
	524296);
INSERT INTO O_REF
	VALUES (2097154,
	1048580,
	0,
	1048595,
	2097172,
	2097195,
	2097194,
	2097157,
	2097177,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (2097157,
	2097154,
	1048589,
	1048579,
	1);
INSERT INTO O_ATTR
	VALUES (2097157,
	2097154,
	2097156,
	'EE_ID',
	'',
	'',
	'EE_ID',
	0,
	524296);
INSERT INTO O_REF
	VALUES (2097154,
	2621441,
	0,
	2621441,
	2097171,
	2097192,
	2097193,
	2097158,
	2097155,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (2097158,
	2097154,
	2621441,
	2621441,
	1);
INSERT INTO O_ATTR
	VALUES (2097158,
	2097154,
	2097157,
	'SM_ID',
	'',
	'',
	'SM_ID',
	0,
	524296);
INSERT INTO O_ID
	VALUES (0,
	2097154);
INSERT INTO O_OIDA
	VALUES (2097155,
	2097154,
	0);
INSERT INTO O_RTIDA
	VALUES (2097155,
	2097154,
	0,
	2097154,
	2097157);
INSERT INTO O_OBJ
	VALUES (2097155,
	'SM to SM Comm Path',
	403,
	'CA_SMSMC',
	'An SM to SM communication path represents a path on the Object Communication Model that originates at State Model and terminates at a State Model.  ',
	2097156);
INSERT INTO O_REF
	VALUES (2097155,
	2097153,
	0,
	2097153,
	2097153,
	2097156,
	2097153,
	2097159,
	2097156,
	0,
	0,
	'

Notes:

');
INSERT INTO O_RATTR
	VALUES (2097159,
	2097155,
	2097153,
	2097153,
	1);
INSERT INTO O_ATTR
	VALUES (2097159,
	2097155,
	0,
	'CPath_ID',
	'',
	'',
	'CPath_ID',
	0,
	524296);
INSERT INTO O_REF
	VALUES (2097155,
	2621441,
	0,
	2621441,
	2097175,
	2097200,
	2097201,
	2097160,
	2097157,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (2097160,
	2097155,
	2621441,
	2621441,
	1);
INSERT INTO O_ATTR
	VALUES (2097160,
	2097155,
	2097159,
	'OSM_ID',
	'',
	'O',
	'SM_ID',
	1,
	524296);
INSERT INTO O_REF
	VALUES (2097155,
	2621441,
	0,
	2621441,
	2097176,
	2097202,
	2097203,
	2097161,
	2097158,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (2097161,
	2097155,
	2621441,
	2621441,
	1);
INSERT INTO O_ATTR
	VALUES (2097161,
	2097155,
	2097160,
	'DSM_ID',
	'',
	'D',
	'SM_ID',
	1,
	524296);
INSERT INTO O_REF
	VALUES (2097155,
	3145740,
	0,
	3145787,
	2097158,
	2097165,
	2097166,
	2097163,
	2097160,
	0,
	0,
	'

Notes:

');
INSERT INTO O_RATTR
	VALUES (2097163,
	2097155,
	3145787,
	3145740,
	1);
INSERT INTO O_ATTR
	VALUES (2097163,
	2097155,
	2097161,
	'DIObj_ID',
	'',
	'D',
	'IObj_ID',
	1,
	524296);
INSERT INTO O_REF
	VALUES (2097155,
	3145740,
	0,
	3145787,
	2097167,
	2097184,
	2097185,
	2097162,
	2097159,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (2097162,
	2097155,
	3145787,
	3145740,
	1);
INSERT INTO O_ATTR
	VALUES (2097162,
	2097155,
	2097163,
	'OIObj_ID',
	'',
	'O',
	'IObj_ID',
	1,
	524296);
INSERT INTO O_ID
	VALUES (0,
	2097155);
INSERT INTO O_OIDA
	VALUES (2097159,
	2097155,
	0);
INSERT INTO O_RTIDA
	VALUES (2097159,
	2097155,
	0,
	2097155,
	2097159);
INSERT INTO O_ID
	VALUES (1,
	2097155);
INSERT INTO O_OIDA
	VALUES (2097160,
	2097155,
	1);
INSERT INTO O_OIDA
	VALUES (2097161,
	2097155,
	1);
INSERT INTO O_OBJ
	VALUES (2097156,
	'SM to EE Comm Path',
	404,
	'CA_SMEEC',
	'An SM to EE communication path represents a path on the Object Communication Model that originates at a State Model  and terminates at an External Entity.  ',
	2097156);
INSERT INTO O_REF
	VALUES (2097156,
	2097153,
	0,
	2097153,
	2097153,
	2097155,
	2097153,
	2097164,
	2097161,
	0,
	0,
	'

Notes:

');
INSERT INTO O_RATTR
	VALUES (2097164,
	2097156,
	2097153,
	2097153,
	1);
INSERT INTO O_ATTR
	VALUES (2097164,
	2097156,
	0,
	'CPath_ID',
	'',
	'',
	'CPath_ID',
	0,
	524296);
INSERT INTO O_REF
	VALUES (2097156,
	2621441,
	0,
	2621441,
	2097173,
	2097196,
	2097197,
	2097165,
	2097162,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (2097165,
	2097156,
	2621441,
	2621441,
	1);
INSERT INTO O_ATTR
	VALUES (2097165,
	2097156,
	2097164,
	'SM_ID',
	'',
	'',
	'SM_ID',
	0,
	524296);
INSERT INTO O_REF
	VALUES (2097156,
	1048580,
	0,
	1048595,
	2097174,
	2097199,
	2097198,
	2097166,
	2097178,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (2097166,
	2097156,
	1048589,
	1048579,
	1);
INSERT INTO O_ATTR
	VALUES (2097166,
	2097156,
	2097165,
	'EE_ID',
	'',
	'',
	'EE_ID',
	0,
	524296);
INSERT INTO O_REF
	VALUES (2097156,
	1048580,
	0,
	1048594,
	2097174,
	2097199,
	2097198,
	2097167,
	2097179,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (2097167,
	2097156,
	1048594,
	1048580,
	1);
INSERT INTO O_ATTR
	VALUES (2097167,
	2097156,
	2097166,
	'EEmod_ID',
	'',
	'',
	'EEmod_ID',
	0,
	524296);
INSERT INTO O_ID
	VALUES (0,
	2097156);
INSERT INTO O_OIDA
	VALUES (2097164,
	2097156,
	0);
INSERT INTO O_RTIDA
	VALUES (2097164,
	2097156,
	0,
	2097156,
	2097161);
INSERT INTO O_ID
	VALUES (1,
	2097156);
INSERT INTO O_OIDA
	VALUES (2097166,
	2097156,
	1);
INSERT INTO O_OIDA
	VALUES (2097165,
	2097156,
	1);
INSERT INTO O_OBJ
	VALUES (2097157,
	'EE to SM Event Comm',
	405,
	'CA_EESME',
	'This object represents the event communication attributed to the EE to SM communication path (CA_EESMC).',
	2097156);
INSERT INTO O_REF
	VALUES (2097157,
	2097154,
	0,
	2097155,
	2097154,
	2097158,
	2097157,
	2097168,
	2097180,
	0,
	0,
	'

Notes:

');
INSERT INTO O_RATTR
	VALUES (2097168,
	2097157,
	2097153,
	2097153,
	1);
INSERT INTO O_ATTR
	VALUES (2097168,
	2097157,
	0,
	'CPath_ID',
	'',
	'',
	'CPath_ID',
	0,
	524296);
INSERT INTO O_REF
	VALUES (2097157,
	2621442,
	0,
	2621444,
	2097169,
	2097188,
	2097189,
	2097170,
	2097164,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (2097170,
	2097157,
	2621444,
	2621442,
	1);
INSERT INTO O_ATTR
	VALUES (2097170,
	2097157,
	2097168,
	'SMevt_ID',
	'',
	'',
	'SMevt_ID',
	0,
	524296);
INSERT INTO O_ID
	VALUES (0,
	2097157);
INSERT INTO O_OIDA
	VALUES (2097168,
	2097157,
	0);
INSERT INTO O_OBJ
	VALUES (2097158,
	'SM to SM Event Comm',
	406,
	'CA_SMSME',
	'This object represents the event communication attributed to the SM to SM communication path (CA_SMSMC).',
	2097156);
INSERT INTO O_REF
	VALUES (2097158,
	2097155,
	0,
	2097159,
	2097155,
	2097160,
	2097159,
	2097171,
	2097181,
	0,
	0,
	'

Notes:

');
INSERT INTO O_RATTR
	VALUES (2097171,
	2097158,
	2097153,
	2097153,
	1);
INSERT INTO O_ATTR
	VALUES (2097171,
	2097158,
	0,
	'CPath_ID',
	'',
	'',
	'CPath_ID',
	0,
	524296);
INSERT INTO O_REF
	VALUES (2097158,
	2621442,
	0,
	2621444,
	2097170,
	2097190,
	2097191,
	2097173,
	2097166,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (2097173,
	2097158,
	2621444,
	2621442,
	1);
INSERT INTO O_ATTR
	VALUES (2097173,
	2097158,
	2097171,
	'SMevt_ID',
	'',
	'',
	'SMevt_ID',
	0,
	524296);
INSERT INTO O_ID
	VALUES (0,
	2097158);
INSERT INTO O_OIDA
	VALUES (2097171,
	2097158,
	0);
INSERT INTO O_OBJ
	VALUES (2097159,
	'SM to EE Event Comm',
	407,
	'CA_SMEEE',
	'This object represents the event communication attributed to the SM to EE communication path (CA_SMEEC).',
	2097156);
INSERT INTO O_REF
	VALUES (2097159,
	2097156,
	0,
	2097164,
	2097156,
	2097162,
	2097161,
	2097174,
	2097182,
	0,
	0,
	'

Notes:

');
INSERT INTO O_RATTR
	VALUES (2097174,
	2097159,
	2097153,
	2097153,
	1);
INSERT INTO O_ATTR
	VALUES (2097174,
	2097159,
	0,
	'CPath_ID',
	'',
	'',
	'CPath_ID',
	0,
	524296);
INSERT INTO O_REF
	VALUES (2097159,
	1048582,
	0,
	1048604,
	2097157,
	2097164,
	2097163,
	2097175,
	2097183,
	0,
	0,
	'

Notes:

');
INSERT INTO O_RATTR
	VALUES (2097175,
	2097159,
	1048589,
	1048579,
	1);
INSERT INTO O_ATTR
	VALUES (2097175,
	2097159,
	2097174,
	'EE_ID',
	'',
	'',
	'EE_ID',
	0,
	524296);
INSERT INTO O_REF
	VALUES (2097159,
	1048582,
	0,
	1048603,
	2097157,
	2097164,
	2097163,
	2097176,
	2097184,
	0,
	0,
	'

Notes:

');
INSERT INTO O_RATTR
	VALUES (2097176,
	2097159,
	1048603,
	1048582,
	1);
INSERT INTO O_ATTR
	VALUES (2097176,
	2097159,
	2097175,
	'EEevt_ID',
	'',
	'',
	'EEevt_ID',
	0,
	524296);
INSERT INTO O_ID
	VALUES (0,
	2097159);
INSERT INTO O_OIDA
	VALUES (2097175,
	2097159,
	0);
INSERT INTO O_OIDA
	VALUES (2097176,
	2097159,
	0);
INSERT INTO O_OIDA
	VALUES (2097174,
	2097159,
	0);
INSERT INTO O_OBJ
	VALUES (2097160,
	'Access Path',
	408,
	'CA_ACC',
	'An access path (CA_ACC) represents modeled data access path (on the Object Access Model) between two of the following elements: objects (O_OBJ) and External Entities (S_EEM).  The access path is created by the tool in fully-derived mode, or by the user in partially-derived mode.',
	2097156);
INSERT INTO O_NBATTR
	VALUES (2097177,
	2097160);
INSERT INTO O_BATTR
	VALUES (2097177,
	2097160);
INSERT INTO O_ATTR
	VALUES (2097177,
	2097160,
	0,
	'APath_ID',
	'Full Name: Access Path Identifier',
	'',
	'APath_ID',
	0,
	524294);
INSERT INTO O_REF
	VALUES (2097160,
	1048578,
	0,
	1048582,
	1048597,
	1048621,
	1048620,
	2097178,
	2097167,
	0,
	0,
	'

Notes:

');
INSERT INTO O_RATTR
	VALUES (2097178,
	2097160,
	1048582,
	1048578,
	1);
INSERT INTO O_ATTR
	VALUES (2097178,
	2097160,
	2097177,
	'SS_ID',
	'',
	'',
	'SS_ID',
	0,
	524296);
INSERT INTO O_REF
	VALUES (2097160,
	2621441,
	0,
	2621441,
	2097177,
	2097204,
	2097205,
	2097179,
	2097168,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (2097179,
	2097160,
	2621441,
	2621441,
	1);
INSERT INTO O_ATTR
	VALUES (2097179,
	2097160,
	2097178,
	'SM_ID',
	'',
	'',
	'SM_ID',
	0,
	524296);
INSERT INTO O_REF
	VALUES (2097160,
	3145740,
	0,
	3145787,
	2097168,
	2097186,
	2097187,
	2097180,
	2097169,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (2097180,
	2097160,
	3145787,
	3145740,
	1);
INSERT INTO O_ATTR
	VALUES (2097180,
	2097160,
	2097179,
	'IObj_ID',
	'',
	'',
	'IObj_ID',
	0,
	524296);
INSERT INTO O_ID
	VALUES (0,
	2097160);
INSERT INTO O_OIDA
	VALUES (2097177,
	2097160,
	0);
INSERT INTO O_RTIDA
	VALUES (2097177,
	2097160,
	0,
	2097159,
	2097167);
INSERT INTO O_OBJ
	VALUES (2097161,
	'SM to OBJ Access Path',
	409,
	'CA_SMOA',
	'An SM to OBJ access path represents a path on the Object Access Model that originates at a State Model  and terminates at an object.  ',
	2097156);
INSERT INTO O_REF
	VALUES (2097161,
	2097160,
	0,
	2097177,
	2097159,
	2097168,
	2097167,
	2097181,
	2097170,
	0,
	0,
	'

Notes:

');
INSERT INTO O_RATTR
	VALUES (2097181,
	2097161,
	2097177,
	2097160,
	1);
INSERT INTO O_ATTR
	VALUES (2097181,
	2097161,
	0,
	'APath_ID',
	'',
	'',
	'APath_ID',
	0,
	524296);
INSERT INTO O_REF
	VALUES (2097161,
	3145732,
	0,
	3145751,
	2097160,
	2097170,
	2097171,
	2097182,
	2097171,
	0,
	0,
	'

Notes:

');
INSERT INTO O_RATTR
	VALUES (2097182,
	2097161,
	3145751,
	3145732,
	1);
INSERT INTO O_ATTR
	VALUES (2097182,
	2097161,
	2097181,
	'Obj_ID',
	'',
	'',
	'Obj_ID',
	0,
	524296);
INSERT INTO O_REF
	VALUES (2097161,
	3145740,
	0,
	3145787,
	2097163,
	2097176,
	2097177,
	2097183,
	2097172,
	0,
	1,
	'SM to OBJ Access Path (CA_SMOA).IObj_ID must be constrained such that Imported Object (O_IOBJ).Obj_ID is the same as (CA_SMOA).IObj_ID. That is, the Imported Object instance that the SM to OBJ Access Path instance is related to by R420 , if any, must be the one representing the Object (O_OBJ) instance it is related to by R417.');
INSERT INTO O_RATTR
	VALUES (2097183,
	2097161,
	3145787,
	3145740,
	1);
INSERT INTO O_ATTR
	VALUES (2097183,
	2097161,
	2097182,
	'IObj_ID',
	'',
	'',
	'IObj_ID',
	0,
	524296);
INSERT INTO O_ID
	VALUES (0,
	2097161);
INSERT INTO O_OIDA
	VALUES (2097181,
	2097161,
	0);
INSERT INTO O_ID
	VALUES (1,
	2097161);
INSERT INTO O_OIDA
	VALUES (2097182,
	2097161,
	1);
INSERT INTO O_RTIDA
	VALUES (2097182,
	2097161,
	1,
	2097161,
	2097172);
INSERT INTO O_OIDA
	VALUES (2097181,
	2097161,
	1);
INSERT INTO O_RTIDA
	VALUES (2097181,
	2097161,
	1,
	2097161,
	2097172);
INSERT INTO O_OBJ
	VALUES (2097162,
	'SM to EE Access Path',
	410,
	'CA_SMEEA',
	'An SM to EE access path represents a path on the Object Access Model that originates at a State Model  and terminates at an external entity.  ',
	2097156);
INSERT INTO O_REF
	VALUES (2097162,
	2097160,
	0,
	2097177,
	2097159,
	2097169,
	2097167,
	2097184,
	2097173,
	0,
	0,
	'

Notes:

');
INSERT INTO O_RATTR
	VALUES (2097184,
	2097162,
	2097177,
	2097160,
	1);
INSERT INTO O_ATTR
	VALUES (2097184,
	2097162,
	0,
	'APath_ID',
	'

Domain:


Notes:

',
	'',
	'APath_ID',
	0,
	524296);
INSERT INTO O_REF
	VALUES (2097162,
	1048580,
	0,
	1048595,
	2097164,
	2097179,
	2097178,
	2097185,
	2097185,
	0,
	0,
	'

Notes:

');
INSERT INTO O_RATTR
	VALUES (2097185,
	2097162,
	1048589,
	1048579,
	1);
INSERT INTO O_ATTR
	VALUES (2097185,
	2097162,
	2097184,
	'EE_ID',
	'

Domain:


Notes:

',
	'',
	'EE_ID',
	0,
	524296);
INSERT INTO O_REF
	VALUES (2097162,
	1048580,
	0,
	1048594,
	2097164,
	2097179,
	2097178,
	2097186,
	2097186,
	0,
	0,
	'

Notes:

');
INSERT INTO O_RATTR
	VALUES (2097186,
	2097162,
	1048594,
	1048580,
	1);
INSERT INTO O_ATTR
	VALUES (2097186,
	2097162,
	2097185,
	'EEmod_ID',
	'

Domain:


Notes:

',
	'',
	'EEmod_ID',
	0,
	524296);
INSERT INTO O_ID
	VALUES (0,
	2097162);
INSERT INTO O_OIDA
	VALUES (2097184,
	2097162,
	0);
INSERT INTO O_ID
	VALUES (1,
	2097162);
INSERT INTO O_OIDA
	VALUES (2097185,
	2097162,
	1);
INSERT INTO O_RTIDA
	VALUES (2097185,
	2097162,
	1,
	2097165,
	2097180);
INSERT INTO O_OIDA
	VALUES (2097184,
	2097162,
	1);
INSERT INTO O_RTIDA
	VALUES (2097184,
	2097162,
	1,
	2097165,
	2097180);
INSERT INTO O_OBJ
	VALUES (2097163,
	'SM to OBJ Attribute Access',
	411,
	'CA_SMOAA',
	'This object represents the data access attributed to the SM to OBJ data access path (CA_SMOA).',
	2097156);
INSERT INTO O_REF
	VALUES (2097163,
	2097161,
	1,
	2097181,
	2097161,
	2097173,
	2097172,
	2097187,
	2097187,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (2097187,
	2097163,
	2097177,
	2097160,
	1);
INSERT INTO O_ATTR
	VALUES (2097187,
	2097163,
	0,
	'APath_ID',
	'',
	'',
	'APath_ID',
	0,
	524296);
INSERT INTO O_REF
	VALUES (2097163,
	3145731,
	0,
	3145742,
	2097162,
	2097174,
	2097175,
	2097188,
	2097174,
	0,
	0,
	'

Notes:

');
INSERT INTO O_RATTR
	VALUES (2097188,
	2097163,
	3145742,
	3145731,
	1);
INSERT INTO O_ATTR
	VALUES (2097188,
	2097163,
	2097187,
	'Attr_ID',
	'

Domain:


Notes:

',
	'',
	'Attr_ID',
	0,
	524296);
INSERT INTO O_REF
	VALUES (2097163,
	3145731,
	0,
	3145743,
	2097162,
	2097174,
	2097175,
	2097189,
	2097175,
	0,
	0,
	'

Notes:

');
INSERT INTO O_REF
	VALUES (2097163,
	2097161,
	1,
	2097182,
	2097161,
	2097173,
	2097172,
	2097189,
	2097188,
	2097175,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (2097189,
	2097163,
	3145751,
	3145732,
	1);
INSERT INTO O_ATTR
	VALUES (2097189,
	2097163,
	2097188,
	'Obj_ID',
	'',
	'',
	'Obj_ID',
	0,
	524296);
INSERT INTO O_ID
	VALUES (0,
	2097163);
INSERT INTO O_OIDA
	VALUES (2097189,
	2097163,
	0);
INSERT INTO O_OIDA
	VALUES (2097187,
	2097163,
	0);
INSERT INTO O_OIDA
	VALUES (2097188,
	2097163,
	0);
INSERT INTO O_OBJ
	VALUES (2097164,
	'SM to EE Data Item Access',
	412,
	'CA_SMEED',
	'This object represents the data access attributed to the SM to EE data access path (CA_SMEEA).',
	2097156);
INSERT INTO O_REF
	VALUES (2097164,
	2097162,
	1,
	2097184,
	2097165,
	2097181,
	2097180,
	2097190,
	2097189,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (2097190,
	2097164,
	2097177,
	2097160,
	1);
INSERT INTO O_ATTR
	VALUES (2097190,
	2097164,
	0,
	'APath_ID',
	'',
	'',
	'APath_ID',
	0,
	524296);
INSERT INTO O_REF
	VALUES (2097164,
	1048581,
	0,
	1048598,
	2097166,
	2097183,
	2097182,
	2097191,
	2097190,
	0,
	0,
	'

Notes:

');
INSERT INTO O_RATTR
	VALUES (2097191,
	2097164,
	1048598,
	1048581,
	1);
INSERT INTO O_ATTR
	VALUES (2097191,
	2097164,
	2097190,
	'EEdi_ID',
	'

Domain:


Notes:

',
	'',
	'EEdi_ID',
	0,
	524296);
INSERT INTO O_REF
	VALUES (2097164,
	1048581,
	0,
	1048599,
	2097166,
	2097183,
	2097182,
	2097192,
	2097191,
	0,
	0,
	'

Notes:

');
INSERT INTO O_REF
	VALUES (2097164,
	2097162,
	1,
	2097185,
	2097165,
	2097181,
	2097180,
	2097192,
	2097192,
	2097191,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (2097192,
	2097164,
	1048589,
	1048579,
	1);
INSERT INTO O_ATTR
	VALUES (2097192,
	2097164,
	2097191,
	'EE_ID',
	'',
	'',
	'EE_ID',
	0,
	524296);
INSERT INTO O_ID
	VALUES (0,
	2097164);
INSERT INTO O_OIDA
	VALUES (2097190,
	2097164,
	0);
INSERT INTO O_OIDA
	VALUES (2097191,
	2097164,
	0);
INSERT INTO O_OIDA
	VALUES (2097192,
	2097164,
	0);
INSERT INTO O_IOBJ
	VALUES (2097153,
	1048580,
	5,
	2097156,
	'External Entity in Model',
	'S_EEM');
INSERT INTO O_IOBJ
	VALUES (2097154,
	1048581,
	5,
	2097156,
	'External Entity Data Item',
	'S_EEDI');
INSERT INTO O_IOBJ
	VALUES (2097155,
	1048582,
	5,
	2097156,
	'External Entity Event',
	'S_EEEVT');
INSERT INTO O_IOBJ
	VALUES (2097156,
	1048580,
	5,
	2097156,
	'External Entity in Model',
	'S_EEM');
INSERT INTO O_IOBJ
	VALUES (2097157,
	3145731,
	5,
	2097156,
	'Attribute',
	'O_ATTR');
INSERT INTO O_IOBJ
	VALUES (2097158,
	3145732,
	5,
	2097156,
	'Object',
	'O_OBJ');
INSERT INTO O_IOBJ
	VALUES (2097159,
	3145740,
	5,
	2097156,
	'Imported Object',
	'O_IOBJ');
INSERT INTO O_IOBJ
	VALUES (2097160,
	2621442,
	5,
	2097156,
	'State Model Event',
	'SM_EVT');
INSERT INTO O_IOBJ
	VALUES (2097161,
	2621441,
	5,
	2097156,
	'State Model',
	'SM_SM');
INSERT INTO R_SUBSUP
	VALUES (2097153);
INSERT INTO R_REL
	VALUES (2097153,
	401,
	'

Notes:

',
	2097156);
INSERT INTO R_SUPER
	VALUES (2097153,
	2097153,
	2097153);
INSERT INTO R_RTO
	VALUES (2097153,
	2097153,
	2097153,
	0);
INSERT INTO R_OIR
	VALUES (2097153,
	2097153,
	2097153,
	0);
INSERT INTO R_SUB
	VALUES (2097154,
	2097153,
	2097154);
INSERT INTO R_RGO
	VALUES (2097154,
	2097153,
	2097154);
INSERT INTO R_OIR
	VALUES (2097154,
	2097153,
	2097154,
	0);
INSERT INTO R_SUB
	VALUES (2097156,
	2097153,
	2097155);
INSERT INTO R_RGO
	VALUES (2097156,
	2097153,
	2097155);
INSERT INTO R_OIR
	VALUES (2097156,
	2097153,
	2097155,
	0);
INSERT INTO R_SUB
	VALUES (2097155,
	2097153,
	2097156);
INSERT INTO R_RGO
	VALUES (2097155,
	2097153,
	2097156);
INSERT INTO R_OIR
	VALUES (2097155,
	2097153,
	2097156,
	0);
INSERT INTO R_SIMP
	VALUES (2097154);
INSERT INTO R_REL
	VALUES (2097154,
	404,
	'

Notes:

',
	2097156);
INSERT INTO R_PART
	VALUES (2097154,
	2097154,
	2097157,
	0,
	0,
	'is carried by');
INSERT INTO R_RTO
	VALUES (2097154,
	2097154,
	2097157,
	0);
INSERT INTO R_OIR
	VALUES (2097154,
	2097154,
	2097157,
	0);
INSERT INTO R_FORM
	VALUES (2097157,
	2097154,
	2097158,
	1,
	1,
	'carries');
INSERT INTO R_RGO
	VALUES (2097157,
	2097154,
	2097158);
INSERT INTO R_OIR
	VALUES (2097157,
	2097154,
	2097158,
	0);
INSERT INTO R_SIMP
	VALUES (2097155);
INSERT INTO R_REL
	VALUES (2097155,
	408,
	'

Notes:

',
	2097156);
INSERT INTO R_PART
	VALUES (2097155,
	2097155,
	2097159,
	0,
	0,
	'is carried by');
INSERT INTO R_RTO
	VALUES (2097155,
	2097155,
	2097159,
	0);
INSERT INTO R_OIR
	VALUES (2097155,
	2097155,
	2097159,
	0);
INSERT INTO R_FORM
	VALUES (2097158,
	2097155,
	2097160,
	1,
	1,
	'carries');
INSERT INTO R_RGO
	VALUES (2097158,
	2097155,
	2097160);
INSERT INTO R_OIR
	VALUES (2097158,
	2097155,
	2097160,
	0);
INSERT INTO R_SIMP
	VALUES (2097156);
INSERT INTO R_REL
	VALUES (2097156,
	412,
	'

Notes:

',
	2097156);
INSERT INTO R_PART
	VALUES (2097156,
	2097156,
	2097161,
	0,
	0,
	'is carried by');
INSERT INTO R_RTO
	VALUES (2097156,
	2097156,
	2097161,
	0);
INSERT INTO R_OIR
	VALUES (2097156,
	2097156,
	2097161,
	0);
INSERT INTO R_FORM
	VALUES (2097159,
	2097156,
	2097162,
	1,
	1,
	'carries');
INSERT INTO R_RGO
	VALUES (2097159,
	2097156,
	2097162);
INSERT INTO R_OIR
	VALUES (2097159,
	2097156,
	2097162,
	0);
INSERT INTO R_SIMP
	VALUES (2097157);
INSERT INTO R_REL
	VALUES (2097157,
	413,
	'

Notes:

',
	2097156);
INSERT INTO R_PART
	VALUES (1048582,
	2097157,
	2097163,
	0,
	0,
	'represents communication of');
INSERT INTO R_RTO
	VALUES (1048582,
	2097157,
	2097163,
	0);
INSERT INTO R_OIR
	VALUES (1048582,
	2097157,
	2097163,
	2097155);
INSERT INTO R_FORM
	VALUES (2097159,
	2097157,
	2097164,
	1,
	1,
	'is carried to EE via');
INSERT INTO R_RGO
	VALUES (2097159,
	2097157,
	2097164);
INSERT INTO R_OIR
	VALUES (2097159,
	2097157,
	2097164,
	0);
INSERT INTO R_SIMP
	VALUES (2097158);
INSERT INTO R_REL
	VALUES (2097158,
	414,
	'

Notes:

',
	2097156);
INSERT INTO R_FORM
	VALUES (2097155,
	2097158,
	2097165,
	1,
	1,
	'represents the destination SM for');
INSERT INTO R_RGO
	VALUES (2097155,
	2097158,
	2097165);
INSERT INTO R_OIR
	VALUES (2097155,
	2097158,
	2097165,
	0);
INSERT INTO R_PART
	VALUES (3145740,
	2097158,
	2097166,
	0,
	1,
	'destination SM can be represented by');
INSERT INTO R_RTO
	VALUES (3145740,
	2097158,
	2097166,
	0);
INSERT INTO R_OIR
	VALUES (3145740,
	2097158,
	2097166,
	2097159);
INSERT INTO R_SUBSUP
	VALUES (2097159);
INSERT INTO R_REL
	VALUES (2097159,
	415,
	'

Notes:

',
	2097156);
INSERT INTO R_SUPER
	VALUES (2097160,
	2097159,
	2097167);
INSERT INTO R_RTO
	VALUES (2097160,
	2097159,
	2097167,
	0);
INSERT INTO R_OIR
	VALUES (2097160,
	2097159,
	2097167,
	0);
INSERT INTO R_SUB
	VALUES (2097161,
	2097159,
	2097168);
INSERT INTO R_RGO
	VALUES (2097161,
	2097159,
	2097168);
INSERT INTO R_OIR
	VALUES (2097161,
	2097159,
	2097168,
	0);
INSERT INTO R_SUB
	VALUES (2097162,
	2097159,
	2097169);
INSERT INTO R_RGO
	VALUES (2097162,
	2097159,
	2097169);
INSERT INTO R_OIR
	VALUES (2097162,
	2097159,
	2097169,
	0);
INSERT INTO R_SIMP
	VALUES (2097160);
INSERT INTO R_REL
	VALUES (2097160,
	417,
	'

Notes:

',
	2097156);
INSERT INTO R_FORM
	VALUES (2097161,
	2097160,
	2097170,
	1,
	1,
	'has data access represented by');
INSERT INTO R_RGO
	VALUES (2097161,
	2097160,
	2097170);
INSERT INTO R_OIR
	VALUES (2097161,
	2097160,
	2097170,
	0);
INSERT INTO R_PART
	VALUES (3145732,
	2097160,
	2097171,
	0,
	0,
	'shows accesses of data from');
INSERT INTO R_RTO
	VALUES (3145732,
	2097160,
	2097171,
	0);
INSERT INTO R_OIR
	VALUES (3145732,
	2097160,
	2097171,
	2097158);
INSERT INTO R_SIMP
	VALUES (2097161);
INSERT INTO R_REL
	VALUES (2097161,
	418,
	'

Notes:

',
	2097156);
INSERT INTO R_PART
	VALUES (2097161,
	2097161,
	2097172,
	0,
	0,
	'is carried by ');
INSERT INTO R_RTO
	VALUES (2097161,
	2097161,
	2097172,
	1);
INSERT INTO R_OIR
	VALUES (2097161,
	2097161,
	2097172,
	0);
INSERT INTO R_FORM
	VALUES (2097163,
	2097161,
	2097173,
	1,
	1,
	'carries');
INSERT INTO R_RGO
	VALUES (2097163,
	2097161,
	2097173);
INSERT INTO R_OIR
	VALUES (2097163,
	2097161,
	2097173,
	0);
INSERT INTO R_SIMP
	VALUES (2097162);
INSERT INTO R_REL
	VALUES (2097162,
	419,
	'

Notes:

',
	2097156);
INSERT INTO R_FORM
	VALUES (2097163,
	2097162,
	2097174,
	1,
	1,
	'is accessed by');
INSERT INTO R_RGO
	VALUES (2097163,
	2097162,
	2097174);
INSERT INTO R_OIR
	VALUES (2097163,
	2097162,
	2097174,
	0);
INSERT INTO R_PART
	VALUES (3145731,
	2097162,
	2097175,
	0,
	0,
	'represents access of');
INSERT INTO R_RTO
	VALUES (3145731,
	2097162,
	2097175,
	0);
INSERT INTO R_OIR
	VALUES (3145731,
	2097162,
	2097175,
	2097157);
INSERT INTO R_SIMP
	VALUES (2097163);
INSERT INTO R_REL
	VALUES (2097163,
	420,
	'

Notes:

',
	2097156);
INSERT INTO R_FORM
	VALUES (2097161,
	2097163,
	2097176,
	1,
	1,
	'represents the destination OBJ for');
INSERT INTO R_RGO
	VALUES (2097161,
	2097163,
	2097176);
INSERT INTO R_OIR
	VALUES (2097161,
	2097163,
	2097176,
	0);
INSERT INTO R_PART
	VALUES (3145740,
	2097163,
	2097177,
	0,
	1,
	'destination OBJ can be represented by');
INSERT INTO R_RTO
	VALUES (3145740,
	2097163,
	2097177,
	0);
INSERT INTO R_OIR
	VALUES (3145740,
	2097163,
	2097177,
	2097159);
INSERT INTO R_SIMP
	VALUES (2097164);
INSERT INTO R_REL
	VALUES (2097164,
	421,
	'

Notes:

',
	2097156);
INSERT INTO R_PART
	VALUES (1048580,
	2097164,
	2097178,
	0,
	0,
	'accesses data of');
INSERT INTO R_RTO
	VALUES (1048580,
	2097164,
	2097178,
	0);
INSERT INTO R_OIR
	VALUES (1048580,
	2097164,
	2097178,
	2097153);
INSERT INTO R_FORM
	VALUES (2097162,
	2097164,
	2097179,
	1,
	1,
	'has data access represented by');
INSERT INTO R_RGO
	VALUES (2097162,
	2097164,
	2097179);
INSERT INTO R_OIR
	VALUES (2097162,
	2097164,
	2097179,
	0);
INSERT INTO R_SIMP
	VALUES (2097165);
INSERT INTO R_REL
	VALUES (2097165,
	422,
	'

Notes:

',
	2097156);
INSERT INTO R_PART
	VALUES (2097162,
	2097165,
	2097180,
	0,
	0,
	'is carried by');
INSERT INTO R_RTO
	VALUES (2097162,
	2097165,
	2097180,
	1);
INSERT INTO R_OIR
	VALUES (2097162,
	2097165,
	2097180,
	0);
INSERT INTO R_FORM
	VALUES (2097164,
	2097165,
	2097181,
	1,
	1,
	'carries');
INSERT INTO R_RGO
	VALUES (2097164,
	2097165,
	2097181);
INSERT INTO R_OIR
	VALUES (2097164,
	2097165,
	2097181,
	0);
INSERT INTO R_SIMP
	VALUES (2097166);
INSERT INTO R_REL
	VALUES (2097166,
	423,
	'

Notes:

',
	2097156);
INSERT INTO R_PART
	VALUES (1048581,
	2097166,
	2097182,
	0,
	0,
	'represents access of');
INSERT INTO R_RTO
	VALUES (1048581,
	2097166,
	2097182,
	0);
INSERT INTO R_OIR
	VALUES (1048581,
	2097166,
	2097182,
	2097154);
INSERT INTO R_FORM
	VALUES (2097164,
	2097166,
	2097183,
	1,
	1,
	'is accessed by');
INSERT INTO R_RGO
	VALUES (2097164,
	2097166,
	2097183);
INSERT INTO R_OIR
	VALUES (2097164,
	2097166,
	2097183,
	0);
INSERT INTO R_SIMP
	VALUES (2097167);
INSERT INTO R_REL
	VALUES (2097167,
	424,
	'',
	2097156);
INSERT INTO R_FORM
	VALUES (2097155,
	2097167,
	2097184,
	1,
	1,
	'represents the origination SM for');
INSERT INTO R_RGO
	VALUES (2097155,
	2097167,
	2097184);
INSERT INTO R_OIR
	VALUES (2097155,
	2097167,
	2097184,
	0);
INSERT INTO R_PART
	VALUES (3145740,
	2097167,
	2097185,
	0,
	1,
	'origination SM can be represented by');
INSERT INTO R_RTO
	VALUES (3145740,
	2097167,
	2097185,
	0);
INSERT INTO R_OIR
	VALUES (3145740,
	2097167,
	2097185,
	2097159);
INSERT INTO R_SIMP
	VALUES (2097168);
INSERT INTO R_REL
	VALUES (2097168,
	425,
	'',
	2097156);
INSERT INTO R_FORM
	VALUES (2097160,
	2097168,
	2097186,
	1,
	1,
	'represents origination OBJ for');
INSERT INTO R_RGO
	VALUES (2097160,
	2097168,
	2097186);
INSERT INTO R_OIR
	VALUES (2097160,
	2097168,
	2097186,
	0);
INSERT INTO R_PART
	VALUES (3145740,
	2097168,
	2097187,
	0,
	1,
	'origination OBJ can be represented by');
INSERT INTO R_RTO
	VALUES (3145740,
	2097168,
	2097187,
	0);
INSERT INTO R_OIR
	VALUES (3145740,
	2097168,
	2097187,
	2097159);
INSERT INTO R_SIMP
	VALUES (2097169);
INSERT INTO R_REL
	VALUES (2097169,
	405,
	'',
	2097156);
INSERT INTO R_FORM
	VALUES (2097157,
	2097169,
	2097188,
	1,
	1,
	'is carried to other SMs via');
INSERT INTO R_RGO
	VALUES (2097157,
	2097169,
	2097188);
INSERT INTO R_OIR
	VALUES (2097157,
	2097169,
	2097188,
	0);
INSERT INTO R_PART
	VALUES (2621442,
	2097169,
	2097189,
	0,
	0,
	'represents communication of');
INSERT INTO R_RTO
	VALUES (2621442,
	2097169,
	2097189,
	0);
INSERT INTO R_OIR
	VALUES (2621442,
	2097169,
	2097189,
	2097160);
INSERT INTO R_SIMP
	VALUES (2097170);
INSERT INTO R_REL
	VALUES (2097170,
	409,
	'',
	2097156);
INSERT INTO R_FORM
	VALUES (2097158,
	2097170,
	2097190,
	1,
	1,
	'is carried to other SMs via');
INSERT INTO R_RGO
	VALUES (2097158,
	2097170,
	2097190);
INSERT INTO R_OIR
	VALUES (2097158,
	2097170,
	2097190,
	0);
INSERT INTO R_PART
	VALUES (2621442,
	2097170,
	2097191,
	0,
	0,
	'represents communication of');
INSERT INTO R_RTO
	VALUES (2621442,
	2097170,
	2097191,
	0);
INSERT INTO R_OIR
	VALUES (2621442,
	2097170,
	2097191,
	2097160);
INSERT INTO R_SIMP
	VALUES (2097171);
INSERT INTO R_REL
	VALUES (2097171,
	403,
	'',
	2097156);
INSERT INTO R_FORM
	VALUES (2097154,
	2097171,
	2097192,
	1,
	1,
	'has received event communication represented by');
INSERT INTO R_RGO
	VALUES (2097154,
	2097171,
	2097192);
INSERT INTO R_OIR
	VALUES (2097154,
	2097171,
	2097192,
	0);
INSERT INTO R_PART
	VALUES (2621441,
	2097171,
	2097193,
	0,
	0,
	'shows event communication to');
INSERT INTO R_RTO
	VALUES (2621441,
	2097171,
	2097193,
	0);
INSERT INTO R_OIR
	VALUES (2621441,
	2097171,
	2097193,
	2097161);
INSERT INTO R_SIMP
	VALUES (2097172);
INSERT INTO R_REL
	VALUES (2097172,
	402,
	'',
	2097156);
INSERT INTO R_PART
	VALUES (1048580,
	2097172,
	2097194,
	0,
	0,
	'originates from');
INSERT INTO R_RTO
	VALUES (1048580,
	2097172,
	2097194,
	0);
INSERT INTO R_OIR
	VALUES (1048580,
	2097172,
	2097194,
	2097156);
INSERT INTO R_FORM
	VALUES (2097154,
	2097172,
	2097195,
	1,
	1,
	'originates');
INSERT INTO R_RGO
	VALUES (2097154,
	2097172,
	2097195);
INSERT INTO R_OIR
	VALUES (2097154,
	2097172,
	2097195,
	0);
INSERT INTO R_SIMP
	VALUES (2097173);
INSERT INTO R_REL
	VALUES (2097173,
	410,
	'',
	2097156);
INSERT INTO R_FORM
	VALUES (2097156,
	2097173,
	2097196,
	1,
	1,
	'originates');
INSERT INTO R_RGO
	VALUES (2097156,
	2097173,
	2097196);
INSERT INTO R_OIR
	VALUES (2097156,
	2097173,
	2097196,
	0);
INSERT INTO R_PART
	VALUES (2621441,
	2097173,
	2097197,
	0,
	0,
	'originates from');
INSERT INTO R_RTO
	VALUES (2621441,
	2097173,
	2097197,
	0);
INSERT INTO R_OIR
	VALUES (2621441,
	2097173,
	2097197,
	2097161);
INSERT INTO R_SIMP
	VALUES (2097174);
INSERT INTO R_REL
	VALUES (2097174,
	411,
	'',
	2097156);
INSERT INTO R_PART
	VALUES (1048580,
	2097174,
	2097198,
	0,
	0,
	'shows event communication to');
INSERT INTO R_RTO
	VALUES (1048580,
	2097174,
	2097198,
	0);
INSERT INTO R_OIR
	VALUES (1048580,
	2097174,
	2097198,
	2097156);
INSERT INTO R_FORM
	VALUES (2097156,
	2097174,
	2097199,
	1,
	1,
	'has received event communication represented by');
INSERT INTO R_RGO
	VALUES (2097156,
	2097174,
	2097199);
INSERT INTO R_OIR
	VALUES (2097156,
	2097174,
	2097199,
	0);
INSERT INTO R_SIMP
	VALUES (2097175);
INSERT INTO R_REL
	VALUES (2097175,
	406,
	'',
	2097156);
INSERT INTO R_FORM
	VALUES (2097155,
	2097175,
	2097200,
	1,
	1,
	'originates');
INSERT INTO R_RGO
	VALUES (2097155,
	2097175,
	2097200);
INSERT INTO R_OIR
	VALUES (2097155,
	2097175,
	2097200,
	0);
INSERT INTO R_PART
	VALUES (2621441,
	2097175,
	2097201,
	0,
	0,
	'originates from');
INSERT INTO R_RTO
	VALUES (2621441,
	2097175,
	2097201,
	0);
INSERT INTO R_OIR
	VALUES (2621441,
	2097175,
	2097201,
	2097161);
INSERT INTO R_SIMP
	VALUES (2097176);
INSERT INTO R_REL
	VALUES (2097176,
	407,
	'',
	2097156);
INSERT INTO R_FORM
	VALUES (2097155,
	2097176,
	2097202,
	1,
	1,
	'represents the destination SM for');
INSERT INTO R_RGO
	VALUES (2097155,
	2097176,
	2097202);
INSERT INTO R_OIR
	VALUES (2097155,
	2097176,
	2097202,
	0);
INSERT INTO R_PART
	VALUES (2621441,
	2097176,
	2097203,
	0,
	0,
	'shows event communication to');
INSERT INTO R_RTO
	VALUES (2621441,
	2097176,
	2097203,
	0);
INSERT INTO R_OIR
	VALUES (2621441,
	2097176,
	2097203,
	2097161);
INSERT INTO R_SIMP
	VALUES (2097177);
INSERT INTO R_REL
	VALUES (2097177,
	416,
	'',
	2097156);
INSERT INTO R_FORM
	VALUES (2097160,
	2097177,
	2097204,
	1,
	1,
	'originates');
INSERT INTO R_RGO
	VALUES (2097160,
	2097177,
	2097204);
INSERT INTO R_OIR
	VALUES (2097160,
	2097177,
	2097204,
	0);
INSERT INTO R_PART
	VALUES (2621441,
	2097177,
	2097205,
	0,
	0,
	'originates from');
INSERT INTO R_RTO
	VALUES (2621441,
	2097177,
	2097205,
	0);
INSERT INTO R_OIR
	VALUES (2621441,
	2097177,
	2097205,
	2097161);
INSERT INTO S_SS
	VALUES (2621445,
	'State Model',
	'Objects (O_OBJ) that have interesting behavior are given lifecycles. These lifecycles are described using state models (SM_SM). A state model consists of states, events, transitions and state actions. The state model exists for each instance of the object for which it has been modeled (SM_ISM). A state model can also be an assigner state model (SM_ASM) of which only one can exists for all object instances. The purpose of the assigner state model is to act as a single point of control through which competing requests are serialized.',
	'SM',
	501,
	2531670,
	2621445);
INSERT INTO O_OBJ
	VALUES (2621441,
	'State Model',
	501,
	'SM_SM',
	'A State Model represents the lifecycle of an object in terms of states, transitions, events, and actions.',
	2621445);
INSERT INTO O_NBATTR
	VALUES (2621441,
	2621441);
INSERT INTO O_BATTR
	VALUES (2621441,
	2621441);
INSERT INTO O_ATTR
	VALUES (2621441,
	2621441,
	0,
	'SM_ID',
	'Full Name: State Model Identifier',
	'',
	'SM_ID',
	0,
	524294);
INSERT INTO O_NBATTR
	VALUES (2621442,
	2621441);
INSERT INTO O_BATTR
	VALUES (2621442,
	2621441);
INSERT INTO O_ATTR
	VALUES (2621442,
	2621441,
	2621441,
	'Descrip',
	'Full Name: Description',
	'',
	'Descrip',
	0,
	524293);
INSERT INTO O_NBATTR
	VALUES (2621443,
	2621441);
INSERT INTO O_BATTR
	VALUES (2621443,
	2621441);
INSERT INTO O_ATTR
	VALUES (2621443,
	2621441,
	2621442,
	'Config_ID',
	'Full Name: Configuration Identifier
Description: The component ID of  this state model.',
	'',
	'Config_ID',
	0,
	524291);
INSERT INTO O_ID
	VALUES (0,
	2621441);
INSERT INTO O_OIDA
	VALUES (2621441,
	2621441,
	0);
INSERT INTO O_RTIDA
	VALUES (2621441,
	2621441,
	0,
	2621448,
	2621459);
INSERT INTO O_RTIDA
	VALUES (2621441,
	2621441,
	0,
	2097176,
	2097203);
INSERT INTO O_RTIDA
	VALUES (2621441,
	2621441,
	0,
	2097173,
	2097197);
INSERT INTO O_RTIDA
	VALUES (2621441,
	2621441,
	0,
	2097175,
	2097201);
INSERT INTO O_RTIDA
	VALUES (2621441,
	2621441,
	0,
	2621441,
	2621442);
INSERT INTO O_RTIDA
	VALUES (2621441,
	2621441,
	0,
	2621453,
	2621474);
INSERT INTO O_RTIDA
	VALUES (2621441,
	2621441,
	0,
	2621442,
	2621444);
INSERT INTO O_RTIDA
	VALUES (2621441,
	2621441,
	0,
	2621461,
	2621492);
INSERT INTO O_RTIDA
	VALUES (2621441,
	2621441,
	0,
	2621447,
	2621458);
INSERT INTO O_RTIDA
	VALUES (2621441,
	2621441,
	0,
	2097171,
	2097193);
INSERT INTO O_RTIDA
	VALUES (2621441,
	2621441,
	0,
	2097177,
	2097205);
INSERT INTO O_RTIDA
	VALUES (2621441,
	2621441,
	0,
	2621454,
	2621475);
INSERT INTO O_RTIDA
	VALUES (2621441,
	2621441,
	0,
	2621457,
	2621483);
INSERT INTO O_OBJ
	VALUES (2621442,
	'State Model Event',
	503,
	'SM_EVT',
	'Represents an event for this state model.  Events can be either State Event Matrix events (SM_SEVT) or polymorphic events (SM_PEVT).',
	2621445);
INSERT INTO O_NBATTR
	VALUES (2621444,
	2621442);
INSERT INTO O_BATTR
	VALUES (2621444,
	2621442);
INSERT INTO O_ATTR
	VALUES (2621444,
	2621442,
	0,
	'SMevt_ID',
	'Full Name: State Model Event Identifier',
	'',
	'SMevt_ID',
	0,
	524294);
INSERT INTO O_REF
	VALUES (2621442,
	2621441,
	0,
	2621441,
	2621441,
	2621441,
	2621442,
	2621445,
	2621441,
	0,
	0,
	'');
INSERT INTO O_REF
	VALUES (2621442,
	2621460,
	0,
	2621515,
	2621459,
	2621486,
	2621487,
	2621445,
	2621482,
	2621441,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (2621445,
	2621442,
	2621441,
	2621441,
	1);
INSERT INTO O_ATTR
	VALUES (2621445,
	2621442,
	2621444,
	'SM_ID',
	'',
	'',
	'SM_ID',
	0,
	524296);
INSERT INTO O_REF
	VALUES (2621442,
	2621460,
	0,
	2621514,
	2621459,
	2621486,
	2621487,
	2621446,
	2621483,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (2621446,
	2621442,
	2621514,
	2621460,
	1);
INSERT INTO O_ATTR
	VALUES (2621446,
	2621442,
	2621445,
	'SMspd_ID',
	'',
	'',
	'SMspd_ID',
	0,
	524296);
INSERT INTO O_NBATTR
	VALUES (2621447,
	2621442);
INSERT INTO O_BATTR
	VALUES (2621447,
	2621442);
INSERT INTO O_ATTR
	VALUES (2621447,
	2621442,
	2621446,
	'Numb',
	'Full Name: Event Number',
	'',
	'Numb',
	0,
	524291);
INSERT INTO O_NBATTR
	VALUES (2621448,
	2621442);
INSERT INTO O_BATTR
	VALUES (2621448,
	2621442);
INSERT INTO O_ATTR
	VALUES (2621448,
	2621442,
	2621447,
	'Mning',
	'Full Name: Event Meaning',
	'',
	'Mning',
	0,
	524293);
INSERT INTO O_NBATTR
	VALUES (2621449,
	2621442);
INSERT INTO O_BATTR
	VALUES (2621449,
	2621442);
INSERT INTO O_ATTR
	VALUES (2621449,
	2621442,
	2621448,
	'Is_Lbl_U',
	'Full Name: State Model Event Label Unique Indicator
Description: This is a flag that indicates whether custom label keyletters are used for the event.
Data Domain: 0 = custom label keyletters are used, 1 = object keyletters are used',
	'',
	'Is_Lbl_U',
	0,
	524291);
INSERT INTO O_NBATTR
	VALUES (2621450,
	2621442);
INSERT INTO O_BATTR
	VALUES (2621450,
	2621442);
INSERT INTO O_ATTR
	VALUES (2621450,
	2621442,
	2621449,
	'Unq_Lbl',
	'''Full Name: State Model Event Unique Label
Description: Custom keyletters for the state model event',
	'',
	'Unq_Lbl',
	0,
	524293);
INSERT INTO O_DBATTR
	VALUES (2621451,
	2621442,
	'',
	0);
INSERT INTO O_BATTR
	VALUES (2621451,
	2621442);
INSERT INTO O_ATTR
	VALUES (2621451,
	2621442,
	2621450,
	'Drv_Lbl',
	'Full Name: State Model Event Derived Label
Description: contains  the event label derived by concatenating the keyletters and the event number.
Data Domain: 0 = derived label created by concatenating object keyletters + event number, 1 = derived label created by concatenating Unq_Lbl with event number',
	'',
	'Drv_Lbl',
	0,
	524293);
INSERT INTO O_NBATTR
	VALUES (2621452,
	2621442);
INSERT INTO O_BATTR
	VALUES (2621452,
	2621442);
INSERT INTO O_ATTR
	VALUES (2621452,
	2621442,
	2621451,
	'Descrip',
	'Full Name: Description',
	'',
	'Descrip',
	0,
	524293);
INSERT INTO O_ID
	VALUES (0,
	2621442);
INSERT INTO O_OIDA
	VALUES (2621444,
	2621442,
	0);
INSERT INTO O_RTIDA
	VALUES (2621444,
	2621442,
	0,
	2097169,
	2097189);
INSERT INTO O_RTIDA
	VALUES (2621444,
	2621442,
	0,
	2097170,
	2097191);
INSERT INTO O_OIDA
	VALUES (2621445,
	2621442,
	0);
INSERT INTO O_RTIDA
	VALUES (2621445,
	2621442,
	0,
	2097169,
	2097189);
INSERT INTO O_RTIDA
	VALUES (2621445,
	2621442,
	0,
	2097170,
	2097191);
INSERT INTO O_ID
	VALUES (1,
	2621442);
INSERT INTO O_OIDA
	VALUES (2621446,
	2621442,
	1);
INSERT INTO O_RTIDA
	VALUES (2621446,
	2621442,
	1,
	2621463,
	2621495);
INSERT INTO O_OIDA
	VALUES (2621444,
	2621442,
	1);
INSERT INTO O_RTIDA
	VALUES (2621444,
	2621442,
	1,
	2621463,
	2621495);
INSERT INTO O_OIDA
	VALUES (2621445,
	2621442,
	1);
INSERT INTO O_RTIDA
	VALUES (2621445,
	2621442,
	1,
	2621463,
	2621495);
INSERT INTO O_OBJ
	VALUES (2621443,
	'State Model State',
	502,
	'SM_STATE',
	'Represents a state in the state model.',
	2621445);
INSERT INTO O_NBATTR
	VALUES (2621453,
	2621443);
INSERT INTO O_BATTR
	VALUES (2621453,
	2621443);
INSERT INTO O_ATTR
	VALUES (2621453,
	2621443,
	0,
	'SMstt_ID',
	'Full Name: State Model State Identifier',
	'',
	'SMstt_ID',
	0,
	524294);
INSERT INTO O_REF
	VALUES (2621443,
	2621441,
	0,
	2621441,
	2621442,
	2621443,
	2621444,
	2621454,
	2621442,
	0,
	0,
	'');
INSERT INTO O_REF
	VALUES (2621443,
	2621460,
	0,
	2621515,
	2621458,
	2621484,
	2621485,
	2621454,
	2621484,
	2621442,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (2621454,
	2621443,
	2621441,
	2621441,
	1);
INSERT INTO O_ATTR
	VALUES (2621454,
	2621443,
	2621453,
	'SM_ID',
	'',
	'',
	'SM_ID',
	0,
	524296);
INSERT INTO O_REF
	VALUES (2621443,
	2621460,
	0,
	2621514,
	2621458,
	2621484,
	2621485,
	2621455,
	2621485,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (2621455,
	2621443,
	2621514,
	2621460,
	1);
INSERT INTO O_ATTR
	VALUES (2621455,
	2621443,
	2621454,
	'SMspd_ID',
	'',
	'',
	'SMspd_ID',
	0,
	524296);
INSERT INTO O_NBATTR
	VALUES (2621456,
	2621443);
INSERT INTO O_BATTR
	VALUES (2621456,
	2621443);
INSERT INTO O_ATTR
	VALUES (2621456,
	2621443,
	2621455,
	'Name',
	'Full Name: State Name',
	'',
	'Name',
	0,
	524293);
INSERT INTO O_NBATTR
	VALUES (2621457,
	2621443);
INSERT INTO O_BATTR
	VALUES (2621457,
	2621443);
INSERT INTO O_ATTR
	VALUES (2621457,
	2621443,
	2621456,
	'Numb',
	'Full Name: State Number',
	'',
	'Numb',
	0,
	524291);
INSERT INTO O_NBATTR
	VALUES (2621458,
	2621443);
INSERT INTO O_BATTR
	VALUES (2621458,
	2621443);
INSERT INTO O_ATTR
	VALUES (2621458,
	2621443,
	2621457,
	'Final',
	'Full Name: Final State Indicator
Description: Indicates whether or not the state is a final state
Data Domain: 0 = not a final state, 1 = final state',
	'',
	'Final',
	0,
	524291);
INSERT INTO O_ID
	VALUES (0,
	2621443);
INSERT INTO O_OIDA
	VALUES (2621454,
	2621443,
	0);
INSERT INTO O_RTIDA
	VALUES (2621454,
	2621443,
	0,
	2621449,
	2621463);
INSERT INTO O_RTIDA
	VALUES (2621454,
	2621443,
	0,
	2621464,
	2621498);
INSERT INTO O_RTIDA
	VALUES (2621454,
	2621443,
	0,
	2621445,
	2621454);
INSERT INTO O_OIDA
	VALUES (2621453,
	2621443,
	0);
INSERT INTO O_RTIDA
	VALUES (2621453,
	2621443,
	0,
	2621445,
	2621454);
INSERT INTO O_RTIDA
	VALUES (2621453,
	2621443,
	0,
	2621449,
	2621463);
INSERT INTO O_RTIDA
	VALUES (2621453,
	2621443,
	0,
	2621464,
	2621498);
INSERT INTO O_ID
	VALUES (1,
	2621443);
INSERT INTO O_OIDA
	VALUES (2621455,
	2621443,
	1);
INSERT INTO O_RTIDA
	VALUES (2621455,
	2621443,
	1,
	2621446,
	2621456);
INSERT INTO O_OIDA
	VALUES (2621453,
	2621443,
	1);
INSERT INTO O_RTIDA
	VALUES (2621453,
	2621443,
	1,
	2621446,
	2621456);
INSERT INTO O_OIDA
	VALUES (2621454,
	2621443,
	1);
INSERT INTO O_RTIDA
	VALUES (2621454,
	2621443,
	1,
	2621446,
	2621456);
INSERT INTO O_OBJ
	VALUES (2621444,
	'State Event Matrix Entry',
	504,
	'SM_SEME',
	'This object represents an entry in the state event matrix (SEM).  A state event matrix entry describes what happens in each state (SM_STATE) for each event (SM_EVT).  The SEM can be thought of as a two-dimensional array where the states of a state model  are represented by rows and events are represented by columns.  An entry consists of one of the following: transition to another state, event ignored, event cant happen.',
	2621445);
INSERT INTO O_REF
	VALUES (2621444,
	2621443,
	0,
	2621453,
	2621464,
	2621500,
	2621498,
	2621459,
	2621486,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (2621459,
	2621444,
	2621453,
	2621443,
	1);
INSERT INTO O_ATTR
	VALUES (2621459,
	2621444,
	0,
	'SMstt_ID',
	'',
	'',
	'SMstt_ID',
	0,
	524296);
INSERT INTO O_REF
	VALUES (2621444,
	2621463,
	0,
	2621523,
	2621464,
	2621500,
	2621499,
	2621460,
	2621487,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (2621460,
	2621444,
	2621444,
	2621442,
	1);
INSERT INTO O_ATTR
	VALUES (2621460,
	2621444,
	2621459,
	'SMevt_ID',
	'',
	'',
	'SMevt_ID',
	0,
	524296);
INSERT INTO O_REF
	VALUES (2621444,
	2621443,
	0,
	2621454,
	2621464,
	2621500,
	2621498,
	2621461,
	2621488,
	0,
	0,
	'');
INSERT INTO O_REF
	VALUES (2621444,
	2621463,
	0,
	2621524,
	2621464,
	2621500,
	2621499,
	2621461,
	2621489,
	2621488,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (2621461,
	2621444,
	2621441,
	2621441,
	1);
INSERT INTO O_ATTR
	VALUES (2621461,
	2621444,
	2621460,
	'SM_ID',
	'',
	'',
	'SM_ID',
	0,
	524296);
INSERT INTO O_REF
	VALUES (2621444,
	2621463,
	0,
	2621525,
	2621464,
	2621500,
	2621499,
	2621462,
	2621490,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (2621462,
	2621444,
	2621514,
	2621460,
	1);
INSERT INTO O_ATTR
	VALUES (2621462,
	2621444,
	2621461,
	'SMspd_ID',
	'',
	'',
	'SMspd_ID',
	0,
	524296);
INSERT INTO O_ID
	VALUES (0,
	2621444);
INSERT INTO O_OIDA
	VALUES (2621461,
	2621444,
	0);
INSERT INTO O_RTIDA
	VALUES (2621461,
	2621444,
	0,
	2621443,
	2621445);
INSERT INTO O_OIDA
	VALUES (2621460,
	2621444,
	0);
INSERT INTO O_RTIDA
	VALUES (2621460,
	2621444,
	0,
	2621443,
	2621445);
INSERT INTO O_OIDA
	VALUES (2621459,
	2621444,
	0);
INSERT INTO O_RTIDA
	VALUES (2621459,
	2621444,
	0,
	2621443,
	2621445);
INSERT INTO O_OIDA
	VALUES (2621462,
	2621444,
	0);
INSERT INTO O_RTIDA
	VALUES (2621462,
	2621444,
	0,
	2621443,
	2621445);
INSERT INTO O_OBJ
	VALUES (2621445,
	'Event Ignored',
	506,
	'SM_EIGN',
	'An event ignored is an entry in the state event matrix (SEM) specifying that the event (SM_EVT) is ignored when received in the state SM_STATE.  When the analyst asserts that the event is ignored he is saying that the event is expected, but the state model should not transition on the event, but ignore it.  ',
	2621445);
INSERT INTO O_REF
	VALUES (2621445,
	2621444,
	0,
	2621459,
	2621443,
	2621446,
	2621445,
	2621463,
	2621443,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (2621463,
	2621445,
	2621453,
	2621443,
	1);
INSERT INTO O_ATTR
	VALUES (2621463,
	2621445,
	0,
	'SMstt_ID',
	'',
	'',
	'SMstt_ID',
	0,
	524296);
INSERT INTO O_REF
	VALUES (2621445,
	2621444,
	0,
	2621460,
	2621443,
	2621446,
	2621445,
	2621464,
	2621444,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (2621464,
	2621445,
	2621444,
	2621442,
	1);
INSERT INTO O_ATTR
	VALUES (2621464,
	2621445,
	2621463,
	'SMevt_ID',
	'',
	'',
	'SMevt_ID',
	0,
	524296);
INSERT INTO O_REF
	VALUES (2621445,
	2621444,
	0,
	2621461,
	2621443,
	2621446,
	2621445,
	2621465,
	2621445,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (2621465,
	2621445,
	2621441,
	2621441,
	1);
INSERT INTO O_ATTR
	VALUES (2621465,
	2621445,
	2621464,
	'SM_ID',
	'',
	'',
	'SM_ID',
	0,
	524296);
INSERT INTO O_REF
	VALUES (2621445,
	2621444,
	0,
	2621462,
	2621443,
	2621446,
	2621445,
	2621466,
	2621446,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (2621466,
	2621445,
	2621514,
	2621460,
	1);
INSERT INTO O_ATTR
	VALUES (2621466,
	2621445,
	2621465,
	'SMspd_ID',
	'',
	'',
	'SMspd_ID',
	0,
	524296);
INSERT INTO O_NBATTR
	VALUES (2621467,
	2621445);
INSERT INTO O_BATTR
	VALUES (2621467,
	2621445);
INSERT INTO O_ATTR
	VALUES (2621467,
	2621445,
	2621466,
	'Descrip',
	'Full Name: Description
Description: An explanation of why the event can be ignored if received in this state.',
	'',
	'Descrip',
	0,
	524293);
INSERT INTO O_ID
	VALUES (0,
	2621445);
INSERT INTO O_OIDA
	VALUES (2621463,
	2621445,
	0);
INSERT INTO O_OIDA
	VALUES (2621466,
	2621445,
	0);
INSERT INTO O_OIDA
	VALUES (2621465,
	2621445,
	0);
INSERT INTO O_OIDA
	VALUES (2621464,
	2621445,
	0);
INSERT INTO O_OBJ
	VALUES (2621446,
	'New State Transition',
	505,
	'SM_NSTXN',
	'A new state transition is an entry in the state event matrix (SEM) that  represents a transition from one state to another upon the receipt of an event while in a particular state.',
	2621445);
INSERT INTO O_REF
	VALUES (2621446,
	2621450,
	0,
	2621486,
	2621444,
	2621452,
	2621449,
	2621468,
	2621491,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (2621468,
	2621446,
	2621486,
	2621450,
	1);
INSERT INTO O_ATTR
	VALUES (2621468,
	2621446,
	0,
	'Trans_ID',
	'',
	'',
	'Trans_ID',
	0,
	524296);
INSERT INTO O_REF
	VALUES (2621446,
	2621444,
	0,
	2621461,
	2621443,
	2621448,
	2621445,
	2621469,
	2621447,
	0,
	0,
	'');
INSERT INTO O_REF
	VALUES (2621446,
	2621450,
	0,
	2621487,
	2621444,
	2621452,
	2621449,
	2621469,
	2621492,
	2621447,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (2621469,
	2621446,
	2621441,
	2621441,
	1);
INSERT INTO O_ATTR
	VALUES (2621469,
	2621446,
	2621468,
	'SM_ID',
	'',
	'',
	'SM_ID',
	0,
	524296);
INSERT INTO O_REF
	VALUES (2621446,
	2621444,
	0,
	2621459,
	2621443,
	2621448,
	2621445,
	2621470,
	2621448,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (2621470,
	2621446,
	2621453,
	2621443,
	1);
INSERT INTO O_ATTR
	VALUES (2621470,
	2621446,
	2621469,
	'SMstt_ID',
	'',
	'',
	'SMstt_ID',
	0,
	524296);
INSERT INTO O_REF
	VALUES (2621446,
	2621444,
	0,
	2621460,
	2621443,
	2621448,
	2621445,
	2621471,
	2621449,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (2621471,
	2621446,
	2621444,
	2621442,
	1);
INSERT INTO O_ATTR
	VALUES (2621471,
	2621446,
	2621470,
	'SMevt_ID',
	'',
	'',
	'SMevt_ID',
	0,
	524296);
INSERT INTO O_REF
	VALUES (2621446,
	2621444,
	0,
	2621462,
	2621443,
	2621448,
	2621445,
	2621472,
	2621450,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (2621472,
	2621446,
	2621514,
	2621460,
	1);
INSERT INTO O_ATTR
	VALUES (2621472,
	2621446,
	2621471,
	'SMspd_ID',
	'',
	'',
	'SMspd_ID',
	0,
	524296);
INSERT INTO O_ID
	VALUES (0,
	2621446);
INSERT INTO O_OIDA
	VALUES (2621469,
	2621446,
	0);
INSERT INTO O_OIDA
	VALUES (2621471,
	2621446,
	0);
INSERT INTO O_OIDA
	VALUES (2621472,
	2621446,
	0);
INSERT INTO O_OIDA
	VALUES (2621470,
	2621446,
	0);
INSERT INTO O_ID
	VALUES (1,
	2621446);
INSERT INTO O_OIDA
	VALUES (2621468,
	2621446,
	1);
INSERT INTO O_OIDA
	VALUES (2621469,
	2621446,
	1);
INSERT INTO O_OBJ
	VALUES (2621447,
	'Cant Happen',
	507,
	'SM_CH',
	'A cant happen is an entry in the state event matrix (SEM) specifying that the event (SM_EVT) should never happen under normal circumstances when received in the state SM_STATE.  When the analyst asserts that the event cant happen in the state he is saying that the event is not expected and an error should be flagged.  ',
	2621445);
INSERT INTO O_REF
	VALUES (2621447,
	2621444,
	0,
	2621459,
	2621443,
	2621447,
	2621445,
	2621473,
	2621451,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (2621473,
	2621447,
	2621453,
	2621443,
	1);
INSERT INTO O_ATTR
	VALUES (2621473,
	2621447,
	0,
	'SMstt_ID',
	'',
	'',
	'SMstt_ID',
	0,
	524296);
INSERT INTO O_REF
	VALUES (2621447,
	2621444,
	0,
	2621460,
	2621443,
	2621447,
	2621445,
	2621474,
	2621452,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (2621474,
	2621447,
	2621444,
	2621442,
	1);
INSERT INTO O_ATTR
	VALUES (2621474,
	2621447,
	2621473,
	'SMevt_ID',
	'',
	'',
	'SMevt_ID',
	0,
	524296);
INSERT INTO O_REF
	VALUES (2621447,
	2621444,
	0,
	2621461,
	2621443,
	2621447,
	2621445,
	2621475,
	2621453,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (2621475,
	2621447,
	2621441,
	2621441,
	1);
INSERT INTO O_ATTR
	VALUES (2621475,
	2621447,
	2621474,
	'SM_ID',
	'',
	'',
	'SM_ID',
	0,
	524296);
INSERT INTO O_REF
	VALUES (2621447,
	2621444,
	0,
	2621462,
	2621443,
	2621447,
	2621445,
	2621476,
	2621454,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (2621476,
	2621447,
	2621514,
	2621460,
	1);
INSERT INTO O_ATTR
	VALUES (2621476,
	2621447,
	2621475,
	'SMspd_ID',
	'',
	'',
	'SMspd_ID',
	0,
	524296);
INSERT INTO O_NBATTR
	VALUES (2621477,
	2621447);
INSERT INTO O_BATTR
	VALUES (2621477,
	2621447);
INSERT INTO O_ATTR
	VALUES (2621477,
	2621447,
	2621476,
	'Descrip',
	'Full Name: Description
Description: An explanation of why the event cant happen while the instance is in this state.',
	'',
	'Descrip',
	0,
	524293);
INSERT INTO O_ID
	VALUES (0,
	2621447);
INSERT INTO O_OIDA
	VALUES (2621473,
	2621447,
	0);
INSERT INTO O_OIDA
	VALUES (2621476,
	2621447,
	0);
INSERT INTO O_OIDA
	VALUES (2621474,
	2621447,
	0);
INSERT INTO O_OIDA
	VALUES (2621475,
	2621447,
	0);
INSERT INTO O_OBJ
	VALUES (2621448,
	'Creation Transition',
	510,
	'SM_CRTXN',
	'A creation transition is a transition into a state from no other state.  ',
	2621445);
INSERT INTO O_REF
	VALUES (2621448,
	2621450,
	0,
	2621486,
	2621444,
	2621451,
	2621449,
	2621478,
	2621493,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (2621478,
	2621448,
	2621486,
	2621450,
	1);
INSERT INTO O_ATTR
	VALUES (2621478,
	2621448,
	0,
	'Trans_ID',
	'',
	'',
	'Trans_ID',
	0,
	524296);
INSERT INTO O_REF
	VALUES (2621448,
	2621450,
	0,
	2621487,
	2621444,
	2621451,
	2621449,
	2621479,
	2621494,
	0,
	0,
	'');
INSERT INTO O_REF
	VALUES (2621448,
	2621465,
	0,
	2621534,
	2621466,
	2621504,
	2621505,
	2621479,
	2621495,
	2621494,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (2621479,
	2621448,
	2621441,
	2621441,
	1);
INSERT INTO O_ATTR
	VALUES (2621479,
	2621448,
	2621478,
	'SM_ID',
	'',
	'',
	'SM_ID',
	0,
	524296);
INSERT INTO O_REF
	VALUES (2621448,
	2621465,
	0,
	2621533,
	2621466,
	2621504,
	2621505,
	2621480,
	2621496,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (2621480,
	2621448,
	2621444,
	2621442,
	1);
INSERT INTO O_ATTR
	VALUES (2621480,
	2621448,
	2621479,
	'SMevt_ID',
	'',
	'',
	'SMevt_ID',
	0,
	524296);
INSERT INTO O_NBATTR
	VALUES (2621481,
	2621448);
INSERT INTO O_BATTR
	VALUES (2621481,
	2621448);
INSERT INTO O_ATTR
	VALUES (2621481,
	2621448,
	2621480,
	'SMspd_ID',
	'For backwards compatibility with pre-5.1 model databases.',
	'',
	'SMspd_ID',
	0,
	524294);
INSERT INTO O_ID
	VALUES (1,
	2621448);
INSERT INTO O_OIDA
	VALUES (2621479,
	2621448,
	1);
INSERT INTO O_OIDA
	VALUES (2621478,
	2621448,
	1);
INSERT INTO O_OBJ
	VALUES (2621449,
	'No Event Transition',
	509,
	'SM_NETXN',
	'A no event transition is a transition between to states to which no event is currently assigned. ',
	2621445);
INSERT INTO O_REF
	VALUES (2621449,
	2621450,
	0,
	2621486,
	2621444,
	2621450,
	2621449,
	2621482,
	2621497,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (2621482,
	2621449,
	2621486,
	2621450,
	1);
INSERT INTO O_ATTR
	VALUES (2621482,
	2621449,
	0,
	'Trans_ID',
	'',
	'',
	'Trans_ID',
	0,
	524296);
INSERT INTO O_REF
	VALUES (2621449,
	2621443,
	0,
	2621454,
	2621445,
	2621453,
	2621454,
	2621483,
	2621455,
	0,
	0,
	'');
INSERT INTO O_REF
	VALUES (2621449,
	2621450,
	0,
	2621487,
	2621444,
	2621450,
	2621449,
	2621483,
	2621498,
	2621455,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (2621483,
	2621449,
	2621441,
	2621441,
	1);
INSERT INTO O_ATTR
	VALUES (2621483,
	2621449,
	2621482,
	'SM_ID',
	'',
	'',
	'SM_ID',
	0,
	524296);
INSERT INTO O_REF
	VALUES (2621449,
	2621443,
	0,
	2621453,
	2621445,
	2621453,
	2621454,
	2621484,
	2621456,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (2621484,
	2621449,
	2621453,
	2621443,
	1);
INSERT INTO O_ATTR
	VALUES (2621484,
	2621449,
	2621483,
	'SMstt_ID',
	'',
	'',
	'SMstt_ID',
	0,
	524296);
INSERT INTO O_NBATTR
	VALUES (2621485,
	2621449);
INSERT INTO O_BATTR
	VALUES (2621485,
	2621449);
INSERT INTO O_ATTR
	VALUES (2621485,
	2621449,
	2621484,
	'SMspd_ID',
	'For backwards compatibility with pre-5.1 model databases.',
	'',
	'SMspd_ID',
	0,
	524294);
INSERT INTO O_ID
	VALUES (1,
	2621449);
INSERT INTO O_OIDA
	VALUES (2621482,
	2621449,
	1);
INSERT INTO O_OIDA
	VALUES (2621483,
	2621449,
	1);
INSERT INTO O_OBJ
	VALUES (2621450,
	'Transition',
	508,
	'SM_TXN',
	'A transition represents a change in state of an instance.  It is represented by a line between to states (SM_NETXN and SM_NSTXN), or a line into a single state (SM_CRTXN).  Transitions are labeled with a single event (SM_EVT).',
	2621445);
INSERT INTO O_NBATTR
	VALUES (2621486,
	2621450);
INSERT INTO O_BATTR
	VALUES (2621486,
	2621450);
INSERT INTO O_ATTR
	VALUES (2621486,
	2621450,
	0,
	'Trans_ID',
	'Full Name: Transition Identifier',
	'',
	'Trans_ID',
	0,
	524294);
INSERT INTO O_REF
	VALUES (2621450,
	2621441,
	0,
	2621441,
	2621447,
	2621457,
	2621458,
	2621487,
	2621457,
	0,
	0,
	'');
INSERT INTO O_REF
	VALUES (2621450,
	2621443,
	1,
	2621454,
	2621446,
	2621455,
	2621456,
	2621487,
	2621458,
	2621457,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (2621487,
	2621450,
	2621441,
	2621441,
	1);
INSERT INTO O_ATTR
	VALUES (2621487,
	2621450,
	2621486,
	'SM_ID',
	'',
	'',
	'SM_ID',
	0,
	524296);
INSERT INTO O_REF
	VALUES (2621450,
	2621443,
	1,
	2621453,
	2621446,
	2621455,
	2621456,
	2621488,
	2621459,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (2621488,
	2621450,
	2621453,
	2621443,
	1);
INSERT INTO O_ATTR
	VALUES (2621488,
	2621450,
	2621487,
	'SMstt_ID',
	'',
	'',
	'SMstt_ID',
	0,
	524296);
INSERT INTO O_REF
	VALUES (2621450,
	2621443,
	1,
	2621455,
	2621446,
	2621455,
	2621456,
	2621489,
	2621460,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (2621489,
	2621450,
	2621514,
	2621460,
	1);
INSERT INTO O_ATTR
	VALUES (2621489,
	2621450,
	2621488,
	'SMspd_ID',
	'',
	'',
	'SMspd_ID',
	0,
	524296);
INSERT INTO O_ID
	VALUES (0,
	2621450);
INSERT INTO O_OIDA
	VALUES (2621486,
	2621450,
	0);
INSERT INTO O_RTIDA
	VALUES (2621486,
	2621450,
	0,
	2621444,
	2621449);
INSERT INTO O_RTIDA
	VALUES (2621486,
	2621450,
	0,
	2621450,
	2621466);
INSERT INTO O_OIDA
	VALUES (2621487,
	2621450,
	0);
INSERT INTO O_RTIDA
	VALUES (2621487,
	2621450,
	0,
	2621444,
	2621449);
INSERT INTO O_RTIDA
	VALUES (2621487,
	2621450,
	0,
	2621450,
	2621466);
INSERT INTO O_OBJ
	VALUES (2621451,
	'Moore State Model',
	511,
	'SM_MOORE',
	'A Moore state model is one in which actions (SM_ACT) are assigned to states (SM_STATE).  The state action is executed upon entry into the state.',
	2621445);
INSERT INTO O_REF
	VALUES (2621451,
	2621441,
	0,
	2621441,
	2621448,
	2621461,
	2621459,
	2621490,
	2621461,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (2621490,
	2621451,
	2621441,
	2621441,
	1);
INSERT INTO O_ATTR
	VALUES (2621490,
	2621451,
	0,
	'SM_ID',
	'',
	'',
	'SM_ID',
	0,
	524296);
INSERT INTO O_ID
	VALUES (0,
	2621451);
INSERT INTO O_OIDA
	VALUES (2621490,
	2621451,
	0);
INSERT INTO O_RTIDA
	VALUES (2621490,
	2621451,
	0,
	2621449,
	2621462);
INSERT INTO O_OBJ
	VALUES (2621452,
	'Mealy State Model',
	512,
	'SM_MEALY',
	'A Mealy state model is one in which actions (SM_ACT) are assigned to transitions (SM_TXN).  The action is executed during the transition from one state to another.  Support for mealy state models is currently absent from the tool.',
	2621445);
INSERT INTO O_REF
	VALUES (2621452,
	2621441,
	0,
	2621441,
	2621448,
	2621460,
	2621459,
	2621491,
	2621462,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (2621491,
	2621452,
	2621441,
	2621441,
	1);
INSERT INTO O_ATTR
	VALUES (2621491,
	2621452,
	0,
	'SM_ID',
	'',
	'',
	'SM_ID',
	0,
	524296);
INSERT INTO O_ID
	VALUES (0,
	2621452);
INSERT INTO O_OIDA
	VALUES (2621491,
	2621452,
	0);
INSERT INTO O_RTIDA
	VALUES (2621491,
	2621452,
	0,
	2621450,
	2621465);
INSERT INTO O_OBJ
	VALUES (2621453,
	'Moore Action Home',
	513,
	'SM_MOAH',
	'A Moore action home represents the home for a Moore action.',
	2621445);
INSERT INTO O_REF
	VALUES (2621453,
	2621455,
	0,
	2621498,
	2621451,
	2621469,
	2621468,
	2621492,
	2621499,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (2621492,
	2621453,
	2621500,
	2621456,
	1);
INSERT INTO O_ATTR
	VALUES (2621492,
	2621453,
	0,
	'Act_ID',
	'',
	'',
	'Act_ID',
	0,
	524296);
INSERT INTO O_REF
	VALUES (2621453,
	2621455,
	0,
	2621499,
	2621451,
	2621469,
	2621468,
	2621493,
	2621500,
	0,
	0,
	'');
INSERT INTO O_REF
	VALUES (2621453,
	2621451,
	0,
	2621490,
	2621449,
	2621464,
	2621462,
	2621493,
	2621501,
	2621500,
	0,
	'');
INSERT INTO O_REF
	VALUES (2621453,
	2621443,
	0,
	2621454,
	2621449,
	2621464,
	2621463,
	2621493,
	2621502,
	2621501,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (2621493,
	2621453,
	2621441,
	2621441,
	1);
INSERT INTO O_ATTR
	VALUES (2621493,
	2621453,
	2621492,
	'SM_ID',
	'',
	'',
	'SM_ID',
	0,
	524296);
INSERT INTO O_REF
	VALUES (2621453,
	2621443,
	0,
	2621453,
	2621449,
	2621464,
	2621463,
	2621494,
	2621503,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (2621494,
	2621453,
	2621453,
	2621443,
	1);
INSERT INTO O_ATTR
	VALUES (2621494,
	2621453,
	2621493,
	'SMstt_ID',
	'',
	'',
	'SMstt_ID',
	0,
	524296);
INSERT INTO O_ID
	VALUES (0,
	2621453);
INSERT INTO O_OIDA
	VALUES (2621493,
	2621453,
	0);
INSERT INTO O_OIDA
	VALUES (2621494,
	2621453,
	0);
INSERT INTO O_ID
	VALUES (1,
	2621453);
INSERT INTO O_OIDA
	VALUES (2621493,
	2621453,
	1);
INSERT INTO O_OIDA
	VALUES (2621492,
	2621453,
	1);
INSERT INTO O_OBJ
	VALUES (2621454,
	'Mealy Action Home',
	514,
	'SM_MEAH',
	'A Mealy action home represents the home for a Mealy action.',
	2621445);
INSERT INTO O_REF
	VALUES (2621454,
	2621455,
	0,
	2621498,
	2621451,
	2621470,
	2621468,
	2621495,
	2621504,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (2621495,
	2621454,
	2621500,
	2621456,
	1);
INSERT INTO O_ATTR
	VALUES (2621495,
	2621454,
	0,
	'Act_ID',
	'',
	'',
	'Act_ID',
	0,
	524296);
INSERT INTO O_REF
	VALUES (2621454,
	2621455,
	0,
	2621499,
	2621451,
	2621470,
	2621468,
	2621496,
	2621505,
	0,
	0,
	'');
INSERT INTO O_REF
	VALUES (2621454,
	2621452,
	0,
	2621491,
	2621450,
	2621467,
	2621465,
	2621496,
	2621506,
	2621505,
	0,
	'');
INSERT INTO O_REF
	VALUES (2621454,
	2621450,
	0,
	2621487,
	2621450,
	2621467,
	2621466,
	2621496,
	2621507,
	2621506,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (2621496,
	2621454,
	2621441,
	2621441,
	1);
INSERT INTO O_ATTR
	VALUES (2621496,
	2621454,
	2621495,
	'SM_ID',
	'',
	'',
	'SM_ID',
	0,
	524296);
INSERT INTO O_REF
	VALUES (2621454,
	2621450,
	0,
	2621486,
	2621450,
	2621467,
	2621466,
	2621497,
	2621508,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (2621497,
	2621454,
	2621486,
	2621450,
	1);
INSERT INTO O_ATTR
	VALUES (2621497,
	2621454,
	2621496,
	'Trans_ID',
	'',
	'',
	'Trans_ID',
	0,
	524296);
INSERT INTO O_ID
	VALUES (0,
	2621454);
INSERT INTO O_OIDA
	VALUES (2621497,
	2621454,
	0);
INSERT INTO O_OIDA
	VALUES (2621496,
	2621454,
	0);
INSERT INTO O_ID
	VALUES (1,
	2621454);
INSERT INTO O_OIDA
	VALUES (2621495,
	2621454,
	1);
INSERT INTO O_OIDA
	VALUES (2621496,
	2621454,
	1);
INSERT INTO O_OBJ
	VALUES (2621455,
	'Action Home',
	515,
	'SM_AH',
	'An action (SM_ACT) resides in an action home, either a Moore action home (SM_MOAH) or a Mealy action home (SM_MEAH).',
	2621445);
INSERT INTO O_REF
	VALUES (2621455,
	2621456,
	0,
	2621500,
	2621452,
	2621471,
	2621472,
	2621498,
	2621509,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (2621498,
	2621455,
	2621500,
	2621456,
	1);
INSERT INTO O_ATTR
	VALUES (2621498,
	2621455,
	0,
	'Act_ID',
	'',
	'',
	'Act_ID',
	0,
	524296);
INSERT INTO O_REF
	VALUES (2621455,
	2621456,
	0,
	2621501,
	2621452,
	2621471,
	2621472,
	2621499,
	2621510,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (2621499,
	2621455,
	2621441,
	2621441,
	1);
INSERT INTO O_ATTR
	VALUES (2621499,
	2621455,
	2621498,
	'SM_ID',
	'',
	'',
	'SM_ID',
	0,
	524296);
INSERT INTO O_ID
	VALUES (0,
	2621455);
INSERT INTO O_OIDA
	VALUES (2621498,
	2621455,
	0);
INSERT INTO O_RTIDA
	VALUES (2621498,
	2621455,
	0,
	2621451,
	2621468);
INSERT INTO O_OIDA
	VALUES (2621499,
	2621455,
	0);
INSERT INTO O_RTIDA
	VALUES (2621499,
	2621455,
	0,
	2621451,
	2621468);
INSERT INTO O_OBJ
	VALUES (2621456,
	'Action',
	516,
	'SM_ACT',
	'An action defines the action language that gets executed upon arrival into the state (SM_STATE) that is related to the action.  An action may have both action language (SM_ACT.Action_Semantics)  and a description (SM_ACT.Descrip).',
	2621445);
INSERT INTO O_NBATTR
	VALUES (2621500,
	2621456);
INSERT INTO O_BATTR
	VALUES (2621500,
	2621456);
INSERT INTO O_ATTR
	VALUES (2621500,
	2621456,
	0,
	'Act_ID',
	'Full Name: Action Identifier',
	'',
	'Act_ID',
	0,
	524294);
INSERT INTO O_REF
	VALUES (2621456,
	2621441,
	0,
	2621441,
	2621453,
	2621473,
	2621474,
	2621501,
	2621463,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (2621501,
	2621456,
	2621441,
	2621441,
	1);
INSERT INTO O_ATTR
	VALUES (2621501,
	2621456,
	2621500,
	'SM_ID',
	'',
	'',
	'SM_ID',
	0,
	524296);
INSERT INTO O_NBATTR
	VALUES (2621502,
	2621456);
INSERT INTO O_BATTR
	VALUES (2621502,
	2621456);
INSERT INTO O_ATTR
	VALUES (2621502,
	2621456,
	2621501,
	'Suc_Pars',
	'Full Name: Successful Parse Indicator
Description: Indicates the status of the parse for the state action specification in the attribute Action_Semantics
Data Domain: 0=not parsed, 1 = parse successful, 2 = parse unsuccessful, 3 = parse on apply set but never been parsed',
	'',
	'Suc_Pars',
	0,
	524291);
INSERT INTO O_NBATTR
	VALUES (2621503,
	2621456);
INSERT INTO O_BATTR
	VALUES (2621503,
	2621456);
INSERT INTO O_ATTR
	VALUES (2621503,
	2621456,
	2621502,
	'Action_Semantics',
	'Full Name: Action Semantics Field
Description: Action Semantics for the state action',
	'',
	'Action_Semantics',
	0,
	524293);
INSERT INTO O_NBATTR
	VALUES (2621504,
	2621456);
INSERT INTO O_BATTR
	VALUES (2621504,
	2621456);
INSERT INTO O_ATTR
	VALUES (2621504,
	2621456,
	2621503,
	'Descrip',
	'Full Name: Description
Description: Optional description for the state action.',
	'',
	'Descrip',
	0,
	524293);
INSERT INTO O_ID
	VALUES (0,
	2621456);
INSERT INTO O_OIDA
	VALUES (2621500,
	2621456,
	0);
INSERT INTO O_RTIDA
	VALUES (2621500,
	2621456,
	0,
	2621452,
	2621472);
INSERT INTO O_OIDA
	VALUES (2621501,
	2621456,
	0);
INSERT INTO O_RTIDA
	VALUES (2621501,
	2621456,
	0,
	2621452,
	2621472);
INSERT INTO O_OBJ
	VALUES (2621457,
	'Instance State Model',
	520,
	'SM_ISM',
	'An instance state model is a state model for an instance of an object.  Each instance of an object can be thought of as executing its own private version of the state model.  This is in contrast to an assigner state model (SM_ASM) which is a state model for the entire object.',
	2621445);
INSERT INTO O_REF
	VALUES (2621457,
	2621441,
	0,
	2621441,
	2621454,
	2621476,
	2621475,
	2621505,
	2621464,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (2621505,
	2621457,
	2621441,
	2621441,
	1);
INSERT INTO O_ATTR
	VALUES (2621505,
	2621457,
	0,
	'SM_ID',
	'',
	'',
	'SM_ID',
	0,
	524296);
INSERT INTO O_REF
	VALUES (2621457,
	3145732,
	0,
	3145751,
	2621455,
	2621478,
	2621479,
	2621506,
	2621465,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (2621506,
	2621457,
	3145751,
	3145732,
	1);
INSERT INTO O_ATTR
	VALUES (2621506,
	2621457,
	2621505,
	'Obj_ID',
	'',
	'',
	'Obj_ID',
	0,
	524296);
INSERT INTO O_ID
	VALUES (0,
	2621457);
INSERT INTO O_OIDA
	VALUES (2621505,
	2621457,
	0);
INSERT INTO O_OBJ
	VALUES (2621458,
	'Assigner State Model',
	521,
	'SM_ASM',
	'An assigner state model is a state model for an object.  This is in contrast to an instance state model (SM_ISM) which is a state model for an instance.',
	2621445);
INSERT INTO O_REF
	VALUES (2621458,
	2621441,
	0,
	2621441,
	2621454,
	2621477,
	2621475,
	2621507,
	2621466,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (2621507,
	2621458,
	2621441,
	2621441,
	1);
INSERT INTO O_ATTR
	VALUES (2621507,
	2621458,
	0,
	'SM_ID',
	'',
	'',
	'SM_ID',
	0,
	524296);
INSERT INTO O_REF
	VALUES (2621458,
	3145732,
	0,
	3145751,
	2621456,
	2621480,
	2621481,
	2621508,
	2621467,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (2621508,
	2621458,
	3145751,
	3145732,
	1);
INSERT INTO O_ATTR
	VALUES (2621508,
	2621458,
	2621507,
	'Obj_ID',
	'',
	'',
	'Obj_ID',
	0,
	524296);
INSERT INTO O_ID
	VALUES (0,
	2621458);
INSERT INTO O_OIDA
	VALUES (2621507,
	2621458,
	0);
INSERT INTO O_OBJ
	VALUES (2621459,
	'State Model Event Data Item',
	517,
	'SM_EVTDI',
	'Each state model (SM_SM) has a pool of event data items that can be used as supplemental data (SM_SUPDT) for the events (SM_EVT) of the state model.  When an event data item is used as supplemental data for an event an instance of SM_SDI is created.',
	2621445);
INSERT INTO O_NBATTR
	VALUES (2621509,
	2621459);
INSERT INTO O_BATTR
	VALUES (2621509,
	2621459);
INSERT INTO O_ATTR
	VALUES (2621509,
	2621459,
	0,
	'SMedi_ID',
	'Full Name: Event Data Item Identifier',
	'',
	'SMedi_ID',
	0,
	524294);
INSERT INTO O_REF
	VALUES (2621459,
	2621441,
	0,
	2621441,
	2621457,
	2621482,
	2621483,
	2621510,
	2621468,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (2621510,
	2621459,
	2621441,
	2621441,
	1);
INSERT INTO O_ATTR
	VALUES (2621510,
	2621459,
	2621509,
	'SM_ID',
	'',
	'',
	'SM_ID',
	0,
	524296);
INSERT INTO O_NBATTR
	VALUES (2621511,
	2621459);
INSERT INTO O_BATTR
	VALUES (2621511,
	2621459);
INSERT INTO O_ATTR
	VALUES (2621511,
	2621459,
	2621510,
	'Name',
	'Full Name: Event Data Item Name',
	'',
	'Name',
	0,
	524293);
INSERT INTO O_NBATTR
	VALUES (2621512,
	2621459);
INSERT INTO O_BATTR
	VALUES (2621512,
	2621459);
INSERT INTO O_ATTR
	VALUES (2621512,
	2621459,
	2621511,
	'Descrip',
	'Full Name: Event Data Item Description',
	'',
	'Descrip',
	0,
	524293);
INSERT INTO O_REF
	VALUES (2621459,
	1048584,
	0,
	1048616,
	2621462,
	2621494,
	2621493,
	2621513,
	2621511,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (2621513,
	2621459,
	1048616,
	1048584,
	1);
INSERT INTO O_ATTR
	VALUES (2621513,
	2621459,
	2621512,
	'DT_ID',
	'',
	'',
	'DT_ID',
	0,
	524296);
INSERT INTO O_ID
	VALUES (0,
	2621459);
INSERT INTO O_OIDA
	VALUES (2621509,
	2621459,
	0);
INSERT INTO O_RTIDA
	VALUES (2621509,
	2621459,
	0,
	2621460,
	2621489);
INSERT INTO O_OIDA
	VALUES (2621510,
	2621459,
	0);
INSERT INTO O_RTIDA
	VALUES (2621510,
	2621459,
	0,
	2621460,
	2621489);
INSERT INTO O_OBJ
	VALUES (2621460,
	'Event Supplemental Data',
	518,
	'SM_SUPDT',
	'Each event (SM_EVT) may carry supplemental event data defined by state model event data items (SM_EVTDI).  The supplemental data items for the event are defined by instances of supplemental data items (SM_SDI).',
	2621445);
INSERT INTO O_NBATTR
	VALUES (2621514,
	2621460);
INSERT INTO O_BATTR
	VALUES (2621514,
	2621460);
INSERT INTO O_ATTR
	VALUES (2621514,
	2621460,
	0,
	'SMspd_ID',
	'Full Name: Event Supplemental Data Identifier',
	'',
	'SMspd_ID',
	0,
	524294);
INSERT INTO O_REF
	VALUES (2621460,
	2621441,
	0,
	2621441,
	2621461,
	2621491,
	2621492,
	2621515,
	2621469,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (2621515,
	2621460,
	2621441,
	2621441,
	1);
INSERT INTO O_ATTR
	VALUES (2621515,
	2621460,
	2621514,
	'SM_ID',
	'',
	'',
	'SM_ID',
	0,
	524296);
INSERT INTO O_DBATTR
	VALUES (2621516,
	2621460,
	'select any event related by self->SM_EVT[R520];
select one sevt related by event->SM_SEVT[R525];
if cardinality sevt == 0
  self.Non_Local = false;
else
  select one non_local_event related by sevt->SM_NLEVT[R526];
  self.Non_Local = (cardinality non_local_event != 0);
end if;',
	1);
INSERT INTO O_BATTR
	VALUES (2621516,
	2621460);
INSERT INTO O_ATTR
	VALUES (2621516,
	2621460,
	2621515,
	'Non_Local',
	'Full Name: Non local event indicator
Description: This attribute captures that the event supplemental data is for a non-local event.
',
	'',
	'Non_Local',
	0,
	524290);
INSERT INTO O_ID
	VALUES (0,
	2621460);
INSERT INTO O_OIDA
	VALUES (2621515,
	2621460,
	0);
INSERT INTO O_RTIDA
	VALUES (2621515,
	2621460,
	0,
	2621459,
	2621487);
INSERT INTO O_RTIDA
	VALUES (2621515,
	2621460,
	0,
	2621460,
	2621488);
INSERT INTO O_RTIDA
	VALUES (2621515,
	2621460,
	0,
	2621458,
	2621485);
INSERT INTO O_OIDA
	VALUES (2621514,
	2621460,
	0);
INSERT INTO O_RTIDA
	VALUES (2621514,
	2621460,
	0,
	2621460,
	2621488);
INSERT INTO O_RTIDA
	VALUES (2621514,
	2621460,
	0,
	2621458,
	2621485);
INSERT INTO O_RTIDA
	VALUES (2621514,
	2621460,
	0,
	2621459,
	2621487);
INSERT INTO O_OBJ
	VALUES (2621461,
	'Supplemental Data Items',
	519,
	'SM_SDI',
	'A supplemental data item for the supplemental data (SM_SUPDT) for an event (SM_EVT).',
	2621445);
INSERT INTO O_REF
	VALUES (2621461,
	2621459,
	0,
	2621509,
	2621460,
	2621490,
	2621489,
	2621517,
	2621512,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (2621517,
	2621461,
	2621509,
	2621459,
	1);
INSERT INTO O_ATTR
	VALUES (2621517,
	2621461,
	0,
	'SMedi_ID',
	'',
	'',
	'SMedi_ID',
	0,
	524296);
INSERT INTO O_REF
	VALUES (2621461,
	2621460,
	0,
	2621514,
	2621460,
	2621490,
	2621488,
	2621518,
	2621513,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (2621518,
	2621461,
	2621514,
	2621460,
	1);
INSERT INTO O_ATTR
	VALUES (2621518,
	2621461,
	2621517,
	'SMspd_ID',
	'',
	'',
	'SMspd_ID',
	0,
	524296);
INSERT INTO O_REF
	VALUES (2621461,
	2621459,
	0,
	2621510,
	2621460,
	2621490,
	2621489,
	2621519,
	2621514,
	0,
	0,
	'');
INSERT INTO O_REF
	VALUES (2621461,
	2621460,
	0,
	2621515,
	2621460,
	2621490,
	2621488,
	2621519,
	2621515,
	2621514,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (2621519,
	2621461,
	2621441,
	2621441,
	1);
INSERT INTO O_ATTR
	VALUES (2621519,
	2621461,
	2621518,
	'SM_ID',
	'',
	'',
	'SM_ID',
	0,
	524296);
INSERT INTO O_ID
	VALUES (0,
	2621461);
INSERT INTO O_OIDA
	VALUES (2621519,
	2621461,
	0);
INSERT INTO O_OIDA
	VALUES (2621517,
	2621461,
	0);
INSERT INTO O_OIDA
	VALUES (2621518,
	2621461,
	0);
INSERT INTO O_OBJ
	VALUES (2621462,
	'Polymorphic Event',
	522,
	'SM_PEVT',
	'A polymorphic event (SM_PEVT) is one that is defined in a super-type state model  and used in one or more sub-type state models.  Each polymorphic event has one or more aliases (SM_NLEVT) in one or more sub-types.',
	2621445);
INSERT INTO O_REF
	VALUES (2621462,
	2621442,
	1,
	2621444,
	2621463,
	2621497,
	2621495,
	2621520,
	2621470,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (2621520,
	2621462,
	2621444,
	2621442,
	1);
INSERT INTO O_ATTR
	VALUES (2621520,
	2621462,
	0,
	'SMevt_ID',
	'',
	'',
	'SMevt_ID',
	0,
	524296);
INSERT INTO O_REF
	VALUES (2621462,
	2621442,
	1,
	2621445,
	2621463,
	2621497,
	2621495,
	2621521,
	2621471,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (2621521,
	2621462,
	2621441,
	2621441,
	1);
INSERT INTO O_ATTR
	VALUES (2621521,
	2621462,
	2621520,
	'SM_ID',
	'',
	'',
	'SM_ID',
	0,
	524296);
INSERT INTO O_REF
	VALUES (2621462,
	2621442,
	1,
	2621446,
	2621463,
	2621497,
	2621495,
	2621522,
	2621472,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (2621522,
	2621462,
	2621514,
	2621460,
	1);
INSERT INTO O_ATTR
	VALUES (2621522,
	2621462,
	2621521,
	'SMspd_ID',
	'',
	'',
	'SMspd_ID',
	0,
	524296);
INSERT INTO O_NBATTR
	VALUES (500000,
	2621462);
INSERT INTO O_BATTR
	VALUES (500000,
	2621462);
INSERT INTO O_ATTR
	VALUES (500000,
	2621462,
	2621522,
	'localClassName',
	'User_Visible: false',
	'', 
	'localClassName',
	0,
	524293);
INSERT INTO O_NBATTR
	VALUES (500001,
	2621462);
INSERT INTO O_BATTR
	VALUES (500001,
	2621462);
INSERT INTO O_ATTR
	VALUES (500001,
	2621462,
	500000,
	'localClassKL',
	'User_Visible: false',
	'',
	'localClassKL',
	0,
	524293);
INSERT INTO O_NBATTR
	VALUES (500002,
	2621462);
INSERT INTO O_BATTR
	VALUES (500002,
	2621462);
INSERT INTO O_ATTR
	VALUES (500002,
	2621462,
	500001,
	'localEventMning',
	'User_Visible: false',
	'',
	'localEventMning',
	0,
	524293);
INSERT INTO O_ID
	VALUES (0,
	2621462);
INSERT INTO O_OIDA
	VALUES (2621520,
	2621462,
	0);
INSERT INTO O_RTIDA
	VALUES (2621520,
	2621462,
	0,
	2621467,
	2621506);
INSERT INTO O_OIDA
	VALUES (2621521,
	2621462,
	0);
INSERT INTO O_RTIDA
	VALUES (2621521,
	2621462,
	0,
	2621467,
	2621506);
INSERT INTO O_OBJ
	VALUES (2621463,
	'SEM Event',
	523,
	'SM_SEVT',
	'An SEM (State Event Matrix) event is one that appears in the state event matrix.  Contrast this to a polymorphic event which is defined at the supertype-level but does not appear in the SEM for the super-type.  An SEM event can either be a local event (SM_LEVT), in the case of an event that is defined in the state model for the object, or a non-local event (SM_NLEVT) which is an alias for a polymorphic event (SM_PEVT) defined in the super-type but accessible via the subtype.',
	2621445);
INSERT INTO O_REF
	VALUES (2621463,
	2621442,
	1,
	2621444,
	2621463,
	2621496,
	2621495,
	2621523,
	2621473,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (2621523,
	2621463,
	2621444,
	2621442,
	1);
INSERT INTO O_ATTR
	VALUES (2621523,
	2621463,
	0,
	'SMevt_ID',
	'',
	'',
	'SMevt_ID',
	0,
	524296);
INSERT INTO O_REF
	VALUES (2621463,
	2621442,
	1,
	2621445,
	2621463,
	2621496,
	2621495,
	2621524,
	2621474,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (2621524,
	2621463,
	2621441,
	2621441,
	1);
INSERT INTO O_ATTR
	VALUES (2621524,
	2621463,
	2621523,
	'SM_ID',
	'',
	'',
	'SM_ID',
	0,
	524296);
INSERT INTO O_REF
	VALUES (2621463,
	2621442,
	1,
	2621446,
	2621463,
	2621496,
	2621495,
	2621525,
	2621475,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (2621525,
	2621463,
	2621514,
	2621460,
	1);
INSERT INTO O_ATTR
	VALUES (2621525,
	2621463,
	2621524,
	'SMspd_ID',
	'',
	'',
	'SMspd_ID',
	0,
	524296);
INSERT INTO O_ID
	VALUES (0,
	2621463);
INSERT INTO O_OIDA
	VALUES (2621525,
	2621463,
	0);
INSERT INTO O_RTIDA
	VALUES (2621525,
	2621463,
	0,
	2621465,
	2621501);
INSERT INTO O_RTIDA
	VALUES (2621525,
	2621463,
	0,
	2621464,
	2621499);
INSERT INTO O_OIDA
	VALUES (2621524,
	2621463,
	0);
INSERT INTO O_RTIDA
	VALUES (2621524,
	2621463,
	0,
	2621465,
	2621501);
INSERT INTO O_RTIDA
	VALUES (2621524,
	2621463,
	0,
	2621464,
	2621499);
INSERT INTO O_OIDA
	VALUES (2621523,
	2621463,
	0);
INSERT INTO O_RTIDA
	VALUES (2621523,
	2621463,
	0,
	2621464,
	2621499);
INSERT INTO O_RTIDA
	VALUES (2621523,
	2621463,
	0,
	2621465,
	2621501);
INSERT INTO O_OBJ
	VALUES (2621464,
	'Non Local Event',
	525,
	'SM_NLEVT',
	'A non-local event is one that is defined in another state model.  Non-local events represent entries in SEMs for events that are defined in a super-type but used on transitions in the sub-type.',
	2621445);
INSERT INTO O_REF
	VALUES (2621464,
	2621463,
	0,
	2621523,
	2621465,
	2621502,
	2621501,
	2621526,
	2621476,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (2621526,
	2621464,
	2621444,
	2621442,
	1);
INSERT INTO O_ATTR
	VALUES (2621526,
	2621464,
	0,
	'SMevt_ID',
	'',
	'',
	'SMevt_ID',
	0,
	524296);
INSERT INTO O_REF
	VALUES (2621464,
	2621463,
	0,
	2621524,
	2621465,
	2621502,
	2621501,
	2621527,
	2621477,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (2621527,
	2621464,
	2621441,
	2621441,
	1);
INSERT INTO O_ATTR
	VALUES (2621527,
	2621464,
	2621526,
	'SM_ID',
	'',
	'',
	'SM_ID',
	0,
	524296);
INSERT INTO O_REF
	VALUES (2621464,
	2621463,
	0,
	2621525,
	2621465,
	2621502,
	2621501,
	2621528,
	2621478,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (2621528,
	2621464,
	2621514,
	2621460,
	1);
INSERT INTO O_ATTR
	VALUES (2621528,
	2621464,
	2621527,
	'SMspd_ID',
	'',
	'',
	'SMspd_ID',
	0,
	524296);
INSERT INTO O_REF
	VALUES (2621464,
	2621462,
	0,
	2621520,
	2621467,
	2621507,
	2621506,
	2621529,
	2621516,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (2621529,
	2621464,
	2621444,
	2621442,
	1);
INSERT INTO O_ATTR
	VALUES (2621529,
	2621464,
	2621528,
	'polySMevt_ID',
	'',
	'poly',
	'SMevt_ID',
	1,
	524296);
INSERT INTO O_REF
	VALUES (2621464,
	2621462,
	0,
	2621521,
	2621467,
	2621507,
	2621506,
	2621530,
	2621517,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (2621530,
	2621464,
	2621441,
	2621441,
	1);
INSERT INTO O_ATTR
	VALUES (2621530,
	2621464,
	2621529,
	'polySM_ID',
	'',
	'poly',
	'SM_ID',
	1,
	524296);
INSERT INTO O_NBATTR
	VALUES (2621531,
	2621464);
INSERT INTO O_BATTR
	VALUES (2621531,
	2621464);
INSERT INTO O_ATTR
	VALUES (2621531,
	2621464,
	2621530,
	'polySMspd_ID',
	'For backwards compatibility with pre-5.1 model databases.',
	'',
	'polySMspd_ID',
	0,
	524294);
INSERT INTO O_NBATTR
	VALUES (2621532,
	2621464);
INSERT INTO O_BATTR
	VALUES (2621532,
	2621464);
INSERT INTO O_ATTR
	VALUES (2621532,
	2621464,
	2621531,
	'Local_Meaning',
	'Full Name: Non-Local Event Local Meaning
Description: The local meaning of the non-local event.  The meaning can be overridden in the sup-types state model.',
	'',
	'Local_Meaning',
	0,
	524293);
INSERT INTO O_ID
	VALUES (0,
	2621464);
INSERT INTO O_OIDA
	VALUES (2621527,
	2621464,
	0);
INSERT INTO O_OIDA
	VALUES (2621526,
	2621464,
	0);
INSERT INTO O_OIDA
	VALUES (2621528,
	2621464,
	0);
INSERT INTO O_OBJ
	VALUES (2621465,
	'Local Event',
	524,
	'SM_LEVT',
	'A local event is one that is defined in the state model.  Contrast this to a non-local event which is defined in a different state model (that of the super-type) than the one in which it is used.',
	2621445);
INSERT INTO O_REF
	VALUES (2621465,
	2621463,
	0,
	2621523,
	2621465,
	2621503,
	2621501,
	2621533,
	2621479,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (2621533,
	2621465,
	2621444,
	2621442,
	1);
INSERT INTO O_ATTR
	VALUES (2621533,
	2621465,
	0,
	'SMevt_ID',
	'',
	'',
	'SMevt_ID',
	0,
	524296);
INSERT INTO O_REF
	VALUES (2621465,
	2621463,
	0,
	2621524,
	2621465,
	2621503,
	2621501,
	2621534,
	2621480,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (2621534,
	2621465,
	2621441,
	2621441,
	1);
INSERT INTO O_ATTR
	VALUES (2621534,
	2621465,
	2621533,
	'SM_ID',
	'',
	'',
	'SM_ID',
	0,
	524296);
INSERT INTO O_REF
	VALUES (2621465,
	2621463,
	0,
	2621525,
	2621465,
	2621503,
	2621501,
	2621535,
	2621481,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (2621535,
	2621465,
	2621514,
	2621460,
	1);
INSERT INTO O_ATTR
	VALUES (2621535,
	2621465,
	2621534,
	'SMspd_ID',
	'',
	'',
	'SMspd_ID',
	0,
	524296);
INSERT INTO O_ID
	VALUES (0,
	2621465);
INSERT INTO O_OIDA
	VALUES (2621533,
	2621465,
	0);
INSERT INTO O_RTIDA
	VALUES (2621533,
	2621465,
	0,
	2621466,
	2621505);
INSERT INTO O_OIDA
	VALUES (2621534,
	2621465,
	0);
INSERT INTO O_RTIDA
	VALUES (2621534,
	2621465,
	0,
	2621466,
	2621505);
INSERT INTO O_IOBJ
	VALUES (2621441,
	1048584,
	5,
	2621445,
	'Data Type',
	'S_DT');
INSERT INTO O_IOBJ
	VALUES (2621442,
	3145732,
	5,
	2621445,
	'Object',
	'O_OBJ');
INSERT INTO R_SIMP
	VALUES (2621441);
INSERT INTO R_REL
	VALUES (2621441,
	502,
	'',
	2621445);
INSERT INTO R_FORM
	VALUES (2621442,
	2621441,
	2621441,
	1,
	1,
	'can be communicated to via');
INSERT INTO R_RGO
	VALUES (2621442,
	2621441,
	2621441);
INSERT INTO R_OIR
	VALUES (2621442,
	2621441,
	2621441,
	0);
INSERT INTO R_PART
	VALUES (2621441,
	2621441,
	2621442,
	0,
	0,
	'');
INSERT INTO R_RTO
	VALUES (2621441,
	2621441,
	2621442,
	0);
INSERT INTO R_OIR
	VALUES (2621441,
	2621441,
	2621442,
	0);
INSERT INTO R_SIMP
	VALUES (2621442);
INSERT INTO R_REL
	VALUES (2621442,
	501,
	'',
	2621445);
INSERT INTO R_FORM
	VALUES (2621443,
	2621442,
	2621443,
	1,
	1,
	'is decomposed into');
INSERT INTO R_RGO
	VALUES (2621443,
	2621442,
	2621443);
INSERT INTO R_OIR
	VALUES (2621443,
	2621442,
	2621443,
	0);
INSERT INTO R_PART
	VALUES (2621441,
	2621442,
	2621444,
	0,
	0,
	'');
INSERT INTO R_RTO
	VALUES (2621441,
	2621442,
	2621444,
	0);
INSERT INTO R_OIR
	VALUES (2621441,
	2621442,
	2621444,
	0);
INSERT INTO R_SUBSUP
	VALUES (2621443);
INSERT INTO R_REL
	VALUES (2621443,
	504,
	'',
	2621445);
INSERT INTO R_SUPER
	VALUES (2621444,
	2621443,
	2621445);
INSERT INTO R_RTO
	VALUES (2621444,
	2621443,
	2621445,
	0);
INSERT INTO R_OIR
	VALUES (2621444,
	2621443,
	2621445,
	0);
INSERT INTO R_SUB
	VALUES (2621445,
	2621443,
	2621446);
INSERT INTO R_RGO
	VALUES (2621445,
	2621443,
	2621446);
INSERT INTO R_OIR
	VALUES (2621445,
	2621443,
	2621446,
	0);
INSERT INTO R_SUB
	VALUES (2621447,
	2621443,
	2621447);
INSERT INTO R_RGO
	VALUES (2621447,
	2621443,
	2621447);
INSERT INTO R_OIR
	VALUES (2621447,
	2621443,
	2621447,
	0);
INSERT INTO R_SUB
	VALUES (2621446,
	2621443,
	2621448);
INSERT INTO R_RGO
	VALUES (2621446,
	2621443,
	2621448);
INSERT INTO R_OIR
	VALUES (2621446,
	2621443,
	2621448,
	0);
INSERT INTO R_SUBSUP
	VALUES (2621444);
INSERT INTO R_REL
	VALUES (2621444,
	507,
	'',
	2621445);
INSERT INTO R_SUPER
	VALUES (2621450,
	2621444,
	2621449);
INSERT INTO R_RTO
	VALUES (2621450,
	2621444,
	2621449,
	0);
INSERT INTO R_OIR
	VALUES (2621450,
	2621444,
	2621449,
	0);
INSERT INTO R_SUB
	VALUES (2621449,
	2621444,
	2621450);
INSERT INTO R_RGO
	VALUES (2621449,
	2621444,
	2621450);
INSERT INTO R_OIR
	VALUES (2621449,
	2621444,
	2621450,
	0);
INSERT INTO R_SUB
	VALUES (2621448,
	2621444,
	2621451);
INSERT INTO R_RGO
	VALUES (2621448,
	2621444,
	2621451);
INSERT INTO R_OIR
	VALUES (2621448,
	2621444,
	2621451,
	0);
INSERT INTO R_SUB
	VALUES (2621446,
	2621444,
	2621452);
INSERT INTO R_RGO
	VALUES (2621446,
	2621444,
	2621452);
INSERT INTO R_OIR
	VALUES (2621446,
	2621444,
	2621452,
	0);
INSERT INTO R_SIMP
	VALUES (2621445);
INSERT INTO R_REL
	VALUES (2621445,
	508,
	'',
	2621445);
INSERT INTO R_FORM
	VALUES (2621449,
	2621445,
	2621453,
	1,
	1,
	'is origination of');
INSERT INTO R_RGO
	VALUES (2621449,
	2621445,
	2621453);
INSERT INTO R_OIR
	VALUES (2621449,
	2621445,
	2621453,
	0);
INSERT INTO R_PART
	VALUES (2621443,
	2621445,
	2621454,
	0,
	0,
	'originates from');
INSERT INTO R_RTO
	VALUES (2621443,
	2621445,
	2621454,
	0);
INSERT INTO R_OIR
	VALUES (2621443,
	2621445,
	2621454,
	0);
INSERT INTO R_SIMP
	VALUES (2621446);
INSERT INTO R_REL
	VALUES (2621446,
	506,
	'',
	2621445);
INSERT INTO R_FORM
	VALUES (2621450,
	2621446,
	2621455,
	1,
	1,
	'is destination of');
INSERT INTO R_RGO
	VALUES (2621450,
	2621446,
	2621455);
INSERT INTO R_OIR
	VALUES (2621450,
	2621446,
	2621455,
	0);
INSERT INTO R_PART
	VALUES (2621443,
	2621446,
	2621456,
	0,
	0,
	'is destine to');
INSERT INTO R_RTO
	VALUES (2621443,
	2621446,
	2621456,
	1);
INSERT INTO R_OIR
	VALUES (2621443,
	2621446,
	2621456,
	0);
INSERT INTO R_SIMP
	VALUES (2621447);
INSERT INTO R_REL
	VALUES (2621447,
	505,
	'',
	2621445);
INSERT INTO R_FORM
	VALUES (2621450,
	2621447,
	2621457,
	1,
	1,
	'contains');
INSERT INTO R_RGO
	VALUES (2621450,
	2621447,
	2621457);
INSERT INTO R_OIR
	VALUES (2621450,
	2621447,
	2621457,
	0);
INSERT INTO R_PART
	VALUES (2621441,
	2621447,
	2621458,
	0,
	0,
	'');
INSERT INTO R_RTO
	VALUES (2621441,
	2621447,
	2621458,
	0);
INSERT INTO R_OIR
	VALUES (2621441,
	2621447,
	2621458,
	0);
INSERT INTO R_SUBSUP
	VALUES (2621448);
INSERT INTO R_REL
	VALUES (2621448,
	510,
	'',
	2621445);
INSERT INTO R_SUPER
	VALUES (2621441,
	2621448,
	2621459);
INSERT INTO R_RTO
	VALUES (2621441,
	2621448,
	2621459,
	0);
INSERT INTO R_OIR
	VALUES (2621441,
	2621448,
	2621459,
	0);
INSERT INTO R_SUB
	VALUES (2621452,
	2621448,
	2621460);
INSERT INTO R_RGO
	VALUES (2621452,
	2621448,
	2621460);
INSERT INTO R_OIR
	VALUES (2621452,
	2621448,
	2621460,
	0);
INSERT INTO R_SUB
	VALUES (2621451,
	2621448,
	2621461);
INSERT INTO R_RGO
	VALUES (2621451,
	2621448,
	2621461);
INSERT INTO R_OIR
	VALUES (2621451,
	2621448,
	2621461,
	0);
INSERT INTO R_ASSOC
	VALUES (2621449);
INSERT INTO R_REL
	VALUES (2621449,
	511,
	'',
	2621445);
INSERT INTO R_AONE
	VALUES (2621451,
	2621449,
	2621462,
	0,
	1,
	'');
INSERT INTO R_RTO
	VALUES (2621451,
	2621449,
	2621462,
	0);
INSERT INTO R_OIR
	VALUES (2621451,
	2621449,
	2621462,
	0);
INSERT INTO R_AOTH
	VALUES (2621443,
	2621449,
	2621463,
	1,
	0,
	'');
INSERT INTO R_RTO
	VALUES (2621443,
	2621449,
	2621463,
	0);
INSERT INTO R_OIR
	VALUES (2621443,
	2621449,
	2621463,
	0);
INSERT INTO R_ASSR
	VALUES (2621453,
	2621449,
	2621464,
	0);
INSERT INTO R_RGO
	VALUES (2621453,
	2621449,
	2621464);
INSERT INTO R_OIR
	VALUES (2621453,
	2621449,
	2621464,
	0);
INSERT INTO R_ASSOC
	VALUES (2621450);
INSERT INTO R_REL
	VALUES (2621450,
	512,
	'',
	2621445);
INSERT INTO R_AONE
	VALUES (2621452,
	2621450,
	2621465,
	0,
	1,
	'');
INSERT INTO R_RTO
	VALUES (2621452,
	2621450,
	2621465,
	0);
INSERT INTO R_OIR
	VALUES (2621452,
	2621450,
	2621465,
	0);
INSERT INTO R_AOTH
	VALUES (2621450,
	2621450,
	2621466,
	1,
	0,
	'');
INSERT INTO R_RTO
	VALUES (2621450,
	2621450,
	2621466,
	0);
INSERT INTO R_OIR
	VALUES (2621450,
	2621450,
	2621466,
	0);
INSERT INTO R_ASSR
	VALUES (2621454,
	2621450,
	2621467,
	0);
INSERT INTO R_RGO
	VALUES (2621454,
	2621450,
	2621467);
INSERT INTO R_OIR
	VALUES (2621454,
	2621450,
	2621467,
	0);
INSERT INTO R_SUBSUP
	VALUES (2621451);
INSERT INTO R_REL
	VALUES (2621451,
	513,
	'',
	2621445);
INSERT INTO R_SUPER
	VALUES (2621455,
	2621451,
	2621468);
INSERT INTO R_RTO
	VALUES (2621455,
	2621451,
	2621468,
	0);
INSERT INTO R_OIR
	VALUES (2621455,
	2621451,
	2621468,
	0);
INSERT INTO R_SUB
	VALUES (2621453,
	2621451,
	2621469);
INSERT INTO R_RGO
	VALUES (2621453,
	2621451,
	2621469);
INSERT INTO R_OIR
	VALUES (2621453,
	2621451,
	2621469,
	0);
INSERT INTO R_SUB
	VALUES (2621454,
	2621451,
	2621470);
INSERT INTO R_RGO
	VALUES (2621454,
	2621451,
	2621470);
INSERT INTO R_OIR
	VALUES (2621454,
	2621451,
	2621470,
	0);
INSERT INTO R_SIMP
	VALUES (2621452);
INSERT INTO R_REL
	VALUES (2621452,
	514,
	'',
	2621445);
INSERT INTO R_FORM
	VALUES (2621455,
	2621452,
	2621471,
	0,
	0,
	'resides in');
INSERT INTO R_RGO
	VALUES (2621455,
	2621452,
	2621471);
INSERT INTO R_OIR
	VALUES (2621455,
	2621452,
	2621471,
	0);
INSERT INTO R_PART
	VALUES (2621456,
	2621452,
	2621472,
	0,
	0,
	'houses');
INSERT INTO R_RTO
	VALUES (2621456,
	2621452,
	2621472,
	0);
INSERT INTO R_OIR
	VALUES (2621456,
	2621452,
	2621472,
	0);
INSERT INTO R_SIMP
	VALUES (2621453);
INSERT INTO R_REL
	VALUES (2621453,
	515,
	'',
	2621445);
INSERT INTO R_FORM
	VALUES (2621456,
	2621453,
	2621473,
	1,
	1,
	'contains');
INSERT INTO R_RGO
	VALUES (2621456,
	2621453,
	2621473);
INSERT INTO R_OIR
	VALUES (2621456,
	2621453,
	2621473,
	0);
INSERT INTO R_PART
	VALUES (2621441,
	2621453,
	2621474,
	0,
	0,
	'');
INSERT INTO R_RTO
	VALUES (2621441,
	2621453,
	2621474,
	0);
INSERT INTO R_OIR
	VALUES (2621441,
	2621453,
	2621474,
	0);
INSERT INTO R_SUBSUP
	VALUES (2621454);
INSERT INTO R_REL
	VALUES (2621454,
	517,
	'',
	2621445);
INSERT INTO R_SUPER
	VALUES (2621441,
	2621454,
	2621475);
INSERT INTO R_RTO
	VALUES (2621441,
	2621454,
	2621475,
	0);
INSERT INTO R_OIR
	VALUES (2621441,
	2621454,
	2621475,
	0);
INSERT INTO R_SUB
	VALUES (2621457,
	2621454,
	2621476);
INSERT INTO R_RGO
	VALUES (2621457,
	2621454,
	2621476);
INSERT INTO R_OIR
	VALUES (2621457,
	2621454,
	2621476,
	0);
INSERT INTO R_SUB
	VALUES (2621458,
	2621454,
	2621477);
INSERT INTO R_RGO
	VALUES (2621458,
	2621454,
	2621477);
INSERT INTO R_OIR
	VALUES (2621458,
	2621454,
	2621477,
	0);
INSERT INTO R_SIMP
	VALUES (2621455);
INSERT INTO R_REL
	VALUES (2621455,
	518,
	'',
	2621445);
INSERT INTO R_FORM
	VALUES (2621457,
	2621455,
	2621478,
	0,
	1,
	'');
INSERT INTO R_RGO
	VALUES (2621457,
	2621455,
	2621478);
INSERT INTO R_OIR
	VALUES (2621457,
	2621455,
	2621478,
	0);
INSERT INTO R_PART
	VALUES (3145732,
	2621455,
	2621479,
	0,
	0,
	'');
INSERT INTO R_RTO
	VALUES (3145732,
	2621455,
	2621479,
	0);
INSERT INTO R_OIR
	VALUES (3145732,
	2621455,
	2621479,
	2621442);
INSERT INTO R_SIMP
	VALUES (2621456);
INSERT INTO R_REL
	VALUES (2621456,
	519,
	'',
	2621445);
INSERT INTO R_FORM
	VALUES (2621458,
	2621456,
	2621480,
	0,
	1,
	'');
INSERT INTO R_RGO
	VALUES (2621458,
	2621456,
	2621480);
INSERT INTO R_OIR
	VALUES (2621458,
	2621456,
	2621480,
	0);
INSERT INTO R_PART
	VALUES (3145732,
	2621456,
	2621481,
	0,
	0,
	'');
INSERT INTO R_RTO
	VALUES (3145732,
	2621456,
	2621481,
	0);
INSERT INTO R_OIR
	VALUES (3145732,
	2621456,
	2621481,
	2621442);
INSERT INTO R_SIMP
	VALUES (2621457);
INSERT INTO R_REL
	VALUES (2621457,
	516,
	'',
	2621445);
INSERT INTO R_FORM
	VALUES (2621459,
	2621457,
	2621482,
	1,
	1,
	'can asynchronously communicate via');
INSERT INTO R_RGO
	VALUES (2621459,
	2621457,
	2621482);
INSERT INTO R_OIR
	VALUES (2621459,
	2621457,
	2621482,
	0);
INSERT INTO R_PART
	VALUES (2621441,
	2621457,
	2621483,
	0,
	0,
	'is carried on events into');
INSERT INTO R_RTO
	VALUES (2621441,
	2621457,
	2621483,
	0);
INSERT INTO R_OIR
	VALUES (2621441,
	2621457,
	2621483,
	0);
INSERT INTO R_SIMP
	VALUES (2621458);
INSERT INTO R_REL
	VALUES (2621458,
	521,
	'',
	2621445);
INSERT INTO R_FORM
	VALUES (2621443,
	2621458,
	2621484,
	1,
	1,
	'is delivered by received event to');
INSERT INTO R_RGO
	VALUES (2621443,
	2621458,
	2621484);
INSERT INTO R_OIR
	VALUES (2621443,
	2621458,
	2621484,
	0);
INSERT INTO R_PART
	VALUES (2621460,
	2621458,
	2621485,
	0,
	1,
	'receives asynchronous data via');
INSERT INTO R_RTO
	VALUES (2621460,
	2621458,
	2621485,
	0);
INSERT INTO R_OIR
	VALUES (2621460,
	2621458,
	2621485,
	0);
INSERT INTO R_SIMP
	VALUES (2621459);
INSERT INTO R_REL
	VALUES (2621459,
	520,
	'',
	2621445);
INSERT INTO R_FORM
	VALUES (2621442,
	2621459,
	2621486,
	1,
	0,
	'defines signature of');
INSERT INTO R_RGO
	VALUES (2621442,
	2621459,
	2621486);
INSERT INTO R_OIR
	VALUES (2621442,
	2621459,
	2621486,
	0);
INSERT INTO R_PART
	VALUES (2621460,
	2621459,
	2621487,
	0,
	0,
	'carries');
INSERT INTO R_RTO
	VALUES (2621460,
	2621459,
	2621487,
	0);
INSERT INTO R_OIR
	VALUES (2621460,
	2621459,
	2621487,
	0);
INSERT INTO R_ASSOC
	VALUES (2621460);
INSERT INTO R_REL
	VALUES (2621460,
	522,
	'',
	2621445);
INSERT INTO R_AONE
	VALUES (2621460,
	2621460,
	2621488,
	1,
	1,
	'makes up');
INSERT INTO R_RTO
	VALUES (2621460,
	2621460,
	2621488,
	0);
INSERT INTO R_OIR
	VALUES (2621460,
	2621460,
	2621488,
	0);
INSERT INTO R_AOTH
	VALUES (2621459,
	2621460,
	2621489,
	1,
	1,
	'is made up of');
INSERT INTO R_RTO
	VALUES (2621459,
	2621460,
	2621489,
	0);
INSERT INTO R_OIR
	VALUES (2621459,
	2621460,
	2621489,
	0);
INSERT INTO R_ASSR
	VALUES (2621461,
	2621460,
	2621490,
	0);
INSERT INTO R_RGO
	VALUES (2621461,
	2621460,
	2621490);
INSERT INTO R_OIR
	VALUES (2621461,
	2621460,
	2621490,
	0);
INSERT INTO R_SIMP
	VALUES (2621461);
INSERT INTO R_REL
	VALUES (2621461,
	523,
	'',
	2621445);
INSERT INTO R_FORM
	VALUES (2621460,
	2621461,
	2621491,
	1,
	1,
	'contains');
INSERT INTO R_RGO
	VALUES (2621460,
	2621461,
	2621491);
INSERT INTO R_OIR
	VALUES (2621460,
	2621461,
	2621491,
	0);
INSERT INTO R_PART
	VALUES (2621441,
	2621461,
	2621492,
	0,
	0,
	'is assigned to');
INSERT INTO R_RTO
	VALUES (2621441,
	2621461,
	2621492,
	0);
INSERT INTO R_OIR
	VALUES (2621441,
	2621461,
	2621492,
	0);
INSERT INTO R_SIMP
	VALUES (2621462);
INSERT INTO R_REL
	VALUES (2621462,
	524,
	'Relationship Description:

Purpose of Abstraction:',
	2621445);
INSERT INTO R_PART
	VALUES (1048584,
	2621462,
	2621493,
	0,
	0,
	'is defined by');
INSERT INTO R_RTO
	VALUES (1048584,
	2621462,
	2621493,
	0);
INSERT INTO R_OIR
	VALUES (1048584,
	2621462,
	2621493,
	2621441);
INSERT INTO R_FORM
	VALUES (2621459,
	2621462,
	2621494,
	1,
	1,
	'defines the type of');
INSERT INTO R_RGO
	VALUES (2621459,
	2621462,
	2621494);
INSERT INTO R_OIR
	VALUES (2621459,
	2621462,
	2621494,
	0);
INSERT INTO R_SUBSUP
	VALUES (2621463);
INSERT INTO R_REL
	VALUES (2621463,
	525,
	'',
	2621445);
INSERT INTO R_SUPER
	VALUES (2621442,
	2621463,
	2621495);
INSERT INTO R_RTO
	VALUES (2621442,
	2621463,
	2621495,
	1);
INSERT INTO R_OIR
	VALUES (2621442,
	2621463,
	2621495,
	0);
INSERT INTO R_SUB
	VALUES (2621463,
	2621463,
	2621496);
INSERT INTO R_RGO
	VALUES (2621463,
	2621463,
	2621496);
INSERT INTO R_OIR
	VALUES (2621463,
	2621463,
	2621496,
	0);
INSERT INTO R_SUB
	VALUES (2621462,
	2621463,
	2621497);
INSERT INTO R_RGO
	VALUES (2621462,
	2621463,
	2621497);
INSERT INTO R_OIR
	VALUES (2621462,
	2621463,
	2621497,
	0);
INSERT INTO R_ASSOC
	VALUES (2621464);
INSERT INTO R_REL
	VALUES (2621464,
	503,
	'',
	2621445);
INSERT INTO R_AONE
	VALUES (2621443,
	2621464,
	2621498,
	1,
	1,
	'may be received in');
INSERT INTO R_RTO
	VALUES (2621443,
	2621464,
	2621498,
	0);
INSERT INTO R_OIR
	VALUES (2621443,
	2621464,
	2621498,
	0);
INSERT INTO R_AOTH
	VALUES (2621463,
	2621464,
	2621499,
	1,
	1,
	'is occupied when reception occurs of');
INSERT INTO R_RTO
	VALUES (2621463,
	2621464,
	2621499,
	0);
INSERT INTO R_OIR
	VALUES (2621463,
	2621464,
	2621499,
	0);
INSERT INTO R_ASSR
	VALUES (2621444,
	2621464,
	2621500,
	0);
INSERT INTO R_RGO
	VALUES (2621444,
	2621464,
	2621500);
INSERT INTO R_OIR
	VALUES (2621444,
	2621464,
	2621500,
	0);
INSERT INTO R_SUBSUP
	VALUES (2621465);
INSERT INTO R_REL
	VALUES (2621465,
	526,
	'',
	2621445);
INSERT INTO R_SUPER
	VALUES (2621463,
	2621465,
	2621501);
INSERT INTO R_RTO
	VALUES (2621463,
	2621465,
	2621501,
	0);
INSERT INTO R_OIR
	VALUES (2621463,
	2621465,
	2621501,
	0);
INSERT INTO R_SUB
	VALUES (2621464,
	2621465,
	2621502);
INSERT INTO R_RGO
	VALUES (2621464,
	2621465,
	2621502);
INSERT INTO R_OIR
	VALUES (2621464,
	2621465,
	2621502,
	0);
INSERT INTO R_SUB
	VALUES (2621465,
	2621465,
	2621503);
INSERT INTO R_RGO
	VALUES (2621465,
	2621465,
	2621503);
INSERT INTO R_OIR
	VALUES (2621465,
	2621465,
	2621503,
	0);
INSERT INTO R_SIMP
	VALUES (2621466);
INSERT INTO R_REL
	VALUES (2621466,
	509,
	'',
	2621445);
INSERT INTO R_FORM
	VALUES (2621448,
	2621466,
	2621504,
	0,
	1,
	'has assigned to it');
INSERT INTO R_RGO
	VALUES (2621448,
	2621466,
	2621504);
INSERT INTO R_OIR
	VALUES (2621448,
	2621466,
	2621504,
	0);
INSERT INTO R_PART
	VALUES (2621465,
	2621466,
	2621505,
	0,
	1,
	'is assigned to');
INSERT INTO R_RTO
	VALUES (2621465,
	2621466,
	2621505,
	0);
INSERT INTO R_OIR
	VALUES (2621465,
	2621466,
	2621505,
	0);
INSERT INTO R_SIMP
	VALUES (2621467);
INSERT INTO R_REL
	VALUES (2621467,
	527,
	'',
	2621445);
INSERT INTO R_PART
	VALUES (2621462,
	2621467,
	2621506,
	0,
	0,
	'is defined by');
INSERT INTO R_RTO
	VALUES (2621462,
	2621467,
	2621506,
	0);
INSERT INTO R_OIR
	VALUES (2621462,
	2621467,
	2621506,
	0);
INSERT INTO R_FORM
	VALUES (2621464,
	2621467,
	2621507,
	1,
	1,
	'is aliased by');
INSERT INTO R_RGO
	VALUES (2621464,
	2621467,
	2621507);
INSERT INTO R_OIR
	VALUES (2621464,
	2621467,
	2621507,
	0);
INSERT INTO S_SS
	VALUES (3145734,
	'Object',
	'An object (O_OBJ)  is an abstraction of a real world thing that has specific attributes (O_ATTR) and, if active, has a lifecycle model as a state model (SM_SM).  All instances of the object must have the same characteristics and follow the same state behavior.  Objects fall into many categories, some of which are: tangible things, roles, interactions and specifications. ',
	'O',
	101,
	2531670,
	3145734);
INSERT INTO O_OBJ
	VALUES (3145729,
	'Transformer',
	112,
	'O_TFR',
	'A Transformer is a method associated with an Object.  Transformers can be synchronously called from Action Specifications.  Two types of transformers exist.  Class-based transformers affect the class as a whole.  Instance-based transformers affect only an instance.',
	3145734);
INSERT INTO O_NBATTR
	VALUES (3145729,
	3145729);
INSERT INTO O_BATTR
	VALUES (3145729,
	3145729);
INSERT INTO O_ATTR
	VALUES (3145729,
	3145729,
	0,
	'Tfr_ID',
	'Full Name: Transformer Identifier',
	'',
	'Tfr_ID',
	0,
	524294);
INSERT INTO O_REF
	VALUES (3145729,
	3145732,
	0,
	3145751,
	3145732,
	3145735,
	3145736,
	3145730,
	3145742,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (3145730,
	3145729,
	3145751,
	3145732,
	1);
INSERT INTO O_ATTR
	VALUES (3145730,
	3145729,
	3145729,
	'Obj_ID',
	'',
	'',
	'Obj_ID',
	0,
	524296);
INSERT INTO O_NBATTR
	VALUES (3145731,
	3145729);
INSERT INTO O_BATTR
	VALUES (3145731,
	3145729);
INSERT INTO O_ATTR
	VALUES (3145731,
	3145729,
	3145730,
	'Name',
	'Full Name: Transformer Name
Description: The name of this Transformer operation.',
	'',
	'Name',
	0,
	524293);
INSERT INTO O_NBATTR
	VALUES (3145732,
	3145729);
INSERT INTO O_BATTR
	VALUES (3145732,
	3145729);
INSERT INTO O_ATTR
	VALUES (3145732,
	3145729,
	3145731,
	'Descrip',
	'Full Name: Transformer Description
Description: A textual description of this Transformer operation.',
	'',
	'Descrip',
	0,
	524293);
INSERT INTO O_REF
	VALUES (3145729,
	1048584,
	0,
	1048616,
	3145731,
	3145734,
	3145733,
	3145733,
	3145743,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (3145733,
	3145729,
	1048616,
	1048584,
	1);
INSERT INTO O_ATTR
	VALUES (3145733,
	3145729,
	3145732,
	'DT_ID',
	'',
	'',
	'DT_ID',
	0,
	524296);
INSERT INTO O_NBATTR
	VALUES (3145734,
	3145729);
INSERT INTO O_BATTR
	VALUES (3145734,
	3145729);
INSERT INTO O_ATTR
	VALUES (3145734,
	3145729,
	3145733,
	'Instance_Based',
	'Full Name: Instance Based Indicator
Description: Indicates whether the transformer is instance-based or class-based.
Data Domain: 0 = class-based, 1 = instance-based',
	'',
	'Instance_Based',
	0,
	524291);
INSERT INTO O_NBATTR
	VALUES (3145735,
	3145729);
INSERT INTO O_BATTR
	VALUES (3145735,
	3145729);
INSERT INTO O_ATTR
	VALUES (3145735,
	3145729,
	3145734,
	'Action_Semantics',
	'Full Name: Action Semantics Field
Description: Defines the action semantics for the transformer
',
	'',
	'Action_Semantics',
	0,
	524293);
INSERT INTO O_NBATTR
	VALUES (3145736,
	3145729);
INSERT INTO O_BATTR
	VALUES (3145736,
	3145729);
INSERT INTO O_ATTR
	VALUES (3145736,
	3145729,
	3145735,
	'Suc_Pars',
	'Full Name: Successful Parse Indicator
Description: Indicates the status of the parse for the transformer action described in the attribute Action_Semantics
Data Domain: 0=not parsed, 1 = parse successful, 2 = parse unsuccessful, 3 = parse on apply set but never been parsed',
	'',
	'Suc_Pars',
	0,
	524291);
INSERT INTO O_ID
	VALUES (0,
	3145729);
INSERT INTO O_OIDA
	VALUES (3145729,
	3145729,
	0);
INSERT INTO O_RTIDA
	VALUES (3145729,
	3145729,
	0,
	3145729,
	3145729);
INSERT INTO O_OBJ
	VALUES (3145730,
	'Transformer Parameter',
	113,
	'O_TPARM',
	'Each transformer (O_TFR) can have one or more parameters (O_TPARM).  A parameter to a transformer is called a transformer parameter.  Transformer parameters are either passed in by value, or by reference.  Transformer parameters can be accessed by using the param keyword from within a transformer action specification.',
	3145734);
INSERT INTO O_NBATTR
	VALUES (3145737,
	3145730);
INSERT INTO O_BATTR
	VALUES (3145737,
	3145730);
INSERT INTO O_ATTR
	VALUES (3145737,
	3145730,
	0,
	'TParm_ID',
	'Full Name: Transformer Parameter Identifier
Description: Uniquely identifies a transformer parameter',
	'',
	'TParm_ID',
	0,
	524294);
INSERT INTO O_REF
	VALUES (3145730,
	3145729,
	0,
	3145729,
	3145729,
	3145730,
	3145729,
	3145738,
	3145744,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (3145738,
	3145730,
	3145729,
	3145729,
	1);
INSERT INTO O_ATTR
	VALUES (3145738,
	3145730,
	3145737,
	'Tfr_ID',
	'',
	'',
	'Tfr_ID',
	0,
	524296);
INSERT INTO O_NBATTR
	VALUES (3145739,
	3145730);
INSERT INTO O_BATTR
	VALUES (3145739,
	3145730);
INSERT INTO O_ATTR
	VALUES (3145739,
	3145730,
	3145738,
	'Name',
	'Full Name: Transformer Parameter Name
Description: This is the name of the parameter.',
	'',
	'Name',
	0,
	524293);
INSERT INTO O_REF
	VALUES (3145730,
	1048584,
	0,
	1048616,
	3145730,
	3145732,
	3145731,
	3145740,
	3145745,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (3145740,
	3145730,
	1048616,
	1048584,
	1);
INSERT INTO O_ATTR
	VALUES (3145740,
	3145730,
	3145739,
	'DT_ID',
	'',
	'',
	'DT_ID',
	0,
	524296);
INSERT INTO O_NBATTR
	VALUES (3145741,
	3145730);
INSERT INTO O_BATTR
	VALUES (3145741,
	3145730);
INSERT INTO O_ATTR
	VALUES (3145741,
	3145730,
	3145740,
	'By_Ref',
	'Full Name: By Reference Indicator
Description: Indicates whether or not this parameter is passed by reference
Data Domain: 0 = passed by value, 1 = passed by reference',
	'',
	'By_Ref',
	0,
	524291);
INSERT INTO O_ID
	VALUES (0,
	3145730);
INSERT INTO O_OIDA
	VALUES (3145737,
	3145730,
	0);
INSERT INTO O_OBJ
	VALUES (3145731,
	'Attribute',
	103,
	'O_ATTR',
	'An attribute is an abstraction of a single characteristic possessed by an object.  Usually objects contain a set of attributes to completely capture all pertinent information.  Each attribute is given a unique name within an object.',
	3145734);
INSERT INTO O_NBATTR
	VALUES (3145742,
	3145731);
INSERT INTO O_BATTR
	VALUES (3145742,
	3145731);
INSERT INTO O_ATTR
	VALUES (3145742,
	3145731,
	0,
	'Attr_ID',
	'Full Name: Attribute Identifier
Description: A unique identifier for an instance of attribute.',
	'',
	'Attr_ID',
	0,
	524294);
INSERT INTO O_REF
	VALUES (3145731,
	3145732,
	0,
	3145751,
	3145733,
	3145737,
	3145738,
	3145743,
	3145746,
	0,
	0,
	'');
INSERT INTO O_REF
	VALUES (3145731,
	3145731,
	0,
	3145743,
	3145734,
	3145740,
	3145739,
	3145743,
	3145747,
	3145746,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (3145743,
	3145731,
	3145751,
	3145732,
	0);
INSERT INTO O_ATTR
	VALUES (3145743,
	3145731,
	3145742,
	'Obj_ID',
	'',
	'',
	'Obj_ID',
	0,
	524296);
INSERT INTO O_REF
	VALUES (3145731,
	3145731,
	0,
	3145742,
	3145734,
	3145740,
	3145739,
	3145744,
	3145748,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (3145744,
	3145731,
	3145742,
	3145731,
	1);
INSERT INTO O_ATTR
	VALUES (3145744,
	3145731,
	3145743,
	'PAttr_ID',
	'',
	'P',
	'Attr_ID',
	1,
	524296);
INSERT INTO O_DBATTR
	VALUES (3145745,
	3145731,
	'',
	0);
INSERT INTO O_BATTR
	VALUES (3145745,
	3145731);
INSERT INTO O_ATTR
	VALUES (3145745,
	3145731,
	3145744,
	'Name',
	'Full Name: Attribute Name
Description: The name of the object attribute as it appears on the OIM of the application analysis domain.  The name of the object attribute is mathematically derived from ''O_ATTR.Root_Nam'', ''O_ATTR.Prefix'', and ''O_ATTR.Prx_Mode''.',
	'',
	'Name',
	0,
	524293);
INSERT INTO O_NBATTR
	VALUES (3145746,
	3145731);
INSERT INTO O_BATTR
	VALUES (3145746,
	3145731);
INSERT INTO O_ATTR
	VALUES (3145746,
	3145731,
	3145745,
	'Descrip',
	'Full Name: Description
Description:  A textual description of the object attribute as specified in the application analysis domain.',
	'',
	'Descrip',
	0,
	524293);
INSERT INTO O_NBATTR
	VALUES (3145747,
	3145731);
INSERT INTO O_BATTR
	VALUES (3145747,
	3145731);
INSERT INTO O_ATTR
	VALUES (3145747,
	3145731,
	3145746,
	'Prefix',
	'Full Name: Attribute Name Prefix
Description: Prefix for the attribute.',
	'',
	'Prefix',
	0,
	524293);
INSERT INTO O_NBATTR
	VALUES (3145748,
	3145731);
INSERT INTO O_BATTR
	VALUES (3145748,
	3145731);
INSERT INTO O_ATTR
	VALUES (3145748,
	3145731,
	3145747,
	'Root_Nam',
	'Full Name: Attribute Root Name
Description: The root name (name without prefix) of the attribute',
	'',
	'Root_Nam',
	0,
	524293);
INSERT INTO O_NBATTR
	VALUES (3145749,
	3145731);
INSERT INTO O_BATTR
	VALUES (3145749,
	3145731);
INSERT INTO O_ATTR
	VALUES (3145749,
	3145731,
	3145748,
	'Pfx_Mode',
	'Full Name: Prefix Mode
Description: Indicates usage of a prefix.
Data Domain: 0 = uses no prefix, 1 = uses local prefix, 2 = uses referred to prefix',
	'',
	'Pfx_Mode',
	0,
	524291);
INSERT INTO O_REF
	VALUES (3145731,
	1048584,
	0,
	1048616,
	3145735,
	3145742,
	3145741,
	3145750,
	3145749,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (3145750,
	3145731,
	1048616,
	1048584,
	1);
INSERT INTO O_ATTR
	VALUES (3145750,
	3145731,
	3145749,
	'DT_ID',
	'',
	'',
	'DT_ID',
	0,
	524296);
INSERT INTO O_ID
	VALUES (0,
	3145731);
INSERT INTO O_OIDA
	VALUES (3145742,
	3145731,
	0);
INSERT INTO O_RTIDA
	VALUES (3145742,
	3145731,
	0,
	2097162,
	2097175);
INSERT INTO O_RTIDA
	VALUES (3145742,
	3145731,
	0,
	3145734,
	3145739);
INSERT INTO O_RTIDA
	VALUES (3145742,
	3145731,
	0,
	3145741,
	3145755);
INSERT INTO O_RTIDA
	VALUES (3145742,
	3145731,
	0,
	3145736,
	3145743);
INSERT INTO O_OIDA
	VALUES (3145743,
	3145731,
	0);
INSERT INTO O_RTIDA
	VALUES (3145743,
	3145731,
	0,
	3145741,
	3145755);
INSERT INTO O_RTIDA
	VALUES (3145743,
	3145731,
	0,
	2097162,
	2097175);
INSERT INTO O_RTIDA
	VALUES (3145743,
	3145731,
	0,
	3145734,
	3145739);
INSERT INTO O_RTIDA
	VALUES (3145743,
	3145731,
	0,
	3145736,
	3145743);
INSERT INTO O_OBJ
	VALUES (3145732,
	'Object',
	101,
	'O_OBJ',
	'An object represents an abstraction of a real world thing.  All instances of an object have the same characteristics and conform to the same set of rules.  The characteristics of an object are captured as attributes.  Each object within a domain are assigned a unique name, number, and keyletter.',
	3145734);
INSERT INTO O_NBATTR
	VALUES (3145751,
	3145732);
INSERT INTO O_BATTR
	VALUES (3145751,
	3145732);
INSERT INTO O_ATTR
	VALUES (3145751,
	3145732,
	0,
	'Obj_ID',
	'Full Name: Object Identifier
Description: Uniquely identifies the object within the domain.',
	'',
	'Obj_ID',
	0,
	524294);
INSERT INTO O_NBATTR
	VALUES (3145752,
	3145732);
INSERT INTO O_BATTR
	VALUES (3145752,
	3145732);
INSERT INTO O_ATTR
	VALUES (3145752,
	3145732,
	3145751,
	'Name',
	'Full Name: Object Name
Description: Name of the object as entered in the model.',
	'',
	'Name',
	0,
	524293);
INSERT INTO O_NBATTR
	VALUES (3145753,
	3145732);
INSERT INTO O_BATTR
	VALUES (3145753,
	3145732);
INSERT INTO O_ATTR
	VALUES (3145753,
	3145732,
	3145752,
	'Numb',
	'Full Name: Object Number
Description: Number of the object as it appears in the model.',
	'',
	'Numb',
	0,
	524291);
INSERT INTO O_NBATTR
	VALUES (3145754,
	3145732);
INSERT INTO O_BATTR
	VALUES (3145754,
	3145732);
INSERT INTO O_ATTR
	VALUES (3145754,
	3145732,
	3145753,
	'Key_Lett',
	'Full Name: Object Key Letters
Description: Key letters of the object as they appear in the model.',
	'',
	'Key_Lett',
	0,
	524293);
INSERT INTO O_NBATTR
	VALUES (3145755,
	3145732);
INSERT INTO O_BATTR
	VALUES (3145755,
	3145732);
INSERT INTO O_ATTR
	VALUES (3145755,
	3145732,
	3145754,
	'Descrip',
	'Full Name: Object Description
Description: This is a description of the object as entered by the user.',
	'',
	'Descrip',
	0,
	524293);
INSERT INTO O_REF
	VALUES (3145732,
	1048578,
	0,
	1048582,
	1048594,
	1048615,
	1048614,
	3145756,
	3145729,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (3145756,
	3145732,
	1048582,
	1048578,
	1);
INSERT INTO O_ATTR
	VALUES (3145756,
	3145732,
	3145755,
	'SS_ID',
	'',
	'',
	'SS_ID',
	0,
	524296);
INSERT INTO O_ID
	VALUES (0,
	3145732);
INSERT INTO O_OIDA
	VALUES (3145751,
	3145732,
	0);
INSERT INTO O_RTIDA
	VALUES (3145751,
	3145732,
	0,
	2621456,
	2621481);
INSERT INTO O_RTIDA
	VALUES (3145751,
	3145732,
	0,
	3145732,
	3145736);
INSERT INTO O_RTIDA
	VALUES (3145751,
	3145732,
	0,
	2621455,
	2621479);
INSERT INTO O_RTIDA
	VALUES (3145751,
	3145732,
	0,
	2097160,
	2097171);
INSERT INTO O_RTIDA
	VALUES (3145751,
	3145732,
	0,
	3145742,
	3145759);
INSERT INTO O_RTIDA
	VALUES (3145751,
	3145732,
	0,
	3145733,
	3145738);
INSERT INTO O_RTIDA
	VALUES (3145751,
	3145732,
	0,
	3145743,
	3145760);
INSERT INTO O_RTIDA
	VALUES (3145751,
	3145732,
	0,
	1572865,
	1572867);
INSERT INTO O_ID
	VALUES (1,
	3145732);
INSERT INTO O_OIDA
	VALUES (3145754,
	3145732,
	1);
INSERT INTO O_OBJ
	VALUES (3145733,
	'Base Attribute',
	106,
	'O_BATTR',
	'A Base attribute is a non-referential attribute.  Base attributes are either derived base attributes (O_DBATTR) or non-derived base attributes (O_NBATTR).',
	3145734);
INSERT INTO O_REF
	VALUES (3145733,
	3145731,
	0,
	3145742,
	3145736,
	3145744,
	3145743,
	3145757,
	3145730,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (3145757,
	3145733,
	3145742,
	3145731,
	1);
INSERT INTO O_ATTR
	VALUES (3145757,
	3145733,
	0,
	'Attr_ID',
	'',
	'',
	'Attr_ID',
	0,
	524296);
INSERT INTO O_REF
	VALUES (3145733,
	3145731,
	0,
	3145743,
	3145736,
	3145744,
	3145743,
	3145758,
	3145731,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (3145758,
	3145733,
	3145751,
	3145732,
	1);
INSERT INTO O_ATTR
	VALUES (3145758,
	3145733,
	3145757,
	'Obj_ID',
	'',
	'',
	'Obj_ID',
	0,
	524296);
INSERT INTO O_ID
	VALUES (0,
	3145733);
INSERT INTO O_OIDA
	VALUES (3145757,
	3145733,
	0);
INSERT INTO O_RTIDA
	VALUES (3145757,
	3145733,
	0,
	3145738,
	3145748);
INSERT INTO O_RTIDA
	VALUES (3145757,
	3145733,
	0,
	3145737,
	3145746);
INSERT INTO O_OIDA
	VALUES (3145758,
	3145733,
	0);
INSERT INTO O_RTIDA
	VALUES (3145758,
	3145733,
	0,
	3145738,
	3145748);
INSERT INTO O_RTIDA
	VALUES (3145758,
	3145733,
	0,
	3145737,
	3145746);
INSERT INTO O_OBJ
	VALUES (3145734,
	'Referential Attribute',
	109,
	'O_RATTR',
	'A referential attribute captures the formalization of a relationship.  A referential attribute refers to an identifying attribute in the object at the other end of the relationship which it formalizes.',
	3145734);
INSERT INTO O_REF
	VALUES (3145734,
	3145731,
	0,
	3145742,
	3145736,
	3145745,
	3145743,
	3145759,
	3145732,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (3145759,
	3145734,
	3145742,
	3145731,
	1);
INSERT INTO O_ATTR
	VALUES (3145759,
	3145734,
	0,
	'Attr_ID',
	'',
	'',
	'Attr_ID',
	0,
	524296);
INSERT INTO O_REF
	VALUES (3145734,
	3145731,
	0,
	3145743,
	3145736,
	3145745,
	3145743,
	3145760,
	3145733,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (3145760,
	3145734,
	3145751,
	3145732,
	1);
INSERT INTO O_ATTR
	VALUES (3145760,
	3145734,
	3145759,
	'Obj_ID',
	'',
	'',
	'Obj_ID',
	0,
	524296);
INSERT INTO O_REF
	VALUES (3145734,
	3145733,
	0,
	3145757,
	3145737,
	3145747,
	3145746,
	3145761,
	3145750,
	0,
	1,
	'');
INSERT INTO O_RATTR
	VALUES (3145761,
	3145734,
	3145742,
	3145731,
	1);
INSERT INTO O_ATTR
	VALUES (3145761,
	3145734,
	3145760,
	'BAttr_ID',
	'Reference IS CONSTRAINED such that Base Attribute related across R113 is same Base Attribute which is found by navigating back through the referred to attributes until Base Attribute is found.',
	'B',
	'Attr_ID',
	1,
	524296);
INSERT INTO O_REF
	VALUES (3145734,
	3145733,
	0,
	3145758,
	3145737,
	3145747,
	3145746,
	3145762,
	3145751,
	0,
	1,
	'');
INSERT INTO O_RATTR
	VALUES (3145762,
	3145734,
	3145751,
	3145732,
	1);
INSERT INTO O_ATTR
	VALUES (3145762,
	3145734,
	3145761,
	'BObj_ID',
	'Reference IS CONSTRAINED such that Base Attribute related across R113 is same Base Attribute which is found by navigating back through the referred to attributes until Base Attribute is found.',
	'B',
	'Obj_ID',
	1,
	524296);
INSERT INTO O_NBATTR
	VALUES (3145763,
	3145734);
INSERT INTO O_BATTR
	VALUES (3145763,
	3145734);
INSERT INTO O_ATTR
	VALUES (3145763,
	3145734,
	3145762,
	'Ref_Mode',
	'Full Name: Referential Attribute Referred to Mode
Description: Indicates how to build the name of the referential attribute.
Data Domain: 0 = use local root, 1 = use referred to root, 2 = use referred to attribute name',
	'',
	'Ref_Mode',
	0,
	524291);
INSERT INTO O_ID
	VALUES (0,
	3145734);
INSERT INTO O_OIDA
	VALUES (3145760,
	3145734,
	0);
INSERT INTO O_RTIDA
	VALUES (3145760,
	3145734,
	0,
	3145739,
	3145752);
INSERT INTO O_OIDA
	VALUES (3145759,
	3145734,
	0);
INSERT INTO O_RTIDA
	VALUES (3145759,
	3145734,
	0,
	3145739,
	3145752);
INSERT INTO O_OBJ
	VALUES (3145735,
	'Derived Based Attribute',
	107,
	'O_DBATTR',
	'A Derived Attribute is the result of an algorithm used to derive the value. A derived based attribute is said to be mathematically-dependent since is value is derived from other data in the model.  The action for the derived base attribute is used to set the value of the attribute by using the self keyword.',
	3145734);
INSERT INTO O_REF
	VALUES (3145735,
	3145733,
	0,
	3145757,
	3145738,
	3145749,
	3145748,
	3145764,
	3145734,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (3145764,
	3145735,
	3145742,
	3145731,
	1);
INSERT INTO O_ATTR
	VALUES (3145764,
	3145735,
	0,
	'Attr_ID',
	'',
	'',
	'Attr_ID',
	0,
	524296);
INSERT INTO O_REF
	VALUES (3145735,
	3145733,
	0,
	3145758,
	3145738,
	3145749,
	3145748,
	3145765,
	3145735,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (3145765,
	3145735,
	3145751,
	3145732,
	1);
INSERT INTO O_ATTR
	VALUES (3145765,
	3145735,
	3145764,
	'Obj_ID',
	'',
	'',
	'Obj_ID',
	0,
	524296);
INSERT INTO O_NBATTR
	VALUES (3145766,
	3145735);
INSERT INTO O_BATTR
	VALUES (3145766,
	3145735);
INSERT INTO O_ATTR
	VALUES (3145766,
	3145735,
	3145765,
	'Action_Semantics',
	'Full Name: Action Semantics Field
Description: This attribute contains the action semantics for the mathematically-dependent attribute.',
	'',
	'Action_Semantics',
	0,
	524293);
INSERT INTO O_NBATTR
	VALUES (3145767,
	3145735);
INSERT INTO O_BATTR
	VALUES (3145767,
	3145735);
INSERT INTO O_ATTR
	VALUES (3145767,
	3145735,
	3145766,
	'Suc_Pars',
	'Full Name: Successful Parse Indicator
Description: Indicates the status of the parse for the derived base attribute action specification in the attribute Action_Semantics
Data Domain: 0=not parsed, 1 = parse successful, 2 = parse unsuccessful, 3 = parse on apply set but never been parsed',
	'',
	'Suc_Pars',
	0,
	524291);
INSERT INTO O_ID
	VALUES (0,
	3145735);
INSERT INTO O_OIDA
	VALUES (3145764,
	3145735,
	0);
INSERT INTO O_OIDA
	VALUES (3145765,
	3145735,
	0);
INSERT INTO O_OBJ
	VALUES (3145736,
	'New Base Attribute',
	108,
	'O_NBATTR',
	'A New Base Attribute is a non-derived base attribute.',
	3145734);
INSERT INTO O_REF
	VALUES (3145736,
	3145733,
	0,
	3145757,
	3145738,
	3145750,
	3145748,
	3145768,
	3145736,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (3145768,
	3145736,
	3145742,
	3145731,
	1);
INSERT INTO O_ATTR
	VALUES (3145768,
	3145736,
	0,
	'Attr_ID',
	'',
	'',
	'Attr_ID',
	0,
	524296);
INSERT INTO O_REF
	VALUES (3145736,
	3145733,
	0,
	3145758,
	3145738,
	3145750,
	3145748,
	3145769,
	3145737,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (3145769,
	3145736,
	3145751,
	3145732,
	1);
INSERT INTO O_ATTR
	VALUES (3145769,
	3145736,
	3145768,
	'Obj_ID',
	'',
	'',
	'Obj_ID',
	0,
	524296);
INSERT INTO O_ID
	VALUES (0,
	3145736);
INSERT INTO O_OIDA
	VALUES (3145768,
	3145736,
	0);
INSERT INTO O_OIDA
	VALUES (3145769,
	3145736,
	0);
INSERT INTO O_OBJ
	VALUES (3145737,
	'Attribute Reference in Object',
	110,
	'O_REF',
	'The object represents an relationship number (R#) which follows a referential attribute.',
	3145734);
INSERT INTO O_REF
	VALUES (3145737,
	3145734,
	0,
	3145760,
	3145739,
	3145751,
	3145752,
	3145770,
	3145738,
	0,
	0,
	'');
INSERT INTO O_REF
	VALUES (3145737,
	1572881,
	0,
	1572928,
	3145746,
	3145769,
	3145767,
	3145770,
	3145752,
	3145738,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (3145770,
	3145737,
	3145751,
	3145732,
	1);
INSERT INTO O_ATTR
	VALUES (3145770,
	3145737,
	0,
	'Obj_ID',
	'',
	'',
	'Obj_ID',
	0,
	524296);
INSERT INTO O_REF
	VALUES (3145737,
	3145741,
	0,
	3145794,
	3145746,
	3145769,
	3145768,
	3145771,
	3145753,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (3145771,
	3145737,
	3145751,
	3145732,
	1);
INSERT INTO O_ATTR
	VALUES (3145771,
	3145737,
	3145770,
	'RObj_ID',
	'',
	'R',
	'Obj_ID',
	1,
	524296);
INSERT INTO O_REF
	VALUES (3145737,
	3145741,
	0,
	3145795,
	3145746,
	3145769,
	3145768,
	3145772,
	3145754,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (3145772,
	3145737,
	3145782,
	3145738,
	1);
INSERT INTO O_ATTR
	VALUES (3145772,
	3145737,
	3145771,
	'ROid_ID',
	'',
	'R',
	'Oid_ID',
	1,
	524296);
INSERT INTO O_REF
	VALUES (3145737,
	3145741,
	0,
	3145793,
	3145746,
	3145769,
	3145768,
	3145773,
	3145755,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (3145773,
	3145737,
	3145742,
	3145731,
	1);
INSERT INTO O_ATTR
	VALUES (3145773,
	3145737,
	3145772,
	'RAttr_ID',
	'',
	'R',
	'Attr_ID',
	1,
	524296);
INSERT INTO O_REF
	VALUES (3145737,
	1572881,
	0,
	1572929,
	3145746,
	3145769,
	3145767,
	3145774,
	3145756,
	0,
	0,
	'');
INSERT INTO O_REF
	VALUES (3145737,
	3145741,
	0,
	3145796,
	3145746,
	3145769,
	3145768,
	3145774,
	3145757,
	3145756,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (3145774,
	3145737,
	1572865,
	1572865,
	1);
INSERT INTO O_ATTR
	VALUES (3145774,
	3145737,
	3145773,
	'Rel_ID',
	'',
	'',
	'Rel_ID',
	0,
	524296);
INSERT INTO O_REF
	VALUES (3145737,
	1572881,
	0,
	1572930,
	3145746,
	3145769,
	3145767,
	3145775,
	3145758,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (3145775,
	3145737,
	1572922,
	1572879,
	1);
INSERT INTO O_ATTR
	VALUES (3145775,
	3145737,
	3145774,
	'OIR_ID',
	'',
	'',
	'OIR_ID',
	0,
	524296);
INSERT INTO O_REF
	VALUES (3145737,
	3145741,
	0,
	3145797,
	3145746,
	3145769,
	3145768,
	3145776,
	3145759,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (3145776,
	3145737,
	1572922,
	1572879,
	1);
INSERT INTO O_ATTR
	VALUES (3145776,
	3145737,
	3145775,
	'ROIR_ID',
	'',
	'R',
	'OIR_ID',
	1,
	524296);
INSERT INTO O_REF
	VALUES (3145737,
	3145734,
	0,
	3145759,
	3145739,
	3145751,
	3145752,
	3145777,
	3145739,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (3145777,
	3145737,
	3145742,
	3145731,
	1);
INSERT INTO O_ATTR
	VALUES (3145777,
	3145737,
	3145776,
	'Attr_ID',
	'',
	'',
	'Attr_ID',
	0,
	524296);
INSERT INTO O_NBATTR
	VALUES (3145778,
	3145737);
INSERT INTO O_BATTR
	VALUES (3145778,
	3145737);
INSERT INTO O_ATTR
	VALUES (3145778,
	3145737,
	3145777,
	'ARef_ID',
	'Full Name: Attribute Reference In Object Identifier
Description: Secondary Identifier
',
	'',
	'ARef_ID',
	0,
	524294);
INSERT INTO O_REF
	VALUES (3145737,
	3145737,
	1,
	3145778,
	3145740,
	3145754,
	3145753,
	3145779,
	3145760,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (3145779,
	3145737,
	3145778,
	3145737,
	1);
INSERT INTO O_ATTR
	VALUES (3145779,
	3145737,
	3145778,
	'PARef_ID',
	'',
	'P',
	'ARef_ID',
	1,
	524296);
INSERT INTO O_NBATTR
	VALUES (3145780,
	3145737);
INSERT INTO O_BATTR
	VALUES (3145780,
	3145737);
INSERT INTO O_ATTR
	VALUES (3145780,
	3145737,
	3145779,
	'Is_Cstrd',
	'Full Name: Is Constrained
Description: Signifies a constrained referential
Data Domain: 0 = not  constrained, 1 = constrained',
	'',
	'Is_Cstrd',
	0,
	524290);
INSERT INTO O_NBATTR
	VALUES (3145781,
	3145737);
INSERT INTO O_BATTR
	VALUES (3145781,
	3145737);
INSERT INTO O_ATTR
	VALUES (3145781,
	3145737,
	3145780,
	'Descrip',
	'Full Name: Attribute Reference in Object Description
Description: Description of the attribute reference.',
	'',
	'Descrip',
	0,
	524293);
INSERT INTO O_ID
	VALUES (0,
	3145737);
INSERT INTO O_OIDA
	VALUES (3145773,
	3145737,
	0);
INSERT INTO O_OIDA
	VALUES (3145770,
	3145737,
	0);
INSERT INTO O_OIDA
	VALUES (3145775,
	3145737,
	0);
INSERT INTO O_OIDA
	VALUES (3145774,
	3145737,
	0);
INSERT INTO O_OIDA
	VALUES (3145771,
	3145737,
	0);
INSERT INTO O_OIDA
	VALUES (3145776,
	3145737,
	0);
INSERT INTO O_OIDA
	VALUES (3145772,
	3145737,
	0);
INSERT INTO O_ID
	VALUES (1,
	3145737);
INSERT INTO O_OIDA
	VALUES (3145778,
	3145737,
	1);
INSERT INTO O_RTIDA
	VALUES (3145778,
	3145737,
	1,
	3145740,
	3145753);
INSERT INTO O_OBJ
	VALUES (3145738,
	'Object Identifier',
	104,
	'O_ID',
	'A set of one or more Attributes which uniquely distinguishes each instance of an object is an object identifier.  An object may have several identifiers.',
	3145734);
INSERT INTO O_NBATTR
	VALUES (3145782,
	3145738);
INSERT INTO O_BATTR
	VALUES (3145782,
	3145738);
INSERT INTO O_ATTR
	VALUES (3145782,
	3145738,
	0,
	'Oid_ID',
	'Full Name: Object Identifier Identifier
Description: The identifying attribute set number.
Data Domain: 0 = Primary Identifer Set (e.g., *), 1 = Secondary Identifier Set (e.g., *2), 2 = Third Identifier Set (e.g., *3)',
	'',
	'Oid_ID',
	0,
	524291);
INSERT INTO O_REF
	VALUES (3145738,
	3145732,
	0,
	3145751,
	3145742,
	3145758,
	3145759,
	3145783,
	3145740,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (3145783,
	3145738,
	3145751,
	3145732,
	1);
INSERT INTO O_ATTR
	VALUES (3145783,
	3145738,
	3145782,
	'Obj_ID',
	'',
	'',
	'Obj_ID',
	0,
	524296);
INSERT INTO O_ID
	VALUES (0,
	3145738);
INSERT INTO O_OIDA
	VALUES (3145783,
	3145738,
	0);
INSERT INTO O_RTIDA
	VALUES (3145783,
	3145738,
	0,
	3145745,
	3145766);
INSERT INTO O_RTIDA
	VALUES (3145783,
	3145738,
	0,
	3145741,
	3145756);
INSERT INTO O_OIDA
	VALUES (3145782,
	3145738,
	0);
INSERT INTO O_RTIDA
	VALUES (3145782,
	3145738,
	0,
	3145741,
	3145756);
INSERT INTO O_RTIDA
	VALUES (3145782,
	3145738,
	0,
	3145745,
	3145766);
INSERT INTO O_OBJ
	VALUES (3145739,
	'Object Identifier Attribute',
	105,
	'O_OIDA',
	'An attribute that is part of an object identifier is an object identifier attribute.',
	3145734);
INSERT INTO O_REF
	VALUES (3145739,
	3145731,
	0,
	3145742,
	3145741,
	3145757,
	3145755,
	3145784,
	3145761,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (3145784,
	3145739,
	3145742,
	3145731,
	1);
INSERT INTO O_ATTR
	VALUES (3145784,
	3145739,
	0,
	'Attr_ID',
	'',
	'',
	'Attr_ID',
	0,
	524296);
INSERT INTO O_REF
	VALUES (3145739,
	3145738,
	0,
	3145783,
	3145741,
	3145757,
	3145756,
	3145785,
	3145762,
	0,
	0,
	'');
INSERT INTO O_REF
	VALUES (3145739,
	3145731,
	0,
	3145743,
	3145741,
	3145757,
	3145755,
	3145785,
	3145763,
	3145762,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (3145785,
	3145739,
	3145751,
	3145732,
	1);
INSERT INTO O_ATTR
	VALUES (3145785,
	3145739,
	3145784,
	'Obj_ID',
	'',
	'',
	'Obj_ID',
	0,
	524296);
INSERT INTO O_REF
	VALUES (3145739,
	3145738,
	0,
	3145782,
	3145741,
	3145757,
	3145756,
	3145786,
	3145764,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (3145786,
	3145739,
	3145782,
	3145738,
	1);
INSERT INTO O_ATTR
	VALUES (3145786,
	3145739,
	3145785,
	'Oid_ID',
	'',
	'',
	'Oid_ID',
	0,
	524296);
INSERT INTO O_ID
	VALUES (0,
	3145739);
INSERT INTO O_OIDA
	VALUES (3145786,
	3145739,
	0);
INSERT INTO O_RTIDA
	VALUES (3145786,
	3145739,
	0,
	3145744,
	3145763);
INSERT INTO O_OIDA
	VALUES (3145785,
	3145739,
	0);
INSERT INTO O_RTIDA
	VALUES (3145785,
	3145739,
	0,
	3145744,
	3145763);
INSERT INTO O_OIDA
	VALUES (3145784,
	3145739,
	0);
INSERT INTO O_RTIDA
	VALUES (3145784,
	3145739,
	0,
	3145744,
	3145763);
INSERT INTO O_NBATTR
	VALUES (500003,
	3145739);
INSERT INTO O_BATTR
	VALUES (500003,
	3145739);
INSERT INTO O_ATTR
	VALUES (500003,
	3145739,
	3145786,
	'localAttributeName',
	'User_Visible: false',
	'', 
	'localAttributeName',
	0,
	524293);
INSERT INTO O_OBJ
	VALUES (3145740,
	'Imported Object',
	102,
	'O_IOBJ',
	'Objects can have interactions with objects in other subsystems.  In order to capture these spanning interactions, Objects can be imported into another subsystem.  Spanning interactions can be relationships, event communications, or data accesses and are captured in object information model, object communication model, and object access model, respectively.  Spanning interactions provide the data for derivation of the subsystem relationship model, subsystem communication model, and subsystem access model.',
	3145734);
INSERT INTO O_NBATTR
	VALUES (3145787,
	3145740);
INSERT INTO O_BATTR
	VALUES (3145787,
	3145740);
INSERT INTO O_ATTR
	VALUES (3145787,
	3145740,
	0,
	'IObj_ID',
	'Full Name: Imported Object Identifier
Description: Uniquely identifies an instance of imported object',
	'',
	'IObj_ID',
	0,
	524294);
INSERT INTO O_REF
	VALUES (3145740,
	3145732,
	0,
	3145751,
	3145743,
	3145761,
	3145760,
	3145788,
	3145765,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (3145788,
	3145740,
	3145751,
	3145732,
	1);
INSERT INTO O_ATTR
	VALUES (3145788,
	3145740,
	3145787,
	'Obj_ID',
	'',
	'',
	'Obj_ID',
	0,
	524296);
INSERT INTO O_NBATTR
	VALUES (3145789,
	3145740);
INSERT INTO O_BATTR
	VALUES (3145789,
	3145740);
INSERT INTO O_ATTR
	VALUES (3145789,
	3145740,
	3145788,
	'Modl_Typ',
	'Full Name: Model Type
Description: Indicates what type of model the Imported Object is in.
Data Domain: 5 indicates Object Information Model, 6 indicates Object Communication Model, 7 indicates Object Access Model',
	'',
	'Modl_Typ',
	0,
	524291);
INSERT INTO O_REF
	VALUES (3145740,
	1048578,
	0,
	1048582,
	1048598,
	1048623,
	1048622,
	3145790,
	3145741,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (3145790,
	3145740,
	1048582,
	1048578,
	1);
INSERT INTO O_ATTR
	VALUES (3145790,
	3145740,
	3145789,
	'SS_ID',
	'',
	'',
	'SS_ID',
	0,
	524296);
INSERT INTO O_DBATTR
	VALUES (3145791,
	3145740,
	'select one object related by self->O_OBJ[R101];
if ( empty object )
   self.Obj_Name = "";
else
   self.Obj_Name = object.Name;
end if;

',
	1);
INSERT INTO O_BATTR
	VALUES (3145791,
	3145740);
INSERT INTO O_ATTR
	VALUES (3145791,
	3145740,
	3145790,
	'Obj_Name',
	'Full Name: Object Name
Description:  This attribute provides identifying information for the Object related to this Imported Object.  This is used to associate the Imported Object to its related Object when importing a subsystem containing the Imported Object into a new/different domain.

',
	'',
	'Obj_Name',
	0,
	524293);
INSERT INTO O_DBATTR
	VALUES (3145792,
	3145740,
	'select one object related by self->O_OBJ[R101];
if ( empty object )
   self.Obj_KL = "";
else
   self.Obj_KL = object.Key_Lett;
end if;

',
	1);
INSERT INTO O_BATTR
	VALUES (3145792,
	3145740);
INSERT INTO O_ATTR
	VALUES (3145792,
	3145740,
	3145791,
	'Obj_KL',
	'Full Name: Object Key Letter
Description:  This attribute provides identifying information for the Object related to this Imported object.  This is used to associate the Imported Object to its related object when importing a  subsystem containing the Imported Object into a new/different domain.

',
	'',
	'Obj_KL',
	0,
	524293);
INSERT INTO O_ID
	VALUES (0,
	3145740);
INSERT INTO O_OIDA
	VALUES (3145787,
	3145740,
	0);
INSERT INTO O_RTIDA
	VALUES (3145787,
	3145740,
	0,
	2097168,
	2097187);
INSERT INTO O_RTIDA
	VALUES (3145787,
	3145740,
	0,
	2097167,
	2097185);
INSERT INTO O_RTIDA
	VALUES (3145787,
	3145740,
	0,
	2097158,
	2097166);
INSERT INTO O_RTIDA
	VALUES (3145787,
	3145740,
	0,
	2097163,
	2097177);
INSERT INTO O_RTIDA
	VALUES (3145787,
	3145740,
	0,
	1572879,
	1572906);
INSERT INTO O_OBJ
	VALUES (3145741,
	'Referred To Identifier Attribute',
	111,
	'O_RTIDA',
	'This object serves a linkage between R# (Attribute Reference in Object ) and the referred to object identifier attribute.',
	3145734);
INSERT INTO O_REF
	VALUES (3145741,
	3145739,
	0,
	3145784,
	3145744,
	3145764,
	3145763,
	3145793,
	3145766,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (3145793,
	3145741,
	3145742,
	3145731,
	1);
INSERT INTO O_ATTR
	VALUES (3145793,
	3145741,
	0,
	'Attr_ID',
	'',
	'',
	'Attr_ID',
	0,
	524296);
INSERT INTO O_REF
	VALUES (3145741,
	1572880,
	1,
	1572924,
	3145744,
	3145764,
	3145762,
	3145794,
	3145767,
	0,
	0,
	'');
INSERT INTO O_REF
	VALUES (3145741,
	3145739,
	0,
	3145785,
	3145744,
	3145764,
	3145763,
	3145794,
	3145768,
	3145767,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (3145794,
	3145741,
	3145751,
	3145732,
	1);
INSERT INTO O_ATTR
	VALUES (3145794,
	3145741,
	3145793,
	'Obj_ID',
	'',
	'',
	'Obj_ID',
	0,
	524296);
INSERT INTO O_REF
	VALUES (3145741,
	1572880,
	1,
	1572927,
	3145744,
	3145764,
	3145762,
	3145795,
	3145769,
	0,
	0,
	'');
INSERT INTO O_REF
	VALUES (3145741,
	3145739,
	0,
	3145786,
	3145744,
	3145764,
	3145763,
	3145795,
	3145770,
	3145769,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (3145795,
	3145741,
	3145782,
	3145738,
	1);
INSERT INTO O_ATTR
	VALUES (3145795,
	3145741,
	3145794,
	'Oid_ID',
	'',
	'',
	'Oid_ID',
	0,
	524296);
INSERT INTO O_REF
	VALUES (3145741,
	1572880,
	1,
	1572925,
	3145744,
	3145764,
	3145762,
	3145796,
	3145771,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (3145796,
	3145741,
	1572865,
	1572865,
	1);
INSERT INTO O_ATTR
	VALUES (3145796,
	3145741,
	3145795,
	'Rel_ID',
	'',
	'',
	'Rel_ID',
	0,
	524296);
INSERT INTO O_REF
	VALUES (3145741,
	1572880,
	1,
	1572926,
	3145744,
	3145764,
	3145762,
	3145797,
	3145772,
	0,
	0,
	'');
INSERT INTO O_RATTR
	VALUES (3145797,
	3145741,
	1572922,
	1572879,
	1);
INSERT INTO O_ATTR
	VALUES (3145797,
	3145741,
	3145796,
	'OIR_ID',
	'',
	'',
	'OIR_ID',
	0,
	524296);
INSERT INTO O_ID
	VALUES (0,
	3145741);
INSERT INTO O_OIDA
	VALUES (3145794,
	3145741,
	0);
INSERT INTO O_RTIDA
	VALUES (3145794,
	3145741,
	0,
	3145746,
	3145768);
INSERT INTO O_OIDA
	VALUES (3145796,
	3145741,
	0);
INSERT INTO O_RTIDA
	VALUES (3145796,
	3145741,
	0,
	3145746,
	3145768);
INSERT INTO O_OIDA
	VALUES (3145793,
	3145741,
	0);
INSERT INTO O_RTIDA
	VALUES (3145793,
	3145741,
	0,
	3145746,
	3145768);
INSERT INTO O_OIDA
	VALUES (3145795,
	3145741,
	0);
INSERT INTO O_RTIDA
	VALUES (3145795,
	3145741,
	0,
	3145746,
	3145768);
INSERT INTO O_OIDA
	VALUES (3145797,
	3145741,
	0);
INSERT INTO O_RTIDA
	VALUES (3145797,
	3145741,
	0,
	3145746,
	3145768);
INSERT INTO O_IOBJ
	VALUES (3145729,
	1048584,
	5,
	3145734,
	'Data Type',
	'S_DT');
INSERT INTO O_IOBJ
	VALUES (3145730,
	1572880,
	5,
	3145734,
	'Referred To Object in Rel',
	'R_RTO');
INSERT INTO O_IOBJ
	VALUES (3145731,
	1572881,
	5,
	3145734,
	'Referring Object In Rel',
	'R_RGO');
INSERT INTO R_SIMP
	VALUES (3145729);
INSERT INTO R_REL
	VALUES (3145729,
	117,
	'',
	3145734);
INSERT INTO R_PART
	VALUES (3145729,
	3145729,
	3145729,
	0,
	0,
	'is part of ');
INSERT INTO R_RTO
	VALUES (3145729,
	3145729,
	3145729,
	0);
INSERT INTO R_OIR
	VALUES (3145729,
	3145729,
	3145729,
	0);
INSERT INTO R_FORM
	VALUES (3145730,
	3145729,
	3145730,
	1,
	1,
	'contains');
INSERT INTO R_RGO
	VALUES (3145730,
	3145729,
	3145730);
INSERT INTO R_OIR
	VALUES (3145730,
	3145729,
	3145730,
	0);
INSERT INTO R_SIMP
	VALUES (3145730);
INSERT INTO R_REL
	VALUES (3145730,
	118,
	'',
	3145734);
INSERT INTO R_PART
	VALUES (1048584,
	3145730,
	3145731,
	0,
	0,
	'is defined by');
INSERT INTO R_RTO
	VALUES (1048584,
	3145730,
	3145731,
	0);
INSERT INTO R_OIR
	VALUES (1048584,
	3145730,
	3145731,
	3145729);
INSERT INTO R_FORM
	VALUES (3145730,
	3145730,
	3145732,
	1,
	1,
	'defines the type of ');
INSERT INTO R_RGO
	VALUES (3145730,
	3145730,
	3145732);
INSERT INTO R_OIR
	VALUES (3145730,
	3145730,
	3145732,
	0);
INSERT INTO R_SIMP
	VALUES (3145731);
INSERT INTO R_REL
	VALUES (3145731,
	116,
	'',
	3145734);
INSERT INTO R_PART
	VALUES (1048584,
	3145731,
	3145733,
	0,
	0,
	'return code is defined by');
INSERT INTO R_RTO
	VALUES (1048584,
	3145731,
	3145733,
	0);
INSERT INTO R_OIR
	VALUES (1048584,
	3145731,
	3145733,
	3145729);
INSERT INTO R_FORM
	VALUES (3145729,
	3145731,
	3145734,
	1,
	1,
	'defines the type of return code');
INSERT INTO R_RGO
	VALUES (3145729,
	3145731,
	3145734);
INSERT INTO R_OIR
	VALUES (3145729,
	3145731,
	3145734,
	0);
INSERT INTO R_SIMP
	VALUES (3145732);
INSERT INTO R_REL
	VALUES (3145732,
	115,
	'',
	3145734);
INSERT INTO R_FORM
	VALUES (3145729,
	3145732,
	3145735,
	1,
	1,
	'may contain');
INSERT INTO R_RGO
	VALUES (3145729,
	3145732,
	3145735);
INSERT INTO R_OIR
	VALUES (3145729,
	3145732,
	3145735,
	0);
INSERT INTO R_PART
	VALUES (3145732,
	3145732,
	3145736,
	0,
	0,
	'is associated with');
INSERT INTO R_RTO
	VALUES (3145732,
	3145732,
	3145736,
	0);
INSERT INTO R_OIR
	VALUES (3145732,
	3145732,
	3145736,
	0);
INSERT INTO R_SIMP
	VALUES (3145733);
INSERT INTO R_REL
	VALUES (3145733,
	102,
	'',
	3145734);
INSERT INTO R_FORM
	VALUES (3145731,
	3145733,
	3145737,
	1,
	1,
	'has characteristics abstracted by');
INSERT INTO R_RGO
	VALUES (3145731,
	3145733,
	3145737);
INSERT INTO R_OIR
	VALUES (3145731,
	3145733,
	3145737,
	0);
INSERT INTO R_PART
	VALUES (3145732,
	3145733,
	3145738,
	0,
	0,
	'abstracts characteristics of');
INSERT INTO R_RTO
	VALUES (3145732,
	3145733,
	3145738,
	0);
INSERT INTO R_OIR
	VALUES (3145732,
	3145733,
	3145738,
	0);
INSERT INTO R_SIMP
	VALUES (3145734);
INSERT INTO R_REL
	VALUES (3145734,
	103,
	'This relationship specifies the order in which an objects attributes appear on the OIM. The instance for which no instance ''preceeds'' is the first (top) object attribute on the OIM. The instance for which no instance ''succeeds'' is the last (bottom) object attribute on the OIM.',
	3145734);
INSERT INTO R_PART
	VALUES (3145731,
	3145734,
	3145739,
	0,
	1,
	'precedes');
INSERT INTO R_RTO
	VALUES (3145731,
	3145734,
	3145739,
	0);
INSERT INTO R_OIR
	VALUES (3145731,
	3145734,
	3145739,
	0);
INSERT INTO R_FORM
	VALUES (3145731,
	3145734,
	3145740,
	0,
	1,
	'succeeds');
INSERT INTO R_RGO
	VALUES (3145731,
	3145734,
	3145740);
INSERT INTO R_OIR
	VALUES (3145731,
	3145734,
	3145740,
	0);
INSERT INTO R_SIMP
	VALUES (3145735);
INSERT INTO R_REL
	VALUES (3145735,
	114,
	'',
	3145734);
INSERT INTO R_PART
	VALUES (1048584,
	3145735,
	3145741,
	0,
	0,
	'defines type of');
INSERT INTO R_RTO
	VALUES (1048584,
	3145735,
	3145741,
	0);
INSERT INTO R_OIR
	VALUES (1048584,
	3145735,
	3145741,
	3145729);
INSERT INTO R_FORM
	VALUES (3145731,
	3145735,
	3145742,
	1,
	1,
	'is defined by');
INSERT INTO R_RGO
	VALUES (3145731,
	3145735,
	3145742);
INSERT INTO R_OIR
	VALUES (3145731,
	3145735,
	3145742,
	0);
INSERT INTO R_SUBSUP
	VALUES (3145736);
INSERT INTO R_REL
	VALUES (3145736,
	106,
	'',
	3145734);
INSERT INTO R_SUPER
	VALUES (3145731,
	3145736,
	3145743);
INSERT INTO R_RTO
	VALUES (3145731,
	3145736,
	3145743,
	0);
INSERT INTO R_OIR
	VALUES (3145731,
	3145736,
	3145743,
	0);
INSERT INTO R_SUB
	VALUES (3145733,
	3145736,
	3145744);
INSERT INTO R_RGO
	VALUES (3145733,
	3145736,
	3145744);
INSERT INTO R_OIR
	VALUES (3145733,
	3145736,
	3145744,
	0);
INSERT INTO R_SUB
	VALUES (3145734,
	3145736,
	3145745);
INSERT INTO R_RGO
	VALUES (3145734,
	3145736,
	3145745);
INSERT INTO R_OIR
	VALUES (3145734,
	3145736,
	3145745,
	0);
INSERT INTO R_SIMP
	VALUES (3145737);
INSERT INTO R_REL
	VALUES (3145737,
	113,
	'',
	3145734);
INSERT INTO R_PART
	VALUES (3145733,
	3145737,
	3145746,
	0,
	0,
	'navigates back to');
INSERT INTO R_RTO
	VALUES (3145733,
	3145737,
	3145746,
	0);
INSERT INTO R_OIR
	VALUES (3145733,
	3145737,
	3145746,
	0);
INSERT INTO R_FORM
	VALUES (3145734,
	3145737,
	3145747,
	1,
	1,
	'can be the base of');
INSERT INTO R_RGO
	VALUES (3145734,
	3145737,
	3145747);
INSERT INTO R_OIR
	VALUES (3145734,
	3145737,
	3145747,
	0);
INSERT INTO R_SUBSUP
	VALUES (3145738);
INSERT INTO R_REL
	VALUES (3145738,
	107,
	'',
	3145734);
INSERT INTO R_SUPER
	VALUES (3145733,
	3145738,
	3145748);
INSERT INTO R_RTO
	VALUES (3145733,
	3145738,
	3145748,
	0);
INSERT INTO R_OIR
	VALUES (3145733,
	3145738,
	3145748,
	0);
INSERT INTO R_SUB
	VALUES (3145735,
	3145738,
	3145749);
INSERT INTO R_RGO
	VALUES (3145735,
	3145738,
	3145749);
INSERT INTO R_OIR
	VALUES (3145735,
	3145738,
	3145749,
	0);
INSERT INTO R_SUB
	VALUES (3145736,
	3145738,
	3145750);
INSERT INTO R_RGO
	VALUES (3145736,
	3145738,
	3145750);
INSERT INTO R_OIR
	VALUES (3145736,
	3145738,
	3145750,
	0);
INSERT INTO R_SIMP
	VALUES (3145739);
INSERT INTO R_REL
	VALUES (3145739,
	108,
	'',
	3145734);
INSERT INTO R_FORM
	VALUES (3145737,
	3145739,
	3145751,
	1,
	0,
	'resolves ');
INSERT INTO R_RGO
	VALUES (3145737,
	3145739,
	3145751);
INSERT INTO R_OIR
	VALUES (3145737,
	3145739,
	3145751,
	0);
INSERT INTO R_PART
	VALUES (3145734,
	3145739,
	3145752,
	0,
	0,
	'is resolved by');
INSERT INTO R_RTO
	VALUES (3145734,
	3145739,
	3145752,
	0);
INSERT INTO R_OIR
	VALUES (3145734,
	3145739,
	3145752,
	0);
INSERT INTO R_SIMP
	VALUES (3145740);
INSERT INTO R_REL
	VALUES (3145740,
	112,
	'',
	3145734);
INSERT INTO R_PART
	VALUES (3145737,
	3145740,
	3145753,
	0,
	1,
	'precedes');
INSERT INTO R_RTO
	VALUES (3145737,
	3145740,
	3145753,
	1);
INSERT INTO R_OIR
	VALUES (3145737,
	3145740,
	3145753,
	0);
INSERT INTO R_FORM
	VALUES (3145737,
	3145740,
	3145754,
	0,
	1,
	'succeeds');
INSERT INTO R_RGO
	VALUES (3145737,
	3145740,
	3145754);
INSERT INTO R_OIR
	VALUES (3145737,
	3145740,
	3145754,
	0);
INSERT INTO R_ASSOC
	VALUES (3145741);
INSERT INTO R_REL
	VALUES (3145741,
	105,
	'',
	3145734);
INSERT INTO R_AOTH
	VALUES (3145731,
	3145741,
	3145755,
	1,
	1,
	'is made up of ');
INSERT INTO R_RTO
	VALUES (3145731,
	3145741,
	3145755,
	0);
INSERT INTO R_OIR
	VALUES (3145731,
	3145741,
	3145755,
	0);
INSERT INTO R_AONE
	VALUES (3145738,
	3145741,
	3145756,
	1,
	1,
	'is part of ');
INSERT INTO R_RTO
	VALUES (3145738,
	3145741,
	3145756,
	0);
INSERT INTO R_OIR
	VALUES (3145738,
	3145741,
	3145756,
	0);
INSERT INTO R_ASSR
	VALUES (3145739,
	3145741,
	3145757,
	0);
INSERT INTO R_RGO
	VALUES (3145739,
	3145741,
	3145757);
INSERT INTO R_OIR
	VALUES (3145739,
	3145741,
	3145757,
	0);
INSERT INTO R_SIMP
	VALUES (3145742);
INSERT INTO R_REL
	VALUES (3145742,
	104,
	'',
	3145734);
INSERT INTO R_FORM
	VALUES (3145738,
	3145742,
	3145758,
	1,
	1,
	'is identified by');
INSERT INTO R_RGO
	VALUES (3145738,
	3145742,
	3145758);
INSERT INTO R_OIR
	VALUES (3145738,
	3145742,
	3145758,
	0);
INSERT INTO R_PART
	VALUES (3145732,
	3145742,
	3145759,
	0,
	0,
	'identifies');
INSERT INTO R_RTO
	VALUES (3145732,
	3145742,
	3145759,
	0);
INSERT INTO R_OIR
	VALUES (3145732,
	3145742,
	3145759,
	0);
INSERT INTO R_SIMP
	VALUES (3145743);
INSERT INTO R_REL
	VALUES (3145743,
	101,
	'',
	3145734);
INSERT INTO R_PART
	VALUES (3145732,
	3145743,
	3145760,
	0,
	0,
	'represents');
INSERT INTO R_RTO
	VALUES (3145732,
	3145743,
	3145760,
	0);
INSERT INTO R_OIR
	VALUES (3145732,
	3145743,
	3145760,
	0);
INSERT INTO R_FORM
	VALUES (3145740,
	3145743,
	3145761,
	1,
	1,
	'has presence in other subsystems');
INSERT INTO R_RGO
	VALUES (3145740,
	3145743,
	3145761);
INSERT INTO R_OIR
	VALUES (3145740,
	3145743,
	3145761,
	0);
INSERT INTO R_ASSOC
	VALUES (3145744);
INSERT INTO R_REL
	VALUES (3145744,
	110,
	'',
	3145734);
INSERT INTO R_AONE
	VALUES (1572880,
	3145744,
	3145762,
	1,
	1,
	'identifies for this relationship');
INSERT INTO R_RTO
	VALUES (1572880,
	3145744,
	3145762,
	1);
INSERT INTO R_OIR
	VALUES (1572880,
	3145744,
	3145762,
	3145730);
INSERT INTO R_AOTH
	VALUES (3145739,
	3145744,
	3145763,
	1,
	0,
	'is identified in this relationship by');
INSERT INTO R_RTO
	VALUES (3145739,
	3145744,
	3145763,
	0);
INSERT INTO R_OIR
	VALUES (3145739,
	3145744,
	3145763,
	0);
INSERT INTO R_ASSR
	VALUES (3145741,
	3145744,
	3145764,
	0);
INSERT INTO R_RGO
	VALUES (3145741,
	3145744,
	3145764);
INSERT INTO R_OIR
	VALUES (3145741,
	3145744,
	3145764,
	0);
INSERT INTO R_SIMP
	VALUES (3145745);
INSERT INTO R_REL
	VALUES (3145745,
	109,
	'',
	3145734);
INSERT INTO R_FORM
	VALUES (1572880,
	3145745,
	3145765,
	1,
	1,
	'identifies for this relationship ');
INSERT INTO R_RGO
	VALUES (1572880,
	3145745,
	3145765);
INSERT INTO R_OIR
	VALUES (1572880,
	3145745,
	3145765,
	3145730);
INSERT INTO R_PART
	VALUES (3145738,
	3145745,
	3145766,
	0,
	1,
	'is identified in this relationship by');
INSERT INTO R_RTO
	VALUES (3145738,
	3145745,
	3145766,
	0);
INSERT INTO R_OIR
	VALUES (3145738,
	3145745,
	3145766,
	0);
INSERT INTO R_ASSOC
	VALUES (3145746);
INSERT INTO R_REL
	VALUES (3145746,
	111,
	'',
	3145734);
INSERT INTO R_AONE
	VALUES (1572881,
	3145746,
	3145767,
	1,
	0,
	'is used to refer object by');
INSERT INTO R_RTO
	VALUES (1572881,
	3145746,
	3145767,
	0);
INSERT INTO R_OIR
	VALUES (1572881,
	3145746,
	3145767,
	3145731);
INSERT INTO R_AOTH
	VALUES (3145741,
	3145746,
	3145768,
	1,
	0,
	'refers across relationship via');
INSERT INTO R_RTO
	VALUES (3145741,
	3145746,
	3145768,
	0);
INSERT INTO R_OIR
	VALUES (3145741,
	3145746,
	3145768,
	0);
INSERT INTO R_ASSR
	VALUES (3145737,
	3145746,
	3145769,
	0);
INSERT INTO R_RGO
	VALUES (3145737,
	3145746,
	3145769);
INSERT INTO R_OIR
	VALUES (3145737,
	3145746,
	3145769,
	0);
