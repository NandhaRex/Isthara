package com.tao.isthara.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Records{

    @SerializedName("IssueNumber")
    private String IssueNumber;

    private int EventDetails_Id;

    private ArrayList<String> DocumentData;

    public ArrayList<String> getDocumentData() { return this.DocumentData; }

    public int getEventDetailsId() { return this.EventDetails_Id; }

    public String getIssueNumber(){
        return IssueNumber;
    }
}