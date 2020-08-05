package com.example.frombefore.manager

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONArray
import org.json.JSONObject
import java.io.*
import java.lang.StringBuilder
import java.util.Calendar

data class UserInfo(
    val mContext: Context
) : Serializable {
    private val pathString = "userInfo"
    private val infoFile = mContext.getFileStreamPath(pathString)

    fun reset() {
        saveFile(JSONObject())
    }

    // 파일이 존재하는지 체크
    fun isJsonExists(): Boolean {
        return infoFile != null && infoFile.exists()
    }

    fun writeFile(key: String?, value: String) {
        var json: JSONObject
        if (isJsonExists())
            json = readFile()
        else
            json = JSONObject()

        if (key != null)
            json.put(key, value)

        saveFile(json)
    }

    fun writeFile(key: String?, value: JSONArray) {
        var json: JSONObject
        if (isJsonExists())
            json = readFile()
        else
            json = JSONObject()

        if (key != null) {
            json.put(key, value)
        }
        saveFile(json)
    }

    fun saveFile(json: JSONObject) {
        val os = mContext.openFileOutput(pathString, AppCompatActivity.MODE_PRIVATE)
        val bw = BufferedWriter(OutputStreamWriter(os))
        bw.write(json.toString())
        bw.flush()
    }

    fun readFile(): JSONObject {
        var os:FileInputStream
        try {
            os = mContext.openFileInput(pathString)

        } catch (ex:FileNotFoundException) {
            writeFile(null, "")

            return JSONObject()
        }

        val br = BufferedReader(InputStreamReader(os))
        var strBuilder = StringBuilder()
        var line = br.readLine()
        while (line != null) {
            // 읽어서 한줄씩 추가
            strBuilder.append(line).append("\n")
            line = br.readLine()
        }
        br.close()

        return JSONObject(strBuilder.toString())
    }

    companion object {
        val keys =
            mutableListOf<String>("finalMessage", "year", "month", "dayOfMonth", "d_day", "subject", "dayArray", "attendArray")
    }
}