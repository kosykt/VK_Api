package com.example.vk_api.ui.homefragment

import com.example.domain.GetProfileInfoUseCase
import com.example.domain.model.UseCaseResponse
import com.example.vk_api.ui.base.BaseViewModel
import com.example.vk_api.utils.AppState
import com.example.vk_api.utils.auth.AuthRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val homeSubcomponentProvider: HomeSubcomponentProvider,
    private val authRepository: AuthRepository,
    private val getProfileInfoUseCase: GetProfileInfoUseCase,
) : BaseViewModel() {

    fun getProfileInfo(networkIsAvailable: Boolean) {
        baseViewModelScope.launch {
            mutableStateFlow.value = AppState.Loading
            try {
                val response = getProfileInfoUseCase.execute(authRepository.token.toString())
                when (response) {
                    is UseCaseResponse.Error -> {
                        mutableStateFlow.value = AppState.Error(response.message)
                    }
                    is UseCaseResponse.Success<*> -> {
                        mutableStateFlow.value = AppState.Success(response.data)
                    }
                }
            } catch (e: Exception) {
                mutableStateFlow.value = AppState.Error(e.message.toString())
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        homeSubcomponentProvider.destroyHomeSubcomponent()
    }
}