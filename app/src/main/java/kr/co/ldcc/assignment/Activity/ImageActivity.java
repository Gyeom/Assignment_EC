package kr.co.ldcc.assignment.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.util.ArrayList;

import kr.co.ldcc.assignment.Adapter.ReplyAdapter;
import kr.co.ldcc.assignment.DB.AppDatabase;
import kr.co.ldcc.assignment.Dao.BmarkDao;
import kr.co.ldcc.assignment.Dao.ReplyDao;
import kr.co.ldcc.assignment.R;
import kr.co.ldcc.assignment.Vo.BmarkVo;
import kr.co.ldcc.assignment.Vo.ReplyVo;

public class ImageActivity extends AppCompatActivity {
    ImageView imageView;
    String thumbnail;
    String contentId;
    String datetime;

    //userInfo
    String userId;
    String profile;
    AppDatabase db=null;
    YouTubePlayer.OnInitializedListener listener;

    //Reply
    TextView tv_replyCount;
    //recyclerView
    ArrayList<ReplyVo> replyList;
    ReplyAdapter replyAdapter;
    RecyclerView rv_reply;
    LinearLayoutManager linearLayoutManager;

    //Bookmark
    Button btn_bookmark;
    //example
    TextView tvExample;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        imageView = (ImageView)findViewById(R.id.imageView);
        tv_replyCount = (TextView)findViewById(R.id.tv_replyCount);
        btn_bookmark = (Button)findViewById(R.id.bmarkBtn);
        Intent intent = getIntent();


        userId = intent.getStringExtra("userId");
        profile = intent.getStringExtra("profile");

        thumbnail = intent.getStringExtra("thumbnail");
        datetime = intent.getStringExtra("datetime");
        Log.d("test",thumbnail.substring(thumbnail.lastIndexOf("/")));
        contentId = thumbnail.substring(thumbnail.lastIndexOf("/")+1);
        imageView = (ImageView) findViewById(R.id.imageView);
        Glide.with(imageView.getContext()).load(thumbnail).into(imageView);
        //relply ArrayList 초기화
//        replyList = new ArrayList<ReplyVo>();

        //RecyclerView 관련
        rv_reply = (RecyclerView)findViewById(R.id.rv_reply);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        rv_reply.setLayoutManager(linearLayoutManager);

        //디비생성
        db = AppDatabase.getInstance(this);
        new ImageActivity.SelectAllReply(db.replyDao()).execute();
        new ImageActivity.SelectBmark(db.bmarkDao()).execute();
//        replyList = new ArrayList<ReplyVo>(db.replyDao().getAll());
//                if(replyAdapter==null){
//                    replyAdapter = new ReplyAdapter(replyList, user, profile);
//                    rv_reply.setAdapter(replyAdapter);
//                }else{
//                    replyAdapter.setReplyList(replyList);
//                }

//        //UI 갱신 (라이브데이터 Observer 이용, 해당 디비값이 변화가생기면 실행됨)
//        db.replyDao().getAll().observe(this, new Observer<List<ReplyVo>>() {
//            @Override
//            public void onChanged(List<ReplyVo> vos) {
//                if(vos==null){
//                    tvExample.setText("");
//                }
//
//                tvExample.setText(vos.toString());
//                replyList = new ArrayList<>(vos);
//                Log.d("test",replyList.toString()+"1");
//                if(replyAdapter==null){
//                    replyAdapter = new ReplyAdapter(replyList, user, profile);
//                    rv_reply.setAdapter(replyAdapter);
//                }else{
//                    replyAdapter.setReplyList(replyList);
//                }
//            }
//        });


    }

    //
    public void bmarkBtnListener(View view){
        if(btn_bookmark.isSelected()==true){
            new ImageActivity.DeleteBmark(db.bmarkDao()).execute();
        }else{
            new ImageActivity.InsertBmark(db.bmarkDao()).execute();
        }
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
        AsyncTask asyncTask = new ImageActivity.DeleteAllAsyncTask(db.replyDao()).execute();

    }

    public DialogInterface.OnClickListener writeButtonClickListener = new DialogInterface.OnClickListener()     {

        @Override
        public void onClick(DialogInterface dialog, int which) {
            //---------------------------------------------------------
            // Response user selection.
            AsyncTask asyncTask= new ImageActivity.InsertAsyncTask(db.replyDao()).execute(new ReplyVo(userId,editText.getText().toString(),contentId));
        }
    };




    //메인스레드에서 데이터베이스에 접근할 수 없으므로 AsyncTask를 사용하도록 한다.
    public class SelectAllReply extends AsyncTask<Void, Void, Void> {
            private ReplyDao replyDao;

        public SelectAllReply(ReplyDao replyDao){
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
            if(replyAdapter==null){
                replyAdapter = new ReplyAdapter(replyList, userId, profile);
                rv_reply.setAdapter(replyAdapter);
            }else{
                replyAdapter.setReplyList(replyList);
            }
            tv_replyCount.setText("("+replyAdapter.getItemCount()+")");
        }
    }

    public class SelectBmark extends  AsyncTask<Void, Void, Boolean> {
        private BmarkDao bmarkDao;

        public SelectBmark(BmarkDao bmarkDao){ this.bmarkDao = bmarkDao; }

        @Override
        protected Boolean doInBackground(Void... voids) {
            boolean result=false;
            if(null== bmarkDao.getOne(userId,contentId)){

            }else{
                result = true;
            }
            return result;
        }

        @Override
        protected void onPostExecute(Boolean results) {
            btn_bookmark.setSelected(results);

        }
    }
    public class DeleteBmark extends  AsyncTask<Void, Void, Void>{
        private  BmarkDao bmarkDao;
        public DeleteBmark(BmarkDao bmarkDao){
            this.bmarkDao = bmarkDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            bmarkDao.delete(userId,contentId);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            btn_bookmark.setSelected(false);
        }


    }
    public class InsertBmark extends  AsyncTask<Void, Void, Void>{
        private BmarkDao bmarkDao;
        public InsertBmark(BmarkDao bmarkDao) {
            this.bmarkDao = bmarkDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            BmarkVo bmarkVo = new BmarkVo("",thumbnail,"",datetime,userId,contentId);
            bmarkDao.insert(bmarkVo);
            Log.d("test",bmarkVo.toString());
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            btn_bookmark.setSelected(true);
        }

    }

    public class DeleteAllAsyncTask extends AsyncTask<Void, Void, Void> {
        private ReplyDao replyDao;

        public DeleteAllAsyncTask(ReplyDao replyDao){
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
                    tv_replyCount.setText("("+replyList.size()+")");
                    replyAdapter.notifyDataSetChanged();
                }
            });
        }

    }

    public class InsertAsyncTask extends AsyncTask<ReplyVo, Void, Void> {
        private ReplyDao replyDao;

        public InsertAsyncTask(ReplyDao replyDao){
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
            tv_replyCount.setText("("+replyList.size()+")");
        }
    }

}
