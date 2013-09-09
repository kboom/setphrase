/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdroid.setphrase.dictionary.request;

/**
 * 
 * @author kboom
 */
public class WordNetRequestContractor extends DictServiceRequestContractor {

	private static final String SERVICE_ID = "wn";

	@Override
	public String getDictID() {
		return SERVICE_ID;
	}

}
