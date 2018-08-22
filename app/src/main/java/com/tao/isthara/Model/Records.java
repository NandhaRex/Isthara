package com.tao.isthara.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Records{

    @SerializedName("IssueNumber")
    private String IssueNumber;

    private boolean IsLinkGivenToEventDetail;

    private int EventDetails_Id;

    private String EventDetailsType;

    private String DocumentData;

    //getReasonforExist
    private String Text;

    private int Value;

    public String getText() { return this.Text; }

    public int getValue() { return this.Value; }

    public String getDocumentData() { return this.DocumentData; }

    public int getEventDetailsId() { return this.EventDetails_Id; }

    public String getIssueNumber(){
        return IssueNumber;
    }

    public int getEventDetails_Id() {
        return EventDetails_Id;
    }

    public String getEventDetailsType() {
        return EventDetailsType;
    }

     public  boolean getIsLinkGivenToEventDetail()
     {
         return  IsLinkGivenToEventDetail;
     }
}