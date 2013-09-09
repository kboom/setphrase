package com.gdroid.cacheing.naming;

/**
 * 
 * @author kboom
 */
public interface NameGeneratable {
	/** Generates unique file name for image defined by URI */
	public abstract String generate(String id);
}
