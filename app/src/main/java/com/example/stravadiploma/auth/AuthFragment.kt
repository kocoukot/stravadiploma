package com.example.stravadiploma.auth


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.stravadiploma.MainActivity
import com.example.stravadiploma.R
import com.example.stravadiploma.UserActivity
import com.example.stravadiploma.databinding.FragmentAuthBinding
import com.example.stravadiploma.net.oauth.SuccessAccessToken
import com.example.stravadiploma.utils.Constants
import com.example.stravadiploma.utils.logInfo
import com.example.stravadiploma.utils.toast
import net.openid.appauth.AuthorizationException
import net.openid.appauth.AuthorizationResponse

class AuthFragment : Fragment(R.layout.fragment_auth) {

    private val sharedPref by lazy {
        requireContext().getSharedPreferences(
            Constants.SHARED_PREF,
            Context.MODE_PRIVATE
        )
    }

    private val viewModel: AuthViewModel by viewModels()
    private val binding by viewBinding(FragmentAuthBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViewModel()
        loginIfHasToken()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == AUTH_REQUEST_CODE && data != null) {
            val tokenExchangeRequest = AuthorizationResponse.fromIntent(data)
                ?.createTokenExchangeRequest()
            val exception = AuthorizationException.fromIntent(data)
            when {
                tokenExchangeRequest != null && exception == null ->
                    viewModel.onAuthCodeReceived(tokenExchangeRequest)
                exception != null -> viewModel.onAuthCodeFailed(exception)
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun bindViewModel() {
        binding.loginButton.setOnClickListener { viewModel.openLoginPage() }
        viewModel.loadingLiveData.observe(viewLifecycleOwner, ::updateIsLoading)
        viewModel.openAuthPageLiveData.observe(viewLifecycleOwner, ::openAuthPage)
        viewModel.toastLiveData.observe(viewLifecycleOwner, ::toast)
        viewModel.authSuccessLiveData.observe(viewLifecycleOwner) {
            openProfile()
        }
    }

    private fun openProfile() {
        val activityClass = UserActivity::class.java
        val intent = Intent(requireContext(), activityClass)
        startActivity(intent)
        (requireContext() as MainActivity).finish()
    }

    private fun updateIsLoading(isLoading: Boolean) {
        binding.loginButton.isVisible = !isLoading
        binding.loginProgress.isVisible = isLoading
        binding.headerLogging.isVisible = !isLoading
    }

    private fun loginIfHasToken() {
        val currentTime = System.currentTimeMillis()
        val expTime = sharedPref.getLong(Constants.ACCESS_TOKEN_EXPIRATION, 0)
        if ((expTime - currentTime) >= 0) {
            SuccessAccessToken.token = sharedPref.getString(Constants.ACCESS_TOKEN, "") ?: ""
            SuccessAccessToken.expTime = sharedPref.getLong(Constants.ACCESS_TOKEN_EXPIRATION, 0.toLong())
            openProfile()
        }
    }

    private fun openAuthPage(intent: Intent) {
        startActivityForResult(intent, AUTH_REQUEST_CODE)
    }

    companion object {
        private const val AUTH_REQUEST_CODE = 341
    }
}