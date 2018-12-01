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
}
