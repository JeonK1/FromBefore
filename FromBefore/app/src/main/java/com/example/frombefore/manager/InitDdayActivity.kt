package com.example.frombefore.manager

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.frombefore.R
import kotlinx.android.synthetic.main.activity_init_dday.*
import java.text.SimpleDateFormat
import java.util.*

class InitDdayActivity : AppCompatActivity() {
    lateinit var pickedCalendar: Calendar
    var dayArray = arrayOf(0,0,0,0,0,0,0) //일~토 선택여부
    var d_day = 0
    var subject = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_init_dday)

        // 캘린더 초기화
        var cal = Calendar.getInstance()
        val mDateListener =
                DatePickerDialog.OnDateSetListener { datePicker, year, monthOfYear, dayOfMonth ->
                    datePicker.minDate = System.currentTimeMillis()
                    cal.set(Calendar.YEAR, year)
                    cal.set(Calendar.MONTH, monthOfYear)
                    cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                    pickedCalendar = cal
                    val myFormat = "yyyy.MM.dd"
                    val sdf = SimpleDateFormat(myFormat, Locale.KOREA)
//            debug.text = sdf.format(cal.time)
                    val tmp = Calendar.getInstance()
                    val now_day = tmp.timeInMillis //현재 시간
                    val event_day = pickedCalendar.timeInMillis //목표일에 대한 시간
                    val d_day = (event_day - now_day) / (60 * 60 * 24 * 1000)
                    this.d_day = (d_day+1).toInt()  // 인텐트로 넘길 값
                    ddayTextView.text = "D-"+(d_day+1).toString()
                }
        ddayBtn.setOnClickListener {
            var dp = DatePickerDialog(
                    this,
                R.style.DatePickerDialogTheme, mDateListener,
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)
            )
            dp.datePicker.minDate = System.currentTimeMillis()
            dp.show()
        }


        // 다음 버튼 초기화
        nextBtn.setOnClickListener {
            if (!(::pickedCalendar.isInitialized)) { //초기화 되어 있는지 확인
                val builder = AlertDialog.Builder(this)
                builder.setMessage("목표 날짜를 선택해 주세요.")
                builder.setPositiveButton(
                        "OK",
                        DialogInterface.OnClickListener { dialogInterface, id -> }) //아무것도 안하는데 OK만들어주고 싶어서 넣음
                builder.create()
                builder.show()
            } else {
                // 여기서는 인텐트로만 넘기고 실제로 저장하는 로직은 다음 액티비티에서
                val i = Intent(this, InitMessageActivity::class.java)
                i.putExtra("year", cal.get(Calendar.YEAR))
                i.putExtra("month", cal.get(Calendar.MONTH))
                i.putExtra("dayOfMonth", cal.get(Calendar.DAY_OF_MONTH))
                i.putExtra("d_day", this.d_day)
                i.putExtra("subject", this.subject)
                i.putExtra("dayArray", this.dayArray)
                startActivity(i)
            }
        }

        // 공부 spinner 초기화
        val subjectList = arrayOf("대학 입시", "자격증 시험", "국가 고시", "취업 준비", "일상 공부")
        val spinnerAdapter = ArrayAdapter(this,
            R.layout.spinner_item, subjectList)
        spinnerAdapter.setDropDownViewResource(R.layout.spinner_item)
        subject_spinner.adapter = spinnerAdapter
//        subject_spinner.setSelection(0)
        subject_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
                subject = subjectList[0]
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, pos: Int, p3: Long) {
                subject = subject_spinner.getItemAtPosition(pos).toString()
//                debug2.text = subject_spinner.getItemAtPosition(pos).toString()
            }
        }

        // 일월화수목금토 입력
        val texts: Array<TextView> = arrayOf<TextView>(tv_sun, tv_mon, tv_tue, tv_wed, tv_thr, tv_fri, tv_sat)

        for ((idx, button) in texts.withIndex()) {
            button.setOnClickListener {
                if(dayArray[idx] == 0) {
                    button.setTextColor(Color.parseColor("#383838"))
                    dayArray[idx] = 1
                }
                else{
                    button.setTextColor(Color.parseColor("#999999"))
                    dayArray[idx] = 0
                }
            }
        }
    }
}