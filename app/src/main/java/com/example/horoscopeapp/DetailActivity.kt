package com.example.horoscopeapp

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.MainScope

class DetailActivity : AppCompatActivity() {

    companion object{
        const val EXTRA_HOROSCOPE_ID = "HOROSCOPE_ID"
    }

    lateinit var horoscope: Horoscope
    private lateinit var menu_next: Button
    private lateinit var menu_prev: Button
    var isFavorite : Boolean = false
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
        val id = intent.getStringExtra(DetailActivity.EXTRA_HOROSCOPE_ID)


        horoscope = HoroscopeProvider.findById(id!!)!!
        /*val name = intent.getIntExtra("HOROSCOPE_NAME",-1)
        val logo = intent.getIntExtra("HOROSCOPE_LOGO",-1)*/
        //findViewById<TextView>(R.id.textView).text = getString(id)
        findViewById<TextView>(R.id.horoscopeTextView).setText(id)
        findViewById<ImageView>(R.id.horoscopeImageView).setImageResource(horoscope.logo)
        /*findViewById<ImageView>(R.id.imageView).setImageDrawable(getDrawable(logo))*/

        menu_next = findViewById(R.id.menu_next)
        menu_prev = findViewById(R.id.menu_prev)

    }

    //Procedimiento para instanciar inflar el menu que hemos creado en la activity
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_activity_detail,menu)
        return true
    }
    //Funcion para hacer algo a
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_favorite -> {
                if (!isFavorite) {
                    isFavorite = true
                    item.icon = getDrawable(R.drawable.ic_favorite_24)
                    Log.i("MENU","HE hecho click en el menu favorite selecionado favorito")
                }else
                if (isFavorite){
                    isFavorite = false
                    item.icon = getDrawable(R.drawable.ic_favorite_border_24)
                    Log.i("MENU","HE hecho click en el menu favorite desselecionado favorito")
                }

                true
            }
            R.id.menu_share -> {
                Log.i("MENU","HE hecho click en el menu share")
                true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}