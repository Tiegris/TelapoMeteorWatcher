package telapo.meteorwatcher.dal.model.observation.persistance

import android.content.Context
import com.google.gson.Gson
import telapo.meteorwatcher.dal.local.AppDatabase
import telapo.meteorwatcher.dal.model.observation.Observation

class ObservationManager {

    fun Insert(observation: Observation) {
        val value = Gson().toJson(observation)
        val json = ObservationJson(null, value)
        Observation.ActiveDbId = AppDatabase.getInstance(context!!).observationDao().Insert(json)
    }

    fun Save(observation: Observation) {
        val value = Gson().toJson(observation)
        val json = ObservationJson(null, value)

        if (Observation.ActiveDbId == null || AppDatabase.getInstance(context!!)
                .observationDao().Exists(Observation.ActiveDbId!!) == 0)
            Observation.ActiveDbId = AppDatabase.getInstance(context!!).observationDao().Insert(json)
        else {
            json.id = Observation.ActiveDbId
            AppDatabase.getInstance(context!!).observationDao().Update(json)
        }
    }

    fun LoadAll(): MutableList<Observation> {
        val result = mutableListOf<Observation>()
        val list = AppDatabase.getInstance(context!!).observationDao().GetAll()
        for (item in list) {
            result.add(Gson().fromJson<Observation>(item, Observation::class.java))
        }
        return result
    }

    fun Delete(observation: Observation) {
        val value = Gson().toJson(observation)
        AppDatabase.getInstance(context!!).observationDao().DeleteItem(value)
    }

    private var context: Context? = null
    companion object {
        private var instance: ObservationManager? = null
        fun getInstance(context: Context): ObservationManager {
            if (instance == null) {
                instance = ObservationManager()
            }
            instance!!.context = context
            return instance!!
        }
    }
}