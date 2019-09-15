package com.lihan.ak_networkchecker

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            cm.registerDefaultNetworkCallback(object : ConnectivityManager.NetworkCallback(){
                override fun onAvailable(network: Network) {
                    super.onAvailable(network)
                    Toast.makeText(this@MainActivity,"onAvailable ${checkNetWork()}",Toast.LENGTH_SHORT).show()
                    runOnUiThread{
                        resultTextView.text = ("${Build.VERSION.SDK_INT} / ${checkNetWork()}")

                    }
                }

                override fun onUnavailable() {
                    super.onUnavailable()
                    Toast.makeText(this@MainActivity,"onAvailable ${checkNetWork()}",Toast.LENGTH_SHORT).show()
                    runOnUiThread{
                        resultTextView.text = ("${Build.VERSION.SDK_INT} / ${checkNetWork()}")

                    }
                }

                override fun onLost(network: Network) {
                    super.onLost(network)
                    Toast.makeText(this@MainActivity,"onAvailable ${checkNetWork()}",Toast.LENGTH_SHORT).show()
                    runOnUiThread{
                        resultTextView.text = ("${Build.VERSION.SDK_INT} / ${checkNetWork()}")

                    }

                }
            })
        }
    }

    private fun checkNetWork() : String{

        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        cm?.run {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                getNetworkCapabilities(activeNetwork)?.run {
                 return when{
                        hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> "SDK>M WIFI"
                        hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> "SDK>M MOBILE"
                        else-> "NONE"
                    }
                }
            }else{
                @Suppress("DEPRECATION")
                return when (activeNetworkInfo.type){
                    ConnectivityManager.TYPE_WIFI -> "SDK<M WIFI"
                    ConnectivityManager.TYPE_MOBILE -> "SDK<M MOBILE"
                    else -> "NONE"
                }
            }
        }
        return "NONE"


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
