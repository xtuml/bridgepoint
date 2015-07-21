package lib;

import java.io.DataOutputStream;
import java.util.StringTokenizer;

public class SetTime extends SignalData {
	
	public static final long serialVersionUID = 0;
	public int time;
	
	public SetTime() {
		super(SIGNAL_NO_SET_TIME);
	}
	
	public SetTime(int time) {
		super(SIGNAL_NO_SET_TIME);
		this.time = time;
	}
	
	@Override
	public void unserialize(StringTokenizer in) {
		time = Integer.parseInt(in.nextToken());
	}
	
	@Override
	public void serialize(DataOutputStream dos) {
		super.serialize(dos);
		SignalData.serializeInt(dos, time);
	}
}
