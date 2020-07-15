package com.example.frombefore

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_init_message.*
import java.util.Calendar

class InitMessageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_init_message)
        nextBtn.setOnClickListener {
            val received = intent
            val i = Intent(this, CalendarActivity::class.java)
            val cal = Calendar.getInstance()
            cal.set(Calendar.YEAR, received.extras!!.getInt("year"))
            cal.set(Calendar.MONTH, received.extras!!.getInt("month"))
            cal.set(Calendar.DAY_OF_MONTH, received.extras!!.getInt("dayOfMonth"))
            var userInfo = UserInfo(goalDate = cal, d_day = 10, maxCombo = 0, finalMessage = finalMessageInput.text.toString())
            i.putExtra("userInfo", userInfo)
            i.putExtra("finalMessage", finalMessageInput.text)
            i.putExtra("year", received.extras?.getInt("year"))
            i.putExtra("month", received.extras?.getInt("month"))
            i.putExtra("dayOfMonth", received.extras?.getInt("dayOfMonth"))
            startActivity(i)
        }
    }


}