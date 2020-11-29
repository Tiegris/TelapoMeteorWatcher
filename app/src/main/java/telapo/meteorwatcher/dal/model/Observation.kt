package telapo.meteorwatcher.dal.model

import android.location.Location
import telapo.meteorwatcher.dal.model.scheme.Scheme
import telapo.meteorwatcher.utility.Formater
import java.util.*

data class Cycle(
    val Id: Int,
    val Swarms: MutableList<SwarmResults>,
    var Hmg: Int,
    var Lm: Int
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
        val c = if (Cycles.size == 0) 0 else Cycles.size-1

        val copy = OfficialStart.clone() as Calendar
        copy.add(Calendar.MINUTE, PeriodTime * (c))

        val from = Formater.GetTime(copy)
        copy.add(Calendar.MINUTE, PeriodTime)
        val to = Formater.GetTime(copy)
        return "$from - $to"
    }

    fun NewCycle(hmg : Int, lm: Int) {
        val count = Cycles.size
        val list = mutableListOf<SwarmResults>()
        if (!Scheme.swarm1.equals("")) Scheme.swarm1?.let { SwarmResults(it) }?.let { list.add(it) }
        if (!Scheme.swarm2.equals("")) Scheme.swarm2?.let { SwarmResults(it) }?.let { list.add(it) }
        if (!Scheme.swarm3.equals("")) Scheme.swarm3?.let { SwarmResults(it) }?.let { list.add(it) }
        if (!Scheme.swarm4.equals("")) Scheme.swarm4?.let { SwarmResults(it) }?.let { list.add(it) }
        if (!Scheme.swarm5.equals("")) Scheme.swarm5?.let { SwarmResults(it) }?.let { list.add(it) }
        if (!Scheme.swarm6.equals("")) Scheme.swarm6?.let { SwarmResults(it) }?.let { list.add(it) }
        list.add(SwarmResults("Spo"))

        Cycles.add(Cycle(count, list, hmg, lm))
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