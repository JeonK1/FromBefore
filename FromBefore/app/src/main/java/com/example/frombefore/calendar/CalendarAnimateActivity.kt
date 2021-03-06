package com.example.frombefore.calendar

import android.animation.Animator
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.frombefore.R
import com.example.frombefore.manager.*
import kotlinx.android.synthetic.main.activity_calendar_animate.*
import org.json.JSONObject
import java.io.File
import java.util.*

class CalendarAnimateActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar_animate)
        val handler = Handler()
        initCalendar()
        animateCalendar()
    }

    private fun initCalendar() {
        todayDateTextView.text = MyCalendar.date.toString()

        yestdayDateTextView.text = MyCalendar.yesterday().toString()
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

                        // 일단 finalMessage 폴더가 있는지로 구분하지만 추후에 수정 필요할지도
//                        val file = getFileStreamPath("finalMessage")
//                        val intent : Intent
//                        Log.e("file", file.toString())
//                        Log.e("file", file.exists().toString())
//                        if (file != null && file.exists()) { //이미 초기화 완료시
//                            intent = Intent(applicationContext, MainLayoutActivity::class.java)
//                        }else{
//                            intent = Intent(applicationContext, InitDdayActivity::class.java)

                        //TODO
                        val intent : Intent = if (UserInfo.has("year") && UserInfo.has("subject") && UserInfo.has("d_day") ) {
                            Intent(applicationContext, MainLayoutActivity::class.java)
                        } else {
                            Intent(applicationContext, InitDdayActivity::class.java)
                        }

                        startActivity(intent)
                        finish()

                    }, SPLASH_TIME_OUT)
                }
            })
    }
}
