package com.tao.isthara.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class EventsHeaderImageResponse {

    private boolean IsValid;

    public boolean getIsValid() { return this.IsValid; }

    public void setIsValid(boolean IsValid) { this.IsValid = IsValid; }

    private int TotalRecord;

    public int getTotalRecord() { return this.TotalRecord; }

    public void setTotalRecord(int TotalRecord) { this.TotalRecord = TotalRecord; }

    @SerializedName("Records")
    private ArrayList<Records> Records;

    @SerializedName("HeaderImage")
    private ArrayList<HeaderImage> HeaderImage;

    public ArrayList<Records> getRecords() { return this.Records; }

    public void setRecords(ArrayList<Records> Records) { this.Records = Records; }

    public ArrayList<HeaderImage> getHeaderImage() {
        return HeaderImage;
    }
}
