package kr.co.ldcc.assignment.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import kr.co.ldcc.assignment.R;

public class VideoActivity extends YouTubeBaseActivity {
    YouTubePlayerView youtubeView;
    String title;
    String thumbnail;
    String url;
    String id;
    YouTubePlayer.OnInitializedListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        thumbnail = intent.getStringExtra("thumbnail");
        url = intent.getStringExtra("url");
        Log.d("test",url.substring(url.lastIndexOf("v=")));
        id = url.substring(url.lastIndexOf("v=")+2);
        youtubeView = (YouTubePlayerView) findViewById(R.id.youtubeView);
        listener = new YouTubePlayer.OnInitializedListener(){

            @Override

            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                // 비디오 아이디
                youTubePlayer.loadVideo(id);

            }
            @Override

            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
            }

        };
                youtubeView.initialize(id, listener);
    }
}
