package com.gdroid.setphrase.questioningsystem;

import com.gdroid.setphrase.questioningsystem.actionabledata.LastUsedSensibleQuestioningData;
import com.gdroid.setphrase.questioningsystem.actionabledata.QuestioningData;
import com.gdroid.setphrase.questioningsystem.actionabledata.QuestioningDataList;
import com.gdroid.setphrase.questioningsystem.actionabledata.SimpleNegativeAction;
import com.gdroid.setphrase.questioningsystem.actionabledata.SimplePositiveAction;

/**
 * Builder of
 * {@link com.gdroid.setphrase.questioningsystem.ChoicedQuestioningAlgorithm }
 * with
 * {@link com.gdroid.setphrase.questioningsystem.actionabledata.SimplePositiveAction }
 * and
 * {@link com.gdroid.setphrase.questioningsystem.actionabledata.SimpleNegativeAction }
 * .
 * 
 * @author kboom
 */
public class ChoicedAlgorithmBuilderTypeA {

	private final ChoicedQuestioningAlgorithm mAlgorithm;

	public ChoicedAlgorithmBuilderTypeA(int choiceCount) {
		mAlgorithm = new ChoicedQuestioningAlgorithm(choiceCount);
	}

	public void buildPositiveAction() {
		mAlgorithm.setPositiveAction(new SimplePositiveAction());
	}

	public void buildNegativeAction() {
		mAlgorithm.setNegativeAction(new SimpleNegativeAction());
	}

	public void buildQuestioningData() {
		QuestioningData prototype = new LastUsedSensibleQuestioningData();
		QuestioningDataList data = new QuestioningDataList(prototype);
		mAlgorithm.setDataSource(data);
	}

	public void buildMore() {

	}

	public QuestioningAlgorithm release() {
		return mAlgorithm;
	}
}
