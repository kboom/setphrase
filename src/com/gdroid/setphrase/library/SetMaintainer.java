package com.gdroid.setphrase.library;

import java.util.ArrayList;
import java.util.Collection;

import com.gdroid.setphrase.phrase.set.PhraseSet;
import com.gdroid.setphrase.phrase.set.PhraseSetManager;
import com.gdroid.setphrase.phrase.target.TargetPhrase;
import com.gdroid.storage.object.ObjectStoreable;
import com.gdroid.storage.object.StorageOperationException;
import com.gdroid.storage.object.mapped.MapObjectStorage;
import com.gdroid.utils.SLog;

/**
 * Used to create, modify or destroy sets.
 * 
 * @author kboom
 */
class SetMaintainer {

	static {
		SLog.register(SetMaintainer.class);
		SLog.setTag(SetMaintainer.class, "Set maintainer");
	}

	private static String rootDir;
	private static String rootFile;

	SetMaintainer() {
	}

	/**
	 * Sets home directory and a root path.
	 * 
	 * @param dir
	 */
	void setHomeDir(String dir, String root) {
		rootDir = dir;
		rootFile = rootDir + "/" + root;
	}

	/**
	 * Every method willing to obtain library directory should call this
	 * function.
	 * 
	 * @return path to current directory
	 */
	String getDir() {
		return rootDir;
	}

	public String getSetResource(PhraseSet set) {
		return rootDir + "/data/" + set.getID() + ".ps";
	}

	/**
	 * Creates new set and saves it on the list of available sets.
	 * 
	 * @param name
	 * @return
	 */
	PhraseSet create(PhraseSetManager manager, String name)
			throws StorageOperationException {

		manager.setID(getLastKnownID());
		manager.setTitle(name);

		PhraseSet result = manager.getPhraseSet();
		int id = result.getID();

		ObjectStoreable setStore = new MapObjectStorage<Integer, TargetPhrase>();
		setStore.clean(getSetResource(result));
		SLog.v(this, "Creating phrase set resource...");
		setStore.prepare(getSetResource(result));
		SLog.v(this, "Putting new set into store...");
		ObjectStoreable store = getRootStore();
		store.put(id, result);

		SLog.i(this, "New -empty- set has been created and added"
				+ " to set store.");

		return result;
	}

	void save(PhraseSet set) throws StorageOperationException {
		ObjectStoreable store = getRootStore();
		store.put(set.getID(), set);
	}

	PhraseSet load(int id) throws StorageOperationException {
		ObjectStoreable<Integer, PhraseSet> store = getRootStore();
		if (store.getKeys().contains(id)) {
			return store.get(id);
		} else {
			throw new IllegalArgumentException(String.format(
					"Set (%d) does not exist!", id));
		}
	}

	Collection<PhraseSet> load(Integer... ids) throws StorageOperationException {
		SLog.v(this, "Method: load");
		SLog.d(this, "Loading selective sets...");
		ObjectStoreable<Integer, PhraseSet> store = getRootStore();
		Collection<PhraseSet> result = store.getAll(ids);
		if (result == null)
			result = new ArrayList<PhraseSet>();
		return result;

	}

	Collection<Integer> getAvailable() throws StorageOperationException {
		ObjectStoreable store = getRootStore();
		Collection<Integer> result = store.getKeys();
		if (result == null)
			result = new ArrayList<Integer>();
		return result;

	}

	private int getLastKnownID() throws StorageOperationException {
		SLog.v(this, "Getting last known free id of the set...");
		int lastID = getAvailable().size();
		SLog.d(this, "First assignable id for a new set is: " + lastID);
		return lastID;
	}

	static ObjectStoreable<Integer, PhraseSet> getRootStore()
			throws StorageOperationException {
		SLog.v(SetMaintainer.class, "Getting phrase base reader on file <"
				+ rootFile + ">...");

		ObjectStoreable store = new MapObjectStorage<Integer, PhraseSet>();
		store.prepare(rootFile);
		SLog.v(SetMaintainer.class, "Store prepared.");
		return store;
	}

}
