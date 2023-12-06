package com.alvine.advancenoteapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.InputChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.alvine.advancenoteapp.presentation.bookmark.BookedMarkViewModel
import com.alvine.advancenoteapp.presentation.detail.DetailAssistedFactory
import com.alvine.advancenoteapp.presentation.home.HomeViewModel
import com.alvine.advancenoteapp.presentation.navigation.NoteNavigation
import com.alvine.advancenoteapp.presentation.navigation.Screens
import com.alvine.advancenoteapp.presentation.navigation.navigateToSingleTop
import com.alvine.advancenoteapp.ui.theme.AdvanceNoteAppTheme
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var assistedFactory: DetailAssistedFactory
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AdvanceNoteAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                    contentColor = MaterialTheme.colorScheme.onSurface

                ) {
                    NoteApp()
                }
            }
        }
    }


    enum class TabScreen {
        Home, BookMark
    }

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun NoteApp() {
        val homeViewModel: HomeViewModel = viewModel()
        val bookmarkviewmodel: BookedMarkViewModel = viewModel()
        val navController = rememberNavController()
        var currentTab by remember {
            mutableStateOf(TabScreen.Home)
        }

    Scaffold(
        bottomBar = {
            BottomAppBar(
                actions = {
                Row (horizontalArrangement = Arrangement.Center
                ){
                  InputChip(
                      selected = currentTab == TabScreen.Home,
                      onClick = { 
                          currentTab= TabScreen.Home
                          navController.navigateToSingleTop(route = Screens.Home.name)
                                },
                       
                      label = { Text(text = "Home") },
                       trailingIcon = {
                           Icon(imageVector = Icons.Default.Home, contentDescription = null)

                       }
                      )

                    Spacer(modifier = Modifier.size(12.dp ))
                    InputChip(
                        selected = currentTab == TabScreen.BookMark,
                        onClick = {
                            currentTab= TabScreen.BookMark
                            navController.navigateToSingleTop(route = Screens.BookMark.name)
                        },

                        label = { Text(text = "BookMark") },
                        trailingIcon = {
                            Icon(imageVector = Icons.Default.Star
                                , contentDescription = null)

                        }
                    )

                }

            },
                floatingActionButton = {
                    FloatingActionButton(onClick = {
                        navController.navigateToSingleTop(
                            route = Screens.Detail.name)
                    }) {

                        Icon(imageVector = Icons.Default.Add, contentDescription = null)
                    }
                }
            )
                
        }
    ) {
        NoteNavigation(
            modifier= Modifier.padding(it),
            navHostController = navController,
            homeViewModel = homeViewModel,
            bookedMarkViewModel = bookmarkviewmodel,
            assistedFactory = assistedFactory
        )
    }
    }
}
