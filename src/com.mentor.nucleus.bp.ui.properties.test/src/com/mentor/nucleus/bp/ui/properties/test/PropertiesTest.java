//=====================================================================
//
//File:      $RCSfile: PropertiesTest.java,v $
//Version:   $Revision: 1.9 $
//Modified:  $Date: 2013/01/10 23:15:24 $
//
//(c) Copyright 2004-2013 by Mentor Graphics Corp. All rights reserved.
//
//=====================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp. and is not for external distribution.
//=====================================================================
package com.mentor.nucleus.bp.ui.properties.test;

import java.io.File;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.test.common.BaseTest;

public class PropertiesTest extends BaseTest
{
    private static String m_logfile_path = "";

    public PropertiesTest(String name) {
        super("com.mentor.nucleus.bp.ui.properties.test", name);
    }

    protected void setUp() throws Exception {
        super.setUp();
        if (m_logfile_path == null || m_logfile_path.equals(""))
        {
            m_logfile_path = System.getProperty("LOGFILE_PATH");
        }
        assertNotNull( m_logfile_path );
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        IPath in_path = new Path(m_logfile_path);
        File in_fh = in_path.toFile();
        if ( in_fh.exists() )
        {
            fail( ".log file is not empty");
        }
    }

}
