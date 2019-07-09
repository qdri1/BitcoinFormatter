package alimapps.kz.bitcoinformatter.util

import alimapps.kz.bitcoinformatter.classes.Data
import alimapps.kz.bitcoinformatter.classes.TransactionItemData
import com.google.gson.GsonBuilder

object GsonFormatter {

    @JvmStatic
    fun getDataFromJson(json: String): Data {
        val gson = GsonBuilder().create()
        return gson.fromJson(json, Data::class.java)
    }

    @JvmStatic
    fun getTransactionItemFromJson(json: String): TransactionItemData {
        val gson = GsonBuilder().create()
        return gson.fromJson(json, TransactionItemData::class.java)
    }

}