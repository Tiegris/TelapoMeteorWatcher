package telapo.meteorwatcher.modules.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import telapo.meteorwatcher.R
import telapo.meteorwatcher.dal.NetworkingInterface
import telapo.meteorwatcher.modules.newobservation.NewObservationActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { _ ->
            startActivity(Intent(this, NewObservationActivity::class.java))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
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
                NetworkingInterface.SyncObservations()
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