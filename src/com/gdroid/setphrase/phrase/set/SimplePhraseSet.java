package com.gdroid.setphrase.phrase.set;

import java.util.Date;

import com.gdroid.setphrase.questioningsystem.actionabledata.QuestioningDataStoreable;
import com.gdroid.utils.SLog;

/**
 * 
 * @author kboom
 */
public class SimplePhraseSet implements PhraseSet {

	static {
		SLog.register(com.gdroid.setphrase.phrase.set.PhraseSet.class);
		SLog.setTag(com.gdroid.setphrase.phrase.set.PhraseSet.class,
				"Phrase set");
	}

	int id;
	String title;
	int qCount, qAsked, qAnwsered;
	Date lastUsedDate, creationDate;
	QuestioningDataStoreable questioningData;

	SimplePhraseSet() {
		// Accessible only by another class in this package.
	}

	@Override
	public int getID() {
		return id;
	}

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public int getQuestionCount() {
		return qCount;
	}

	public int getQuestionsAsked() {
		return qAsked;
	}

	public int getQuestionsAnwsered() {
		return qAnwsered;
	}

	public int questionAsked() {
		return ++qAsked;
	}

	@Override
	public String toString() {
		String result = String.format(
				"[<id=%i><title=%s><qCount=%i><qAsked=%i>"
						+ "<qAnwsered=%i><lastUsedDate=%s><creationDate=%s>]",
				id, title, qCount, qAsked, qAnwsered, lastUsedDate.toString(),
				creationDate.toString());
		return result;
	}
}
