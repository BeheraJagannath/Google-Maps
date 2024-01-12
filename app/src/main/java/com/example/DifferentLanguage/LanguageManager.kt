package com.example.DifferentLanguage

import android.content.Context
import android.content.SharedPreferences
import java.util.*

 class LanguageManager (private val context: Context){

//    lateinit var editor: SharedPreferences.Editor
//    val sharedPreferences = context!!.getSharedPreferences("UserPref", Context.MODE_PRIVATE)

    fun updeteResource(code: String?) {
        val locale = Locale(code)
        Locale.setDefault(locale)
        val resources = context!!.resources
        val configuration = resources.configuration
        configuration.locale = locale
        resources.updateConfiguration(configuration, resources.displayMetrics)
    }
//    fun ScreenAnalog(): Boolean? {
//        return sharedPreferences?.getBoolean("screen_mode", false)
//    }
//    fun ScreenalogMode(nightM: Boolean) {
//        editor = sharedPreferences!!.edit()
//        editor!!.putBoolean("screen_mode", nightM)
//        editor!!.apply()
//    }

}
