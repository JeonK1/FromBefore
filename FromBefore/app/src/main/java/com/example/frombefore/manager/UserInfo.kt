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
        var subject: String = getString("subject")
        var dday: Int = getInt("d_day")
        var dayArray: JSONArray = getJsonArray("dayArray")
        var attendArray: JSONArray = getJsonArray("attendArray")

        var year: Int = getInt("year")
        var month: Int = getInt("month")
        var dayOfMonth: Int = getInt("dayOfMonth")

        var finalMessage: String = getString("finalMessage")

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

        private fun getString(key: String):String {
            if (userInfo.has(key)) {
                return userInfo[key].toString()
            }

            return ""
        }

        private fun getInt(key:String):Int {
            if (userInfo.has(key)) {
                return userInfo[key].toString().toInt()
            }

            return 0
        }

        private fun getJsonArray(key:String):JSONArray {
            var default:JSONArray = JSONArray()

            if (userInfo.has(key)) {
                return userInfo[key] as JSONArray
            }

            if (key == "dayArray")
                default = defaultDayArray()

            return default
        }

        private fun defaultDayArray():JSONArray {
            return JSONArray(arrayListOf<Int>(0,0,0,0,0,0,0))
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