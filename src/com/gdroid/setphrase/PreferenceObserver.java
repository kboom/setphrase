/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdroid.setphrase;

import com.gdroid.setphrase.library.CachingMethods;
import com.gdroid.setphrase.library.StoringMethods;
import com.gdroid.setphrase.model.ModelManager;

/**
 * Responsible for handling actions that change program behavior. It usually
 * happens when an user clicks an option.
 * 
 * @author kboom
 */
public class PreferenceObserver {

	private PreferenceManager prefManager;
	private ModelManager modelManager;
	private ViewManager viewManager;

	public PreferenceObserver(PreferenceManager manager) {
		prefManager = manager;
	}

	public void registerModel(ModelManager manager) {
		modelManager = manager;
	}

	public void registerView(ViewManager manager) {
		viewManager = manager;
	}

	public void refresh() {
		onDictionaryChanged(null);
		onDictionarySortingChanged(null);
		onDictionaryDumpingChanged(null);
		onLibraryCachingChanged(null);
		onLibraryStoringChanged(null);
		onQuestioningMethodChanged(null);
		onQuestioningFilterChanged(null);
		onPhraseFamilyChanged(null);
	}

	/*
	 * ========================================================= MODEL
	 * =========================================================
	 */
	public void onPhraseFamilyChanged(ModelManager.PhraseFamily val) {
		int iparam;
		if (val != null) {
			prefManager.setPhraseFamily(val);
		} else {
			val = prefManager
					.getPhraseFamily(ModelManager.PhraseFamily.DEFAULT);
		}
		modelManager.setPhraseFamily(val);
	}

	public void onDictionaryChanged(ModelManager.Dictionary val) {
		if (val != null) {
			prefManager.setDictionary(val);
		} else {
			val = prefManager.getDictionary(ModelManager.Dictionary.DEFAULT);
		}
		modelManager.setDictionary(val);
	}

	public void onDictionarySortingChanged(ModelManager.DictionarySorting val) {
		if (val != null) {
			prefManager.setDictionarySorting(val);
		} else {
			val = prefManager
					.getDictionarySorting(ModelManager.DictionarySorting.DEFAULT);
		}
		modelManager.setDictionarySorting(val);
	}

	public void onDictionaryDumpingChanged(ModelManager.DictionaryDumping val) {
		if (val != null) {
			prefManager.setDictionaryDumping(val);
		} else {
			val = prefManager
					.getDictionaryDumping(ModelManager.DictionaryDumping.DEFAULT);
		}
		modelManager.setDictionaryDumping(val);
	}

	public void onLibraryCachingChanged(CachingMethods val) {
		if (val != null) {
			prefManager.setLibraryCaching(val);
		} else {
			val = prefManager
					.getLibraryCaching(ModelManager.LibraryCaching.DEFAULT);
		}
		modelManager.setLibraryCaching(val);
	}

	public void onLibraryStoringChanged(StoringMethods val) {
		if (val != null) {
			prefManager.setLibraryStoring(val);
		} else {
			val = prefManager
					.getLibraryStoring(ModelManager.LibraryStoring.DEFAULT);
		}
		modelManager.setLibraryStoring(val);
	}

	public void onQuestioningMethodChanged(ModelManager.QuestioningMethod val) {
		if (val != null) {
			prefManager.setQuestioningMethod(val);
		} else {
			val = prefManager
					.getQuestioningMethod(ModelManager.QuestioningMethod.DEFAULT);
		}
		modelManager.setQuestioningMethod(val);
	}

	public void onQuestioningFilterChanged(ModelManager.QuestionFiltering val) {
		if (val != null) {
			prefManager.setQuestioningFilter(val);
		} else {
			val = prefManager
					.getQuestioningFilter(ModelManager.QuestionFiltering.DEFAULT);
		}
		modelManager.setQuestioningFilter(val);
	}

}
