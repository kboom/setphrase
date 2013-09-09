package com.gdroid.setphrase.dictionary.request;

import com.gdroid.setphrase.dictionary.request.base.HTMLRequest;
import com.gdroid.setphrase.dictionary.request.base.RequestContractable;
import com.gdroid.setphrase.dictionary.request.base.WebRequestlike;
import com.gdroid.utils.SLog;

/**
 * 
 * @author kboom
 */
public abstract class DictServiceRequestContractor implements
		RequestContractable {

	static {
		SLog.register(DictServiceRequestContractor.class);
		SLog.setTag(DictServiceRequestContractor.class,
				"Dict service contractor.");
	}

	/*
	 * POST /DictService/DictService.asmx HTTP/1.1 Host: services.aonaware.com
	 * Content-Type: text/xml; charset=utf-8 Content-Length: length SOAPAction:
	 * "http://services.aonaware.com/webservices/DefineInDict"
	 * 
	 * <?xml version="1.0" encoding="utf-8"?> <soap:Envelope
	 * xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	 * xmlns:xsd="http://www.w3.org/2001/XMLSchema"
	 * xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/"> <soap:Body>
	 * <DefineInDict xmlns="http://services.aonaware.com/webservices/">
	 * <dictId>string</dictId> <word>string</word> </DefineInDict> </soap:Body>
	 * </soap:Envelope>
	 */

	/*
	 * if soap private static final String NAMESPACE =
	 * "http://services.aonaware.com/webservices/"; private static final String
	 * URL = "http://services.aonaware.com/DictService/DictService.asmx";
	 * private static final String ACTION =
	 * "http://services.aonaware.com/webservices/DefineInDict"; private static
	 * final String METHOD = "DefineInDict";
	 */
	private static final String ATTR_DICTIONARY_ID = "dictId";
	private static final String ATTR_WORD = "word";

	private static final String POST_URL = "http://services.aonaware.com/DictService/DictService.asmx/DefineInDict";

	/**
	 * 
	 * @param toSearch
	 * @return
	 */
	@Override
	public WebRequestlike getRequest(String toSearch) {

		/*
		 * if soap SOAPRequest.Builder builder = new SOAPRequest.Builder();
		 * builder.setNamespace(NAMESPACE); builder.setUrl(URL);
		 * builder.setActionToPerform(ACTION); builder.setMethodToCall(METHOD);
		 * 
		 * builder.addAttribute(ATTR_DICTIONARY_ID, getDictID());
		 * builder.addAttribute(ATTR_WORD, toSearch);
		 */

		// html post

		HTMLRequest.Builder builder = new HTMLRequest.Builder();
		// builder.viaPost();
		builder.setUrl(POST_URL);
		builder.addAttribute(ATTR_DICTIONARY_ID, getDictID());
		builder.addAttribute(ATTR_WORD, toSearch);

		return builder.build();
	}

	/**
	 * A hook for subclasses to change actual dictionary.
	 * 
	 * @return
	 */
	public abstract String getDictID();
}
