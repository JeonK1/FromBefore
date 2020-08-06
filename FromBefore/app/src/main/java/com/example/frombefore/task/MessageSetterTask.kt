package com.example.frombefore.task

import android.content.Context
import android.os.AsyncTask
import com.example.frombefore.manager.UserInfo
import com.example.frombefore.message.MessageData
import org.json.JSONObject
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL

class MessageSetterTask(private val callerContext: Context) : AsyncTask<MessageData, Void, Int>() { // 불러온 activity의 context저장
    private val specifiedUrl = "http://ec2-13-209-87-49.ap-northeast-2.compute.amazonaws.com:8080/fbf/"
    override fun onPostExecute(result:Int?) {
        super.onPostExecute(result)
    }

    override fun doInBackground(vararg params: MessageData): Int {
        val url = URL(specifiedUrl)
        val con = url.openConnection() as HttpURLConnection
        con.requestMethod = "POST"
//        con.setRequestProperty("Content-Type", "application/json");
        con.doOutput = true

        val os = OutputStreamWriter(con.outputStream)
        val json = JSONObject();
        json.put("subject", params[0].subject)
        json.put("dday", params[0].d_day)
        json.put("text",  params[0].text)
        json.put("edit_date", "2020-07-19")
        os.write(json.toString())
        os.flush()
        os.close()

        val code= con.responseCode
        con.disconnect()
        return code
    }

    override fun onPreExecute() {
        super.onPreExecute()
    }
}

