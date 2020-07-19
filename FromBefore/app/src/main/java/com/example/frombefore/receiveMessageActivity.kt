package com.example.frombefore

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import kotlinx.android.synthetic.main.activity_msg_send_to_me.*
import kotlinx.android.synthetic.main.activity_msg_send_to_me.btn_back
import kotlinx.android.synthetic.main.activity_receive_message.*
import kotlinx.android.synthetic.main.activity_write_message.*
import kotlinx.android.synthetic.main.item_schedule_today.*
import kotlinx.android.synthetic.main.item_schedule_today.tv_date
import kotlinx.android.synthetic.main.received_message.view.*
import kotlinx.android.synthetic.main.received_message.view.btn_write_back
import kotlinx.android.synthetic.main.received_message.view.tv_date
import kotlinx.android.synthetic.main.received_message.view.tv_from_d_day
import kotlinx.android.synthetic.main.received_message.view.tv_main_text

class receiveMessageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receive_message)
        init()
        buttonInit()
    }

    private fun buttonInit() {
        btn_write_back.setOnClickListener {
            val intent = Intent(this, WriteMessage::class.java)
            startActivity(intent)
        }
        btn_skip.setOnClickListener {
            finish()
        }
    }

    private fun init() {

        tv_subject.text = UserInfo.readFile(this, "subject")+"을(를) 위해 공부하는"
        val data = MessageGetterTask(this).execute(1).get()
        tv_date.text = "날짜가 올 자리" //날짜
        tv_from_d_day.text = "D-" + data[0].d_day.toString() + "의 누군가로부터"//d-n의 누군가로부터
        tv_main_text.text =  data[0].text//본문
        btn_write_back.setOnClickListener {
            //TODO : 출석 버튼 눌렀을때 이후 작업
        }
        btn_skip.setOnClickListener {
            finish()
        }
    }
}