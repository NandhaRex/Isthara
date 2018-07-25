package com.tao.isthara.Model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Categories{

	@SerializedName("TotalRecords")
	private int totalRecords;

	@SerializedName("MessageText")
	private String messageText;

	@SerializedName("StatusCode")
	private int statusCode;

	@SerializedName("Records")
	private List<CategoriesItem> records;

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

	public void setRecords(List<CategoriesItem> records){
		this.records = records;
	}

	public List<CategoriesItem> getRecords(){
		return records;
	}

	@Override
 	public String toString(){
		return 
			"Categories{" + 
			"totalRecords = '" + totalRecords + '\'' + 
			",messageText = '" + messageText + '\'' + 
			",statusCode = '" + statusCode + '\'' + 
			",records = '" + records + '\'' + 
			"}";
		}
}