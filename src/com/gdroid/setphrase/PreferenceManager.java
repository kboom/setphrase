package com.gdroid.setphrase;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.gdroid.setphrase.library.CachingMethods;
import com.gdroid.setphrase.library.StoringMethods;
import com.gdroid.setphrase.model.ModelManager;

/**
 * Standard preference manager.
 * 
 * @author kboom
 */
public class PreferenceManager {

	// maybe do not commit only on exit?

	public static final String ROOT_FILE = "base.sf";

	private static final String PREFFILE = "preferences.sfp";
	private static final String DICT = "com.gdroid.sayit.pref.dictionary";
	private static final String PHRASE_FAMILY = "com.gdroid.sayit.pref.phrasefamily";
	private static final String DICT_SORTING = "com.gdroid.sayit.pref.dictionary.sorting";
	private static final String DICT_DUMPING = "com.gdroid.sayit.pref.dictionary.dumping";
	private static final String LIBR_CACHING = "com.gdroid.sayit.pref.library.caching";
	private static final String LIBR_STORING = "com.gdroid.sayit.pref.library.storing";
	private static final String QUEST_METHOD = "com.gdroid.sayit.pref.questioning.algorithm";
	private static final String QUEST_FILTERING = "com.gdroid.sayit.pref.questioning.filtering";

	private SharedPreferences prefs;
	private Editor prefsEditor;

	public PreferenceManager(Context context) {
		this.prefs = context.getSharedPreferences(PREFFILE,
				Context.MODE_PRIVATE);
		this.prefsEditor = prefs.edit();
	}

	public ModelManager.PhraseFamily getPhraseFamily(
			ModelManager.PhraseFamily def) {
		int ival = prefs.getInt(PHRASE_FAMILY, def.ordinal());
		return ModelManager.PhraseFamily.get(ival);
	}

	public void setPhraseFamily(ModelManager.PhraseFamily val) {
		prefsEditor.putInt(PHRASE_FAMILY, val.ordinal());
		prefsEditor.commit();
	}

	public void setDictionary(ModelManager.Dictionary val) {
		prefsEditor.putInt(DICT, val.ordinal());
		prefsEditor.commit();
	}

	public ModelManager.Dictionary getDictionary(ModelManager.Dictionary def) {
		int ival = prefs.getInt(DICT, def.ordinal());
		return ModelManager.Dictionary.get(ival);
	}

	public ModelManager.DictionarySorting getDictionarySorting(
			ModelManager.DictionarySorting def) {
		int ival = prefs.getInt(DICT_SORTING, def.ordinal());
		return ModelManager.DictionarySorting.get(ival);
	}

	public void setDictionarySorting(ModelManager.DictionarySorting val) {
		prefsEditor.putInt(DICT_SORTING, val.ordinal());
		prefsEditor.commit();
	}

	public ModelManager.DictionaryDumping getDictionaryDumping(
			ModelManager.DictionaryDumping def) {
		int ival = prefs.getInt(DICT_DUMPING, def.ordinal());
		return ModelManager.DictionaryDumping.get(ival);
	}

	public void setDictionaryDumping(ModelManager.DictionaryDumping val) {
		prefsEditor.putInt(DICT_DUMPING, val.ordinal());
		prefsEditor.commit();
	}

	public CachingMethods getLibraryCaching(CachingMethods def) {
		int ival = prefs.getInt(LIBR_CACHING, def.ordinal());
		return ModelManager.LibraryCaching.get(ival);
	}

	public void setLibraryCaching(CachingMethods val) {
		prefsEditor.putInt(LIBR_CACHING, val.ordinal());
		prefsEditor.commit();
	}

	public StoringMethods getLibraryStoring(StoringMethods def) {
		int ival = prefs.getInt(LIBR_STORING, def.ordinal());
		return ModelManager.LibraryStoring.get(ival);
	}

	public void setLibraryStoring(StoringMethods val) {
		prefsEditor.putInt(LIBR_STORING, val.ordinal());
		prefsEditor.commit();
	}

	public ModelManager.QuestioningMethod getQuestioningMethod(
			ModelManager.QuestioningMethod def) {
		int ival = prefs.getInt(QUEST_METHOD, def.ordinal());
		return ModelManager.QuestioningMethod.get(ival);
	}

	public void setQuestioningMethod(ModelManager.QuestioningMethod val) {
		prefsEditor.putInt(QUEST_METHOD, val.ordinal());
		prefsEditor.commit();
	}

	public ModelManager.QuestionFiltering getQuestioningFilter(
			ModelManager.QuestionFiltering def) {
		int ival = prefs.getInt(QUEST_FILTERING, def.ordinal());
		return ModelManager.QuestionFiltering.get(ival);
	}

	public void setQuestioningFilter(ModelManager.QuestionFiltering val) {
		prefsEditor.putInt(QUEST_FILTERING, val.ordinal());
		prefsEditor.commit();
	}

}
