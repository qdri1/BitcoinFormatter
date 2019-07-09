package alimapps.kz.bitcoinformatter.dialog

import alimapps.kz.bitcoinformatter.R
import alimapps.kz.bitcoinformatter.classes.TransactionItemData
import alimapps.kz.bitcoinformatter.util.TFormatter
import android.content.Context
import androidx.appcompat.app.AlertDialog
import java.lang.StringBuilder

object BaseDialogs {

    fun transactionDialog(context: Context, item: TransactionItemData): AlertDialog {
        val builder = AlertDialog.Builder(context)
        val title = if (item.type == 0) context.getString(R.string.type_buy) else context.getString(R.string.type_sell)
        builder.setTitle(title)

        val labelId = context.getString(R.string.label_id) + ": "
        val labelDate = context.getString(R.string.label_date) + ": "
        val labelQuantity = context.getString(R.string.label_quantity) + ": "
        val labelBtc = " " + context.getString(R.string.btc)
        val labelSum = context.getString(R.string.label_sum) + ": "
        val labelUsd = " " + context.getString(R.string.usd)
        val labelExchangeRate = context.getString(R.string.label_exchange_rate) + ": "

        val stringBuilder = StringBuilder()
        stringBuilder.append(labelId).append(item.tid).append("\n")
            .append(labelDate).append(TFormatter.asDate(item.date.toLong())).append("\n")
            .append(labelQuantity).append(item.amount).append(labelBtc).append("\n")
            .append(labelSum).append(item.price).append(labelUsd).append("\n")
            .append(labelExchangeRate).append(item.exchangeRate).append(labelUsd)
        builder.setMessage(stringBuilder.toString())
        builder.setPositiveButton(R.string.action_close, null)
        return builder.create()
    }

}