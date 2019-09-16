package kz.newsapp.model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PageKeyedDataSource;
import android.arch.paging.PagedList;
import kz.newsapp.ItemDataSourceFactory;
import kz.newsapp.date.ItemDataSource;

public class ItemViewModel extends ViewModel {

     public LiveData<PagedList<Article>> itemPagedList;
    public LiveData<PageKeyedDataSource<Integer,Article>> liveDataSource;

    public ItemViewModel(){

        ItemDataSourceFactory itemDataSourceFactory = new ItemDataSourceFactory();
        liveDataSource = itemDataSourceFactory.getItemLiveDataSource();

        PagedList.Config config =
                (new PagedList.Config.Builder()).setEnablePlaceholders(false)
                .setPageSize(ItemDataSource.PAGE_SIZE).build();

        itemPagedList = (new LivePagedListBuilder(itemDataSourceFactory, config)).build();
    }

}
