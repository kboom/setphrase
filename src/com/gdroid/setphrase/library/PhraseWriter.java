package com.gdroid.setphrase.library;

import com.gdroid.setphrase.phrase.set.PhraseSetManager;
import com.gdroid.setphrase.phrase.target.TargetPhrase;
import com.gdroid.storage.object.ObjectStoreable;
import com.gdroid.storage.object.StorageOperationException;
import com.gdroid.utils.SLog;

/**
 * Tool for writing phrases into the library. It is assigned to a
 * {@link com.gdroid.sayit.phrase.PhraseSet} and works on it until released.
 * {@link #close() } must be called when this object is no longer needed.
 * 
 * @author kboom
 */
public abstract class PhraseWriter extends PhraseAccessor {

	static {
		SLog.register(PhraseWriter.class);
		SLog.setTag(PhraseWriter.class, "Phrase writer");
	}

	private ObjectStoreable<Integer, TargetPhrase> diskStore;

	PhraseWriter(PhraseSetManager manager, String path,
			ObjectStoreable<Integer, TargetPhrase> store)
			throws StorageOperationException {
		super(manager, path);
		diskStore = store;
		init();
	}

	@Override
	final boolean init() throws StorageOperationException {
		SLog.d(this, "Preparing disk store on file " + super.getPathAssigned());
		return diskStore.prepare(super.getPathAssigned());
	}

	/**
	 * Writes a phrase back to the library. Cannot be used to (re)assign random
	 * id. Use {@link #write(com.gdroid.sayit.phrase.TargetPhrase) } to write a
	 * new phrase.
	 * 
	 * @param id
	 *            valid id of this phrase in an assigned set
	 * @param phrase
	 *            phrase to update in the library
	 * @return {@code true}, if write was successful, false otherwise
	 */
	public boolean write(int id, TargetPhrase phrase) {
		super.printAction(this, "write", id);
		// don't let to allocate random ids
		super.validateID(id);

		try {
			diskStore.put(id, phrase);
			return true;
		} catch (StorageOperationException ex) {
			/*
			 * A caller cannot recover from error, a writer could but just\
			 * print this for now.
			 */
			SLog.e(this,
					String.format("Could not perform write operation: %s",
							ex.getLocalizedMessage()));
		}
		return false;
	}

	/**
	 * Writes a new phrase into the library.
	 * 
	 * @param phrase
	 *            phrase to save into library
	 * @return {@code true}, if write was successful, false otherwise
	 */
	public boolean write(TargetPhrase phrase) {
		int id = this.getSet().getQuestionCount();
		if (this.write(id, phrase)) {
			getSetManager().setQuestionCount(id + 1);
			onWriteAction();
			return true;
		} else
			return false;
	}

	/**
	 * SetManager-specific action performed on the set when a phrase is written
	 * into a set.
	 */
	abstract void onWriteAction();

}
