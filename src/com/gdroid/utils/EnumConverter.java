/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdroid.utils;

/**
 * 
 * @author kboom
 */
public interface EnumConverter<E extends Enum<E> & EnumConverter<E>> {
	int convert();

	E convert(int val);
}
