package com.example.bl.networkRequest

import com.example.bl.networkRequest.model.ResponseRequest
import com.itzik.common.datamodels.ResponseData
import retrofit2.Call
import retrofit2.http.GET




interface APIItemInterface {

    @GET("/3/discover/movie?api_key=0789b0e525562b48383c6d5bded33741")
    fun doGetListItems(): Call<ResponseData>
}