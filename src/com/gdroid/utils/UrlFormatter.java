package com.gdroid.utils;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Utility class. Provides some handy URL formatting functions.
 * 
 * @author kboom
 */
public class UrlFormatter {
	public static final String formatToString(String urlToConvert) {

		URL url = null;
		try {
			url = new URL(urlToConvert);
		} catch (MalformedURLException ex) {
			Logger.getLogger(UrlFormatter.class.getName()).log(Level.SEVERE,
					null, ex);
		}
		URI uri = null;
		try {
			uri = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(),
					url.getPort(), url.getPath(), url.getQuery(), url.getRef());
		} catch (URISyntaxException ex) {
			Logger.getLogger(UrlFormatter.class.getName()).log(Level.SEVERE,
					null, ex);
		}
		try {
			url = uri.toURL();
		} catch (MalformedURLException ex) {
			Logger.getLogger(UrlFormatter.class.getName()).log(Level.SEVERE,
					null, ex);
		}

		return url.toString();
	}

	public static final URI formatToUri(String urlToConvert) {
		URL url = null;
		try {
			url = new URL(urlToConvert);
		} catch (MalformedURLException ex) {
			Logger.getLogger(UrlFormatter.class.getName()).log(Level.SEVERE,
					null, ex);
		}
		URI uri = null;
		try {
			uri = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(),
					url.getPort(), url.getPath(), url.getQuery(), url.getRef());
		} catch (URISyntaxException ex) {
			Logger.getLogger(UrlFormatter.class.getName()).log(Level.SEVERE,
					null, ex);
		}

		return uri;
	}
}
