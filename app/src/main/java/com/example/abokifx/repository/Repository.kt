package com.example.abokifx.repository

import com.example.abokifx.modules.Tryal
import com.example.abokifx.networkcall.FXCall
import retrofit2.Response
import javax.inject.Inject

class Repository@Inject constructor(val networkCall: FXCall) {
    suspend fun getCurrency(convertedFrom:String,convertedTo:String): Response<Tryal> {
        return networkCall.currencyConversion(convertedFrom,convertedTo)
    }
}