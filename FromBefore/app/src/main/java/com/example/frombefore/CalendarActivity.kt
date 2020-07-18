package com.example.frombefore

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_calendar.*
import kotlinx.android.synthetic.main.attendbox_dialog.view.*
import kotlinx.android.synthetic.main.attendbox_dialog.view.attendBtn
import kotlinx.android.synthetic.main.received_message.view.*
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
        tv_dday.text = "D-" + UserInfo.readFile(this, "d_day")
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
        btn_receive_letter.setOnClickListener {
            //남에게 온 편지함 버튼
            val mDialogView = layoutInflater.inflate(R.layout.received_message, null)
            val mBuilder = AlertDialog.Builder(this).setView(mDialogView)
            val mAlertDialog = mBuilder.show()
            mAlertDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

            mDialogView.tv_subject.text = UserInfo.readFile(this, "subject")+"을(를) 위해 공부하는"

            val data = MessageGetterTask(this).execute(1).get()
            mDialogView.tv_date.text = "날짜가 올 자리" //날짜
            mDialogView.tv_from_d_day.text = "D-" + data[0].d_day.toString() + "의 누군가로부터"//d-n의 누군가로부터
            mDialogView.tv_main_text.text =  data[0].text//본문
            mDialogView.btn_write_back.setOnClickListener {
                //TODO : 출석 버튼 눌렀을때 이후 작업
            }
            mDialogView.btn_skip.setOnClickListener {
                mAlertDialog.dismiss()
            }
        }

    }

}