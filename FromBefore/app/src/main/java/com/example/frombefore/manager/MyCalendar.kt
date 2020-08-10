package com.example.frombefore.manager

import android.util.Log
import java.util.Calendar

class MyCalendar {
    companion object {
        // 날짜를 customDate를 쓰고 싶다: false. 현재 날짜를 쓰고 싶다: true
        // 커밋할 때 항상 false로 설정해주세요 ^^
        private const val useCustomCalendar:Boolean = false
        private const val customYear:Int = 2020
        private const val customMonth:Int = 8
        private const val customDate:Int = 12

        // 현재 날짜 정보
        val calendar:Calendar = initCalendar()

        private fun initCalendar():Calendar {
            var cal:Calendar = Calendar.getInstance()

            if (useCustomCalendar) {
                cal.set(customYear, customMonth - 1, customDate)
            }

            return cal
        }

        // 편리하게 년,월,일,요일 가져오기. Calendar.year이런 식으로
        val year:Int = calendar.get(Calendar.YEAR)
        val month:Int = calendar.get(Calendar.MONTH)
        val date:Int = calendar.get(Calendar.DAY_OF_MONTH)
        val day:Int = calendar.get(Calendar.DAY_OF_WEEK)
        val timeInMillis = calendar.timeInMillis

        // 특정 일을 가져오는 함수들
        fun today():Calendar {
            return initCalendar()
        }

        fun yesterday():Int {
            var cal:Calendar = initCalendar()
            cal.add(Calendar.DAY_OF_YEAR, -1)

            return cal.get(Calendar.DAY_OF_MONTH)
        }

        // "2020-08-20"을 2020, 08, 20으로 나누어 호출
        fun with(dateStr:String): Calendar {
            var arr:List<String> = dateStr.split("-")

            if (arr.size != 3) {
                Log.e("Error", "Calendar.with got wrong parameter: $dateStr")
                return initCalendar()
            }

            return with(arr[0].toInt(), arr[1].toInt(), arr[2].toInt())
        }

        // 연, 월(1~12), 일을 입력하면 그에 맞는 calendar 객체 반환
        fun with(year:Int, month:Int, dayOfMonth:Int): Calendar {
            var cal = Calendar.getInstance()
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH,  month - 1)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            return cal
        }
    }
}