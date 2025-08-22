package nz.jeremylee.invoices.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

@HiltViewModel
class InvoiceListViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow<InvoiceListUiState>(InvoiceListUiState.Loading)
    val uiState: StateFlow<InvoiceListUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            delay(1.seconds)
            _uiState.update { InvoiceListUiState.Loaded }
        }
    }

    fun onRetryClick() {
        // TODO
    }
}

sealed interface InvoiceListUiState {
    data object Loading : InvoiceListUiState
    data object Loaded : InvoiceListUiState
    data object Empty : InvoiceListUiState
    data object Error : InvoiceListUiState
}