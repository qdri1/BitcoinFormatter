package alimapps.kz.bitcoinformatter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        val navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        navController.graph = navController.navInflater.inflate(R.navigation.nav_graph)

        NavigationUI.setupWithNavController(navigation_view, navController)
        navigation_view.setOnNavigationItemSelectedListener {
            viewModel.switchPage(it.itemId)
            true
        }

        viewModel.transitionObservable.observe(this, Observer { page ->
            if (page.putInBackStack) {
                println("###With history")
                navController.navigate(page.pageId)
            } else {
                println("###Without history")
                navController.popBackStack()

                val navOptions = NavOptions.Builder().setPopUpTo(page.pageId, false).build()
                navController.navigate(page.pageId, null, navOptions)
            }
        })
    }

}
