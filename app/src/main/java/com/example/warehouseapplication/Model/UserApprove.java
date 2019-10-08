package com.example.warehouseapplication.Model;

public class UserApprove {

    public String ROWAUTO,username,userpasword,user_type,name,lastname,telephone,address,active;


    public UserApprove(
             String ROWAUTO
            ,String username
            ,String userpasword
            ,String user_type
            ,String name
            ,String lastname
            ,String telephone
            ,String address
            ,String active) {
        this.ROWAUTO = ROWAUTO;
        this.username = username;
        this.userpasword = userpasword;
        this.user_type = user_type;
        this.name = name;
        this.lastname = lastname;
        this.telephone = telephone;
        this.address = address;
        this.active = active;

    }

    public String getROWAUTO() {
        return ROWAUTO;
    }

    public void setROWAUTO(String ROWAUTO) {
        this.ROWAUTO = ROWAUTO;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserpasword() {
        return userpasword;
    }

    public void setUserpasword(String userpasword) {
        this.userpasword = userpasword;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }
}
