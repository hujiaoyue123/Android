package com.boxin.beautypine.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.boxin.beautypine.R;
import com.boxin.beautypine.model.CourseDetail;
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
 * User: zouyu
 * Date: :2017/10/16 0016
 * Version: 1.0
 */

public class CourseDetailActivity extends AppCompatActivity  {

    private ImageView courseImage;
    private ImageView courseDetailImage;
    private TextView courseTitle;
    private TextView courseTeacher;
    private TextView courseDuration;
    private Button courseButton;
    List<CourseDetail> tempList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_detail);

        setCustomActionBar();

        init();
    }

    public void setCustomActionBar(){
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            LogUtils.d("actionBar");
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("课程详情");
            actionBar.show();
        }
    }

    public void init(){

        Intent intent = getIntent();
        Integer courseId = Integer.valueOf(intent.getStringExtra("id"));

        courseImage = (ImageView) findViewById(R.id.course_image_onDetail);
        courseDetailImage = (ImageView) findViewById(R.id.course_detail_image);

        courseTitle = (TextView) findViewById(R.id.course_title_onDetail);
        courseTeacher = (TextView) findViewById(R.id.course_teacher_onDetail);
        courseDuration = (TextView) findViewById(R.id.course_duration_onDetail);
        courseButton = (Button) findViewById(R.id.course_button);

        loadData(courseId);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //init();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish(); // back button
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 获取数据
     * @param courseId
     */
    public void loadData(Integer courseId){
        LogUtils.d("courseId:"+courseId);

        OkHttpUtils
            .post()
            .url(Config.GET_COURSEDETAIL)
            .addParams("courseID", courseId.toString())
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
                                renderPage(jsonObj.getString("data"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }
            });
    }

    /**
     * 界面渲染
     * @param data
     */
    public void renderPage(String data){
        LogUtils.d(data);

        try {
            data = "["+data+"]";
            JSONArray array= new JSONArray(data);
            tempList = new ArrayList<>();
            for(int i=0;i<array.length();i++){
                JSONObject jsonObject = (JSONObject) array.get(i);
                CourseDetail detail = new CourseDetail();
                detail.setId(jsonObject.getInt("id"));
                detail.setDescUrl(jsonObject.getString("descUrl"));
                detail.setCourseName(jsonObject.getString("courseName"));
                detail.setCourseDuration(jsonObject.getInt("courseDuration"));
                detail.setImageUrl(jsonObject.getString("image_url"));
                detail.setTeacherName(jsonObject.getString("teacherName"));
                detail.setTitle(jsonObject.getString("title"));
                LogUtils.d("add review");
                tempList.add(detail);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        CourseDetailActivity.this.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                CourseDetail detail = tempList.get(0);
                courseTitle.setText("课程名称："+detail.getTitle());
                courseTeacher.setText("讲师："+detail.getTeacherName());
                courseDuration.setText("课时："+detail.getCourseDuration().toString());
                courseButton.setText("观看视频");
                Picasso.with(getApplicationContext()).load(Uri.parse(Config.DOMAIN+detail.getImageUrl())).into(courseImage);
                Picasso.with(getApplicationContext()).load(Uri.parse(Config.DOMAIN+detail.getDescUrl())).into(courseDetailImage);
            }
        });

    }

}
