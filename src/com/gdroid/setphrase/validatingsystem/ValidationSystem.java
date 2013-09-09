package com.gdroid.setphrase.validatingsystem;

import com.gdroid.setphrase.phrase.CapturedPhrase;
import com.gdroid.setphrase.phrase.target.TargetPhrase;
import com.gdroid.setphrase.validatingsystem.validators.PhraseValidator;
import com.gdroid.system.System;

/**
 * 
 * @author kboom
 */
public class ValidationSystem extends System {

	private float successLevel;
	private PhraseValidator validator;

	public ValidationSystem() {

	}

	public void setValidator(PhraseValidator val) {
		validator = val;
	}

	public void setSuccessLevel(float level) {
		successLevel = level;
	}

	public boolean validateMatch(TargetPhrase tphrase, CapturedPhrase cphrase) {
		if (validator.validate(tphrase, cphrase) > successLevel)
			return true;
		else
			return false;
	}
}
