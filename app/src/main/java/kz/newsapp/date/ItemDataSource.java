package kz.newsapp.date;

import android.arch.paging.PageKeyedDataSource;
import android.support.annotation.NonNull;
import android.util.Log;
import kz.newsapp.api.NewsService;
import kz.newsapp.model.Article;
import kz.newsapp.model.News;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ItemDataSource extends PageKeyedDataSource<Integer, Article> {

    public static final int PAGE_SIZE = 10;
    public static final int FIRST_PAGE = 1;
    private static final String API_TOKEN = "a70582ac9ece41ec8bd0d91afdf8654f";

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull final LoadInitialCallback<Integer, Article> callback) {
        NewsService.getInstance().getApi()
                .getNews("us", PAGE_SIZE, FIRST_PAGE, API_TOKEN)
                .enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                if (response.body() != null) {
                    callback.onResult(response.body().getArticles(), null, FIRST_PAGE + 1);
                }
            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {

            }
        });

    }

    @Override
    public void loadBefore(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, Article> callback) {
        NewsService.getInstance().getApi()
                .getNews("us", PAGE_SIZE, params.key , API_TOKEN)
                .enqueue(new Callback<News>() {
                    @Override
                    public void onResponse(Call<News> call, Response<News> response) {
                        if(response.body() != null){
                            Integer key = (params.key >1) ? params.key - 1 : null;
                            callback.onResult(response.body().getArticles(), key);

                            Log.i("Tag", "go up");
                        }
                    }

                    @Override
                    public void onFailure(Call<News> call, Throwable t) {

                    }
                });

    }

    @Override
    public void loadAfter(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, Article> callback) {
        NewsService.getInstance().getApi()
                .getNews("us", PAGE_SIZE, params.key , API_TOKEN)
                .enqueue(new Callback<News>() {
                    @Override
                    public void onResponse(Call<News> call, Response<News> response) {

                        if(response.body() != null){
                            Integer key = response.body().has_more ? params.key + 1 : null;
                            callback.onResult(response.body().getArticles(), key);
                            Log.i("Tag", "go down");
                        }
                    }

                    @Override
                    public void onFailure(Call<News> call, Throwable t) {

                    }
                });

    }
}
