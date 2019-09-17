package kz.newsapp

import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.arch.paging.PagedList
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import kz.newsapp.api.NewsApi
import kz.newsapp.api.NewsService
import kz.newsapp.model.Article
import kz.newsapp.model.ItemViewModel
import kz.newsapp.model.News
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

class MainActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener {

    private var articles: List<Article> = ArrayList()
    private lateinit var recyclerView: RecyclerView
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var itemViewModel: ItemViewModel


    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        swipeRefresh = findViewById(R.id.refreshLayout)
        swipeRefresh.setOnRefreshListener(this)
        swipeRefresh.setColorSchemeColors(R.color.colorAccent)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

         itemViewModel= ViewModelProviders.of(this).get(ItemViewModel::class.java)
        getApi()


    }

    private fun getApi(){

        var adapter = ItemAdapter(this)

        swipeRefresh.isRefreshing = true

        itemViewModel.itemPagedList.observe(this, object : Observer<PagedList<Article>>{
            override fun onChanged(t: PagedList<Article>?) {
                adapter.submitList(t)
            }

        })
        recyclerView.adapter = adapter
        swipeRefresh.isRefreshing = false

    }

    override fun onRefresh() {
        getApi()
    }




    private fun performPagination(){

    }
}
