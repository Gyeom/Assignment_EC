package kr.co.ldcc.assignment.api;

import kr.co.ldcc.assignment.vo.ImageResponse;
import kr.co.ldcc.assignment.vo.VideoResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface RetrofitService {
    @GET("/v2/search/vclip")
    Call<VideoResponse>
    getVideo(@Header("Authorization") String authorization,
            @Query("query") String query);
    @GET("/v2/search/image")
    Call<ImageResponse>
    getImage(@Header("Authorization") String authorization,
             @Query("query") String query);
}
