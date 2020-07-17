package com.example.frombefore

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    // 전환까지 기다리는 시간
    private val SPLASH_TIME_OUT : Long = 1000
    override fun onCreate(savedInstanceState: Bundle?) {
        Handler().postDelayed(Runnable {
            setTheme(R.style.AppTheme)
            // 어떤 화면으로 전환될지 결정 가능
            val i = Intent(this, InitDdayActivity::class.java)
            startActivity(i)
            finish()
        }, SPLASH_TIME_OUT)
        super.onCreate(savedInstanceState)
    }
}
