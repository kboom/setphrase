package com.gdroid.setphrase;

import java.util.logging.Level;
import java.util.logging.Logger;

import android.app.Activity;
import android.os.Bundle;

import com.gdroid.setphrase.test.LibrarySystemTest;
import com.gdroid.utils.SLog;

public class MainTestingActivity extends Activity {

	static {
		SLog.register(MainTestingActivity.class);
		SLog.setTag(MainTestingActivity.class, "Main testing activity.");

		// turn on debugging (do not forget to turn this off before release!
		SLog.turnOn();
	}

	private static final int VOICE_RECOGNITION_REQUEST = 0x343222;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		String dataDir = null;
		try {
			dataDir = getDataDir();
		} catch (Exception ex) {
			Logger.getLogger(MainTestingActivity.class.getName()).log(
					Level.SEVERE, null, ex);
		}

		/*
		 * QUESTIONING TEST LinearLayout ll = (LinearLayout)
		 * findViewById(R.id.root); QuestioningSystemTest questioningTest = new
		 * QuestioningSystemTest(this, ll, dataDir);
		 */

		/* LIBRARY TEST */
		LibrarySystemTest libraryTest = new LibrarySystemTest(this, dataDir);
		libraryTest.performTest();

		/*
		 * DICTIONARY TEST / DictionarySystemTest dictionaryTest = new
		 * DictionarySystemTest(this); dictionaryTest.performTest();
		 */

		/*
		 * RECOGNITION TEST if(!SpeechRecognizer.isRecognitionAvailable(this))
		 * Toast.makeText(this, "Service not available!", Toast.LENGTH_LONG);
		 * 
		 * //speakToMe();
		 */
		/*
		 * SpeechSystemTest test = new SpeechSystemTest(this); try {
		 * test.performTest(SpeechSystemTest.Provider.GOOGLE,
		 * SpeechSystemTest.Validator.EXACT_MATCH, "English"); } catch
		 * (InvalidApiKeyException ex) {
		 * SLog.getLogger(MainTestingActivity.class.getName()).log(Level.SEVERE,
		 * null, ex); }
		 */

	}

	public String getDataDir() throws Exception {
		return getApplicationContext().getPackageManager().getPackageInfo(
				getPackageName(), 0).applicationInfo.dataDir;
	}

	/*
	 * public void speakToMe() { Intent intent = new
	 * Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
	 * intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
	 * RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
	 * intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
	 * "Please speak slowly and enunciate clearly.");
	 * startActivityForResult(intent, VOICE_RECOGNITION_REQUEST); }
	 * 
	 * @Override protected void onActivityResult(int requestCode, int
	 * resultCode, Intent data) { if (requestCode == VOICE_RECOGNITION_REQUEST
	 * && resultCode == RESULT_OK) { ArrayList<String> matches = data
	 * .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS); String
	 * firstMatch = matches.get(0); Toast.makeText(this, firstMatch,
	 * Toast.LENGTH_LONG); } }
	 */

}
