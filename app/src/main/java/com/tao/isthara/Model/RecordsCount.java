
package com.tao.isthara.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RecordsCount {

    @SerializedName("Available")
    @Expose
    private Integer available;
    @SerializedName("Assigned")
    @Expose
    private Integer assigned;
    @SerializedName("Sent")
    @Expose
    private Integer sent;
    @SerializedName("Completed")
    @Expose
    private Integer completed;

    public Integer getAvailable() {
        return available;
    }

    public void setAvailable(Integer available) {
        this.available = available;
    }

    public Integer getAssigned() {
        return assigned;
    }

    public void setAssigned(Integer assigned) {
        this.assigned = assigned;
    }

    public Integer getSent() {
        return sent;
    }

    public void setSent(Integer sent) {
        this.sent = sent;
    }

    public Integer getCompleted() {
        return completed;
    }

    public void setCompleted(Integer completed) {
        this.completed = completed;
    }

}
