package alimapps.kz.bitcoinformatter.adapter

import alimapps.kz.bitcoinformatter.R
import alimapps.kz.bitcoinformatter.util.NumberFormatter
import alimapps.kz.bitcoinformatter.util.TFormatter
import android.widget.TextView
import androidx.databinding.BindingAdapter

object BindingAdapters {

    @JvmStatic
    @BindingAdapter("btc:asDate")
    fun setText(view: TextView, value: String) {
        view.text = TFormatter.asDate(value.toLong())
    }

    @JvmStatic
    @BindingAdapter(value = ["btc:value", "btc:symbol"], requireAll = false)
    fun setText(view: TextView, value: Double, symbol: String?) {
        val numberFormatter = NumberFormatter()
        val str = "${numberFormatter.toString(value)} ${symbol ?: ""}"
        view.text = str
    }

    @JvmStatic
    @BindingAdapter("btc:textBtc")
    fun setTextBtc(view: TextView, text: String) {
        val str = text + " " + view.context.getString(R.string.btc)
        view.text = str
    }

}