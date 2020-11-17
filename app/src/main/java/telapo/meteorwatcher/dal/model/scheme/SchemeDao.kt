package telapo.meteorwatcher.dal.model.scheme

import androidx.room.*

@Dao
interface ShoppingItemDao {
    @Query("SELECT * FROM schemes")
    fun GetAll(): List<Scheme>

    @Insert
    fun Insert(scheme: Scheme): Long

    @Update
    fun Update(scheme: Scheme)

    @Delete
    fun DeleteItem(scheme: Scheme)
}