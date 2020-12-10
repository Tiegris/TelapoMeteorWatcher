package telapo.meteorwatcher.dal.model.observation

data class Cycle(
    val Id: Int,
    val Swarms: MutableList<SwarmResults>,
    var Hmg: Double,
    var Lm: Int
)