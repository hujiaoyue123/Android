package com.boxin.beautypine;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.boxin.beautypine.activity.LoginActivity;
import com.boxin.beautypine.fragment.CourseFragment;
import com.boxin.beautypine.fragment.HomeFragment;
import com.boxin.beautypine.fragment.InformationFragment;
import com.boxin.beautypine.fragment.OpenClassFragment;
import com.boxin.beautypine.utils.LogUtils;
import com.boxin.beautypine.view.BottomNavigationViewEx;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private int lastShowFragment = 0;
    private HomeFragment homeFragment;
    private OpenClassFragment openClassFragment;
    private CourseFragment courseFragment;
    private InformationFragment informationFragment;
    private Fragment[] fragments;
    //左侧菜单
    private View headerView;
    private TextView login;
    private TextView account;
    private ImageView userHeader;
    private ImageView levelIcon;

    //底部菜单
    private BottomNavigationViewEx.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationViewEx.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            LogUtils.d("点击底部菜单切换fragment:"+item.getItemId());
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    if (lastShowFragment != 0) {
                        switchFrament(lastShowFragment, 0,String.valueOf(R.id.navigation_home));
                        lastShowFragment = 0;
                    }
                    return true;
                case R.id.navigation_openClass:
                    if (lastShowFragment != 1) {
                        switchFrament(lastShowFragment, 1,String.valueOf(R.id.navigation_openClass));
                        lastShowFragment = 1;
                    }
                    return true;
                case R.id.navigation_course:
                    if (lastShowFragment != 2) {
                        switchFrament(lastShowFragment, 2,String.valueOf(R.id.navigation_course));
                        lastShowFragment = 2;
                    }
                    return true;
                case R.id.navigation_information:
                    if (lastShowFragment != 3) {
                        switchFrament(lastShowFragment, 3,String.valueOf(R.id.navigation_information));
                        lastShowFragment = 3;
                    }
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //加载顶部菜单栏
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //顶部菜单及监听事件
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        //左侧用户中心监听事件
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        headerView = navigationView.getHeaderView(0);

        login = (TextView) headerView.findViewById(R.id.app_login_txt);
        account = (TextView) headerView.findViewById(R.id.app_user_account);
        userHeader = (ImageView) headerView.findViewById(R.id.app_user_header_image);
        levelIcon = (ImageView) headerView.findViewById(R.id.app_user_level_icon);

        //底部菜单监听事件
        BottomNavigationViewEx navigation = (BottomNavigationViewEx) findViewById(R.id.navigation);
        navigation.enableAnimation(false);              //去掉动画效果
        navigation.enableShiftingMode(false);
        navigation.enableItemShiftingMode(false);
        setButtonNavgationColor(navigation);            //设置颜色
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //判断用户是否登录
        if(true){//未登录
            login.setVisibility(View.VISIBLE);
            login.setClickable(true);

            login.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(MainActivity.this, LoginActivity.class);
                    MainActivity.this.startActivity(intent);
                }

            });

        }else{
            login.setVisibility(View.GONE);
        }

        initFragment();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /**
     * 添加顶部右侧菜单选项
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /**
     * 顶部右侧菜单选择事件
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * 左侧用户中心菜单点击事件
     * @param item
     * @return
     */
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * 初始化Fragment
     */
    public void initFragment(){
        homeFragment = new HomeFragment();
        openClassFragment = new OpenClassFragment();
        courseFragment = new CourseFragment();
        informationFragment = new InformationFragment();
        fragments = new Fragment[]{homeFragment, openClassFragment, courseFragment,informationFragment};
        lastShowFragment = 0;
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, homeFragment)
                .show(homeFragment)
                .commit();
    }

    /**
     * 切换Fragment
     * @param lastIndex 上个显示Fragment的索引
     * @param index     需要显示的Fragment的索引
     * @param fragMentId
     */
    public void switchFrament(int lastIndex, int index,String fragMentId) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.hide(fragments[lastIndex]);
        if (!fragments[index].isAdded()) {
            LogUtils.d("change fragment to :"+index);
            transaction.add(R.id.fragment_container, fragments[index],fragMentId);
        }
        transaction.show(fragments[index]).commitAllowingStateLoss();
    }


    /**
     * 设置底部菜单颜色
     * @param navigation
     */
    public void setButtonNavgationColor(BottomNavigationViewEx navigation){
        int[][] states = new int[][]{
                new int[]{-android.R.attr.state_checked},
                new int[]{android.R.attr.state_checked}
        };

        int[] colors = new int[]{getResources().getColor(R.color.home_bottom_normal),
                getResources().getColor(R.color.home_bottom_checked)
        };
        ColorStateList csl = new ColorStateList(states, colors);
        navigation.setItemTextColor(csl);
        navigation.setItemIconTintList(csl);
    }



}
