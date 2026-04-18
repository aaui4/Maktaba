package com.ElOuedUniv.maktaba.data.repository

import com.ElOuedUniv.maktaba.data.model.Book
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class BookRepositoryImpl @Inject constructor() : BookRepository {

    private val _booksList = mutableListOf(
        Book(
            isbn = "978-0135398579",
            title = "Clean Code",
            nbPages = 464,
            imageUrl = "https://m.media-amazon.com/images/I/41xShlnTZTL.jpg"
        ),
        Book(
            isbn = "978-0135957059",
            title = "The Pragmatic Programmer",
            nbPages = 352,
            imageUrl = "https://m.media-amazon.com/images/I/518FqJvR9aL.jpg"
        ),
        Book(
            isbn = "978-0134757599",
            title = "Refactoring",
            nbPages = 448,
            imageUrl = "https://m.media-amazon.com/images/I/71e6ndHEwqL._SY342_.jpg"
        ) ,
        Book(
        isbn = "978-0201633610",
        title = "Design Patterns",
        nbPages = 416,
        imageUrl = "https://m.media-amazon.com/images/I/81IGFC6oFmL._SY385_.jpg"
        ),
        Book(
        isbn = "978-1492078005",
        title = "Head First Design Patterns",
        nbPages = 669,
        imageUrl = "https://m.media-amazon.com/images/I/91quawUTiVL._SY342_.jpg"
        ),
        Book(
        isbn = "978-0262046305",
        title = "Introduction to Algorithms",
        nbPages = 1312,
        imageUrl = "https://m.media-amazon.com/images/I/61Mw06x2XcL._SY342_.jpg"
        )
    )



    private val booksFlow = MutableSharedFlow<List<Book>>(replay = 1).apply {
        tryEmit(_booksList.sortedBy { it.title })
    }

    override fun getAllBooks(): Flow<List<Book>> = flow {
        delay(2000)
        emitAll(
            booksFlow
        )
    }

    override fun getBookByIsbn(isbn: String): Book? {
        return _booksList.find { it.isbn == isbn }
    }

    override fun addBook(book: Book) {
        _booksList.add(book)

        booksFlow.tryEmit(
            _booksList.sortedBy { it.title }
        )
    }
}
