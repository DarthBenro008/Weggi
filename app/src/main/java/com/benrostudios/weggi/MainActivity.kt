package com.benrostudios.weggi

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import org.json.JSONObject
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*






class MainActivity : AppCompatActivity() {


    val CITY: String = "vellore,in"
    val API: String = "eb0f39ed1fe264b1c0622293bb1a36b9"
    lateinit var henlo : MutableList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadFrag(home_frag())

    }


    private fun loadFrag(fragment: Fragment?): Boolean {
        if (fragment != null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.frame, fragment)
                .commit()


            return false
        }
        return true
    }
    companion object{
        lateinit var henlo: String
    }
}
