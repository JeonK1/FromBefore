package com.example.frombefore

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import java.io.*
import java.util.Calendar

data class UserInfo(
    val goalDate: Calendar, //목표 날짜
    var d_day: Int, //D-n을 표현하기 위한 변수
    var maxCombo: Int, //최대 누적 학습일
    val finalMessage: String //목표 달성시 출력될 메세지
):Serializable {
    companion object{
        val keys = mutableListOf<String>("finalMessage", "year", "month", "dayOfMonth", "d_day", "subject")
        public fun writeFile(context: Context, name : String, data : String){
            val os = context.openFileOutput(name, AppCompatActivity.MODE_PRIVATE)
            val bw = BufferedWriter(OutputStreamWriter(os))
            bw.write(data)
            bw.flush()
        }
        public fun readFile(context:Context, name:String) : String{
//        val file = context.getFileStreamPath(name)
//        if (file != null && file.exists()) {
//        }
            val os = context.openFileInput(name)
            val br = BufferedReader(InputStreamReader(os))
            return br.readLine()
        }
    }


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