//
// UK Crown Copyright (c) 2016. All rights reserved.
//

state PSC::TANK.Checking_Levels_After_Pump_Usage (Delivered_Volume : in  real) is
  below_threshold : boolean; 

begin
  //# Reduce recorded tank level by volume delivered. 
  //# If the level is less than 4% of the tanks capacity 
  //# then inhibit connected pumps from making further
  //# deliveries.
  
  this.Reduce_Level(Delivered_Volume);
  
  this.Check_Level(below_threshold);
  
  if below_threshold then
  	this.Tank_Empty_Flag := true;
  	generate TANK.Level_Below_Threshold() to this;
  else
  	generate TANK.Level_Above_Threshold() to this;
  end if;
end state;
