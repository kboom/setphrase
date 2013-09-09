/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdroid.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * 
 * @author kboom
 */
public abstract class Tools {
	public static <T> Collection<T> convert(T[] what) {
		Collection<T> result = new ArrayList();
		result.addAll(Arrays.asList(what));
		return result;
	}
}
