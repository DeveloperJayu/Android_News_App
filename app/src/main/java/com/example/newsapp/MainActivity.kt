package com.example.newsapp


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException

class MainActivity : AppCompatActivity() {

    lateinit var recyclerDashboard: RecyclerView
    lateinit var layoutManager: RecyclerView.LayoutManager
    lateinit var recyclerAdapter: NewsRecyclerAdapter
    lateinit var progressBar : ProgressBar
    lateinit var progressLayout : RelativeLayout

    val newsList = arrayListOf<News>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerDashboard= findViewById(R.id.recyclerView)
        layoutManager = LinearLayoutManager(this@MainActivity)
        progressBar = findViewById(R.id.progressBar)
        progressLayout = findViewById(R.id.progressLayout)

        progressLayout.visibility = View.VISIBLE

        val queue = Volley.newRequestQueue(this@MainActivity)
        val api = "390f0f7032d04bb0bb276d49f2fa425e"
        val url = "https://newsapi.org/v2/top-headlines?country=in&apiKey=$api"

        val jsonObjectRequest = object : JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            Response.Listener{
                try{
                    progressLayout.visibility = View.GONE
                    val data = it.getJSONArray("articles")
                    for(i in 0 until data.length()){
                        val newsJsonObject = data.getJSONObject(i)
                        val source = newsJsonObject.getJSONObject("source")
                        val newsObject = News(
                            newsJsonObject.getString("title"),
                            newsJsonObject.getString("content"),
                            newsJsonObject.getString("author"),
                            newsJsonObject.getString("urlToImage"),
                            newsJsonObject.getString("url"),
                            source.getString("name")
                        )
                        newsList.add(newsObject)

                        recyclerAdapter = NewsRecyclerAdapter(this@MainActivity,newsList)
                        recyclerDashboard.adapter = recyclerAdapter
                        recyclerDashboard.layoutManager = layoutManager
                    }

                } catch (e : JSONException){
                    Toast.makeText(this@MainActivity,"Some unexpected error occurred",Toast.LENGTH_LONG).show()
                    print("Error is $e")
                }

            },
            Response.ErrorListener {
                Toast.makeText(this@MainActivity,"Volley Error Ocurred is $it",Toast.LENGTH_LONG).show()
                print("Volley Error is $it")
            }
        ){
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["Content-type"] = "application/json"
                headers["token"] = "cea7543a68799a"
                return headers
            }
        }
        queue.add(jsonObjectRequest)
    }
}