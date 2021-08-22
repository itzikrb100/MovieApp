package com.example.uilib.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(var title: String = "", var desc: String = "", var posterPath: String = "",
                 var rate: String = "", var date: String = "", var movieId: String = ""): Parcelable