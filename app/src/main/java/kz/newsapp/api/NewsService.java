package kz.newsapp.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewsService {

    public static final String ENDPOINT = "https://newsapi.org/v2/";
    public static NewsService mInstance;
    private Retrofit retrofit;

    private  NewsService() {
        retrofit = new Retrofit.Builder()
                .baseUrl(ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static synchronized NewsService getInstance(){
        if(mInstance == null){
            mInstance = new NewsService();

        }
        return mInstance;
    }

    public NewsApi getApi(){
        return retrofit.create(NewsApi.class);
    }
}
