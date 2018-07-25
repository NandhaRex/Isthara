package com.tao.isthara.Model;

import com.google.gson.annotations.SerializedName;

public class SubmitUserRatingResponse {


	@SerializedName("MessageText")
	private String messageText;

	@SerializedName("StatusCode")
	private int statusCode;


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

}