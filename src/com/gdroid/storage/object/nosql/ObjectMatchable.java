package com.gdroid.storage.object.nosql;

/**
 * 
 * @author kboom
 */
public interface ObjectMatchable<V> {
	boolean match(V v);
}
