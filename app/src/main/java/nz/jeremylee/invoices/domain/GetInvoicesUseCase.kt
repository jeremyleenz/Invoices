package nz.jeremylee.invoices.domain

import nz.jeremylee.invoices.data.InvoiceRepository
import nz.jeremylee.invoices.domain.model.Invoice
import javax.inject.Inject

class GetInvoicesUseCase @Inject constructor(
    private val invoiceRepository: InvoiceRepository,
) {

    suspend operator fun invoke(): Result<List<Invoice>> =
        invoiceRepository.getInvoices()
}