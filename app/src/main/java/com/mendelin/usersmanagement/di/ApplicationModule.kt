package com.mendelin.usersmanagement.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.mendelin.usersmanagement.BuildConfig
import com.mendelin.usersmanagement.data.remote.RandomUsersApi
import com.mendelin.usersmanagement.data.remote.RandomUsersRepository
import com.mendelin.usersmanagement.domain.usecase.RandomUsersListUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {
    private const val READ_TIMEOUT = 60L
    private const val CONNECTION_TIMEOUT = 60L

    private const val TRY_COUNT = 3
    private const val TRY_PAUSE_BETWEEN = 1000L

    @Provides
     @Singleton
     fun provideHeaderInterceptor(): Interceptor {
        return Interceptor { chain ->
            val request = chain.request()
            val builder = request.newBuilder()

            builder.header("accept", "*/*")
            builder.header("Content-Type", "application/json")

            var response = chain.proceed(builder.build())

            /* Automatically retry the call for N times if you receive a server error (5xx) */
            var tryCount = 0
            while (response.code() in 500..599 && tryCount < TRY_COUNT) {
                try {
                    response.close()
                    Thread.sleep(TRY_PAUSE_BETWEEN)
                    response = chain.proceed(builder.build())
                    Timber.e("Request is not successful - $tryCount")
                } catch (e: Exception) {
                    Timber.e(e.localizedMessage)
                } finally {
                    tryCount++
                }
            }

            /* Intercept empty body response */
            if ((response.body()?.contentLength() ?: 0L) == 0L) {
                val contentType: MediaType? = response.body()?.contentType()
                val body: ResponseBody = ResponseBody.create(contentType, "{}")
                return@Interceptor response.newBuilder().body(body).build()
            }

            return@Interceptor response
        }
     }

    @Provides
    @Singleton
    fun provideOkHttpClient(interceptor: Interceptor): OkHttpClient {
        return OkHttpClient.Builder().apply {
            readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
            addInterceptor(interceptor)
        }.build()
    }

    @Provides
    @Singleton
    fun provideGsonClient(): Gson {
        return GsonBuilder().setLenient().create()
    }

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson, client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    fun provideRandomUsersApi(retrofit: Retrofit): RandomUsersApi {
        return retrofit.create(RandomUsersApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRandomUsersRepository(api: RandomUsersApi): RandomUsersRepository {
        return RandomUsersRepository(api)
    }


    @Provides
    @Singleton
    fun provideRandomUsersListUseCase(repository: RandomUsersRepository): RandomUsersListUseCase {
        return RandomUsersListUseCase(repository)
    }
}