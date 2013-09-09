package com.gdroid.setphrase.questioningsystem;

/**
 * Builder for questioning algorithms. Can build different type of algorithms,
 * so it is not an abstract class with "builder" field.
 * 
 * @author kboom
 */
public interface QuestioningAlgorithmBuildable {
	void buildPositiveAction();

	void buildNegativeAction();

	void buildQuestioningData();

	void buildMore();

	public QuestioningAlgorithm release();
}
