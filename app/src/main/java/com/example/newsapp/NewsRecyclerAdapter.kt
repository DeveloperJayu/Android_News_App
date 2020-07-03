package com.example.newsapp

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso


class NewsRecyclerAdapter(val context:Context, val newsList:ArrayList<News>): RecyclerView.Adapter<NewsRecyclerAdapter.NewsViewHolder>() {

    class NewsViewHolder(view: View):RecyclerView.ViewHolder(view){
        val title: TextView = view.findViewById(R.id.txtTitle)
        val content: TextView = view.findViewById(R.id.txtContent)
        val author: TextView = view.findViewById(R.id.txtAuthor)
        val image: ImageView = view.findViewById(R.id.imgImage)
        val url: TextView = view.findViewById(R.id.txtUrl)
        val source: TextView = view.findViewById(R.id.txtSource)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_single_row,parent,false)

        return NewsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val news = newsList[position]
        holder.title.text = news.title
        holder.content.text = news.content
        holder.author.text = news.author
        holder.url.text = news.url
        holder.source.text = news.source
        Picasso.get().load(news.image).error(R.mipmap.ic_launcher).into(holder.image)

        holder.itemView.setOnClickListener{
            val uri: Uri = Uri.parse(news.url) // missing 'http://' will cause crashed
            val intent = Intent(Intent.ACTION_VIEW, uri)
            context.startActivity(intent)
        }
    }

}