package telapo.meteorwatcher.modules.newobservation

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import telapo.meteorwatcher.R
import telapo.meteorwatcher.dal.NetworkingInterface

class NewObservationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_observation)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_new_observation, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.miProfile -> {
                //TODO: Show Profile Settings
                return true
            }
            R.id.miSchemes -> {
                //TODO: Show Schemes
                return true
            }
            R.id.miFetch -> {
                NetworkingInterface.FetchSchemese()
                return true
            }
            R.id.miServerStatus -> {
                Toast.makeText(this, getString(R.string.strNetworkStatus) + " " + when(NetworkingInterface.GetServerStatus()) { true->getString(
                    R.string.strOnline) false->getString(R.string.strOffline)}, Toast.LENGTH_SHORT).show()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


}