package com.example.horoscopeapp.adapters

import android.graphics.Color
import android.text.SpannableString
import android.text.style.BackgroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.horoscopeapp.R
import com.example.horoscopeapp.data.Horoscope
import com.example.horoscopeapp.utils.SessionManager
import com.example.horoscopeapp.utils.highlight

class HoroscopeAdapter (private var dataSet: List<Horoscope>, private val onItemClickListener: (Int)->Unit) :
    RecyclerView.Adapter<HoroscopeViewHolder>() {
    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    private var highlightText:String? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HoroscopeViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_horoscope, parent, false)

        return HoroscopeViewHolder(view)
    }
    //Recupera el numero de registros del data set
    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun onBindViewHolder(holder: HoroscopeViewHolder, position: Int) {
        val horoscope = dataSet[position]

        //
        /*holder.nametextView.text = horoscope.name
        holder.desctextView.text = horoscope.desc
        //holder.datetextView.text = horoscope.date
        holder.datetextView.text = getString(horoscope.name)
        //holder.logoimageView.setImageResource(horoscope.logo)
        val context = holder.logoimageView.context
        holder.logoimageView.setImageDrawable(context.getDrawable(horoscope.logo))*/
        holder.render(horoscope)
        if (highlightText != null) {
            holder.highlight(highlightText!!)
        }
        //Click en la celda ejecuta el bloque de codigo(Funcion lambda)
        holder.itemView.setOnClickListener{
            //Log.i("ADAPTER","_He hecho click en el horoscopo: ${holder.itemView.context.getString(horoscope.name)}")
            onItemClickListener(position)
        }
    }

    //Estemetodo sirve para actualizar los datos
    fun updateData(newDataSet: List<Horoscope>){

        dataSet = newDataSet
        notifyDataSetChanged()
    }
    //Este metodo sirve para resaltar los datos y actualizar los datos
    fun updateData(newDataSet: List<Horoscope>, highlight:String){
        this.highlightText = highlight
        dataSet = newDataSet
        notifyDataSetChanged()
    }
}


class HoroscopeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val nametextView: TextView
    val desctextView: TextView
    val datetextView: TextView
    val logoimageView: ImageView
    val favoriteImageView: ImageView
    //Asigna los valores que se van a mostrar con los valores de layout
    init {
        // Define click listener for the ViewHolder's View
        nametextView = view.findViewById(R.id.nameTextView)
        desctextView = view.findViewById(R.id.descriptionTextView)
        datetextView = view.findViewById(R.id.dateTextView)
        logoimageView = view.findViewById(R.id.logoImageView)
        favoriteImageView = view.findViewById(R.id.favorites)
    }

    //Muestra los componentes
    fun render(horoscope: Horoscope){
        nametextView.setText(horoscope.name)
        desctextView.setText(horoscope.desc)
        datetextView.setText(horoscope.date)
        logoimageView.setImageResource(horoscope.logo)
        //Recuperamos el context del itemView
        val context = itemView.context
        //Con el context recuperamos de la sesion el horoscopo favorito
        var isFavorite = SessionManager(context).isFavorite(horoscope.id)

        //Si es favorito mostramos la imagen de favorito
        if (isFavorite){
            favoriteImageView.visibility = View.VISIBLE
        //Sino ocultamos la imagen de favorito
        }else{
            favoriteImageView.visibility = View.GONE
        }

    }
    //Subraya el texto que coincide con la busqueda
    fun highlight(text: String) {
        try {
            val highlighted = nametextView.text.toString().highlight(text)
            nametextView.text = highlighted
        } catch (e: Exception) { }
        try {
            val highlighted = datetextView.text.toString().highlight(text)
            datetextView.text = highlighted
        } catch (e: Exception) { }
    }
}

