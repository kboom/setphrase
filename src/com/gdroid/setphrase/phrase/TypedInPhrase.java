package com.gdroid.setphrase.phrase;

/**
 * A phrase that was typed in. Therefore it has always {@link #getConfidence()}
 * of {@code 1} and {@link #getNextMatch()} is always {@code null}.
 * 
 * @author kboom
 */
public class TypedInPhrase implements CapturedPhrase {

	Phrase mPhrase;

	public TypedInPhrase(String phrase) {
		mPhrase = new SimplePhrase(phrase);
	}

	@Override
	public String getPhrase() {
		return mPhrase.getPhrase();
	}

	@Override
	public float getConfidence() {
		return 1f;
	}

	@Override
	public CapturedPhrase getNextMatch() {
		return null;
	}

}
