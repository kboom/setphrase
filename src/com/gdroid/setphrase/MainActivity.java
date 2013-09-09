package com.gdroid.setphrase;

import java.util.logging.Level;
import java.util.logging.Logger;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.widget.Toast;

import com.gdroid.setphrase.library.PhraseReader;
import com.gdroid.setphrase.library.PhraseWriter;
import com.gdroid.setphrase.model.ModelFacade;
import com.gdroid.setphrase.model.ModelManager;
import com.gdroid.setphrase.phrase.target.TargetPhrase;
import com.gdroid.utils.SLog;

/**
 * 
 * @author kboom
 */
public class MainActivity extends Activity {

	static {
		SLog.register(MainActivity.class);
		SLog.setTag(MainActivity.class, "Main activity.");
		// turn on debugging (do not forget to turn this off before release!
		SLog.turnOn();
	}

	private PreferenceObserver prefObserver;
	private ModelFacade model;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initializeModel();
	}

	private void test() {
		Intent i = new Intent(this, MainTestingActivity.class);
		startActivity(i);
	}

	public String getDataDir() throws NameNotFoundException {
		return getApplicationContext().getPackageManager().getPackageInfo(
				getPackageName(), 0).applicationInfo.dataDir;
	}

	private void initializeModel() {
		prefObserver = new PreferenceObserver(new PreferenceManager(this));
		ModelManager.InitialModelParameters parameters = new ModelManager.InitialModelParameters();

		String dataDir = null;
		try {
			dataDir = getDataDir();
		} catch (Exception ex) {
			Logger.getLogger(MainTestingActivity.class.getName()).log(
					Level.SEVERE, null, ex);
		}

		parameters.rootDir = dataDir;
		parameters.rootFile = GlobalParameters.ROOT_FILE;

		ModelManager modelManager = new ModelManager(parameters);
		prefObserver.registerModel(modelManager);
		prefObserver.refresh();
		model = modelManager.getModel();
	}

}
