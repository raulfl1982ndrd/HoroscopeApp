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

    init {
        sharedPrefs = context.getSharedPreferences("horoscope_session", Context.MODE_PRIVATE)
    }

    fun setFavoriteHoroscopeValue (id:String) {
        val editor = sharedPrefs?.edit()
        if (editor != null) {
            editor.putString(FAVORITE_HOROSCOPE, id)
            editor.apply()
        }
    }

    fun getFavoriteHoroscopeValue ():String? {
        return sharedPrefs?.getString(FAVORITE_HOROSCOPE, null)
    }
}