package nz.jeremylee.invoices.data

import retrofit2.http.GET

interface InvoiceService {

    @GET("invoices.json")
    suspend fun getInvoices(): InvoiceResponse
}