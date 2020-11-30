package telapo.meteorwatcher.dal.model.observation.persistance

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "observations")
data class ObservationJson (
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) var id: Long?,
    @ColumnInfo(name = "value") val value: String,
)