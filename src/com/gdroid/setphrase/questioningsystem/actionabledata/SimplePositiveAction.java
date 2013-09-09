package com.gdroid.setphrase.questioningsystem.actionabledata;

/**
 * Simplest positive action. No more operations are performed than in {@link
 * #super }
 * 
 * @author kboom
 */
public class SimplePositiveAction extends PositiveAction {

	@Override
	protected void execute(QuestioningData data) {
		if (data.getPositiveAnwserCount() > data.getNegativeAnwserCount())
			super.remove(data);
	}

}
