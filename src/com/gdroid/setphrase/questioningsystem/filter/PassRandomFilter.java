package com.gdroid.setphrase.questioningsystem.filter;

import java.util.Random;

/**
 * 
 * @author kboom
 */
public class PassRandomFilter implements QuestionFilterable {

	private final Random generator = new Random();

	@Override
	public boolean filter(int id) {
		return generator.nextBoolean();
	}

}
