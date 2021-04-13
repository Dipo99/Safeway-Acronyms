package com.acronyms.api

import com.acronyms.model.AcronymResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

internal interface ApiInterface {

    /**
     *
     */
    @GET("dictionary.py")
    fun getDetails(@Query("sf") sf: String): Call<List<AcronymResponse>>
}