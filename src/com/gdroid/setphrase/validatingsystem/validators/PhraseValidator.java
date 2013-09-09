/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdroid.setphrase.validatingsystem.validators;

import com.gdroid.setphrase.phrase.CapturedPhrase;
import com.gdroid.setphrase.phrase.target.TargetPhrase;

/**
 * 
 * @author kboom
 */
public interface PhraseValidator {
	/**
	 * Compares two phrases given and returns match ratio contained in [0,1].
	 * 
	 * @param tphrase
	 *            a phrase being compared with
	 * @param cphrase
	 *            a phrase being compared to
	 * @return
	 */
	float validate(TargetPhrase tphrase, CapturedPhrase cphrase);
}
