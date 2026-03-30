package com.ElOuedUniv.maktaba.presentation.book

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ElOuedUniv.maktaba.data.model.Book
import com.ElOuedUniv.maktaba.domain.usecase.AddBookUseCase
import com.ElOuedUniv.maktaba.domain.usecase.GetBooksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookViewModel @Inject constructor(
    private val getBooksUseCase: GetBooksUseCase,
    private val addBookUseCase: AddBookUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(BookUiState())
    val uiState: StateFlow<BookUiState> = _uiState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<BookUiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    init {
        loadBooks()
    }

    fun loadBooks() {

        viewModelScope.launch {

            _uiState.update { it.copy(isLoading = true) }

            getBooksUseCase()
                .catch {

                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = "Error loading books"
                        )
                    }

                }
                .collect { bookList ->

                    _uiState.update {
                        it.copy(
                            books = bookList,
                            isLoading = false
                        )
                    }

                }
        }
    }

    fun onAction(action: BookUiAction) {

        when (action) {

            BookUiAction.RefreshBooks -> refreshBooks()

            BookUiAction.OnAddBookClick -> {

                _uiState.update {
                    it.copy(isAddingBook = true)
                }

            }

            BookUiAction.OnDismissAddBook -> {

                _uiState.update {
                    it.copy(isAddingBook = false)
                }

            }

            is BookUiAction.OnAddBookConfirm -> {

                viewModelScope.launch {

                    val newBook = Book(
                        isbn = action.isbn,
                        title = action.title,
                        nbPages = action.nbPages
                    )

                    addBookUseCase(newBook)

                    _uiState.update {
                        it.copy(isAddingBook = false)
                    }

                    _uiEvent.emit(
                        BookUiEvent.ShowSnackbar("Book added successfully")
                    )
                }

            }
        }
    }

    fun refreshBooks() {

        loadBooks()

    }
}