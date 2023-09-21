package com.example.pin

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.songssam.DB.PreferenceUtil


class SplashActivity : AppCompatActivity() {
    companion object {
        lateinit var prefs: PreferenceUtil
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashTime:Long = 1000
        super.onCreate(savedInstanceState)
        // 상단 액션바 숨기기
        prefs = PreferenceUtil(applicationContext)
        supportActionBar?.hide()
        setContentView(R.layout.activity_splash)
        Handler().postDelayed({
            // This method will be executed once the timer is over
            // Start your app main activity
            startActivity(Intent(this, MainActivity::class.java))
            // close this activity
            finish()
        }, splashTime)
    }

}