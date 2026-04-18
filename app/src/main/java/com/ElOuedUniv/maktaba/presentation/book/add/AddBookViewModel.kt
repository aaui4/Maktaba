package com.ElOuedUniv.maktaba.presentation.book.add

import androidx.lifecycle.ViewModel
import com.ElOuedUniv.maktaba.data.model.Book
import com.ElOuedUniv.maktaba.domain.usecase.AddBookUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class AddBookViewModel @Inject constructor(
    private val addBookUseCase: AddBookUseCase
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(AddBookUiState())
    val uiState = _uiState.asStateFlow()

    fun onAction(action: AddBookUiAction) {
        when (action) {
            is AddBookUiAction.OnTitleChange -> {
                _uiState.update { it.copy(title = action.title) }
                validateInputs()
            }
            is AddBookUiAction.OnIsbnChange -> {
                _uiState.update { it.copy(isbn = action.isbn) }
                validateInputs()
            }
            is AddBookUiAction.OnPagesChange -> {
                _uiState.update { it.copy(nbPages = action.pages) }
                validateInputs()
            }
            AddBookUiAction.OnAddClick -> {
                if (_uiState.value.isFormValid) {
                    addBook()
                }
            }
            is AddBookUiAction.OnImageUrlChange -> {
                _uiState.update { it.copy(imageUrl = action.imageUrl) }
            }
            is AddBookUiAction.OnStatusChange -> {
                _uiState.update { it.copy(status = action.status) }
            }
        }
    }

    private fun addBook() {
        val currentState = _uiState.value
        val book = Book(
            isbn = currentState.isbn,
            title = currentState.title,
            nbPages = currentState.nbPages.toIntOrNull() ?: 0,
            status = currentState.status
            )
        addBookUseCase(book)
        _uiState.update { it.copy(isSuccess = true) }
    }

    private fun isFormValid(state: AddBookUiState): Boolean {
        return state.titleError == null &&
                state.isbnError == null &&
                state.pagesError == null &&
                state.title.isNotBlank() &&
                state.isbn.isNotBlank()
    }

    private fun validateInputs() {
        val state = _uiState.value

        val titleError = if (state.title.isBlank()) {
            "Title cannot be empty"
        } else null

        val isbnError = if (state.isbn.length != 13 || !state.isbn.all { it.isDigit() }) {
            "ISBN must be 13 digits"
        } else null

        val pagesError = if (state.nbPages.toIntOrNull() == null || state.nbPages.toInt() <= 0) {
            "Pages must be positive"
        } else null

        val isValid = titleError == null && isbnError == null && pagesError == null

        _uiState.update {
            it.copy(
                titleError = titleError,
                isbnError = isbnError,
                pagesError = pagesError,
                isFormValid = isValid
            )
        }
    }
}
