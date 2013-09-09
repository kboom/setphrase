package com.gdroid.setphrase.library;

import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.gdroid.cacheing.memory.MemoryCacheable;
import com.gdroid.setphrase.phrase.PhraseFactory;
import com.gdroid.setphrase.phrase.set.PhraseSet;
import com.gdroid.setphrase.phrase.set.PhraseSetManager;
import com.gdroid.storage.object.ObjectStoreable;
import com.gdroid.storage.object.StorageOperationException;
import com.gdroid.system.System;
import com.gdroid.utils.SLog;

/**
 * A library responsible for maintaining all
 * {@link com.gdroid.sayit.phrase.TargetPhrase } saved. Used also to create, load
 * or save entire sets.
 * 
 * @author kboom
 */
public class LibrarySystem extends System {

	static {
		SLog.register(LibrarySystem.class);
		SLog.setTag(LibrarySystem.class, "Library system");
	}

	private int accessorReleasedCount = 0;
	private PhraseFactory phraseFactory;
	private SetMaintainer setMaintainer;

	private CachingMethods defaultCachingMethod;
	private StoringMethods defaultStoringMethod;

	/*
	 * ====================================================================
	 * Constructors
	 * ====================================================================
	 */

	/**
	 * Creates library system. Expects environment to be prepared, that is an
	 * root file, even if empty. Use {@link #checkEnvironmentIntegrity() } first,
	 * to avoid throwing exception. If test is not passed, call
	 * {@link #setUpNewEnvironment() }.
	 * {@link #setMountingPoint(java.lang.String, java.lang.String) } must be
	 * called before use!
	 * 
	 * @param dir
	 */
	public LibrarySystem(String rootDir, String rootFile) {
		setMaintainer = new SetMaintainer();
		moveMountingPoint(rootDir, rootFile);
		SLog.i(LibrarySystem.class,
				String.format("Library set. Root directory = (%s), "
						+ "root file = (%s).", rootDir, rootFile));

		defaultStoringMethod = StoringMethods.RANDOM_HEAVY;
		defaultCachingMethod = CachingMethods.FIFO;
	}

	/*
	 * ====================================================================
	 * Accessible methods
	 * ====================================================================
	 */

	public final void moveMountingPoint(String rootDir, String root) {
		if (!checkEnvironmentIntegrity(rootDir, root))
			throw new IllegalStateException(String.format(
					"Environment %s : %s is not valid.", rootDir, root));

		setMaintainer.setHomeDir(rootDir, root);
	}

	public void setPhraseFactory(PhraseFactory factory) {
		phraseFactory = factory;
	}

	public void setDefaultStoringMethod(StoringMethods method) {
		defaultStoringMethod = method;
	}

	public void setDefaultCachingMethod(CachingMethods method) {
		defaultCachingMethod = method;
	}

	/**
	 * Checks if an environment does exist and is valid. This
	 * <strong>should</strong> be called before instantiating {@link #this}.
	 * 
	 * @param rootDir
	 *            absolute path to root directory
	 * @param root
	 *            relative path to root file from rootDir perspective
	 * @return {@code true } if environment is valid, {@code false } otherwise.
	 */
	public static boolean checkEnvironmentIntegrity(String rootDir, String root) {
		SLog.v(LibrarySystem.class, "Checking environment integrity...");
		ObjectStoreable<Integer, PhraseSet> storage = StoreFactory
				.create(StoringMethods.RANDOM_HEAVY);
		boolean result = false;
		try {
			result = storage.prepare(getRootPath(rootDir, root));
		} catch (StorageOperationException ex) {
			Logger.getLogger(LibrarySystem.class.getName()).log(Level.SEVERE,
					null, ex);
		}
		return result;
	}

	/**
	 * Sets up a new environment in specified location.
	 * 
	 * @param rootDir
	 *            absolute path to root directory
	 * @param root
	 *            relative path to root file from rootDir perspective
	 * @return {@code true } if environment has been set, {@code false }
	 *         otherwise.
	 */
	public static boolean setUpNewEnvironment(String rootDir, String root) {
		// populate file with simple hash map
		SLog.v(LibrarySystem.class, "Creating new environment...");
		ObjectStoreable<Integer, PhraseSet> storage = StoreFactory
				.create(StoringMethods.RANDOM_HEAVY);

		storage.clean(getRootPath(rootDir, root));
		try {
			storage.prepare(getRootPath(rootDir, root));
			SLog.d(LibrarySystem.class, "Environment set up.");
			return true;
		} catch (StorageOperationException ex) {
			/** do some logic but for now just print */
			SLog.e(LibrarySystem.class,
					"Not recovered: " + ex.getLocalizedMessage());
		}
		return false;
	}

	/**
	 * Returns a reader capable of reading any phrase of a set given. Default
	 * parameters.
	 * 
	 * @param set
	 *            set to be read from
	 * @return {@link com.gdroid.setphrase.library.PhraseReader }
	 */
	public PhraseReader getReader(PhraseSet set) {
		return this.getReader(set, defaultStoringMethod, defaultCachingMethod);
	}

	/**
	 * Returns a reader capable of reading any phrase of a set given. Default
	 * caching method.
	 * 
	 * @param set
	 *            set to be read from
	 * @param storing
	 *            storing method to be used
	 * @return {@link com.gdroid.setphrase.library.PhraseReader }
	 */
	public PhraseReader getReader(PhraseSet set, StoringMethods storing) {
		return this.getReader(set, storing, CachingMethods.DISABLED);
	}

	/**
	 * Returns a reader capable of reading any phrase of a set given.
	 * 
	 * @param set
	 *            set to be read from
	 * @return {@link com.gdroid.setphrase.library.PhraseReader }
	 */
	public PhraseReader getReader(PhraseSet set, CachingMethods caching) {
		return this.getReader(set, StoringMethods.RANDOM_HEAVY, caching);
	}

	/**
	 * Returns reader capable of reading any phrase of a set given.
	 * 
	 * @param set
	 *            set to be read from
	 * @return {@link com.gdroid.setphrase.library.PhraseReader }
	 */
	public PhraseReader getReader(final PhraseSet set, StoringMethods storing,
			CachingMethods caching) {
		MemoryCacheable cache = StoreFactory.create(caching);
		ObjectStoreable storage = StoreFactory.create(storing);
		accessorReleasedCount++;

		final LibrarySystem system = this;
		final PhraseSetManager manager = phraseFactory
				.createPhraseSetManager(set);
		SLog.d(this, "Reader released.");

		PhraseReader reader = null;
		try {
			reader = new PhraseReader(manager,
					setMaintainer.getSetResource(set), storage, cache) {

				@Override
				void onReadAction() {
					phraseFactory.createLibraryTools().onReadAction(system,
							manager);
				}

				@Override
				public void close() {
					try {
						recoverAccessor(this);
					} catch (StorageOperationException ex) {
						Logger.getLogger(LibrarySystem.class.getName()).log(
								Level.SEVERE, null, ex);
					}
				}

			};
		} catch (StorageOperationException ex) {
			SLog.e(this, "A reader could not be released!", ex);
		}

		return reader;
	}

	/**
	 * Returns writer capable of writing any phrase of a set given. Default
	 * storing method.
	 * 
	 * @param set
	 *            set to be written in
	 * @return writer
	 */
	public PhraseWriter getWriter(PhraseSet set) {
		return this.getWriter(set, defaultStoringMethod);
	}

	/**
	 * Returns writer capable of writing any phrase of a set given.
	 * 
	 * @param set
	 *            set to be written in
	 * @param storing
	 *            storing mechanism to be used
	 * @return writer
	 */
	public PhraseWriter getWriter(final PhraseSet set, StoringMethods storing) {
		ObjectStoreable storage = StoreFactory.create(storing);

		SLog.d(this, "Writer released.");
		accessorReleasedCount++;

		final LibrarySystem system = this;
		final PhraseSetManager manager = phraseFactory
				.createPhraseSetManager(set);

		PhraseWriter writer = null;
		try {
			writer = new PhraseWriter(manager,
					setMaintainer.getSetResource(set), storage) {

				@Override
				void onWriteAction() {
					phraseFactory.createLibraryTools().onWriteAction(system,
							manager);
				}

				@Override
				public void close() {
					try {
						recoverAccessor(this);
					} catch (StorageOperationException ex) {
						Logger.getLogger(LibrarySystem.class.getName()).log(
								Level.SEVERE, null, ex);
					}
				}

			};
		} catch (StorageOperationException ex) {
			SLog.e(this, "A reader could not be released!", ex);
		}
		return writer;
	}

	/**
	 * Creates a new set with name specified. Identifier is assigned
	 * automatically.
	 * 
	 * @param name
	 *            the name of this new set
	 * @return
	 */
	public PhraseSet createSet(String name) {
		PhraseSet set = null;
		try {
			set = setMaintainer.create(
					phraseFactory.createPhraseSetManager(null), name);
		} catch (StorageOperationException ex) {
			/*
			 * if something wrong happens, try to do as much as possible here or
			 * delegate it further if an user can do something about it.
			 */
			SLog.e(this,
					String.format("Error when trying to create set: %s",
							ex.getGeneralCause()));
		}
		return set;
	}

	/**
	 * Returns an existing set identified by this id.
	 * 
	 * @param id
	 * @return
	 */
	public PhraseSet getSet(int id) {
		PhraseSet set = null;
		try {
			set = setMaintainer.load(id);
			return set;
		} catch (StorageOperationException ex) {
			/*
			 * if something wrong happens, try to do as much as possible here or
			 * delegate it further if an user can do something about it.
			 */
			SLog.e(this,
					String.format("Error when trying to get set: %s",
							ex.getGeneralCause()));
		}
		return set;
	}

	/**
	 * Loads all keys of sets stored. Should be used before calling
	 * {@link #getSomeSets(int, int) }
	 * 
	 * @return {@link java.util.Collection } of all known keys
	 */
	public Collection<Integer> getSetIDList() {
		Collection<Integer> setKeys = null;
		try {
			setKeys = setMaintainer.getAvailable();
		} catch (StorageOperationException ex) {
			/*
			 * if something wrong happens, try to do as much as possible here or
			 * delegate it further if an user can do something about it.
			 */
			SLog.e(this,
					String.format("Error when trying to get set ids: %s",
							ex.getGeneralCause()));
		}
		return setKeys;
	}

	/**
	 * Loads all known sets.
	 * 
	 * @return {@link java.util.Collection } of all known sets
	 */
	public Collection<PhraseSet> getAllSets() {
		Collection<Integer> keys = this.getSetIDList();

		SLog.d(this, "There are " + keys.size() + " keys available.");

		if (keys.size() < 1)
			return new ArrayList<PhraseSet>();
		if (keys.size() > 1)
			return this.getSomeSets(0, keys.size());
		else {
			Collection<PhraseSet> result = new ArrayList<PhraseSet>();
			result.add(this.getSet(0));
			return result;
		}

	}

	/**
	 * Loads group of sets.
	 * 
	 * @param start
	 *            first set to be loaded
	 * @param end
	 *            last set to be loaded
	 * @return {@link java.util.Collection } of this group of sets
	 */
	public Collection<PhraseSet> getSomeSets(Integer... ids) {
		Collection<PhraseSet> result = null;
		try {
			Collection toLoad = new ArrayList();
			for (int i = 0; i < ids.length; i++) {
				toLoad.add(ids);
			}
			result = setMaintainer.load(ids);
			return result;
		} catch (StorageOperationException ex) {
			/*
			 * if something wrong happens, try to do as much as possible here or
			 * delegate it further if an user can do something about it.
			 */
			SLog.e(this,
					String.format("Error when trying to get some sets: %s",
							ex.getGeneralCause()));
		}

		return result;
	}

	/**
	 * Handy function for getting human-readable list of sets available.
	 * 
	 * @return {@code String } containing list of sets available
	 */
	public String getPrintableSetList() {
		String result = "";
		Collection<PhraseSet> setList = getAllSets();
		for (PhraseSet set : setList) {
			result += "ID: " + set.getID() + ", title: " + set.getTitle()
					+ ", questions: " + set.getQuestionCount() + "\n";
		}
		return result;
	}

	/**
	 * It is called when {@link com.gdroid.setphrase.library.PhraseReader } or
	 * {@link com.gdroid.setphrase.library.PhraseWriter } is closed. Even if set
	 * content itself was saved on hard drive, base of sets must be updated with
	 * this function.
	 * 
	 * @param accessor
	 *            reader or writer that was closed
	 */
	private void recoverAccessor(PhraseAccessor accessor)
			throws StorageOperationException {
		PhraseSet set = accessor.getSetManager().getPhraseSet();
		SLog.d(this, "Updating set base with the set: " + set.getTitle() + "("
				+ set.getID() + ")...");

		setMaintainer.save(set);
		accessorReleasedCount--;
	}

	/**
	 * Checks integrity of all operations performed. Usually means that counts
	 * released accessors and those that come back after calling
	 * {@link com.gdroid.setphrase.library.PhraseAccessor#close() }
	 * 
	 * @return
	 */
	public boolean checkIntegrity() {
		if (accessorReleasedCount > 0)
			return false;
		else
			return true;
	}

	/*
	 * ====================================================================
	 * Utilities
	 * ====================================================================
	 */

	private static String getRootPath(String rootDir, String root) {
		return rootDir + "/" + root;
	}
}
