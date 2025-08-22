package nz.jeremylee.invoices.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import nz.jeremylee.invoices.R
import nz.jeremylee.invoices.ui.theme.InvoicesTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InvoiceListScreen(
    viewModel: InvoiceListViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.invoice_list_title)) }
            )
        }
    ) { innerPadding ->
        val modifier = Modifier.padding(innerPadding)
        when (uiState) {
            InvoiceListUiState.Loading -> LoadingContent(modifier)
            is InvoiceListUiState.Loaded -> LoadedContent(
                uiState = uiState as InvoiceListUiState.Loaded,
                modifier = modifier,
            )
            InvoiceListUiState.Empty -> EmptyContent(modifier)
            InvoiceListUiState.Error -> ErrorContent(
                modifier = modifier,
                onRetryClick = viewModel::onRetryClick,
            )
        }
    }
}

@Composable
private fun LoadingContent(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
    ) {
        CircularProgressIndicator(
            modifier = Modifier.align(Alignment.Center),
        )
    }
}

@Composable
private fun LoadedContent(
    uiState: InvoiceListUiState.Loaded,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
    ) {
        items(
            items = uiState.invoices,
            key = { it.id },
        ) {
            InvoiceItem(it)
        }
    }
}

@Composable
private fun InvoiceItem(
    item: InvoiceUi,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = item.date,
                style = MaterialTheme.typography.bodyLarge,
            )
            item.description?.let { description ->
                Spacer(Modifier.height(8.dp))
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodySmall,
                )
            }
            Spacer(Modifier.height(8.dp))
            // TODO: display line items
            Text(
                text = stringResource(R.string.invoice_item_total_label, item.total),
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}

@Composable
private fun EmptyContent(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
    ) {
        Text(
            text = stringResource(R.string.invoice_list_empty_message),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.Center),
        )
    }
}

@Composable
private fun ErrorContent(
    modifier: Modifier = Modifier,
    onRetryClick: () -> Unit = {},
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = stringResource(R.string.invoice_list_error_message),
            style = MaterialTheme.typography.bodyLarge,
        )
        Button(
            onClick = onRetryClick,
            modifier = Modifier.padding(16.dp),
        ) {
            Text(
                text = stringResource(R.string.invoice_list_error_retry_action),
                style = MaterialTheme.typography.labelLarge,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewLoadingContent() {
    InvoicesTheme {
        LoadingContent()
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewLoadedContent() {
    InvoicesTheme {
        LoadedContent(
            uiState = InvoiceListUiState.Loaded(
                invoices = listOf(
                    InvoiceUi(
                        id = "1",
                        date = "1 Jan 2025",
                        description = "Description",
                        total = "$100.00",
                    ),
                    InvoiceUi(
                        id = "2",
                        date = "2 Jan 2025",
                        description = null,
                        total = "$123.10"
                    ),
                )
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewEmptyContent() {
    InvoicesTheme {
        EmptyContent()
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewErrorContent() {
    InvoicesTheme {
        ErrorContent()
    }
}
