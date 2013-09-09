package com.gdroid.setphrase.questioningsystem;

import com.gdroid.setphrase.questioningsystem.actionabledata.QuestioningData;

/**
 * Does not support multiple choice questions. Perfect for voice recognition
 * tests. Does not decide about algorithm logic.
 * 
 * @author kboom
 */
public class SimpleQuestioningAlgorithm extends QuestioningAlgorithm {

	/**
	 * Data source must be set up.
	 * 
	 * @param positiveAction
	 *            action to be performed on correct answer
	 * @param negativeAction
	 *            action to be performed on wrong answer
	 * @param dataSource
	 *            data source and maintainer
	 */
	SimpleQuestioningAlgorithm() {

	}

	@Override
	protected Command getCommand() {
		Command result = new QuestioningCommand(this);
		return result;
	}

	public class QuestioningCommand extends Command {
		// only single data is being hold
		private QuestioningData mData;

		private QuestioningCommand(SimpleQuestioningAlgorithm subject) {
			super(subject);
			mData = subject.getDataSource().get(0);
		}

		public QuestioningData getQuestioningInformation() {
			return mData;
		}

	}
}
