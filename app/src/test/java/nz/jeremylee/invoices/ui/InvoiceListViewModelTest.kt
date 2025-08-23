package nz.jeremylee.invoices.ui

import app.cash.turbine.test
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import io.mockk.unmockkAll
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import nz.jeremylee.invoices.domain.GetInvoicesUseCase
import nz.jeremylee.invoices.domain.model.Invoice
import nz.jeremylee.invoices.domain.model.InvoiceLineItem
import nz.jeremylee.invoices.util.MainDispatcherRule
import org.junit.After
import org.junit.Rule
import org.junit.Test
import java.time.LocalDateTime

class InvoiceListViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val getInvoicesUseCase = mockk<GetInvoicesUseCase>()

    private val viewModel by lazy { InvoiceListViewModel(getInvoicesUseCase) }

    @After
    fun tearDown() {
        confirmVerified(getInvoicesUseCase)
        unmockkAll()
    }

    @Test
    fun `when load invoices success with non-empty list, should show empty state`() = runTest {
        coEvery { getInvoicesUseCase() } returns Result.success(invoices())

        viewModel.uiState.test {
            viewModel.loadInvoices()

            assertEquals(InvoiceListUiState.Loading, awaitItem())
            assertEquals(InvoiceListUiState.Loaded(invoicesUi()), awaitItem())
            expectNoEvents()
        }
        coVerify { getInvoicesUseCase() }
    }

    @Test
    fun `when load invoices success with empty list, should show empty state`() = runTest {
        coEvery { getInvoicesUseCase() } returns Result.success(emptyList())

        viewModel.uiState.test {
            viewModel.loadInvoices()

            assertEquals(InvoiceListUiState.Loading, awaitItem())
            assertEquals(InvoiceListUiState.Empty, awaitItem())
            expectNoEvents()
        }
        coVerify { getInvoicesUseCase() }
    }

    @Test
    fun `when load invoices with error, should show error state`() = runTest {
        coEvery { getInvoicesUseCase() } returns Result.failure(Exception())

        viewModel.uiState.test {
            viewModel.loadInvoices()

            assertEquals(InvoiceListUiState.Loading, awaitItem())
            assertEquals(InvoiceListUiState.Error, awaitItem())
            expectNoEvents()
        }
        coVerify { getInvoicesUseCase() }
    }

    private fun invoices() = listOf(
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

    private fun invoicesUi() = listOf(
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
}
