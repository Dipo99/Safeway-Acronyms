package com.acronyms.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object ApiClient {
    private var retrofit: Retrofit? = null
    private const val URL = "http://www.nactem.ac.uk/software/acromine/"

    /**
     * Setup Retrofit API client
     * @return retrofit object to make API call
     */
    val client: Retrofit?
        get() {
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .baseUrl(URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit
        }
}