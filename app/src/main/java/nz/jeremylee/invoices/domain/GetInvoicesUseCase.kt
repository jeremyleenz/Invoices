package nz.jeremylee.invoices.domain

import nz.jeremylee.invoices.domain.model.Invoice
import nz.jeremylee.invoices.domain.model.InvoiceLineItem
import java.time.LocalDateTime
import javax.inject.Inject

class GetInvoicesUseCase @Inject constructor() {

    suspend operator fun invoke(): Result<List<Invoice>> {
        return Result.success(
            listOf(
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
                    date = LocalDateTime.of(2023, 1, 2, 0, 0),
                    description = "Invoice 2",
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
        )
    }
}