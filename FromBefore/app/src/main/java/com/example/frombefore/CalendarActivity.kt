package com.example.frombefore

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
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
        tv_dday.text = "D-" + UserInfo.readFile(this, "d_day")
    }

    private fun initTestCase() {
        //테스트케이스용 함수
        accStudyCntNum=5 // 누적학습일
        accStudyCnt.setText(accStudyCntNum.toString())
        progressNum=10 // 진행도 퍼센트(progressBar)
//        progressBar.setProgress(progressNum)

    }
    private fun initCalendarView() {

        scheduleRecyclerViewAdapter = CalendarRecyclerViewAdapter(this)

        tv_current_day.setText(SimpleDateFormat("M월 dd일", Locale.getDefault()).format(Calendar.getInstance().time))

        val calendarFB = CalendarFB(this, tableLayout)

        btn_send_msg.setOnClickListener {
            //메시지 전송 버튼
            val secondIntent = Intent(this, MsgSendToMeActivity::class.java)
            startActivity(secondIntent)
        }

        if(/* 나에게 온 편지가 있을 때*/1==1){
            btn_receive_letter.setImageResource(R.drawable.letter_exist)
            btn_receive_letter.setOnClickListener {
                //나에게 온 편지가 있는 경우 버튼 클릭 했을 때
                //TODO : 서버에서 해당 편지 내용 가져와서 읽기
                val intent = Intent(this, ReceiveMessageActivity::class.java)
                startActivity(intent)
            }
        }

//        else{
//            //TODO : 읽을 펼지가 없다고 메시지 출력하는데 편지 읽는 dialog랑 형식 맞출꺼라 일단 주석
//        }

    }
}