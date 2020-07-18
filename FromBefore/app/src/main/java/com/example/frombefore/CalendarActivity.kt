package com.example.frombefore

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_calendar.*
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.text.SimpleDateFormat
import java.util.*


class CalendarActivity : AppCompatActivity() {

    lateinit var scheduleRecyclerViewAdapter: CalendarRecyclerViewAdapter
    var progressNum=0
    var accStudyCntNum=0 // 누적 학습일

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)
        initDefaultValue()
        initTestCase()
        initCalendarView()

        val cheatkey = findViewById<LinearLayout>(R.id.check_key)
        cheatkey.setOnClickListener{
//            val i = Intent(this, GifTestActivity::class.java)
            val i = Intent(this, DaySelectActivity::class.java)
            startActivity(i)
//            finish()
        }
    }

    private fun initDefaultValue() {
        val values = ArrayList<String>()
        for(i in 0..UserInfo.keys.size-1){
            val os = openFileInput(UserInfo.keys[i])
            val br = BufferedReader(InputStreamReader(os))
            values.add(br.readLine())
        }
        tv_dday.text = "D-" + values[4].toString()
    }

    private fun initTestCase() {
        //테스트케이스용 함수
        accStudyCntNum=5 // 누적학습일
        accStudyCnt.setText(accStudyCntNum.toString())
        progressNum=10 // 진행도 퍼센트(progressBar)
        progressBar.setProgress(progressNum)

    }

    private fun initCalendarView() {

        scheduleRecyclerViewAdapter = CalendarRecyclerViewAdapter(this)

        tv_current_day.setText(SimpleDateFormat("M/dd", Locale.getDefault()).format(Calendar.getInstance().time))

        rv_schedule.layoutManager = GridLayoutManager(this, BaseCalendar.DAYS_OF_WEEK)
        rv_schedule.adapter = scheduleRecyclerViewAdapter
        rv_schedule.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL))
        rv_schedule.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        btn_send_msg.setOnClickListener {
            //메시지 전송 버튼
            val secondIntent = Intent(this, MsgSendToMeActivity::class.java)
            startActivity(secondIntent)
        }

    }

}