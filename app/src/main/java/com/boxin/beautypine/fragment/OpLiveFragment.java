package com.boxin.beautypine.fragment;

import android.content.res.Configuration;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.boxin.beautypine.R;
import com.boxin.beautypine.adapter.FragAdapter;
import com.boxin.beautypine.cache.ACache;
import com.boxin.beautypine.cache.Globals;
import com.boxin.beautypine.model.ProgramLive;
import com.boxin.beautypine.utils.Config;
import com.boxin.beautypine.utils.LogUtils;
import com.boxin.beautypine.utils.StringUtils;
import com.boxin.ijkplayer.bean.VideoijkBean;
import com.boxin.ijkplayer.widget.PlayStateParams;
import com.boxin.ijkplayer.widget.PlayerView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

/**
 * 视频直播
 * User: zouyu
 * Date: :2017/10/11
 * Version: 1.0
 */

public class OpLiveFragment extends BaseFragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private List<Fragment> listFragment;
    private FragAdapter adapter;
    private OpLiveListFragment opLiveListFragment;
    private OpLiveInteractionFragment opLiveInteractionFragment;
    private List<ProgramLive> lives;

    private PlayerView player;


    private String titles[] = {"直播列表", "在线互动"};

    @Override
    protected void initView() {

        IjkMediaPlayer.loadLibrariesOnce(null);
        IjkMediaPlayer.native_profileBegin("libijkplayer.so");

        viewPager = (ViewPager)mView.findViewById(R.id.openClass_live_viewPager);
        tabLayout = (TabLayout)mView.findViewById(R.id.openClass_live_tabLayout);

        initPlayer();

        //页面，数据源
        listFragment = new ArrayList<>();
        opLiveListFragment = new OpLiveListFragment();
        opLiveInteractionFragment = new OpLiveInteractionFragment();
        listFragment.add(opLiveListFragment);
        listFragment.add(opLiveInteractionFragment);
        //ViewPager的适配器
        adapter = new FragAdapter(getActivity().getSupportFragmentManager(), listFragment) {
            @Override
            public CharSequence getPageTitle(int position) {
                return titles[position];
            }
        };
        viewPager.setAdapter(adapter);
        //绑定
        tabLayout.setupWithViewPager(viewPager);

    }

    public void initPlayer(){

    }

    @Override
    public int getLayoutId() {
        return R.layout.openclass_live;
    }

    @Override
    protected void getDataFromServer() {
        LogUtils.d("lIVEfRAGE获取数据");
        getLiveListData();
    }

    /**
     * 获取直播列表
     */
    public void getLiveListData(){
        String liveList = ACache.get(getActivity()).getAsString(Globals.LIVE_LIST);
        if(StringUtils.isEmpty(liveList)){
            //获取直播列表
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
                            LogUtils.d("videolist:"+s);
                            if(!StringUtils.isEmpty(s)){
                                try {
                                    JSONObject jsonObj = new JSONObject(s);
                                    Boolean success = jsonObj.getBoolean("success");
                                    if(success){
                                        String data = jsonObj.getString("data");
                                        //缓存数据6个小时
                                        ACache.get(getContext()).put(Globals.LIVE_LIST,data,ACache.TIME_DAY/4);
                                        renderVideo(data);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                        }
                    });
        }else{
            renderVideo(liveList);
        }
    }

    /**
     * 渲染视频窗口
     * @param data
     */
    public void renderVideo(String data){
        lives = new ArrayList<>();
        try {
            JSONArray array= new JSONArray(data);
            for(int i=0;i<array.length();i++){
                JSONObject jsonObject = (JSONObject) array.get(i);
                ProgramLive live = new ProgramLive();
                live.setInitImg(jsonObject.getString("initImg"));
                live.setFlvUrl(jsonObject.getString("flvUrl"));
                live.setName(jsonObject.getString("name"));
                live.setTeacherName(jsonObject.getString("teacherName"));
                live.setPlayBackUrl(jsonObject.getString("playBackUrl"));
                lives.add(live);
            }

            String flvUrl = lives.get(0).getFlvUrl();
            String playBack = lives.get(0).getPlayBackUrl();
            String img = lives.get(0).getInitImg();
            List<VideoijkBean> list = new ArrayList() ;
            VideoijkBean mbean = new VideoijkBean();
            mbean.setStream("高清");
            mbean.setUrl(flvUrl);
            list.add(mbean);

            LogUtils.d("playBack:"+playBack);




            player = new PlayerView(this.getActivity(),mView)
                    .setTitle(lives.get(0).getName())
                    .setScaleType(PlayStateParams.fitparent)
                    .hideMenu(true)
                    .forbidTouch(false)
                    /*.showThumbnail(new OnShowThumbnailListener() {
                        @Override
                        public void onShowThumbnail(ImageView ivThumbnail) {
                            Picasso.with(mContext).load(Uri.parse(Config.DOMAIN+lives.get(0).getInitImg())).into(ivThumbnail);

                        }
                    })*/
                    .setPlaySource(playBack)
                    .startPlay();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



    @Override
    public void onPause() {
        super.onPause();
        if (player != null) {
            player.onPause();
        }
        /**demo的内容，恢复系统其它媒体的状态*/
        //MediaUtils.muteAudioFocus(mContext, true);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (player != null) {
            player.onResume();
        }
        /**demo的内容，暂停系统其它媒体的状态*/
        //MediaUtils.muteAudioFocus(mContext, false);
        /**demo的内容，激活设备常亮状态*/
        //if (wakeLock != null) {
        //    wakeLock.acquire();
        //}
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (player != null) {
            player.onDestroy();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (player != null) {
            player.onConfigurationChanged(newConfig);
        }
    }

    /*@Override
    public void onBackPressed() {
        if (player != null && player.onBackPressed()) {
            return;
        }
        super.onBackPressed();
        *//**demo的内容，恢复设备亮度状态*//*
        //if (wakeLock != null) {
        //    wakeLock.release();
        //}
    }*/

}
