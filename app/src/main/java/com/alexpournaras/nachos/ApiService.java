package com.alexpournaras.nachos;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("movie/upcoming")
    Call<MovieApiResponse> getPopularMovies(@Query("api_key") String apiKey,
                                            @Query("language") String language,
                                            @Query("page") int page);
}
