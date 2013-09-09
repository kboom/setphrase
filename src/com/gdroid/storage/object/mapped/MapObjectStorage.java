package com.gdroid.storage.object.mapped;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.gdroid.storage.object.ObjectStoreable;
import com.gdroid.storage.object.StorageOperationException;
import com.gdroid.storage.object.StorageOperationException.Cause;

/**
 * A very simple implementation of an object storage using serialization and a
 * map to store values. A key must implement
 * 
 * @author kboom
 */
public class MapObjectStorage<K, V extends Serializable> implements
		ObjectStoreable<K, V> {

	File file;

	@Override
	public Set<K> getKeys() throws StorageOperationException {
		return getMap().keySet();
	}

	@Override
	public V get(K k) throws StorageOperationException {
		Map<K, V> map = getMap();
		return map.get(k);
	}

	@Override
	public void put(K k, V v) throws StorageOperationException {
		Map<K, V> map = getMap();
		map.put(k, v);
		putMap(map);
	}

	@Override
	public boolean prepare(String path) throws StorageOperationException {
		file = new File(path);
		boolean didExist = true;
		boolean wasValid = false;
		if (!file.exists()) {

			File directory = file.getParentFile();
			if (!directory.exists())
				directory.mkdirs();

			didExist = false;
			try {
				file.createNewFile();
			} catch (IOException ex) {
				throw new StorageOperationException(Cause.NOT_STOREABLE,
						"Could not crate new file.", ex);
			} finally {
				if (!file.exists()) {
					Logger.getLogger("Object storage").log(Level.SEVERE,
							"Could not create a new file!");
					return false;
				}
			}
		} else if (file.length() > 0) {
			InputStream is = null;
			try {
				is = new FileInputStream(file);
				InputStream ibuffer = new BufferedInputStream(is);
				ObjectInputStream istream = new ObjectInputStream(ibuffer);
				Logger.getLogger("Object storage").log(Level.INFO,
						"Validating file structure...");
				if (istream.readObject() instanceof Map) {
					wasValid = true;
					Logger.getLogger("Object storage").log(Level.INFO,
							"...file structure ok.");
				} else {
					file.delete();
					file.createNewFile();
				}
			} catch (ClassNotFoundException ex) {
				throw new StorageOperationException(Cause.NOT_READABLE,
						"Could not find out what structure does a file have.",
						ex);
			} catch (IOException ex) {
				throw new StorageOperationException(Cause.NOT_READABLE,
						"Could not read an object from a file.", ex);
			} finally {
				try {
					is.close();
				} catch (IOException ex) {
					Logger.getLogger(MapObjectStorage.class.getName()).log(
							Level.SEVERE, null, ex);
				}
			}
		}

		if (!didExist || !wasValid) {
			putMap(createMap());

			Logger.getLogger("Object storage").log(
					Level.INFO,
					String.format("Storage is prepared. "
							+ "Base size: %d bytes.", file.length()));

			return false;
		} else {
			return true;
		}
	}

	@Override
	public Collection<V> getAll(Collection<K> keys)
			throws StorageOperationException {
		Map<K, V> map = getMap();
		Collection result = new ArrayList();
		for (K k : keys) {
			if (map.containsKey(k))
				result.add(map.get(k));
		}
		return result;
	}

	@Override
	public Collection<V> getAll(K... keys) throws StorageOperationException {
		Map<K, V> map = getMap();
		if (keys.length == 0)
			return map.values();
		else {
			Collection<V> result = new ArrayList<V>();
			for (K k : keys) {
				if (map.containsKey(k))
					result.add(map.get(k));
			}
			return result;
		}
	}

	@SuppressWarnings("unchecked")
	private Map<K, V> getMap() throws StorageOperationException {
		if (file == null)
			return createMap();

		Map<K, V> map = null;
		InputStream is = null;
		ObjectInputStream istream = null;
		try {
			is = new FileInputStream(file);
			InputStream ibuffer = new BufferedInputStream(is);
			istream = new ObjectInputStream(ibuffer);
			map = (Map<K, V>) istream.readObject();
			Logger.getLogger("Object storage").log(Level.INFO,
					String.format("Read %d bytes.", file.length()));
		} catch (ClassNotFoundException ex) {
			throw new StorageOperationException(Cause.NOT_READABLE,
					"Could not match object's class to any known type.", ex);
		} catch (IOException ex) {
			throw new StorageOperationException(Cause.NOT_READABLE,
					"Could not read an object from a file.", ex);
		} finally {
			try {
				istream.close();
				is.close();
			} catch (IOException ex) {
				throw new IllegalStateException("Fatal: could not close "
						+ "stream!", ex);
			}
		}

		return map;
	}

	private void putMap(Map<K, V> map) throws StorageOperationException {

		OutputStream os = null;
		ObjectOutputStream output = null;
		try {
			long preSize = file.length();
			os = new FileOutputStream(file);
			OutputStream obuffer = new BufferedOutputStream(os);
			output = new ObjectOutputStream(obuffer);
			output.writeObject(map);
			output.flush();
			Logger.getLogger("Object storage")
					.log(Level.INFO,
							String.format("Written %d bytes.", file.length()
									- preSize));

		} catch (FileNotFoundException ex) {
			throw new StorageOperationException(Cause.NOT_FOUND, "Could not "
					+ "access a file. Did you remember to call "
					+ "\"open(String string)\" first?", ex);
		} catch (IOException ex) {
			throw new StorageOperationException(Cause.NOT_READABLE,
					"Could not read an object from a file.", ex);
		} finally {
			try {
				output.close();
				os.close();
			} catch (IOException ex) {
				throw new IllegalStateException("Fatal: could not close "
						+ "stream!", ex);
			}
		}
	}

	/**
	 * Override to switch to another map implementation.
	 * 
	 * @return
	 */
	protected Map<K, V> createMap() {
		return new HashMap<K, V>();
	}

	@Override
	public boolean clean(String path) {
		File f = new File(path);
		if (f.exists()) {
			f.delete();
			return true;
		} else
			return false;
	}

}
