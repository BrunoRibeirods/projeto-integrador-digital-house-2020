package com.filmly.app.ui


import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.filmly.app.R
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity() : AppCompatActivity() {

    var applicationContext_: Context? = null
    var defaultHandler: Thread.UncaughtExceptionHandler? = null
    var exceptionHandler: Thread.UncaughtExceptionHandler? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (defaultHandler == null) {
            defaultHandler = Thread.getDefaultUncaughtExceptionHandler()
        }

        if (applicationContext == null) {
            applicationContext_ = applicationContext
        }

        if (exceptionHandler == null) {
            exceptionHandler = Thread.UncaughtExceptionHandler { paramThread, paramThrowable ->
                Log.e("Uncaught Exception", paramThrowable.message!!)
                paramThrowable.printStackTrace()
                defaultHandler?.uncaughtException(paramThread, paramThrowable)
            }
            Thread.setDefaultUncaughtExceptionHandler(exceptionHandler)
        }
        setNavigationController()

        btn_reconect.setOnClickListener {
            onResume()
            Toast.makeText(this, "Reconectando...", Toast.LENGTH_SHORT).show()
        }
    }

    fun setNavigationController() {
        val navController = findNavController(R.id.navHostFragment_MainActivity)
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            if(arrayListOf(R.id.homeFragment, R.id.searchFragment).contains(destination.id)){
                isOnline(this)
            }
        }
        isOnline(this)
        bottom_navigation.setupWithNavController(navController)

    }

    override fun onResume() {
        super.onResume()
        isOnline(this)
    }

    fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)


        if (capabilities != null) {
            if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                setItemOn()
                return true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                setItemOn()
                return true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                setItemOn()
                return true
            }
        }

        setItemOff()
        return false
    }

    private fun setItemOff(){
        bottom_navigation.menu.findItem(R.id.homeFragment).isEnabled = false
        bottom_navigation.menu.findItem(R.id.searchFragment).isEnabled = false

        btn_reconect.visibility = View.VISIBLE


        findNavController(R.id.navHostFragment_MainActivity).navigate(R.id.yourListsFragment)
    }

    private fun setItemOn(){
        bottom_navigation.menu.findItem(R.id.homeFragment).isEnabled = true
        bottom_navigation.menu.findItem(R.id.searchFragment).isEnabled = true

        btn_reconect.visibility = View.GONE


    }

    override fun onBackPressed() {
        super.onBackPressed()
        if(!isOnline(this)){
            this.finishAffinity();
        }
    }

}