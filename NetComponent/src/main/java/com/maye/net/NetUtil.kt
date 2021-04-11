package com.maye.net

import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object NetUtil{

    private val netServers = hashMapOf<String, Any?>()

    @JvmStatic
    fun <T>createApi(api: Class<T>): T {
        val classObject = api::class.java
        var server = netServers[classObject.name]
        if (server != null) {
            return server as T
        } else {
            val retrofit: Retrofit = Retrofit.Builder()
                    .baseUrl(NetConst.login)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .build()
            server = retrofit.create(api)
            netServers[classObject.name] = server
            return server
        }
    }


}