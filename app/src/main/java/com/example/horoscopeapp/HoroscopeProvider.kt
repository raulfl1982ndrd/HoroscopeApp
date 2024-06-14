package com.example.horoscopeapp

class HoroscopeProvider {
    companion object{
        private val horoscopesList: List<Horoscope> = listOf(
            Horoscope("aries","Aries",R.drawable.aries,R.string.date_aries,"1",false),
            Horoscope("taurus","Taurus",R.drawable.taurus,R.string.date_taurus,"2",false),
            Horoscope("gemini","Gemini",R.drawable.gemini,R.string.date_gemini,"3",false),
            Horoscope("cancer","Cancer",R.drawable.cancer,R.string.date_cancer,"4",false),
            Horoscope("leo","Leo",R.drawable.leo,R.string.date_leo,"5",false),
            Horoscope("virgo","Virgo",R.drawable.virgo,R.string.date_virgo,"6",false),
            Horoscope("libra","Libra",R.drawable.libra,R.string.date_libra,"7",false),
            Horoscope("scorpio","Scorpio", R.drawable.scorpio,R.string.date_scorpio,"8",false),
            Horoscope("sagittarius","Sagittarius",R.drawable.sagittarius,R.string.date_sagittarius,"9",false),
            Horoscope("capricorn","Capricorn",R.drawable.capricornus,R.string.date_capricorn,"10",false),
            Horoscope("aquarius","Aquarius",R.drawable.aquarius,R.string.date_aquarius,"11",false),
            Horoscope("pisces","Pisces",R.drawable.pisces,R.string.date_pisces,"12",false)
        )
        fun findAll():List<Horoscope>{
            return horoscopesList
        }

        fun findById(id:String):Horoscope?{
            return horoscopesList.find{it.id == id }
        }
    }
}