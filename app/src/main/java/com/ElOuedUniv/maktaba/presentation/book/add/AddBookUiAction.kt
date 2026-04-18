package com.ElOuedUniv.maktaba.presentation.book.add

sealed class AddBookUiAction {
    data class OnTitleChange(val title: String) : AddBookUiAction()
    data class OnIsbnChange(val isbn: String) : AddBookUiAction()
    data class OnPagesChange(val pages: String) : AddBookUiAction()
    data class OnImageUrlChange(val imageUrl: String) : AddBookUiAction()

    data class OnStatusChange(val status: String) : AddBookUiAction()
    object OnAddClick : AddBookUiAction()
}
