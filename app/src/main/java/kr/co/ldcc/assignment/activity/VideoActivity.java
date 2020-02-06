package kr.co.ldcc.assignment.activity;

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

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.util.ArrayList;

import kr.co.ldcc.assignment.adapter.ReplyAdapter;
import kr.co.ldcc.assignment.db.AppDatabase;
import kr.co.ldcc.assignment.dao.BookmarkDao;
import kr.co.ldcc.assignment.dao.ReplyDao;
import kr.co.ldcc.assignment.R;
import kr.co.ldcc.assignment.vo.BookmarkVo;
import kr.co.ldcc.assignment.vo.ReplyVo;
import kr.co.ldcc.assignment.vo.VideoVo;


public class VideoActivity extends YouTubeBaseActivity {
    private YouTubePlayerView youtubeView;
    private String title;
    private String thumbnail;
    private String url;
    private String contentId;
    private String datetime;

    //userInfo
    private String userId;
    private String profile;
    private AppDatabase db = null;
    private YouTubePlayer.OnInitializedListener listener;

    //Reply
    private TextView TextViewReplyCount;

    //recyclerView
    private ArrayList<ReplyVo> replyList;
    private ReplyAdapter replyAdapter;
    private RecyclerView RecyclerViewReply;
    private LinearLayoutManager linearLayoutManager;

    //Bookmark
    private Button buttonBookmark;
    private EditText dialogEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        TextViewReplyCount = (TextView) findViewById(R.id.textViewReplyCount);
        buttonBookmark = (Button) findViewById(R.id.buttonBookmark);
        Intent intent = getIntent();
        VideoVo videoVo = intent.getParcelableExtra("videoVo");

        userId = intent.getStringExtra("userId");
        profile = intent.getStringExtra("profile");

        Log.d("test", "videoVo의 Hashcode : " + videoVo.toString().hashCode());
        title = videoVo.getTitle();
        thumbnail = videoVo.getThumbnail();
        url = videoVo.getUrl();
        datetime = videoVo.getDatetime();
        contentId = url.substring(url.lastIndexOf("v=") + 2);

        youtubeView = (YouTubePlayerView) findViewById(R.id.youtubeView);
        listener = new YouTubePlayer.OnInitializedListener() {

            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                // 비디오 아이디
                youTubePlayer.loadVideo(contentId);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
            }

        };

        youtubeView.initialize(contentId, listener);

        //RecyclerView 관련
        RecyclerViewReply = (RecyclerView) findViewById(R.id.rv_reply);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        RecyclerViewReply.setLayoutManager(linearLayoutManager);

        //디비생성
        db = AppDatabase.getInstance(this);
        new SelectAllReply(db.replyDao()).execute();
        new SelectBookmark(db.bookmarkDao()).execute();


        // liveData 사용법
       /*   replyList = new ArrayList<ReplyVo>(db.replyDao().getAll());
                if(replyAdapter==null){
                    replyAdapter = new ReplyAdapter(replyList, user, profile);
                    recyclerViewReply.setAdapter(replyAdapter);
                }else{
                    replyAdapter.setReplyVos(replyList);
                }

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
                    recyclerViewReply.setAdapter(replyAdapter);
                }else{
                    replyAdapter.setReplyVos(replyList);
                }
            }
        });*/
    }

    public void bookmarkButtonListener(View view) {
        if (buttonBookmark.isSelected() == true) {
            new DeleteBookmark(db.bookmarkDao()).execute();
        } else {
            new InsertBookmark(db.bookmarkDao()).execute();
        }
    }

    public void writeBtnListener(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("댓글쓰기");
        builder.setIcon(R.drawable.write);
        // 다이얼로그를 통해 보여줄 뷰를 생성한다.
        LayoutInflater inflater = getLayoutInflater();
        View v1 = inflater.inflate(R.layout.dialog, null);
        dialogEditText = (EditText) v1.findViewById(R.id.editTextDialog);
        builder.setView(v1);

        builder.setPositiveButton("작성하기", writeButtonClickListener);
        builder.setNegativeButton("취소하기", null);

        builder.show();
    }

    public void deleteBtnListener(View view) {
        AsyncTask asyncTask = new DeleteAllReply(db.replyDao()).execute();

    }

    public DialogInterface.OnClickListener writeButtonClickListener = new DialogInterface.OnClickListener() {

        @Override
        public void onClick(DialogInterface dialog, int which) {
            //---------------------------------------------------------
            // Response user selection.
            AsyncTask asyncTask = new InsertAsyncTask(db.replyDao()).execute(new ReplyVo(userId, dialogEditText.getText().toString(), contentId));
        }
    };


    //메인스레드에서 데이터베이스에 접근할 수 없으므로 AsyncTask를 사용하도록 한다.
    public class SelectAllReply extends AsyncTask<Void, Void, Void> {
        private ReplyDao replyDao;

        public SelectAllReply(ReplyDao replyDao) {
            this.replyDao = replyDao;
        }

        @Override // 백그라운드작업(메인스레드 X)
        protected Void doInBackground(Void... voids) {
            replyList = new ArrayList<ReplyVo>(replyDao.getAll(contentId));
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (replyAdapter == null) {
                replyAdapter = new ReplyAdapter(replyList, userId, profile);
                RecyclerViewReply.setAdapter(replyAdapter);
            } else {
                replyAdapter.setReplyVos(replyList);
                replyAdapter.notifyDataSetChanged();
            }
            TextViewReplyCount.setText("(" + replyAdapter.getItemCount() + ")");
        }
    }

    public class SelectBookmark extends AsyncTask<Void, Void, Boolean> {
        private BookmarkDao bookmarkDao;

        public SelectBookmark(BookmarkDao bookmarkDao) {
            this.bookmarkDao = bookmarkDao;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            boolean result = false;
            if (null == bookmarkDao.getOne(userId, contentId)) {

            } else {
                result = true;
            }
            return result;
        }

        @Override
        protected void onPostExecute(Boolean results) {
            buttonBookmark.setSelected(results);

        }
    }

    public class DeleteBookmark extends AsyncTask<Void, Void, Void> {
        private BookmarkDao bookmarkDao;

        public DeleteBookmark(BookmarkDao bookmarkDao) {
            this.bookmarkDao = bookmarkDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            bookmarkDao.delete(userId, contentId);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            buttonBookmark.setSelected(false);
        }


    }

    public class InsertBookmark extends AsyncTask<Void, Void, Void> {
        private BookmarkDao bookmarkDao;

        public InsertBookmark(BookmarkDao bookmarkDao) {
            this.bookmarkDao = bookmarkDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            bookmarkDao.insert(new BookmarkVo(title, thumbnail, url, datetime, userId, contentId));
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            buttonBookmark.setSelected(true);
        }

    }

    public class DeleteAllReply extends AsyncTask<Void, Void, Void> {
        private ReplyDao replyDao;

        public DeleteAllReply(ReplyDao replyDao) {
            this.replyDao = replyDao;
        }

        @Override // 백그라운드작업(메인스레드 X)
        protected Void doInBackground(Void... voids) {
            replyDao.deleteAll();


            return null;
        }


        // parameter로 결과값 받아오기 Test (list size get하면 되기 때문에 굳이 이렇게안해도 됨)


        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    replyList.clear();
                    TextViewReplyCount.setText("(" + replyList.size() + ")");
                    replyAdapter.notifyDataSetChanged();
                }
            });
        }

    }

    public class InsertAsyncTask extends AsyncTask<ReplyVo, Void, Void> {
        private ReplyDao replyDao;

        public InsertAsyncTask(ReplyDao replyDao) {
            this.replyDao = replyDao;
        }

        @Override //백그라운드작업(메인스레드 X)
        protected Void doInBackground(ReplyVo... replyVos) {
            //추가만하고 따로 SELECT문을 안해도 라이브데이터로 인해
            //getAll()이 반응해서 데이터를 갱신해서 보여줄 것이다,  메인액티비티에 옵저버에 쓴 코드가 실행된다. (라이브데이터는 스스로 백그라운드로 처리해준다.)
            ReplyVo replyVo = replyVos[0];
            replyDao.insert(replyVo);
            replyList.add(replyVo);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            replyAdapter.notifyDataSetChanged();
            TextViewReplyCount.setText("(" + replyList.size() + ")");
        }
    }
}
