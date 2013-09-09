package com.gdroid.setphrase.phrase;

/**
 * All captured phrases must implement this interface. This kind of phrases are
 * compared with {@link com.gdroid.sayit.phrase.TargetPhrase }.
 * 
 * @author kboom
 */
public interface CapturedPhrase extends Phrase {

	/**
	 * Use to get confidence level for this phrase. The higher the confidence
	 * level is, the more accurate the result of recognition is. Use this to add
	 * some flexibility when comparing with
	 * {@link com.gdroid.sayit.phrase .targetphrase }
	 * 
	 * @return
	 */
	float getConfidence();

	/**
	 * Use to obtain next possible match. Order of this chain is usually
	 * determined by level of confidence.
	 * 
	 * @return
	 */
	CapturedPhrase getNextMatch();

}
