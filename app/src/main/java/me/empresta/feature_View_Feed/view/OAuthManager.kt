package me.empresta.feature_View_Feed.view

import android.content.Context
import android.net.Uri
import androidx.core.app.ActivityCompat.startActivityForResult
import net.openid.appauth.*



class OAuthManager(private val context: Context) {
    private val UA_OAUTH_ENDPOINT_URI = "https://wso2-gw.ua.pt/authorize"
    private val UA_OAUTH_REDIRECT_URI = "myapp://oauthcallback"
    private val UA_OAUTH_TOKEN_URI = "https://wso2-gw.ua.pt/token"
    private val UA_OAUTH_SCOPE = "openid"
    private val UA_OAUTH_RESPONSE_TYPE = "code"
    private val UA_OAUTH_CLIENT_ID = "u8vpP8lcTQLURxk8itbLvY4pySoa"
    private val UA_OAUTH_CLIENT_SECRET = "r5f7TTsA1wWTCbXjhznjFzf23sEa"
    private val REQUEST_CODE_AUTH = 1000
    // Create authorization service configuration
    private val serviceConfig = AuthorizationServiceConfiguration(
        Uri.parse(UA_OAUTH_ENDPOINT_URI),
        Uri.parse(UA_OAUTH_TOKEN_URI)
    )

    fun startIDPoauth2() {
        //requestAuthorization()
    }
    /*
    // Request authorization and obtain authorization code
    fun requestAuthorization() {
        val authRequestBuilder = AuthorizationRequest.Builder(
            serviceConfig,
            UA_OAUTH_CLIENT_ID,
            ResponseTypeValues.CODE,
            Uri.parse(UA_OAUTH_REDIRECT_URI)
        )
        authRequestBuilder.setScopes(UA_OAUTH_SCOPE)
        val authRequest = authRequestBuilder.build()
        val authService = AuthorizationService(context)
        val authIntent = authService.getAuthorizationRequestIntent(authRequest)
        context.startActivity(authIntent)
    }

    // Exchange authorization code for access token
    fun requestAccessToken() {
        val authService = AuthorizationService(context)
        val authResponse = AuthorizationResponse.Builder().fromUri(Uri.parse(UA_OAUTH_REDIRECT_URI)).build()
        val authException = AuthorizationException.fromIntent(context.intent)
        val tokenRequestBuilder = TokenRequest.Builder(
            serviceConfig,
            UA_OAUTH_CLIENT_ID
        )
            .setAuthorizationCode(authResponse.authorizationCode)
            .setRedirectUri(Uri.parse(UA_OAUTH_REDIRECT_URI))
        val tokenRequest = tokenRequestBuilder.build()
        authService.performTokenRequest(tokenRequest) { tokenResponse, exception ->
            if (exception != null) {
                // Authorization failed
            } else {
                // Authorization succeeded
                val accessToken = tokenResponse!!.accessToken
                val idToken = tokenResponse.idToken
                val refreshToken = tokenResponse.refreshToken
                val expiresIn = tokenResponse.accessTokenExpirationTime
            }
        }
    }

    // Access protected resource service
    fun requestProtectedResource() {
        val authService = AuthorizationService(context)
        val authResponse = AuthorizationResponse.Builder().fromUri(Uri.parse(UA_OAUTH_REDIRECT_URI)).build()
        val authException = AuthorizationException.fromIntent(context.intent)
        val tokenRequestBuilder = TokenRequest.Builder(
            serviceConfig,
            UA_OAUTH_CLIENT_ID
        )
            .setAuthorizationCode(authResponse.authorizationCode)
            .setRedirectUri(Uri.parse(UA_OAUTH_REDIRECT_URI))
        val tokenRequest = tokenRequestBuilder.build()
        authService.performTokenRequest(tokenRequest) { tokenResponse, exception ->
            if (exception != null) {
                // Authorization failed
            } else {
                // Authorization succeeded
                val accessToken = tokenResponse!!.accessToken
                val idToken = tokenResponse.idToken
                val refreshToken = tokenResponse.refreshToken
                val expiresIn = tokenResponse.accessTokenExpirationTime
            }
        }
    }*/


}

