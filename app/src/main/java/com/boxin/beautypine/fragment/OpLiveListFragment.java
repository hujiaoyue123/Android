package com.boxin.beautypine.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.boxin.beautypine.R;
import com.boxin.beautypine.adapter.ListViewAdapter;
import com.boxin.beautypine.cache.ACache;
import com.boxin.beautypine.cache.Globals;
import com.boxin.beautypine.model.ProgramLive;
import com.boxin.beautypine.utils.LogUtils;
import com.boxin.beautypine.utils.StringUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 公开课直播列表
 * User: zouyu
 * Date: :2017/10/11
 * Version: 1.0
 */

public class OpLiveListFragment extends BaseFragment {

    List<ProgramLive> lives;
    private LayoutInflater layoutInflater ;
    private ListView listView;
    private ListAdapter listAdapter;

    @Override
    protected void initView() {
        layoutInflater = LayoutInflater.from(mContext);
        listView = (ListView) mView.findViewById(R.id.openClass_live_list);

        /*listView.setOnItemClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                LogUtils.d(v.getTag().toString());
            }
        });*/
    }

    @Override
    public int getLayoutId() {
        return R.layout.openclass_live_list;
    }

    @Override
    protected void getDataFromServer() {
        LogUtils.d("LIVELIST获取数据");
        loadData();
    }

    public void loadData(){
        String liveList = ACache.get(mContext).getAsString(Globals.LIVE_LIST);
        if(StringUtils.isEmpty(liveList)){

        }else{
            renderListView(liveList);
        }
    }

    public void renderListView(String data){
        try {
            lives = new ArrayList<>();
            JSONArray array= new JSONArray(data);
            for(int i=0;i<array.length();i++){
                JSONObject jsonObject = (JSONObject) array.get(i);
                ProgramLive live = new ProgramLive();
                live.setInitImg(jsonObject.getString("initImg"));
                live.setName(jsonObject.getString("name"));
                live.setTeacherName(jsonObject.getString("teacherName"));
                lives.add(live);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                listAdapter = new ListViewAdapter<ProgramLive>(lives) {
                    @Override
                    public View OvrideGetView(int position, View convertView, ViewGroup parent) {
                        View itemView = layoutInflater.inflate(R.layout.openclass_live_list_item, null);
                        TextView textView = (TextView)itemView.findViewById(R.id.openclass_live_list_title);
                        ProgramLive live = this.getList().get(position);
                        textView.setText(live.getName());
                        itemView.setTag(live.getId());
                        return itemView;
                    }
                };

                listView.setAdapter(listAdapter);
            }
        });
    }

}
