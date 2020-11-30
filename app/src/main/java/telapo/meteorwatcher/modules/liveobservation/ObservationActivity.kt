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
import telapo.meteorwatcher.dal.model.observation.Comment
import telapo.meteorwatcher.dal.model.observation.Observation
import telapo.meteorwatcher.dal.model.observation.persistance.ObservationManager
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
        tvCycle.text = Observation.ActiveObservation?.GetCycleDuration()
    }

    private fun initRecyclerView() {
        recyclerView = ObservationRecyclerView
        adapter = ObservationAdapter(this)
        val item = Observation.ActiveObservation!!.Scheme
        adapter.Set(item)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    override fun onPause() {
        super.onPause()
        thread {
            ObservationManager.getInstance(this).Save(Observation.ActiveObservation!!)
        }
    }

    override fun onBackPressed() {
        exit()
    }

    private fun exit(){
        val dialogClickListener =
            DialogInterface.OnClickListener { _, which ->
                when (which) {
                    DialogInterface.BUTTON_POSITIVE -> {
                        this.finish()
                    }
                    DialogInterface.BUTTON_NEGATIVE -> {
                    }
                }
            }

        val builder: AlertDialog.Builder = AlertDialog.Builder(this,  R.style.AlertDialogStyle)
        builder.setMessage("Are you sure you want to exit and terminate Observation?").setPositiveButton("Yes", dialogClickListener)
            .setNegativeButton("Cancel", dialogClickListener).show()
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
                tvCycle.text = Observation.ActiveObservation?.GetCycleDuration()
                return true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}