package com.gdroid.cacheing.memory;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Can be used when no memory cache is enabled.
 * 
 * @author kboom
 */
public class NoMemoryCache<K, V> implements MemoryCacheable<K, V> {

	@Override
	public boolean put(Object key, Object value) {
		return true;
	}

	@Override
	public Object get(Object key) {
		return new Object();
	}

	@Override
	public void remove(Object key) {

	}

	@Override
	public Collection<K> keys() {
		return new ArrayList<K>();
	}

	@Override
	public void clear() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

}
