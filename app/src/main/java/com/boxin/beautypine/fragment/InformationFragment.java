package com.boxin.beautypine.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.boxin.beautypine.R;
import com.boxin.beautypine.adapter.FragAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 资讯内容
 * User: zouyu
 * Date: :2017/9/28
 * Version: 1.0
 */

public class InformationFragment extends BaseFragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private List<Fragment> listFragment;
    private FragAdapter adapter;
    private String[] titles;

    @Override
    protected void initView() {

        viewPager = (ViewPager)mView.findViewById(R.id.information_viewPager);
        tabLayout = (TabLayout)mView.findViewById(R.id.information_tabLayout);

        titles = new String[]{"金牌操盘", "超级顶底","盘前猛料","涨停揭秘","黄金特刊","绝对热点"}; //免费版
        //titles = new String[]{"政策王牌", "龙头掘金","重磅出击","机构龙虎榜","金股一号"}; //初级版
        //titles = new String[]{"操盘计划","红百万","服务"}; //初级版

        //页面，数据源
        listFragment = new ArrayList<>();
        listFragment.add(new InfoListFragment("JPCP"));  //金牌操盘
        listFragment.add(new InfoListFragment("CJDD"));  //超级顶底
        listFragment.add(new InfoListFragment("PQML"));  //盘前猛料
        listFragment.add(new InfoListFragment("ZTJM"));  //涨停揭秘
        listFragment.add(new InfoListFragment("HJTK"));  //黄金特刊
        listFragment.add(new InfoListFragment("JDRD"));  //绝对热点
        //ViewPager的适配器
        adapter = new FragAdapter(getActivity().getSupportFragmentManager(), listFragment) {
            @Override
            public CharSequence getPageTitle(int position) {
                return titles[position];
            }
        };
        viewPager.setAdapter(adapter);
        //绑定
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setupWithViewPager(viewPager);
    }
    @Override
    public int getLayoutId() {
        return R.layout.fragment_information;
    }
    @Override
    protected void getDataFromServer() {
        //Toast.makeText(mContext, "InformationFragment页面请求数据了", Toast.LENGTH_SHORT).show();
    }

}
