/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdroid.setphrase.phrase.tools;

import com.gdroid.setphrase.library.LibrarySystem;
import com.gdroid.setphrase.phrase.set.PhraseSetManager;

/**
 * 
 * @author kboom
 */
public interface LibraryTools {
	void onWriteAction(LibrarySystem system, PhraseSetManager set);

	void onReadAction(LibrarySystem system, PhraseSetManager set);
}
