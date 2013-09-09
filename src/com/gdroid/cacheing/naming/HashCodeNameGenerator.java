/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdroid.cacheing.naming;

/**
 * 
 * @author kboom
 */
public class HashCodeNameGenerator implements NameGeneratable {
	@Override
	public String generate(String id) {
		return String.valueOf(id.hashCode());
	}
}
