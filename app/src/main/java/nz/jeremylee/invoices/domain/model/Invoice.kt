package nz.jeremylee.invoices.domain.model

import java.time.LocalDateTime

data class Invoice(
    val id: String,
    val date: LocalDateTime,
    val description: String?,
    val items: List<InvoiceLineItem>,
)

val Invoice.totalInCents: Int
    get() = items.sumOf { it.totalInCents }
