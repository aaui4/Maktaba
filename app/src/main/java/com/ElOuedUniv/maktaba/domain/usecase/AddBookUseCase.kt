package com.ElOuedUniv.maktaba.domain.usecase

import android.net.Uri
import com.ElOuedUniv.maktaba.data.model.Book
import com.ElOuedUniv.maktaba.data.repository.BookRepository
import javax.inject.Inject

class AddBookUseCase @Inject constructor(
    private val repository: BookRepository
) {
    suspend operator fun invoke(book: Book, imageBytes: ByteArray?) {
        repository.addBook(book, imageBytes)
    }
}