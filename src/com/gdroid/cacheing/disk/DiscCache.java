package com.gdroid.cacheing.disk;

import java.io.File;

import com.gdroid.cacheing.naming.NameGeneratable;

/**
 * Base disc cache. Implements common functionality for disc cache.
 * 
 * @see DiscCacheAware
 * @see FileNameGenerator
 */
public abstract class DiscCache implements DiscCacheable {

	private File cacheDir;
	private NameGeneratable fileNameGenerator;

	public DiscCache(File cacheDir, NameGeneratable fileNameGenerator) {
		this.cacheDir = cacheDir;
		this.fileNameGenerator = fileNameGenerator;
	}

	@Override
	public File get(String key) {
		String fileName = fileNameGenerator.generate(key);
		return new File(cacheDir, fileName);
	}

	@Override
	public void clear() {
		File[] files = cacheDir.listFiles();
		if (files != null) {
			for (File f : files) {
				f.delete();
			}
		}
	}

	protected File getCacheDir() {
		return cacheDir;
	}
}