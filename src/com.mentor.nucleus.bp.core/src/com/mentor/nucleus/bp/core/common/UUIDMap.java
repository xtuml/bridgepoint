//========================================================================
//
//File:      $RCSfile: UUIDMap.java,v $
//Version:   $Revision: 1.9 $
//Modified:  $Date: 2013/01/10 22:54:09 $
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
//
package com.mentor.nucleus.bp.core.common;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.UUID;
import java.util.Map.Entry;

import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.Domain_c;

public class UUIDMap {

	public static final String MODEL_VERSION_KEY = "model.version"; //$NON-NLS-1$
	
	String domainName;
	String revision;
	Map<String, UUID> map = new HashMap<String, UUID>();
	IDConvertor convertor = IDConvertor.getInstance();

	public UUIDMap(Domain_c domain) {
		if(domain != null){
			revision = extractRevision(domain);
			domainName = domain.getName();
		}
	}

	public UUID getUUID(NonRootModelElement modelElement) {
		String key = convertor.createKey(modelElement);
		if(key != null){
			return map.get(key);
		}
		return null;
	}
	
	public Set<Map.Entry<String, UUID>> getEntrySet(){
		return map.entrySet();
	}

	void createUUIDEntry(String key, UUID id) {
		map.put(key, id);
	}
	
	public void clear(){
		map.clear();
	}

	public String getModelRevision() {
		return revision;
	}

	public void setModelRevision(String aRevision) {
		revision = aRevision;
	}

	public void load(InputStream in) throws IOException{
		revision = null;
		Properties p = new Properties();
		p.load(in);
		
		for (Entry entry : p.entrySet()) {
			String key = (String)entry.getKey();
			if(revision == null && MODEL_VERSION_KEY.equals(key)){
				revision = (String)entry.getValue();
			}else{
				map.put(key, UUID.fromString((String)entry.getValue()));
			}
		}
	}

	public void store(OutputStream out) throws IOException{
		Properties p = new Properties();
		p.put(MODEL_VERSION_KEY, revision);
		for (Entry<String, UUID> entry : map.entrySet()) {
			p.setProperty(entry.getKey(), entry.getValue().toString());
		}
		p.store(out, "Map file for domain:" + domainName + " rev:" + getModelRevision());  //$NON-NLS-1$ //$NON-NLS-2$
	}
	
	private static String revisionStartRule = null;
	private static String revisionEndRule = null;
	static {
		URL url = CorePlugin.getDefault().getBundle().getEntry("plugin.properties"); //$NON-NLS-1$
		if(url != null){
			Properties properties = new Properties();
			try {
				properties.load(url.openStream());
				revisionStartRule = properties.getProperty("domain.revision.startrule"); //$NON-NLS-1$
				if(revisionStartRule != null){
					revisionStartRule = revisionStartRule.trim();
				}
				revisionEndRule = properties.getProperty("domain.revision.endrule"); //$NON-NLS-1$
				if(revisionEndRule != null){
					revisionEndRule = revisionEndRule.trim();
				}
			} catch (IOException e) {
				CorePlugin.logError("Could load revision extraction properties", e); //$NON-NLS-1$
			}
		}
	}
	
	private String extractRevision(Domain_c domain){
		if(revisionStartRule != null){
			String desc = domain.getDescrip();
			int index = desc.indexOf(revisionStartRule);
			if(index >= 0){
				String revision = desc.substring(desc.indexOf(revisionStartRule) + revisionStartRule.length());
				revision = revision.substring(0, revision.indexOf(revisionEndRule));
				return revision.trim();
			}
		}
		return "";  //$NON-NLS-1$
	}
}
