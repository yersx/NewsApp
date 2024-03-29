package kz.newsapp;

import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.DataSource;
import android.arch.paging.PageKeyedDataSource;
import kz.newsapp.date.ItemDataSource;
import kz.newsapp.model.Article;

public class ItemDataSourceFactory extends DataSource.Factory {

    private MutableLiveData<PageKeyedDataSource<Integer, Article>> itemLiveDataSource = new MutableLiveData<>();


    @Override
    public DataSource create() {
        ItemDataSource itemDataSource = new ItemDataSource();
        itemLiveDataSource.postValue(itemDataSource);
        return  itemDataSource;
    }

    public MutableLiveData<PageKeyedDataSource<Integer, Article>> getItemLiveDataSource() {
        return itemLiveDataSource;
    }
}
