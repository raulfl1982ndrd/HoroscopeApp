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
        Horoscope("aries","Aries",R.drawable.aries,R.string.date_aries,""),
        Horoscope("taurus","Taurus",R.drawable.taurus,R.string.date_taurus,""),
        Horoscope("gemini","Gemini",R.drawable.gemini,R.string.date_gemini,""),
        Horoscope("cancer","Cancer",R.drawable.cancer,R.string.date_cancer,""),
        Horoscope("leo","Leo",R.drawable.leo,R.string.date_leo,""),
        Horoscope("virgo","Virgo",R.drawable.virgo,R.string.date_virgo,""),
        Horoscope("libra","Libra",R.drawable.libra,R.string.date_libra,""),
        Horoscope("scorpio","Scorpio", R.drawable.scorpio,R.string.date_scorpio,""),
        Horoscope("sagittarius","Sagittarius",R.drawable.sagittarius,R.string.date_sagittarius,""),
        Horoscope("capricorn","Capricorn",R.drawable.capricornus,R.string.date_capricorn,""),
        Horoscope("aquarius","Aquarius",R.drawable.aquarius,R.string.date_aquarius,""),
        Horoscope("pisces","Pisces",R.drawable.pisces,R.string.date_pisces,"")
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