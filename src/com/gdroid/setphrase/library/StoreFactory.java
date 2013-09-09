/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdroid.setphrase.library;

import com.gdroid.cacheing.memory.FIFOLimitedMemoryCache;
import com.gdroid.cacheing.memory.FreqLimitedMemoryCache;
import com.gdroid.cacheing.memory.MemoryCacheable;
import com.gdroid.cacheing.memory.NoMemoryCache;
import com.gdroid.setphrase.phrase.target.TargetPhrase;
import com.gdroid.storage.object.ObjectStoreable;
import com.gdroid.storage.object.mapped.MapObjectStorage;

/**
 * 
 * @author kboom
 */
public class StoreFactory {

	public static ObjectStoreable create(StoringMethods method) {
		switch (method) {
		case RANDOM_HEAVY:
			return new MapObjectStorage<Integer, TargetPhrase>();
		default:
			throw new IllegalArgumentException("Cache type not known!");
		}
	}

	public static MemoryCacheable create(CachingMethods method) {
		switch (method) {
		case DISABLED:
			return new NoMemoryCache();
		case FREQ:
			return new FreqLimitedMemoryCache<Integer, TargetPhrase>(1024) {

				@Override
				protected int getSize(TargetPhrase value) {
					return 64;
				}

			};
		case FIFO:
			return new FIFOLimitedMemoryCache<Integer, TargetPhrase>(1024) {

				@Override
				protected int getSize(TargetPhrase value) {
					return 64;
				}

			};
		default:
			throw new IllegalArgumentException("Cache type not known!");
		}
	}

}
