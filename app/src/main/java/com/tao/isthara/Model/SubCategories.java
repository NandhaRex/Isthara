package com.tao.isthara.Model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class SubCategories{

	@SerializedName("TotalRecords")
	private int totalRecords;

	@SerializedName("MessageText")
	private String messageText;

	@SerializedName("StatusCode")
	private int statusCode;

	@SerializedName("Records")
	private List<SubCategoriesItem> records;

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

	public void setRecords(List<SubCategoriesItem> records){
		this.records = records;
	}

	public List<SubCategoriesItem> getRecords(){
		return records;
	}
}