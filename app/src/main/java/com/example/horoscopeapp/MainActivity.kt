package com.example.horoscopeapp

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    val horoscopesList: List<Horoscope> = listOf(
        Horoscope("aries","Aries",0),
        Horoscope("taurus","Taurus",0),
        Horoscope("gemini","Gemini",0),
        Horoscope("cancer","Cancer",0),
        Horoscope("leo","Leo",0),
        Horoscope("virgo","Virgo",0),
        Horoscope("libra","Libra",0),
        Horoscope("scorpio","Scorpio",0),
        Horoscope("sagittarius","Sagittarius",0),
        Horoscope("capricorn","Capricorn",0),
        Horoscope("aquarius","Aquarius",0),
        Horoscope("pisces","Pisces",0)
    )

    lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)

        /*ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
         */
        for (horoscope in horoscopesList){
            Log.i("HOROSCOPE",horoscope.name)
        }

        val adapter = HoroscopeAdapter(horoscopesList)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
    }
}