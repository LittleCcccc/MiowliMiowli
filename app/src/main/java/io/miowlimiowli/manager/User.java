package io.miowlimiowli.manager;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Pair;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import io.miowlimiowli.R;
import io.miowlimiowli.manager.sql.SqlUser;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

/**
 * 前端不应直接修改，应调用setter
 * 修改类别列表用setCat_list
 */
public class User {
    public String username;
    public String password;
    public Drawable avator;
    public String mail_address;
    public String nickname;
    public String short_description, long_description;
    public boolean is_vip;
    public List<String> cat_list;
    public User(SqlUser user){
        this.username = user.username;
        this.password = user.password;
        this .mail_address = user.mail_address;
        this.nickname = user.nickname;
        this.short_description = user.short_description;
        this.long_description = user.long_description;
        this.is_vip = user.is_vip;
        cat_list = new ArrayList<>(Manager.cat_list);
    }

    public void setCat_list(List<String> cat_list) {
        this.cat_list = cat_list;
    }



    public User(String username, String email, String password){
        this.username = username;
        this.mail_address = email;
        this.password = password;

        //set default

        this.nickname = username;
        this.short_description = "一只小朋友";
        this.long_description = "一大大大大只小朋友！";
        is_vip = false;

    }

    Runnable update = ()->{
        Manager.getInstance().db.SqlUserDao().update(new SqlUser(this));
    };

    public void setAvator(Context context, Drawable avator) {
        this.avator = avator;
        try {
            OutputStream out = context.openFileOutput(username + ".png", Context.MODE_PRIVATE);
            Bitmap bitmap = drawableToBitmap(avator);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
            byte[] bitmapdata = bos.toByteArray();

            out.write(bitmapdata);
            out.close();
        } catch (IOException e) {
            System.out.println("save avator failed!");
            e.printStackTrace();
        }
    }
    public void setMail_address(String mail_address) {
        this.mail_address = mail_address;
        Observable.just("suck")
                .subscribeOn(Schedulers.io())
                .subscribe((item)->{update.run();});
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
        Observable.just("suck")
                .subscribeOn(Schedulers.io())
                .subscribe((item)->{update.run();});
    }

    public void setShort_description(String short_description) {
        this.short_description = short_description;
        Observable.just("suck")
                .subscribeOn(Schedulers.io())
                .subscribe((item)->{update.run();});
    }

    public void setLong_description(String long_description) {
        this.long_description = long_description;
        Observable.just("suck")
                .subscribeOn(Schedulers.io())
                .subscribe((item)->{update.run();});
    }

    public void setIs_vip(boolean is_vip) {
        this.is_vip = is_vip;
        Observable.just("suck")
                .subscribeOn(Schedulers.io())
                .subscribe((item)->{update.run();});
    }
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }


    public static Bitmap drawableToBitmap (Drawable drawable) {
        Bitmap bitmap = null;

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if(bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if(drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }
}
