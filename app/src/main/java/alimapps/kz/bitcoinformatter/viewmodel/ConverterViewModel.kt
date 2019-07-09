package alimapps.kz.bitcoinformatter.viewmodel

import alimapps.kz.bitcoinformatter.classes.Consts
import alimapps.kz.bitcoinformatter.classes.ConverterData
import alimapps.kz.bitcoinformatter.classes.Data
import alimapps.kz.bitcoinformatter.model.ApiService
import alimapps.kz.bitcoinformatter.util.GsonFormatter
import alimapps.kz.bitcoinformatter.util.NumberFormatter
import alimapps.kz.bitcoinformatter.util.TFormatter
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ConverterViewModel : ViewModel() {

    private val apiService by lazy {
        ApiService.create(ApiService.URL_COINDESK)
    }

    private val compositeDisposable = CompositeDisposable()
    var currentCode = Consts.CODE_KZT
    val converterData = ConverterData()

    fun init() {
        loadCurrentBitcoinPrice(currentCode)
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
                        val price = getPrice(code, data)
                        converterData.price = price
                        converterData.symbol.set(TFormatter.formatSymbol(code))
                    },
                    { error ->
                        println("###$error")
                    }
                )
        )
    }

    private fun getPrice(code: String, data: Data): Double {
        val rate = when (code) {
            Consts.CODE_KZT -> data.bpi!!.KZT!!.rate!!
            Consts.CODE_USD -> data.bpi!!.USD!!.rate!!
            Consts.CODE_EUR -> data.bpi!!.EUR!!.rate!!
            else -> "0"
        }

        val numberFormatter = NumberFormatter()
        return numberFormatter.toNumber(rate)
    }

    fun onRadioButtonClick(code: String) {
        loadCurrentBitcoinPrice(code)
        currentCode = code
    }

    override fun onCleared() {
        super.onCleared()
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
    }

}