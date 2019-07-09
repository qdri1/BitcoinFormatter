package alimapps.kz.bitcoinformatter.classes

import alimapps.kz.bitcoinformatter.BR
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.ObservableField

class ConverterData : BaseObservable() {

    val symbol = ObservableField<String>()

    val btcSymbol = "BTC"

    var price = 0.0
        set(value) {
            field = value
            calculateBtcValue()
            calculateMoneyValue()
        }

    @Bindable
    var moneyField = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.moneyField)
            calculateBtcValue()
        }

    @Bindable
    var btcValue = 0.0
        set(value) {
            field = value
            notifyPropertyChanged(BR.btcValue)
        }

    @Bindable
    var btcField = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.btcField)
            calculateMoneyValue()
        }

    @Bindable
    var moneyValue = 0.0
        set(value) {
            field = value
            notifyPropertyChanged(BR.moneyValue)
        }

    private fun calculateBtcValue() {
        btcValue = (1 * getMoneyFieldValue()) / price
    }

    private fun calculateMoneyValue() {
        moneyValue = getBtcFieldValue() * price
    }

    private fun getMoneyFieldValue() = if (moneyField.isBlank()) 0.0 else moneyField.toDouble()

    private fun getBtcFieldValue() = if (btcField.isBlank()) 0.0 else btcField.toDouble()

}