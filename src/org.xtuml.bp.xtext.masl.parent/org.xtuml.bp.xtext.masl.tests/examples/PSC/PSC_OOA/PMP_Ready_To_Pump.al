//
// UK Crown Copyright (c) 2016. All rights reserved.
//

state PSC::PUMP.Ready_To_Pump () is

begin
  //# Start pump motor and wait for the gun trigger to be depressed.
  //# Or the gun can be replaced.
  MOTOR~>Start_Motor();
end state;
