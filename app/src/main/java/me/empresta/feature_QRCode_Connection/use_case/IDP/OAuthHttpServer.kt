package me.empresta.feature_QRCode_Connection.use_case.IDP

import fi.iki.elonen.NanoHTTPD

class OAuthHttpServer() : NanoHTTPD(3000) {
    private var isServerOpen = false
    private var receivedCode: String? = null

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
            val redirectUri = "myapp://www.mycoolapp.com/Android?code=$receivedCode"

            // Create the redirect response
            val response = newFixedLengthResponse(
                Response.Status.REDIRECT,
                MIME_HTML,
                "<html><body><a href=\"$redirectUri\">Redirect</a></body></html>"
            )

            // Set the Location header to the redirect URI
            response.addHeader("Location", redirectUri)

            // Return the redirect response
            return response
        }

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
