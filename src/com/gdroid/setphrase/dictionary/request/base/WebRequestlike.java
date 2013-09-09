package com.gdroid.setphrase.dictionary.request.base;

/**
 * Interface for obtaining payload through different request types.
 * 
 * @author kboom
 */
public interface WebRequestlike {

	/**
	 * Executes request and returns its payload. Both request type and payload
	 * type can differ, so nearly each combination can be handled. It is vitally
	 * important to select proper parser for returned payload.
	 * {@link com.gdroid.setphrase.dictionary.DictionaryFactory} should provide
	 * valid pairs.
	 * 
	 * @return
	 */
	public String execute();
}
