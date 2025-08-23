package nz.jeremylee.invoices.data

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import io.mockk.unmockkAll
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.test.runTest
import nz.jeremylee.invoices.data.model.InvoiceDto
import nz.jeremylee.invoices.data.model.InvoiceLineItemDto
import nz.jeremylee.invoices.data.model.InvoiceResponseDto
import nz.jeremylee.invoices.util.invoiceDtos
import nz.jeremylee.invoices.util.invoices
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.time.format.DateTimeParseException

class InvoiceRepositoryTest {

    private val service = mockk<InvoiceService>()
    private lateinit var repository: InvoiceRepository

    @Before
    fun setUp() {
        repository = InvoiceRepository(service)
    }

    @After
    fun tearDown() {
        confirmVerified(service)
        unmockkAll()
    }

    @Test
    fun `when service success with list, should return success with mapped invoices`() = runTest {
        // Given
        coEvery { service.getInvoices() } returns InvoiceResponseDto(items = invoiceDtos())

        // When
        val result = repository.getInvoices()

        // Then
        assertTrue(result.isSuccess)
        assertEquals(invoices(), result.getOrThrow())
        coVerify { service.getInvoices() }
    }

    @Test
    fun `when service success with empty list, should return success with empty list`() = runTest {
        // Given
        coEvery { service.getInvoices() } returns InvoiceResponseDto(items = emptyList())

        // When
        val result = repository.getInvoices()

        // Then
        assertTrue(result.isSuccess)
        assertTrue(result.getOrThrow().isEmpty())
        coVerify { service.getInvoices() }
    }

    @Test
    fun `when service exception, should return failure`() = runTest {
        // Given
        val error = RuntimeException("error")
        coEvery { service.getInvoices() } throws error

        // When
        val result = repository.getInvoices()

        // Then
        assertTrue(result.isFailure)
        assertEquals(error, result.exceptionOrNull())
        coVerify { service.getInvoices() }
    }

    @Test
    fun `when service success with invalid date, should return failure`() = runTest {
        // Given
        val dto = InvoiceDto(
            id = "1",
            date = "invalid-date",
            description = null,
            items = listOf(
                InvoiceLineItemDto(
                    id = "1",
                    name = "Item 1",
                    quantity = 1,
                    priceInCents = 100,
                )
            )
        )
        coEvery { service.getInvoices() } returns InvoiceResponseDto(items = listOf(dto))

        // When
        val result = repository.getInvoices()

        // Then
        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull() is DateTimeParseException)
        coVerify { service.getInvoices() }
    }
}