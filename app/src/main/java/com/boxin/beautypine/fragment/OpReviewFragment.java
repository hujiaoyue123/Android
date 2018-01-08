package com.boxin.beautypine.fragment;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.boxin.beautypine.R;
import com.boxin.beautypine.adapter.ListViewAdapter;
import com.boxin.beautypine.model.Review;
import com.boxin.beautypine.utils.Config;
import com.boxin.beautypine.utils.LogUtils;
import com.boxin.beautypine.utils.StringUtils;
import com.squareup.picasso.Picasso;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * 实盘案例
 * User: zouyu
 * Date: :2017/10/11
 * Version: 1.0
 */

public class OpReviewFragment extends BaseFragment {

    private ListView reviewList;
    private LayoutInflater layoutInflater;
    private ListViewAdapter adapter;
    private Integer currentPage = 0;
    private List<Review> dataList;
    private List<Review> tempList;

    @Override
    protected void initView() {
        layoutInflater = LayoutInflater.from(mContext);
        reviewList = (ListView) mView.findViewById(R.id.openClass_review_list);

        dataList = new ArrayList();

        reviewList.setOnScrollListener(new AbsListView.OnScrollListener() {
            private boolean isBottom = false;   //用于标记是否到达顶端
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if(isBottom && scrollState== AbsListView.OnScrollListener.SCROLL_STATE_IDLE){
                    System.out.println("数据加载");
                    requestData();
                    isBottom = false;
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if(firstVisibleItem+visibleItemCount == totalItemCount){
                    isBottom = true;
                }else{
                    isBottom = false;
                }
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.openclass_review;
    }

    @Override
    protected void getDataFromServer() {
        LogUtils.d("review:获取数据");
        requestData();
    }

    public void requestData(){
        currentPage++;
        OkHttpUtils
            .get()
            .url(Config.GET_REVIEWLIST)
            .addParams("index", ""+currentPage)
            .addParams("size", ""+currentPage*5)
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
                                dynamicLoadData(jsonObj.getString("data"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }
            });
    }

    /**
     * 动态加载数据
     * @param data
     */
    public void dynamicLoadData(String data){
        try {
            JSONArray array= new JSONArray(data);
            tempList = new ArrayList<>();
            for(int i=0;i<array.length();i++){
                JSONObject jsonObject = (JSONObject) array.get(i);
                Review review = new Review();
                review.setId(jsonObject.getLong("id"));
                review.setCreateTime(jsonObject.getInt("createTime"));
                review.setReviewLink(jsonObject.getString("reviewLink"));
                review.setReviewUrl(jsonObject.getString("reviewUrl"));
                LogUtils.d("add review");
                tempList.add(review);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if(null == adapter) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    dataList.addAll(tempList);
                    adapter = new ListViewAdapter<Review>(dataList) {

                        @Override
                        public View OvrideGetView(int position, View convertView, ViewGroup parent) {
                            View itemView = layoutInflater.inflate(R.layout.openclass_review_list, null);
                            ImageView imageView = (ImageView)itemView.findViewById(R.id.openClass_review_list_img);
                            Review review = this.getList().get(position);
                            imageView.setTag(review.getId());

                            Picasso.with(mView.getContext()).load(Uri.parse(Config.DOMAIN+review.getReviewUrl())).into(imageView);
                            imageView.setOnClickListener(new View.OnClickListener(){
                                @Override
                                public void onClick(View v) {
                                    LogUtils.d("click imageView");
                                    Toast.makeText(getActivity(), "信息ID:"+v.getTag(),Toast.LENGTH_SHORT).show();
                                }
                            });
                            return itemView;
                        }
                    };

                    reviewList.setAdapter(adapter);
                }
            });
        }else{
            LogUtils.d(data);
            adapter.getList().addAll(tempList);
            adapter.notifyDataSetChanged();
        }

    }

}
