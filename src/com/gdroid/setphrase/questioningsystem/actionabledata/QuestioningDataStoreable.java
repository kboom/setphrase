package com.gdroid.setphrase.questioningsystem.actionabledata;

/**
 * Object capable of storing
 * {@link com.gdroid.setphrase.questioningsystem.actionabledata.QuestioningData }
 * .
 * 
 * @author kboom
 */
public interface QuestioningDataStoreable extends Iterable {
	/**
	 * Gets data stored at specified location. The lowest the position is, the
	 * earlier it will be used for questioning.
	 * 
	 * @param pos
	 *            position of a question
	 * @return QuestioningData
	 */
	QuestioningData get(int pos);

	/**
	 * Remove a question from the list
	 * 
	 * @param data
	 *            data to be removed
	 * @return id of a question removed
	 */
	int remove(QuestioningData data);

	/**
	 * Adds a question to the list.
	 * 
	 * @param id
	 *            id of a question added
	 */
	QuestioningData add(int id);

	/**
	 * Removes everything from the list.
	 */
	public void clear();

	/**
	 * Refreshes the list. Call this every time an item was added or modified.
	 */
	public void refresh();

	/**
	 * Returns number of elements stored.
	 * 
	 * @return
	 */
	public int size();

}
