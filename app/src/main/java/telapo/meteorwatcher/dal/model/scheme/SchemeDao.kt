package telapo.meteorwatcher.dal.model.scheme

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface SchemeDao {
    @Query("SELECT * FROM schemes")
    fun GetAll(): List<Scheme>

    @Insert
    fun Insert(vararg scheme: Scheme)

    @Delete
    fun DeleteItem(scheme: Scheme)

    @Query("DELETE FROM schemes")
    fun DeleteAll()
}