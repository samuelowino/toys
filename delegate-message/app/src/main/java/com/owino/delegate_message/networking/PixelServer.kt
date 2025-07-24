package com.owino.delegate_message.networking;
import android.graphics.Bitmap
import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import kotlin.random.Random
import androidx.core.graphics.createBitmap
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
        stressTestIntermediateOp()
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
    /**
     * Performs a memory and CPU-intensive stress test operation.
     *
     * This function simulates a heavy computational workload by executing three major operations:
     *
     * 1. **Matrix Multiplication**: Multiplies two randomly generated 500x500 float matrices.
     *    This step is highly CPU-intensive due to the nested loops and large number of operations.
     *
     * 2. **Bitmap Allocation**: Creates ten 2048x2048 ARGB_8888 bitmaps and stores them in memory.
     *    This puts significant pressure on the heap and tests memory allocation limits.
     *
     * 3. **Large List Sorting**: Generates and sorts a list of 10 million random integers,
     *    stressing both memory and CPU, particularly the garbage collector.
     *
     * Log messages are used to indicate the completion of each major operation.
     *
     * @throws OutOfMemoryError if the system is unable to allocate required memory,
     *         especially on low-memory devices or without enabling `largeHeap`.
     *
     * Note: This function is intended for internal performance testing only
     * and should not be used in production environments.
     */
    private fun stressTestIntermediateOp(){
        val matrixSize = 500 // 500 x 500 matrix = 250,000 floats
        val matrixA = Array(matrixSize) { FloatArray(matrixSize) { Random.nextFloat() } }
        val matrixB = Array(matrixSize) { FloatArray(matrixSize) { Random.nextFloat() } }
        val result = Array(matrixSize) { FloatArray(matrixSize) }

        // Matrix multiplication
        for (i in 0 until matrixSize) {
            for (j in 0 until matrixSize) {
                var sum = 0f
                for (k in 0 until matrixSize) {
                    sum += matrixA[i][k] * matrixB[k][j]
                }
                result[i][j] = sum
            }
        }

        Log.w("PerformanceTest", "Matrix multiplication done")

        // Allocate large bitmaps in memory (e.g., 10 images)
        val bitmaps = mutableListOf<Bitmap>()
        for (i in 0 until 10) {
            val bitmap = createBitmap(2048, 2048)
            bitmaps.add(bitmap)
        }

        Log.w("PerformanceTest", "Bitmap allocations done")

        // Memory + CPU sort operation
        val bigList = MutableList(10_000_000) { Random.nextInt() }
        bigList.sort()

        Log.w("PerformanceTest", "Sorting done")
    }
}
