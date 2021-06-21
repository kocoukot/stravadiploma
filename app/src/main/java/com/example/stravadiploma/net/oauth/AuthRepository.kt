package com.example.stravadiploma.net

import android.net.Uri
import android.util.Log
import androidx.browser.trusted.TokenStore
import com.example.stravadiploma.net.oauth.AuthConfig
import net.openid.appauth.*

class AuthRepository {

    fun getAuthRequest(): AuthorizationRequest {

        val serviceConfiguration = AuthorizationServiceConfiguration(
            Uri.parse(AuthConfig.AUTH_URI),
            Uri.parse(AuthConfig.TOKEN_URI)
        )

        val redirectUri = Uri.parse(AuthConfig.CALLBACK_URL)
        return AuthorizationRequest.Builder(
            serviceConfiguration,
            AuthConfig.CLIENT_ID,
            AuthConfig.RESPONSE_TYPE,
            redirectUri
        )
            .setPrompt("force")
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