package com.kolo.prayer.data.repository

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.kolo.prayer.data.model.Book
import com.kolo.prayer.data.model.BookSummary
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BookRepository @Inject constructor(
    private val firestore: FirebaseFirestore,
) {
    private var cachedIndex: List<BookSummary>? = null
    private val cachedBooks = mutableMapOf<String, Book>()

    suspend fun getAllBooks(forceRefresh: Boolean = false): List<BookSummary> {
        if (!forceRefresh && cachedIndex != null) {
            return cachedIndex!!
        }
        
        return try {
            val snapshot = firestore.collection("summaries")
                .orderBy("sort_order")
                .get()
                .await()

            val summaries = snapshot.documents.mapNotNull { doc ->
                // Try decoding the map to BookSummary. Note: doc.toObject() doesn't auto-handle kotlinx.serialization 
                // cleanly out of the box if the model uses @SerialName, but since we are using Kotlinx serialization,
                // we map the fields manually or use a helper, but manual mapping is safest avoiding reflection issues.
                val id = doc.id
                val titleMl = doc.getString("title_ml") ?: ""
                val titleEn = doc.getString("title_en") ?: ""
                val subtitle = doc.getString("subtitle") ?: ""
                val category = doc.getString("category") ?: ""
                val colorHex = doc.getString("color_hex") ?: ""
                val symbol = doc.getString("symbol") ?: ""
                val sectionCount = doc.getLong("section_count")?.toInt() ?: 0
                val isDaily = doc.getBoolean("is_daily") ?: false
                val sortOrder = doc.getLong("sort_order")?.toInt() ?: 0
                val file = doc.getString("file") ?: ""

                BookSummary(
                    id = id,
                    titleMl = titleMl,
                    titleEn = titleEn,
                    subtitle = subtitle,
                    category = category,
                    colorHex = colorHex,
                    symbol = symbol,
                    sectionCount = sectionCount,
                    isDaily = isDaily,
                    sortOrder = sortOrder,
                    file = file
                )
            }
            cachedIndex = summaries
            summaries
        } catch (e: Exception) {
            Log.e("BookRepository", "Error fetching books index", e)
            emptyList()
        }
    }

    suspend fun getBook(bookId: String): Book? {
        cachedBooks[bookId]?.let { return it }

        return try {
            val doc = firestore.collection("books").document(bookId).get().await()
            if (!doc.exists()) return null

            // Map the Firebase map object to our Book model
            val titleMl = doc.getString("title_ml") ?: ""
            val titleEn = doc.getString("title_en") ?: ""
            val descriptionMl = doc.getString("description_ml") ?: ""
            
            // Map sections safely
            val sectionsList = doc.get("sections") as? List<Map<String, Any>> ?: emptyList()
            val sections = sectionsList.map { sectionMap ->
                val sId = sectionMap["id"] as? String ?: ""
                val sSortOrder = (sectionMap["sort_order"] as? Number)?.toInt() ?: 0
                val sTitleMl = sectionMap["title_ml"] as? String ?: ""
                val sTitleEn = sectionMap["title_en"] as? String ?: ""
                val categoryLabel = sectionMap["category_label"] as? String ?: ""
                
                val versesList = sectionMap["verses"] as? List<Map<String, Any>> ?: emptyList()
                val verses = versesList.map { verseMap ->
                    val vNum = (verseMap["verse_num"] as? Number)?.toInt() ?: 0
                    val vTextMl = verseMap["text_ml"] as? String ?: ""
                    val vTextSyriac = verseMap["text_syriac"] as? String ?: ""
                    val vTextEn = verseMap["text_en"] as? String ?: ""
                    val vIsResponse = verseMap["is_response"] as? Boolean ?: false
                    val speaker = verseMap["speaker"] as? String ?: "all"
                    val type = verseMap["type"] as? String ?: "text"
                    
                    com.kolo.prayer.data.model.Verse(
                        verseNum = vNum,
                        textMl = vTextMl,
                        textSyriac = vTextSyriac,
                        textEn = vTextEn,
                        isResponse = vIsResponse,
                        speaker = speaker,
                        type = type,
                    )
                }

                com.kolo.prayer.data.model.Section(
                    id = sId,
                    sortOrder = sSortOrder,
                    titleMl = sTitleMl,
                    titleEn = sTitleEn,
                    categoryLabel = categoryLabel,
                    verses = verses
                )
            }

            val book = Book(
                id = doc.id,
                titleMl = titleMl,
                titleEn = titleEn,
                descriptionMl = descriptionMl,
                sections = sections
            )
            
            cachedBooks[bookId] = book
            book
        } catch (e: Exception) {
            Log.e("BookRepository", "Error fetching book details", e)
            null
        }
    }

    suspend fun getBookSummary(bookId: String): BookSummary? {
        return getAllBooks().find { it.id == bookId }
    }
}
