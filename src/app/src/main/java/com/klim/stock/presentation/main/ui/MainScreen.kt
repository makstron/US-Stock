package com.klim.stock.presentation.main.ui

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.klim.coreUi.daggerViewModel
import com.klim.stock.dependencyinjection.ApplicationContextProvider
import com.klim.stock.favorited.ui.FavoritedScreen
import com.klim.stock.favorited.ui.di.SymbolsFavoritedComponent
import com.klim.stock.info.ui.presentation.InfoScreen
import com.klim.stock.presentation.main.MainActivity
import com.klim.stock.presentation.main.MainActivityViewModel
import com.klim.stock.presentation.main.findDependencies
import com.klim.stock.resources.AppTheme
import com.klim.stock.resources.LocalAppSize
import com.klim.stock.resources.toPx
import com.klim.stock.settings.ui.presentation.SettingsScreen
import kotlinx.coroutines.flow.flowOf
import kotlin.math.roundToInt
import com.klim.stock.resources.R as Res

data class ScreenState(
    val menuState: Boolean = false, //true = visible
    val internalContainerContent: NavigationDestination = NavigationDestination.Favourite,
)

@Composable
fun MainScreen(viewModel: MainActivityViewModel) {
    val menuWidth by remember { mutableStateOf(200.dp) }
    val screenState by viewModel.screenState.collectAsState(ScreenState())
    val offsetX: Float by animateFloatAsState(if (screenState.menuState) menuWidth.toPx() else 0f)


    val actionListeners by remember {
        mutableStateOf(
            ActionListeners(
                menu = { viewModel.setAction(Actions.MenuButton()) },
                search = { viewModel.setAction(Actions.SearchButton()) },
                favorited = { viewModel.setAction(Actions.Navigation(NavigationDestination.Favourite)) },
                settings = { viewModel.setAction(Actions.Navigation(NavigationDestination.Settings)) },
                info = {viewModel.setAction(Actions.Navigation(NavigationDestination.Info))},
                exit = {viewModel.setAction(Actions.ExitButton())},
            )
        )
    }

    Box(
        modifier = Modifier
            .background(color = colorResource(id = Res.color.brand_red))
            .safeDrawingPadding()
    ) {
        Column {
            ActionBar(
                openMenuListener = actionListeners.menu,
                searchListener = actionListeners.search
            )
            Spacer(
                modifier = Modifier
                    .height(32.dp)
            )
            Menu(
                actionListeners = actionListeners,
                modifier = Modifier
                    .width(menuWidth)
            )
        }
        ContentContainer(
            internalContainerContent = screenState.internalContainerContent,
            modifier = Modifier.offset { IntOffset(offsetX.roundToInt(), 0) }
        )
    }
}

//@Composable
//private fun ScreenContent(
//    actionListeners: ActionListeners,
//    menuWidth: Dp,
//    internalContainerContent: NavigationDestination?,
//    offset: IntOffset,
//) {
//
//    val navController = rememberNavController()
////    val destination by remember {
////        mutableStateOf(internalContainerContent)
////    }
//
//    internalContainerContent?.let { destination ->
//        navController.navigate(destination.destination)
//    }
//
//    Box(
//        modifier = Modifier
//            .background(color = colorResource(id = Res.color.brand_red))
//            .safeDrawingPadding()
//    ) {
//        Column {
//            ActionBar(
//                openMenuListener = actionListeners.menu,
//                searchListener = actionListeners.search
//            )
//            Spacer(
//                modifier = Modifier
//                    .height(32.dp)
//            )
//            Menu(
//                actionListeners = actionListeners,
//                modifier = Modifier
//                    .width(menuWidth)
//            )
//        }
////        println("@@@ params " + navController)
//        ContentContainer(
//            navController = navController,
////            containerContent = screenState.containerContent,
//            modifier = Modifier.offset { offset }
//        )
////        AndroidViewBinding(WindowsContainerBinding::inflate) {
////            val windowsContainer: WindowsContainer = this.root.findViewById(R.id.wcWindowsContainer)
////            windowsContainer.startWindow()
////
////            v.findNavController().navigate(containerContent)
////        }
//    }
//}

@Composable
private fun ActionBar(openMenuListener: () -> Unit, searchListener: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.height(LocalAppSize.current.appBarHeight),
    ) {
        Image(
            painter = painterResource(id = Res.drawable.ic_menu),
            contentDescription = null,
            colorFilter = ColorFilter.tint(Color.White),
            modifier = Modifier
                .height(LocalAppSize.current.appBarHeight)
                .width(LocalAppSize.current.appBarHeight)
                .clip(
                    shape = RoundedCornerShape(size = 24.dp)
                )
                .clickable(
                    onClick = openMenuListener
                )

                .padding(8.dp),
        )
        Text(
            text = stringResource(id = Res.string.app_name),
            color = Color.White,
            style = AppTheme.styles.headLineLarge,
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp)
                .weight(1f)
        )
        Image(
            painter = painterResource(id = Res.drawable.ic_search),
            contentDescription = null,
            colorFilter = ColorFilter.tint(Color.White),
            modifier = Modifier
                .height(LocalAppSize.current.appBarHeight)
                .width(LocalAppSize.current.appBarHeight)
                .clip(
                    shape = RoundedCornerShape(size = 24.dp)
                )
                .clickable(
                    onClick = searchListener
                )
                .padding(8.dp),
        )
    }
}

@Composable
private fun Menu(modifier: Modifier = Modifier, actionListeners: ActionListeners) {
    Column(
        modifier = modifier
            .fillMaxHeight()
            .padding(all = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Image(
            painter = painterResource(id = Res.drawable.ic_launcher_foreground),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .clip(
                    shape = RoundedCornerShape(size = 41.dp)
                )
                .background(Color.White)
                .padding(all = 2.dp)
                .clip(
                    shape = RoundedCornerShape(size = 41.dp)
                )
                .background(Color.Red)
                .height(82.dp)
                .width(82.dp),
        )
        Text(
            text = "Mc DO-DO",
            color = Color.White,
            style = AppTheme.styles.headLineMedium
        )
        Text(
            text = stringResource(id = Res.string.company_email),
            color = Color.White,
            style = AppTheme.styles.bodyMedium
        )
        Spacer(modifier = Modifier.height(24.dp))
        MenuItem(
            icon = Res.drawable.ic_company,
            label = Res.string.menu_favorited,
            clickListener = actionListeners.favorited,
        )
        MenuItem(
            icon = Res.drawable.ic_settings,
            label = Res.string.menu_settings,
            clickListener = actionListeners.settings,
        )
        MenuItem(
            icon = Res.drawable.ic_info,
            label = Res.string.menu_info,
            clickListener = actionListeners.info,
        )
        Spacer(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
        )
        MenuItem(
            icon = Res.drawable.ic_exit,
            label = Res.string.menu_exit,
            clickListener = actionListeners.exit,
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "v0.1",
            color = Color.White,
            textAlign = TextAlign.Center,
            style = AppTheme.styles.labelLarge,
            modifier = Modifier
                .fillMaxWidth()
                .alpha(0.5f)
        )
    }
}

@Composable
private fun MenuItem(@DrawableRes icon: Int, @StringRes label: Int, clickListener: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .clip(
                shape = RoundedCornerShape(size = 24.dp)
            )
            .clickable(onClick = clickListener)
    ) {
        Image(
            painter = painterResource(id = icon),
            contentDescription = null,
            colorFilter = ColorFilter.tint(Color.White),
            modifier = Modifier
                .height(LocalAppSize.current.appBarHeight)
                .width(LocalAppSize.current.appBarHeight)
                .padding(8.dp),
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = stringResource(id = label),
            color = Color.White,
            style = AppTheme.styles.titleLarge,
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}

@Composable
private fun ContentContainer(
    internalContainerContent: NavigationDestination,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Spacer(
            modifier = Modifier.height(LocalAppSize.current.appBarHeight + 8.dp)
        )
        Card(
            shape = RoundedCornerShape(topStart = 36.dp),
            backgroundColor = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(top = 0.dp)
                .shadow(
                    elevation = 4.dp,
                    shape = RoundedCornerShape(topStart = 36.dp),
                ),

            ) {
            when (internalContainerContent) {
                NavigationDestination.Favourite -> {
                    val activity = LocalContext.current as MainActivity
                    val component = SymbolsFavoritedComponent.Initializer
                        .init(
                            activity.application as ApplicationContextProvider,
                            activity.findDependencies(),
                            activity.findDependencies(),
                            activity.findDependencies(),
                        )
                    val viewModel = daggerViewModel {
                        component.getViewModel()
                    }
                    FavoritedScreen(viewModel)
                }

                NavigationDestination.Settings -> {
                    SettingsScreen()
                }

                NavigationDestination.Info -> {
                    InfoScreen()
                }
            }
        }
    }
}

@Composable
@Preview
fun ActionBarPreview() {
    AppTheme {
        ActionBar(
            openMenuListener = {},
            searchListener = {},
        )
    }
}

@Composable
@Preview
fun MenuItemPreview() {
    AppTheme {
        MenuItem(
            icon = Res.drawable.ic_company,
            label = Res.string.menu_favorited,
            clickListener = {},
        )
    }
}

@Composable
@Preview
fun MenuPreview() {
    AppTheme {
        Menu(
            actionListeners = ActionListeners({}, {}, {}, {}, {}, {})
        )
    }
}

@Composable
@Preview
fun ContentContainerPreview() {
    AppTheme {
        ContentContainer(
            internalContainerContent = NavigationDestination.Favourite
        )
    }
}


@Composable
@Preview
fun ScreenPreview() {
    AppTheme {
        MainScreen(
            viewModel = object : MainActivityViewModel() {
                override val screenState = flowOf(
                    ScreenState(
                        menuState = true,
                    )
                )

                override fun setAction(action: Actions) {

                }

            }
        )
    }
}