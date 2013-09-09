package com.gdroid.setphrase.test;

import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import android.content.Context;

import com.gdroid.setphrase.dictionary.DictionarySystem;
import com.gdroid.setphrase.dictionary.WordNetDictionaryFactory;
import com.gdroid.setphrase.dictionary.extractor.buildingstrategy.DumpNone;
import com.gdroid.setphrase.dictionary.extractor.buildingstrategy.SortByName;
import com.gdroid.setphrase.library.LibrarySystem;
import com.gdroid.setphrase.library.PhraseReader;
import com.gdroid.setphrase.library.PhraseWriter;
import com.gdroid.setphrase.phrase.PhraseFactory;
import com.gdroid.setphrase.phrase.SimplePhraseFactory;
import com.gdroid.setphrase.phrase.set.PhraseSet;
import com.gdroid.setphrase.phrase.target.TargetPhrase;
import com.gdroid.utils.SLog;

/**
 * Is dependent from DictionarySystem! (creates target phrase)
 * 
 * @author kboom
 */
public class LibrarySystemTest {

	static {
		SLog.register(LibrarySystemTest.class);
		SLog.setTag(LibrarySystemTest.class, "Dictionary system test.");
	}

	private static final String ROOT_FILE = "base.si";

	private static final int WHAT_TO_TEST = 1;
	private static final int READ_TEST_A = 1;
	private static final int WRITE_TEST_A = 2;
	private static final int CHANGE_TEST_A = 3;

	private static Context context;
	private static String rootDir;
	private PhraseFactory phraseFactory;

	public LibrarySystemTest(Context context, String dir) {
		LibrarySystemTest.context = context;
		LibrarySystemTest.rootDir = dir;
		phraseFactory = new SimplePhraseFactory();
	}

	public void performTest() {

		DictionarySystem system = null;
		switch (WHAT_TO_TEST) {
		case READ_TEST_A:
			readTestA();
			break;
		case WRITE_TEST_A:
			writeTestA();
			break;
		case CHANGE_TEST_A:
			changeTestA();
			break;
		default:
			throw new IllegalArgumentException("Wrong test type!");
		}

	}

	private LibrarySystem createLibrary() {
		SLog.i(this, "Checking integrity and creating library.");
		if (!LibrarySystem.checkEnvironmentIntegrity(rootDir, ROOT_FILE)) {
			LibrarySystem.setUpNewEnvironment(rootDir, ROOT_FILE);
		}
		// will check up environment integrity for itself too!
		LibrarySystem result = new LibrarySystem(rootDir, ROOT_FILE);
		result.setPhraseFactory(phraseFactory);
		return result;
	}

	private void readTestA() {
		LibrarySystem library = createLibrary();
		library.setPhraseFactory(phraseFactory);
		SLog.i(this, library.getPrintableSetList());

		Collection<PhraseSet> sets = library.getAllSets();

		TargetPhrase phrase = null;
		for (PhraseSet s : sets) {
			PhraseReader reader = library.getReader(s);
			SLog.i(this,
					"Found another set=(" + s.getTitle() + "), size=("
							+ s.getQuestionCount() + ")");
			for (int i = 0; i < s.getQuestionCount(); i++) {
				try {
					phrase = reader.read(i);
				} catch (Exception ex) {
					Logger.getLogger(LibrarySystemTest.class.getName()).log(
							Level.SEVERE, null, ex);
				}
				SLog.i(this,
						"Got phrase=(" + phrase.getPhrase() + "), def=("
								+ phrase.getDefinition() + "), syn=("
								+ phrase.getSynonymList() + ")");
			}
		}

		SLog.i(this, "Random read test!");
		PhraseReader reader = library.getReader((PhraseSet) sets.toArray()[0]);
		Collection<TargetPhrase> randomPhrases = reader.read(0, 1);

		int id = 0;
		for (TargetPhrase p : randomPhrases) {
			SLog.i(this,
					String.format("Phrase (%s :%d), def: (%s), syn: (%s)",
							p.getPhrase(), id++, p.getDefinition(),
							p.getSynonymList()));
		}

	}

	private void changeTestA() {
		SLog.i(this, "Changing content test.");
		LibrarySystem library = createLibrary();
		SLog.i(this, "Library ready.");

		// get first set
		Collection<Integer> setIDs = library.getSetIDList();
		if (setIDs.contains(0)) {
			PhraseSet s = null;
			try {
				s = library.getSet(0);
			} catch (Exception ex) {
				Logger.getLogger(LibrarySystemTest.class.getName()).log(
						Level.SEVERE, null, ex);
			}
			PhraseReader r = library.getReader(s);
			SLog.i(this, "Reading stored content...");
			TargetPhrase phrase = null;
			try {
				phrase = r.read(0);
			} catch (Exception ex) {
				Logger.getLogger(LibrarySystemTest.class.getName()).log(
						Level.SEVERE, null, ex);
			}
			SLog.i(this,
					"Got phrase=(" + phrase.getPhrase() + "), def=("
							+ phrase.getDefinition() + "), syn=("
							+ phrase.getSynonymList() + ")");

			r.close();

			SLog.i(this, "Now let's change it!");
			PhraseWriter w = library.getWriter(s);
			phrase.setDefinition("!!!! Dupa dupa dupa !!!!!");

			w.write(0, phrase);
			w.close();

			SLog.i(this, "Read it again.");
			// read again
			r = library.getReader(s);
			SLog.i(this, "Reading stored content...");
			phrase = null;
			try {
				phrase = r.read(0);
			} catch (Exception ex) {
				Logger.getLogger(LibrarySystemTest.class.getName()).log(
						Level.SEVERE, null, ex);
			}
			SLog.i(this,
					"Got phrase=(" + phrase.getPhrase() + "), def=("
							+ phrase.getDefinition() + "), syn=("
							+ phrase.getSynonymList() + ")");

			r.close();

		}

	}

	private void writeTestA() {

		SLog.i(this, "Setting new environment");
		// LibrarySystem.setUpNewEnvironment(rootDir, ROOT_FILE);

		LibrarySystem library = createLibrary();
		// hash this out to preserve previous environment
		// LibrarySystem.setUpNewEnvironment(rootDir, ROOT_FILE);

		// print existing entries
		SLog.i(this, "Existing:\n " + library.getPrintableSetList());

		PhraseSet set;
		PhraseWriter writer;

		set = library.createSet("set");
		writer = library.getWriter(set);
		SLog.i(this, writer.printSetInformation());
		//
		DictionarySystem dictSystem;

		dictSystem = new DictionarySystem();
		dictSystem.setDictionaryFactory(new WordNetDictionaryFactory());
		dictSystem.setDumpingStrategy(new DumpNone());
		dictSystem.setSortingStrategy(new SortByName());
		dictSystem.setPhraseFactory(phraseFactory);

		for (int i = 0; i < 3; i++) {
			TargetPhrase phrase = dictSystem
					.getAllMatchingPhrases(phraseFactory.createPhrase("power")).iterator().next();
			SLog.i(this, "Got for success: " + phrase.getDefinition());
			writer.write(phrase);

			phrase = dictSystem.getAllMatchingPhrases(phraseFactory
					.createPhrase("imagination")).iterator().next();
			SLog.i(this, "Got for imagination: " + phrase.getDefinition());
			writer.write(phrase);

			phrase = dictSystem.getAllMatchingPhrases(phraseFactory
					.createPhrase("sleep")).iterator().next();
			SLog.i(this, "Got for money: " + phrase.getDefinition());
			writer.write(phrase);

			// do not forget about this one!
			writer.close();
		}

		if (!library.checkIntegrity())
			SLog.w(this, "Integrity check NOT passed!");
	}
}
