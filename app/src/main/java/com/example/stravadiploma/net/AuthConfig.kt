package com.example.androidhomework.gitHub.net

import net.openid.appauth.ResponseTypeValues

object AuthConfig {

    const val AUTH_URI = "https://www.strava.com/oauth/mobile/authorize"
    const val TOKEN_URI = "https://www.strava.com/oauth/token"
    const val RESPONSE_TYPE = ResponseTypeValues.CODE

    const val SCOPE = "profile:read_all,profile:write,activity:read_all,activity:write"
    //    const val SCOPE = "profile:read_all"
    const val CLIENT_ID = "67063"
   const val CLIENT_SECRET = "53e36fe5b80784f4315d9a92804d4949ac7b663d"
    const val CALLBACK_URL = "skillbox://skillbox.ru/callback"
}

