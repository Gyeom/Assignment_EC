package kr.co.ldcc.assignment.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import kr.co.ldcc.assignment.Adapter.ImageAdapter;
import kr.co.ldcc.assignment.Vo.ImageVo;
import kr.co.ldcc.assignment.R;
import kr.co.ldcc.assignment.Adapter.TabPagerAdapter;
import kr.co.ldcc.assignment.Adapter.VideoAdapter;
import kr.co.ldcc.assignment.Vo.VideoVo;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private EditText searchText;
    private Button searchBtn;
    private LinearLayout layout_container;
    private ArrayList<VideoVo> videoData_list;
    private ArrayList<ImageVo> imageData_list;
    public static VideoAdapter videoAdapter;
    public static ImageAdapter imageAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Contents List");

        //Initializing the TabLayout;
        tabLayout = (TabLayout)findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("전체보기"));
        tabLayout.addTab(tabLayout.newTab().setText("북마크"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        //Initializing ViewPager
        viewPager = (ViewPager)findViewById(R.id.viewPager);
        //Initializing Button
        searchBtn = (Button) findViewById(R.id.searchBtn);
        //Initializing EditText
        searchText = (EditText) findViewById(R.id.searchText);

        //Creating adapter
        TabPagerAdapter pagerAdapter = new TabPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        //Set TabSelectedListener
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        layout_container = (LinearLayout)findViewById(R.id.layout_container);
        layout_container.setVisibility(View.INVISIBLE);
    }

    public void searchBtnClickListener(View view){
        layout_container.setVisibility(View.VISIBLE);
        VideoSearchAPI videoSearch = new VideoSearchAPI(searchText.getText().toString());
        ImageSearchAPI imageSearch = new ImageSearchAPI(searchText.getText().toString());
        videoSearch.start();
        imageSearch.start();
    }
    public class ImageSearchAPI extends Thread{
        String keyword;
        public ImageSearchAPI(String keyword){
            this.keyword=keyword;
            imageData_list = new ArrayList<>();
        }

        @Override
        public void run() {
            Gson gson = new Gson();
            try{
                String address = "https://dapi.kakao.com/v2/search/image?query="+keyword;

                URL url = new URL(address);
                // 접속
                URLConnection conn = url.openConnection();
                // 요청헤더 추가
                conn.setRequestProperty("Authorization","KakaoAK f73ede515a6f7edcb9697b7af164db1d");

                // 서버와 연결되어 있는 스트림을 추출한다.
                InputStream is = conn.getInputStream();
                InputStreamReader isr = new InputStreamReader(is, "UTF-8");
                BufferedReader br = new BufferedReader(isr);

                String str = null;
                StringBuffer buf = new StringBuffer();

                // 읽어온다.
                do{
                    str = br.readLine();
                    if(str!=null){
                        buf.append(str);
                    }
                }while(str!=null);

                final String result = buf.toString();

                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("documents");

                int index = 0;

                while (index < jsonArray.length()) {
                    ImageVo imageData =  gson.fromJson(jsonArray.get(index).toString(), ImageVo.class);
                    imageData_list.add(imageData);
                    index++;
                }

                if(imageAdapter==null){
                    imageAdapter= new ImageAdapter(imageData_list);
                }else{
                    imageAdapter.setmData(imageData_list);
                }

                // UI를 제어하기 위해서 사용
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        imageAdapter.notifyDataSetChanged();
                    }
                });
            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }
    public class VideoSearchAPI extends Thread{
        String keyword;

        public VideoSearchAPI(String keyword){
            this.keyword=keyword;
            videoData_list = new ArrayList<>();
        }

        @Override
        public void run() {
            Gson gson = new Gson();
            try{
                String address = "https://dapi.kakao.com/v2/search/vclip?query="+keyword;

                URL url = new URL(address);
                // 접속
                URLConnection conn = url.openConnection();
                // 요청헤더 추가
                conn.setRequestProperty("Authorization","KakaoAK f73ede515a6f7edcb9697b7af164db1d");

                // 서버와 연결되어 있는 스트림을 추출한다.
                InputStream is = conn.getInputStream();
                InputStreamReader isr = new InputStreamReader(is, "UTF-8");
                BufferedReader br = new BufferedReader(isr);

                String str = null;
                StringBuffer buf = new StringBuffer();

                // 읽어온다.
                do{
                    str = br.readLine();
                    if(str!=null){
                        buf.append(str);
                    }
                }while(str!=null);

                final String result = buf.toString();

                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("documents");

                int index = 0;

                while (index < jsonArray.length()) {
                    VideoVo videoData =  gson.fromJson(jsonArray.get(index).toString(), VideoVo.class);
                    videoData_list.add(videoData);
                    index++;
                }

                    if(videoAdapter==null){
                        videoAdapter= new VideoAdapter(videoData_list);
                    }else{
                        Collections.sort(videoData_list, new Comparator<VideoVo>() {
                            @Override
                            public int compare(VideoVo b1, VideoVo b2) { return b1.getDatetime().compareTo(b2.getDatetime());
                            } });

                        videoAdapter.setmData(videoData_list);
                    }

                // UI를 제어하기 위해서 사용
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        videoAdapter.notifyDataSetChanged();
                    }
                });
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}