package com.gdroid.setphrase.test;

import java.util.Collection;

import android.content.Context;

import com.gdroid.setphrase.dictionary.DictionarySystem;
import com.gdroid.setphrase.dictionary.ThesaurusDictionaryFactory;
import com.gdroid.setphrase.dictionary.WordNetDictionaryFactory;
import com.gdroid.setphrase.dictionary.extractor.buildingstrategy.DumpNone;
import com.gdroid.setphrase.dictionary.extractor.buildingstrategy.SortByName;
import com.gdroid.setphrase.phrase.PhraseFactory;
import com.gdroid.setphrase.phrase.SimplePhrase;
import com.gdroid.setphrase.phrase.SimplePhraseFactory;
import com.gdroid.setphrase.phrase.target.TargetPhrase;
import com.gdroid.utils.SLog;

/**
 * 
 * @author kboom
 */
public class DictionarySystemTest {

	static {
		SLog.register(DictionarySystemTest.class);
		SLog.setTag(DictionarySystemTest.class, "Dictionary system test.");
	}

	private static final String PHRASE = "win";
	private static final int WHAT_TO_TEST = 2;
	private static final int DICT_SERVICE = 1;
	private static final int WORD_NET = 2;

	private static Context context;
	private PhraseFactory phraseFactory;

	public DictionarySystemTest(Context context) {
		DictionarySystemTest.context = context;
	}

	public void performTest() {
		phraseFactory = new SimplePhraseFactory();
		DictionarySystem system = null;
		switch (WHAT_TO_TEST) {
		case DICT_SERVICE:
			system = new DictionarySystem();
			system.setDictionaryFactory(new ThesaurusDictionaryFactory());
			system.setDumpingStrategy(new DumpNone());
			system.setSortingStrategy(new SortByName());
			system.setPhraseFactory(phraseFactory);
			break;
		case WORD_NET:
			system = new DictionarySystem();
			system.setDictionaryFactory(new WordNetDictionaryFactory());
			system.setDumpingStrategy(new DumpNone());
			system.setSortingStrategy(new SortByName());
			system.setPhraseFactory(phraseFactory);
			break;
		default:
			throw new IllegalArgumentException("Wrong test type!");

		}

		SLog.i(this, "Searching for phrases matching \"" + PHRASE + "\"");
		Collection<TargetPhrase> phraseList = system
				.getAllMatchingPhrases(new SimplePhrase(PHRASE));
		SLog.i(this, "Target phrases received.");
		for(TargetPhrase t : phraseList) {
			SLog.d(this, "Phrase(" + t.getID() + "):" + t.getPhrase()
					+ ", " + t.getDefinition() + ", "
					+ t.getSynonyms());
		}
			

	}
}
