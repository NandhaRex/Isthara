
package com.tao.isthara.Model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IssueListResponse {

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
    private List<IssueListResponseRecords> records = null;


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

    public List<IssueListResponseRecords> getRecords() {
        return records;
    }

    public void setRecords(List<IssueListResponseRecords> records) {
        this.records = records;
    }

    public RecordsCount getRecordsCount() {
        return recordsCount;
    }

    public void setRecordsCount(RecordsCount recordsCount) {
        this.recordsCount = recordsCount;
    }


}
