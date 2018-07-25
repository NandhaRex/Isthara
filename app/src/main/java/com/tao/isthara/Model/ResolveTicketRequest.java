
package com.tao.isthara.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResolveTicketRequest {

    @SerializedName("HelpDeskId")
    @Expose
    private Integer helpDeskId;
    @SerializedName("userId")
    @Expose
    private Integer userId;

    @SerializedName("Resolution")
    @Expose
    private String Resolution;

    public Integer getHelpDeskId() {
        return helpDeskId;
    }

    public void setHelpDeskId(Integer helpDeskId) {
        this.helpDeskId = helpDeskId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getResolution() {
        return Resolution;
    }

    public void setResolution(String mClosingComments) {
        this.Resolution = mClosingComments;
    }


}
