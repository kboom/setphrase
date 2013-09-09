package com.gdroid.setphrase.questioningsystem;

import com.gdroid.setphrase.questioningsystem.actionabledata.QuestioningDataStoreable;
import com.gdroid.setphrase.questioningsystem.actionabledata.ResultActionable;
import com.gdroid.utils.SLog;

/**
 * Base for all algorithms. Does not decide about the logic.
 * 
 * @author kboom
 */
public abstract class QuestioningAlgorithm {

	static {
		SLog.register(QuestioningAlgorithm.class);
		SLog.setTag(QuestioningAlgorithm.class, "Questioning algorithm.");
	}

	private ResultActionable mPositiveAction;
	private ResultActionable mNegativeAction;
	// private ResultActionable mNeutralAction in Choiced (...)
	private QuestioningDataStoreable mDataSource;
	private Command mLastCommand;
	private int mActionCount;

	public QuestioningAlgorithm() {

	}

	void setPositiveAction(ResultActionable positive) {
		mPositiveAction = positive;
	}

	void setNegativeAction(ResultActionable negative) {
		mNegativeAction = negative;
	}

	void setDataSource(QuestioningDataStoreable source) {
		mDataSource = source;
	}

	public void reset() {
		mDataSource.clear();
		mActionCount = 1;
		mLastCommand = new Command(this) {
		};
	}

	protected QuestioningDataStoreable getDataSource() {
		return mDataSource;
	}

	protected void performPositiveAction() {
		mActionCount++;
		mPositiveAction.execute(mDataSource, 0);
	}

	protected void performNegativeAction() {
		mActionCount++;
		mNegativeAction.execute(mDataSource, 0);
	}

	protected int assignCommandId() {
		return mActionCount;
	}

	QuestioningCommandable getNextCommand() {
		if (mLastCommand == null || mLastCommand.getCommandID() < mActionCount) {
			SLog.d(this, "Next command request is valid, generating new one...");
			mDataSource.refresh();
			return getCommand();
		} else {
			SLog.d(this, "Next command request is invalid, "
					+ "returining previous command...");
			return mLastCommand;
		}
	}

	protected abstract Command getCommand();

	/**
	 * All subclasses should create concrete implementation of this class. It
	 * should contain some data fields, extracted from data source according to
	 * algorithm type, and provide proper access to them.
	 */
	protected abstract class Command implements QuestioningCommandable {
		private QuestioningAlgorithm mSubject;
		private int mId;

		protected Command(QuestioningAlgorithm subject) {
			mSubject = subject;
		}

		@Override
		public void anwseredCorrectly() {
			mSubject.performPositiveAction();
		}

		@Override
		public void anwseredWrongly() {
			mSubject.performNegativeAction();
		}

		@Override
		public int getCommandID() {
			return mId;
		}

	}
}
