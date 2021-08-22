package com.example.bl.networkRequest

import android.util.Log
import com.example.bl.networkRequest.model.ResponseRequest
import com.example.bl.repository.ResponseEvent
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.itzik.common.datamodels.Definitions.Companion.URL
import com.itzik.common.datamodels.ResponseData
import kotlinx.coroutines.*
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.util.*

class NetworkRequestManager : CoroutineScope by MainScope() {


    private val TAG: String = NetworkRequestManager::class.java.simpleName


    enum class RequestMethods {
        GET {
            override fun getMethod(): String {
                return GET.name
            }

        },
        PUT {
            override fun getMethod(): String {
                return PUT.name
            }
        },
        DELET {
            override fun getMethod(): String {
                return DELET.name
            }

        };

        abstract fun getMethod(): String
    }



    private var client: OkHttpClient

    init {
        Log.d(TAG, "INIT")
        client = OkHttpClient()
    }


    fun cleanup() {
        cancel()
        Log.d(TAG, "cleanup all request")
    }

    fun getMovieRequestNetwork(event: ResponseEvent) {
        launch {
            val response = async(Dispatchers.IO) { requestNetwork() }
            // switch from main thread and wait until receive response
            val resWait = response.await()
            event.onResponse(resWait.data, resWait.codeResponse)
        }
        Log.d(TAG, "getMovieRequestNetwork")
    }


    private fun requestNetwork(): ResponseRequest {
        Log.d(TAG, "requestNetwork:")
        var response = getRequest(URL, RequestMethods.GET.getMethod())
        val turnsType = object : TypeToken<ResponseData>() {}.type
        var responsData: ResponseData =
            Gson().fromJson(response.body()!!.string(), turnsType)
        Log.d(TAG, Arrays.deepToString(responsData.result.toTypedArray()))
        var responseRequest = ResponseRequest(responsData, response.code())
        return responseRequest
    }


    private fun getRequest(url: String, method: String): Response {
        Log.d(TAG, "request")
        Thread.sleep(500)
        val request = Request.Builder()
            .url(url)
            .method(method, null)
            .build()
        val response = client.newCall(request).execute()

        return response

    }

}