package com.gdroid.storage.object.nosql;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.config.EmbeddedConfiguration;
import com.db4o.query.Predicate;
import com.gdroid.storage.object.StorageOperationException;

/**
 * 
 * @author kboom
 */
public class DB4oObjectStorage<K extends ObjectMatchable<V>, V> implements
		MatchableObjectStorage<K, V> {

	private String source;
	private EmbeddedConfiguration config;

	public EmbeddedConfiguration getConfiguration() {
		if (config == null) {
			config = Db4oEmbedded.newConfiguration();
		}
		return config;
	}

	public void setConfiguration(EmbeddedConfiguration config) {
		this.config = config;
	}

	@Override
	public Collection<Integer> getKeys() throws StorageOperationException {
		ObjectContainer db = Db4oEmbedded.openFile(getConfiguration(), source);

		final Collection<Integer> result = new ArrayList<Integer>();

		try {
			db.query(new Predicate<V>() {
				@Override
				public boolean match(V v) {
					result.add(v.hashCode());
					return false;
				}
			});

		} finally {
			db.close();
		}

		return result;
	}

	@Override
	public V get(K key) throws StorageOperationException {
		List<V> items = getAll(key);
		if (items.size() > 0)
			return items.get(0);
		else
			return null;
	}

	@Override
	public Collection<V> getAll(Collection<K> keys)
			throws StorageOperationException {
		return getAll((K[]) keys.toArray());
	}

	@Override
	public List<V> getAll(final K... keys) throws StorageOperationException {
		ObjectContainer db = Db4oEmbedded.openFile(getConfiguration(), source);

		List<V> result = null;
		try {
			result = db.query(new Predicate<V>() {
				@Override
				public boolean match(V v) {
					for (K key : keys) {
						if (key.match(v))
							return true;
					}
					return false;
				}
			});

		} finally {
			db.close();
		}
		if (result == null)
			return createList();
		else
			return result;
	}

	@Override
	public void put(final K key, V value) throws StorageOperationException {
		ObjectContainer db = Db4oEmbedded.openFile(getConfiguration(), source);
		ObjectSet<V> set = db.query(new Predicate<V>() {
			@Override
			public boolean match(V v) {
				if (key.match(v))
					return true;
				else
					return false;
			}
		});
		db.delete(set);
		db.store(value);
		db.close();
	}

	@Override
	public boolean prepare(String path) throws StorageOperationException {
		source = path;
		File file = new File(source);
		if (file.exists())
			return false;
		else
			return true;
	}

	private List<V> createList() {
		return new ArrayList<V>();
	}

	@Override
	public boolean clean(String path) {
		File file = new File(path);
		if (file.exists()) {
			file.delete();
			return true;
		} else
			return false;
	}

}
