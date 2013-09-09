package com.gdroid.setphrase.phrase;

/**
 * A phrase that was recognized from speech. It has always a certain
 * {@link #getConfidence()} and sometimes multiple {@link #getNextMatch()}
 * results.
 * 
 * @author kboom
 */
public abstract class RecognizedPhrase implements CapturedPhrase {

	Phrase recognizedPhrase;
	RecognizedPhrase mNextMatchingPhrase;

	public RecognizedPhrase(Phrase phrase) {
		recognizedPhrase = phrase;
	}

	@Override
	public String getPhrase() {
		return recognizedPhrase.getPhrase();
	}

	@Override
	public CapturedPhrase getNextMatch() {
		return mNextMatchingPhrase;
	}

}
