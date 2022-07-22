package com.example.vk_api.ui.homefragment

import com.example.domain.GetProfileInfoUseCase
import com.example.domain.GetProfilePhotoUseCase
import com.example.domain.PostProfileInfoStatusUseCase
import com.example.domain.model.SaveProfileInfoDomainModel
import com.example.domain.model.UseCaseResponse
import com.example.vk_api.ui.base.BaseViewModel
import com.example.vk_api.utils.AppState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val homeSubcomponentProvider: HomeSubcomponentProvider,
    private val getProfileInfoUseCase: GetProfileInfoUseCase,
    private val getProfilePhotoUseCase: GetProfilePhotoUseCase,
    private val postProfileInfoStatusUseCase: PostProfileInfoStatusUseCase,
) : BaseViewModel() {

    private var _profilePhoto = MutableStateFlow<AppState>(AppState.Loading)
    val profilePhoto: StateFlow<AppState> = _profilePhoto.asStateFlow()

    suspend fun postProfileStatus(networkIsAvailable: Boolean, status: String): String {
        return try {
            val response = postProfileInfoStatusUseCase.execute(status)
            when (response) {
                is UseCaseResponse.Error -> {
                    response.message
                }
                is UseCaseResponse.Success<*> -> {
                    return (response.data as SaveProfileInfoDomainModel).response
                }
            }
        } catch (e: Exception) {
            return e.message.toString()
        }
    }

    fun getProfilePhoto(networkIsAvailable: Boolean) {
        baseViewModelScope.launch {
            _profilePhoto.value = AppState.Loading
            try {
                val response = getProfilePhotoUseCase.execute()
                when (response) {
                    is UseCaseResponse.Error -> {
                        _profilePhoto.value = AppState.Error(response.message)
                    }
                    is UseCaseResponse.Success<*> -> {
                        _profilePhoto.value = AppState.Success(response.data)
                    }
                }
            } catch (e: Exception) {
                _profilePhoto.value = AppState.Error(e.message.toString())
            }
        }
    }

    fun getProfileInfo(networkIsAvailable: Boolean) {
        baseViewModelScope.launch {
            mutableStateFlow.value = AppState.Loading
            try {
                val response = getProfileInfoUseCase.execute()
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