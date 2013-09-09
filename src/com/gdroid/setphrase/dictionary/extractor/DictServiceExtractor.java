package com.gdroid.setphrase.dictionary.extractor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import com.gdroid.setphrase.dictionary.extractor.base.XMLExtractor;
import com.gdroid.utils.SLog;

/**
 * Common for all DictService dictionaries. Delegates interpreting results using
 * {@link #resolveSynonyms(java.util.ArrayList) to concrete service of
 * DictService interface.
 * @author kboom
 */
public abstract class DictServiceExtractor extends XMLExtractor {

	static {
		SLog.register(DictServiceExtractor.class);
		SLog.setTag(DictServiceExtractor.class, "WordNet extractor.");
		SLog.setLevel(DictServiceExtractor.class, SLog.Level.INFO);
	}

	private static final String TAG_OPENING = "WordDefinition";
	private static final String TAG_DEFINITIONS = "Definitions";
	private static final String TAG_DEFINITION_ENTRY = "Definition";
	private static final String TAG_WORD_DEFINITION = "WordDefinition";

	@Override
	protected String getOpeningTag() {
		return TAG_OPENING;
	}

	@Override
	protected void iterateThrough(XmlPullParser parser,
			Collection<TargetPhraseParameters> result) {
		ArrayList<String> wordDefinitions = new ArrayList<String>();

		try {
			while (parser.next() != XmlPullParser.END_TAG) {
				if (parser.getEventType() != XmlPullParser.START_TAG) {
					continue;
				}
				String name = parser.getName();

				if (name.equals(TAG_DEFINITIONS)
						|| name.equals(TAG_DEFINITION_ENTRY)) {
					continue;
				} else if (name.equals(TAG_WORD_DEFINITION)) {
					String read = this.read(parser, TAG_OPENING);
					wordDefinitions.add(read);
				} else {
					this.skip(parser);
				}
			}

		} catch (XmlPullParserException ex) {
			Logger.getLogger(DictServiceExtractor.class.getName()).log(
					Level.SEVERE, null, ex);
		} catch (IOException ex) {
			Logger.getLogger(DictServiceExtractor.class.getName()).log(
					Level.SEVERE, null, ex);
		}

		/*
		 * Data is read but it's totally unformatted. Delegate it to child
		 * object, it will know what to do about it.
		 */

		for (String s : wordDefinitions) {
			result.addAll(this.resolveSynonyms(s));
		}
	}

	/**
	 * Instantiated class should decide how to interpret results. DictService
	 * will provide a String that needs to be converted to
	 * TargetPhraseParameters array.
	 * 
	 * @param synonyms
	 *            list to modification
	 */
	protected abstract Collection<TargetPhraseParameters> resolveSynonyms(
			String data);

}
