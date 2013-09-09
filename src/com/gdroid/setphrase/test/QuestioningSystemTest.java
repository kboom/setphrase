package com.gdroid.setphrase.test;

import java.util.Collection;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gdroid.setphrase.library.LibrarySystem;
import com.gdroid.setphrase.library.PhraseWriter;
import com.gdroid.setphrase.phrase.PhraseFactory;
import com.gdroid.setphrase.phrase.SimplePhraseFactory;
import com.gdroid.setphrase.phrase.set.PhraseSet;
import com.gdroid.setphrase.questioningsystem.AlgorithmConstructor;
import com.gdroid.setphrase.questioningsystem.QuestioningAlgorithm;
import com.gdroid.setphrase.questioningsystem.QuestioningAlgorithmBuildable;
import com.gdroid.setphrase.questioningsystem.QuestioningCommandable;
import com.gdroid.setphrase.questioningsystem.QuestioningSystem;
import com.gdroid.setphrase.questioningsystem.SimpleAlgorithmBuilderTypeA;
import com.gdroid.setphrase.questioningsystem.SimpleQuestioningAlgorithm;
import com.gdroid.setphrase.questioningsystem.filter.PassEverythingFilter;
import com.gdroid.utils.SLog;

/**
 * 
 * @author kboom
 */
public class QuestioningSystemTest {

	static {
		SLog.register(QuestioningSystemTest.class);
		SLog.setTag(QuestioningSystemTest.class, "Questioning system test.");
	}

	private static Context context;
	private static final String ROOT_FILE = "base.si";
	private QuestioningCommandable mCurrentCommand;
	private TextView mCurrentID, mQuestionCount;
	private QuestioningSystem mQuestioningSystem;
	private LibrarySystem mLibrarySystem;
	private PhraseFactory phraseFactory;

	public QuestioningSystemTest(Context context, LinearLayout root, String dir) {
		QuestioningSystemTest.context = context;

		phraseFactory = new SimplePhraseFactory();

		// create layout
		LinearLayout ll = new LinearLayout(context);
		ll.setOrientation(LinearLayout.VERTICAL);
		TextView tv = new TextView(context);
		tv.setText("0");
		ll.addView(tv);
		mCurrentID = tv;

		tv = new TextView(context);
		tv.setText("0");
		ll.addView(tv);

		mQuestionCount = tv;

		Button correctButton = new Button(context);
		correctButton.setText("correct");
		correctButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				mCurrentCommand.anwseredCorrectly();
				nextQuestion();
			}

		});

		Button wrongButton = new Button(context);
		wrongButton.setText("wrong");
		wrongButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mCurrentCommand.anwseredWrongly();
				nextQuestion();
			}

		});

		ll.addView(correctButton);
		ll.addView(wrongButton);
		root.addView(ll);

		// prepare system
		QuestioningSystem system = new QuestioningSystem();
		QuestioningAlgorithmBuildable builder = new SimpleAlgorithmBuilderTypeA();
		QuestioningAlgorithm algorithm = AlgorithmConstructor
				.construct(builder);

		system.setQuestioningAlgorithm(algorithm);
		system.setFilteringAlgorithm(new PassEverythingFilter());

		mLibrarySystem = new LibrarySystem(dir, ROOT_FILE);
		mLibrarySystem.setPhraseFactory(phraseFactory);
		Collection<PhraseSet> sets = mLibrarySystem.getAllSets();

		PhraseSet testSet = (PhraseSet) sets.toArray()[0];
		system.startNewSession(testSet);
		mQuestioningSystem = system;
		nextQuestion();

	}

	private void nextQuestion() {
		mCurrentCommand = mQuestioningSystem.getCurrentStep();
		if (mCurrentCommand == null) {
			Toast.makeText(context, "No more questions!", Toast.LENGTH_LONG)
					.show();
			PhraseSet set = mQuestioningSystem.endSession();
			PhraseWriter writer = mLibrarySystem.getWriter(set);
			writer.close();
			return;
		}

		// this will be done somewhere remotely, probably a factory will provide
		// proper function
		mCurrentID
				.setText("Current question ID: "
						+ ((SimpleQuestioningAlgorithm.QuestioningCommand) mCurrentCommand)
								.getQuestioningInformation().getID());

		mQuestionCount.setText("Questions left: "
				+ mQuestioningSystem.getQuestionsLeft());
	}
}
