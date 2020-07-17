package com.example.frombefore

import android.content.Context
import android.os.AsyncTask
import android.util.Log
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class MessageGetterTask(private val callerContext : Context): AsyncTask<Int, Void, MutableList<String>>() { // 불러온 activity의 context저장
    private val specifiedUrl = "http://ec2-13-209-87-49.ap-northeast-2.compute.amazonaws.com:8080/fbf/"
    override fun onPostExecute(result: MutableList<String>?) {
        super.onPostExecute(result)
    }
    override fun doInBackground(vararg params: Int?): MutableList<String> {
        val messageArr = mutableListOf<String>()
        // 인자로 들어온 횟수만큼 호출해서 메시지 받아옴
        for (i in 0..params[0]!!) {
            val url = URL(specifiedUrl)
            val con = url.openConnection() as HttpURLConnection
            con.requestMethod = "GET"

            val ir = InputStreamReader(con.inputStream, "UTF-8")
            val br = BufferedReader(ir)
            var str = br.readLine()

            //    \" --> " 로 바꾸기, 양 끝에 " 빼기
            str = str.replace("\\\"", "\"")
                    .substring(1, str.length - 1)

            val byteArray = str.toByteArray(Charsets.US_ASCII)
            val json = JSONObject(byteArray.toString(Charsets.UTF_8));
            Log.d("Response", json.getInt("id").toString())
            val message = json.getString("text")
            messageArr[i] = message
        }
        return messageArr
    }
    override fun onPreExecute() {
        super.onPreExecute()
    }
}
