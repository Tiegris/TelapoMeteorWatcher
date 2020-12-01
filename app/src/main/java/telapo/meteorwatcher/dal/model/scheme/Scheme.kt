package telapo.meteorwatcher.dal.model.scheme

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "schemes")
data class Scheme(
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) val id: Long?,
    @ColumnInfo(name = "name")val name: String,
    @ColumnInfo(name = "ver") val version: Int,
    @ColumnInfo(name = "swarm1") var swarm1 : String?,
    @ColumnInfo(name = "swarm2") var swarm2 : String?,
    @ColumnInfo(name = "swarm3") var swarm3 : String?,
    @ColumnInfo(name = "swarm4") var swarm4 : String?,
    @ColumnInfo(name = "swarm5") var swarm5 : String?,
    @ColumnInfo(name = "swarm6") var swarm6 : String?,
    ) {

    override fun toString(): String {
        return "${name}, v$version"
    }
}
