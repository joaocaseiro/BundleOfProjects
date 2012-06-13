package org.xproject.moony.utils;

import java.util.List;

public class TestUtils {
	/**
	 * Searches a List for an object 
	 * 
	 * @param object
	 * @param list
	 * @return position of the first occurrence of the object or -1 if not found
	 */
	public <T> int findObjectPosition(T object, List<T> list) {
		int i = 0;
		for(T obj : list) {
			if(object.equals(obj)) {
				return i;
			}
			i++;
		}
		return -1;
	}
}
