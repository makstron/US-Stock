package com.klim.stock.presentation.main

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.ui.AppBarConfiguration
import com.klim.stock.dependencyinjection.ApplicationContextProvider
import com.klim.stock.dependencyinjection.view_model.ViewModelFactory
import com.klim.stock.dicore.Dependency
import com.klim.stock.dicore.DependencyContainer
import com.klim.stock.presentation.main.di.MainActivityComponent
import com.klim.stock.presentation.main.ui.MainScreen
import com.klim.stock.resources.AppTheme
import com.klim.windowsmanager.WindowsContainerActivity
import com.klim.windowsmanager.views.WindowsContainer
import dagger.Lazy
import javax.inject.Inject

class MainActivity : AppCompatActivity(), WindowsContainerActivity {

    @Inject
    lateinit var viewModelFactory: Lazy<ViewModelFactory>
    val viewModel: MainActivityViewModel by viewModels { viewModelFactory.get() }

    private lateinit var appBarConfiguration: AppBarConfiguration


    override fun onCreate(savedInstanceState: Bundle?) {
        inject()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                MainScreen(viewModel)
            }
        }

//        binding.appBarMain.wcWindowsContainer.windowsKeeper = WindowsKeeper(this@MainActivity)
//        binding.appBarMain.wcWindowsContainer.windowCloseListener = ::windowsClose
//        binding.appBarMain.wcWindowsContainer.windowOpenListener = ::windowsOpen
    }

    private fun inject() {
        val component = MainActivityComponent.Initializer
            .init(
                application as ApplicationContextProvider,
                findDependencies()
            )
        component.inject(this)
    }

//    override fun onSupportNavigateUp(): Boolean {
//        val navController = findNavController(R.id.nav_host_fragment_content_main)
//        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
//    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when (item.itemId) {
//            R.id.action_search -> {
//                startWindow(SearchFragment.newInstance(Bundle()), false)
//            }
//        }
        return super.onOptionsItemSelected(item)
    }

    override fun startWindow(fragment: Fragment, isItBase: Boolean) {
//        binding.appBarMain.wcWindowsContainer.startWindow(fragment, isItBase)
//        binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
    }

    override fun getActivityViewContainer(): WindowsContainer = TODO() //binding.appBarMain.wcWindowsContainer

    private fun windowsClose() {
//        if (binding.appBarMain.wcWindowsContainer.windowsKeeper.getTopWindowOrNull() == null) {
//            binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
//            resumeFragment()
//        }
    }

    private fun resumeFragment() {
//        supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_main)?.childFragmentManager?.let { fragmentManager ->
//            val fragmentTransaction = fragmentManager.beginTransaction()
//            fragmentTransaction.setMaxLifecycle(fragmentManager.fragments.first(), Lifecycle.State.RESUMED)
//            fragmentTransaction.commit()
//        }
    }

    private fun windowsOpen() {
//        if (binding.appBarMain.wcWindowsContainer.windowsKeeper.getStackSize() == 1) {
//            pauseFragment()
//        }
    }

    private fun pauseFragment() {
//        supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_main)?.childFragmentManager?.let { fragmentManager ->
//            val fragmentTransaction = fragmentManager.beginTransaction()
//            fragmentTransaction.setMaxLifecycle(fragmentManager.fragments[0], Lifecycle.State.STARTED)
//            fragmentTransaction.commit()
//        }
    }

    override fun getContext(): Context {
        return this
    }

    override fun onBackPressed() {
//        if (binding.appBarMain.wcWindowsContainer.onBackPressed()) {
//            return
//        }
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

}

inline fun <reified D : Dependency> Activity.findDependencies(): D {
        val dependenciesClass = D::class.java
        return (application as DependencyContainer)
            .dependenciesMap[dependenciesClass] as D?
            ?: throw IllegalStateException("No $dependenciesClass provided to DependencyMap ")
    }