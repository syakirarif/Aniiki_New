package com.syakirarif.aniiki.apiservice.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import java.net.InetSocketAddress
import java.net.Socket
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NoConnectionInterceptor @Inject constructor(private val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        return if (!isOnline()) {
            throw NoConnectivityException()
        }
//        else if (!isInternetAvailable()) {
//            throw NoInternetException()
//        }
        else {
            chain.proceed(chain.request())
        }
    }

    private fun isConnectionOn(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork
            val connection = connectivityManager.getNetworkCapabilities(network)
            return connection != null && (
                    connection.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                            connection.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR))
        } else {
            val activeNetwork = connectivityManager.activeNetworkInfo
            if (activeNetwork != null) {
                return (activeNetwork.type == ConnectivityManager.TYPE_WIFI ||
                        activeNetwork.type == ConnectivityManager.TYPE_MOBILE)
            }
            return false
        }
    }

    private fun isOnline(): Boolean {
        val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M)
            manager.getNetworkCapabilities(manager.activeNetwork)?.let {
                it.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                        it.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                        it.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) ||
                        it.hasTransport(NetworkCapabilities.TRANSPORT_VPN)
            } ?: false
        else
            @Suppress("DEPRECATION")
            manager.activeNetworkInfo?.isConnectedOrConnecting == true
    }

    private fun isOnline2(): Boolean {
        if (context == null) return false
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                        return true
                    }

                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                        return true
                    }

                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                        return true
                    }

                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_VPN) -> {
                        return true
                    }
                }
            }
        } else {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                return true
            }
        }
        return false
    }


    private fun isInternetAvailable(): Boolean {
        return try {
            val timeoutMs = 1500
            val sock = Socket()
            val sockaddr = InetSocketAddress("8.8.8.8", 53)

            sock.connect(sockaddr, timeoutMs)
            sock.close()

            true
        } catch (e: IOException) {
            debugPrint("isInternetAvailable()", e.message)
            false
        }

    }

    class NoConnectivityException : IOException() {
        override val message: String
            get() = "Tidak dapat terhubung ke internet, mohon cek koneksi WiFi atau Paket Data anda."
    }

    class NoInternetException() : IOException() {
        override val message: String
            get() = "No internet available, please check your connected WIFi or Data"
    }
}