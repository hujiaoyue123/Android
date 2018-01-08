package com.boxin.beautypine.fragment;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.boxin.beautypine.R;
import com.boxin.beautypine.adapter.GridViewAdapter;
import com.boxin.beautypine.model.ProgramLive;
import com.boxin.beautypine.utils.Config;
import com.boxin.beautypine.utils.LogUtils;
import com.boxin.beautypine.utils.StringUtils;
import com.squareup.picasso.Picasso;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.Call;

/**
 * 公开课历史
 * User: zouyu
 * Date: :2017/10/11
 * Version: 1.0
 */

public class OpHistoryFragment extends BaseFragment {

    private GridView gview;
    private LayoutInflater layoutInflater;
    private Integer currentWeek = 1;
    private GridViewAdapter<ProgramLive> adapter;
    private List<ProgramLive> dataList;
    private List<ProgramLive> tempList;
    private AutoLinearLayout openClassHistoryListLayout ;


    @Override
    protected void initView() {
        layoutInflater = LayoutInflater.from(mContext);
        gview = (GridView) mView.findViewById(R.id.openclass_history_gridView);
        openClassHistoryListLayout = (AutoLinearLayout)mView.findViewById(R.id.openclass_history_list);

        dataList = new ArrayList();
    }

    @Override
    public int getLayoutId() {
        return R.layout.openclass_history;
    }

    @Override
    protected void getDataFromServer() {
        LogUtils.d("HISTORY获取数据");
        getHistoryListData();
    }

    public void getHistoryListData(){
        //startTime:time[0],endTime:time[1]
        List times = timeInterval(currentWeek++);
        OkHttpUtils
            .get()
            .url(Config.GET_LIVELISTHIS)
            .addParams("startTime",times.get(0).toString())
            .addParams("endTime",times.get(1).toString())
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
                                renderHistory(jsonObj.getString("data"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
    }

    public void renderHistory(String data){
        try {
            JSONArray array= new JSONArray(data);
            tempList = new ArrayList<>();
            for(int i=0;i<array.length();i++){
                JSONObject jsonObject = (JSONObject) array.get(i);
                ProgramLive live = new ProgramLive();
                live.setInitImg(jsonObject.getString("initImg"));
                live.setName(jsonObject.getString("name"));
                live.setTeacherName(jsonObject.getString("teacherName"));
                tempList.add(live);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if(null == adapter) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    adapter = new GridViewAdapter<ProgramLive>(dataList) {

                        @Override
                        public View OvrideGetView(int position, View convertView, ViewGroup parent) {
                            View itemView = layoutInflater.inflate(R.layout.openclass_history_list, null);
                            ImageView imageView = (ImageView)itemView.findViewById(R.id.openclass_history_image);
                            TextView textView = (TextView) itemView.findViewById(R.id.openclass_history_date);
                            ProgramLive live = this.getList().get(position);
                            imageView.setTag(live.getId());
                            textView.setText(live.getName());

                            Picasso.with(mView.getContext()).load(Uri.parse(Config.DOMAIN+live.getInitImg())).into(imageView);
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
                    gview.setAdapter(adapter);
                    for(int n = 0;n<tempList.size();n++){
                        dataList.add(0,tempList.get(n));
                        adapter.notifyDataSetChanged();
                    }
                }
            });
        }else{
            LogUtils.d(data);
            for(int n = 0;n<tempList.size();n++){
                dataList.add(0,tempList.get(n));
                adapter.notifyDataSetChanged();
            }
        }
    }

    public List timeInterval(int week){
        List time = new ArrayList();
        long endTime = (new Date().getTime()/1000);
        Integer perWeekTime = 24 * 60 * 60 * 7 ;
        long startTime = endTime - perWeekTime * week;
        if(week != 1){
            endTime = startTime + perWeekTime;
        }
        time.add(startTime);
        time.add(endTime);
        return time;
    }


}
