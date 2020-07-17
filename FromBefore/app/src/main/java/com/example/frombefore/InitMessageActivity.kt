package com.example.frombefore

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_init_message.*
import java.util.Calendar

class InitMessageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_init_message)
        val tmp = desc.text
        desc.text= intent.extras?.get("d_day").toString() + tmp
        btn_back.setOnClickListener {
            finish()
        }
        nextBtn.setOnClickListener {
            if(finalMessageInput.text.toString() == ""){
                val builder = AlertDialog.Builder(this)
                builder.setMessage("목표를 이룬 나에게 메세지를 작성 해 주세요.")
                builder.setPositiveButton(
                    "OK",
                    DialogInterface.OnClickListener { dialogInterface, id -> }) //아무것도 안하는데 OK만들어주고 싶어서 넣음
                builder.create()
                builder.show()
            }else{
                // 이 액티비티가 받은 인텐트의 내용을 또 보내줘야함
                val received = intent
                val i = Intent(this, CalendarActivity::class.java)
                val cal = Calendar.getInstance()

                // 받은 내용으로 Calendar 초기화
                cal.set(Calendar.YEAR, received.extras!!.getInt("year"))
                cal.set(Calendar.MONTH, received.extras!!.getInt("month"))
                cal.set(Calendar.DAY_OF_MONTH, received.extras!!.getInt("dayOfMonth"))

                // 인텐트에 넣어 보낼 데이터 클래스 객체 생성
                var userInfo = UserInfo(goalDate = cal, d_day = 10, maxCombo = 0, finalMessage = finalMessageInput.text.toString())
                i.putExtra("userInfo", userInfo)
                i.putExtra("finalMessage", finalMessageInput.text)
                i.putExtra("year", received.extras?.getInt("year"))
                i.putExtra("month", received.extras?.getInt("month"))
                i.putExtra("dayOfMonth", received.extras?.getInt("dayOfMonth"))
                i.putExtra("d_day", received.extras?.getInt("d_day"))
                // 기존 백스택 모두 날려버리기
                i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(i)
            }
        }
    }


}