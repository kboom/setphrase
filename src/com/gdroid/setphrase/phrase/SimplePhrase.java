package com.gdroid.setphrase.phrase;

/**
 * A simplest implementation of a phrase.
 * 
 * @author kboom
 */
public class SimplePhrase implements Phrase {

	private String mPhrase;

	public SimplePhrase(String phrase) {
		mPhrase = phrase;
	}

	@Override
	public String getPhrase() {
		return mPhrase;
	}

}
