package com.example.horoscopeapp

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    val horoscopesList: List<Horoscope> = listOf(
        Horoscope("aries","Aries",R.drawable.aries,"(march 21 - april 19)",""),
        Horoscope("taurus","Taurus",R.drawable.taurus,"(april 20 - may 20)",""),
        Horoscope("gemini","Gemini",R.drawable.gemini,"(may 21 - june 20)",""),
        Horoscope("cancer","Cancer",R.drawable.cancer,"(june 21 - july 22)",""),
        Horoscope("leo","Leo",R.drawable.leo,"(july 23 - august 22)",""),
        Horoscope("virgo","Virgo",R.drawable.virgo,"(august 23 - september 22)",""),
        Horoscope("libra","Libra",R.drawable.libra,"(september 23 - october 22)",""),
        Horoscope("scorpio","Scorpio", R.drawable.scorpio,"(october 23 - november 21)",""),
        Horoscope("sagittarius","Sagittarius",R.drawable.sagittarius,"(november 22 - december 21)",""),
        Horoscope("capricorn","Capricorn",R.drawable.capricornus,"(december 22 - january 19)",""),
        Horoscope("aquarius","Aquarius",R.drawable.aquarius,"(january 20 - february 18)",""),
        Horoscope("pisces","Pisces",R.drawable.pisces,"(february 19 - march 20","")
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
        //recyclerView.layoutManager = GridLayoutManager(this,2)
        getDrawable(R.drawable.sagittarius)
        getString(R.string.app_name)
        getColor(R.color.purple_200)

    }
}