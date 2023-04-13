package com.alexpournaras.nachos.services;

import android.content.Context;

import com.alexpournaras.nachos.BuildConfig;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class ApiClient {
    private static Retrofit retrofit = null;

    public static Retrofit getClient(Context context) {
        if (retrofit == null) {
            int cacheSize = BuildConfig.CACHE_SIZE * 1024 * 1024; // MB
            Cache cache = new Cache(context.getCacheDir(), cacheSize);

            // Initialize retrofit to make the http calls for first time
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(logging).cache(cache).build();

            retrofit = new Retrofit.Builder().baseUrl(BuildConfig.TMDB_API_URL).addConverterFactory(GsonConverterFactory.create()).client(client).build();
        }

        return retrofit;
    }

    public interface ApiService {
        @GET("movie/now_playing")
        Call<ApiMovieResponse> getNowPlayingMovies(
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("page") int page,
            @Query("region") String region,
            @Header("Cache-Control") String cache
        );

        @GET("movie/popular")
        Call<ApiMovieResponse> getPopularMovies(
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("page") int page,
            @Query("region") String region,
            @Header("Cache-Control") String cache
        );

        @GET("movie/{movieId}/videos")
        Call<ApiVideoResponse> getMovieVideos(
            @Path("movieId") int movieId,
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Header("Cache-Control") String cache
        );

        @GET("movie/{movieId}/credits")
        Call<ApiCastResponse> getMovieCast(
            @Path("movieId") int movieId,
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Header("Cache-Control") String cache
        );

        @GET("search/movie")
        Call<ApiMovieResponse> searchMovies(
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("query") String query,
            @Query("page") int page,
            @Header("Cache-Control") String cache
        );
    }
}
