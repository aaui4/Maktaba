package com.ElOuedUniv.maktaba

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.ElOuedUniv.maktaba.data.local.DataStoreManager
import com.ElOuedUniv.maktaba.presentation.navigation.NavGraph
import com.ElOuedUniv.maktaba.presentation.theme.MaktabaTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var dataStoreManager: DataStoreManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaktabaTheme {
                NavGraph(
                    dataStoreManager = dataStoreManager
                )
            }
        }
    }
}