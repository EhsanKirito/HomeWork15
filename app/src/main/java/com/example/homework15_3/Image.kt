package com.example.homework15_3

import com.google.gson.annotations.SerializedName

data class Image(
    @SerializedName("id") val id: String,
    @SerializedName("author") val author: String,
    @SerializedName("url") val url: String,
    @SerializedName("download_url") val downloadUrl: String
)