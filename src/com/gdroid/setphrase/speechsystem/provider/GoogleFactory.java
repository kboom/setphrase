package com.gdroid.setphrase.speechsystem.provider;

import android.content.Context;
import android.speech.SpeechRecognizer;

/**
 * Google speech factory
 * 
 * @author kboom
 */
public class GoogleFactory extends SpeechProviderFactory {

	public GoogleFactory(Context context) {
		super(context);
	}

	@Override
	public SpeechRecognizable getSpeechRecognizer() {
		SpeechRecognizer googleRecognizer = SpeechRecognizer
				.createSpeechRecognizer(this.context);
		return new GoogleRecognizer(googleRecognizer);
	}

	@Override
	public SpeechSynthesizable getSpeechSynthesizer() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

}
