package com.example.horoscopeapp

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContentView(R.layout.activity_detail)
        /*ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }*/
        //
        val id = intent.getStringExtra("HOROSCOPE_ID")
        /*val name = intent.getIntExtra("HOROSCOPE_NAME",-1)
        val logo = intent.getIntExtra("HOROSCOPE_LOGO",-1)*/
        //findViewById<TextView>(R.id.textView).text = getString(id)
        findViewById<TextView>(R.id.textView).setText(id)
       /* findViewById<ImageView>(R.id.imageView).setImageResource(logo)
        findViewById<ImageView>(R.id.imageView).setImageDrawable(getDrawable(logo))*/
    }
}