package com.example.frombefore.manager

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONObject
import java.io.*
import java.lang.StringBuilder

class FileManager {
    companion object {
        val USER_INFO: String = "userInfo"
        val MESSAGES_FROM_ME: String = "messageFromMe"
        val MESSAGES_FROM_OTHERS: String = "messagesFromOthers"

        // for util
        private val c: Context = GlobalContext.getContext()

        // 파일 읽기
        private fun getPath(path: String): File? {
            var file: File? = c.getFileStreamPath(path)

            if (file == null || !file.exists()) {
                return null
            }

            return file
        }

        fun reset() {
            saveFile(JSONObject())
        }

//        fun writeFile(key: String?, value: Any) {
//            var map: HashMap<String, Any> = HashMap<String, Any>()
//
//            if (key != null)
//                map[key] = value
//
//            writeFile(map)
//        }
//
//        fun writeFile(map: HashMap<String, Any>) {
//            // get exist data or create new
//            var json: JSONObject = readFile()
//
//            for ((key, value) in map) {
//                json.put(key, value)
//            }
//
//            saveFile(json)
//        }

        fun saveFile(data: Any, fileName: String = USER_INFO) {
            val os = c.openFileOutput(fileName, AppCompatActivity.MODE_PRIVATE)
            val bw = BufferedWriter(OutputStreamWriter(os))
            bw.write(data.toString())
            bw.flush()
        }

        fun readFile(fileName: String = USER_INFO): String {
            var os: FileInputStream
            try {
                os = c.openFileInput(fileName)

            } catch (ex: FileNotFoundException) {
                saveFile(JSONObject())

                return ""
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

            return strBuilder.toString()
        }
    }
}