package com.gdroid.setphrase.speechsystem;

import com.gdroid.setphrase.phrase.RecognizedPhrase;
import com.gdroid.setphrase.phrase.target.TargetPhrase;
import com.gdroid.setphrase.speechsystem.provider.SpeechRecognizable;
import com.gdroid.setphrase.speechsystem.provider.SpeechSynthesizable;
import com.gdroid.system.System;

/**
 * System providing support for speech actions.
 * 
 * @author kboom
 */
public class SpeechSystem extends System {

	private SpeechRecognizable recognizer;
	private SpeechSynthesizable synthesizer;

	public void setRecognizer(SpeechRecognizable recognizer) {
		this.recognizer = recognizer;
	}

	public void setSynthesizer(SpeechSynthesizable synthesizer) {
		this.synthesizer = synthesizer;
	}

	/**
	 * Records a phrase. Parameter given may strengthen a chance of proper
	 * recognition because it may contain some additional hints important for
	 * phrase recognition process.
	 * 
	 * @param targetPhrase
	 *            phrase to compare to
	 * @return if recorded phrase match given one
	 */
	public RecognizedPhrase record(TargetPhrase targetPhrase) {
		recognizer.setTargetPhrase(targetPhrase.getPhrase());
		return recognizer.recognize();
	}

	public RecognizedPhrase record() {
		return recognizer.recognize();
	}

}
