package com.gdroid.setphrase.speechsystem.provider;

import org.ispeech.SpeechResult;

import com.gdroid.setphrase.phrase.RecognizedPhrase;
import com.gdroid.setphrase.phrase.SimplePhrase;

/**
 * Converter for iSpeech results.
 * 
 * @author kboom
 */
public class ISpeechRecording extends RecognizedPhrase {

	private float confidence;

	public ISpeechRecording(SpeechResult sr) {
		super(new SimplePhrase(sr.getText()));
		confidence = sr.getConfidence();
	}

	@Override
	public float getConfidence() {
		return confidence;
	}
}
