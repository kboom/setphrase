package com.gdroid.setphrase.questioningsystem;

import com.gdroid.setphrase.phrase.PhraseFactory;
import com.gdroid.setphrase.phrase.set.PhraseSet;
import com.gdroid.setphrase.phrase.set.PhraseSetManager;
import com.gdroid.setphrase.questioningsystem.actionabledata.QuestioningDataStoreable;
import com.gdroid.setphrase.questioningsystem.filter.QuestionFilterable;
import com.gdroid.system.System;
import com.gdroid.utils.SLog;

/**
 * A system providing some asking strategies. Does not operate on phrases. Is
 * capable of saving and restoring a session later. This is done by writing some
 * data into target phrase.
 * 
 * @author kboom
 */
public class QuestioningSystem extends System {

	static {
		SLog.register(QuestioningSystem.class);
		SLog.setTag(QuestioningSystem.class, "Questioning system test.");
	}

	private QuestioningAlgorithm mQuestioningAlgorithm;
	private QuestionFilterable mFilteringAlgorithm;
	private PhraseFactory phraseFactory;
	private PhraseSet activeSet;

	/**
	 * Creates the questioning system. Can be instantiated only once.
	 */
	public QuestioningSystem() {

	}

	/**
	 * Sets a questioning algorithm that defines questioning logic. An algorithm
	 * can be tuned but it is not done within this system.
	 * 
	 * @param algorithm
	 */
	public void setQuestioningAlgorithm(QuestioningAlgorithm algorithm) {
		mQuestioningAlgorithm = algorithm;
	}

	public void setFilteringAlgorithm(QuestionFilterable algorithm) {
		mFilteringAlgorithm = algorithm;
	}

	public void setPhraseFactory(PhraseFactory factory) {
		phraseFactory = factory;
	}

	/**
	 * Starts a new session for a set given. Remember to call
	 * {@link #endSession() } after questioning ends to get updated
	 * {@link com.gdroid.sayit.phrase.PhraseSet }.
	 * 
	 * @param set
	 * @return
	 */
	public void startNewSession(PhraseSet set) {
		SLog.d(this, "Starting new session...");
		activeSet = set;
		mQuestioningAlgorithm.reset();
		QuestioningDataStoreable data = mQuestioningAlgorithm.getDataSource();

		for (int i = 0; i < set.getQuestionCount(); i++) {
			if (mFilteringAlgorithm.filter(i))
				data.add(i);
		}
		SLog.i(this, "New session successfully started.");
	}

	/**
	 * Restores most recent previous session for a given
	 * {@link com.gdroid.sayit.phrase.PhraseSet }. Remember to call
	 * {@link #endSession() } after questioning ends to get updated
	 * {@link com.gdroid.sayit.phrase.PhraseSet }.
	 * 
	 * @param set
	 * @return {@code true } if a state was restored, {@code false } otherwise
	 */
	public boolean restoreSession(PhraseSet set) {
		PhraseSetManager manager = phraseFactory
				.createPhraseSetManager(activeSet);
		QuestioningDataStoreable data = manager.loadQuestioningState();
		if (data != null) {
			mQuestioningAlgorithm.setDataSource(data);
			return true;
		} else
			return false;
	}

	/**
	 * Ends session returning an updated PhraseSet. It should always be called
	 * after questioning is done.
	 * 
	 * @return
	 */
	public PhraseSet endSession() {
		PhraseSetManager manager = phraseFactory
				.createPhraseSetManager(activeSet);
		manager.saveQuestioningState(mQuestioningAlgorithm.getDataSource());
		return activeSet;
	}

	/**
	 * Use to obtain next step of the questioning.
	 * 
	 * @return next step of the algorithm in the form of command
	 */
	public QuestioningCommandable getCurrentStep() {
		SLog.v(this, "Next step request.");
		if (getQuestionsLeft() < 1)
			return null;
		return mQuestioningAlgorithm.getNextCommand();
	}

	public int getQuestionsLeft() {
		return mQuestioningAlgorithm.getDataSource().size();
	}

}
