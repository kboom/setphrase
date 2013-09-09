package com.gdroid.setphrase.dictionary.request.base;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import com.gdroid.utils.SLog;
import com.gdroid.utils.UrlFormatter;

/**
 * Simple HTMLRequest. Built by builder.
 * 
 * @author kboom
 */
public class HTMLRequest implements WebRequestlike {

	static {
		SLog.register(HTMLRequest.class);
		SLog.setTag(HTMLRequest.class, "HTML request.");
	}

	private enum RequestType {
		GET, POST
	}

	private String url;
	private RequestType requestType;
	private ArrayList<BasicNameValuePair> attributeList;

	private HTMLRequest(Builder builder) {
		this.url = builder.url;
		this.attributeList = builder.attributeList;
		this.requestType = (builder.requestType == null) ? RequestType.GET
				: builder.requestType;
	};

	private HttpGet makeGetRequest() {
		HttpGet httpGet;

		// maybe there's a method for this? It's easy anyways.
		String getUrl = url + "?";
		for (BasicNameValuePair p : attributeList) {
			getUrl += p.getName() + "=" + p.getValue() + "&";
		}

		httpGet = new HttpGet(UrlFormatter.formatToString(getUrl));
		return httpGet;
	}

	private HttpPost makePostRequest() {
		HttpPost post = new HttpPost();
		try {
			String validUrl = UrlFormatter.formatToString(url);
			post.setURI(new URI(validUrl));
			post.setEntity(new UrlEncodedFormEntity(attributeList));
		} catch (UnsupportedEncodingException ex) {
			Logger.getLogger(HTMLRequest.class.getName()).log(Level.SEVERE,
					null, ex);
		} catch (URISyntaxException ex) {
			Logger.getLogger(HTMLRequest.class.getName()).log(Level.SEVERE,
					null, ex);
		}

		return post;
	}

	/**
	 * Executes built-in command
	 * 
	 * @return response
	 */
	@Override
	public String execute() {
		HttpClient httpclient = new DefaultHttpClient();
		HttpResponse response = null;
		String responseString = null;

		try {

			switch (requestType) {
			case GET:
				response = httpclient.execute(makeGetRequest());
				break;
			case POST:
				response = httpclient.execute(makePostRequest());
				break;
			default:
				break;
			}

		} catch (IOException ex) {
			Logger.getLogger(HTMLRequest.class.getName()).log(Level.SEVERE,
					null, ex);
		}

		try {

			StatusLine statusLine = response.getStatusLine();
			if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				response.getEntity().writeTo(out);
				out.close();
				responseString = out.toString();
			} else {
				// Closes the connection.
				response.getEntity().getContent().close();
				throw new IOException(statusLine.getReasonPhrase());
			}
		} catch (ClientProtocolException e) {
			// TODO Handle problems..
		} catch (IOException e) {
			// TODO Handle problems..
		}

		return responseString;
	}

	/**
	 * An static builder ensuring an request will be created once and remain
	 * immutable during execution.
	 */
	public static class Builder {

		private String url;
		RequestType requestType;
		private ArrayList<BasicNameValuePair> attributeList = new ArrayList<BasicNameValuePair>();

		public Builder() {
		}

		/**
		 * 
		 * @param uri
		 *            path to desired resource
		 * @return
		 */
		public Builder setUrl(String url) {
			this.url = url;
			return this;
		}

		public Builder viaGet() {
			this.requestType = RequestType.GET;
			return this;
		}

		public Builder viaPost() {
			this.requestType = RequestType.POST;
			return this;
		}

		public Builder addAttribute(String attr, String val) {
			attributeList.add(new BasicNameValuePair(attr, val));
			return this;
		}

		public HTMLRequest build() {
			if (url == null)
				throw new IllegalStateException(
						"Uri is not set when build() called.");
			else
				return new HTMLRequest(this);
		}

	}

}
