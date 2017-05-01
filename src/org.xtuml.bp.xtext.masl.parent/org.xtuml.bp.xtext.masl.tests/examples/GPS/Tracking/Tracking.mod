domain Tracking is

  object Achievement;
  object Display;
  object HeartRateSample;
  object LapMarker;
  object TrackLog;
  object TrackPoint;
  object WorkoutSession;
  object WorkoutTimer;
  object Goal;
  object GoalSpec;

  public type GoalCriteria is enum ( HeartRate, Pace );
  public type GoalDisposition is enum ( Achieving, Increase, Decrease );
  public type GoalSpan is enum ( Distance, Time );

  private service GoalTest_1 ();
  private service Initialize ();

  public service heartRateChanged ( heartRate : in integer );
  public service setTargetPressed ();
  public service startStopPressed ();
  public service lapResetPressed ();
  public service lightPressed ();
  public service modePressed ();
  public service newGoalSpec ( spanType : in GoalSpan,
                               criteriaType : in GoalCriteria,
                               span : in real,
                               maximum : in real,
                               minimum : in real,
                               sequenceNumber : in integer );

  terminator Location is
    private function getDistance ( toLong : in real,
                                   toLat : in real,
                                   fromLong : in real,
                                   fromLat : in real ) return real;
    private service getLocation ( longitude : in real,
                                  latitude : in real );
    private service registerListener ();
    private service unregisterListener ();
  end terminator;
  pragma key_letter ("LOC");

  terminator UI is
    private service setData ( unit : in UI::Unit,
                              value : in real );
    private service setIndicator ( indicator : in UI::Indicator );
    private service setTime ( time : in integer );
    private service startTest ();
  end terminator;
  pragma key_letter ("UI");
  
  terminator HeartRateMonitor is
    private service registerListener ();
    private service unregisterListener ();
  end terminator;
  pragma key_letter ("HR");

  relationship R1 is TrackPoint conditionally is_start_of one TrackLog,
                     TrackLog conditionally has_first one TrackPoint;

  relationship R2 is TrackPoint conditionally precedes one TrackPoint,
                     TrackPoint conditionally follows one TrackPoint;

  relationship R3 is TrackLog conditionally has_last one TrackPoint,
                     TrackPoint conditionally is_last_for one TrackLog;

  relationship R5 is TrackLog conditionally has_laps_defined_by many LapMarker,
                     LapMarker unconditionally marks_end_of_lap_in one TrackLog;

  relationship R7 is Display unconditionally indicates_current_status_of one WorkoutSession,
                     WorkoutSession unconditionally current_status_indicated_on one Display;

  relationship R8 is WorkoutTimer unconditionally acts_as_the_stopwatch_for one WorkoutSession,
                     WorkoutSession unconditionally is_timed_by one WorkoutTimer;

  relationship R4 is TrackLog unconditionally represents_path_for one WorkoutSession,
                     WorkoutSession unconditionally captures_path_in one TrackLog;

  relationship R6 is WorkoutSession conditionally tracks_heart_rate_over_time_as many HeartRateSample,
                     HeartRateSample unconditionally was_collected_during one WorkoutSession;

  relationship R9 is Goal unconditionally specified_by one GoalSpec,
                     GoalSpec conditionally specifies many Goal;

  relationship R10 is GoalSpec unconditionally included_in one WorkoutSession,
                      WorkoutSession conditionally includes many GoalSpec;

  relationship R11 is Goal conditionally is_currently_executing_within one WorkoutSession,
                      WorkoutSession conditionally is_currently_executing one Goal;

  relationship R12 is Achievement unconditionally specifies_achievement_of one Goal,
                      Goal conditionally has_recorded many Achievement;

  relationship R13 is WorkoutSession conditionally has_executed many Goal,
                      Goal unconditionally was_executed_within one WorkoutSession;
    
  relationship R14 is Achievement conditionally is_open_for one Goal,
                      Goal conditionally has_open one Achievement;

  object Achievement is
    startTime : integer;
    endTime : integer;
    public instance service close ();
  end object;
  pragma key_letter ("A");

  object Display is
    public function goalDispositionIndicator () return UI::Indicator;

    state displayDistance();
    state displaySpeed();
    state displayPace();
    state displayHeartRate();
    state displayLapCount();
    event modeChange();
    event refresh();

    transition is
      displayDistance (
        modeChange => displaySpeed,
        refresh => displayDistance );
      displaySpeed (
        modeChange => displayPace,
        refresh => displaySpeed );
      displayPace (
        modeChange => displayHeartRate,
        refresh => displayPace );
      displayHeartRate (
        modeChange => displayLapCount,
        refresh => displayHeartRate );
      displayLapCount (
        modeChange => displayDistance,
        refresh => displayLapCount );
    end transition;
  end object;
  pragma key_letter ("D");

  object HeartRateSample is
    heartRate : integer;
    time : integer;
  end object;
  pragma key_letter ("HRS");

  object LapMarker is
    lapTime : integer;
  end object;
  pragma key_letter ("LM");
  
  object TrackLog is
    public instance service addTrackPoint ();
    public instance service clearTrackPoints ();
    public instance service addLapMarker ();
    public instance service clearLapMarkers ();
    public instance service updateDisplay ();
  end object;
  pragma key_letter ("TL");

  object TrackPoint is
    time : integer;
    longitude : real;
    latitude : real;
  end object;
  pragma key_letter ("TP");

  object WorkoutSession is
    startDate : preferred string;
    startTime : preferred timestamp;
    currentSpeed : real;
    currentPace : real;
    currentHeartRate : integer;
    accumulatedDistance : real;
    public instance service addHeartRateSample ( heartRate : in integer );
    public instance service clearHeartRateSamples ();
    public instance service initialize ();
    public service sessioncreate ();
    public instance service reset ();
  end object;
  pragma key_letter ("WS");

  object WorkoutTimer is
    time : integer;
    timer : timer;
    public instance service activate ();
    public instance service deactivate ();
    public instance service initialize ();

    state stopped();
    state running();
    state paused();
    event startStopPressed();
    event lapResetPressed();
    event tick();

    transition is
      stopped (
        startStopPressed => running,
        lapResetPressed => Ignore,
        tick => Ignore );
      running (
        startStopPressed => paused,
        lapResetPressed => running,
        tick => running );
      paused (
        startStopPressed => running,
        lapResetPressed => stopped,
        tick => Ignore );
    end transition;
  end object;
  pragma key_letter ("WT");

  object Goal is
    disposition : GoalDisposition;
    goalstart : real;
    ID : unique integer;
    evaluationTimer : timer;

    public service goalcreate ( sequenceNumber : in integer );
    public instance service calculateStart ();
    public instance function evaluateAchievement () return GoalDisposition;
    public instance service evaluateCompletion ();
    public  service nextGoal ();

    state Executing();
    state Completed();
    state Paused();
    event Completed();
    event Evaluate();
    event Pause();

    transition is
      Executing (
        Completed => Completed,
        Evaluate => Executing,
        Pause => Paused );
      Completed (
        Completed => Ignore,
        Evaluate => Ignore,
        Pause => cannot_happen );
      Paused (
        Completed => Completed,
        Evaluate => Executing,
        Pause => cannot_happen );
    end transition;
  end object;
  pragma key_letter ("G");

  object GoalSpec is
    minimum : real;
    maximum : real;
    span : real;
    criteriaType : GoalCriteria;
    spanType : GoalSpan;
    sequenceNumber : preferred integer;
  end object;
  pragma key_letter ("GS");

end domain;
