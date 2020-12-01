package telapo.meteorwatcher.utility

import java.util.*

object Formater {
    fun GetDate(time : Calendar) : String =
        "${time.get(Calendar.YEAR)}-${cz(time.get(Calendar.MONTH)+1)}-${cz(time.get(Calendar.DAY_OF_MONTH))}"

    fun GetTime(time: Calendar) : String =
        "${cz(time.get(Calendar.HOUR_OF_DAY))}:${cz(time.get(Calendar.MINUTE))}"

    fun GetDateTime(time : Calendar) : String =
        "${time.get(Calendar.YEAR)}-${cz(time.get(Calendar.MONTH)+1)}-${cz(time.get(Calendar.DAY_OF_MONTH))} ${cz(time.get(Calendar.HOUR_OF_DAY))}:${cz(time.get(Calendar.MINUTE))}"

    private fun cz(a : Int) : String = if (a<10) "0${a}" else "${a}"

}