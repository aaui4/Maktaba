package com.ElOuedUniv.maktaba.data.repository

import com.ElOuedUniv.maktaba.data.model.Book
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class BookRepositoryImpl @Inject constructor() : BookRepository {

    private val books = MutableStateFlow(
        listOf(
            Book("9780132350884", "Clean Code", 464, "https://covers.openlibrary.org/b/isbn/9780132350884-L.jpg"),
            Book("9780201616224", "The Pragmatic Programmer", 352, "https://covers.openlibrary.org/b/isbn/9780201616224-L.jpg"),
            Book("9780201633610", "Design Patterns", 395, "https://covers.openlibrary.org/b/isbn/9780201633610-L.jpg")
        )
    )

    override fun getAllBooks(): Flow<List<Book>> = books

    override suspend fun getBookByIsbn(isbn: String): Book? {
        return books.value.find { it.isbn == isbn }
    }

    override suspend fun addBook(book: Book, imageBytes: ByteArray?) {
        books.update { current ->
            current + book.copy(
                imageUrl = "https://covers.openlibrary.org/b/isbn/${book.isbn}-L.jpg"
            )
        }
    }
}