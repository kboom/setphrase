package com.gdroid.setphrase.library;

import com.gdroid.setphrase.phrase.set.PhraseSet;
import com.gdroid.storage.object.ObjectStoreable;
import com.gdroid.storage.object.mapped.MapObjectStorage;

/**
 * 
 * @author kboom
 */
public class RootStoringMethodFactory {
	public static ObjectStoreable create(StoringMethods method) {
		switch (method) {
		case RANDOM_HEAVY:
			return new MapObjectStorage<Integer, PhraseSet>();
		default:
			throw new IllegalArgumentException("Cache type not known!");
		}
	}
}
