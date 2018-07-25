
package com.tao.isthara.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProfileResponse {

    @SerializedName("StatusCode")
    @Expose
    private Integer statusCode;
    @SerializedName("MessageText")
    @Expose
    private String messageText;
    @SerializedName("TotalRecords")
    @Expose
    private Integer totalRecords;
    @SerializedName("Records")
    @Expose
    private ProfileRecords records;

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public Integer getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(Integer totalRecords) {
        this.totalRecords = totalRecords;
    }

    public ProfileRecords getRecords() {
        return records;
    }

    public void setRecords(ProfileRecords records) {
        this.records = records;
    }

}
