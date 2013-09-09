/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdroid.setphrase.speechsystem.provider;

import android.content.Context;

/**
 * 
 * @author kboom
 */
public abstract class SpeechProviderFactory {

	protected Context context;

	public SpeechProviderFactory(Context c) {
		context = c;
	};

	public abstract SpeechRecognizable getSpeechRecognizer();

	public abstract SpeechSynthesizable getSpeechSynthesizer();
}
