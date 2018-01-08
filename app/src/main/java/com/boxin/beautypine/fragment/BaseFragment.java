package com.boxin.beautypine.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.boxin.beautypine.utils.LogUtils;
import com.boxin.beautypine.view.RefreshableView;

/**
 * User: zouyu
 * Date: :2017/9/29
 * Version: 1.0
 */

public abstract class BaseFragment extends Fragment{

    protected View mView;
    protected RefreshableView refreshableView;                          //下拉刷新
    protected Boolean isViewInitiated = false;                          //当前页面是否初始化
    protected Boolean isVisibleToUser = false;                          //当前页面是否显示
    protected Boolean isDataRequested = false;                          //是否已经请求了数据
    protected Context mContext;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //防止第二次加载时重新调用onCreateView方法
        if (mView != null) {
            ViewGroup parent = (ViewGroup) mView.getParent();
            if (parent != null) {
                parent.removeView(mView);
            }
            return mView;
        }

        mContext = getContext();
        mView = inflater.inflate(getLayoutId(), null);
        isViewInitiated = true;
        //视图
        initView();
        //数据
        prepareGetData();
        return mView;
    }

    /*@Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        LogUtils.d("onViewCreated");
        if (getUserVisibleHint()) {
            if (isViewInitiated) {
                setUserVisibleHint(true);
            }
        }
        super.onViewCreated(view, savedInstanceState);
    }*/


    /*初始化页面布局和数据*/
    protected abstract void initView();
    /*布局*/
    public abstract int getLayoutId();
    /*服务器获取数据*/
    protected abstract void getDataFromServer();


    /**
     * 当前页面是否展示
     * @param isVisibleToUser 显示为true， 不显示为false
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        LogUtils.d("setUserVisibleHint");
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;
        prepareGetData();
    }

    @Override
    public void onHiddenChanged(boolean hidden){
        super.onHiddenChanged(hidden);
        LogUtils.d(hidden==true?"true":"false");
    }

    /**
     * 如果只想第一次进入该页面请求数据，return prepareGetData(false)
     * 如果想每次进入该页面就请求数据，return prepareGetData(true)
     * @return
     */
    private boolean prepareGetData(){
        return prepareGetData(false);
    }

    /**
     * 判断是否从服务器器获取数据
     * @param isforceUpdate 强制更新的标记
     * @return
     */
    protected boolean prepareGetData(boolean isforceUpdate) {
        //if(isVisibleToUser && isViewInitiated && (!isDataRequested || isforceUpdate)){
        if(isViewInitiated && (!isDataRequested || isforceUpdate)){
            /*从服务器获取数据*/
            getDataFromServer();
            isDataRequested = true;
            return true;
        }
        return false;
    }
}
