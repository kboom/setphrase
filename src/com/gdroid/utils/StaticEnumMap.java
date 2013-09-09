/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdroid.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author kboom
 */
public class StaticEnumMap<V extends Enum<V>> {
	protected Map<Integer, V> map = new HashMap<Integer, V>();

	public StaticEnumMap(Class<V> valueType) {
		for (V v : valueType.getEnumConstants()) {
			map.put(v.ordinal(), v);
		}
	}

	public V get(int num) {
		return map.get(num);
	}
}
