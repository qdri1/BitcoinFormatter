package alimapps.kz.bitcoinformatter.model

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiService {

    const val URL_COINDESK = "https://api.coindesk.com/"
    const val URL_BITSTAMP = "https://www.bitstamp.net/"

    fun create(url: String): Link {

        val client = OkHttpClient.Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .build()

        val retrofit = Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .baseUrl(url)
            .build()

        return retrofit.create(Link::class.java)
    }

}