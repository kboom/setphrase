package com.gdroid.setphrase.dictionary.extractor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.gdroid.setphrase.dictionary.extractor.base.JSONExtractor;
import com.gdroid.setphrase.phrase.SpeechPart;
import com.gdroid.utils.SLog;

/**
 * Extractor for Thesaurus provider. Uses JSON as data format, so it inherits
 * from {@link com.gdroid.setphrase.dictionary.extractor.base.JSONExtractor}
 * 
 * @author kboom
 */
public class ThesaurusExtractor extends JSONExtractor {

	static {
		SLog.register(ThesaurusExtractor.class);
		SLog.setTag(ThesaurusExtractor.class, "Thesaurus extractor.");
		SLog.setLevel(ThesaurusExtractor.class, SLog.Level.INFO);
	}

	// Sample response
	/*
	 * { "adjective": { "syn": [ "flashy","gaudy","showy","sporty"], "sim":
	 * ["colorful","colourful","rhythmic","rhythmical"] ... }, ... }
	 */

	// Thesaurus constants
	private static final String TAG_SYNONYMS = "syn";
	private static final String NOUN = "noun";
	private static final String VERB = "verb";
	private static final String UNDEFINED = "undefined";

	@Override
	protected void iterateThrough(JSONObject jsonRoot,
			Collection<TargetPhraseParameters> result) {

		JSONObject currentNode = null;
		JSONArray nameArray = jsonRoot.names();

		for (int i = 0; i < nameArray.length(); i++) {
			String nodeName = "";
			try {
				nodeName = nameArray.getString(i);
			} catch (JSONException ex) {
				Logger.getLogger(ThesaurusExtractor.class.getName()).log(
						Level.SEVERE, null, ex);
			}

			// extract node of given name
			try {
				currentNode = jsonRoot.getJSONObject(nodeName);
			} catch (JSONException ex) {
				Logger.getLogger(ThesaurusExtractor.class.getName()).log(
						Level.SEVERE, null, ex);
			}

			// obtain proper canonnical name
			SpeechPart speechPart = resolveSpeechPart(nodeName);

			// put synonyms in array
			JSONArray synonyms = null;
			try {
				synonyms = currentNode.getJSONArray(TAG_SYNONYMS);
			} catch (JSONException ex) {
				Logger.getLogger(ThesaurusExtractor.class.getName()).log(
						Level.SEVERE, null, ex);
			}
			ArrayList<String> synonymList = this.getSynonymsFromArray(synonyms);
			TargetPhraseParameters ps = super.createPhraseParameters(
					speechPart, synonymList);
			result.add(ps);
		}
	}

	@Override
	protected SpeechPart resolveSpeechPart(String part) {
		if (part.equals(NOUN)) {
			return SpeechPart.NOUN;
		} else if (part.equals(VERB)) {
			return SpeechPart.VERB;
		}

		return SpeechPart.UNDEFINED;
	}

	@Override
	protected String resolveSpeechPart(SpeechPart part) {
		if (part.ordinal() == 1) {
			return NOUN;
		} else
			return UNDEFINED;
	}

	@Override
	protected TargetPhraseParameters obtain(JSONObject jsonRoot,
			SpeechPart speechPart) {
		JSONObject targetNode = null;
		try {
			targetNode = jsonRoot.getJSONObject(resolveSpeechPart(speechPart));
		} catch (JSONException ex) {
			Logger.getLogger(ThesaurusExtractor.class.getName()).log(
					Level.SEVERE, null, ex);
		}
		JSONArray targetArray = null;
		try {
			targetArray = targetNode.getJSONArray(TAG_SYNONYMS);
		} catch (JSONException ex) {
			Logger.getLogger(ThesaurusExtractor.class.getName()).log(
					Level.SEVERE, null, ex);
		}
		ArrayList<String> synonymList = this.getSynonymsFromArray(targetArray);
		TargetPhraseParameters ps = super.createPhraseParameters(speechPart,
				synonymList);
		return ps;
	}

}
