package me.empresta.feature_QRCode_Connection.use_case.IDP

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.webkit.WebView
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Url
import java.io.IOException
import java.net.URI

interface ApiService {
    @GET
    fun authorize(@Url url: String): Call<ResponseBody>

    @FormUrlEncoded
    @POST
    fun exchangeCodeForToken(
        @Url url: String,
        @Field("code") code: String,
        @Field("redirect_uri") redirectUri: String,
        @Field("client_id") clientId: String,
        @Field("client_secret") clientSecret: String,
        @Field("grant_type") grantType: String
    ): Call<ResponseBody>

}

class IDPAuthenticator(private val context: Context) {
    val UA_OAUTH_AUTH_URI = "https://wso2-gw.ua.pt/authorize?"
    val UA_OAUTH_REDIRECT_URI = "http://localhost:3000"
    val UA_OAUTH_SCOPE = "openid"
    val UA_OAUTH_RESPONSE_TYPE = "code"
    val UA_OAUTH_CLIENT_ID = "u8vpP8lcTQLURxk8itbLvY4pySoa"
    val tokenUrl = "https://wso2-gw.ua.pt/token"
    val redirectUri = "myapp://oauthcallback"
    val clientId = "u8vpP8lcTQLURxk8itbLvY4pySoa"
    val grantType = "authorization_code"
    val clientSecret = "r5f7TTsA1wWTCbXjhznjFzf23sEa"

    // Create ApiService instance
    fun createApiService(): ApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://wso2-gw.ua.pt/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(ApiService::class.java)
    }

    // Get the authorization code from the redirect URL
    fun associateWithIDP(apiService: ApiService, context: Context) {
        val uaAuthUriComplete = buildURI("ua")

        Log.d("URI", "UA AUTH URI COMPLETE: ${uaAuthUriComplete.toString()}")

        // Create a WebView instance
        val webView = WebView(context)

        var authorizationCode: String? = null

        // Start the HTTP server
        val server = OAuthHttpServer()

        server.start()

        Log.d("LOADURL", "Load url to webview ${uaAuthUriComplete.toString()}")

        // Load the authorization URL in the WebView
        webView.loadUrl(uaAuthUriComplete.toString())
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uaAuthUriComplete.toString()))
        context.startActivity(intent)


        // Start the HTTP server to receive the authorization code
        server.openServer()

        // Get the authorization code from the redirect URL
        authorizationCode = server.getReceivedCode()
        print("AUTHORIZATION CODE: $authorizationCode")
        Log.d("AUTHORIZATION CODE", "AUTHORIZATION CODE: $authorizationCode")
    }

    // Function to perform the exchangeCodeForToken request
    fun exchangeCodeForToken(authorizationCode: String?, context: Context) {
        val requestBody = "code=$authorizationCode&redirect_uri=$redirectUri&grant_type=$grantType"
            .toRequestBody("application/x-www-form-urlencoded".toMediaType())

        val client = OkHttpClient()
        val request = Request.Builder()
            .url(tokenUrl)
            .addHeader("Authorization", "Basic ${base64Encode("$clientId:$clientSecret")}")
            .post(requestBody)
            .build()

        client.newCall(request).enqueue(object : okhttp3.Callback {
            override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
                val responseBody = response.body?.string()
                if (response.isSuccessful && responseBody != null) {
                    val accessToken = extractAccessToken(responseBody)
                    Log.d("ACCESS TOKEN", "Access token: $accessToken")

                    // Handle the access token and resume the session
                    onTokenReceived(accessToken, context)
                } else {
                    // Handle unsuccessful response
                    // Log the error or perform any desired actions
                }
            }

            override fun onFailure(call: okhttp3.Call, e: IOException) {
                // Handle failure
                // Log the error or perform any desired actions
                Log.d("FAILED", "Request failed with exception: ${e.message}")
            }
        })
    }


    // Function to handle the access token and resume the session
    private fun onTokenReceived(accessToken: String, context: Context) {
        // Perform the necessary actions with the access token
        // For example, save it to a session manager or initiate API requests

        // Redirect back to the application using a custom URL scheme
        val redirectUri = "myapp://oauthcallback?access_token=$accessToken"
        Log.d("REDIRECT URI", "REDIRECT URI: $redirectUri")
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(redirectUri))
        Log.d("REDIRECT", "Redirecting to the app")
        context.startActivity(intent)
    }


    fun extractAccessToken(responseBody: String): String {
        val json = JSONObject(responseBody)
        return json.optString("access_token", "")
    }

    fun extractAuthorizationCode(responseBody: String?): String? {
        val uri = Uri.parse(responseBody)
        return uri.getQueryParameter("code")
    }

    fun base64Encode(value: String): String {
        return android.util.Base64.encodeToString(value.toByteArray(), android.util.Base64.NO_WRAP)
    }

    private fun buildURI(provider: String): URI {
        if (provider == "ua") {
            val uaAuthUriComplete = URI("${UA_OAUTH_AUTH_URI}redirect_uri=${UA_OAUTH_REDIRECT_URI}&scope=${UA_OAUTH_SCOPE}&response_type=${UA_OAUTH_RESPONSE_TYPE}&client_id=${UA_OAUTH_CLIENT_ID}")
            return uaAuthUriComplete
        }
        throw IllegalArgumentException("Invalid login provider")
    }
}

