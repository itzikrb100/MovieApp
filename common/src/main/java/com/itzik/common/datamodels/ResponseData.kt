package com.itzik.common.datamodels

import com.google.gson.annotations.SerializedName
import com.itzik.interfaces.datamodels.MovieData

data class ResponseData(@SerializedName("results")var result: ArrayList<MovieData> = ArrayList(),
                        @SerializedName("total_results")var totalResult: String = "")