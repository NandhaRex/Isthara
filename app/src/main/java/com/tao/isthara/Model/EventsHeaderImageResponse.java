package com.tao.isthara.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class EventsHeaderImageResponse {

    private boolean IsValid;
    private int TotalRecord;
    private EventData EventData;

    @SerializedName("Records")
    private ArrayList<Records> Records;

    @SerializedName("HeaderImage")
    private ArrayList<HeaderImage> HeaderImage;

    public EventData getEventData() {
        return this.EventData;
    }

    public boolean getIsValid() {
        return this.IsValid;
    }

    public int getTotalRecord() {
        return this.TotalRecord;
    }

    public ArrayList<Records> getRecords() {
        return this.Records;
    }

    public ArrayList<HeaderImage> getHeaderImage() {
        return HeaderImage;
    }

    public void setTotalRecord(int TotalRecord) {
        this.TotalRecord = TotalRecord;
    }

    public void setIsValid(boolean IsValid) {
        this.IsValid = IsValid;
    }


    public void setRecords(ArrayList<Records> Records) {
        this.Records = Records;
    }

}
