package com.itzik.interfaces.datamodels


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
data class MovieData(
    @SerializedName("original_title") var title: String = "",
    @SerializedName("overview") var desc: String = "",
    @SerializedName("vote_average") var rate: String = "",
    @SerializedName("poster_path") var posterPath: String = "",
    @SerializedName("release_date") var date: String = "",
    @SerializedName("id") var movieId: String = ""
): Parcelable

