package telapo.meteorwatcher.dal.model

import android.app.Activity
import android.content.Context

object Profile {
    var Name : String = "Your name"
    var AddressCity: String = "Your address"

    fun Commit(o: Activity) {
        val sp = o.getSharedPreferences("shpPrifle", Context.MODE_PRIVATE).edit()
        sp.putString("name", Name)
        sp.putString("addr", AddressCity)
        sp.apply()
    }

    fun Update(o : Activity) {
        val pref = o.getSharedPreferences("shpPrifle", Context.MODE_PRIVATE)
        Name = pref.getString("name", "name not set").toString()
        AddressCity = pref.getString("addr", "address not set").toString()
    }
}