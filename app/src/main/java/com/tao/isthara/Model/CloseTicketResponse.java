package com.tao.isthara.Model;

import com.google.gson.annotations.SerializedName;

public class CloseTicketResponse {

	@SerializedName("TotalRecords")
	private int totalRecords;

	@SerializedName("MessageText")
	private String messageText;

	@SerializedName("StatusCode")
	private int statusCode;

	@SerializedName("Records")
	private Records records;

	public void setTotalRecords(int totalRecords){
		this.totalRecords = totalRecords;
	}

	public int getTotalRecords(){
		return totalRecords;
	}

	public void setMessageText(String messageText){
		this.messageText = messageText;
	}

	public String getMessageText(){
		return messageText;
	}

	public void setStatusCode(int statusCode){
		this.statusCode = statusCode;
	}

	public int getStatusCode(){
		return statusCode;
	}

	public void setRecords(Records records){
		this.records = records;
	}

	public Records getRecords(){
		return records;
	}
}