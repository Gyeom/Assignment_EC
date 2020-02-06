package kr.co.ldcc.assignment;

import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import kr.co.ldcc.assignment.vo.Video;
import kr.co.ldcc.assignment.vo.VideoVo;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface RetrofitService {
    @GET("/v2/search/vclip")
    Call<Video>
    getVideo(
            @Header("Authorization") String authorization,
            @Query("query") String query);
}
