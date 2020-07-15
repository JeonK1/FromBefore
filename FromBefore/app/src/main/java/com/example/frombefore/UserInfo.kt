package com.example.frombefore

import java.io.Serializable
import java.util.Calendar

data class UserInfo(
    val goalDate: Calendar, //목표 날짜
    var d_day: Int, //D-n을 표현하기 위한 변수
    var maxCombo: Int, //최대 누적 학습일
    val finalMessage: String //목표 달성시 출력될 메세지
):Serializable {
    public fun daysPassed() { // 00시가 지나 1일이 지날 시 실행
        if (ifGoalDate()) {
            TODO("목표 날짜 도달 시 처리")
        } else {
            d_day--;
        }
    }

    public fun ifGoalDate(): Boolean {
        val curCal = Calendar.getInstance()
        return curCal == goalDate
    }
}