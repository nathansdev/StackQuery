package com.nathansdev.stack.auth

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import com.nathansdev.stack.AppConstants
import com.nathansdev.stack.AppPreferences
import com.nathansdev.stack.R
import com.nathansdev.stack.base.BaseActivity
import com.nathansdev.stack.home.HomeActivity
import timber.log.Timber
import javax.inject.Inject


/**
 * A login screen that offers login via email/password.
 */
class LoginActivity : BaseActivity() {

    @Inject
    lateinit var appPreferences: AppPreferences

    private var progressBar: ProgressBar? = null
    private var buttonLogin: Button? = null
    private var buttonSkip: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.nathansdev.stack.R.layout.activity_login)
        buttonLogin = findViewById(com.nathansdev.stack.R.id.button_auth)
        buttonSkip = findViewById(com.nathansdev.stack.R.id.button_skip_login)
        progressBar = findViewById(com.nathansdev.stack.R.id.progress_loading)
        progressBar?.visibility = View.GONE
        buttonLogin?.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW,
                    Uri.parse(AppConstants.AUTH_URL + "?" + AppConstants.CLIENT_ID + "=" + getString(R.string.client_id) +
                            "&" + AppConstants.REDIRECT_URI + "=" + getString(R.string.redirect_uri)))
            startActivity(intent)
            showProgress()
        }
        buttonSkip?.setOnClickListener { routeToHome() }
        Timber.d("onCreate")
    }

    override fun onResume() {
        super.onResume()
        Timber.d("onResume %s", intent)
        handleIntent()
    }

    private fun handleIntent() {
        // the intent filter defined in AndroidManifest will handle the return from ACTION_VIEW intent
        val uri = intent.data
        Timber.d("uri %s", uri)
        if (uri != null && uri.toString().startsWith(getString(R.string.redirect_uri))) {
            val extra = uri.fragment
            Timber.d("extra %s", extra)
            val accessToken = extra?.split("&")?.get(0)?.split("=")?.get(1)
            Timber.d("token %s", accessToken)
            if (accessToken != null) {
                appPreferences.setIsLoggedIn(true)
                appPreferences.accessToken = accessToken
                // get access token
                // we'll do that in a minute
                routeToHome()
            } else if (uri.getQueryParameter(AppConstants.ERROR) != null) {
                hideProgress()
                val error = uri.getQueryParameter(AppConstants.ERROR)
                Timber.d(error)
                // show an error message here
            }
        } else {
            hideProgress()
        }
    }

    private fun showProgress() {
        progressBar?.visibility = View.VISIBLE
        buttonLogin?.visibility = View.GONE
    }

    private fun hideProgress() {
        progressBar?.visibility = View.GONE
        buttonLogin?.visibility = View.VISIBLE
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        Timber.d("onNewIntent %s", intent)
    }

    private fun routeToHome() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }
}
