package com.example.horoscopeapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    lateinit var horoscopesList: List<Horoscope>

    lateinit var recyclerView: RecyclerView

    lateinit var adapter:HoroscopeAdapter
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
        //Utilizo la función de la clase horoscope provider para recuperar la lista
        // de horoscopes de la clase horosocope provider
        horoscopesList = HoroscopeProvider.findAll()

        for (horoscope in horoscopesList){
            Log.i("HOROSCOPE",horoscope.name)
        }

         adapter = HoroscopeAdapter(horoscopesList) { position ->
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
//Procedimiento para instanciar el menu que hemos creado en la activity
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_activity_main,menu)
        val searchViewItem = menu?.findItem(R.id.menu_search)
        val searchView: SearchView = searchViewItem?.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                Log.i("SEARCH","Estoy buscando: $newText")
                if (newText != null) {
                    //Devolvemos toda la lista.filtramos tal que el nombre de la lista que estamos buscando en la
                    //busqueda contenga el texto ignorando las mayusculas
                    horoscopesList =
                            //Dependiedo como hallamos declarado el horoscope.name en la clase Horoscope
                            //como un entero si utilizamos el fichero de string o un string si tenemos el
                            //el nomnbre harcoded como string en el Horosope provider utilizamos una linea u otra de codigo
                            //HoroscopeProvider.findAll().filter{getString(it.name).contains(newText,true) }  //Strings
                            HoroscopeProvider.findAll().filter{it.name.contains(newText,true) }     //Hardcoded

                    adapter.updateData(horoscopesList,newText)
                }
                return true
            }

        })
        return true
    }
//funcion para detectar el click en el menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_search -> {
                Log.i("MENU","He hecho click en el menu settings")
                true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    //Funcion que navega a la siguiente pantalla para mostrar el detalle
    fun navigateToDetail(horoscope: Horoscope){
        //Para navegar a otra pantalla(Activity)
        val intent: Intent = Intent(this,DetailActivity::class.java)
        //intent.putExtra("HOROSCOPE_ID",horoscope.id)//Optional parameters para el intent de la nueva pantalla
        intent.putExtra(DetailActivity.EXTRA_HOROSCOPE_ID,horoscope.id)//Optional parameters para el intent de la nueva pantalla
        intent.putExtra("HOROSCOPE_NAME",horoscope.name)//Optional parameters para el intent de la nueva pantalla
        intent.putExtra("HOROSCOPE_LOGO",horoscope.logo)//Optional parameters para el intent de la nueva pantalla
        startActivity(intent)
    }
}