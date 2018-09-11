package com.tao.isthara.Model;

import java.math.BigInteger;

public class CheckOutRequest {
    private int ResidentDetailsId;

    public void setResidentDetailsId(int ResidentDetailsId) { this.ResidentDetailsId = ResidentDetailsId; }

    private int Reason_Id;

    public void setReasonId(int Reason_Id) { this.Reason_Id = Reason_Id; }

    private String CheckoutDate;

    public void setCheckoutDate(String CheckoutDate) { this.CheckoutDate = CheckoutDate; }

    public String getCheckoutDate() {
        return CheckoutDate;
    }

    private String BankName;

    public void setBankName(String BankName) { this.BankName = BankName; }

    public String getBankName() {
        return BankName;
    }

    private String AccountHolderName;

    public void setAccountHolderName(String AccountHolderName) { this.AccountHolderName = AccountHolderName; }

    public String getAccountHolderName() {
        return AccountHolderName;
    }

    private String Reason;

    public String getReason() {
        return Reason;
    }

    private String IFSC;

    public void setIFSC(String IFSC) { this.IFSC = IFSC; }

    public String getIFSC() {
        return IFSC;
    }

    private String AccountNo;

    public void setAccountNo(String AccountNo) { this.AccountNo = AccountNo; }

    public String getAccountNo() {
        return AccountNo;
    }

    private String RequestedVia;

    public void setRequestedVia(String RequestedVia) { this.RequestedVia = RequestedVia; }

    public String getRequestedVia() {
        return RequestedVia;
    }

    private int RequestedBy;

    public void setRequestedBy(int RequestedBy) { this.RequestedBy = RequestedBy; }

    private String ResidentFeedBack;

    public String getResidentFeedBack() {
        return ResidentFeedBack;
    }

    public void setResidentFeedBack(String residentFeedBack) {
        ResidentFeedBack = residentFeedBack;
    }
}
