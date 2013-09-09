package com.gdroid.setphrase.dictionary.extractor.base;

import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.gdroid.setphrase.phrase.Phrase;
import com.gdroid.setphrase.phrase.SpeechPart;
import com.gdroid.setphrase.phrase.target.TargetPhrase;
import com.gdroid.utils.SLog;

/**
 * For JSON payloads.
 * 
 * @author kboom
 */
public abstract class JSONExtractor extends TargetPhraseExtractor {

	static {
		SLog.register(JSONExtractor.class);
		SLog.setTag(JSONExtractor.class, "JSON extractor.");
		SLog.setLevel(JSONExtractor.class, SLog.Level.INFO);
	}

	// new instances should not be created every time method is called
	// private JSONExtractor extractor;

	/**
	 * Obtains only one synonym of a given phrase, specified by provided type of
	 * speech value.
	 * 
	 * @param phrase
	 * @param part
	 * @param response
	 * @return
	 */
	@Override
	public TargetPhrase extract(Phrase phrase, SpeechPart part, String response) {
		// if(extractor == null) extractor = createExtractor();
		JSONObject jsonRoot = getRoot(response);

		TargetPhraseParameters s = /* extractor. */obtain(jsonRoot, part);
		TargetPhrase result = this.buildPhrase(phrase, s);
		return result;
	}

	/**
	 * Obtains all synonyms of a given phrase. A given phrase can be
	 * simultaneously more than one part of speech.
	 * 
	 * @param phrase
	 * @param response
	 * @return
	 */
	@Override
	public ArrayList<TargetPhrase> extractAll(Phrase phrase, String response) {
		assert phrase != null;

		JSONObject jsonRoot = getRoot(response);

		// create an array to store results
		ArrayList<TargetPhraseParameters> parametersList = new ArrayList<TargetPhraseParameters>();
		// call for filling this array
		iterateThrough(jsonRoot, parametersList);
		// pass it to TargetPhraseBuilder
		ArrayList<TargetPhrase> phrases = new ArrayList<TargetPhrase>();
		for (TargetPhraseParameters s : parametersList) {
			TargetPhrase p = this.buildPhrase(phrase, s);
			phrases.add(p);
		}
		return phrases;
	}

	protected final JSONObject getRoot(String body) {
		JSONObject jsonRoot = null;
		try {
			jsonRoot = new JSONObject(body);
		} catch (JSONException ex) {
			Logger.getLogger(JSONExtractor.class.getName()).log(Level.SEVERE,
					null, ex);
		}

		return jsonRoot;
	}

	protected ArrayList<String> getSynonymsFromArray(JSONArray jsonArray) {
		ArrayList<String> synonyms = new ArrayList<String>();
		for (int j = 0; j < jsonArray.length(); j++) {
			try {
				synonyms.add(jsonArray.getString(j));
			} catch (JSONException ex) {
				Logger.getLogger(JSONExtractor.class.getName()).log(
						Level.SEVERE, null, ex);
			}
		}

		return synonyms;
	}

	/*
	 * DELEGATE SOME BEHAVIOUR THAT VARIES TO CHILDREN CLASSES. This is NOT an
	 * factory method. It is simple delegation through inheritance.
	 * 
	 * Factory method would create an object of ANOTHER class, not related with
	 * this one.
	 */

	/**
	 * Obtain one and only synonym of given type from given structure.
	 * 
	 * @param jsonRoot
	 * @param speechPart
	 * @param result
	 */
	protected abstract TargetPhraseParameters obtain(JSONObject jsonRoot,
			SpeechPart speechPart);

	// all methods underneath are meant to be overriden
	/**
	 * Should iterate through particular JSON structure to extract synonyms and
	 * store them in given array with a key of their speech part. Should use
	 * provided functions {@link #getSynonymsFromArray(JSONArray)} and
	 * {@link #resolveSpeechPart(java.lang.String)}
	 * 
	 * @param jsonRoot
	 *            root of iteration
	 * @param result
	 *            pairs of SpeechPart - List of synonyms
	 */
	protected abstract void iterateThrough(JSONObject jsonRoot,
			Collection<TargetPhraseParameters> result);

	/**
	 * Returns generalized name of speech part.
	 * 
	 * @param part
	 *            name of speechpart in this namespace
	 * @return SpeechPart
	 * @see com.gdroid.setphrase.phrase.SpeechPart
	 */
	protected abstract SpeechPart resolveSpeechPart(String part);

	/**
	 * Returns speechpart name in this namespace.
	 * 
	 * @param part
	 * @return
	 */
	protected abstract String resolveSpeechPart(SpeechPart part);

}
