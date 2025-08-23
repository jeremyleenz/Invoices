package nz.jeremylee.invoices.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class InvoiceResponseDto(
    val items: List<InvoiceDto>,
)

@Serializable
data class InvoiceDto(
    val id: String,
    val date: String,
    val description: String? = null,
    val items: List<InvoiceLineItemDto>,
)

@Serializable
data class InvoiceLineItemDto(
    val id: String,
    val name: String,
    val quantity: Int,
    @SerialName("priceinCents") val priceInCents: Int,
)