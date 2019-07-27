package com.example.warehouseapplication.Model;

public class OrderDetail {

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    String code,name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    int amount,price;

}
