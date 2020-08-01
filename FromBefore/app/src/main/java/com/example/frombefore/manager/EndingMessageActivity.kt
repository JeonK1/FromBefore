package com.example.frombefore.manager

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.frombefore.R
import kotlinx.android.synthetic.main.activity_ending_message.*

class EndingMessageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ending_message)
        megInit()
        buttonInit()
    }

    private fun buttonInit() {
        btn_ending_msg_me.setOnClickListener {
            val i = Intent(this, EndingMessageAllActivity::class.java)
            startActivity(i)
            finish()
        }
    }

    private fun megInit() {
        val message = "목표를 이룬 당신,\n정말 축하드립니다\n\n그 기쁜 마음은\n누구보다 본인이\n가장 잘 느끼고 있을거에요\n" +
                "무척 긴 여정이었을지도\n혹은 짧은 순간이었을지도 모르지만\n초심을 간직하고\n여기까지 달려온 순간을\n늘 잊지 않기를 바랍니다\n\n" +
                "저희와 함께\n끝까지 함께해 주셔서\n정말 감사합니다"
        endingCongratMessage.text = message
    }
}
