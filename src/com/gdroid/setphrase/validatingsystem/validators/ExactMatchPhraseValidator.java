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
public class ExactMatchPhraseValidator implements PhraseValidator {

	@Override
	public float validate(TargetPhrase tphrase, CapturedPhrase cphrase) {
		if (tphrase.getPhrase().equalsIgnoreCase(cphrase.getPhrase()))
			return 1f;
		else
			return 0f;
	}

}
