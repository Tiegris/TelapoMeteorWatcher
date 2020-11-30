package telapo.meteorwatcher.dal.model.observation.persistance

import androidx.room.*

@Dao
interface ObservationDao {
    @Query("SELECT value FROM observations")
    fun GetAll(): List<String>

    @Insert
    fun Insert(vararg observation: ObservationJson) : Long

    @Query("SELECT COUNT(*) FROM observations WHERE id = :id")
    fun Exists(vararg id: Long) : Int

    @Update
    fun Update(vararg observation: ObservationJson)

    @Delete
    fun DeleteItem(observation: ObservationJson)

    @Query("DELETE FROM observations")
    fun DeleteAll()
}