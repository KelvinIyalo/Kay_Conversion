package com.example.abokifx.modules


import com.google.gson.annotations.SerializedName

data class Tryal(
    @SerializedName("info")
    val info: Info?,
    @SerializedName("query")
    val query: Query?,
    @SerializedName("result")
    val convertedResult: Double
)