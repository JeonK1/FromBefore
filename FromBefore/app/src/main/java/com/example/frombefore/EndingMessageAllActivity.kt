package com.example.frombefore

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_ending_message_all.*

class EndingMessageAllActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ending_message_all)
        initMyMessage()
        initbtn()
    }

    private fun initbtn() {
        btn_ending_msg_all.setOnClickListener {
            val i = Intent(this, EndingActivity::class.java)
            startActivity(i)
            finish()
        }
    }

    private fun initMyMessage() {
        val message = "벌써 끝이라니 믿기지가 않는다\n정말 많이 힘들었지?\n여태껏 보낸 모든 시간들이\n" +
                "모두 값진 추억과 기억으로 남았으면\n그리고 모든 게 끝난 지금\n누구보다 재밌게 즐기고\n이 추억을 평생 간직하자\n너무 수고했어!"
        val firstDDay = 100 // 처음 시작할 때 D 몇이었는지
        msgfromMeDdayTextView.text = "D-"+firstDDay.toString()+msgfromMeDdayTextView.text
        endingCongratMessageAll.text = message
    }
}
