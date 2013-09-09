package com.gdroid.setphrase.dictionary.extractor.base;

import java.util.ArrayList;

import com.gdroid.setphrase.phrase.Phrase;
import com.gdroid.setphrase.phrase.SpeechPart;
import com.gdroid.setphrase.phrase.target.TargetPhrase;

/**
 * For pure-soap payloads. Parses pure soap response (use other parsers when it
 * is soap response but with string-only payload which can be any type - plan,
 * json or xml)
 * 
 * 
 * Na razie sobie podaruję, bo jest to dziwny typ odpowiedzi. Jeśli kiedyś
 * trzeba byłoby to wykorzystać, to wystarczy dopisać. Interfejs cały jest
 * bowiem gotowy!
 * 
 * @author kboom
 */
public class SOAPExtractor extends TargetPhraseExtractor {

	@Override
	public TargetPhrase extract(Phrase phrase, SpeechPart part, String response) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public ArrayList<TargetPhrase> extractAll(Phrase phrase, String response) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

}
