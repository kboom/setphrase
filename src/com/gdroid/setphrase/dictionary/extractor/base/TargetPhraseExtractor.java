package com.gdroid.setphrase.dictionary.extractor.base;

import java.util.ArrayList;

import com.gdroid.setphrase.phrase.Phrase;
import com.gdroid.setphrase.phrase.SpeechPart;
import com.gdroid.setphrase.phrase.target.TargetPhrase;
import com.gdroid.utils.SLog;

/**
 * Processes payload according to its type. Supported formats so far: PlainText,
 * JSON, XML to be written SOAP *
 * 
 * @author kboom
 */
public abstract class TargetPhraseExtractor {

	static {
		SLog.register(TargetPhraseExtractor.class);
		SLog.setTag(TargetPhraseExtractor.class, "Target phrase extractor.");
		SLog.setLevel(TargetPhraseExtractor.class, SLog.Level.INFO);
	}

	private TargetPhraseBuilder phraseBuilder;

	public TargetPhraseExtractor() {

	}

	/**
	 * Set builder for building phrases. It will also filter them.
	 * 
	 * @param builder
	 */
	public void setPhraseBuilder(TargetPhraseBuilder builder) {
		phraseBuilder = builder;
	}

	/**
	 * Build a phrase with given parameters. Can contain null ones.
	 * 
	 * @param phrase
	 * @param part
	 * @param synonyms
	 * @return
	 */
	public TargetPhrase buildPhrase(Phrase phrase, TargetPhraseParameters params) {
		assert phrase != null;
		assert params != null;

		phraseBuilder.init();
		phraseBuilder.setBasePhrase(phrase);
		phraseBuilder.assignToSpeechPart(params.part);
		if (params.synonyms != null)
			phraseBuilder.addSynonym(params.synonyms);
		if (params.definition != null)
			phraseBuilder.setDefinition(params.definition);
		return phraseBuilder.build();
	}

	public abstract TargetPhrase extract(Phrase phrase, SpeechPart part,
			String response);

	public abstract ArrayList<TargetPhrase> extractAll(Phrase phrase,
			String response);

	protected static class TargetPhraseParameters {
		private SpeechPart part;
		private String definition;
		private ArrayList<String> synonyms;
	}

	public TargetPhraseParameters createPhraseParameters(SpeechPart part,
			String def) {
		TargetPhraseParameters tpp = new TargetPhraseParameters();
		tpp.part = part;
		tpp.definition = def;
		return tpp;
	}

	public TargetPhraseParameters createPhraseParameters(SpeechPart part,
			ArrayList<String> synonyms) {
		TargetPhraseParameters tpp = new TargetPhraseParameters();
		tpp.part = part;
		tpp.synonyms = synonyms;
		return tpp;
	}

	public TargetPhraseParameters createPhraseParameters(SpeechPart part,
			String def, ArrayList<String> synonyms) {
		TargetPhraseParameters tpp = new TargetPhraseParameters();
		tpp.part = part;
		tpp.definition = def;
		tpp.synonyms = synonyms;
		return tpp;
	}

}
