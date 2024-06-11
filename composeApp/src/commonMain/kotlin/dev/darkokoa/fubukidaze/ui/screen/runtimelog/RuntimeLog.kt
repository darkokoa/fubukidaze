package dev.darkokoa.fubukidaze.ui.screen.runtimelog

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.bottomSheet.BottomSheetNavigator
import compose.icons.FeatherIcons
import compose.icons.feathericons.ArrowLeft
import dev.darkokoa.fubukidaze.ui.screen.Screen

class RuntimeLog : Screen {

  @Composable
  override fun Content(navigator: Navigator, bottomSheetNavigator: BottomSheetNavigator) {
    val uiModel = getScreenModel<RuntimeLogUiModel>()
    val uiState by uiModel.collectAsState()

    RuntimeLogContent(navigator, uiState)
  }

}

@Composable
private fun RuntimeLogContent(
  navigator: Navigator,
  uiState: RuntimeLogUiState,
) {
  val topBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

  Scaffold(
    topBar = { RuntimeLogTopBar(navigator::pop, topBarScrollBehavior) },
    modifier = Modifier.nestedScroll(topBarScrollBehavior.nestedScrollConnection)
  ) { paddingValues ->
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues)
        .verticalScroll(rememberScrollState())
    ) {
      Text(text = uiState.logContent, style = MaterialTheme.typography.bodySmall)
    }
  }
}

@Composable
private fun RuntimeLogTopBar(
  navUp: () -> Unit,
  scrollBehavior: TopAppBarScrollBehavior,
  modifier: Modifier = Modifier
) {
  TopAppBar(
    title = { Text("Fubuki Core Runtime Log") },
    modifier = modifier,
    navigationIcon = {
      IconButton(onClick = navUp) {
        Icon(imageVector = FeatherIcons.ArrowLeft, contentDescription = null)
      }
    },
    scrollBehavior = scrollBehavior,
  )
}