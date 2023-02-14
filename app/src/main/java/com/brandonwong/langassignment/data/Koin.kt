package com.brandonwong.langassignment.data

import android.content.Context
import android.view.inputmethod.InputMethodManager
import com.brandonwong.langassignment.BuildConfig
import com.brandonwong.langassignment.data.api.ITmdbApiService
import com.brandonwong.langassignment.data.moshi.DefaultIfNullFactory
import com.brandonwong.langassignment.data.provider.ApiInfoProvider
import com.brandonwong.langassignment.data.provider.MoshiProvider
import com.brandonwong.langassignment.data.repository.*
import com.brandonwong.langassignment.ui.viewmodel.ChartViewModel
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object Koin {
    @JvmStatic
    fun initKoin(app: Context, moduleList: List<Module> = generateModules(app)) {
        startKoin {
            androidContext(app)
            modules(moduleList)
        }
    }

    private fun generateModules(context: Context): List<Module> {
        return listOf (
            module {
                viewModel { ChartViewModel(get(), get()) }
                factory { context.applicationContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager }
                single { TmdbMovieRepository() } bind ITmdbMovieRepository::class
                single { PagingSourceRepository() } bind IPagingSourceRepository::class
                single<ITmdbApiService> {
                    val client: OkHttpClient = OkHttpClient.Builder()
                        .addInterceptor { chain ->
                            val newRequest = chain.request().newBuilder()
                                .addHeader("Authorization", "Bearer ${BuildConfig.TMBD_ACCESS_KEY}")
                                .build()
                            chain.proceed(newRequest)
//                            Response.Builder()
//                                .request(chain.request())
//                                .protocol(Protocol.HTTP_1_1)
//                                .code(999)
//                                .message("Just testing")
//                                .body("[]".toResponseBody(null)).build()
                        }.also {
                            it.addInterceptor(HttpLoggingInterceptor().apply {
                                level = when (BuildConfig.DEBUG) {
                                    true -> HttpLoggingInterceptor.Level.BODY
                                    else -> HttpLoggingInterceptor.Level.NONE
                                }
                            })
                        }.build()
                    Retrofit.Builder()
                        .baseUrl(ApiInfoProvider.geTmdbApiBaseUrl())
                        .client(client)
                        .addConverterFactory(MoshiConverterFactory.create(MoshiProvider.moshiDefaultIfNull))
                        .build()
                        .create(ITmdbApiService::class.java)
                }
            }
        )
    }
}