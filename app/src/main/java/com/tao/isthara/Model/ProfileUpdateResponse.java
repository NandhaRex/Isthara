package com.tao.isthara.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProfileUpdateResponse {
    @SerializedName("IsValid")
    @Expose
    private String isValid;
    @SerializedName("Result")
    @Expose
    private String result;

    public String getResult()
    {
        return result;
    }
}
