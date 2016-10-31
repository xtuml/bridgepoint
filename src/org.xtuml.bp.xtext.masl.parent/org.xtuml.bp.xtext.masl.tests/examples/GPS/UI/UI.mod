domain UI is

  object TestCase;
  object UI;

  public type Unit is enum ( Blank, Down, Flat, Up );
  public type Indicator is enum ( km, meters, minPerKm, kmPerHour, miles, yards, feet, minPerMile, mph, bpm, laps );

  private service createGoals_1 ();
  private service init ();
  private service RunTestCase ();
  private service sendLapResetPressed ();
  private service sendLightPressed ();
  private service sendModePressed ();
  private service sendStartStopPressed ();
  private service sendTargetPressed ();

  public service setData ( unit : in Unit, value : in real );
  public service setTime ( time : in integer );
  public service setIndicator ( indicator : in Indicator );
  public service startTest ();

  terminator Tracking is
    private service setTargetPressed ();
    private service startStopPressed ();
    private service lapResetPressed ();
    private service lightPressed ();
    private service modePressed ();
    private service newGoalSpec ( spanType : in Tracking::GoalSpan,
                                 criteriaType : in Tracking::GoalCriteria,
                                 span : in real,
                                 maximum : in real,
                                 minimum : in real,
                                 sequenceNumber : in integer );
  end terminator;
  pragma key_letter ("T");

  object TestCase is
    iterations : integer;
    public service execute ();

    state pressStartStop();
    state testCaseFinished();
    event tcdelay();
    event tcstart( iterations : in integer );
    event tcfinish();
    transition is
      pressStartStop (
        tcdelay => pressStartStop,
        tcstart => cannot_happen,
        tcfinish => testCaseFinished ); 
      testCaseFinished (
        tcdelay => cannot_happen,
        tcstart => cannot_happen,
        tcfinish => cannot_happen ); 
    end transition;
  end object;
  pragma key_letter ("TC");

  object UI is
    public service connect ();
    assigner start state running();
    assigner event setTargetPressed();
    assigner event startStopPressed();
    assigner event lapResetPressed();
    assigner event lightPressed();
    assigner event modePressed();
    assigner transition is
      running (
        setTargetPressed => running,
        startStopPressed => running,
        lapResetPressed => running,
        lightPressed => running,
        modePressed => running ); 
    end transition;
  end object;
  pragma key_letter ("UI");

end domain;
