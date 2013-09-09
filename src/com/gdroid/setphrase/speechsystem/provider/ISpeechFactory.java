package com.gdroid.setphrase.speechsystem.provider;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.ispeech.error.InvalidApiKeyException;

import android.content.Context;

/**
 * iSpeech factory
 * 
 * @author kboom
 */
public class ISpeechFactory extends SpeechProviderFactory {

	public ISpeechFactory(Context context) {
		super(context);
	}

	@Override
	public SpeechRecognizable getSpeechRecognizer() {
		org.ispeech.SpeechRecognizer recognizer = null;
		try {
			recognizer = org.ispeech.SpeechRecognizer.getInstance(this.context);
		} catch (InvalidApiKeyException ex) {
			Logger.getLogger(ISpeechFactory.class.getName()).log(Level.SEVERE,
					null, ex);
		} finally {
			return new ISpeechRecognizer(recognizer);
		}
	}

	@Override
	public SpeechSynthesizable getSpeechSynthesizer() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

}
