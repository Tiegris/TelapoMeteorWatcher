package telapo.meteorwatcher.dal.model.observation.persistance

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ObservationDao {
    @Query("SELECT value FROM observations")
    fun GetAll(): List<String>

    @Insert
    fun Insert(observation: ObservationJson) : Long

    @Query("SELECT COUNT(*) FROM observations WHERE id = :id")
    fun Exists(vararg id: Long) : Int

    @Update
    fun Update(vararg observation: ObservationJson)

    @Query("DELETE FROM observations WHERE value = :json")
    fun DeleteItem(json: String)

    @Query("DELETE FROM observations")
    fun DeleteAll()
}