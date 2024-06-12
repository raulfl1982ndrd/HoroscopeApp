package com.example.horoscopeapp

class HoroscopeProvider {
    companion object{
        private val horoscopesList: List<Horoscope> = listOf(
            Horoscope("aries","Aries",R.drawable.aries,R.string.date_aries,"1"),
            Horoscope("taurus","Taurus",R.drawable.taurus,R.string.date_taurus,"2"),
            Horoscope("gemini","Gemini",R.drawable.gemini,R.string.date_gemini,"3"),
            Horoscope("cancer","Cancer",R.drawable.cancer,R.string.date_cancer,"4"),
            Horoscope("leo","Leo",R.drawable.leo,R.string.date_leo,"5"),
            Horoscope("virgo","Virgo",R.drawable.virgo,R.string.date_virgo,"6"),
            Horoscope("libra","Libra",R.drawable.libra,R.string.date_libra,"7"),
            Horoscope("scorpio","Scorpio", R.drawable.scorpio,R.string.date_scorpio,"8"),
            Horoscope("sagittarius","Sagittarius",R.drawable.sagittarius,R.string.date_sagittarius,"9"),
            Horoscope("capricorn","Capricorn",R.drawable.capricornus,R.string.date_capricorn,"10"),
            Horoscope("aquarius","Aquarius",R.drawable.aquarius,R.string.date_aquarius,"11"),
            Horoscope("pisces","Pisces",R.drawable.pisces,R.string.date_pisces,"12")
        )
        fun findAll():List<Horoscope>{
            return horoscopesList
        }

        fun findById(id:String):Horoscope?{
            return horoscopesList.find{it.id == id }
        }
    }
}