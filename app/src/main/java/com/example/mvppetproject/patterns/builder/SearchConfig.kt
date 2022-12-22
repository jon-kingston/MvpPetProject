package com.example.mvppetproject.patterns.builder

data class SearchConfig(
    val query: String,
    val type: SearchType,
    val rating: Int
)

class SearchConfigBuilder() {
    private var query = ""
    private var type =  SearchType.All
    private var rating = 10

    fun setQuery(value: String): SearchConfigBuilder {
        this.query = value
        return this
    }

    fun setType(value: SearchType): SearchConfigBuilder {
        this.type = value
        return this
    }

    fun setRating(value: Int): SearchConfigBuilder {
        this.rating = value
        return this
    }

    fun build() = SearchConfig(
        query = query,
        type = type,
        rating = rating
    )
}