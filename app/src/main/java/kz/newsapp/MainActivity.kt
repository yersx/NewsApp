package kz.newsapp

import android.annotation.SuppressLint
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
import kz.newsapp.model.News
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

const val API_TOKEN = "a70582ac9ece41ec8bd0d91afdf8654f"

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

        onLoading()

    }

    private fun getApi(){


        swipeRefresh.isRefreshing = true

        val newsApi = NewsService.getRetrofit().create(NewsApi::class.java)
        val call: Call<News> = newsApi.getNews("us",API_TOKEN)
        call.enqueue(object : Callback<News>{
            override fun onFailure(call: Call<News>, t: Throwable) {
               Toast.makeText(this@MainActivity, "Error reading Json", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<News>, response: Response<News>) {
                if(response.isSuccessful){

                    articles = response.body()!!.articles
                    adapter = Adapter(articles, this@MainActivity,this@MainActivity)
                    recyclerView.adapter = adapter
                    adapter.notifyDataSetChanged()
                    swipeRefresh.isRefreshing = false

                } else {
                    Toast.makeText(this@MainActivity, "No result", Toast.LENGTH_SHORT).show()
                    swipeRefresh.isRefreshing = false
                }
                  }

        })
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
}
