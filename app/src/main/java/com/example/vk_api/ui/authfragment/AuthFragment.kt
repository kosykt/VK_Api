package com.example.vk_api.ui.authfragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.CookieManager
import android.webkit.WebView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.vk_api.R
import com.example.vk_api.utils.NetworkObserver
import com.example.vk_api.utils.auth.AuthRepository
import com.example.vk_api.utils.auth.AuthStatus
import com.example.vk_api.utils.auth.AuthWebViewClient
import com.example.vk_api.utils.auth.SCOPE
import java.net.URLEncoder
import java.util.regex.Pattern
import javax.inject.Inject

/**
 * Представляет фрагмент 'Войти в аккаунт'.
 */
class AuthFragment : Fragment() {

    @Inject
    lateinit var networkObserver: NetworkObserver

    @Inject
    lateinit var authRepository: AuthRepository

    private val webview by lazy { WebView(context!!) }
    private val _authParams = StringBuilder("https://oauth.vk.com/authorize?").apply {
        append(String.format("%s=%s",
            URLEncoder.encode("client_id", "UTF-8"),
            URLEncoder.encode("8214097", "UTF-8")) + "&")
        append(String.format("%s=%s",
            URLEncoder.encode("redirect_uri", "UTF-8"),
            URLEncoder.encode("https://oauth.vk.com/blank.html", "UTF-8")) + "&")
        append(String.format("%s=%s",
            URLEncoder.encode("display", "UTF-8"),
            URLEncoder.encode("mobile", "UTF-8")) + "&")
        append(String.format("%s=%s",
            URLEncoder.encode("scope", "UTF-8"),
            URLEncoder.encode(SCOPE, "UTF-8")) + "&")
        append(String.format("%s=%s",
            URLEncoder.encode("response_type", "UTF-8"),
            URLEncoder.encode("token", "UTF-8")) + "&")
        append(String.format("%s=%s",
            URLEncoder.encode("v", "UTF-8"),
            URLEncoder.encode("5.131", "UTF-8")) + "&")
        append(String.format("%s=%s",
            URLEncoder.encode("state", "UTF-8"),
            URLEncoder.encode("12345", "UTF-8")) + "&")
        append(String.format("%s=%s",
            URLEncoder.encode("revoke", "UTF-8"),
            URLEncoder.encode("1", "UTF-8")))
    }.toString()

    override fun onAttach(context: Context) {
        (requireActivity().application as AuthSubcomponentProvider)
            .initAuthSubcomponent()
            .inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ) = webview

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (authRepository.token == null) {
            webview.webViewClient = AuthWebViewClient(context!!) { status ->
                when (status) {
                    AuthStatus.AUTH -> {

                    }
                    AuthStatus.CONFIRM -> {

                    }
                    AuthStatus.ERROR -> {
                        Toast.makeText(context, "Не верный логин или пароль", Toast.LENGTH_LONG)
                            .show()
                    }
                    AuthStatus.BLOCKED -> {
                        showAuthWindow()
                        Toast.makeText(context, "Аккаунт заблокирован", Toast.LENGTH_LONG).show()
                    }
                    AuthStatus.SUCCESS -> {
                        val url = webview.url!!
                        val tokenMather = Pattern.compile("access_token=\\w+").matcher(url)
                        val userIdMather = Pattern.compile("user_id=\\w+").matcher(url)
                        // Если есть совпадение с патерном.
                        if (tokenMather.find() && userIdMather.find()) {
                            val token = url.substringAfter("access_token=").substringBefore("&")
                            val userId = url.substringAfter("user_id=").substringBefore("&")
                            // Если токен и id получен.
                            if (token.isNotEmpty() && userId.isNotEmpty()) {
                                authRepository.token = token
                                authRepository.userId = userId
                                navigateTo()
                            }
                        }
                    }
                }
            }
        } else {
            navigateTo()
        }
    }

    override fun onStart() {
        super.onStart()
        if (networkObserver.networkIsAvailable().value){
            if (authRepository.token == null) {
                showAuthWindow()
            }
        }else{
          Toast.makeText(context, "Network is not available", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showAuthWindow() {
        CookieManager.getInstance().removeAllCookies(null)
        webview.loadUrl(_authParams)
    }

    private fun navigateTo() {
        findNavController().navigate(R.id.action_authFragment_to_homeFragment)
    }

    override fun onDestroy() {
        (requireActivity().application as AuthSubcomponentProvider).destroyAuthSubcomponent()
        super.onDestroy()
    }
}