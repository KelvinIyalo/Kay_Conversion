package com.example.abokifx.viewModel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.provider.ContactsContract
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.abokifx.di.FXApplication
import com.example.abokifx.modules.Tryal
import com.example.abokifx.repository.Repository
import com.example.abokifx.utilss.Resource
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

class FXViewModel @ViewModelInject constructor(private val app: Application,private val repository: Repository): AndroidViewModel(app)  {
    val currencyValue: MutableLiveData<Resource<Tryal>> = MutableLiveData()

    fun getCurrency(convertedFrom:String,convertedTo:String) = viewModelScope.launch {
        currencyValue.postValue(Resource.Loading())
        safeConversion(convertedFrom,convertedTo)
    }


    private fun handleConversion(response: Response<Tryal>):Resource<Tryal>{
        if (response.isSuccessful){
            response.body().let {   ResultResponse ->
                return Resource.Success(ResultResponse)

            }
        }
        return Resource.Error(response.message(),data = null)
    }


    suspend fun safeConversion(convertedFrom:String,convertedTo:String){
        currencyValue.postValue(Resource.Loading())
        try {
            if (isInternetAvailable()) {
                val breakingResponse = repository.getCurrency(convertedFrom,convertedTo)
                currencyValue.postValue(handleConversion(breakingResponse))
            }else{
                currencyValue.postValue(Resource.Error("No Internet Connection", null))
            }

        }catch (t:Throwable){
            when(t){
                is IOException -> currencyValue.postValue(Resource.Error("Network Failure", null))
                else -> currencyValue.postValue(Resource.Error("Conversion Error:$t", null))
            }

        }

    }
    fun isInternetAvailable():Boolean{

        val connectivityManager = getApplication<FXApplication>().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
            return when{
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false

            }

        } else{
            connectivityManager.activeNetworkInfo?.run {
                return when(type){
                    ConnectivityManager.TYPE_WIFI -> true
                    ContactsContract.CommonDataKinds.Email.TYPE_MOBILE -> true
                    ConnectivityManager.TYPE_ETHERNET -> true
                    else -> false

                }
            }
        }
        return false

    }
}