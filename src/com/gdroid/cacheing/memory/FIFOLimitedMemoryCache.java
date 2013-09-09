package com.gdroid.cacheing.memory;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Limited {@link V object } cache. Provides {@link V object } storing. Size of
 * all stored bitmaps will not to exceed size limit. When cache reaches limit
 * size then cache clearing is processed by FIFO principle.
 */
public abstract class FIFOLimitedMemoryCache<K, V> extends
		LimitedMemoryCache<K, V> {

	private final List<V> queue = Collections
			.synchronizedList(new LinkedList<V>());

	public FIFOLimitedMemoryCache(int sizeLimit) {
		super(sizeLimit);
	}

	@Override
	public boolean put(K key, V value) {
		if (super.put(key, value)) {
			queue.add(value);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void remove(K key) {
		V value = super.get(key);
		if (value != null) {
			queue.remove(value);
		}
		super.remove(key);
	}

	@Override
	public void clear() {
		queue.clear();
		super.clear();
	}

	@Override
	protected V removeNext() {
		return queue.remove(0);
	}

	@Override
	protected Reference<V> createReference(V value) {
		return new WeakReference<V>(value);
	}
}