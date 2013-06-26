package lib;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.StringTokenizer;

/**
 * Base class for any signal that send from or to the GUI Watch
 */
public class SignalData implements Serializable, Runnable {
	
	public static final long serialVersionUID  = 0;
	
	// client to server signals
	public static final int SIGNAL_NO_SET_DATA = 0;
	public static final int SIGNAL_NO_SET_TIME = 1;
	
	//server to client signals
	public static final int SIGNAL_NO_START_STOP_PRESSED = 0;
	public static final int SIGNAL_NO_TARGET_PRESSED     = 1;
	public static final int SIGNAL_NO_LAP_RESET_PRESSED  = 2;
	public static final int SIGNAL_NO_LIGHT_PRESSED      = 3;
	public static final int SIGNAL_NO_MODE_PRESSED       = 4;
	
	public int signalNo;
	
	public SignalData(int signalNo) {
		this.signalNo = signalNo;
	}
	public void unserialize(StringTokenizer in) {}
	
	public void serialize(DataOutputStream dos) {
		serializeInt(dos, signalNo);
	}
	
	@Override
	public void run() {}
	
	protected static void serializeInt(DataOutputStream dos, int value) {
		char[] chars = (value + ",").toCharArray();
		for (char c : chars) {
			try {
				dos.write(c);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	protected static void serializeFloat(DataOutputStream dos, float value) {
		char[] chars = (value + ",").toCharArray();
		for (char c : chars) {
			try {
				dos.write(c);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
