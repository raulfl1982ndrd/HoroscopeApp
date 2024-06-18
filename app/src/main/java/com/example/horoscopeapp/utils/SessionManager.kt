package com.example.horoscopeapp.utils

import android.content.Context
import android.content.SharedPreferences

class SessionManager (context:Context) {

    companion object {
        const val FAVORITE_HOROSCOPE = "FAVORITE_HOROSCOPE"
    }

    private var sharedPrefs:SharedPreferences? = null

    var favoriteHoroscope:String?
        get() = getFavoriteHoroscopeValue()
        set(value) = setFavoriteHoroscopeValue(value!!)

    //inicializa la sesion con get shared preferences
    init {
        sharedPrefs = context.getSharedPreferences("horoscope_session", Context.MODE_PRIVATE)
    }
    //Funcion que me devuelve un boleano indicando
    // que es favorito para un determinado id de horoscopo
    fun isFavorite(horoscopeId : String):Boolean{
        return getFavoriteHoroscopeValue()?.equals(horoscopeId) ?: false
    }
    //Funcion para añadir valores a la sesion
    fun setFavoriteHoroscopeValue (id:String) {
        val editor = sharedPrefs?.edit()
        if (editor != null) {
            editor.putString(FAVORITE_HOROSCOPE, id)
            editor.apply()
        }
    }
    //Funcion para recuperar valores de la sesion
    fun getFavoriteHoroscopeValue ():String? {
        return sharedPrefs?.getString(FAVORITE_HOROSCOPE, null)
    }
}