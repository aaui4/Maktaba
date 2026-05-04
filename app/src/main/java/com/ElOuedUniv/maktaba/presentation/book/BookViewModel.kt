package com.ElOuedUniv.maktaba.presentation.book

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ElOuedUniv.maktaba.data.model.Book
import com.ElOuedUniv.maktaba.domain.usecase.AddBookUseCase
import com.ElOuedUniv.maktaba.domain.usecase.GetBooksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookViewModel @Inject constructor(
    private val getBooksUseCase: GetBooksUseCase,
    private val addBookUseCase: AddBookUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(BookUiState())
    val uiState: StateFlow<BookUiState> = _uiState.asStateFlow()

    init {
        loadBooks()
    }

    private fun loadBooks() {
        viewModelScope.launch {

            getBooksUseCase()
                .catch { e ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = e.message
                    )
                }
                .collect { bookList ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        books = bookList
                    )
                }
        }
    }

    fun onAction(action: BookUiAction) {
        when (action) {

            BookUiAction.RefreshBooks -> {
            }

            BookUiAction.OnAddBookClick -> {
                _uiState.value = _uiState.value.copy(isAddingBook = true)
            }

            BookUiAction.OnDismissAddBook -> {
                _uiState.value = _uiState.value.copy(isAddingBook = false)
            }

            is BookUiAction.OnAddBookConfirm -> {

                val newBook = Book(
                    isbn = action.isbn,
                    title = action.title,
                    nbPages = action.nbPages
                )

                viewModelScope.launch {
                    try {
                        addBookUseCase(newBook, null)

                        _uiState.value = _uiState.value.copy(
                            isAddingBook = false,
                            isBookAdded = true
                        )

                    } catch (e: Exception) {
                        _uiState.value = _uiState.value.copy(
                            errorMessage = e.message
                        )
                    }
                }
            }
        }
    }
}