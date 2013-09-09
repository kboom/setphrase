package com.gdroid.setphrase.phrase;

import java.io.Serializable;

/**
 * Every possible kind of a phrase implements this interface.
 * 
 * @author kboom
 */
public interface Phrase extends Serializable {
	String getPhrase();
}
