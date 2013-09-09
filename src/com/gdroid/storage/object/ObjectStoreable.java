package com.gdroid.storage.object;

import java.util.Collection;

/**
 * 
 * @author kboom
 */
public interface ObjectStoreable<K, V> {

	boolean clean(String path);

	/**
	 * Opens a data source to work on. Usually it will create new source if one
	 * provided does not exist. If one is created, {@code null} is returned.
	 * 
	 * @param path
	 * @return
	 * @throws StorageOperationException
	 */
	boolean prepare(String path) throws StorageOperationException;

	/**
	 * Returns all keys available in a data source.
	 * 
	 * @return
	 * @throws StorageOperationException
	 */
	Collection<?> getKeys() throws StorageOperationException;

	/**
	 * Returns a value matching given key. If multiple matches is found, the
	 * first one is returned.
	 * 
	 * @param keys
	 * @return
	 * @throws StorageOperationException
	 */
	V get(K key) throws StorageOperationException;

	/**
	 * Returns all phrases matching given keys.
	 * 
	 * @param keys
	 * @return
	 * @throws StorageOperationException
	 */
	Collection<V> getAll(Collection<K> keys) throws StorageOperationException;

	/**
	 * Returns all phrases matching given keys. If no key is provided,
	 * everything is returned.
	 * 
	 * @param keys
	 * @return
	 * @throws StorageOperationException
	 */
	Collection<V> getAll(K... keys) throws StorageOperationException;

	/**
	 * Puts a value into data source with a given key. If a key already exists,
	 * a value is updated.
	 * 
	 * @param key
	 * @param value
	 * @throws StorageOperationException
	 */
	void put(K key, V value) throws StorageOperationException;

}
