/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdroid.setphrase.questioningsystem;

/**
 * 
 * @author kboom
 */
public interface QuestioningCommandable {
	void anwseredCorrectly();

	void anwseredWrongly();

	int getCommandID();
}
