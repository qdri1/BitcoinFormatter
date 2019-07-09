package alimapps.kz.bitcoinformatter.viewmodel

import alimapps.kz.bitcoinformatter.classes.TransactionItemData
import alimapps.kz.bitcoinformatter.model.ApiService
import alimapps.kz.bitcoinformatter.util.GsonFormatter
import alimapps.kz.bitcoinformatter.util.TFormatter
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.json.JSONArray
import org.json.JSONObject
import java.math.RoundingMode
import java.text.DecimalFormat

class SalesViewModel : ViewModel() {

    private val apiService by lazy {
        ApiService.create(ApiService.URL_BITSTAMP)
    }
    private val compositeDisposable = CompositeDisposable()

    val transactions = ObservableArrayList<TransactionItemData>()
    val progressBarVisible = ObservableBoolean()

    fun init() {
        loadTransactions()
    }

    private fun loadTransactions() {
        progressBarVisible.set(true)
        compositeDisposable.add(
            apiService.getTransactions()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { result ->
                        transactions.addAll(getTransactionList(result.string()))
                        progressBarVisible.set(false)
                    },
                    { error ->
                        println("###$error")
                        progressBarVisible.set(false)
                    }
                )
        )
    }

    private fun getTransactionList(json: String): List<TransactionItemData> {
        val transactionList = ArrayList<TransactionItemData>()
        val array = JSONArray(json)
        for (i in 0 until array.length()) {
            val mObj = array.getJSONObject(i)
            val item = GsonFormatter.getTransactionItemFromJson(mObj.toString())

            val price = item.price.toDouble()
            val amount = item.amount.toDouble()
            val exchangeRate = (1.0 * price) / amount
            val df = DecimalFormat("#.##")
            df.roundingMode = RoundingMode.CEILING
            item.exchangeRate = df.format(exchangeRate)

            transactionList.add(item)
        }
        return transactionList
    }

    override fun onCleared() {
        super.onCleared()
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
    }

}