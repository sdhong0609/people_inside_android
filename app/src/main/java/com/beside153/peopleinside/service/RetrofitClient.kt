package com.beside153.peopleinside.service

import com.beside153.peopleinside.App
import com.beside153.peopleinside.common.exception.ApiException
import com.beside153.peopleinside.model.common.ErrorEnvelope
import com.beside153.peopleinside.service.mediacontent.BookmarkService
import com.beside153.peopleinside.service.mediacontent.MediaContentService
import com.beside153.peopleinside.service.mediacontent.RatingService
import com.beside153.peopleinside.service.mediacontent.ReviewService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.skydoves.sandwich.adapters.ApiResponseCallAdapterFactory
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import java.io.IOException
import java.net.UnknownHostException
import timber.log.Timber

object RetrofitClient {
    private const val baseUrl = "https://people-inside.com"
    private const val contentType = "application/json"

    private val json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
    }

    private val signUpRetrofit: Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(provideOkHttpClient(ErrorInterceptor()))
        .addConverterFactory(json.asConverterFactory(contentType.toMediaType()))
        .addCallAdapterFactory(ApiResponseCallAdapterFactory.create())
        .build()

    private val apiResponseRetrofit: Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(provideOkHttpClient(AppInterceptor(), ErrorInterceptor()))
        .addConverterFactory(json.asConverterFactory(contentType.toMediaType()))
        .addCallAdapterFactory(ApiResponseCallAdapterFactory.create())
        .build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(provideOkHttpClient(AppInterceptor(), ErrorInterceptor()))
        .addConverterFactory(json.asConverterFactory(contentType.toMediaType()))
        .build()

    private fun provideOkHttpClient(vararg interceptors: Interceptor): OkHttpClient = OkHttpClient.Builder().run {
        interceptors.forEach {
            addInterceptor(it)
        }
        build()
    }

    class AppInterceptor : Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response = with(chain) {
            @Suppress("UnusedPrivateMember")
            val userId = App.prefs.getUserId()
            val jwtToken =
                App.prefs.getString(App.prefs.jwtTokenKey)
            val newRequest = request().newBuilder()
                .addHeader("authorization", "Bearer $jwtToken")
                .build()
            proceed(newRequest)
        }
    }

    @Suppress("TooGenericExceptionCaught", "RethrowCaughtException", "SwallowedException")
    class ErrorInterceptor : Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response = with(chain) {
            val request = chain.request()
            val response: Response
            try {
                response = chain.proceed(request)
            } catch (e: UnknownHostException) {
                throw e
            }

            if (response.isSuccessful) {
                return response
            }

            val body = response.body?.string() ?: run {
                Timber.e("body is null")
                return response
            }
            Timber.e("body = $body")
            val error = json.decodeFromString<ErrorEnvelope>(body)
            throw ApiException(error)
        }
    }

    val mediaContentService: MediaContentService = retrofit.create(MediaContentService::class.java)
    val ratingService: RatingService = retrofit.create(RatingService::class.java)
    val reviewService: ReviewService = retrofit.create(ReviewService::class.java)
    val bookmarkService: BookmarkService = retrofit.create(BookmarkService::class.java)
    val myContentService: MyContentService = retrofit.create(MyContentService::class.java)
    val reportService: ReportService = apiResponseRetrofit.create(ReportService::class.java)
    val authService: AuthService = signUpRetrofit.create(AuthService::class.java)
    val userService: UserService = retrofit.create(UserService::class.java)
    val withDrawalService: WithDrawalService = retrofit.create(WithDrawalService::class.java)

    val editProfileService: EditProfileService = apiResponseRetrofit.create(EditProfileService::class.java)
}
