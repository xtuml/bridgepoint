//
// UK Crown Copyright (c) 2016. All rights reserved.
//

state PSC::TRANSACTION.Evaded_state (Customer_Details : in  string) is
  pending_transaction_instance : instance of PENDING_TRANSACTION; 
  pump_instance : instance of PUMP; 
  evaded_transaction_instance : instance of EVADED_TRANSACTION; 

begin
  //# Migrate pending subtype to evaded subtype. 
  //# Record customer details.
  
  pending_transaction_instance := this->R4.PENDING_TRANSACTION;
  unlink this R4.PENDING_TRANSACTION pending_transaction_instance;
  pump_instance := pending_transaction_instance->R9;
  unlink pending_transaction_instance R9 pump_instance;
  delete pending_transaction_instance;
  
  evaded_transaction_instance := create EVADED_TRANSACTION (
	Transaction_Number => this.Transaction_Number, 
	Observations => Customer_Details);
  
  this.Transaction_Type := Evaded;
  
  link this R4.EVADED_TRANSACTION evaded_transaction_instance;
end state;
