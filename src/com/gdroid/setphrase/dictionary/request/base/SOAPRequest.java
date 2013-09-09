package com.gdroid.setphrase.dictionary.request.base;

import java.util.ArrayList;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import com.gdroid.utils.SLog;

/**
 * SOAP request. Note that even if response has also SOAP format, the payload
 * can still be anything. If payload is pure-soap, then it must be parsed with
 * SOAPExtractor. Due to portability and convenience, regardless of type of
 * payload it is converted to string first, then reconverted to proper format
 * and parsed in a subclass of
 * {@link com.gdroid.setphrase.dictionary.extractor.base.TargetPhraseBuilder }.
 * 
 * @author kboom
 */
public class SOAPRequest implements WebRequestlike {

	static {
		SLog.register(SOAPRequest.class);
		SLog.setTag(SOAPRequest.class, "Soap request.");
	}

	private String namespace, url, action, method;

	// it is possible to use BasicNameValuePair but let it be this way
	// it shows that class defined in static class can be instantiated there
	private ArrayList<Builder.AttrValuePair> attributeList;

	private SOAPRequest(Builder builder) {
		namespace = builder.namespace;
		url = builder.url;
		action = builder.action;
		method = builder.method;
		attributeList = new ArrayList<Builder.AttrValuePair>();
		attributeList.addAll(builder.attributeList);
	}

	@Override
	public String execute() {
		// Make request
		SoapObject request = new SoapObject(namespace, method);
		for (Builder.AttrValuePair pair : attributeList) {
			request.addProperty(pair.attribute, pair.value);
		}
		// Make SOAP envelope
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.setOutputSoapObject(request);
		envelope.dotNet = true;
		// Send request
		HttpTransportSE httpTransport = new HttpTransportSE(url);
		httpTransport.debug = true;

		// SoapPrimitive soapResponse = null;

		// zmiana na SoapObject
		SoapObject soapResponse = null;

		String result = "";
		try {
			httpTransport.call(action, envelope);
			soapResponse = (SoapObject) envelope.getResponse();

			result = soapResponse.toString();

		} catch (Exception e) {
		} finally {
			return result;
		}
	}

	/**
	 * An static builder ensuring an request will be created once and remain
	 * immutable during execution.
	 */
	public static class Builder {

		private String namespace, url, action, method, attribute,
				attributeValue;

		private ArrayList<AttrValuePair> attributeList = new ArrayList<AttrValuePair>();

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

		public Builder setNamespace(String namespace) {
			this.namespace = namespace;
			return this;
		}

		public Builder setMethodToCall(String method) {
			this.method = method;
			return this;
		}

		public Builder addAttribute(String attr, String val) {
			attributeList.add(new AttrValuePair(attr, val));
			return this;
		}

		public Builder setActionToPerform(String action) {
			this.action = action;
			return this;
		}

		public SOAPRequest build() {
			return new SOAPRequest(this);
		}

		private class AttrValuePair {
			public AttrValuePair(String attr, String val) {
				attribute = attr;
				value = val;
			}

			public String attribute;
			public String value;
		}

	}

}
