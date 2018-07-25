
package com.tao.isthara.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IssueListResponseRecords {

    @SerializedName("HelpDeskId")
    @Expose
    private Integer helpDeskId;
    @SerializedName("Title")
    @Expose
    private String title;
    @SerializedName("IssueNumber")
    @Expose
    private String issueNumber;
    @SerializedName("IssueDate")
    @Expose
    private String issueDate;
    @SerializedName("Description")
    @Expose
    private String description;
    @SerializedName("UserInboxId")
    @Expose
    private Integer userInboxId;
    @SerializedName("Status")
    @Expose
    private String status;
    @SerializedName("InstanceId")
    @Expose
    private Integer instanceId;
    @SerializedName("Resolution")
    @Expose
    private String resolution;
    @SerializedName("BlockId")
    @Expose
    private Integer blockId;
    @SerializedName("ActivityFullName")
    @Expose
    private String activityFullName;
    @SerializedName("PerformerId")
    @Expose
    private Integer performerId;
    @SerializedName("CategoryMasterId")
    @Expose
    private Integer categoryMasterId;
    @SerializedName("SubCategoryMasterId")
    @Expose
    private Integer subCategoryMasterId;
    @SerializedName("CategoryName")
    @Expose
    private String categoryName;
    @SerializedName("SubCategoryName")
    @Expose
    private String subCategoryName;
    @SerializedName("Property")
    @Expose
    private String property;
    @SerializedName("BedName")
    @Expose
    private String bedName;
    @SerializedName("RoomNo")
    @Expose
    private String roomNo;
    @SerializedName("Rating")
    @Expose
    private Integer rating;


    public Integer getHelpDeskId() {
        return helpDeskId;
    }

    public void setHelpDeskId(Integer helpDeskId) {
        this.helpDeskId = helpDeskId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIssueNumber() {
        return issueNumber;
    }

    public void setIssueNumber(String issueNumber) {
        this.issueNumber = issueNumber;
    }

    public String getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(String issueDate) {
        this.issueDate = issueDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getUserInboxId() {
        return userInboxId;
    }

    public void setUserInboxId(Integer userInboxId) {
        this.userInboxId = userInboxId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(Integer instanceId) {
        this.instanceId = instanceId;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public Integer getBlockId() {
        return blockId;
    }

    public void setBlockId(Integer blockId) {
        this.blockId = blockId;
    }

    public String getActivityFullName() {
        return activityFullName;
    }

    public void setActivityFullName(String activityFullName) {
        this.activityFullName = activityFullName;
    }

    public Integer getPerformerId() {
        return performerId;
    }

    public void setPerformerId(Integer performerId) {
        this.performerId = performerId;
    }

    public Integer getCategoryMasterId() {
        return categoryMasterId;
    }

    public void setCategoryMasterId(Integer categoryMasterId) {
        this.categoryMasterId = categoryMasterId;
    }

    public Integer getSubCategoryMasterId() {
        return subCategoryMasterId;
    }

    public void setSubCategoryMasterId(Integer subCategoryMasterId) {
        this.subCategoryMasterId = subCategoryMasterId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getSubCategoryName() {
        return subCategoryName;
    }

    public void setSubCategoryName(String subCategoryName) {
        this.subCategoryName = subCategoryName;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getBedName() {
        return bedName;
    }

    public void setBedName(String bedName) {
        this.bedName = bedName;
    }

    public String getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
    }

    public Integer getRating() {
        return rating;
    }
    public void setRating(Integer rating) {
        this.rating = rating;
    }


}
