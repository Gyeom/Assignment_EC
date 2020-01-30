package kr.co.ldcc.assignment.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Delete;

import com.google.android.youtube.player.YouTubePlayer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import kr.co.ldcc.assignment.Adapter.ReplyAdapter;
import kr.co.ldcc.assignment.DB.AppDatabase;
import kr.co.ldcc.assignment.Dao.ReplyDao;
import kr.co.ldcc.assignment.R;
import kr.co.ldcc.assignment.Vo.ReplyVo;
import kr.co.ldcc.assignment.Vo.VideoVo;


public class VideoActivity extends AppCompatActivity {
//    YouTubePlayerView youtubeView;
    String title;
    String thumbnail;
    String url;
    String id;

    //userInfo
    String user;
    String profile;
    AppDatabase db=null;
    YouTubePlayer.OnInitializedListener listener;

    //recyclerView
    ArrayList<ReplyVo> replyList;
    ReplyAdapter replyAdapter;
    RecyclerView rv_reply;
    LinearLayoutManager linearLayoutManager;

    //example
    TextView tvExample;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        //example
        tvExample = (TextView)findViewById(R.id.tv_example);


        Intent intent = getIntent();
        VideoVo videoVo = intent.getParcelableExtra("videoVo");

        user = intent.getStringExtra("user");
        profile = intent.getStringExtra("profile");

        Log.d("test","videoVo의 Hashcode : "+videoVo.toString().hashCode());
        title = videoVo.getTitle();
        thumbnail = videoVo.getThumbnail();
        url = videoVo.getUrl();
        Log.d("test",url.substring(url.lastIndexOf("v=")));
        id = url.substring(url.lastIndexOf("v=")+2);

//        youtubeView = (YouTubePlayerView) findViewById(R.id.youtubeView);
//        listener = new YouTubePlayer.OnInitializedListener(){
//
//            @Override
//
//            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
//                // 비디오 아이디
//                youTubePlayer.loadVideo(id);
//
//            }
//            @Override
//
//            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
//            }
//
//        };
//
//        youtubeView.initialize(id, listener);


        //relply ArrayList 초기화
        replyList = new ArrayList<ReplyVo>();
        //RecyclerView 관련
        rv_reply = (RecyclerView)findViewById(R.id.rv_reply);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        rv_reply.setLayoutManager(linearLayoutManager);

        //디비생성
        db = AppDatabase.getInstance(this);

        //UI 갱신 (라이브데이터 Observer 이용, 해당 디비값이 변화가생기면 실행됨)
        db.replyDao().getAll().observe(this, new Observer<List<ReplyVo>>() {
            @Override
            public void onChanged(List<ReplyVo> vos) {
                if(vos==null){
                    tvExample.setText("");
                }

                tvExample.setText(vos.toString());
                replyList = new ArrayList<>(vos);
                Log.d("test",replyList.toString()+"1");
                if(replyAdapter==null){
                    replyAdapter = new ReplyAdapter(replyList, user, profile);
                    rv_reply.setAdapter(replyAdapter);
                }else{
                    replyAdapter.setReplyList(replyList);
                }
            }
        });


    }

    public void writeBtnListener(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("댓글쓰기");
        builder.setIcon(R.drawable.write);
        // 다이얼로그를 통해 보여줄 뷰를 생성한다.
        LayoutInflater inflater = getLayoutInflater();
        View v1 = inflater.inflate(R.layout.dialog, null);
        editText = (EditText)v1.findViewById(R.id.editText);
        builder.setView(v1);

        builder.setPositiveButton("작성하기", writeButtonClickListener);
        builder.setNegativeButton("취소하기",null);

        builder.show();
    }
    public void deleteBtnListener(View view){
        new DeleteAllAsyncTask(db.replyDao()).execute();
    }

    public DialogInterface.OnClickListener writeButtonClickListener = new DialogInterface.OnClickListener()     {

        @Override
        public void onClick(DialogInterface dialog, int which) {
            //---------------------------------------------------------
            // Response user selection.
            new InsertAsyncTask(db.replyDao()).execute(new ReplyVo(user,editText.getText().toString(),1));
        }
    };



    //메인스레드에서 데이터베이스에 접근할 수 없으므로 AsyncTask를 사용하도록 한다.
    public static class DeleteAllAsyncTask extends AsyncTask<Void, Void, Void> {
        private ReplyDao replyDao;

        public DeleteAllAsyncTask(ReplyDao replyDao){
            this.replyDao = replyDao;
        }

        @Override // 백그라운드작업(메인스레드 X)
        protected Void doInBackground(Void... voids) {
            replyDao.deleteAll();
            return null;
        }
    }

    public static class InsertAsyncTask extends AsyncTask<ReplyVo, Void, Void> {
        private ReplyDao replyDao;

        public  InsertAsyncTask(ReplyDao replyDao){
            this.replyDao = replyDao;
        }

        @Override //백그라운드작업(메인스레드 X)
        protected Void doInBackground(ReplyVo... replyVos) {
            //추가만하고 따로 SELECT문을 안해도 라이브데이터로 인해
            //getAll()이 반응해서 데이터를 갱신해서 보여줄 것이다,  메인액티비티에 옵저버에 쓴 코드가 실행된다. (라이브데이터는 스스로 백그라운드로 처리해준다.)
            replyDao.insert(replyVos[0]);
            return null;
        }
    }
}
