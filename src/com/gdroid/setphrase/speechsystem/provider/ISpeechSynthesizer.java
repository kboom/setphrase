package com.gdroid.setphrase.speechsystem.provider;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.ispeech.Synthesizer;
import org.ispeech.error.BusyException;
import org.ispeech.error.NoNetworkException;

/**
 * 
 * @author kboom
 */
public class ISpeechSynthesizer implements SpeechSynthesizable {

	private Synthesizer synthesizer;

	public ISpeechSynthesizer(Synthesizer synthesizer) {
		this.synthesizer = synthesizer;
	}

	@Override
	public void synthesize(String phrase) {
		try {
			synthesizer.speak(phrase);
		} catch (BusyException ex) {
			Logger.getLogger(ISpeechSynthesizer.class.getName()).log(
					Level.SEVERE, null, ex);
		} catch (NoNetworkException ex) {
			Logger.getLogger(ISpeechSynthesizer.class.getName()).log(
					Level.SEVERE, null, ex);
		}
	}

	@Override
	public void setLanguage(String language) {

	}

}
