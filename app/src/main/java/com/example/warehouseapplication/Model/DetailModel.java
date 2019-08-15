package com.example.warehouseapplication.Model;

public class DetailModel {
    String ROWAUTO;
    String docno;

    String prod_name;
    String unit_name;
    String qty;
    String agv;
    String amount;

    public DetailModel(String ROWAUTO, String docno, String prod_name, String unit_name, String qty, String agv, String amount) {
        this.ROWAUTO = ROWAUTO;
        this.docno = docno;

        this.prod_name = prod_name;
        this.unit_name = unit_name;
        this.qty = qty;
        this.agv = agv;
        this.amount = amount;
    }

    public String getROWAUTO() {
        return ROWAUTO;
    }

    public void setROWAUTO(String ROWAUTO) {
        this.ROWAUTO = ROWAUTO;
    }

    public String getDocno() {
        return docno;
    }

    public void setDocno(String docno) {
        this.docno = docno;
    }

    public String getProd_name() {
        return prod_name;
    }

    public void setProd_name(String prod_name) {
        this.prod_name = prod_name;
    }

    public String getUnit_name() {
        return unit_name;
    }

    public void setUnit_name(String unit_name) {
        this.unit_name = unit_name;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getAgv() {
        return agv;
    }

    public void setAgv(String agv) {
        this.agv = agv;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
