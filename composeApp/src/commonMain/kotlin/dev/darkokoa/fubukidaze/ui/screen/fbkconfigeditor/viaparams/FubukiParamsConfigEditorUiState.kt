package dev.darkokoa.fubukidaze.ui.screen.fbkconfigeditor.viaparams

import androidx.compose.ui.text.input.TextFieldValue

data class FubukiParamsConfigEditorUiState(
  val nodeName: TextFieldValue = TextFieldValue(),
  val serverIp: TextFieldValue = TextFieldValue(),
  val serverPort: TextFieldValue = TextFieldValue(),
  val key: TextFieldValue = TextFieldValue(),
  val tunAddrIp: TextFieldValue = TextFieldValue(),
  val tunAddrNetmask: TextFieldValue = TextFieldValue(),
  val showConfigInputDialog: Boolean = false,
  val canLaunch: Boolean = false
)