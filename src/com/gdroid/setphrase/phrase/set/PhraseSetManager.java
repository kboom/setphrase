/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdroid.setphrase.phrase.set;

import com.gdroid.setphrase.questioningsystem.actionabledata.QuestioningDataStoreable;

/**
 * A wrapping interface for {@link com.gdroid.sayit.phrase.PhraseSet } providing
 * creation and modification methods. Should be used only inside
 * {@link com.gdroid.setphrase.library}.
 * 
 * @author kboom
 */
public interface PhraseSetManager {
	void setID(int id);

	void setTitle(String title);

	void setQuestionCount(int count);

	void saveQuestioningState(QuestioningDataStoreable data);

	/**
	 * Get questioning data. It should not be really used outside
	 * {@link com.gdroid.setphrase.questioningsystem }
	 * 
	 * @return
	 */
	QuestioningDataStoreable loadQuestioningState();

	PhraseSet getPhraseSet();
}
