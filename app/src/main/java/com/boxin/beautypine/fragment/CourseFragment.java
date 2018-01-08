package com.boxin.beautypine.fragment;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.boxin.beautypine.R;
import com.boxin.beautypine.activity.CourseDetailActivity;
import com.boxin.beautypine.adapter.GridViewAdapter;
import com.boxin.beautypine.model.Course;
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
 * 课程内容
 * User: zouyu
 * Date: :2017/9/28
 * Version: 1.0
 */

public class CourseFragment extends BaseFragment {

    private ListView listView;
    private LayoutInflater layoutInflater;
    private GridViewAdapter<Course> adapter;
    private List<Course> dataList;

    @Override
    protected void initView() {
       // Toast.makeText(mContext, "CourseFragment页面请求数据了", Toast.LENGTH_SHORT).show();
        layoutInflater = LayoutInflater.from(mContext);
        listView = (ListView) mView.findViewById(R.id.course_list);

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_course;
    }

    @Override
    protected void getDataFromServer() {
        LogUtils.d("Course 获取数据");
        getCourseData();
    }

    public void getCourseData(){
        OkHttpUtils
            .get()
            .url(Config.GET_COURSE)
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
                                renderCourse(jsonObj.getString("data"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
    }

    public void renderCourse(String data){
        LogUtils.d(data);
        try {
            JSONArray array= new JSONArray(data);
            dataList = new ArrayList<>();
            for(int i=0;i<array.length();i++){
                JSONObject jsonObject = (JSONObject) array.get(i);
                Course course = new Course();
                course.setImageUrl(jsonObject.getString("image_url"));
                course.setTitle(jsonObject.getString("title"));
                course.setId(jsonObject.getInt("id"));
                course.setIntro(jsonObject.getString("intro"));
                dataList.add(course);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter = new GridViewAdapter<Course>(dataList) {

                    @Override
                    public View OvrideGetView(int position, View convertView, ViewGroup parent) {
                        View itemView = layoutInflater.inflate(R.layout.course_list, null);
                        ImageView imageView = (ImageView)itemView.findViewById(R.id.course_image);
                        ImageView teacherView = (ImageView)itemView.findViewById(R.id.course_teacher_image);
                        TextView title = (TextView) itemView.findViewById(R.id.course_title);
                        TextView teacherName = (TextView) itemView.findViewById(R.id.course_teacher_name);
                        Course course = dataList.get(position);
                        imageView.setTag(course.getId());
                        title.setText(course.getTitle());
                        if(course.getTitle().contains("龙回头")|| course.getTitle().contains("短线")||course.getTitle().contains("妖")){
                            teacherName.setText("CCTV证券资讯频道资深讲师/乔立方");
                            //teacherView
                            Picasso.with(mView.getContext()).load(Uri.parse(Config.DOMAIN+course.getImageUrl())).into(teacherView);
                        }else if(course.getTitle().contains("股道")){
                            teacherName.setText("CCTV证券资讯频道资深讲师/范建强");
                            Picasso.with(mView.getContext()).load(Uri.parse(Config.DOMAIN+course.getImageUrl())).into(teacherView);
                        }else{
                            teacherName.setText("CCTV证券资讯频道资深讲师/乔立方、范建强");
                            Picasso.with(mView.getContext()).load(Uri.parse(Config.DOMAIN+course.getImageUrl())).into(teacherView);
                        }

                        Picasso.with(mView.getContext()).load(Uri.parse(Config.DOMAIN+course.getImageUrl())).into(imageView);

                        imageView.setOnClickListener(new View.OnClickListener(){

                            @Override
                            public void onClick(View v) {
                                LogUtils.d("click imageView");
                                Intent intent = new Intent(getActivity(),CourseDetailActivity.class);
                                intent.putExtra("id", v.getTag().toString());
                                startActivity(intent);
                            }
                        });
                        return itemView;
                    }
                };
                listView.setAdapter(adapter);
            }
        });

    }
}
