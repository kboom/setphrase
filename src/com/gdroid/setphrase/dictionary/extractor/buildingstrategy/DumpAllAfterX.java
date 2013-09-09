package com.gdroid.setphrase.dictionary.extractor.buildingstrategy;

import java.util.ArrayList;

/**
 * 
 * @author kboom
 */
public class DumpAllAfterX implements DumpingStrategy {

	private int x = -1;

	public void setDumpBeginning(int n) {
		x = n;
	}

	@Override
	public void filter(ArrayList<String> array) {
		if (x == -1) {
			return;
		} else {
			array.removeAll(array.subList(0, array.size() - 1));
		}
	}

}
