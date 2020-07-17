package com.example.frombefore

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_calendar.*
import java.text.SimpleDateFormat
import java.util.*


class CalendarActivity : AppCompatActivity() {

    lateinit var scheduleRecyclerViewAdapter: CalendarRecyclerViewAdapter
    var progressNum=0
    var accStudyCntNum=0 // 누적 학습일

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)
        initTestCase()
        initCalendarView()
    }

    private fun initTestCase() {
        //테스트케이스용 함수
        accStudyCntNum=5 // 누적학습일
        accStudyCnt.setText(accStudyCntNum.toString())
        progressNum=10 // 진행도 퍼센트(progressBar)
        progressBar.setProgress(progressNum)

        tv_dday.text = "D-" + intent.extras?.getInt("d_day").toString()
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