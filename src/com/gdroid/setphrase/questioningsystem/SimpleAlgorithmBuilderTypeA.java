package com.gdroid.setphrase.questioningsystem;

import com.gdroid.setphrase.questioningsystem.actionabledata.LastUsedSensibleQuestioningData;
import com.gdroid.setphrase.questioningsystem.actionabledata.QuestioningData;
import com.gdroid.setphrase.questioningsystem.actionabledata.QuestioningDataList;
import com.gdroid.setphrase.questioningsystem.actionabledata.SimpleNegativeAction;
import com.gdroid.setphrase.questioningsystem.actionabledata.SimplePositiveAction;

/**
 * Builder of
 * {@link com.gdroid.setphrase.questioningsystem.SimpleQuestioningAlgorithm }
 * with
 * {@link com.gdroid.setphrase.questioningsystem.actionabledata.SimplePositiveAction }
 * and
 * {@link com.gdroid.setphrase.questioningsystem.actionabledata.SimpleNegativeAction }
 * .
 * 
 * @author kboom
 */
public class SimpleAlgorithmBuilderTypeA implements
		QuestioningAlgorithmBuildable {

	private final SimpleQuestioningAlgorithm mAlgorithm;

	public SimpleAlgorithmBuilderTypeA() {
		mAlgorithm = new SimpleQuestioningAlgorithm();
	}

	@Override
	public void buildPositiveAction() {
		mAlgorithm.setPositiveAction(new SimplePositiveAction());
	}

	@Override
	public void buildNegativeAction() {
		mAlgorithm.setNegativeAction(new SimpleNegativeAction());
	}

	@Override
	public void buildQuestioningData() {
		QuestioningData prototype = new LastUsedSensibleQuestioningData();
		QuestioningDataList data = new QuestioningDataList(prototype);
		mAlgorithm.setDataSource(data);
	}

	@Override
	public void buildMore() {
		// nothing more
	}

	@Override
	public QuestioningAlgorithm release() {
		return mAlgorithm;
	}

}
