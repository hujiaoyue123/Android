package com.boxin.beautypine.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.boxin.beautypine.R;
import com.boxin.beautypine.adapter.FragAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 公开课内容
 * User: zouyu
 * Date: :2017/9/28
 * Version: 1.0
 */

public class OpenClassFragment extends BaseFragment{

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private List<Fragment> listFragment;
    private FragAdapter adapter;
    private OpLiveFragment opLiveFragment;
    private OpHistoryFragment opHistoryFragment;
    private OpKungfuFragment opKungfuFragment;
    private OpReviewFragment opReviewFragment;

    private String titles[] = {"视频直播", "公开课历史", "股市真功夫","实盘案例"};

    @Override
    protected void initView() {
        viewPager = (ViewPager)mView.findViewById(R.id.openClass_viewPager);
        tabLayout = (TabLayout)mView.findViewById(R.id.openClass_tabLayout);

        opLiveFragment = new OpLiveFragment();
        opHistoryFragment = new OpHistoryFragment();
        opKungfuFragment = new OpKungfuFragment();
        opReviewFragment = new OpReviewFragment();

        //页面，数据源
        listFragment = new ArrayList<>();
        listFragment.add(opLiveFragment);
        listFragment.add(opHistoryFragment);
        listFragment.add(opKungfuFragment);
        listFragment.add(opReviewFragment);
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

    @Override
    public int getLayoutId() {
        return R.layout.fragment_openclass;
    }

    @Override
    protected void getDataFromServer() {
        //Toast.makeText(mContext, "CourseFragment页面请求数据了", Toast.LENGTH_SHORT).show();
    }

}
