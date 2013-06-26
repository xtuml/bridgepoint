package com.mentor.nucleus.bp.debug.ui.model;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;

import org.eclipse.debug.core.IStreamListener;
import org.eclipse.debug.core.model.IStreamMonitor;

public class BPStreamMonitor extends OutputStream implements IStreamMonitor {
    private ArrayList listeners = new ArrayList();
    
    String buffer = "";
    
	public BPStreamMonitor() {
		super();
	}

	public void addListener(IStreamListener listener) {
		listeners.add(listener);
	}

	public String getContents() {
		return buffer;
	}

	public void removeListener(IStreamListener listener) {
		listeners.remove(listener);
	}
	
	public void write(int arg) {
		buffer = buffer + String.valueOf((char)arg);
		if (buffer.endsWith("\n")) {
			flush();
		}
	}
	
	public void write (byte[] output) {
		buffer = buffer + output.toString();
		if (buffer.endsWith("\n")) {
			flush();
		}
	}
	
	public void write (byte[] output, int off, int len) {
		buffer = new String(output, off, len);
		notify(buffer);
		buffer = "";
	}
	
	public void flush() {
		notify(buffer);
		buffer = "";
	}

	public void close() {
		notify(buffer);
		buffer = "";
	}

	private void notify(String newMsg) {
		Iterator streamIt = listeners.iterator();
		while (streamIt.hasNext()) {
			IStreamListener li = (IStreamListener)streamIt.next();
			li.streamAppended(newMsg, this);
		}
	}
}
