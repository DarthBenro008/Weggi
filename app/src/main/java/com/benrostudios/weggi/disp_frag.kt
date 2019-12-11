package com.benrostudios.weggi

import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.disp.*
import org.json.JSONObject
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*

class disp_frag : Fragment(){
    lateinit var CITY: String
    lateinit var longi: String
    lateinit var lati : String
    val API: String = "eb0f39ed1fe264b1c0622293bb1a36b9"
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val fragview = inflater.inflate(R.layout.disp ,null)
        var recmode = MainActivity.mode
        if(recmode ==0) {
            CITY = MainActivity.cityo
            Log.e("Testo", MainActivity.cityo)
            weggiTask(0).execute()
        }else if(recmode ==1) {
            longi = MainActivity.latlongo[1]
            lati = MainActivity.latlongo[0]
            weggiTask(1).execute()
        }
        return fragview
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    inner class weggiTask(modp: Int) : AsyncTask<String, Void, String>() {
        var modo = modp
        override fun onPreExecute() {
            super.onPreExecute()
        }

        override fun doInBackground(vararg params: String?): String? {
            var response: String?
            if(modo ==0) {
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
        }else{
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
                Log.e("TESTO",jsonObj.toString())
                val main = jsonObj.getJSONObject("main")
                val sys = jsonObj.getJSONObject("sys")
                val wind = jsonObj.getJSONObject("wind")
                val weather = jsonObj.getJSONArray("weather").getJSONObject(0)



                val updatedAt: Long = jsonObj.getLong("dt")
                val updatedAtText =
                    "Updated at: " + SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH).format(
                        Date(updatedAt * 1000)
                    )
                val temp = main.getString("temp") + "°C"
                val tempMin = main.getString("temp_min") + "°C"
                val tempMax = main.getString("temp_max") + "°C"
                val pressure = main.getString("pressure")
                val humidity = main.getString("humidity")

                val sunrise: Long = sys.getLong("sunrise")
                val sunset: Long = sys.getLong("sunset")
                val windSpeed = wind.getString("speed")
                val weatherDescription = weather.getString("description")
                val address = jsonObj.getString("name") + ", " + sys.getString("country")
                Log.e("TESTO",address)
                City.setText(address)
                Temp.setText(temp)
                val minmax = tempMin + "/"+ tempMax
                MinMax.setText(minmax)
            }catch(e: Exception){
                Log.e("BESTO",e.toString())
            }
        }
    }
}