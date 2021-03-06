package com.example.frombefore.manager

import android.animation.Animator
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.frombefore.R
import com.example.frombefore.calendar.CalendarAnimateActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File


class MainActivity : AppCompatActivity() {
    // 전환까지 기다리는 시간
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // 첫 실행되는 액티비티에 있어야 합니다.
        // 시작 엑티비티가 바뀌면 이 코드도 옮겨주세요
        GlobalContext.setContext(this)

        // 내부 저장소 삭제 할 필요가 있을때 주석 푸세요
        //UserInfo().reset()

        introMainLayout.animate()
            .alpha(1f)
            .setDuration(1000)
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
