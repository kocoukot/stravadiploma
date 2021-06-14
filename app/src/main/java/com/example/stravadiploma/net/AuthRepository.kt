package com.example.stravadiploma.net

import android.content.Context
import android.net.Uri
import android.util.Log
import com.example.androidhomework.gitHub.net.AuthConfig
import com.example.stravadiploma.utils.Constants
import net.openid.appauth.AuthorizationRequest
import net.openid.appauth.AuthorizationService
import net.openid.appauth.AuthorizationServiceConfiguration
import net.openid.appauth.ClientAuthentication
import net.openid.appauth.ClientSecretPost
import net.openid.appauth.TokenRequest

class AuthRepository {


    fun getAuthRequest(): AuthorizationRequest {
        val serviceConfiguration = AuthorizationServiceConfiguration(
            Uri.parse(AuthConfig.AUTH_URI),
            Uri.parse(AuthConfig.TOKEN_URI)
        )

        val test = mapOf("approval_prompt" to "auto")
        val redirectUri = Uri.parse(AuthConfig.CALLBACK_URL)
        return AuthorizationRequest.Builder(
            serviceConfiguration,

            AuthConfig.CLIENT_ID,
            AuthConfig.RESPONSE_TYPE,
            redirectUri
        )
            .setAdditionalParameters(test)
            .setScope(AuthConfig.SCOPE)

            .build()
    }

    fun performTokenRequest(
        authService: AuthorizationService,
        tokenRequest: TokenRequest,
        onComplete: () -> Unit,
        onError: () -> Unit
    ) {
        authService.performTokenRequest(tokenRequest, getClientAuthentication()) { response, _ ->
            when {
                response != null -> {
                    val accessToken = response.accessToken.orEmpty()
                    SuccessAccessToken.token = accessToken
                    Log.d("DiplomaProject", accessToken)
                    onComplete()
                }
                else -> onError()
            }
        }
    }

    private fun getClientAuthentication(): ClientAuthentication {
        return ClientSecretPost(AuthConfig.CLIENT_SECRET)
    }
}

object SuccessAccessToken {
    var token = ""

}