
package com.tao.isthara.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CloseTicketRequest {

    @SerializedName("HelpDeskId")
    @Expose
    private Integer helpDeskId;
    @SerializedName("userId")
    @Expose
    private Integer userId;

    @SerializedName("ClosingComments")
    @Expose
    private String ClosingComments;

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

    public String getClosingComments() {
        return ClosingComments;
    }

    public void setClosingComments(String mClosingComments) {
        this.ClosingComments = mClosingComments;
    }


}
