package com.gdroid.setphrase.dictionary;

import java.util.ArrayList;
import java.util.Collection;

import com.gdroid.setphrase.dictionary.extractor.base.TargetPhraseBuilder;
import com.gdroid.setphrase.dictionary.extractor.base.TargetPhraseExtractor;
import com.gdroid.setphrase.dictionary.extractor.buildingstrategy.DumpingStrategy;
import com.gdroid.setphrase.dictionary.extractor.buildingstrategy.SortingStrategy;
import com.gdroid.setphrase.dictionary.request.base.RequestContractable;
import com.gdroid.setphrase.dictionary.request.base.WebRequestlike;
import com.gdroid.setphrase.phrase.Phrase;
import com.gdroid.setphrase.phrase.PhraseFactory;
import com.gdroid.setphrase.phrase.SpeechPart;
import com.gdroid.setphrase.phrase.target.TargetPhrase;
import com.gdroid.system.System;
import com.gdroid.utils.SLog;

/**
 * 
 * @author kboom
 */
public class DictionarySystem extends System {

	static {
		SLog.register(DictionarySystem.class);
		SLog.setTag(DictionarySystem.class, "Dictionary system.");
		SLog.setLevel(DictionarySystem.class, SLog.Level.INFO);
	}

	private DictionaryFactory factory;
	private DumpingStrategy dumpingStrategy;
	private SortingStrategy sortingStrategy;
	private PhraseFactory phraseFactory;

	public DictionarySystem() {
	}

	public void setDictionaryFactory(DictionaryFactory factory) {
		setProviderFactory(factory);
	}

	public void setPhraseFactory(PhraseFactory factory) {
		phraseFactory = factory;
	}

	/**
	 * Sets provider factory. Use
	 * {@link com.gdroid.setphrase.dictionary.WordNetDictionaryFactory} for
	 * actual dictionary or
	 * {@link com.gdroid.setphrase.dictionary.ThesaurusDictionaryFactory} for
	 * anything else.
	 * 
	 * @param factory
	 */
	public final void setProviderFactory(DictionaryFactory factory) {
		this.factory = factory;
	}

	public final void setDumpingStrategy(DumpingStrategy strategy) {
		dumpingStrategy = strategy;
	}

	public final void setSortingStrategy(SortingStrategy strategy) {
		sortingStrategy = strategy;
	}

	public Collection<TargetPhrase> getAllMatchingPhrases(Phrase phrase) {
		SLog.d(this, "Searching matching phrases for: " + phrase.getPhrase());

		String response = getData(phrase);
		ArrayList<TargetPhrase> phrases = getPreparedExtractor().extractAll(
				phrase, response);

		SLog.i(this, "Phrases got: " + phrases.size());
		return phrases;
	}

	public TargetPhrase getAllMatchingPhrases(SpeechPart part, Phrase phrase) {
		return getPreparedExtractor().extract(phrase, part, getData(phrase));
	}

	public TargetPhraseExtractor getTargetExtractor() {
		return factory.createTargetExtractor();
	}

	public RequestContractable getWebContractor() {
		return factory.createWebContractor();
	}

	private TargetPhraseExtractor getPreparedExtractor() {

		TargetPhraseBuilder phraseBuilder = phraseFactory.createPhraseBuilder();
		TargetPhraseExtractor extractor = getTargetExtractor();
		phraseBuilder.setDumpingStrategy(dumpingStrategy);
		phraseBuilder.setSortingStrategy(sortingStrategy);
		extractor.setPhraseBuilder(phraseBuilder);

		return extractor;
	}

	private String getData(Phrase phrase) {

		RequestContractable webContractor = getWebContractor();

		WebRequestlike request = webContractor.getRequest(phrase.getPhrase());
		String response = request.execute();
		return response;
	}

}
