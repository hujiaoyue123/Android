package com.boxin.beautypine.fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.boxin.beautypine.R;
import com.boxin.beautypine.adapter.CardArrayAdapter;
import com.boxin.beautypine.model.News;
import com.boxin.beautypine.utils.Config;
import com.boxin.beautypine.utils.LogUtils;
import com.boxin.beautypine.utils.StringUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * 资讯列表页
 * User: zouyu
 * Date: :2017/10/17 0017
 * Version: 1.0
 */

public class InfoListFragment extends BaseFragment  {

    private String tag;
    private List<News> newsList;
    private LayoutInflater layoutInflater;
    private CardArrayAdapter cardArrayAdapter;
    private ListView listView;

    public InfoListFragment(String tag){
        this.tag = tag;
    }

    @Override
    protected void initView() {
        layoutInflater = LayoutInflater.from(mContext);
        listView = (ListView)mView.findViewById(R.id.info_list);

        listView.addHeaderView(new View(mContext));
        listView.addFooterView(new View(mContext));
    }

    @Override
    public int getLayoutId() {
        return R.layout.info_list;
    }

    @Override
    protected void getDataFromServer() {
        getNewsData();
    }

    public void getNewsData(){
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
                                renderNews(listData);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
    }

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
                news.setContent(jsonObject.getString("content"));
                newsList.add(news);
            }

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    cardArrayAdapter = new CardArrayAdapter<News>(getContext(), R.layout.info_list_item) {

                        class CardViewHolder {
                            TextView title;
                            TextView summary;
                            TextView time;
                        }

                        @Override
                        public View OvrideGetView(int position, View convertView, ViewGroup parent) {
                            View row = convertView;
                            CardViewHolder viewHolder;
                            if (row == null) {
                                LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                row = inflater.inflate(R.layout.info_list_item, parent, false);
                                viewHolder = new CardViewHolder();
                                viewHolder.title = (TextView) row.findViewById(R.id.info_list_title);
                                viewHolder.summary = (TextView) row.findViewById(R.id.info_list_summary);
                                viewHolder.time = (TextView) row.findViewById(R.id.info_list_time);
                                row.setTag(viewHolder);
                            } else {
                                viewHolder = (CardViewHolder)row.getTag();
                            }
                            News news = getItem(position);
                            viewHolder.title.setText(news.getTitle());
                            LogUtils.d(news.getContent());
                            viewHolder.summary.setText(news.getContent());
                            viewHolder.time.setText(news.getCreatetime());
                            return row;
                        }
                    };

                    for (int i = 0; i < newsList.size(); i++) {
                        News news = newsList.get(i);
                        cardArrayAdapter.add(news);
                    }
                    listView.setAdapter(cardArrayAdapter);
                }
            });


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
