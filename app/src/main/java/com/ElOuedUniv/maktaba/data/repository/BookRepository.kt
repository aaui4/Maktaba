package com.ElOuedUniv.maktaba.data.repository

import android.net.Uri
import com.ElOuedUniv.maktaba.data.model.Book
import kotlinx.coroutines.flow.Flow

interface BookRepository {
    suspend fun addBook(book: Book, imageBytes: ByteArray?)
    fun getAllBooks(): Flow<List<Book>>
    suspend fun getBookByIsbn(isbn: String): Book?
}