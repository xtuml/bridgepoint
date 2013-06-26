-- BP 6.1D content: domain syschar: 3

INSERT INTO S_DOM
	VALUES (53426,
	'odms1',
	'',
	1,
	1);
INSERT INTO S_CDT
	VALUES (524289,
	0);
INSERT INTO S_DT
	VALUES (524289,
	53426,
	'void',
	'');
INSERT INTO S_CDT
	VALUES (524290,
	1);
INSERT INTO S_DT
	VALUES (524290,
	53426,
	'boolean',
	'');
INSERT INTO S_CDT
	VALUES (524291,
	2);
INSERT INTO S_DT
	VALUES (524291,
	53426,
	'integer',
	'');
INSERT INTO S_CDT
	VALUES (524292,
	3);
INSERT INTO S_DT
	VALUES (524292,
	53426,
	'real',
	'');
INSERT INTO S_CDT
	VALUES (524293,
	4);
INSERT INTO S_DT
	VALUES (524293,
	53426,
	'string',
	'');
INSERT INTO S_CDT
	VALUES (524294,
	5);
INSERT INTO S_DT
	VALUES (524294,
	53426,
	'unique_id',
	'');
INSERT INTO S_CDT
	VALUES (524295,
	6);
INSERT INTO S_DT
	VALUES (524295,
	53426,
	'state<State_Model>',
	'');
INSERT INTO S_CDT
	VALUES (524296,
	7);
INSERT INTO S_DT
	VALUES (524296,
	53426,
	'same_as<Base_Attribute>',
	'');
INSERT INTO S_CDT
	VALUES (524297,
	8);
INSERT INTO S_DT
	VALUES (524297,
	53426,
	'inst_ref<Object>',
	'');
INSERT INTO S_CDT
	VALUES (524298,
	9);
INSERT INTO S_DT
	VALUES (524298,
	53426,
	'inst_ref_set<Object>',
	'');
INSERT INTO S_CDT
	VALUES (524299,
	10);
INSERT INTO S_DT
	VALUES (524299,
	53426,
	'inst<Event>',
	'');
INSERT INTO S_CDT
	VALUES (524300,
	11);
INSERT INTO S_DT
	VALUES (524300,
	53426,
	'inst<Mapping>',
	'');
INSERT INTO S_CDT
	VALUES (524301,
	12);
INSERT INTO S_DT
	VALUES (524301,
	53426,
	'inst_ref<Mapping>',
	'');
INSERT INTO S_UDT
	VALUES (524302,
	524300,
	1);
INSERT INTO S_DT
	VALUES (524302,
	53426,
	'date',
	'');
INSERT INTO S_UDT
	VALUES (524303,
	524300,
	2);
INSERT INTO S_DT
	VALUES (524303,
	53426,
	'timestamp',
	'');
INSERT INTO S_UDT
	VALUES (524304,
	524301,
	3);
INSERT INTO S_DT
	VALUES (524304,
	53426,
	'inst_ref<Timer>',
	'');
INSERT INTO S_UDT
	VALUES (524305,
	524293,
	0);
INSERT INTO S_DT
	VALUES (524305,
	53426,
	'transfer_status',
	'');
INSERT INTO S_UDT
	VALUES (524306,
	524293,
	0);
INSERT INTO S_DT
	VALUES (524306,
	53426,
	'transfer_vector_string',
	'');
INSERT INTO S_SYNC
	VALUES (524289,
	53426,
	'intialize',
	'',
	'// Create preexisting instances

// ONLINE LOCATION
// EE
create object instance ee  of D_P;
create object instance ole of D_OL;
assign ole.Online_Location_ID = 1;
relate ole to ee across R7;

//DRIVE
create object instance dr of D_DR;
assign dr.Idle = TRUE;
create object instance old of D_OL;
assign old.Online_Location_ID = 2;
relate old to dr across R7;

//SLOT
assign ol_count = 3;
while (ol_count < 5)
    create object instance ol of D_OL;
    assign ol.Online_Location_ID = ol_count;
    assign ol_count = ol_count + 1;
    create object instance slot of D_S;
    assign slot.Unassigned = TRUE;
   relate ol to slot across R7;
end while;

// DISK
assign disk_count = 1;
while (disk_count < 5)
    create object instance d of D_D;
    assign d.Disk_ID = disk_count;
    assign d.Serial_Number = 1000 + (disk_count * 10);
    assign d.Waiting_for_Slot = FALSE;
    assign d.Waiting_for_Drive = FALSE;
    assign d.Unowned_in_Cabinet = FALSE;
    assign d.Unowned_in_Library = TRUE;
    // OFFLINE DISK
    create object instance od of D_OD;
    relate d to od across R3;
    // PERMANENT HOME
    create object instance h of D_H;
    assign h.Row_Number =1;
    assign h.Column_Number =disk_count;
    relate d to h across R4;
    disk_count = disk_count + 1;
end while;

// QP
create object instance qp1 of D_QP;
assign qp1.Qualified_Process_ID = 1;

// ROBOT
create object instance r of D_R;
assign r.Robot_ID =1;',
	524289,
	1);
INSERT INTO S_SYNC
	VALUES (524290,
	53426,
	'PIO_robot_move_complete',
	'',
	'select any robot from instances of D_R where (selected.Robot_ID == param.id);
generate D_R2:''Robot Move Complete''() to robot;',
	524289,
	1);
INSERT INTO S_SPARM
	VALUES (524289,
	524290,
	'id',
	524291,
	0);
INSERT INTO S_SYNC
	VALUES (524291,
	53426,
	'PIO_gripper_closed_on_disk',
	'',
	'select any robot from instances of D_R where (selected.Robot_ID == param.id);
generate D_R4:''Gripper Closed on Disk''() to robot;',
	524289,
	1);
INSERT INTO S_SPARM
	VALUES (524290,
	524291,
	'id',
	524291,
	0);
INSERT INTO S_SYNC
	VALUES (524292,
	53426,
	'PIO_gripper_opened',
	'',
	'select any robot from instances of D_R where (selected.Robot_ID == param.id);
generate D_R3:''Gripper Opened''() to robot;',
	524289,
	1);
INSERT INTO S_SPARM
	VALUES (524291,
	524292,
	'id',
	524291,
	0);
INSERT INTO S_SYNC
	VALUES (524293,
	53426,
	'PIO_drive_at_spin_speed',
	'',
	'select any drive from instances of D_DR where (selected.Drive_ID == param.id);
generate D_DR2:''Drive at Spin Speed''() to drive;',
	524289,
	1);
INSERT INTO S_SPARM
	VALUES (524292,
	524293,
	'id',
	524291,
	0);
INSERT INTO S_SYNC
	VALUES (524294,
	53426,
	'PIO_drive_stopped',
	'',
	'select any drive from instances of D_DR where (selected.Drive_ID == param.id);
generate D_DR4:''Drive Stopped''() to drive;',
	524289,
	1);
INSERT INTO S_SPARM
	VALUES (524293,
	524294,
	'id',
	524291,
	0);
INSERT INTO S_SYNC
	VALUES (524295,
	53426,
	'PIO_port_unlocked',
	'',
	'select any port from instances of D_P where (selected.Port_ID == param.id);
generate D_P3:''Port Unlocked''() to port;',
	524289,
	1);
INSERT INTO S_SPARM
	VALUES (524294,
	524295,
	'id',
	524291,
	0);
INSERT INTO S_SYNC
	VALUES (524296,
	53426,
	'PIO_operator_closed_door',
	'',
	'select any port from instances of D_P where (selected.Port_ID == param.id);
generate D_P1:''Operator Closes Door''() to port;',
	524289,
	1);
INSERT INTO S_SPARM
	VALUES (524295,
	524296,
	'id',
	524291,
	0);
INSERT INTO S_SYNC
	VALUES (524297,
	53426,
	'PIO_operator_opened_door',
	'',
	'select any port from instances of D_P where (selected.Port_ID == param.id);
generate D_P6:''Operator Opens Door''() to port;',
	524289,
	1);
INSERT INTO S_SPARM
	VALUES (524296,
	524297,
	'id',
	524291,
	0);
INSERT INTO S_SYNC
	VALUES (524298,
	53426,
	'PIO_operator_removed_disk',
	'',
	'select any port from instances of D_P where (selected.Port_ID == param.id);
generate D_P5:''Operator Removes Disk''() to port;',
	524289,
	1);
INSERT INTO S_SPARM
	VALUES (524297,
	524298,
	'id',
	524291,
	0);
INSERT INTO S_SYNC
	VALUES (524299,
	53426,
	'PIO_operator_inserted_disk',
	'',
	'select any port from instances of D_P where (selected.Port_ID == param.id);
generate D_P4:''Operator Inserts Disk''() to port;',
	524289,
	1);
INSERT INTO S_SPARM
	VALUES (524298,
	524299,
	'id',
	524291,
	0);
INSERT INTO S_SYNC
	VALUES (524300,
	53426,
	'PIO_port_locked',
	'',
	'select any port from instances of D_P where (selected.Port_ID == param.id);
generate D_P2:''EE Port Locked''() to port;',
	524289,
	1);
INSERT INTO S_SPARM
	VALUES (524299,
	524300,
	'id',
	524291,
	0);
INSERT INTO S_SYNC
	VALUES (524301,
	53426,
	'QP_disk_unmount_request',
	'',
	'select any disk from instances of D_D where (selected.Disk_ID == param.disk_id);
if (not_empty disk)
  generate D_D4:''Disk Unmount Request''() to disk;
end if;',
	524289,
	1);
INSERT INTO S_SPARM
	VALUES (524300,
	524301,
	'disk_id',
	524291,
	0);
INSERT INTO S_SYNC
	VALUES (524302,
	53426,
	'QP_disk_mount_request',
	'',
	'select any qp from instances of D_QP where (selected.Qualified_Process_ID == param.qp_id);
generate D_QP1:''Mount Disk''(disk_id:param.disk_id) to qp;',
	524289,
	1);
INSERT INTO S_SPARM
	VALUES (524301,
	524302,
	'disk_id',
	524291,
	0);
INSERT INTO S_SPARM
	VALUES (524302,
	524302,
	'qp_id',
	524291,
	0);
INSERT INTO S_SYNC
	VALUES (524303,
	53426,
	'test_class_evt_ignored',
	'',
	'generate D_DO_A1:''Disk Requested by QP'' to D_DO class;',
	524289,
	1);
INSERT INTO S_SYNC
	VALUES (524304,
	53426,
	'test_robot',
	'',
	'create object instance second_rbt of D_R;',
	524289,
	1);
INSERT INTO S_SYNC
	VALUES (524305,
	53426,
	'test_request_pending',
	'',
	'select any rbt from instances of D_R where (selected.Robot_ID == param.id);
generate D_R5:''Robot Request Pending''() to rbt;',
	524289,
	1);
INSERT INTO S_SPARM
	VALUES (524303,
	524305,
	'id',
	524291,
	0);
INSERT INTO S_EE
	VALUES (524289,
	'PIO',
	'',
	'pio1',
	53426);
INSERT INTO S_BRG
	VALUES (524289,
	524289,
	'load_stepper_motors',
	'',
	0,
	524289,
	'::PIO_robot_move_complete(id:param.robot_id);',
	1);
INSERT INTO S_BPARM
	VALUES (524304,
	524289,
	'robot_id',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524305,
	524289,
	'x',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524306,
	524289,
	'y',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524307,
	524289,
	'theta',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524308,
	524289,
	'tvs',
	524306,
	0);
INSERT INTO S_BRG
	VALUES (524290,
	524289,
	'extend_hand',
	'',
	0,
	524289,
	'::PIO_robot_move_complete(id:param.robot_id);',
	1);
INSERT INTO S_BPARM
	VALUES (524309,
	524290,
	'robot_id',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524310,
	524290,
	'tvs',
	524306,
	0);
INSERT INTO S_BRG
	VALUES (524291,
	524289,
	'close_gripper',
	'',
	0,
	524289,
	'::PIO_gripper_closed_on_disk(id:param.robot_id);',
	1);
INSERT INTO S_BPARM
	VALUES (524311,
	524291,
	'robot_id',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524312,
	524291,
	'tvs',
	524306,
	0);
INSERT INTO S_BRG
	VALUES (524292,
	524289,
	'retract_hand',
	'',
	0,
	524289,
	'::PIO_robot_move_complete(id:param.robot_id);',
	1);
INSERT INTO S_BPARM
	VALUES (524313,
	524292,
	'robot_id',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524314,
	524292,
	'tvs',
	524306,
	0);
INSERT INTO S_BRG
	VALUES (524293,
	524289,
	'open_gripper',
	'',
	0,
	524289,
	'::PIO_gripper_opened(id:param.robot_id);',
	1);
INSERT INTO S_BPARM
	VALUES (524315,
	524293,
	'robot_id',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524316,
	524293,
	'tvs',
	524306,
	0);
INSERT INTO S_BRG
	VALUES (524294,
	524289,
	'unlock_door',
	'',
	0,
	524289,
	'::PIO_port_unlocked(id:param.ee_id);',
	1);
INSERT INTO S_BPARM
	VALUES (524317,
	524294,
	'ee_id',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524318,
	524294,
	'tvs',
	524306,
	0);
INSERT INTO S_BRG
	VALUES (524295,
	524289,
	'lock_door',
	'',
	0,
	524289,
	'::PIO_port_locked(id:param.ee_id);',
	1);
INSERT INTO S_BPARM
	VALUES (524319,
	524295,
	'ee_id',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524320,
	524295,
	'tvs',
	524306,
	0);
INSERT INTO S_BRG
	VALUES (524296,
	524289,
	'get_disk',
	'',
	0,
	524289,
	'select any port from instances of D_P where (selected.Port_ID == param.ee_id);
select one transfer related by port->D_DT[R8];
if (transfer.Source_ID == port.Port_ID)
  ::PIO_operator_opened_door(id:port.Port_ID);
  ::PIO_operator_inserted_disk(id:port.Port_ID);
  ::PIO_operator_closed_door(id:port.Port_ID);
else
  ::PIO_operator_opened_door(id:port.Port_ID);
  ::PIO_operator_removed_disk(id:port.Port_ID);
  ::PIO_operator_closed_door(id:port.Port_ID);
end if;',
	1);
INSERT INTO S_BPARM
	VALUES (524321,
	524296,
	'ee_id',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524322,
	524296,
	'disk_id',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524323,
	524296,
	'tvs1',
	524306,
	0);
INSERT INTO S_BPARM
	VALUES (524324,
	524296,
	'tvs2',
	524306,
	0);
INSERT INTO S_BPARM
	VALUES (524325,
	524296,
	'tvs3',
	524306,
	0);
INSERT INTO S_BRG
	VALUES (524297,
	524289,
	'spin_up_drive',
	'',
	0,
	524289,
	'::PIO_drive_at_spin_speed(id:param.drive_id);',
	1);
INSERT INTO S_BPARM
	VALUES (524326,
	524297,
	'drive_id',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524327,
	524297,
	'tvs',
	524306,
	0);
INSERT INTO S_BRG
	VALUES (524298,
	524289,
	'spin_down_drive',
	'',
	0,
	524289,
	'::PIO_drive_stopped(id:param.drive_id);',
	1);
INSERT INTO S_BPARM
	VALUES (524328,
	524298,
	'drive_id',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524329,
	524298,
	'tvs',
	524306,
	0);
INSERT INTO S_EE
	VALUES (524290,
	'QP',
	'',
	'qp1',
	53426);
INSERT INTO S_BRG
	VALUES (524299,
	524290,
	'disk_mount_done',
	'',
	0,
	524289,
	'',
	1);
INSERT INTO S_BPARM
	VALUES (524330,
	524299,
	'disk_id',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524331,
	524299,
	'drive_id',
	524291,
	0);
INSERT INTO S_BPARM
	VALUES (524332,
	524299,
	'qp_id',
	524291,
	0);
INSERT INTO S_EEEDI
	VALUES (524289,
	524290,
	'qp_id',
	'',
	524291);
INSERT INTO S_EEEDI
	VALUES (524290,
	524290,
	'disk_id',
	'',
	524291);
INSERT INTO S_EEEDI
	VALUES (524291,
	524290,
	'drive_id',
	'',
	524291);
INSERT INTO GD_MD
	VALUES (524289,
	1,
	53426,
	1,
	1,
	0,
	1,
	1,
	0,
	12,
	1600,
	4349,
	1.000000,
	0);
INSERT INTO GD_GE
	VALUES (524295,
	524289,
	1048578,
	11);
INSERT INTO GD_SHP
	VALUES (524295,
	1920,
	1344,
	2080,
	1440);
INSERT INTO GD_GE
	VALUES (524293,
	524289,
	524289,
	12);
INSERT INTO GD_SHP
	VALUES (524293,
	1792,
	1504,
	1952,
	1600);
INSERT INTO GD_GE
	VALUES (524294,
	524289,
	524290,
	12);
INSERT INTO GD_SHP
	VALUES (524294,
	2048,
	1504,
	2208,
	1600);
INSERT INTO S_SS
	VALUES (1048578,
	'Odms',
	'This is the main subsystem for the Optical Disk Management System model.',
	'D',
	1,
	53426,
	1048578);
INSERT INTO O_OBJ
	VALUES (1048577,
	'ƒaƒXƒN',
	1,
	'D_D',
	'In this model, a Disk refers to an optical disk that is used to store data
records.  All disks in the optical disk management system are required to have a
permanently assigned home in the external disk library.  Each disk in the system
is also required to be labeled with its disk identifier.  Disks can be one of
two types: offline or online.  When a disk is in its home in the external
library or moving its name to home, it is an offline disk.  When it is in the
cabinet or moving to the cabinet, it is accessible by applications, and so is
termed an online disk.
',
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
	'Disk_ID',
	'Every disk in the system is assigned a unique disk identifier.  This identifier
is written on the disk itself in machine-readable form so that the optical disk
management system can keep track of it.  The disk identifier must also be placed
on the outside of the disk in a humah-readable form so that an operator can
identify it.
',
	'',
	'Disk_ID',
	0,
	524291);
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
	'Serial_Number',
	'Disks are bought from various manufacturers.  Each manufacturer stamps the disks
with serial numbers to distinguish them from other disks being manufactured.  It
would be convenient to simply use the manufacturers serial number as the unique
identifier for the disk.  This cannot be done, however, because there is nothing
that prevents the exact same serial number being stamped on two different disks
made by two different manufacturers.
',
	'',
	'Serial_Number',
	0,
	524291);
INSERT INTO O_REF
	VALUES (1048577,
	1048578,
	0,
	1048589,
	1048577,
	1048577,
	1048578,
	1048579,
	1048584,
	0,
	0,
	'Reference Domain:
');
INSERT INTO O_RATTR
	VALUES (1048579,
	1048577,
	1048589,
	1048578,
	1);
INSERT INTO O_ATTR
	VALUES (1048579,
	1048577,
	1048578,
	'Row_Number',
	'The row number in the external library in which the disk is stored when it is
offline.  This attribute, together with Disk.Column Number, formalizes the
association Disk IS PERMANENTLY ASSIGNED TO Permanent Home in External Library.
',
	'',
	'Row_Number',
	0,
	524296);
INSERT INTO O_REF
	VALUES (1048577,
	1048578,
	0,
	1048590,
	1048577,
	1048577,
	1048578,
	1048580,
	1048585,
	0,
	0,
	'Reference Domain:
');
INSERT INTO O_RATTR
	VALUES (1048580,
	1048577,
	1048590,
	1048578,
	1);
INSERT INTO O_ATTR
	VALUES (1048580,
	1048577,
	1048579,
	'Column_Number',
	'The column number in the external library in which the disk is stored when it is
offline.  Row number and column number together pinpoint the disks location in
the library.  This attribute, together with Disk.Row Number, formalizes the
association Disk IS PERMANENTLY ASSIGNED TO Permanent Home in External Library.
',
	'',
	'Column_Number',
	0,
	524296);
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
	'Number_of_Bytes_Available',
	'The number of bytes on the disk that are currently available for writing.',
	'',
	'Number_of_Bytes_Available',
	0,
	524291);
INSERT INTO O_NBATTR
	VALUES (1048582,
	1048577);
INSERT INTO O_BATTR
	VALUES (1048582,
	1048577);
INSERT INTO O_ATTR
	VALUES (1048582,
	1048577,
	1048581,
	'Address_to_Start_Writing',
	'The address of the first sector which is writable.',
	'',
	'Address_to_Start_Writing',
	0,
	524291);
INSERT INTO O_NBATTR
	VALUES (1048583,
	1048577);
INSERT INTO O_BATTR
	VALUES (1048583,
	1048577);
INSERT INTO O_ATTR
	VALUES (1048583,
	1048577,
	1048582,
	'Write_Protect_ Status',
	'Disks can be write-protected if desired.  This attribute records the
write-protect status of the disk.
',
	'',
	'Write_Protect_ Status',
	0,
	524290);
INSERT INTO O_NBATTR
	VALUES (1048584,
	1048577);
INSERT INTO O_BATTR
	VALUES (1048584,
	1048577);
INSERT INTO O_ATTR
	VALUES (1048584,
	1048577,
	1048583,
	'Waiting_for_Slot',
	'This attribute is set based on the current state of the state machine, it is
always FALSE except when in the "Waiting for Slot" state.',
	'',
	'Waiting_for_Slot',
	0,
	524290);
INSERT INTO O_NBATTR
	VALUES (1048585,
	1048577);
INSERT INTO O_BATTR
	VALUES (1048585,
	1048577);
INSERT INTO O_ATTR
	VALUES (1048585,
	1048577,
	1048584,
	'Waiting_for_Drive',
	'This attribute is TRUE only when the state machine is in the Waiting for Drive
state.',
	'',
	'Waiting_for_Drive',
	0,
	524290);
INSERT INTO O_NBATTR
	VALUES (1048586,
	1048577);
INSERT INTO O_BATTR
	VALUES (1048586,
	1048577);
INSERT INTO O_ATTR
	VALUES (1048586,
	1048577,
	1048585,
	'Unowned_in_Cabinet',
	'This attribute is TRUE only when the state machine is in the Unowned in Cabinet
state.',
	'',
	'Unowned_in_Cabinet',
	0,
	524290);
INSERT INTO O_NBATTR
	VALUES (1048587,
	1048577);
INSERT INTO O_BATTR
	VALUES (1048587,
	1048577);
INSERT INTO O_ATTR
	VALUES (1048587,
	1048577,
	1048586,
	'Unowned_in_Library',
	'This attribute is TRUE only when the state machine is in the Unowned in Library
state.',
	'',
	'Unowned_in_Library',
	0,
	524290);
INSERT INTO O_NBATTR
	VALUES (1048588,
	1048577);
INSERT INTO O_BATTR
	VALUES (1048588,
	1048577);
INSERT INTO O_ATTR
	VALUES (1048588,
	1048577,
	1048587,
	'current_state',
	'The current state of the Disk.',
	'',
	'current_state',
	0,
	524295);
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
	1048578,
	1048579);
INSERT INTO O_RTIDA
	VALUES (1048577,
	1048577,
	0,
	1048579,
	1048583);
INSERT INTO O_RTIDA
	VALUES (1048577,
	1048577,
	0,
	1048580,
	1048586);
INSERT INTO SM_ISM
	VALUES (1572867,
	1048577);
INSERT INTO SM_SM
	VALUES (1572867,
	'',
	3);
INSERT INTO SM_MOORE
	VALUES (1572867);
INSERT INTO SM_SUPDT
	VALUES (1572865,
	1572867,
	0);
INSERT INTO SM_LEVT
	VALUES (1572865,
	1572867,
	1572865);
INSERT INTO SM_SEVT
	VALUES (1572865,
	1572867,
	1572865);
INSERT INTO SM_EVT
	VALUES (1572865,
	1572867,
	1572865,
	1,
	'Disk Ownership Established',
	0,
	'',
	'D_D1',
	'');
INSERT INTO SM_LEVT
	VALUES (1572866,
	1572867,
	1572865);
INSERT INTO SM_SEVT
	VALUES (1572866,
	1572867,
	1572865);
INSERT INTO SM_EVT
	VALUES (1572866,
	1572867,
	1572865,
	2,
	'Disk Arrived in Slot',
	0,
	'',
	'D_D2',
	'');
INSERT INTO SM_LEVT
	VALUES (1572867,
	1572867,
	1572865);
INSERT INTO SM_SEVT
	VALUES (1572867,
	1572867,
	1572865);
INSERT INTO SM_EVT
	VALUES (1572867,
	1572867,
	1572865,
	3,
	'Disk in Drive',
	0,
	'',
	'D_D3',
	'');
INSERT INTO SM_LEVT
	VALUES (1572868,
	1572867,
	1572865);
INSERT INTO SM_SEVT
	VALUES (1572868,
	1572867,
	1572865);
INSERT INTO SM_EVT
	VALUES (1572868,
	1572867,
	1572865,
	4,
	'Disk Unmount Request',
	0,
	'',
	'D_D4',
	'');
INSERT INTO SM_LEVT
	VALUES (1572869,
	1572867,
	1572865);
INSERT INTO SM_SEVT
	VALUES (1572869,
	1572867,
	1572865);
INSERT INTO SM_EVT
	VALUES (1572869,
	1572867,
	1572865,
	5,
	'Disk Returned to Slot',
	0,
	'',
	'D_D5',
	'');
INSERT INTO SM_LEVT
	VALUES (1572870,
	1572867,
	1572865);
INSERT INTO SM_SEVT
	VALUES (1572870,
	1572867,
	1572865);
INSERT INTO SM_EVT
	VALUES (1572870,
	1572867,
	1572865,
	6,
	'Return Unowned Disk to Library',
	0,
	'',
	'D_D6',
	'');
INSERT INTO SM_LEVT
	VALUES (1572871,
	1572867,
	1572865);
INSERT INTO SM_SEVT
	VALUES (1572871,
	1572867,
	1572865);
INSERT INTO SM_EVT
	VALUES (1572871,
	1572867,
	1572865,
	7,
	'Disk Left Slot on way to Library',
	0,
	'',
	'D_D7',
	'');
INSERT INTO SM_STATE
	VALUES (1572865,
	1572867,
	1572865,
	'Unowned in Library',
	1,
	0);
INSERT INTO SM_SEME
	VALUES (1572865,
	1572865,
	1572867,
	1572865);
INSERT INTO SM_EIGN
	VALUES (1572865,
	1572866,
	1572867,
	1572865,
	'');
INSERT INTO SM_SEME
	VALUES (1572865,
	1572866,
	1572867,
	1572865);
INSERT INTO SM_EIGN
	VALUES (1572865,
	1572867,
	1572867,
	1572865,
	'');
INSERT INTO SM_SEME
	VALUES (1572865,
	1572867,
	1572867,
	1572865);
INSERT INTO SM_EIGN
	VALUES (1572865,
	1572868,
	1572867,
	1572865,
	'');
INSERT INTO SM_SEME
	VALUES (1572865,
	1572868,
	1572867,
	1572865);
INSERT INTO SM_EIGN
	VALUES (1572865,
	1572869,
	1572867,
	1572865,
	'');
INSERT INTO SM_SEME
	VALUES (1572865,
	1572869,
	1572867,
	1572865);
INSERT INTO SM_EIGN
	VALUES (1572865,
	1572870,
	1572867,
	1572865,
	'');
INSERT INTO SM_SEME
	VALUES (1572865,
	1572870,
	1572867,
	1572865);
INSERT INTO SM_EIGN
	VALUES (1572865,
	1572871,
	1572867,
	1572865,
	'');
INSERT INTO SM_SEME
	VALUES (1572865,
	1572871,
	1572867,
	1572865);
INSERT INTO SM_STATE
	VALUES (1572866,
	1572867,
	1572865,
	'Waiting for Slot',
	2,
	0);
INSERT INTO SM_EIGN
	VALUES (1572866,
	1572865,
	1572867,
	1572865,
	'');
INSERT INTO SM_SEME
	VALUES (1572866,
	1572865,
	1572867,
	1572865);
INSERT INTO SM_SEME
	VALUES (1572866,
	1572866,
	1572867,
	1572865);
INSERT INTO SM_EIGN
	VALUES (1572866,
	1572867,
	1572867,
	1572865,
	'');
INSERT INTO SM_SEME
	VALUES (1572866,
	1572867,
	1572867,
	1572865);
INSERT INTO SM_EIGN
	VALUES (1572866,
	1572868,
	1572867,
	1572865,
	'');
INSERT INTO SM_SEME
	VALUES (1572866,
	1572868,
	1572867,
	1572865);
INSERT INTO SM_EIGN
	VALUES (1572866,
	1572869,
	1572867,
	1572865,
	'');
INSERT INTO SM_SEME
	VALUES (1572866,
	1572869,
	1572867,
	1572865);
INSERT INTO SM_EIGN
	VALUES (1572866,
	1572870,
	1572867,
	1572865,
	'');
INSERT INTO SM_SEME
	VALUES (1572866,
	1572870,
	1572867,
	1572865);
INSERT INTO SM_EIGN
	VALUES (1572866,
	1572871,
	1572867,
	1572865,
	'');
INSERT INTO SM_SEME
	VALUES (1572866,
	1572871,
	1572867,
	1572865);
INSERT INTO SM_STATE
	VALUES (1572867,
	1572867,
	1572865,
	'Waiting for Drive',
	3,
	0);
INSERT INTO SM_EIGN
	VALUES (1572867,
	1572865,
	1572867,
	1572865,
	'');
INSERT INTO SM_SEME
	VALUES (1572867,
	1572865,
	1572867,
	1572865);
INSERT INTO SM_EIGN
	VALUES (1572867,
	1572866,
	1572867,
	1572865,
	'');
INSERT INTO SM_SEME
	VALUES (1572867,
	1572866,
	1572867,
	1572865);
INSERT INTO SM_SEME
	VALUES (1572867,
	1572867,
	1572867,
	1572865);
INSERT INTO SM_EIGN
	VALUES (1572867,
	1572868,
	1572867,
	1572865,
	'');
INSERT INTO SM_SEME
	VALUES (1572867,
	1572868,
	1572867,
	1572865);
INSERT INTO SM_EIGN
	VALUES (1572867,
	1572869,
	1572867,
	1572865,
	'');
INSERT INTO SM_SEME
	VALUES (1572867,
	1572869,
	1572867,
	1572865);
INSERT INTO SM_EIGN
	VALUES (1572867,
	1572870,
	1572867,
	1572865,
	'');
INSERT INTO SM_SEME
	VALUES (1572867,
	1572870,
	1572867,
	1572865);
INSERT INTO SM_EIGN
	VALUES (1572867,
	1572871,
	1572867,
	1572865,
	'');
INSERT INTO SM_SEME
	VALUES (1572867,
	1572871,
	1572867,
	1572865);
INSERT INTO SM_STATE
	VALUES (1572868,
	1572867,
	1572865,
	'In Drive',
	4,
	0);
INSERT INTO SM_EIGN
	VALUES (1572868,
	1572865,
	1572867,
	1572865,
	'');
INSERT INTO SM_SEME
	VALUES (1572868,
	1572865,
	1572867,
	1572865);
INSERT INTO SM_EIGN
	VALUES (1572868,
	1572866,
	1572867,
	1572865,
	'');
INSERT INTO SM_SEME
	VALUES (1572868,
	1572866,
	1572867,
	1572865);
INSERT INTO SM_EIGN
	VALUES (1572868,
	1572867,
	1572867,
	1572865,
	'');
INSERT INTO SM_SEME
	VALUES (1572868,
	1572867,
	1572867,
	1572865);
INSERT INTO SM_SEME
	VALUES (1572868,
	1572868,
	1572867,
	1572865);
INSERT INTO SM_EIGN
	VALUES (1572868,
	1572869,
	1572867,
	1572865,
	'');
INSERT INTO SM_SEME
	VALUES (1572868,
	1572869,
	1572867,
	1572865);
INSERT INTO SM_EIGN
	VALUES (1572868,
	1572870,
	1572867,
	1572865,
	'');
INSERT INTO SM_SEME
	VALUES (1572868,
	1572870,
	1572867,
	1572865);
INSERT INTO SM_EIGN
	VALUES (1572868,
	1572871,
	1572867,
	1572865,
	'');
INSERT INTO SM_SEME
	VALUES (1572868,
	1572871,
	1572867,
	1572865);
INSERT INTO SM_STATE
	VALUES (1572869,
	1572867,
	1572865,
	'Leaving Drive',
	5,
	0);
INSERT INTO SM_EIGN
	VALUES (1572869,
	1572865,
	1572867,
	1572865,
	'');
INSERT INTO SM_SEME
	VALUES (1572869,
	1572865,
	1572867,
	1572865);
INSERT INTO SM_EIGN
	VALUES (1572869,
	1572866,
	1572867,
	1572865,
	'');
INSERT INTO SM_SEME
	VALUES (1572869,
	1572866,
	1572867,
	1572865);
INSERT INTO SM_EIGN
	VALUES (1572869,
	1572867,
	1572867,
	1572865,
	'');
INSERT INTO SM_SEME
	VALUES (1572869,
	1572867,
	1572867,
	1572865);
INSERT INTO SM_EIGN
	VALUES (1572869,
	1572868,
	1572867,
	1572865,
	'');
INSERT INTO SM_SEME
	VALUES (1572869,
	1572868,
	1572867,
	1572865);
INSERT INTO SM_SEME
	VALUES (1572869,
	1572869,
	1572867,
	1572865);
INSERT INTO SM_EIGN
	VALUES (1572869,
	1572870,
	1572867,
	1572865,
	'');
INSERT INTO SM_SEME
	VALUES (1572869,
	1572870,
	1572867,
	1572865);
INSERT INTO SM_EIGN
	VALUES (1572869,
	1572871,
	1572867,
	1572865,
	'');
INSERT INTO SM_SEME
	VALUES (1572869,
	1572871,
	1572867,
	1572865);
INSERT INTO SM_STATE
	VALUES (1572870,
	1572867,
	1572865,
	'Unowned in Cabinet',
	6,
	0);
INSERT INTO SM_SEME
	VALUES (1572870,
	1572865,
	1572867,
	1572865);
INSERT INTO SM_EIGN
	VALUES (1572870,
	1572866,
	1572867,
	1572865,
	'');
INSERT INTO SM_SEME
	VALUES (1572870,
	1572866,
	1572867,
	1572865);
INSERT INTO SM_EIGN
	VALUES (1572870,
	1572867,
	1572867,
	1572865,
	'');
INSERT INTO SM_SEME
	VALUES (1572870,
	1572867,
	1572867,
	1572865);
INSERT INTO SM_EIGN
	VALUES (1572870,
	1572868,
	1572867,
	1572865,
	'');
INSERT INTO SM_SEME
	VALUES (1572870,
	1572868,
	1572867,
	1572865);
INSERT INTO SM_EIGN
	VALUES (1572870,
	1572869,
	1572867,
	1572865,
	'');
INSERT INTO SM_SEME
	VALUES (1572870,
	1572869,
	1572867,
	1572865);
INSERT INTO SM_SEME
	VALUES (1572870,
	1572870,
	1572867,
	1572865);
INSERT INTO SM_EIGN
	VALUES (1572870,
	1572871,
	1572867,
	1572865,
	'');
INSERT INTO SM_SEME
	VALUES (1572870,
	1572871,
	1572867,
	1572865);
INSERT INTO SM_STATE
	VALUES (1572871,
	1572867,
	1572865,
	'Leaving Cabinet',
	7,
	0);
INSERT INTO SM_EIGN
	VALUES (1572871,
	1572865,
	1572867,
	1572865,
	'');
INSERT INTO SM_SEME
	VALUES (1572871,
	1572865,
	1572867,
	1572865);
INSERT INTO SM_EIGN
	VALUES (1572871,
	1572866,
	1572867,
	1572865,
	'');
INSERT INTO SM_SEME
	VALUES (1572871,
	1572866,
	1572867,
	1572865);
INSERT INTO SM_EIGN
	VALUES (1572871,
	1572867,
	1572867,
	1572865,
	'');
INSERT INTO SM_SEME
	VALUES (1572871,
	1572867,
	1572867,
	1572865);
INSERT INTO SM_EIGN
	VALUES (1572871,
	1572868,
	1572867,
	1572865,
	'');
INSERT INTO SM_SEME
	VALUES (1572871,
	1572868,
	1572867,
	1572865);
INSERT INTO SM_EIGN
	VALUES (1572871,
	1572869,
	1572867,
	1572865,
	'');
INSERT INTO SM_SEME
	VALUES (1572871,
	1572869,
	1572867,
	1572865);
INSERT INTO SM_EIGN
	VALUES (1572871,
	1572870,
	1572867,
	1572865,
	'');
INSERT INTO SM_SEME
	VALUES (1572871,
	1572870,
	1572867,
	1572865);
INSERT INTO SM_SEME
	VALUES (1572871,
	1572871,
	1572867,
	1572865);
INSERT INTO SM_NSTXN
	VALUES (1572865,
	1572867,
	1572865,
	1572865,
	1572865);
INSERT INTO SM_TXN
	VALUES (1572865,
	1572867,
	1572866,
	1572865);
INSERT INTO SM_NSTXN
	VALUES (1572866,
	1572867,
	1572866,
	1572866,
	1572865);
INSERT INTO SM_TXN
	VALUES (1572866,
	1572867,
	1572867,
	1572865);
INSERT INTO SM_NSTXN
	VALUES (1572867,
	1572867,
	1572867,
	1572867,
	1572865);
INSERT INTO SM_TXN
	VALUES (1572867,
	1572867,
	1572868,
	1572865);
INSERT INTO SM_NSTXN
	VALUES (1572868,
	1572867,
	1572868,
	1572868,
	1572865);
INSERT INTO SM_TXN
	VALUES (1572868,
	1572867,
	1572869,
	1572865);
INSERT INTO SM_NSTXN
	VALUES (1572869,
	1572867,
	1572870,
	1572865,
	1572865);
INSERT INTO SM_TXN
	VALUES (1572869,
	1572867,
	1572867,
	1572865);
INSERT INTO SM_NSTXN
	VALUES (1572870,
	1572867,
	1572869,
	1572869,
	1572865);
INSERT INTO SM_TXN
	VALUES (1572870,
	1572867,
	1572870,
	1572865);
INSERT INTO SM_NSTXN
	VALUES (1572871,
	1572867,
	1572871,
	1572871,
	1572865);
INSERT INTO SM_TXN
	VALUES (1572871,
	1572867,
	1572865,
	1572865);
INSERT INTO SM_NSTXN
	VALUES (1572872,
	1572867,
	1572870,
	1572870,
	1572865);
INSERT INTO SM_TXN
	VALUES (1572872,
	1572867,
	1572871,
	1572865);
INSERT INTO SM_MOAH
	VALUES (1572865,
	1572867,
	1572865);
INSERT INTO SM_AH
	VALUES (1572865,
	1572867);
INSERT INTO SM_ACT
	VALUES (1572865,
	1572867,
	1,
	'Assign Self.Unowned_in_Library = TRUE;

Generate D_DO_A5:''Disk Ownership Released'' () to D_DO assigner;',
	'');
INSERT INTO SM_MOAH
	VALUES (1572866,
	1572867,
	1572866);
INSERT INTO SM_AH
	VALUES (1572866,
	1572867);
INSERT INTO SM_ACT
	VALUES (1572866,
	1572867,
	1,
	'Assign Self.Unowned_in_Library = FALSE;
Assign Self.Waiting_for_Slot = TRUE;

Select one offline_disk related by Self->D_OD[R3];
Delete object instance offline_disk;

Create object instance online_disk of D_OND;
Relate Self to online_disk across R3;

Generate D_SDA_A1:''Request Slot'' () to D_SDA assigner;
Generate D_DO_A7:''Disk Now Online'' (serial_number:Self.Serial_Number) to D_DO assigner;',
	'');
INSERT INTO SM_MOAH
	VALUES (1572867,
	1572867,
	1572867);
INSERT INTO SM_AH
	VALUES (1572867,
	1572867);
INSERT INTO SM_ACT
	VALUES (1572867,
	1572867,
	1,
	'Assign Self.Waiting_for_Slot = FALSE;
Assign Self.Unowned_in_Cabinet = FALSE;
Assign Self.Waiting_for_Drive = TRUE;

Generate D_DDA_A1:''Disk Waiting for Drive'' () to D_DDA assigner;',
	'');
INSERT INTO SM_MOAH
	VALUES (1572868,
	1572867,
	1572868);
INSERT INTO SM_AH
	VALUES (1572868,
	1572867);
INSERT INTO SM_ACT
	VALUES (1572868,
	1572867,
	1,
	'// foo:bar
Assign Self.Waiting_for_Drive = FALSE;


Select one process Related by Self->D_DO[R2]->D_QP[R2];
Select one drive related by self->D_OND[R3]->D_DDA[R5]->D_DR[R5];

Bridge qp1::disk_mount_done(qp_id:process.Qualified_Process_ID,
      drive_id:drive.Drive_ID,
      disk_id:self.Disk_ID);
',
	'');
INSERT INTO SM_MOAH
	VALUES (1572869,
	1572867,
	1572869);
INSERT INTO SM_AH
	VALUES (1572869,
	1572867);
INSERT INTO SM_ACT
	VALUES (1572869,
	1572867,
	1,
	'Select one drive related by Self->D_OND[R3]->D_DDA[R5]->D_DR[R5];
Generate D_DR3:''Disk Through With Drive'' () to drive;',
	'');
INSERT INTO SM_MOAH
	VALUES (1572870,
	1572867,
	1572870);
INSERT INTO SM_AH
	VALUES (1572870,
	1572867);
INSERT INTO SM_ACT
	VALUES (1572870,
	1572867,
	1,
	'Assign Self.Unowned_in_Cabinet = TRUE;

Select one disk_owner related by Self->D_DO[R2];
Delete object instance disk_owner;

Generate D_DO_A5:''Disk Ownership Released'' () to D_DO assigner;',
	'');
INSERT INTO SM_MOAH
	VALUES (1572871,
	1572867,
	1572871);
INSERT INTO SM_AH
	VALUES (1572871,
	1572867);
INSERT INTO SM_ACT
	VALUES (1572871,
	1572867,
	1,
	'Assign Self.Unowned_in_Cabinet = FALSE;

Select one slot related by Self->D_OND[R3]->D_SDA[R6]->D_S[R6];

Generate D_DO_A8:''Disk Now Offline'' () to D_DO assigner;
Generate D_S2:''Free Up Slot'' () to slot;

Select one online_disk related by Self->D_OND[R3];
Select one slot_assignment related by online_disk->D_SDA[R6];
Delete object instance slot_assignment;
Delete object instance online_disk;

Create object instance offline_disk of D_OD;
Relate Self to offline_disk across R3;
',
	'');
INSERT INTO GD_MD
	VALUES (1572865,
	8,
	1572867,
	40,
	0,
	0,
	1,
	1,
	0,
	12,
	1600,
	3726,
	1.000000,
	0);
INSERT INTO GD_GE
	VALUES (1572866,
	1572865,
	1572871,
	41);
INSERT INTO GD_SHP
	VALUES (1572866,
	2224,
	1248,
	2608,
	1536);
INSERT INTO GD_GE
	VALUES (1572867,
	1572865,
	1572870,
	41);
INSERT INTO GD_SHP
	VALUES (1572867,
	2224,
	1712,
	2624,
	1872);
INSERT INTO GD_GE
	VALUES (1572868,
	1572865,
	1572869,
	41);
INSERT INTO GD_SHP
	VALUES (1572868,
	2224,
	1952,
	2544,
	2080);
INSERT INTO GD_GE
	VALUES (1572869,
	1572865,
	1572868,
	41);
INSERT INTO GD_SHP
	VALUES (1572869,
	1712,
	1952,
	2048,
	2144);
INSERT INTO GD_GE
	VALUES (1572870,
	1572865,
	1572867,
	41);
INSERT INTO GD_SHP
	VALUES (1572870,
	1616,
	1744,
	2016,
	1872);
INSERT INTO GD_GE
	VALUES (1572871,
	1572865,
	1572866,
	41);
INSERT INTO GD_SHP
	VALUES (1572871,
	1584,
	1424,
	2096,
	1664);
INSERT INTO GD_GE
	VALUES (1572872,
	1572865,
	1572865,
	41);
INSERT INTO GD_SHP
	VALUES (1572872,
	1616,
	1248,
	2016,
	1344);
INSERT INTO GD_GE
	VALUES (1572873,
	1572865,
	1572871,
	42);
INSERT INTO GD_CON
	VALUES (1572873,
	1572866,
	1572872,
	0);
INSERT INTO GD_CTXT
	VALUES (1572873,
	0,
	0,
	0,
	0,
	0,
	0,
	2036,
	1256,
	2194,
	1295,
	-59,
	-15,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (1572874,
	1572873,
	2224,
	1296,
	2016,
	1296,
	0);
INSERT INTO GD_GE
	VALUES (1572875,
	1572865,
	1572872,
	42);
INSERT INTO GD_CON
	VALUES (1572875,
	1572867,
	1572866,
	0);
INSERT INTO GD_CTXT
	VALUES (1572875,
	0,
	0,
	0,
	0,
	0,
	0,
	2417,
	1579,
	2644,
	1617,
	17,
	-6,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (1572876,
	1572875,
	2416,
	1712,
	2416,
	1536,
	0);
INSERT INTO GD_GE
	VALUES (1572877,
	1572865,
	1572869,
	42);
INSERT INTO GD_CON
	VALUES (1572877,
	1572867,
	1572870,
	0);
INSERT INTO GD_CTXT
	VALUES (1572877,
	0,
	0,
	0,
	0,
	0,
	0,
	2039,
	1759,
	2204,
	1806,
	-56,
	-24,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (1572878,
	1572877,
	2224,
	1808,
	2016,
	1808,
	0);
INSERT INTO GD_GE
	VALUES (1572879,
	1572865,
	1572870,
	42);
INSERT INTO GD_CON
	VALUES (1572879,
	1572868,
	1572867,
	0);
INSERT INTO GD_CTXT
	VALUES (1572879,
	0,
	0,
	0,
	0,
	0,
	0,
	2367,
	1895,
	2550,
	1935,
	15,
	-2,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (1572880,
	1572879,
	2368,
	1952,
	2368,
	1872,
	0);
INSERT INTO GD_GE
	VALUES (1572881,
	1572865,
	1572868,
	42);
INSERT INTO GD_CON
	VALUES (1572881,
	1572869,
	1572868,
	0);
INSERT INTO GD_CTXT
	VALUES (1572881,
	0,
	0,
	0,
	0,
	0,
	0,
	2032,
	1972,
	2209,
	2014,
	-63,
	-19,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (1572882,
	1572881,
	2048,
	2016,
	2224,
	2016,
	0);
INSERT INTO GD_GE
	VALUES (1572883,
	1572865,
	1572867,
	42);
INSERT INTO GD_CON
	VALUES (1572883,
	1572870,
	1572869,
	0);
INSERT INTO GD_CTXT
	VALUES (1572883,
	0,
	0,
	0,
	0,
	0,
	0,
	1808,
	1896,
	1971,
	1941,
	0,
	-1,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (1572884,
	1572883,
	1824,
	1872,
	1824,
	1952,
	0);
INSERT INTO GD_GE
	VALUES (1572885,
	1572865,
	1572866,
	42);
INSERT INTO GD_CON
	VALUES (1572885,
	1572871,
	1572870,
	0);
INSERT INTO GD_CTXT
	VALUES (1572885,
	0,
	0,
	0,
	0,
	0,
	0,
	1808,
	1688,
	2000,
	1731,
	0,
	-1,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (1572886,
	1572885,
	1824,
	1664,
	1824,
	1744,
	0);
INSERT INTO GD_GE
	VALUES (1572887,
	1572865,
	1572865,
	42);
INSERT INTO GD_CON
	VALUES (1572887,
	1572872,
	1572871,
	0);
INSERT INTO GD_CTXT
	VALUES (1572887,
	0,
	0,
	0,
	0,
	0,
	0,
	1821,
	1370,
	2037,
	1410,
	13,
	-7,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (1572888,
	1572887,
	1824,
	1344,
	1824,
	1424,
	0);
INSERT INTO O_OBJ
	VALUES (1048578,
	'Permanent Home in Library',
	2,
	'D_H',
	'A location in the external library which can hold one optical disk.

The analysis is based on the assumption that the external library will consist
of a disk rack that has rows and columns.  A given location in the rack is
addressed by the row and column number.  Although the analysis assumes row and
column addressing of locations, the model could be easily modified to
incorporate a different addressing scheme.
',
	1048578);
INSERT INTO O_NBATTR
	VALUES (1048589,
	1048578);
INSERT INTO O_BATTR
	VALUES (1048589,
	1048578);
INSERT INTO O_ATTR
	VALUES (1048589,
	1048578,
	0,
	'Row_Number',
	'The row number of the location in the library.',
	'',
	'Row_Number',
	0,
	524291);
INSERT INTO O_NBATTR
	VALUES (1048590,
	1048578);
INSERT INTO O_BATTR
	VALUES (1048590,
	1048578);
INSERT INTO O_ATTR
	VALUES (1048590,
	1048578,
	1048589,
	'Column_Number',
	'The column number of the location in the library.',
	'',
	'Column_Number',
	0,
	524291);
INSERT INTO O_ID
	VALUES (0,
	1048578);
INSERT INTO O_OIDA
	VALUES (1048590,
	1048578,
	0);
INSERT INTO O_RTIDA
	VALUES (1048590,
	1048578,
	0,
	1048577,
	1048578);
INSERT INTO O_OIDA
	VALUES (1048589,
	1048578,
	0);
INSERT INTO O_RTIDA
	VALUES (1048589,
	1048578,
	0,
	1048577,
	1048578);
INSERT INTO O_OBJ
	VALUES (1048579,
	'Online Disk',
	4,
	'D_OND',
	'An online disk is a disk that is currently in the optical disk cabinet.',
	1048578);
INSERT INTO O_REF
	VALUES (1048579,
	1048577,
	0,
	1048577,
	1048578,
	1048580,
	1048579,
	1048591,
	1048577,
	0,
	0,
	'Reference Domain:
');
INSERT INTO O_RATTR
	VALUES (1048591,
	1048579,
	1048577,
	1048577,
	1);
INSERT INTO O_ATTR
	VALUES (1048591,
	1048579,
	0,
	'Disk_ID',
	'The identifier of the online disk.  This attribute also formalizes the
association Online Disk IS A (SUBTYPE OF) Disk.
',
	'',
	'Disk_ID',
	0,
	524296);
INSERT INTO O_ID
	VALUES (0,
	1048579);
INSERT INTO O_OIDA
	VALUES (1048591,
	1048579,
	0);
INSERT INTO O_RTIDA
	VALUES (1048591,
	1048579,
	0,
	1048582,
	1048593);
INSERT INTO O_RTIDA
	VALUES (1048591,
	1048579,
	0,
	1048583,
	1048595);
INSERT INTO O_OBJ
	VALUES (1048580,
	'Offline Disk',
	3,
	'D_OD',
	'A disk that is currently not in the optical disk cabinet.  Operational policy
requires that the disk be in its permanent home in the external library when it
is offline.
',
	1048578);
INSERT INTO O_REF
	VALUES (1048580,
	1048577,
	0,
	1048577,
	1048578,
	1048581,
	1048579,
	1048592,
	1048578,
	0,
	0,
	'Reference Domain:
');
INSERT INTO O_RATTR
	VALUES (1048592,
	1048580,
	1048577,
	1048577,
	1);
INSERT INTO O_ATTR
	VALUES (1048592,
	1048580,
	0,
	'Disk_ID',
	'The identifier of the offline disk.  This attribute also formalizes the
association Offline Disk IS A (SUBTYPE OF) Disk.
',
	'',
	'Disk_ID',
	0,
	524296);
INSERT INTO O_ID
	VALUES (0,
	1048580);
INSERT INTO O_OIDA
	VALUES (1048592,
	1048580,
	0);
INSERT INTO O_OBJ
	VALUES (1048581,
	'Disk Request',
	10,
	'D_DQ',
	'A Disk Request is a request from a qualified process to mount a certain disk on
a drive.  Disk Requests are served as soon as possible, given that only one
qualified process can own a disk at a time.  See also description for Disk
Ownership.  A request from a qualified process to unmount (or dismount) a disk
is not considered a Disk Request.  Requests to unmount a disk are serviced
immediately.
',
	1048578);
INSERT INTO O_REF
	VALUES (1048581,
	1048582,
	0,
	1048599,
	1048579,
	1048584,
	1048582,
	1048593,
	1048586,
	0,
	0,
	'Reference Domain:
');
INSERT INTO O_RATTR
	VALUES (1048593,
	1048581,
	1048599,
	1048582,
	1);
INSERT INTO O_ATTR
	VALUES (1048593,
	1048581,
	0,
	'Qualified_Process_ID',
	'The identifier of the qualified process that has requested the disk.  This
attribute, together with Disk Request.Disk ID, formalizes the association
Qualified Process NEEDS Disk.
',
	'',
	'Qualified_Process_ID',
	0,
	524296);
INSERT INTO O_REF
	VALUES (1048581,
	1048577,
	0,
	1048577,
	1048579,
	1048584,
	1048583,
	1048594,
	1048587,
	0,
	0,
	'Reference Domain:
');
INSERT INTO O_RATTR
	VALUES (1048594,
	1048581,
	1048577,
	1048577,
	1);
INSERT INTO O_ATTR
	VALUES (1048594,
	1048581,
	1048593,
	'Disk_ID',
	'The identifier of the disk that is being requested.  This attribute, together
with Disk Request.Qualified Process ID formalizes the association Qualified
Process NEEDS Disk.
',
	'',
	'Disk_ID',
	0,
	524296);
INSERT INTO O_NBATTR
	VALUES (1048595,
	1048581);
INSERT INTO O_BATTR
	VALUES (1048595,
	1048581);
INSERT INTO O_ATTR
	VALUES (1048595,
	1048581,
	1048594,
	'Time_of_Request',
	'The time the Disk Request is made by the qualified process.',
	'',
	'Time_of_Request',
	0,
	524291);
INSERT INTO O_NBATTR
	VALUES (1048596,
	1048581);
INSERT INTO O_BATTR
	VALUES (1048596,
	1048581);
INSERT INTO O_ATTR
	VALUES (1048596,
	1048581,
	1048595,
	'Time_Request_Completed',
	'The time at which Disk Ownership was established, and so may be considered
satisfied.  Note that a disk request outlives ownership of the disk.
',
	'',
	'Time_Request_Completed',
	0,
	524291);
INSERT INTO O_NBATTR
	VALUES (1048597,
	1048581);
INSERT INTO O_BATTR
	VALUES (1048597,
	1048581);
INSERT INTO O_ATTR
	VALUES (1048597,
	1048581,
	1048596,
	'Request_Pending',
	'The status of a disk request.  Returns true if there is a disk request currently
pending.',
	'',
	'Request_Pending',
	0,
	524290);
INSERT INTO O_NBATTR
	VALUES (1048598,
	1048581);
INSERT INTO O_BATTR
	VALUES (1048598,
	1048581);
INSERT INTO O_ATTR
	VALUES (1048598,
	1048581,
	1048597,
	'current_state',
	'The current state of the Disk Request.',
	'',
	'current_state',
	0,
	524295);
INSERT INTO O_ID
	VALUES (0,
	1048581);
INSERT INTO O_OIDA
	VALUES (1048595,
	1048581,
	0);
INSERT INTO O_OIDA
	VALUES (1048593,
	1048581,
	0);
INSERT INTO O_OIDA
	VALUES (1048594,
	1048581,
	0);
INSERT INTO SM_ISM
	VALUES (2097156,
	1048581);
INSERT INTO SM_SM
	VALUES (2097156,
	'',
	4);
INSERT INTO SM_MOORE
	VALUES (2097156);
INSERT INTO SM_SUPDT
	VALUES (2097153,
	2097156,
	0);
INSERT INTO SM_LEVT
	VALUES (2097153,
	2097156,
	2097153);
INSERT INTO SM_SEVT
	VALUES (2097153,
	2097156,
	2097153);
INSERT INTO SM_EVT
	VALUES (2097153,
	2097156,
	2097153,
	1,
	'Disk Mount Request',
	0,
	'',
	'D_DQ1',
	'');
INSERT INTO SM_LEVT
	VALUES (2097154,
	2097156,
	2097153);
INSERT INTO SM_SEVT
	VALUES (2097154,
	2097156,
	2097153);
INSERT INTO SM_EVT
	VALUES (2097154,
	2097156,
	2097153,
	2,
	'Disk Request Satisfied',
	0,
	'',
	'D_DQ2',
	'');
INSERT INTO SM_STATE
	VALUES (2097153,
	2097156,
	2097153,
	'Accepting Disk Mount Request',
	1,
	0);
INSERT INTO SM_SEME
	VALUES (2097153,
	2097153,
	2097156,
	2097153);
INSERT INTO SM_SEME
	VALUES (2097153,
	2097154,
	2097156,
	2097153);
INSERT INTO SM_STATE
	VALUES (2097154,
	2097156,
	2097153,
	'Disk Request Satisfied',
	2,
	1);
INSERT INTO SM_EIGN
	VALUES (2097154,
	2097153,
	2097156,
	2097153,
	'');
INSERT INTO SM_SEME
	VALUES (2097154,
	2097153,
	2097156,
	2097153);
INSERT INTO SM_EIGN
	VALUES (2097154,
	2097154,
	2097156,
	2097153,
	'');
INSERT INTO SM_SEME
	VALUES (2097154,
	2097154,
	2097156,
	2097153);
INSERT INTO SM_NSTXN
	VALUES (2097153,
	2097156,
	2097153,
	2097154,
	2097153);
INSERT INTO SM_TXN
	VALUES (2097153,
	2097156,
	2097154,
	2097153);
INSERT INTO SM_NSTXN
	VALUES (2097154,
	2097156,
	2097153,
	2097153,
	2097153);
INSERT INTO SM_TXN
	VALUES (2097154,
	2097156,
	2097153,
	2097153);
INSERT INTO SM_MOAH
	VALUES (2097153,
	2097156,
	2097153);
INSERT INTO SM_AH
	VALUES (2097153,
	2097156);
INSERT INTO SM_ACT
	VALUES (2097153,
	2097156,
	1,
	'Assign Self.Request_Pending = TRUE;
Generate D_DO_A1:''Disk Requested by QP'' () to D_DO Assigner;',
	'');
INSERT INTO SM_MOAH
	VALUES (2097154,
	2097156,
	2097154);
INSERT INTO SM_AH
	VALUES (2097154,
	2097156);
INSERT INTO SM_ACT
	VALUES (2097154,
	2097156,
	1,
	'// Update Time Request Satisfied
Assign Self.Request_Pending = FALSE;

Generate D_DO_A6:''Ownership Established'' () to D_DO Assigner;',
	'');
INSERT INTO GD_MD
	VALUES (2097153,
	8,
	2097156,
	40,
	1,
	0,
	1,
	1,
	0,
	12,
	1600,
	4199,
	1.000000,
	0);
INSERT INTO GD_GE
	VALUES (2097154,
	2097153,
	2097154,
	41);
INSERT INTO GD_SHP
	VALUES (2097154,
	1840,
	1568,
	2256,
	1760);
INSERT INTO GD_GE
	VALUES (2097155,
	2097153,
	2097153,
	41);
INSERT INTO GD_SHP
	VALUES (2097155,
	1840,
	1296,
	2256,
	1424);
INSERT INTO GD_GE
	VALUES (2097156,
	2097153,
	2097154,
	42);
INSERT INTO GD_CON
	VALUES (2097156,
	2097155,
	2097155,
	0);
INSERT INTO GD_CTXT
	VALUES (2097156,
	0,
	0,
	0,
	0,
	0,
	0,
	1645,
	1342,
	1769,
	1390,
	-115,
	-3,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (2097157,
	2097156,
	1840,
	1312,
	1776,
	1312,
	0);
INSERT INTO GD_LS
	VALUES (2097158,
	2097156,
	1776,
	1312,
	1776,
	1408,
	2097157);
INSERT INTO GD_LS
	VALUES (2097159,
	2097156,
	1776,
	1408,
	1840,
	1408,
	2097158);
INSERT INTO GD_GE
	VALUES (2097160,
	2097153,
	2097153,
	42);
INSERT INTO GD_CON
	VALUES (2097160,
	2097155,
	2097154,
	0);
INSERT INTO GD_CTXT
	VALUES (2097160,
	0,
	0,
	0,
	0,
	0,
	0,
	2041,
	1476,
	2251,
	1521,
	9,
	-5,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (2097161,
	2097160,
	2048,
	1424,
	2048,
	1568,
	0);
INSERT INTO O_OBJ
	VALUES (1048582,
	'Qualified Process',
	9,
	'D_QP',
	'Customers will write their own file management software to keep track of which
disk (and where on that disk) each file is located.  The term
"Qualified Process" has been adopted to mean this file management software or
any other system-level software (e.g. diagnostics) which use disks, addressing
them by sectors.
',
	1048578);
INSERT INTO O_NBATTR
	VALUES (1048599,
	1048582);
INSERT INTO O_BATTR
	VALUES (1048599,
	1048582);
INSERT INTO O_ATTR
	VALUES (1048599,
	1048582,
	0,
	'Qualified_Process_ID',
	'The identifier of the qualified process.',
	'',
	'Qualified_Process_ID',
	0,
	524291);
INSERT INTO O_NBATTR
	VALUES (1048600,
	1048582);
INSERT INTO O_BATTR
	VALUES (1048600,
	1048582);
INSERT INTO O_ATTR
	VALUES (1048600,
	1048582,
	1048599,
	'current_state',
	'The current state of a Qualified Process.',
	'',
	'current_state',
	0,
	524295);
INSERT INTO O_ID
	VALUES (0,
	1048582);
INSERT INTO O_OIDA
	VALUES (1048599,
	1048582,
	0);
INSERT INTO O_RTIDA
	VALUES (1048599,
	1048582,
	0,
	1048580,
	1048585);
INSERT INTO O_RTIDA
	VALUES (1048599,
	1048582,
	0,
	1048579,
	1048582);
INSERT INTO SM_ISM
	VALUES (2621445,
	1048582);
INSERT INTO SM_SM
	VALUES (2621445,
	'',
	5);
INSERT INTO SM_MOORE
	VALUES (2621445);
INSERT INTO SM_EVTDI
	VALUES (2621441,
	2621445,
	'disk_id',
	'',
	524291);
INSERT INTO SM_SUPDT
	VALUES (2621441,
	2621445,
	0);
INSERT INTO SM_SDI
	VALUES (2621441,
	2621441,
	2621445);
INSERT INTO SM_LEVT
	VALUES (2621441,
	2621445,
	2621441);
INSERT INTO SM_SEVT
	VALUES (2621441,
	2621445,
	2621441);
INSERT INTO SM_EVT
	VALUES (2621441,
	2621445,
	2621441,
	1,
	'Mount Disk',
	0,
	'',
	'D_QP1',
	'');
INSERT INTO SM_STATE
	VALUES (2621441,
	2621445,
	2621441,
	'Requesting Disk',
	1,
	0);
INSERT INTO SM_SEME
	VALUES (2621441,
	2621441,
	2621445,
	2621441);
INSERT INTO SM_NSTXN
	VALUES (2621441,
	2621445,
	2621441,
	2621441,
	2621441);
INSERT INTO SM_TXN
	VALUES (2621441,
	2621445,
	2621441,
	2621441);
INSERT INTO SM_MOAH
	VALUES (2621441,
	2621445,
	2621441);
INSERT INTO SM_AH
	VALUES (2621441,
	2621445);
INSERT INTO SM_ACT
	VALUES (2621441,
	2621445,
	1,
	'create object instance dq of D_DQ;
select any disk from instances of D_D where (selected.Disk_ID == rcvd_evt.disk_id);
relate self to disk across R1 using dq;
generate D_DQ1:''Disk Mount Request''() to dq;',
	'');
INSERT INTO GD_MD
	VALUES (2621441,
	8,
	2621445,
	40,
	0,
	0,
	1,
	1,
	0,
	12,
	1600,
	4199,
	1.000000,
	0);
INSERT INTO GD_GE
	VALUES (2621442,
	2621441,
	2621441,
	41);
INSERT INTO GD_SHP
	VALUES (2621442,
	1824,
	1312,
	2240,
	1536);
INSERT INTO GD_GE
	VALUES (2621443,
	2621441,
	2621441,
	42);
INSERT INTO GD_CON
	VALUES (2621443,
	2621442,
	2621442,
	0);
INSERT INTO GD_CTXT
	VALUES (2621443,
	0,
	0,
	0,
	0,
	0,
	0,
	1889,
	1214,
	2050,
	1264,
	122,
	-9,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (2621444,
	2621443,
	1824,
	1344,
	1776,
	1344,
	0);
INSERT INTO GD_LS
	VALUES (2621445,
	2621443,
	1776,
	1344,
	1776,
	1248,
	2621444);
INSERT INTO GD_LS
	VALUES (2621446,
	2621443,
	1776,
	1248,
	1888,
	1248,
	2621445);
INSERT INTO GD_LS
	VALUES (2621447,
	2621443,
	1888,
	1248,
	1888,
	1312,
	2621446);
INSERT INTO O_OBJ
	VALUES (1048583,
	'Disk Ownership',
	11,
	'D_DO',
	'Disks can be owned by a single qualified process at a time.  The Disk Ownership
class records the association between a disk and a single qualified process.  At
any given time, it is possible to determine which qualified process owns a given
disk by looking at the Disk Ownership instances.
',
	1048578);
INSERT INTO O_REF
	VALUES (1048583,
	1048577,
	0,
	1048577,
	1048580,
	1048587,
	1048586,
	1048601,
	1048588,
	0,
	0,
	'Reference Domain:
');
INSERT INTO O_RATTR
	VALUES (1048601,
	1048583,
	1048577,
	1048577,
	1);
INSERT INTO O_ATTR
	VALUES (1048601,
	1048583,
	0,
	'Disk_ID',
	'The identifier of the disk.  This attribute, together with Disk
Ownership.Qualified Process ID, formalizes the association Qualified Process
OWNS Disk.
',
	'',
	'Disk_ID',
	0,
	524296);
INSERT INTO O_REF
	VALUES (1048583,
	1048582,
	0,
	1048599,
	1048580,
	1048587,
	1048585,
	1048602,
	1048589,
	0,
	0,
	'Reference Domain:
');
INSERT INTO O_RATTR
	VALUES (1048602,
	1048583,
	1048599,
	1048582,
	1);
INSERT INTO O_ATTR
	VALUES (1048602,
	1048583,
	1048601,
	'Qualified_Process_ID',
	'The identifier of the qualified process which currently owns the disk.  This
attribute, together with Disk Ownership.Disk ID, formalizes the association
Qualified Process OWNS Disk.
',
	'',
	'Qualified_Process_ID',
	0,
	524296);
INSERT INTO O_ID
	VALUES (0,
	1048583);
INSERT INTO O_OIDA
	VALUES (1048601,
	1048583,
	0);
INSERT INTO SM_ASM
	VALUES (3145734,
	1048583);
INSERT INTO SM_SM
	VALUES (3145734,
	'',
	6);
INSERT INTO SM_MOORE
	VALUES (3145734);
INSERT INTO SM_EVTDI
	VALUES (3145729,
	3145734,
	'serial_number',
	'',
	524291);
INSERT INTO SM_SUPDT
	VALUES (3145729,
	3145734,
	0);
INSERT INTO SM_SUPDT
	VALUES (3145730,
	3145734,
	0);
INSERT INTO SM_SDI
	VALUES (3145729,
	3145730,
	3145734);
INSERT INTO SM_LEVT
	VALUES (3145729,
	3145734,
	3145729);
INSERT INTO SM_SEVT
	VALUES (3145729,
	3145734,
	3145729);
INSERT INTO SM_EVT
	VALUES (3145729,
	3145734,
	3145729,
	1,
	'Disk Requested by QP',
	0,
	'',
	'D_DO_A1',
	'');
INSERT INTO SM_LEVT
	VALUES (3145730,
	3145734,
	3145729);
INSERT INTO SM_SEVT
	VALUES (3145730,
	3145734,
	3145729);
INSERT INTO SM_EVT
	VALUES (3145730,
	3145734,
	3145729,
	2,
	'Establish Ownership of an Offline Disk',
	0,
	'',
	'D_DO_A2',
	'');
INSERT INTO SM_LEVT
	VALUES (3145731,
	3145734,
	3145729);
INSERT INTO SM_SEVT
	VALUES (3145731,
	3145734,
	3145729);
INSERT INTO SM_EVT
	VALUES (3145731,
	3145734,
	3145729,
	3,
	'Establish Ownership of an Online Disk',
	0,
	'',
	'D_DO_A3',
	'');
INSERT INTO SM_LEVT
	VALUES (3145732,
	3145734,
	3145729);
INSERT INTO SM_SEVT
	VALUES (3145732,
	3145734,
	3145729);
INSERT INTO SM_EVT
	VALUES (3145732,
	3145734,
	3145729,
	4,
	'Eject Disk if Possible',
	0,
	'',
	'D_DO_A4',
	'');
INSERT INTO SM_LEVT
	VALUES (3145733,
	3145734,
	3145729);
INSERT INTO SM_SEVT
	VALUES (3145733,
	3145734,
	3145729);
INSERT INTO SM_EVT
	VALUES (3145733,
	3145734,
	3145729,
	5,
	'Disk Ownership Released',
	0,
	'',
	'D_DO_A5',
	'');
INSERT INTO SM_LEVT
	VALUES (3145734,
	3145734,
	3145729);
INSERT INTO SM_SEVT
	VALUES (3145734,
	3145734,
	3145729);
INSERT INTO SM_EVT
	VALUES (3145734,
	3145734,
	3145729,
	6,
	'Ownership Established',
	0,
	'',
	'D_DO_A6',
	'');
INSERT INTO SM_LEVT
	VALUES (3145735,
	3145734,
	3145730);
INSERT INTO SM_SEVT
	VALUES (3145735,
	3145734,
	3145730);
INSERT INTO SM_EVT
	VALUES (3145735,
	3145734,
	3145730,
	7,
	'Disk Now Online',
	0,
	'',
	'D_DO_A7',
	'');
INSERT INTO SM_LEVT
	VALUES (3145736,
	3145734,
	3145729);
INSERT INTO SM_SEVT
	VALUES (3145736,
	3145734,
	3145729);
INSERT INTO SM_EVT
	VALUES (3145736,
	3145734,
	3145729,
	8,
	'Disk Now Offline',
	0,
	'',
	'D_DO_A8',
	'');
INSERT INTO SM_STATE
	VALUES (3145729,
	3145734,
	3145729,
	'Waiting for a Disk Request',
	1,
	0);
INSERT INTO SM_SEME
	VALUES (3145729,
	3145729,
	3145734,
	3145729);
INSERT INTO SM_EIGN
	VALUES (3145729,
	3145730,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145729,
	3145730,
	3145734,
	3145729);
INSERT INTO SM_EIGN
	VALUES (3145729,
	3145731,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145729,
	3145731,
	3145734,
	3145729);
INSERT INTO SM_EIGN
	VALUES (3145729,
	3145732,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145729,
	3145732,
	3145734,
	3145729);
INSERT INTO SM_EIGN
	VALUES (3145729,
	3145733,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145729,
	3145733,
	3145734,
	3145729);
INSERT INTO SM_EIGN
	VALUES (3145729,
	3145734,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145729,
	3145734,
	3145734,
	3145729);
INSERT INTO SM_EIGN
	VALUES (3145729,
	3145735,
	3145734,
	3145730,
	'');
INSERT INTO SM_SEME
	VALUES (3145729,
	3145735,
	3145734,
	3145730);
INSERT INTO SM_EIGN
	VALUES (3145729,
	3145736,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145729,
	3145736,
	3145734,
	3145729);
INSERT INTO SM_STATE
	VALUES (3145730,
	3145734,
	3145729,
	'Waiting for a Requested Disk to be Ownable',
	2,
	0);
INSERT INTO SM_SEME
	VALUES (3145730,
	3145729,
	3145734,
	3145729);
INSERT INTO SM_SEME
	VALUES (3145730,
	3145730,
	3145734,
	3145729);
INSERT INTO SM_SEME
	VALUES (3145730,
	3145731,
	3145734,
	3145729);
INSERT INTO SM_SEME
	VALUES (3145730,
	3145732,
	3145734,
	3145729);
INSERT INTO SM_SEME
	VALUES (3145730,
	3145733,
	3145734,
	3145729);
INSERT INTO SM_EIGN
	VALUES (3145730,
	3145734,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145730,
	3145734,
	3145734,
	3145729);
INSERT INTO SM_EIGN
	VALUES (3145730,
	3145735,
	3145734,
	3145730,
	'');
INSERT INTO SM_SEME
	VALUES (3145730,
	3145735,
	3145734,
	3145730);
INSERT INTO SM_EIGN
	VALUES (3145730,
	3145736,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145730,
	3145736,
	3145734,
	3145729);
INSERT INTO SM_STATE
	VALUES (3145731,
	3145734,
	3145729,
	'Establishing Ownership of an Online Disk',
	3,
	0);
INSERT INTO SM_EIGN
	VALUES (3145731,
	3145729,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145731,
	3145729,
	3145734,
	3145729);
INSERT INTO SM_EIGN
	VALUES (3145731,
	3145730,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145731,
	3145730,
	3145734,
	3145729);
INSERT INTO SM_EIGN
	VALUES (3145731,
	3145731,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145731,
	3145731,
	3145734,
	3145729);
INSERT INTO SM_EIGN
	VALUES (3145731,
	3145732,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145731,
	3145732,
	3145734,
	3145729);
INSERT INTO SM_EIGN
	VALUES (3145731,
	3145733,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145731,
	3145733,
	3145734,
	3145729);
INSERT INTO SM_SEME
	VALUES (3145731,
	3145734,
	3145734,
	3145729);
INSERT INTO SM_EIGN
	VALUES (3145731,
	3145735,
	3145734,
	3145730,
	'');
INSERT INTO SM_SEME
	VALUES (3145731,
	3145735,
	3145734,
	3145730);
INSERT INTO SM_EIGN
	VALUES (3145731,
	3145736,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145731,
	3145736,
	3145734,
	3145729);
INSERT INTO SM_STATE
	VALUES (3145732,
	3145734,
	3145729,
	'Tying to Eject Disk',
	4,
	0);
INSERT INTO SM_CH
	VALUES (3145732,
	3145729,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145732,
	3145729,
	3145734,
	3145729);
INSERT INTO SM_EIGN
	VALUES (3145732,
	3145730,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145732,
	3145730,
	3145734,
	3145729);
INSERT INTO SM_EIGN
	VALUES (3145732,
	3145731,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145732,
	3145731,
	3145734,
	3145729);
INSERT INTO SM_EIGN
	VALUES (3145732,
	3145732,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145732,
	3145732,
	3145734,
	3145729);
INSERT INTO SM_SEME
	VALUES (3145732,
	3145733,
	3145734,
	3145729);
INSERT INTO SM_EIGN
	VALUES (3145732,
	3145734,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145732,
	3145734,
	3145734,
	3145729);
INSERT INTO SM_EIGN
	VALUES (3145732,
	3145735,
	3145734,
	3145730,
	'');
INSERT INTO SM_SEME
	VALUES (3145732,
	3145735,
	3145734,
	3145730);
INSERT INTO SM_SEME
	VALUES (3145732,
	3145736,
	3145734,
	3145729);
INSERT INTO SM_STATE
	VALUES (3145733,
	3145734,
	3145729,
	'Establishing Ownership of an Offline Disk',
	5,
	0);
INSERT INTO SM_EIGN
	VALUES (3145733,
	3145729,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145733,
	3145729,
	3145734,
	3145729);
INSERT INTO SM_EIGN
	VALUES (3145733,
	3145730,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145733,
	3145730,
	3145734,
	3145729);
INSERT INTO SM_EIGN
	VALUES (3145733,
	3145731,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145733,
	3145731,
	3145734,
	3145729);
INSERT INTO SM_EIGN
	VALUES (3145733,
	3145732,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145733,
	3145732,
	3145734,
	3145729);
INSERT INTO SM_EIGN
	VALUES (3145733,
	3145733,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145733,
	3145733,
	3145734,
	3145729);
INSERT INTO SM_EIGN
	VALUES (3145733,
	3145734,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145733,
	3145734,
	3145734,
	3145729);
INSERT INTO SM_SEME
	VALUES (3145733,
	3145735,
	3145734,
	3145730);
INSERT INTO SM_EIGN
	VALUES (3145733,
	3145736,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145733,
	3145736,
	3145734,
	3145729);
INSERT INTO SM_STATE
	VALUES (3145734,
	3145734,
	3145730,
	'Satisfying Disk Request',
	6,
	0);
INSERT INTO SM_EIGN
	VALUES (3145734,
	3145729,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145734,
	3145729,
	3145734,
	3145729);
INSERT INTO SM_EIGN
	VALUES (3145734,
	3145730,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145734,
	3145730,
	3145734,
	3145729);
INSERT INTO SM_EIGN
	VALUES (3145734,
	3145731,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145734,
	3145731,
	3145734,
	3145729);
INSERT INTO SM_EIGN
	VALUES (3145734,
	3145732,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145734,
	3145732,
	3145734,
	3145729);
INSERT INTO SM_EIGN
	VALUES (3145734,
	3145733,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145734,
	3145733,
	3145734,
	3145729);
INSERT INTO SM_SEME
	VALUES (3145734,
	3145734,
	3145734,
	3145729);
INSERT INTO SM_EIGN
	VALUES (3145734,
	3145735,
	3145734,
	3145730,
	'');
INSERT INTO SM_SEME
	VALUES (3145734,
	3145735,
	3145734,
	3145730);
INSERT INTO SM_EIGN
	VALUES (3145734,
	3145736,
	3145734,
	3145729,
	'');
INSERT INTO SM_SEME
	VALUES (3145734,
	3145736,
	3145734,
	3145729);
INSERT INTO SM_NSTXN
	VALUES (3145729,
	3145734,
	3145734,
	3145734,
	3145729);
INSERT INTO SM_TXN
	VALUES (3145729,
	3145734,
	3145729,
	3145729);
INSERT INTO SM_NSTXN
	VALUES (3145730,
	3145734,
	3145731,
	3145734,
	3145729);
INSERT INTO SM_TXN
	VALUES (3145730,
	3145734,
	3145729,
	3145729);
INSERT INTO SM_NSTXN
	VALUES (3145731,
	3145734,
	3145729,
	3145729,
	3145729);
INSERT INTO SM_TXN
	VALUES (3145731,
	3145734,
	3145730,
	3145729);
INSERT INTO SM_NSTXN
	VALUES (3145732,
	3145734,
	3145730,
	3145731,
	3145729);
INSERT INTO SM_TXN
	VALUES (3145732,
	3145734,
	3145731,
	3145729);
INSERT INTO SM_NSTXN
	VALUES (3145733,
	3145734,
	3145730,
	3145730,
	3145729);
INSERT INTO SM_TXN
	VALUES (3145733,
	3145734,
	3145733,
	3145729);
INSERT INTO SM_NSTXN
	VALUES (3145734,
	3145734,
	3145730,
	3145733,
	3145729);
INSERT INTO SM_TXN
	VALUES (3145734,
	3145734,
	3145730,
	3145729);
INSERT INTO SM_NSTXN
	VALUES (3145735,
	3145734,
	3145730,
	3145729,
	3145729);
INSERT INTO SM_TXN
	VALUES (3145735,
	3145734,
	3145730,
	3145729);
INSERT INTO SM_NSTXN
	VALUES (3145736,
	3145734,
	3145733,
	3145735,
	3145730);
INSERT INTO SM_TXN
	VALUES (3145736,
	3145734,
	3145734,
	3145730);
INSERT INTO SM_NSTXN
	VALUES (3145737,
	3145734,
	3145732,
	3145736,
	3145729);
INSERT INTO SM_TXN
	VALUES (3145737,
	3145734,
	3145733,
	3145729);
INSERT INTO SM_NSTXN
	VALUES (3145738,
	3145734,
	3145730,
	3145732,
	3145729);
INSERT INTO SM_TXN
	VALUES (3145738,
	3145734,
	3145732,
	3145729);
INSERT INTO SM_NSTXN
	VALUES (3145739,
	3145734,
	3145732,
	3145733,
	3145729);
INSERT INTO SM_TXN
	VALUES (3145739,
	3145734,
	3145730,
	3145729);
INSERT INTO SM_MOAH
	VALUES (3145729,
	3145734,
	3145729);
INSERT INTO SM_AH
	VALUES (3145729,
	3145734);
INSERT INTO SM_ACT
	VALUES (3145729,
	3145734,
	1,
	'Select many disk_requests from instances of D_DQ;
Assign request_found = FALSE;
For each disk_request in disk_requests
    If (request_found == FALSE)
        If (disk_request.Request_Pending == TRUE)
            Assign request_found = TRUE;
            Generate D_DO_A1:''Disk Requested by QP'' () to D_DO assigner;
        End if;
    End if;
End for;',
	'');
INSERT INTO SM_MOAH
	VALUES (3145730,
	3145734,
	3145730);
INSERT INTO SM_AH
	VALUES (3145730,
	3145734);
INSERT INTO SM_ACT
	VALUES (3145730,
	3145734,
	1,
	'Select many disk_requests from instances of D_DQ;
For each disk_request in disk_requests
if (disk_request.Request_Pending == TRUE)
Select one disk related by disk_request->D_D[R1];
    If (disk.Unowned_in_Cabinet == TRUE)
        Select one disk_owner related by disk->D_DO[R2];
        If (empty disk_owner)
            Generate D_DO_A3:''Establish Ownership of an Online Disk'' () to D_DO assigner;
        End if;
    Else 
    If (disk.Unowned_in_Library == TRUE)
        Select one disk_owner related by disk->D_DO[R2];
        If (empty disk_owner)
            Assign number_of_online = 0;
            Select many online_disks from instances of D_OND;
            For each online_disk in online_disks
                Assign number_of_online = number_of_online + 1;
            End for;

             Assign number_of_slots = 0;
             Select many slots from instances of D_S;
             For each slot in slots
                 Assign number_of_slots = number_of_slots + 1;
             End for;

             If (number_of_online < number_of_slots)
                 Generate D_DO_A2:''Establish Ownership of an Offline Disk'' () to D_DO assigner;
             Else
                 Generate D_DO_A4:''Eject Disk if Possible'' () to D_DO assigner;
             End if;
        End if;
    End if;
    End if;
End if;
End for;',
	'');
INSERT INTO SM_MOAH
	VALUES (3145731,
	3145734,
	3145731);
INSERT INTO SM_AH
	VALUES (3145731,
	3145734);
INSERT INTO SM_ACT
	VALUES (3145731,
	3145734,
	1,
	'Select many disks from instances of D_D;
Assign disk_found = FALSE;
For each disk in disks
    If (disk_found == FALSE)
    If (disk.Unowned_in_Cabinet == TRUE)
        Select any disk_request related by disk->D_DQ[R1];
        If (not_empty disk_request)
            Assign disk_found = TRUE;
            Select one process related by disk_request->D_QP[R1]; 
            Create  object instance owner of D_DO;
            Relate disk to process across R2 using owner;
            Generate D_D1:''Disk Ownership Established'' () to disk;
            Generate D_DQ2:''Disk Request Satisfied''() to disk_request;
        End if;
    End if;
    End if;
End for;',
	'');
INSERT INTO SM_MOAH
	VALUES (3145732,
	3145734,
	3145732);
INSERT INTO SM_AH
	VALUES (3145732,
	3145734);
INSERT INTO SM_ACT
	VALUES (3145732,
	3145734,
	1,
	'Select many disk_set from instances of D_D;
Assign disk_found = FALSE;
For each disk in disk_set
    If (disk_found == FALSE)
    If (disk.Unowned_in_Cabinet == TRUE)
        Select one disk_owner related by disk->D_DO[R2];
        If (empty disk_owner)
            Select many disk_request related by disk->D_DQ[R1];
            If (empty disk_request)
                Assign disk_found = TRUE;
                Generate D_D6:''Return Unowned Disk to Library'' () to disk;
            End if;
        End if;
    End if;
    End if;
End for;',
	'');
INSERT INTO SM_MOAH
	VALUES (3145733,
	3145734,
	3145733);
INSERT INTO SM_AH
	VALUES (3145733,
	3145734);
INSERT INTO SM_ACT
	VALUES (3145733,
	3145734,
	1,
	'Select many disks from instances of D_D;
Assign disk_found = FALSE;
For each disk in disks
    If (disk_found == FALSE)
    If (disk.Unowned_in_Library == TRUE)
        Select any disk_request related by disk->D_DQ[R1];
        If (not_empty disk_request) 
            Assign disk_found = TRUE;
            Create object instance disk_owner of D_DO;
            Select one process related by disk_request->D_QP[R1];
            Relate disk to process across R2 using disk_owner;
            Generate D_D1:''Disk Ownership Established'' () to disk;
        End if;
    End if;
    End if;
End for;',
	'');
INSERT INTO SM_MOAH
	VALUES (3145734,
	3145734,
	3145734);
INSERT INTO SM_AH
	VALUES (3145734,
	3145734);
INSERT INTO SM_ACT
	VALUES (3145734,
	3145734,
	1,
	'Select many disks from instances of D_D;
Assign disk_found = FALSE;
For each disk in disks
    If (disk_found == FALSE)
        If (disk.Serial_Number == rcvd_evt.serial_number)
            Assign disk_found = TRUE;
            Select any disk_request related by disk->D_DQ[R1];
            Select one process related by disk_request->D_QP[R1];
            Unrelate disk from process across R1 using disk_request;
            Generate D_DQ2:''Disk Request Satisfied'' () to disk_request;
        End if;
    End if;
End for;',
	'');
INSERT INTO GD_MD
	VALUES (3145729,
	10,
	3145734,
	40,
	1,
	0,
	1,
	1,
	0,
	12,
	1600,
	4199,
	1.000000,
	0);
INSERT INTO GD_GE
	VALUES (3145730,
	3145729,
	3145734,
	41);
INSERT INTO GD_SHP
	VALUES (3145730,
	1280,
	1264,
	1648,
	1520);
INSERT INTO GD_GE
	VALUES (3145731,
	3145729,
	3145733,
	41);
INSERT INTO GD_SHP
	VALUES (3145731,
	1296,
	1664,
	1648,
	1952);
INSERT INTO GD_GE
	VALUES (3145732,
	3145729,
	3145732,
	41);
INSERT INTO GD_SHP
	VALUES (3145732,
	1888,
	2336,
	2288,
	2640);
INSERT INTO GD_GE
	VALUES (3145733,
	3145729,
	3145731,
	41);
INSERT INTO GD_SHP
	VALUES (3145733,
	2656,
	1664,
	3040,
	2000);
INSERT INTO GD_GE
	VALUES (3145734,
	3145729,
	3145730,
	41);
INSERT INTO GD_SHP
	VALUES (3145734,
	1888,
	1664,
	2400,
	2192);
INSERT INTO GD_GE
	VALUES (3145735,
	3145729,
	3145729,
	41);
INSERT INTO GD_SHP
	VALUES (3145735,
	1888,
	1264,
	2336,
	1504);
INSERT INTO GD_GE
	VALUES (3145736,
	3145729,
	3145737,
	42);
INSERT INTO GD_CON
	VALUES (3145736,
	3145732,
	3145731,
	0);
INSERT INTO GD_CTXT
	VALUES (3145736,
	0,
	0,
	0,
	0,
	0,
	0,
	1471,
	2439,
	1647,
	2478,
	31,
	14,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (3145737,
	3145736,
	1888,
	2496,
	1456,
	2496,
	0);
INSERT INTO GD_LS
	VALUES (3145738,
	3145736,
	1456,
	2496,
	1456,
	1952,
	3145737);
INSERT INTO GD_GE
	VALUES (3145739,
	3145729,
	3145739,
	42);
INSERT INTO GD_CON
	VALUES (3145739,
	3145732,
	3145734,
	0);
INSERT INTO GD_CTXT
	VALUES (3145739,
	0,
	0,
	0,
	0,
	0,
	0,
	2249,
	2258,
	2450,
	2302,
	25,
	9,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (3145740,
	3145739,
	2240,
	2336,
	2240,
	2192,
	0);
INSERT INTO GD_GE
	VALUES (3145741,
	3145729,
	3145738,
	42);
INSERT INTO GD_CON
	VALUES (3145741,
	3145734,
	3145732,
	0);
INSERT INTO GD_CTXT
	VALUES (3145741,
	0,
	0,
	0,
	0,
	0,
	0,
	1732,
	2248,
	1938,
	2283,
	-188,
	-1,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (3145742,
	3145741,
	1936,
	2192,
	1936,
	2336,
	0);
INSERT INTO GD_GE
	VALUES (3145743,
	3145729,
	3145734,
	42);
INSERT INTO GD_CON
	VALUES (3145743,
	3145734,
	3145734,
	0);
INSERT INTO GD_CTXT
	VALUES (3145743,
	0,
	0,
	0,
	0,
	0,
	0,
	2502,
	2043,
	2647,
	2093,
	22,
	-6,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (3145744,
	3145743,
	2400,
	1984,
	2496,
	1984,
	0);
INSERT INTO GD_LS
	VALUES (3145745,
	3145743,
	2496,
	1984,
	2496,
	2144,
	3145744);
INSERT INTO GD_LS
	VALUES (3145746,
	3145743,
	2496,
	2144,
	2400,
	2144,
	3145745);
INSERT INTO GD_GE
	VALUES (3145747,
	3145729,
	3145735,
	42);
INSERT INTO GD_CON
	VALUES (3145747,
	3145734,
	3145734,
	0);
INSERT INTO GD_CTXT
	VALUES (3145747,
	0,
	0,
	0,
	0,
	0,
	0,
	1757,
	2037,
	1883,
	2083,
	13,
	-12,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (3145748,
	3145747,
	1888,
	1984,
	1760,
	1984,
	0);
INSERT INTO GD_LS
	VALUES (3145749,
	3145747,
	1760,
	1984,
	1760,
	2144,
	3145748);
INSERT INTO GD_LS
	VALUES (3145750,
	3145747,
	1760,
	2144,
	1888,
	2144,
	3145749);
INSERT INTO GD_GE
	VALUES (3145751,
	3145729,
	3145730,
	42);
INSERT INTO GD_CON
	VALUES (3145751,
	3145733,
	3145735,
	0);
INSERT INTO GD_CTXT
	VALUES (3145751,
	0,
	0,
	0,
	0,
	0,
	0,
	2568,
	1338,
	2829,
	1374,
	-135,
	-13,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (3145752,
	3145751,
	2832,
	1664,
	2832,
	1376,
	0);
INSERT INTO GD_LS
	VALUES (3145753,
	3145751,
	2832,
	1376,
	2336,
	1376,
	3145752);
INSERT INTO GD_GE
	VALUES (3145754,
	3145729,
	3145732,
	42);
INSERT INTO GD_CON
	VALUES (3145754,
	3145734,
	3145733,
	0);
INSERT INTO GD_CTXT
	VALUES (3145754,
	0,
	0,
	0,
	0,
	0,
	0,
	2423,
	1724,
	2623,
	1774,
	-80,
	-27,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (3145755,
	3145754,
	2400,
	1776,
	2656,
	1776,
	0);
INSERT INTO GD_GE
	VALUES (3145756,
	3145729,
	3145736,
	42);
INSERT INTO GD_CON
	VALUES (3145756,
	3145731,
	3145730,
	0);
INSERT INTO GD_CTXT
	VALUES (3145756,
	0,
	0,
	0,
	0,
	0,
	0,
	1437,
	1569,
	1635,
	1612,
	13,
	-8,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (3145757,
	3145756,
	1440,
	1664,
	1440,
	1520,
	0);
INSERT INTO GD_GE
	VALUES (3145758,
	3145729,
	3145729,
	42);
INSERT INTO GD_CON
	VALUES (3145758,
	3145730,
	3145735,
	0);
INSERT INTO GD_CTXT
	VALUES (3145758,
	0,
	0,
	0,
	0,
	0,
	0,
	1689,
	1328,
	1840,
	1370,
	-54,
	-23,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (3145759,
	3145758,
	1648,
	1376,
	1888,
	1376,
	0);
INSERT INTO GD_GE
	VALUES (3145760,
	3145729,
	3145733,
	42);
INSERT INTO GD_CON
	VALUES (3145760,
	3145734,
	3145731,
	0);
INSERT INTO GD_CTXT
	VALUES (3145760,
	0,
	0,
	0,
	0,
	0,
	0,
	1682,
	1731,
	1846,
	1774,
	-61,
	-20,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (3145761,
	3145760,
	1888,
	1776,
	1648,
	1776,
	0);
INSERT INTO GD_GE
	VALUES (3145762,
	3145729,
	3145731,
	42);
INSERT INTO GD_CON
	VALUES (3145762,
	3145735,
	3145734,
	0);
INSERT INTO GD_CTXT
	VALUES (3145762,
	0,
	0,
	0,
	0,
	0,
	0,
	2207,
	1565,
	2421,
	1604,
	15,
	-1,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (3145763,
	3145762,
	2176,
	1504,
	2176,
	1664,
	0);
INSERT INTO O_OBJ
	VALUES (1048584,
	'Drive_Disk Assignment',
	14,
	'D_DDA',
	'Before a disk can be mounted, it must be assigned to a drive.  An instance of
this class is created when a disk is assigned to a drive, and is deleted when a
disk is removed from the drive and returns to its assigned slot.
',
	1048578);
INSERT INTO O_REF
	VALUES (1048584,
	1048588,
	0,
	1048615,
	1048583,
	1048597,
	1048596,
	1048603,
	1048590,
	0,
	0,
	'Reference Domain:
');
INSERT INTO O_RATTR
	VALUES (1048603,
	1048584,
	1048608,
	1048586,
	1);
INSERT INTO O_ATTR
	VALUES (1048603,
	1048584,
	0,
	'Drive_ID',
	'The identifier of the drive. This attribute, together with Drive Disk
Assignment.Disk ID, formalizes the association Online Disk IS ASSIGNED TO
Drive.
',
	'',
	'Drive_ID',
	0,
	524296);
INSERT INTO O_REF
	VALUES (1048584,
	1048579,
	0,
	1048591,
	1048583,
	1048597,
	1048595,
	1048604,
	1048591,
	0,
	0,
	'Reference Domain:
');
INSERT INTO O_RATTR
	VALUES (1048604,
	1048584,
	1048577,
	1048577,
	1);
INSERT INTO O_ATTR
	VALUES (1048604,
	1048584,
	1048603,
	'Disk_ID',
	'The identifier of the disk.  This attribute, together with Drive Disk
Assignnient.Drive ID, formalizes the association Online Disk IS ASSIGNED TO
Drive.
',
	'',
	'Disk_ID',
	0,
	524296);
INSERT INTO O_ID
	VALUES (0,
	1048584);
INSERT INTO O_OIDA
	VALUES (1048603,
	1048584,
	0);
INSERT INTO SM_ASM
	VALUES (3670023,
	1048584);
INSERT INTO SM_SM
	VALUES (3670023,
	'',
	7);
INSERT INTO SM_MOORE
	VALUES (3670023);
INSERT INTO SM_SUPDT
	VALUES (3670017,
	3670023,
	0);
INSERT INTO SM_LEVT
	VALUES (3670017,
	3670023,
	3670017);
INSERT INTO SM_SEVT
	VALUES (3670017,
	3670023,
	3670017);
INSERT INTO SM_EVT
	VALUES (3670017,
	3670023,
	3670017,
	1,
	'Disk Waiting for Drive',
	0,
	'',
	'D_DDA_A1',
	'');
INSERT INTO SM_LEVT
	VALUES (3670018,
	3670023,
	3670017);
INSERT INTO SM_SEVT
	VALUES (3670018,
	3670023,
	3670017);
INSERT INTO SM_EVT
	VALUES (3670018,
	3670023,
	3670017,
	2,
	'Drive Free',
	0,
	'',
	'D_DDA_A2',
	'');
INSERT INTO SM_LEVT
	VALUES (3670019,
	3670023,
	3670017);
INSERT INTO SM_SEVT
	VALUES (3670019,
	3670023,
	3670017);
INSERT INTO SM_EVT
	VALUES (3670019,
	3670023,
	3670017,
	3,
	'Drive Assigned',
	0,
	'',
	'D_DDA_A3',
	'');
INSERT INTO SM_STATE
	VALUES (3670017,
	3670023,
	3670017,
	'Waiting for a Disk to be Waiting',
	1,
	0);
INSERT INTO SM_SEME
	VALUES (3670017,
	3670017,
	3670023,
	3670017);
INSERT INTO SM_EIGN
	VALUES (3670017,
	3670018,
	3670023,
	3670017,
	'');
INSERT INTO SM_SEME
	VALUES (3670017,
	3670018,
	3670023,
	3670017);
INSERT INTO SM_EIGN
	VALUES (3670017,
	3670019,
	3670023,
	3670017,
	'');
INSERT INTO SM_SEME
	VALUES (3670017,
	3670019,
	3670023,
	3670017);
INSERT INTO SM_STATE
	VALUES (3670018,
	3670023,
	3670017,
	'Waiting for a Drive to be Free',
	2,
	0);
INSERT INTO SM_EIGN
	VALUES (3670018,
	3670017,
	3670023,
	3670017,
	'');
INSERT INTO SM_SEME
	VALUES (3670018,
	3670017,
	3670023,
	3670017);
INSERT INTO SM_SEME
	VALUES (3670018,
	3670018,
	3670023,
	3670017);
INSERT INTO SM_EIGN
	VALUES (3670018,
	3670019,
	3670023,
	3670017,
	'');
INSERT INTO SM_SEME
	VALUES (3670018,
	3670019,
	3670023,
	3670017);
INSERT INTO SM_STATE
	VALUES (3670019,
	3670023,
	3670017,
	'Assigning Disk to Drive',
	3,
	0);
INSERT INTO SM_EIGN
	VALUES (3670019,
	3670017,
	3670023,
	3670017,
	'');
INSERT INTO SM_SEME
	VALUES (3670019,
	3670017,
	3670023,
	3670017);
INSERT INTO SM_EIGN
	VALUES (3670019,
	3670018,
	3670023,
	3670017,
	'');
INSERT INTO SM_SEME
	VALUES (3670019,
	3670018,
	3670023,
	3670017);
INSERT INTO SM_SEME
	VALUES (3670019,
	3670019,
	3670023,
	3670017);
INSERT INTO SM_NSTXN
	VALUES (3670017,
	3670023,
	3670019,
	3670019,
	3670017);
INSERT INTO SM_TXN
	VALUES (3670017,
	3670023,
	3670017,
	3670017);
INSERT INTO SM_NSTXN
	VALUES (3670018,
	3670023,
	3670017,
	3670017,
	3670017);
INSERT INTO SM_TXN
	VALUES (3670018,
	3670023,
	3670018,
	3670017);
INSERT INTO SM_NSTXN
	VALUES (3670019,
	3670023,
	3670018,
	3670018,
	3670017);
INSERT INTO SM_TXN
	VALUES (3670019,
	3670023,
	3670019,
	3670017);
INSERT INTO SM_MOAH
	VALUES (3670017,
	3670023,
	3670017);
INSERT INTO SM_AH
	VALUES (3670017,
	3670023);
INSERT INTO SM_ACT
	VALUES (3670017,
	3670023,
	1,
	'Select many disk_set from instances of D_D where (selected.Waiting_for_Drive == TRUE);
For each disk in disk_set
    Select one slot_disk related by disk->D_OND[R3]->D_SDA[R6];
    If (empty slot_disk)
        Generate D_DDA_A1:''Disk Waiting for Drive'' ()  to D_DDA assigner;
        Break;
    End if;
End for;
',
	'');
INSERT INTO SM_MOAH
	VALUES (3670018,
	3670023,
	3670018);
INSERT INTO SM_AH
	VALUES (3670018,
	3670023);
INSERT INTO SM_ACT
	VALUES (3670018,
	3670023,
	1,
	'Select many drives from instances of D_DR where (selected.Idle == TRUE);
For each drive in drives
    Select one drive_assignment related by drive->D_DDA[R5];
    If (empty drive_assignment)
        Generate D_DDA_A2:''Drive Free'' () to D_DDA assigner;
       Break;
    End if;
End for;
',
	'');
INSERT INTO SM_MOAH
	VALUES (3670019,
	3670023,
	3670019);
INSERT INTO SM_AH
	VALUES (3670019,
	3670023);
INSERT INTO SM_ACT
	VALUES (3670019,
	3670023,
	1,
	'Select many disk_set from instances of D_D where (selected.Waiting_for_Drive == TRUE);
Assign disk_found = FALSE;
For each disk in disk_set
    Select one disk_assignment related by disk->D_OND[R3]->D_DDA[R5];
    If (empty disk_assignment)
        If (disk_found == FALSE)
            Assign disk_found = TRUE;
            Assign drive_found = FALSE;
            Select many drives from instances of D_DR;
            For each drive in drives
                If (drive.Idle == TRUE)
                Select one drive_assignment related by drive->D_DDA[R5];
                If (empty drive_assignment)
                    If (drive_found == FALSE)
                        Assign drive_found = TRUE;
                        Create object instance drive_disk of D_DDA;
                        Select one online_disk related by disk->D_OND[R3];
                        Relate drive to online_disk across R5 using drive_disk;
                        Generate D_DR1:''Drive Assigned'' () to drive;
                        Generate D_DDA_A3:''Drive Assigned'' () to D_DDA assigner;
                    End if;
                End if;
                End if;
            End for;
        End if;
    End if;
End for;
',
	'');
INSERT INTO GD_MD
	VALUES (3670017,
	10,
	3670023,
	40,
	0,
	0,
	1,
	1,
	0,
	12,
	1578,
	4036,
	0.901703,
	0);
INSERT INTO GD_GE
	VALUES (3670018,
	3670017,
	3670019,
	41);
INSERT INTO GD_SHP
	VALUES (3670018,
	2288,
	1344,
	2848,
	1920);
INSERT INTO GD_GE
	VALUES (3670019,
	3670017,
	3670018,
	41);
INSERT INTO GD_SHP
	VALUES (3670019,
	1600,
	1664,
	2144,
	1920);
INSERT INTO GD_GE
	VALUES (3670020,
	3670017,
	3670017,
	41);
INSERT INTO GD_SHP
	VALUES (3670020,
	1600,
	1296,
	2128,
	1536);
INSERT INTO GD_GE
	VALUES (3670021,
	3670017,
	3670017,
	42);
INSERT INTO GD_CON
	VALUES (3670021,
	3670018,
	3670020,
	0);
INSERT INTO GD_CTXT
	VALUES (3670021,
	0,
	0,
	0,
	0,
	0,
	0,
	2138,
	1342,
	2272,
	1382,
	-5,
	-4,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (3670022,
	3670021,
	2288,
	1392,
	2128,
	1392,
	0);
INSERT INTO GD_GE
	VALUES (3670023,
	3670017,
	3670019,
	42);
INSERT INTO GD_CON
	VALUES (3670023,
	3670019,
	3670018,
	0);
INSERT INTO GD_CTXT
	VALUES (3670023,
	0,
	0,
	0,
	0,
	0,
	0,
	2149,
	1710,
	2283,
	1750,
	-2,
	-4,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (3670024,
	3670023,
	2144,
	1760,
	2288,
	1760,
	0);
INSERT INTO GD_GE
	VALUES (3670025,
	3670017,
	3670018,
	42);
INSERT INTO GD_CON
	VALUES (3670025,
	3670020,
	3670019,
	0);
INSERT INTO GD_CTXT
	VALUES (3670025,
	0,
	0,
	0,
	0,
	0,
	0,
	1864,
	1571,
	2046,
	1611,
	196,
	-11,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (3670026,
	3670025,
	1856,
	1536,
	1856,
	1664,
	0);
INSERT INTO O_OBJ
	VALUES (1048585,
	'Slot',
	6,
	'D_S',
	'A location in the cabinet which is capable of holding an optical disk.  Every
disk that is in the cabinet must have a slot assigned to it.  A disk keeps its
assigned slot even when it journeys to a drive.  A slot can hold only one disk
at a time.
',
	1048578);
INSERT INTO O_REF
	VALUES (1048585,
	1048586,
	0,
	1048608,
	1048581,
	1048589,
	1048588,
	1048605,
	1048592,
	0,
	0,
	'Reference Domain:
');
INSERT INTO O_RATTR
	VALUES (1048605,
	1048585,
	1048608,
	1048586,
	0);
INSERT INTO O_ATTR
	VALUES (1048605,
	1048585,
	0,
	'Slot_ID',
	'The identifier of the slot.  This attribute also formalizes the association Slot
IS A (SUBTYPE OF) Online Location.
',
	'',
	'Slot_ID',
	0,
	524296);
INSERT INTO O_NBATTR
	VALUES (1048606,
	1048585);
INSERT INTO O_BATTR
	VALUES (1048606,
	1048585);
INSERT INTO O_ATTR
	VALUES (1048606,
	1048585,
	1048605,
	'Unassigned',
	'This attribute returns true if the slot is not assigned to any disk, and false
if it is assigned to a disk.',
	'',
	'Unassigned',
	0,
	524290);
INSERT INTO O_NBATTR
	VALUES (1048607,
	1048585);
INSERT INTO O_BATTR
	VALUES (1048607,
	1048585);
INSERT INTO O_ATTR
	VALUES (1048607,
	1048585,
	1048606,
	'current_state',
	'The current state of the Slot.',
	'',
	'current_state',
	0,
	524295);
INSERT INTO O_ID
	VALUES (0,
	1048585);
INSERT INTO O_OIDA
	VALUES (1048605,
	1048585,
	0);
INSERT INTO O_RTIDA
	VALUES (1048605,
	1048585,
	0,
	1048582,
	1048592);
INSERT INTO SM_ISM
	VALUES (4194312,
	1048585);
INSERT INTO SM_SM
	VALUES (4194312,
	'',
	8);
INSERT INTO SM_MOORE
	VALUES (4194312);
INSERT INTO SM_SUPDT
	VALUES (4194305,
	4194312,
	0);
INSERT INTO SM_LEVT
	VALUES (4194305,
	4194312,
	4194305);
INSERT INTO SM_SEVT
	VALUES (4194305,
	4194312,
	4194305);
INSERT INTO SM_EVT
	VALUES (4194305,
	4194312,
	4194305,
	1,
	'Slot Assigned',
	0,
	'',
	'D_S1',
	'
');
INSERT INTO SM_LEVT
	VALUES (4194306,
	4194312,
	4194305);
INSERT INTO SM_SEVT
	VALUES (4194306,
	4194312,
	4194305);
INSERT INTO SM_EVT
	VALUES (4194306,
	4194312,
	4194305,
	2,
	'Free Up Slot',
	0,
	'',
	'D_S2',
	'
');
INSERT INTO SM_LEVT
	VALUES (4194307,
	4194312,
	4194305);
INSERT INTO SM_SEVT
	VALUES (4194307,
	4194312,
	4194305);
INSERT INTO SM_EVT
	VALUES (4194307,
	4194312,
	4194305,
	3,
	'Robot Removed Disk',
	0,
	'',
	'D_S3',
	'
');
INSERT INTO SM_LEVT
	VALUES (4194308,
	4194312,
	4194305);
INSERT INTO SM_SEVT
	VALUES (4194308,
	4194312,
	4194305);
INSERT INTO SM_EVT
	VALUES (4194308,
	4194312,
	4194305,
	4,
	'Robot Delivered Disk',
	0,
	'',
	'D_S4',
	'
');
INSERT INTO SM_STATE
	VALUES (4194305,
	4194312,
	4194305,
	'Unassigned',
	1,
	0);
INSERT INTO SM_SEME
	VALUES (4194305,
	4194305,
	4194312,
	4194305);
INSERT INTO SM_EIGN
	VALUES (4194305,
	4194306,
	4194312,
	4194305,
	'');
INSERT INTO SM_SEME
	VALUES (4194305,
	4194306,
	4194312,
	4194305);
INSERT INTO SM_EIGN
	VALUES (4194305,
	4194307,
	4194312,
	4194305,
	'');
INSERT INTO SM_SEME
	VALUES (4194305,
	4194307,
	4194312,
	4194305);
INSERT INTO SM_EIGN
	VALUES (4194305,
	4194308,
	4194312,
	4194305,
	'');
INSERT INTO SM_SEME
	VALUES (4194305,
	4194308,
	4194312,
	4194305);
INSERT INTO SM_STATE
	VALUES (4194306,
	4194312,
	4194305,
	'Assigned and Waiting for Disk',
	2,
	0);
INSERT INTO SM_EIGN
	VALUES (4194306,
	4194305,
	4194312,
	4194305,
	'');
INSERT INTO SM_SEME
	VALUES (4194306,
	4194305,
	4194312,
	4194305);
INSERT INTO SM_EIGN
	VALUES (4194306,
	4194306,
	4194312,
	4194305,
	'');
INSERT INTO SM_SEME
	VALUES (4194306,
	4194306,
	4194312,
	4194305);
INSERT INTO SM_EIGN
	VALUES (4194306,
	4194307,
	4194312,
	4194305,
	'');
INSERT INTO SM_SEME
	VALUES (4194306,
	4194307,
	4194312,
	4194305);
INSERT INTO SM_SEME
	VALUES (4194306,
	4194308,
	4194312,
	4194305);
INSERT INTO SM_STATE
	VALUES (4194307,
	4194312,
	4194305,
	'Occupied by Disk from Library',
	3,
	0);
INSERT INTO SM_EIGN
	VALUES (4194307,
	4194305,
	4194312,
	4194305,
	'');
INSERT INTO SM_SEME
	VALUES (4194307,
	4194305,
	4194312,
	4194305);
INSERT INTO SM_EIGN
	VALUES (4194307,
	4194306,
	4194312,
	4194305,
	'');
INSERT INTO SM_SEME
	VALUES (4194307,
	4194306,
	4194312,
	4194305);
INSERT INTO SM_SEME
	VALUES (4194307,
	4194307,
	4194312,
	4194305);
INSERT INTO SM_EIGN
	VALUES (4194307,
	4194308,
	4194312,
	4194305,
	'');
INSERT INTO SM_SEME
	VALUES (4194307,
	4194308,
	4194312,
	4194305);
INSERT INTO SM_STATE
	VALUES (4194308,
	4194312,
	4194305,
	'Waiting for Disk to Return from Drive',
	4,
	0);
INSERT INTO SM_EIGN
	VALUES (4194308,
	4194305,
	4194312,
	4194305,
	'');
INSERT INTO SM_SEME
	VALUES (4194308,
	4194305,
	4194312,
	4194305);
INSERT INTO SM_EIGN
	VALUES (4194308,
	4194306,
	4194312,
	4194305,
	'');
INSERT INTO SM_SEME
	VALUES (4194308,
	4194306,
	4194312,
	4194305);
INSERT INTO SM_EIGN
	VALUES (4194308,
	4194307,
	4194312,
	4194305,
	'');
INSERT INTO SM_SEME
	VALUES (4194308,
	4194307,
	4194312,
	4194305);
INSERT INTO SM_SEME
	VALUES (4194308,
	4194308,
	4194312,
	4194305);
INSERT INTO SM_STATE
	VALUES (4194309,
	4194312,
	4194305,
	'Occupied by Disk from Drive',
	5,
	0);
INSERT INTO SM_EIGN
	VALUES (4194309,
	4194305,
	4194312,
	4194305,
	'');
INSERT INTO SM_SEME
	VALUES (4194309,
	4194305,
	4194312,
	4194305);
INSERT INTO SM_SEME
	VALUES (4194309,
	4194306,
	4194312,
	4194305);
INSERT INTO SM_SEME
	VALUES (4194309,
	4194307,
	4194312,
	4194305);
INSERT INTO SM_EIGN
	VALUES (4194309,
	4194308,
	4194312,
	4194305,
	'');
INSERT INTO SM_SEME
	VALUES (4194309,
	4194308,
	4194312,
	4194305);
INSERT INTO SM_STATE
	VALUES (4194310,
	4194312,
	4194305,
	'Waiting for Disk to Leave Slot',
	6,
	0);
INSERT INTO SM_EIGN
	VALUES (4194310,
	4194305,
	4194312,
	4194305,
	'');
INSERT INTO SM_SEME
	VALUES (4194310,
	4194305,
	4194312,
	4194305);
INSERT INTO SM_EIGN
	VALUES (4194310,
	4194306,
	4194312,
	4194305,
	'');
INSERT INTO SM_SEME
	VALUES (4194310,
	4194306,
	4194312,
	4194305);
INSERT INTO SM_SEME
	VALUES (4194310,
	4194307,
	4194312,
	4194305);
INSERT INTO SM_EIGN
	VALUES (4194310,
	4194308,
	4194312,
	4194305,
	'');
INSERT INTO SM_SEME
	VALUES (4194310,
	4194308,
	4194312,
	4194305);
INSERT INTO SM_NSTXN
	VALUES (4194305,
	4194312,
	4194305,
	4194305,
	4194305);
INSERT INTO SM_TXN
	VALUES (4194305,
	4194312,
	4194306,
	4194305);
INSERT INTO SM_NSTXN
	VALUES (4194306,
	4194312,
	4194309,
	4194306,
	4194305);
INSERT INTO SM_TXN
	VALUES (4194306,
	4194312,
	4194310,
	4194305);
INSERT INTO SM_NSTXN
	VALUES (4194307,
	4194312,
	4194310,
	4194307,
	4194305);
INSERT INTO SM_TXN
	VALUES (4194307,
	4194312,
	4194305,
	4194305);
INSERT INTO SM_NSTXN
	VALUES (4194308,
	4194312,
	4194306,
	4194308,
	4194305);
INSERT INTO SM_TXN
	VALUES (4194308,
	4194312,
	4194307,
	4194305);
INSERT INTO SM_NSTXN
	VALUES (4194309,
	4194312,
	4194307,
	4194307,
	4194305);
INSERT INTO SM_TXN
	VALUES (4194309,
	4194312,
	4194308,
	4194305);
INSERT INTO SM_NSTXN
	VALUES (4194310,
	4194312,
	4194308,
	4194308,
	4194305);
INSERT INTO SM_TXN
	VALUES (4194310,
	4194312,
	4194309,
	4194305);
INSERT INTO SM_NSTXN
	VALUES (4194311,
	4194312,
	4194309,
	4194307,
	4194305);
INSERT INTO SM_TXN
	VALUES (4194311,
	4194312,
	4194308,
	4194305);
INSERT INTO SM_MOAH
	VALUES (4194305,
	4194312,
	4194305);
INSERT INTO SM_AH
	VALUES (4194305,
	4194312);
INSERT INTO SM_ACT
	VALUES (4194305,
	4194312,
	1,
	'Assign Self.Unassigned = TRUE;

Select many offline_disks from instances of D_OD;
For each offline_disk in offline_disks
    Select one disk related by offline_disk->D_D[R3];
    If (disk.Unowned_in_Library == FALSE)
    If (disk.Unowned_in_Cabinet == FALSE)
        Generate D_D7:''Disk Left Slot on way to Library'' () to disk;
    End if;
    End if;
End for;

Generate D_SDA_A2:''Unassigned Slots Available'' () to D_SDA assigner;
',
	'');
INSERT INTO SM_MOAH
	VALUES (4194306,
	4194312,
	4194306);
INSERT INTO SM_AH
	VALUES (4194306,
	4194312);
INSERT INTO SM_ACT
	VALUES (4194306,
	4194312,
	1,
	'Assign Self.Unassigned = FALSE;

Create object instance disk_tfr of D_DT;

Select any ee_port from instances of D_P;

Select one source_loc related by ee_port->D_OL[R7];
Relate disk_tfr to source_loc across R10;

Select one dest_loc related by Self->D_OL[R7];
Relate disk_tfr to dest_loc across R11;

// Set Status Ready For Port
Assign disk_tfr.Status = "Ready_for_Entry_Exit_Port";

Generate D_P10:''EE Port Request Pending'' () to ee_port;',
	'');
INSERT INTO SM_MOAH
	VALUES (4194307,
	4194312,
	4194307);
INSERT INTO SM_AH
	VALUES (4194307,
	4194312);
INSERT INTO SM_ACT
	VALUES (4194307,
	4194312,
	1,
	'Select one  disk related by Self->D_SDA[R6]->D_OND[R6]->D_D[R3];
Generate D_D2:''Disk Arrived in Slot'' () to disk;

Select one disk_tfr related by Self->D_OL[R7]->D_DT[R11];
Delete object instance disk_tfr;
',
	'');
INSERT INTO SM_MOAH
	VALUES (4194308,
	4194312,
	4194308);
INSERT INTO SM_AH
	VALUES (4194308,
	4194312);
INSERT INTO SM_ACT
	VALUES (4194308,
	4194312,
	1,
	'',
	'');
INSERT INTO SM_MOAH
	VALUES (4194309,
	4194312,
	4194309);
INSERT INTO SM_AH
	VALUES (4194309,
	4194312);
INSERT INTO SM_ACT
	VALUES (4194309,
	4194312,
	1,
	'Select one disk related by Self->D_SDA[R6]->D_OND[R6]->D_D[R3];
Generate D_D5:''Disk Returned to Slot'' () to disk;

Select one disk_tfr related by Self->D_OL[R7]->D_DT[R11];
Delete object instance disk_tfr;
',
	'');
INSERT INTO SM_MOAH
	VALUES (4194310,
	4194312,
	4194310);
INSERT INTO SM_AH
	VALUES (4194310,
	4194312);
INSERT INTO SM_ACT
	VALUES (4194310,
	4194312,
	1,
	'Create object instance disk_tfr of D_DT;

Select any ee_port from instances of D_P;

Select one dest_loc related by ee_port->D_OL[R7];
Relate disk_tfr to dest_loc across R11;

Select one source_loc related by Self->D_OL[R7];
Relate disk_tfr to source_loc across R10;

// Set Status Ready For Port
Assign disk_tfr.Status = "Ready_for_Entry_Exit_Port";

Generate D_P10:''EE Port Request Pending'' () to ee_port;
',
	'');
INSERT INTO GD_MD
	VALUES (4194305,
	8,
	4194312,
	40,
	1,
	0,
	1,
	1,
	0,
	12,
	1600,
	4199,
	1.000000,
	0);
INSERT INTO GD_GE
	VALUES (4194306,
	4194305,
	4194310,
	41);
INSERT INTO GD_SHP
	VALUES (4194306,
	2320,
	1312,
	2640,
	1568);
INSERT INTO GD_GE
	VALUES (4194307,
	4194305,
	4194309,
	41);
INSERT INTO GD_SHP
	VALUES (4194307,
	2320,
	1680,
	2624,
	1840);
INSERT INTO GD_GE
	VALUES (4194308,
	4194305,
	4194308,
	41);
INSERT INTO GD_SHP
	VALUES (4194308,
	2320,
	1936,
	2592,
	2048);
INSERT INTO GD_GE
	VALUES (4194309,
	4194305,
	4194307,
	41);
INSERT INTO GD_SHP
	VALUES (4194309,
	1712,
	1888,
	2048,
	2048);
INSERT INTO GD_GE
	VALUES (4194310,
	4194305,
	4194306,
	41);
INSERT INTO GD_SHP
	VALUES (4194310,
	1712,
	1520,
	2048,
	1792);
INSERT INTO GD_GE
	VALUES (4194311,
	4194305,
	4194305,
	41);
INSERT INTO GD_SHP
	VALUES (4194311,
	1712,
	1248,
	2144,
	1440);
INSERT INTO GD_GE
	VALUES (4194312,
	4194305,
	4194307,
	42);
INSERT INTO GD_CON
	VALUES (4194312,
	4194306,
	4194311,
	0);
INSERT INTO GD_CTXT
	VALUES (4194312,
	0,
	0,
	0,
	0,
	0,
	0,
	2166,
	1316,
	2299,
	1361,
	-41,
	-19,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (4194313,
	4194312,
	2320,
	1360,
	2144,
	1360,
	0);
INSERT INTO GD_GE
	VALUES (4194314,
	4194305,
	4194306,
	42);
INSERT INTO GD_CON
	VALUES (4194314,
	4194307,
	4194306,
	0);
INSERT INTO GD_CTXT
	VALUES (4194314,
	0,
	0,
	0,
	0,
	0,
	0,
	2469,
	1608,
	2597,
	1646,
	21,
	-1,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (4194315,
	4194314,
	2464,
	1680,
	2464,
	1568,
	0);
INSERT INTO GD_GE
	VALUES (4194316,
	4194305,
	4194311,
	42);
INSERT INTO GD_CON
	VALUES (4194316,
	4194307,
	4194308,
	0);
INSERT INTO GD_CTXT
	VALUES (4194316,
	0,
	0,
	0,
	0,
	0,
	0,
	2570,
	1869,
	2664,
	1912,
	26,
	-4,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (4194317,
	4194316,
	2560,
	1840,
	2560,
	1936,
	0);
INSERT INTO GD_GE
	VALUES (4194318,
	4194305,
	4194310,
	42);
INSERT INTO GD_CON
	VALUES (4194318,
	4194308,
	4194307,
	0);
INSERT INTO GD_CTXT
	VALUES (4194318,
	0,
	0,
	0,
	0,
	0,
	0,
	2347,
	1865,
	2470,
	1912,
	11,
	-8,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (4194319,
	4194318,
	2352,
	1936,
	2352,
	1840,
	0);
INSERT INTO GD_GE
	VALUES (4194320,
	4194305,
	4194309,
	42);
INSERT INTO GD_CON
	VALUES (4194320,
	4194309,
	4194308,
	0);
INSERT INTO GD_CTXT
	VALUES (4194320,
	0,
	0,
	0,
	0,
	0,
	0,
	2095,
	1960,
	2261,
	1998,
	-64,
	-15,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (4194321,
	4194320,
	2048,
	2000,
	2320,
	2000,
	0);
INSERT INTO GD_GE
	VALUES (4194322,
	4194305,
	4194308,
	42);
INSERT INTO GD_CON
	VALUES (4194322,
	4194310,
	4194309,
	0);
INSERT INTO GD_CTXT
	VALUES (4194322,
	0,
	0,
	0,
	0,
	0,
	0,
	1861,
	1820,
	2018,
	1861,
	21,
	-5,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (4194323,
	4194322,
	1856,
	1792,
	1856,
	1888,
	0);
INSERT INTO GD_GE
	VALUES (4194324,
	4194305,
	4194305,
	42);
INSERT INTO GD_CON
	VALUES (4194324,
	4194311,
	4194310,
	0);
INSERT INTO GD_CTXT
	VALUES (4194324,
	0,
	0,
	0,
	0,
	0,
	0,
	1863,
	1456,
	1995,
	1490,
	23,
	-1,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (4194325,
	4194324,
	1856,
	1440,
	1856,
	1520,
	0);
INSERT INTO O_OBJ
	VALUES (1048586,
	'Online Location',
	15,
	'D_OL',
	'An Online Location is a location in the cabinet that can store a disk.  There
are three types of online locations: slots, drives and EE ports.  Online
Locations have x, y and theta coordinates.  These coordinates are used to direct
the robot when it moves to the location.  Each Online Location also has a z
coordinate.  In the current optical disk cabinet, all Online Locations have the
same value for the z coordinate. For this reason we did not capture the z
coordinate as an attribute of Online Location.  If a new cabinet is designed so
that Online Locations have varying z coordinates, the attribute z coordinate
should be added to the Online Location class.
',
	1048578);
INSERT INTO O_NBATTR
	VALUES (1048608,
	1048586);
INSERT INTO O_BATTR
	VALUES (1048608,
	1048586);
INSERT INTO O_ATTR
	VALUES (1048608,
	1048586,
	0,
	'Online_Location_ID',
	'The unique identifier of a location in the cabinet.
',
	'',
	'Online_Location_ID',
	0,
	524291);
INSERT INTO O_NBATTR
	VALUES (1048609,
	1048586);
INSERT INTO O_BATTR
	VALUES (1048609,
	1048586);
INSERT INTO O_ATTR
	VALUES (1048609,
	1048586,
	1048608,
	'X_Coordinate',
	'The x coordinate of the Online Location.
',
	'',
	'X_Coordinate',
	0,
	524291);
INSERT INTO O_NBATTR
	VALUES (1048610,
	1048586);
INSERT INTO O_BATTR
	VALUES (1048610,
	1048586);
INSERT INTO O_ATTR
	VALUES (1048610,
	1048586,
	1048609,
	'Y_Coordinate',
	'The y coordinate of the Online Location.
',
	'',
	'Y_Coordinate',
	0,
	524291);
INSERT INTO O_NBATTR
	VALUES (1048611,
	1048586);
INSERT INTO O_BATTR
	VALUES (1048611,
	1048586);
INSERT INTO O_ATTR
	VALUES (1048611,
	1048586,
	1048610,
	'Theta_Coordinate',
	'The theta coordinate of the Online Location.

This attribute represents the orientation of the location with respect to the
cabinet.  If the orientation is parallel to the ceiling of the cabinet, the
theta coordinate has a value of zero degrees (drives).  If the orientation is
perpendicular to the ceiling of the cabinet (slots, EE ports), the theta
coordinate has a value of ninety degrees.
',
	'',
	'Theta_Coordinate',
	0,
	524291);
INSERT INTO O_NBATTR
	VALUES (1048612,
	1048586);
INSERT INTO O_BATTR
	VALUES (1048612,
	1048586);
INSERT INTO O_ATTR
	VALUES (1048612,
	1048586,
	1048611,
	'current_state',
	'The current state of the Online Location.',
	'',
	'current_state',
	0,
	524295);
INSERT INTO O_ID
	VALUES (0,
	1048586);
INSERT INTO O_OIDA
	VALUES (1048608,
	1048586,
	0);
INSERT INTO O_RTIDA
	VALUES (1048608,
	1048586,
	0,
	1048586,
	1048603);
INSERT INTO O_RTIDA
	VALUES (1048608,
	1048586,
	0,
	1048587,
	1048605);
INSERT INTO O_RTIDA
	VALUES (1048608,
	1048586,
	0,
	1048581,
	1048588);
INSERT INTO SM_ISM
	VALUES (4718601,
	1048586);
INSERT INTO SM_SM
	VALUES (4718601,
	'',
	9);
INSERT INTO SM_MOORE
	VALUES (4718601);
INSERT INTO SM_SUPDT
	VALUES (4718593,
	4718601,
	0);
INSERT INTO SM_LEVT
	VALUES (4718593,
	4718601,
	4718593);
INSERT INTO SM_SEVT
	VALUES (4718593,
	4718601,
	4718593);
INSERT INTO SM_EVT
	VALUES (4718593,
	4718601,
	4718593,
	1,
	'Robot Removed Disk',
	0,
	'',
	'D_OL1',
	'
');
INSERT INTO SM_LEVT
	VALUES (4718594,
	4718601,
	4718593);
INSERT INTO SM_SEVT
	VALUES (4718594,
	4718601,
	4718593);
INSERT INTO SM_EVT
	VALUES (4718594,
	4718601,
	4718593,
	2,
	'Robot Delivered Disk',
	0,
	'',
	'D_OL2',
	'
');
INSERT INTO SM_STATE
	VALUES (4718593,
	4718601,
	4718593,
	'Removed Disk',
	1,
	0);
INSERT INTO SM_SEME
	VALUES (4718593,
	4718593,
	4718601,
	4718593);
INSERT INTO SM_SEME
	VALUES (4718593,
	4718594,
	4718601,
	4718593);
INSERT INTO SM_STATE
	VALUES (4718594,
	4718601,
	4718593,
	'Delivered Disk',
	2,
	0);
INSERT INTO SM_SEME
	VALUES (4718594,
	4718593,
	4718601,
	4718593);
INSERT INTO SM_SEME
	VALUES (4718594,
	4718594,
	4718601,
	4718593);
INSERT INTO SM_NSTXN
	VALUES (4718593,
	4718601,
	4718594,
	4718593,
	4718593);
INSERT INTO SM_TXN
	VALUES (4718593,
	4718601,
	4718593,
	4718593);
INSERT INTO SM_NSTXN
	VALUES (4718594,
	4718601,
	4718593,
	4718593,
	4718593);
INSERT INTO SM_TXN
	VALUES (4718594,
	4718601,
	4718593,
	4718593);
INSERT INTO SM_NSTXN
	VALUES (4718595,
	4718601,
	4718593,
	4718594,
	4718593);
INSERT INTO SM_TXN
	VALUES (4718595,
	4718601,
	4718594,
	4718593);
INSERT INTO SM_NSTXN
	VALUES (4718596,
	4718601,
	4718594,
	4718594,
	4718593);
INSERT INTO SM_TXN
	VALUES (4718596,
	4718601,
	4718594,
	4718593);
INSERT INTO SM_MOAH
	VALUES (4718593,
	4718601,
	4718593);
INSERT INTO SM_AH
	VALUES (4718593,
	4718601);
INSERT INTO SM_ACT
	VALUES (4718593,
	4718601,
	1,
	'Select one slot related by Self->D_S[R7];
If (not_empty slot)
    Generate D_S3:''Robot Removed Disk'' () to slot;
End if;

Select one drive related by Self->D_DR[R7];
If (not_empty drive)
    Generate D_DR5:''Robot Removed Disk'' () to drive;
End if;

Select one port related by Self->D_P[R7];
If (not_empty port)
    Generate D_P11:''Robot Removed Disk'' () to port;
End if;',
	'');
INSERT INTO SM_MOAH
	VALUES (4718594,
	4718601,
	4718594);
INSERT INTO SM_AH
	VALUES (4718594,
	4718601);
INSERT INTO SM_ACT
	VALUES (4718594,
	4718601,
	1,
	'Select one slot related by Self->D_S[R7];
If (not_empty slot)
    Generate D_S4:''Robot Delivered Disk'' () to slot;
End if;

Select one drive related by Self->D_DR[R7];
If (not_empty drive)
    Generate D_DR6:''Robot Delivered Disk'' () to drive;
End if;

Select one port related by Self->D_P[R7];
If (not_empty port)
    Generate D_P12:''Robot Delivered Disk'' () to port;
End if;',
	'');
INSERT INTO GD_MD
	VALUES (4718593,
	8,
	4718601,
	40,
	0,
	0,
	1,
	1,
	0,
	12,
	1600,
	4200,
	1.000000,
	0);
INSERT INTO GD_GE
	VALUES (4718594,
	4718593,
	4718594,
	41);
INSERT INTO GD_SHP
	VALUES (4718594,
	2112,
	1344,
	2416,
	1616);
INSERT INTO GD_GE
	VALUES (4718595,
	4718593,
	4718593,
	41);
INSERT INTO GD_SHP
	VALUES (4718595,
	1600,
	1344,
	1904,
	1616);
INSERT INTO GD_GE
	VALUES (4718596,
	4718593,
	4718593,
	42);
INSERT INTO GD_CON
	VALUES (4718596,
	4718594,
	4718595,
	0);
INSERT INTO GD_CTXT
	VALUES (4718596,
	0,
	0,
	0,
	0,
	0,
	0,
	1958,
	1507,
	2060,
	1551,
	-25,
	-20,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (4718597,
	4718596,
	2112,
	1552,
	1904,
	1552,
	0);
INSERT INTO GD_GE
	VALUES (4718598,
	4718593,
	4718595,
	42);
INSERT INTO GD_CON
	VALUES (4718598,
	4718595,
	4718594,
	0);
INSERT INTO GD_CTXT
	VALUES (4718598,
	0,
	0,
	0,
	0,
	0,
	0,
	1956,
	1377,
	2065,
	1422,
	-27,
	-22,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (4718599,
	4718598,
	1904,
	1424,
	2112,
	1424,
	0);
INSERT INTO GD_GE
	VALUES (4718600,
	4718593,
	4718596,
	42);
INSERT INTO GD_CON
	VALUES (4718600,
	4718594,
	4718594,
	0);
INSERT INTO GD_CTXT
	VALUES (4718600,
	0,
	0,
	0,
	0,
	0,
	0,
	2144,
	1244,
	2365,
	1278,
	-103,
	-11,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (4718601,
	4718600,
	2400,
	1344,
	2400,
	1280,
	0);
INSERT INTO GD_LS
	VALUES (4718602,
	4718600,
	2400,
	1280,
	2144,
	1280,
	4718601);
INSERT INTO GD_LS
	VALUES (4718603,
	4718600,
	2144,
	1280,
	2144,
	1344,
	4718602);
INSERT INTO GD_GE
	VALUES (4718604,
	4718593,
	4718594,
	42);
INSERT INTO GD_CON
	VALUES (4718604,
	4718595,
	4718595,
	0);
INSERT INTO GD_CTXT
	VALUES (4718604,
	0,
	0,
	0,
	0,
	0,
	0,
	1669,
	1245,
	1845,
	1279,
	-50,
	-10,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (4718605,
	4718604,
	1632,
	1344,
	1632,
	1280,
	0);
INSERT INTO GD_LS
	VALUES (4718606,
	4718604,
	1632,
	1280,
	1856,
	1280,
	4718605);
INSERT INTO GD_LS
	VALUES (4718607,
	4718604,
	1856,
	1280,
	1856,
	1344,
	4718606);
INSERT INTO O_OBJ
	VALUES (1048587,
	'Slot_Disk Assignment',
	5,
	'D_SDA',
	'Before a disk can be brought into the cabinet, it must be assigned to a slot.
An instance of this class is created when a disk is assigned to a slot, and is
deleted when a disk is ejected from the cabinet.
',
	1048578);
INSERT INTO O_REF
	VALUES (1048587,
	1048579,
	0,
	1048591,
	1048582,
	1048594,
	1048593,
	1048613,
	1048593,
	0,
	0,
	'Reference Domain:
');
INSERT INTO O_RATTR
	VALUES (1048613,
	1048587,
	1048577,
	1048577,
	1);
INSERT INTO O_ATTR
	VALUES (1048613,
	1048587,
	0,
	'Disk_ID',
	'The identifier of the disk.  This attribute, together with Slot Disk
Assigmnent.Slot ID, formalizes the association Online Disk IS ASSIGNED TO Slot.
',
	'',
	'Disk_ID',
	0,
	524296);
INSERT INTO O_REF
	VALUES (1048587,
	1048585,
	0,
	1048605,
	1048582,
	1048594,
	1048592,
	1048614,
	1048594,
	0,
	0,
	'Reference Domain:
');
INSERT INTO O_RATTR
	VALUES (1048614,
	1048587,
	1048608,
	1048586,
	1);
INSERT INTO O_ATTR
	VALUES (1048614,
	1048587,
	1048613,
	'Slot_ID',
	'The identifier of the slot. This attribute, together with Slot Disk
Assignment.Disk ID, formalizes the association Online Disk IS ASSIGNED TO Slot.
',
	'',
	'Slot_ID',
	0,
	524296);
INSERT INTO O_ID
	VALUES (0,
	1048587);
INSERT INTO O_OIDA
	VALUES (1048613,
	1048587,
	0);
INSERT INTO SM_ASM
	VALUES (5242890,
	1048587);
INSERT INTO SM_SM
	VALUES (5242890,
	'',
	10);
INSERT INTO SM_MOORE
	VALUES (5242890);
INSERT INTO SM_SUPDT
	VALUES (5242881,
	5242890,
	0);
INSERT INTO SM_LEVT
	VALUES (5242881,
	5242890,
	5242881);
INSERT INTO SM_SEVT
	VALUES (5242881,
	5242890,
	5242881);
INSERT INTO SM_EVT
	VALUES (5242881,
	5242890,
	5242881,
	1,
	'Request Slot',
	0,
	'',
	'D_SDA_A1',
	'
');
INSERT INTO SM_LEVT
	VALUES (5242882,
	5242890,
	5242881);
INSERT INTO SM_SEVT
	VALUES (5242882,
	5242890,
	5242881);
INSERT INTO SM_EVT
	VALUES (5242882,
	5242890,
	5242881,
	2,
	'Unassigned Slots Available',
	0,
	'',
	'D_SDA_A2',
	'
');
INSERT INTO SM_LEVT
	VALUES (5242883,
	5242890,
	5242881);
INSERT INTO SM_SEVT
	VALUES (5242883,
	5242890,
	5242881);
INSERT INTO SM_EVT
	VALUES (5242883,
	5242890,
	5242881,
	3,
	'Slot Assigned',
	0,
	'',
	'D_SDA_A3',
	'
');
INSERT INTO SM_STATE
	VALUES (5242881,
	5242890,
	5242881,
	'Waiting for a Disk',
	1,
	0);
INSERT INTO SM_SEME
	VALUES (5242881,
	5242881,
	5242890,
	5242881);
INSERT INTO SM_EIGN
	VALUES (5242881,
	5242882,
	5242890,
	5242881,
	'');
INSERT INTO SM_SEME
	VALUES (5242881,
	5242882,
	5242890,
	5242881);
INSERT INTO SM_EIGN
	VALUES (5242881,
	5242883,
	5242890,
	5242881,
	'');
INSERT INTO SM_SEME
	VALUES (5242881,
	5242883,
	5242890,
	5242881);
INSERT INTO SM_STATE
	VALUES (5242882,
	5242890,
	5242881,
	'Waiting for Free Slot',
	2,
	0);
INSERT INTO SM_EIGN
	VALUES (5242882,
	5242881,
	5242890,
	5242881,
	'');
INSERT INTO SM_SEME
	VALUES (5242882,
	5242881,
	5242890,
	5242881);
INSERT INTO SM_SEME
	VALUES (5242882,
	5242882,
	5242890,
	5242881);
INSERT INTO SM_EIGN
	VALUES (5242882,
	5242883,
	5242890,
	5242881,
	'');
INSERT INTO SM_SEME
	VALUES (5242882,
	5242883,
	5242890,
	5242881);
INSERT INTO SM_STATE
	VALUES (5242883,
	5242890,
	5242881,
	'Assigning Slot to Disk',
	3,
	0);
INSERT INTO SM_EIGN
	VALUES (5242883,
	5242881,
	5242890,
	5242881,
	'');
INSERT INTO SM_SEME
	VALUES (5242883,
	5242881,
	5242890,
	5242881);
INSERT INTO SM_EIGN
	VALUES (5242883,
	5242882,
	5242890,
	5242881,
	'');
INSERT INTO SM_SEME
	VALUES (5242883,
	5242882,
	5242890,
	5242881);
INSERT INTO SM_SEME
	VALUES (5242883,
	5242883,
	5242890,
	5242881);
INSERT INTO SM_NSTXN
	VALUES (5242881,
	5242890,
	5242883,
	5242883,
	5242881);
INSERT INTO SM_TXN
	VALUES (5242881,
	5242890,
	5242881,
	5242881);
INSERT INTO SM_NSTXN
	VALUES (5242882,
	5242890,
	5242882,
	5242882,
	5242881);
INSERT INTO SM_TXN
	VALUES (5242882,
	5242890,
	5242883,
	5242881);
INSERT INTO SM_NSTXN
	VALUES (5242883,
	5242890,
	5242881,
	5242881,
	5242881);
INSERT INTO SM_TXN
	VALUES (5242883,
	5242890,
	5242882,
	5242881);
INSERT INTO SM_MOAH
	VALUES (5242881,
	5242890,
	5242881);
INSERT INTO SM_AH
	VALUES (5242881,
	5242890);
INSERT INTO SM_ACT
	VALUES (5242881,
	5242890,
	1,
	'Select many disk_set from instances of D_D where (selected.Waiting_for_Slot == TRUE);
For each disk in disk_set
    Select one disk_assignment related by disk->D_OND[R3]->D_SDA[R6];
    If (empty disk_assignment)
        Generate D_SDA_A1:''Request Slot'' () to D_SDA assigner;
    End if;
End for;
',
	'');
INSERT INTO SM_MOAH
	VALUES (5242882,
	5242890,
	5242882);
INSERT INTO SM_AH
	VALUES (5242882,
	5242890);
INSERT INTO SM_ACT
	VALUES (5242882,
	5242890,
	1,
	'Select many slots from instances of D_S where (selected.Unassigned == TRUE);
For each slot in slots
    Select one slot_assignment related by slot->D_SDA[R6];
    If (empty slot_assignment)
        Generate D_SDA_A2:''Unassigned Slots Available'' () to D_SDA assigner;
        Break;
    End if;
End for;
',
	'');
INSERT INTO SM_MOAH
	VALUES (5242883,
	5242890,
	5242883);
INSERT INTO SM_AH
	VALUES (5242883,
	5242890);
INSERT INTO SM_ACT
	VALUES (5242883,
	5242890,
	1,
	'Select many disk_set from instances of D_D where 
                                                                         (selected.Waiting_for_Slot == TRUE);
For each disk in disk_set
    Select one disk_assignment related by disk->D_OND[R3]->D_SDA[R6];
    If (empty disk_assignment)
        Select any slot from instances of D_S where (selected.Unassigned == TRUE);
        If (not_empty slot)
            Select one slot_assignment related by slot->D_SDA[R6];
            If (empty slot_assignment)
                Create object instance slot_disk of D_SDA;
                Select one online_disk related by disk->D_OND[R3];
                Relate slot to online_disk across R6 using slot_disk;
                Generate D_S1:''Slot Assigned'' () to slot;
                Generate D_SDA_A3:''Slot Assigned'' () to D_SDA assigner;
            End if;
        End if;
    End if;
End for;',
	'');
INSERT INTO GD_MD
	VALUES (5242881,
	10,
	5242890,
	40,
	0,
	0,
	1,
	1,
	0,
	12,
	1659,
	4117,
	1.000000,
	0);
INSERT INTO GD_GE
	VALUES (5242882,
	5242881,
	5242883,
	41);
INSERT INTO GD_SHP
	VALUES (5242882,
	2384,
	1328,
	2976,
	1744);
INSERT INTO GD_GE
	VALUES (5242883,
	5242881,
	5242882,
	41);
INSERT INTO GD_SHP
	VALUES (5242883,
	1680,
	1616,
	2128,
	1824);
INSERT INTO GD_GE
	VALUES (5242884,
	5242881,
	5242881,
	41);
INSERT INTO GD_SHP
	VALUES (5242884,
	1680,
	1264,
	2128,
	1488);
INSERT INTO GD_GE
	VALUES (5242885,
	5242881,
	5242883,
	42);
INSERT INTO GD_CON
	VALUES (5242885,
	5242884,
	5242883,
	0);
INSERT INTO GD_CTXT
	VALUES (5242885,
	0,
	0,
	0,
	0,
	0,
	0,
	1846,
	1536,
	2064,
	1580,
	-18,
	-2,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (5242886,
	5242885,
	1888,
	1488,
	1888,
	1616,
	0);
INSERT INTO GD_GE
	VALUES (5242887,
	5242881,
	5242881,
	42);
INSERT INTO GD_CON
	VALUES (5242887,
	5242882,
	5242884,
	0);
INSERT INTO GD_CTXT
	VALUES (5242887,
	0,
	0,
	0,
	0,
	0,
	0,
	2187,
	1359,
	2310,
	1399,
	-10,
	13,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (5242888,
	5242887,
	2384,
	1392,
	2128,
	1392,
	0);
INSERT INTO GD_GE
	VALUES (5242889,
	5242881,
	5242882,
	42);
INSERT INTO GD_CON
	VALUES (5242889,
	5242883,
	5242882,
	0);
INSERT INTO GD_CTXT
	VALUES (5242889,
	0,
	0,
	0,
	0,
	0,
	0,
	2166,
	1649,
	2345,
	1689,
	-3,
	-1,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (5242890,
	5242889,
	2128,
	1696,
	2384,
	1696,
	0);
INSERT INTO O_OBJ
	VALUES (1048588,
	'Drive',
	7,
	'D_DR',
	'A disk drive in the cabinet.  Before a disk can be read or written it must be
assigned to a drive and transferred to the drive by the robot.
',
	1048578);
INSERT INTO O_REF
	VALUES (1048588,
	1048586,
	0,
	1048608,
	1048581,
	1048590,
	1048588,
	1048615,
	1048579,
	0,
	0,
	'Reference Domain:
');
INSERT INTO O_RATTR
	VALUES (1048615,
	1048588,
	1048608,
	1048586,
	0);
INSERT INTO O_ATTR
	VALUES (1048615,
	1048588,
	0,
	'Drive_ID',
	'The identifier of the drive.  This attribute also formalizes the association
Drive IS A (SUBTYPE OF) Online Location.
',
	'',
	'Drive_ID',
	0,
	524296);
INSERT INTO O_NBATTR
	VALUES (1048616,
	1048588);
INSERT INTO O_BATTR
	VALUES (1048616,
	1048588);
INSERT INTO O_ATTR
	VALUES (1048616,
	1048588,
	1048615,
	'Idle',
	'This attribute returns true if the Drive is currently idle and false if it is
not.',
	'',
	'Idle',
	0,
	524290);
INSERT INTO O_NBATTR
	VALUES (1048617,
	1048588);
INSERT INTO O_BATTR
	VALUES (1048617,
	1048588);
INSERT INTO O_ATTR
	VALUES (1048617,
	1048588,
	1048616,
	'current_state',
	'The current state of the Drive.',
	'',
	'current_state',
	0,
	524295);
INSERT INTO O_ID
	VALUES (0,
	1048588);
INSERT INTO O_OIDA
	VALUES (1048615,
	1048588,
	0);
INSERT INTO O_RTIDA
	VALUES (1048615,
	1048588,
	0,
	1048583,
	1048596);
INSERT INTO SM_ISM
	VALUES (5767179,
	1048588);
INSERT INTO SM_SM
	VALUES (5767179,
	'',
	11);
INSERT INTO SM_MOORE
	VALUES (5767179);
INSERT INTO SM_SUPDT
	VALUES (5767169,
	5767179,
	0);
INSERT INTO SM_LEVT
	VALUES (5767169,
	5767179,
	5767169);
INSERT INTO SM_SEVT
	VALUES (5767169,
	5767179,
	5767169);
INSERT INTO SM_EVT
	VALUES (5767169,
	5767179,
	5767169,
	1,
	'Drive Assigned',
	0,
	'',
	'D_DR1',
	'
');
INSERT INTO SM_LEVT
	VALUES (5767170,
	5767179,
	5767169);
INSERT INTO SM_SEVT
	VALUES (5767170,
	5767179,
	5767169);
INSERT INTO SM_EVT
	VALUES (5767170,
	5767179,
	5767169,
	2,
	'Drive at Spin Speed',
	0,
	'',
	'D_DR2',
	'
');
INSERT INTO SM_LEVT
	VALUES (5767171,
	5767179,
	5767169);
INSERT INTO SM_SEVT
	VALUES (5767171,
	5767179,
	5767169);
INSERT INTO SM_EVT
	VALUES (5767171,
	5767179,
	5767169,
	3,
	'Disk Through With Drive',
	0,
	'',
	'D_DR3',
	'
');
INSERT INTO SM_LEVT
	VALUES (5767172,
	5767179,
	5767169);
INSERT INTO SM_SEVT
	VALUES (5767172,
	5767179,
	5767169);
INSERT INTO SM_EVT
	VALUES (5767172,
	5767179,
	5767169,
	4,
	'Drive Stopped',
	0,
	'',
	'D_DR4',
	'
');
INSERT INTO SM_LEVT
	VALUES (5767173,
	5767179,
	5767169);
INSERT INTO SM_SEVT
	VALUES (5767173,
	5767179,
	5767169);
INSERT INTO SM_EVT
	VALUES (5767173,
	5767179,
	5767169,
	5,
	'Robot Removed Disk',
	0,
	'',
	'D_DR5',
	'
');
INSERT INTO SM_LEVT
	VALUES (5767174,
	5767179,
	5767169);
INSERT INTO SM_SEVT
	VALUES (5767174,
	5767179,
	5767169);
INSERT INTO SM_EVT
	VALUES (5767174,
	5767179,
	5767169,
	6,
	'Robot Delivered Disk',
	0,
	'',
	'D_DR6',
	'
');
INSERT INTO SM_STATE
	VALUES (5767169,
	5767179,
	5767169,
	'Idle',
	1,
	0);
INSERT INTO SM_SEME
	VALUES (5767169,
	5767169,
	5767179,
	5767169);
INSERT INTO SM_EIGN
	VALUES (5767169,
	5767170,
	5767179,
	5767169,
	'');
INSERT INTO SM_SEME
	VALUES (5767169,
	5767170,
	5767179,
	5767169);
INSERT INTO SM_EIGN
	VALUES (5767169,
	5767171,
	5767179,
	5767169,
	'');
INSERT INTO SM_SEME
	VALUES (5767169,
	5767171,
	5767179,
	5767169);
INSERT INTO SM_EIGN
	VALUES (5767169,
	5767172,
	5767179,
	5767169,
	'');
INSERT INTO SM_SEME
	VALUES (5767169,
	5767172,
	5767179,
	5767169);
INSERT INTO SM_EIGN
	VALUES (5767169,
	5767173,
	5767179,
	5767169,
	'');
INSERT INTO SM_SEME
	VALUES (5767169,
	5767173,
	5767179,
	5767169);
INSERT INTO SM_EIGN
	VALUES (5767169,
	5767174,
	5767179,
	5767169,
	'');
INSERT INTO SM_SEME
	VALUES (5767169,
	5767174,
	5767179,
	5767169);
INSERT INTO SM_STATE
	VALUES (5767170,
	5767179,
	5767169,
	'Calling Disk into Drive',
	2,
	0);
INSERT INTO SM_EIGN
	VALUES (5767170,
	5767169,
	5767179,
	5767169,
	'');
INSERT INTO SM_SEME
	VALUES (5767170,
	5767169,
	5767179,
	5767169);
INSERT INTO SM_EIGN
	VALUES (5767170,
	5767170,
	5767179,
	5767169,
	'');
INSERT INTO SM_SEME
	VALUES (5767170,
	5767170,
	5767179,
	5767169);
INSERT INTO SM_EIGN
	VALUES (5767170,
	5767171,
	5767179,
	5767169,
	'');
INSERT INTO SM_SEME
	VALUES (5767170,
	5767171,
	5767179,
	5767169);
INSERT INTO SM_EIGN
	VALUES (5767170,
	5767172,
	5767179,
	5767169,
	'');
INSERT INTO SM_SEME
	VALUES (5767170,
	5767172,
	5767179,
	5767169);
INSERT INTO SM_EIGN
	VALUES (5767170,
	5767173,
	5767179,
	5767169,
	'');
INSERT INTO SM_SEME
	VALUES (5767170,
	5767173,
	5767179,
	5767169);
INSERT INTO SM_SEME
	VALUES (5767170,
	5767174,
	5767179,
	5767169);
INSERT INTO SM_STATE
	VALUES (5767171,
	5767179,
	5767169,
	'Spinning Up',
	3,
	0);
INSERT INTO SM_EIGN
	VALUES (5767171,
	5767169,
	5767179,
	5767169,
	'');
INSERT INTO SM_SEME
	VALUES (5767171,
	5767169,
	5767179,
	5767169);
INSERT INTO SM_SEME
	VALUES (5767171,
	5767170,
	5767179,
	5767169);
INSERT INTO SM_EIGN
	VALUES (5767171,
	5767171,
	5767179,
	5767169,
	'');
INSERT INTO SM_SEME
	VALUES (5767171,
	5767171,
	5767179,
	5767169);
INSERT INTO SM_EIGN
	VALUES (5767171,
	5767172,
	5767179,
	5767169,
	'');
INSERT INTO SM_SEME
	VALUES (5767171,
	5767172,
	5767179,
	5767169);
INSERT INTO SM_EIGN
	VALUES (5767171,
	5767173,
	5767179,
	5767169,
	'');
INSERT INTO SM_SEME
	VALUES (5767171,
	5767173,
	5767179,
	5767169);
INSERT INTO SM_EIGN
	VALUES (5767171,
	5767174,
	5767179,
	5767169,
	'');
INSERT INTO SM_SEME
	VALUES (5767171,
	5767174,
	5767179,
	5767169);
INSERT INTO SM_STATE
	VALUES (5767172,
	5767179,
	5767169,
	'Drive Ready',
	4,
	0);
INSERT INTO SM_EIGN
	VALUES (5767172,
	5767169,
	5767179,
	5767169,
	'');
INSERT INTO SM_SEME
	VALUES (5767172,
	5767169,
	5767179,
	5767169);
INSERT INTO SM_EIGN
	VALUES (5767172,
	5767170,
	5767179,
	5767169,
	'');
INSERT INTO SM_SEME
	VALUES (5767172,
	5767170,
	5767179,
	5767169);
INSERT INTO SM_SEME
	VALUES (5767172,
	5767171,
	5767179,
	5767169);
INSERT INTO SM_EIGN
	VALUES (5767172,
	5767172,
	5767179,
	5767169,
	'');
INSERT INTO SM_SEME
	VALUES (5767172,
	5767172,
	5767179,
	5767169);
INSERT INTO SM_EIGN
	VALUES (5767172,
	5767173,
	5767179,
	5767169,
	'');
INSERT INTO SM_SEME
	VALUES (5767172,
	5767173,
	5767179,
	5767169);
INSERT INTO SM_EIGN
	VALUES (5767172,
	5767174,
	5767179,
	5767169,
	'');
INSERT INTO SM_SEME
	VALUES (5767172,
	5767174,
	5767179,
	5767169);
INSERT INTO SM_STATE
	VALUES (5767173,
	5767179,
	5767169,
	'Spinning Down',
	5,
	0);
INSERT INTO SM_EIGN
	VALUES (5767173,
	5767169,
	5767179,
	5767169,
	'');
INSERT INTO SM_SEME
	VALUES (5767173,
	5767169,
	5767179,
	5767169);
INSERT INTO SM_EIGN
	VALUES (5767173,
	5767170,
	5767179,
	5767169,
	'');
INSERT INTO SM_SEME
	VALUES (5767173,
	5767170,
	5767179,
	5767169);
INSERT INTO SM_EIGN
	VALUES (5767173,
	5767171,
	5767179,
	5767169,
	'');
INSERT INTO SM_SEME
	VALUES (5767173,
	5767171,
	5767179,
	5767169);
INSERT INTO SM_SEME
	VALUES (5767173,
	5767172,
	5767179,
	5767169);
INSERT INTO SM_EIGN
	VALUES (5767173,
	5767173,
	5767179,
	5767169,
	'');
INSERT INTO SM_SEME
	VALUES (5767173,
	5767173,
	5767179,
	5767169);
INSERT INTO SM_EIGN
	VALUES (5767173,
	5767174,
	5767179,
	5767169,
	'');
INSERT INTO SM_SEME
	VALUES (5767173,
	5767174,
	5767179,
	5767169);
INSERT INTO SM_STATE
	VALUES (5767174,
	5767179,
	5767169,
	'Sending Disk Back to Slot',
	6,
	0);
INSERT INTO SM_EIGN
	VALUES (5767174,
	5767169,
	5767179,
	5767169,
	'');
INSERT INTO SM_SEME
	VALUES (5767174,
	5767169,
	5767179,
	5767169);
INSERT INTO SM_EIGN
	VALUES (5767174,
	5767170,
	5767179,
	5767169,
	'');
INSERT INTO SM_SEME
	VALUES (5767174,
	5767170,
	5767179,
	5767169);
INSERT INTO SM_EIGN
	VALUES (5767174,
	5767171,
	5767179,
	5767169,
	'');
INSERT INTO SM_SEME
	VALUES (5767174,
	5767171,
	5767179,
	5767169);
INSERT INTO SM_EIGN
	VALUES (5767174,
	5767172,
	5767179,
	5767169,
	'');
INSERT INTO SM_SEME
	VALUES (5767174,
	5767172,
	5767179,
	5767169);
INSERT INTO SM_SEME
	VALUES (5767174,
	5767173,
	5767179,
	5767169);
INSERT INTO SM_EIGN
	VALUES (5767174,
	5767174,
	5767179,
	5767169,
	'');
INSERT INTO SM_SEME
	VALUES (5767174,
	5767174,
	5767179,
	5767169);
INSERT INTO SM_NSTXN
	VALUES (5767169,
	5767179,
	5767169,
	5767169,
	5767169);
INSERT INTO SM_TXN
	VALUES (5767169,
	5767179,
	5767170,
	5767169);
INSERT INTO SM_NSTXN
	VALUES (5767170,
	5767179,
	5767171,
	5767170,
	5767169);
INSERT INTO SM_TXN
	VALUES (5767170,
	5767179,
	5767172,
	5767169);
INSERT INTO SM_NSTXN
	VALUES (5767171,
	5767179,
	5767172,
	5767171,
	5767169);
INSERT INTO SM_TXN
	VALUES (5767171,
	5767179,
	5767173,
	5767169);
INSERT INTO SM_NSTXN
	VALUES (5767172,
	5767179,
	5767173,
	5767172,
	5767169);
INSERT INTO SM_TXN
	VALUES (5767172,
	5767179,
	5767174,
	5767169);
INSERT INTO SM_NSTXN
	VALUES (5767173,
	5767179,
	5767170,
	5767174,
	5767169);
INSERT INTO SM_TXN
	VALUES (5767173,
	5767179,
	5767171,
	5767169);
INSERT INTO SM_NSTXN
	VALUES (5767174,
	5767179,
	5767174,
	5767173,
	5767169);
INSERT INTO SM_TXN
	VALUES (5767174,
	5767179,
	5767169,
	5767169);
INSERT INTO SM_MOAH
	VALUES (5767169,
	5767179,
	5767169);
INSERT INTO SM_AH
	VALUES (5767169,
	5767179);
INSERT INTO SM_ACT
	VALUES (5767169,
	5767179,
	1,
	'Assign Self.Idle = TRUE;

Select one drive_disk related by Self->D_DDA[R5];
Select one disk related by drive_disk->D_OND[R5];
unrelate self from disk across R5 using drive_disk;
Delete object instance drive_disk;

Generate D_DDA_A2:''Drive Free'' () to D_DDA assigner;
',
	'');
INSERT INTO SM_MOAH
	VALUES (5767170,
	5767179,
	5767170);
INSERT INTO SM_AH
	VALUES (5767170,
	5767179);
INSERT INTO SM_ACT
	VALUES (5767170,
	5767179,
	1,
	'Assign Self.Idle = FALSE;

Select one online_disk related by Self->D_DDA[R5]->D_OND[R5];
Select one slot related by online_disk->D_SDA[R6]->D_S[R6];

Select one source_loc related by slot->D_OL[R7];
Select one dest_loc related by Self->D_OL[R7];

Create object instance disk_tfr of D_DT;
Relate disk_tfr to source_loc across R10;
Relate disk_tfr to dest_loc across R11;

// Set Status Ready For Robot
Assign disk_tfr.Status = "Ready_for_Robot";

Select any robot from instances of D_R;
Generate D_R5:''Robot Request Pending'' () to robot;',
	'');
INSERT INTO SM_MOAH
	VALUES (5767171,
	5767179,
	5767171);
INSERT INTO SM_AH
	VALUES (5767171,
	5767179);
INSERT INTO SM_ACT
	VALUES (5767171,
	5767179,
	1,
	'Select one disk_tfr related by Self->D_OL[R7]->D_DT[R11];
Delete object instance disk_tfr;

// Bridge to PIO: spin_up_drive
Bridge pio1::spin_up_drive(tvs: "D_PIOBR::drive_at_spin_speed", drive_id:self.Drive_ID);',
	'');
INSERT INTO SM_MOAH
	VALUES (5767172,
	5767179,
	5767172);
INSERT INTO SM_AH
	VALUES (5767172,
	5767179);
INSERT INTO SM_ACT
	VALUES (5767172,
	5767179,
	1,
	'Select one disk related by Self->D_DDA[R5]->D_OND[R5]->D_D[R3];
Generate D_D3:''Disk in Drive'' () to disk;',
	'');
INSERT INTO SM_MOAH
	VALUES (5767173,
	5767179,
	5767173);
INSERT INTO SM_AH
	VALUES (5767173,
	5767179);
INSERT INTO SM_ACT
	VALUES (5767173,
	5767179,
	1,
	'// Bridge to PIO: spin_down_drive
Bridge pio1::spin_down_drive(tvs: "D_PIOBR::drive_stopped", drive_id:self.Drive_ID);',
	'');
INSERT INTO SM_MOAH
	VALUES (5767174,
	5767179,
	5767174);
INSERT INTO SM_AH
	VALUES (5767174,
	5767179);
INSERT INTO SM_ACT
	VALUES (5767174,
	5767179,
	1,
	'Select one online_disk related by Self->D_DDA[R5]->D_OND[R5];
Select one slot related by online_disk->D_SDA[R6]->D_S[R6];

Select one source_loc related by Self->D_OL[R7];
Select one dest_loc related by slot->D_OL[R7];

Create object instance disk_tfr of D_DT;
Relate disk_tfr to source_loc across R10;
Relate disk_tfr to dest_loc across R11;

// Set Status Ready For Robot
Assign disk_tfr.Status = "Ready_for_Robot";

Select any robot from instances of D_R;
Generate D_R5:''Robot Request Pending'' () to robot;
',
	'');
INSERT INTO GD_MD
	VALUES (5767169,
	8,
	5767179,
	40,
	1,
	0,
	1,
	1,
	0,
	12,
	1600,
	4177,
	1.000000,
	0);
INSERT INTO GD_GE
	VALUES (5767170,
	5767169,
	5767174,
	41);
INSERT INTO GD_SHP
	VALUES (5767170,
	2112,
	1280,
	2480,
	1584);
INSERT INTO GD_GE
	VALUES (5767171,
	5767169,
	5767173,
	41);
INSERT INTO GD_SHP
	VALUES (5767171,
	2128,
	1712,
	2400,
	1792);
INSERT INTO GD_GE
	VALUES (5767172,
	5767169,
	5767172,
	41);
INSERT INTO GD_SHP
	VALUES (5767172,
	2128,
	1920,
	2416,
	2032);
INSERT INTO GD_GE
	VALUES (5767173,
	5767169,
	5767171,
	41);
INSERT INTO GD_SHP
	VALUES (5767173,
	1632,
	1920,
	1856,
	2032);
INSERT INTO GD_GE
	VALUES (5767174,
	5767169,
	5767170,
	41);
INSERT INTO GD_SHP
	VALUES (5767174,
	1632,
	1504,
	1968,
	1824);
INSERT INTO GD_GE
	VALUES (5767175,
	5767169,
	5767169,
	41);
INSERT INTO GD_SHP
	VALUES (5767175,
	1632,
	1280,
	1968,
	1408);
INSERT INTO GD_GE
	VALUES (5767176,
	5767169,
	5767174,
	42);
INSERT INTO GD_CON
	VALUES (5767176,
	5767170,
	5767175,
	0);
INSERT INTO GD_CTXT
	VALUES (5767176,
	0,
	0,
	0,
	0,
	0,
	0,
	1980,
	1297,
	2091,
	1341,
	-35,
	-22,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (5767177,
	5767176,
	2112,
	1344,
	1968,
	1344,
	0);
INSERT INTO GD_GE
	VALUES (5767178,
	5767169,
	5767173,
	42);
INSERT INTO GD_CON
	VALUES (5767178,
	5767174,
	5767173,
	0);
INSERT INTO GD_CTXT
	VALUES (5767178,
	0,
	0,
	0,
	0,
	0,
	0,
	1743,
	1849,
	1856,
	1893,
	15,
	-8,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (5767179,
	5767178,
	1744,
	1824,
	1744,
	1920,
	0);
INSERT INTO GD_GE
	VALUES (5767180,
	5767169,
	5767172,
	42);
INSERT INTO GD_CON
	VALUES (5767180,
	5767171,
	5767170,
	0);
INSERT INTO GD_CTXT
	VALUES (5767180,
	0,
	0,
	0,
	0,
	0,
	0,
	2258,
	1629,
	2403,
	1670,
	18,
	-4,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (5767181,
	5767180,
	2256,
	1712,
	2256,
	1584,
	0);
INSERT INTO GD_GE
	VALUES (5767182,
	5767169,
	5767171,
	42);
INSERT INTO GD_CON
	VALUES (5767182,
	5767172,
	5767171,
	0);
INSERT INTO GD_CTXT
	VALUES (5767182,
	0,
	0,
	0,
	0,
	0,
	0,
	2258,
	1841,
	2428,
	1888,
	18,
	0,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (5767183,
	5767182,
	2256,
	1920,
	2256,
	1792,
	0);
INSERT INTO GD_GE
	VALUES (5767184,
	5767169,
	5767170,
	42);
INSERT INTO GD_CON
	VALUES (5767184,
	5767173,
	5767172,
	0);
INSERT INTO GD_CTXT
	VALUES (5767184,
	0,
	0,
	0,
	0,
	0,
	0,
	1890,
	1930,
	2078,
	1966,
	-77,
	-13,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (5767185,
	5767184,
	1856,
	1968,
	2128,
	1968,
	0);
INSERT INTO GD_GE
	VALUES (5767186,
	5767169,
	5767169,
	42);
INSERT INTO GD_CON
	VALUES (5767186,
	5767175,
	5767174,
	0);
INSERT INTO GD_CTXT
	VALUES (5767186,
	0,
	0,
	0,
	0,
	0,
	0,
	1782,
	1432,
	1949,
	1472,
	6,
	-9,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (5767187,
	5767186,
	1792,
	1408,
	1792,
	1504,
	0);
INSERT INTO O_OBJ
	VALUES (1048589,
	'Entry_Exit Port',
	8,
	'D_P',
	'The Entry/Exit port (EE port) is a special slot in the cabinet which is used to
transfer disks into and out of the cabinet.  This slot is accessible by both the
robot and the operator.  The Entry/Exit port has a door on the operator side
which is always locked, except when the system explicitly asks the operator to
insert a disk into the port or take a disk out of the cabinet.  The Entry/Exit
port is open on the robot side, just like a regular slot.  The lock on the
Entry/Exit port door prevents the robot and operator from trying to access the
port at the same time.
',
	1048578);
INSERT INTO O_REF
	VALUES (1048589,
	1048586,
	0,
	1048608,
	1048581,
	1048591,
	1048588,
	1048618,
	1048580,
	0,
	0,
	'Reference Domain:
');
INSERT INTO O_RATTR
	VALUES (1048618,
	1048589,
	1048608,
	1048586,
	0);
INSERT INTO O_ATTR
	VALUES (1048618,
	1048589,
	0,
	'Port_ID',
	'The identifier of the Entry/Exit port.  This attribute also formalizes the
association Entry-Exit Port IS A (SUBTYPE OF) Online Location.
',
	'',
	'Port_ID',
	0,
	524296);
INSERT INTO O_NBATTR
	VALUES (1048619,
	1048589);
INSERT INTO O_BATTR
	VALUES (1048619,
	1048589);
INSERT INTO O_ATTR
	VALUES (1048619,
	1048589,
	1048618,
	'current_state',
	'The current state of the Entry/Exit port.',
	'',
	'current_state',
	0,
	524295);
INSERT INTO O_REF
	VALUES (1048589,
	1048590,
	0,
	1048621,
	1048584,
	1048598,
	1048599,
	1048620,
	1048595,
	0,
	0,
	'Reference Domain:
');
INSERT INTO O_RATTR
	VALUES (1048620,
	1048589,
	1048621,
	1048590,
	1);
INSERT INTO O_ATTR
	VALUES (1048620,
	1048589,
	1048619,
	'Disk_Transfer_ID',
	'The identifier of the disk transfer instance that the entry/exit port is
currently working on.
',
	'',
	'Disk_Transfer_ID',
	0,
	524296);
INSERT INTO O_ID
	VALUES (0,
	1048589);
INSERT INTO O_OIDA
	VALUES (1048618,
	1048589,
	0);
INSERT INTO SM_ISM
	VALUES (6291468,
	1048589);
INSERT INTO SM_SM
	VALUES (6291468,
	'',
	12);
INSERT INTO SM_MOORE
	VALUES (6291468);
INSERT INTO SM_SUPDT
	VALUES (6291457,
	6291468,
	0);
INSERT INTO SM_LEVT
	VALUES (6291457,
	6291468,
	6291457);
INSERT INTO SM_SEVT
	VALUES (6291457,
	6291468,
	6291457);
INSERT INTO SM_EVT
	VALUES (6291457,
	6291468,
	6291457,
	1,
	'Operator Closes Door',
	0,
	'',
	'D_P1',
	'');
INSERT INTO SM_LEVT
	VALUES (6291458,
	6291468,
	6291457);
INSERT INTO SM_SEVT
	VALUES (6291458,
	6291468,
	6291457);
INSERT INTO SM_EVT
	VALUES (6291458,
	6291468,
	6291457,
	2,
	'EE Port Locked',
	0,
	'',
	'D_P2',
	'');
INSERT INTO SM_LEVT
	VALUES (6291459,
	6291468,
	6291457);
INSERT INTO SM_SEVT
	VALUES (6291459,
	6291468,
	6291457);
INSERT INTO SM_EVT
	VALUES (6291459,
	6291468,
	6291457,
	3,
	'Port Unlocked',
	0,
	'',
	'D_P3',
	'');
INSERT INTO SM_LEVT
	VALUES (6291460,
	6291468,
	6291457);
INSERT INTO SM_SEVT
	VALUES (6291460,
	6291468,
	6291457);
INSERT INTO SM_EVT
	VALUES (6291460,
	6291468,
	6291457,
	4,
	'Operator Inserts Disk',
	0,
	'',
	'D_P4',
	'');
INSERT INTO SM_LEVT
	VALUES (6291461,
	6291468,
	6291457);
INSERT INTO SM_SEVT
	VALUES (6291461,
	6291468,
	6291457);
INSERT INTO SM_EVT
	VALUES (6291461,
	6291468,
	6291457,
	5,
	'Operator Removes Disk',
	0,
	'',
	'D_P5',
	'');
INSERT INTO SM_LEVT
	VALUES (6291462,
	6291468,
	6291457);
INSERT INTO SM_SEVT
	VALUES (6291462,
	6291468,
	6291457);
INSERT INTO SM_EVT
	VALUES (6291462,
	6291468,
	6291457,
	6,
	'Operator Opens Door',
	0,
	'',
	'D_P6',
	'');
INSERT INTO SM_LEVT
	VALUES (6291463,
	6291468,
	6291457);
INSERT INTO SM_SEVT
	VALUES (6291463,
	6291468,
	6291457);
INSERT INTO SM_EVT
	VALUES (6291463,
	6291468,
	6291457,
	7,
	'Insert Request Selected',
	0,
	'',
	'D_P7',
	'');
INSERT INTO SM_LEVT
	VALUES (6291464,
	6291468,
	6291457);
INSERT INTO SM_SEVT
	VALUES (6291464,
	6291468,
	6291457);
INSERT INTO SM_EVT
	VALUES (6291464,
	6291468,
	6291457,
	8,
	'Eject Request Selected',
	0,
	'',
	'D_P8',
	'');
INSERT INTO SM_LEVT
	VALUES (6291465,
	6291468,
	6291457);
INSERT INTO SM_SEVT
	VALUES (6291465,
	6291468,
	6291457);
INSERT INTO SM_EVT
	VALUES (6291465,
	6291468,
	6291457,
	10,
	'EE Port Request Pending',
	0,
	'',
	'D_P10',
	'');
INSERT INTO SM_LEVT
	VALUES (6291466,
	6291468,
	6291457);
INSERT INTO SM_SEVT
	VALUES (6291466,
	6291468,
	6291457);
INSERT INTO SM_EVT
	VALUES (6291466,
	6291468,
	6291457,
	11,
	'Robot Removed Disk',
	0,
	'',
	'D_P11',
	'');
INSERT INTO SM_LEVT
	VALUES (6291467,
	6291468,
	6291457);
INSERT INTO SM_SEVT
	VALUES (6291467,
	6291468,
	6291457);
INSERT INTO SM_EVT
	VALUES (6291467,
	6291468,
	6291457,
	12,
	'Robot Delivered Disk',
	0,
	'',
	'D_P12',
	'');
INSERT INTO SM_STATE
	VALUES (6291457,
	6291468,
	6291457,
	'Idle',
	1,
	0);
INSERT INTO SM_EIGN
	VALUES (6291457,
	6291457,
	6291468,
	6291457,
	'');
INSERT INTO SM_SEME
	VALUES (6291457,
	6291457,
	6291468,
	6291457);
INSERT INTO SM_EIGN
	VALUES (6291457,
	6291458,
	6291468,
	6291457,
	'');
INSERT INTO SM_SEME
	VALUES (6291457,
	6291458,
	6291468,
	6291457);
INSERT INTO SM_EIGN
	VALUES (6291457,
	6291459,
	6291468,
	6291457,
	'');
INSERT INTO SM_SEME
	VALUES (6291457,
	6291459,
	6291468,
	6291457);
INSERT INTO SM_EIGN
	VALUES (6291457,
	6291460,
	6291468,
	6291457,
	'');
INSERT INTO SM_SEME
	VALUES (6291457,
	6291460,
	6291468,
	6291457);
INSERT INTO SM_EIGN
	VALUES (6291457,
	6291461,
	6291468,
	6291457,
	'');
INSERT INTO SM_SEME
	VALUES (6291457,
	6291461,
	6291468,
	6291457);
INSERT INTO SM_EIGN
	VALUES (6291457,
	6291462,
	6291468,
	6291457,
	'');
INSERT INTO SM_SEME
	VALUES (6291457,
	6291462,
	6291468,
	6291457);
INSERT INTO SM_EIGN
	VALUES (6291457,
	6291463,
	6291468,
	6291457,
	'');
INSERT INTO SM_SEME
	VALUES (6291457,
	6291463,
	6291468,
	6291457);
INSERT INTO SM_EIGN
	VALUES (6291457,
	6291464,
	6291468,
	6291457,
	'');
INSERT INTO SM_SEME
	VALUES (6291457,
	6291464,
	6291468,
	6291457);
INSERT INTO SM_SEME
	VALUES (6291457,
	6291465,
	6291468,
	6291457);
INSERT INTO SM_EIGN
	VALUES (6291457,
	6291466,
	6291468,
	6291457,
	'');
INSERT INTO SM_SEME
	VALUES (6291457,
	6291466,
	6291468,
	6291457);
INSERT INTO SM_EIGN
	VALUES (6291457,
	6291467,
	6291468,
	6291457,
	'');
INSERT INTO SM_SEME
	VALUES (6291457,
	6291467,
	6291468,
	6291457);
INSERT INTO SM_STATE
	VALUES (6291458,
	6291468,
	6291457,
	'Selecting a Disk Transfer',
	2,
	0);
INSERT INTO SM_EIGN
	VALUES (6291458,
	6291457,
	6291468,
	6291457,
	'');
INSERT INTO SM_SEME
	VALUES (6291458,
	6291457,
	6291468,
	6291457);
INSERT INTO SM_EIGN
	VALUES (6291458,
	6291458,
	6291468,
	6291457,
	'');
INSERT INTO SM_SEME
	VALUES (6291458,
	6291458,
	6291468,
	6291457);
INSERT INTO SM_EIGN
	VALUES (6291458,
	6291459,
	6291468,
	6291457,
	'');
INSERT INTO SM_SEME
	VALUES (6291458,
	6291459,
	6291468,
	6291457);
INSERT INTO SM_EIGN
	VALUES (6291458,
	6291460,
	6291468,
	6291457,
	'');
INSERT INTO SM_SEME
	VALUES (6291458,
	6291460,
	6291468,
	6291457);
INSERT INTO SM_EIGN
	VALUES (6291458,
	6291461,
	6291468,
	6291457,
	'');
INSERT INTO SM_SEME
	VALUES (6291458,
	6291461,
	6291468,
	6291457);
INSERT INTO SM_EIGN
	VALUES (6291458,
	6291462,
	6291468,
	6291457,
	'');
INSERT INTO SM_SEME
	VALUES (6291458,
	6291462,
	6291468,
	6291457);
INSERT INTO SM_SEME
	VALUES (6291458,
	6291463,
	6291468,
	6291457);
INSERT INTO SM_SEME
	VALUES (6291458,
	6291464,
	6291468,
	6291457);
INSERT INTO SM_EIGN
	VALUES (6291458,
	6291465,
	6291468,
	6291457,
	'');
INSERT INTO SM_SEME
	VALUES (6291458,
	6291465,
	6291468,
	6291457);
INSERT INTO SM_EIGN
	VALUES (6291458,
	6291466,
	6291468,
	6291457,
	'');
INSERT INTO SM_SEME
	VALUES (6291458,
	6291466,
	6291468,
	6291457);
INSERT INTO SM_EIGN
	VALUES (6291458,
	6291467,
	6291468,
	6291457,
	'');
INSERT INTO SM_SEME
	VALUES (6291458,
	6291467,
	6291468,
	6291457);
INSERT INTO SM_STATE
	VALUES (6291459,
	6291468,
	6291457,
	'Unlocking the Door on Insert',
	3,
	0);
INSERT INTO SM_EIGN
	VALUES (6291459,
	6291457,
	6291468,
	6291457,
	'');
INSERT INTO SM_SEME
	VALUES (6291459,
	6291457,
	6291468,
	6291457);
INSERT INTO SM_EIGN
	VALUES (6291459,
	6291458,
	6291468,
	6291457,
	'');
INSERT INTO SM_SEME
	VALUES (6291459,
	6291458,
	6291468,
	6291457);
INSERT INTO SM_SEME
	VALUES (6291459,
	6291459,
	6291468,
	6291457);
INSERT INTO SM_EIGN
	VALUES (6291459,
	6291460,
	6291468,
	6291457,
	'');
INSERT INTO SM_SEME
	VALUES (6291459,
	6291460,
	6291468,
	6291457);
INSERT INTO SM_EIGN
	VALUES (6291459,
	6291461,
	6291468,
	6291457,
	'');
INSERT INTO SM_SEME
	VALUES (6291459,
	6291461,
	6291468,
	6291457);
INSERT INTO SM_EIGN
	VALUES (6291459,
	6291462,
	6291468,
	6291457,
	'');
INSERT INTO SM_SEME
	VALUES (6291459,
	6291462,
	6291468,
	6291457);
INSERT INTO SM_EIGN
	VALUES (6291459,
	6291463,
	6291468,
	6291457,
	'');
INSERT INTO SM_SEME
	VALUES (6291459,
	6291463,
	6291468,
	6291457);
INSERT INTO SM_EIGN
	VALUES (6291459,
	6291464,
	6291468,
	6291457,
	'');
INSERT INTO SM_SEME
	VALUES (6291459,
	6291464,
	6291468,
	6291457);
INSERT INTO SM_EIGN
	VALUES (6291459,
	6291465,
	6291468,
	6291457,
	'');
INSERT INTO SM_SEME
	VALUES (6291459,
	6291465,
	6291468,
	6291457);
INSERT INTO SM_EIGN
	VALUES (6291459,
	6291466,
	6291468,
	6291457,
	'');
INSERT INTO SM_SEME
	VALUES (6291459,
	6291466,
	6291468,
	6291457);
INSERT INTO SM_EIGN
	VALUES (6291459,
	6291467,
	6291468,
	6291457,
	'');
INSERT INTO SM_SEME
	VALUES (6291459,
	6291467,
	6291468,
	6291457);
INSERT INTO SM_STATE
	VALUES (6291460,
	6291468,
	6291457,
	'Waiting for Operator to Open Door on Insert',
	4,
	0);
INSERT INTO SM_EIGN
	VALUES (6291460,
	6291457,
	6291468,
	6291457,
	'');
INSERT INTO SM_SEME
	VALUES (6291460,
	6291457,
	6291468,
	6291457);
INSERT INTO SM_EIGN
	VALUES (6291460,
	6291458,
	6291468,
	6291457,
	'');
INSERT INTO SM_SEME
	VALUES (6291460,
	6291458,
	6291468,
	6291457);
INSERT INTO SM_EIGN
	VALUES (6291460,
	6291459,
	6291468,
	6291457,
	'');
INSERT INTO SM_SEME
	VALUES (6291460,
	6291459,
	6291468,
	6291457);
INSERT INTO SM_EIGN
	VALUES (6291460,
	6291460,
	6291468,
	6291457,
	'');
INSERT INTO SM_SEME
	VALUES (6291460,
	6291460,
	6291468,
	6291457);
INSERT INTO SM_EIGN
	VALUES (6291460,
	6291461,
	6291468,
	6291457,
	'');
INSERT INTO SM_SEME
	VALUES (6291460,
	6291461,
	6291468,
	6291457);
INSERT INTO SM_SEME
	VALUES (6291460,
	6291462,
	6291468,
	6291457);
INSERT INTO SM_EIGN
	VALUES (6291460,
	6291463,
	6291468,
	6291457,
	'');
INSERT INTO SM_SEME
	VALUES (6291460,
	6291463,
	6291468,
	6291457);
INSERT INTO SM_EIGN
	VALUES (6291460,
	6291464,
	6291468,
	6291457,
	'');
INSERT INTO SM_SEME
	VALUES (6291460,
	6291464,
	6291468,
	6291457);
INSERT INTO SM_EIGN
	VALUES (6291460,
	6291465,
	6291468,
	6291457,
	'');
INSERT INTO SM_SEME
	VALUES (6291460,
	6291465,
	6291468,
	6291457);
INSERT INTO SM_EIGN
	VALUES (6291460,
	6291466,
	6291468,
	6291457,
	'');
INSERT INTO SM_SEME
	VALUES (6291460,
	6291466,
	6291468,
	6291457);
INSERT INTO SM_EIGN
	VALUES (6291460,
	6291467,
	6291468,
	6291457,
	'');
INSERT INTO SM_SEME
	VALUES (6291460,
	6291467,
	6291468,
	6291457);
INSERT INTO SM_STATE
	VALUES (6291461,
	6291468,
	6291457,
	'Waiting for Operator to Insert Disk',
	5,
	0);
INSERT INTO SM_SEME
	VALUES (6291461,
	6291457,
	6291468,
	6291457);
INSERT INTO SM_EIGN
	VALUES (6291461,
	6291458,
	6291468,
	6291457,
	'');
INSERT INTO SM_SEME
	VALUES (6291461,
	6291458,
	6291468,
	6291457);
INSERT INTO SM_EIGN
	VALUES (6291461,
	6291459,
	6291468,
	6291457,
	'');
INSERT INTO SM_SEME
	VALUES (6291461,
	6291459,
	6291468,
	6291457);
INSERT INTO SM_SEME
	VALUES (6291461,
	6291460,
	6291468,
	6291457);
INSERT INTO SM_EIGN
	VALUES (6291461,
	6291461,
	6291468,
	6291457,
	'');
INSERT INTO SM_SEME
	VALUES (6291461,
	6291461,
	6291468,
	6291457);
INSERT INTO SM_EIGN
	VALUES (6291461,
	6291462,
	6291468,
	6291457,
	'');
INSERT INTO SM_SEME
	VALUES (6291461,
	6291462,
	6291468,
	6291457);
INSERT INTO SM_EIGN
	VALUES (6291461,
	6291463,
	6291468,
	6291457,
	'');
INSERT INTO SM_SEME
	VALUES (6291461,
	6291463,
	6291468,
	6291457);
INSERT INTO SM_EIGN
	VALUES (6291461,
	6291464,
	6291468,
	6291457,
	'');
INSERT INTO SM_SEME
	VALUES (6291461,
	6291464,
	6291468,
	6291457);
INSERT INTO SM_EIGN
	VALUES (6291461,
	6291465,
	6291468,
	6291457,
	'');
INSERT INTO SM_SEME
	VALUES (6291461,
	6291465,
	6291468,
	6291457);
INSERT INTO SM_EIGN
	VALUES (6291461,
	6291466,
	6291468,
	6291457,
	'');
INSERT INTO SM_SEME
	VALUES (6291461,
	6291466,
	6291468,
	6291457);
INSERT INTO SM_EIGN
	VALUES (6291461,
	6291467,
	6291468,
	6291457,
	'');
INSERT INTO SM_SEME
	VALUES (6291461,
	6291467,
	6291468,
	6291457);
INSERT INTO SM_STATE
	VALUES (6291462,
	6291468,
	6291457,
	'Waiting for Operator to Close Door on Insert',
	6,
	0);
INSERT INTO SM_SEME
	VALUES (6291462,
	6291457,
	6291468,
	6291457);
INSERT INTO SM_EIGN
	VALUES (6291462,
	6291458,
	6291468,
	6291457,
	'');
INSERT INTO SM_SEME
	VALUES (6291462,
	6291458,
	6291468,
	6291457);
INSERT INTO SM_EIGN
	VALUES (6291462,
	6291459,
	6291468,
	6291457,
	'');
INSERT INTO SM_SEME
	VALUES (6291462,
	6291459,
	6291468,
	6291457);
INSERT INTO SM_EIGN
	VALUES (6291462,
	6291460,
	6291468,
	6291457,
	'');
INSERT INTO SM_SEME
	VALUES (6291462,
	6291460,
	6291468,
	6291457);
INSERT INTO SM_SEME
	VALUES (6291462,
	6291461,
	6291468,
	6291457);
INSERT INTO SM_EIGN
	VALUES (6291462,
	6291462,
	6291468,
	6291457,
	'');
INSERT INTO SM_SEME
	VALUES (6291462,
	6291462,
	6291468,
	6291457);
INSERT INTO SM_EIGN
	VALUES (6291462,
	6291463,
	6291468,
	6291457,
	'');
INSERT INTO SM_SEME
	VALUES (6291462,
	6291463,
	6291468,
	6291457);
INSERT INTO SM_EIGN
	VALUES (6291462,
	6291464,
	6291468,
	6291457,
	'');
INSERT INTO SM_SEME
	VALUES (6291462,
	6291464,
	6291468,
	6291457);
INSERT INTO SM_EIGN
	VALUES (6291462,
	6291465,
	6291468,
	6291457,
	'');
INSERT INTO SM_SEME
	VALUES (6291462,
	6291465,
	6291468,
	6291457);
INSERT INTO SM_EIGN
	VALUES (6291462,
	6291466,
	6291468,
	6291457,
	'');
INSERT INTO SM_SEME
	VALUES (6291462,
	6291466,
	6291468,
	6291457);
INSERT INTO SM_EIGN
	VALUES (6291462,
	6291467,
	6291468,
	6291457,
	'');
INSERT INTO SM_SEME
	VALUES (6291462,
	6291467,
	6291468,
	6291457);
INSERT INTO SM_STATE
	VALUES (6291463,
	6291468,
	6291457,
	'Locking the Door on Insert',
	7,
	0);
INSERT INTO SM_EIGN
	VALUES (6291463,
	6291457,
	6291468,
	6291457,
	'');
INSERT INTO SM_SEME
	VALUES (6291463,
	6291457,
	6291468,
	6291457);
INSERT INTO SM_SEME
	VALUES (6291463,
	6291458,
	6291468,
	6291457);
INSERT INTO SM_EIGN
	VALUES (6291463,
	6291459,
	6291468,
	6291457,
	'');
INSERT INTO SM_SEME
	VALUES (6291463,
	6291459,
	6291468,
	6291457);
INSERT INTO SM_EIGN
	VALUES (6291463,
	6291460,
	6291468,
	6291457,
	'');
INSERT INTO SM_SEME
	VALUES (6291463,
	6291460,
	6291468,
	6291457);
INSERT INTO SM_EIGN
	VALUES (6291463,
	6291461,
	6291468,
	6291457,
	'');
INSERT INTO SM_SEME
	VALUES (6291463,
	6291461,
	6291468,
	6291457);
INSERT INTO SM_SEME
	VALUES (6291463,
	6291462,
	6291468,
	6291457);
INSERT INTO SM_EIGN
	VALUES (6291463,
	6291463,
	6291468,
	6291457,
	'');
INSERT INTO SM_SEME
	VALUES (6291463,
	6291463,
	6291468,
	6291457);
INSERT INTO SM_EIGN
	VALUES (6291463,
	6291464,
	6291468,
	6291457,
	'');
INSERT INTO SM_SEME
	VALUES (6291463,
	6291464,
	6291468,
	6291457);
INSERT INTO SM_EIGN
	VALUES (6291463,
	6291465,
	6291468,
	6291457,
	'');
INSERT INTO SM_SEME
	VALUES (6291463,
	6291465,
	6291468,
	6291457);
INSERT INTO SM_EIGN
	VALUES (6291463,
	6291466,
	6291468,
	6291457,
	'');
INSERT INTO SM_SEME
	VALUES (6291463,
	6291466,
	6291468,
	6291457);
INSERT INTO SM_EIGN
	VALUES (6291463,
	6291467,
	6291468,
	6291457,
	'');
INSERT INTO SM_SEME
	VALUES (6291463,
	6291467,
	6291468,
	6291457);
INSERT INTO SM_STATE
	VALUES (6291464,
	6291468,
	6291457,
	'Waiting for Robot Transfer on Insert',
	8,
	0);
INSERT INTO SM_EIGN
	VALUES (6291464,
	6291457,
	6291468,
	6291457,
	'');
INSERT INTO SM_SEME
	VALUES (6291464,
	6291457,
	6291468,
	6291457);
INSERT INTO SM_EIGN
	VALUES (6291464,
	6291458,
	6291468,
	6291457,
	'');
INSERT INTO SM_SEME
	VALUES (6291464,
	6291458,
	6291468,
	6291457);
INSERT INTO SM_EIGN
	VALUES (6291464,
	6291459,
	6291468,
	6291457,
	'');
INSERT INTO SM_SEME
	VALUES (6291464,
	6291459,
	6291468,
	6291457);
INSERT INTO SM_EIGN
	VALUES (6291464,
	6291460,
	6291468,
	6291457,
	'');
INSERT INTO SM_SEME
	VALUES (6291464,
	6291460,
	6291468,
	6291457);
INSERT INTO SM_EIGN
	VALUES (6291464,
	6291461,
	6291468,
	6291457,
	'');
INSERT INTO SM_SEME
	VALUES (6291464,
	6291461,
	6291468,
	6291457);
INSERT INTO SM_EIGN
	VALUES (6291464,
	6291462,
	6291468,
	6291457,
	'');
INSERT INTO SM_SEME
	VALUES (6291464,
	6291462,
	6291468,
	6291457);
INSERT INTO SM_EIGN
	VALUES (6291464,
	6291463,
	6291468,
	6291457,
	'');
INSERT INTO SM_SEME
	VALUES (6291464,
	6291463,
	6291468,
	6291457);
INSERT INTO SM_EIGN
	VALUES (6291464,
	6291464,
	6291468,
	6291457,
	'');
INSERT INTO SM_SEME
	VALUES (6291464,
	6291464,
	6291468,
	6291457);
INSERT INTO SM_EIGN
	VALUES (6291464,
	6291465,
	6291468,
	6291457,
	'');
INSERT INTO SM_SEME
	VALUES (6291464,
	6291465,
	6291468,
	6291457);
INSERT INTO SM_SEME
	VALUES (6291464,
	6291466,
	6291468,
	6291457);
INSERT INTO SM_EIGN
	VALUES (6291464,
	6291467,
	6291468,
	6291457,
	'');
INSERT INTO SM_SEME
	VALUES (6291464,
	6291467,
	6291468,
	6291457);
INSERT INTO SM_STATE
	VALUES (6291465,
	6291468,
	6291457,
	'Waiting for Robot Transfer on Eject',
	9,
	0);
INSERT INTO SM_EIGN
	VALUES (6291465,
	6291457,
	6291468,
	6291457,
	'');
INSERT INTO SM_SEME
	VALUES (6291465,
	6291457,
	6291468,
	6291457);
INSERT INTO SM_EIGN
	VALUES (6291465,
	6291458,
	6291468,
	6291457,
	'');
INSERT INTO SM_SEME
	VALUES (6291465,
	6291458,
	6291468,
	6291457);
INSERT INTO SM_EIGN
	VALUES (6291465,
	6291459,
	6291468,
	6291457,
	'');
INSERT INTO SM_SEME
	VALUES (6291465,
	6291459,
	6291468,
	6291457);
INSERT INTO SM_EIGN
	VALUES (6291465,
	6291460,
	6291468,
	6291457,
	'');
INSERT INTO SM_SEME
	VALUES (6291465,
	6291460,
	6291468,
	6291457);
INSERT INTO SM_EIGN
	VALUES (6291465,
	6291461,
	6291468,
	6291457,
	'');
INSERT INTO SM_SEME
	VALUES (6291465,
	6291461,
	6291468,
	6291457);
INSERT INTO SM_EIGN
	VALUES (6291465,
	6291462,
	6291468,
	6291457,
	'');
INSERT INTO SM_SEME
	VALUES (6291465,
	6291462,
	6291468,
	6291457);
INSERT INTO SM_EIGN
	VALUES (6291465,
	6291463,
	6291468,
	6291457,
	'');
INSERT INTO SM_SEME
	VALUES (6291465,
	6291463,
	6291468,
	6291457);
INSERT INTO SM_EIGN
	VALUES (6291465,
	6291464,
	6291468,
	6291457,
	'');
INSERT INTO SM_SEME
	VALUES (6291465,
	6291464,
	6291468,
	6291457);
INSERT INTO SM_EIGN
	VALUES (6291465,
	6291465,
	6291468,
	6291457,
	'');
INSERT INTO SM_SEME
	VALUES (6291465,
	6291465,
	6291468,
	6291457);
INSERT INTO SM_EIGN
	VALUES (6291465,
	6291466,
	6291468,
	6291457,
	'');
INSERT INTO SM_SEME
	VALUES (6291465,
	6291466,
	6291468,
	6291457);
INSERT INTO SM_SEME
	VALUES (6291465,
	6291467,
	6291468,
	6291457);
INSERT INTO SM_STATE
	VALUES (6291466,
	6291468,
	6291457,
	'Unlocking the Door on Eject',
	10,
	0);
INSERT INTO SM_EIGN
	VALUES (6291466,
	6291457,
	6291468,
	6291457,
	'');
INSERT INTO SM_SEME
	VALUES (6291466,
	6291457,
	6291468,
	6291457);
INSERT INTO SM_EIGN
	VALUES (6291466,
	6291458,
	6291468,
	6291457,
	'');
INSERT INTO SM_SEME
	VALUES (6291466,
	6291458,
	6291468,
	6291457);
INSERT INTO SM_SEME
	VALUES (6291466,
	6291459,
	6291468,
	6291457);
INSERT INTO SM_EIGN
	VALUES (6291466,
	6291460,
	6291468,
	6291457,
	'');
INSERT INTO SM_SEME
	VALUES (6291466,
	6291460,
	6291468,
	6291457);
INSERT INTO SM_EIGN
	VALUES (6291466,
	6291461,
	6291468,
	6291457,
	'');
INSERT INTO SM_SEME
	VALUES (6291466,
	6291461,
	6291468,
	6291457);
INSERT INTO SM_EIGN
	VALUES (6291466,
	6291462,
	6291468,
	6291457,
	'');
INSERT INTO SM_SEME
	VALUES (6291466,
	6291462,
	6291468,
	6291457);
INSERT INTO SM_EIGN
	VALUES (6291466,
	6291463,
	6291468,
	6291457,
	'');
INSERT INTO SM_SEME
	VALUES (6291466,
	6291463,
	6291468,
	6291457);
INSERT INTO SM_EIGN
	VALUES (6291466,
	6291464,
	6291468,
	6291457,
	'');
INSERT INTO SM_SEME
	VALUES (6291466,
	6291464,
	6291468,
	6291457);
INSERT INTO SM_EIGN
	VALUES (6291466,
	6291465,
	6291468,
	6291457,
	'');
INSERT INTO SM_SEME
	VALUES (6291466,
	6291465,
	6291468,
	6291457);
INSERT INTO SM_EIGN
	VALUES (6291466,
	6291466,
	6291468,
	6291457,
	'');
INSERT INTO SM_SEME
	VALUES (6291466,
	6291466,
	6291468,
	6291457);
INSERT INTO SM_EIGN
	VALUES (6291466,
	6291467,
	6291468,
	6291457,
	'');
INSERT INTO SM_SEME
	VALUES (6291466,
	6291467,
	6291468,
	6291457);
INSERT INTO SM_STATE
	VALUES (6291467,
	6291468,
	6291457,
	'Waiting for Operator to Open Door on Eject',
	11,
	0);
INSERT INTO SM_EIGN
	VALUES (6291467,
	6291457,
	6291468,
	6291457,
	'');
INSERT INTO SM_SEME
	VALUES (6291467,
	6291457,
	6291468,
	6291457);
INSERT INTO SM_EIGN
	VALUES (6291467,
	6291458,
	6291468,
	6291457,
	'');
INSERT INTO SM_SEME
	VALUES (6291467,
	6291458,
	6291468,
	6291457);
INSERT INTO SM_EIGN
	VALUES (6291467,
	6291459,
	6291468,
	6291457,
	'');
INSERT INTO SM_SEME
	VALUES (6291467,
	6291459,
	6291468,
	6291457);
INSERT INTO SM_EIGN
	VALUES (6291467,
	6291460,
	6291468,
	6291457,
	'');
INSERT INTO SM_SEME
	VALUES (6291467,
	6291460,
	6291468,
	6291457);
INSERT INTO SM_EIGN
	VALUES (6291467,
	6291461,
	6291468,
	6291457,
	'');
INSERT INTO SM_SEME
	VALUES (6291467,
	6291461,
	6291468,
	6291457);
INSERT INTO SM_SEME
	VALUES (6291467,
	6291462,
	6291468,
	6291457);
INSERT INTO SM_EIGN
	VALUES (6291467,
	6291463,
	6291468,
	6291457,
	'');
INSERT INTO SM_SEME
	VALUES (6291467,
	6291463,
	6291468,
	6291457);
INSERT INTO SM_EIGN
	VALUES (6291467,
	6291464,
	6291468,
	6291457,
	'');
INSERT INTO SM_SEME
	VALUES (6291467,
	6291464,
	6291468,
	6291457);
INSERT INTO SM_EIGN
	VALUES (6291467,
	6291465,
	6291468,
	6291457,
	'');
INSERT INTO SM_SEME
	VALUES (6291467,
	6291465,
	6291468,
	6291457);
INSERT INTO SM_EIGN
	VALUES (6291467,
	6291466,
	6291468,
	6291457,
	'');
INSERT INTO SM_SEME
	VALUES (6291467,
	6291466,
	6291468,
	6291457);
INSERT INTO SM_EIGN
	VALUES (6291467,
	6291467,
	6291468,
	6291457,
	'');
INSERT INTO SM_SEME
	VALUES (6291467,
	6291467,
	6291468,
	6291457);
INSERT INTO SM_STATE
	VALUES (6291468,
	6291468,
	6291457,
	'Waiting for Operator to Remove Disk',
	12,
	0);
INSERT INTO SM_SEME
	VALUES (6291468,
	6291457,
	6291468,
	6291457);
INSERT INTO SM_EIGN
	VALUES (6291468,
	6291458,
	6291468,
	6291457,
	'');
INSERT INTO SM_SEME
	VALUES (6291468,
	6291458,
	6291468,
	6291457);
INSERT INTO SM_EIGN
	VALUES (6291468,
	6291459,
	6291468,
	6291457,
	'');
INSERT INTO SM_SEME
	VALUES (6291468,
	6291459,
	6291468,
	6291457);
INSERT INTO SM_EIGN
	VALUES (6291468,
	6291460,
	6291468,
	6291457,
	'');
INSERT INTO SM_SEME
	VALUES (6291468,
	6291460,
	6291468,
	6291457);
INSERT INTO SM_SEME
	VALUES (6291468,
	6291461,
	6291468,
	6291457);
INSERT INTO SM_EIGN
	VALUES (6291468,
	6291462,
	6291468,
	6291457,
	'');
INSERT INTO SM_SEME
	VALUES (6291468,
	6291462,
	6291468,
	6291457);
INSERT INTO SM_EIGN
	VALUES (6291468,
	6291463,
	6291468,
	6291457,
	'');
INSERT INTO SM_SEME
	VALUES (6291468,
	6291463,
	6291468,
	6291457);
INSERT INTO SM_EIGN
	VALUES (6291468,
	6291464,
	6291468,
	6291457,
	'');
INSERT INTO SM_SEME
	VALUES (6291468,
	6291464,
	6291468,
	6291457);
INSERT INTO SM_EIGN
	VALUES (6291468,
	6291465,
	6291468,
	6291457,
	'');
INSERT INTO SM_SEME
	VALUES (6291468,
	6291465,
	6291468,
	6291457);
INSERT INTO SM_EIGN
	VALUES (6291468,
	6291466,
	6291468,
	6291457,
	'');
INSERT INTO SM_SEME
	VALUES (6291468,
	6291466,
	6291468,
	6291457);
INSERT INTO SM_EIGN
	VALUES (6291468,
	6291467,
	6291468,
	6291457,
	'');
INSERT INTO SM_SEME
	VALUES (6291468,
	6291467,
	6291468,
	6291457);
INSERT INTO SM_STATE
	VALUES (6291469,
	6291468,
	6291457,
	'Waiting for Operator to Close Door on Eject',
	13,
	0);
INSERT INTO SM_SEME
	VALUES (6291469,
	6291457,
	6291468,
	6291457);
INSERT INTO SM_EIGN
	VALUES (6291469,
	6291458,
	6291468,
	6291457,
	'');
INSERT INTO SM_SEME
	VALUES (6291469,
	6291458,
	6291468,
	6291457);
INSERT INTO SM_EIGN
	VALUES (6291469,
	6291459,
	6291468,
	6291457,
	'');
INSERT INTO SM_SEME
	VALUES (6291469,
	6291459,
	6291468,
	6291457);
INSERT INTO SM_SEME
	VALUES (6291469,
	6291460,
	6291468,
	6291457);
INSERT INTO SM_EIGN
	VALUES (6291469,
	6291461,
	6291468,
	6291457,
	'');
INSERT INTO SM_SEME
	VALUES (6291469,
	6291461,
	6291468,
	6291457);
INSERT INTO SM_EIGN
	VALUES (6291469,
	6291462,
	6291468,
	6291457,
	'');
INSERT INTO SM_SEME
	VALUES (6291469,
	6291462,
	6291468,
	6291457);
INSERT INTO SM_EIGN
	VALUES (6291469,
	6291463,
	6291468,
	6291457,
	'');
INSERT INTO SM_SEME
	VALUES (6291469,
	6291463,
	6291468,
	6291457);
INSERT INTO SM_EIGN
	VALUES (6291469,
	6291464,
	6291468,
	6291457,
	'');
INSERT INTO SM_SEME
	VALUES (6291469,
	6291464,
	6291468,
	6291457);
INSERT INTO SM_EIGN
	VALUES (6291469,
	6291465,
	6291468,
	6291457,
	'');
INSERT INTO SM_SEME
	VALUES (6291469,
	6291465,
	6291468,
	6291457);
INSERT INTO SM_EIGN
	VALUES (6291469,
	6291466,
	6291468,
	6291457,
	'');
INSERT INTO SM_SEME
	VALUES (6291469,
	6291466,
	6291468,
	6291457);
INSERT INTO SM_EIGN
	VALUES (6291469,
	6291467,
	6291468,
	6291457,
	'');
INSERT INTO SM_SEME
	VALUES (6291469,
	6291467,
	6291468,
	6291457);
INSERT INTO SM_STATE
	VALUES (6291470,
	6291468,
	6291457,
	'Locking Door on Eject',
	14,
	0);
INSERT INTO SM_EIGN
	VALUES (6291470,
	6291457,
	6291468,
	6291457,
	'');
INSERT INTO SM_SEME
	VALUES (6291470,
	6291457,
	6291468,
	6291457);
INSERT INTO SM_SEME
	VALUES (6291470,
	6291458,
	6291468,
	6291457);
INSERT INTO SM_EIGN
	VALUES (6291470,
	6291459,
	6291468,
	6291457,
	'');
INSERT INTO SM_SEME
	VALUES (6291470,
	6291459,
	6291468,
	6291457);
INSERT INTO SM_EIGN
	VALUES (6291470,
	6291460,
	6291468,
	6291457,
	'');
INSERT INTO SM_SEME
	VALUES (6291470,
	6291460,
	6291468,
	6291457);
INSERT INTO SM_EIGN
	VALUES (6291470,
	6291461,
	6291468,
	6291457,
	'');
INSERT INTO SM_SEME
	VALUES (6291470,
	6291461,
	6291468,
	6291457);
INSERT INTO SM_SEME
	VALUES (6291470,
	6291462,
	6291468,
	6291457);
INSERT INTO SM_EIGN
	VALUES (6291470,
	6291463,
	6291468,
	6291457,
	'');
INSERT INTO SM_SEME
	VALUES (6291470,
	6291463,
	6291468,
	6291457);
INSERT INTO SM_EIGN
	VALUES (6291470,
	6291464,
	6291468,
	6291457,
	'');
INSERT INTO SM_SEME
	VALUES (6291470,
	6291464,
	6291468,
	6291457);
INSERT INTO SM_EIGN
	VALUES (6291470,
	6291465,
	6291468,
	6291457,
	'');
INSERT INTO SM_SEME
	VALUES (6291470,
	6291465,
	6291468,
	6291457);
INSERT INTO SM_EIGN
	VALUES (6291470,
	6291466,
	6291468,
	6291457,
	'');
INSERT INTO SM_SEME
	VALUES (6291470,
	6291466,
	6291468,
	6291457);
INSERT INTO SM_EIGN
	VALUES (6291470,
	6291467,
	6291468,
	6291457,
	'');
INSERT INTO SM_SEME
	VALUES (6291470,
	6291467,
	6291468,
	6291457);
INSERT INTO SM_NSTXN
	VALUES (6291457,
	6291468,
	6291457,
	6291465,
	6291457);
INSERT INTO SM_TXN
	VALUES (6291457,
	6291468,
	6291458,
	6291457);
INSERT INTO SM_NSTXN
	VALUES (6291458,
	6291468,
	6291458,
	6291463,
	6291457);
INSERT INTO SM_TXN
	VALUES (6291458,
	6291468,
	6291459,
	6291457);
INSERT INTO SM_NSTXN
	VALUES (6291459,
	6291468,
	6291459,
	6291459,
	6291457);
INSERT INTO SM_TXN
	VALUES (6291459,
	6291468,
	6291460,
	6291457);
INSERT INTO SM_NSTXN
	VALUES (6291460,
	6291468,
	6291461,
	6291457,
	6291457);
INSERT INTO SM_TXN
	VALUES (6291460,
	6291468,
	6291460,
	6291457);
INSERT INTO SM_NSTXN
	VALUES (6291461,
	6291468,
	6291460,
	6291462,
	6291457);
INSERT INTO SM_TXN
	VALUES (6291461,
	6291468,
	6291461,
	6291457);
INSERT INTO SM_NSTXN
	VALUES (6291462,
	6291468,
	6291462,
	6291461,
	6291457);
INSERT INTO SM_TXN
	VALUES (6291462,
	6291468,
	6291461,
	6291457);
INSERT INTO SM_NSTXN
	VALUES (6291463,
	6291468,
	6291461,
	6291460,
	6291457);
INSERT INTO SM_TXN
	VALUES (6291463,
	6291468,
	6291462,
	6291457);
INSERT INTO SM_NSTXN
	VALUES (6291464,
	6291468,
	6291463,
	6291462,
	6291457);
INSERT INTO SM_TXN
	VALUES (6291464,
	6291468,
	6291462,
	6291457);
INSERT INTO SM_NSTXN
	VALUES (6291465,
	6291468,
	6291462,
	6291457,
	6291457);
INSERT INTO SM_TXN
	VALUES (6291465,
	6291468,
	6291463,
	6291457);
INSERT INTO SM_NSTXN
	VALUES (6291466,
	6291468,
	6291463,
	6291458,
	6291457);
INSERT INTO SM_TXN
	VALUES (6291466,
	6291468,
	6291464,
	6291457);
INSERT INTO SM_NSTXN
	VALUES (6291467,
	6291468,
	6291466,
	6291459,
	6291457);
INSERT INTO SM_TXN
	VALUES (6291467,
	6291468,
	6291467,
	6291457);
INSERT INTO SM_NSTXN
	VALUES (6291468,
	6291468,
	6291468,
	6291457,
	6291457);
INSERT INTO SM_TXN
	VALUES (6291468,
	6291468,
	6291467,
	6291457);
INSERT INTO SM_NSTXN
	VALUES (6291469,
	6291468,
	6291467,
	6291462,
	6291457);
INSERT INTO SM_TXN
	VALUES (6291469,
	6291468,
	6291468,
	6291457);
INSERT INTO SM_NSTXN
	VALUES (6291470,
	6291468,
	6291469,
	6291460,
	6291457);
INSERT INTO SM_TXN
	VALUES (6291470,
	6291468,
	6291468,
	6291457);
INSERT INTO SM_NSTXN
	VALUES (6291471,
	6291468,
	6291468,
	6291461,
	6291457);
INSERT INTO SM_TXN
	VALUES (6291471,
	6291468,
	6291469,
	6291457);
INSERT INTO SM_NSTXN
	VALUES (6291472,
	6291468,
	6291469,
	6291457,
	6291457);
INSERT INTO SM_TXN
	VALUES (6291472,
	6291468,
	6291470,
	6291457);
INSERT INTO SM_NSTXN
	VALUES (6291473,
	6291468,
	6291470,
	6291462,
	6291457);
INSERT INTO SM_TXN
	VALUES (6291473,
	6291468,
	6291469,
	6291457);
INSERT INTO SM_NSTXN
	VALUES (6291474,
	6291468,
	6291470,
	6291458,
	6291457);
INSERT INTO SM_TXN
	VALUES (6291474,
	6291468,
	6291457,
	6291457);
INSERT INTO SM_NSTXN
	VALUES (6291475,
	6291468,
	6291464,
	6291466,
	6291457);
INSERT INTO SM_TXN
	VALUES (6291475,
	6291468,
	6291457,
	6291457);
INSERT INTO SM_NSTXN
	VALUES (6291476,
	6291468,
	6291458,
	6291464,
	6291457);
INSERT INTO SM_TXN
	VALUES (6291476,
	6291468,
	6291465,
	6291457);
INSERT INTO SM_NSTXN
	VALUES (6291477,
	6291468,
	6291465,
	6291467,
	6291457);
INSERT INTO SM_TXN
	VALUES (6291477,
	6291468,
	6291466,
	6291457);
INSERT INTO SM_MOAH
	VALUES (6291457,
	6291468,
	6291457);
INSERT INTO SM_AH
	VALUES (6291457,
	6291468);
INSERT INTO SM_ACT
	VALUES (6291457,
	6291468,
	1,
	'Select many disk_tfr_set from instances of D_DT;
For each tmp_disk_tfr in disk_tfr_set

     // Status is Ready For Port
    If (tmp_disk_tfr.Status == "Ready_for_Entry_Exit_Port")
        Generate D_P10:''EE Port Request Pending'' () To Self;
        Select one disk_tfr related by Self->D_DT[R8];
        Unrelate Self from disk_tfr across R8;
    End If;
End for;
',
	'');
INSERT INTO SM_MOAH
	VALUES (6291458,
	6291468,
	6291458);
INSERT INTO SM_AH
	VALUES (6291458,
	6291468);
INSERT INTO SM_ACT
	VALUES (6291458,
	6291468,
	1,
	'Select many disk_tfr_set from instances of D_DT;
For each disk_tfr in disk_tfr_set

    // Select Status = Ready For Port
    If (disk_tfr.Status == "Ready_for_Entry_Exit_Port")
        If (disk_tfr.Source_ID == Self.Port_ID)
            Generate D_P7:''Insert Request Selected'' () to Self;
        Else
            Generate D_P8:''Eject Request Selected'' () to Self;
        End if;
        Relate Self to disk_tfr across R8;
    End if;
End for;',
	'');
INSERT INTO SM_MOAH
	VALUES (6291459,
	6291468,
	6291459);
INSERT INTO SM_AH
	VALUES (6291459,
	6291468);
INSERT INTO SM_ACT
	VALUES (6291459,
	6291468,
	1,
	'// Bridge to PIO:: Unlock door
Bridge pio1::unlock_door(tvs:"D_PIOBR::port_unlocked", ee_id:self.Port_ID);',
	'');
INSERT INTO SM_MOAH
	VALUES (6291460,
	6291468,
	6291460);
INSERT INTO SM_AH
	VALUES (6291460,
	6291468);
INSERT INTO SM_ACT
	VALUES (6291460,
	6291468,
	1,
	'// Tell Operator to Insert Disk
select one disk related by self->D_DT[R8]->D_OL[R11]->D_S[R7]->D_SDA[R6]->D_OND[R6]->D_D[R3];
Bridge pio1::get_disk(tvs1:"D_PIOBR::operator_opens_door",tvs2:"D_PIOBR::operator_inserts_disk",tvs3:"D_PIOBR::operator_closes_door", ee_id:self.Port_ID, disk_id:disk.Disk_ID);
',
	'');
INSERT INTO SM_MOAH
	VALUES (6291461,
	6291468,
	6291461);
INSERT INTO SM_AH
	VALUES (6291461,
	6291468);
INSERT INTO SM_ACT
	VALUES (6291461,
	6291468,
	1,
	'// Action Logic:
',
	'');
INSERT INTO SM_MOAH
	VALUES (6291462,
	6291468,
	6291462);
INSERT INTO SM_AH
	VALUES (6291462,
	6291468);
INSERT INTO SM_ACT
	VALUES (6291462,
	6291468,
	1,
	'// Action Logic:
',
	'');
INSERT INTO SM_MOAH
	VALUES (6291463,
	6291468,
	6291463);
INSERT INTO SM_AH
	VALUES (6291463,
	6291468);
INSERT INTO SM_ACT
	VALUES (6291463,
	6291468,
	1,
	'// Action Logic:
Bridge pio1::lock_door(tvs:"D_PIOBR::port_locked", ee_id:self.Port_ID);',
	'');
INSERT INTO SM_MOAH
	VALUES (6291464,
	6291468,
	6291464);
INSERT INTO SM_AH
	VALUES (6291464,
	6291468);
INSERT INTO SM_ACT
	VALUES (6291464,
	6291468,
	1,
	'Select one disk_tfr related by Self->D_DT[R8];

// Set Status Ready For Robot
Assign disk_tfr.Status = "Ready_for_Robot";

Select any robot from instances of D_R;
Generate D_R5:''Robot Request Pending'' () to robot;
',
	'');
INSERT INTO SM_MOAH
	VALUES (6291465,
	6291468,
	6291465);
INSERT INTO SM_AH
	VALUES (6291465,
	6291468);
INSERT INTO SM_ACT
	VALUES (6291465,
	6291468,
	1,
	'Select one disk_tfr related by Self->D_DT[R8];

// Set Status = Ready For Robot
Assign disk_tfr.Status = "Ready_for_Robot";

Select any robot from instances of D_R;
Generate D_R5:''Robot Request Pending'' () to robot;
',
	'');
INSERT INTO SM_MOAH
	VALUES (6291466,
	6291468,
	6291466);
INSERT INTO SM_AH
	VALUES (6291466,
	6291468);
INSERT INTO SM_ACT
	VALUES (6291466,
	6291468,
	1,
	'Select one disk_tfr related by Self->D_DT[R8];

Unrelate Self from disk_tfr across R8;
Delete object instance disk_tfr;

// Generate events for simulation
Generate D_P3:''Port Unlocked'' () to Self;
Generate D_P6:''Operator Opens Door'' () to Self;
Generate D_P5:''Operator Removes Disk'' () to Self;
Generate D_P1:''Operator Closes Door'' () to Self;
Generate D_P2:''EE Port Locked'' () to Self;
',
	'');
INSERT INTO SM_MOAH
	VALUES (6291467,
	6291468,
	6291467);
INSERT INTO SM_AH
	VALUES (6291467,
	6291468);
INSERT INTO SM_ACT
	VALUES (6291467,
	6291468,
	1,
	'
',
	'');
INSERT INTO SM_MOAH
	VALUES (6291468,
	6291468,
	6291468);
INSERT INTO SM_AH
	VALUES (6291468,
	6291468);
INSERT INTO SM_ACT
	VALUES (6291468,
	6291468,
	1,
	'
',
	'');
INSERT INTO SM_MOAH
	VALUES (6291469,
	6291468,
	6291469);
INSERT INTO SM_AH
	VALUES (6291469,
	6291468);
INSERT INTO SM_ACT
	VALUES (6291469,
	6291468,
	1,
	'
',
	'');
INSERT INTO SM_MOAH
	VALUES (6291470,
	6291468,
	6291470);
INSERT INTO SM_AH
	VALUES (6291470,
	6291468);
INSERT INTO SM_ACT
	VALUES (6291470,
	6291468,
	1,
	'
',
	'');
INSERT INTO GD_MD
	VALUES (6291457,
	8,
	6291468,
	40,
	1,
	0,
	1,
	1,
	0,
	12,
	1877,
	3797,
	1.000000,
	0);
INSERT INTO GD_GE
	VALUES (6291458,
	6291457,
	6291470,
	41);
INSERT INTO GD_SHP
	VALUES (6291458,
	1344,
	816,
	1632,
	928);
INSERT INTO GD_GE
	VALUES (6291459,
	6291457,
	6291469,
	41);
INSERT INTO GD_SHP
	VALUES (6291459,
	1344,
	1008,
	1632,
	1120);
INSERT INTO GD_GE
	VALUES (6291460,
	6291457,
	6291468,
	41);
INSERT INTO GD_SHP
	VALUES (6291460,
	1344,
	1200,
	1632,
	1312);
INSERT INTO GD_GE
	VALUES (6291461,
	6291457,
	6291467,
	41);
INSERT INTO GD_SHP
	VALUES (6291461,
	1344,
	1392,
	1632,
	1504);
INSERT INTO GD_GE
	VALUES (6291462,
	6291457,
	6291466,
	41);
INSERT INTO GD_SHP
	VALUES (6291462,
	1344,
	1584,
	1632,
	1728);
INSERT INTO GD_GE
	VALUES (6291463,
	6291457,
	6291465,
	41);
INSERT INTO GD_SHP
	VALUES (6291463,
	1584,
	1792,
	1888,
	1968);
INSERT INTO GD_GE
	VALUES (6291464,
	6291457,
	6291464,
	41);
INSERT INTO GD_SHP
	VALUES (6291464,
	2416,
	752,
	2704,
	928);
INSERT INTO GD_GE
	VALUES (6291465,
	6291457,
	6291463,
	41);
INSERT INTO GD_SHP
	VALUES (6291465,
	2416,
	1008,
	2704,
	1120);
INSERT INTO GD_GE
	VALUES (6291466,
	6291457,
	6291462,
	41);
INSERT INTO GD_SHP
	VALUES (6291466,
	2416,
	1200,
	2704,
	1312);
INSERT INTO GD_GE
	VALUES (6291467,
	6291457,
	6291461,
	41);
INSERT INTO GD_SHP
	VALUES (6291467,
	2416,
	1392,
	2704,
	1504);
INSERT INTO GD_GE
	VALUES (6291468,
	6291457,
	6291460,
	41);
INSERT INTO GD_SHP
	VALUES (6291468,
	2416,
	1584,
	2704,
	1696);
INSERT INTO GD_GE
	VALUES (6291469,
	6291457,
	6291459,
	41);
INSERT INTO GD_SHP
	VALUES (6291469,
	2288,
	1792,
	2576,
	1904);
INSERT INTO GD_GE
	VALUES (6291470,
	6291457,
	6291458,
	41);
INSERT INTO GD_SHP
	VALUES (6291470,
	1904,
	1488,
	2256,
	1728);
INSERT INTO GD_GE
	VALUES (6291471,
	6291457,
	6291457,
	41);
INSERT INTO GD_SHP
	VALUES (6291471,
	1904,
	1120,
	2304,
	1328);
INSERT INTO GD_GE
	VALUES (6291472,
	6291457,
	6291474,
	42);
INSERT INTO GD_CON
	VALUES (6291472,
	6291458,
	6291471,
	0);
INSERT INTO GD_CTXT
	VALUES (6291472,
	0,
	0,
	0,
	0,
	0,
	0,
	1797,
	826,
	1946,
	864,
	-98,
	-13,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (6291473,
	6291472,
	1632,
	864,
	1952,
	864,
	0);
INSERT INTO GD_LS
	VALUES (6291474,
	6291472,
	1952,
	864,
	1952,
	1120,
	6291473);
INSERT INTO GD_GE
	VALUES (6291475,
	6291457,
	6291473,
	42);
INSERT INTO GD_CON
	VALUES (6291475,
	6291458,
	6291459,
	0);
INSERT INTO GD_CTXT
	VALUES (6291475,
	0,
	0,
	0,
	0,
	0,
	0,
	1627,
	958,
	1754,
	995,
	27,
	5,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (6291476,
	6291475,
	1616,
	928,
	1616,
	1008,
	0);
INSERT INTO GD_GE
	VALUES (6291477,
	6291457,
	6291472,
	42);
INSERT INTO GD_CON
	VALUES (6291477,
	6291459,
	6291458,
	0);
INSERT INTO GD_CTXT
	VALUES (6291477,
	0,
	0,
	0,
	0,
	0,
	0,
	1368,
	953,
	1506,
	993,
	24,
	0,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (6291478,
	6291477,
	1360,
	1008,
	1360,
	928,
	0);
INSERT INTO GD_GE
	VALUES (6291479,
	6291457,
	6291470,
	42);
INSERT INTO GD_CON
	VALUES (6291479,
	6291459,
	6291460,
	0);
INSERT INTO GD_CTXT
	VALUES (6291479,
	0,
	0,
	0,
	0,
	0,
	0,
	1624,
	1145,
	1758,
	1186,
	24,
	0,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (6291480,
	6291479,
	1616,
	1120,
	1616,
	1200,
	0);
INSERT INTO GD_GE
	VALUES (6291481,
	6291457,
	6291471,
	42);
INSERT INTO GD_CON
	VALUES (6291481,
	6291460,
	6291459,
	0);
INSERT INTO GD_CTXT
	VALUES (6291481,
	0,
	0,
	0,
	0,
	0,
	0,
	1369,
	1145,
	1514,
	1183,
	25,
	0,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (6291482,
	6291481,
	1360,
	1200,
	1360,
	1120,
	0);
INSERT INTO GD_GE
	VALUES (6291483,
	6291457,
	6291468,
	42);
INSERT INTO GD_CON
	VALUES (6291483,
	6291460,
	6291461,
	0);
INSERT INTO GD_CTXT
	VALUES (6291483,
	0,
	0,
	0,
	0,
	0,
	0,
	1621,
	1334,
	1744,
	1371,
	21,
	-3,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (6291484,
	6291483,
	1616,
	1312,
	1616,
	1392,
	0);
INSERT INTO GD_GE
	VALUES (6291485,
	6291457,
	6291469,
	42);
INSERT INTO GD_CON
	VALUES (6291485,
	6291461,
	6291460,
	0);
INSERT INTO GD_CTXT
	VALUES (6291485,
	0,
	0,
	0,
	0,
	0,
	0,
	1365,
	1337,
	1489,
	1378,
	21,
	0,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (6291486,
	6291485,
	1360,
	1392,
	1360,
	1312,
	0);
INSERT INTO GD_GE
	VALUES (6291487,
	6291457,
	6291467,
	42);
INSERT INTO GD_CON
	VALUES (6291487,
	6291462,
	6291461,
	0);
INSERT INTO GD_CTXT
	VALUES (6291487,
	0,
	0,
	0,
	0,
	0,
	0,
	1494,
	1532,
	1621,
	1570,
	22,
	3,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (6291488,
	6291487,
	1488,
	1584,
	1488,
	1504,
	0);
INSERT INTO GD_GE
	VALUES (6291489,
	6291457,
	6291477,
	42);
INSERT INTO GD_CON
	VALUES (6291489,
	6291463,
	6291462,
	0);
INSERT INTO GD_CTXT
	VALUES (6291489,
	0,
	0,
	0,
	0,
	0,
	0,
	1602,
	1745,
	1710,
	1783,
	18,
	0,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (6291490,
	6291489,
	1600,
	1792,
	1600,
	1728,
	0);
INSERT INTO GD_GE
	VALUES (6291491,
	6291457,
	6291476,
	42);
INSERT INTO GD_CON
	VALUES (6291491,
	6291470,
	6291463,
	0);
INSERT INTO GD_CTXT
	VALUES (6291491,
	0,
	0,
	0,
	0,
	0,
	0,
	1909,
	1898,
	2018,
	1939,
	-27,
	73,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (6291492,
	6291491,
	1952,
	1728,
	1952,
	1888,
	0);
INSERT INTO GD_LS
	VALUES (6291493,
	6291491,
	1952,
	1888,
	1888,
	1888,
	6291492);
INSERT INTO GD_GE
	VALUES (6291494,
	6291457,
	6291475,
	42);
INSERT INTO GD_CON
	VALUES (6291494,
	6291464,
	6291471,
	0);
INSERT INTO GD_CTXT
	VALUES (6291494,
	0,
	0,
	0,
	0,
	0,
	0,
	2227,
	795,
	2441,
	835,
	19,
	-70,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (6291495,
	6291494,
	2416,
	832,
	2224,
	832,
	0);
INSERT INTO GD_LS
	VALUES (6291496,
	6291494,
	2224,
	832,
	2224,
	1120,
	6291495);
INSERT INTO GD_GE
	VALUES (6291497,
	6291457,
	6291466,
	42);
INSERT INTO GD_CON
	VALUES (6291497,
	6291465,
	6291464,
	0);
INSERT INTO GD_CTXT
	VALUES (6291497,
	0,
	0,
	0,
	0,
	0,
	0,
	2544,
	945,
	2728,
	988,
	0,
	-8,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (6291498,
	6291497,
	2560,
	1008,
	2560,
	928,
	0);
INSERT INTO GD_GE
	VALUES (6291499,
	6291457,
	6291464,
	42);
INSERT INTO GD_CON
	VALUES (6291499,
	6291465,
	6291466,
	0);
INSERT INTO GD_CTXT
	VALUES (6291499,
	0,
	0,
	0,
	0,
	0,
	0,
	2679,
	1143,
	2821,
	1179,
	23,
	-2,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (6291500,
	6291499,
	2672,
	1120,
	2672,
	1200,
	0);
INSERT INTO GD_GE
	VALUES (6291501,
	6291457,
	6291465,
	42);
INSERT INTO GD_CON
	VALUES (6291501,
	6291466,
	6291465,
	0);
INSERT INTO GD_CTXT
	VALUES (6291501,
	0,
	0,
	0,
	0,
	0,
	0,
	2455,
	1146,
	2588,
	1183,
	23,
	1,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (6291502,
	6291501,
	2448,
	1200,
	2448,
	1120,
	0);
INSERT INTO GD_GE
	VALUES (6291503,
	6291457,
	6291462,
	42);
INSERT INTO GD_CON
	VALUES (6291503,
	6291466,
	6291467,
	0);
INSERT INTO GD_CTXT
	VALUES (6291503,
	0,
	0,
	0,
	0,
	0,
	0,
	2679,
	1327,
	2817,
	1373,
	23,
	-10,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (6291504,
	6291503,
	2672,
	1312,
	2672,
	1392,
	0);
INSERT INTO GD_GE
	VALUES (6291505,
	6291457,
	6291463,
	42);
INSERT INTO GD_CON
	VALUES (6291505,
	6291467,
	6291466,
	0);
INSERT INTO GD_CTXT
	VALUES (6291505,
	0,
	0,
	0,
	0,
	0,
	0,
	2456,
	1336,
	2587,
	1378,
	24,
	-1,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (6291506,
	6291505,
	2448,
	1392,
	2448,
	1312,
	0);
INSERT INTO GD_GE
	VALUES (6291507,
	6291457,
	6291460,
	42);
INSERT INTO GD_CON
	VALUES (6291507,
	6291467,
	6291468,
	0);
INSERT INTO GD_CTXT
	VALUES (6291507,
	0,
	0,
	0,
	0,
	0,
	0,
	2686,
	1521,
	2816,
	1565,
	30,
	-8,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (6291508,
	6291507,
	2672,
	1504,
	2672,
	1584,
	0);
INSERT INTO GD_GE
	VALUES (6291509,
	6291457,
	6291461,
	42);
INSERT INTO GD_CON
	VALUES (6291509,
	6291468,
	6291467,
	0);
INSERT INTO GD_CTXT
	VALUES (6291509,
	0,
	0,
	0,
	0,
	0,
	0,
	2451,
	1525,
	2607,
	1573,
	19,
	-4,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (6291510,
	6291509,
	2448,
	1584,
	2448,
	1504,
	0);
INSERT INTO GD_GE
	VALUES (6291511,
	6291457,
	6291459,
	42);
INSERT INTO GD_CON
	VALUES (6291511,
	6291469,
	6291468,
	0);
INSERT INTO GD_CTXT
	VALUES (6291511,
	0,
	0,
	0,
	0,
	0,
	0,
	2491,
	1729,
	2643,
	1774,
	11,
	0,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (6291512,
	6291511,
	2496,
	1792,
	2496,
	1696,
	0);
INSERT INTO GD_GE
	VALUES (6291513,
	6291457,
	6291458,
	42);
INSERT INTO GD_CON
	VALUES (6291513,
	6291470,
	6291469,
	0);
INSERT INTO GD_CTXT
	VALUES (6291513,
	0,
	0,
	0,
	0,
	0,
	0,
	2121,
	1842,
	2277,
	1897,
	-87,
	41,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (6291514,
	6291513,
	2224,
	1728,
	2224,
	1840,
	0);
INSERT INTO GD_LS
	VALUES (6291515,
	6291513,
	2224,
	1840,
	2288,
	1840,
	6291514);
INSERT INTO GD_GE
	VALUES (6291516,
	6291457,
	6291457,
	42);
INSERT INTO GD_CON
	VALUES (6291516,
	6291471,
	6291470,
	0);
INSERT INTO GD_CTXT
	VALUES (6291516,
	0,
	0,
	0,
	0,
	0,
	0,
	2089,
	1377,
	2283,
	1427,
	25,
	-16,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (6291517,
	6291516,
	2080,
	1328,
	2080,
	1488,
	0);
INSERT INTO O_OBJ
	VALUES (1048590,
	'Disk Transfer',
	13,
	'D_DT',
	'A request to the robot to move a disk from a source location in the cabinet to a
destination location (both Online Locations).  Disk transfers are not allowed
between two slots, between two Entry/Exit ports, between two dives, from an
Entry/Exit port to a drive, or from a drive to an Entry/Exit port.  These
disallowed transfers are a result of a policy decision that was made to simplify
the analysis.  The analysis can later be expanded if desired to include all
possible transfers.
',
	1048578);
INSERT INTO O_NBATTR
	VALUES (1048621,
	1048590);
INSERT INTO O_BATTR
	VALUES (1048621,
	1048590);
INSERT INTO O_ATTR
	VALUES (1048621,
	1048590,
	0,
	'Disk_Transfer_ID',
	'The identifier of the Disk Transfer.',
	'',
	'Disk_Transfer_ID',
	0,
	524294);
INSERT INTO O_REF
	VALUES (1048590,
	1048586,
	0,
	1048608,
	1048586,
	1048602,
	1048603,
	1048622,
	1048581,
	0,
	0,
	'Reference Domain:
');
INSERT INTO O_RATTR
	VALUES (1048622,
	1048590,
	1048608,
	1048586,
	0);
INSERT INTO O_ATTR
	VALUES (1048622,
	1048590,
	1048621,
	'Source_ID',
	'The identifier of the source location.  The source location can either be the
Entry/Exit port, a drive or a slot.
',
	'',
	'Source_ID',
	0,
	524296);
INSERT INTO O_REF
	VALUES (1048590,
	1048586,
	0,
	1048608,
	1048587,
	1048604,
	1048605,
	1048623,
	1048582,
	0,
	0,
	'Reference Domain:
');
INSERT INTO O_RATTR
	VALUES (1048623,
	1048590,
	1048608,
	1048586,
	0);
INSERT INTO O_ATTR
	VALUES (1048623,
	1048590,
	1048622,
	'Destination_ID',
	'The identifier of the destination location.  The destination location can either
be the Entry/Exit port, a drive or a slot.
',
	'',
	'Destination_ID',
	0,
	524296);
INSERT INTO O_NBATTR
	VALUES (1048624,
	1048590);
INSERT INTO O_BATTR
	VALUES (1048624,
	1048590);
INSERT INTO O_ATTR
	VALUES (1048624,
	1048590,
	1048623,
	'Status',
	'The status of the transfer.',
	'',
	'Status',
	0,
	524305);
INSERT INTO O_ID
	VALUES (0,
	1048590);
INSERT INTO O_OIDA
	VALUES (1048621,
	1048590,
	0);
INSERT INTO O_RTIDA
	VALUES (1048621,
	1048590,
	0,
	1048584,
	1048599);
INSERT INTO O_RTIDA
	VALUES (1048621,
	1048590,
	0,
	1048585,
	1048601);
INSERT INTO O_OBJ
	VALUES (1048591,
	'Robot',
	12,
	'D_R',
	'The mechanism that shuttles disks between locations in the cabinet.  The robot
has a hand with grippers which can hold one disk at a time.  See also Technical
Note odms.tn.09: The Robot Hardware.
',
	1048578);
INSERT INTO O_NBATTR
	VALUES (1048625,
	1048591);
INSERT INTO O_BATTR
	VALUES (1048625,
	1048591);
INSERT INTO O_ATTR
	VALUES (1048625,
	1048591,
	0,
	'Robot_ID',
	'The identifier of the robot.',
	'',
	'Robot_ID',
	0,
	524291);
INSERT INTO O_REF
	VALUES (1048591,
	1048590,
	0,
	1048621,
	1048585,
	1048600,
	1048601,
	1048626,
	1048583,
	0,
	0,
	'Reference Domain:
');
INSERT INTO O_RATTR
	VALUES (1048626,
	1048591,
	1048621,
	1048590,
	1);
INSERT INTO O_ATTR
	VALUES (1048626,
	1048591,
	1048625,
	'Disk_Transfer_ID',
	'The identifier of the disk transfer that the robot is currently executing, if
any.
',
	'',
	'Disk_Transfer_ID',
	0,
	524296);
INSERT INTO O_NBATTR
	VALUES (1048627,
	1048591);
INSERT INTO O_BATTR
	VALUES (1048627,
	1048591);
INSERT INTO O_ATTR
	VALUES (1048627,
	1048591,
	1048626,
	'Actual_X_Coordinate',
	'The x coordinate which describes the robots current position in the cabinet.
',
	'',
	'Actual_X_Coordinate',
	0,
	524291);
INSERT INTO O_NBATTR
	VALUES (1048628,
	1048591);
INSERT INTO O_BATTR
	VALUES (1048628,
	1048591);
INSERT INTO O_ATTR
	VALUES (1048628,
	1048591,
	1048627,
	'Actual_Y_Coordinate',
	'The y coordinate which describes the robot hands current position in the
cabinet.
',
	'',
	'Actual_Y_Coordinate',
	0,
	524291);
INSERT INTO O_NBATTR
	VALUES (1048629,
	1048591);
INSERT INTO O_BATTR
	VALUES (1048629,
	1048591);
INSERT INTO O_ATTR
	VALUES (1048629,
	1048591,
	1048628,
	'Actual_Theta_Coordinate',
	'The theta coordinate which describes the current rotational position of the
robot hand.
',
	'',
	'Actual_Theta_Coordinate',
	0,
	524291);
INSERT INTO O_NBATTR
	VALUES (1048630,
	1048591);
INSERT INTO O_BATTR
	VALUES (1048630,
	1048591);
INSERT INTO O_ATTR
	VALUES (1048630,
	1048591,
	1048629,
	'current_state',
	'The current state of the robot.',
	'',
	'current_state',
	0,
	524295);
INSERT INTO O_ID
	VALUES (0,
	1048591);
INSERT INTO O_OIDA
	VALUES (1048625,
	1048591,
	0);
INSERT INTO SM_ISM
	VALUES (6815757,
	1048591);
INSERT INTO SM_SM
	VALUES (6815757,
	'',
	13);
INSERT INTO SM_MOORE
	VALUES (6815757);
INSERT INTO SM_SUPDT
	VALUES (6815745,
	6815757,
	0);
INSERT INTO SM_LEVT
	VALUES (6815745,
	6815757,
	6815745);
INSERT INTO SM_SEVT
	VALUES (6815745,
	6815757,
	6815745);
INSERT INTO SM_EVT
	VALUES (6815745,
	6815757,
	6815745,
	2,
	'Robot Move Complete',
	0,
	'',
	'D_R2',
	'');
INSERT INTO SM_LEVT
	VALUES (6815746,
	6815757,
	6815745);
INSERT INTO SM_SEVT
	VALUES (6815746,
	6815757,
	6815745);
INSERT INTO SM_EVT
	VALUES (6815746,
	6815757,
	6815745,
	3,
	'Gripper Opened',
	0,
	'',
	'D_R3',
	'');
INSERT INTO SM_LEVT
	VALUES (6815747,
	6815757,
	6815745);
INSERT INTO SM_SEVT
	VALUES (6815747,
	6815757,
	6815745);
INSERT INTO SM_EVT
	VALUES (6815747,
	6815757,
	6815745,
	4,
	'Gripper Closed on Disk',
	0,
	'',
	'D_R4',
	'');
INSERT INTO SM_LEVT
	VALUES (6815748,
	6815757,
	6815745);
INSERT INTO SM_SEVT
	VALUES (6815748,
	6815757,
	6815745);
INSERT INTO SM_EVT
	VALUES (6815748,
	6815757,
	6815745,
	5,
	'Robot Request Pending',
	0,
	'',
	'D_R5',
	'');
INSERT INTO SM_STATE
	VALUES (6815745,
	6815757,
	6815745,
	'Idle',
	1,
	0);
INSERT INTO SM_EIGN
	VALUES (6815745,
	6815745,
	6815757,
	6815745,
	'');
INSERT INTO SM_SEME
	VALUES (6815745,
	6815745,
	6815757,
	6815745);
INSERT INTO SM_EIGN
	VALUES (6815745,
	6815746,
	6815757,
	6815745,
	'');
INSERT INTO SM_SEME
	VALUES (6815745,
	6815746,
	6815757,
	6815745);
INSERT INTO SM_EIGN
	VALUES (6815745,
	6815747,
	6815757,
	6815745,
	'');
INSERT INTO SM_SEME
	VALUES (6815745,
	6815747,
	6815757,
	6815745);
INSERT INTO SM_SEME
	VALUES (6815745,
	6815748,
	6815757,
	6815745);
INSERT INTO SM_STATE
	VALUES (6815746,
	6815757,
	6815745,
	'Going to Source',
	2,
	0);
INSERT INTO SM_SEME
	VALUES (6815746,
	6815745,
	6815757,
	6815745);
INSERT INTO SM_EIGN
	VALUES (6815746,
	6815746,
	6815757,
	6815745,
	'');
INSERT INTO SM_SEME
	VALUES (6815746,
	6815746,
	6815757,
	6815745);
INSERT INTO SM_EIGN
	VALUES (6815746,
	6815747,
	6815757,
	6815745,
	'');
INSERT INTO SM_SEME
	VALUES (6815746,
	6815747,
	6815757,
	6815745);
INSERT INTO SM_EIGN
	VALUES (6815746,
	6815748,
	6815757,
	6815745,
	'');
INSERT INTO SM_SEME
	VALUES (6815746,
	6815748,
	6815757,
	6815745);
INSERT INTO SM_STATE
	VALUES (6815747,
	6815757,
	6815745,
	'Extending to Grab Disk',
	3,
	0);
INSERT INTO SM_SEME
	VALUES (6815747,
	6815745,
	6815757,
	6815745);
INSERT INTO SM_EIGN
	VALUES (6815747,
	6815746,
	6815757,
	6815745,
	'');
INSERT INTO SM_SEME
	VALUES (6815747,
	6815746,
	6815757,
	6815745);
INSERT INTO SM_EIGN
	VALUES (6815747,
	6815747,
	6815757,
	6815745,
	'');
INSERT INTO SM_SEME
	VALUES (6815747,
	6815747,
	6815757,
	6815745);
INSERT INTO SM_EIGN
	VALUES (6815747,
	6815748,
	6815757,
	6815745,
	'');
INSERT INTO SM_SEME
	VALUES (6815747,
	6815748,
	6815757,
	6815745);
INSERT INTO SM_STATE
	VALUES (6815748,
	6815757,
	6815745,
	'Grabbing Disk',
	4,
	0);
INSERT INTO SM_EIGN
	VALUES (6815748,
	6815745,
	6815757,
	6815745,
	'');
INSERT INTO SM_SEME
	VALUES (6815748,
	6815745,
	6815757,
	6815745);
INSERT INTO SM_EIGN
	VALUES (6815748,
	6815746,
	6815757,
	6815745,
	'');
INSERT INTO SM_SEME
	VALUES (6815748,
	6815746,
	6815757,
	6815745);
INSERT INTO SM_SEME
	VALUES (6815748,
	6815747,
	6815757,
	6815745);
INSERT INTO SM_EIGN
	VALUES (6815748,
	6815748,
	6815757,
	6815745,
	'');
INSERT INTO SM_SEME
	VALUES (6815748,
	6815748,
	6815757,
	6815745);
INSERT INTO SM_STATE
	VALUES (6815749,
	6815757,
	6815745,
	'Retracting After Grab',
	5,
	0);
INSERT INTO SM_SEME
	VALUES (6815749,
	6815745,
	6815757,
	6815745);
INSERT INTO SM_EIGN
	VALUES (6815749,
	6815746,
	6815757,
	6815745,
	'');
INSERT INTO SM_SEME
	VALUES (6815749,
	6815746,
	6815757,
	6815745);
INSERT INTO SM_EIGN
	VALUES (6815749,
	6815747,
	6815757,
	6815745,
	'');
INSERT INTO SM_SEME
	VALUES (6815749,
	6815747,
	6815757,
	6815745);
INSERT INTO SM_EIGN
	VALUES (6815749,
	6815748,
	6815757,
	6815745,
	'');
INSERT INTO SM_SEME
	VALUES (6815749,
	6815748,
	6815757,
	6815745);
INSERT INTO SM_STATE
	VALUES (6815750,
	6815757,
	6815745,
	'Going to Destination',
	6,
	0);
INSERT INTO SM_SEME
	VALUES (6815750,
	6815745,
	6815757,
	6815745);
INSERT INTO SM_EIGN
	VALUES (6815750,
	6815746,
	6815757,
	6815745,
	'');
INSERT INTO SM_SEME
	VALUES (6815750,
	6815746,
	6815757,
	6815745);
INSERT INTO SM_EIGN
	VALUES (6815750,
	6815747,
	6815757,
	6815745,
	'');
INSERT INTO SM_SEME
	VALUES (6815750,
	6815747,
	6815757,
	6815745);
INSERT INTO SM_EIGN
	VALUES (6815750,
	6815748,
	6815757,
	6815745,
	'');
INSERT INTO SM_SEME
	VALUES (6815750,
	6815748,
	6815757,
	6815745);
INSERT INTO SM_STATE
	VALUES (6815751,
	6815757,
	6815745,
	'Extending to Release Disk',
	7,
	0);
INSERT INTO SM_SEME
	VALUES (6815751,
	6815745,
	6815757,
	6815745);
INSERT INTO SM_EIGN
	VALUES (6815751,
	6815746,
	6815757,
	6815745,
	'');
INSERT INTO SM_SEME
	VALUES (6815751,
	6815746,
	6815757,
	6815745);
INSERT INTO SM_EIGN
	VALUES (6815751,
	6815747,
	6815757,
	6815745,
	'');
INSERT INTO SM_SEME
	VALUES (6815751,
	6815747,
	6815757,
	6815745);
INSERT INTO SM_EIGN
	VALUES (6815751,
	6815748,
	6815757,
	6815745,
	'');
INSERT INTO SM_SEME
	VALUES (6815751,
	6815748,
	6815757,
	6815745);
INSERT INTO SM_STATE
	VALUES (6815752,
	6815757,
	6815745,
	'Releasing Disk',
	8,
	0);
INSERT INTO SM_EIGN
	VALUES (6815752,
	6815745,
	6815757,
	6815745,
	'');
INSERT INTO SM_SEME
	VALUES (6815752,
	6815745,
	6815757,
	6815745);
INSERT INTO SM_SEME
	VALUES (6815752,
	6815746,
	6815757,
	6815745);
INSERT INTO SM_EIGN
	VALUES (6815752,
	6815747,
	6815757,
	6815745,
	'');
INSERT INTO SM_SEME
	VALUES (6815752,
	6815747,
	6815757,
	6815745);
INSERT INTO SM_EIGN
	VALUES (6815752,
	6815748,
	6815757,
	6815745,
	'');
INSERT INTO SM_SEME
	VALUES (6815752,
	6815748,
	6815757,
	6815745);
INSERT INTO SM_STATE
	VALUES (6815753,
	6815757,
	6815745,
	'Retracting After Release',
	9,
	0);
INSERT INTO SM_SEME
	VALUES (6815753,
	6815745,
	6815757,
	6815745);
INSERT INTO SM_EIGN
	VALUES (6815753,
	6815746,
	6815757,
	6815745,
	'');
INSERT INTO SM_SEME
	VALUES (6815753,
	6815746,
	6815757,
	6815745);
INSERT INTO SM_EIGN
	VALUES (6815753,
	6815747,
	6815757,
	6815745,
	'');
INSERT INTO SM_SEME
	VALUES (6815753,
	6815747,
	6815757,
	6815745);
INSERT INTO SM_EIGN
	VALUES (6815753,
	6815748,
	6815757,
	6815745,
	'');
INSERT INTO SM_SEME
	VALUES (6815753,
	6815748,
	6815757,
	6815745);
INSERT INTO SM_NSTXN
	VALUES (6815745,
	6815757,
	6815745,
	6815748,
	6815745);
INSERT INTO SM_TXN
	VALUES (6815745,
	6815757,
	6815746,
	6815745);
INSERT INTO SM_NSTXN
	VALUES (6815746,
	6815757,
	6815746,
	6815745,
	6815745);
INSERT INTO SM_TXN
	VALUES (6815746,
	6815757,
	6815747,
	6815745);
INSERT INTO SM_NSTXN
	VALUES (6815747,
	6815757,
	6815747,
	6815745,
	6815745);
INSERT INTO SM_TXN
	VALUES (6815747,
	6815757,
	6815748,
	6815745);
INSERT INTO SM_NSTXN
	VALUES (6815748,
	6815757,
	6815748,
	6815747,
	6815745);
INSERT INTO SM_TXN
	VALUES (6815748,
	6815757,
	6815749,
	6815745);
INSERT INTO SM_NSTXN
	VALUES (6815749,
	6815757,
	6815749,
	6815745,
	6815745);
INSERT INTO SM_TXN
	VALUES (6815749,
	6815757,
	6815750,
	6815745);
INSERT INTO SM_NSTXN
	VALUES (6815750,
	6815757,
	6815750,
	6815745,
	6815745);
INSERT INTO SM_TXN
	VALUES (6815750,
	6815757,
	6815751,
	6815745);
INSERT INTO SM_NSTXN
	VALUES (6815751,
	6815757,
	6815751,
	6815745,
	6815745);
INSERT INTO SM_TXN
	VALUES (6815751,
	6815757,
	6815752,
	6815745);
INSERT INTO SM_NSTXN
	VALUES (6815752,
	6815757,
	6815752,
	6815746,
	6815745);
INSERT INTO SM_TXN
	VALUES (6815752,
	6815757,
	6815753,
	6815745);
INSERT INTO SM_NSTXN
	VALUES (6815753,
	6815757,
	6815753,
	6815745,
	6815745);
INSERT INTO SM_TXN
	VALUES (6815753,
	6815757,
	6815745,
	6815745);
INSERT INTO SM_MOAH
	VALUES (6815745,
	6815757,
	6815745);
INSERT INTO SM_AH
	VALUES (6815745,
	6815757);
INSERT INTO SM_ACT
	VALUES (6815745,
	6815757,
	1,
	'Select one disk_tfr related by Self->D_DT[R9];
Select one dest_loc related by disk_tfr->D_OL[R11];
Generate D_OL2:''Robot Delivered Disk'' () to dest_loc;

Unrelate Self from disk_tfr across R9;

Select many waiting_disk_tfrs from instances of D_DT 
                    where (selected.Status == "Ready_for_Robot");
if (not_empty waiting_disk_tfrs)
      Generate D_R5:''Robot Request Pending'' to self;
End if;
',
	'');
INSERT INTO SM_MOAH
	VALUES (6815746,
	6815757,
	6815746);
INSERT INTO SM_AH
	VALUES (6815746,
	6815757);
INSERT INTO SM_ACT
	VALUES (6815746,
	6815757,
	1,
	'Select many disk_tfr_set from instances of D_DT;
Assign transfer_found = FALSE;
For each disk_tfr in disk_tfr_set
    If (transfer_found == FALSE)

    // Status = Ready for Robot
    If (disk_tfr.Status == "Ready_for_Robot")
        Assign transfer_found = TRUE;

        // Set Status to In Progress and Relate Self to Disk Transfer
        Assign disk_tfr.Status = "In_Progress";
        Relate Self to disk_tfr across R9;
 
        Select One source_loc Related by disk_tfr->D_OL[R10];
        Assign x = source_loc.X_Coordinate;
        Assign y = source_loc.Y_Coordinate;
        Assign theta = source_loc.Theta_Coordinate;

        // Load into Stepper Motor

        Assign Self.Actual_X_Coordinate = x;
        Assign Self.Actual_Y_Coordinate = y;
        Assign Self.Actual_Theta_Coordinate = theta;
        
        // Bridge to PIO: Load Stepper Motors
        Bridge pio1::load_stepper_motors(tvs:"D_PIOBR::robot_move_complete", 	robot_id:self.Robot_ID, x:x, y:y, theta:theta);
    End if;
    End if;
End for;',
	'');
INSERT INTO SM_MOAH
	VALUES (6815747,
	6815757,
	6815747);
INSERT INTO SM_AH
	VALUES (6815747,
	6815757);
INSERT INTO SM_ACT
	VALUES (6815747,
	6815757,
	1,
	'// Extend Hand
Bridge pio1::extend_hand(tvs:"D_PIOBR::robot_move_complete", robot_id:self.Robot_ID);',
	'');
INSERT INTO SM_MOAH
	VALUES (6815748,
	6815757,
	6815748);
INSERT INTO SM_AH
	VALUES (6815748,
	6815757);
INSERT INTO SM_ACT
	VALUES (6815748,
	6815757,
	1,
	'// Bridge to PIO: Close Gripper
Bridge pio1::close_gripper(tvs:"D_PIOBR::gripper_closed_on_disk", robot_id:self.Robot_ID);',
	'');
INSERT INTO SM_MOAH
	VALUES (6815749,
	6815757,
	6815749);
INSERT INTO SM_AH
	VALUES (6815749,
	6815757);
INSERT INTO SM_ACT
	VALUES (6815749,
	6815757,
	1,
	'// Retract Hand
Bridge pio1::retract_hand(tvs:"D_PIOBR::robot_move_complete", robot_id:self.Robot_ID);',
	'');
INSERT INTO SM_MOAH
	VALUES (6815750,
	6815757,
	6815750);
INSERT INTO SM_AH
	VALUES (6815750,
	6815757);
INSERT INTO SM_ACT
	VALUES (6815750,
	6815757,
	1,
	'Select one dest_loc related by Self->D_DT[R9]->D_OL[R11];
Assign x = dest_loc.X_Coordinate;
Assign y = dest_loc.Y_Coordinate;
Assign theta = dest_loc.Theta_Coordinate;

// Load Stepper Motor Registers
Bridge pio1::load_stepper_motors(tvs:"D_PIOBR::robot_move_complete", robot_id:self.Robot_ID, x:x, y:y, theta:theta);

Assign Self.Actual_X_Coordinate = x;
Assign Self.Actual_Y_Coordinate = y;
Assign Self.Actual_Theta_Coordinate = theta;

Select one source_loc related by Self->D_DT[R9]->D_OL[R10];
Generate D_OL1:''Robot Removed Disk'' () to source_loc;
',
	'');
INSERT INTO SM_MOAH
	VALUES (6815751,
	6815757,
	6815751);
INSERT INTO SM_AH
	VALUES (6815751,
	6815757);
INSERT INTO SM_ACT
	VALUES (6815751,
	6815757,
	1,
	'// Extend Hand
Bridge pio1::extend_hand(tvs:"D_PIOBR::robot_move_complete", robot_id:self.Robot_ID);
',
	'');
INSERT INTO SM_MOAH
	VALUES (6815752,
	6815757,
	6815752);
INSERT INTO SM_AH
	VALUES (6815752,
	6815757);
INSERT INTO SM_ACT
	VALUES (6815752,
	6815757,
	1,
	'// Open Gripper
Bridge pio1::open_gripper(tvs:"D_PIOBR::gripper_opened", robot_id:self.Robot_ID);',
	'');
INSERT INTO SM_MOAH
	VALUES (6815753,
	6815757,
	6815753);
INSERT INTO SM_AH
	VALUES (6815753,
	6815757);
INSERT INTO SM_ACT
	VALUES (6815753,
	6815757,
	1,
	'// Retract Hand

Select one disk_tfr related by Self->D_DT[R9];

// Set Status to Complete
Assign disk_tfr.Status = "Complete";

Bridge pio1::retract_hand(tvs:"D_PIOBR::robot_move_complete", robot_id:self.Robot_ID);
',
	'');
INSERT INTO GD_MD
	VALUES (6815745,
	8,
	6815757,
	40,
	1,
	0,
	1,
	1,
	0,
	12,
	1464,
	4137,
	0.750000,
	0);
INSERT INTO GD_GE
	VALUES (6815746,
	6815745,
	6815753,
	41);
INSERT INTO GD_SHP
	VALUES (6815746,
	2272,
	1440,
	2624,
	1616);
INSERT INTO GD_GE
	VALUES (6815747,
	6815745,
	6815752,
	41);
INSERT INTO GD_SHP
	VALUES (6815747,
	2272,
	1744,
	2576,
	1824);
INSERT INTO GD_GE
	VALUES (6815748,
	6815745,
	6815751,
	41);
INSERT INTO GD_SHP
	VALUES (6815748,
	2272,
	1936,
	2576,
	2016);
INSERT INTO GD_GE
	VALUES (6815749,
	6815745,
	6815750,
	41);
INSERT INTO GD_SHP
	VALUES (6815749,
	2272,
	2112,
	2768,
	2480);
INSERT INTO GD_GE
	VALUES (6815750,
	6815745,
	6815749,
	41);
INSERT INTO GD_SHP
	VALUES (6815750,
	1568,
	2320,
	1936,
	2416);
INSERT INTO GD_GE
	VALUES (6815751,
	6815745,
	6815748,
	41);
INSERT INTO GD_SHP
	VALUES (6815751,
	1568,
	2144,
	1936,
	2256);
INSERT INTO GD_GE
	VALUES (6815752,
	6815745,
	6815747,
	41);
INSERT INTO GD_SHP
	VALUES (6815752,
	1568,
	2000,
	1936,
	2080);
INSERT INTO GD_GE
	VALUES (6815753,
	6815745,
	6815746,
	41);
INSERT INTO GD_SHP
	VALUES (6815753,
	1568,
	1392,
	2080,
	1888);
INSERT INTO GD_GE
	VALUES (6815754,
	6815745,
	6815745,
	41);
INSERT INTO GD_SHP
	VALUES (6815754,
	1872,
	1072,
	2304,
	1360);
INSERT INTO GD_GE
	VALUES (6815755,
	6815745,
	6815753,
	42);
INSERT INTO GD_CON
	VALUES (6815755,
	6815746,
	6815754,
	0);
INSERT INTO GD_CTXT
	VALUES (6815755,
	0,
	0,
	0,
	0,
	0,
	0,
	2439,
	1261,
	2576,
	1301,
	150,
	-81,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (6815756,
	6815755,
	2432,
	1440,
	2432,
	1280,
	0);
INSERT INTO GD_LS
	VALUES (6815757,
	6815755,
	2432,
	1280,
	2304,
	1280,
	6815756);
INSERT INTO GD_GE
	VALUES (6815758,
	6815745,
	6815752,
	42);
INSERT INTO GD_CON
	VALUES (6815758,
	6815747,
	6815746,
	0);
INSERT INTO GD_CTXT
	VALUES (6815758,
	0,
	0,
	0,
	0,
	0,
	0,
	2446,
	1674,
	2552,
	1714,
	126,
	12,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (6815759,
	6815758,
	2432,
	1744,
	2432,
	1616,
	0);
INSERT INTO GD_GE
	VALUES (6815760,
	6815745,
	6815751,
	42);
INSERT INTO GD_CON
	VALUES (6815760,
	6815748,
	6815747,
	0);
INSERT INTO GD_CTXT
	VALUES (6815760,
	0,
	0,
	0,
	0,
	0,
	0,
	2440,
	1861,
	2554,
	1901,
	128,
	-1,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (6815761,
	6815760,
	2432,
	1936,
	2432,
	1824,
	0);
INSERT INTO GD_GE
	VALUES (6815762,
	6815745,
	6815750,
	42);
INSERT INTO GD_CON
	VALUES (6815762,
	6815749,
	6815748,
	0);
INSERT INTO GD_CTXT
	VALUES (6815762,
	0,
	0,
	0,
	0,
	0,
	0,
	2443,
	2055,
	2557,
	2095,
	131,
	9,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (6815763,
	6815762,
	2432,
	2112,
	2432,
	2016,
	0);
INSERT INTO GD_GE
	VALUES (6815764,
	6815745,
	6815749,
	42);
INSERT INTO GD_CON
	VALUES (6815764,
	6815750,
	6815749,
	0);
INSERT INTO GD_CTXT
	VALUES (6815764,
	0,
	0,
	0,
	0,
	0,
	0,
	1974,
	2325,
	2111,
	2365,
	-64,
	3,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (6815765,
	6815764,
	1936,
	2368,
	2272,
	2368,
	0);
INSERT INTO GD_GE
	VALUES (6815766,
	6815745,
	6815748,
	42);
INSERT INTO GD_CON
	VALUES (6815766,
	6815751,
	6815750,
	0);
INSERT INTO GD_CTXT
	VALUES (6815766,
	0,
	0,
	0,
	0,
	0,
	0,
	1578,
	2265,
	1704,
	2311,
	-118,
	-8,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (6815767,
	6815766,
	1712,
	2256,
	1712,
	2320,
	0);
INSERT INTO GD_GE
	VALUES (6815768,
	6815745,
	6815747,
	42);
INSERT INTO GD_CON
	VALUES (6815768,
	6815752,
	6815751,
	0);
INSERT INTO GD_CTXT
	VALUES (6815768,
	0,
	0,
	0,
	0,
	0,
	0,
	1576,
	2085,
	1690,
	2125,
	-16,
	-9,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (6815769,
	6815768,
	1712,
	2080,
	1712,
	2144,
	0);
INSERT INTO GD_GE
	VALUES (6815770,
	6815745,
	6815746,
	42);
INSERT INTO GD_CON
	VALUES (6815770,
	6815753,
	6815752,
	0);
INSERT INTO GD_CTXT
	VALUES (6815770,
	0,
	0,
	0,
	0,
	0,
	0,
	1744,
	1917,
	1881,
	1957,
	175,
	-9,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (6815771,
	6815770,
	1712,
	1888,
	1712,
	2000,
	0);
INSERT INTO GD_GE
	VALUES (6815772,
	6815745,
	6815745,
	42);
INSERT INTO GD_CON
	VALUES (6815772,
	6815754,
	6815753,
	0);
INSERT INTO GD_CTXT
	VALUES (6815772,
	0,
	0,
	0,
	0,
	0,
	0,
	1660,
	1184,
	1817,
	1224,
	-56,
	-50,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (6815773,
	6815772,
	1872,
	1280,
	1712,
	1280,
	0);
INSERT INTO GD_LS
	VALUES (6815774,
	6815772,
	1712,
	1280,
	1712,
	1392,
	6815773);
INSERT INTO R_SIMP
	VALUES (1048577);
INSERT INTO R_REL
	VALUES (1048577,
	4,
	'The disks have an assigned home in the library to which they will always retiun.
A disk may not migrate from one home to another home in the library.  This makes
locating disks easier for the operator.
',
	1048578);
INSERT INTO R_FORM
	VALUES (1048577,
	1048577,
	1048577,
	0,
	1,
	'is permanent home for');
INSERT INTO R_RGO
	VALUES (1048577,
	1048577,
	1048577);
INSERT INTO R_OIR
	VALUES (1048577,
	1048577,
	1048577,
	0);
INSERT INTO R_PART
	VALUES (1048578,
	1048577,
	1048578,
	0,
	0,
	'is permanently assigned to');
INSERT INTO R_RTO
	VALUES (1048578,
	1048577,
	1048578,
	0);
INSERT INTO R_OIR
	VALUES (1048578,
	1048577,
	1048578,
	0);
INSERT INTO R_SUBSUP
	VALUES (1048578);
INSERT INTO R_REL
	VALUES (1048578,
	3,
	'When a disk is in the cabinet or moving to the cabinet, it is considered to be
online.  When a disk is outside the cabinet or moving to outside the cabinet, it
is considered to be offline.
',
	1048578);
INSERT INTO R_SUPER
	VALUES (1048577,
	1048578,
	1048579);
INSERT INTO R_RTO
	VALUES (1048577,
	1048578,
	1048579,
	0);
INSERT INTO R_OIR
	VALUES (1048577,
	1048578,
	1048579,
	0);
INSERT INTO R_SUB
	VALUES (1048579,
	1048578,
	1048580);
INSERT INTO R_RGO
	VALUES (1048579,
	1048578,
	1048580);
INSERT INTO R_OIR
	VALUES (1048579,
	1048578,
	1048580,
	0);
INSERT INTO R_SUB
	VALUES (1048580,
	1048578,
	1048581);
INSERT INTO R_RGO
	VALUES (1048580,
	1048578,
	1048581);
INSERT INTO R_OIR
	VALUES (1048580,
	1048578,
	1048581,
	0);
INSERT INTO R_ASSOC
	VALUES (1048579);
INSERT INTO R_REL
	VALUES (1048579,
	1,
	'A qualified process at a given time may need to access information contained on
one or more disks or add information to one or more disks.  A given disk may be
unrequested or requested by several QPs at the same time.  Finally, a QP may
request the same disk at different times.
',
	1048578);
INSERT INTO R_AONE
	VALUES (1048582,
	1048579,
	1048582,
	1,
	1,
	'needed by');
INSERT INTO R_RTO
	VALUES (1048582,
	1048579,
	1048582,
	0);
INSERT INTO R_OIR
	VALUES (1048582,
	1048579,
	1048582,
	0);
INSERT INTO R_AOTH
	VALUES (1048577,
	1048579,
	1048583,
	1,
	1,
	'needs');
INSERT INTO R_RTO
	VALUES (1048577,
	1048579,
	1048583,
	0);
INSERT INTO R_OIR
	VALUES (1048577,
	1048579,
	1048583,
	0);
INSERT INTO R_ASSR
	VALUES (1048581,
	1048579,
	1048584,
	1);
INSERT INTO R_RGO
	VALUES (1048581,
	1048579,
	1048584);
INSERT INTO R_OIR
	VALUES (1048581,
	1048579,
	1048584,
	0);
INSERT INTO R_ASSOC
	VALUES (1048580);
INSERT INTO R_REL
	VALUES (1048580,
	2,
	'To ensure that only one QP may be accessing a disk at any time, a QP is granted
exclusive ownership of a disk prior to any data operation on the disk.  This
policy ensures data consistency.
',
	1048578);
INSERT INTO R_AONE
	VALUES (1048582,
	1048580,
	1048585,
	0,
	1,
	'is owned by');
INSERT INTO R_RTO
	VALUES (1048582,
	1048580,
	1048585,
	0);
INSERT INTO R_OIR
	VALUES (1048582,
	1048580,
	1048585,
	0);
INSERT INTO R_AOTH
	VALUES (1048577,
	1048580,
	1048586,
	1,
	1,
	'owns');
INSERT INTO R_RTO
	VALUES (1048577,
	1048580,
	1048586,
	0);
INSERT INTO R_OIR
	VALUES (1048577,
	1048580,
	1048586,
	0);
INSERT INTO R_ASSR
	VALUES (1048583,
	1048580,
	1048587,
	0);
INSERT INTO R_RGO
	VALUES (1048583,
	1048580,
	1048587);
INSERT INTO R_OIR
	VALUES (1048583,
	1048580,
	1048587,
	0);
INSERT INTO R_SUBSUP
	VALUES (1048581);
INSERT INTO R_REL
	VALUES (1048581,
	7,
	'The legal places for a disk in the cabinet are the slot, the port, and the
drive.  Online location captures the physical location in the cabinet of these
places.
',
	1048578);
INSERT INTO R_SUPER
	VALUES (1048586,
	1048581,
	1048588);
INSERT INTO R_RTO
	VALUES (1048586,
	1048581,
	1048588,
	0);
INSERT INTO R_OIR
	VALUES (1048586,
	1048581,
	1048588,
	0);
INSERT INTO R_SUB
	VALUES (1048585,
	1048581,
	1048589);
INSERT INTO R_RGO
	VALUES (1048585,
	1048581,
	1048589);
INSERT INTO R_OIR
	VALUES (1048585,
	1048581,
	1048589,
	0);
INSERT INTO R_SUB
	VALUES (1048588,
	1048581,
	1048590);
INSERT INTO R_RGO
	VALUES (1048588,
	1048581,
	1048590);
INSERT INTO R_OIR
	VALUES (1048588,
	1048581,
	1048590,
	0);
INSERT INTO R_SUB
	VALUES (1048589,
	1048581,
	1048591);
INSERT INTO R_RGO
	VALUES (1048589,
	1048581,
	1048591);
INSERT INTO R_OIR
	VALUES (1048589,
	1048581,
	1048591,
	0);
INSERT INTO R_ASSOC
	VALUES (1048582);
INSERT INTO R_REL
	VALUES (1048582,
	6,
	'For a disk to be brought into the cabinet, it must be assigned a slot.  The disk
will always return from a drive to the same slot it is assigned.
',
	1048578);
INSERT INTO R_AONE
	VALUES (1048585,
	1048582,
	1048592,
	0,
	0,
	'is assigned to');
INSERT INTO R_RTO
	VALUES (1048585,
	1048582,
	1048592,
	0);
INSERT INTO R_OIR
	VALUES (1048585,
	1048582,
	1048592,
	0);
INSERT INTO R_AOTH
	VALUES (1048579,
	1048582,
	1048593,
	0,
	1,
	'is assigned to');
INSERT INTO R_RTO
	VALUES (1048579,
	1048582,
	1048593,
	0);
INSERT INTO R_OIR
	VALUES (1048579,
	1048582,
	1048593,
	0);
INSERT INTO R_ASSR
	VALUES (1048587,
	1048582,
	1048594,
	0);
INSERT INTO R_RGO
	VALUES (1048587,
	1048582,
	1048594);
INSERT INTO R_OIR
	VALUES (1048587,
	1048582,
	1048594,
	0);
INSERT INTO R_ASSOC
	VALUES (1048583);
INSERT INTO R_REL
	VALUES (1048583,
	5,
	'An online disk is assigned to a drive so it can be accessed or an online disk
may not need to be accessed so it is not assigned a drive.  A drive may or may
not be assigned to an online disk.
',
	1048578);
INSERT INTO R_AONE
	VALUES (1048579,
	1048583,
	1048595,
	0,
	1,
	'is assigned to');
INSERT INTO R_RTO
	VALUES (1048579,
	1048583,
	1048595,
	0);
INSERT INTO R_OIR
	VALUES (1048579,
	1048583,
	1048595,
	0);
INSERT INTO R_AOTH
	VALUES (1048588,
	1048583,
	1048596,
	0,
	1,
	'is assigned to');
INSERT INTO R_RTO
	VALUES (1048588,
	1048583,
	1048596,
	0);
INSERT INTO R_OIR
	VALUES (1048588,
	1048583,
	1048596,
	0);
INSERT INTO R_ASSR
	VALUES (1048584,
	1048583,
	1048597,
	0);
INSERT INTO R_RGO
	VALUES (1048584,
	1048583,
	1048597);
INSERT INTO R_OIR
	VALUES (1048584,
	1048583,
	1048597,
	0);
INSERT INTO R_SIMP
	VALUES (1048584);
INSERT INTO R_REL
	VALUES (1048584,
	8,
	'The entiy/exit port is responsible for handling the process of inserting new
disks into the cabinet and the process of ejecting disks from the cabinet.
',
	1048578);
INSERT INTO R_FORM
	VALUES (1048589,
	1048584,
	1048598,
	0,
	1,
	'is being executed by');
INSERT INTO R_RGO
	VALUES (1048589,
	1048584,
	1048598);
INSERT INTO R_OIR
	VALUES (1048589,
	1048584,
	1048598,
	0);
INSERT INTO R_PART
	VALUES (1048590,
	1048584,
	1048599,
	0,
	1,
	'is executing');
INSERT INTO R_RTO
	VALUES (1048590,
	1048584,
	1048599,
	0);
INSERT INTO R_OIR
	VALUES (1048590,
	1048584,
	1048599,
	0);
INSERT INTO R_SIMP
	VALUES (1048585);
INSERT INTO R_REL
	VALUES (1048585,
	9,
	'The robot performs all movement of disks within the cabinet.  The location
requiring robot access is responsible for ensuring it is ready for the robot.
',
	1048578);
INSERT INTO R_FORM
	VALUES (1048591,
	1048585,
	1048600,
	0,
	1,
	'is being executed by');
INSERT INTO R_RGO
	VALUES (1048591,
	1048585,
	1048600);
INSERT INTO R_OIR
	VALUES (1048591,
	1048585,
	1048600,
	0);
INSERT INTO R_PART
	VALUES (1048590,
	1048585,
	1048601,
	0,
	1,
	'is executing');
INSERT INTO R_RTO
	VALUES (1048590,
	1048585,
	1048601,
	0);
INSERT INTO R_OIR
	VALUES (1048590,
	1048585,
	1048601,
	0);
INSERT INTO R_SIMP
	VALUES (1048586);
INSERT INTO R_REL
	VALUES (1048586,
	10,
	'Disks need to move between different online locations in the cabinet to get to
drives and be entered in the cabinet.  Transfers in the cabinet are made between
port and slot and slot and drive.  No transfers can occur between instances of
the same type of online location or between instances of drive and poll.  This
is a policy to simplify the dynamics.
',
	1048578);
INSERT INTO R_FORM
	VALUES (1048590,
	1048586,
	1048602,
	0,
	1,
	'is source for');
INSERT INTO R_RGO
	VALUES (1048590,
	1048586,
	1048602);
INSERT INTO R_OIR
	VALUES (1048590,
	1048586,
	1048602,
	0);
INSERT INTO R_PART
	VALUES (1048586,
	1048586,
	1048603,
	0,
	0,
	'originates at');
INSERT INTO R_RTO
	VALUES (1048586,
	1048586,
	1048603,
	0);
INSERT INTO R_OIR
	VALUES (1048586,
	1048586,
	1048603,
	0);
INSERT INTO R_SIMP
	VALUES (1048587);
INSERT INTO R_REL
	VALUES (1048587,
	11,
	'Disks need to move between different online locations in the cabinet to get to
drives and be entered in the cabinet.  Transfers in the cabinet are made between
port and slot and slot and drive.  No transfers can occur between instances of
the same type of online location or between instances of drive and port.  This
is a policy to simplify the dynamics.
',
	1048578);
INSERT INTO R_FORM
	VALUES (1048590,
	1048587,
	1048604,
	0,
	1,
	'is destination for');
INSERT INTO R_RGO
	VALUES (1048590,
	1048587,
	1048604);
INSERT INTO R_OIR
	VALUES (1048590,
	1048587,
	1048604,
	0);
INSERT INTO R_PART
	VALUES (1048586,
	1048587,
	1048605,
	0,
	0,
	'completes at');
INSERT INTO R_RTO
	VALUES (1048586,
	1048587,
	1048605,
	0);
INSERT INTO R_OIR
	VALUES (1048586,
	1048587,
	1048605,
	0);
INSERT INTO CA_COMM
	VALUES (1572865,
	1048578);
INSERT INTO CA_SMSMC
	VALUES (1572865,
	1572867,
	3145734,
	0);
INSERT INTO CA_SMSME
	VALUES (1572865,
	3145734,
	3145733);
INSERT INTO CA_SMSME
	VALUES (1572865,
	3145734,
	3145736);
INSERT INTO CA_SMSME
	VALUES (1572865,
	3145734,
	3145735);
INSERT INTO CA_COMM
	VALUES (1572866,
	1048578);
INSERT INTO CA_SMSMC
	VALUES (1572866,
	1572867,
	5242890,
	0);
INSERT INTO CA_SMSME
	VALUES (1572866,
	5242890,
	5242881);
INSERT INTO CA_COMM
	VALUES (1572867,
	1048578);
INSERT INTO CA_SMSMC
	VALUES (1572867,
	1572867,
	3670023,
	0);
INSERT INTO CA_SMSME
	VALUES (1572867,
	3670023,
	3670017);
INSERT INTO CA_COMM
	VALUES (1572868,
	1048578);
INSERT INTO CA_SMSMC
	VALUES (1572868,
	1572867,
	5767179,
	0);
INSERT INTO CA_SMSME
	VALUES (1572868,
	5767179,
	5767171);
INSERT INTO CA_COMM
	VALUES (1572869,
	1048578);
INSERT INTO CA_SMSMC
	VALUES (1572869,
	1572867,
	4194312,
	0);
INSERT INTO CA_SMSME
	VALUES (1572869,
	4194312,
	4194306);
INSERT INTO CA_COMM
	VALUES (2097153,
	1048578);
INSERT INTO CA_SMSMC
	VALUES (2097153,
	2097156,
	3145734,
	0);
INSERT INTO CA_SMSME
	VALUES (2097153,
	3145734,
	3145729);
INSERT INTO CA_SMSME
	VALUES (2097153,
	3145734,
	3145734);
INSERT INTO CA_COMM
	VALUES (2621441,
	1048578);
INSERT INTO CA_SMSMC
	VALUES (2621441,
	2621445,
	2097156,
	0);
INSERT INTO CA_SMSME
	VALUES (2621441,
	2097156,
	2097153);
INSERT INTO CA_COMM
	VALUES (3145729,
	1048578);
INSERT INTO CA_SMSMC
	VALUES (3145729,
	3145734,
	3145734,
	0);
INSERT INTO CA_SMSME
	VALUES (3145729,
	3145734,
	3145730);
INSERT INTO CA_SMSME
	VALUES (3145729,
	3145734,
	3145732);
INSERT INTO CA_SMSME
	VALUES (3145729,
	3145734,
	3145729);
INSERT INTO CA_SMSME
	VALUES (3145729,
	3145734,
	3145731);
INSERT INTO CA_COMM
	VALUES (3145730,
	1048578);
INSERT INTO CA_SMSMC
	VALUES (3145730,
	3145734,
	1572867,
	0);
INSERT INTO CA_SMSME
	VALUES (3145730,
	1572867,
	1572865);
INSERT INTO CA_SMSME
	VALUES (3145730,
	1572867,
	1572870);
INSERT INTO CA_COMM
	VALUES (3145731,
	1048578);
INSERT INTO CA_SMSMC
	VALUES (3145731,
	3145734,
	2097156,
	0);
INSERT INTO CA_SMSME
	VALUES (3145731,
	2097156,
	2097154);
INSERT INTO CA_COMM
	VALUES (3670017,
	1048578);
INSERT INTO CA_SMSMC
	VALUES (3670017,
	3670023,
	3670023,
	0);
INSERT INTO CA_SMSME
	VALUES (3670017,
	3670023,
	3670017);
INSERT INTO CA_SMSME
	VALUES (3670017,
	3670023,
	3670018);
INSERT INTO CA_SMSME
	VALUES (3670017,
	3670023,
	3670019);
INSERT INTO CA_COMM
	VALUES (3670018,
	1048578);
INSERT INTO CA_SMSMC
	VALUES (3670018,
	3670023,
	5767179,
	0);
INSERT INTO CA_SMSME
	VALUES (3670018,
	5767179,
	5767169);
INSERT INTO CA_COMM
	VALUES (4194305,
	1048578);
INSERT INTO CA_SMSMC
	VALUES (4194305,
	4194312,
	1572867,
	0);
INSERT INTO CA_SMSME
	VALUES (4194305,
	1572867,
	1572866);
INSERT INTO CA_SMSME
	VALUES (4194305,
	1572867,
	1572871);
INSERT INTO CA_SMSME
	VALUES (4194305,
	1572867,
	1572869);
INSERT INTO CA_COMM
	VALUES (4194306,
	1048578);
INSERT INTO CA_SMSMC
	VALUES (4194306,
	4194312,
	5242890,
	0);
INSERT INTO CA_SMSME
	VALUES (4194306,
	5242890,
	5242882);
INSERT INTO CA_COMM
	VALUES (4194307,
	1048578);
INSERT INTO CA_SMSMC
	VALUES (4194307,
	4194312,
	6291468,
	0);
INSERT INTO CA_SMSME
	VALUES (4194307,
	6291468,
	6291465);
INSERT INTO CA_COMM
	VALUES (4718593,
	1048578);
INSERT INTO CA_SMSMC
	VALUES (4718593,
	4718601,
	4194312,
	0);
INSERT INTO CA_SMSME
	VALUES (4718593,
	4194312,
	4194308);
INSERT INTO CA_SMSME
	VALUES (4718593,
	4194312,
	4194307);
INSERT INTO CA_COMM
	VALUES (4718594,
	1048578);
INSERT INTO CA_SMSMC
	VALUES (4718594,
	4718601,
	5767179,
	0);
INSERT INTO CA_SMSME
	VALUES (4718594,
	5767179,
	5767174);
INSERT INTO CA_SMSME
	VALUES (4718594,
	5767179,
	5767173);
INSERT INTO CA_COMM
	VALUES (4718595,
	1048578);
INSERT INTO CA_SMSMC
	VALUES (4718595,
	4718601,
	6291468,
	0);
INSERT INTO CA_SMSME
	VALUES (4718595,
	6291468,
	6291466);
INSERT INTO CA_SMSME
	VALUES (4718595,
	6291468,
	6291467);
INSERT INTO CA_COMM
	VALUES (5242881,
	1048578);
INSERT INTO CA_SMSMC
	VALUES (5242881,
	5242890,
	5242890,
	0);
INSERT INTO CA_SMSME
	VALUES (5242881,
	5242890,
	5242883);
INSERT INTO CA_SMSME
	VALUES (5242881,
	5242890,
	5242882);
INSERT INTO CA_SMSME
	VALUES (5242881,
	5242890,
	5242881);
INSERT INTO CA_COMM
	VALUES (5242882,
	1048578);
INSERT INTO CA_SMSMC
	VALUES (5242882,
	5242890,
	4194312,
	0);
INSERT INTO CA_SMSME
	VALUES (5242882,
	4194312,
	4194305);
INSERT INTO CA_COMM
	VALUES (5767169,
	1048578);
INSERT INTO CA_SMSMC
	VALUES (5767169,
	5767179,
	3670023,
	0);
INSERT INTO CA_SMSME
	VALUES (5767169,
	3670023,
	3670018);
INSERT INTO CA_COMM
	VALUES (5767170,
	1048578);
INSERT INTO CA_SMSMC
	VALUES (5767170,
	5767179,
	6815757,
	0);
INSERT INTO CA_SMSME
	VALUES (5767170,
	6815757,
	6815748);
INSERT INTO CA_COMM
	VALUES (5767171,
	1048578);
INSERT INTO CA_SMSMC
	VALUES (5767171,
	5767179,
	1572867,
	0);
INSERT INTO CA_SMSME
	VALUES (5767171,
	1572867,
	1572867);
INSERT INTO CA_COMM
	VALUES (6291457,
	1048578);
INSERT INTO CA_SMSMC
	VALUES (6291457,
	6291468,
	6815757,
	0);
INSERT INTO CA_SMSME
	VALUES (6291457,
	6815757,
	6815748);
INSERT INTO CA_COMM
	VALUES (6815745,
	1048578);
INSERT INTO CA_SMSMC
	VALUES (6815745,
	6815757,
	4718601,
	0);
INSERT INTO CA_SMSME
	VALUES (6815745,
	4718601,
	4718594);
INSERT INTO CA_SMSME
	VALUES (6815745,
	4718601,
	4718593);
INSERT INTO CA_ACC
	VALUES (1572865,
	1048578,
	1572867,
	0);
INSERT INTO CA_SMOA
	VALUES (1572865,
	1048577,
	0);
INSERT INTO CA_SMOAA
	VALUES (1572865,
	1048587,
	1048577);
INSERT INTO CA_SMOAA
	VALUES (1572865,
	1048585,
	1048577);
INSERT INTO CA_SMOAA
	VALUES (1572865,
	1048577,
	1048577);
INSERT INTO CA_SMOAA
	VALUES (1572865,
	1048578,
	1048577);
INSERT INTO CA_SMOAA
	VALUES (1572865,
	1048586,
	1048577);
INSERT INTO CA_SMOAA
	VALUES (1572865,
	1048584,
	1048577);
INSERT INTO CA_ACC
	VALUES (1572866,
	1048578,
	1572867,
	0);
INSERT INTO CA_SMOA
	VALUES (1572866,
	1048582,
	0);
INSERT INTO CA_SMOAA
	VALUES (1572866,
	1048599,
	1048582);
INSERT INTO CA_ACC
	VALUES (1572867,
	1048578,
	1572867,
	0);
INSERT INTO CA_SMOA
	VALUES (1572867,
	1048588,
	0);
INSERT INTO CA_SMOAA
	VALUES (1572867,
	1048615,
	1048588);
INSERT INTO CA_ACC
	VALUES (2097153,
	1048578,
	2097156,
	0);
INSERT INTO CA_SMOA
	VALUES (2097153,
	1048581,
	0);
INSERT INTO CA_SMOAA
	VALUES (2097153,
	1048597,
	1048581);
INSERT INTO CA_ACC
	VALUES (2621441,
	1048578,
	2621445,
	0);
INSERT INTO CA_SMOA
	VALUES (2621441,
	1048577,
	0);
INSERT INTO CA_SMOAA
	VALUES (2621441,
	1048577,
	1048577);
INSERT INTO CA_ACC
	VALUES (3145729,
	1048578,
	3145734,
	0);
INSERT INTO CA_SMOA
	VALUES (3145729,
	1048581,
	0);
INSERT INTO CA_SMOAA
	VALUES (3145729,
	1048597,
	1048581);
INSERT INTO CA_ACC
	VALUES (3145730,
	1048578,
	3145734,
	0);
INSERT INTO CA_SMOA
	VALUES (3145730,
	1048577,
	0);
INSERT INTO CA_SMOAA
	VALUES (3145730,
	1048578,
	1048577);
INSERT INTO CA_SMOAA
	VALUES (3145730,
	1048587,
	1048577);
INSERT INTO CA_SMOAA
	VALUES (3145730,
	1048586,
	1048577);
INSERT INTO CA_ACC
	VALUES (3670017,
	1048578,
	3670023,
	0);
INSERT INTO CA_SMOA
	VALUES (3670017,
	1048577,
	0);
INSERT INTO CA_SMOAA
	VALUES (3670017,
	1048585,
	1048577);
INSERT INTO CA_ACC
	VALUES (3670018,
	1048578,
	3670023,
	0);
INSERT INTO CA_SMOA
	VALUES (3670018,
	1048588,
	0);
INSERT INTO CA_SMOAA
	VALUES (3670018,
	1048616,
	1048588);
INSERT INTO CA_ACC
	VALUES (4194305,
	1048578,
	4194312,
	0);
INSERT INTO CA_SMOA
	VALUES (4194305,
	1048585,
	0);
INSERT INTO CA_SMOAA
	VALUES (4194305,
	1048606,
	1048585);
INSERT INTO CA_ACC
	VALUES (4194306,
	1048578,
	4194312,
	0);
INSERT INTO CA_SMOA
	VALUES (4194306,
	1048577,
	0);
INSERT INTO CA_SMOAA
	VALUES (4194306,
	1048587,
	1048577);
INSERT INTO CA_SMOAA
	VALUES (4194306,
	1048586,
	1048577);
INSERT INTO CA_ACC
	VALUES (4194307,
	1048578,
	4194312,
	0);
INSERT INTO CA_SMOA
	VALUES (4194307,
	1048590,
	0);
INSERT INTO CA_SMOAA
	VALUES (4194307,
	1048624,
	1048590);
INSERT INTO CA_ACC
	VALUES (5242881,
	1048578,
	5242890,
	0);
INSERT INTO CA_SMOA
	VALUES (5242881,
	1048577,
	0);
INSERT INTO CA_SMOAA
	VALUES (5242881,
	1048584,
	1048577);
INSERT INTO CA_ACC
	VALUES (5242882,
	1048578,
	5242890,
	0);
INSERT INTO CA_SMOA
	VALUES (5242882,
	1048585,
	0);
INSERT INTO CA_SMOAA
	VALUES (5242882,
	1048606,
	1048585);
INSERT INTO CA_ACC
	VALUES (5767169,
	1048578,
	5767179,
	0);
INSERT INTO CA_SMOA
	VALUES (5767169,
	1048588,
	0);
INSERT INTO CA_SMOAA
	VALUES (5767169,
	1048616,
	1048588);
INSERT INTO CA_SMOAA
	VALUES (5767169,
	1048615,
	1048588);
INSERT INTO CA_ACC
	VALUES (5767170,
	1048578,
	5767179,
	0);
INSERT INTO CA_SMOA
	VALUES (5767170,
	1048590,
	0);
INSERT INTO CA_SMOAA
	VALUES (5767170,
	1048624,
	1048590);
INSERT INTO CA_ACC
	VALUES (6291457,
	1048578,
	6291468,
	0);
INSERT INTO CA_SMOA
	VALUES (6291457,
	1048590,
	0);
INSERT INTO CA_SMOAA
	VALUES (6291457,
	1048624,
	1048590);
INSERT INTO CA_SMOAA
	VALUES (6291457,
	1048622,
	1048590);
INSERT INTO CA_ACC
	VALUES (6291458,
	1048578,
	6291468,
	0);
INSERT INTO CA_SMOA
	VALUES (6291458,
	1048589,
	0);
INSERT INTO CA_SMOAA
	VALUES (6291458,
	1048618,
	1048589);
INSERT INTO CA_ACC
	VALUES (6291459,
	1048578,
	6291468,
	0);
INSERT INTO CA_SMOA
	VALUES (6291459,
	1048577,
	0);
INSERT INTO CA_SMOAA
	VALUES (6291459,
	1048577,
	1048577);
INSERT INTO CA_ACC
	VALUES (6815745,
	1048578,
	6815757,
	0);
INSERT INTO CA_SMOA
	VALUES (6815745,
	1048590,
	0);
INSERT INTO CA_SMOAA
	VALUES (6815745,
	1048624,
	1048590);
INSERT INTO CA_ACC
	VALUES (6815746,
	1048578,
	6815757,
	0);
INSERT INTO CA_SMOA
	VALUES (6815746,
	1048586,
	0);
INSERT INTO CA_SMOAA
	VALUES (6815746,
	1048609,
	1048586);
INSERT INTO CA_SMOAA
	VALUES (6815746,
	1048611,
	1048586);
INSERT INTO CA_SMOAA
	VALUES (6815746,
	1048610,
	1048586);
INSERT INTO CA_ACC
	VALUES (6815747,
	1048578,
	6815757,
	0);
INSERT INTO CA_SMOA
	VALUES (6815747,
	1048591,
	0);
INSERT INTO CA_SMOAA
	VALUES (6815747,
	1048628,
	1048591);
INSERT INTO CA_SMOAA
	VALUES (6815747,
	1048625,
	1048591);
INSERT INTO CA_SMOAA
	VALUES (6815747,
	1048627,
	1048591);
INSERT INTO CA_SMOAA
	VALUES (6815747,
	1048629,
	1048591);
INSERT INTO GD_MD
	VALUES (1048577,
	5,
	1048578,
	11,
	1,
	0,
	1,
	1,
	0,
	12,
	1308,
	3861,
	0.664328,
	0);
INSERT INTO GD_GE
	VALUES (1048580,
	1048577,
	1048591,
	21);
INSERT INTO GD_SHP
	VALUES (1048580,
	2432,
	1920,
	2656,
	2080);
INSERT INTO GD_GE
	VALUES (1048581,
	1048577,
	1048590,
	21);
INSERT INTO GD_SHP
	VALUES (1048581,
	2384,
	2224,
	2592,
	2336);
INSERT INTO GD_GE
	VALUES (1048582,
	1048577,
	1048589,
	21);
INSERT INTO GD_SHP
	VALUES (1048582,
	1424,
	1952,
	1600,
	2064);
INSERT INTO GD_GE
	VALUES (1048583,
	1048577,
	1048588,
	21);
INSERT INTO GD_SHP
	VALUES (1048583,
	1680,
	1968,
	1824,
	2064);
INSERT INTO GD_GE
	VALUES (1048584,
	1048577,
	1048587,
	21);
INSERT INTO GD_SHP
	VALUES (1048584,
	1824,
	1824,
	2048,
	1920);
INSERT INTO GD_GE
	VALUES (1048585,
	1048577,
	1048586,
	21);
INSERT INTO GD_SHP
	VALUES (1048585,
	1936,
	2208,
	2160,
	2352);
INSERT INTO GD_GE
	VALUES (1048586,
	1048577,
	1048585,
	21);
INSERT INTO GD_SHP
	VALUES (1048586,
	1968,
	1968,
	2128,
	2064);
INSERT INTO GD_GE
	VALUES (1048587,
	1048577,
	1048584,
	21);
INSERT INTO GD_SHP
	VALUES (1048587,
	1408,
	1744,
	1648,
	1840);
INSERT INTO GD_GE
	VALUES (1048588,
	1048577,
	1048583,
	21);
INSERT INTO GD_SHP
	VALUES (1048588,
	1648,
	1584,
	1856,
	1664);
INSERT INTO GD_GE
	VALUES (1048589,
	1048577,
	1048582,
	21);
INSERT INTO GD_SHP
	VALUES (1048589,
	1328,
	1440,
	1552,
	1552);
INSERT INTO GD_GE
	VALUES (1048590,
	1048577,
	1048581,
	21);
INSERT INTO GD_SHP
	VALUES (1048590,
	1632,
	1216,
	1856,
	1360);
INSERT INTO GD_GE
	VALUES (1048591,
	1048577,
	1048580,
	21);
INSERT INTO GD_SHP
	VALUES (1048591,
	2288,
	1680,
	2480,
	1760);
INSERT INTO GD_GE
	VALUES (1048592,
	1048577,
	1048579,
	21);
INSERT INTO GD_SHP
	VALUES (1048592,
	1952,
	1680,
	2160,
	1760);
INSERT INTO GD_GE
	VALUES (1048593,
	1048577,
	1048578,
	21);
INSERT INTO GD_SHP
	VALUES (1048593,
	2432,
	1440,
	2672,
	1568);
INSERT INTO GD_GE
	VALUES (1048594,
	1048577,
	1048577,
	21);
INSERT INTO GD_SHP
	VALUES (1048594,
	1936,
	1376,
	2160,
	1600);
INSERT INTO GD_GE
	VALUES (1048730,
	1048577,
	1048587,
	24);
INSERT INTO GD_CON
	VALUES (1048730,
	1048585,
	1048581,
	0);
INSERT INTO GD_CTXT
	VALUES (1048730,
	2169,
	2336,
	2262,
	2358,
	0,
	0,
	2257,
	2290,
	2290,
	2312,
	0,
	0,
	2289,
	2328,
	2376,
	2377,
	-25,
	33);
INSERT INTO GD_LS
	VALUES (1048731,
	1048730,
	2160,
	2320,
	2384,
	2320,
	0);
INSERT INTO GD_GE
	VALUES (1048732,
	1048577,
	1048586,
	24);
INSERT INTO GD_CON
	VALUES (1048732,
	1048585,
	1048581,
	0);
INSERT INTO GD_CTXT
	VALUES (1048732,
	2167,
	2187,
	2257,
	2209,
	0,
	0,
	2284,
	2212,
	2334,
	2242,
	37,
	-3,
	2279,
	2247,
	2368,
	2269,
	0,
	0);
INSERT INTO GD_LS
	VALUES (1048733,
	1048732,
	2160,
	2240,
	2384,
	2240,
	0);
INSERT INTO GD_GE
	VALUES (1048734,
	1048577,
	1048585,
	24);
INSERT INTO GD_CON
	VALUES (1048734,
	1048581,
	1048580,
	0);
INSERT INTO GD_CTXT
	VALUES (1048734,
	2577,
	2195,
	2663,
	2217,
	0,
	0,
	2566,
	2137,
	2616,
	2167,
	22,
	0,
	2578,
	2083,
	2665,
	2123,
	0,
	0);
INSERT INTO GD_LS
	VALUES (1048735,
	1048734,
	2560,
	2224,
	2560,
	2080,
	0);
INSERT INTO GD_GE
	VALUES (1048736,
	1048577,
	1048584,
	24);
INSERT INTO GD_CON
	VALUES (1048736,
	1048581,
	1048582,
	0);
INSERT INTO GD_CTXT
	VALUES (1048736,
	2306,
	2109,
	2406,
	2139,
	-69,
	-80,
	1864,
	2149,
	1914,
	2179,
	-47,
	30,
	1378,
	2091,
	1451,
	2140,
	-37,
	22);
INSERT INTO GD_LS
	VALUES (1048737,
	1048736,
	2416,
	2224,
	2416,
	2144,
	0);
INSERT INTO GD_LS
	VALUES (1048738,
	1048736,
	2416,
	2144,
	1456,
	2144,
	1048737);
INSERT INTO GD_LS
	VALUES (1048739,
	1048736,
	1456,
	2144,
	1456,
	2064,
	1048738);
INSERT INTO GD_GE
	VALUES (1048740,
	1048577,
	1048583,
	24);
INSERT INTO GD_CON
	VALUES (1048740,
	1048592,
	1048583,
	1048742);
INSERT INTO GD_CTXT
	VALUES (1048740,
	1847,
	1734,
	1947,
	1764,
	-35,
	31,
	1780,
	1768,
	1830,
	1798,
	15,
	31,
	1626,
	1914,
	1714,
	1954,
	0,
	0);
INSERT INTO GD_LS
	VALUES (1048741,
	1048740,
	1952,
	1728,
	1744,
	1728,
	0);
INSERT INTO GD_LS
	VALUES (1048742,
	1048740,
	1744,
	1728,
	1744,
	1968,
	1048741);
INSERT INTO GD_GE
	VALUES (1048743,
	1048577,
	1048582,
	24);
INSERT INTO GD_CON
	VALUES (1048743,
	1048586,
	1048592,
	1048744);
INSERT INTO GD_CTXT
	VALUES (1048743,
	2124,
	1911,
	2212,
	1951,
	0,
	0,
	2119,
	1858,
	2169,
	1888,
	23,
	1,
	2133,
	1775,
	2221,
	1815,
	0,
	0);
INSERT INTO GD_LS
	VALUES (1048744,
	1048743,
	2112,
	1968,
	2112,
	1760,
	0);
INSERT INTO GD_GE
	VALUES (1048745,
	1048577,
	1048581,
	24);
INSERT INTO GD_CON
	VALUES (1048745,
	1048585,
	0,
	0);
INSERT INTO GD_CTXT
	VALUES (1048745,
	1930,
	2158,
	2003,
	2198,
	0,
	0,
	0,
	0,
	0,
	0,
	0,
	0,
	2080,
	2158,
	2104,
	2180,
	0,
	0);
INSERT INTO GD_LS
	VALUES (1048746,
	1048745,
	2048,
	2208,
	2048,
	2112,
	0);
INSERT INTO GD_GE
	VALUES (1048747,
	1048577,
	1048580,
	24);
INSERT INTO GD_CON
	VALUES (1048747,
	1048589,
	1048594,
	1048748);
INSERT INTO GD_CTXT
	VALUES (1048747,
	1570,
	1518,
	1656,
	1540,
	0,
	0,
	1753,
	1510,
	1803,
	1540,
	34,
	31,
	1838,
	1510,
	1938,
	1540,
	-28,
	31);
INSERT INTO GD_LS
	VALUES (1048748,
	1048747,
	1552,
	1504,
	1936,
	1504,
	0);
INSERT INTO GD_GE
	VALUES (1048749,
	1048577,
	1048579,
	24);
INSERT INTO GD_CON
	VALUES (1048749,
	1048589,
	1048594,
	1048750);
INSERT INTO GD_CTXT
	VALUES (1048749,
	1578,
	1414,
	1655,
	1436,
	0,
	0,
	1711,
	1460,
	1761,
	1490,
	-8,
	29,
	1834,
	1463,
	1934,
	1493,
	-32,
	32);
INSERT INTO GD_LS
	VALUES (1048750,
	1048749,
	1552,
	1456,
	1936,
	1456,
	0);
INSERT INTO GD_GE
	VALUES (1048751,
	1048577,
	1048578,
	24);
INSERT INTO GD_CON
	VALUES (1048751,
	1048594,
	0,
	0);
INSERT INTO GD_CTXT
	VALUES (1048751,
	1943,
	1603,
	2016,
	1643,
	0,
	0,
	0,
	0,
	0,
	0,
	0,
	0,
	2072,
	1610,
	2096,
	1632,
	0,
	0);
INSERT INTO GD_LS
	VALUES (1048752,
	1048751,
	2048,
	1600,
	2048,
	1648,
	0);
INSERT INTO GD_GE
	VALUES (1048753,
	1048577,
	1048577,
	24);
INSERT INTO GD_CON
	VALUES (1048753,
	1048594,
	1048593,
	0);
INSERT INTO GD_CTXT
	VALUES (1048753,
	2163,
	1447,
	2258,
	1494,
	33,
	-32,
	2272,
	1472,
	2322,
	1502,
	1,
	-7,
	2315,
	1517,
	2423,
	1561,
	-47,
	38);
INSERT INTO GD_LS
	VALUES (1048754,
	1048753,
	2160,
	1504,
	2432,
	1504,
	0);
INSERT INTO GD_GE
	VALUES (1048755,
	1048577,
	0,
	-1);
INSERT INTO GD_CON
	VALUES (1048755,
	1048582,
	1048745,
	0);
INSERT INTO GD_CTXT
	VALUES (1048755,
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
	0,
	0,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (1048756,
	1048755,
	1552,
	2064,
	1552,
	2112,
	0);
INSERT INTO GD_LS
	VALUES (1048757,
	1048755,
	1552,
	2112,
	2048,
	2112,
	1048756);
INSERT INTO GD_GE
	VALUES (1048758,
	1048577,
	0,
	-1);
INSERT INTO GD_CON
	VALUES (1048758,
	1048587,
	1048740,
	0);
INSERT INTO GD_CTXT
	VALUES (1048758,
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
	0,
	0,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (1048759,
	1048758,
	1648,
	1792,
	1744,
	1792,
	0);
INSERT INTO GD_GE
	VALUES (1048760,
	1048577,
	0,
	-1);
INSERT INTO GD_CON
	VALUES (1048760,
	1048583,
	1048745,
	0);
INSERT INTO GD_CTXT
	VALUES (1048760,
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
	0,
	0,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (1048761,
	1048760,
	1744,
	2064,
	1744,
	2080,
	0);
INSERT INTO GD_LS
	VALUES (1048762,
	1048760,
	1744,
	2080,
	2048,
	2112,
	1048761);
INSERT INTO GD_GE
	VALUES (1048763,
	1048577,
	0,
	-1);
INSERT INTO GD_CON
	VALUES (1048763,
	1048584,
	1048743,
	0);
INSERT INTO GD_CTXT
	VALUES (1048763,
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
	0,
	0,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (1048764,
	1048763,
	2048,
	1872,
	2112,
	1872,
	0);
INSERT INTO GD_GE
	VALUES (1048765,
	1048577,
	0,
	-1);
INSERT INTO GD_CON
	VALUES (1048765,
	1048586,
	1048745,
	0);
INSERT INTO GD_CTXT
	VALUES (1048765,
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
	0,
	0,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (1048766,
	1048765,
	2048,
	2064,
	2048,
	2112,
	0);
INSERT INTO GD_GE
	VALUES (1048767,
	1048577,
	0,
	-1);
INSERT INTO GD_CON
	VALUES (1048767,
	1048588,
	1048747,
	0);
INSERT INTO GD_CTXT
	VALUES (1048767,
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
	0,
	0,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (1048768,
	1048767,
	1744,
	1584,
	1744,
	1504,
	0);
INSERT INTO GD_GE
	VALUES (1048769,
	1048577,
	0,
	-1);
INSERT INTO GD_CON
	VALUES (1048769,
	1048590,
	1048749,
	0);
INSERT INTO GD_CTXT
	VALUES (1048769,
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
	0,
	0,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (1048770,
	1048769,
	1744,
	1360,
	1744,
	1456,
	0);
INSERT INTO GD_GE
	VALUES (1048771,
	1048577,
	0,
	-1);
INSERT INTO GD_CON
	VALUES (1048771,
	1048591,
	1048751,
	0);
INSERT INTO GD_CTXT
	VALUES (1048771,
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
	0,
	0,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (1048772,
	1048771,
	2384,
	1680,
	2384,
	1648,
	0);
INSERT INTO GD_LS
	VALUES (1048773,
	1048771,
	2384,
	1648,
	2048,
	1648,
	1048772);
INSERT INTO GD_GE
	VALUES (1048774,
	1048577,
	0,
	-1);
INSERT INTO GD_CON
	VALUES (1048774,
	1048592,
	1048751,
	0);
INSERT INTO GD_CTXT
	VALUES (1048774,
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
	0,
	0,
	0,
	0,
	0,
	0,
	0,
	0);
INSERT INTO GD_LS
	VALUES (1048775,
	1048774,
	2048,
	1680,
	2048,
	1648,
	0);
