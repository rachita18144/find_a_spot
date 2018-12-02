package com.example.rachitabhagchandani.findaspot;

import java.io.Serializable;

public class User implements Serializable{
    public String email_id, name , phone;

    public User(String email, String name, String phone) {
        this.email_id = email;
        this.name = name;
        this.phone = phone;
    }

    public User() {
    }

    public String getEmail_id() {
        return email_id;
    }

    public void setEmail_id(String email_id) {
        this.email_id = email_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
