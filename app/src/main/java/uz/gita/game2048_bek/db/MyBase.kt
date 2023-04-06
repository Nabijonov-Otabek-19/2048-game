package uz.gita.game2048_bek.db

import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor

class MyBase {

    companion object {
        private lateinit var myBase: MyBase

        private val SHARED_PREF = "shared_pref"
        private val RECORD = "record"

        private lateinit var pref: SharedPreferences
        private lateinit var editor: Editor

        fun getInstance(context: Context): MyBase {
            pref = context.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
            editor = pref.edit()

            if (!(::myBase.isInitialized)) {
                myBase = MyBase()
            }
            return myBase
        }
    }

    var record: Int
        get() = pref.getInt(RECORD, 0)
        set(value) = editor.putInt(RECORD, value).apply()
}