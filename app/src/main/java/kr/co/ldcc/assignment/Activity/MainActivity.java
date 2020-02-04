package kr.co.ldcc.assignment.Activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import kr.co.ldcc.assignment.Adapter.AllDataAdapter;
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
    private ArrayList<VideoVo> videoVos;
    private ArrayList<ImageVo> imageVos;
    private ArrayList<Object> allDataVos;
    private AllDataAdapter allDataAdapter;
    private ImageAdapter imageAdapter;
    private VideoAdapter videoAdapter;

    public ArrayList<VideoVo> getVideoVos() {
        return videoVos;
    }

    public ArrayList<ImageVo> getImageVos() {
        return imageVos;
    }

    public ArrayList<Object> getAllDataVos() {
        return allDataVos;
    }

    String userId;
    String profile;

    public String getUser() {
        return userId;
    }

    public String getProfile() {
        return profile;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("컨텐츠 목록");

        // UserInfo
        Intent intent = getIntent();
        userId = intent.getStringExtra("userId");
        profile = intent.getStringExtra("profile");

        //Initializing the TabLayout;
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setVisibility(View.INVISIBLE);
        tabLayout.addTab(tabLayout.newTab().setText("전체보기"));
        tabLayout.addTab(tabLayout.newTab().setText("북마크"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        //Initializing ViewPager
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        //Initializing EditText
        searchText = (EditText) findViewById(R.id.textViewSearch);

        getAppKeyHash();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.logoutBtn:
                Toast.makeText(getApplicationContext(), "정상적으로 로그아웃되었습니다.", Toast.LENGTH_SHORT).show();

                UserManagement.getInstance().requestLogout(new LogoutResponseCallback() {
                    @Override
                    public void onCompleteLogout() {
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                });
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void getAppKeyHash() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md;
                md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String something = new String(Base64.encode(md.digest(), 0));
                Log.e("Hash key", something);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            Log.e("name not found", e.toString());
        }
    }


    public void searchBtnClickListener(View view) {

        VideoSearchAPI videoSearch = new VideoSearchAPI(searchText.getText().toString());
        ImageSearchAPI imageSearch = new ImageSearchAPI(searchText.getText().toString());

        allDataVos = new ArrayList<Object>();

        videoSearch.start();
        imageSearch.start();

        // Thread Control
        try {
            videoSearch.join();
            imageSearch.join();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Collections.sort(allDataVos, new Comparator<Object>() {
            @Override
            public int compare(Object o1, Object o2) {
                String datetime_o1 = null;
                String datetime_o2 = null;
                if (o1.getClass() == ImageVo.class) {
                    datetime_o1 = ((ImageVo) o1).getDatetime();
                } else if (o1.getClass() == VideoVo.class) {
                    datetime_o1 = ((VideoVo) o1).getDatetime();
                }
                if (o2.getClass() == ImageVo.class) {
                    datetime_o2 = ((ImageVo) o2).getDatetime();
                } else if (o2.getClass() == VideoVo.class) {
                    datetime_o2 = ((VideoVo) o2).getDatetime();
                }
                return -1 * (datetime_o1.compareTo(datetime_o2));
            }
        });

        for (Object obj : allDataVos) {
            if (obj.getClass() == VideoVo.class) {
                Log.d("test", ((VideoVo) obj).getDatetime() + "");
            } else if (obj.getClass() == ImageVo.class) {
                Log.d("test", ((ImageVo) obj).getDatetime() + "");
            }
        }
        allDataAdapter = new AllDataAdapter(allDataVos, userId, profile);
        allDataAdapter.notifyDataSetChanged();


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
        tabLayout.setVisibility(View.VISIBLE);
    }

    public class ImageSearchAPI extends Thread {
        String keyword;

        public ImageSearchAPI(String keyword) {
            this.keyword = keyword;
            imageVos = new ArrayList<>();
        }

        @Override
        public void run() {
            Gson gson = new Gson();
            try {
                String address = "https://dapi.kakao.com/v2/search/image?query=" + keyword;

                URL url = new URL(address);
                // 접속
                URLConnection conn = url.openConnection();
                // 요청헤더 추가
                conn.setRequestProperty("Authorization", "KakaoAK f73ede515a6f7edcb9697b7af164db1d");

                // 서버와 연결되어 있는 스트림을 추출한다.
                InputStream is = conn.getInputStream();
                InputStreamReader isr = new InputStreamReader(is, "UTF-8");
                BufferedReader br = new BufferedReader(isr);

                String str = null;
                StringBuffer buf = new StringBuffer();

                // 읽어온다.
                do {
                    str = br.readLine();
                    if (str != null) {
                        buf.append(str);
                    }
                } while (str != null);

                final String result = buf.toString();

                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("documents");

                int index = 0;

                while (index < jsonArray.length()) {
                    ImageVo imageData = gson.fromJson(jsonArray.get(index).toString(), ImageVo.class);
                    imageVos.add(imageData);
                    allDataVos.add(imageData);
                    index++;
                }
                Collections.sort(imageVos, new Comparator<ImageVo>() {
                    @Override
                    public int compare(ImageVo o1, ImageVo o2) {
                        return -1 * o1.getDatetime().compareTo(o2.getDatetime());
                    }
                });
                imageAdapter = new ImageAdapter(imageVos, userId, profile);

                // UI를 제어하기 위해서 사용
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        imageAdapter.notifyDataSetChanged();
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public class VideoSearchAPI extends Thread {
        String keyword;

        public VideoSearchAPI(String keyword) {
            this.keyword = keyword;
            videoVos = new ArrayList<>();
        }

        @Override
        public void run() {

            Gson gson = new Gson();
            try {
                String address = "https://dapi.kakao.com/v2/search/vclip?query=" + keyword;

                URL url = new URL(address);
                // 접속
                URLConnection conn = url.openConnection();
                // 요청헤더 추가
                conn.setRequestProperty("Authorization", "KakaoAK f73ede515a6f7edcb9697b7af164db1d");

                // 서버와 연결되어 있는 스트림을 추출한다.
                InputStream is = conn.getInputStream();
                InputStreamReader isr = new InputStreamReader(is, "UTF-8");
                BufferedReader br = new BufferedReader(isr);

                String str = null;
                StringBuffer buf = new StringBuffer();

                // 읽어온다.
                do {
                    str = br.readLine();
                    if (str != null) {
                        buf.append(str);
                    }
                } while (str != null);

                final String result = buf.toString();

                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("documents");

                int index = 0;

                while (index < jsonArray.length()) {
                    VideoVo videoData = gson.fromJson(jsonArray.get(index).toString(), VideoVo.class);
                    videoVos.add(videoData);
                    allDataVos.add(videoData);
                    index++;
                }

                Collections.sort(videoVos, new Comparator<VideoVo>() {
                    @Override
                    public int compare(VideoVo o1, VideoVo o2) {
                        return -1 * o1.getDatetime().compareTo(o2.getDatetime());
                    }
                });
                videoAdapter = new VideoAdapter(videoVos, userId, profile);

                // UI를 제어하기 위해서 사용
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        videoAdapter.notifyDataSetChanged();
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}