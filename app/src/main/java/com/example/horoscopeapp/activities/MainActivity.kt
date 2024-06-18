package com.example.horoscopeapp.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.horoscopeapp.data.Horoscope
import com.example.horoscopeapp.adapters.HoroscopeAdapter
import com.example.horoscopeapp.data.HoroscopeProvider
import com.example.horoscopeapp.R


class MainActivity : AppCompatActivity() {

    lateinit var horoscopesList: List<Horoscope>

    lateinit var recyclerView: RecyclerView

    lateinit var adapter: HoroscopeAdapter


    //On create se lanza al crear el activity
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
        //Instanciamos el adapter y en el onclick asociado
        //al adapter llamamos a navegar al  detalle
         adapter = HoroscopeAdapter(horoscopesList) { position ->
            //val horoscope = horoscopesList[position]
            Log.i(
                "onItemClickListener",
                "He hecho click en la posicion: $position, -> ${horoscopesList[position].name}"
            )
            navigateToDetail(horoscopesList[position])
        }
        //Se le asigna el adapter instanciado al adapter del recicle view
        recyclerView.adapter = adapter

        //Se le asigna un layout manager para ver como se muestran los datos en la vista
        //en forma de lista
        recyclerView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        //En forma de grid
        //recyclerView.layoutManager = GridLayoutManager(this,2)

        //Ejemplos de cosas que se pueden recuperar no se utilizan para nada ni se asignan a nada
        getDrawable(R.drawable.sagittarius)
        getString(R.string.app_name)
        getColor(R.color.purple_200)

    }
//On Resume Se lanza al volver a la activity
    override fun onResume() {
        super.onResume()
        adapter.updateData(horoscopesList)
    }
//Procedimiento para instanciar el menu que hemos creado en la activity
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        //Inflamos instanciamos el menu segun el layouy del menuque hemos creado
        menuInflater.inflate(R.menu.menu_activity_main,menu)
        //Creamos una variable para guardar el item del menu que hemos creado
        val searchViewItem = menu?.findItem(R.id.menu_search)
        //Creamos una variable para guardar el search view que vamos a instanciar para realizar una busqueda
        val searchView: SearchView = searchViewItem?.actionView as SearchView
    //Preparamos el listener para la vista de busqueda
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            //Se lanza cuando se envia a la busqueda el texto
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
            //Se lanza cuando al modificarse el texto
            override fun onQueryTextChange(newText: String?): Boolean {
                Log.i("SEARCH","Estoy buscando: $newText")
                if (newText != null) {
                    //Devolvemos toda la lista.filtramos tal que el nombre de la lista que estamos buscando en la
                    //busqueda contenga el texto ignorando las mayusculas
  /*                  horoscopesList =
                            //Dependiedo como hallamos declarado el horoscope.name en la clase Horoscope
                            //como un entero si utilizamos el fichero de string o un string si tenemos el
                            //el nomnbre harcoded como string en el Horosope provider utilizamos una linea u otra de codigo
                            //HoroscopeProvider.findAll().filter{getString(it.name).contains(newText,true) }  //Strings
                            HoroscopeProvider.findAll().filter{it.name.contains(newText,true) }     //Hardcoded
*/
                    //Filtramos los horoscopos
                    horoscopesList = HoroscopeProvider.findAll().filter {
                        //tal que nombre contenga el texto o que la fecha contenga el texto
                       it.name.contains(newText, true) ||
                                getString(it.date).contains(newText, true)
                    }
                    //resaltamos el nuevo texto en el horoscopo
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
        //Se crea un intent pasandole en contexto y pasandole la clase que debe instanciar
        val intent: Intent = Intent(this, DetailActivity::class.java)
        //intent.putExtra("HOROSCOPE_ID",horoscope.id)//Optional parameters para el intent de la nueva pantalla
        intent.putExtra(DetailActivity.EXTRA_HOROSCOPE_ID,horoscope.id)//Optional parameters para el intent de la nueva pantalla
        intent.putExtra("HOROSCOPE_NAME",horoscope.name)//Optional parameters para el intent de la nueva pantalla
        intent.putExtra("HOROSCOPE_LOGO",horoscope.logo)//Optional parameters para el intent de la nueva pantalla
        startActivity(intent)
    }
}