package com.gdroid.setphrase.questioningsystem;

import com.gdroid.setphrase.questioningsystem.actionabledata.QuestioningData;

/**
 * Implementation of
 * {@link com.gdroid.setphrase.questioningsystem.QuestioningAlgorithm }
 * supporting multiple choice.
 * 
 * @author kboom
 */
public class ChoicedQuestioningAlgorithm extends QuestioningAlgorithm {

	private int mChoiceCount;

	ChoicedQuestioningAlgorithm(int count) {
		mChoiceCount = count;
	}

	@Override
	protected Command getCommand() {
		Command result = new QuestioningCommand(this);
		return result;
	}

	public class QuestioningCommand extends Command {
		// only single data is being hold
		private final QuestioningData[] mData;

		private QuestioningCommand(ChoicedQuestioningAlgorithm subject) {
			super(subject);
			mData = new QuestioningData[mChoiceCount];
			for (int i = 0; i < mChoiceCount; i++) {
				mData[i] = subject.getDataSource().get(i);
			}
		};

		public QuestioningData[] getQuestioningInformation() {
			return mData;
		}
	}

}
