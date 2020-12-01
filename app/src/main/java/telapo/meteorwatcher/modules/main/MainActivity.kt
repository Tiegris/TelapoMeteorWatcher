package telapo.meteorwatcher.modules.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_main.*
import telapo.meteorwatcher.R
import telapo.meteorwatcher.dal.model.Profile
import telapo.meteorwatcher.dal.model.observation.Observation
import telapo.meteorwatcher.dal.model.observation.persistance.ObservationManager
import telapo.meteorwatcher.modules.newobservation.NewObservationActivity
import telapo.meteorwatcher.modules.profile.ProfileFragment
import telapo.meteorwatcher.modules.schemes.SchemesActivity
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MainAdapter

    private fun initRecyclerView() {
        recyclerView = MainRecyclerView
        adapter = MainAdapter(this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        Profile.Initialise(this)

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { _ ->
            startActivity(Intent(this, NewObservationActivity::class.java))
        }
    }

    override fun onStart() {
        super.onStart()
        initRecyclerView()
        thread {
            val result = ObservationManager.getInstance(this).LoadAll()
            onDataLoaded(result)
        }
    }

    private fun onDataLoaded(list : MutableList<Observation>) {
        runOnUiThread {
            adapter.Update(list)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.miProfile -> {
                ProfileFragment(null).show(supportFragmentManager, ProfileFragment::class.java.simpleName)
                return true
            }
            R.id.miSchemes -> {
                startActivity(Intent(this, SchemesActivity::class.java))
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}