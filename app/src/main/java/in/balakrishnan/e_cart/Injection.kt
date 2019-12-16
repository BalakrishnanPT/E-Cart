package `in`.balakrishnan.e_cart

import `in`.balakrishnan.e_cart.repo.remote.IRemoteRepository
import `in`.balakrishnan.e_cart.repo.remote.RemoteAPI
import `in`.balakrishnan.e_cart.repo.remote.RemoteRepository
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object Injection {
    fun provideRepository(): IRemoteRepository =
        RemoteRepository

    private fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://www.mocky.io/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(provideOkHttpClient())
            .build()
    }

    private fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.level = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
        return logging
    }

    private fun provideOkHttpClient(): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        val builder = CertificatePinner.Builder()
            .add("mocky.io", "sha256/Z+r9VBKzv/i1pxUhONWCIcIxrzd7IjmuDFu/+bEvj60=").build()
        httpClient.addInterceptor(provideLoggingInterceptor())
        return httpClient.certificatePinner(builder).build()
    }

    fun provideRemoteAPI(): RemoteAPI {
        return provideRetrofit().create(RemoteAPI::class.java)
    }

}