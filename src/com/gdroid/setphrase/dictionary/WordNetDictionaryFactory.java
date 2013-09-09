package com.gdroid.setphrase.dictionary;

import com.gdroid.setphrase.dictionary.extractor.WordNetExtractor;
import com.gdroid.setphrase.dictionary.extractor.base.TargetPhraseExtractor;
import com.gdroid.setphrase.dictionary.request.WordNetRequestContractor;
import com.gdroid.setphrase.dictionary.request.base.RequestContractable;

/**
 * Note that this is hosted by DictService. What it means is that both extractor
 * and contractor do not directly inherit proper interfaces.
 * 
 * @author kboom
 */
public class WordNetDictionaryFactory extends DictionaryFactory {

	@Override
	public TargetPhraseExtractor createTargetExtractor() {
		return new WordNetExtractor();
	}

	@Override
	public RequestContractable createWebContractor() {
		return new WordNetRequestContractor();
	}

}
