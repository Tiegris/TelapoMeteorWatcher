package telapo.meteorwatcher.modules.liveobservation

import android.content.DialogInterface
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_observation.*
import telapo.meteorwatcher.R
import telapo.meteorwatcher.dal.model.Comment
import telapo.meteorwatcher.dal.model.Observation
import telapo.meteorwatcher.dal.model.scheme.SampleSchemeProvider
import kotlin.concurrent.thread


class ObservationActivity : AppCompatActivity(), ICommentable {
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

    override fun onStart() {
        super.onStart()

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

    override fun onBackPressed() {
        exit()
    }

    private fun exit(){
        val dialogClickListener =
            DialogInterface.OnClickListener { dialog, which ->
                when (which) {
                    DialogInterface.BUTTON_POSITIVE -> {
                        this.finish()
                    }
                    DialogInterface.BUTTON_NEGATIVE -> {
                    }
                }
            }

        val builder: AlertDialog.Builder = AlertDialog.Builder(this,  R.style.AlertDialogStyle)
        builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
            .setNegativeButton("No", dialogClickListener).show()
    }

    override fun AddComment(c: Comment) {
        Observation.ActiveObservation?.Comments?.add(c)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.miQuit -> {
                exit()
                return true
            }
            R.id.miComment -> {
                CommentFragment(this).show(
                    supportFragmentManager,
                    CommentFragment::class.java.simpleName
                )
                return true
            }

            R.id.miHmg -> {
                HmgFragment().show(
                    supportFragmentManager,
                    HmgFragment::class.java.simpleName
                )
                return true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}