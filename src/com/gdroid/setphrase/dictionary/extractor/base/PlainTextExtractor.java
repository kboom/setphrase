package com.gdroid.setphrase.dictionary.extractor.base;

import java.util.ArrayList;

import com.gdroid.setphrase.phrase.Phrase;
import com.gdroid.setphrase.phrase.SpeechPart;
import com.gdroid.setphrase.phrase.target.TargetPhrase;

/**
 * For plain text payloads.
 * 
 * @author kboom
 */
public class PlainTextExtractor extends TargetPhraseExtractor {

	@Override
	public TargetPhrase extract(Phrase phrase, SpeechPart part, String response) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public ArrayList<TargetPhrase> extractAll(Phrase phrase, String response) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

}
