package dev.darkokoa.fubukidaze.ui.screen.runtimelog

import dev.darkokoa.fubukidaze.core.UiModel
import dev.darkokoa.fubukidaze.core.log.RuntimeLogger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch


class RuntimeLogUiModel(
  private val runtimeLogger: RuntimeLogger,
) : UiModel<RuntimeLogUiState, Nothing>(RuntimeLogUiState()) {

  init {
    uiModelScope.launch(Dispatchers.Default) {
      runtimeLogger.appLogStream().onEach { logLine ->
        intent {
          reduce { state -> state.copy(logContent = state.logContent + logLine) }
        }
      }.launchIn(this)
    }
  }
}