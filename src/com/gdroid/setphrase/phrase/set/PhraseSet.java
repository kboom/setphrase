package com.gdroid.setphrase.phrase.set;

import java.io.Serializable;

/**
 * All phrase sets must implement this interface. It is a common identifier for
 * target phrases from a particular group. It is the only object used for
 * loading, modifying and storing {@link com.gdroid.sayit.phrase.TargetPhrase}.
 * Use {@link com.gdroid.setphrase.library.LibrarySystem } for all operations.
 * 
 * @author kboom
 */
public interface PhraseSet extends Serializable {

	/**
	 * Obtain unique identifier of this set.
	 * 
	 * @return
	 */
	int getID();

	/**
	 * Get title of this set.
	 * 
	 * @return
	 */
	String getTitle();

	/**
	 * Get the number of {@link com.gdroid.sayit.phrase.TargetPhrase} stored in
	 * this set.
	 * 
	 * @return
	 */
	int getQuestionCount();

}
