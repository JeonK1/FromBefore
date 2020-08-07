package com.example.frombefore.message

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import com.example.frombefore.R
import com.example.frombefore.manager.UserInfo
import com.example.frombefore.task.MessageSetterTask
import kotlinx.android.synthetic.main.activity_write_message.*

class WriteMessage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write_message)
        buttonInit()
        msgBoxInit()
    }

    private fun buttonInit() {

        // set button handlers
        msgEditText2.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val maxWordCnt=100; // 최대입력수
                val input= msgEditText2.text
                msgWordCnt2.text = input.length.toString() + "/" + maxWordCnt
            }
        })

        btn_send_msg_server2.setOnClickListener {
            val str = msgEditText2.text.toString()
            val ui = UserInfo()
            val jsonObj = ui.readFile()
            val data = MessageData(
                jsonObj.get("d_day").toString().toInt(),
                str,
                jsonObj.get("subject").toString()
            )
            val code = MessageSetterTask(this).execute(data).get()

            finish()
        }
    }

    private fun msgBoxInit() {
        // set texts
        val ui = UserInfo()
        val jsonObj = ui.readFile()
        val subject = jsonObj.get("subject").toString()
        write_message_title_top.text = subject + "을(를) 위해 공부하는"

        val dDay =  jsonObj.get("d_day").toString()
        write_message_title_top2.text = "D-" + dDay + "의 누군가로부터"//d-n의 누군가로부터
    }

}