package com.gdroid.setphrase.questioningsystem.actionabledata;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * List implementation of
 * {@link com.gdroid.setphrase.questioningsystem.actionabledata.QuestioningDataStoreable }
 * . Needs to be refreshed on each change by calling {@link #refresh() }.
 * 
 * @author kboom
 */
public class QuestioningDataList implements QuestioningDataStoreable {

	private QuestioningData mPrototype;
	private List<QuestioningData> mData;

	public QuestioningDataList(QuestioningData prototype) {
		mPrototype = prototype;
		mData = new ArrayList<QuestioningData>();
	}

	@Override
	public QuestioningData get(int pos) {
		QuestioningData data = mData.get(pos);
		return mData.get(pos);
	}

	@Override
	public int remove(QuestioningData data) {
		int id = data.getID();
		mData.remove(data);
		return id;
	}

	@Override
	public QuestioningData add(int id) {
		QuestioningData result = mPrototype.clone(id);
		mData.add(result);
		return result;
	}

	@Override
	public void clear() {
		mData.clear();
	}

	@Override
	public void refresh() {
		Collections.sort(mData, mPrototype.getComparator());
	}

	@Override
	public int size() {
		return mData.size();
	}

	@Override
	public Iterator iterator() {
		return new Iterator() {
			int pos = 0;

			@Override
			public boolean hasNext() {
				return mData.size() > pos ? true : false;
			}

			@Override
			public Object next() {
				return mData.get(pos++);
			}

			@Override
			public void remove() {
				mData.remove(pos);
			}

		};
	}

}
