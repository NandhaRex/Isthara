package com.tao.isthara.Model;

import com.google.gson.annotations.SerializedName;

public class Records{

    @SerializedName("IssueNumber")
    private String IssueNumber;

    public void setIssueNumber(String issueNumber){
        this.IssueNumber = issueNumber;
    }

    public String getIssueNumber(){
        return IssueNumber;
    }

}