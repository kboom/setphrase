package com.gdroid.setphrase.dictionary.extractor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.gdroid.setphrase.phrase.SpeechPart;
import com.gdroid.utils.SLog;

/**
 * One of the providers for DictService. This will return a <b>very detailed
 * result</b>. It should be used as a real dictionary.
 * 
 * @author kboom
 */
public class WordNetExtractor extends DictServiceExtractor {

	static {
		SLog.register(WordNetExtractor.class);
		SLog.setTag(WordNetExtractor.class, "WordNet extractor.");
		SLog.setLevel(WordNetExtractor.class, SLog.Level.NONE);
	}

	private static final String VERB = "v";
	private static final String NOUN = "n";
	private static final String ADJECTIVE = "adj";

	private static final String REGEX_EXTRACT_SPARTS = "\\w*(?= \\d?:)";
	private static final String REGEX_EXTRACT_PHRASE = "((?!\\d+:))(.|\\n)+?((?=\\d+:)|($))";
	private static final String REGEX_REMOVE_WHITESPACES = "\\s +";
	private static final String REGEX_FOCUSON_SYNONYMS = "(\\[)(.|\\s)*(\\])";
	private static final String REGEX_EXTRACT_SYNONYMS = "((?!\\{))(\\w+)((?=\\}))";

	@Override
	protected Collection<TargetPhraseParameters> resolveSynonyms(String data) {

		ArrayList<String> csList = new ArrayList<String>();
		ArrayList<TargetPhraseParameters> result = new ArrayList<TargetPhraseParameters>();

		Pattern p = Pattern.compile(REGEX_EXTRACT_SPARTS);
		Matcher m = p.matcher(data);

		SLog.d(this, "Separating parts of speech blocks.");
		HashMap<String, String> block = getBlocks(p, m, data);
		SLog.i(this, "Separated " + block.size() + " parts of speech blocks.");

		// data is no longer needed, so get rid of it since it's big!
		data = null;

		// split into separate meanings
		p = Pattern.compile(REGEX_EXTRACT_PHRASE);
		SLog.d(this, "Separating meanings in each block.");
		HashMap<String, ArrayList<String>> definitions = getMeanings(p, m,
				block);
		if (SLog.isOn()) {
			int count = 0;
			for (ArrayList<String> a : definitions.values()) {
				count += a.size();
			}
			SLog.d(this,
					"Got " + count + " meanings total from "
							+ definitions.size() + " blocks.");
		}
		// block is no longer needed so get rid of it
		block = null;

		// last part, detach synonyms from meaning
		for (Entry<String, ArrayList<String>> entry : definitions.entrySet()) {
			String key = entry.getKey();
			ArrayList<String> value = entry.getValue();

			for (String v : value) {
				p = Pattern.compile(REGEX_FOCUSON_SYNONYMS);
				m = p.matcher(v);
				if (!m.find()) {
					TargetPhraseParameters tpp = super.createPhraseParameters(
							resolveSpeechPart(key), v);
					result.add(tpp);
					continue;
				}

				// separate
				String synonyms = m.group();
				String definition = v.substring(0, m.start() - 1);

				// split synonyms
				p = Pattern.compile(REGEX_EXTRACT_SYNONYMS);
				m = p.matcher(synonyms);
				ArrayList<String> syn = new ArrayList<String>();
				while (m.find()) {
					if (m.start() == m.end())
						continue;
					syn.add(m.group());
				}

				// add to list
				TargetPhraseParameters tpp = super.createPhraseParameters(
						resolveSpeechPart(key), definition, syn);
				result.add(tpp);

			}

			value = null; // free up space
		}
		return result;
	}

	private HashMap<String, String> getBlocks(Pattern p, Matcher m, String data) {
		// first find all part of speech delimeters
		class Delim {
			String name;
			int start, end;
		}
		ArrayList<Delim> occurances = new ArrayList<Delim>();
		while (m.find()) {
			if (m.start() == m.end())
				continue;

			Delim d = new Delim();
			d.name = m.group();
			d.start = m.start();
			d.end = m.end();
			SLog.d(this, "Part of speech found: " + d.name + ", start="
					+ d.start + ", end=" + d.end);
			// erase it
			occurances.add(d);
		}

		// get rid of their names and split
		HashMap<String, String> parted = new HashMap<String, String>();

		for (int i = 0; i < occurances.size(); i++) {
			Delim d = occurances.get(i);
			int end = i + 1 < occurances.size() ? occurances.get(i + 1).start
					: data.length();
			String block = data.substring(d.end, end);
			SLog.v(this, "Separated block: " + block);
			parted.put(d.name, block);
		}

		return parted;
	}

	private HashMap<String, ArrayList<String>> getMeanings(Pattern p,
			Matcher m, HashMap<String, String> data) {
		HashMap<String, ArrayList<String>> partedMeanings = new HashMap<String, ArrayList<String>>();
		for (Entry<String, String> entry : data.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();

			m = p.matcher(value);
			ArrayList<String> samePartMean = new ArrayList<String>();
			while (m.find()) {
				if (m.start() == m.end() || m.group().length() < 2)
					continue;

				String meaning = m.group();
				// get rid of ": " at the beginning
				meaning = meaning.substring(1, meaning.length() - 1);
				// remove whitespaces to free up memory a bit
				meaning = meaning.replaceAll(REGEX_REMOVE_WHITESPACES, " ");
				samePartMean.add(meaning);
			}
			partedMeanings.put(key, samePartMean);
		}

		return partedMeanings;
	}

	@Override
	protected SpeechPart resolveSpeechPart(String part) {
		if (part.equalsIgnoreCase(VERB)) {
			return SpeechPart.VERB;
		} else if (part.equalsIgnoreCase(NOUN)) {
			return SpeechPart.NOUN;
		} else if (part.equalsIgnoreCase(ADJECTIVE)) {
			return SpeechPart.ADJECTIVE;
		} else
			return SpeechPart.UNDEFINED;
	}

}
