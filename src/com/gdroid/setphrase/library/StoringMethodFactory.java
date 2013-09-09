package com.gdroid.setphrase.library;

import com.gdroid.setphrase.phrase.target.TargetPhrase;
import com.gdroid.storage.object.ObjectStoreable;
import com.gdroid.storage.object.mapped.MapObjectStorage;

abstract class StoringMethodFactory {

	public static ObjectStoreable create(StoringMethods method) {
		switch (method) {
		case RANDOM_HEAVY:
			return new MapObjectStorage<Integer, TargetPhrase>();
		default:
			throw new IllegalArgumentException("Cache type not known!");
		}
	}

}
