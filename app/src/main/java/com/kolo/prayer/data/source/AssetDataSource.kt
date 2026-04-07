package com.kolo.prayer.data.source

import android.content.Context
import com.kolo.prayer.data.model.Book
import com.kolo.prayer.data.model.BooksIndex
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.serialization.json.Json
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AssetDataSource @Inject constructor(
    @ApplicationContext private val context: Context,
) {
    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

    fun loadBooksIndex(): BooksIndex {
        val raw = context.assets.open("books_index.json")
            .bufferedReader()
            .use { it.readText() }
        return json.decodeFromString<BooksIndex>(raw)
    }

    fun loadBook(filename: String): Book {
        val raw = context.assets.open(filename)
            .bufferedReader()
            .use { it.readText() }
        return json.decodeFromString<Book>(raw)
    }
}
