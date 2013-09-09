/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdroid.setphrase.phrase;

import com.gdroid.setphrase.dictionary.extractor.base.TargetPhraseBuilder;
import com.gdroid.setphrase.phrase.set.PhraseSet;
import com.gdroid.setphrase.phrase.set.PhraseSetManager;
import com.gdroid.setphrase.phrase.tools.LibraryTools;

/**
 * 
 * @author kboom
 */
public interface PhraseFactory {

	Phrase createPhrase(String name);

	/**
	 * Phrase set manager also determines what type of TargetPhrase it is.
	 * 
	 * @param set
	 * @return
	 */
	PhraseSetManager createPhraseSetManager(PhraseSet set);

	LibraryTools createLibraryTools();

	TargetPhraseBuilder createPhraseBuilder();
}
