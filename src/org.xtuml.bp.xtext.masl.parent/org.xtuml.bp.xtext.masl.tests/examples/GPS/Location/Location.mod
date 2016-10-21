domain Location is

  object GPS;

  public function getDistance ( toLong : in real,
                                toLat : in real,
                                fromLong : in real,
                                fromLat : in real ) return real;
  public service getLocation ( longitude : in real,
                               latitude : in real );
  public service registerListener ();
  public service unregisterListener ();

  object GPS is
    timer : timer;
    currentLatitude : real;
    currentLongitude : real;
    motionSegments : integer;

    public service activate ();

    assigner start state idle();
    assigner state locating();
    assigner event tick();
    assigner event registerListener();
    assigner event unregisterListener();

    assigner transition is
      idle (
        tick => Ignore,
        registerListener => locating,
        unregisterListener => cannot_happen );
      locating (
        tick => locating,
        registerListener => cannot_happen,
        unregisterListener => idle );
    end transition;
  end object;
  pragma key_letter ("GPS");

end domain;
