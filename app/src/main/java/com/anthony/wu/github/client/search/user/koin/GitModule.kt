package com.anthony.wu.github.client.search.user.koin

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.core.net.toUri
import com.anthony.wu.github.client.search.user.BuildConfig
import com.anthony.wu.github.client.search.user.dto.response.ErrorMessageDto
import com.google.gson.Gson
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.nio.charset.Charset
import java.util.concurrent.TimeUnit
import kotlin.concurrent.thread


val gitModule = module {
    single { createOkHttpClient(androidContext()) }
}

fun createOkHttpClient(context: Context,openInterceptor: Boolean = true): OkHttpClient {
    val httpClient = OkHttpClient.Builder()
    httpClient.connectTimeout(20, TimeUnit.SECONDS)
        .readTimeout(20, TimeUnit.SECONDS)
        .followRedirects(false)
        .followSslRedirects(false)
        .addInterceptor(
            HttpLoggingInterceptor().setLevel(
                if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
            )
        )

    if (openInterceptor) {
        httpClient.addInterceptor(RedirectInterceptor(context))
    }

    return httpClient.build()
}



inline fun <reified T> createService(okHttpClient: OkHttpClient): T {
    val retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build()
    return retrofit.create(T::class.java)
}

private fun createResponse(chain: Interceptor.Chain, request: Request): Response {
    return chain.proceed(
        request.newBuilder().header("Accept", "application/json")
            .method(request.method, request.body).build()
    )
}


class RedirectInterceptor(private val context: Context) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request()
        val response = createResponse(chain, request)
        when (response.code) {
            in 400..900 -> {
                getResponseBody(response.body)?.let {

                    val responseError = Gson().fromJson(it, ErrorMessageDto::class.java).message

                   throw IOException(responseError)

                }
            }
        }

        return response

    }

    private fun getResponseBody(responseBody: ResponseBody?): String? {

        var body: String? = null

        responseBody?.let {

            val contentLength = responseBody.contentLength()

            if (contentLength != 0L) {

                val source = responseBody.source()
                source.request(Integer.MAX_VALUE.toLong()) // Buffer the entire body.
                val buffer = source.buffer

                var charset: Charset? = Charset.forName("UTF-8")
                val contentType = responseBody.contentType()
                if (contentType != null) {
                    charset = contentType.charset(Charset.forName("UTF-8"))
                }

                body = buffer.clone().readString(charset!!)
            }
        }

        return body

    }
}


