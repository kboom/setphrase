package com.gdroid.setphrase.dictionary;

import com.gdroid.setphrase.dictionary.extractor.ThesaurusExtractor;
import com.gdroid.setphrase.dictionary.extractor.base.TargetPhraseExtractor;
import com.gdroid.setphrase.dictionary.request.ThesaurusRequestContractor;
import com.gdroid.setphrase.dictionary.request.base.RequestContractable;

/**
 * Note that this is not hosted by anyone. It is independent from any other
 * services (this methods inherits directly from general interfaces)
 * 
 * @author kboom
 */
public class ThesaurusDictionaryFactory extends DictionaryFactory {

	@Override
	public TargetPhraseExtractor createTargetExtractor() {
		return new ThesaurusExtractor();
	}

	@Override
	public RequestContractable createWebContractor() {
		return new ThesaurusRequestContractor();
	}

}
