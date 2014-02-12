package com.mentor.nucleus.bp.core.common;
//========================================================================
//
//File:      $RCSfile: TraceLogger.java,v $
//Version:   $Revision: 1.11 $
//Modified:  $Date: 2013/01/10 22:54:10 $
//
//(c) Copyright 2005-2014 by Mentor Graphics Corp. All rights reserved.
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

import org.eclipse.core.runtime.Platform;

public class TraceLogger implements ILogger {
	private static final String[] subFlagNames =
		{ "functions", "operations", "states", "bridges", "consistency" };
	private static final int[] subFlagsWithFilter =
		{ FUNCTION, OPERATION, STATE_MACHINE, BRIDGE, CONSISTENCY };

	private boolean m_debug = false;
    private boolean [] m_subEnabled;
    private String [][] m_subFilter;
	public TraceLogger(String path)
	{
        String debug_option = Platform.getDebugOption(path);
        if ( debug_option != null )
        {
			m_debug = debug_option.equals("true");
    	    m_subEnabled = new boolean [subFlagsWithFilter.length];
        	m_subFilter = new String[subFlagsWithFilter.length][];
	        for (int i = 0; i < subFlagsWithFilter.length; ++i)
	        {
                String subFlag = Platform.getDebugOption(path + "/" + subFlagNames[i]);
                if ( subFlag != null )
                {
                    m_subEnabled[i] = subFlag.equals("true");
                }
                else
                {
                    m_subEnabled[i] = false;
                }
                String subFilter = Platform.getDebugOption(path + "/" + subFlagNames[i] + "/filter");
                if ( subFilter != null )
                {
        			m_subFilter[i] = subFilter.split(";");
                }
                else
                {
                    m_subFilter[i] = new String [1];
                    m_subFilter[i][0] = "";
                }
	        }
	    }
	}

	public String getThreadInfo() { 
		Thread curThread = Thread.currentThread();
		String threadInfo = "[" + curThread.getId() + ", " + curThread.getName() + "] ";
		return threadInfo;
	}
	
	public void println(int Type, String filterValue, String toPrint)
	{
		if (m_debug)
		{
			boolean filtered = false;
			for (int i = 0; i < subFlagsWithFilter.length; ++i)
			{
				if (Type == subFlagsWithFilter[i])
				{
					filtered = true;
					if (m_subEnabled[i] && (m_subFilter[i][0].equals("*")
						|| contains(m_subFilter[i], filterValue)))
					{
						toPrint = getThreadInfo() + toPrint;
						System.out.println(toPrint);
					}
				}
			}

			if (!filtered)
			{
				toPrint = getThreadInfo() + toPrint;
				System.out.println(toPrint);
			}
		}
	}
    public void println(Exception toPrint) {
        System.out.println(toPrint);
    }
    private boolean contains(String[] list, String candidate) {
        for (int i = 0; i < list.length; i++) {
            if (list[i].equals(candidate)) {
                return true;
            }
        }
        return false;
    }
}
