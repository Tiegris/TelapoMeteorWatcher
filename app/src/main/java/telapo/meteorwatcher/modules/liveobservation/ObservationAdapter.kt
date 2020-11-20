package telapo.meteorwatcher.modules.liveobservation

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import telapo.meteorwatcher.R
import telapo.meteorwatcher.dal.model.scheme.Scheme

class ObservationAdapter(private val context: Context) :
    RecyclerView.Adapter<ObservationViewHolder>() {

    private var items : MutableList<String> = mutableListOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ObservationViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.observation_row_item, parent, false
        )
        return ObservationViewHolder(view)

    }

    fun Set(scheme: Scheme) {
        this.items = mutableListOf<String>(
            scheme.Swarm1,
            scheme.Swarm2,
            scheme.Swarm3,
            scheme.Swarm4,
            scheme.Swarm5,
            scheme.Swarm6,
            "Spo"
        )
        while (items.remove(""));

        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ObservationViewHolder, position: Int) {
        val item = items[position]
        holder.tvSwarmName.text = item
        //holder.tvVersion.text = item.Version.toString()
    }

    override fun getItemCount(): Int = items.size
}