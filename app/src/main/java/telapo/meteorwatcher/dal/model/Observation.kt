package telapo.meteorwatcher.dal.model

import android.location.Location
import telapo.meteorwatcher.dal.model.scheme.Scheme
import java.util.*

data class SwarmResults(
    val Name: String
) {

}

data class Observation(
    val Comments : List<Comment>,
    val UsedProfile: ProfileSnapshot,
    val Gps: Location?,
    val OfficialStart : Calendar,
    var PeriodTime : Int,
    val Scheme: Scheme) {

    companion object {
        var ActiveObservation : Observation? = null
        fun Activate(o : Observation) {
            ActiveObservation = o;
        }
    }
}