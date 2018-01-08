package com.boxin.beautypine.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.boxin.beautypine.R;
import com.boxin.ijkplayer.listener.OnPlayerBackListener;
import com.boxin.ijkplayer.widget.PlayStateParams;
import com.boxin.ijkplayer.widget.PlayerView;

/**
 * User: zouyu
 * Date: :2017/10/20 0020
 * Version: 1.0
 */

public class FullVideoActivity extends AppCompatActivity {

    private PlayerView player;
    private Context mContext;
    private View rootView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simple_player_view_player);


        Intent intent = getIntent();
        String url= String.valueOf(intent.getStringExtra("url"));
        String burl= String.valueOf(intent.getStringExtra("burl"));
        String title = String.valueOf(intent.getStringExtra("title"));



        player = new PlayerView(this, rootView)
                .setTitle(title)
                .setScaleType(PlayStateParams.fitparent)
                .forbidTouch(false)
                .hideMenu(true)
                .setOnlyFullScreen(true)
              /*  .showThumbnail(new OnShowThumbnailListener() {
                    @Override
                    public void onShowThumbnail(ImageView ivThumbnail) {
                    }
                })*/
                .setPlaySource(burl)
                .setPlayerBackListener(new OnPlayerBackListener() {
                    @Override
                    public void onPlayerBack() {
                        finish();
                    }
                })
                .startPlay();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (player != null) {
            player.onPause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (player != null) {
            player.onResume();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (player != null) {
            player.onDestroy();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (player != null) {
            player.onConfigurationChanged(newConfig);
        }
    }

    @Override
    public void onBackPressed() {
        if (player != null && player.onBackPressed()) {
            return;
        }
        super.onBackPressed();
    }

}
