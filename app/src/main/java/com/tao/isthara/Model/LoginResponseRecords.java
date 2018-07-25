package com.tao.isthara.Model;

import com.google.gson.annotations.SerializedName;

public class LoginResponseRecords {

	@SerializedName("NewPassword")
	private String newPassword;

	@SerializedName("MobileNo")
	private long mobileNo;

	@SerializedName("Campus")
	private String campus;

	@SerializedName("EmailId")
	private String emailId;

	@SerializedName("UserName")
	private String userName;

	@SerializedName("CreatedBy")
	private String createdBy;

	@SerializedName("IsActive")
	private boolean isActive;

	@SerializedName("PasswordAnswer")
	private String passwordAnswer;

	@SerializedName("ModifiedDate")
	private String  modifiedDate;

	@SerializedName("ModifiedBy")
	private String modifiedBy;

	@SerializedName("ConfirmPassword")
	private String confirmPassword;

	@SerializedName("Role")
	private String role;

	@SerializedName("BlockMaster")
	private BlockMaster blockMaster;

	@SerializedName("Type")
	private String type;

	@SerializedName("User_Id")
	private String userId;

	@SerializedName("CreatedDate")
	private String createdDate;

	@SerializedName("Id")
	private int id;

	@SerializedName("EmployeeId")
	private String employeeId;

	@SerializedName("UserType")
	private String userType;

	@SerializedName("PasswordQuestion")
	private String passwordQuestion;

	@SerializedName("Password")
	private String password;

	public void setNewPassword(String newPassword){
		this.newPassword = newPassword;
	}

	public String getNewPassword(){
		return newPassword;
	}

	public void setMobileNo(long mobileNo){
		this.mobileNo = mobileNo;
	}

	public long getMobileNo(){
		return mobileNo;
	}

	public void setCampus(String campus){
		this.campus = campus;
	}

	public String getCampus(){
		return campus;
	}

	public void setEmailId(String emailId){
		this.emailId = emailId;
	}

	public String getEmailId(){
		return emailId;
	}

	public void setUserName(String userName){
		this.userName = userName;
	}

	public String getUserName(){
		return userName;
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

	public void setPasswordAnswer(String passwordAnswer){
		this.passwordAnswer = passwordAnswer;
	}

	public String getPasswordAnswer(){
		return passwordAnswer;
	}

	public void setModifiedDate(String modifiedDate){
		this.modifiedDate = modifiedDate;
	}

	public String getModifiedDate(){
		return modifiedDate;
	}

	public void setModifiedBy(String modifiedBy){
		this.modifiedBy = modifiedBy;
	}

	public String getModifiedBy(){
		return modifiedBy;
	}

	public void setConfirmPassword(String confirmPassword){
		this.confirmPassword = confirmPassword;
	}

	public String getConfirmPassword(){
		return confirmPassword;
	}

	public void setRole(String role){
		this.role = role;
	}

	public String getRole(){
		return role;
	}

	public void setBlockMaster(BlockMaster blockMaster){
		this.blockMaster = blockMaster;
	}

	public BlockMaster getBlockMaster(){
		return blockMaster;
	}

	public void setType(String type){
		this.type = type;
	}

	public String getType(){
		return type;
	}

	public void setUserId(String userId){
		this.userId = userId;
	}

	public String getUserId(){
		return userId;
	}

	public void setCreatedDate(String createdDate){
		this.createdDate = createdDate;
	}

	public String getCreatedDate(){
		return createdDate;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setEmployeeId(String employeeId){
		this.employeeId = employeeId;
	}

	public String getEmployeeId(){
		return employeeId;
	}

	public void setUserType(String userType){
		this.userType = userType;
	}

	public String getUserType(){
		return userType;
	}

	public void setPasswordQuestion(String passwordQuestion){
		this.passwordQuestion = passwordQuestion;
	}

	public String getPasswordQuestion(){
		return passwordQuestion;
	}

	public void setPassword(String password){
		this.password = password;
	}

	public String getPassword(){
		return password;
	}

	@Override
 	public String toString(){
		return 
			"LoginResponseRecords{" +
			"newPassword = '" + newPassword + '\'' + 
			",mobileNo = '" + mobileNo + '\'' + 
			",campus = '" + campus + '\'' + 
			",emailId = '" + emailId + '\'' + 
			",userName = '" + userName + '\'' + 
			",createdBy = '" + createdBy + '\'' + 
			",isActive = '" + isActive + '\'' + 
			",passwordAnswer = '" + passwordAnswer + '\'' + 
			",modifiedDate = '" + modifiedDate + '\'' + 
			",modifiedBy = '" + modifiedBy + '\'' + 
			",confirmPassword = '" + confirmPassword + '\'' + 
			",role = '" + role + '\'' + 
			",blockMaster = '" + blockMaster + '\'' + 
			",type = '" + type + '\'' + 
			",userId = '" + userId + '\'' + 
			",createdDate = '" + createdDate + '\'' + 
			",id = '" + id + '\'' + 
			",employeeId = '" + employeeId + '\'' + 
			",userType = '" + userType + '\'' + 
			",passwordQuestion = '" + passwordQuestion + '\'' + 
			",password = '" + password + '\'' + 
			"}";
		}
}