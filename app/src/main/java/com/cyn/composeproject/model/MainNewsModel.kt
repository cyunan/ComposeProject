package com.cyn.composeproject.model

class PageModel<T>(
    val page: Int,
    val page_count: Int,
    val status: Int,
    val total_counts: Int,
    val data: T
)

class NewsModelModel(
    val top_stories: MutableList<TopStoryModel> = mutableListOf(),
    val news: MutableList<StoryModel> = mutableListOf()
)

class TopStoryModel(
    val id: Int = 0,
    val hint: String = "",
    val url: String = "",
    val title: String = "",
    val image: String = ""
)

class StoryModel(
    val id: Int = 0,
    val hint: String = "",
    val url: String = "",
    val title: String = "",
    val image: String = ""
)