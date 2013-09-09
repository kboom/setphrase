package com.gdroid.setphrase.speechsystem.provider;

/**
 * Interface for all synthesizers.
 * 
 * @author kboom
 */
public interface SpeechSynthesizable extends Speakable {
	public void synthesize(String phrase);
}
