package com.gdroid.setphrase.library;

import com.gdroid.cacheing.memory.FreqLimitedMemoryCache;
import com.gdroid.cacheing.memory.MemoryCacheable;
import com.gdroid.cacheing.memory.NoMemoryCache;
import com.gdroid.setphrase.phrase.target.TargetPhrase;

abstract class CachingMethodFactory {

	public static MemoryCacheable create(CachingMethods method) {
		switch (method) {
		case DISABLED:
			return new NoMemoryCache();
		case FREQ:
			return new FreqLimitedMemoryCache<Integer, TargetPhrase>(1024) {

				@Override
				protected int getSize(TargetPhrase value) {
					return 1024;
				}

			};
		default:
			throw new IllegalArgumentException("Cache type not known!");
		}
	}
}
