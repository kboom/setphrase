package com.gdroid.setphrase.library;

import com.gdroid.setphrase.phrase.set.PhraseSet;
import com.gdroid.setphrase.phrase.set.PhraseSetManager;
import com.gdroid.storage.object.StorageOperationException;
import com.gdroid.utils.SLog;

/**
 * Base for readers and writers.
 * 
 * @author kboom
 */
abstract class PhraseAccessor {

	static {
		SLog.register(PhraseAccessor.class);
		SLog.setTag(PhraseAccessor.class, "Phrase Accessor");
	}

	private final PhraseSetManager mSetManager;
	private final String mPath;

	public PhraseAccessor(PhraseSetManager manager, String path) {
		mSetManager = manager;
		mPath = path;
	}

	public String getPathAssigned() {
		return mPath;
	}

	protected PhraseSetManager getSetManager() {
		return mSetManager;
	}

	protected PhraseSet getSet() {
		return mSetManager.getPhraseSet();
	}

	public String printSetInformation() {
		PhraseSet set = mSetManager.getPhraseSet();
		return String.format("Working on set (%d: %s) with (%d) questions, "
				+ "stored in file (%s)", set.getID(), set.getTitle(),
				set.getQuestionCount(), getPathAssigned());
	}

	protected void printAction(PhraseAccessor c, String action, int id) {
		SLog.d(c, "Attempting to " + action + " phrase <" + id + "> in set <"
				+ mSetManager.getPhraseSet().getID() + ">");
	}

	void validateID(int id) {
		if (id > this.getSet().getQuestionCount())
			throw new IllegalArgumentException(String.format(
					"There is no phrase (%d) in set (%d: %s)", id, mSetManager
							.getPhraseSet().getID(), mSetManager.getPhraseSet()
							.getTitle()));
	}

	/**
	 * Must be called just after accessor is no longer needed.
	 */
	public abstract void close();

	abstract boolean init() throws StorageOperationException;
}
