package com.gdroid.setphrase.speechsystem.provider;

import java.util.logging.Level;
import java.util.logging.Logger;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognitionService;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;

import com.gdroid.setphrase.phrase.RecognizedPhrase;
import com.gdroid.utils.SLog;

/**
 * 
 * @author kboom
 */
public class GoogleRecognizer implements SpeechRecognizable {

	static {
		SLog.register(GoogleRecognizer.class);
		SLog.setTag(GoogleRecognizer.class, "Google recognizer.");
	}

	private SpeechRecognizer recognizer;
	private RecognitionListener recognitionListener;
	private static GoogleSpeechResult result;
	private Object busyLock = new Object();

	public GoogleRecognizer(SpeechRecognizer recognizer) {
		this.recognizer = recognizer;
		recognitionListener = new RecognitionListener() {

			@Override
			public void onReadyForSpeech(Bundle arg0) {

			}

			@Override
			public void onBeginningOfSpeech() {

			}

			@Override
			public void onRmsChanged(float arg0) {

			}

			@Override
			public void onBufferReceived(byte[] arg0) {

			}

			@Override
			public void onEndOfSpeech() {

			}

			@Override
			public void onError(int arg0) {

			}

			@Override
			public void onResults(Bundle arg0) {
				result = new GoogleSpeechResult(arg0.getStringArrayList(
						SpeechRecognizer.RESULTS_RECOGNITION).get(0));
				busyLock.notify();
			}

			@Override
			public void onPartialResults(Bundle arg0) {
			}

			@Override
			public void onEvent(int arg0, Bundle arg1) {
			}

		};
	}

	@Override
	public RecognizedPhrase recognize() {
		// set an observer on observable (thats the same as listener!)
		recognizer.setRecognitionListener(null);
		Intent intent = new Intent();
		intent.setAction(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
		intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
				RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
		intent.putExtra(RecognitionService.SERVICE_INTERFACE,
				"https://www.google.com/speech-api/v1/recognize");

		recognizer.startListening(intent);
		synchronized (busyLock) {
			try {
				busyLock.wait();
			} catch (InterruptedException ex) {
				Logger.getLogger(GoogleRecognizer.class.getName()).log(
						Level.SEVERE, null, ex);
			}
		}
		return result;
	}

	@Override
	public void setTargetPhrase(String phrase) {
		// google don't need this
	}

	@Override
	public void setLanguage(String language) {

	}

}
