package com.ElOuedUniv.maktaba.data.repository

import android.content.Context
import com.ElOuedUniv.maktaba.data.model.Book
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.storage.storage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import dagger.hilt.android.qualifiers.ApplicationContext
import android.net.Uri

class SupabaseBookRepositoryImpl @Inject constructor(
    private val supabase: SupabaseClient,
    @ApplicationContext private val context: Context
) : BookRepository {

    override suspend fun addBook(book: Book, imageBytes: ByteArray?) {

        var imageUrl: String? = null

        if (imageBytes != null) {
            val fileName = "book_${System.currentTimeMillis()}.jpg"

            supabase.storage
                .from("book_covers")
                .upload(fileName, imageBytes)

            imageUrl = supabase.storage
                .from("book_covers")
                .publicUrl(fileName)
        }

        val finalBook = book.copy(imageUrl = imageUrl)

        supabase.from("books").insert(finalBook)
    }

    override fun getAllBooks(): Flow<List<Book>> = flow {
        val result = supabase.from("books")
            .select()
            .decodeList<Book>()

        emit(result)
    }

    override suspend fun getBookByIsbn(isbn: String): Book? {
        return try {
            supabase.from("books")
                .select {
                    filter {
                        eq("isbn", isbn)
                    }
                }
                .decodeSingle<Book>()
        } catch (e: Exception) {
            null
        }
    }
}