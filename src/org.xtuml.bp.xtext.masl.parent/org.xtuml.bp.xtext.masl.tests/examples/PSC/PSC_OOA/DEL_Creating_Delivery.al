//
// UK Crown Copyright (c) 2016. All rights reserved.
//

creation state PSC::DELIVERY.Creating_Delivery (Pump_Id : in  integer) is
  new_delivery : instance of DELIVERY; 
  delivering_pump : instance of PUMP; 

begin
  //# Create a delivery instance and enable the pump.
  
  new_delivery := create DELIVERY (
	Pump_Number => Pump_Id, 
	Time => timestamp'now,
	Volume_Delivered => 0.0, 
	Cost => 0.0,
        Current_State => Creating_Delivery);
  
  delivering_pump := find_one PUMP (Pump_Number = Pump_Id);
  
  link new_delivery R3 delivering_pump;
  
  generate PUMP.Pump_Enabled() to delivering_pump;
end state;
