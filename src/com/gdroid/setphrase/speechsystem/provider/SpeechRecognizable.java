package com.gdroid.setphrase.speechsystem.provider;

import com.gdroid.setphrase.phrase.RecognizedPhrase;

/**
 * Interface for all recognizers.
 * 
 * @author kboom
 */
public interface SpeechRecognizable extends Speakable {
	public RecognizedPhrase recognize();

	public void setTargetPhrase(String phrase);
}
