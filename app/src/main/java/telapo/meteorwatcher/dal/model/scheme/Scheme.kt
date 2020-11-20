package telapo.meteorwatcher.dal.model.scheme

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


interface SchemeProvider {
    fun GetScheme(i : Int) : Scheme
    fun GetList() : List<Scheme>
}

class SampleSchemeProvider : SchemeProvider{
    private var list = listOf<Scheme>(
        Scheme(0,"PerseidaMax",1, "Per","Bar","Asd","Foo","Baz","Gar"),
        Scheme(1,"Orionids",1, "","","","","","")
    )

    override fun GetScheme(i: Int) : Scheme{
        return list[i]
    }

    override fun GetList(): List<Scheme> {
        return list
    }

}

@Entity(tableName = "schemes")
data class Scheme(
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) val id: Long?,
    @ColumnInfo(name = "name")val Name: String,
    @ColumnInfo(name = "ver") val Version: Int,
    @ColumnInfo(name = "swarm1") var Swarm1 : String,
    @ColumnInfo(name = "swarm2") var Swarm2 : String,
    @ColumnInfo(name = "swarm3") var Swarm3 : String,
    @ColumnInfo(name = "swarm4") var Swarm4 : String,
    @ColumnInfo(name = "swarm5") var Swarm5 : String,
    @ColumnInfo(name = "swarm6") var Swarm6 : String,
    ) {

    override fun toString(): String {
        return Name
    }
}
