package com.gdroid.storage.object;

import java.io.File;

/**
 * 
 * @author kboom
 */
public class StorageOperationException extends Exception {

	/**
	 * All general causes for storage operation to fail. {@link #NOT_READABLE
	 * resource is corrupted} {@link #NOT_FOUND resource not found}
	 * {@link #NOT_READABLE resource content is not known to this
	 * implementation}
	 */
	public enum Cause {
		NOT_FOUND, NOT_READABLE, NOT_STOREABLE
	}

	// general cause for this problem
	private Cause gCause;

	public StorageOperationException(Cause gCause, Throwable tCause) {
		this(gCause, "-not specified-", tCause);
	}

	public StorageOperationException(Cause gCause, String message,
			Throwable tCause) {
		super(message, tCause);
		this.gCause = gCause;
	}

	/**
	 * This is how the clients can know which type of error has occured. The
	 * reason for this is different implementation could throw different
	 * specific errors, which would lead to make interface very complicated. It
	 * is better to gather them.
	 * 
	 * @return
	 */
	public Cause getGeneralCause() {
		return gCause;
	}

	public Object getResource() {
		return new File("dupa");
	}

}
