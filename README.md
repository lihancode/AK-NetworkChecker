# AK-NetworkChecker
Android kotlin NetworkChecker


# AndroidManifest.xml
```
    <uses-permission android:name="android.permission.INTERNET"/>
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
```

# MainActivity.kt
```
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
    
```

# Result
<img src="https://github.com/lihancode/AK-NetworkChecker/blob/master/moblie.png" hieght=600 width=200></img>
<img src="https://github.com/lihancode/AK-NetworkChecker/blob/master/wifi.png" hieght=600 width=200></img>
<img src="https://github.com/lihancode/AK-NetworkChecker/blob/master/none.png" hieght=600 width=200></img>
