package nz.jeremylee.invoices.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import nz.jeremylee.invoices.domain.GetInvoicesUseCase
import nz.jeremylee.invoices.domain.model.Invoice
import nz.jeremylee.invoices.domain.model.totalInCents
import timber.log.Timber
import java.text.NumberFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class InvoiceListViewModel @Inject constructor(
    private val getInvoicesUseCase: GetInvoicesUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow<InvoiceListUiState>(InvoiceListUiState.Loading)
    val uiState: StateFlow<InvoiceListUiState> = _uiState.asStateFlow()

    init {
        loadInvoices()
    }

    fun loadInvoices() {
        _uiState.update { InvoiceListUiState.Loading }
        viewModelScope.launch {
            getInvoicesUseCase()
                .onSuccess(::handleLoadSuccess)
                .onFailure(::handleLoadFailure)
        }
    }

    private fun handleLoadSuccess(invoices: List<Invoice>) {
        _uiState.update {
            if (invoices.isEmpty()) {
                InvoiceListUiState.Empty
            } else {
                InvoiceListUiState.Loaded(
                    invoices = invoices.map { it.toUi() },
                )
            }
        }
    }

    private fun handleLoadFailure(error: Throwable) {
        Timber.e(error, "Error loading invoices")
        _uiState.update { InvoiceListUiState.Error }
    }

    private fun Invoice.toUi() =
        InvoiceUi(
            id = id,
            date = date.toDisplayString(),
            description = description,
            total = totalInCents.centsToDisplayDollars(),
        )

    private fun Int.centsToDisplayDollars(): String =
        NumberFormat
            .getCurrencyInstance(Locale.forLanguageTag("en-AU"))
            .format(this / 100.0)

    private fun LocalDateTime.toDisplayString(): String =
        this.format(DateTimeFormatter.ofPattern(DATE_FORMAT))

    companion object {
        private const val DATE_FORMAT = "d MMM yyyy"
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