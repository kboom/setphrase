package com.gdroid.setphrase.dictionary.request;

import com.gdroid.setphrase.dictionary.request.base.HTMLRequest;
import com.gdroid.setphrase.dictionary.request.base.RequestContractable;
import com.gdroid.setphrase.dictionary.request.base.WebRequestlike;
import com.gdroid.utils.SLog;

/**
 * Contractor for building proper for Thesaurus web request.
 * 
 * @author kboom
 */
public class ThesaurusRequestContractor implements RequestContractable {

	static {
		SLog.register(ThesaurusRequestContractor.class);
		SLog.setTag(ThesaurusRequestContractor.class,
				"Thesaurus request contractor.");
	}

	private String key = "21a8d64eca4cbaf4321f5704c03431d3";
	private String baseUrl = "http://words.bighugelabs.com/api/2";
	private String responseType = "json";

	@Override
	public WebRequestlike getRequest(String toSearch) {
		HTMLRequest.Builder builder = new HTMLRequest.Builder();
		builder.setUrl(baseUrl + "/" + key + "/" + toSearch + "/"
				+ responseType);
		return builder.build();
	}

}
