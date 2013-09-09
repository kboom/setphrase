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
public class ReverseEnumMap<V extends Enum<V> & EnumConverter> {
	private Map<Integer, V> map = new HashMap<Integer, V>();

	public ReverseEnumMap(Class<V> valueType) {
		for (V v : valueType.getEnumConstants()) {
			map.put(v.convert(), v);
		}
	}

	public V get(int num) {
		return map.get(num);
	}
}
