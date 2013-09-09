package com.gdroid.setphrase.speechsystem.provider;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.ispeech.Recognizer;
import org.ispeech.SpeechRecognizerEvent;
import org.ispeech.SpeechResult;
import org.ispeech.error.BusyException;
import org.ispeech.error.NoNetworkException;

import com.gdroid.setphrase.phrase.RecognizedPhrase;
import com.gdroid.utils.SLog;

/**
 * Adapter to satisfy common interface @target SpeechRecognizable with iSpeech
 * recognizer.
 * 
 * @author kboom
 */
public class ISpeechRecognizer implements SpeechRecognizable {

	static {
		SLog.register(ISpeechRecognizer.class);
		SLog.setTag(ISpeechRecognizer.class, "ISpeech recognizer.");
	}

	private static Recognizer recognizer;
	private SpeechResult result;
	private Object busyLock = new Object();

	public ISpeechRecognizer(Recognizer recognizer) {
		ISpeechRecognizer.recognizer = recognizer;
	}

	@Override
	public RecognizedPhrase recognize() {
		/*
		 * A good example of when to use that "hooks" (at least 1 abstract to
		 * enforce inline extension) making event totally independent of
		 * anything else. What's even more important there is no return type and
		 * everything can by done as if this event were part of this class!
		 */
		SpeechRecognizerEvent event = new SpeechRecognizerEvent() {

			@Override
			public void onRecognitionComplete(SpeechResult sr) {
				result = sr;
				synchronized (busyLock) {
					busyLock.notify();
				}
			}

		};
		try {
			recognizer.startRecord(event);
			synchronized (busyLock) {
				busyLock.wait();
			}

		} catch (BusyException ex) {
			Logger.getLogger(ISpeechRecognizer.class.getName()).log(
					Level.SEVERE, null, ex);
		} catch (NoNetworkException ex) {
			Logger.getLogger(ISpeechRecognizer.class.getName()).log(
					Level.SEVERE, null, ex);
		} finally {
			// called after recognition has ended (finally)
			return new ISpeechRecording(result);
		}
	}

	@Override
	public void setLanguage(String language) {
		recognizer.setLocale(language);
	}

	@Override
	public void setTargetPhrase(String phrase) {
		recognizer.addCommand(phrase);
	}
}
