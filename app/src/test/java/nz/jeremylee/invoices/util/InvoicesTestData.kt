package nz.jeremylee.invoices.util

import nz.jeremylee.invoices.data.model.InvoiceDto
import nz.jeremylee.invoices.data.model.InvoiceLineItemDto
import nz.jeremylee.invoices.domain.model.Invoice
import nz.jeremylee.invoices.domain.model.InvoiceLineItem
import nz.jeremylee.invoices.ui.InvoiceLineItemUi
import nz.jeremylee.invoices.ui.InvoiceUi
import java.time.LocalDateTime

fun invoices() = listOf(
    Invoice(
        id = "1",
        date = LocalDateTime.of(2023, 1, 1, 0, 0),
        description = "Invoice 1",
        items = listOf(
            InvoiceLineItem(
                name = "Item 1",
                quantity = 1,
                priceInCents = 100,
            ),
            InvoiceLineItem(
                name = "Item 2",
                quantity = 3,
                priceInCents = 200,
            ),
        )
    ),
    Invoice(
        id = "2",
        date = LocalDateTime.of(2023, 3, 15, 0, 0),
        description = null,
        items = listOf(
            InvoiceLineItem(
                name = "Item 1",
                quantity = 3,
                priceInCents = 100,
            ),
            InvoiceLineItem(
                name = "Item 2",
                quantity = 7,
                priceInCents = 200,
            ),
        )
    )
)

fun invoicesUi() = listOf(
    InvoiceUi(
        id = "1",
        date = "1 Jan 2023",
        description = "Invoice 1",
        total = "$7.00",
        lineItems = listOf(
            InvoiceLineItemUi(
                name = "Item 1",
                quantity = "1",
                price = "$1.00",
            ),
            InvoiceLineItemUi(
                name = "Item 2",
                quantity = "3",
                price = "$2.00",
            ),
        )
    ),
    InvoiceUi(
        id = "2",
        date = "15 Mar 2023",
        description = null,
        total = "$17.00",
        lineItems = listOf(
            InvoiceLineItemUi(
                name = "Item 1",
                quantity = "3",
                price = "$1.00",
            ),
            InvoiceLineItemUi(
                name = "Item 2",
                quantity = "7",
                price = "$2.00",
            ),
        )
    )
)

fun invoiceDtos() = listOf(
    InvoiceDto(
        id = "1",
        date = "2023-01-01T00:00:00",
        description = "Invoice 1",
        items = listOf(
            InvoiceLineItemDto(
                id = "1",
                name = "Item 1",
                quantity = 1,
                priceInCents = 100,
            ),
            InvoiceLineItemDto(
                id = "2",
                name = "Item 2",
                quantity = 3,
                priceInCents = 200,
            ),
        )
    ),
    InvoiceDto(
        id = "2",
        date = "2023-03-15T00:00:00",
        description = null,
        items = listOf(
            InvoiceLineItemDto(
                id = "1",
                name = "Item 1",
                quantity = 3,
                priceInCents = 100,
            ),
            InvoiceLineItemDto(
                id = "2",
                name = "Item 2",
                quantity = 7,
                priceInCents = 200,
            ),
        )
    ),
)