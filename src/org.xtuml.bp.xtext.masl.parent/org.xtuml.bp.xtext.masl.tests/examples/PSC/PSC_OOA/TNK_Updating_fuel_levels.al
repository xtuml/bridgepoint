//
// Filename: TNK_Updating_fuel_levels.al
//
// Description: 
//
// UK Crown Copyright (c) 2007,2009. All rights reserved.
//
state PSC::TANK.Updating_fuel_levels (Delivered_Volume : in  real) is

begin
  //# Fuel has been used so reduce the volume in the tank.
  
  this.Reduce_Level(Delivered_Volume);
  
  generate TANK.Level_Below_Threshold() to this;
end state;
