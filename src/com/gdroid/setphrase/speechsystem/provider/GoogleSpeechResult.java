package com.gdroid.setphrase.speechsystem.provider;

import com.gdroid.setphrase.phrase.RecognizedPhrase;
import com.gdroid.setphrase.phrase.SimplePhrase;

/**
 * 
 * @author kboom
 */
public class GoogleSpeechResult extends RecognizedPhrase {

	public GoogleSpeechResult(String result) {
		super(new SimplePhrase(result));
	}

	@Override
	public float getConfidence() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

}
