package com.gdroid.setphrase.questioningsystem.actionabledata;

/**
 * Interface for all actions available to be performed on each
 * {@link com.gdroid.setphrase.questioningsystem.actionabledata.QuestioningData }
 * 
 * @author kboom
 */
public interface ResultActionable {
	void execute(QuestioningDataStoreable source, int pos);
}
