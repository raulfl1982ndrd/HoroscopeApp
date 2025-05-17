package com.example.horoscopeapp.activities

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.example.horoscopeapp.utils.SessionManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.example.horoscopeapp.data.Horoscope
import com.example.horoscopeapp.data.HoroscopeProvider
import com.example.horoscopeapp.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.io.BufferedReader
import java.io.File
import java.io.FileOutputStream
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
    private var currentHoroscopeIndex:Int = -1
    private lateinit var dailyhoroscopeTextView:TextView
    lateinit var navigationBar: BottomNavigationView
    private lateinit var progress: ProgressBar
    private lateinit var menu_next: Button
    private lateinit var menu_prev: Button
    lateinit var  favoriteMenuItem: MenuItem
    private var shareMenuItem: MenuItem? = null
    private lateinit var session : SessionManager
    private lateinit var modalidad : String
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
        textView = findViewById<TextView>(R.id.horoscopeTextView)
        imageView = findViewById<ImageView>(R.id.horoscopeImageView)
        textView.setText(horoscope.name)
        imageView.setImageResource(horoscope.logo)
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
        navigationBar = findViewById(R.id.navigationBar)
        progress = findViewById(R.id.progress)
        menu_prev = findViewById(R.id.menu_prev)
        menu_next = findViewById(R.id.menu_next)
        currentHoroscopeIndex = HoroscopeProvider.getHoroscopeIndex(horoscope)
        menu_prev.setOnClickListener{
            if (currentHoroscopeIndex == 0) {
                currentHoroscopeIndex = HoroscopeProvider.gethoroscopesListSize()
            }
            currentHoroscopeIndex--
            loadData()
        }

        menu_next.setOnClickListener{
            currentHoroscopeIndex ++
            if (currentHoroscopeIndex == HoroscopeProvider.gethoroscopesListSize()) {
                currentHoroscopeIndex = 0
            }
            loadData()
        }
        //Se pone el titulo de la action bar
        supportActionBar?.setTitle(horoscope.name)
        //Se pone el subtitulo de la action bar
        supportActionBar?.setSubtitle(horoscope.date)
        //Se muestra el boton atras en la action bar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //getDailyHoroscope()
        navigationBar.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.menu_daily -> {
                    getDailyHoroscope("daily")
                    modalidad = "daily"
                }
                R.id.menu_weekly -> {
                    getDailyHoroscope("weekly")
                    modalidad = "weekly"
                }
                R.id.menu_monthly -> {
                    getDailyHoroscope("monthly")
                    modalidad = "monthly"
                }
            }

            return@setOnItemSelectedListener true
        }
        navigationBar.selectedItemId = R.id.menu_daily
    }
    private fun loadData() {
        horoscope = HoroscopeProvider.getHoroscope(currentHoroscopeIndex)
        isFavorite = horoscope.id == session.favoriteHoroscope

        // Set title
        supportActionBar?.setTitle(horoscope.name);
        supportActionBar?.setSubtitle(horoscope.date);

        textView.text = horoscope.name
        imageView.setImageResource(horoscope.logo)

        setFavoriteButtonIcon()

        //getDailyHoroscope()
        navigationBar.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.menu_daily -> {
                    getDailyHoroscope("daily")
                    modalidad = "daily"
                }
                R.id.menu_weekly -> {
                    getDailyHoroscope("weekly")
                    modalidad = "weekly"
                }
                R.id.menu_monthly -> {
                    getDailyHoroscope("monthly")
                    modalidad = "monthly"
                }
            }

            return@setOnItemSelectedListener true
        }
        navigationBar.selectedItemId = R.id.menu_daily
    }


    private fun setFavoriteDrawable () {
        val favDrawableId = if (isFavorite) {
            R.drawable.ic_favorite_24
        } else {
            R.drawable.ic_favorite_border_24
        }
        favoriteMenuItem?.setIcon(favDrawableId);
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
        shareMenuItem = menu?.findItem(R.id.menu_share)
        shareMenuItem?.setEnabled(false)
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
                var  texttoshare : String = "The " + modalidad + " horoscope for " + horoscope.name + " is: \n\n" + dailyhoroscopeTextView.text
                /*val sendIntent = Intent()
                sendIntent.setAction(Intent.ACTION_SEND)
                sendIntent.putExtra(Intent.EXTRA_TEXT,texttoshare)
                sendIntent.setType("text/plain")
                val shareIntent  = Intent.createChooser(sendIntent,null)

                startActivity(shareIntent)*/
                shareDrawableImageWithText(this, horoscope.logo, texttoshare)
                true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    fun getDailyHoroscope(method: String) {
        progress.visibility = View.VISIBLE
        CoroutineScope(Dispatchers.IO).launch{


        try {
        val direccionurl: String =
            "https://horoscope-app-api.vercel.app/api/v1/get-horoscope/$method?sign=${horoscope.id}&day=TODAY"

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
            Log.i("HTTP", "Response ::${response.toString()}")
            //
            val json = JSONObject(response.toString())

            val result =json.getJSONObject("data").getString("horoscope_data")
            //Ejecutamos en el hilo principal o con la corutina sino es un activity o con runOnUiThread si es un activity
            /*Coroutine(Dispacher.Main).launch{dailyhoroscopeTextView.text = result}*/
            Log.i("HTTP","Result::${result}")
            runOnUiThread {
                progress.visibility = View.GONE
                dailyhoroscopeTextView.text = result
                shareMenuItem?.setEnabled(true)
            }
        } else {//Hubo un error
            Log.w("HTTP", "Response ::Hubo un error")
        }
    }catch(e: Exception){
        Log.e("HTTP", "Response Error ::${e.stackTraceToString()}")
    }
    }
    }

    fun shareDrawableImageWithText(context: Context, drawableId: Int, message: String) {
        val drawable = ContextCompat.getDrawable(context, drawableId)

        if (drawable == null) {
            Toast.makeText(context, "No se pudo cargar la imagen", Toast.LENGTH_SHORT).show()
            return
        }

        // Convertir cualquier drawable (XML o PNG) a bitmap
        val bitmap = Bitmap.createBitmap(
            drawable.intrinsicWidth.takeIf { it > 0 } ?: 100,
            drawable.intrinsicHeight.takeIf { it > 0 } ?: 100,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)

        try {
            val cachePath = File(context.cacheDir, "images")
            cachePath.mkdirs()
            val file = File(cachePath, "shared_image.png")
            FileOutputStream(file).use { fos ->
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos)
            }

            val contentUri = FileProvider.getUriForFile(
                context,
                "${context.packageName}.provider",
                file
            )

            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "image/*"
                putExtra(Intent.EXTRA_STREAM, contentUri)
                putExtra(Intent.EXTRA_TEXT, message)
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }

            context.startActivity(Intent.createChooser(shareIntent, "Compartir con:"))

        } catch (e: Exception) {
            Log.e("Compartir", "Error al compartir imagen: ${e.message}", e)
            Toast.makeText(context, "Error al compartir imagen", Toast.LENGTH_SHORT).show()
        }
    }
}