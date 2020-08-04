package com.example.frombefore.calendar

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TableLayout
import android.widget.TableLayout.LayoutParams
import android.widget.TextView
import com.example.frombefore.R
import com.example.frombefore.manager.UserInfo
import kotlinx.android.synthetic.main.activity_calendar.*
import org.json.JSONArray
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class CalendarFB(val context: Context?, val tableLayout:TableLayout) {
    var calendarLayout: TableLayout

    init {
        //tablelayout 연결
        calendarLayout = tableLayout
        calendarLayout.removeAllViews()

        //abbreviationsBar(얼화수목금토일) 만들어주기
        val daysOfWeek = listOf("일", "월", "화", "수", "목", "금", "토")
        val abbrbar = LinearLayout(context)
        abbrbar.layoutParams =
            TableLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        abbrbar.orientation = LinearLayout.HORIZONTAL
        abbrbar.setBackgroundResource(R.drawable.textview_bgpaper2)
        for (i in 0..6) {
            val textView = TextView(context)
            textView.text = daysOfWeek[i]
            textView.setTextColor(Color.WHITE)
            textView.setBackgroundResource(R.drawable.block_transparent)
            textView.layoutParams = TableLayout.LayoutParams(0, LayoutParams.MATCH_PARENT, 1f)
            textView.gravity = Gravity.CENTER
            textView.setPadding(0, 10, 0, 10)
            abbrbar.addView(textView)
        }
        calendarLayout.addView(abbrbar)

        //요일추가
        val ui = UserInfo(context!!)
        val jsonObj = ui.readFile()
        val dayArray = jsonObj.get("dayArray") as JSONArray

        val current = LocalDateTime.now()
        val todayDayFormat = DateTimeFormatter.ofPattern("dd")
        val todayDay = current.format(todayDayFormat)
        val offset = 0 // 오늘을 기준으로 현재 달인지 이전달인지 다음달인지 등등..
        val calendarDayList = initCalendarDay(offset)
        val rowCnt = calendarDayList.size/7
        for(i in 0..rowCnt-1){
            val weekLinearLayout = LinearLayout(context)
            val params = TableLayout.LayoutParams(LayoutParams.MATCH_PARENT, 0, 1f)
            weekLinearLayout.layoutParams = params
            weekLinearLayout.orientation = LinearLayout.HORIZONTAL
            for(j in 0..6){
                val nowIdx = i*7+j
                val dayLinearLayout = LinearLayout(context)
                val params = LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
                params.weight = 1f
                dayLinearLayout.layoutParams = params
                dayLinearLayout.orientation = LinearLayout.VERTICAL
                dayLinearLayout.setBackgroundResource(R.drawable.block_transparent)

                val dateTextView = TextView(context)
                dateTextView.layoutParams = LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
                dateTextView.gravity = Gravity.CENTER_HORIZONTAL
                dateTextView.text = calendarDayList[nowIdx].day.toString()
                if(calendarDayList[nowIdx].enable) {
                    //여기에 날짜설정
                    if(calendarDayList[nowIdx].day == todayDay.toInt()){
                        // 오늘날짜 설정
                        dateTextView.setTextColor(Color.parseColor("#ffffff"))
                        dayLinearLayout.setBackgroundResource(R.drawable.calender_today)
                    }
                    else {
                        if(dayArray[j]==1){
                            // 오늘 제외한 공부하기로 했던 모든 주
                            dateTextView.setTextColor(Color.parseColor("#383838"))
                        }
                        else {
                            dateTextView.setTextColor(Color.parseColor("#999999"))
                        }
                    }
                }
                else
                    dateTextView.setTextColor(Color.parseColor("#00ffffff"))
                dateTextView.setPadding(0, 15, 0, 0)

                dayLinearLayout.addView(dateTextView)
                weekLinearLayout.addView(dayLinearLayout)
            }
            calendarLayout.addView(weekLinearLayout)
        }
    }

    private fun initCalendarDay(offset: Int) : ArrayList<CalendarFBDay> {
        //달력에 넣어줄 날짜들 List에 넣어주는 함수
        val calendarDayList = ArrayList<CalendarFBDay>()
        val calendar = Calendar.getInstance()
        var curMonth = calendar.get(Calendar.MONTH) + offset
        calendar.set(Calendar.DATE, 1)
        calendar.set(Calendar.MONTH, curMonth)
        //이전 달 추가, enable=false
        val prevMonthTailOffset = calendar.get(Calendar.DAY_OF_WEEK) - 1
        calendar.set(Calendar.MONTH, curMonth-1)
        curMonth-=1
        val maxPrevMonthDate = calendar.getActualMaximum(Calendar.DATE)
        var maxOffsetDate = maxPrevMonthDate - prevMonthTailOffset
        for(i in 1..prevMonthTailOffset)
            calendarDayList.add(
                CalendarFBDay(
                    ++maxOffsetDate,
                    false
                )
            )
        //현재 달 추가, enable=true
        calendar.set(Calendar.MONTH, curMonth+1)
        curMonth+=1
        val maxCurMonthDate = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        for(i in 1..maxCurMonthDate){
            calendarDayList.add(
                CalendarFBDay(
                    i,
                    true
                )
            )
        }
        //다음 달 추가, enable=false
        val NUMBER_OF_WEEK = 7
        val rowOfCalendar = if(calendarDayList.size>35) 6 else 5
        val nextMonthHeadOffset = rowOfCalendar * NUMBER_OF_WEEK - (prevMonthTailOffset+maxCurMonthDate)
        for(i in 1..nextMonthHeadOffset){
            calendarDayList.add(
                CalendarFBDay(
                    i,
                    false
                )
            )
        }
        return calendarDayList
    }
}
