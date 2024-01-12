package com.example.DifferentLanguage

import android.annotation.SuppressLint
import android.app.Activity
import android.app.PendingIntent.getActivity
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.googlemaps.R
import java.util.*

class ChangeLanguageActivity : AppCompatActivity() {
    lateinit var german :Button
    lateinit var hindi :Button
    lateinit var oriya :Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadLocale()
        setContentView(R.layout.activity_change_language)
        hindi = findViewById(R.id.hindi)
        german = findViewById(R.id.german)
        oriya = findViewById(R.id.oriya)


        StartApp()
        loadLocale()


    }

    private fun StartApp() {
        val actionBar = supportActionBar
        actionBar!!.title = resources.getString(R.string.app_name)

        german .setOnClickListener{
            setlocale("de")

        }
        hindi.setOnClickListener{
            setlocale("hi")

        }
        oriya.setOnClickListener{
            setlocale("or")

        }

    }

    private fun setlocale(code: String) {

        val locale = Locale(code)
        Locale.setDefault(locale)
        val configuration = resources.configuration
        configuration.locale = locale
        baseContext.resources.updateConfiguration(configuration,baseContext.resources.displayMetrics)
         val editor = getSharedPreferences("Settings", MODE_PRIVATE).edit()
        editor.putString("My_LANG",code)
        editor.apply()
    }
    fun loadLocale(){
        val  preferences = getSharedPreferences("Settings", Activity.MODE_PRIVATE)
        val  language =preferences .getString("My_LANG","")
        setlocale(language!!)

    }

    override fun onResume() {
        super.onResume()
        StartApp()
        loadLocale()
    }

}