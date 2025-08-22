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
        loadInvoices()
    }

    fun onRetryClick() {
        loadInvoices()
    }

    private fun loadInvoices() {
        _uiState.update { InvoiceListUiState.Loading }
        viewModelScope.launch {
            delay(1.seconds)
            _uiState.update {
                InvoiceListUiState.Loaded(
                    invoices = listOf(
                        InvoiceUi(
                            id = "1",
                            date = "1 Jan 2025",
                            description = "Description",
                            total = "$100.00",
                        ),
                        InvoiceUi(
                            id = "2",
                            date = "2 Jan 2025",
                            description = null,
                            total = "$123.10"
                        ),
                    ),
                )
            }
        }
    }
}

sealed interface InvoiceListUiState {
    data object Loading : InvoiceListUiState
    data class Loaded(val invoices: List<InvoiceUi>) : InvoiceListUiState
    data object Empty : InvoiceListUiState
    data object Error : InvoiceListUiState
}

data class InvoiceUi(
    val id: String,
    val date: String,
    val description: String?,
    val total: String,
)