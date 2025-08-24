package nz.jeremylee.invoices.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import nz.jeremylee.invoices.data.InvoiceService
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object InvoicesModule {

    @Provides
    @Singleton
    fun provideInvoiceService(retrofit: Retrofit): InvoiceService {
        return retrofit.create()
    }

    @Provides
    @Singleton
    fun provideRetrofit(json: Json): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://storage.googleapis.com/xmm-homework/")
            .addConverterFactory(
                json.asConverterFactory("application/json".toMediaType())
            )
            .build()
    }

    @Provides
    @Singleton
    fun provideJson(): Json {
        return Json {
            isLenient = true
            ignoreUnknownKeys = true
        }
    }
}