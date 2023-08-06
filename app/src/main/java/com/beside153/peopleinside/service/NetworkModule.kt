package com.beside153.peopleinside.service

import com.beside153.peopleinside.App
import com.beside153.peopleinside.common.exception.ApiException
import com.beside153.peopleinside.model.common.ErrorEnvelope
import com.beside153.peopleinside.service.mediacontent.BookmarkService
import com.beside153.peopleinside.service.mediacontent.MediaContentService
import com.beside153.peopleinside.service.mediacontent.RatingService
import com.beside153.peopleinside.service.mediacontent.ReviewService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import timber.log.Timber
import java.io.IOException
import java.net.UnknownHostException
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val baseUrl = "https://people-inside.com"
    private const val contentType = "application/json"

    private val json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
    }

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class AuthRetrofit

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class AppRetrofit

    @Singleton
    @Provides
    @AuthRetrofit
    fun provideAuthRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(provideOkHttpClient(ErrorInterceptor()))
        .addConverterFactory(json.asConverterFactory(contentType.toMediaType()))
        .build()

    @Singleton
    @Provides
    @AppRetrofit
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
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
            val userId = App.prefs.getUserId()
            val jwtToken = App.prefs.getJwtToken()
            val newRequest = request().newBuilder()
                .addHeader("authorization", "Bearer $jwtToken")
                .build()
            proceed(newRequest)
        }
    }

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

    @Singleton
    @Provides
    fun provideMediaContentService(@AppRetrofit retrofit: Retrofit): MediaContentService =
        retrofit.create(MediaContentService::class.java)

    @Singleton
    @Provides
    fun provideRatingService(@AppRetrofit retrofit: Retrofit): RatingService =
        retrofit.create(RatingService::class.java)

    @Singleton
    @Provides
    fun provideReviewService(@AppRetrofit retrofit: Retrofit): ReviewService =
        retrofit.create(ReviewService::class.java)

    @Singleton
    @Provides
    fun provideBookmarkService(@AppRetrofit retrofit: Retrofit): BookmarkService =
        retrofit.create(BookmarkService::class.java)

    @Singleton
    @Provides
    fun provideMyContentService(@AppRetrofit retrofit: Retrofit): MyContentService =
        retrofit.create(MyContentService::class.java)

    @Singleton
    @Provides
    fun provideReportService(@AppRetrofit retrofit: Retrofit): ReportService =
        retrofit.create(ReportService::class.java)

    @Singleton
    @Provides
    fun provideAuthService(@AuthRetrofit authRetrofit: Retrofit): AuthService =
        authRetrofit.create(AuthService::class.java)

    @Singleton
    @Provides
    fun provideUserService(@AppRetrofit retrofit: Retrofit): UserService =
        retrofit.create(UserService::class.java)

    @Singleton
    @Provides
    fun provideWithDrawalService(@AppRetrofit retrofit: Retrofit): WithDrawalService =
        retrofit.create(WithDrawalService::class.java)

    @Singleton
    @Provides
    fun provideAppVersionService(@AppRetrofit retrofit: Retrofit): AppVersionService =
        retrofit.create(AppVersionService::class.java)
}
