package com.example.news

import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Request.Method.GET
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import org.json.JSONObject
import java.lang.reflect.Method

class MainActivity : AppCompatActivity(), NewsItemClicked {

    var url = "https://newsapi.org/v2/top-headlines?country=in&country=us&apiKey=c7d3786c30db425f9a382e3a69497cc8"

    private lateinit var mAdapter: NewsListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView.layoutManager = LinearLayoutManager(this)
        fetchData()
        mAdapter = NewsListAdapter(this)
        recyclerView.adapter = mAdapter
    }

    private fun fetchData() {
        progressBar.visibility = View.VISIBLE;

        val jsonObjectRequest = object: JsonObjectRequest(
            GET, url,null,
            {
                val newsJsonArray = it.getJSONArray("articles")
                val newsArray = ArrayList<News>()

                for(i in 0 until newsJsonArray.length()) {
                    val newsJSONObject = newsJsonArray.getJSONObject(i)
                    val sourceObject = newsJSONObject.getJSONObject("source")
                    val author = sourceObject.getString("name")
                    val title = newsJSONObject.getString("title")
                    val url = newsJSONObject.getString("url")
                    val description = newsJSONObject.getString("description")
                    val imageUrl = newsJSONObject.getString("urlToImage")
                    if(newsJSONObject.isNull("urlToImage")) {
                        continue
                    }
                    else
                    {
                        val news = News(
                            title, url, imageUrl, author, description
                        )
                        newsArray.add(news)
                    }
                }
                mAdapter.updateNews(newsArray)
                progressBar.visibility = View.GONE
            },
            {

            })
        {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["User-Agent"] = "Mozilla/5.0"
                return headers
            }
        }
        // Add the request to the RequestQueue.
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }

    override fun onItemClicked(item: News) {
        val builder = CustomTabsIntent.Builder();
        val customTabsIntent = builder.build();
        customTabsIntent.launchUrl(this, Uri.parse(item.url));
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.nav_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.refresh -> {
                Toast.makeText(this,"Refreshing Feed",Toast.LENGTH_SHORT).show()
                fetchData()
            }
            R.id.general -> {
                Toast.makeText(this,"Switching news category to ${item.title}",Toast.LENGTH_SHORT).show()
                url = "https://newsapi.org/v2/top-headlines?country=in&country=us&category=general&apiKey=c7d3786c30db425f9a382e3a69497cc8"
                fetchData()
            }
            R.id.science -> {
                Toast.makeText(this,"Switching news category to ${item.title}",Toast.LENGTH_SHORT).show()
                url = "https://newsapi.org/v2/top-headlines?country=in&country=us&category=science&apiKey=c7d3786c30db425f9a382e3a69497cc8"
                fetchData()
            }
            R.id.business -> {
                Toast.makeText(this,"Switching news category to ${item.title}",Toast.LENGTH_SHORT).show()
                url = "https://newsapi.org/v2/top-headlines?country=in&country=us&category=business&apiKey=c7d3786c30db425f9a382e3a69497cc8"
                fetchData()
            }
            R.id.entertainment -> {
                Toast.makeText(this,"Switching news category to ${item.title}",Toast.LENGTH_SHORT).show()
                url = "https://newsapi.org/v2/top-headlines?country=in&country=us&category=entertainment&apiKey=c7d3786c30db425f9a382e3a69497cc8"
                fetchData()
            }
            R.id.technology -> {
                Toast.makeText(this,"Switching news category to ${item.title}",Toast.LENGTH_SHORT).show()
                url = "https://newsapi.org/v2/top-headlines?country=in&country=us&category=technology&apiKey=c7d3786c30db425f9a382e3a69497cc8"
                fetchData()
            }
            R.id.health -> {
                Toast.makeText(this,"Switching news category to ${item.title}",Toast.LENGTH_SHORT).show()
                url = "https://newsapi.org/v2/top-headlines?country=in&country=us&category=health&apiKey=c7d3786c30db425f9a382e3a69497cc8"
                fetchData()
            }
            R.id.sports -> {
                Toast.makeText(this,"Switching news category to ${item.title}",Toast.LENGTH_SHORT).show()
                url = "https://newsapi.org/v2/top-headlines?country=in&country=us&category=sports&apiKey=c7d3786c30db425f9a382e3a69497cc8"
                fetchData()
            }
        }
        return true
    }

}