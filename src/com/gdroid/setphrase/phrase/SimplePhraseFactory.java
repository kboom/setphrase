package com.gdroid.setphrase.phrase;

import com.gdroid.setphrase.dictionary.extractor.base.TargetPhraseBuilder;
import com.gdroid.setphrase.dictionary.extractor.phrasebuilder.SimpleTargetPhraseBuilder;
import com.gdroid.setphrase.phrase.set.PhraseSet;
import com.gdroid.setphrase.phrase.set.PhraseSetManager;
import com.gdroid.setphrase.phrase.set.SimplePhraseSet;
import com.gdroid.setphrase.phrase.set.SimplePhraseSetManager;
import com.gdroid.setphrase.phrase.tools.LibraryTools;
import com.gdroid.setphrase.phrase.tools.SimpleLibraryTools;

/**
 * 
 * @author kboom
 */
public class SimplePhraseFactory implements PhraseFactory {

	@Override
	public PhraseSetManager createPhraseSetManager(PhraseSet set) {
		if (set != null)
			return new SimplePhraseSetManager((SimplePhraseSet) set);
		else
			return new SimplePhraseSetManager();
	}

	@Override
	public LibraryTools createLibraryTools() {
		return new SimpleLibraryTools();
	}

	@Override
	public Phrase createPhrase(String name) {
		return new SimplePhrase(name);
	}

	@Override
	public TargetPhraseBuilder createPhraseBuilder() {
		return new SimpleTargetPhraseBuilder();
	}

}
