package com.jcy.ch22_searchfreeimage

import com.jcy.ch22_searchfreeimage.data.models.PhotoResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface UnsplashApiService {
    @GET("photos/random?"+
            "client_id=${BuildConfig.UNSPLASH_ACCESS_KEY}" +
            "&count=30"
    )
    suspend fun getRandomPhotos(
        //null이면 랜덤이미지 추출, query가 있으면 query에 대한 이미지들 추출
        @Query("query") query: String?
    ): Response<List<PhotoResponse>>
}