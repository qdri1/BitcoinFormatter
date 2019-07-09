package alimapps.kz.bitcoinformatter.classes

class Page(val pageId: Int, val putInBackStack: Boolean = false)

class Historical(val date: String, val value: Double)

class DatePeriod(val startDate: String, val endDate: String)

class TransactionItemData(
    val date: String,
    val tid: Long,
    val price: String,
    val type: Int,
    val amount: String,
    var exchangeRate: String = ""
)