package kr.co.ldcc.assignment.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import kr.co.ldcc.assignment.R;
import kr.co.ldcc.assignment.Vo.VideoVo;

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
        VideoVo videoVo = intent.getParcelableExtra("videoVo");
        title = videoVo.getTitle();
        thumbnail = videoVo.getThumbnail();
        url = videoVo.getUrl();
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

    public void writeBtnListener(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("댓글쓰기");
        builder.setIcon(R.drawable.write);
        // 다이얼로그를 통해 보여줄 뷰를 생성한다.
        LayoutInflater inflater = getLayoutInflater();
        View v1 = inflater.inflate(R.layout.dialog, null);
        builder.setView(v1);

        builder.setPositiveButton("작성하기",null);
        builder.setNegativeButton("취소하기",null);

        builder.show();
    }

    class DialogListener implements DialogInterface.OnClickListener{
        @Override
        public void onClick(DialogInterface dialog, int which) {

        }
    }

}
