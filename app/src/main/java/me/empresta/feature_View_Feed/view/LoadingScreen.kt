package me.empresta.feature_View_Feed.view

import OAuthHttpServer
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import me.empresta.R

class LoadingScreen : AppCompatActivity()  {
    private val context: Context = this
    private val auth = IDPAuthenticator(context)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set your loading screen layout here
        setContentView(R.layout.activity_loading)

        // Start the server
        val server = OAuthHttpServer()
        server.openServer()

        // Handle the redirect after a delay
        Handler().postDelayed({
            val receivedCode = server.getReceivedCode()
            if (receivedCode != null) {
                // Handle the received code and perform necessary processing
                auth.exchangeCodeForToken(receivedCode, context)
            }
        }, 3000) // Adjust the delay time as needed
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        // Handle the custom scheme URI when the activity is launched
        val uri: Uri? = intent?.data
        if (uri != null && uri.scheme == "your_custom_scheme") {
            val receivedCode = uri.getQueryParameter("code")
            if (receivedCode != null) {
                // Handle the received code and perform necessary processing
                auth.exchangeCodeForToken(receivedCode, context)
            }
        }
    }



}
