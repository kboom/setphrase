package com.gdroid.setphrase.dictionary;

import com.gdroid.setphrase.dictionary.extractor.base.TargetPhraseExtractor;
import com.gdroid.setphrase.dictionary.request.base.RequestContractable;

/**
 * 
 * @author kboom
 */
public abstract class DictionaryFactory {
	/**
	 * Creates instance of a class used for extracting data from responses.
	 * 
	 * @return
	 */
	public abstract TargetPhraseExtractor createTargetExtractor();

	/**
	 * Creates instance of a class used for building proper requests.
	 * 
	 * @return
	 */
	public abstract RequestContractable createWebContractor();
}
