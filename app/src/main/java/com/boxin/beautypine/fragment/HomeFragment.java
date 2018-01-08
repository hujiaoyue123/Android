package com.boxin.beautypine.fragment;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.boxin.beautypine.R;
import com.boxin.beautypine.activity.FullVideoActivity;
import com.boxin.beautypine.adapter.ListViewAdapter;
import com.boxin.beautypine.cache.ACache;
import com.boxin.beautypine.cache.Globals;
import com.boxin.beautypine.model.News;
import com.boxin.beautypine.model.ProgramLive;
import com.boxin.beautypine.model.Review;
import com.boxin.beautypine.utils.Config;
import com.boxin.beautypine.utils.LogUtils;
import com.boxin.beautypine.utils.PicassoImageLoader;
import com.boxin.beautypine.utils.StringUtils;
import com.squareup.picasso.Picasso;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * 首页内容
 * User: zouyu
 * Date: :2017/9/28
 * Version: 1.0
 */

public class HomeFragment extends BaseFragment {

    Banner banner;                  //轮播图
    List<String> images;            //banner images data
    List<ProgramLive> lives;        //live object data
    List<News> newsList;                //news object data
    List<Review> reviews;

    AutoLinearLayout homeLiveLineLayout;
    AutoLinearLayout homeReviewLineLayout;
    LayoutInflater layoutInflater;


    @Override
    protected void initView() {

        LogUtils.d("创建Homefragment页面");

        layoutInflater = LayoutInflater.from(mContext);
        banner = (Banner) mView.findViewById(R.id.home_banner);
        homeLiveLineLayout = (AutoLinearLayout) mView.findViewById(R.id.home_live_lineLayout);
        homeReviewLineLayout = (AutoLinearLayout) mView.findViewById(R.id.home_review_lineLayout);

    }
    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    /**
     * 获取数据
     */
    @Override
    protected void getDataFromServer() {
        LogUtils.d("HOME获取数据");
        getLiveListData();
        getBannerListData();
        getInfoListData();
        getReviewData();
        getTeacherData();
    }

    //获取老师
    public void getTeacherData(){
        String cacheData = ACache.get(getActivity()).getAsString(Globals.TEACHER_LIST);
        if (StringUtils.isEmpty(cacheData)){
            OkHttpUtils
                .get()
                .url(Config.GET_HOME_TEACHER)
                .build()
                .execute(new StringCallback()
                {
                    @Override
                    public void onError(Call call, Exception e, int i) {
                        LogUtils.e(e);
                    }
                    @Override
                    public void onResponse(String s, int i) {
                        LogUtils.d(s);
                        if(!StringUtils.isEmpty(s)){
                            try {
                                JSONObject jsonObj = new JSONObject(s);
                                Boolean success = jsonObj.getBoolean("success");
                                if(success){
                                    String data = jsonObj.getString("data");
                                    ACache.get(getActivity()).put(Globals.TEACHER_LIST,data,ACache.TIME_DAY/4);
                                    renderTeacher(data);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                });
        }else{
            renderTeacher(cacheData);
        }
    }

    //获取直播列表
    public void getLiveListData(){
        String cacheData = ACache.get(getActivity()).getAsString(Globals.LIVE_LIST);
        if(StringUtils.isEmpty(cacheData)){
            OkHttpUtils
                    .get()
                    .url(Config.GET_LIVELIST)
                    .build()
                    .execute(new StringCallback()
                    {
                        @Override
                        public void onError(Call call, Exception e, int i) {
                            LogUtils.e(e);
                        }
                        @Override
                        public void onResponse(String s, int i) {
                            LogUtils.d("livelist:"+s);
                            if(!StringUtils.isEmpty(s)){
                                try {
                                    JSONObject jsonObj = new JSONObject(s);
                                    Boolean success = jsonObj.getBoolean("success");
                                    if(success){
                                        String data = jsonObj.getString("data");
                                        //缓存数据6个小时
                                        ACache.get(getActivity()).put(Globals.LIVE_LIST,data,ACache.TIME_DAY/4);
                                        renderLiveList(data);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                        }
                    });
        }else {
            renderLiveList(cacheData);
        }
    }

    //获取Banner图
    public void getBannerListData(){
        String cacheData = ACache.get(getActivity()).getAsString(Globals.BANNER_LIST);
        if(StringUtils.isEmpty(cacheData)){
            OkHttpUtils
                    .get()
                    .url(Config.GET_HOME_BANNER)
                    .build()
                    .execute(new StringCallback()
                    {
                        @Override
                        public void onError(Call call, Exception e, int i) {
                            LogUtils.e(e);
                        }
                        @Override
                        public void onResponse(String s, int i) {
                            LogUtils.d(s);
                            if(!StringUtils.isEmpty(s)){
                                try {
                                    JSONObject jsonObj = new JSONObject(s);
                                    Boolean success = jsonObj.getBoolean("success");
                                    if(success){
                                        String data = jsonObj.getString("data");
                                        ACache.get(getActivity()).put(Globals.BANNER_LIST,data,ACache.TIME_DAY/4);
                                        renderBanner(data);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
        }else{
            renderBanner(cacheData);
        }
    }

    //获取资讯
    public void getInfoListData(){
        String cacheData = ACache.get(getActivity()).getAsString(Globals.NEWS_LIST);
        LogUtils.d("cache:"+cacheData);
        if (StringUtils.isEmpty(cacheData)){
            LogUtils.d("no cache so url");
            OkHttpUtils
                .get()
                .url(Config.GET_HOME_NEWS)
                .build()
                .execute(new StringCallback()
                {
                    @Override
                    public void onError(Call call, Exception e, int i) {
                        LogUtils.e(e);
                    }
                    @Override
                    public void onResponse(String s, int i) {
                        LogUtils.d(s);
                        if(!StringUtils.isEmpty(s)){
                            try {
                                s = s.substring(1,s.length()-1);
                                JSONObject jsonObj = new JSONObject(s);
                                String listData = jsonObj.getString("list");
                                if(!StringUtils.isEmpty(listData)){
                                    ACache.get(getActivity()).put(Globals.NEWS_LIST,listData,5*60);
                                    renderNews(listData);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
        }else{
            renderNews(cacheData);
        }



    }

    //战记回顾
    public void getReviewData(){
        String cacheData = ACache.get(getActivity()).getAsString(Globals.REVIEW_LIST);
        if (StringUtils.isEmpty(cacheData)){
            OkHttpUtils
                .get()
                .url(Config.GET_REVIEWLIST)
                .addParams("index", "1")
                .addParams("size", "5")
                .build()
                .execute(new StringCallback()
                {
                    @Override
                    public void onError(Call call, Exception e, int i) {
                        LogUtils.e(e);
                    }
                    @Override
                    public void onResponse(String s, int i) {
                        LogUtils.d(s);
                        if(!StringUtils.isEmpty(s)){
                            try {
                                JSONObject jsonObj = new JSONObject(s);
                                Boolean success = jsonObj.getBoolean("success");
                                if(success){
                                    String data = jsonObj.getString("data");
                                    ACache.get(getActivity()).put(Globals.REVIEW_LIST,data,ACache.TIME_DAY/4);
                                    renderReview(data);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                });
        }else{
            renderReview(cacheData);
        }


    }

    /**
     * 渲染轮播图
     */
    public void renderBanner(String data){
        images = new ArrayList<>();
        try {
            JSONArray array= new JSONArray(data);
            for(int i=0;i<array.length();i++){
                JSONObject jsonObject = (JSONObject) array.get(i);
                images.add(Config.DOMAIN+jsonObject.getString("bannerUrl"));
                LogUtils.d(jsonObject.getString("bannerUrl"));
            }

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    banner.setImageLoader(new PicassoImageLoader());
                    banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
                    banner.setBannerAnimation(Transformer.Default);
                    //设置轮播时间
                    banner.setDelayTime(5000);
                    banner.setImages(images);
                    banner.start();
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    /**
     * 渲染直播列表
     */
    public void renderLiveList(String data){
        try {
            lives = new ArrayList<>();
            JSONArray array= new JSONArray(data);
            for(int i=0;i<array.length();i++){
                JSONObject jsonObject = (JSONObject) array.get(i);
                ProgramLive live = new ProgramLive();
                live.setInitImg(jsonObject.getString("initImg"));
                live.setName(jsonObject.getString("name"));
                live.setTeacherName(jsonObject.getString("teacherName"));
                live.setPlayBackUrl(jsonObject.getString("playBackUrl"));
                lives.add(live);
            }

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    for(int i=0;i<lives.size();i++){
                        View view = layoutInflater.inflate(R.layout.list_home_live,
                                homeLiveLineLayout, false);
                        ImageView imageView = (ImageView) view.findViewById(R.id.home_live_img);
                        TextView courseTitle = (TextView) view.findViewById(R.id.home_live_course_title);
                        TextView courseTeacher = (TextView) view.findViewById(R.id.home_live_course_teacher);
                        ProgramLive live = lives.get(i);
                        imageView.setTag(live);

                        Picasso.with(mView.getContext()).load(Uri.parse(Config.DOMAIN+live.getInitImg())).into(imageView);
                        courseTitle.setText(live.getName());
                        courseTeacher.setText(live.getTeacherName());
                        imageView.setOnClickListener(new View.OnClickListener(){

                            @Override
                            public void onClick(View v) {
                                LogUtils.d("click imageView");
                                //Toast.makeText(getActivity(), "信息ID:"+v.getTag(),Toast.LENGTH_SHORT).show();
                                ProgramLive programLive = (ProgramLive)v.getTag();
                                Intent intent = new Intent(getActivity(),FullVideoActivity.class);
                                intent.putExtra("title", programLive.getName());
                                intent.putExtra("url", programLive.getM3u8Url());
                                intent.putExtra("burl", programLive.getPlayBackUrl());
                                startActivity(intent);
                            }
                        });
                        homeLiveLineLayout.addView(view);
                    }
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 渲染新闻
     */
    public void renderNews(String data){
        try {
            newsList = new ArrayList();
            JSONArray array= new JSONArray(data);
            for(int i=0;i<array.length();i++){
                JSONObject jsonObject = (JSONObject) array.get(i);
                LogUtils.d(jsonObject.toString());
                News news = new News();
                news.setId(jsonObject.getString("id"));
                news.setTitle(jsonObject.getString("title"));
                news.setCreatetime(jsonObject.getString("createtime"));
                newsList.add(news);
            }

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ListView listView = (ListView)mView.findViewById(R.id.home_news_list);
                    ListViewAdapter adapter = new ListViewAdapter<News>(newsList) {

                        @Override
                        public View OvrideGetView(int position, View convertView, ViewGroup parent) {
                            View itemView = layoutInflater.inflate(R.layout.list_home_news, null);
                            TextView title = (TextView)itemView.findViewById(R.id.home_news_list_title);
                            TextView source = (TextView)itemView.findViewById(R.id.home_news_list_source);
                            TextView time = (TextView)itemView.findViewById(R.id.home_news_list_time);
                            News news = newsList.get(position);
                            String stitle = news.getTitle();
                            if(stitle.length()>25){
                                stitle = stitle.substring(0,24)+"...";
                            }
                            title.setText(stitle);
                            source.setText("弘学教育");
                            time.setText(news.getCreatetime());
                            return itemView;
                        }
                    };

                    listView.setAdapter(adapter);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            News news = newsList.get(position);   //通过position获取所点击的对象
                            //Toast显示测试
                            Toast.makeText(getActivity(), "信息ID:"+news.getId(),Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 渲染战记回顾
     * @param data
     */
    public void renderReview(String data){
        try {
            reviews = new ArrayList<>();
            JSONArray array= new JSONArray(data);
            for(int i=0;i<array.length();i++){
                JSONObject jsonObject = (JSONObject) array.get(i);
                Review review = new Review();
                review.setId(jsonObject.getLong("id"));
                review.setCreateTime(jsonObject.getInt("createTime"));
                review.setReviewLink(jsonObject.getString("reviewLink"));
                review.setReviewUrl(jsonObject.getString("reviewUrl"));
                reviews.add(review);
            }

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    for(int i=0;i<reviews.size();i++){
                        View view = layoutInflater.inflate(R.layout.list_home_review,
                                homeReviewLineLayout, false);
                        ImageView imageView = (ImageView) view.findViewById(R.id.home_review_img);
                        TextView time = (TextView) view.findViewById(R.id.home_review_course_time);
                        TextView source = (TextView) view.findViewById(R.id.home_review_course_source);
                        Review review = reviews.get(i);
                        imageView.setTag(review.getId());

                        Picasso.with(mView.getContext()).load(Uri.parse(Config.DOMAIN+review.getReviewUrl())).into(imageView);
                        /*time.setText(review.getCreateTime());
                        source.setText("弘学教育");*/
                        imageView.setOnClickListener(new View.OnClickListener(){

                            @Override
                            public void onClick(View v) {
                                LogUtils.d("click imageView");
                                Toast.makeText(getActivity(), "信息ID:"+v.getTag(),Toast.LENGTH_SHORT).show();
                            }
                        });
                        homeReviewLineLayout.addView(view);
                    }
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    /**
     * 渲染讲师
     */
    public void renderTeacher(String data){
        try {
            JSONArray array= new JSONArray(data);
            for(int i=0;i<array.length();i++){
                JSONObject jsonObject = (JSONObject) array.get(i);
                //images.add(Config.DOMAIN+jsonObject.getString("bannerUrl"));
            }

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {

                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }







}
