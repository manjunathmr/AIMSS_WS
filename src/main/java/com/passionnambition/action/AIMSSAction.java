package com.passionnambition.action;


import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.opensymphony.xwork2.ActionSupport;

public class AIMSSAction extends ActionSupport implements ServletRequestAware,ServletResponseAware {
	
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(AIMSSAction.class);
	private File file;
	private String templateOutput;
	private String charCache;
	private String pdfFileId;
	private String imageFileId;
	private String apiKey;
	private String allTagDetails;
 
	public String getAllTagDetails() {
		return allTagDetails;
	}

	public void setAllTagDetails(String allTagDetails) {
		this.allTagDetails = allTagDetails;
	}

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	public File getFile() {
		return file;
	}
 
	public void setFile(File file) {
		this.file = file;
	}
 
	public String execute() throws Exception{
		return SUCCESS;
	}
	
	public String display() {
		return NONE;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}
	
	protected HttpServletRequest request;
	protected HttpServletResponse response;

	@Override
	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	public String getTemplateOutput() {
		return templateOutput;
	}

	public void setTemplateOutput(String templateOutput) {
		this.templateOutput = templateOutput;
	}

	public String getCharCache() {
		return charCache;
	}

	public void setCharCache(String charCache) {
		this.charCache = charCache;
	}

	public String getPdfFileId() {
		return pdfFileId;
	}

	public void setPdfFileId(String pdfFileId) {
		this.pdfFileId = pdfFileId;
	}

	public String getImageFileId() {
		return imageFileId;
	}

	public void setImageFileId(String imageFileId) {
		this.imageFileId = imageFileId;
	}
}