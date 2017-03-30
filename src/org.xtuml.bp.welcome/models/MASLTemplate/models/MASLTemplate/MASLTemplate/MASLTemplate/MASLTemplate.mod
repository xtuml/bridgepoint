domain MASLTemplate is
  object A1;
  object B1;
  object Isuper;
  object Jsub;
  object Ksubsuper;
  object Lsub;
  object employee;
  object report_line;
  private type domtyp is integer
  ;
  public type ex_type is structure
    group_name: string;     
    operation_names: sequence of string;     
end structure
  ;
    public service public_service_1 (
        val : in ex_type    );
  terminator TermA is
    public service terminator_svc_1 (
    );
  end terminator;
  relationship R7 is employee unconditionally manages many employee,
    employee conditionally works_for one employee
    using report_line;
  relationship R8 is B1 conditionally aend one A1,
    A1 conditionally bend one B1;
  relationship R9 is Isuper is_a ( Ksubsuper, Jsub );
  relationship R10 is Ksubsuper is_a ( Lsub );
  object A1 is
    aID : preferred  integer;
    i :   integer;
    bID :   referential ( R8.bend.B1.bID ) integer;
  end object;
  object B1 is
    bID : preferred  integer;
    i :   integer;
//! state one comment
     state first();
//! state two comment
//! line two
     state second();
     state third();
//! event comment
     event go();
     transition is
      Non_Existent (
        go => Cannot_Happen      ); 
      first (
        go => second      ); 
      second (
        go => third      ); 
      third (
        go => first      ); 
    end transition;
  end object;
  object Isuper is
    iID : preferred  integer;
    common :   integer;
//!This is an example of a deferred operation.  In the supertype the dialect is 
//!set to None.  In the subtypes where it is actually implemented the dialect
//!is set to MASL.
    public instance deferred ( R9 ) service deferredOp (
    );
  end object;
  object Jsub is
    jID : preferred  integer;
    specialJ :   integer;
    iID :   referential ( R9.iID ) integer;
    identifier is ( iID );
    public instance service deferredOp (
    );
  end object;
  object Ksubsuper is
    kID : preferred  integer;
    specialcommon :   integer;
    iID :   referential ( R9.iID ) integer;
    identifier is ( iID );
    public instance service deferredOp (
    );
  end object;
  object Lsub is
    lID : preferred  integer;
    specialL :   integer;
    kID :   referential ( R10.kID ) integer;
    identifier is ( kID );
  end object;
  object employee is
    nID : preferred  integer;
    name :   string;
    public instance service get_name (
    ) return string;
  end object;
  object report_line is
    mID : preferred  integer;
    department :   string;
    manages_nID :   referential ( R7.works_for.employee.nID ) integer;
    works_for_nID :   referential ( R7.manages.employee.nID ) integer;
    identifier is ( works_for_nID, manages_nID );
  end object;
end domain;
