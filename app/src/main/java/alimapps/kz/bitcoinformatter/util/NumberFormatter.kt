package alimapps.kz.bitcoinformatter.util

import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.NumberFormat
import java.util.*

class NumberFormatter {

    private var formatter: DecimalFormat = NumberFormat.getCurrencyInstance(Locale.US) as DecimalFormat
    private var groupingSeparator: Char = '\u0000'
    private var decimalSeparator: Char = '\u0000'

    init {
        val currencySymbolFormat = DecimalFormatSymbols.getInstance(Locale.US)
        currencySymbolFormat.currencySymbol = ""
        this.groupingSeparator = currencySymbolFormat.groupingSeparator
        this.decimalSeparator = currencySymbolFormat.decimalSeparator
        this.formatter.decimalFormatSymbols = currencySymbolFormat
        this.formatter.roundingMode = RoundingMode.CEILING
    }

    fun toString(amount: Double): String {
        return this.formatter.format(amount)
    }

    fun toNumber(value: String): Double {
        var amount = value
        return if (amount.isBlank()) {
            0.0
        } else {
            amount = amount.replace(("[^0-9" + this.decimalSeparator + "]").toRegex(), "")
            amount = amount.replace(("[^0-9" + this.groupingSeparator + "]").toRegex(), ".")
            amount.toDouble()
        }
    }

}