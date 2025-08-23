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
        // Given
        val invoices = invoices()
        coEvery { invoiceRepository.getInvoices() } returns Result.success(invoices)

        // When
        val result = getInvoicesUseCase()

        // Then
        assertEquals(Result.success(invoices), result)
        coVerify { invoiceRepository.getInvoices() }
    }

    @Test
    fun `when repository failure, should return error`() = runTest {
        // Given
        val error = Exception()
        coEvery { invoiceRepository.getInvoices() } returns Result.failure(error)

        // When
        val result = getInvoicesUseCase()

        // Then
        assertEquals(Result.failure<List<Invoice>>(error), result)
        coVerify { invoiceRepository.getInvoices() }
    }
}