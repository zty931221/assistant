package com.zty.assistant.greendao.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

import androidx.annotation.NonNull;

/**
 * Created by zhang.tianyi on 2019/12/24 14:32
 */

@Entity
public class WeChatBean {
    @Id
    private Long id;
//    private boolean switchPlayByFontAndBack;//"按前后台播报"开关
//    private boolean switchBackGroundPlay;//"微信在后台时播报"开关
//    private boolean switchFrontPlay;//"微信在前台时播报"开关
    private boolean switchPlayByBlackAndWhite;//"按白名单播报"开关
    private boolean switchOutBlackListPlay;//"只播报黑名单外"开关
    private boolean switchInWhiteListPlay;//"只播报白名单内"开关

    @Generated(hash = 29489484)
    public WeChatBean(Long id, boolean switchPlayByBlackAndWhite, boolean switchOutBlackListPlay,
            boolean switchInWhiteListPlay) {
        this.id = id;
        this.switchPlayByBlackAndWhite = switchPlayByBlackAndWhite;
        this.switchOutBlackListPlay = switchOutBlackListPlay;
        this.switchInWhiteListPlay = switchInWhiteListPlay;
    }

    @Generated(hash = 1246098617)
    public WeChatBean() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @NonNull
    @Override
    public String toString() {
        return "WeChatBean:"
//                + "按前后台播报 = " + switchPlayByFontAndBack
//                + "微信在后台时播报 = " + switchBackGroundPlay
//                + "微信在前台时播报 = " + switchFrontPlay
                + "按白名单播报 = " + switchPlayByBlackAndWhite
                + "只播报黑名单外 = " + switchOutBlackListPlay
                + "只播报白名单内 = " + switchInWhiteListPlay;
    }

//    public boolean getSwitchPlayByFontAndBack() {
//        return this.switchPlayByFontAndBack;
//    }
//
//    public void setSwitchPlayByFontAndBack(boolean switchPlayByFontAndBack) {
//        this.switchPlayByFontAndBack = switchPlayByFontAndBack;
//    }
//
//    public boolean getSwitchBackGroundPlay() {
//        return this.switchBackGroundPlay;
//    }
//
//    public void setSwitchBackGroundPlay(boolean switchBackGroundPlay) {
//        this.switchBackGroundPlay = switchBackGroundPlay;
//    }
//
//    public boolean getSwitchFrontPlay() {
//        return this.switchFrontPlay;
//    }
//
//    public void setSwitchFrontPlay(boolean switchFrontPlay) {
//        this.switchFrontPlay = switchFrontPlay;
//    }

    public boolean getSwitchPlayByBlackAndWhite() {
        return this.switchPlayByBlackAndWhite;
    }

    public void setSwitchPlayByBlackAndWhite(boolean switchPlayByBlackAndWhite) {
        this.switchPlayByBlackAndWhite = switchPlayByBlackAndWhite;
    }

    public boolean getSwitchOutBlackListPlay() {
        return this.switchOutBlackListPlay;
    }

    public void setSwitchOutBlackListPlay(boolean switchOutBlackListPlay) {
        this.switchOutBlackListPlay = switchOutBlackListPlay;
    }

    public boolean getSwitchInWhiteListPlay() {
        return this.switchInWhiteListPlay;
    }

    public void setSwitchInWhiteListPlay(boolean switchInWhiteListPlay) {
        this.switchInWhiteListPlay = switchInWhiteListPlay;
    }

//    public boolean isNeedPermissions(){
//        return switchFrontPlay&&switchBackGroundPlay&&switchInWhiteListPlay&&switchOutBlackListPlay;
//    }

    public boolean isNeedPermissions(){
        return switchInWhiteListPlay&&switchOutBlackListPlay;
    }
}
