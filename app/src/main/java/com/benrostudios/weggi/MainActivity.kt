package com.benrostudios.weggi

import android.content.pm.ActivityInfo
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import org.json.JSONObject
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*






class MainActivity : AppCompatActivity(), SwitchFragmentInterface {

    companion object{
        lateinit var cityo: String
        lateinit var latlongo: MutableList<String>
        lateinit var switchInterface: SwitchFragmentInterface
        var mode: Int = 0
    }

    override fun switchToDisplayFragment() {
        loadFrag(disp_frag())
    }

    override fun switchToHomeFragment() {
        loadFrag(home_frag())
    }



    val CITY: String = "vellore,in"
    val API: String = "eb0f39ed1fe264b1c0622293bb1a36b9"
    lateinit var henlo : MutableList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main)
        loadFrag(home_frag())
        switchInterface = this

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

}
