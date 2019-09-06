package io.miowlimiowli;

import android.app.Application;
import android.widget.ImageView;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;

import io.miowlimiowli.R;
import io.miowlimiowli.manager.Manager;


public class MainApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // 创建数据管理
       Manager.getInstance().setContext(this);

    }
}
