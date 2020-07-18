package com.example.frombefore

import android.os.Bundle
import android.widget.CheckBox
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.activity_day_select.*

class DaySelectActivity : AppCompatActivity() {
    companion object {
        val keys = mutableListOf<Int>(R.id.daySelectDaily, R.id.daySelectWeekday, R.id.daySelectWeekend)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_day_select)

        for (key in keys) {
            var radio = findViewById<RadioButton>(key)
            radio.setOnClickListener {
                daySelectList.isVisible = it.id == R.id.daySelectWeekday
            }
        }
    }
}