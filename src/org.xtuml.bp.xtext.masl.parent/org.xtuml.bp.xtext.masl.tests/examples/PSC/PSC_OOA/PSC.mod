//
// UK Crown Copyright (c) 2016. All rights reserved.
//

//! The Petrol Station Control domain manages the dispensing of
//! petrol, customer payments and tank levels.
//! 
//! Before a customer can use the self-service pumps, the pump
//! must be enabled by the attendant.  When a pump is enabled,
//! the pump motor is started, if it is not already on, with the
//! pump clutch free.  When the trigger in the gun is depressed,
//! closing a micro switch, the clutch is engaged and petrol
//! pumped.  When it is released, the clutch is freed.  There is
//! also a micro switch on the holster in which the gun is kept
//! which prevents petrol being pumped until the gun is taken
//! out.  Once the gun is replaced in the holster, the delivery
//! is deemed to be completed and the pump disabled.  Further
//! depressions of the trigger in the gun cannot dispense more
//! petrol.  After a short standby period, the pump motor will
//! be turned off unless the pump is re-enabled.
//! 
//! A metering device in the petrol line sends a pulse to the
//! system for each 1/100 litre dispensed.  The cost of the fuel
//! is calculated using the amount delivered and unit cost which
//! is displayed on the pump.
//! 
//! There are two kinds of pump.  The normal kind allows the
//! user to dispense petrol ad lib.  The sophisticated pumps,
//! imported from New Zealand, allow the customer to preset an
//! amount and a volume of petrol.  Petrol will then be pumped
//! up to a maximum of the required quantity.
//! 
//! Transactions are stored until the customer pays.  Payment
//! may be either in cash, by credit card or account.  A
//! customer may request a receipt (showing price, time, date
//! etc.) and will get a token for every 5 pounds.  Customers
//! sometimes abscond without paying and the operator must
//! annotate the transaction with any available information, the
//! vehicle's registration number for example.  At the end of
//! the day, transactions are archived and may be used for ad
//! hoc enquiries on sales.
//! 
//! At present, two grades of petrol are dispensed from five
//! pumps on the forecourt.  Each pump takes its supply from one
//! of two tanks, one tank for each grade.  The tank level must
//! not drop below 4% of the tank's capacity.  If this happens,
//! the pumps serviced by that tank cannot be enabled to
//! dispense petrol.
domain PSC is
  object DELIVERY;
  object PUMP;
  object TANK;
  object FUEL_GRADE;
  object TRANSACTION;
  object PENDING_TRANSACTION;
  object PAID_TRANSACTION;
  object EVADED_TRANSACTION;

  //! Indicates the subtype of the TRANSACTION object.
  private type Transaction_Subtype is enum (Pending,
                                            Paid,
                                            Evaded);

  private type Timer_ID is integer;

  //! Represents garage attendant interface
  terminator ATTENDANT is
    public service Request_Pump_Enable ();

    public service Transaction_Pending ();

    public service Delivery_Cancelled ();

  end terminator;
  pragma key_letter ("AT");


  //! Represents interface with the pump's motor
  terminator MOTOR is
    public service Start_Motor ();

    public service Stop_Motor ();

  end terminator;
  pragma key_letter ("MO");


  //! Represents interface with customer
  terminator CUSTOMER is
    public service Pump_Unavailable ();

  end terminator;
  pragma key_letter ("CU");


  //! Represents meter associated with a pump
  terminator METER is
  end terminator;
  pragma key_letter ("ME");


  //! Represents interface with pump's clutch
  terminator CLUTCH is
    public service Engage_Clutch ();

    public service Disengage_Clutch ();

  end terminator;
  pragma key_letter ("CL");


  //! Represents interface with tanker operator
  terminator TANKER_OPERATOR is
  end terminator;
  pragma key_letter ("TO");


  //! Represents interface with pump's gun.
  terminator GUN is
  end terminator;
  pragma key_letter ("GU");


  //! Represents interface with pump's holster.
  terminator HOLSTER is
  end terminator;
  pragma key_letter ("HO");


  //! Example Instance Population
  private service Example_Instance_Population_1 (); pragma scenario (1);

  //! CUSTOMER Removes Gun From Pump 2
  private service CUSTOMER_Removes_Gun_From_Pump_2_1 (); pragma external (1);

  //! ATTENDENT Enables Pump 2
  private service ATTENDENT_Enables_Pump_2_2 (); pragma external (2);

  //! CUSTOMER Presses Trigger At Pump 2
  private service CUSTOMER_Presses_Trigger_At_Pump_2_3 (); pragma external (3);

  //! METER Delivers Fuel Unit For Pump 2
  private service METER_Delivers_Fuel_Unit_For_Pump_2_4 (); pragma external (4);

  //! CUSTOMER Releases Trigger At Pump 2
  private service CUSTOMER_Releases_Trigger_At_Pump_2_5 (); pragma external (5);

  //! CUSTOMER Replaces Gun At Pump 2
  private service CUSTOMER_Replaces_Gun_At_Pump_2_6 (); pragma external (6);

  //! CUSTOMER Pays For Fuel For Pump 2
  private service CUSTOMER_Pays_For_Fuel_For_Pump_2_7 (); pragma external (7);

  //! CUSTOMER Absconds From Pump 2
  private service CUSTOMER_Absconds_From_Pump_2_8 (); pragma external (8);

  //! TANKER Delivery For Tank 1002
  private service TANKER_Delivery_For_Tank_1002_9 (); pragma external (9);

  //! Relationship between a pump and the tank which supplies it.
  relationship R1 is PUMP unconditionally is_pumping_fuel_from one TANK,
                     TANK conditionally is_providing_fuel_for many PUMP;

  //! Relationship between a petrol tank and the type of petrol it
  //! stores.
  relationship R2 is TANK unconditionally stores one FUEL_GRADE,
                     FUEL_GRADE unconditionally is_stored_in one TANK;

  //! Relationship between a delivery and the pump which was used
  relationship R3 is DELIVERY unconditionally is_being_made_using one PUMP,
                     PUMP conditionally is_being_used_for one DELIVERY;

  relationship R9 is PUMP conditionally delivered_fuel_for one PENDING_TRANSACTION,
                     PENDING_TRANSACTION unconditionally is_pending_for one PUMP;

  relationship R10 is PUMP conditionally has_delivered_fuel_for many TRANSACTION,
                      TRANSACTION unconditionally records_fuel_delivery_for one PUMP;

  relationship R4 is TRANSACTION is_a (PENDING_TRANSACTION,
                                       PAID_TRANSACTION,
                                       EVADED_TRANSACTION);

  //! A Delivery records the customers interaction with a Pump.
  //! 
  //! The delivery is identified by the Pump Number and the time
  //! the fuel delivery started. It also records the volume of
  //! fuel delivered and the cost.
  //! 
  //! On completion of the fuel delivery if the customer has
  //! pumped some fuel then a transaction is created with the
  //! appropriate details. If no fuel was delivered then the
  //! delivery is deleted and no transaction created.
  object DELIVERY is

    //! Time fuel delivery commenced.
    Time             : preferred timestamp;

    //! Pump used for the delivery
    Pump_Number      : preferred referential (R3.is_being_made_using.PUMP.Pump_Number) integer;

    //! The amount of fuel currently delivered in litres.
    Volume_Delivered : real;

    //! Cost of the fuel delivery.
    Cost             : real;


    creation state Creating_Delivery (Pump_Id : in  integer);

    state Calculating_price_of_unconstrained_delivery ();

    terminal state Delivery_complete_state ();

    terminal state Delivery_Cancelled ();

    creation event Create_Delivery (Pump_Id : in  integer);

    event Fuel_Unit_Delivered ();

    event Delivery_Complete ();

    event Delete_Delivery ();

    transition is
      Non_Existent (                               Create_Delivery     => Creating_Delivery,
                                                   Fuel_Unit_Delivered => Cannot_Happen,
                                                   Delivery_Complete   => Cannot_Happen,
                                                   Delete_Delivery     => Cannot_Happen);
      Creating_Delivery (                          Create_Delivery     => Cannot_Happen,
                                                   Fuel_Unit_Delivered => Calculating_price_of_unconstrained_delivery,
                                                   Delivery_Complete   => Delivery_Cancelled,
                                                   Delete_Delivery     => Ignore);
      Calculating_price_of_unconstrained_delivery (Create_Delivery     => Cannot_Happen,
                                                   Fuel_Unit_Delivered => Calculating_price_of_unconstrained_delivery,
                                                   Delivery_Complete   => Delivery_complete_state,
                                                   Delete_Delivery     => Ignore);
      Delivery_complete_state (                    Create_Delivery     => Cannot_Happen,
                                                   Fuel_Unit_Delivered => Cannot_Happen,
                                                   Delivery_Complete   => Cannot_Happen,
                                                   Delete_Delivery     => Cannot_Happen);
      Delivery_Cancelled (                         Create_Delivery     => Cannot_Happen,
                                                   Fuel_Unit_Delivered => Cannot_Happen,
                                                   Delivery_Complete   => Cannot_Happen,
                                                   Delete_Delivery     => Cannot_Happen);
    end transition;

  end object;
  pragma id (2);
  pragma key_letter ("DEL");

  //! Represents the self service pump within the Petrol Station.
  //! 
  //! Each pump is uniquely identified by a number, this is the
  //! same number as written on the side of the pump which
  //! Customers use to identify which pump they have used when
  //! paying for petrol.
  //! 
  //! Each pump supplies one type of fuel and is permanently
  //! connected to a tank supplying that Fuel Grade.
  object PUMP is

    //! Pump identifier, as written on the pump. This shall be used
    //! by the customer when he/she has to pay.
    Pump_Number : preferred integer;

    //! Link to connected tank
    Tank_Number : referential (R1.is_pumping_fuel_from.TANK.Tank_Number) integer;


    state Waiting_For_Customer ();

    state Waiting_Pump_Enable ();

    state Fuel_Unavailable ();

    state Fuel_Delivery_Complete ();

    state Ready_To_Pump ();

    state Pumping_Paused ();

    state Pumping ();

    event Gun_Removed ();

    event Gun_Replaced ();

    event Fuel_Level_Ok ();

    event Fuel_Level_Low ();

    event Fuel_Available ();

    event Pump_Enabled ();

    event Trigger_Depressed ();

    event Trigger_Released ();

    event Customer_Finished ();

    transition is
      Non_Existent (          Gun_Removed       => Cannot_Happen,
                              Gun_Replaced      => Cannot_Happen,
                              Fuel_Level_Ok     => Cannot_Happen,
                              Fuel_Level_Low    => Cannot_Happen,
                              Fuel_Available    => Cannot_Happen,
                              Pump_Enabled      => Cannot_Happen,
                              Trigger_Depressed => Cannot_Happen,
                              Trigger_Released  => Cannot_Happen,
                              Customer_Finished => Cannot_Happen);
      Waiting_For_Customer (  Gun_Removed       => Waiting_Pump_Enable,
                              Gun_Replaced      => Ignore,
                              Fuel_Level_Ok     => Ignore,
                              Fuel_Level_Low    => Ignore,
                              Fuel_Available    => Ignore,
                              Pump_Enabled      => Ignore,
                              Trigger_Depressed => Ignore,
                              Trigger_Released  => Ignore,
                              Customer_Finished => Ignore);
      Waiting_Pump_Enable (   Gun_Removed       => Ignore,
                              Gun_Replaced      => Waiting_For_Customer,
                              Fuel_Level_Ok     => Cannot_Happen,
                              Fuel_Level_Low    => Fuel_Unavailable,
                              Fuel_Available    => Ignore,
                              Pump_Enabled      => Ready_To_Pump,
                              Trigger_Depressed => Ignore,
                              Trigger_Released  => Ignore,
                              Customer_Finished => Ignore);
      Fuel_Unavailable (      Gun_Removed       => Ignore,
                              Gun_Replaced      => Ignore,
                              Fuel_Level_Ok     => Ignore,
                              Fuel_Level_Low    => Ignore,
                              Fuel_Available    => Waiting_For_Customer,
                              Pump_Enabled      => Ignore,
                              Trigger_Depressed => Ignore,
                              Trigger_Released  => Ignore,
                              Customer_Finished => Ignore);
      Fuel_Delivery_Complete (Gun_Removed       => Ignore,
                              Gun_Replaced      => Ignore,
                              Fuel_Level_Ok     => Ignore,
                              Fuel_Level_Low    => Ignore,
                              Fuel_Available    => Ignore,
                              Pump_Enabled      => Ignore,
                              Trigger_Depressed => Ignore,
                              Trigger_Released  => Ignore,
                              Customer_Finished => Waiting_For_Customer);
      Ready_To_Pump (         Gun_Removed       => Ignore,
                              Gun_Replaced      => Fuel_Delivery_Complete,
                              Fuel_Level_Ok     => Ignore,
                              Fuel_Level_Low    => Ignore,
                              Fuel_Available    => Ignore,
                              Pump_Enabled      => Ignore,
                              Trigger_Depressed => Pumping,
                              Trigger_Released  => Ignore,
                              Customer_Finished => Ignore);
      Pumping_Paused (        Gun_Removed       => Ignore,
                              Gun_Replaced      => Fuel_Delivery_Complete,
                              Fuel_Level_Ok     => Ignore,
                              Fuel_Level_Low    => Ignore,
                              Fuel_Available    => Ignore,
                              Pump_Enabled      => Ignore,
                              Trigger_Depressed => Pumping,
                              Trigger_Released  => Ignore,
                              Customer_Finished => Ignore);
      Pumping (               Gun_Removed       => Ignore,
                              Gun_Replaced      => Ignore,
                              Fuel_Level_Ok     => Ignore,
                              Fuel_Level_Low    => Ignore,
                              Fuel_Available    => Ignore,
                              Pump_Enabled      => Ignore,
                              Trigger_Depressed => Ignore,
                              Trigger_Released  => Pumping_Paused,
                              Customer_Finished => Ignore);
    end transition;

  end object;
  pragma id (3);
  pragma key_letter ("PMP");

  //! Represents the storage vessel of fuel within the Petrol
  //! Station. The Tank is normally buried under ground and can be
  //! filled through an inlet valve. A Tank contains one type of
  //! Fuel and cannot be re-filled with another fuel type.
  //! 
  //! Each Tank has a Capacity in litres, current level in litres,
  //! an empty threshold which is the percentage that the tank is
  //! allowed to fall to before it is regarded as being Empty. 
  //! 
  //! Each Tank is identified by a Unique number. This number
  //! shall be the same as indicated on the Inlet valve to the
  //! tank.
  object TANK is

    //! Tank identifier, this shall be the number on the inlet valve
    //! to the tank. The Tanker operator shall use this when
    //! re-filling the tanks.
    Tank_Number     : preferred integer;

    //! Referential attribute to Fuel Grade
    Grade_Name      : referential (R2.stores.FUEL_GRADE.Grade_Name) string;

    //! Set to true when tank level goes below 4% of its capacity.
    Tank_Empty_Flag : boolean;

    //! Litres of petrol in tank. This level is calculated by the
    //! system during normal operation. It shall be possible for the
    //! operator to update this value when measuring the tank levels
    //! using a dip stick.
    Tank_Level      : real;

    //! The capacity of the tank in litres.
    Tank_Capacity   : real;

    Empty_Threshold : real;


    public instance service Check_Level (Below_Threshold : out boolean);

    public instance service Reduce_Level (Delivered_Volume : in  real);

    public instance service Increase_Level (Delivered_Volume : in  real);

    public instance service Inform_Connected_Pumps_Fuel_Available ();

    state Checking_Levels_After_Tanker_Delivery (Added_Volume : in  real);

    state Waiting_For_Tanker_Delivery ();

    state Reset_waiting_pumps ();

    state Checking_Levels_After_Pump_Usage (Delivered_Volume : in  real);

    state Updating_fuel_levels (Delivered_Volume : in  real);

    event Tanker_Delivery (Added_Volume : in  real);

    event Level_Below_Threshold ();

    event Level_Above_Threshold ();

    event Fuel_Used (Delivered_Volume : in  real);

    transition is
      Non_Existent (                         Tanker_Delivery       => Cannot_Happen,
                                             Level_Below_Threshold => Cannot_Happen,
                                             Level_Above_Threshold => Cannot_Happen,
                                             Fuel_Used             => Cannot_Happen);
      Checking_Levels_After_Tanker_Delivery (Tanker_Delivery       => Cannot_Happen,
                                             Level_Below_Threshold => Waiting_For_Tanker_Delivery,
                                             Level_Above_Threshold => Reset_waiting_pumps,
                                             Fuel_Used             => Cannot_Happen);
      Waiting_For_Tanker_Delivery (          Tanker_Delivery       => Checking_Levels_After_Tanker_Delivery,
                                             Level_Below_Threshold => Cannot_Happen,
                                             Level_Above_Threshold => Cannot_Happen,
                                             Fuel_Used             => Updating_fuel_levels);
      Reset_waiting_pumps (                  Tanker_Delivery       => Checking_Levels_After_Tanker_Delivery,
                                             Level_Below_Threshold => Cannot_Happen,
                                             Level_Above_Threshold => Cannot_Happen,
                                             Fuel_Used             => Checking_Levels_After_Pump_Usage);
      Checking_Levels_After_Pump_Usage (     Tanker_Delivery       => Cannot_Happen,
                                             Level_Below_Threshold => Waiting_For_Tanker_Delivery,
                                             Level_Above_Threshold => Reset_waiting_pumps,
                                             Fuel_Used             => Cannot_Happen);
      Updating_fuel_levels (                 Tanker_Delivery       => Cannot_Happen,
                                             Level_Below_Threshold => Waiting_For_Tanker_Delivery,
                                             Level_Above_Threshold => Cannot_Happen,
                                             Fuel_Used             => Cannot_Happen);
    end transition;

  end object;
  pragma id (4);
  pragma key_letter ("TNK");

  //! Each Petrol Station supplies a number of fuel grades. Each
  //! grade has a name which uniquely identifies that grade and a
  //! price for each litre of that fuel grade.
  object FUEL_GRADE is

    //! The type of petrol and identifier.
    Grade_Name : preferred string;

    //! Price per litre in pounds sterling.
    Unit_Price : real;


  end object;
  pragma id (5);
  pragma key_letter ("FGR");

  //! Transactions record the interaction between a customer and a
  //! pump that has resulted in petrol being pumped.
  //! 
  //! The transaction is identified by a unique abitray number. It
  //! is envisaged that transactions shall be archived at the end
  //! of each working day.
  //! 
  //! The transaction records a number of details including The
  //! Pump used, Cost, Delivery Start time and the time the
  //! transaction was last processed.
  //! 
  //! There are also a number of types of transaction :-
  //! 'Evaded Transaction' - The customer absconds
  //! 'Paid Transaction' - The customer has paid for the fuel
  //! 'Pending Transaction' - Waiting for the customer to pay.
  object TRANSACTION is

    //! Transaction identifier this is an abitary number.
    Transaction_Number       : preferred unique integer;

    Pump_Number              : referential (R10.records_fuel_delivery_for.PUMP.Pump_Number) integer;

    //! Indicates the Related Sub-Type of this transaction.
    Transaction_Type         : Transaction_Subtype;

    //! The cost of the fuel delivered in this transaction.
    Cost                     : real;

    //! The time at which the attendent processed this transaction.
    Transaction_Process_Time : timestamp;

    //! The time the delivery commenced, this shall be copied from
    //! the DELIVERY Object.
    Delivery_Start_Time      : timestamp;


    creation state Creating_pending_transaction (Delivery_Time : in  timestamp,
                                                 Delivery_Pump : in  integer,
                                                 Delivery_Cost : in  real);

    state Paid_state ();

    state Evaded_state (Customer_Details : in  string);

    creation event Create_Transaction (Delivery_Time : in  timestamp,
                                       Delivery_Pump : in  integer,
                                       Delivery_Cost : in  real);

    event Payment_Received ();

    event Customer_Absconds (Customer_Details : in  string);

    event Transaction_paid ();

    transition is
      Non_Existent (                Create_Transaction => Creating_pending_transaction,
                                    Payment_Received   => Cannot_Happen,
                                    Customer_Absconds  => Cannot_Happen,
                                    Transaction_paid   => Cannot_Happen);
      Creating_pending_transaction (Create_Transaction => Cannot_Happen,
                                    Payment_Received   => Paid_state,
                                    Customer_Absconds  => Evaded_state,
                                    Transaction_paid   => Cannot_Happen);
      Paid_state (                  Create_Transaction => Cannot_Happen,
                                    Payment_Received   => Cannot_Happen,
                                    Customer_Absconds  => Cannot_Happen,
                                    Transaction_paid   => Cannot_Happen);
      Evaded_state (                Create_Transaction => Cannot_Happen,
                                    Payment_Received   => Paid_state,
                                    Customer_Absconds  => Ignore,
                                    Transaction_paid   => Cannot_Happen);
    end transition;

  end object;
  pragma id (6);
  pragma key_letter ("TRN");

  //! A Transaction that is waiting for the customer to pay.
  //! 
  //! The pump where the transaction has been performed is
  //! recorded.
  object PENDING_TRANSACTION is

    //! Link to supertype
    Transaction_Number : preferred referential (R4.Transaction_Number) integer;

    Pump_Number        : referential (R9.is_pending_for.PUMP.Pump_Number) integer;


  end object;
  pragma id (7);
  pragma key_letter ("PND");

  //! A Transaction where the customer has paid for the fuel.
  object PAID_TRANSACTION is

    //! Link to supertype
    Transaction_Number : preferred referential (R4.Transaction_Number) integer;


  end object;
  pragma id (8);
  pragma key_letter ("PDT");

  //! A Transaction where the customer has left without paying.
  object EVADED_TRANSACTION is

    //! Link to supertype
    Transaction_Number : preferred referential (R4.Transaction_Number) integer;

    //! Vehicle registration number etc which identifies customer
    Observations       : string;


  end object;
  pragma id (9);
  pragma key_letter ("EVD");

end domain;
pragma number (28);
