package io.miowlimiowli.others;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.baidu.tts.client.SpeechError;
import com.baidu.tts.client.SpeechSynthesizer;
import com.baidu.tts.client.SpeechSynthesizerListener;
import com.baidu.tts.client.TtsMode;

import java.util.ArrayList;


/**
 * 语音合成工具类
 *
 * @author JPH
 * @date 2015-4-14 下午1:33:37
 */
public class SpeechUtil implements SpeechSynthesizerListener {
    private String appId = "10656192";
    private String appKey = "uUD2UqbvPKA5kUKcxFvngo2f";
    private String secretKey = "Xaikvmcfi6OrGWzaxcV8VmrmoAiro9Dz";

    protected static final int UI_LOG_TO_VIEW = 0;
    private SpeechSynthesizer speechSynthesizer;
    private Context context;
    private Activity activity;
    private TtsMode ttsMode = TtsMode.ONLINE;

    public SpeechUtil(Context context,int voice) {
        this.context = activity;
        init(context,voice);
    }
    /**
     * 初始化合成相关组件
     *
     * @author JPH
     * @date 2015-4-14 下午1:36:53
     */
    private void init(Context context,int voice) {
        speechSynthesizer = SpeechSynthesizer.getInstance();
        // 此处需要将setApiKey方法的两个参数替换为你在百度开发者中心注册应用所得到的apiKey和secretKey
        speechSynthesizer.setAppId(appId);
        speechSynthesizer.setApiKey(appKey,secretKey);
        speechSynthesizer.setContext(context);
        speechSynthesizer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        speechSynthesizer.initTts(TtsMode.ONLINE);
//		activity.setVolumeControlStream(AudioManager.STREAM_MUSIC);
        setParams(voice);
    }

    private void initPermission() {
        String[] permissions = {
                Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.MODIFY_AUDIO_SETTINGS,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_SETTINGS,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.CHANGE_WIFI_STATE
        };

        ArrayList<String> toApplyList = new ArrayList<String>();

        for (String perm : permissions) {
            if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(context, perm)) {
                toApplyList.add(perm);
                // 进入到这里代表没有权限.
            }
        }
        String[] tmpList = new String[toApplyList.size()];
        if (!toApplyList.isEmpty()) {
            ActivityCompat.requestPermissions(activity, toApplyList.toArray(tmpList), 123);
        }

    }

    /**
     * 开始文本合成并朗读
     * @author JPH
     * @date 2015-4-14 下午1:47:05
     * @param content
     */
    public void speak(final String content) {
        if (speechSynthesizer == null) {
            System.out.println("[ERROR], 初始化失败");
            return;
        }
        speechSynthesizer.speak(content);
    }
    /**
     * 取消本次合成并停止朗读
     * @author JPH
     * @date 2015-4-14 下午2:20:33
     */
    public void stop() {
        speechSynthesizer.stop();
    }
    /**
     * 暂停文本朗读，如果没有调用speak(String)方法或者合成器初始化失败，该方法将无任何效果
     * @author JPH
     * @date 2015-4-14 下午2:21:07
     */
    public void pause() {
        speechSynthesizer.pause();
    }
    /**
     * 继续文本朗读，如果没有调用speak(String)方法或者合成器初始化失败，该方法将无任何效果
     * @author JPH
     * @date 2015-4-14 下午2:21:29
     */
    public void resume() {
        speechSynthesizer.resume();
    }
    /**
     * 为语音合成器设置相关参数
     * @author JPH
     * @date 2015-4-14 下午1:45:11
     */
    private void setParams(int voice) {
        // 5. 以下setParam 参数选填。不填写则默认值生效
        // 设置在线发声音人： 0 普通女声（默认） 1 普通男声 2 特别男声 3 情感男声<度逍遥> 4 情感儿童声<度丫丫>
        speechSynthesizer.setParam(SpeechSynthesizer.PARAM_SPEAKER, Integer.toString(voice));//发音人，目前支持女声(0)和男声(1)
        speechSynthesizer.setParam(SpeechSynthesizer.PARAM_VOLUME, "9");//音量，取值范围[0, 9]，数值越大，音量越大
        speechSynthesizer.setParam(SpeechSynthesizer.PARAM_SPEED, "5");//朗读语速，取值范围[0, 9]，数值越大，语速越快
        speechSynthesizer.setParam(SpeechSynthesizer.PARAM_PITCH, "5");//音调，取值范围[0, 9]，数值越大，音量越高
        speechSynthesizer.setParam(SpeechSynthesizer.PARAM_AUDIO_ENCODE,
                SpeechSynthesizer.AUDIO_ENCODE_AMR);//音频格式，支持bv/amr/opus/mp3，取值详见随后常量声明
        speechSynthesizer.setParam(SpeechSynthesizer.PARAM_AUDIO_RATE,
                SpeechSynthesizer.AUDIO_BITRATE_AMR_15K85);//音频比特率，各音频格式支持的比特率详见随后常量声明
    }

    public void setVoice(int i){
        speechSynthesizer.setParam(SpeechSynthesizer.PARAM_SPEAKER, Integer.toString(i));//发音人，目前支持女声(0)和男声(1)
    }

    public void setStereoVolume(float leftVolume, float rightVolume) {
        speechSynthesizer.setStereoVolume(leftVolume, rightVolume);
    }

    public void release() {
        speechSynthesizer.stop();
        speechSynthesizer.release();
        speechSynthesizer = null;
    }

    @Override
    public void onSynthesizeStart(String var1){
        Log.i("msg", "开始工作，请等待数据...");
    }

    @Override
    public void onSynthesizeDataArrived(String var1, byte[] var2, int var3){
        Log.i("msg", "新的音频数据：" + var2.length
                + ((var3!=0) ? "(end)" : ""));
    }

    @Override
    public void onSynthesizeFinish(String var1){
        Log.i("msg", "已结束");
    }

    @Override
    public void onSpeechStart(String var1){
        Log.i("msg", "朗读开始");
    }

    @Override
    public void onSpeechProgressChanged(String var1, int var2){
        // TODO Auto-generated method stub
    }

    @Override
    public void onSpeechFinish(String var1){
        Log.i("msg", "朗读已停止");
    }

    @Override
    public void onError(String var1, SpeechError var2){
        Log.i("msg", "发生错误：" + var2.description + "(" + var2.code
                + ")");
    }
}