package nz.jeremylee.invoices.data

import nz.jeremylee.invoices.data.model.InvoiceResponseDto
import retrofit2.http.GET

interface InvoiceService {

    @GET("invoices.json")
    suspend fun getInvoices(): InvoiceResponseDto
}