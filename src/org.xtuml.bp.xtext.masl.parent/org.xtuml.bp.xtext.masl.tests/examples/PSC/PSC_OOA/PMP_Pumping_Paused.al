//
// UK Crown Copyright (c) 2016. All rights reserved.
//

state PSC::PUMP.Pumping_Paused () is

begin
  //# Disengage clutch which stops pumping.
  //# Wait for gun to be replaced into the 
  //# holster or for the trigger to be depressed.
  
  CLUTCH~>Disengage_Clutch();
end state;
