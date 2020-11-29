package telapo.meteorwatcher.dal.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import telapo.meteorwatcher.dal.model.scheme.Scheme
import telapo.meteorwatcher.dal.model.scheme.SchemeDao

@Database(entities = [Scheme::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun schemeDao(): SchemeDao
    companion object {
        private var INSTANCE: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.applicationContext,
                    AppDatabase::class.java, "meteor.db").build()
            }
            return INSTANCE!!
        }
        fun destroyInstance() {
            INSTANCE = null
        }
    }
}