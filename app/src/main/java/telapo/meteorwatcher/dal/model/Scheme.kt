package telapo.meteorwatcher.dal.model

interface SchemeProvider {
    fun GetScheme(i : Int) : Scheme
    fun GetList() : List<Scheme>
}

class SampleSchemeProvider : SchemeProvider{
    private var list = listOf<Scheme>(
        Scheme(1, "PerseidaMax"),
        Scheme(1, "Orionids")
    )

    override fun GetScheme(i: Int) : Scheme{
        return list[i]
    }

    override fun GetList(): List<Scheme> {
        return list
    }

}

data class Scheme(val Version: Int, val Name: String) {
    override fun toString(): String {
        return "${Name},   v: ${Version}"
    }
}
