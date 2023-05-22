import android.content.Context
import fi.iki.elonen.NanoHTTPD
import me.empresta.feature_View_Feed.view.IDPAuthenticator

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
            receivedCode = parameters["code"]?.get(0)
            //return newFixedLengthResponse("Received code: $receivedCode")
            // Redirect to the application using a custom scheme URI
            val redirectUri = "redirect://oauth?code=${receivedCode}"
            println("REDIRECT URI: $redirectUri")
            exchangeForAccessToken()
            return newFixedLengthResponse(
                Response.Status.REDIRECT,
                NanoHTTPD.MIME_PLAINTEXT,
                redirectUri
            )
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
