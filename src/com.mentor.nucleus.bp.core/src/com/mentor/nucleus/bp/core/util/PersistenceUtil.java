//=====================================================================
//
//File:      $RCSfile: PersistenceUtil.java,v $
//Version:   $Revision: 1.14 $
//Modified:  $Date: 2013/01/17 03:38:29 $
//
//(c) Copyright 2004-2014 by Mentor Graphics Corp. All rights reserved.
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

package com.mentor.nucleus.bp.core.util;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

/**
 * Utility methods related to this product's UI.
 */
public class PersistenceUtil
{
	public static String createRelativePath(IPath p_localPath, IPath p_remotePath) {
		// if already relative return
		if(!p_remotePath.toString().startsWith("/")) {
			// if the relative path had ./ in the path we must add it again
			if(!p_remotePath.toString().startsWith(".")) {
				return "./" + p_remotePath.toString();
			}
			return p_remotePath.toString();
		}
		// if the paths are identical (which should only happen in single file
		// export) then return the file part of the path
		if(p_localPath.equals(p_remotePath)) {
			return "../" + p_localPath.lastSegment().toString();
		}

		if(p_localPath.toString().indexOf(p_remotePath.toString()) != -1) 
			return p_remotePath.toString();
        
		int matchingSegCount = p_localPath.matchingFirstSegments(p_remotePath);

		int upCount = p_localPath.segmentCount() - matchingSegCount - 1;
		
		String relativePath = "";
		if(upCount == 0) {
			// The referred to element exists as a child of
			// the local element in the file hierarchy start
			// at the current location
			relativePath = "./";
		}
		for(int i = 0; i < upCount; i++) {
			relativePath = "../" + relativePath;
		}
		relativePath = relativePath
				+ p_remotePath.removeFirstSegments(matchingSegCount)
						.toString();
		return relativePath.replaceAll("//", "/");
    }
	public static String resolveRelativePath(IPath p_currentPath, IPath p_relativePath) {
		if (p_relativePath.segments().length != 0
                && !p_relativePath.segments()[0].equals("null")) {
            // Compute the full path
            String[] relativeSegments = p_relativePath.segments();
            int upCount;
            for (upCount = 0; upCount < relativeSegments.length; upCount++) {
                if (!relativeSegments[upCount].equals("..")) {
                    break;
                }
            }
			String[] currentSegments = p_currentPath.segments();
            IPath computedFullPath = new Path("");
            // We only want the lower part of the path, if there are relative
            // path navigations.
            // We remove one more segment because we do not want the filename of
			// the local path, we want to use the content path filename to reconstruct
            // the full path.
            int numLocalSegsToUse = currentSegments.length - upCount - 1;
            for (int i = 0; i < numLocalSegsToUse; i++) {
                computedFullPath = computedFullPath.append(currentSegments[i]);
            }
            int relativePathOffset = 0;
            if (upCount != 0) {
                relativePathOffset = upCount;
            } else {
                if (relativeSegments[0].equals(".")) {
                    relativePathOffset = 1;
                }
            }
            for (int i = relativePathOffset; i < relativeSegments.length; i++) {
                computedFullPath = computedFullPath.append(relativeSegments[i]);
            }
            return Path.SEPARATOR + computedFullPath.toString();

        } else {
            return Path.SEPARATOR + "";
        }
    }
	public static String getProjectNameFromPath(String relativePath) {
		String[] projectName = relativePath.split("/");
		if (projectName.length >1)
			return projectName[1];
		else
			return "";
	}
}
