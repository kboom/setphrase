package com.gdroid.setphrase.questioningsystem.actionabledata;

/**
 * Base for all negative actions. This usually means an user clicked wrong
 * answer. Extending class should implement
 * {@link #execute(com.gdroid.sayit .questioningsystem.actionabledata.QuestioningData) }
 * adding some specific functionality. Stack counter is already incremented so
 * is the negative answer count.
 * 
 * @author kboom
 */
public abstract class NegativeAction implements ResultActionable {

	@Override
	public void execute(QuestioningDataStoreable source, int pos) {
		QuestioningData data = source.get(pos);
		data.incrementNegativeAnwserCount();
		data.incrementStackCounter();
		execute(data);
	}

	protected abstract void execute(QuestioningData data);

}
