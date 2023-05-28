package me.empresta.feature_QRCode_Connection.use_case.IDP

import android.util.Log
import fi.iki.elonen.NanoHTTPD


class OAuthHttpServer(url: String) : NanoHTTPD(3000) {
    private var isServerOpen = false
    private var receivedCode: String? = null
    private var url = url

    override fun serve(session: IHTTPSession): Response {
        // Check if the server is open to receive requests
        if (!isServerOpen) {
            return newFixedLengthResponse("Server not available")
        }

        // Get the full url
        val parameters = session.parameters

        // Log both
        println("Parameters: $parameters")

        // Respond with the code parameter if it is present
        if (parameters.containsKey("code")) {
            val receivedCode = parameters["code"]?.get(0)
            val redirectUri = "myapp://Android?code=$receivedCode"

            Log.d("SERVER", "Redirecting to the server $url")

            // Send the code to the Community server
            //var api = AppModule.provideBuilder()

            Log.d("COMMUNITY URL", "Community Url: $url")
            /*
            GlobalScope.launch {
                if (receivedCode != null) {
                    Log.d("API CALL", "Sending auth code to http://$url/auth/associate")
                    print(api.getInfo("http://$url/meta/info"))
                    print(api.postAssociate("http://$url/auth/associate", receivedCode))
                };
            }*/
            /*
            // Create the redirect response
            val response = newFixedLengthResponse(
                Response.Status.REDIRECT,
                MIME_HTML,
                "<html><body><a href=\"$redirectUri\">Redirect</a></body></html>"
            )*/
            val htmlResponse = "<html><body><a href=\"$redirectUri\">Redirect</a></body></html>"
            val response = newFixedLengthResponse(
                Response.Status.REDIRECT,
                MIME_HTML,
                htmlResponse
            )

            // Set the Location header to the redirect URI
            response.addHeader("Location", redirectUri)
            // Return the redirect response
            return newFixedLengthResponse("<html><body><a href=\"$redirectUri\">Redirect</a></body></html>")
        }

        //closeServer()

        // Return a response indicating that the server is waiting for the code parameter
        return newFixedLengthResponse("Waiting for code parameter")
    }

    private fun exchangeForAccessToken() {
        TODO("Not yet implemented")
    }

    fun openServer() {
        isServerOpen = true
    }

    fun closeServer() {
        isServerOpen = false
        stop()
    }

    fun getReceivedCode(): String? {
        return receivedCode
    }
}
