//
// Filename: PMP_Pumping_Paused.al
//
// Description: 
//
// UK Crown Copyright (c) 2007,2009. All rights reserved.
//
state PSC::PUMP.Pumping_Paused () is

begin
  //# Disengage clutch which stops pumping.
  //# Wait for gun to be replaced into the 
  //# holster or for the trigger to be depressed.
  
  CLUTCH~>Disengage_Clutch();
end state;
