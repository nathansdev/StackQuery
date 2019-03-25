package com.nathansdev.stack.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.nathansdev.stack.R
import com.nathansdev.stack.base.BaseActivity
import com.nathansdev.stack.home.HomeActivity

class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme_Launcher)
        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        routeToHome()
    }

    private fun routeToHome() {
        Handler().postDelayed({
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }, 1000)
    }
}