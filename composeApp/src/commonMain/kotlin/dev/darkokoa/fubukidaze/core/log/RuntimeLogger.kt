package dev.darkokoa.fubukidaze.core.log

import kotlinx.coroutines.flow.Flow

expect class RuntimeLogger() {

  fun init()

  fun appLogStream(): Flow<String>
}