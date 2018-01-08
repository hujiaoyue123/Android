package com.boxin.beautypine.utils;

/**
 * 项目环境
 * User: zouyu
 * Date: :2017/10/8
 * Version: 1.0
 */

public class Config {

    public final static String DOMAIN = "http://www.cctvjy.cn";
    //home
    public final static String GET_HOME_BANNER = DOMAIN + "/banner/list.do?size=4";                 //获取banner
    public final static String GET_HOME_TEACHER = DOMAIN + "/teacher/list.do?size=99&type=1";                 //获取banner
    public final static String GET_LIVELIST = DOMAIN + "/program/liveList.do";                      //获取直播列表
    public final static String GET_REVIEWLIST = DOMAIN + "/review/list.do";                      //获取战记回顾列表?index=1&size=5
    public final static String GET_KUNGFULIST = DOMAIN + "/program/kungfuList.do";                   //获取股市真功夫?pageIndex=2&pageSize=4"
    public final static String GET_HOME_NEWS = "http://zixun.cctvjy.cn/zixunserver/index.php/news14/datacon/909/5"; //获取首页资讯
    //openclass
    public final static String GET_LIVELISTHIS = DOMAIN + "/program/liveList.do";                   //获取公开课历史
    //course
    public final static String GET_COURSE = DOMAIN + "/program/getCourses.do";                      //获取高级课程
    public final static String GET_COURSEDETAIL = DOMAIN + "/program/getCourseDetails.do";              //获取课程详情
}
