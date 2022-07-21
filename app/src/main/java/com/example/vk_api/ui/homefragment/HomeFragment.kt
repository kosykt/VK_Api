package com.example.vk_api.ui.homefragment

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.example.domain.model.ProfileInfoDomainModel
import com.example.domain.model.ProfilePhotoDomainModel
import com.example.vk_api.databinding.FragmentHomeBinding
import com.example.vk_api.ui.base.BaseFragment
import com.example.vk_api.utils.AppState
import com.example.vk_api.utils.NetworkObserver
import com.example.vk_api.utils.ViewModelFactory
import com.example.vk_api.utils.imageloader.AppImageLoader
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject

class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    @Inject
    lateinit var networkObserver: NetworkObserver

    @Inject
    lateinit var imageLoader: AppImageLoader

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: HomeViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[HomeViewModel::class.java]
    }

    override fun onAttach(context: Context) {
        (requireActivity().application as HomeSubcomponentProvider)
            .initHomeSubcomponent()
            .inject(this)
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun getViewBinding(container: ViewGroup?) =
        FragmentHomeBinding.inflate(layoutInflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launchWhenCreated {
            networkObserver.networkIsAvailable()
                .flowWithLifecycle(lifecycle, Lifecycle.State.CREATED)
                .distinctUntilChanged()
                .collectLatest {
                    viewModel.getProfileInfo(it)
                }
        }
        lifecycleScope.launchWhenCreated {
            networkObserver.networkIsAvailable()
                .flowWithLifecycle(lifecycle, Lifecycle.State.CREATED)
                .distinctUntilChanged()
                .collectLatest {
                    viewModel.getProfilePhoto(it)
                }
        }
    }

    override fun onStart() {
        super.onStart()
        lifecycleScope.launchWhenStarted {
            viewModel.profilePhoto
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .distinctUntilChanged()
                .collectLatest {
                    renderPhoto(it)
                }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.stateFlow
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .distinctUntilChanged()
                .collectLatest {
                    renderInfo(it)
                }
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun renderPhoto(appState: AppState) {
        when (appState) {
            is AppState.Success<*> -> {
                val data = appState.data as ProfilePhotoDomainModel
                imageLoader.loadInto(data.url, binding.homeProfilePhoto)
            }
            is AppState.Loading -> {
                Toast.makeText(context, "LOADING", Toast.LENGTH_SHORT).show()
            }
            is AppState.Error -> {
                Toast.makeText(context, appState.error, Toast.LENGTH_SHORT).show()
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun renderInfo(appState: AppState) {
        clearView()
        when (appState) {
            is AppState.Success<*> -> {
                val data = appState.data as ProfileInfoDomainModel
                with(binding){
                    homeProfileId.text = data.id
                    homeProfileStatus.text = data.status
                    homeProfileBdate.text = data.bDate
                    homeProfileFirstName.text = data.firstName
                    homeProfileLastName.text = data.lastName
                }
            }
            is AppState.Loading -> {
                Toast.makeText(context, "LOADING", Toast.LENGTH_SHORT).show()
            }
            is AppState.Error -> {
                Toast.makeText(context, appState.error, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun clearView() {
        with(binding){
            homeProfileId.text = ""
            homeProfileStatus.text = ""
            homeProfileBdate.text = ""
            homeProfileFirstName.text = ""
            homeProfileLastName.text = ""
        }
    }
}