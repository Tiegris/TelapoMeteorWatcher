package telapo.meteorwatcher.modules.main

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import telapo.meteorwatcher.R
import telapo.meteorwatcher.dal.model.observation.Observation
import telapo.meteorwatcher.dal.model.observation.persistance.ObservationManager
import telapo.meteorwatcher.dal.network.NetworkManager
import telapo.meteorwatcher.dal.network.NetworkManager.IObservationUploadResultReceiver
import telapo.meteorwatcher.utility.Formater
import kotlin.concurrent.thread

interface IMainListener {
    fun Delete(observation: Observation)
    fun Upload(observation: Observation)
}

class MainAdapter(private val context: Activity) :
    RecyclerView.Adapter<MainViewHolder>() , IMainListener, IObservationUploadResultReceiver{

    val Items = mutableListOf<Observation>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.statistic_row_item , parent, false
        )
        return MainViewHolder(view)
    }

    fun Update(items: List<Observation>) {
        this.Items.clear()
        this.Items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val item = Items[position]
        holder.tvObservationDate.text = Formater.GetDateTime(item.OfficialStart)
        holder.tvObservationTitle.text = item.Scheme.name;
        if (item.Synced) {
            holder.ibUpload.setImageResource(R.drawable.upload_icon_chackmark)
            holder.ibUpload.isEnabled = false
        }

        holder.listener = this
        holder.item = item
    }

    override fun getItemCount(): Int = Items.size

    override fun Delete(observation: Observation) {
        thread {
            ObservationManager.getInstance(context).Delete(observation)
            Items.remove(observation)
            context.runOnUiThread {
                notifyDataSetChanged()
            }
        }
    }

    override fun Upload(observation: Observation) {
        NetworkManager.SyncObservation(observation, this)
    }

    override fun AcknowledgeSucces(observation: Observation) {
        thread {
            ObservationManager.getInstance(context).Delete(observation)
            observation.Synced = true
            ObservationManager.getInstance(context).Save(observation)
            this.Items.clear()
            this.Items.addAll(ObservationManager.getInstance(context).LoadAll())
            context.runOnUiThread{
                notifyDataSetChanged()
            }
        }

    }

    override fun HandleError(throwable: Throwable) {
        Toast.makeText( context, context.getString(R.string.strNetworkError), Toast.LENGTH_SHORT).show()
    }


}