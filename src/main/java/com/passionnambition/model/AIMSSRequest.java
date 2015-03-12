package com.passionnambition.model;

public class AIMSSRequest {
	public AIMSSRequest()
	{
		
	}
	
	private AIMSSTemplate template;
	private String apiKey;
	
	public AIMSSTemplate getTemplate() {
		return template;
	}
	public void setTemplate(AIMSSTemplate template) {
		this.template = template;
	}
	public String getApiKey() {
		return apiKey;
	}
	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}
	
}
