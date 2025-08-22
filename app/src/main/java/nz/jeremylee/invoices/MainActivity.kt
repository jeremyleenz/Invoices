package nz.jeremylee.invoices

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import dagger.hilt.android.AndroidEntryPoint
import nz.jeremylee.invoices.ui.InvoiceListScreen
import nz.jeremylee.invoices.ui.theme.InvoicesTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            InvoicesTheme {
                InvoiceListScreen()
            }
        }
    }
}
