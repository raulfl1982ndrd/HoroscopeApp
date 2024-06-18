package com.example.horoscopeapp.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.horoscopeapp.utils.SessionManager
import com.example.horoscopeapp.data.Horoscope
import com.example.horoscopeapp.data.HoroscopeProvider
import com.example.horoscopeapp.R

class DetailActivity : AppCompatActivity() {
    companion object{
        const val EXTRA_HOROSCOPE_ID = "HOROSCOPE_ID"
    }

    lateinit var horoscope: Horoscope

    lateinit var favoriteButton:ImageButton
    lateinit var imageView: ImageView
    lateinit var textView: TextView
    private lateinit var menu_next: Button
    private lateinit var menu_prev: Button
    lateinit var  favoriteMenuItem: MenuItem
    private lateinit var session : SessionManager

    var isFavorite : Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContentView(R.layout.activity_detail)

        session = SessionManager(this)
        /*ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }*/
        //
        val id = intent.getStringExtra(EXTRA_HOROSCOPE_ID)


        horoscope = HoroscopeProvider.findById(id!!)!!
        isFavorite = session.getFavoriteHoroscopeValue()?.equals(horoscope.id)?:false

        /*val name = intent.getIntExtra("HOROSCOPE_NAME",-1)
        val logo = intent.getIntExtra("HOROSCOPE_LOGO",-1)*/
        //findViewById<TextView>(R.id.textView).text = getString(id)
        findViewById<TextView>(R.id.horoscopeTextView).setText(id)
        findViewById<ImageView>(R.id.horoscopeImageView).setImageResource(horoscope.logo)
        /*findViewById<ImageView>(R.id.imageView).setImageDrawable(getDrawable(logo))*/
        favoriteButton = findViewById(R.id.favoriteButton)
        //setFavoriteButtonIcon()
        //setFavoriteIcon()
        favoriteButton.setOnClickListener {
            if (isFavorite){
                session.setFavoriteHoroscopeValue("")
            }else{
                session.setFavoriteHoroscopeValue(horoscope.id)
            }
            isFavorite = !isFavorite
            setFavoriteButtonIcon()
        }

        menu_next = findViewById(R.id.menu_next)
        menu_prev = findViewById(R.id.menu_prev)
        supportActionBar?.setTitle(horoscope.name)
        supportActionBar?.setSubtitle(horoscope.date)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

    fun setFavoriteButtonIcon(){
        if (isFavorite){
            favoriteButton.setImageResource(R.drawable.ic_favorite_24)
            favoriteMenuItem.setIcon(R.drawable.ic_favorite_24)
        }else{
            favoriteButton.setImageResource(R.drawable.ic_favorite_border_24)
            favoriteMenuItem.setIcon(R.drawable.ic_favorite_border_24)
        }
    }
    /*fun setFavoriteIcon(){
        if (isFavorite){
            favoriteMenuItem.setIcon(R.drawable.ic_favorite_24)
        }else{
            favoriteMenuItem.setIcon(R.drawable.ic_favorite_border_24)
        }
    }*/


    //Procedimiento para instanciar inflar el menu que hemos creado en la activity
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_activity_detail,menu)
        favoriteMenuItem = menu!!.findItem(R.id.menu_favorite)
        setFavoriteButtonIcon()
        return true



    }
    //Funcion para hacer algo a
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home ->{
                finish()
                true
            }
            R.id.menu_favorite -> {

                if (isFavorite) {
                    //item.icon = getDrawable(R.drawable.ic_favorite_24)
                    session.setFavoriteHoroscopeValue("")
                    Log.i("MENU","HE hecho click en el menu favorite selecionado favorito")
                }else {
                    //item.icon = getDrawable(R.drawable.ic_favorite_border_24)
                    session.setFavoriteHoroscopeValue(horoscope.id)
                    Log.i("MENU","HE hecho click en el menu favorite desselecionado favorito")
                }
                isFavorite = !isFavorite
                setFavoriteButtonIcon()
                true
            }
            R.id.menu_share -> {
                Log.i("MENU","HE hecho click en el menu share")
                val sendIntent = Intent()
                sendIntent.setAction(Intent.ACTION_SEND)
                sendIntent.putExtra(Intent.EXTRA_TEXT,"This is my text to send")
                sendIntent.setType("text/plain")
                val shareIntent  = Intent.createChooser(sendIntent,null)

                startActivity(shareIntent)
                true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}