package com.gdroid.cacheing.disk;

import java.io.File;

import com.gdroid.cacheing.naming.NameGeneratable;

/**
 * Default implementation of {@linkplain DiscCacheAware disc cache}. Cache size
 * is unlimited.
 * 
 * @see BaseDiscCache
 */
public class UnlimitedDiscCache extends DiscCache {

	/*
	 * SUPER SZTUCZKA, WARTO ZAPAMIETAC! Mozna zrobic prosty konstruktor, który
	 * uruchamia główny z jakimiś domyślnymi parametrami! Rozwiązanie bardzo
	 * eleganckie i tak włąśnie robić! Co więcej, może on wywołać np fabrykę...
	 * public UnlimitedDiscCache(File cacheDir) { this(cacheDir,
	 * DefaultConfigurationFactory.createFileNameGenerator()); }
	 */

	/**
	 * @param cacheDir
	 *            Directory for file caching
	 * @param fileNameGenerator
	 *            Name generator for cached files
	 */
	public UnlimitedDiscCache(File cacheDir, NameGeneratable fileNameGenerator) {
		super(cacheDir, fileNameGenerator);
	}

	@Override
	public void put(String key, File file) {
		// Do nothing
	}
}
