package com.tao.isthara.Model;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {

	@SerializedName("TotalRecords")
	private int totalRecords;

	@SerializedName("MessageText")
	private String messageText;

	@SerializedName("StatusCode")
	private int statusCode;

	@SerializedName("Records")
	private LoginResponseRecords records;

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

	public void setRecords(LoginResponseRecords records){
		this.records = records;
	}

	public LoginResponseRecords getRecords(){
		return records;
	}

	@Override
 	public String toString(){
		return 
			"LoginResponse{" +
			"totalRecords = '" + totalRecords + '\'' + 
			",messageText = '" + messageText + '\'' + 
			",statusCode = '" + statusCode + '\'' + 
			",records = '" + records + '\'' + 
			"}";
		}
}