package com.example.frombefore

import android.animation.Animator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_calendar_animate.*
import java.io.File

class CalendarAnimateActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar_animate)
        val handler = Handler()
        initCalendar()
        animateCalendar()
    }

    private fun initCalendar() {
        val todayDate = 19
        val yestdayDate = 18
        todayDateTextView.setText(todayDate.toString())
        yestdayDateTextView.setText(yestdayDate.toString())
    }

    private fun animateCalendar() {
        yestdayPage.animate()
            .alpha(0f)
            .setDuration(500)
            .setStartDelay(1000)
        yestdayPage.animate()
            .translationY(800f)
            .setDuration(1000)
            .setStartDelay(1000)
            .setListener(object: Animator.AnimatorListener{
                override fun onAnimationRepeat(animation: Animator?) {
                }

                override fun onAnimationCancel(animation: Animator?) {
                }

                override fun onAnimationStart(animation: Animator?) {
                }

                override fun onAnimationEnd(animation: Animator?) {
                    //애니메이션 끝나면 이동
                    Thread.sleep(1000)

                    val SPLASH_TIME_OUT : Long = 1000
                    Handler().postDelayed(Runnable {
                        setTheme(R.style.AppTheme)

                        //내부 저장소 삭제 할 필요가 있을때 주석 푸세요
                        val dir: File = filesDir
                        for(i in 0..UserInfo.keys.size-1){
                            val fm = File(dir, UserInfo.keys[i])
                            val deleted: Boolean = fm.delete()
                        }

                        val file = getFileStreamPath("finalMessage")
                        val intent : Intent
                        if (file != null && file.exists()) { //이미 초기화 완료시
                            intent = Intent(applicationContext, CalendarActivity::class.java)
                        }else{
                            intent = Intent(applicationContext, InitDdayActivity::class.java)
                        }
                        startActivity(intent)
                        finish()

                    }, SPLASH_TIME_OUT)
                }
            })
    }
}
