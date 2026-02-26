package com.ElOuedUniv.maktaba.data.repository

import com.ElOuedUniv.maktaba.data.model.Book

class BookRepositoryImpl : BookRepository {
    private val booksList = listOf(
        Book(isbn = "978-0-13-235088-4", title = "Clean Code", nbPages = 464),
        Book(isbn = "978-0-201-61622-4", title = "The Pragmatic Programmer", nbPages = 352),
        Book(isbn = "978-0-201-63361-0", title = "Design Patterns", nbPages = 416),
        Book(isbn = "978-0-13-475759-9", title = "Refactoring", nbPages = 488),
        Book(isbn = "978-0-596-52068-7", title = "Head First Design Patterns", nbPages = 1312),
        Book(isbn = "978-1-491-94728-5", title = "Kotlin in Action", nbPages = 360),
        Book(isbn = "978-0-262-03384-8", title = "Introduction to Algorithms", nbPages = 1312)
    )

    override fun getAllBooks(): List<Book> = booksList

    override fun getBookByIsbn(isbn: String): Book? =
        booksList.find { it.isbn == isbn }
}
