package com.tao.isthara.Model;

import java.math.BigInteger;

public class CheckOutRequest {
    private int ResidentDetailsId;

    public void setResidentDetailsId(int ResidentDetailsId) { this.ResidentDetailsId = ResidentDetailsId; }

    private int Reason_Id;

    public void setReasonId(int Reason_Id) { this.Reason_Id = Reason_Id; }

    private String CheckoutDate;

    public void setCheckoutDate(String CheckoutDate) { this.CheckoutDate = CheckoutDate; }

    private String BankName;

    public void setBankName(String BankName) { this.BankName = BankName; }

    private String AccountHolderName;

    public void setAccountHolderName(String AccountHolderName) { this.AccountHolderName = AccountHolderName; }

    private String IFSC;

    public void setIFSC(String IFSC) { this.IFSC = IFSC; }

    private String AccountNo;

    public void setAccountNo(String AccountNo) { this.AccountNo = AccountNo; }

    private String RequestedVia;

    public void setRequestedVia(String RequestedVia) { this.RequestedVia = RequestedVia; }

    private int RequestedBy;

    public void setRequestedBy(int RequestedBy) { this.RequestedBy = RequestedBy; }

}
