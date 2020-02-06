package kr.co.ldcc.assignment.activity;

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
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;

import java.io.IOException;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import kr.co.ldcc.assignment.api.NetRetrofit;
import kr.co.ldcc.assignment.adapter.AllDataAdapter;
import kr.co.ldcc.assignment.adapter.ImageAdapter;
import kr.co.ldcc.assignment.vo.ImageResponse;
import kr.co.ldcc.assignment.vo.ImageVo;
import kr.co.ldcc.assignment.R;
import kr.co.ldcc.assignment.adapter.TabPagerAdapter;
import kr.co.ldcc.assignment.adapter.VideoAdapter;
import kr.co.ldcc.assignment.vo.VideoResponse;
import kr.co.ldcc.assignment.vo.VideoVo;
import retrofit2.Call;

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
    private LinearLayout layoutContainer;

    public void setAllDataAdapter(AllDataAdapter allDataAdapter) {
        this.allDataAdapter = allDataAdapter;
    }

    public void setImageAdapter(ImageAdapter imageAdapter) {
        this.imageAdapter = imageAdapter;
    }

    public void setVideoAdapter(VideoAdapter videoAdapter) {
        this.videoAdapter = videoAdapter;
    }

    public ArrayList<VideoVo> getVideoVos() {
        return videoVos;
    }
    public ArrayList<ImageVo> getImageVos() {
        return imageVos;
    }
    public ArrayList<Object> getAllDataVos() {
        return allDataVos;
    }

    private String userId;
    private String profile;

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

        layoutContainer = (LinearLayout) findViewById(R.id.layoutContainer);
        layoutContainer.setVisibility(View.INVISIBLE);

        // UserInfo
        Intent intent = getIntent();
        userId = intent.getStringExtra("userId");
        profile = intent.getStringExtra("profile");

        //Initializing the TabLayout;
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("전체보기"));
        tabLayout.addTab(tabLayout.newTab().setText("북마크"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        //Initializing ViewPager
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        //Initializing EditText
        searchText = (EditText) findViewById(R.id.textViewSearch);

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
        layoutContainer.setVisibility(View.VISIBLE);
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
        while ((videoVos == null) || (imageVos == null)) ;

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
            if (obj instanceof VideoVo) {
                Log.d("test", ((VideoVo) obj).getDatetime() + "video");
            } else if (obj instanceof ImageVo) {
                Log.d("test", ((ImageVo) obj).getDatetime() + "image");
            }
        }
        allDataAdapter.setAllDataVos(allDataVos);
        allDataAdapter.notifyDataSetChanged();

    }

    public class ImageSearchAPI extends Thread {
        String keyword;

        public ImageSearchAPI(String keyword) {
            this.keyword = keyword;
            imageVos = new ArrayList<>();
        }

        @Override
        public void run() {
            Call<ImageResponse> call = NetRetrofit.getInstance().getService().getImage("KakaoAK f73ede515a6f7edcb9697b7af164db1d", keyword);
            try {
                imageVos = call.execute().body().getDocuments();
                allDataVos.addAll(imageVos);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Collections.sort(imageVos, new Comparator<ImageVo>() {
                @Override
                public int compare(ImageVo o1, ImageVo o2) {
                    return -1 * o1.getDatetime().compareTo(o2.getDatetime());
                }
            });
            Log.d("test", imageVos.toString());
            imageAdapter.setImageVos(imageVos);
            // UI를 제어하기 위해서 사용
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    imageAdapter.notifyDataSetChanged();
                }
            });
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

            Call<VideoResponse> call = NetRetrofit.getInstance().getService().getVideo("KakaoAK f73ede515a6f7edcb9697b7af164db1d", keyword);
            try {
                videoVos = call.execute().body().getDocuments();
                allDataVos.addAll(videoVos);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Collections.sort(videoVos, new Comparator<VideoVo>() {
                @Override
                public int compare(VideoVo o1, VideoVo o2) {
                    return -1 * o1.getDatetime().compareTo(o2.getDatetime());
                }
            });
            Log.d("test", videoVos.toString());
            videoAdapter.setVideoVos(videoVos);

            // UI를 제어하기 위해서 사용
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    videoAdapter.notifyDataSetChanged();
                }
            });

// Call 비동기 처리 방법
/*            call.enqueue(new Callback<VideoResponse>() {
                @Override
                public void onResponse(Call<VideoResponse> call, Response<VideoResponse> response) {
                    if (response.isSuccessful()) {
                            if (response.isSuccessful()) {
                                videoVos = response.body().getDocuments();
                                Log.d("test",allDataVos.size()+"zz");
                                allDataVos.addAll(response.body().getDocuments());
                                Log.d("test",allDataVos.size()+"zz");
                                Collections.sort(videoVos, new Comparator<VideoVo>() {
                                    @Override
                                    public int compare(VideoVo o1, VideoVo o2) {
                                        return -1 * o1.getDatetime().compareTo(o2.getDatetime());
                                    }
                                });

                                videoAdapter.setVideoVos(videoVos);

                                // UI를 제어하기 위해서 사용
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        videoAdapter.notifyDataSetChanged();
                                    }
                                });
                            }
                        }
                    }
                @Override
                public void onFailure(Call<VideoResponse> call, Throwable t) {

                }
            });*/

        }

    }
}