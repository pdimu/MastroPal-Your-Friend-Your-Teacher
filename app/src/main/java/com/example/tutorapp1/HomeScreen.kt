package com.example.tutorapp1


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import android.R.attr.contentDescription
import android.util.Log
import androidx.benchmark.perfetto.ExperimentalPerfettoTraceProcessorApi
import androidx.benchmark.perfetto.Row
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Category
import androidx.compose.material.icons.outlined.Create
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.BlendMode.Companion.Screen
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    userId: String,
    userEmail: String,
    onLogout: () -> Unit
){

    val navController = rememberNavController()
    var selected by remember { mutableStateOf(0) }
    val pagerState = rememberPagerState(initialPage = selected) // Sync with pager
    val coroutineScope = rememberCoroutineScope()
    var isInsideSubpage by remember { mutableStateOf(false) }

    // Track whether to reset Categories navigation
    var resetCategories by remember { mutableStateOf(false) }

    // Sync selected state when swiping (Avoids extra recompositions)
    LaunchedEffect(pagerState.currentPage) {
        selected = pagerState.currentPage
    }

    Scaffold (
        topBar = {
            TopAppBar(
                title = { Text("Tutor App") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = AppColors.Surface
                )
            )
        },
        bottomBar = {
            BottomAppBar(selected, navController) { newIndex ->
                // selected = newIndex
                coroutineScope.launch {
                    //   pagerState.animateScrollToPage(newIndex)
                    pagerState.scrollToPage(newIndex)
                }

                //  Reset Categories when clicking Bottom Navigation
                if (newIndex == 1) {
                    resetCategories = true  // Tell CategoriesNavigation to reset
                }

//                    if (newIndex == 1) { // When clicking Categories
//                        navController.navigate(Screen.MainCategories.route) {
//                            popUpTo(Screen.MainCategories.route) { inclusive = true }
//                        }
//                    }

//                    if (newIndex != 1) { // If not on Categories tab
//                        navController.navigate("mainCategories") {
//                            popUpTo("mainCategories") { inclusive = true }
//                        }
//                    }

            }
//            NavigationBar(
//                containerColor = MaterialTheme.colorScheme.primary,
//                contentColor = MaterialTheme.colorScheme.onPrimary,
//            ) {// NavigationBar
//                NavigationBarItem(
//                    selected = true,
//                    onClick = { /* Navigate to Home */ },
//                    icon = {
//                        Icon(Icons.Filled.Home, contentDescription = "Home")
//                    }
//                )
//
//                NavigationBarItem(
//                    selected = true,
//                    onClick = { /* do something */ },
//                    icon = {
//                        Icon(Icons.Filled.Create, contentDescription = "Menu")
//                    }
//                )
//
//                NavigationBarItem(
//                    selected = true,
//                    onClick = { /* Navigate to Home */ },
//                    icon = {
//                        Icon(Icons.Filled.Menu, contentDescription = "Home")
//                    }
//                )
//
//                NavigationBarItem(
//                    selected = true,
//                    onClick = { /* Navigate to Home */ },
//                    icon = {
//                        Icon(Icons.Filled.Person, contentDescription = "Home")
//                    }
//                )
//            } // NavigationBar
        },

        floatingActionButton = {
            // ✅ Conditionally show FAB only on certain pages if needed
            if (selected == 0 || selected == 1) {
                CreateButton {
                    // ✅ Define FAB click behavior here
                    Log.d("FAB_ACTION", "Floating Action Button Clicked")
                    // You can also trigger navigation or open a dialog here
                }
            }
        }

//        val isFabVisible = selected == 1 || selected == 0
//    floatingActionButton = {
//        if (isFabVisible) {
//            CreateButton {
//                Log.d("FAB_ACTION", "Floating Action Button Clicked")
//            }
//        }
//    }


    ) {innerPadding ->
        HorizontalPager(
            count = 4,
            state = pagerState,
            modifier = Modifier.padding(innerPadding),
            userScrollEnabled = !isInsideSubpage
        ) {page ->
            //  selected = page
            when (page){
//                0 -> SignUp(onSignUpSuccess = { },
//                    onSwitchToLogin = {  })
                0 -> Column (
                    verticalArrangement = Arrangement.SpaceEvenly
                ){
                    UserProfile(userEmail = userEmail)
                    NotesList(
                        userName = userId,
                        onLogout = { onLogout() }
                    )
                }
                1 -> NotesList(
                    userName = userId,
                    onLogout = { onLogout() }
                )
                2 -> SearchScreen()
                3 -> UserProfile(userEmail = userEmail)
            }
        }

//        Column(
//            modifier = Modifier
//                .padding(innerPadding),
//            verticalArrangement = Arrangement.spacedBy(16.dp),
//        ) {
//            NotesList()
//
//        }

    }
}


@Composable
fun CreateButton(onClick: () -> Unit){
    FloatingActionButton(
        onClick = { onClick() }
    ) {
        Icon(Icons.Filled.Create, "")
    }
}

@Composable
fun BottomAppBar(selected: Int, navController: NavController, onItemSelected: (Int) -> Unit){

    var shouldResetThePage by remember { mutableStateOf(false) }

    NavigationBar(
        modifier = Modifier
            .padding(
                start = 16.dp, end = 16.dp, top = 0.dp, bottom = 0.dp
            )
            .graphicsLayer {
                shape = RoundedCornerShape(
                    topStart = 12.dp,
                    topEnd = 12.dp,
                    bottomEnd = 0.dp,
                    bottomStart = 0.dp
                )
                clip = true
            },
        containerColor = LightColorScheme.PrimaryFixed

    ){
        BottomAppBarItem.forEachIndexed { index, BottomNavItems ->
            NavigationBarItem(
                selected = index == selected, // Now properly updates based on `selected`
                onClick = {
                    if (index == selected && BottomNavItems.route == "list") {
                        shouldResetThePage = true
                    } else {
                        onItemSelected(index)  // ✅ This triggers the pager change in HomeScreen
                    }
                },
                icon = {
                    Box(
                        modifier = Modifier
                            .background(
                                color =
                                    if (index == selected)
                                        AppColors.White.copy(alpha = 0.4f)
                                    else
                                        Color.Transparent,
                                shape =
                                    if (index == selected)
                                        RoundedCornerShape(
                                            12.dp
                                        ) else
                                        RoundedCornerShape(0.dp)
                                // Optional: rounded background
                            )

                            .padding(
                                start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp
                            ) // Padding for better appearance)

                    ) {
                        BadgedBox(
                            badge = {
                                if (BottomNavItems.badges != 0) {
                                    Badge {
                                        Text(text = BottomNavItems.badges.toString())
                                    }
                                }
                            }
                        ) {
                            Icon(
                                imageVector = if (index == selected)
                                    BottomNavItems.selectedIcon
                                else
                                    BottomNavItems.unselectedIcon,
                                contentDescription = BottomNavItems.title,
                                tint = if (index == selected)
                                    AppColors.Primary
                                else
                                    LightColorScheme.Secondary
                            )
                        }
                    }

                },
                label = {
                    Text(
                        text = BottomNavItems.title,
                        fontWeight = if (index == selected) FontWeight.Bold else FontWeight.Normal,
                        color = if (index == selected) AppColors.Primary else LightColorScheme.Secondary,
                        modifier = Modifier
                            .padding(0.dp),
                    )
                },
                alwaysShowLabel = true,
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = AppColors.Primary,
                    unselectedIconColor = AppColors.White,
                    selectedTextColor = AppColors.Primary,
                    unselectedTextColor = AppColors.White,
                    indicatorColor = Color.Transparent // Properly sets the selection indicator
                )
            )
        }

    }
}


data class BottomNavItems(
    val title: String,
    val route: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val badges: Int
)

val BottomAppBarItem = listOf(
    BottomNavItems(
        title = "Home",
        route = "home",
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home,
        badges = 0,
    ),

    BottomNavItems(
        title = "List",
        route = "list",
        selectedIcon = Icons.Filled.List,
        // selectedIcon = Icons.AutoMirrored.Filled.List,
        unselectedIcon = Icons.Outlined.List,
        badges = 0,
    ),

    BottomNavItems(
        title = "Search",
        route = "search",
        selectedIcon = Icons.Filled.Search,
        unselectedIcon = Icons.Outlined.Search,
        // selectedIcon = Icons.AutoMirrored.Filled.List,
        // unselectedIcon = Icons.AutoMirrored.Outlined.List,
        badges = 0,
    ),

    BottomNavItems(
        title = "Profile",
        route = "profile",
        selectedIcon = Icons.Filled.AccountCircle,
        unselectedIcon = Icons.Outlined.AccountCircle,
        badges = 0,
    ),


    )




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen1(
    userId: String,
    userEmail: String,
    onLogout: () -> Unit
){

    val navController = rememberNavController()
    var selected by remember { mutableStateOf(0) }
    val pagerState = rememberPagerState(initialPage = selected) // Sync with pager
    val coroutineScope = rememberCoroutineScope()
    var isInsideSubpage by remember { mutableStateOf(false) }

    // Track whether to reset Categories navigation
    var resetCategories by remember { mutableStateOf(false) }

    // Sync selected state when swiping (Avoids extra recompositions)
    LaunchedEffect(pagerState.currentPage) {
        selected = pagerState.currentPage
    }

    Scaffold (
        topBar = {
            TopAppBar(
                title = { Text("Tutor App") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = AppColors.Surface
                )
            )
        },
        bottomBar = {
           BottomAppBar1(selected, navController) { newIndex ->
               // selected = newIndex
               coroutineScope.launch {
                   //   pagerState.animateScrollToPage(newIndex)
                   pagerState.scrollToPage(newIndex)
               }

               //  Reset Categories when clicking Bottom Navigation
               if (newIndex == 1) {
                   resetCategories = true  // Tell CategoriesNavigation to reset
               }

//                    if (newIndex == 1) { // When clicking Categories
//                        navController.navigate(Screen.MainCategories.route) {
//                            popUpTo(Screen.MainCategories.route) { inclusive = true }
//                        }
//                    }

//                    if (newIndex != 1) { // If not on Categories tab
//                        navController.navigate("mainCategories") {
//                            popUpTo("mainCategories") { inclusive = true }
//                        }
//                    }

           }
//            NavigationBar(
//                containerColor = MaterialTheme.colorScheme.primary,
//                contentColor = MaterialTheme.colorScheme.onPrimary,
//            ) {// NavigationBar
//                NavigationBarItem(
//                    selected = true,
//                    onClick = { /* Navigate to Home */ },
//                    icon = {
//                        Icon(Icons.Filled.Home, contentDescription = "Home")
//                    }
//                )
//
//                NavigationBarItem(
//                    selected = true,
//                    onClick = { /* do something */ },
//                    icon = {
//                        Icon(Icons.Filled.Create, contentDescription = "Menu")
//                    }
//                )
//
//                NavigationBarItem(
//                    selected = true,
//                    onClick = { /* Navigate to Home */ },
//                    icon = {
//                        Icon(Icons.Filled.Menu, contentDescription = "Home")
//                    }
//                )
//
//                NavigationBarItem(
//                    selected = true,
//                    onClick = { /* Navigate to Home */ },
//                    icon = {
//                        Icon(Icons.Filled.Person, contentDescription = "Home")
//                    }
//                )
//            } // NavigationBar
        },

        floatingActionButton = {
            // ✅ Conditionally show FAB only on certain pages if needed
            if (selected == 0 || selected == 1) {
                CreateButton1 {
                    // ✅ Define FAB click behavior here
                    Log.d("FAB_ACTION", "Floating Action Button Clicked")
                    // You can also trigger navigation or open a dialog here
                }
            }
        }

//        val isFabVisible = selected == 1 || selected == 0
//    floatingActionButton = {
//        if (isFabVisible) {
//            CreateButton {
//                Log.d("FAB_ACTION", "Floating Action Button Clicked")
//            }
//        }
//    }


    ) {innerPadding ->
        HorizontalPager(
            count = 4,
            state = pagerState,
            modifier = Modifier.padding(innerPadding),
            userScrollEnabled = !isInsideSubpage
        ) {page ->
            //  selected = page
            when (page){
//                0 -> SignUp(onSignUpSuccess = { },
//                    onSwitchToLogin = {  })
                0 -> Column (
                    verticalArrangement = Arrangement.SpaceEvenly
                ){
                    UserProfile(userEmail = userEmail)
                    NotesList(
                        userName = userId,
                        onLogout = { onLogout() }
                    )
                }
                1 -> NotesList(
                    userName = userId,
                    onLogout = { onLogout() }
                )
                2 -> SearchScreen()
                3 -> UserProfile(userEmail = userEmail)
            }
        }

//        Column(
//            modifier = Modifier
//                .padding(innerPadding),
//            verticalArrangement = Arrangement.spacedBy(16.dp),
//        ) {
//            NotesList()
//
//        }

    }
}


@Composable
fun CreateButton1(onClick: () -> Unit){
    FloatingActionButton(
        onClick = { onClick() }
    ) {
        Icon(Icons.Filled.Create, "")
    }
}

@Composable
fun BottomAppBar1(selected: Int, navController: NavController, onItemSelected: (Int) -> Unit){

    var shouldResetThePage by remember { mutableStateOf(false) }

    NavigationBar(
        modifier = Modifier
            .padding(
                start = 16.dp, end = 16.dp, top = 0.dp, bottom = 0.dp
            )
            .graphicsLayer {
                shape = RoundedCornerShape(
                    topStart = 12.dp,
                    topEnd = 12.dp,
                    bottomEnd = 0.dp,
                    bottomStart = 0.dp
                )
                clip = true
            },
         containerColor = LightColorScheme.PrimaryFixed

    ){
        BottomAppBarItem1.forEachIndexed { index, BottomNavItems1 ->
            NavigationBarItem(
                selected = index == selected, // Now properly updates based on `selected`
                    onClick = {
                        if (index == selected && BottomNavItems1.route == "list") {
                            shouldResetThePage = true
                        } else {
                            onItemSelected(index)  // ✅ This triggers the pager change in HomeScreen
                        }
                    },
                icon = {
                    Box(
                        modifier = Modifier
                            .background(
                                color =
                                    if (index == selected)
                                        AppColors.White.copy(alpha = 0.4f)
                                    else
                                        Color.Transparent,
                                shape =
                                    if (index == selected)
                                        RoundedCornerShape(
                                            12.dp
                                        ) else
                                        RoundedCornerShape(0.dp)
                                // Optional: rounded background
                            )

                            .padding(
                                start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp
                            ) // Padding for better appearance)

                    ) {
                        BadgedBox(
                            badge = {
                                if (BottomNavItems1.badges != 0) {
                                    Badge {
                                        Text(text = BottomNavItems1.badges.toString())
                                    }
                                }
                            }
                        ) {
                            Icon(
                                imageVector = if (index == selected)
                                    BottomNavItems1.selectedIcon
                                else
                                    BottomNavItems1.unselectedIcon,
                                contentDescription = BottomNavItems1.title,
                                tint = if (index == selected)
                                    AppColors.Primary
                                else
                                    LightColorScheme.Secondary
                            )
                        }
                    }

                },
                label = {
                    Text(
                        text = BottomNavItems1.title,
                        fontWeight = if (index == selected) FontWeight.Bold else FontWeight.Normal,
                        color = if (index == selected) AppColors.Primary else LightColorScheme.Secondary,
                        modifier = Modifier
                            .padding(0.dp),
                    )
                },
                alwaysShowLabel = true,
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = AppColors.Primary,
                    unselectedIconColor = AppColors.White,
                    selectedTextColor = AppColors.Primary,
                    unselectedTextColor = AppColors.White,
                    indicatorColor = Color.Transparent // Properly sets the selection indicator
                )
            )
        }

    }
}


data class BottomNavItems1(
    val title: String,
    val route: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val badges: Int
)

val BottomAppBarItem1 = listOf(
        BottomNavItems1(
            title = "Home",
            route = "home",
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home,
            badges = 0,
        ),

        BottomNavItems1(
            title = "List",
            route = "list",
            selectedIcon = Icons.Filled.List,
           // selectedIcon = Icons.AutoMirrored.Filled.List,
            unselectedIcon = Icons.Outlined.List,
            badges = 0,
        ),

        BottomNavItems1(
            title = "Search",
            route = "search",
            selectedIcon = Icons.Filled.Search,
            unselectedIcon = Icons.Outlined.Search,
            // selectedIcon = Icons.AutoMirrored.Filled.List,
            // unselectedIcon = Icons.AutoMirrored.Outlined.List,
            badges = 0,
        ),

        BottomNavItems1(
            title = "Profile",
            route = "profile",
            selectedIcon = Icons.Filled.AccountCircle,
            unselectedIcon = Icons.Outlined.AccountCircle,
            badges = 0,
        ),
    )
