//
// Filename: PMP_Waiting_Pump_Enable.al
//
// Description: 
//
// UK Crown Copyright (c) 2007,2009. All rights reserved.
//
state PSC::PUMP.Waiting_Pump_Enable () is
  supplying_tank : instance of TANK; 

begin
  //# Determine whether the connected tank contains 
  //# more than 4% of its capacity.
  
  supplying_tank := this->R1;
  
  if supplying_tank.Tank_Empty_Flag = true then
    generate PUMP.Fuel_Level_Low() to this;
  else
    ATTENDANT~>Request_Pump_Enable();
  end if;
end state;
