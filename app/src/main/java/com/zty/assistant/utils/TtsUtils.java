package com.zty.assistant.utils;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechEvent;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;

import java.util.ArrayList;

/**
 * Created by zhang.tianyi on 2019/12/23 16:54
 * description : 用于提供科大讯飞TTS功能
 */

public class TtsUtils {
    private static final String TAG = TtsUtils.class.getSimpleName();

    private SpeechSynthesizer mTts;

    private static boolean isSynthesizer = false;
    private static boolean isFirst = true;

    private static TtsUtils mInstance;

    private static ArrayList<String[]> mList;

    private String name = "";

    public static TtsUtils getInstance(Context context){
        if (mInstance == null){
            mInstance = new TtsUtils(context.getApplicationContext());
        }
        return mInstance;
    }

    private TtsUtils(Context context){
        if (mTts == null) {
            mTts = SpeechSynthesizer.createSynthesizer(context,mTtsInitListener);
            mList = new ArrayList<>();
            isFirst = true;
        }
    }

    public int startSpeaking(String texts, SynthesizerListener mTtsListener){
        if (isFirst){
            // 设置参数
            setParam();
        }
        if (mTtsListener == null) {
            return mTts.startSpeaking(texts, TtsUtils.this.mTtsListener);
        } else {
            return mTts.startSpeaking(texts, mTtsListener);
        }
    }

    /**
     * 开始tts播报
     * @param name 播报的名字
     * @param message 播报的消息内容
     * @param isContinue 是否连续播报
     * @param mTtsListener 播报监听
     */
    public void startSpeaking(String name,String message,boolean isContinue, SynthesizerListener mTtsListener){
        if (isFirst){
            // 设置参数
            setParam();
        }
        if (!isSynthesizer){
            MyToast.getInstance().showLong("讯飞音频初始化失败,请清理后台重新打开本应用");
            return;
        }
        Log.e(TAG,"isSpeaking = " + mTts.isSpeaking());
        if (mTts.isSpeaking()){
            mList.add(new String[]{name,message});
            Log.e(TAG,"添加，mList.size = " + mList.size());
        } else {
            if (mTtsListener == null) {
                mTts.startSpeaking(getSpeakText(name,message,isContinue), TtsUtils.this.mTtsListener);
            } else {
                mTts.startSpeaking(getSpeakText(name,message,isContinue), mTtsListener);
            }
        }
    }

    private String getSpeakText(String name,String message,boolean isContinue){
        if (isContinue && name.equals(this.name)){
            return message;
        } else {
            return "收到"+name+"的消息"+message;
        }
    }

    public void pauseSpeaking(){
        mTts.pauseSpeaking();
    }

    public void resumeSpeaking(){
        mTts.resumeSpeaking();
    }

    public void stopSpeaking(){
        mTts.stopSpeaking();
    }

    /**
     * 初始化监听。
     */
    private InitListener mTtsInitListener = new InitListener() {
        @Override
        public void onInit(int code) {
            if (code != ErrorCode.SUCCESS) {
                Log.i(TAG,"初始化失败,错误码："+code+",请点击网址https://www.xfyun.cn/document/error-code查询解决方案");
                MyToast.getInstance().showLong("讯飞音频初始化失败,请清理后台重新打开本应用");
                isSynthesizer = false;
            } else {
                Log.i(TAG,"初始化成功");
                isSynthesizer = true;
            }
        }
    };

    /**
     * 合成回调监听。
     */
    private SynthesizerListener mTtsListener = new SynthesizerListener() {

        @Override
        public void onSpeakBegin() {
            Log.i(TAG,"开始播放");
        }

        @Override
        public void onSpeakPaused() {
            Log.i(TAG,"暂停播放");
        }

        @Override
        public void onSpeakResumed() {
            Log.i(TAG,"继续播放");
        }

        @Override
        public void onBufferProgress(int percent, int beginPos, int endPos,
                                     String info) {
            // 合成进度
        }

        @Override
        public void onSpeakProgress(int percent, int beginPos, int endPos) {
            // 播放进度
        }

        @Override
        public void onCompleted(SpeechError error) {
//            if (error == null) {
//                //	showTip("播放完成");
//            } else if (error != null) {
//                Log.e(TAG,"播放发生错误"+error.getErrorDescription());
//            }
            Log.e(TAG,"播放完成,mList.size = " + mList.size());
            if (mList.size() != 0){
                String[] strings = mList.get(0);
                mList.remove(0);
                startSpeaking(strings[0],strings[1],true,null);
            }
        }

        @Override
        public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
            //	 以下代码用于获取与云端的会话id，当业务出错时将会话id提供给技术支持人员，可用于查询会话日志，定位出错原因
            //	 若使用本地能力，会话id为null
            if (SpeechEvent.EVENT_SESSION_ID == eventType) {
                String sid = obj.getString(SpeechEvent.KEY_EVENT_SESSION_ID);
                Log.d(TAG, "session id =" + sid);
            }

            //当设置SpeechConstant.TTS_DATA_NOTIFY为1时，抛出buf数据
            if (SpeechEvent.EVENT_TTS_BUFFER == eventType) {
                byte[] buf = obj.getByteArray(SpeechEvent.KEY_EVENT_TTS_BUFFER);
                Log.e("MscSpeechLog_", "bufis =" + buf.length);
            }


        }
    };



    // 引擎类型(在线引擎)
    private String mEngineType = SpeechConstant.TYPE_CLOUD;
    // 默认发音人
    private String voicer = "xiaoyan";

    /**
     * 参数设置
     */
    private void setParam(){
        isFirst = false;
        // 清空参数
        mTts.setParameter(SpeechConstant.PARAMS, null);
        // 根据合成引擎设置相应参数
        if(mEngineType.equals(SpeechConstant.TYPE_CLOUD)) {
            mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
            //支持实时音频返回，仅在synthesizeToUri条件下支持
            mTts.setParameter(SpeechConstant.TTS_DATA_NOTIFY, "1");
            //	mTts.setParameter(SpeechConstant.TTS_BUFFER_TIME,"1");

            // 设置在线合成发音人
            mTts.setParameter(SpeechConstant.VOICE_NAME, voicer);
            //设置合成语速
            mTts.setParameter(SpeechConstant.SPEED, "50");
            //设置合成音调
            mTts.setParameter(SpeechConstant.PITCH, "50");
            //设置合成音量
            mTts.setParameter(SpeechConstant.VOLUME, "50");
        }else {
            mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_LOCAL);
            mTts.setParameter(SpeechConstant.VOICE_NAME, "");

        }

        //设置播放器音频流类型
        mTts.setParameter(SpeechConstant.STREAM_TYPE, "3");
        // 设置播放合成音频打断音乐播放，默认为true
        mTts.setParameter(SpeechConstant.KEY_REQUEST_FOCUS, "false");

        // 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
        mTts.setParameter(SpeechConstant.AUDIO_FORMAT, "pcm");
        mTts.setParameter(SpeechConstant.TTS_AUDIO_PATH, Environment.getExternalStorageDirectory()+"/msc/tts.pcm");
    }
}
