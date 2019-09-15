package kz.newsapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class DetailActivity : AppCompatActivity() {

    private lateinit var mImageView: ImageView
    private lateinit var mTitle : TextView
    private lateinit var mAuthor : TextView
    private lateinit var mDate : TextView
    private lateinit var mContent : TextView
    private lateinit var mDescription : TextView
    private lateinit var mSource : TextView

    private lateinit var url : String
    private lateinit var title : String
    private lateinit var author : String
    private lateinit var date : String
    private lateinit var content : String
    private lateinit var source : String
    private lateinit var description : String
    private lateinit var image : String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        supportActionBar!!.hide()
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        mTitle = findViewById(R.id.title)
        mAuthor = findViewById(R.id.author)
        mDate = findViewById(R.id.date)
        mContent = findViewById(R.id.content)
        mSource = findViewById(R.id.source)
        mImageView = findViewById(R.id.image)
        mDescription = findViewById(R.id.description)

        val intent = intent
        title = intent.getStringExtra("title")
        image = intent.getStringExtra("image")
        content = intent.getStringExtra("content")
        description = intent.getStringExtra("description")
        date = intent.getStringExtra("date")
        source = intent.getStringExtra("source")
        author = intent.getStringExtra("author")
        url = intent.getStringExtra("url")

        Glide.with(this).load(image).into(mImageView)
        mTitle.setText(title)
        mSource.setText(source)
        mDescription.setText(description)
        mDate.setText(date)

        if(author.isNullOrEmpty()){
            author = "Unknown"
        }
        if(content.isNullOrEmpty()){
            author = "Not exist"
        }
        showWeb(url)
    }

    private fun showWeb(url : String) {
        val webView: WebView = findViewById(R.id.webView)
        webView.settings.loadsImagesAutomatically = true
        webView.settings.javaScriptEnabled = true
        webView.settings.setSupportZoom(true)
        webView.settings.builtInZoomControls = true
        webView.settings.displayZoomControls = true
        webView.webViewClient=  WebViewClient()


        webView.loadUrl(url)
    }
}
