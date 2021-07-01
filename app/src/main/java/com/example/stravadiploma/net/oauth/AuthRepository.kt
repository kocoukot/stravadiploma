package com.example.stravadiploma.net.oauth

import android.content.Context
import android.net.Uri
import com.example.stravadiploma.utils.Constants
import com.example.stravadiploma.utils.logInfo
import net.openid.appauth.*

class AuthRepository(context: Context) {

    private val sharedPref by lazy {
        context.getSharedPreferences(
            Constants.SHARED_PREF,
            Context.MODE_PRIVATE
        )
    }

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
                    logInfo(accessToken)
                    sharedPref.edit()
                        .putString(Constants.ACCESS_TOKEN, accessToken)
                        .putLong(
                            Constants.ACCESS_TOKEN_EXPIRATION,
                            response.accessTokenExpirationTime ?: 0
                        )
                        .apply()
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
    var expTime: Long = 0
}