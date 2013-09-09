package com.gdroid.setphrase.dictionary.request.base;

/**
 * Contractors building requests. Differ for each provider.
 * 
 * @author kboom
 */
public interface RequestContractable {
	public WebRequestlike getRequest(String toSearch);
}
