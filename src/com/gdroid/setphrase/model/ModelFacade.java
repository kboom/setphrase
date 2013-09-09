package com.gdroid.setphrase.model;

import java.util.Collection;

import com.gdroid.setphrase.dictionary.DictionarySystem;
import com.gdroid.setphrase.library.LibrarySystem;
import com.gdroid.setphrase.library.PhraseReader;
import com.gdroid.setphrase.library.PhraseWriter;
import com.gdroid.setphrase.phrase.Phrase;
import com.gdroid.setphrase.phrase.PhraseFactory;
import com.gdroid.setphrase.phrase.set.PhraseSet;
import com.gdroid.setphrase.phrase.target.TargetPhrase;
import com.gdroid.setphrase.questioningsystem.QuestioningSystem;
import com.gdroid.setphrase.validatingsystem.ValidationSystem;
import com.gdroid.system.System;

/**
 * Facade for all functionality. Is able to handle exceptions so using class
 * will not be bothered with try-catch blocks.
 * 
 * @author kboom
 */
public class ModelFacade extends System {

	PhraseFactory phraseFactory;
	DictionarySystem dictionarySystem;
	LibrarySystem librarySystem;
	QuestioningSystem questioningSystem;
	ValidationSystem validationSystem;

	ModelFacade() {

	}

	public Collection<TargetPhrase> lookUpForPhrases(String rawText) {
		Phrase phrase = phraseFactory.createPhrase(rawText);
		return dictionarySystem.getAllMatchingPhrases(phrase);
	}

	public PhraseReader getReader(PhraseSet set) {
		return librarySystem.getReader(set);
	}

	public PhraseWriter getWriter(PhraseSet set) {
		return librarySystem.getWriter(set);
	}

	public PhraseSet createNewSet(String name) {
		return librarySystem.createSet(name);
	}

	public PhraseSet getSet(int id) {
		return librarySystem.getSet(id);
	}

}
