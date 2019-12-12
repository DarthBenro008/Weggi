package com.benrostudios.weggi

import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.room.Room
import com.benrostudios.weggi.Data.AppDatabase
import com.benrostudios.weggi.Data.LocHistory
import kotlinx.android.synthetic.main.disp.*
import org.json.JSONObject
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions


class disp_frag : Fragment() {
    lateinit var CITY: String
    lateinit var longi: String
    lateinit var lati: String
    lateinit var mContext: Context
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    val API: String = "eb0f39ed1fe264b1c0622293bb1a36b9"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragview = inflater.inflate(R.layout.disp, null)
        var recmode = MainActivity.mode
        if (recmode == 0) {
            CITY = MainActivity.cityo
            Log.e("Testo", MainActivity.cityo)
            weggiTask(0).execute()
        } else if (recmode == 1) {
            longi = MainActivity.latlongo[1]
            lati = MainActivity.latlongo[0]
            weggiTask(1).execute()
        }


        return fragview
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        City.visibility = (View.INVISIBLE)
        Temp.visibility = (View.INVISIBLE)
        MinMax.visibility =(View.INVISIBLE)
        wdesc.visibility = (View.INVISIBLE)
        ssrise.visibility = (View.INVISIBLE)
        sset.visibility = (View.INVISIBLE)
        whum.visibility = (View.INVISIBLE)
        wpre.visibility = (View.INVISIBLE)
        update.visibility = (View.INVISIBLE)

    }

    inner class weggiTask(modp: Int) : AsyncTask<String, Void, String>() {
        var modo = modp

        fun replaceFragment(someFragment: Fragment) {
            val transaction = fragmentManager!!.beginTransaction()
            transaction.replace(R.id.frame, someFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }

        override fun onPreExecute() {
            super.onPreExecute()
        }

        override fun doInBackground(vararg params: String?): String? {
            var response: String?
            if (modo == 0) {
                try {
                    response =
                        URL("https://api.openweathermap.org/data/2.5/weather?q=$CITY&units=metric&appid=$API").readText(
                            Charsets.UTF_8
                        )
                } catch (e: Exception) {
                    response = null
                    Log.e("TESTO", "NO RESPONSE")
                }
                return response
            } else {
                try {
                    response =
                        URL("https://api.openweathermap.org/data/2.5/weather?lat=$lati&lon=$longi&units=metric&appid=$API").readText(
                            Charsets.UTF_8
                        )
                } catch (e: Exception) {
                    response = null
                    Log.e("TESTO", "NO RESPONSE")
                }
                return response
            }

        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)

            try {
                val jsonObj = JSONObject(result)
                Log.e("TESTO", jsonObj.toString())
                val main = jsonObj.getJSONObject("main")
                val sys = jsonObj.getJSONObject("sys")
                val wind = jsonObj.getJSONObject("wind")
                val weather = jsonObj.getJSONArray("weather").getJSONObject(0)


                val updateo: Long = jsonObj.getLong("dt")
                val updatedAt =
                    "Updated at: " + SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(
                        Date(updateo * 1000)
                    )

                val sunrise: Long = sys.getLong("sunrise")
                val sunriseA =
                    SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(
                        Date(sunrise * 1000)
                    )
                val sunset: Long = sys.getLong("sunset")
                val sunsetA =
                    SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(
                        Date(sunset * 1000)
                    )
                val weatherDescription = weather.getString("description")
                var words = weatherDescription.split(" ").toMutableList()
                var finaldesc = ""
                for (p in words) {
                    finaldesc += p.capitalize() + " "

                }
                val address = jsonObj.getString("name") + ", " + sys.getString("country")
                CITY = address
                val temp = main.getString("temp") + "°C"
                val tempMin = main.getString("temp_min") + "°C"
                val tempMax = main.getString("temp_max") + "°C"
                val pressure = main.getString("pressure") + " hPa"
                val humidity = main.getString("humidity") + "%"
                Log.e("TESTO", address)

                val minmax = tempMin + "/" + tempMax
                //SETTING EVERYTHING
                City.text = (address)
                City.visibility = (View.VISIBLE)
                Temp.text = (temp)
                Temp.visibility = (View.VISIBLE)
                MinMax.text = (minmax)
                MinMax.visibility = (View.VISIBLE)
                wdesc.text = (finaldesc)
                wdesc.visibility = (View.VISIBLE)
                update.text = (updatedAt)
                update.visibility = (View.VISIBLE)
                ssrise.text = (sunriseA)
                ssrise.visibility = (View.VISIBLE)
                sset.text = (sunsetA)
                sset.visibility = (View.VISIBLE)
                whum.text = (humidity)
                whum.visibility = (View.VISIBLE)
                wpre.text = (pressure)
                wpre.visibility = (View.VISIBLE)
                val icon = weather.getString("icon")
                val option = RequestOptions()
                    .centerCrop()
                    .override(50, 50)
                val iconUrl = "https://openweathermap.org/img/w/$icon.png"
                Log.e("TESTO", iconUrl)
                Glide.with(mContext).load(iconUrl).apply(option).into(wView)
            } catch (e: Exception) {
                Log.e("BESTO", e.toString())
                replaceFragment(home_frag())
                Toast.makeText(context, "Please Input an Valid City!", Toast.LENGTH_LONG).show()
            }


            var db = Room.databaseBuilder(mContext, AppDatabase::class.java, "LocHistory").build()
            val thread = Thread {
                var timepass: LocHistory = LocHistory()

                var henlo: LocHistory = LocHistory()
                henlo.Location = CITY
                var checker: Int = 0
                db.historyDao().allHistory.forEach() {
                    if (CITY.equals(it.Location)) {
                        checker = 1
                        timepass.Location = it.Location
                        timepass.dateo = it.dateo
                        timepass.uid = it.uid
                    }
                    Log.i("TESTO", it.Location)
                }
                val currentTime = Calendar.getInstance().getTime()
                val timeFormat = SimpleDateFormat("dd-MM-yyyy hh:mm:ss aa")
                val output = timeFormat.format(currentTime)
                henlo.dateo = output
                if (checker == 1) {
                    db.historyDao().deleteByUid(timepass.uid!!)
                    Log.i(
                        "TESTO",
                        "THIS IS TIMEPASS: " + timepass.Location + timepass.dateo + " " + timepass.uid
                    )
                    db.historyDao().insert(henlo)
                    Log.i("TESTO", "BLAH")
                } else {
                    db.historyDao().insert(henlo)
                    Log.i("TESTO", "NO BLAH")
                }

            }
            thread.start()
        }
    }
}