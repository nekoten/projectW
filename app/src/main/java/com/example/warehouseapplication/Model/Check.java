package com.example.warehouseapplication.Model;

public class Check {

    String DocNo, Status;

    public String rOWAUTO;
  ;

    public String getrOWAUTO() {
        return rOWAUTO;
    }

    public void setrOWAUTO(String rOWAUTO) {
        this.rOWAUTO = rOWAUTO;
    }

    public String getDocDate() {
        return DocDate;
    }

    public void setDocDate(String DocDate) {
        this.DocDate = DocDate;
    }

    public String getStockId() {
        return StockId;
    }

    public void setStockId(String StockId) {
        this.StockId = StockId;
    }

    public String getamount() {
        return amount;
    }

    public void setamount(String amount) {
        this.amount = amount;
    }

    public String getdis() {
        return dis;
    }

    public void setdis(String dis) {
        this.dis = dis;
    }

    public String getamountAll() {
        return amountAll;
    }

    public void setamountAll(String amountAll) {
        this.amountAll = amountAll;
    }

    public String DocDate;
    public String StockId;
    public String amount;
    public String dis;
    public String amountAll;
    public String slip;

    public String getSlip() {
        return slip;
    }

    public void setSlip(String slip) {
        this.slip = slip;
    }

    public Check(String docNo, String status) {
        DocNo = docNo;
        Status = status;

    }

    public String getDocNo() {
        return DocNo;
    }

    public String getStatus() {
        return Status;
    }

    public static class ItemsBean {
        /**
         * id_order : 19
         * id_member : 2
         * id_emp : null
         * date : 2018-05-16 00:19:43
         * totalPrice : 917
         * id_promotion : null
         * id_status : 5
         * id_orderType : 2
         * status : รอการอนุมัติ
         * Firstname : Poon
         * Lastname : Pim
         * Email : a@m.com
         * Password : 123456
         * Cash : 2000
         * Tel : 0820665945
         * Latitude : 97845632
         * Longitude : 0
         */

        private String docno;
        private String status;


        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getDocno() {
            return docno;
        }

        public void setDocno(String docno) {
            this.docno = docno;
        }

    }

}
