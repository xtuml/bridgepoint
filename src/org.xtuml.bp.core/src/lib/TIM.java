package lib;

import java.util.ArrayList;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.ComponentInstance_c;
import org.xtuml.bp.core.PendingEvent_c;
import org.xtuml.bp.core.SystemModel_c;
import org.xtuml.bp.core.Timer_c;
import org.xtuml.bp.core.Util_c;
import org.xtuml.bp.core.common.ModelRoot;
import org.xtuml.bp.core.util.OoaofooaUtil;

//========================================================================
//
//File:      $RCSfile: TIM.java,v $
//Version:   $Revision: 1.22 $
//Modified:  $Date: 2012/01/23 21:27:42 $
//
//(c) Copyright 2006-2014 by Mentor Graphics Corp. All rights reserved.
//
//========================================================================
// Licensed under the Apache License, Version 2.0 (the "License"); you may not
// use this file except in compliance with the License.  You may obtain a copy
// of the License at
//
//       http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
// WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.   See the
// License for the specific language governing permissions and limitations under
// the License.
//========================================================================

public class TIM {

  private static Thread realThread = null;
  private static Thread simThread = null;

  private static final int VIEWERS_REFRESH_RATE_MILLISECS = 1000;

  private static boolean SIM_TIME = false;

  private static ArrayList<Timer_c> timersList = new ArrayList<Timer_c>();
  private static ArrayList<ComponentInstance_c> eelist = new ArrayList<ComponentInstance_c>();
  private static ArrayList<Boolean> eeIdleList = new ArrayList<Boolean>();
  private static HashMap<Timer_c ,ComponentInstance_c> timersMap = new HashMap<Timer_c, ComponentInstance_c >();

  private static Object idleBusyLock = new Object();
  private static Object timerLock = new Object();

  private static long timeAdjustmentOffset = 0;
  private static Instant systemEpoch;
  private static long systemEpochOffset = 0;
  private static boolean allIdle = true;
  private static boolean suspended = false;
  private static long simulatedTime;
  private static long suspendMark;
  private static long suspendTime = 0;
  private static boolean running = false;


  /*
   * Initializes the real-time verifier timer thread
   */
  private static void initializeRealTime() {
    running = true;
    realThread = new Thread(new Runnable() {
      public void run() {
        long remTime;
        while (running) {
          try {
            synchronized(timerLock) {
              if (!timersList.isEmpty() && !suspended){
                remTime = timersList.get(0).getExpiration()- getCurrentTime();
                if (remTime > VIEWERS_REFRESH_RATE_MILLISECS*1000)
                  timerLock.wait(VIEWERS_REFRESH_RATE_MILLISECS);
                else if (remTime > 0)
                  timerLock.wait(remTime/1000,(int)((remTime-(remTime/1000)*1000)*1000));
              }
              else {
                //Thread waiting until a timer gets added to the empty timers list
                timerLock.wait();
              }
            }

            if (!timersList.isEmpty() && !suspended){
              ComponentInstance_c ee = ComponentInstance_c.ComponentInstanceInstance(timersList.get(0).getModelRoot());
              if (ee != null){
                try {
                  ModelRoot.disableChangeNotification();
                  timersList.get(0).Tick();
                }
                catch (Exception e) {
                  CorePlugin.logError("Exception found during verifier timer execution.", e);
                }
                finally {
                  ModelRoot.enableChangeNotification();
                }
                //Refreshing viewers
                ee.Notify();
                // Signal single execution thread if needed.
                SystemModel_c system = OoaofooaUtil.getSystemForElement(ee);
                if (system != null) {
                  synchronized (system) {
                    system.notify();
                  }
                }

              }
            }
          } catch (InterruptedException e) {
            // Normal behavior
          }
        }

      }
    }, "Timer Clock");
    realThread.start();
  }

  /*
   * Initializes the simulated-time verifier timer thread
   */
  private static void initializeSimTime() {
    running = true;
    simThread = new Thread(new Runnable() {
      public void run() {
        while (running) {
          boolean eventsDelivered = false;
          if (allIdle && !timersList.isEmpty()) {
            eventsDelivered = TIM.performSimulatedTime();
            if (suspended || !eventsDelivered ) {
              synchronized (idleBusyLock) {
                try {
                  idleBusyLock.wait();
                } catch (InterruptedException e) {
                  // normal operation
                }
              }
            }
          }
        }
      }
    }, "Simulated Timer Clock");
    simThread.start();
  }

  public static void stopTimers() {
    running = false;
    try {
      if (simThread != null) {
        synchronized(idleBusyLock) {
          idleBusyLock.notify();
        }
        simThread.join();
        simThread = null;
      }
      if (realThread != null) {
        synchronized(timerLock) {
          timerLock.notify();
        }
        if(realThread != null) {
          realThread.join();
        }
        realThread = null;
      }
    } catch (InterruptedException e) {
      CorePlugin.logError("Unexpected Interrupted exception waiting for timers to stop", e);
    }
  }
  /**
   * This function advances the timers when using simulated time.
   *
   * @return true if time was advanced for a component instance and
   *         false if it was not.
   */
  public static boolean performSimulatedTime() {
    boolean eventDelivered = false;
    if (!suspended && !timersList.isEmpty()) {
      ComponentInstance_c ee = ComponentInstance_c
          .ComponentInstanceInstance(timersList.get(0).getModelRoot());
      if (ee != null) {
        try {
          ModelRoot.disableChangeNotification();
          long timeValue = timersList.get(0).getExpiration() - timeAdjustmentOffset;
          if ( simulatedTime < timeValue) {
        	  simulatedTime = timeValue;
          }
          timersList.get(0).Tick();
          eventDelivered = true;
        } catch (Exception e) {
          CorePlugin.logError(
              "Exception found during verifier timer execution.",
              e);
        } finally {
          ModelRoot.enableChangeNotification();
        }
      }
    }
    return eventDelivered;
  }

  public static void addExecEngines(ComponentInstance_c ee){
    eelist.add(ee);
    eeIdleList.add(false);
  }

  /**
   * When we initialize a launch we support SimTime or
   * Clock, but not both at the same time.
   *
   * @param simTime
   */
  public static void init(boolean simTime, boolean deterministic) {
    systemEpoch = Instant.EPOCH;
    Instant now = Instant.now();
    simulatedTime = TimeUnit.SECONDS.toMicros(now.getEpochSecond()) + TimeUnit.NANOSECONDS.toMicros(now.getNano());
    timeAdjustmentOffset = 0;
    systemEpochOffset = 0;
    allIdle = true;
    suspended = false;
    suspendMark = 0;
    suspendTime = 0;
    running = false;
    if (!deterministic) {  
      if (simTime) {
        if (simThread == null) {
          initializeSimTime();
        }
      } else {
        if (realThread == null)
          initializeRealTime();
      }
    }
  }

  public static void terminate(ComponentInstance_c ee){
    suspended = false;
    allIdle = false;
    suspendTime = 0;
    synchronized (idleBusyLock) {
      if (eelist.contains(ee)) {
        eeIdleList.remove(eelist.indexOf(ee));
        eelist.remove(ee);
      }
    }
    ArrayList<Timer_c> timersToBeRemoved = new ArrayList<Timer_c>();
    synchronized (timerLock) {
      for (Entry<Timer_c, ComponentInstance_c> entry : timersMap
          .entrySet()) {
        if (entry.getValue().equals(ee)) {
          Timer_c timTemp = entry.getKey();
          timersList.remove(timTemp);
          timersToBeRemoved.add(timTemp);
        }
      }
      for (int i=0 ; i< timersToBeRemoved.size(); i++){
        timersMap.remove(timersToBeRemoved.get(i));
      }
    }
  }


  public static void busyNotification(ComponentInstance_c ee){
    synchronized (idleBusyLock){
      for (int i = 0; i < eelist.size(); i++){
        if (eelist.get(i).equals(ee)){
          eeIdleList.set(i, false);
          break;
        }
      }
      allIdle = false;
    }
  }

  public static void idleNotification(ComponentInstance_c ee){
    synchronized (idleBusyLock){
      for (int i = 0; i < eelist.size(); i++){
        if (eelist.get(i).equals(ee)){
          eeIdleList.set(i, true);
          break;
        }
      }

      allIdle = true;
      for (int i = 0; i < eeIdleList.size(); i++){
        allIdle &= eeIdleList.get(i);
      }
      if (eeIdleList.isEmpty())
        allIdle = false;

      if (allIdle){
        idleBusyLock.notify();
      }
    }
  }

  public static boolean isSIM_TIME() {
    return SIM_TIME;
  }

  public static void setSIM_TIME(boolean sim_time) {
    SIM_TIME = sim_time;
  }

  public static void suspendTime(){
    suspended = true;
    suspendMark = System.nanoTime()/1000;
  }

  public static void resumeTime(){
    if (suspended){
      suspendTime += System.nanoTime()/1000 - suspendMark;
      suspended = false;
      if (isSIM_TIME()){
        synchronized(idleBusyLock){
          idleBusyLock.notify();
        }
      }
      else {
        synchronized(timerLock){
          timerLock.notify();
        }
      }
    }
  }

  public static void insertTimer(Timer_c timer , ComponentInstance_c compInst){
    synchronized (timerLock) {
      if (timersList.isEmpty()){
        timersList.add(timer);
        timersMap.put(timer, compInst);
        if (!isSIM_TIME())
          timerLock.notify();
        return;
      }
      for (int i = 0; i < timersList.size(); i++) {
        if (timersList.get(i) != null) {
          if(timer.getExpiration() < timersList.get(i).getExpiration()){
            timersList.add(i, timer);
            timersMap.put(timer, compInst);
            return;
          }
        }
      }
      timersList.add(timer);
      timersMap.put(timer, compInst);
    }
  }

  public static void removeTimer(Timer_c timer){
    synchronized (timerLock) {
      for (int i = 0; i < timersList.size(); i++) {
        if (timersList.get(i) != null) {
          if(timer.equals(timersList.get(i))){
            timersList.remove(i);
            timersMap.remove(timer);
            break;
          }
        }
      }
    }
  }

  public static long getCurrentTime(){
    return current_clock();
  }

  // ========================================================================
  // Bridge: timer_start
  // ========================================================================
  public static Object timer_start(Object event_inst, int microseconds) {
    if (event_inst instanceof PendingEvent_c) {
      PendingEvent_c pendingEvent = (PendingEvent_c) event_inst;
      // create object instance timer of I_TIM;
      Timer_c timer = new Timer_c(pendingEvent.getModelRoot());
      // timer.set(param.microseconds);
      timer.Set(microseconds);
      // relate timer to event_inst across R937;
      timer.relateAcrossR2940To(pendingEvent);
      timer.setRunning(true);

      insertTimer(timer,ComponentInstance_c.getOneI_EXEOnR2964(pendingEvent));

      return timer;
    }
    return null;
  }

  // ========================================================================
  // Bridge: timer_start_recurring
  // ========================================================================
  public static Object timer_start_recurring(Object event_inst,
      int microseconds) {
    Timer_c result = (Timer_c) timer_start(event_inst, microseconds);
    if (result != null) {
      result.setRecurring(true);
      return result;
    }
    return null;
  }

  // ========================================================================
  // Bridge: timer_cancel
  // ========================================================================
  public static boolean timer_cancel(Object timer_inst) {
    if ((timer_inst instanceof Timer_c) && timersList.contains(timer_inst)) {
      Timer_c timer = (Timer_c) timer_inst;
      removeTimer(timer);
      timer.Dispose();
      return true;
    }
    return false;
  }

  // ========================================================================
  // Bridge: timer_remaining_time
  // ========================================================================
  public static int timer_remaining_time(Object timer_inst) {
    int left = 0;
    if ((timer_inst instanceof Timer_c) && timersList.contains(timer_inst)) {
      Timer_c timer = (Timer_c) timer_inst;
      left = (int)(timer.getExpiration() - getCurrentTime());
    }
    return left;
  }

  // ========================================================================
  // Bridge: timer_reset_time
  // ========================================================================
  public static boolean timer_reset_time(int microseconds, Object timer_inst) {
    if ((timer_inst instanceof Timer_c) && timersList.contains(timer_inst)) {
      Timer_c timer = (Timer_c) timer_inst;
      ComponentInstance_c compInst = timersMap.get(timer);
      removeTimer(timer);
      timer.setExpiration(Util_c.Addinttolong(microseconds, getCurrentTime()));
      timer.setRunning(true);
      insertTimer(timer,compInst);
      return true; // successfully reset
    }
    return false;
  }

  // ========================================================================
  // Bridge: timer_add_time
  // ========================================================================
  public static boolean timer_add_time(int microseconds, Object timer_inst) {
    if ((timer_inst instanceof Timer_c) && timersList.contains(timer_inst)) {
      Timer_c timer = (Timer_c) timer_inst;
      ComponentInstance_c compInst = timersMap.get(timer);
      removeTimer(timer);
      long newExpiration = 0;
      newExpiration = timer.getExpiration() + microseconds;
      timer.setExpiration(newExpiration);
      insertTimer(timer,compInst);
      return true; // successfully added time
    }
    return false;
  }

  // ========================================================================
  // Bridge: timer_pause
  // ========================================================================
  public static void timer_pause(Object timer_inst) {
    if (timer_inst instanceof Timer_c) {
      Timer_c timer = (Timer_c) timer_inst;
      timer.setRunning(false);
    }
  }

  // ========================================================================
  // Bridge: timer_restart
  // ========================================================================
  public static void timer_restart(Object timer_inst) {
    if (timer_inst instanceof Timer_c) {
      Timer_c timer = (Timer_c) timer_inst;
      timer.setRunning(true);
    }
  }

  // ========================================================================
  // Bridge: get_second
  // ========================================================================
  public static int get_second(Object date) {
    int ret = -1;
    if (date instanceof Instant){
      LocalDateTime cal = LocalDateTime.ofInstant( ((Instant)date).plusSeconds(TimeUnit.MICROSECONDS.toSeconds(systemEpochOffset)), ZoneOffset.UTC );
      if ( cal.isSupported(ChronoField.SECOND_OF_MINUTE) ) {
        ret = cal.get( ChronoField.SECOND_OF_MINUTE );
      } else {
        // If the field is unsupported, then return as zero.
        ret = 0;
      }
    }
    return ret;
  }

  // ========================================================================
  // Bridge: get_minute
  // ========================================================================
  public static int get_minute(Object date) {
    int ret = -1;
    if (date instanceof Instant){
      LocalDateTime cal = LocalDateTime.ofInstant( ((Instant)date).plusSeconds(TimeUnit.MICROSECONDS.toSeconds(systemEpochOffset)), ZoneOffset.UTC );
      if ( cal.isSupported(ChronoField.MINUTE_OF_HOUR) ) {
        ret = cal.get(ChronoField.MINUTE_OF_HOUR);
      } else {
        // If the field is unsupported, then return as zero.
        ret = 0;
      }
    }
    return ret;
  }

  // ========================================================================
  // Bridge: get_hour
  // ========================================================================
  public static int get_hour(Object date) {
    int ret = -1;
    if (date instanceof Instant){
      LocalDateTime cal = LocalDateTime.ofInstant( ((Instant)date).plusSeconds(TimeUnit.MICROSECONDS.toSeconds(systemEpochOffset)), ZoneOffset.UTC );
      if ( cal.isSupported(ChronoField.HOUR_OF_DAY) ) {
        ret = cal.get(ChronoField.HOUR_OF_DAY);
      } else {
        // If the field is unsupported, then return as zero.
        ret = 0;
      }
    }
    return ret;
  }

  // ========================================================================
  // Bridge: get_day
  // ========================================================================
  public static int get_day(Object date) {
    int ret = -1;
    if (date instanceof Instant){
      LocalDateTime cal = LocalDateTime.ofInstant( ((Instant)date).plusSeconds(TimeUnit.MICROSECONDS.toSeconds(systemEpochOffset)), ZoneOffset.UTC );
      if ( cal.isSupported(ChronoField.DAY_OF_MONTH) ) {
        ret = cal.get(ChronoField.DAY_OF_MONTH);
      } else {
        // If the field is unsupported, then return as first day of month.
        ret = 1;
      }
    }
    return ret;
  }

  // ========================================================================
  // Bridge: get_month
  // ========================================================================
  public static int get_month(Object date) {
    int ret = -1;
    if (date instanceof Instant){
      LocalDateTime cal = LocalDateTime.ofInstant( ((Instant)date).plusSeconds(TimeUnit.MICROSECONDS.toSeconds(systemEpochOffset)), ZoneOffset.UTC );
      if ( cal.isSupported(ChronoField.MONTH_OF_YEAR) ) {
        ret = cal.get(ChronoField.MONTH_OF_YEAR);
      } else {
        // If the field is unsupported, then return as January.
        ret = 1;
      }
    }
    return ret;
  }

  // ========================================================================
  // Bridge: get_year
  // ========================================================================
  public static int get_year(Object date) {
    int ret = -1;
    if (date instanceof Instant){
      LocalDateTime cal = LocalDateTime.ofInstant( ((Instant)date).plusSeconds(TimeUnit.MICROSECONDS.toSeconds(systemEpochOffset)), ZoneOffset.UTC );
      if ( cal.isSupported(ChronoField.YEAR) ) {
        ret = cal.get(ChronoField.YEAR);
      } else {
        // If the field is unsupported, then return as zero.
        ret = 0;
      }
    }
    return ret;
  }

  // ========================================================================
  // Bridge: current_clock
  // ========================================================================
  public static long current_clock() {
    long timeValue = simulatedTime;
    if ( !isSIM_TIME() ) {
      Instant now = Instant.now();
      timeValue = TimeUnit.SECONDS.toMicros(now.getEpochSecond()) + TimeUnit.NANOSECONDS.toMicros(now.getNano());
    }
    timeValue -= systemEpochOffset;
    timeValue += timeAdjustmentOffset;
    return timeValue;
  }

  // ========================================================================
  // Bridge: create_date
  // ========================================================================
  public static Object create_date(int day, int hour, int min, int month,
    int sec, int year) {
    Instant cal = LocalDateTime.of(year, month, day, hour, min, sec).toInstant(ZoneOffset.UTC);
    cal.minusSeconds(TimeUnit.MICROSECONDS.toSeconds(systemEpochOffset));
    return (Object) cal;
  }

  // ========================================================================
  // Bridge: current_date
  // ========================================================================
  public static Object current_date() {
    Instant cal = Instant.ofEpochSecond(TimeUnit.MICROSECONDS.toSeconds(current_clock()));
    return (Object) cal;
  }

  // ========================================================================
  // Bridge: current_seconds
  // ========================================================================
   public static long current_seconds() {
     return TimeUnit.MICROSECONDS.toSeconds( current_clock() );
   }

  // ========================================================================
  // Bridge: set_epoch
  // ========================================================================
   public static void set_epoch(int day, int month, int year) {
     systemEpoch = LocalDate.of(year, month, day).atStartOfDay(ZoneOffset.UTC).toInstant();
     systemEpochOffset = TimeUnit.SECONDS.toMicros(systemEpoch.getEpochSecond() - Instant.EPOCH.getEpochSecond());
   }

  // ========================================================================
  // Bridge: time_of_day
  // ========================================================================
  public static long time_of_day(long timeval) {
    return timeval % (24 * 60 * 60 * 1000000);
  }

  // ========================================================================
  // Bridge: timestamp_format
  // ========================================================================
  public static String timestamp_format(long timestamp, String format) {
    long nanoVal = TimeUnit.MICROSECONDS.toNanos(timestamp + systemEpochOffset);
    DateTimeFormatter formatter;
    try {
      formatter = DateTimeFormatter.ofPattern(format);
    } catch ( IllegalArgumentException e ) {
      System.err.println("IllegalArgumentException: " + e.getMessage());
      return "Invalid format string!";
    }
    return LocalDateTime.ofInstant(Instant.ofEpochSecond(0, nanoVal), ZoneOffset.UTC).format(formatter);
  }

  // ========================================================================
  // Bridge: timestamp_to_string
  // ========================================================================
  public static String timestamp_to_string(long timestamp) {
    return Long.toString(timestamp);
  }
  
  // ========================================================================
  // Bridge: set_time
   // TIM::set_time( year, month, day, hour, minute, second, microsecond ) : timestamp
  // ========================================================================
  public static long set_time( int day, int hour, int microsecond, int minute, int month, int second, int year ) {
    Instant cal = LocalDateTime.of(year, month, day, hour, minute, second, (int)TimeUnit.MICROSECONDS.toNanos(microsecond)).toInstant(ZoneOffset.UTC);
    long setTime = TimeUnit.NANOSECONDS.toMicros( TimeUnit.SECONDS.toNanos( cal.getEpochSecond() ) + cal.getNano() );
    timeAdjustmentOffset = 0; // Clear out previous time setting.
    timeAdjustmentOffset = setTime - systemEpochOffset - current_clock();
    return current_clock();
  }

  // ========================================================================
  // Bridge: advance_time
  // ========================================================================
  public static long advance_time(long microseconds) {
    timeAdjustmentOffset += microseconds;
    return current_clock();
  }
  
}
