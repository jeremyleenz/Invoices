package nz.jeremylee.invoices.data

import nz.jeremylee.invoices.data.model.InvoiceDto
import nz.jeremylee.invoices.data.model.InvoiceLineItemDto
import nz.jeremylee.invoices.domain.model.Invoice
import nz.jeremylee.invoices.domain.model.InvoiceLineItem
import java.time.LocalDateTime
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InvoiceRepository @Inject constructor(
    private val invoiceService: InvoiceService,
) {

    suspend fun getInvoices(): Result<List<Invoice>> = runCatching {
        invoiceService.getInvoices().items.map { it.toDomain() }
    }

    private fun InvoiceDto.toDomain() = Invoice(
        id = id,
        date = LocalDateTime.parse(date),
        description = description,
        items = items.map { it.toDomain() },
    )

    private fun InvoiceLineItemDto.toDomain() = InvoiceLineItem(
        name = name,
        quantity = quantity,
        priceInCents = priceInCents,
    )
}
