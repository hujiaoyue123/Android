package com.boxin.beautypine.model;

/**
 * 资讯Model
 * User: zouyu
 * Date: :2017/10/10
 * Version: 1.0
 */

public class News {

    private String id;
    private String title;
    private String createtime;
    private String sid;
    private String glid;
    private String content;
    private String codelist;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getGlid() {
        return glid;
    }

    public void setGlid(String glid) {
        this.glid = glid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCodelist() {
        return codelist;
    }

    public void setCodelist(String codelist) {
        this.codelist = codelist;
    }
}
