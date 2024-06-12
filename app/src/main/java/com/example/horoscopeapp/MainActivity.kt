package com.example.horoscopeapp

import android.content.Intent
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
        Horoscope("aries","Aries",R.drawable.aries,R.string.date_aries,"1"),
        Horoscope("taurus","Taurus",R.drawable.taurus,R.string.date_taurus,"2"),
        Horoscope("gemini","Gemini",R.drawable.gemini,R.string.date_gemini,"3"),
        Horoscope("cancer","Cancer",R.drawable.cancer,R.string.date_cancer,"4"),
        Horoscope("leo","Leo",R.drawable.leo,R.string.date_leo,"5"),
        Horoscope("virgo","Virgo",R.drawable.virgo,R.string.date_virgo,"6"),
        Horoscope("libra","Libra",R.drawable.libra,R.string.date_libra,"7"),
        Horoscope("scorpio","Scorpio", R.drawable.scorpio,R.string.date_scorpio,"8"),
        Horoscope("sagittarius","Sagittarius",R.drawable.sagittarius,R.string.date_sagittarius,"9"),
        Horoscope("capricorn","Capricorn",R.drawable.capricornus,R.string.date_capricorn,"10"),
        Horoscope("aquarius","Aquarius",R.drawable.aquarius,R.string.date_aquarius,"11"),
        Horoscope("pisces","Pisces",R.drawable.pisces,R.string.date_pisces,"12")
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

        val adapter = HoroscopeAdapter(horoscopesList) { position ->
            //val horoscope = horoscopesList[position]
            Log.i(
                "onItemClickListener",
                "He hecho click en la posicion: $position, -> ${horoscopesList[position].name}"
            )
            navigateToDetail(horoscopesList[position])
        }

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        //recyclerView.layoutManager = GridLayoutManager(this,2)
        getDrawable(R.drawable.sagittarius)
        getString(R.string.app_name)
        getColor(R.color.purple_200)

    }

    //Funcion que navega a la siguiente pantalla para mostrar el detalle
    fun navigateToDetail(horoscope: Horoscope){
        //Para navegar a otra pantalla(Activity)
        val intent: Intent = Intent(this,DetailActivity::class.java)
        intent.putExtra("HOROSCOPE_ID",horoscope.id)//Optional parameters para el intent de la nueva pantalla
        intent.putExtra("HOROSCOPE_NAME",horoscope.name)//Optional parameters para el intent de la nueva pantalla
        intent.putExtra("HOROSCOPE_LOGO",horoscope.logo)//Optional parameters para el intent de la nueva pantalla
        startActivity(intent)
    }
}