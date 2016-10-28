//
// UK Crown Copyright (c) 2016. All rights reserved.
//

state PSC::PUMP.Pumping () is

begin
  //# Engage clutch which starts pumping. 
  //# Continue until the gun trigger is released
  
  CLUTCH~>Engage_Clutch();
end state;
