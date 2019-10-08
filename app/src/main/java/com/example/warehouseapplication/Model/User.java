package com.example.warehouseapplication.Model;

/**
 * Created by Killy77 on 14/4/2561.
 */

public class User {

    /**
     * id_member : 18
     * username : poon
     * name : null
     * lastname : พิมพ์รัตน์
     * telephone : null
     * address : 2166
     * firstname : วิชญ์พล
     * tel : 0820665945
     */

    private String id_member;
    private String username;
    private String name;
    private String lastname;
    private String telephone;
    private String address;
    private String firstname;
    private String tel;
    private String active;

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getId_member() {
        return id_member;
    }

    public void setId_member(String id_member) {
        this.id_member = id_member;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }
}
