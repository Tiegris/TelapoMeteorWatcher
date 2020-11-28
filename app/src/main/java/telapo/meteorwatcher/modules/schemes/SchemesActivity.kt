package telapo.meteorwatcher.modules.schemes

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_schemes.*
import telapo.meteorwatcher.R
import telapo.meteorwatcher.dal.model.scheme.Scheme
import telapo.meteorwatcher.dal.model.scheme.SchemeProvider
import telapo.meteorwatcher.dal.network.NetworkManager
import kotlin.concurrent.thread

class SchemesActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: SchemesAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schemes)

        initRecyclerView()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_schemes, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.miDelete -> {
                SchemeProvider().DeleteSchemes()
                return true
            }
            R.id.miFetch -> {
                NetworkManager.FetchSchemes()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun initRecyclerView() {
        recyclerView = SchemeRecyclerView
        adapter = SchemesAdapter(this)
        loadItemsInBackground()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    private fun loadItemsInBackground() {
        thread {
            //val items = database.shoppingItemDao().getAll()
            val items = mutableListOf<Scheme>(
                Scheme(0,"PerseidaMax",1, "","","","","",""),
                Scheme(1,"Orionids",1, "","","","","","")
            )
            runOnUiThread {
                adapter.Update(items)
            }
        }
    }

}