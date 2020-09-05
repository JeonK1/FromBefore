package com.example.frombefore.manager

import androidx.appcompat.app.AppCompatActivity
import org.json.JSONArray
import org.json.JSONObject
import java.io.*
import java.lang.StringBuilder

class UserInfo {
    companion object {
        // userInfo getter/setter
        private val userInfo:JSONObject = makeInitialUserInfo()

        // 변수
        var subject: String = if (userInfo.has("subject")) userInfo.getString("subject") else ""
        var dday: Int =if (userInfo.has("d_day"))  userInfo.getInt("d_day") else 0
        var dayArray: JSONArray = if (userInfo.has("dayArray")) userInfo.getJSONArray("dayArray") else JSONArray()
        var attendArray: JSONArray = if (userInfo.has("attendArray")) userInfo.getJSONArray("attendArray") else JSONArray()

        var year: Int = if (userInfo.has("year")) userInfo.getInt("year") else 0
        var month: Int = if (userInfo.has("month")) userInfo.getInt("month") else 0
        var dayOfMonth: Int = if (userInfo.has("dayOfMonth")) userInfo.getInt("dayOfMonth") else 0

        var finalMessage: String = if (userInfo.has("finalMessage")) userInfo.getString("finalMessage") else ""

        // 키 종류
        val keys =
            mutableListOf<String>(
                "finalMessage",
                "year",
                "month",
                "dayOfMonth",
                "d_day",
                "subject",
                "dayArray",
                "attendArray"
            )

        private val calendarKeys = arrayOf<String>("year", "month", "dayOfMonth")

        // 출석 상태
        const val ATTEND_NO_NEED = -2; // -2 : 출석할 필요 없음
        const val ATTEND_NOT_DONE_NO_MSG = -1; // -1 : 출석 아직 안함(자기반성 메시지 아직 안보냄)
        const val ATTEND_NOT_DONE_YES_MSG = 0; // 0 : 출석 아직 안함
        const val ATTEND_DONE = 1; // 1 : 출석 완료

        // 항목 종류 및 키값
        val subjects: Map<String, String> = mapOf<String, String>(
            "대학 입시" to "college",
            "자격증 시험" to "license",
            "국가 고시" to "country",
            "취업 준비" to "employ",
            "일상 공부" to "study"
        )
        val subjectTitles = subjects.keys.toTypedArray()

        private fun makeInitialUserInfo():JSONObject {
            var data:String = FileManager.readFile()

            // add initial or essential data
            return if (data.isNotEmpty()) {
                JSONObject(data)
            } else {
                JSONObject()
            }
        }

        fun get(key: String):Any? {
            if (userInfo.has(key)) {
                return userInfo[key]
            }

            return null
        }

        fun set(key: String, value:Any) {
            var map: HashMap<String, Any> = HashMap<String, Any>()

            if (key != null)
                map[key] = value

            set(map)
        }

        fun set(map: HashMap<String, Any>) {
            for ((key, value) in map) {
                userInfo.put(key, value)
            }

            FileManager.saveFile(userInfo)
        }

        fun calendar():JSONObject {
            var json:JSONObject = JSONObject()

            for (key in calendarKeys) {
                json.put(key, userInfo.getInt(key))
            }

            return json
        }

        fun calendarStr():String {
            var str:String = ""

            for ((idx, key) in calendarKeys.iterator().withIndex()) {
                str += userInfo.getString(key)

                if (idx != calendarKeys.size - 1) {
                    str += "-"
                }
            }

            return str
        }

        fun has(key: String):Boolean {
            userInfo.has(key)

            return userInfo.has(key)
        }

    }
}