package com.example.frombefore.message

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.frombefore.R
import com.example.frombefore.manager.UserInfo
import com.example.frombefore.task.MessageGetterTask
import kotlinx.android.synthetic.main.activity_receive_message.*
import kotlinx.android.synthetic.main.item_schedule_today.tv_date

class ReceiveMessageActivity : AppCompatActivity() {
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
        tv_subject.text = UserInfo.get("subject").toString() +"을(를) 위해 공부하는"
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