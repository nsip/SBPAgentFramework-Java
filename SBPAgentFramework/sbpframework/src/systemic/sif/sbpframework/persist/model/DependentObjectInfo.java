/*
 * DependentObjectInfo.java
 * Created: 10/10/2011
 *
 * Copyright 2011 Systemic Pty Ltd
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License 
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 */

package systemic.sif.sbpframework.persist.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author Joerg Huber
 *
 */
public class DependentObjectInfo
{
	public Long id;
	public SIFObject parentObject;
	public Boolean listOfObjects;
	public String xpathToList;
	public Set<DependentKeyInfo> keyInfoList;

	public DependentObjectInfo() {}
	
	public DependentObjectInfo(Long id, SIFObject parentObject, Boolean listOfObjects, String xpathToList) 
	{
		setId(id);
		setParentObject(parentObject);
		setListOfObjects(listOfObjects);
		setXpathToList(xpathToList);
	}

	public Long getId()
    {
    	return this.id;
    }

	public void setId(Long id)
    {
    	this.id = id;
    }

	public SIFObject getParentObject()
    {
    	return this.parentObject;
    }

	public void setParentObject(SIFObject parentObject)
    {
    	this.parentObject = parentObject;
    }

	public Boolean getListOfObjects()
    {
    	return this.listOfObjects;
    }

	public void setListOfObjects(Boolean listOfObjects)
    {
    	this.listOfObjects = listOfObjects;
    }

	public String getXpathToList()
    {
    	return this.xpathToList;
    }

	public void setXpathToList(String xpathToList)
    {
    	this.xpathToList = xpathToList;
    }

	public Set<DependentKeyInfo> getKeyInfoList()
    {
    	return this.keyInfoList;
    }

	public void setKeyInfoList(Set<DependentKeyInfo> keyInfoList)
    {
    	this.keyInfoList = keyInfoList;
    }
	
	/**
	 * Returns the keys of this object as a list ordered by sortOrder. This is a true copy of the key 
	 * stored internally. Only Keys marked with 'indicatorField = false' are returned.
	 */
	public List<DependentKeyInfo> getOrderedKeyInfoList()
    {
		ArrayList<DependentKeyInfo> keyList = new ArrayList<DependentKeyInfo>(getKeyInfoList());
		
		// set correct order
		int indcatorIdx = -1;
		int currentIdx = 0;
		for (DependentKeyInfo key : getKeyInfoList())
		{
			if (!key.getIndicatorField())
			{
				keyList.set(key.getSortOrder() - 1, new DependentKeyInfo(null, key.getXpath(), key.getSortOrder(), false));
			}
			else
			{
				indcatorIdx = currentIdx;
			}
			currentIdx++;
		}
		
		// remove indicator field if one exists
		if (indcatorIdx != -1)
		{
			keyList.remove(indcatorIdx);
		}
		
		return keyList;
    }
	
	/**
	 * This method returns the xPath name of the indicator field of the dependent object. If there is no such field then null
	 * is returned. The returned object is a true copy of the indicator field. If the parameter includeValidIndicator is set to
	 * TRUE then the valid indicator list is returned as well otherwise it is not returned.
	 * 
	 * @return See description
	 */
	public DependentKeyInfo getIndicatorField(boolean includeValidIndicator)
	{
		for (DependentKeyInfo key : getKeyInfoList())
		{
			if (key.getIndicatorField())
			{
				DependentKeyInfo keyInfo = new DependentKeyInfo(null, key.getXpath(), key.getSortOrder(), true);
				if (includeValidIndicator)
				{
					keyInfo.setValidIndicatorList(key.getValidIndicatorList());
				}
				return keyInfo;
			}
		}
		return null; // none found
	}
	
	@Override
	public String toString()
	{
		return "id = " + id +
		"\nlistOfObjects = " + listOfObjects +
		"\nxpathToList = " + xpathToList +
		"\nparentObject = " + parentObject +
		"\nkeyInfoList = " + keyInfoList;
	}	
}
