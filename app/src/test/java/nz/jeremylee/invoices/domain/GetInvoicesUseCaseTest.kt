package nz.jeremylee.invoices.domain

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.test.runTest
import nz.jeremylee.invoices.data.InvoiceRepository
import nz.jeremylee.invoices.domain.model.Invoice
import nz.jeremylee.invoices.util.invoices
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetInvoicesUseCaseTest {

    private val invoiceRepository = mockk<InvoiceRepository>()

    private lateinit var getInvoicesUseCase: GetInvoicesUseCase

    @Before
    fun setUp() {
        getInvoicesUseCase = GetInvoicesUseCase(invoiceRepository)
    }

    @After
    fun tearDown() {
        confirmVerified(invoiceRepository)
        unmockkAll()
    }

    @Test
    fun `when repository success, should return list of invoices`() = runTest {
        val invoices = invoices()
        coEvery { invoiceRepository.getInvoices() } returns Result.success(invoices)

        val result = getInvoicesUseCase()

        assertEquals(Result.success(invoices), result)
        coVerify { invoiceRepository.getInvoices() }
    }

    @Test
    fun `when repository failure, should return error`() = runTest {
        val error = Exception()
        coEvery { invoiceRepository.getInvoices() } returns Result.failure(error)

        val result = getInvoicesUseCase()

        assertEquals(Result.failure<List<Invoice>>(error), result)
        coVerify { invoiceRepository.getInvoices() }
    }
}