package com.example.newsapp

data class News(
    val title: String,
    val content: String,
    val author: String,
    val image: String,
    val url: String,
    val source: String
) {
}