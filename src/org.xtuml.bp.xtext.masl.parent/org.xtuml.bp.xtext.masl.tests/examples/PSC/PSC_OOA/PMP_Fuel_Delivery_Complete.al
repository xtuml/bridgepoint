//
// Filename: PMP_Fuel_Delivery_Complete.al
//
// Description: 
//
// UK Crown Copyright (c) 2007,2009. All rights reserved.
//
state PSC::PUMP.Fuel_Delivery_Complete () is
  current_delivery : instance of DELIVERY; 

begin
  //# Delivery Now Complete, Stop motor and return pump 
  //# to waiting state
 
  current_delivery := this->R3;
  generate DELIVERY.Delivery_Complete() to current_delivery;
  
  MOTOR~>Stop_Motor();
  generate PUMP.Customer_Finished() to this;
end state;
