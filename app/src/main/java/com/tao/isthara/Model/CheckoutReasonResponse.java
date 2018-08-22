package com.tao.isthara.Model;

import java.util.ArrayList;

public class CheckoutReasonResponse {
    private boolean IsValid;

    public boolean getIsValid() { return this.IsValid; }

    private int TotalRecord;

    public int getTotalRecord() { return this.TotalRecord; }

    private ArrayList<Records> Records;

    public ArrayList<Records> getRecords() { return this.Records; }

}
