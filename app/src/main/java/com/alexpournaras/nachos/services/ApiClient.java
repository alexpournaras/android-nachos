package com.alexpournaras.nachos.services;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class ApiClient {

    private static final String BASE_URL = "https://api.themoviedb.org/3/";
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            // Initialize retrofit to make the http calls for first time
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(logging).build();

            retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).client(client).build();
        }

        return retrofit;
    }

    public interface ApiService {
        @GET("movie/now_playing")
        Call<ApiMovieResponse> getNowPlayingMovies(
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("page") int page,
            @Query("region") String region
        );

        @GET("movie/popular")
        Call<ApiMovieResponse> getPopularMovies(
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("page") int page,
            @Query("region") String region
        );

        @GET("movie/{movieId}/videos")
        Call<ApiVideoResponse> getMovieVideos(
            @Path("movieId") int movieId,
            @Query("api_key") String apiKey,
            @Query("language") String language
        );

        @GET("movie/{movieId}/credits")
        Call<ApiCastResponse> getMovieCast(
                @Path("movieId") int movieId,
                @Query("api_key") String apiKey,
                @Query("language") String language
        );
    }
}
