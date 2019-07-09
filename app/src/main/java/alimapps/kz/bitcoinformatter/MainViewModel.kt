package alimapps.kz.bitcoinformatter

import alimapps.kz.bitcoinformatter.classes.Page
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    private var currentMenu: Int = R.id.bitcoin
    val transitionObservable = MutableLiveData<Page>()

    fun switchPage(selectedMenu: Int) {
        if (selectedMenu != currentMenu) {
            when (selectedMenu) {
                R.id.bitcoin -> transitionObservable.value = Page(R.id.infoFragment)
                R.id.transaction -> transitionObservable.value = Page(R.id.salesFragment)
                R.id.converter -> transitionObservable.value = Page(R.id.converterFragment)
            }
            currentMenu = selectedMenu
        }
    }

}