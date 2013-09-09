package com.gdroid.setphrase.phrase.set;

import java.util.Date;

import com.gdroid.setphrase.questioningsystem.actionabledata.QuestioningDataStoreable;

/**
 * 
 * @author kboom
 */
public class SimplePhraseSetManager implements PhraseSetManager {

	SimplePhraseSet mSet;

	/**
	 * Creates new set.
	 */
	public SimplePhraseSetManager() {
		mSet = new SimplePhraseSet();
		mSet.title = "undefined";
		mSet.id = -1;
		mSet.lastUsedDate = new Date();
		mSet.creationDate = mSet.lastUsedDate;
		mSet.qAnwsered = 0;
		mSet.qAsked = 0;
		mSet.qCount = 0;
	}

	/**
	 * Wraps {@link com.gdroid.sayit.phrase.SimplePhraseSet} for modification.
	 * 
	 * @param set
	 */
	public SimplePhraseSetManager(SimplePhraseSet set) {
		mSet = set;
	}

	@Override
	public void setID(int id) {
		mSet.id = id;
	}

	@Override
	public void setTitle(String title) {
		mSet.title = title;
	}

	@Override
	public void setQuestionCount(int count) {
		mSet.qCount = count;
	}

	@Override
	public PhraseSet getPhraseSet() {
		return mSet;
	}

	@Override
	public void saveQuestioningState(QuestioningDataStoreable data) {
		mSet.questioningData = data;
	}

	@Override
	public QuestioningDataStoreable loadQuestioningState() {
		return mSet.questioningData;
	}

	public void setQuestionsAsked(int times) {
		mSet.qAsked = times;
	}

	public void setQuestionsAnwsered(int times) {
		mSet.qAnwsered = times;
	}

}
