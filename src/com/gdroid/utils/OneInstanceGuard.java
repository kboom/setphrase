package com.gdroid.utils;

/**
 * Ensures that there's only one instance of a given class.
 * 
 * @author kboom
 */
public class OneInstanceGuard {
	private static boolean isInstantiated = false;

	public OneInstanceGuard() {
		/*
		 * if(isInstantiated) throw new IllegalStateException();
		 */

		isInstantiated = true;
	}
}
