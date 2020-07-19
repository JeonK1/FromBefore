package com.example.frombefore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import kotlinx.android.synthetic.main.activity_msg_send_to_me.*
import kotlinx.android.synthetic.main.activity_write_message.*

class WriteMessage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write_message)
        buttonInit()
        msgBoxInit()
    }

    private fun buttonInit() {
        btn_send_msg_server2.setOnClickListener {
            //Todo : send to server
            finish()
        }
        btn_back2.setOnClickListener {
            finish()
        }
    }
    private fun msgBoxInit() {
        msgEditText2.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val maxWordCnt=100; // 최대입력수
                val input= msgEditText2.text
                msgWordCnt2.setText(input.length.toString() + "/" + maxWordCnt)
            }

        })
    }
}