package com.example.frombefore

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader


class MainActivity : AppCompatActivity() {
    // 전환까지 기다리는 시간
    private val SPLASH_TIME_OUT : Long = 1000
    override fun onCreate(savedInstanceState: Bundle?) {
        Handler().postDelayed(Runnable {
            setTheme(R.style.AppTheme)

            //내부 저장소 삭제 할 필요가 있을때 주석 푸세요
//            val dir: File = filesDir
//            for(i in 0..UserInfo.keys.size-1){
//                val fm = File(dir, UserInfo.keys[i])
//                val deleted: Boolean = fm.delete()
//            }

            val file = getFileStreamPath("finalMessage")
            val intent : Intent
            if (file != null && file.exists()) { //이미 초기화 완료시
                intent = Intent(this, CalendarActivity::class.java)
            }else{
                intent = Intent(this, InitDdayActivity::class.java)
            }
            startActivity(intent)
            finish()

        }, SPLASH_TIME_OUT)
        super.onCreate(savedInstanceState)
    }
}
