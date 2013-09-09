package com.gdroid.setphrase.model;

import com.gdroid.setphrase.dictionary.DictionaryFactory;
import com.gdroid.setphrase.dictionary.DictionarySystem;
import com.gdroid.setphrase.dictionary.ThesaurusDictionaryFactory;
import com.gdroid.setphrase.dictionary.WordNetDictionaryFactory;
import com.gdroid.setphrase.dictionary.extractor.buildingstrategy.DumpNone;
import com.gdroid.setphrase.dictionary.extractor.buildingstrategy.DumpingStrategy;
import com.gdroid.setphrase.dictionary.extractor.buildingstrategy.SortByName;
import com.gdroid.setphrase.dictionary.extractor.buildingstrategy.SortingStrategy;
import com.gdroid.setphrase.library.CachingMethods;
import com.gdroid.setphrase.library.LibrarySystem;
import com.gdroid.setphrase.library.StoringMethods;
import com.gdroid.setphrase.phrase.PhraseFactory;
import com.gdroid.setphrase.phrase.SimplePhraseFactory;
import com.gdroid.setphrase.questioningsystem.AlgorithmConstructor;
import com.gdroid.setphrase.questioningsystem.QuestioningAlgorithm;
import com.gdroid.setphrase.questioningsystem.QuestioningAlgorithmBuildable;
import com.gdroid.setphrase.questioningsystem.QuestioningSystem;
import com.gdroid.setphrase.questioningsystem.SimpleAlgorithmBuilderTypeA;
import com.gdroid.setphrase.questioningsystem.filter.PassEverythingFilter;
import com.gdroid.setphrase.questioningsystem.filter.QuestionFilterable;
import com.gdroid.setphrase.validatingsystem.ValidationSystem;
import com.gdroid.utils.StaticEnumMap;

/**
 * Used to set up and modify behavior of the system without it knowing it. It
 * greatly simplifies the system itself limiting it to only useful options. It
 * provides enumerations being preference parameters. Name of the preferences
 * are not defined here.
 * 
 * @author kboom
 */
public class ModelManager {

	private ModelFacade model;

	public static class InitialModelParameters {
		public String rootDir, rootFile;
	}

	public ModelManager(InitialModelParameters params) {
		if (!LibrarySystem.checkEnvironmentIntegrity(params.rootDir,
				params.rootFile))
			LibrarySystem.setUpNewEnvironment(params.rootDir, params.rootFile);

		model = new ModelFacade();
		model.librarySystem = new LibrarySystem(params.rootDir, params.rootFile);
		model.dictionarySystem = new DictionarySystem();
		model.questioningSystem = new QuestioningSystem();
		model.validationSystem = new ValidationSystem();
	}

	public ModelFacade getModel() {
		return model;
	}

	/*
	 * ====================================================================
	 * Common
	 * ====================================================================
	 */

	public enum PhraseFamily {
		SIMPLE;

		public static final PhraseFamily DEFAULT = SIMPLE;

		private static StaticEnumMap<PhraseFamily> lookup = new StaticEnumMap<PhraseFamily>(
				PhraseFamily.class);

		public static PhraseFamily get(int code) {
			return lookup.get(code);
		}

		private static PhraseFactory get(PhraseFamily type) {
			PhraseFactory result = null;
			switch (type) {
			case SIMPLE:
				result = new SimplePhraseFactory();
				break;
			}
			return result;
		}
	}

	public void setPhraseFamily(PhraseFamily family) {
		PhraseFactory factory = null;
		switch (family) {
		case SIMPLE:
			factory = new SimplePhraseFactory();
			break;
		}

		model.phraseFactory = factory;
		model.dictionarySystem.setPhraseFactory(factory);
		model.librarySystem.setPhraseFactory(factory);
		model.questioningSystem.setPhraseFactory(factory);
	}

	/*
	 * ====================================================================
	 * Dictionary
	 * ====================================================================
	 */

	public enum Dictionary {
		WORDNET, THESAURUS;

		public static final Dictionary DEFAULT = WORDNET;

		private static final StaticEnumMap<Dictionary> lookup = new StaticEnumMap<Dictionary>(
				Dictionary.class);

		public static Dictionary get(int code) {
			return lookup.get(code);
		}

		private static DictionaryFactory get(Dictionary dict) {
			DictionaryFactory factory = null;
			switch (dict) {
			case WORDNET:
				factory = new WordNetDictionaryFactory();
				break;
			case THESAURUS:
				factory = new ThesaurusDictionaryFactory();
				break;
			}
			return factory;
		}

	}

	public enum DictionarySorting {
		BY_NAME;

		public static final DictionarySorting DEFAULT = BY_NAME;

		private static final StaticEnumMap<DictionarySorting> lookup = new StaticEnumMap<DictionarySorting>(
				DictionarySorting.class);

		public static DictionarySorting get(int code) {
			return lookup.get(code);
		}

		private static SortingStrategy get(DictionarySorting type) {
			SortingStrategy strategy = null;
			switch (type) {
			case BY_NAME:
				strategy = new SortByName();
				break;
			}
			return strategy;
		}
	}

	public enum DictionaryDumping {
		NONE;
		public static final DictionaryDumping DEFAULT = NONE;

		private static final StaticEnumMap<DictionaryDumping> lookup = new StaticEnumMap<DictionaryDumping>(
				DictionaryDumping.class);

		public static DictionaryDumping get(int code) {
			return lookup.get(code);
		}

		private static DumpingStrategy get(DictionaryDumping type) {
			DumpingStrategy strategy = null;
			switch (type) {
			case NONE:
				strategy = new DumpNone();
				break;
			}
			return strategy;
		}
	}

	public void setDictionary(Dictionary dictionary) {
		DictionaryFactory factory = Dictionary.get(dictionary);
		model.dictionarySystem.setDictionaryFactory(factory);
	}

	public void setDictionarySorting(DictionarySorting sorting) {
		SortingStrategy strategy = DictionarySorting.get(sorting);
		model.dictionarySystem.setSortingStrategy(strategy);
	}

	public void setDictionaryDumping(DictionaryDumping dumping) {
		DumpingStrategy strategy = DictionaryDumping.get(dumping);
		model.dictionarySystem.setDumpingStrategy(strategy);
	}

	/*
	 * ====================================================================
	 * Library
	 * ====================================================================
	 */

	public enum LibraryCaching {
		; // hehe
		public static CachingMethods DEFAULT = CachingMethods.DISABLED;

		private static final StaticEnumMap<CachingMethods> lookup = new StaticEnumMap<CachingMethods>(
				CachingMethods.class);

		public static CachingMethods get(int code) {
			return lookup.get(code);
		}

	}

	public enum LibraryStoring {
		; // hehex2
		public static StoringMethods DEFAULT = StoringMethods.RANDOM_HEAVY;

		private static final StaticEnumMap<StoringMethods> lookup = new StaticEnumMap<StoringMethods>(
				StoringMethods.class);

		public static StoringMethods get(int code) {
			return lookup.get(code);
		}
	}

	private void setLibraryMountingPoint(String dirPath, String rootPath) {
		if (!LibrarySystem.checkEnvironmentIntegrity(dirPath, rootPath))
			LibrarySystem.setUpNewEnvironment(dirPath, rootPath);

		model.librarySystem.moveMountingPoint(dirPath, rootPath);
	}

	public void setLibraryCaching(CachingMethods cache) {
		model.librarySystem.setDefaultCachingMethod(cache);
	}

	public void setLibraryStoring(StoringMethods store) {
		model.librarySystem.setDefaultStoringMethod(store);
	}

	/*
	 * ====================================================================
	 * Questioning
	 * ====================================================================
	 */

	public enum QuestioningMethod {
		SIMPLE;

		public static final QuestioningMethod DEFAULT = SIMPLE;

		private static final StaticEnumMap<QuestioningMethod> lookup = new StaticEnumMap<QuestioningMethod>(
				QuestioningMethod.class);

		public static QuestioningMethod get(int code) {
			return lookup.get(code);
		}

		private static QuestioningAlgorithm get(QuestioningMethod dict) {
			QuestioningAlgorithmBuildable builder = null;
			switch (dict) {
			case SIMPLE:
				builder = new SimpleAlgorithmBuilderTypeA();
				break;
			}
			return AlgorithmConstructor.construct(builder);
		}
	}

	public enum QuestionFiltering {
		NONE;

		public static final QuestionFiltering DEFAULT = NONE;

		private static final StaticEnumMap<QuestionFiltering> lookup = new StaticEnumMap<QuestionFiltering>(
				QuestionFiltering.class);

		public static QuestionFiltering get(int code) {
			return lookup.get(code);
		}

		private static QuestionFilterable get(QuestionFiltering type) {
			QuestionFilterable result = null;
			switch (type) {
			case NONE:
				result = new PassEverythingFilter();
				break;
			}
			return result;
		}
	}

	public void setQuestioningMethod(QuestioningMethod method) {
		QuestioningAlgorithm algo = QuestioningMethod.get(method);
		model.questioningSystem.setQuestioningAlgorithm(algo);
	}

	public void setQuestioningFilter(QuestionFiltering filtering) {
		QuestionFilterable filter = QuestionFiltering.get(filtering);
		model.questioningSystem.setFilteringAlgorithm(filter);
	}

}
