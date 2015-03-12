package com.passionnambition.model;

public class AIMSSTemplate {

	private String templateId;
	private String name;
	private int predictedAER;
	private String fileId;
	public AIMSSTemplate() {

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public int getPredictedAER() {
		return predictedAER;
	}

	public void setPredictedAER(int predictedAER) {
		this.predictedAER = predictedAER;
	}

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

}
