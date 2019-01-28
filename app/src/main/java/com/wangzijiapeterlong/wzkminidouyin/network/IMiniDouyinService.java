package com.wangzijiapeterlong.wzkminidouyin.network;


import com.wangzijiapeterlong.wzkminidouyin.bean.PostVideoResponse;
import com.wangzijiapeterlong.wzkminidouyin.model.FeedResponse;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface IMiniDouyinService {
    String HOST = "http://10.108.10.39:8080/";

      @Multipart
    @POST("/minidouyin/video")
      Call<PostVideoResponse> createVideo(
              @Query("student_id") String param1,
              @Query("user_name") String param2,
              @Part MultipartBody.Part file1, @Part MultipartBody.Part file2);


    @GET("/minidouyin/feed")
    Call<FeedResponse> randomFeeds();

    @GET("/minidouyin/feed")
    Call<FeedResponse> fetchFeed();

}
