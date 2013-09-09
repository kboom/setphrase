package com.gdroid.setphrase.questioningsystem.actionabledata;

import java.util.Comparator;

/**
 * Implementation of
 * {@link com.gdroid.setphrase.questioningsystem.actionabledata .QuestioningData }
 * that puts unanswered questions at the end.
 * 
 * @author kboom
 */
public class LastUsedSensibleQuestioningData extends QuestioningData {

	@Override
	protected Comparator getComparator() {
		// is it safe?
		final LastUsedSensibleQuestioningData handler = this;
		return new Comparator() {

			@Override
			public int compare(Object a, Object b) {
				LastUsedSensibleQuestioningData dataA = (LastUsedSensibleQuestioningData) a;
				LastUsedSensibleQuestioningData dataB = (LastUsedSensibleQuestioningData) b;

				handler.printComparisonInfo(dataA, dataB);

				if (dataA.getStackCounter() > dataB.getStackCounter())
					return 1;
				else
					return -1;
			}

		};
	}

	@Override
	protected QuestioningData create() {
		return new LastUsedSensibleQuestioningData();
	}

}
