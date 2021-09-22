package com.klim.us_stock.ui

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.klim.us_stock.App
import com.klim.windowsmanager.WindowsContainerActivity
import com.klim.windowsmanager.WindowsKeeper
import com.klim.windowsmanager.views.WindowsContainer
import com.klim.us_stock.R
import com.klim.us_stock.databinding.ActivityMainBinding
import com.klim.us_stock.ui.windows.MainActivityViewModel
import com.klim.us_stock.ui.windows.home.SymbolViewModel
import com.klim.us_stock.ui.windows.search.SearchFragment
import com.klim.us_stock.ui.windows.symbol_details.SymbolDetailsFragment
import dagger.android.AndroidInjection
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.*
import javax.inject.Inject

class MainActivity : AppCompatActivity(), WindowsContainerActivity {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var vm: MainActivityViewModel

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as App).componentsProvider.getMainActivityComponent().inject(this)
        super.onCreate(savedInstanceState)

        vm = ViewModelProvider(this, viewModelFactory).get(MainActivityViewModel::class.java)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.appBarMain.toolbar)
        vm.init()

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_company_list, R.id.nav_settings, R.id.nav_info
            ), binding.drawerLayout
        )
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.navView.setupWithNavController(navController)

        binding.appBarMain.wcWindowsContainer.windowsKeeper = WindowsKeeper(this@MainActivity)
        binding.appBarMain.wcWindowsContainer.windowCloseListener = ::windowsClose
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_search -> {
                startWindow(SearchFragment.newInstance(Bundle()), false)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun startWindow(fragment: Fragment, isItBase: Boolean) {
        binding.appBarMain.wcWindowsContainer.startWindow(fragment, isItBase)
        binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
    }

    override fun getActivityViewContainer(): WindowsContainer = binding.appBarMain.wcWindowsContainer

    private fun windowsClose() {
        if (binding.appBarMain.wcWindowsContainer.windowsKeeper.getTopWindowOrNull() == null) {
            binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
        }
    }

    override fun getContext(): Context {
        return this
    }

    override fun onBackPressed() {
        if (binding.appBarMain.wcWindowsContainer.onBackPressed()) {
            return
        }
        super.onBackPressed()
    }

    override fun onResume() {
        super.onResume()
        //just for test
//        GlobalScope.launch(Dispatchers.Main) {
//            delay(100)
//            val args = Bundle().apply {
//                putString(SymbolDetailsFragment.SYMBOL, "TSLA")
//            }
//            startWindow(SymbolDetailsFragment.newInstance(args))
//        }
    }

    override fun onDestroy() {
        super.onDestroy()
        (application as App).componentsProvider.destroyMainActivityComponent()
    }
}