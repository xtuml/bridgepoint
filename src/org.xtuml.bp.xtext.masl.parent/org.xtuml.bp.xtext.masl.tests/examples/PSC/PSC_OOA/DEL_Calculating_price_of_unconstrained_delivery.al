//
// UK Crown Copyright (c) 2016. All rights reserved.
//

state PSC::DELIVERY.Calculating_price_of_unconstrained_delivery () is
  new_volume : real; 
  grade : instance of FUEL_GRADE; 
  new_cost : real; 

begin
  //# A unit of petrol has been delivered. 
  //# Update the volume delivered and current cost. 
  
  new_volume := this.Volume_Delivered + 0.01;
  this.Volume_Delivered := new_volume;
  
  grade := this->R3->R1->R2;
  new_cost := this.Cost + grade.Unit_Price;
  this.Cost := new_cost;
end state;
