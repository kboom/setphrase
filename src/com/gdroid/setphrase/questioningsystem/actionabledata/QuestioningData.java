package com.gdroid.setphrase.questioningsystem.actionabledata;

import java.util.Comparator;

import com.gdroid.utils.SLog;

/**
 * Data that is self-sortable in terms of some internal field. contains.
 * 
 * @author kboom
 */
public abstract class QuestioningData {

	static {
		SLog.register(QuestioningData.class);
		SLog.setTag(QuestioningData.class, "Questioning data.");
	}

	private int id;
	private int negativeAnwserCount;
	private int positiveAnwserCount;
	private int stackCounter;

	public int getID() {
		return id;
	}

	public int getNegativeAnwserCount() {
		return negativeAnwserCount;
	}

	public int getPositiveAnwserCount() {
		return positiveAnwserCount;
	}

	void incrementStackCounter() {
		stackCounter++;
	}

	void incrementPositiveAnwserCount() {
		positiveAnwserCount++;
	}

	void incrementNegativeAnwserCount() {
		negativeAnwserCount++;
	}

	int getStackCounter() {
		return stackCounter;
	}

	protected void printComparisonInfo(QuestioningData a, QuestioningData b) {
		String info = String.format("Comparing (%d)\t(%d). "
				+ "Stack counter: (%d)\t(%d)", a.getID(), b.getID(),
				a.getStackCounter(), b.getStackCounter());
		SLog.v(this, info);
	}

	protected abstract Comparator getComparator();

	final QuestioningData clone(int id) {
		QuestioningData result = create();
		result.id = id;
		result.negativeAnwserCount = 0;
		result.positiveAnwserCount = 0;
		result.stackCounter = 0;
		return result;
	};

	protected abstract QuestioningData create();
}
