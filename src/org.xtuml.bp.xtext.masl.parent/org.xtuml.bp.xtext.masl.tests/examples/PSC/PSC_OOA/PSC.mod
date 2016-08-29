//
// Filename: PSC.mod
//
// Description: Petrol Station Control
//              The Petrol Station Control domain manages the dispensing of
//              petrol, customer payments and tank levels.
//              
//              Before a customer can use the self-service pumps, the pump
//              must be enabled by the attendant.  When a pump is enabled,
//              the pump motor is started, if it is not already on, with the
//              pump clutch free.  When the trigger in the gun is depressed,
//              closing a micro switch, the clutch is engaged and petrol
//              pumped.  When it is released, the clutch is freed.  There is
//              also a micro switch on the holster in which the gun is kept
//              which prevents petrol being pumped until the gun is taken
//              out.  Once the gun is replaced in the holster, the delivery
//              is deemed to be completed and the pump disabled.  Further
//              depressions of the trigger in the gun cannot dispense more
//              petrol.  After a short standby period, the pump motor will
//              be turned off unless the pump is re-enabled.
//              
//              A metering device in the petrol line sends a pulse to the
//              system for each 1/100 litre dispensed.  The cost of the fuel
//              is calculated using the amount delivered and unit cost which
//              is displayed on the pump.
//              
//              There are two kinds of pump.  The normal kind allows the
//              user to dispense petrol ad lib.  The sophisticated pumps,
//              imported from New Zealand, allow the customer to preset an
//              amount and a volume of petrol.  Petrol will then be pumped
//              up to a maximum of the required quantity.
//              
//              Transactions are stored until the customer pays.  Payment
//              may be either in cash, by credit card or account.  A
//              customer may request a receipt (showing price, time, date
//              etc.) and will get a token for every 5 pounds.  Customers
//              sometimes abscond without paying and the operator must
//              annotate the transaction with any available information, the
//              vehicle's registration number for example.  At the end of
//              the day, transactions are archived and may be used for ad
//              hoc enquiries on sales.
//              
//              At present, two grades of petrol are dispensed from five
//              pumps on the forecourt.  Each pump takes its supply from one
//              of two tanks, one tank for each grade.  The tank level must
//              not drop below 4% of the tank's capacity.  If this happens,
//              the pumps serviced by that tank cannot be enabled to
//              dispense petrol.
//
//
// UK Crown Copyright (c) 2007,2009. All rights reserved.
//
domain PSC is
  object DELIVERY;
  object PUMP;
  object TANK;
  object FUEL_GRADE;
  object TRANSACTION;
  object PENDING_TRANSACTION;
  object PAID_TRANSACTION;
  object EVADED_TRANSACTION;
  object TEST;

  type Transaction_Subtype is enum (Pending,
                                    Paid,
                                    Evaded);

  type Timer_ID is integer;

  terminator ATTENDANT is
    private service Request_Pump_Enable ();

    private service Transaction_Pending ();

    private service Delivery_Cancelled ();

  end terminator;
  pragma key_letter ("AT");


  terminator MOTOR is
    private service Start_Motor ();

    private service Stop_Motor ();

  end terminator;
  pragma key_letter ("MO");


  terminator CUSTOMER is
    private service Pump_Unavailable ();

  end terminator;
  pragma key_letter ("CU");


  terminator METER is
  end terminator;
  pragma key_letter ("ME");


  terminator CLUTCH is
    private service Engage_Clutch ();

    private service Disengage_Clutch ();

  end terminator;
  pragma key_letter ("CL");


  terminator TANKER_OPERATOR is
  end terminator;
  pragma key_letter ("TO");


  terminator GUN is
  end terminator;
  pragma key_letter ("GU");


  terminator HOLSTER is
  end terminator;
  pragma key_letter ("HO");


  // Example Instance Population
  private service Example_Instance_Population_1 (); pragma scenario (1);

  // CUSTOMER Removes Gun From Pump 2
  private service CUSTOMER_Removes_Gun_From_Pump_2_1 (); pragma external (1);

  // ATTENDENT Enables Pump 2
  private service ATTENDENT_Enables_Pump_2_2 (); pragma external (2);

  // CUSTOMER Presses Trigger At Pump 2
  private service CUSTOMER_Presses_Trigger_At_Pump_2_3 (); pragma external (3);

  // METER Delivers Fuel Unit For Pump 2
  private service METER_Delivers_Fuel_Unit_For_Pump_2_4 (); pragma external (4);

  // CUSTOMER Releases Trigger At Pump 2
  private service CUSTOMER_Releases_Trigger_At_Pump_2_5 (); pragma external (5);

  // CUSTOMER Replaces Gun At Pump 2
  private service CUSTOMER_Replaces_Gun_At_Pump_2_6 (); pragma external (6);

  // CUSTOMER Pays For Fuel For Pump 2
  private service CUSTOMER_Pays_For_Fuel_For_Pump_2_7 (); pragma external (7);

  // CUSTOMER Absconds From Pump 2
  private service CUSTOMER_Absconds_From_Pump_2_8 (); pragma external (8);

  // TANKER Delivery For Tank 1002
  private service TANKER_Delivery_For_Tank_1002_9 (); pragma external (9);

  relationship R1 is PUMP unconditionally is_pumping_fuel_from one TANK,
                     TANK conditionally is_providing_fuel_for many PUMP;

  relationship R2 is TANK unconditionally stores one FUEL_GRADE,
                     FUEL_GRADE unconditionally is_stored_in one TANK;

  relationship R3 is DELIVERY unconditionally is_being_made_using one PUMP,
                     PUMP conditionally is_being_used_for one DELIVERY;

  relationship R9 is PUMP conditionally delivered_fuel_for one PENDING_TRANSACTION,
                     PENDING_TRANSACTION unconditionally is_pending_for one PUMP;

  relationship R10 is PUMP conditionally has_delivered_fuel_for many TRANSACTION,
                      TRANSACTION unconditionally records_fuel_delivery_for one PUMP;

  relationship R4 is TRANSACTION is_a (PENDING_TRANSACTION,
                                       PAID_TRANSACTION,
                                       EVADED_TRANSACTION);

  relationship R99 is DELIVERY conditionally related_to1 one TEST,
                     TEST conditionally related_to2 one DELIVERY;

  object TEST is
    id : preferred unique integer;
    Time : referential (R99.related_to2.DELIVERY.Time) timestamp;
    Pump_Number      :  referential (R99.related_to2.DELIVERY.Pump_Number) integer;
  end object;

  object DELIVERY is
    Time             : preferred timestamp;
    Pump_Number      : preferred referential (R3.is_being_made_using.PUMP.Pump_Number) integer;
    Volume_Delivered : real;
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
      Non_Existant (                               Create_Delivery     => Creating_Delivery,
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
  pragma key_letter ("DEL");

  object PUMP is
    Pump_Number : preferred integer;
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
      Non_Existant (          Gun_Removed       => Cannot_Happen,
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
  pragma key_letter ("PMP");

  object TANK is
    Tank_Number     : preferred integer;
    Grade_Name      : referential (R2.stores.FUEL_GRADE.Grade_Name) string;
    Tank_Empty_Flag : boolean;
    Tank_Level      : real;
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
      Non_Existant (                         Tanker_Delivery       => Cannot_Happen,
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
  pragma key_letter ("TNK");

  object FUEL_GRADE is
    Grade_Name : preferred string;
    Unit_Price : real;

  end object;
  pragma key_letter ("FGR");

  object TRANSACTION is
    Transaction_Number       : preferred unique integer;
    Pump_Number              : referential (R10.records_fuel_delivery_for.PUMP.Pump_Number) integer;
    Transaction_Type         : Transaction_Subtype;
    Cost                     : real;
    Transaction_Process_Time : timestamp;
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
      Non_Existant (                Create_Transaction => Creating_pending_transaction,
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
  pragma key_letter ("TRN");

  object PENDING_TRANSACTION is
    Transaction_Number : preferred referential (R4.Transaction_Number) integer;
    Pump_Number        : referential (R9.is_pending_for.PUMP.Pump_Number) integer;

  end object;
  pragma key_letter ("PND");

  object PAID_TRANSACTION is
    Transaction_Number : preferred referential (R4.Transaction_Number) integer;

  end object;
  pragma key_letter ("PDT");

  object EVADED_TRANSACTION is
    Transaction_Number : preferred referential (R4.Transaction_Number) integer;
    Observations       : string;

  end object;
  pragma key_letter ("EVD");

end domain;
pragma number (28);
