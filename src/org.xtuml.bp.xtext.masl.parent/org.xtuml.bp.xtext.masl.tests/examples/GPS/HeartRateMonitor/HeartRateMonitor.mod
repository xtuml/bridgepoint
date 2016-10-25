domain HeartRateMonitor is

  object HeartRateMonitor;

  public service registerListener ();
  public service unregisterListener ();

  terminator Unspecified is
    private service heartRateChanged ( heartRate : in integer );
  end terminator;
  pragma key_letter ("U");

  object HeartRateMonitor is
    recentHeartRate : integer;
    timer : timer;
    
    assigner start state idle();
    assigner state monitoring();
    assigner event timeout();
    assigner event registerListener();
    assigner event unregisterListener();

    assigner transition is
      idle (
        timeout => Ignore,
        registerListener => monitoring,
        unregisterListener => cannot_happen );
      monitoring (
        timeout => monitoring,
        registerListener => cannot_happen,
        unregisterListener => idle );
    end transition;
  end object;
  pragma key_letter ("HR");

end domain;
