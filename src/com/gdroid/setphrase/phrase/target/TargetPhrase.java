package com.gdroid.setphrase.phrase.target;

import java.io.Serializable;
import java.util.List;

import com.gdroid.setphrase.phrase.Phrase;
import com.gdroid.setphrase.phrase.SpeechPart;

/**
 * All target phrases must implement this interface. These kind of phrases are
 * maintained by the {@link com.gdroid.setphrase.dictionary.DictionarySystem}
 * and stored by the {@link com.gdroid.setphrase.library.LibrarySystem}. The
 * contain some additional information such as synonyms and definition. They are
 * also used to compare with
 * {@link com.gdroid.setphrase.phrase.RecognizedPhrase}.
 * 
 * @author kboom
 */
public interface TargetPhrase extends Phrase, Serializable {
	List<Phrase> getSynonymList();

	String getSynonyms();

	String getDefinition();

	SpeechPart getType();

	void setDefinition(String def);

	public int getID();
}
