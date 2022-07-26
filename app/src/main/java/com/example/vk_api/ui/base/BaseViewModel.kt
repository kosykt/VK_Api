package com.example.vk_api.ui.base

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vk_api.utils.AppState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.plus

private const val COROUTINE_EXCEPTION_HANDLER = "COROUTINE_EXCEPTION_HANDLER"

abstract class BaseViewModel : ViewModel() {

    protected val mutableStateFlow = MutableStateFlow<AppState>(AppState.Loading)
    val stateFlow: StateFlow<AppState> = mutableStateFlow.asStateFlow()

    private val coroutineContext =
        SupervisorJob() + Dispatchers.Main.immediate + CoroutineExceptionHandler { _, throwable ->
            Log.e(COROUTINE_EXCEPTION_HANDLER, throwable.message.toString())
        }
    protected val baseViewModelScope = viewModelScope.plus(coroutineContext)
}