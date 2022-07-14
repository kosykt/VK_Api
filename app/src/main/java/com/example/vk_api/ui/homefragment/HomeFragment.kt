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
import com.example.vk_api.databinding.FragmentHomeBinding
import com.example.vk_api.ui.base.BaseFragment
import com.example.vk_api.utils.AppState
import com.example.vk_api.utils.NetworkObserver
import com.example.vk_api.utils.ViewModelFactory
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject

class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    @Inject
    lateinit var networkObserver: NetworkObserver

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
    }

    override fun onStart() {
        super.onStart()
        lifecycleScope.launchWhenStarted {
            viewModel.stateFlow
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .distinctUntilChanged()
                .collectLatest {
                    renderData(it)
                }
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success<*> -> {
                val data = appState.data as ProfileInfoDomainModel
                Toast.makeText(context, data.firstName, Toast.LENGTH_SHORT).show()
            }
            is AppState.Loading -> {
                Toast.makeText(context, "LOADING", Toast.LENGTH_SHORT).show()
            }
            is AppState.Error -> {
                Toast.makeText(context, appState.error, Toast.LENGTH_SHORT).show()
            }
        }
    }
}