package telapo.meteorwatcher.dal.model

import android.content.Context

data class ProfileSnapshot(val Name: String, val Email: String, val AddressCity: String)

object Profile {
    var Name : String = ""
    var AddressCity: String = ""
    var Email: String = ""
    var o : Context? = null
    var initialised : Boolean = false

    fun CreateSnapshot() = ProfileSnapshot(Name, Email, AddressCity)


    fun Initialise(o: Context) {
        this.o = o
        if (!initialised) {
            load()
            initialised = true
        }
    }

    fun Commit() {
        val sp = o?.getSharedPreferences("shpPrifle", Context.MODE_PRIVATE)?.edit()
        sp?.putString("name", Name)
        sp?.putString("email", Email)
        sp?.putString("addr", AddressCity)
        sp?.apply()
    }

    private fun load() {
        val pref = o?.getSharedPreferences("shpPrifle", Context.MODE_PRIVATE)
        Name = pref?.getString("name", "").toString()
        Email = pref?.getString("email", "").toString()
        AddressCity = pref?.getString("addr", "").toString()
    }
}