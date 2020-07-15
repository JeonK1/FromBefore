package com.example.frombefore

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.DatePicker
import kotlinx.android.synthetic.main.activity_init_dday.*
import java.text.SimpleDateFormat
import java.util.*

class InitDdayActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_init_dday)
//        val listener = DatePickerDialog.OnDateSetListener { DatePicker(this), yy, mm, dd ->
//            textView2.text = String.format("%d-%d-%d", yy,mm+1,dd)
//        }
//        DatePickerDialog.OnDateSetListener(){
//
//        }
//        button2.setOnClickListener{
//            DatePickerDialog(this,listener, 2020, 7, 15)
//        }

        var cal = Calendar.getInstance()
        val mDateListener = DatePickerDialog.OnDateSetListener { datePicker, year, monthOfYear, dayOfMonth ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, monthOfYear)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val myFormat = "yyyy.MM.dd"
            val sdf = SimpleDateFormat(myFormat, Locale.KOREA)
            textView2.text = sdf.format(cal.time)
        }

        button2.setOnClickListener {
            DatePickerDialog(this, mDateListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)).show()
        }
        button3.setOnClickListener {
            val i = Intent(this, InitMessageActivity::class.java)
            startActivity(i)
        }
    }

}