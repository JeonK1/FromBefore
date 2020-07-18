package com.example.frombefore

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.frombefore.DaySelectActivity.Companion.keys
import kotlinx.android.synthetic.main.activity_main.*
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader


class MainActivity : AppCompatActivity() {
    // 전환까지 기다리는 시간
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button.setOnClickListener {
            val i = Intent(this, CalendarAnimateActivity::class.java)
            startActivity(i)
        }
    }
}
