package com.jcy.ch22_searchfreeimage.data

import com.jcy.ch22_searchfreeimage.BuildConfig
import com.jcy.ch22_searchfreeimage.UnsplashApiService
import com.jcy.ch22_searchfreeimage.data.models.PhotoResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object Repository {
    private val unsplashApiService: UnsplashApiService by lazy {
        Retrofit.Builder()
            .baseUrl(Url.UNSPLASH_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()) //Json을 Gson으로 변환
            .client(buildOkHttpClient())
            .build()
            .create()
    }
    suspend fun getRandomPhotos(query: String?): List<PhotoResponse>? =
        unsplashApiService.getRandomPhotos(query).body()

    private fun buildOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = if(BuildConfig.DEBUG){ //DEBUG모드이면 모든 로그를 찍고
                        HttpLoggingInterceptor.Level.BODY
                    }else{//그렇지 않으면 로그를 찍지 않는다.
                        HttpLoggingInterceptor.Level.NONE
                    }
                }
            ).build()
}