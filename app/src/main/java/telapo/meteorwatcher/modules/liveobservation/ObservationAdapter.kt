package telapo.meteorwatcher.modules.liveobservation

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import telapo.meteorwatcher.R
import telapo.meteorwatcher.dal.model.scheme.Scheme

class ObservationAdapter(private val context: Context) :
    RecyclerView.Adapter<ObservationViewHolder>() {

    private val items = mutableListOf<Scheme>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ObservationViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.schemes_row_item, parent, false
        )
        return ObservationViewHolder(view)

    }

    fun Set(items: List<Scheme>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ObservationViewHolder, position: Int) {
        val item = items[position]
        //holder.tvName.text = item.Name
        //holder.tvVersion.text = item.Version.toString()
    }

    override fun getItemCount(): Int = items.size
}