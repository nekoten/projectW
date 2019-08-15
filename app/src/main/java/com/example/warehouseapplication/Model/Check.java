package com.example.warehouseapplication.Model;

public class Check {

    public String DocNo, Status,Slip;
    public String DocDate;
    public String StockId;
    public String amount;
    public String dis;
    public String amountAll;


    public Check(String docNo, String status, String slip, String dis, String amountAll) {
        this.DocNo = docNo;
        this.Status = status;
        this.Slip = slip;
        this.dis = dis;
        this.amountAll = amountAll;
    }

    public String getDocNo() {
        return DocNo;
    }

    public void setDocNo(String docNo) {
        DocNo = docNo;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getSlip() {
        return Slip;
    }

    public void setSlip(String slip) {
        Slip = slip;
    }

    public String getDis() {
        return dis;
    }

    public void setDis(String dis) {
        this.dis = dis;
    }

    public String getAmountAll() {
        return amountAll;
    }

    public void setAmountAll(String amountAll) {
        this.amountAll = amountAll;
    }
}
