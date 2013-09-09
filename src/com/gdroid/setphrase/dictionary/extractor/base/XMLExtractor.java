package com.gdroid.setphrase.dictionary.extractor.base;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Xml;

import com.gdroid.setphrase.dictionary.extractor.DictServiceExtractor;
import com.gdroid.setphrase.phrase.Phrase;
import com.gdroid.setphrase.phrase.SpeechPart;
import com.gdroid.setphrase.phrase.target.TargetPhrase;
import com.gdroid.utils.SLog;

/**
 * For XML payloads.
 * 
 * @author kboom
 */
public abstract class XMLExtractor extends TargetPhraseExtractor {

	static {
		SLog.register(XMLExtractor.class);
		SLog.setTag(XMLExtractor.class, "XML extractor.");
	}

	@Override
	public TargetPhrase extract(Phrase phrase, SpeechPart part, String response) {
		if (phrase == null)
			throw new IllegalStateException("Null argument passed.");

		// if(extractor == null) extractor = createExtractor();

		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public ArrayList<TargetPhrase> extractAll(Phrase phrase, String response) {
		SLog.v(this, "Method: extractAll");

		if (phrase == null)
			throw new IllegalStateException("Null argument passed.");

		InputStream stream = getInputStream(response);
		try {
			stream.close();
		} catch (IOException ex) {
			Logger.getLogger(XMLExtractor.class.getName()).log(Level.SEVERE,
					null, ex);
		}

		ArrayList<TargetPhraseParameters> partedSynonymList = null;
		try {
			partedSynonymList = parseAll(stream);
		} catch (XmlPullParserException ex) {
			Logger.getLogger(XMLExtractor.class.getName()).log(Level.SEVERE,
					null, ex);
		} catch (IOException ex) {
			Logger.getLogger(XMLExtractor.class.getName()).log(Level.SEVERE,
					null, ex);
		} finally {
			try {
				stream.close();
			} catch (IOException ex) {
				Logger.getLogger(XMLExtractor.class.getName()).log(
						Level.SEVERE, null, ex);
			}
		}

		// build multiple target phrases
		ArrayList<TargetPhrase> phrases = new ArrayList<TargetPhrase>();
		for (TargetPhraseParameters s : partedSynonymList) {
			TargetPhrase p = this.buildPhrase(phrase, s);
			phrases.add(p);
		}
		return phrases;
	}

	/**
	 * Do not forget to close this input stream.
	 * 
	 * @param s
	 * @return
	 */
	private InputStream getInputStream(String s) {
		SLog.v(this, "Method: getInputStream");
		// convert String into InputStream
		InputStream is = new ByteArrayInputStream(s.getBytes());
		return is;
	}

	/**
	 * Used for reading tag content.
	 * 
	 * @param parser
	 * @param tag
	 * @return
	 * @throws IOException
	 * @throws XmlPullParserException
	 */
	protected String read(XmlPullParser parser, String tag) throws IOException,
			XmlPullParserException {
		SLog.v(this, "Method: read");
		parser.require(XmlPullParser.START_TAG, getNamespace(), tag);
		String result = readText(parser);
		parser.require(XmlPullParser.END_TAG, getNamespace(), tag);
		return result;
	}

	private String readText(XmlPullParser parser) throws IOException,
			XmlPullParserException {
		SLog.v(this, "Method: readText");
		String result = "";
		if (parser.next() == XmlPullParser.TEXT) {
			result = parser.getText();
			parser.nextTag();
		}
		return result;
	}

	/**
	 * Used to skip current tag.
	 * 
	 * @param parser
	 * @throws XmlPullParserException
	 * @throws IOException
	 */
	protected void skip(XmlPullParser parser) throws XmlPullParserException,
			IOException {
		SLog.v(this, "Method: skip");
		if (parser.getEventType() != XmlPullParser.START_TAG) {
			throw new IllegalStateException();
		}
		int depth = 1;
		while (depth != 0) {
			switch (parser.next()) {
			case XmlPullParser.END_TAG:
				depth--;
				break;
			case XmlPullParser.START_TAG:
				depth++;
				break;
			}
		}
	}

	private XmlPullParser preparePullParser(InputStream in)
			throws XmlPullParserException {
		SLog.v(this, "Method: preparePullParser");
		XmlPullParser parser = Xml.newPullParser();
		parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
		parser.setInput(in, null);
		return parser;
	}

	public ArrayList<TargetPhraseParameters> parseAll(InputStream in)
			throws XmlPullParserException, IOException {
		SLog.v(this, "Method: parseAll");
		XmlPullParser parser = preparePullParser(in);

		parser.nextTag();

		try {
			parser.require(XmlPullParser.START_TAG, getNamespace(),
					getOpeningTag());
		} catch (XmlPullParserException ex) {
		} catch (IOException ex) {
			Logger.getLogger(DictServiceExtractor.class.getName()).log(
					Level.SEVERE, null, ex);
		}

		// regardless of what's inside we crate an array
		ArrayList<TargetPhraseParameters> partedSynonymList = new ArrayList<TargetPhraseParameters>();

		// delegate filling synonym list
		iterateThrough(parser, partedSynonymList);

		return partedSynonymList;
	}

	/**
	 * The root tag for parser to focus on.
	 * 
	 * @return
	 */
	protected abstract String getOpeningTag();

	/**
	 * Hook for namespace definition.
	 * 
	 * @return
	 */
	protected String getNamespace() {
		return null;
	}

	/**
	 * Delegate extracting data to child object.
	 * 
	 * @param parser
	 * @param result
	 */
	protected abstract void iterateThrough(XmlPullParser parser,
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
}
