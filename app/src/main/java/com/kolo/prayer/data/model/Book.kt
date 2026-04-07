package com.kolo.prayer.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BooksIndex(
    val books: List<BookSummary>,
)

@Serializable
data class BookSummary(
    val id: String,
    @SerialName("title_ml") val titleMl: String,
    @SerialName("title_en") val titleEn: String,
    val subtitle: String = "",
    val category: String,
    @SerialName("color_hex") val colorHex: String,
    val symbol: String,
    @SerialName("section_count") val sectionCount: Int,
    @SerialName("is_daily") val isDaily: Boolean = false,
    @SerialName("sort_order") val sortOrder: Int,
    val file: String,
)

@Serializable
data class Book(
    val id: String,
    @SerialName("title_ml") val titleMl: String,
    @SerialName("title_en") val titleEn: String = "",
    @SerialName("description_ml") val descriptionMl: String = "",
    val sections: List<Section>,
)

@Serializable
data class Section(
    val id: String,
    @SerialName("sort_order") val sortOrder: Int,
    @SerialName("title_ml") val titleMl: String,
    @SerialName("title_en") val titleEn: String = "",
    @SerialName("category_label") val categoryLabel: String = "",
    val verses: List<Verse>,
)

@Serializable
data class Verse(
    @SerialName("verse_num") val verseNum: Int,
    @SerialName("text_ml") val textMl: String,
    @SerialName("text_syriac") val textSyriac: String = "",
    @SerialName("text_en") val textEn: String = "",
    @SerialName("is_response") val isResponse: Boolean = false,
    val speaker: String = "all",
    val type: String = "text",
)
