package alimapps.kz.bitcoinformatter.viewmodel

import alimapps.kz.bitcoinformatter.classes.Consts
import alimapps.kz.bitcoinformatter.classes.Data
import alimapps.kz.bitcoinformatter.classes.Historical
import alimapps.kz.bitcoinformatter.model.ApiService
import alimapps.kz.bitcoinformatter.util.GsonFormatter
import alimapps.kz.bitcoinformatter.util.TFormatter
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.json.JSONObject

class InfoViewModel : ViewModel() {

    private val apiService by lazy {
        ApiService.create(ApiService.URL_COINDESK)
    }

    private val compositeDisposable = CompositeDisposable()
    val rateObserver = ObservableField<String>()
    val historicalLiveData = MutableLiveData<MutableList<Historical>>()
    val progressBarVisible = ObservableBoolean()

    private var currentType = TFormatter.TYPE_WEEK
    var currentCode = Consts.CODE_KZT

    fun init() {
        loadCurrentBitcoinPrice(currentCode)
        loadBitcoinHistories(currentType)
    }

    private fun loadCurrentBitcoinPrice(code: String) {
        compositeDisposable.add(
            apiService.getInfo(code)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { result ->
                        val str = result.string()
                        val data = GsonFormatter.getDataFromJson(str)
                        val price = TFormatter.formatPrice(getRate(code, data), code)
                        val rateValue = "$price ($code)"
                        rateObserver.set(rateValue)
                    },
                    { error ->
                        println("###$error")
                    }
                )
        )
    }

    private fun getRate(code: String, data: Data): String {
        return when (code) {
            Consts.CODE_KZT -> data.bpi!!.KZT!!.rate!!
            Consts.CODE_USD -> data.bpi!!.USD!!.rate!!
            Consts.CODE_EUR -> data.bpi!!.EUR!!.rate!!
            else -> ""
        }
    }

    private fun loadBitcoinHistories(dateType: Int) {
        progressBarVisible.set(true)
        val period = TFormatter.getDatePeriod(System.currentTimeMillis(), dateType)
        compositeDisposable.add(
            apiService.getHistorical(currentCode, period.startDate, period.endDate)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { result ->
                        historicalLiveData.value = getHistoryList(result.string())
                        progressBarVisible.set(false)
                    },
                    { error ->
                        println("###$error")
                        progressBarVisible.set(false)
                    }
                )
        )
    }

    private fun getHistoryList(json: String): MutableList<Historical> {
        val obj = JSONObject(json)
        val bpi = obj.getJSONObject("bpi")
        val historyList = ArrayList<Historical>()
        for ((i, key) in bpi.keys().withIndex()) {
            when (currentType) {
                TFormatter.TYPE_WEEK ->
                    historyList.add(Historical(key, bpi.getDouble(key)))

                TFormatter.TYPE_MONTH ->
                    if ((i - 1) % 7 == 0)
                        historyList.add(Historical(key, bpi.getDouble(key)))

                TFormatter.TYPE_YEAR ->
                    if ((i - 1) % 30 == 0)
                        historyList.add(Historical(key, bpi.getDouble(key)))
            }
        }
        return historyList
    }

    fun onRadioButtonClick(code: String) {
        loadCurrentBitcoinPrice(code)
        loadBitcoinHistories(currentType)
        currentCode = code
    }

    fun onRadioButtonClick(dateType: Int) {
        loadBitcoinHistories(dateType)
        currentType = dateType
    }

    override fun onCleared() {
        super.onCleared()
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
    }

}