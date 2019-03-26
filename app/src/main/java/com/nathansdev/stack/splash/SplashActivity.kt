package com.nathansdev.stack.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.nathansdev.stack.AppPreferences
import com.nathansdev.stack.R
import com.nathansdev.stack.auth.LoginActivity
import com.nathansdev.stack.base.BaseActivity
import com.nathansdev.stack.home.HomeActivity
import javax.inject.Inject

class SplashActivity : BaseActivity() {

    @Inject
    lateinit var appPreferences: AppPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme_Launcher)
        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        routeTo()
    }

    private fun routeTo() {
        Handler().postDelayed({
            if (appPreferences.isLoggedIn) {
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }, 1000)
    }
}