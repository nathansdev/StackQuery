package com.nathansdev.stack.auth

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import com.nathansdev.stack.AppConstants
import com.nathansdev.stack.AppPreferences
import com.nathansdev.stack.base.BaseActivity
import timber.log.Timber
import javax.inject.Inject


/**
 * A login screen that offers login via email/password.
 */
class LoginActivity : BaseActivity() {

    @Inject
    lateinit var appPreferences: AppPreferences

    private val clientId = "14730"
    private val redirectUri = "https://stack-query.herokuapp.com"
    private var progressBar: ProgressBar? = null
    private var button: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.nathansdev.stack.R.layout.activity_login)
        button = findViewById(com.nathansdev.stack.R.id.button_auth)
        progressBar = findViewById(com.nathansdev.stack.R.id.progress_loading)
        progressBar?.visibility = View.GONE
        button?.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW,
                    Uri.parse(AppConstants.AUTH_URL + "?" + AppConstants.CLIENT_ID + "=" + clientId +
                            "&" + AppConstants.REDIRECT_URI + "=" + redirectUri))
            startActivity(intent)
            progressBar?.visibility = View.VISIBLE
            button?.visibility = View.GONE
        }
        Timber.d("onCreate")
    }

    override fun onResume() {
        super.onResume()
        Timber.d("onResume %s", intent)
        handleIntent()
    }

    private fun handleIntent() {
        progressBar?.visibility = View.GONE
        button?.visibility = View.VISIBLE
        // the intent filter defined in AndroidManifest will handle the return from ACTION_VIEW intent
        val uri = intent.data
        Timber.d("uri %s", uri)
        if (uri != null && uri.toString().startsWith(redirectUri)) {
            val extra = uri.fragment
            Timber.d("extra %s", extra)
            val accessToken = extra?.split("&")?.get(0)?.split("=")?.get(1)
            Timber.d("token %s", accessToken)
            if (accessToken != null) {
                appPreferences.setIsLoggedin(true)
                appPreferences.accessToken = accessToken
                // get access token
                // we'll do that in a minute
            } else if (uri.getQueryParameter(AppConstants.ERROR) != null) {
                val error = uri.getQueryParameter(AppConstants.ERROR)
                Timber.d(error)
                // show an error message here
            }
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        Timber.d("onNewIntent %s", intent)
    }
}
