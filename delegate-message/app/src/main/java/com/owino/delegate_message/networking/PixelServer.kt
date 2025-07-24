package com.owino.delegate_message.networking;
import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
class PixelServer {
    private fun getPixelServer(apiKey: String): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient(apiKey))
            .addConverterFactory(JacksonConverterFactory.create())
            .baseUrl("https://api.pexels.com/v1/")
            .build()
    }
    private fun okHttpClient(apiKey: String): OkHttpClient {
        return OkHttpClient.Builder()
            .protocols(listOf(Protocol.HTTP_1_1))
            .addInterceptor(AuthInterceptor(apiKey))
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS))
            .build()
    }
    fun downloadPhoto(apiKey:String = "missing_key", photoId: Int, callback: (response: Response<Map<String,String>>?, throwable: Throwable?) -> Unit){
        val retrofit = getPixelServer(apiKey)
        val caller = retrofit.create(PhotoApi::class.java)
        caller.getPhoto(photoId).enqueue(object: Callback<Map<String,String>> {
            override fun onResponse(call: Call<Map<String,String>>,response: Response<Map<String, String>>){
                Log.w("DownloadPhoto","Download photo response $response")
                callback.invoke(response,null)
            }
            override fun onFailure(call: Call<Map<String,String>>, throwable: Throwable){
                Log.e("DownloadPhoto", "Failed to download photo $throwable")
                callback.invoke(null,throwable)
            }
        })
    }
}
