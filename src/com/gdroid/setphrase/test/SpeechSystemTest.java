package com.gdroid.setphrase.test;

import org.ispeech.error.InvalidApiKeyException;

import android.content.Context;

import com.gdroid.setphrase.speechsystem.SpeechSystem;
import com.gdroid.setphrase.speechsystem.provider.GoogleFactory;
import com.gdroid.setphrase.speechsystem.provider.ISpeechFactory;
import com.gdroid.setphrase.speechsystem.provider.SpeechProviderFactory;
import com.gdroid.setphrase.speechsystem.provider.SpeechRecognizable;
import com.gdroid.utils.SLog;

/**
 * Set of tests aiming at exercising Speech System.
 * 
 * @author kboom
 */
public class SpeechSystemTest {

	static {
		SLog.register(SpeechSystemTest.class);
		SLog.setTag(SpeechSystemTest.class, "Speech system test.");
	}

	public enum Provider {
		ISPEECH, GOOGLE
	}

	public enum Validator {
		EXACT_MATCH
	}

	private static Context context;

	public SpeechSystemTest(Context context) {
		SpeechSystemTest.context = context;
	}

	public void performTest(Provider provider, Validator validator,
			String language) throws InvalidApiKeyException {
		final SpeechSystem system = new SpeechSystem();

		SpeechProviderFactory factory = null;
		SpeechRecognizable speechRecognizer = null;

		switch (provider) {
		case ISPEECH:
			factory = new ISpeechFactory(context);
			break;

		case GOOGLE:
			factory = new GoogleFactory(context);
			break;
		default:
			break;
		}

		speechRecognizer = factory.getSpeechRecognizer();
		system.setRecognizer(speechRecognizer);

		/*
		 * while(true) { TargetPhrase target = new TargetPhrase(new
		 * RawPhrase("dupa"), SpeechPart.NOUN);
		 * //if(system.recordAndValidate(target)) SLog.i(this.getClass(),
		 * "Match!"); //else SLog.i(this.getClass(), "No match!"); }
		 */
	}

}
