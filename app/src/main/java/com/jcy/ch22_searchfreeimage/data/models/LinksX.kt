package com.jcy.ch22_searchfreeimage.data.models


import com.google.gson.annotations.SerializedName

data class LinksX(
    @SerializedName("html")
    val html: String?,
    @SerializedName("likes")
    val likes: String?,
    @SerializedName("photos")
    val photos: String?,
    @SerializedName("portfolio")
    val portfolio: String?,
    @SerializedName("self")
    val self: String?
)