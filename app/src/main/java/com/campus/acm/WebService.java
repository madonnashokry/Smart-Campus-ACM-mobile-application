package com.campus.acm;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface WebService {
  @POST("/login")
    Call<LoginResponse> login(@Body JSONObject requestBody);
}
