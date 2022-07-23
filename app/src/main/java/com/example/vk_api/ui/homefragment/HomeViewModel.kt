package com.example.vk_api.ui.homefragment

import com.example.domain.*
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
    private val postProfileInfoFirstNameUseCase: PostProfileInfoFirstNameUseCase,
    private val postProfileInfoLastNameUseCase: PostProfileInfoLastNameUseCase,
) : BaseViewModel() {

    private var _profilePhoto = MutableStateFlow<AppState>(AppState.Loading)
    val profilePhoto: StateFlow<AppState> = _profilePhoto.asStateFlow()

    suspend fun postProfileStatus(networkIsAvailable: Boolean, status: String): AppState {
        return if (networkIsAvailable) {
            try {
                postProfileResponseHandling(postProfileInfoStatusUseCase.execute(status))
            } catch (e: Exception) {
                AppState.Error(e.message.toString())
            }
        }else{
            AppState.Error("Network is not available")
        }
    }

    suspend fun postProfileFirstName(networkIsAvailable: Boolean, firstName: String): AppState {
        return if (networkIsAvailable) {
            try {
                postProfileResponseHandling(postProfileInfoFirstNameUseCase.execute(firstName))
            } catch (e: Exception) {
                AppState.Error(e.message.toString())
            }
        }else{
            AppState.Error("Network is not available")
        }
    }

    suspend fun postProfileLastName(networkIsAvailable: Boolean, lastName: String): AppState {
        return if (networkIsAvailable) {
            try {
                postProfileResponseHandling(postProfileInfoLastNameUseCase.execute(lastName))
            } catch (e: Exception) {
                AppState.Error(e.message.toString())
            }
        }else{
            AppState.Error("Network is not available")
        }
    }

    fun getProfilePhoto(networkIsAvailable: Boolean) {
        if (networkIsAvailable) {
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
        }else{
            _profilePhoto.value = AppState.Error("Network is not available")
        }
    }

    fun getProfileInfo(networkIsAvailable: Boolean) {
        if (networkIsAvailable) {
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
        }else{
            mutableStateFlow.value = AppState.Error("Network is not available")
        }
    }

    private fun postProfileResponseHandling(response: UseCaseResponse): AppState {
        return when (response) {
            is UseCaseResponse.Error -> {
                AppState.Error(response.message)
            }
            is UseCaseResponse.Success<*> -> {
                AppState.Success((response.data as SaveProfileInfoDomainModel).response)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        homeSubcomponentProvider.destroyHomeSubcomponent()
    }
}