//
// UK Crown Copyright (c) 2016. All rights reserved.
//

terminal state PSC::DELIVERY.Delivery_complete_state () is
  supplying_tank : instance of TANK; 
  pump_instance : instance of PUMP; 

begin
  //# Delivery completed so create a transaction.
  //# Inform the tank amount of fuel dispensed.
  
  generate TRANSACTION.Create_Transaction(this.Time, this.Pump_Number, this.Cost);
  
  supplying_tank := this->R3->R1;
  generate TANK.Fuel_Used(this.Volume_Delivered) to supplying_tank;
  
  pump_instance := this->R3;
  unlink this R3 pump_instance;
  delete this;
end state;
