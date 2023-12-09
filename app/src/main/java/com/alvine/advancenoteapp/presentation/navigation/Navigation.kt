package com.alvine.advancenoteapp.presentation.navigation

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.alvine.advancenoteapp.presentation.bookmark.BookMarkScreen
import com.alvine.advancenoteapp.presentation.bookmark.BookedMarkViewModel
import com.alvine.advancenoteapp.presentation.detail.DetailAssistedFactory
import com.alvine.advancenoteapp.presentation.detail.DetailScreen
import com.alvine.advancenoteapp.presentation.home.HomeScreen
import com.alvine.advancenoteapp.presentation.home.HomeViewModel

enum class Screens{
    Home,BookMark,Detail
}

@SuppressLint("SuspiciousIndentation")
@Composable
fun NoteNavigation(
    modifier: Modifier=Modifier,
    navHostController: NavHostController,
    homeViewModel: HomeViewModel,
    bookedMarkViewModel: BookedMarkViewModel,
    assistedFactory: DetailAssistedFactory
) {
  NavHost(navController = navHostController,
       startDestination = Screens.Home.name
      ){
      composable(route=Screens.Home.name){
          val state by homeViewModel.state.collectAsState()
          HomeScreen(
              modifier=modifier,
              state = state,
              onBookMarkChange =homeViewModel::onBookedMarkedChange,
              onDeleteNote = homeViewModel::deleteNote
          ) {
              navHostController.navigateToSingleTop(
                  route = "${Screens.Detail.name}?id=$it"
              )
          }
      }
      composable(route=Screens.BookMark.name){
          val state by bookedMarkViewModel.state.collectAsState()
          BookMarkScreen(
              state = state,
              onBookMarkChange =bookedMarkViewModel::onBookMarkChange ,
              onDeleteNote = bookedMarkViewModel:: deleteNote,
              modifier = modifier,
              onNoteClicked ={
                  navHostController.navigateToSingleTop(
                      route = "${Screens.Detail.name}?id=$it")
              }
          )
      }
      composable(
          route="${Screens.Detail.name}?id={id}",
          arguments = listOf(
              navArgument("id")
              {
                  NavType.LongType
                  defaultValue= -1L
              })
      ){backStackEntry->

        val id =backStackEntry.arguments?.getLong("id")?:1L
          DetailScreen(
              modifier =modifier,
              noteId =id,
              assistedFactory =assistedFactory,
              navigateUp = {navHostController.navigateUp()}
          )
      }
  }
}

fun NavHostController.navigateToSingleTop(route:String){
    navigate(route){
        popUpTo(graph.findStartDestination().id){saveState=true}
        launchSingleTop= true
        restoreState=true
    }
}