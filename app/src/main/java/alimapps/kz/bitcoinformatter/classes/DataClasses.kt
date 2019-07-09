package alimapps.kz.bitcoinformatter.classes

class Data {

    var bpi: Bpi? = null

    var time: Time? = null

    override fun toString(): String {
        return "Data(bpi=$bpi, time=$time)"
    }

}

class Bpi {
    var USD: USD? = null

    var KZT: KZT? = null

    var EUR: EUR? = null

    override fun toString(): String {
        return "Bpi [USD = $USD, KZT = $KZT, EUR = $EUR]"
    }
}

class EUR {

    var code: String? = null

    var symbol: String? = null

    var rate: String? = null

    var description: String? = null

    var rate_float: String? = null

    override fun toString(): String {
        return "EUR [symbol = $symbol, rate_float = $rate_float, code = $code, rate = $rate, description = $description]"
    }
}

class KZT {
    var code: String? = null

    var symbol: String? = null

    var rate: String? = null

    var description: String? = null

    var rate_float: String? = null

    override fun toString(): String {
        return "KZT [symbol = $symbol, rate_float = $rate_float, code = $code, rate = $rate, description = $description]"
    }
}

class USD {
    var code: String? = null

    var symbol: String? = null

    var rate: String? = null

    var description: String? = null

    var rate_float: String? = null

    override fun toString(): String {
        return "USD [symbol = $symbol, rate_float = $rate_float, code = $code, rate = $rate, description = $description]"
    }
}

class Time {
    var updateduk: String? = null

    var updatedISO: String? = null

    var updated: String? = null

    override fun toString(): String {
        return "Time [updateduk = $updateduk, updatedISO = $updatedISO, updated = $updated]"
    }
}



