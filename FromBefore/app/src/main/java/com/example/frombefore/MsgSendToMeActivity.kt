package com.example.frombefore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.ArrayAdapter
import android.widget.SpinnerAdapter
import androidx.core.widget.addTextChangedListener
import kotlinx.android.synthetic.main.activity_msg_send_to_me.*

class MsgSendToMeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_msg_send_to_me)
        buttonInit()
        spinnerInit()
        msgBoxInit()
    }

    private fun buttonInit() {
        btn_send_msg_server.setOnClickListener {
            //나에게 보내는편지 서버(?)로컬(?)에 편지 데이터 전송
        }
        btn_back.setOnClickListener {
            finish()
        }
    }

    private fun msgBoxInit() {
        msgEditText.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val maxWordCnt=100; // 최대입력수
                val input= msgEditText.text
                msgWordCnt.setText(input.length.toString() + "/" + maxWordCnt)
            }

        })
    }

    private fun spinnerInit() {
        val ddayList = arrayOf("D-100", "D-50", "D-40", "D-10", "D-1")
        val spinnerAdapter = ArrayAdapter(this, R.layout.spinner_item, ddayList)
        spinnerAdapter.setDropDownViewResource(R.layout.spinner_item)
        ddaySpinner.adapter = spinnerAdapter
        ddaySpinner.setSelection(0)
    }
}
