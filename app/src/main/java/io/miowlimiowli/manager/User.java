package io.miowlimiowli.manager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 前端不应直接修改，应调用setter
 */
public class User {
    public String username;
    public String password;

    public void setMail_address(String mail_address) {
        this.mail_address = mail_address;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setShort_description(String short_description) {
        this.short_description = short_description;
    }

    public void setLong_description(String long_description) {
        this.long_description = long_description;
    }

    public void setIs_vip(boolean is_vip) {
        this.is_vip = is_vip;
    }

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
