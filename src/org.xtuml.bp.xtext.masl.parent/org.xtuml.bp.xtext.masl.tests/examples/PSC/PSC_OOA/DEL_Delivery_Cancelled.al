//
// UK Crown Copyright (c) 2016. All rights reserved.
//

terminal state PSC::DELIVERY.Delivery_Cancelled () is
  pump_instance : instance of PUMP; 

begin
  //# The customer has not pumped any fuel therefore there is
  //# no pending transaction to be paid.
  
  ATTENDANT~>Delivery_Cancelled();
  
  pump_instance := this->R3;
  unlink this R3 pump_instance;
  delete this;
end state;
