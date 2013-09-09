package com.gdroid.setphrase.questioningsystem.filter;

/**
 * Implementation of {@link #super } that passes everything it gets.
 * 
 * @author kboom
 */
public class PassEverythingFilter implements QuestionFilterable {

	@Override
	public boolean filter(int id) {
		return true;
	}

}
