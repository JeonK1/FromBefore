package com.example.frombefore.task

import android.content.Context
import android.os.AsyncTask
import com.example.frombefore.manager.UserInfo
import com.example.frombefore.message.MessageData
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class MessageGetterTask(private val callerContext: Context) : AsyncTask<Int, Void, MutableList<MessageData>>() { // 불러온 activity의 context저장
    private val specifiedUrl = "http://ec2-13-209-87-49.ap-northeast-2.compute.amazonaws.com:8080/fbf/"
    override fun onPostExecute(result: MutableList<MessageData>?) {
        super.onPostExecute(result)
    }

    override fun doInBackground(vararg params: Int?): MutableList<MessageData> {
        val messageArr = mutableListOf<MessageData>()
        val userInfo:JSONObject = UserInfo().readFile()
        val subject:String = userInfo.get("subject").toString()
        val dday:Int = userInfo.get("d_day").toString().toInt()

        // 인자로 들어온 횟수만큼 호출해서 메시지 받아옴
        for (i in 0 until params[0]!!) {
            val url = URL("$specifiedUrl?dday=${dday}&subject=${UserInfo.subjects[subject]}")
            val con = url.openConnection() as HttpURLConnection
            con.requestMethod = "GET"

            val ir = InputStreamReader(con.inputStream, "UTF-8")
            val br = BufferedReader(ir)
            var str = br.readLine()

            //    \" --> " 로 바꾸기, 양 끝에 " 빼기
            str = str.replace("\\\"", "\"")
            str = str.substring(1, str.length - 1)

            val byteArray = str.toByteArray(Charsets.US_ASCII)
            val json = JSONObject(byteArray.toString(Charsets.UTF_8));

            val subject = json.getString("subject")
            val text = json.getString("text")
            val dday = json.getInt("dday")
            messageArr.add(
                MessageData(
                    dday,
                    text,
                    subject
                )
            )
        }

        return messageArr
    }

    override fun onPreExecute() {
        super.onPreExecute()
    }
}
