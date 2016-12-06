//
// UK Crown Copyright (c) 2016. All rights reserved.
//

creation state PSC::TRANSACTION.Creating_pending_transaction (Delivery_Time : in  timestamp,
                                                              Delivery_Pump : in  integer,
                                                              Delivery_Cost : in  real) is
  new_transaction : instance of TRANSACTION; 
  new_pending_transaction : instance of PENDING_TRANSACTION; 
  delivery_pump_instance : instance of PUMP; 

begin
  //# Create pending transaction waiting payment from customer
  
  new_transaction := create unique TRANSACTION (
	Delivery_Start_Time => Delivery_Time, 
	Cost => Delivery_Cost, 
	Transaction_Type => Pending,
        Current_State => Creating_pending_transaction);
  
  new_pending_transaction := create PENDING_TRANSACTION (
	Transaction_Number => new_transaction.Transaction_Number);
  
  link new_transaction R4.PENDING_TRANSACTION new_pending_transaction;
  
  delivery_pump_instance := find_one PUMP (Pump_Number = Delivery_Pump);
  
  link new_transaction R10 delivery_pump_instance;
  link new_pending_transaction R9 delivery_pump_instance;
end state;
