package com.example.abokifx.networkcall

import com.example.abokifx.modules.Tryal
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface FXCall {
    @GET("convert")
    suspend fun currencyConversion(
        @Query("from")FromCurrency:String,
        @Query("to")ToCurrency:String
    ): Response<Tryal>
}