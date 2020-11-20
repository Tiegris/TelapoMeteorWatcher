package telapo.meteorwatcher.dal.model

import android.location.Location
import telapo.meteorwatcher.dal.model.scheme.Scheme
import telapo.meteorwatcher.utility.Formater
import java.util.*

data class Cycle(
    val Id: Int,
    val Swarms: MutableList<SwarmResults>,
    var Hmg: Int
) {

}


data class SwarmResults(
    val Name: String,
    var count_m5: Int = 0,
    var count_m4: Int = 0,
    var count_m3: Int = 0,
    var count_m2: Int = 0,
    var count_m1: Int = 0,
    var count_0: Int = 0,
    var count_1: Int = 0,
    var count_2: Int = 0,
    var count_3: Int = 0,
    var count_4: Int = 0,
    var count_5: Int = 0,
) {
    val Count : Int get() {
        return count_0+count_1+count_2+count_3+count_4+count_5+count_m1+count_m2+count_m3+count_m4+count_m5;
    }
    fun Add(mg: Int) {
        if (mg == 5) count_5++
        if (mg == 4) count_4++
        if (mg == 3) count_3++
        if (mg == 2) count_2++
        if (mg == 1) count_1++
        if (mg == 0) count_0++
        if (mg == -1) count_m1++
        if (mg == -2) count_m2++
        if (mg == -3) count_m3++
        if (mg == -4) count_m4++
        if (mg == -5) count_m5++
    }
}

data class Observation(
    val Comments : MutableList<Comment>,
    val UsedProfile: ProfileSnapshot,
    val Gps: Location?,
    val OfficialStart : Calendar,
    var PeriodTime : Int,
    val Scheme: Scheme,
    var Synced: Boolean = false,
    val Cycles: MutableList<Cycle> = mutableListOf()
)
{
    val LatestCycle: Cycle
    get() {
        return Cycles.last()
    }

    val CycleLives: Boolean
    get() {
        val copy = OfficialStart.clone() as Calendar
        copy.add(Calendar.MINUTE, PeriodTime * (Cycles.size))
        return Calendar.getInstance().before(copy) && Cycles.isNotEmpty()
    }


    fun GetCycleDuration(): String {
        val copy = OfficialStart.clone() as Calendar
        copy.add(Calendar.MINUTE, PeriodTime * (Cycles.size))

        val from = Formater.GetTime(copy)
        copy.add(Calendar.MINUTE, PeriodTime)
        val to = Formater.GetTime(copy)
        return "$from - $to"
    }

    fun NewCycle(hmg : Int) {
        val count = Cycles.size
        val list = mutableListOf<SwarmResults>()
        if (!Scheme.Swarm1.equals("")) list.add(SwarmResults(Scheme.Swarm1))
        if (!Scheme.Swarm2.equals("")) list.add(SwarmResults(Scheme.Swarm2))
        if (!Scheme.Swarm3.equals("")) list.add(SwarmResults(Scheme.Swarm3))
        if (!Scheme.Swarm4.equals("")) list.add(SwarmResults(Scheme.Swarm4))
        if (!Scheme.Swarm5.equals("")) list.add(SwarmResults(Scheme.Swarm5))
        if (!Scheme.Swarm6.equals("")) list.add(SwarmResults(Scheme.Swarm6))
        list.add(SwarmResults("Spo"))

        Cycles.add(Cycle(count, list, hmg))
    }

    fun AddMeteor(mg: Int, name: String) {
        val swarms = Cycles.last().Swarms
        for (i in swarms) {
            if (i.Name.equals(name))
                i.Add(mg)
        }
    }

    companion object {
        var ActiveObservation : Observation? = null
        fun Activate(o : Observation) {
            ActiveObservation = o;
        }
    }
}