package com.example.frombefore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_write_message.*
import kotlinx.android.synthetic.main.write_back_message.view.*

class WriteMessage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write_message)
        btn_send_msg_server2.setOnClickListener {
            val str = msgEditText2.text.toString()
            val data = MessageData(UserInfo.readFile(this, "d_day").toInt(), str)
            val code = MessageSetterTask(this).execute(data).get()
        }
    }

}