package dev.darkokoa.fubukidaze.core.log

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

actual class RuntimeLogger {

  actual fun init() {
    Runtime.getRuntime().exec("logcat -c")
  }

  actual fun appLogStream(): Flow<String> = flow {
    Runtime.getRuntime().exec("logcat")?.inputStream?.bufferedReader()?.useLines { lines ->
      lines.forEach { line ->
        emit(line)
      }
    }
  }.flowOn(Dispatchers.IO)

}