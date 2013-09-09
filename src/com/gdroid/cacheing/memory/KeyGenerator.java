package com.gdroid.cacheing.memory;

import java.util.Comparator;

/**
 * Utility for generating of keys for memory cache and key comparing.
 */
public final class KeyGenerator {

	private static final String URI_AND_SIZE_SEPARATOR = "_";
	private static final String MEMORY_CACHE_KEY_FORMAT = "%s"
			+ URI_AND_SIZE_SEPARATOR + "%sx%s";

	public static String generateKey(String id) {
		return String.format(MEMORY_CACHE_KEY_FORMAT, id, id.length(),
				id.charAt(0));
	}

	public static Comparator<String> createFuzzyKeyComparator() {
		return new Comparator<String>() {
			@Override
			public int compare(String key1, String key2) {
				String imageUri1 = key1.substring(0,
						key1.lastIndexOf(URI_AND_SIZE_SEPARATOR));
				String imageUri2 = key2.substring(0,
						key2.lastIndexOf(URI_AND_SIZE_SEPARATOR));
				return imageUri1.compareTo(imageUri2);
			}
		};
	}
}