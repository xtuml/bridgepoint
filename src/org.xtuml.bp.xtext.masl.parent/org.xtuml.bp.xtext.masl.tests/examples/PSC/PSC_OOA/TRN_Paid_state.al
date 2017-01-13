//
// UK Crown Copyright (c) 2016. All rights reserved.
//

state PSC::TRANSACTION.Paid_state () is
  pending_transaction_instance : instance of PENDING_TRANSACTION; 
  connected_pump : instance of PUMP; 
  evaded_transaction_instance : instance of EVADED_TRANSACTION; 
  paid_transaction_instance : instance of PAID_TRANSACTION; 

begin
  //# Migrate pending subtype to paid subtype. Link all payments to the paid subtype.
  //# Calculate the number of tokens and tell the operator to issue them to
  //# the customer.
  
  case this.Transaction_Type is
  when Pending =>
  	pending_transaction_instance := this->R4.PENDING_TRANSACTION;
  	unlink this R4.PENDING_TRANSACTION pending_transaction_instance;
  	connected_pump := pending_transaction_instance->R9;
  	unlink pending_transaction_instance R9 connected_pump;
  	delete pending_transaction_instance;
  when Evaded =>
  	evaded_transaction_instance := this->R4.EVADED_TRANSACTION;
  	unlink this R4.EVADED_TRANSACTION evaded_transaction_instance;
  	delete evaded_transaction_instance;
  end case;
  
  paid_transaction_instance := create PAID_TRANSACTION (
	Transaction_Number => this.Transaction_Number);
  this.Transaction_Type := Paid;
  
  link this R4.PAID_TRANSACTION paid_transaction_instance;
  
end state;
