package com.gdroid.setphrase.dictionary.extractor.base;

import java.util.ArrayList;
import java.util.Collection;

import com.gdroid.setphrase.dictionary.extractor.buildingstrategy.DumpingStrategy;
import com.gdroid.setphrase.dictionary.extractor.buildingstrategy.SortingStrategy;
import com.gdroid.setphrase.phrase.Phrase;
import com.gdroid.setphrase.phrase.SpeechPart;
import com.gdroid.setphrase.phrase.target.TargetPhrase;
import com.gdroid.utils.SLog;

/**
 * Builds target phrase with parameters given. Yet not all of it may be used as
 * well as it may appear in different order. Strategies determine this.
 * according to strategies set. <b>An extractor should set every field that he
 * can</b> as there is no option to extract single fields.
 * 
 * @author kboom
 */
public abstract class TargetPhraseBuilder {

	static {
		SLog.register(TargetPhraseBuilder.class);
		SLog.setTag(TargetPhraseBuilder.class, "JSON extractor.");
		SLog.setLevel(TargetPhraseBuilder.class, SLog.Level.INFO);
	}

	private DumpingStrategy dumpingStrategy;
	private SortingStrategy sortingStrategy;

	private Phrase phrase;
	private SpeechPart part;
	private ArrayList<String> synonymNameList;
	private String definition;

	void init() {
		reset();
	}

	void setDefinition(String definition) {
		this.definition = definition;
	}

	void setBasePhrase(Phrase base) {
		phrase = base;
	}

	void assignToSpeechPart(SpeechPart part) {
		this.part = part;
	}

	void addSynonym(Collection<String> synonyms) {
		getSynonyms().addAll(synonyms);
	}

	void addSynonym(String synonym) {
		getSynonyms().add(synonym);
	}

	public final void setSortingStrategy(SortingStrategy strategy) {
		sortingStrategy = strategy;
	}

	public final void setDumpingStrategy(DumpingStrategy strategy) {
		dumpingStrategy = strategy;
	}

	protected final DumpingStrategy getDumpingStrategy() {
		return dumpingStrategy;
	}

	protected final SortingStrategy getSortingStrategy() {
		return sortingStrategy;
	}

	protected Phrase getPhrase() {
		return phrase;
	}

	protected SpeechPart getSpeechPart() {
		return part;
	}

	protected ArrayList<String> getSynonymNameList() {
		return synonymNameList;
	}

	protected String getDefinition() {
		return definition;
	}

	protected ArrayList<String> getSynonyms() {
		if (synonymNameList == null)
			synonymNameList = new ArrayList<String>();
		return synonymNameList;
	}

	protected void reset() {
		phrase = null;
		part = null;
		synonymNameList = null;
	}

	public abstract TargetPhrase build();

}
