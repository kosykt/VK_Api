package com.example.vk_api.ui.homefragment

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.example.domain.model.ProfileInfoDomainModel
import com.example.domain.model.ProfilePhotoDomainModel
import com.example.vk_api.databinding.FragmentHomeBinding
import com.example.vk_api.ui.OnDataPass
import com.example.vk_api.ui.base.BaseFragment
import com.example.vk_api.utils.AppState
import com.example.vk_api.utils.NetworkObserver
import com.example.vk_api.utils.ViewModelFactory
import com.example.vk_api.utils.imageloader.AppImageLoader
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.textfield.TextInputLayout
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
        getProfileInfo()
        getProfilePhoto()
        initBottomSheet()
        initLastNameEditor()
        initFirstNameEditor()
        initStatusEditor()
    }

    override fun onStart() {
        super.onStart()
        observeProfilePhoto()
        observeProfileInfo()
        initEndIconStatusListener()
        initEndIconFirstNameListener()
        initEndIconLastNameListener()
    }

    private fun initEndIconFirstNameListener() {
        binding.bottomSheetProfileInfoEdit.editFirstNameLayout.setEndIconOnClickListener {
            val firstName = binding.bottomSheetProfileInfoEdit.editFirstNameEditText.text.toString()
            postFirstName(firstName)
        }
    }

    private fun postFirstName(firstName: String) {
        lifecycleScope.launchWhenStarted {
            val response: AppState = viewModel
                .postProfileFirstName(networkObserver.networkIsAvailable().value, firstName)
            renderPostResponse(response, binding.bottomSheetProfileInfoEdit.editFirstNameLayout)
        }
    }

    private fun initEndIconLastNameListener() {
        binding.bottomSheetProfileInfoEdit.editLastNameLayout.setEndIconOnClickListener {
            val lastName = binding.bottomSheetProfileInfoEdit.editLastNameEditText.text.toString()
            postLastName(lastName)
        }
    }

    private fun postLastName(lastName: String) {
        lifecycleScope.launchWhenStarted {
            val response: AppState = viewModel
                .postProfileLastName(networkObserver.networkIsAvailable().value, lastName)
            renderPostResponse(response, binding.bottomSheetProfileInfoEdit.editLastNameLayout)
        }
    }


    private fun initEndIconStatusListener() {
        binding.bottomSheetProfileInfoEdit.editStatusLayout.setEndIconOnClickListener {
            val status = binding.bottomSheetProfileInfoEdit.editStatusEditText.text.toString()
            postStatus(status)
        }
    }

    private fun postStatus(status: String) {
        lifecycleScope.launchWhenStarted {
            val response: AppState = viewModel
                .postProfileStatus(networkObserver.networkIsAvailable().value, status)
            renderPostResponse(response, binding.bottomSheetProfileInfoEdit.editStatusLayout)
        }
    }

    private fun renderPostResponse(response: AppState, editStatusLayout: TextInputLayout) {
        when (response) {
            is AppState.Error -> {
                editStatusLayout.isErrorEnabled = true
                editStatusLayout.error = response.error
            }
            is AppState.Loading -> {
                editStatusLayout.helperText = "Сhange handling"
            }
            is AppState.Success<*> -> {
                editStatusLayout.helperText = "Success"
                viewModel.getProfileInfo(networkObserver.networkIsAvailable().value)
            }
        }
    }


    private fun initStatusEditor() {
        with(binding.bottomSheetProfileInfoEdit) {
            editStatusLayout.isEndIconVisible = false
            editStatusEditText.addTextChangedListener {
                editStatusLayout.isEndIconVisible = !it.isNullOrBlank()
                editStatusLayout.isErrorEnabled = false
                editStatusLayout.helperText = ""
            }
        }
    }

    private fun initFirstNameEditor() {
        with(binding.bottomSheetProfileInfoEdit) {
            editFirstNameLayout.isEndIconVisible = false
            editFirstNameEditText.addTextChangedListener {
                editFirstNameLayout.isEndIconVisible = !it.isNullOrBlank()
                editFirstNameLayout.isErrorEnabled = false
                editFirstNameLayout.helperText = ""
            }
        }
    }

    private fun initLastNameEditor() {
        with(binding.bottomSheetProfileInfoEdit) {
            editLastNameLayout.isEndIconVisible = false
            editLastNameEditText.addTextChangedListener {
                editLastNameLayout.isEndIconVisible = !it.isNullOrBlank()
                editLastNameLayout.isErrorEnabled = false
                editLastNameLayout.helperText = ""
            }
        }
    }

    private fun observeProfileInfo() {
        lifecycleScope.launchWhenStarted {
            viewModel.stateFlow
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .distinctUntilChanged()
                .collectLatest {
                    renderInfo(it)
                }
        }
    }

    private fun observeProfilePhoto() {
        lifecycleScope.launchWhenStarted {
            viewModel.profilePhoto
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .distinctUntilChanged()
                .collectLatest {
                    renderPhoto(it)
                }
        }
    }

    private fun initBottomSheet() {
        val bottomSheet = BottomSheetBehavior.from(binding.bottomSheetProfileInfoEdit.root)
        binding.homeEditButton.setOnClickListener {
            if (bottomSheet.state == BottomSheetBehavior.STATE_COLLAPSED) {
                bottomSheet.state = BottomSheetBehavior.STATE_EXPANDED
            } else {
                bottomSheet.state = BottomSheetBehavior.STATE_COLLAPSED
            }
        }
    }

    private fun getProfilePhoto() {
        viewModel.getProfilePhoto(networkObserver.networkIsAvailable().value)
    }

    private fun getProfileInfo() {
        viewModel.getProfileInfo(networkObserver.networkIsAvailable().value)
    }

    @Suppress("UNCHECKED_CAST")
    private fun renderPhoto(appState: AppState) {
        when (appState) {
            is AppState.Success<*> -> {
                val data = appState.data as ProfilePhotoDomainModel
                imageLoader.loadInto(data.url, binding.homeProfilePhoto)
            }
            else -> {}
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun renderInfo(appState: AppState) {
        val dataPasser = requireActivity() as OnDataPass
        clearView()
        when (appState) {
            is AppState.Success<*> -> {
                val data = appState.data as ProfileInfoDomainModel
                dataPasser.menuTitle("id${data.id}")
                with(binding) {
                    homeProfileStatus.text = data.status
                    homeProfileFirstName.text = data.firstName
                    homeProfileLastName.text = data.lastName
                    bottomSheetProfileInfoEdit.editLastNameEditText.hint = data.lastName
                    bottomSheetProfileInfoEdit.editFirstNameEditText.hint = data.firstName
                    bottomSheetProfileInfoEdit.editStatusEditText.hint = data.status
                }
            }
            is AppState.Loading -> {
                dataPasser.menuTitle("LOADING")
            }
            is AppState.Error -> {
                dataPasser.menuTitle("ERROR")
            }
        }
    }

    private fun clearView() {
        with(binding) {
            homeProfileStatus.text = ""
            homeProfileFirstName.text = ""
            homeProfileLastName.text = ""
            bottomSheetProfileInfoEdit.editLastNameEditText.hint = ""
            bottomSheetProfileInfoEdit.editFirstNameEditText.hint = ""
            bottomSheetProfileInfoEdit.editStatusEditText.hint = ""
        }
    }
}