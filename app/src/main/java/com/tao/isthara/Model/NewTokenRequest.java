package com.tao.isthara.Model;

import com.google.gson.annotations.SerializedName;

public class NewTokenRequest{

	@SerializedName("Description")
	private String description;

	@SerializedName("SubCategoryMasterId")
	private int subCategoryMasterId;

	@SerializedName("Title")
	private String title;

	@SerializedName("BlockId")
	private int blockId;

	@SerializedName("CategoryMasterId")
	private int categoryMasterId;

	@SerializedName("userId")
	private int userId;

	public void setDescription(String description){
		this.description = description;
	}

	public String getDescription(){
		return description;
	}

	public void setSubCategoryMasterId(int subCategoryMasterId){
		this.subCategoryMasterId = subCategoryMasterId;
	}

	public int getSubCategoryMasterId(){
		return subCategoryMasterId;
	}

	public void setTitle(String title){
		this.title = title;
	}

	public String getTitle(){
		return title;
	}

	public void setBlockId(int blockId){
		this.blockId = blockId;
	}

	public int getBlockId(){
		return blockId;
	}

	public void setCategoryMasterId(int categoryMasterId){
		this.categoryMasterId = categoryMasterId;
	}

	public int getCategoryMasterId(){
		return categoryMasterId;
	}

	public void setUserId(int userId){
		this.userId = userId;
	}

	public int getUserId(){
		return userId;
	}
}