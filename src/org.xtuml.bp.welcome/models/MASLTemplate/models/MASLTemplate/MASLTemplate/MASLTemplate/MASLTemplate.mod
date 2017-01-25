domain MASLTemplate is
  object a_class;
  object b_class;
  private type domtyp is integer
  ;
    public service public_service_1 (
        val : in ex_type    );
  terminator TermA is
    private service terminator_svc_1 (
    );
  end terminator;
  relationship R1 is a_class conditionally uses many b_class,
    b_class unconditionally is_used_by one a_class;
  object a_class is
    name :   wstring;
    public instance function get_name (
    ) return wstring;
  end object;
pragma key_letter ( "a_class" ); 
  object b_class is
  end object;
pragma key_letter ( "b_class" ); 
end domain;
