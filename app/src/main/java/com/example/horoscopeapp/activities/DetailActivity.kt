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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class DetailActivity : AppCompatActivity() {
    companion object{
        const val EXTRA_HOROSCOPE_ID = "HOROSCOPE_ID"
    }

    lateinit var horoscope: Horoscope

    lateinit var favoriteButton:ImageButton
    lateinit var imageView: ImageView
    lateinit var textView: TextView
    private lateinit var dailyhoroscopeTextView:TextView
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
        dailyhoroscopeTextView = findViewById(R.id.horoscopeLuckTextView)
        //setFavoriteButtonIcon()
        //setFavoriteIcon()
        //Al hacer click en el boton de favoritos(No el menu de favoritos)
        favoriteButton.setOnClickListener {
            if (isFavorite){
                //Se elimina el valor de la sesion
                session.setFavoriteHoroscopeValue("")
            }else{
                //Se crea el valor en la sesion
                session.setFavoriteHoroscopeValue(horoscope.id)
            }
            isFavorite = !isFavorite
            //Se actualiza el boton de favoritos y el menu de favoritos con el valor
            setFavoriteButtonIcon()
        }
        menu_prev = findViewById(R.id.menu_prev)
        menu_next = findViewById(R.id.menu_next)

        menu_prev.setOnClickListener{

        }

        menu_next.setOnClickListener{

        }
        //Se pone el titulo de la action bar
        supportActionBar?.setTitle(horoscope.name)
        //Se pone el subtitulo de la action bar
        supportActionBar?.setSubtitle(horoscope.date)
        //Se muestra el boton atras en la action bar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        getDailyHoroscope()
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

    fun getDailyHoroscope() {
        CoroutineScope(Dispatchers.IO).launch{


        try {
        val direccionurl: String =
            "https://horoscope-app-api.vercel.app/api/v1/get-horoscope/daily?sign=${horoscope.id}&day=TODAY"

        val url = URL(direccionurl)
        val con = url.openConnection() as HttpURLConnection
        con.requestMethod = "GET"
        val responseCode = con.responseCode
        Log.i("HTTP", "Response Code::$responseCode")
        if (responseCode == HttpURLConnection.HTTP_OK) {
            //Metemos el cuerppo de la respuesta en un BufferedReader
            val bufferedReader = BufferedReader(InputStreamReader(con.inputStream))
            var inputLine: String?
            val response = StringBuffer()
            while (bufferedReader.readLine().also { inputLine = it } != null) {
                response.append(inputLine)
            }
            bufferedReader.close()
            response.toString()
            Log.i("HTTP", "Response :: $ {response.toString()}")
            //
            val json = JSONObject(response.toString())

            val result =json.getJSONObject("data").getString("horoscope_data")
            //Ejecutamos en el hilo principal o con la corutina sino es un activity o con runOnUiThread si es un activity
            /*Coroutine(Dispacher.Main).launch{dailyhoroscopeTextView.text = result}*/
            Log.i("HTTP","Result::${result}")
            runOnUiThread {
                dailyhoroscopeTextView.text = result
            }
        } else {//Hubo un error
            Log.w("HTTP", "Response ::Hubo un error")
        }
    }catch(e: Exception){
        Log.e("HTTP", "Response Error ::${e.stackTraceToString()}")
    }
    }
    }
}