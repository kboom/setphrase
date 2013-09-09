package com.gdroid.setphrase.questioningsystem.actionabledata;

/**
 * Base for all positive actions. This usually means an user put in correct
 * answer. Extending class should implement
 * {@link #execute(com.gdroid.sayit .questioningsystem.actionabledata.QuestioningData) }
 * adding some specific functionality. Stack counter is already incremented so
 * is the positive answer count.
 * 
 * @author kboom
 */
public abstract class PositiveAction implements ResultActionable {

	private QuestioningDataStoreable mSource;

	@Override
	public void execute(QuestioningDataStoreable source, int pos) {
		mSource = source;
		QuestioningData data = source.get(pos);
		data.incrementPositiveAnwserCount();
		data.incrementStackCounter();
		execute(data);
	}

	protected void remove(QuestioningData data) {
		mSource.remove(data);
	}

	protected abstract void execute(QuestioningData data);

}
