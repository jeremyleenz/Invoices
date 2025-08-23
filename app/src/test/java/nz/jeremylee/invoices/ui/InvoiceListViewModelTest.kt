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
import nz.jeremylee.invoices.util.MainDispatcherRule
import nz.jeremylee.invoices.util.invoices
import nz.jeremylee.invoices.util.invoicesUi
import org.junit.After
import org.junit.Rule
import org.junit.Test

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
    fun `when load invoices success with non-empty list, should show loaded state`() = runTest {
        // Given
        coEvery { getInvoicesUseCase() } returns Result.success(invoices())

        viewModel.uiState.test {
            // When init

            // Then
            assertEquals(InvoiceListUiState.Loading, awaitItem())
            assertEquals(InvoiceListUiState.Loaded(invoicesUi()), awaitItem())
            expectNoEvents()
        }
        coVerify(exactly = 1) { getInvoicesUseCase() }
    }

    @Test
    fun `when load invoices success with empty list, should show empty state`() = runTest {
        // Given
        coEvery { getInvoicesUseCase() } returns Result.success(emptyList())

        viewModel.uiState.test {
            // When init

            // Then
            assertEquals(InvoiceListUiState.Loading, awaitItem())
            assertEquals(InvoiceListUiState.Empty, awaitItem())
            expectNoEvents()
        }
        coVerify(exactly = 1) { getInvoicesUseCase() }
    }

    @Test
    fun `when load invoices with error, should show error state`() = runTest {
        // Given
        coEvery { getInvoicesUseCase() } returns Result.failure(Exception())

        viewModel.uiState.test {
            // When init

            // Then
            assertEquals(InvoiceListUiState.Loading, awaitItem())
            assertEquals(InvoiceListUiState.Error, awaitItem())
            expectNoEvents()
        }
        coVerify(exactly = 1) { getInvoicesUseCase() }
    }

    @Test
    fun `when retry success with non-empty list, should show loaded state`() = runTest {
        // Given
        coEvery { getInvoicesUseCase() } returns Result.success(invoices())

        viewModel.uiState.test {
            // When
            viewModel.onRetryClick()

            // Then
            assertEquals(InvoiceListUiState.Loading, awaitItem())
            assertEquals(InvoiceListUiState.Loaded(invoicesUi()), awaitItem())
            expectNoEvents()
        }
        coVerify(exactly = 2) { getInvoicesUseCase() }
    }

    @Test
    fun `when retry success with empty list, should show empty state`() = runTest {
        // Given
        coEvery { getInvoicesUseCase() } returns Result.success(emptyList())

        viewModel.uiState.test {
            // When
            viewModel.onRetryClick()

            // Then
            assertEquals(InvoiceListUiState.Loading, awaitItem())
            assertEquals(InvoiceListUiState.Empty, awaitItem())
            expectNoEvents()
        }
        coVerify(exactly = 2) { getInvoicesUseCase() }
    }

    @Test
    fun `when retry with error, should show error state`() = runTest {
        // Given
        coEvery { getInvoicesUseCase() } returns Result.failure(Exception())

        viewModel.uiState.test {
            // When
            viewModel.onRetryClick()

            // Then
            assertEquals(InvoiceListUiState.Loading, awaitItem())
            assertEquals(InvoiceListUiState.Error, awaitItem())
            expectNoEvents()
        }
        coVerify(exactly = 2) { getInvoicesUseCase() }
    }
}
