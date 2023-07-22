package com.poly.asm.network;

import com.poly.asm.model.ImageData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("get_image")
    Call<ImageData> getImageData(@Query("date") String date);
}
