domain X is
  
  object object1; 

  public function dom1() return integer;
  public service dom2() return integer;
  public service dom3();

  terminator terminator1 is

    private function term1() return integer;
    private service term2() return integer;
    private service term3();

  end terminator;

  object object1 is

    public function obj1() return integer;
    public service obj2() return integer;
    public service obj3();

  end object;

end domain;
