package com.benrostudios.weggi

import android.content.Context
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
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions


class disp_frag : Fragment(){
    lateinit var CITY: String
    lateinit var longi: String
    lateinit var lati : String
    lateinit var mContext: Context
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

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
        City.setVisibility(View.INVISIBLE)
        Temp.setVisibility(View.INVISIBLE)
        MinMax.setVisibility(View.INVISIBLE)
        wdesc.setVisibility(View.INVISIBLE)
        ssrise.setVisibility(View.INVISIBLE)
        sset.setVisibility(View.INVISIBLE)
        whum.setVisibility(View.INVISIBLE)
        wpre.setVisibility(View.INVISIBLE)


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
                val windSpeed = wind.getString("speed")
                val weatherDescription = weather.getString("description")
                val address = jsonObj.getString("name") + ", " + sys.getString("country")
                val temp = main.getString("temp") + "°C"
                val tempMin = main.getString("temp_min") + "°C"
                val tempMax = main.getString("temp_max") + "°C"
                val pressure = main.getString("pressure") + "hPa"
                val humidity = main.getString("humidity") + "%"
                Log.e("TESTO",address)

                val minmax = tempMin + "/"+ tempMax
                //SETTING EVERYTHING
                City.setText(address)
                City.setVisibility(View.VISIBLE)
                Temp.setText(temp)
                Temp.setVisibility(View.VISIBLE)
                MinMax.setText(minmax)
                MinMax.setVisibility(View.VISIBLE)
                wdesc.setText(weatherDescription)
                wdesc.setVisibility(View.VISIBLE)
                update.setText(updatedAt)
                update.setVisibility(View.VISIBLE)
                ssrise.setText(sunriseA)
                ssrise.setVisibility(View.VISIBLE)
                sset.setText(sunsetA)
                sset.setVisibility(View.VISIBLE)
                whum.setText(humidity)
                whum.setVisibility(View.VISIBLE)
                wpre.setText(pressure)
                wpre.setVisibility(View.VISIBLE)
                val icon = weather.getString("icon")
                val option = RequestOptions()
                    .centerCrop()
                    .override(50,50)
                val iconUrl = "https://openweathermap.org/img/w/$icon.png"
                Log.e("TESTO",iconUrl)
                Glide.with(mContext).load(iconUrl).apply(option).into(wView)
            }catch(e: Exception){
                Log.e("BESTO",e.toString())
            }
        }
    }
}