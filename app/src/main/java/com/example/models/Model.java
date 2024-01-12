package com.example.models;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Model {

    @GET("locations/v1/cities/autocomplete")
    Call<List<ResponseSearch>> getSearch(
            @Header("Content-Type") String header,
            @Query("q") CharSequence apiKey,
            @Query("apikey") String language,
            @Query("language") String details,
            @Query("get_param") String metric);

}
