//=====================================================================
//
//File:      $RCSfile: IAllActivityModifier.java,v $
//Version:   $Revision: 1.9 $
//Modified:  $Date: 2013/01/10 22:54:11 $
//
//(c) Copyright 2005-2014 by Mentor Graphics Corp. All rights reserved.
//
//=====================================================================
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
//=====================================================================

package com.mentor.nucleus.bp.core.common;

public interface IAllActivityModifier {

    public static final int PARSE = 0;
    public static final int CLEAR = 1;
        
    static final String [] pmMessages = {
            "Parsing all activities...",
            "Clearing all activities..."
        };
        
    public void processAllActivities(int op);

    public void clearActionPlaceholder(Object o_input);
}
