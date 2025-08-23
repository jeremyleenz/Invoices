package nz.jeremylee.invoices.domain.model

data class InvoiceLineItem(
    val name: String,
    val quantity: Int,
    val priceInCents: Int,
)

val InvoiceLineItem.totalInCents: Int
    get() = quantity * priceInCents
