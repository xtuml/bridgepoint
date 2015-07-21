package org.xtuml.qa.odometer.timer;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.CharBuffer;
import java.util.Timer;
import java.util.TimerTask;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.part.IPage;
import org.eclipse.ui.views.properties.PropertySheet;

import org.xtuml.qa.odometer.views.OdoView;

public class OdoTimer {

	public enum State { RUNNING, STOPPED, DISABLED, CREATED };
	
	private static State state = State.CREATED; 
	static Timer timer = new Timer();
	private static long millitime = 0;
	static long lastReadAt = 0;
	private static final String fileName = ".elapsed.time";

	static TimerTask task = null;
	
	private static void initialize() {
		char[] charTime = new char[50]; // This can only ever be a long
		CharBuffer time = CharBuffer.wrap(charTime);
		try {
		  String workSpLoc = OdoView.getEclipseVar("eclipse_home");
          FileReader reader = new FileReader(workSpLoc + fileName);
          int read = reader.read(time);
          if ( read == -1 ) {
        	  throw new FileNotFoundException("Odometer file exists but is not initialized.");
          }
          // If we get here, the value was read ok
          millitime = Long.parseLong(String.valueOf(charTime).substring(0,read));
          reader.close();
		}
		catch (FileNotFoundException fnf) {
			int foo = 0;
			// Expected the first time, do nothing
		}
		catch (IOException ioe) {
			System.out.println("Unexpected IO error reading elapsed time: " + ioe.toString());
		}
        state = State.STOPPED;
	}
	
	public static void kick(Object... sources) {
		for (int i=0; i < sources.length; i++) {
		  if (sources[i] instanceof IStructuredSelection) {
			  Object [] elements = ((IStructuredSelection)sources[i]).toArray();
			  for (int j = 0; j < elements.length; j++) {
				  OdoTimer.kick(elements[j]);
			  }
		  }
		  else if (sources[i] instanceof PropertySheet) {
			  IPage page = ((PropertySheet)sources[i]).getCurrentPage();
			  OdoTimer.kick(page);
		  }
		  else {
			String name = sources[i].getClass().getName();
			// We don't want to count interactions with odometer itself
			if (name.contains("org.xtuml.bp") && !name.contains("Odo")) {
		      if (getState() != State.DISABLED) {
		        OdoTimer.internalKick();
		      }
			}
		  }
		}
	}
	private static void internalKick() {
	  if (getState() == State.CREATED) {
		  initialize();
	  }
	  if (getState() == State.RUNNING) {
		if (task != null) {
		  task.cancel();
		  timer.purge();
		}
		long currentTime = System.currentTimeMillis();
		if (OdoTimer.lastReadAt > 0) {
		  OdoTimer.setMillitime(OdoTimer.getMillitime() + (currentTime - OdoTimer.lastReadAt));
		  OdoView.show(getMillitime());
		}
		OdoTimer.lastReadAt = currentTime;
		task = initializeTask();
		OdoTimer.timer.schedule(task, 30000); // Timer stops after 30 seconds inactivity
	  }
	  else { // State.STOPPED
		setState(State.RUNNING);
		task = initializeTask();
		OdoTimer.timer.schedule(task, 30000); // Timer stops after 30 seconds inactivity
	  }
      try {
    	  String time = Long.toString(millitime);
		  String workSpLoc = OdoView.getEclipseVar("eclipse_home");
          FileWriter writer = new FileWriter(workSpLoc + fileName);
          writer.write(time);
          writer.flush();
          writer.close();
      } catch (IOException e) {e.printStackTrace();}
	}

	private static TimerTask initializeTask() {
		return new TimerTask() {
			public void run() {
				setState(State.STOPPED);
				lastReadAt = 0;
			}
		};
	}
	private static void setMillitime(long millitime) {
		OdoTimer.millitime = millitime;
	}
	public static long getMillitime() {
		if (getState() == State.CREATED) {
			initialize();
		}
		return millitime;
	}

	public static void setState(State state) {
		if (OdoTimer.state == State.CREATED) {
			initialize();
		}
		OdoTimer.state = state;
	}

	public static State getState() {
		if (state == State.CREATED) {
			initialize();
		}
		return state;
	}
}
