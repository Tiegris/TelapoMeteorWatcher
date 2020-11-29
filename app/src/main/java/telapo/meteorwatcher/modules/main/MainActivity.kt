package telapo.meteorwatcher.modules.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import telapo.meteorwatcher.R
import telapo.meteorwatcher.dal.model.Profile
import telapo.meteorwatcher.dal.network.NetworkManager
import telapo.meteorwatcher.modules.newobservation.NewObservationActivity
import telapo.meteorwatcher.modules.profile.ProfileFragment
import telapo.meteorwatcher.modules.schemes.SchemesActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        Profile.Initialise(this)

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
                ProfileFragment().show(supportFragmentManager, ProfileFragment::class.java.simpleName)
                return true
            }
            R.id.miSchemes -> {
                startActivity(Intent(this, SchemesActivity::class.java))
                return true
            }
            R.id.miSync -> {
                NetworkManager.SyncObservations()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}