package alimapps.kz.bitcoinformatter.model

import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Link {

    @GET("v1/bpi/currentprice/{code}.json")
    fun getInfo(@Path("code") code: String): Observable<ResponseBody>

    @GET("v1/bpi/historical/close.json")
    fun getHistorical(@Query("currency") currency: String, @Query("start") start: String, @Query("end") end: String): Observable<ResponseBody>

    @GET("api/transactions/")
    fun getTransactions(): Observable<ResponseBody>

}