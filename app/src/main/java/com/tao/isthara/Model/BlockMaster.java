package com.tao.isthara.Model;

import com.google.gson.annotations.SerializedName;

public class BlockMaster{

	@SerializedName("BlockCode")
	private String blockCode;

	@SerializedName("CreatedBy")
	private String createdBy;

	@SerializedName("IsActive")
	private boolean isActive;

	@SerializedName("BlockName")
	private String blockName;

	@SerializedName("CreatedDate")
	private String createdDate;

	@SerializedName("BlockId")
	private int blockId;

	@SerializedName("ModifiedBy")
	private String modifiedBy;

	@SerializedName("ModifiedDate")
	private String modifiedDate;

	@SerializedName("Code")
	private String code;

	public void setBlockCode(String blockCode){
		this.blockCode = blockCode;
	}

	public String getBlockCode(){
		return blockCode;
	}

	public void setCreatedBy(String createdBy){
		this.createdBy = createdBy;
	}

	public String getCreatedBy(){
		return createdBy;
	}

	public void setIsActive(boolean isActive){
		this.isActive = isActive;
	}

	public boolean isIsActive(){
		return isActive;
	}

	public void setBlockName(String blockName){
		this.blockName = blockName;
	}

	public String getBlockName(){
		return blockName;
	}

	public void setCreatedDate(String createdDate){
		this.createdDate = createdDate;
	}

	public String getCreatedDate(){
		return createdDate;
	}

	public void setBlockId(int blockId){
		this.blockId = blockId;
	}

	public int getBlockId(){
		return blockId;
	}

	public void setModifiedBy(String modifiedBy){
		this.modifiedBy = modifiedBy;
	}

	public String getModifiedBy(){
		return modifiedBy;
	}

	public void setModifiedDate(String modifiedDate){
		this.modifiedDate = modifiedDate;
	}

	public String getModifiedDate(){
		return modifiedDate;
	}

	public void setCode(String code){
		this.code = code;
	}

	public String getCode(){
		return code;
	}

	@Override
 	public String toString(){
		return 
			"BlockMaster{" + 
			"blockCode = '" + blockCode + '\'' + 
			",createdBy = '" + createdBy + '\'' + 
			",isActive = '" + isActive + '\'' + 
			",blockName = '" + blockName + '\'' + 
			",createdDate = '" + createdDate + '\'' + 
			",blockId = '" + blockId + '\'' + 
			",modifiedBy = '" + modifiedBy + '\'' + 
			",modifiedDate = '" + modifiedDate + '\'' + 
			",code = '" + code + '\'' + 
			"}";
		}
}