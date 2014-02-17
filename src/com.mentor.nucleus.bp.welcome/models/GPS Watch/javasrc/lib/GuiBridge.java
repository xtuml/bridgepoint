//========================================================================
//
//(c) Copyright 2007-2014 by Mentor Graphics Corp. All rights reserved.
//
//========================================================================
 
package lib;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.StringTokenizer;

import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.PendingEvent_c;


public class GuiBridge {
	
	public static GuiConnection requester;
	
	public static PendingEvent_c setTargetPressedEvt;
	public static PendingEvent_c startStopPressedEvt;
	public static PendingEvent_c lapResetPressedEvt;
	public static PendingEvent_c lightPressedEvt;
	public static PendingEvent_c modePressedEvt;

	/**
	 * Feed the Java side of the Bridge with a pre-created event
	 * that can be fired at any point in time. As soon as it has
	 * been fired, these methods should be invoked again, to provide
	 * a replace the event that was fired. This will enable asynchronous
	 * events to be passed in the Java->model direction.
	 */
	public static void feedSetTargetPressedEvent(Object evt) {
		setTargetPressedEvt = (PendingEvent_c)evt;
	}
	public static void feedStartStopPressedEvent(Object evt) {
		startStopPressedEvt = (PendingEvent_c)evt;
	}
	public static void feedLapResetPressedEvent(Object evt) {
		lapResetPressedEvt = (PendingEvent_c)evt;
	}
	public static void feedLightPressedEvent(Object evt) {
		lightPressedEvt = (PendingEvent_c)evt;
	}
	public static void feedModePressedEvent(Object evt) {
		modePressedEvt = (PendingEvent_c)evt;
	}
	/**
	 * Call this method to establish a socket connection with the GUI
	 */
	public static void connect() {
	    requester = new GuiConnection();
	    requester.start();
	}
	/**
	 * Send message setTime to GUI
	 * @param time in seconds
	 */
	public static void setTime(int time) {
		if (requester != null) {
			requester.sendMessage(new SetTime(time));
		}
	}
	
	/**
	 * Send message setData to GUI. 
	 * @param value
	 * @param unit as specified in <code>SignalData</code>
	 */
	public static void setData(float value, int unit) {
		if (requester != null) {
			requester.sendMessage(new SetData(value, unit));
		}
	}
	
	/**
	 * When instantiated, the <code>GuiConnection</code> will connect to the
	 * Watch GUI. It will then sit in a loop waiting for signals from the GUI.
	 * The loop is terminated when the GUI is closed or some other client
	 * (another Verifier session or generated) steals the connection. There can
	 * only be one
	 */
	public static class GuiConnection extends Thread {
		Socket requestSocket;
		DataOutputStream out;
		BufferedReader in;
	 	SignalData data;

		public void run() {
			try {
				requestSocket = new Socket("localhost", 2003);
				CorePlugin.out.println("Connected to localhost on port 2003");
				out = new DataOutputStream(requestSocket.getOutputStream());
				out.flush();
				in = new BufferedReader(new InputStreamReader(requestSocket.getInputStream()));
				
				// The keep alive will force the underlying  
				// TCP/IP connection to handle heart beat probing,
				// which allow this thread to detect server side 
				// disconnects. If it isn't kept alive, readLine
				// will block forever and the thread will never be
				// terminated.
				requestSocket.setKeepAlive(true);
				do {
    				String msg = in.readLine();
    				if (msg == null) {
    					CorePlugin.out.println("Connection closed by server");
    					break;
    				}
					
					StringTokenizer tokenizer = new StringTokenizer(msg, ",");
					switch (Integer.parseInt(tokenizer.nextToken())) {
					case SignalData.SIGNAL_NO_TARGET_PRESSED:
			    		setTargetPressedEvt.Fire();
		    			CorePlugin.out.println("GUI: setTargetPressedEvent()");
						break;
					case SignalData.SIGNAL_NO_START_STOP_PRESSED:
			    		startStopPressedEvt.Fire();
		    			CorePlugin.out.println("GUI: startStopPressedEvent()");
						break;
					case SignalData.SIGNAL_NO_LAP_RESET_PRESSED:
			    		lapResetPressedEvt.Fire();
		    			CorePlugin.out.println("GUI: lapResetPressedEvent()");
						break;
					case SignalData.SIGNAL_NO_LIGHT_PRESSED:
			    		lightPressedEvt.Fire();
		    			CorePlugin.out.println("GUI: lightPressedEvent()");
						break;
					case SignalData.SIGNAL_NO_MODE_PRESSED:
			    		modePressedEvt.Fire();
		    			CorePlugin.out.println("GUI: modePressedEvent()");
						break;
					default:
						break;
					}
				} while (requestSocket.isConnected());
				CorePlugin.out.println("Client disconnected");
			} catch (UnknownHostException unknownHost) {
				CorePlugin.out.println("You are trying to connect to an unknown host.");
			} catch (IOException ioException) {
				CorePlugin.out.println("Connection lost.");
			} finally {
				try {
					requestSocket.close();
				} catch( IOException ioException) {
					ioException.printStackTrace();
				}
			}
		}
		
		private void sendMessage(SignalData data) {
			try {
				data.serialize(out);
				out.write('\n');
				out.flush();
			} catch(IOException ioException) {
				ioException.printStackTrace();
			}
		}
	}
}