package com.gdroid.setphrase.phrase;

/**
 * Classification of phrases.
 * 
 * @author kboom
 */
public enum SpeechPart {
	VERB("Verb"), NOUN("Noun"), PRONOUN("Pronoun"), ADJECTIVE("Adjective"), UNDEFINED(
			"Undefined");

	private String name;

	SpeechPart(String name) {
		this.name = name;
	}
}
