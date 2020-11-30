package telapo.meteorwatcher.modules.schemes

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_schemes.*
import telapo.meteorwatcher.R
import telapo.meteorwatcher.dal.local.AppDatabase
import telapo.meteorwatcher.dal.model.scheme.Scheme
import telapo.meteorwatcher.dal.network.NetworkManager
import kotlin.concurrent.thread

class SchemesActivity() : AppCompatActivity(), NetworkManager.ISchemeListReceiver {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: SchemesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schemes)
        setSupportActionBar(findViewById(R.id.toolbar))
    }

    override fun onStart() {
        super.onStart()
        initRecyclerView()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_schemes, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.miDeleteAll -> {
                thread {
                    AppDatabase.getInstance(this).schemeDao().DeleteAll()
                    runOnUiThread {
                        adapter.Update(listOf())
                    }
                }
                return true
            }
            R.id.miFetch -> {
                NetworkManager.FetchSchemes(this)
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
        Thread {
            val items = AppDatabase.getInstance(this@SchemesActivity).schemeDao().GetAll()
            runOnUiThread {
                adapter.Update(items)
            }
        }.start()
    }

    override fun ReceiveSchemes(schemes: List<Scheme>) {
        thread {
            AppDatabase.getInstance(this).schemeDao().DeleteAll()
            for (item in schemes) {
                AppDatabase.getInstance(this).schemeDao().Insert(item)
            }
            runOnUiThread {
                Toast.makeText( this, "Succes!",Toast.LENGTH_SHORT).show()
                adapter.Update(schemes)
            }
        }
    }

    override fun HandleError(throwable: Throwable) {
        Toast.makeText( this, this.getString(R.string.strNetworkError),Toast.LENGTH_SHORT).show()
    }

}