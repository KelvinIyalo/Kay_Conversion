package com.example.abokifx.di

import com.example.abokifx.networkcall.FXCall
import com.example.abokifx.repository.Repository
import com.example.abokifx.utilss.Utills.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object FXModule {
    @Provides
    @Singleton
    fun provideNetworkCall() = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(FXCall::class.java)

    @Provides
    @Singleton
    fun provideRepository(networkCall: FXCall) = Repository(networkCall)
}