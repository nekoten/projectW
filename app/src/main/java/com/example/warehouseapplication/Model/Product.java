package com.example.warehouseapplication.Model;

public class Product {

    String Id, Name ,Pic;

    public String rOWAUTO;
    public String code;
    public String detail;

    public String getrOWAUTO() {
        return rOWAUTO;
    }

    public void setrOWAUTO(String rOWAUTO) {
        this.rOWAUTO = rOWAUTO;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getPriceIn() {
        return priceIn;
    }

    public void setPriceIn(String priceIn) {
        this.priceIn = priceIn;
    }

    public String getPriceOut() {
        return priceOut;
    }

    public void setPriceOut(String priceOut) {
        this.priceOut = priceOut;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String unitId;
    public String priceIn;
    public String priceOut;
    public String pic;
    public String status;

    public Product(String id, String name,String code ,String pic) {
        Id = id;
        Name = name;
        this.code = code;
        Pic = pic;
    }

    public String getId() {
        return Id;
    }

    public String getName() {
        return Name;
    }

    public String getPics() {
        return Pic;
    }
}
