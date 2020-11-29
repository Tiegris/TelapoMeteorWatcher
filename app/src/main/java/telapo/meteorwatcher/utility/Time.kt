package telapo.meteorwatcher.utility

import java.util.*

object Time {
    val Utc: Calendar
        get() = Calendar.getInstance(TimeZone.getTimeZone("UTC"))

    val Local: Calendar
        get() = Calendar.getInstance()
}