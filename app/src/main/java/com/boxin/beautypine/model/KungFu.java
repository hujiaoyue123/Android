package com.boxin.beautypine.model;

/**
 * 股市真功能夫
 * User: zouyu
 * Date: :2017/10/16 0016
 * Version: 1.0
 */

public class KungFu {

    private Integer id;

    private String title;

    private String kfVideo;

    private String kfImg;

    private String kfDesc;

    private Integer kfPublish;

    private Integer onoff;

    private Integer delFlag;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getKfVideo() {
        return kfVideo;
    }

    public void setKfVideo(String kfVideo) {
        this.kfVideo = kfVideo;
    }

    public String getKfImg() {
        return kfImg;
    }

    public void setKfImg(String kfImg) {
        this.kfImg = kfImg;
    }

    public String getKfDesc() {
        return kfDesc;
    }

    public void setKfDesc(String kfDesc) {
        this.kfDesc = kfDesc;
    }

    public Integer getKfPublish() {
        return kfPublish;
    }

    public void setKfPublish(Integer kfPublish) {
        this.kfPublish = kfPublish;
    }

    public Integer getOnoff() {
        return onoff;
    }

    public void setOnoff(Integer onoff) {
        this.onoff = onoff;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }
}
