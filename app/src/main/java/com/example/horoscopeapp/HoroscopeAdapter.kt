package com.example.horoscopeapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class HoroscopeAdapter (private val dataSet: List<Horoscope>) :
    RecyclerView.Adapter<HoroscopeViewHolder>() {
    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HoroscopeViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_horoscope, parent, false)

        return HoroscopeViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun onBindViewHolder(holder: HoroscopeViewHolder, position: Int) {
        val horoscope = dataSet[position]
        /*holder.nametextView.text = horoscope.name
        holder.desctextView.text = horoscope.desc
        //holder.datetextView.text = horoscope.date
        holder.datetextView.text = getString(horoscope.name)
        //holder.logoimageView.setImageResource(horoscope.logo)
        val context = holder.logoimageView.context
        holder.logoimageView.setImageDrawable(context.getDrawable(horoscope.logo))*/
        holder.render(horoscope)
    }

}


class HoroscopeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val nametextView: TextView
    val desctextView: TextView
    val datetextView: TextView
    val logoimageView: ImageView
    init {
        // Define click listener for the ViewHolder's View
        nametextView = view.findViewById(R.id.nameTextView)
        desctextView = view.findViewById(R.id.descriptionTextView)
        datetextView = view.findViewById(R.id.dateTextView)
        logoimageView = view.findViewById(R.id.logoImageView)
    }

    fun render(horoscope: Horoscope){
        nametextView.setText(horoscope.name)
        desctextView.setText(horoscope.desc)
        datetextView.setText(horoscope.date)
        logoimageView.setImageResource(horoscope.logo)
    }
}