package com.gdroid.storage.object.nosql;

import com.gdroid.storage.object.ObjectStoreable;

/**
 * 
 * @author kboom
 */
public interface MatchableObjectStorage<K extends ObjectMatchable<V>, V>
		extends ObjectStoreable<K, V> {

}
