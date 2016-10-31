//
// UK Crown Copyright (c) 2016. All rights reserved.
//

state PSC::TANK.Checking_Levels_After_Tanker_Delivery (Added_Volume : in  real) is
  below_threshold : boolean; 

begin
  //# Add volume to tank level. If the level is greater
  //# than 4% of the tanks capacity reset any waiting pumps.
  //# If the tank is still below its threshold return to the
  //# Nearly Empty state.
  
  this.Increase_Level(Added_Volume);
  
  begin
    this.Check_Level(below_threshold);
  end;
  
  if below_threshold = true then
  	this.Tank_Empty_Flag := true;
  	generate TANK.Level_Below_Threshold() to this;
  else
  	this.Tank_Empty_Flag := false;
  this.Inform_Connected_Pumps_Fuel_Available();
  	generate TANK.Level_Above_Threshold() to this;
  end if; 
end state;
