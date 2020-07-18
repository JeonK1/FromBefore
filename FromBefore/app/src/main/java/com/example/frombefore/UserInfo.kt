package com.example.frombefore

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import java.io.*
import java.util.Calendar

data class UserInfo(
    val id: Int // 이거 일단 안씀
):Serializable {
    companion object{
        val keys = mutableListOf<String>("finalMessage", "year", "month", "dayOfMonth", "d_day", "subject")
        fun writeFile(context: Context, name : String, data : String){
            val os = context.openFileOutput(name, AppCompatActivity.MODE_PRIVATE)
            val bw = BufferedWriter(OutputStreamWriter(os))
            bw.write(data)
            bw.flush()
        }
        fun readFile(context:Context, name:String) : String{
            val os = context.openFileInput(name)
            val br = BufferedReader(InputStreamReader(os))
            return br.readLine()
        }
    }
}