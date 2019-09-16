package kz.newsapp.api;

import kz.newsapp.model.News;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsApi {

    @GET("top-headlines")
    Call<News> getNews(@Query("country") String country,
                       @Query("pageSize") int pageSize,
                       @Query("page") int page,
                       @Query("apiKey") String apiKey);
}
