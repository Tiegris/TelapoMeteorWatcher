package telapo.meteorwatcher.modules.liveobservation

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_observation.*
import telapo.meteorwatcher.R
import telapo.meteorwatcher.dal.model.scheme.SampleSchemeProvider
import kotlin.concurrent.thread


class ObservationActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ObservationAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_observation)
        initRecyclerView()
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_live_observation, menu)
        MenuCompat.setGroupDividerEnabled(menu, true)
        return true
    }

    private fun initRecyclerView() {
        recyclerView = ObservationRecyclerView
        adapter = ObservationAdapter(this)
        loadItemsInBackground()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    private fun loadItemsInBackground() {
        thread {
            //val items = database.shoppingItemDao().getAll()
            val item = SampleSchemeProvider().GetScheme(0)
            runOnUiThread {
                adapter.Set(item)
            }
        }
    }
}