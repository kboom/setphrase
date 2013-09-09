package com.gdroid.setphrase.questioningsystem;

/**
 * A constructor capable of creating any of
 * {@link com.gdroid.setphrase.questioningsystem.QuestioningAlgorithm }.
 * 
 * @author kboom
 */
public class AlgorithmConstructor {

	private AlgorithmConstructor() {
	}

	/**
	 * Constructs any implementation of
	 * {@link com.gdroid.setphrase.questioningsystem.QuestioningAlgorithm }
	 * specified by
	 * {@link com.gdroid.sayit.questioningsystem.SimpleAlgorithmBuilder } given.
	 * 
	 * @param builder
	 *            used for algorithm creation
	 * @return
	 */
	public static QuestioningAlgorithm construct(
			QuestioningAlgorithmBuildable builder) {
		builder.buildNegativeAction();
		builder.buildPositiveAction();
		builder.buildQuestioningData();
		builder.buildMore();
		return builder.release();
	}
}
