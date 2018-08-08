
package com.tao.isthara.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProfileRecords {

    @SerializedName("ResidentDetailsId")
    @Expose
    private Integer residentDetailsId;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("BedMasterId")
    @Expose
    private Integer bedMasterId;
    @SerializedName("BedName")
    @Expose
    private String bedName;
    @SerializedName("RoomNo")
    @Expose
    private String roomNo;
    @SerializedName("DOJ")
    @Expose
    private String dOJ;
    @SerializedName("Property")
    @Expose
    private String property;
    @SerializedName("BlockId")
    @Expose
    private Integer blockId;
    @SerializedName("MobileNo")
    @Expose
    private String mobileNo;
    @SerializedName("SecondaryMobileNo")
    @Expose
    private String seconMobileNo;
    @SerializedName("Status")
    @Expose
    private String status;
    @SerializedName("EmailId")
    @Expose
    private String emailId;

    public Integer getResidentDetailsId() {
        return residentDetailsId;
    }

    public void setResidentDetailsId(Integer residentDetailsId) {
        this.residentDetailsId = residentDetailsId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getBedMasterId() {
        return bedMasterId;
    }

    public void setBedMasterId(Integer bedMasterId) {
        this.bedMasterId = bedMasterId;
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

    public String getDOJ() {
        return dOJ;
    }

    public void setDOJ(String dOJ) {
        this.dOJ = dOJ;
    }

    public String getProperty() {
        return property;
    }

    public String getEmailId() {
        return emailId;
    }

    public String getSecMobileNumber() {
        return seconMobileNo;
    }

    public void setSecMobileNumber(String number)
    {
        this.seconMobileNo = number;
    }


    public void setProperty(String property) {
        this.property = property;
    }

    public Integer getBlockId() {
        return blockId;
    }

    public void setBlockId(Integer blockId) {
        this.blockId = blockId;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setEmail(String email) {
        this.emailId = email;
    }
}
