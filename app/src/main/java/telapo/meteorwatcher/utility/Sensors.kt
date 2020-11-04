package telapo.meteorwatcher.utility

import java.util.*

object Sensors {
    object Time {
        val Utc: Calendar
            get() = Calendar.getInstance(TimeZone.getTimeZone("UTC"))

        val Local: Calendar
            get() = Calendar.getInstance()
    }

    object LocationSensor {

    }
}