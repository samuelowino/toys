package com.owino.delegate_message.networking
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface PhotoApi {
    @GET("/photos/{photoId}")
    fun getPhoto(@Path("photoId") photoId: Int): Call<Map<String, String>>
}