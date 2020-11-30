package telapo.meteorwatcher.dal.model.observation

import telapo.meteorwatcher.dal.model.ProfileSnapshot
import telapo.meteorwatcher.dal.model.scheme.Scheme
import telapo.meteorwatcher.utility.Formater
import java.util.*

data class Observation(
    val Comments : MutableList<Comment>,
    val UsedProfile: ProfileSnapshot,
    val Latitude: Double?,
    val Longitude: Double?,
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
        var DbId : Long? = null
        var ActiveObservation : Observation? = null
        fun Activate(o : Observation) {
            ActiveObservation = o;
        }
    }
}