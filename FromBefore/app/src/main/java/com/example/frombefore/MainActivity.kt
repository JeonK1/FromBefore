package com.example.frombefore

import android.animation.Animator
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
        introMainLayout.animate()
            .alpha(1f)
            .setDuration(3000)
            .setListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {
            }

            override fun onAnimationCancel(animation: Animator?) {
            }

            override fun onAnimationStart(animation: Animator?) {
            }

            override fun onAnimationEnd(animation: Animator?) {
                //애니메이션 끝나면 이동
                Thread.sleep(1000)
                val i = Intent(applicationContext, CalendarAnimateActivity::class.java)
                startActivity(i)
                finish()
            }
        })
    }
}
