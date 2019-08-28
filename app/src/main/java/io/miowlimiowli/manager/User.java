package io.miowlimiowli.manager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 前端不应修改
 */
public class User {
    public String username;
    public String password;
    public String mail_address;
    public String nickname;
    public String short_description, long_description;
    public boolean is_vip;

    public User(String username, String password){
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
