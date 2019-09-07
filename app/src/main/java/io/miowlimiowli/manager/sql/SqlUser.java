package io.miowlimiowli.manager.sql;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

import io.miowlimiowli.manager.Manager;
import io.miowlimiowli.manager.User;

@Entity(tableName = "user")
public class SqlUser {
    @PrimaryKey@NonNull
    public String username;
    @ColumnInfo(name = "password")
    public String password;
    @ColumnInfo(name = "mail_address")
    public String mail_address;
    @ColumnInfo(name = "nickname")
    public String nickname;
    @ColumnInfo(name = "short_description")
    public String short_description;
    @ColumnInfo(name = "long_description")
    public String long_description;
    @ColumnInfo(name = "is_vip")
    public boolean is_vip;

    public SqlUser(){};

    public SqlUser(User user){
        this.username = user.username;
        this.password = user.password;
        this .mail_address = user.mail_address;
        this.nickname = user.nickname;
        this.short_description = user.short_description;
        this.long_description = user.long_description;
        this.is_vip = user.is_vip;
    }
}
