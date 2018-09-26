package com.tao.isthara.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class LastFiveMonthDueResponse {
    private boolean IsValid;

    public boolean getIsValid() {
        return this.IsValid;
    }

    private int TotalRecord;

    public int getTotalRecord() {
        return this.TotalRecord;
    }

    private ArrayList<Result> Result;

    public ArrayList<Result> getResult() {
        return this.Result;
    }
}
