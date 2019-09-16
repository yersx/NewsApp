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

private const val API_TOKEN = "a70582ac9ece41ec8bd0d91afdf8654f"
val PAGE_SIZE = 10
class MainActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener, Adapter.OnItemClickListener {

    private var articles: List<Article> = ArrayList()
    private lateinit var recyclerView: RecyclerView
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var adapter: Adapter



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

        onLoading()

    }

    private fun getApi(){


        swipeRefresh.isRefreshing = true

        var itemViewModel: ItemViewModel= ViewModelProviders.of(this).get(ItemViewModel::class.java)
        var adapter = ItemAdapter(this)

        itemViewModel.itemPagedList.observe(this, object : Observer<PagedList<Article>>{
            override fun onChanged(t: PagedList<Article>?) {
                adapter.submitList(t)
                adapter
            }

        })

        swipeRefresh.isRefreshing = false
        recyclerView.adapter = adapter
           }

    override fun onRefresh() {
        getApi()
    }


    override fun onItemClick(position: Int) {
        articles.get(position)
        val i = Intent(this@MainActivity, DetailActivity::class.java)
        val article : Article = articles[position]

        if(article.author.isNullOrEmpty()){
            article.author = "Unknown"
        }
        if(article.content.isNullOrEmpty()){
            article.content = "Not exist"
        }
        i.putExtra("url",article.url)
        i.putExtra("title",article.title)
        i.putExtra("date",article.publishedAt)
        i.putExtra("author",article.author)
        i.putExtra("source",article.source.name)
        i.putExtra("description", article.description)
        i.putExtra("content", article.content)
        i.putExtra("image",article.urlToImage)

        startActivity(i)
    }

    private fun onLoading(){
        swipeRefresh.post(
            object : Runnable {
                override fun run() {
                    getApi()
                }

            }
        )
    }

    private fun performPagination(){

    }
}
