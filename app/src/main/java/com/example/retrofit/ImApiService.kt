package com.example.retrofit

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val URL = "https://picsum.photos/v2/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit: Retrofit = Retrofit.Builder().addConverterFactory(MoshiConverterFactory.create(moshi)).addCallAdapterFactory(CoroutineCallAdapterFactory()).baseUrl(URL)
    .build()

interface ImApiService
{
    @GET("list?limit=65")
    fun getProperties(): Deferred<List<ImageProperties>>
}

object ImageApi
{
    val retrofitService : ImApiService by lazy{
        retrofit.create(ImApiService::class.java)
    }
}
