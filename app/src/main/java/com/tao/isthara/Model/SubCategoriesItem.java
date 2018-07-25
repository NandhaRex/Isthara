package com.tao.isthara.Model;

import com.google.gson.annotations.SerializedName;


public class SubCategoriesItem {

	@SerializedName("Value")
	private int value;

	@SerializedName("Text")
	private String text;

	public void setValue(int value){
		this.value = value;
	}

	public int getValue(){
		return value;
	}

	public void setText(String text){
		this.text = text;
	}

	public String getText(){
		return text;
	}
}