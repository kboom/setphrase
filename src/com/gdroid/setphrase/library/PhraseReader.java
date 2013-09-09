package com.gdroid.setphrase.library;

import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.gdroid.cacheing.memory.MemoryCacheable;
import com.gdroid.setphrase.phrase.set.PhraseSetManager;
import com.gdroid.setphrase.phrase.target.TargetPhrase;
import com.gdroid.storage.object.ObjectStoreable;
import com.gdroid.storage.object.StorageOperationException;
import com.gdroid.utils.SLog;

/**
 * Tool for reading phrases from the library. It is assigned to a
 * {@link com.gdroid.sayit.phrase.PhraseSet} and works on it until released.
 * {@link #close() } must be called when this object is no longer needed.
 * 
 * @author kboom
 */
public abstract class PhraseReader extends PhraseAccessor {

	static {
		SLog.register(PhraseReader.class);
		SLog.setTag(PhraseReader.class, "Phrase reader");
	}

	private MemoryCacheable<Integer, TargetPhrase> memoryCache;
	private ObjectStoreable<Integer, TargetPhrase> diskStore;

	PhraseReader(PhraseSetManager manager, String path, ObjectStoreable store,
			MemoryCacheable cache) throws StorageOperationException {
		super(manager, path);
		this.diskStore = store;
		this.memoryCache = cache;
		init();
	}

	@Override
	final boolean init() throws StorageOperationException {
		SLog.d(this, "Preparing disk store on file " + super.getPathAssigned());
		return diskStore.prepare(super.getPathAssigned());
	}

	/**
	 * Reads a single phrase from the set matching provided matcher. If more
	 * than one object was found, first of them is returned.
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public TargetPhrase read(Integer key) throws Exception {
		super.printAction(this, "read", key);
		super.validateID(key);

		TargetPhrase result;
		if (memoryCache.keys().contains(key))
			result = memoryCache.get(key);
		else {
			result = diskStore.get(key);
			memoryCache.put(result.getID(), result);
		}

		onReadAction();
		return result;
	}

	/**
	 * Reads multiple phrases from the set. Does not use caching.
	 * 
	 * @param ids
	 *            ids of the phrases to read
	 * @return Collection of phrases matching those ids
	 */
	public Collection<TargetPhrase> read(Integer... keys) {
		Collection<TargetPhrase> result = new ArrayList<TargetPhrase>();

		ArrayList<Integer> toLoad = new ArrayList<Integer>();
		for (int id : keys) {
			super.printAction(this, "read", id);
			super.validateID(id);

			if (memoryCache.keys().contains(id))
				result.add(memoryCache.get(id));
			else {
				toLoad.add(id);
			}
		}
		Collection<TargetPhrase> rest;
		try {
			rest = diskStore.getAll(toLoad);
			int i = 0;
			for (TargetPhrase p : rest) {
				memoryCache.put(toLoad.get(i), p);
			}
			result.addAll(rest);

			return result;
		} catch (StorageOperationException ex) {
			Logger.getLogger(PhraseReader.class.getName()).log(Level.SEVERE,
					null, ex);
		}
		return result;
	}

	/**
	 * Not mandatory action performed when a reader reads a target phrase from
	 * the set.
	 */
	abstract void onReadAction();

}
