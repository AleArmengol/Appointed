package com.example.appointed.models;

import java.io.Serializable;

public class Patient implements Serializable {
    private int id;
    private String name;
    private String last_name;
    private String phone_number;
    private String address;
    private String email;
    private String created_at;
    private String updated_at;
    private boolean payment_uptodate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public boolean isPayment_uptodate() {
        return payment_uptodate;
    }

    public void setPayment_uptodate(boolean payment_uptodate) {
        this.payment_uptodate = payment_uptodate;
    }
}
