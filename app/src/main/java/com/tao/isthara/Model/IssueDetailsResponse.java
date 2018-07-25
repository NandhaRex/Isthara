
package com.tao.isthara.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class IssueDetailsResponse {

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
    private IssueListResponseRecords recordDetails = null;

    @SerializedName("RecordsCount")
    @Expose
    private RecordsCount recordsCount;

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


    public RecordsCount getRecordsCount() {
        return recordsCount;
    }

    public void setRecordsCount(RecordsCount recordsCount) {
        this.recordsCount = recordsCount;
    }

    public IssueListResponseRecords getRecordDetails() {
        return recordDetails;
    }

    public void setRecords(IssueListResponseRecords records) {
        this.recordDetails = records;
    }


}
