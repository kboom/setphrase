/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdroid.setphrase.dictionary.extractor.phrasebuilder;

import java.util.ArrayList;

import com.gdroid.setphrase.dictionary.extractor.base.TargetPhraseBuilder;
import com.gdroid.setphrase.phrase.Phrase;
import com.gdroid.setphrase.phrase.SimplePhrase;
import com.gdroid.setphrase.phrase.target.SimpleTargetPhrase;
import com.gdroid.setphrase.phrase.target.TargetPhrase;

/**
 * 
 * @author kboom
 */
public class SimpleTargetPhraseBuilder extends TargetPhraseBuilder {

	@Override
	public TargetPhrase build() {
		SimpleTargetPhrase result = new SimpleTargetPhrase(super.getPhrase(),
				super.getSpeechPart());
		if (super.getSynonymNameList() != null) {
			super.getDumpingStrategy().filter(super.getSynonymNameList());

			ArrayList<Phrase> phraseList = new ArrayList<Phrase>();
			for (String s : super.getSynonymNameList()) {
				phraseList.add(new SimplePhrase(s));
			}

			result.addSynonym(phraseList);
		}
		if (super.getDefinition() != null)
			result.setDefinition(super.getDefinition());

		// loose all references to built objects
		reset();
		return result;

	}

}
