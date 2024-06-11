package dev.darkokoa.fubukidaze.core.log

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

actual class RuntimeLogger {

  actual fun init() {}

  actual fun appLogStream(): Flow<String> = flow { }

}