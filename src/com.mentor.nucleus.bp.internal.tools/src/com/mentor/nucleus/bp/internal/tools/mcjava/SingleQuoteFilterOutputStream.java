package com.mentor.nucleus.bp.internal.tools.mcjava;

import java.io.FilterOutputStream;
import java.io.OutputStream;

public class SingleQuoteFilterOutputStream extends FilterOutputStream {

	int count = 0;

	public SingleQuoteFilterOutputStream(OutputStream arg0) {
		super(arg0);
	}

	public void write(int arg0) throws java.io.IOException {
		if (arg0 != '\'') {
		  if (count != 0) {
		    if (count == 3) {
			  super.write('\'');
			  count = 0; 
		    }
		    else {
		      while (count > 0) {
			    super.write('\'');
			    count--;
			  }
		    }
		  }
		  super.write(arg0);
		}
		else {
	      count++;
		}
	}
	  public void write(byte[] arg0) throws java.io.IOException {
		  for(byte arg: arg0) {
			  write(arg);
		  }
	  }
	  public void write(byte[] arg0, int arg1, int arg2) throws java.io.IOException {
		  int endIndex = arg1 + arg2;
		  for (int i = arg1; i < endIndex; i++) {
			  write(arg0[i]);
		  }
	  }
	  
	  public void flush() throws java.io.IOException {
		  while (count > 0) {
			  super.write('\'');
			  count --;
		  }
		  count = 0;
		  super.flush();
	  }
}
