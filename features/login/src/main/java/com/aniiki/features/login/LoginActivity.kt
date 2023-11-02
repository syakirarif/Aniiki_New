package com.aniiki.features.login

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.syakirarif.aniiki.compose.theme.BidayahTheme
import com.syakirarif.aniiki.core.utils.Status
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class LoginActivity : ComponentActivity() {

    private val viewModel: LoginVM by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fetchData()
//        setContent {
//            BidayahTheme {
//                fetchData()
//            }
//        }
    }

    private fun fetchData() {
        viewModel.getAnimeAsLiveData()
            .observe(this) { result ->
                result?.let {
                    when (it.status) {
                        Status.SUCCESS -> {
                            it.data?.let { data ->
                                setContent {
                                    BidayahTheme {
                                        MainAnimeLayout(posters = data, selectPoster = {
                                            Timber.e("MAL ID => $it")
                                        })
                                    }
                                }
                            } ?: run {
                                setContent {
                                    BidayahTheme {
                                        NoDataScreen(message = it.message.toString())
                                    }
                                }
                            }
                        }

                        Status.LOADING -> {
                            setContent {
                                BidayahTheme {
                                    LoadingScreen()
                                }
                            }
                        }

                        else -> {
                            setContent {
                                BidayahTheme {
                                    NoDataScreen(message = it.message.toString())
                                }
                            }
                        }
                    }
                } ?: run {
                    setContent {
                        BidayahTheme {
                            NoDataScreen(message = result.message.toString())
                        }
                    }
                }
            }
    }
}